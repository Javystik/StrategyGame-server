package com.zoi4erom.strategygame.controller;

import com.zoi4erom.strategygame.dto.AuthRequest;
import com.zoi4erom.strategygame.service.contract.AuthService;
import com.zoi4erom.strategygame.service.contract.UserService;
import com.zoi4erom.strategygame.service.impl.AuthServiceImpl;
import com.zoi4erom.strategygame.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

	private final AuthService authenticateService;
	private final UserService userService;

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
		try {
			String token = authenticateService.authenticate(authRequest);
			if(token != null){
				return ResponseEntity.ok(token);
			}else{
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
			    .body("Authentication failed: " + e.getMessage());
		}
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody AuthRequest authRequest) {
		return ResponseEntity.ok(authenticateService.createUser(authRequest));
	}


	@GetMapping("/check-authentication")
	public ResponseEntity<?> checkAuthentication() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.isAuthenticated()) {
			return ResponseEntity.ok("Authenticated");
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized");
		}
	}

	@GetMapping("/info-for-me")
	public ResponseEntity<?> infoForMe() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.isAuthenticated()) {
			return ResponseEntity.ok(userService.getUserByUsername(authentication.getName()));
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized");
		}
	}
}