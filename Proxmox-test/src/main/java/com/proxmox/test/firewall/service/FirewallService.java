package com.proxmox.test.firewall.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proxmox.test.firewall.entity.FirewallRule;
import com.proxmox.test.firewall.repository.FirewallRuleRepository;

@Service
public class FirewallService {

	@Value("${proxmox.api.url}")
	private String proxmoxUrl;

	@Value("${proxmox.token.id}")
	private String proxmoxTokenId;

	@Value("${proxmox.token.secret}")
	private String proxmoxTokenSecret; // Ensure this is used for authorization

	private final RestTemplate restTemplate;

	@Autowired
	private FirewallRuleRepository firewallRuleRepository;

	public FirewallService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public ResponseEntity<String> createFirewallRule(FirewallRule rule) {
		String url = String.format("%s/nodes/%s/qemu/%s/firewall/rules", proxmoxUrl, rule.getNode(), rule.getVmid());

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "PVEAPIToken=" + proxmoxTokenId + "=" + proxmoxTokenSecret);
		headers.set("Content-Type", "application/json");

		System.out.println("TOKEN-ID : " + proxmoxTokenId + " ========" + "SECRET-KEY :" + proxmoxTokenSecret);

		Map<String, Object> payload = new HashMap<>();
		payload.put("vmid", rule.getVmid());
		payload.put("node", rule.getNode());
		payload.put("action", rule.getAction());
		payload.put("type", rule.getType().name());

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonPayload;
		try {
			jsonPayload = objectMapper.writeValueAsString(payload);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error serializing request body: " + e.getMessage());
		}

		HttpEntity<String> requestEntity = new HttpEntity<>(jsonPayload, headers);

		try {
			ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

			if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.CREATED) {
				firewallRuleRepository.save(rule);
				return ResponseEntity.ok("FirewallRule created successfully: " + response.getBody());
			} else {
				return ResponseEntity.status(response.getStatusCode()).body("Failed to create FirewallRule. Status: "
						+ response.getStatusCode() + ", Body: " + response.getBody());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
		}
	}
}
