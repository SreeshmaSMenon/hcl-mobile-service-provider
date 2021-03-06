package com.hcl.mobileserviceprovider.service.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Setter
@Getter
public class MobileInfo {
	@Id

	@Column(nullable = false, updatable = false)
	private Long mobileId;
	@Column(nullable = false)
	private String number;
	@Column(nullable = false)
	private String status;
}
