package com.proxmox.test.firewall.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "firewall_rules")
public class FirewallRule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String node;
	private String action;
	private Long vmid;

	@Enumerated(EnumType.STRING)
	private FirewallRuleType type;

	public enum FirewallRuleType {
		in, out, forward, group
	}

	public FirewallRule() {
		super();
		// TODO Auto-generated constructor stub
	}

	public FirewallRule(Long id, String node, String action, FirewallRuleType type) {
		super();
		this.id = id;
		this.node = node;
		this.action = action;
		this.type = type;
	}

	public Long getId() {
		return id;
	}

	public Long getVmid() {
		return vmid;
	}

	public void setVmid(Long vmid) {
		this.vmid = vmid;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNode() {
		return node;
	}

	public void setNode(String node) {
		this.node = node;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public FirewallRuleType getType() {
		return type;
	}

	public void setType(FirewallRuleType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "FirewallRule [id=" + id + ", node=" + node + ", action=" + action + ", type=" + type + "]";
	}

}
