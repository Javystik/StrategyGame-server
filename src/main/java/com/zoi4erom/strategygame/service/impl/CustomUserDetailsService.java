package com.zoi4erom.strategygame.service.impl;

import com.zoi4erom.strategygame.repository.UserRepository;
import com.zoi4erom.strategygame.security.CustomUserDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findUserByUsername(username)
		    .map(CustomUserDetails::new)
		    .orElseThrow(() -> new UsernameNotFoundException(username + " not found"));
	}
}
