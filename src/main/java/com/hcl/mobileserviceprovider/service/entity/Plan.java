package com.hcl.mobileserviceprovider.service.entity;

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
public class Plan {
	@Id
	@Column(nullable = false, updatable = false)
	private Long planId;
	@Column(nullable = false)
	private String planName;
	@Column(nullable = false)
	private String validity;
	@Column(nullable = false)
	private Double amount;
}
