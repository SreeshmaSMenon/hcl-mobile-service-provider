package com.hcl.mobileserviceprovider.service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hcl.mobileserviceprovider.service.entity.Connection;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {
	
	Optional<List<Connection>> findByStatus(String status);
}
