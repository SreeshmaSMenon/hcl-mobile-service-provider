package com.hcl.mobileserviceprovider.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.hcl.mobileserviceprovider.service.dto.ConnectionResponse;
import com.hcl.mobileserviceprovider.service.entity.Connection;
import com.hcl.mobileserviceprovider.service.repository.ConnectionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConnectionServiceImpl implements ConnectionService {

	private final ConnectionRepository connectionRepository;

	@Override
	public Optional<List<ConnectionResponse>> retrieveConnections() {

		Optional<List<Connection>> connectionsOptional = connectionRepository.findByStatuss("InProgress");
		List<ConnectionResponse> responses = new ArrayList<>();
		if (connectionsOptional.isPresent()) {
			List<Connection> connections = connectionsOptional.get();
			connections.forEach(connection -> {
				ConnectionResponse response = new ConnectionResponse();
				BeanUtils.copyProperties(connection, response);
				responses.add(response);
			});
		}
		return Optional.of(responses);
	}

}
