package com.hcl.mobileserviceprovider.service;

import com.hcl.mobileserviceprovider.service.dto.ResponseDto;
import com.hcl.mobileserviceprovider.service.dto.UserRequestDto;
import com.hcl.mobileserviceprovider.service.entity.Connection;
import com.hcl.mobileserviceprovider.service.entity.MobileInfo;
import com.hcl.mobileserviceprovider.service.entity.User;
import com.hcl.mobileserviceprovider.service.repository.ConnectionRepository;
import com.hcl.mobileserviceprovider.service.repository.MobileInfoRepository;
import com.hcl.mobileserviceprovider.service.repository.UserRepository;
import com.hcl.mobileserviceprovider.util.Status;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@RunWith(MockitoJUnitRunner.class)
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

    @Before
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

        connection = new Connection();
        connection.setMobileId(userRequestDto.getMobileId());
        connection.setPlanId(userRequestDto.getPlanId());
        connection.setRequestdate(LocalDate.now());
        connection.setStatus(Status.IN_PROGRESS.toString());
        connection.setUpdateDate(LocalDate.now());
        connection.setUserId(user.getUserId());
        connection.setConnectionId(1L);

        mobileInfo = new MobileInfo();
        mobileInfo.setMobileId(1l);
        mobileInfo.setStatus("A");
        mobileInfo.setNumber("12341234");
    }

    @Test
    public void obtainConnectionTest() {
        //GIVEN

        BeanUtils.copyProperties(userRequestDto, user);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        Mockito.when(mobileInfoRepository.findById(Mockito.any())).thenReturn(Optional.of(mobileInfo));
        mobileInfo.setStatus("NA");

        Mockito.when(connectionRepository.save(Mockito.any())).thenReturn(connection);

        //WHEN
        Optional<ResponseDto> savedCustomer = userService.obtainConnection(userRequestDto);

        //THEN
        assertNotNull(savedCustomer);
    }

   /* @Test(expected = MobileServiceProviderException.class)
    public void mobileNumberAlreadyAssigned() {

        userRequestDto.setEmail("ab@gmail.com");
        userRequestDto.setIdProofNumber("122341234");
        BeanUtils.copyProperties(userRequestDto, user);
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        Mockito.when(connectionRepository.findByMobileId(Mockito.any())).thenReturn(Optional.of(connection));
    }*/

}
