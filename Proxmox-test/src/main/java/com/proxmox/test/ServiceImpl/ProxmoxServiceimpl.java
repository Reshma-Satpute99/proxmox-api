package com.proxmox.test.ServiceImpl;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.proxmox.test.Service.SaveProxmox;
import com.proxmox.test.controller.Controller.ResizeDiskRequest;
import com.proxmox.test.entity.ProxmoxDetails;
import com.proxmox.test.repository.ProxmoxDetailsRepo;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class ProxmoxServiceimpl implements SaveProxmox {

	@Value("${proxmox.api.url}")
	private String proxmoxApiUrl;

	@Value("${proxmox.token.id}")
	private String tokenId;

	@Value("${proxmox.token.secret}")
	private String tokenSecret;

	@Autowired
	ProxmoxDetailsRepo detailsRepo;

	public Long generateId() {
		Long startingId = 1000L; // Starting ID is 1000
		Long maxVmId = detailsRepo.findMaxVmId(); // Get the maximum VM ID from the database
		System.out.println("Max VM ID in DB: " + maxVmId);

		if (maxVmId == null || maxVmId < startingId) {
			return startingId;
		}

		Long generatedId = maxVmId + 1;

		while (isVmIdPresent(generatedId)) {
			generatedId++; // Increment the ID if it already exists
			System.out.println("Generated ID " + generatedId + " already exists, incrementing...");
		}

		return generatedId;
	}

	private boolean isVmIdPresent(Long id) {
		return detailsRepo.existsById(id); // Check if the ID exists in the repository
	}

	@Override
	public ResponseEntity<String> createVM(ProxmoxDetails proxmoxDetails) {
		Long generatedId = generateId();
		proxmoxDetails.setVmId(generatedId);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "PVEAPIToken=" + tokenId + "=" + tokenSecret);
		headers.setContentType(MediaType.APPLICATION_JSON);

		Map<String, Object> payload = new HashMap<>();
		payload.put("vmid", proxmoxDetails.getVmId());
		payload.put("name", proxmoxDetails.getName());
		payload.put("memory", proxmoxDetails.getMemory());
		payload.put("cores", proxmoxDetails.getCores());
		payload.put("sockets", proxmoxDetails.getSockets());
		payload.put("scsihw", "virtio-scsi-pci");
		payload.put("net0", "virtio,bridge=vmbr0");
		payload.put("ostype", "l26");

		ObjectMapper objectMapper = new ObjectMapper();
		String jsonPayload;
		try {
			jsonPayload = objectMapper.writeValueAsString(payload);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("Error serializing request body: " + e.getMessage());
		}

		headers.setContentLength(jsonPayload.getBytes(StandardCharsets.UTF_8).length);

		String url = String.format("%s/nodes/%s/qemu", proxmoxApiUrl, proxmoxDetails.getNode());

		RestTemplate restTemplate = new RestTemplate();
		HttpEntity<String> request = new HttpEntity<>(jsonPayload, headers);

		try {
			ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);

			if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.CREATED) {
				detailsRepo.save(proxmoxDetails);
				return ResponseEntity.ok("VM created successfully: " + response.getBody());
			} else {
				return ResponseEntity.status(response.getStatusCode()).body(
						"Failed to create VM. Status: " + response.getStatusCode() + ", Body: " + response.getBody());
			}

		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error occurred: " + e.getMessage());
		}
	}

	@Override
	public void resizeVmDisk(Long vmId, ResizeDiskRequest payload) {
	    // Fetch VM details from repository
	    ProxmoxDetails fetchedVmDetails = detailsRepo.findByVmId(vmId);

	    if (fetchedVmDetails == null) {
	        throw new RuntimeException("VM-ID is not present. Please try with another one.");
	    }

	    System.err.println("VM ID ==============" + fetchedVmDetails);

	    // Construct the URL for resizing the VM disk
	    String url = String.format("%s/nodes/%s/qemu/%s/resize", proxmoxApiUrl, fetchedVmDetails.getNode(), vmId);

	    System.out.println("URL ----" + url);

	    // Set up headers
	    HttpHeaders headers = new HttpHeaders();
	    headers.add("Authorization", "PVEAPIToken=" + tokenId + "=" + tokenSecret);
	    headers.setContentType(MediaType.APPLICATION_JSON);

	    // Prepare request body using ResizeDiskRequest payload
	    Map<String, Object> requestBody = new HashMap<>();
	    requestBody.put("disk", payload.getDisk()); // Use disk identifier from payload
	    requestBody.put("size", payload.getSize()); // Use size increment from payload

	    ObjectMapper objectMapper = new ObjectMapper();
	    String jsonPayload;

	    try {
	        jsonPayload = objectMapper.writeValueAsString(requestBody);
	    } catch (Exception e) {
	        throw new RuntimeException("Error serializing request body: " + e.getMessage(), e);
	    }

	    HttpEntity<String> request = new HttpEntity<>(jsonPayload, headers);

	    // Make the PUT request to resize the disk using exchange()
	    RestTemplate restTemplate = new RestTemplate();

	    try {
	        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.PUT, request, String.class);

	        if (response.getStatusCode() == HttpStatus.OK || response.getStatusCode() == HttpStatus.NO_CONTENT) {
	            System.out.println("Disk resized successfully: " + response.getBody());
	        } else {
	            throw new RuntimeException(
	                    "Failed to resize disk. Status: " + response.getStatusCode() + ", Body: " + response.getBody());
	        }
	    } catch (HttpServerErrorException e) {
	        // Handle server errors specifically
	        throw new RuntimeException("Server error occurred while resizing VM disk: " + e.getStatusCode() + " - "
	                + e.getResponseBodyAsString(), e);
	    } catch (Exception e) {
	        throw new RuntimeException("Error occurred while resizing VM disk: " + e.getMessage(), e);
	    }
	}

}