package com.hcl.mobileserviceprovider.service;

import java.util.Optional;

import com.hcl.mobileserviceprovider.service.dto.Connection;

public interface ConnectionService {

    Optional<Connection> fetchById(String id);

}
