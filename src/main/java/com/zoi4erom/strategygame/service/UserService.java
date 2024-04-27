package com.zoi4erom.strategygame.service;

import com.zoi4erom.strategygame.dto.UserDto;
import com.zoi4erom.strategygame.entity.Role;
import com.zoi4erom.strategygame.mapper.UserMapper;
import com.zoi4erom.strategygame.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final RoleService roleService;
	private final UserMapper userMapper;

	public void createUser(UserDto userDto) {
		var entity = userMapper.toEntity(userDto);
//		Role userRole = roleService.findRoleByName("ROLE_USER")
//		    .orElseThrow(() -> new RuntimeException("User Role not found"));
//		entity.getRoles().add(userRole);

		userRepository.save(entity);
	}

	public List<UserDto> getAllUsers() {
		return userRepository.findAll()
		    .stream()
		    .map(userMapper::toDto)
		    .toList();
	}

	public Optional<UserDto> getUserByUsername(String username) {
		return userRepository.findUserByUsername(username)
		    .map(userMapper::toDto);
	}

	public Optional<UserDto> getUserById(Integer id) {
		return userRepository.findById(id)
		    .map(userMapper::toDto);
	}

}
