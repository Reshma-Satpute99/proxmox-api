package com.inn.proxmox_lxc_container.service;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.inn.proxmox_lxc_container.entity.LxcContainerDetails;
import com.inn.proxmox_lxc_container.repository.LxcContainerRepository;

@Service
public class ProxmoxService {

	@Value("${proxmox.api.url}")
	private String apiUrl;

	@Value("${proxmox.token.id}")
	private String tokenId;

	@Value("${proxmox.token.secret}")
	private String tokenSecret;

	@Autowired
	private LxcContainerRepository lxcContainerRepository;

	private final RestTemplate restTemplate;

	public ProxmoxService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public Long generateId() {
		Long startingId = 1000L; // Starting ID is 1000
		Long maxContainerId = lxcContainerRepository.findMaxContainerId(); // Get the maximum VM ID from the database
		System.out.println("Max VM ID in DB: " + maxContainerId);

		if (maxContainerId == null || maxContainerId < startingId) {
			return startingId;
		}

		Long generatedId = maxContainerId + 1;

		while (isContainerIdPresent(generatedId)) {
			generatedId++; // Increment the ID if it already exists
			System.out.println("Generated ID " + generatedId + " already exists, incrementing...");
		}

		return generatedId;
	}

	private boolean isContainerIdPresent(Long id) {
		return lxcContainerRepository.existsById(id); // Check if the ID exists in the repository
	}

	
	public ResponseEntity<String> createLxc(LxcContainerDetails lxcContainerDetails) {
		Long generatedId = generateId();
		lxcContainerDetails.setVmId(generatedId);
		
		System.out.println("vmid= ============== " +lxcContainerDetails.getVmid());
		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "PVEAPIToken=" + tokenId + "=" + tokenSecret);
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> payload = new HashMap<>();
		payload.put("vmid", lxcContainerDetails.getVmid()); // Make sure this is set correctly
		payload.put("hostname", lxcContainerDetails.getHostname());
		payload.put("memory", lxcContainerDetails.getMemory()); // Ensure this is an integer
		payload.put("cores", 2); // Set this to an integer value representing the number of cores
		payload.put("node", lxcContainerDetails.getNode());
		payload.put("storage", lxcContainerDetails.getStorege()); // Ensure this points to valid storage
		payload.put("ostype", "ubuntu"); // Use 'l26' for Ubuntu
		payload.put("arch", "amd64"); // Use 'x86_64' instead of 'amd64'
		payload.put("ostemplate", lxcContainerDetails.getOstemplate()); // Use 'x86_64' instead of 'amd64'

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonPayload;
		try {
			jsonPayload = objectMapper.writeValueAsString(payload);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error serializing request body: " + e.getMessage());
		}

		headers.setContentLength(jsonPayload.getBytes(StandardCharsets.UTF_8).length);

		String url = String.format("%s/nodes/%s/lxc", apiUrl, lxcContainerDetails.getNode());

		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> request = new HttpEntity<>(jsonPayload, headers);

		try {
			ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

			if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.CREATED) {
				lxcContainerRepository.save(lxcContainerDetails);
				return ResponseEntity.ok("Container created successfully: " + response.getBody());
			} else {
				return ResponseEntity.status(response.getStatusCode()).body(
						"Failed to create container. Status: " + response.getStatusCode() + ", Body: " + response.getBody());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
		}
	}

	

}
