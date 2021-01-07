package com.hcl.mobileserviceprovider.service;

import java.util.List;
import java.util.Optional;

import com.hcl.mobileserviceprovider.service.dto.ConnectionResponse;

/**
 * @since 2021-01-07 This class includes methods for mobile connection services.
 */
public interface ConnectionService {
	
	/**
	 * Retrieves list of connections.
	 * 
	 * @return optional of list of ConnectionResponse
	 */
	public Optional<List<ConnectionResponse>> retrieveConnections();
}
