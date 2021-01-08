package com.hcl.mobileserviceprovider.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.mobileserviceprovider.service.ConnectionService;
import com.hcl.mobileserviceprovider.service.dto.ApproveOrRejectConnection;
import com.hcl.mobileserviceprovider.service.dto.ApproveOrRejectConnectionResponse;
import com.hcl.mobileserviceprovider.service.dto.ConnectionResponse;
import com.hcl.mobileserviceprovider.service.dto.ResponseDto;
import com.hcl.mobileserviceprovider.service.dto.UserRequestDto;
import com.hcl.mobileserviceprovider.service.exception.InvalidConnectionIdException;

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
	public ResponseEntity<Optional<List<ConnectionResponse>>> retrieveConnections() {
		log.info("Started retrieving connections");
		return new ResponseEntity<>(connectionService.retrieveConnections(), HttpStatus.OK);
	}

	/**
	 * Retrieves connection details for given id.
	 *
	 * @return ConnectionResponse
	 * @Param id, connection id
	 */
	@GetMapping("/{id}")
	public ResponseEntity<Optional<ConnectionResponse>> findById(@PathVariable String id) {
		return new ResponseEntity<>(connectionService.fetchById(id), HttpStatus.OK);
	}

	/**
	 * create connection with given info
	 *
	 * @return ResponseDto
	 * @Param userRequestDto, user and connection info
	 */
	@PostMapping
	public ResponseEntity<Optional<ResponseDto>> createConnection(@RequestBody UserRequestDto userRequestDto) {
		return new ResponseEntity<>(connectionService.obtainConnection(userRequestDto), HttpStatus.OK);
	}

	/**
	 * Approve or Reject connection with given info
	 *
	 * @return ApproveOrRejectConnectionResponse
	 * @Param ApproveOrRejectConnection info
	 */
	@PutMapping("/{id}")
	public ResponseEntity<ApproveOrRejectConnectionResponse> approveOrRejectConnection(
			@RequestBody ApproveOrRejectConnection approveOrRejectConnection, @PathVariable Long id)
			throws InvalidConnectionIdException {
		return new ResponseEntity<>(connectionService.approveOrRejectConnection(approveOrRejectConnection, id),
				HttpStatus.OK);
	}

}