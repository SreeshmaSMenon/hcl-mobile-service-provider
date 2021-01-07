package com.hcl.mobileserviceprovider.service;

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
import com.hcl.mobileserviceprovider.util.Status;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    UserRepository userRepository;
    @Mock
    ConnectionRepository connectionRepository;
    @Mock
    MobileInfoRepository mobileInfoRepository;
    @InjectMocks
    UserServiceImpl userService;

    UserRequestDto userRequestDto;

    Connection connection;
    MobileInfo mobileInfo;
    User user;
    Plan plan;

    @BeforeEach
    public void setUp() {
        userRequestDto = new UserRequestDto();
        userRequestDto.setMobileId(1l);
        userRequestDto.setPlanId(1l);
        userRequestDto.setAltMobileNumber("9090909090");
        userRequestDto.setName("Priya");
        userRequestDto.setEmail("a@gmail.com");
        userRequestDto.setIdProofNumber("12341234");
        userRequestDto.setIdProofType("Aadhar");
        userRequestDto.setLocation("Banglore");

        user = new User();
        user.setUserId(1L);
        BeanUtils.copyProperties(userRequestDto, user);

        mobileInfo = new MobileInfo();
        mobileInfo.setMobileId(userRequestDto.getMobileId());
        mobileInfo.setStatus("A");
        mobileInfo.setNumber("12341234");

        plan = new Plan();
        plan.setPlanId(userRequestDto.getPlanId());
        plan.setPlanName("AAA");

        connection = new Connection();
        connection.setMobileInfo(mobileInfo);
        connection.setPlan(plan);
        connection.setRequestdate(LocalDate.now());
        connection.setStatus(Status.IN_PROGRESS.toString());
        connection.setUpdateDate(LocalDate.now());
        connection.setUser(user);
        connection.setConnectionId(1L);
    }

    @Test
    public void obtainConnectionTest() {
        //GIVEN

        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        Mockito.when(mobileInfoRepository.findById(Mockito.any())).thenReturn(Optional.of(mobileInfo));
        mobileInfo.setStatus("NA");

        Mockito.when(connectionRepository.save(Mockito.any())).thenReturn(connection);

        //WHEN
        Optional<ResponseDto> savedCustomer = userService.obtainConnection(userRequestDto);

        //THEN
        Assert.assertNotNull(savedCustomer);
    }

  /*  @Test(expected = MobileServiceProviderException.class)
    public void mobileNumberAlreadyAssigned() {

        Mockito.when(connectionRepository.findByMobileInfo(Mockito.any())).thenReturn(Optional.of(connection));
        userService.obtainConnection(userRequestDto);
    }

    @Test(expected = UserNotFoundException.class)
    public void userAlreadyExistTest() {

        Mockito.when(userRepository.findByEmail(Mockito.any())).thenReturn(Optional.of(user));
        userService.obtainConnection(userRequestDto);
    }*/

}
