package com.hcl.mobileserviceprovider.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
import org.springframework.beans.BeanUtils;

import com.hcl.mobileserviceprovider.service.dto.ConnectionResponse;
import com.hcl.mobileserviceprovider.service.dto.ResponseDto;
import com.hcl.mobileserviceprovider.service.dto.UserRequestDto;
import com.hcl.mobileserviceprovider.service.entity.Connection;
import com.hcl.mobileserviceprovider.service.entity.MobileInfo;
import com.hcl.mobileserviceprovider.service.entity.Plan;
import com.hcl.mobileserviceprovider.service.entity.User;
import com.hcl.mobileserviceprovider.service.exception.MobileServiceProviderException;
import com.hcl.mobileserviceprovider.service.exception.UserNotFoundException;
import com.hcl.mobileserviceprovider.service.repository.ConnectionRepository;
import com.hcl.mobileserviceprovider.service.repository.MobileInfoRepository;
import com.hcl.mobileserviceprovider.service.repository.UserRepository;
import com.hcl.mobileserviceprovider.util.MobileServiceProviderConstants;
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
	private static final String ALT_MOBILE_NUMBER = "9876564534";
	private static final String EMAIL = "a@gmail.com";
	private static final String ADHAR_NUM = "1234123412341234";
	private static final String ADHAR_TYPE = "Adhar";
	private static final String LOCATION = "Banglore";
	private static final Long PLAN_ID = 1L;
	private static final Long MOBILE_ID = 1L;
	private static final Long USER_ID = 1L;

	@Mock
	private ConnectionRepository connectionRepository;
	@Mock
	private MobileInfoRepository mobileInfoRepository;
	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private ConnectionServiceImpl connectionService;

	private List<Connection> connections;
	private UserRequestDto userRequestDto;

	private Connection connection;
	private MobileInfo mobileInfo;
	private User user;
	private Plan plan;

	@BeforeEach
	public void setUp() {

		connections = buildConnections();
		doCreateConnection();
	}

	@Test
	public void shouldReturnConnectionnList() {
		doReturn(connections).when(connectionRepository).findAllByStatus(STATUS);
		List<ConnectionResponse> responses = connectionService.retrieveConnections();
		ConnectionResponse response = responses.get(0);
		assertEquals(CONNECTION_ID, response.getConnectionId());

	}

	@Test
	public void testFetchByIdWhenIdIsAvailable() {
		Connection conn = getConnection();
		Mockito.when(connectionRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(conn));
		Optional<ConnectionResponse> connectionResponse = connectionService.fetchById("1234");
		assertTrue(connectionResponse.isPresent());
		ConnectionResponse connection = connectionResponse.get();
		assertEquals(STATUS, connection.getStatus());
		assertEquals(UPDATE_DATE, connection.getUpdateDate());

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
		connection.setRequestdate(REQUEST_DATE);
		connection.setUpdateDate(UPDATE_DATE);
		connection.setMobileInfo(info);
		connectionsList.add(connection);
		return connectionsList;
	}

	@Test
	public void obtainConnectionTest() {
		// GIVEN

		Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

		Mockito.when(mobileInfoRepository.findById(Mockito.any())).thenReturn(Optional.of(mobileInfo));
		mobileInfo.setStatus("NA");

		Mockito.when(connectionRepository.save(Mockito.any())).thenReturn(connection);

		// WHEN
		Optional<ResponseDto> savedCustomer = connectionService.obtainConnection(userRequestDto);

		// THEN
		org.junit.Assert.assertNotNull(savedCustomer);
	}

	@Test
	public void mobileNumberAlreadyAssigned() {

		Throwable exception = assertThrows(MobileServiceProviderException.class, () -> {
			Mockito.when(connectionRepository.findByMobileInfo(Mockito.any())).thenReturn(Optional.of(connection));
			connectionService.obtainConnection(userRequestDto);
		});
		assertEquals(MobileServiceProviderConstants.ERROR_MOBILE_NUM_ALREADY_EXIST, exception.getMessage());
	}

	@Test
	public void userAlreadyExistTest() {

		Throwable exception = assertThrows(UserNotFoundException.class, () -> {
			Mockito.when(userRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(user));
			connectionService.obtainConnection(userRequestDto);
		});
		assertEquals(MobileServiceProviderConstants.ERROR_USER_ALREADY_EXIST, exception.getMessage());
	}

	private UserRequestDto getUserRequestDto() {
		userRequestDto = new UserRequestDto();
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

	private User getUserEntity() {
		user = new User();
		user.setUserId(USER_ID);
		BeanUtils.copyProperties(userRequestDto, user);
		return user;
	}

	private MobileInfo getMobileInoDto() {
		mobileInfo = new MobileInfo();
		mobileInfo.setMobileId(userRequestDto.getMobileId());
		mobileInfo.setStatus("A");
		mobileInfo.setNumber(MOBILE_NUMBER);
		return mobileInfo;
	}

	private Plan getPlanDto() {

		plan = new Plan();
		plan.setPlanId(userRequestDto.getPlanId());
		plan.setPlanName(PLAN_NAME);

		return plan;
	}

	private Connection getConnection() {

		connection = new Connection();
		connection.setMobileInfo(mobileInfo);
		connection.setPlan(plan);
		connection.setRequestdate(LocalDate.now());
		connection.setStatus(Status.IN_PROGRESS.toString());
		connection.setUpdateDate(LocalDate.now());
		connection.setUser(user);
		connection.setConnectionId(CONNECTION_ID);
		connection.setRemark(REMARK);
		return connection;
	}

	private void doCreateConnection() {
		getUserRequestDto();
		getUserEntity();
		getMobileInoDto();
		getPlanDto();
		getConnection();
	}
}
