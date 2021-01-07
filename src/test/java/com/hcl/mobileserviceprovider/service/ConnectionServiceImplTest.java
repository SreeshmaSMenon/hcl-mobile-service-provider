package com.hcl.mobileserviceprovider.service;

import static org.mockito.Mockito.doReturn;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hcl.mobileserviceprovider.service.dto.ConnectionResponse;
import com.hcl.mobileserviceprovider.service.entity.Connection;
import com.hcl.mobileserviceprovider.service.entity.MobileInfo;
import com.hcl.mobileserviceprovider.service.entity.Plan;
import com.hcl.mobileserviceprovider.service.entity.User;
import com.hcl.mobileserviceprovider.service.repository.ConnectionRepository;
import com.hcl.mobileserviceprovider.util.Status;

import junit.framework.Assert;

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

	private List<Connection> buildConnections() {
		List<Connection> connectionsList = new ArrayList<>();
		Connection connection = new Connection();
		connection.setConnectionId(CONNECTION_ID);
		connection.setUpdateDate(UPDATE_DATE);
		connection.setRequestdate(REQUEST_DATE);
		connection.setStatus(STATUS);
		connection.setRemark(REMARK);
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
}
