package com.hcl.mobileserviceprovider.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.hcl.mobileserviceprovider.service.ConnectionService;
import com.hcl.mobileserviceprovider.service.dto.Connection;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(allowedHeaders = { "*", "*/" }, origins = { "*", "*/" })
public class ConnectionController {
	
	@Autowired
	private ConnectionService ConnectionService;
	
	@GetMapping("/connection/{id}")
    public ResponseEntity<Optional<Connection>> findById(@PathVariable String id) {
        return new ResponseEntity<>(ConnectionService.fetchById(id), HttpStatus.OK);
    }

}
