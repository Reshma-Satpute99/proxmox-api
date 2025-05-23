package com.proxmox.test.cloud;

import org.springframework.http.ResponseEntity;

public interface CloudService {

	public ResponseEntity<String> updateCloudInitConfig(Long vmId, String node, Cloud cloud) ;

}
