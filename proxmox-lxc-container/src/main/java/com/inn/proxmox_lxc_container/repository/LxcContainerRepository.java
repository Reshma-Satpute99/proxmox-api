package com.inn.proxmox_lxc_container.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.inn.proxmox_lxc_container.entity.LxcContainerDetails;

@Repository
public interface LxcContainerRepository extends JpaRepository<LxcContainerDetails, Long> {

	@Query(value = "SELECT COALESCE(MAX(vm_id), 0) FROM `lxc_container_details`", nativeQuery = true)
	Long findMaxContainerId();
}
