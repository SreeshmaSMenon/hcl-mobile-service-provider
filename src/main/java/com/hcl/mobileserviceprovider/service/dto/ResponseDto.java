package com.hcl.mobileserviceprovider.service.dto;

import lombok.Data;

@Data
public class ResponseDto {
    private String message;
    private Integer statusCode;
    private Long requestId;
}
