package com.hcl.mobileserviceprovider.service;

import com.hcl.mobileserviceprovider.service.dto.ResponseDto;
import com.hcl.mobileserviceprovider.service.dto.UserRequestDto;

import java.util.Optional;

public interface UserService {

    Optional<ResponseDto> obtainConnection(UserRequestDto userRequestDto);
}
