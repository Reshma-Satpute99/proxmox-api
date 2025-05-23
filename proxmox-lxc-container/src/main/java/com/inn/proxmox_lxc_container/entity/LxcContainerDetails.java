package com.inn.proxmox_lxc_container.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class LxcContainerDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private Long vmid;
	private String hostname;
	private String node;
	private String ostemplate;
	private String storage;
	private String memory;
	private String core;

	@Enumerated(EnumType.STRING)
	private OperatingSystemType ostype;

	public enum OperatingSystemType {
		debian, devuan, ubuntu, centos, fedora, opensuse, archlinux, alpine, gentoo, nixos;
	}

	@Enumerated(EnumType.STRING)
	private Architecture arch;

	public enum Architecture {
		amd64, i386, arm64, armhf, riscv32, riscv64;
	}

	public LxcContainerDetails() {
		super();
		// TODO Auto-generated constructor stub
	}

	public LxcContainerDetails(Long id, Long vmid, String hostname, String node, String ostemplate, String storage,
			String memory, String core, OperatingSystemType ostype, Architecture arch) {
		super();
		this.id = id;
		this.vmid = vmid;
		this.hostname = hostname;
		this.node = node;
		this.ostemplate = ostemplate;
		this.storage = storage;
		this.memory = memory;
		this.core = core;
		this.ostype = ostype;
		this.arch = arch;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVmid() {
		return vmid;
	}

	public void setVmId(Long vmid) {
		this.vmid = vmid;
	}

	public String getHostname() {
		return hostname;
	}

	public void setHostname(String hostname) {
		this.hostname = hostname;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getOstemplate() {
		return ostemplate;
	}

	public void setOstemplate(String ostemplate) {
		this.ostemplate = ostemplate;
	}

	public String getStorege() {
		return storage;
	}

	public void setStorege(String storege) {
		this.storage = storege;
	}

	public String getMemory() {
		return memory;
	}

	public void setMemory(String memory) {
		this.memory = memory;
	}

	public String getCore() {
		return core;
	}

	public void setCore(String core) {
		this.core = core;
	}

	public String getStorage() {
		return storage;
	}

	public void setStorage(String storage) {
		this.storage = storage;
	}

	public OperatingSystemType getOstype() {
		return ostype;
	}

	public void setOstype(OperatingSystemType ostype) {
		this.ostype = ostype;
	}

	public Architecture getArch() {
		return arch;
	}

	public void setArch(Architecture arch) {
		this.arch = arch;
	}

	public void setVmid(Long vmid) {
		this.vmid = vmid;
	}

	@Override
	public String toString() {
		return "LxcContainerDetails [id=" + id + ", vmId=" + vmid + ", hostname=" + hostname + ", node=" + node
				+ ", ostemplate=" + ostemplate + ", storege=" + storage + ", memory=" + memory + ", core=" + core
				+ ", ostype=" + ostype + ", arch=" + arch + "]";
	}

}
