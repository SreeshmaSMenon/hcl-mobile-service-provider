package com.hcl.mobileserviceprovider.service.dto;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class ConnectionResponse {
	private Long connectionId;
	private Long userId;
	private String planName;
	private String number;
	private String status;
	private LocalDate requestdate;
	private LocalDate updateDate;
	private String remark;
}
