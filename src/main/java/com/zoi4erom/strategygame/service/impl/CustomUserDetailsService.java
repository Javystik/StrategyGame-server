package com.zoi4erom.strategygame.service.impl;

import com.zoi4erom.strategygame.repository.UserRepository;
import com.zoi4erom.strategygame.security.CustomUserDetails;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Implementation of the service for obtaining user details by their username. This service is used
 * for authentication and authorization of users in Spring Security.
 */
@Service
@AllArgsConstructor
@Slf4j
public class CustomUserDetailsService implements UserDetailsService {

	private UserRepository userRepository; // User repository

	/**
	 * Loads user data by their username.
	 *
	 * @param username Username to find user data
	 * @return UserDetails object containing user data
	 * @throws UsernameNotFoundException Exception thrown if user is not found
	 */
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.info("Loading user by username: '{}'", username);
		return userRepository.findUserByUsername(username)
		    .map(CustomUserDetails::new)
		    .orElseThrow(() -> {
			    log.error("User '{}' not found", username);
			    return new UsernameNotFoundException(username + " not found");
		    });
	}
}
