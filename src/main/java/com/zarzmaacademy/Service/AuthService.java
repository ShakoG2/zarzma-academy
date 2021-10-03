package com.zarzmaacademy.Service;

import com.zarzmaacademy.configuration.JwtTokenUtil;
import com.zarzmaacademy.dao.RoleRepository;
import com.zarzmaacademy.dao.UserRepository;
import com.zarzmaacademy.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	@Autowired
	PasswordEncoder encoder;
	@Autowired
	AuthenticationManager authenticationManager;
	@Autowired
	JwtTokenUtil jwtTokenUtil;


	public ResponseEntity<?> userLogin(User user) throws BadCredentialsException {
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String token = jwtTokenUtil.generateJwtToken(authentication);
		UserBean userBean = (UserBean) authentication.getPrincipal();
		List<String> roles = userBean.getAuthorities().stream()
				.map(GrantedAuthority::getAuthority)
				.collect(Collectors.toList());
		AuthRes authResponse = new AuthRes();
		authResponse.setToken(token);
		authResponse.setRoles(roles);
		return ResponseEntity.ok(authResponse);
	}

	public ResponseEntity<?> userSignup(SingUpReq singUpReq) {
		if (userRepository.existsByUserName(singUpReq.getUserName())) {
			return ResponseEntity.badRequest().body("Username is already taken");
		}
		User user = new User();
		Set<Role> roles = new HashSet<>();
		user.setUserName(singUpReq.getUserName());
		Role role = new Role();
		role.setRoleName(Roles.ROLE_USER);
		roleRepository.save(role);
		user.setPassword(encoder.encode(singUpReq.getPassword()));
		String[] roleArr = singUpReq.getRoles();
		if (roleArr == null || roleArr.length == 0) {
			roles.add(roleRepository.findByRoleName(Roles.ROLE_USER).get());
		}
		for (int i = 0; i < Objects.requireNonNull(roleArr).length; i++) {
			switch (roleArr[i]) {
				case "admin":
					roles.add(roleRepository.findByRoleName(Roles.ROLE_ADMIN).get());
					break;
				case "user":
					roles.add(roleRepository.findByRoleName(Roles.ROLE_USER).get());
					break;
				default:
					return ResponseEntity.badRequest().body("Specified role not found");
			}
		}
		user.setRoles(roles);
		userRepository.save(user);
		return ResponseEntity.ok("User signed up successfully");
	}
}
