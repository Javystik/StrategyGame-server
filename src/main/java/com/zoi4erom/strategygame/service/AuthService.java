package com.zoi4erom.strategygame.service;

import com.zoi4erom.strategygame.dto.AuthRequest;
import com.zoi4erom.strategygame.dto.UserDto;
import com.zoi4erom.strategygame.mapper.RoleMapper;
import com.zoi4erom.strategygame.utils.JwtTokenUtils;
import java.time.LocalDate;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

	private UserService userService;
	private final RoleService roleService;
	private UserDetailsService userDetailsService;
	private final JwtTokenUtils jwtTokenUtils;
	private final PasswordEncoder passwordEncoder;
	private final RoleMapper roleMapper;

	public String authenticate(AuthRequest authRequest) {
		var user = userService.getUserByUsername(authRequest.getUsername())
		    .orElseThrow(() -> new UsernameNotFoundException("User not found"));

		var userDetails = userDetailsService.loadUserByUsername(user.getUsername());

		return jwtTokenUtils.generateToken(userDetails);
	}

	public String createUser(AuthRequest authRequest) {
		authRequest.setPassword(passwordEncoder.encode(authRequest.getPassword()));
		try {
			var user = UserDto.builder()
			    .username(authRequest.getUsername())
			    .password(authRequest.getPassword())
			    .email(authRequest.getEmail())
			    .roles(roleMapper.toDtoList(List.of(roleService.getUserRole())))
			    .build();
			userService.createUser(user);
			var userDetails = userDetailsService.loadUserByUsername(user.getUsername());
			return jwtTokenUtils.generateToken(userDetails);
		} catch (Exception e) {
			return null;
		}
	}
}
