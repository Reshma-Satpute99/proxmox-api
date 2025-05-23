package com.proxmox.test.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Table
@Entity(name = "proxmos_details")
public class ProxmoxDetails {

	@Id
	@Column(name = "vm_id", length = 100)
	private Long vmId;

	@Column(name = "cores", length = 50)
	private int cores;

	@Column(name = "node", nullable = false, length = 150)
	private String node;

	@Column(name = "memory", length = 100)
	private int memory;
	
	@Column(name = "sockets", length = 100)
	private int sockets;

	@Column(name = "name", length = 100)
	private String name;

	@Column(name = "virtio", length = 100)
	private String virtio;

	@Column(name = "storage", length = 100)
	private String storage;

	@Column(name = "pool", length = 100)
	private String pool;
	
	private String disk;
	
	private String size;
	

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

	public Long getVmId() {
		return vmId;
	}

	public void setVmId(Long vmId) {
		this.vmId = vmId;
	}

	public int getCores() {
		return cores;
	}

	public void setCores(int cores) {
		this.cores = cores;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public int getMemory() {
		return memory;
	}

	public void setMemory(int memory) {
		this.memory = memory;
	}

	public int getSockets() {
		return sockets;
	}

	public void setSockets(int sockets) {
		this.sockets = sockets;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVirtio() {
		return virtio;
	}

	public void setVirtio(String virtio) {
		this.virtio = virtio;
	}

	public String getStorage() {
		return storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	public String getPool() {
		return pool;
	}

	public void setPool(String pool) {
		this.pool = pool;
	}

}
