package com.hcl.mobileserviceprovider.service;


import com.hcl.mobileserviceprovider.service.dto.*;
import com.hcl.mobileserviceprovider.service.exception.InvalidConnectionIdException;

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
	Optional<List<ConnectionResponse>> retrieveConnections();

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

    /**
     * Obtain connection
     *
     * @param approveOrRejectConnection and id
     * @return ApproveOrRejectConnectionResponse
     */
    ApproveOrRejectConnectionResponse approveOrRejectConnection(ApproveOrRejectConnection approveOrRejectConnection, Long id) throws InvalidConnectionIdException;
}