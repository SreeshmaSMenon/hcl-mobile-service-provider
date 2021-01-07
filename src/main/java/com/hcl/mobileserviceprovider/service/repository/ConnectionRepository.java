package com.hcl.mobileserviceprovider.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hcl.mobileserviceprovider.service.entity.Connection;

public interface ConnectionRepository extends JpaRepository<Connection,Long> {
	
}
