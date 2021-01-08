package com.hcl.mobileserviceprovider.service;

import static org.mockito.Mockito.doReturn;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hcl.mobileserviceprovider.service.dto.ConnectionResponse;
import com.hcl.mobileserviceprovider.service.entity.Connection;
import com.hcl.mobileserviceprovider.service.entity.MobileInfo;
import com.hcl.mobileserviceprovider.service.entity.Plan;
import com.hcl.mobileserviceprovider.service.entity.User;
import com.hcl.mobileserviceprovider.service.exception.MobileServiceProviderException;
import com.hcl.mobileserviceprovider.service.repository.ConnectionRepository;
import com.hcl.mobileserviceprovider.util.Status;


@ExtendWith(MockitoExtension.class)
public class ConnectionServiceImplTest {

	private static final Long CONNECTION_ID = 101L;
	private static final String STATUS = Status.IN_PROGRESS.name();
	private static final LocalDate REQUEST_DATE = LocalDate.now();
	private static final LocalDate UPDATE_DATE = LocalDate.now();
	private static final String REMARK = "None";
	private static final String USER_NAME = "Sree";
	private static final String PLAN_NAME = "Plan1";
	private static final String MOBILE_NUMBER = "9876564534";

	@Mock
	private ConnectionRepository connectionRepository;

	@InjectMocks
	private ConnectionServiceImpl connectionService;

	private List<Connection> connections;

	@BeforeEach
	public void setUp() {

		connections = buildConnections();

	}

	@Test
	public void shouldReturnConnectionnList() {
		doReturn(connections).when(connectionRepository).findAllByStatus(STATUS);
		List<ConnectionResponse> responses = connectionService.retrieveConnections();
		ConnectionResponse response = responses.get(0);
		Assert.assertEquals(CONNECTION_ID, response.getConnectionId());

	}
	
	@Test
	public void testFetchByIdWhenIdIsAvailable() {
		Connection conn = getConnection();
		Mockito.when(connectionRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(conn));
		Optional<ConnectionResponse> connectionResponse = connectionService
				.fetchById("1234");
		Assert.assertTrue(connectionResponse.isPresent());
		ConnectionResponse connection = connectionResponse.get();
		Assert.assertEquals(STATUS, connection.getStatus());
		Assert.assertEquals(UPDATE_DATE, connection.getUpdateDate());
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

		Mockito.when(connectionRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(null));
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

		Mockito.when(connectionRepository.findById(Mockito.anyLong()))
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

	private List<Connection> buildConnections() {
		List<Connection> connectionsList = new ArrayList<>();
		Connection connection = getConnection();
		User user = new User();
		user.setName(USER_NAME);
		connection.setUser(user);
		Plan plan = new Plan();
		plan.setPlanName(PLAN_NAME);
		connection.setPlan(plan);
		MobileInfo info = new MobileInfo();
		info.setNumber(MOBILE_NUMBER);
		connection.setMobileInfo(info);
		connectionsList.add(connection);
		return connectionsList;
	}
	
	private Connection getConnection() {
		Connection connection = new Connection();
		connection.setConnectionId(CONNECTION_ID);
		connection.setUpdateDate(UPDATE_DATE);
		connection.setRequestdate(REQUEST_DATE);
		connection.setStatus(STATUS);
		connection.setRemark(REMARK);
		return connection;
	}
}
