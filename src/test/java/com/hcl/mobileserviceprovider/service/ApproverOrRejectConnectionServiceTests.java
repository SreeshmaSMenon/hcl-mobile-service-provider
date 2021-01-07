package com.hcl.mobileserviceprovider.service;

import com.hcl.mobileserviceprovider.service.dto.ApproveOrRejectConnection;
import com.hcl.mobileserviceprovider.service.dto.ApproveOrRejectConnectionResponse;
import com.hcl.mobileserviceprovider.service.entity.Connection;
import com.hcl.mobileserviceprovider.service.exception.InvalidConnectionIdException;
import com.hcl.mobileserviceprovider.service.impl.ApproveOrRejectConnectionServiceImpl;
import com.hcl.mobileserviceprovider.service.repository.ConnectionRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class ApproverOrRejectConnectionServiceTests {

    @InjectMocks
    ApproveOrRejectConnectionServiceImpl approveOrRejectConnectionService;

    @Mock
    ConnectionRepository connectionRepository;

    Connection connection = null;
    ApproveOrRejectConnection approveOrRejectConnection = null;
    ApproveOrRejectConnectionResponse approveOrRejectConnectionResponse = null;

    @Before
    public void setup() {
        connection = new Connection();
        connection.setConnectionId(1L);
        connection.setRemark("Accepted");
        connection.setStatus("In-progress");

        approveOrRejectConnection = new ApproveOrRejectConnection();
        approveOrRejectConnection.setRemark("Accepted");
        approveOrRejectConnection.setStatus("Approved");

        approveOrRejectConnectionResponse = new ApproveOrRejectConnectionResponse();
        approveOrRejectConnectionResponse.setStatusCode(200);
        approveOrRejectConnectionResponse.setMessage("Success");
    }

    @Test
    public void approveOrRejectConnection() throws InvalidConnectionIdException {
        Mockito.when(connectionRepository.findById(1L)).thenReturn(Optional.of(connection));

        ApproveOrRejectConnectionResponse result = approveOrRejectConnectionService.approveOrRejectConnection(approveOrRejectConnection, 1L);

        Assert.assertEquals(approveOrRejectConnectionResponse.getMessage(), result.getMessage());
        Assert.assertEquals(approveOrRejectConnectionResponse.getStatusCode(), result.getStatusCode());
    }

    @Test(expected =  InvalidConnectionIdException.class)
    public void approveOrRejectConnectionForException() throws InvalidConnectionIdException {
        approveOrRejectConnectionService.approveOrRejectConnection(approveOrRejectConnection, 2L);
    }

}
