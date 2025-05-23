package com.proxmox.test.cloud;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CloudRepository extends JpaRepository<Cloud, Integer> {

}
