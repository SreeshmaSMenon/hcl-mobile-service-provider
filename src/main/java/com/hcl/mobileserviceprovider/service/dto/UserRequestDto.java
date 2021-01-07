package com.hcl.mobileserviceprovider.service.dto;


import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserRequestDto {

    private String name;
    private String email;
    private LocalDate dob;
    private String location;
    private String altMobileNumber;
    private String idProofNumber;
    private String idProofType;
    private Long planId;
    private Long mobileId;
}
