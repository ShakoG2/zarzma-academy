package com.zarzmaacademy.model;


public class SingUpReq {

	private String userName;
	private String password;
	private String[] roles;

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}


	public String getUserName() {
		return userName;
	}

	public String getPassword() {
		return password;
	}

	public String[] getRoles() {
		return roles;
	}
}
