package com.zarzmaacademy.model;


import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@EqualsAndHashCode()
@Getter
@Setter
@Entity
@Table(name = "users")
public class User {

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "user_name")
	private String userName;

	//@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Column(name = "password")
	private String password;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_role",
			joinColumns = @JoinColumn(name="USER_ID", referencedColumnName="ID"),
			inverseJoinColumns = @JoinColumn(name="ROLE_ID", referencedColumnName="ID"))
	private Set<Role> roles = new HashSet<>();
}
