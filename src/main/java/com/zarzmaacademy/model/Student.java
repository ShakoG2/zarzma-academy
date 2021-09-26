package com.zarzmaacademy.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "students")
@Getter
@Setter
public class Student {

	@javax.persistence.Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;

	@Column(name = "first_name")
	private String firstName;

	@Column(name = "last_name")
	private String lastName;

	@Column(name = "email")
	private String email;

	@Column(name = "personal_number")
	private String personalNumber;

	@Column(name = "phone_number")
	private String phoneNumber;

	@Column(name = "school_name")
	private String schoolName;

	@Column(name = "parent_full_name")
	private String parentFullName;

	@Column(name = "code")
	private String code;

	@Column(name = "school_class")
	private Integer schoolClass;

	@Column(name = "active", nullable = false)
	private Boolean active;
}
