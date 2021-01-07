package com.hcl.mobileserviceprovider.service.impl;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import com.hcl.mobileserviceprovider.service.ConnectionService;
import com.hcl.mobileserviceprovider.service.entity.Connection;
import com.hcl.mobileserviceprovider.service.exception.MobileServiceProviderException;
import com.hcl.mobileserviceprovider.service.repository.ConnectionRepository;

@RunWith(MockitoJUnitRunner.class)
public class ConnectionServiceTest {

	@InjectMocks
	private ConnectionService connectionService = new ConnectionServiceImpl();

	@Mock
	private ConnectionRepository ConnectionRepository;

	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	public void testFetchByIdWhenIdIsAvailable() {
		Connection conn = new Connection();
		LocalDate updateDate = LocalDate.of(2020, 10, 10);
		conn.setConnectionId(1234L);
		conn.setUpdateDate(updateDate);
		conn.setStatus("In-progress");
		Mockito.when(ConnectionRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(conn));
		Optional<com.hcl.mobileserviceprovider.service.dto.Connection> connectionResponse = connectionService
				.fetchById("1234");
		Assert.assertTrue(connectionResponse.isPresent());
		com.hcl.mobileserviceprovider.service.dto.Connection connection = connectionResponse.get();
		Assert.assertEquals("In-progress", connection.getStatus());
		Assert.assertEquals(updateDate, connection.getUpdateDate());
		Assert.assertNull(connection.getConnectionId());
		Assert.assertNull(connection.getRemark());

	}

	@Test
	public void testFetchByIdWhenIdIsAvailableNullValue() {

		Boolean isException = false;
		try {
			connectionService.fetchById(null);
		} catch (Exception ex) {
			isException = true;
			Assert.assertEquals("Please pass the proper value for request id in request", ex.getMessage());
			Assert.assertTrue(ex instanceof MobileServiceProviderException);
		}
		Assert.assertTrue(isException);

	}

	@Test
	public void testFetchByIdWhenIdNotAvailable() {

		Mockito.when(ConnectionRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));
		Boolean isException = false;
		try {
			connectionService.fetchById("1234");
		} catch (Exception ex) {
			isException = true;
			Assert.assertEquals("Error while fetching connection details by id", ex.getMessage());
			Assert.assertTrue(ex instanceof MobileServiceProviderException);
		}
		Assert.assertTrue(isException);

	}

	@Test
	public void testFetchByIdWhenExceptionOnDbCall() {

		Mockito.when(ConnectionRepository.findById(Mockito.anyLong()))
				.thenThrow(new RuntimeException("Exception while fetching data from db"));
		Boolean isException = false;
		try {
			connectionService.fetchById("1234");
		} catch (Exception ex) {
			isException = true;
			Assert.assertEquals("Error while fetching connection details by id", ex.getMessage());
			Assert.assertTrue(ex instanceof MobileServiceProviderException);
		}
		Assert.assertTrue(isException);

	}

}