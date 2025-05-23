package com.proxmox.test.firewall.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proxmox.test.firewall.entity.FirewallRule;


@Repository
public interface FirewallRuleRepository extends JpaRepository<FirewallRule, Long> {

}

