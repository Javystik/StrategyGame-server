package com.zoi4erom.strategygame.service;

import com.zoi4erom.strategygame.dto.AuthRequest;
import com.zoi4erom.strategygame.utils.JwtTokenUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

	private UserService userService;
	private UserDetailsService userDetailsService;
	private final JwtTokenUtils jwtTokenUtils;

	public String authenticate(AuthRequest authRequest) {
		return jwtTokenUtils.generateToken(
		    userDetailsService.loadUserByUsername(authRequest.getUsername()));
	}

	public String createUser(AuthRequest authRequest) {
		userService.createUser(authRequest);
		return jwtTokenUtils.generateToken(
		    userDetailsService.loadUserByUsername(authRequest.getUsername()));
	}
}
