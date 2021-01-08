package com.hcl.mobileserviceprovider.controller;

import com.hcl.mobileserviceprovider.service.ConnectionService;
import com.hcl.mobileserviceprovider.service.dto.*;
import com.hcl.mobileserviceprovider.service.entity.Connection;
import com.hcl.mobileserviceprovider.service.exception.InvalidConnectionIdException;
import com.hcl.mobileserviceprovider.util.Status;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class ConnectionControllerUnitTest {

    @Mock
    ConnectionService connectionService;

    @InjectMocks
    ConnectionController connectionController;


    private static final String REMARK = "None";
    private static final String USER_NAME = "Sree";
    private static final String PLAN_NAME = "Plan1";
    private static final String MOBILE_NUMBER = "9876564534";
    private static final String ALT_MOBILE_NUMBER = "9876564534";
    private static final String EMAIL = "a@gmail.com";
    private static final String ADHAR_NUM = "1234123412341234";
    private static final String ADHAR_TYPE = "Adhar";
    private static final String LOCATION = "Banglore";
    private static final Long PLAN_ID = 1L;
    private static final Long MOBILE_ID = 1L;
    private static final Long CONNECTION_ID = 1L;
    private static final String MESSAGE = "Successfully created";
    private static final Integer STATUS_CODE = 200;
    private static final Long REQUEST_ID = 1L;


    @Test
    public void obtainConnectionTest() {

        UserRequestDto userRequestDto = getUserRequestDto();
        ResponseDto responseDto = getResponseDto();

        Mockito.when(connectionService.obtainConnection(Mockito.any()))
                .thenReturn(Optional.of(responseDto));

        ResponseEntity<Optional<ResponseDto>> actualResponse = connectionController.createConnection(userRequestDto);
        Assert.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
    }

    @Test
    public void retrieveConnectionsTest() {
        ConnectionResponse connectionResponse = getConnectionResponse();
        List<ConnectionResponse> connectionResponseList = new ArrayList<>();
        connectionResponseList.add(connectionResponse);
        Mockito.when(connectionService.retrieveConnections())
                .thenReturn(connectionResponseList);


        ResponseEntity<List<ConnectionResponse>> actualResponse = connectionController.retrieveConnections();
        Assert.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());

    }

    @Test
    public void fetchConnectionByIdTest() {
        ConnectionResponse connectionResponse = getConnectionResponse();
        Mockito.when(connectionService.fetchById(Mockito.anyString()))
                .thenReturn(Optional.of(connectionResponse));


        ResponseEntity<Optional<ConnectionResponse>> actualResponse = connectionController.findById(String.valueOf(CONNECTION_ID));
        Assert.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());

    }

    @Test
    public void testApproveOrRejectConnection() throws InvalidConnectionIdException {
        ApproveOrRejectConnection approveOrRejectConnection = getApproveOrRejectConnection();
        ApproveOrRejectConnectionResponse approveOrRejectConnectionResponse = getApproveOrRejectConnectionResponse();
        Mockito.when(connectionService.approveOrRejectConnection(approveOrRejectConnection, 1L)).thenReturn(approveOrRejectConnectionResponse);

        ResponseEntity<ApproveOrRejectConnectionResponse> result = connectionController.approveOrRejectConnection(approveOrRejectConnection, 1L);

        Assert.assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    private ConnectionResponse getConnectionResponse() {
        ConnectionResponse connectionResponse = new ConnectionResponse();
        connectionResponse.setUserName(USER_NAME);
        connectionResponse.setMobileNumber(MOBILE_NUMBER);
        connectionResponse.setPlanName(PLAN_NAME);
        connectionResponse.setStatus(Status.IN_PROGRESS.toString());
        connectionResponse.setConnectionId(CONNECTION_ID);
        connectionResponse.setUpdateDate(LocalDate.now());
        connectionResponse.setRequestdate(LocalDate.now());
        return connectionResponse;
    }

    private ResponseDto getResponseDto() {
        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage(MESSAGE);
        responseDto.setRequestId(REQUEST_ID);
        responseDto.setStatusCode(STATUS_CODE);
        return responseDto;
    }

    private UserRequestDto getUserRequestDto() {
        UserRequestDto userRequestDto = new UserRequestDto();
        userRequestDto.setMobileId(MOBILE_ID);
        userRequestDto.setPlanId(PLAN_ID);
        userRequestDto.setAltMobileNumber(ALT_MOBILE_NUMBER);
        userRequestDto.setName(USER_NAME);
        userRequestDto.setEmail(EMAIL);
        userRequestDto.setIdProofNumber(ADHAR_NUM);
        userRequestDto.setIdProofType(ADHAR_TYPE);
        userRequestDto.setLocation(LOCATION);
        return userRequestDto;
    }

    private Connection getConnection() {

        Connection connection = new Connection();
        connection.setRequestdate(LocalDate.now());
        connection.setStatus(Status.IN_PROGRESS.toString());
        connection.setUpdateDate(LocalDate.now());
        connection.setConnectionId(CONNECTION_ID);
        connection.setRemark(REMARK);
        return connection;
    }

    private ApproveOrRejectConnection getApproveOrRejectConnection() {
        ApproveOrRejectConnection approveOrRejectConnection = new ApproveOrRejectConnection();
        approveOrRejectConnection.setRemark("Accepted");
        approveOrRejectConnection.setStatus("Approved");
        return approveOrRejectConnection;
    }

    private ApproveOrRejectConnectionResponse getApproveOrRejectConnectionResponse() {
        ApproveOrRejectConnectionResponse approveOrRejectConnectionResponse = new ApproveOrRejectConnectionResponse();
        approveOrRejectConnectionResponse.setStatusCode(200);
        approveOrRejectConnectionResponse.setMessage("Success");
        return approveOrRejectConnectionResponse;
    }

}
