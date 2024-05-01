package com.zoi4erom.strategygame.controller;

import com.zoi4erom.strategygame.dto.AuthRequest;
import com.zoi4erom.strategygame.service.AuthService;
import com.zoi4erom.strategygame.service.UserService;
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
			return ResponseEntity.ok(token);
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
			    .body("Authentication failed: " + e.getMessage());
		}
	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody AuthRequest authRequest) {
		String token = authenticateService.createUser(authRequest);
		return ResponseEntity.ok(token);
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