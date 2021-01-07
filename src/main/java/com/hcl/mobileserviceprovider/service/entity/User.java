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
@Setter
@Getter
@Table
public class User {
	@Id
	@Column(nullable = false, updatable = false)
	private Long userId;
	@Column(nullable = false)
	private String name;
	@Column(nullable = false, unique = true)
	private String email;
	@Column(nullable = false)
	private LocalDate dob;
	@Column(nullable = false)
	private String location;
	private String altMobileNumber;
	@Column(nullable = false, unique = true)
	private String idProofNumber;
	@Column(nullable = false)
	private String idProofType;

}
