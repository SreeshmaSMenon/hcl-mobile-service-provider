package com.hcl.mobileserviceprovider.service.repository;

import com.hcl.mobileserviceprovider.service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmailAndIdProofNumber(String email, String idProofNumber);
    Optional<User> findByEmail(String email);
}
