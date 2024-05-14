package com.zoi4erom.strategygame.controller;

import com.zoi4erom.strategygame.dto.AuthRequest;
import com.zoi4erom.strategygame.service.contract.AuthService;
import com.zoi4erom.strategygame.service.contract.UserService;
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

/**
 * Controller class for handling user authentication and registration.
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

	private final AuthService authenticateService;
	private final UserService userService;

	/**
	 * Endpoint for user login.
	 *
	 * @param authRequest Authentication request DTO containing username and password
	 * @return ResponseEntity containing JWT token if authentication is successful, or
	 * unauthorized status otherwise
	 */
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
		try {
			String token = authenticateService.authenticate(authRequest);
			if (token != null) {
				return ResponseEntity.ok(token);
			} else {
				return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
			}
		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
			    .body("Authentication failed: " + e.getMessage());
		}
	}

	/**
	 * Endpoint for user registration.
	 *
	 * @param authRequest Authentication request DTO containing username and password
	 * @return ResponseEntity indicating success or failure of the registration process
	 */
	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody AuthRequest authRequest) {
		String response = authenticateService.createUser(authRequest);
		if (response == null) {
			return ResponseEntity.badRequest().build();
		} else {
			return ResponseEntity.ok(response);
		}
	}

	/**
	 * Endpoint for checking if a user is authenticated.
	 *
	 * @return ResponseEntity indicating whether the user is authenticated or not
	 */
	@GetMapping("/check-authentication")
	public ResponseEntity<?> checkAuthentication() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication != null && authentication.isAuthenticated()) {
			return ResponseEntity.ok("Authenticated");
		} else {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Unauthorized");
		}
	}

	/**
	 * Endpoint for retrieving user information for the authenticated user.
	 *
	 * @return ResponseEntity containing user information if authenticated, or unauthorized status
	 * otherwise
	 */
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