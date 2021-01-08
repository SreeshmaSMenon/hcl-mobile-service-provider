package com.hcl.mobileserviceprovider.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doReturn;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.hcl.mobileserviceprovider.service.ConnectionService;
import com.hcl.mobileserviceprovider.service.dto.ConnectionResponse;
import com.hcl.mobileserviceprovider.service.dto.ResponseDto;
import com.hcl.mobileserviceprovider.service.dto.UserRequestDto;
import com.hcl.mobileserviceprovider.util.Status;

@ExtendWith(MockitoExtension.class)
public class ConnectionControllerUnitTest {

    @Mock
    ConnectionService connectionService;

    @InjectMocks
    ConnectionController connectionController;

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
        doReturn(Optional.of(responseDto)).when(connectionService).obtainConnection(Mockito.any());
        ResponseEntity<Optional<ResponseDto>> actualResponse = connectionController.createConnection(userRequestDto);
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        ResponseDto actualResponseDto = actualResponse.getBody().get();
        assertNotNull(actualResponseDto);
        assertEquals(REQUEST_ID, actualResponseDto.getRequestId());
        assertEquals(MESSAGE, actualResponseDto.getMessage());
    }

    @Test
    public void retrieveConnectionsTest() {
        ConnectionResponse connectionResponse = getConnectionResponse();
        List<ConnectionResponse> connectionResponseList = new ArrayList<>();
        connectionResponseList.add(connectionResponse);
        doReturn(connectionResponseList).when(connectionService).retrieveConnections();
        ResponseEntity<List<ConnectionResponse>> actualResponse = connectionController.retrieveConnections();
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        ConnectionResponse actualConnectionInfo = actualResponse.getBody().get(0);
        assertConnectionResponse(actualConnectionInfo);
    }

    @Test
    public void fetchConnectionByIdTest() {
        ConnectionResponse connectionResponse = getConnectionResponse();
        doReturn(Optional.of(connectionResponse)).when(connectionService).fetchById(Mockito.anyString());
        ResponseEntity<Optional<ConnectionResponse>> actualResponse = connectionController
                .findById(String.valueOf(CONNECTION_ID));
        assertEquals(HttpStatus.OK, actualResponse.getStatusCode());
        ConnectionResponse actualConnectionInfo = actualResponse.getBody().get();
        assertConnectionResponse(actualConnectionInfo);

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

    private void assertConnectionResponse(ConnectionResponse actualConnectionInfo) {
        assertEquals(CONNECTION_ID, actualConnectionInfo.getConnectionId());
        assertEquals(USER_NAME, actualConnectionInfo.getUserName());
        assertEquals(MOBILE_NUMBER, actualConnectionInfo.getMobileNumber());
        assertEquals(PLAN_NAME, actualConnectionInfo.getPlanName());
        assertEquals(Status.IN_PROGRESS.name(), actualConnectionInfo.getStatus());
    }

}