package com.hcl.mobileserviceprovider.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hcl.mobileserviceprovider.service.dto.ConnectionResponse;
import com.hcl.mobileserviceprovider.service.repository.ConnectionRepository;

@ExtendWith(MockitoExtension.class)
public class ConnectionServiceImplTest {

	@Mock
	private ConnectionRepository connectionRepository;
	
	@InjectMocks
	private ConnectionServiceImpl connectionService;
	
	private ConnectionResponse connectionResponse;
	
	@BeforeEach
    public void setUp() {
		connectionResponse= new ConnectionResponse();
     
	}
}
