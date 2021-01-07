package com.hcl.mobileserviceprovider.service;

import com.hcl.mobileserviceprovider.service.dto.ConnectionResponse;
import com.hcl.mobileserviceprovider.service.repository.ConnectionRepository;
import com.hcl.mobileserviceprovider.util.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ConnectionServiceImpl implements ConnectionService {

    private final ConnectionRepository connectionRepository;

    @Override
    public List<ConnectionResponse> retrieveConnections() {

        List<ConnectionResponse> connectionResponseList = connectionRepository.findAllByStatus(Status.IN_PROGRESS.toString()).stream().map(connection -> {

            ConnectionResponse con = new ConnectionResponse();
            con.setPlanName(connection.getPlan().getPlanName());
            con.setMobileNumber(connection.getMobileInfo().getNumber());
            con.setUserName(connection.getUser().getName());
            BeanUtils.copyProperties(connection, con);
            return con;

        }).collect(Collectors.toList());
        return connectionResponseList;
    }

}
