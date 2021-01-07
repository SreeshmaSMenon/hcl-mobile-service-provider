package com.hcl.mobileserviceprovider.service;

import com.hcl.mobileserviceprovider.service.dto.ConnectionResponse;

import java.util.List;

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
}
