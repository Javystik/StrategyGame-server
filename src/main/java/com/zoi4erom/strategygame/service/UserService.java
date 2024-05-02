package com.zoi4erom.strategygame.service;

import com.zoi4erom.strategygame.dto.AuthRequest;
import com.zoi4erom.strategygame.dto.UserDto;
import com.zoi4erom.strategygame.entity.User;
import com.zoi4erom.strategygame.mapper.UserMapper;
import com.zoi4erom.strategygame.repository.UserRepository;
import com.zoi4erom.strategygame.spec.UserSpecification;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
	private final RoleService roleService;

	public void createUser(AuthRequest authRequest) {
		User user = User.builder()
		    .username(authRequest.getUsername())
		    .password(passwordEncoder.encode(authRequest.getPassword()))
		    .email(authRequest.getEmail())
		    .roles(List.of(roleService.getUserRole()))
		    .build();
		try {
			userRepository.save(user);
		} catch (Exception e) {
			log.error("Помилка при створенні юзера! Можлива помилка валідації");
		}
	}

	public List<UserDto> getAllUsers() {
		return userRepository.findAll()
		    .stream()
		    .map(userMapper::toDto)
		    .toList();
	}

	public List<UserDto> getAllUsersBySpecification(UserSpecification userSpecification) {
		return userRepository.findAll(userSpecification)
		    .stream()
		    .map(userMapper::toDto)
		    .toList();
	}

	public Optional<UserDto> getUserByUsername(String username) {
		return userRepository.findUserByUsername(username)
		    .map(userMapper::toDto);
	}

	public Optional<UserDto> getUserById(Long id) {
		return userRepository.findById(id)
		    .map(userMapper::toDto);
	}

	public Optional<UserDto> findUserByEmail(String email) {
		return userRepository.findUserByEmail(email)
		    .map(userMapper::toDto);
	}

	public void saveUser(User user) {
		userRepository.save(user);
	}
}
