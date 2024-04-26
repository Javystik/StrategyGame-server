package com.zoi4erom.strategygame.service;

import com.zoi4erom.strategygame.dto.UserDto;
import com.zoi4erom.strategygame.mapper.UserMapper;
import com.zoi4erom.strategygame.repository.UserRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
//TODO вирішити костиль при створенні з вставкою дати та часу
public class UserService {

	private final UserRepository userRepository;
	private final UserMapper userMapper;

	public void createUser(UserDto userDto) {
		userDto.setCreatedAt(LocalDate.now());
		userDto.setPlayerGames(0);
		userDto.setWinGames(0);
		userDto.setEnemyUnitsKilled(0);
		userDto.setUnitsDeaths(0);
		userDto.setTerritoriesCaptured(0);
		userDto.setTerritoriesLost(0);
		userRepository.save(userMapper.toEntity(userDto));
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