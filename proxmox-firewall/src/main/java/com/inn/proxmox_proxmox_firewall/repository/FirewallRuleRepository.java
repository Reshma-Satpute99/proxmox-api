package com.inn.proxmox_proxmox_firewall.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.inn.proxmox_proxmox_firewall.entity.FirewallRule;

@Repository
public interface FirewallRuleRepository extends JpaRepository<FirewallRule, Long> {

}
