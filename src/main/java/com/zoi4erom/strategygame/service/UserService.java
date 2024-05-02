package com.zoi4erom.strategygame.service;

import com.zoi4erom.strategygame.dto.UserDto;
import com.zoi4erom.strategygame.entity.User;
import com.zoi4erom.strategygame.mapper.UserMapper;
import com.zoi4erom.strategygame.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final StatisticService statisticService;
	private final UserMapper userMapper;

	public void createUser(UserDto userDto) {
		userDto.setStatisticDto(statisticService.createStatistic());
		var user = userMapper.toEntity(userDto);

		user.setIsVerified(false);
		user.setCreatedAt(LocalDateTime.now());
		userRepository.save(user);
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

	public Optional<UserDto> findUserByEmail(String email) {
		return userRepository.findUserByEmail(email)
		    .map(userMapper::toDto);
	}
	public void saveUser(User user){
		userRepository.save(user);
	}
}
