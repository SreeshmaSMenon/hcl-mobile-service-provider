package com.hcl.mobileserviceprovider.controller;

import com.hcl.mobileserviceprovider.service.ConnectionService;
import com.hcl.mobileserviceprovider.service.dto.ConnectionResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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

}
