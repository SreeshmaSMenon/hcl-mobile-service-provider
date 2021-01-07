package com.hcl.mobileserviceprovider.service.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Data;

@Data
@JsonInclude(Include.NON_NULL)
public class Connection {

	private Long connectionId;
	private Long userId;
	private Long planId;
	private Long mobileId;
	private String status;
	private LocalDate requestdate;
	private LocalDate updateDate;
	private String remark;

}

