package com.hcl.mobileserviceprovider.controller;

import com.hcl.mobileserviceprovider.service.ConnectionService;
import com.hcl.mobileserviceprovider.service.dto.ConnectionResponse;
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
    private static final Long USER_ID = 1L;
    private static final Long CONNECTION_ID = 1L;


    @Test
    public void getAllEmployeesTest() {
        ConnectionResponse connectionResponse = getConnectionResponse();
        List<ConnectionResponse> connectionResponseList = new ArrayList<>();
        connectionResponseList.add(connectionResponse);
        Mockito.when(connectionService.retrieveConnections())
                .thenReturn(connectionResponseList);


        ResponseEntity<List<ConnectionResponse>> actualResponse = connectionController.retrieveConnections();
        Assert.assertEquals(HttpStatus.OK, actualResponse.getStatusCode());

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


}
