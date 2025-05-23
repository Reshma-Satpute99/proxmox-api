package com.proxmox.test.container.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface LxcContainerRepository extends JpaRepository<com.proxmox.test.container.entity.LxcContainerDetails, Long> {

	@Query(value = "SELECT COALESCE(MAX(vm_id), 0) FROM `lxc_container_details`", nativeQuery = true)
	Long findMaxContainerId();
}
