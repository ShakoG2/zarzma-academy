package com.zarzmaacademy.Controller;

import com.zarzmaacademy.configuration.JwtTokenUtil;
import com.zarzmaacademy.model.*;
import com.zarzmaacademy.dao.RoleRepository;
import com.zarzmaacademy.dao.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/auth")
public class AuthController {
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

	@PostMapping("login")
	public ResponseEntity<?> userLogin(@Valid @RequestBody User user) throws BadCredentialsException {
		//System.out.println("AuthController -- userLogin");
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

	@PostMapping("signup")
	public ResponseEntity<?> userSignup(@RequestBody SingUpReq signupRequest) {
		if (userRepository.existsByUserName(signupRequest.getUserName())) {
			return ResponseEntity.badRequest().body("Username is already taken");
		}
////		if(userRepository.existsByEmail(signupRequest.getEmail())){
////			return ResponseEntity.badRequest().body("Email is already taken");
////		}
		User user = new User();
		Set<Role> roles = new HashSet<>();
		user.setUserName(signupRequest.getUserName());

		Role role = new Role();
		role.setRoleName(Roles.ROLE_USER);
		roleRepository.save(role);

		user.setPassword(encoder.encode(signupRequest.getPassword()));
		String[] roleArr = signupRequest.getRoles();
//
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


	@GetMapping("users")
	public List<User> getAll() {
		return userRepository.findAll();
	}

}
