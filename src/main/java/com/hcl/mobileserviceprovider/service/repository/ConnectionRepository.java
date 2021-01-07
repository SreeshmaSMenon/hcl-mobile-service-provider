package com.hcl.mobileserviceprovider.service.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hcl.mobileserviceprovider.service.entity.Connection;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {
	
	Optional<Connection> findByMobileId(Long id);
	@Query(value = "SELECT * FROM mobile_service_provider.connection c JOIN mobile_service_provider.plan p ON c.plan_id=p.plan_id AND c.status='InProgress' JOIN mobile_service_provider.mobile_info m ON c.mobile_id=m.mobile_id", nativeQuery = true)
	Optional<List<Connection>> findByStatuss(@Param("status") String status);

}
