package com.zarzmaacademy.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AuthRes {

	private String token;
	private List<String> roles;
}
