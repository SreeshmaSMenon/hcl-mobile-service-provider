package com.hcl.mobileserviceprovider.service.repository;

import com.hcl.mobileserviceprovider.service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {
}
