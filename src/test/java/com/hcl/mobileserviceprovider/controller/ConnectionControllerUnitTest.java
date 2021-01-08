package com.hcl.mobileserviceprovider.controller;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hcl.mobileserviceprovider.service.ConnectionService;

@ExtendWith(MockitoExtension.class)
public class ConnectionControllerUnitTest {

	@Mock
	ConnectionService connectionService;
	
	@InjectMocks
	ConnectionController connectionController;
	
	
}
