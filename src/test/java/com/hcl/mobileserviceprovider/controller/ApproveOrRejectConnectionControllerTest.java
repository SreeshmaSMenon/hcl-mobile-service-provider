package com.hcl.mobileserviceprovider.controller;

import com.hcl.mobileserviceprovider.service.ApproveOrRejectConnectionService;
import com.hcl.mobileserviceprovider.service.dto.ApproveOrRejectConnection;
import com.hcl.mobileserviceprovider.service.dto.ApproveOrRejectConnectionResponse;
import com.hcl.mobileserviceprovider.service.entity.Connection;
import com.hcl.mobileserviceprovider.service.exception.InvalidConnectionIdException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

@RunWith(MockitoJUnitRunner.class)
public class ApproveOrRejectConnectionControllerTest {

    @InjectMocks
    ApproveOrRejectConnectionController approveOrRejectConnectionController;

    @Mock
    ApproveOrRejectConnectionService approveOrRejectConnectionService;

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
        approveOrRejectConnectionResponse.setMessage("Connection Id not found");
    }

    @Test
    public void testApproveOrRejectConnection() throws InvalidConnectionIdException {
        Mockito.when(approveOrRejectConnectionService.approveOrRejectConnection(approveOrRejectConnection, 1L)).thenReturn(approveOrRejectConnectionResponse);

        ResponseEntity<ApproveOrRejectConnectionResponse> result = approveOrRejectConnectionController.approveOrRejectConnection(approveOrRejectConnection, 1L);

        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
    }

}
