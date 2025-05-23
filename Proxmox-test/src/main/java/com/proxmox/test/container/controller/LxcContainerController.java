package com.proxmox.test.container.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proxmox.test.container.entity.LxcContainerDetails;
import com.proxmox.test.container.service.ContainerService;

@RestController
@RequestMapping("/api/proxmox")
public class LxcContainerController {

	@Autowired
	private final ContainerService containerService;

	public LxcContainerController(ContainerService containerService) {
		this.containerService = containerService;
	}

	@PostMapping("/create-lxc")
	public ResponseEntity<String> createLxc(@RequestBody LxcContainerDetails lxcContainerDetails) {
		return containerService.createLxc(lxcContainerDetails);

	}
}
