package com.zoi4erom.strategygame.service.impl;

import com.zoi4erom.strategygame.dto.AuthRequest;
import com.zoi4erom.strategygame.service.contract.AuthService;
import com.zoi4erom.strategygame.service.contract.UserService;
import com.zoi4erom.strategygame.utils.JwtTokenUtils;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

	private UserService userService;
	private UserDetailsService userDetailsService;
	private final JwtTokenUtils jwtTokenUtils;
	private final PasswordEncoder passwordEncoder;

	@Override
	public String authenticate(AuthRequest authRequest) {
		var userDetails = userDetailsService.loadUserByUsername(authRequest.getUsername());

		if (passwordEncoder.matches(authRequest.getPassword(), userDetails.getPassword())) {
			return jwtTokenUtils.generateToken(userDetails);
		}
		return null;
	}
	@Override
	public String createUser(AuthRequest authRequest) {
		userService.createUser(authRequest);
		return jwtTokenUtils.generateToken(
		    userDetailsService.loadUserByUsername(authRequest.getUsername()));
	}
}
