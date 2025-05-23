package com.proxmox.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.proxmox.test", "com.proxmox.test.Service"})
public class ProxmoxTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProxmoxTestApplication.class, args);
	}

}
