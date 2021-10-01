package com.zarzmaacademy.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Enumerated(EnumType.STRING)
	@Column(name="name")
	private Roles roleName;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Roles getRoleName() {
		return roleName;
	}
	public void setRoleName(Roles roleName) {
		this.roleName = roleName;
	}
}
