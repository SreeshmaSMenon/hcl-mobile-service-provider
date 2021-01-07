package com.hcl.mobileserviceprovider.service.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Setter
@Getter
public class Connection {

	@Id
	@Column(nullable = false, updatable = false)
	private Long connectionId;
	@Column(nullable = false)
	private Long userId;
	@Column(nullable = false)
	private Long planId;
	@Column(nullable = false)
	private Long mobileId;
	@Column(nullable = false)
	private String status;
	@Column(nullable = false)
	private LocalDate requestdate;
	@Column(nullable = false)
	private LocalDate updateDate;
	private String remark;
}
