package com.hcl.mobileserviceprovider.controller;

import com.hcl.mobileserviceprovider.service.ConnectionService;
import com.hcl.mobileserviceprovider.service.dto.Connection;
import com.hcl.mobileserviceprovider.service.dto.ConnectionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * @since 2021-01-07 This class includes methods for mobile connection services.
 */
@Slf4j
@RestController
@CrossOrigin(allowedHeaders = {"*", "*/"}, origins = {"*", "*/"})
@RequestMapping("/connections")
@RequiredArgsConstructor
public class ConnectionController {

    private final ConnectionService connectionService;
    
    @Autowired
   	private ConnectionService ConnectionService;

    /**
     * Retrieves list of connections.
     *
     * @return list of ConnectionResponse
     */
    @GetMapping
    public ResponseEntity<List<ConnectionResponse>> retrieveConnections() {
        log.info("Started retrieving connections");
        return new ResponseEntity<>(connectionService.retrieveConnections(), HttpStatus.OK);
    }
	
	@GetMapping("/connection/{id}")
    public ResponseEntity<Optional<Connection>> findById(@PathVariable String id) {
        return new ResponseEntity<>(ConnectionService.fetchById(id), HttpStatus.OK);
    }

}