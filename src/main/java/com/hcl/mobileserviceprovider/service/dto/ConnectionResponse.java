package com.hcl.mobileserviceprovider.service.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_EMPTY)
public class ConnectionResponse {
    private Long connectionId;
    private String userName;
    private String planName;
    private String mobileNumber;
    private String status;
    private LocalDate requestdate;
    private LocalDate updateDate;
    private String remark;
}
