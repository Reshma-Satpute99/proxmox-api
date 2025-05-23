package com.inn.proxmox_lxc_container.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inn.proxmox_lxc_container.entity.LxcContainerDetails;
import com.inn.proxmox_lxc_container.service.ProxmoxService;

@RestController
@RequestMapping("/api/proxmox")
public class ProxmoxController {

	@Autowired
	private final ProxmoxService proxmoxService;

	public ProxmoxController(ProxmoxService proxmoxService) {
		this.proxmoxService = proxmoxService;
	}

	@PostMapping("/create-lxc")
	public ResponseEntity<String> createLxc(@RequestBody LxcContainerDetails lxcContainerDetails) {
		return proxmoxService.createLxc(lxcContainerDetails);
		
	}
}
