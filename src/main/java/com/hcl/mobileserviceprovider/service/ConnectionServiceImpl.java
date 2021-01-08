package com.hcl.mobileserviceprovider.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.hcl.mobileserviceprovider.service.dto.ConnectionResponse;
import com.hcl.mobileserviceprovider.service.exception.MobileServiceProviderException;
import com.hcl.mobileserviceprovider.service.repository.ConnectionRepository;
import com.hcl.mobileserviceprovider.util.MobileServiceProviderConstants;
import com.hcl.mobileserviceprovider.util.Status;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ConnectionServiceImpl implements ConnectionService {

	private final ConnectionRepository connectionRepository;

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

	private void validateId(String id) {
		if (StringUtils.isEmpty(id)) {
			throw new MobileServiceProviderException(MobileServiceProviderConstants.EMPTY_REQUEST_ID);
		}

	}

}
