package com.hcl.mobileserviceprovider.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.mobileserviceprovider.service.ConnectionService;
import com.hcl.mobileserviceprovider.service.dto.ConnectionResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * @since 2021-01-07 This class includes methods for mobile connection services.
 */
@Slf4j
@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
@RequestMapping("/connections")
@RequiredArgsConstructor
public class ConnectionController {

	private final ConnectionService connectionService;

	/**
	 * Retrieves list of connections.
	 * 
	 * @return list of ConnectionResponse
	 */
	@GetMapping
	public ResponseEntity<List<ConnectionResponse>> retrieveConnections() {
		log.info("Started retrieving connections");
		List<ConnectionResponse> connections = new ArrayList<ConnectionResponse>();
		Optional<List<ConnectionResponse>> connectionsOptional = connectionService.retrieveConnections();
		if (connectionsOptional.isPresent())
			connections = connectionsOptional.get();
		log.info("Retrieving connections completed");
		return new ResponseEntity<>(connections, HttpStatus.OK);
	}

}
