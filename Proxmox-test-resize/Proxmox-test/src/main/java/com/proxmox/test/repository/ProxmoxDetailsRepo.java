package com.proxmox.test.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.proxmox.test.entity.ProxmoxDetails;

@Repository
public interface ProxmoxDetailsRepo extends JpaRepository<ProxmoxDetails, Long> {
	@Query(value = "SELECT COALESCE(MAX(vm_id), 0) FROM proxmos_details", nativeQuery = true)
	Long findMaxVmId();
	
	
	ProxmoxDetails findByVmId(Long vmId);
}
