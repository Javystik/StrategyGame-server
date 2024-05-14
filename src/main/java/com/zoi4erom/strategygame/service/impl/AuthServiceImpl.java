package com.zoi4erom.strategygame.service.impl;

import com.zoi4erom.strategygame.dto.AuthRequest;
import com.zoi4erom.strategygame.service.contract.AuthService;
import com.zoi4erom.strategygame.service.contract.UserService;
import com.zoi4erom.strategygame.utils.JwtTokenUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementation of the user authentication service. This service provides methods for
 * authenticating users and creating new users.
 */
@Service
@AllArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {

	private UserService userService; // User service
	private UserDetailsService userDetailsService; // User details service
	private final JwtTokenUtils jwtTokenUtils; // Utility for working with JWT tokens
	private final PasswordEncoder passwordEncoder; // Password encoder

	/**
	 * Authenticates the user using the provided username and password.
	 *
	 * @param authRequest Object containing authentication data (username and password)
	 * @return JWT token for the authenticated user, or null if authentication fails
	 */
	@Override
	public String authenticate(AuthRequest authRequest) {
		log.info("Authenticating user: '{}'", authRequest.getUsername());
		var userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());

		if (passwordEncoder.matches(authRequest.getPassword(), userDetails.getPassword())) {
			log.info("User '{}' authenticated successfully", authRequest.getUsername());
			return jwtTokenUtils.generateToken(userDetails);
		} else {
			log.warn("Authentication failed for user: '{}'", authRequest.getUsername());
			return null;
		}
	}

	/**
	 * Creates a new user with the provided data.
	 *
	 * @param authRequest Object containing data for creating the user (username and password)
	 * @return JWT token for the newly created user, or null if user creation fails
	 */
	@Override
	public String createUser(AuthRequest authRequest) {
		log.info("Creating user: '{}'", authRequest.getUsername());
		var isCreated = userService.createUser(authRequest);
		if (isCreated) {
			return jwtTokenUtils.generateToken(
			    userDetailsService.loadUserByUsername(authRequest.getUsername()));
		} else {
			return null;
		}
	}
}
