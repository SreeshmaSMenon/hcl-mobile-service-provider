package com.hcl.mobileserviceprovider.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.hcl.mobileserviceprovider.service.dto.ConnectionResponse;
import com.hcl.mobileserviceprovider.service.dto.MobileServiceProviderMapper;
import com.hcl.mobileserviceprovider.service.repository.ConnectionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConnectionServiceImpl implements ConnectionService {

	private final ConnectionRepository connectionRepository;

	@Override
	public Optional<List<ConnectionResponse>> retrieveConnections() {

		return Optional.of(connectionRepository.findAll().stream()
				.map(MobileServiceProviderMapper.MAPPER::toConnectionResponse).collect(Collectors.toList()));
	}

}
