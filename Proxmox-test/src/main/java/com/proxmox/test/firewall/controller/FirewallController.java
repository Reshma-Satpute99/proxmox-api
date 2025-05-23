package com.proxmox.test.firewall.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proxmox.test.firewall.entity.FirewallRule;
import com.proxmox.test.firewall.service.FirewallService;


@RestController
@RequestMapping("/api/firewall")
public class FirewallController {

	private final FirewallService firewallService;

	public FirewallController(FirewallService firewallService) {
		this.firewallService = firewallService;
	}

	@PostMapping("/rule")
	public ResponseEntity<String> addFirewallRule(@RequestBody FirewallRule rule) {
		return firewallService.createFirewallRule( rule);
	}
}

