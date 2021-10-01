//package com.zarzmaacademy.model;
//
//import lombok.Getter;
//import lombok.Setter;
//import org.springframework.security.core.GrantedAuthority;
//
//import java.util.Collection;
//
//@Getter
//@Setter
//public class UserDetailsImpl implements org.springframework.security.core.userdetails.UserDetails {
//
//	private final Long id;
//	private final String username;
//	private final String password;
//	private final boolean enabled;
//	private final Collection<GrantedAuthority> authorities;
//
//	public UserDetailsImpl(User user, Collection<GrantedAuthority> authorities) {
//		this.id = user.getId();
//		this.username = user.getUserName();
//		this.password = user.getPassword();
//		this.enabled = user.getActive();
//		this.authorities = authorities;
//	}
//
//	@Override
//	public Collection<? extends GrantedAuthority> getAuthorities() {
//		return authorities;
//	}
//
//	@Override
//	public String getPassword() {
//		return password;
//	}
//
//	@Override
//	public String getUsername() {
//		return username;
//	}
//
//	@Override
//	public boolean isAccountNonExpired() {
//		return true;
//	}
//
//	@Override
//	public boolean isAccountNonLocked() {
//		return true;
//	}
//
//	@Override
//	public boolean isCredentialsNonExpired() {
//		return true;
//	}
//
//	@Override
//	public boolean isEnabled() {
//		return enabled;
//	}
//}