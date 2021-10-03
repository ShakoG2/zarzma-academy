package com.zarzmaacademy.Controller;

import com.zarzmaacademy.Service.AuthService;
import com.zarzmaacademy.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

	@PostMapping("login")
	public ResponseEntity<?> userLogin(@Valid @RequestBody User user) throws BadCredentialsException {
		return authService.userLogin(user);
	}

	@PostMapping("signup")
	public ResponseEntity<?> userSignup(@RequestBody SingUpReq signupRequest) {
		return authService.userSignup(signupRequest);
	}

}
