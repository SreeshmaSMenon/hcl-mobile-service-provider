package com.hcl.mobileserviceprovider.service.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
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
