package com.hcl.mobileserviceprovider.service;

import com.hcl.mobileserviceprovider.service.dto.*;
import com.hcl.mobileserviceprovider.service.entity.Connection;
import com.hcl.mobileserviceprovider.service.entity.MobileInfo;
import com.hcl.mobileserviceprovider.service.entity.Plan;
import com.hcl.mobileserviceprovider.service.entity.User;
import com.hcl.mobileserviceprovider.service.exception.InvalidConnectionIdException;
import com.hcl.mobileserviceprovider.service.exception.MobileServiceProviderException;
import com.hcl.mobileserviceprovider.service.exception.UserNotFoundException;
import com.hcl.mobileserviceprovider.service.repository.ConnectionRepository;
import com.hcl.mobileserviceprovider.service.repository.MobileInfoRepository;
import com.hcl.mobileserviceprovider.service.repository.UserRepository;
import com.hcl.mobileserviceprovider.util.MobileServiceProviderConstants;
import com.hcl.mobileserviceprovider.util.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static com.hcl.mobileserviceprovider.util.MobileServiceProviderConstants.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionServiceImpl implements ConnectionService {

    private final ConnectionRepository connectionRepository;
    private final UserRepository userRepository;
    private final MobileInfoRepository mobileInfoRepository;

    @Override
    public List<ConnectionResponse> retrieveConnections() {

        List<ConnectionResponse> connectionResponseList = connectionRepository
                .findAllByStatus(Status.IN_PROGRESS.toString()).stream().map(connection -> {

                    ConnectionResponse con = new ConnectionResponse();
                    con.setPlanName(connection.getPlan().getPlanName());
                    con.setMobileNumber(connection.getMobileInfo().getNumber());
                    con.setUserName(connection.getUser().getName());
                    BeanUtils.copyProperties(connection, con);
                    return con;

                }).collect(Collectors.toList());
        return connectionResponseList;
    }

    @Override
    public Optional<ConnectionResponse> fetchById(String id) {
        log.info("Fetching the details of connection by connection id {}", id);
        validateId(id);
        Long startTimeForFetch = System.currentTimeMillis();
        try {
            Optional<com.hcl.mobileserviceprovider.service.entity.Connection> connectionDetails = connectionRepository
                    .findById(Long.valueOf(id));

            if (connectionDetails.isPresent()) {
                ConnectionResponse connection = new ConnectionResponse();
                com.hcl.mobileserviceprovider.service.entity.Connection connectionResponse = connectionDetails.get();
                connection.setStatus(connectionResponse.getStatus());
                connection.setUpdateDate(connectionResponse.getUpdateDate());
                return Optional.of(connection);
            } else {
                log.error("Connection details not available for the connection id {}", id);
                throw new MobileServiceProviderException(
                        MobileServiceProviderConstants.ERROR_CONNECTION_ID_NOT_AVAILABLE);
            }
        } catch (Exception ex) {
            log.error("Error while fetching connection details by id from database {}", ex.getMessage());

            throw new MobileServiceProviderException(
                    MobileServiceProviderConstants.ERROR_FETCHING_CONNECTION_DETAILS_BY_ID);
        } finally {
            Long endTimeForFetch = System.currentTimeMillis();
            log.info("Time taken to fetch details by id = {}", endTimeForFetch - startTimeForFetch);
        }

    }

    @Override
    public Optional<ResponseDto> obtainConnection(UserRequestDto userRequestDto) {
        ResponseDto responseDto = new ResponseDto();
        User user = new User();
        BeanUtils.copyProperties(userRequestDto, user);
        if (!emailValidation(userRequestDto.getEmail()))
            throw new MobileServiceProviderException(MobileServiceProviderConstants.ERROR_EMAIL_MESSAGE);
        if (!phoneNumberValidation(userRequestDto.getAltMobileNumber()))
            throw new MobileServiceProviderException(MobileServiceProviderConstants.ERROR_PHONE_NUMBER_MESSAGE);
        Optional<User> userExist = userRepository.findByEmail(userRequestDto.getEmail());
        if (userExist.isPresent())
            throw new UserNotFoundException(MobileServiceProviderConstants.ERROR_USER_ALREADY_EXIST);
        // Change the Mobile number status"A(Availability) to NA(Not available) in
        // MobileInfo table
        Optional<Connection> connectionData = connectionRepository.findByMobileInfo(userRequestDto.getMobileId());
        if (connectionData.isPresent()) {
            throw new MobileServiceProviderException(MobileServiceProviderConstants.ERROR_MOBILE_NUM_ALREADY_EXIST);
        } else {
            // Inserting userdata into user table
            User userData = userRepository.save(user);
            Optional<MobileInfo> mobileInfo = mobileInfoRepository.findById(userRequestDto.getMobileId());
            if (mobileInfo.isPresent()) {
                mobileInfo.get().setStatus("NA");
                mobileInfoRepository.save(mobileInfo.get());
            }
            Connection connection = new Connection();
            MobileInfo mobileInfo1 = new MobileInfo();
            mobileInfo1.setMobileId(userRequestDto.getMobileId());

            Plan plan = new Plan();
            plan.setPlanId(userRequestDto.getPlanId());

            User user1 = new User();
            user1.setUserId(userData.getUserId());

            connection.setMobileInfo(mobileInfo1);
            connection.setPlan(plan);
            connection.setRequestdate(LocalDate.now());
            connection.setStatus(Status.IN_PROGRESS.toString());
            connection.setUpdateDate(LocalDate.now());
            connection.setUser(user1);

            // Inserting connectionRequest data into Connection table
            Connection connectionResult = connectionRepository.save(connection);

            responseDto.setMessage("Successfully created..");
            responseDto.setRequestId(connectionResult.getConnectionId());
            responseDto.setStatusCode(200);

            return Optional.of(responseDto);
        }

    }

    @Override
    public ApproveOrRejectConnectionResponse approveOrRejectConnection(ApproveOrRejectConnection approveOrRejectConnection, Long id) throws InvalidConnectionIdException {
        ApproveOrRejectConnectionResponse approveOrRejectConnectionResponse = new ApproveOrRejectConnectionResponse();
        Optional<Connection> connectionDetails = connectionRepository.findById(id);
        if (connectionDetails.isPresent()) {
            Connection connection = connectionDetails.get();
            connection.setStatus(approveOrRejectConnection.getStatus());
            connection.setRemark(approveOrRejectConnection.getRemark());
            connectionRepository.save(connection);
        } else {
            throw new InvalidConnectionIdException(CONNECTION_ID_NOT_FOUND);
        }
        approveOrRejectConnectionResponse.setMessage(SUCCESS_MESSAGE);
        approveOrRejectConnectionResponse.setStatusCode(SUCCESS_CODE);
        return approveOrRejectConnectionResponse;
    }

    private boolean phoneNumberValidation(String number) {

        Pattern p = Pattern.compile("^[0-9]{10}$");
        Matcher m = p.matcher(number);
        return (m.find() && m.group().equals(number));
    }

    private boolean emailValidation(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." + "[a-zA-Z0-9_+&*-]+)*@" + "(?:[a-zA-Z0-9-]+\\.)+[a-z"
                + "A-Z]{2,7}$";
        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    private void validateId(String id) {
        if (StringUtils.isEmpty(id)) {
            throw new MobileServiceProviderException(MobileServiceProviderConstants.EMPTY_REQUEST_ID);
        }

    }

}
