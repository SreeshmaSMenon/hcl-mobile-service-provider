package com.hcl.mobileserviceprovider.service;


import com.hcl.mobileserviceprovider.service.dto.ConnectionResponse;
import com.hcl.mobileserviceprovider.service.dto.ResponseDto;
import com.hcl.mobileserviceprovider.service.dto.UserRequestDto;

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
     *
     * @param id
     * @return ConnectionResponse
     */
    Optional<ConnectionResponse> fetchById(String id);

    /**
     * Obtain connection
     *
     * @param userRequestDto
     * @return ResponseDto
     */
    Optional<ResponseDto> obtainConnection(UserRequestDto userRequestDto);
}