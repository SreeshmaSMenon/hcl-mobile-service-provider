package com.hcl.mobileserviceprovider.service.impl;

import com.hcl.mobileserviceprovider.service.ApproveOrRejectConnectionService;
import com.hcl.mobileserviceprovider.service.dto.ApproveOrRejectConnection;
import com.hcl.mobileserviceprovider.service.dto.ApproveOrRejectConnectionResponse;
import com.hcl.mobileserviceprovider.service.entity.Connection;
import com.hcl.mobileserviceprovider.service.exception.ErrorResponse;
import com.hcl.mobileserviceprovider.service.exception.InvalidConnectionIdException;
import com.hcl.mobileserviceprovider.service.repository.ConnectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.hcl.mobileserviceprovider.util.MobileServiceProviderConstants.*;

@Service
public class ApproveOrRejectConnectionServiceImpl implements ApproveOrRejectConnectionService {

    @Autowired
    private ConnectionRepository connectionRepository;

    @Override
    public ApproveOrRejectConnectionResponse approveOrRejectConnection(ApproveOrRejectConnection approveOrRejectConnection, Long id) throws InvalidConnectionIdException {
        ApproveOrRejectConnectionResponse approveOrRejectConnectionResponse = new ApproveOrRejectConnectionResponse();
        Optional<Connection> connectionDetails = connectionRepository.findById(id);
        if (connectionDetails.isPresent()) {
            Connection connection = connectionDetails.get();
            connection.setStatus(approveOrRejectConnection.getStatus());
            connection.setRemark(approveOrRejectConnection.getRemark());
            connectionRepository.save(connection);
        } else {
            throw new InvalidConnectionIdException(CONNECTION_ID_NOT_FOUND);
        }
        approveOrRejectConnectionResponse.setMessage(SUCCESS_MESSAGE);
        approveOrRejectConnectionResponse.setStatusCode(SUCCESS_CODE);
        return approveOrRejectConnectionResponse;
    }
}
