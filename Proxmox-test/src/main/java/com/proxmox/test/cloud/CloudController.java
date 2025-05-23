package com.proxmox.test.cloud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CloudController {

	@Autowired(required = true)
	private CloudService cloudService;

	@PostMapping("/vm/{vmId}/node/{node}")
	public ResponseEntity<String> updateCloudConfig(@PathVariable Long vmId, @PathVariable String node,@RequestBody Cloud cloudConfig) {

		return cloudService.updateCloudInitConfig(vmId, node, cloudConfig);
	}
}
