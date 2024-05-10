package com.zoi4erom.strategygame.service;

import com.zoi4erom.strategygame.dto.AuthRequest;
import com.zoi4erom.strategygame.dto.UpdateUserAvatarDto;
import com.zoi4erom.strategygame.dto.UpdateUserDto;
import com.zoi4erom.strategygame.dto.UserDto;
import com.zoi4erom.strategygame.entity.Alliance;
import com.zoi4erom.strategygame.entity.User;
import com.zoi4erom.strategygame.exception.UserNotFoundException;
import com.zoi4erom.strategygame.mapper.UserMapper;
import com.zoi4erom.strategygame.repository.UserRepository;
import com.zoi4erom.strategygame.service.impl.ImageServiceImpl;
import com.zoi4erom.strategygame.service.impl.ImageServiceImpl.DefaultImagePatch;
import com.zoi4erom.strategygame.spec.UserSpecification;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
	private final ImageServiceImpl imageService;

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
		Sort sort = Sort.by(Direction.DESC, "statistic.winGames");
		return userRepository.findAll(sort)
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

	public Optional<User> getUserEntityById(Long id) {
		return userRepository.findById(id);
	}

	public Optional<UserDto> findUserByEmail(String email) {
		return userRepository.findUserByEmail(email)
		    .map(userMapper::toDto);
	}

	public List<UserDto> findUserByAllianceId(Long allianceId) {
		return userRepository.findUserByAlliance_Id(allianceId)
		    .stream()
		    .map(userMapper::toDto)
		    .toList();
	}

	public Long getClanIdByUserName(String userName) {
		var user = userRepository.findUserByUsername(userName).orElseThrow(
		    () -> new UserNotFoundException("username", userName)
		);
		return user.getAlliance().getId();
	}

	public void saveUser(User user) {
		userRepository.save(user);
	}

	public void updateAvatar(UpdateUserAvatarDto updateUserAvatarDto) {
		User user = getUserEntityById(updateUserAvatarDto.getUserId()).orElseThrow(
		    () -> new UserNotFoundException("id", updateUserAvatarDto.getUserId().toString())
		);

		user.setAvatarUrl(imageService.saveImageBase64(updateUserAvatarDto.getBase64Image(),
		    user.getAvatarUrl(), DefaultImagePatch.USER_AVATAR));

		userRepository.save(user);
	}

	public void updateUserAlliance(Alliance alliance, Long id) {
		User user = getUserEntityById(id).orElseThrow(
		    () -> new UserNotFoundException("id", id.toString())
		);
		user.setAlliance(alliance);

		userRepository.save(user);
	}
	public void updateUser(UpdateUserDto updateUserDto, String username){
		var userForUpdate = getUserByUsername(username).orElseThrow();
		var user = getUserEntityById(userForUpdate.getId()).orElseThrow();

		if(updateUserDto.getEmail() != null){
			user.setEmail(updateUserDto.getEmail());
		}

		if (updateUserDto.getPassword() != null && !updateUserDto.getPassword().isBlank()){
			user.setPassword(passwordEncoder.encode(updateUserDto.getPassword()));
		}

		userRepository.save(user);
	}
}
