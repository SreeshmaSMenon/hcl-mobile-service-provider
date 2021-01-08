package com.hcl.mobileserviceprovider.service.repository;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hcl.mobileserviceprovider.service.entity.Connection;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {

	@Query("select c from Connection c where c.mobileInfo.mobileId=:mobileId")
	Optional<Connection> findByMobileInfo(@Param("mobileId") Long mobileId);

	List<Connection> findAllByStatus(String status);

	@Modifying
	@Transactional
	@Query("update Connection c set c.status='CONNECTION_ENABLED', c.updateDate=now() " + "where c.status='APPROVED'")
	void activateConnections();

}
