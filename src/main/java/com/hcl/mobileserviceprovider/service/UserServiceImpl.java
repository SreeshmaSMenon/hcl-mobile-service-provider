package com.hcl.mobileserviceprovider.service;

import com.hcl.mobileserviceprovider.service.dto.ResponseDto;
import com.hcl.mobileserviceprovider.service.dto.UserRequestDto;
import com.hcl.mobileserviceprovider.service.entity.Connection;
import com.hcl.mobileserviceprovider.service.entity.User;
import com.hcl.mobileserviceprovider.service.repository.ConnectionRepository;
import com.hcl.mobileserviceprovider.service.repository.MobileInfoRepository;
import com.hcl.mobileserviceprovider.service.repository.UserRepository;
import com.hcl.mobileserviceprovider.util.Status;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ConnectionRepository connectionRepository;

    @Autowired
    MobileInfoRepository mobileInfoRepository;

    @Override
    public Optional<ResponseDto> obtainConnection(UserRequestDto userRequestDto) {

        User user = new User();
        BeanUtils.copyProperties(userRequestDto, user);

        //Inserting userdata into user table
        User userData = userRepository.save(user);

        //Change the Mobile number status"A(Availability) to NA(Not available) in MobileInfo table

        Optional<Connection> connectionData = connectionRepository.findById(userRequestDto.getMobileId());

       /* CommonResponseDto commonResponseDto = new CommonResponseDto();
        if (connectionData.isPresent()) {
            commonResponseDto.setMessage("Selected Mobile number is not available");

        } else {
            Optional<MobileInfo> mobileInfo = mobileInfoRepository.findById(userRequestDto.getMobileId());
            if (mobileInfo.isPresent()) {
                mobileInfo.get().setStatus("NA");
                mobileInfoRepository.save(mobileInfo.get());
            }
        }*/

        Connection connection = new Connection();
        connection.setMobileId(userRequestDto.getMobileId());
        connection.setPlanId(userRequestDto.getPlanId());
        connection.setRequestdate(LocalDate.now());
        connection.setStatus(Status.IN_PROGRESS.toString());
        connection.setUpdateDate(LocalDate.now());
        connection.setUserId(userData.getUserId());


        //Inserting connectionRequest data into Connection table
        Connection connectionResult = connectionRepository.save(connection);

        ResponseDto responseDto = new ResponseDto();
        responseDto.setMessage("Successfully created..");
        responseDto.setRequestId(connectionResult.getConnectionId());
        responseDto.setStatusCode(200);

        return Optional.of(responseDto);


    }
}
