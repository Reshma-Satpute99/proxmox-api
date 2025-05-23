package com.inn.proxmox_proxmox_firewall.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inn.proxmox_proxmox_firewall.entity.FirewallRule;
import com.inn.proxmox_proxmox_firewall.service.ProxmoxService;

@RestController
@RequestMapping("/api/firewall")
public class FirewallController {

	private final ProxmoxService proxmoxService;

	public FirewallController(ProxmoxService proxmoxService) {
		this.proxmoxService = proxmoxService;
	}

	@PostMapping("/rule")
	public ResponseEntity<String> addFirewallRule(@RequestBody FirewallRule rule) {
		return proxmoxService.createFirewallRule( rule);
	}
}
