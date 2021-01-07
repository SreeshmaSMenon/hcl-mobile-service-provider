package com.hcl.mobileserviceprovider.service.repository;

import com.hcl.mobileserviceprovider.service.entity.Connection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ConnectionRepository extends JpaRepository<Connection, Long> {

    @Query("select c from Connection c where c.mobileInfo.mobileId=:mobileId")
    Optional<Connection> findByMobileInfo(@Param("mobileId") Long mobileId);

    List<Connection> findAllByStatus(String status);

}
