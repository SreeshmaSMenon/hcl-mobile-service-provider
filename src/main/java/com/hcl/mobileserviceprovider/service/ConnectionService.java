package com.hcl.mobileserviceprovider.service;

import com.hcl.mobileserviceprovider.service.dto.Connection;
import com.hcl.mobileserviceprovider.service.dto.ConnectionResponse;

import java.util.List;
import java.util.Optional;

/**
 * @since 2021-01-07 This class includes methods for mobile connection services.
 */
public interface ConnectionService {

    /**
     * Retrieves list of connections.
     *
     * @return optional of list of ConnectionResponse
     */
    List<ConnectionResponse> retrieveConnections();
    
    /**
     * Retrieves connection by id
     * @param id
     * @return
     */
    Optional<ConnectionResponse> fetchById(String id);
}