package com.hcl.mobileserviceprovider.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin(allowedHeaders = {"*","*/"}, origins = {"*","*/"})
public class ConnectionController {
	
	
	/**
	 *Adding sample end point. Just for reference. Feel free to replace.
	 */
    @GetMapping(value = "/hello")
    public ResponseEntity<String> hello() {
    	log.info("Sample endpoint");
        return new ResponseEntity<>("hello",HttpStatus.OK);
    }
	

}
