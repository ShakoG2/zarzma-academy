package com.zarzmaacademy.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserBean implements UserDetails {

	private static final long serialVersionUID = -4709084843450077569L;
	private Long id;
	private String userName;

	@JsonIgnore
	private String password;

	private Collection<? extends GrantedAuthority> authorities;

	public UserBean(Long id, String userName, String password, Collection<? extends GrantedAuthority> authorities) {
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.authorities = authorities;
	}

	public static UserBean createInstance(User user) {
		List<GrantedAuthority> authorities = user.getRoles()
				.stream().map(role -> new SimpleGrantedAuthority(role.getRoleName().name()))
				.collect(Collectors.toList());

		return new UserBean(user.getId(), user.getUserName(), user.getPassword(), authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return null;
	}

	@Override
	public String getPassword() {
		return null;
	}

	@Override
	public String getUsername() {
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
