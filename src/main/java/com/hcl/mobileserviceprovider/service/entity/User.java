package com.hcl.mobileserviceprovider.service.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Setter
@Getter
@Table
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
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

	@OneToMany(mappedBy = "user")
	@JsonManagedReference
	private List<Connection> connectionList;

}
