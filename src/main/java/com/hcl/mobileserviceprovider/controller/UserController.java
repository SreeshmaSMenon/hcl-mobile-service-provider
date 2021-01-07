package com.hcl.mobileserviceprovider.controller;

import com.hcl.mobileserviceprovider.service.UserService;
import com.hcl.mobileserviceprovider.service.dto.ResponseDto;
import com.hcl.mobileserviceprovider.service.dto.UserRequestDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/connection")
    public ResponseEntity<Optional<ResponseDto>> createConnection(@RequestBody UserRequestDto userRequestDto) {
        return new ResponseEntity<>(userService.obtainConnection(userRequestDto), HttpStatus.OK);
    }
}
