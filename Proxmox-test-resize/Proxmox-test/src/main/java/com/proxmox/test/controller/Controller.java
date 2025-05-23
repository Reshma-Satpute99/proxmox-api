package com.proxmox.test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.proxmox.test.Service.SaveProxmox;
import com.proxmox.test.entity.ProxmoxDetails;

@RestController
public class Controller {

	@Value("${proxmox.api.url}")
	private String proxmoxApiUrl;

	@Value("${proxmox.token.id}")
	private String tokenId;

	@Value("${proxmox.token.secret}")
	private String tokenSecret;

	@Autowired
	SaveProxmox saveProxmox;

	@PostMapping("/create-vm")
	public ResponseEntity<String> createVM(@RequestBody ProxmoxDetails proxmoxDetails) {
		System.err.println("Inside create-vm api of controller");
		try {
			return saveProxmox.createVM(proxmoxDetails);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
		}
	}

	@PutMapping("/{vmId}/resize")
	public ResponseEntity<String> resizeVmDisk(@PathVariable Long vmId, @RequestBody ResizeDiskRequest payload) {
		System.err.println("----------------" + vmId + "  from controller  " + payload);
		try {
			saveProxmox.resizeVmDisk(vmId, payload);
			System.err.println("from try catch" + vmId + payload);
			return ResponseEntity.ok("Disk resized successfully.");
		} catch (RuntimeException e) {
			return ResponseEntity.status(500).body("Error resizing disk: " + e.getMessage());
		}
	}

	public static class ResizeDiskRequest {
		private String disk; // Disk identifier (e.g., "scsi0")
		private String size; // Size increment (e.g., "+1G")

		// Getters and Setters
		public String getDisk() {
			return disk;
		}

		public void setDisk(String disk) {
			this.disk = disk;
		}

		public String getSize() {
			return size;
		}

		public void setSize(String size) {
			this.size = size;
		}
	}

}
