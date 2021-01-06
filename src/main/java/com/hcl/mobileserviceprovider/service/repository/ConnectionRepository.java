package com.hcl.mobileserviceprovider.service.repository;

import com.hcl.mobileserviceprovider.service.entity.Connection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ConnectionRepository extends JpaRepository<Connection, Long> {

    Optional<Connection> findByMobileId(Long id);
}
