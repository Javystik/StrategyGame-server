package com.zoi4erom.strategygame.service.impl;

import com.zoi4erom.strategygame.dto.AuthRequest;
import com.zoi4erom.strategygame.dto.UpdateUserAvatarDto;
import com.zoi4erom.strategygame.dto.UpdateUserDto;
import com.zoi4erom.strategygame.dto.UserDto;
import com.zoi4erom.strategygame.dto.search.UserSearch;
import com.zoi4erom.strategygame.entity.Alliance;
import com.zoi4erom.strategygame.entity.User;
import com.zoi4erom.strategygame.exception.UserNotFoundException;
import com.zoi4erom.strategygame.mapper.UserMapper;
import com.zoi4erom.strategygame.repository.UserRepository;
import com.zoi4erom.strategygame.service.contract.ImageService;
import com.zoi4erom.strategygame.service.contract.RoleService;
import com.zoi4erom.strategygame.service.contract.UserService;
import com.zoi4erom.strategygame.service.impl.ImageServiceImpl.DefaultImagePatch;
import com.zoi4erom.strategygame.spec.UserSpecification;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Implementation of the interface providing functionality related to user management. This
 * implementation includes methods for creating users, retrieving user data, updating user
 * information, managing user alliances, and more.
 */
@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository; //User repository
	private final UserMapper userMapper;// User mapper
	private final PasswordEncoder passwordEncoder;// Password encoder
	private final RoleService roleService;// Role service
	private final ImageService imageService;// Image service

	/**
	 * Creates a new user based on the provided authentication request.
	 *
	 * @param authRequest The authentication request containing user data
	 * @return True if the user was created successfully, false otherwise
	 */
	@Override
	public boolean createUser(AuthRequest authRequest) {
		User user = User.builder()
		    .username(authRequest.getUsername())
		    .password(passwordEncoder.encode(authRequest.getPassword()))
		    .email(authRequest.getEmail())
		    .roles(List.of(roleService.getUserRole()))
		    .build();
		try {
			userRepository.save(user);
			log.info("User created successfully: {}", user.getUsername());
			return true;
		} catch (Exception e) {
			log.error("Error creating user: {}", e.getMessage());
			return false;
		}
	}

	/**
	 * Retrieves a list of all users.
	 *
	 * @return A list of user DTOs representing all users
	 */
	@Override
	public List<UserDto> getAllUsers() {
		log.info("Retrieving all users");
		Sort sort = Sort.by(Direction.DESC, "statistic.winGames");
		return userRepository.findAll(sort)
		    .stream()
		    .map(userMapper::toDto)
		    .toList();
	}

	/**
	 * Retrieves a page of users based on the specified search criteria and pagination
	 * parameters.
	 *
	 * @param userSearch The search criteria for filtering users
	 * @param pageNo     The page number to retrieve
	 * @param pageSize   The number of users per page
	 * @return A page of user DTOs matching the search criteria
	 */
	@Override
	public Page<UserDto> getAllUsersBySpecificationAndPagination(UserSearch userSearch,
	    int pageNo, int pageSize) {
		log.info("Executing search query with specifications for user: {}", userSearch);
		Sort sort = Sort.by(Sort.Direction.DESC, "statistic.winGames");
		UserSpecification userSpecification = new UserSpecification(userSearch);
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<User> usersPage = userRepository.findAll(userSpecification, pageable);
		return usersPage.map(userMapper::toDto);
	}

	/**
	 * Retrieves a user DTO by their username.
	 *
	 * @param username The username of the user to retrieve
	 * @return An optional containing the user DTO if found, empty otherwise
	 */
	@Override
	public Optional<UserDto> getUserByUsername(String username) {
		log.info("Retrieving user by username: {}", username);
		return userRepository.findUserByUsername(username)
		    .map(userMapper::toDto);
	}

	/**
	 * Retrieves a user DTO by their ID.
	 *
	 * @param id The ID of the user to retrieve
	 * @return An optional containing the user DTO if found, empty otherwise
	 */
	@Override
	public Optional<UserDto> getUserById(Long id) {
		log.info("Retrieving user by id: {}", id);
		return userRepository.findById(id)
		    .map(userMapper::toDto);
	}

	/**
	 * Retrieves a user entity by their ID.
	 *
	 * @param id The ID of the user entity to retrieve
	 * @return An optional containing the user entity if found, empty otherwise
	 */
	@Override
	public Optional<User> getUserEntityById(Long id) {
		log.info("Retrieving user entity by id: {}", id);
		return userRepository.findById(id);
	}

	/**
	 * Retrieves a user DTO by their email address.
	 *
	 * @param email The email address of the user to retrieve
	 * @return An optional containing the user DTO if found, empty otherwise
	 */
	@Override
	public Optional<UserDto> findUserByEmail(String email) {
		log.info("Retrieving user by email: {}", email);
		return userRepository.findUserByEmail(email)
		    .map(userMapper::toDto);
	}

	/**
	 * Retrieves a list of user DTOs belonging to a specific alliance.
	 *
	 * @param allianceId The ID of the alliance
	 * @return A list of user DTOs belonging to the alliance
	 */
	@Override
	public List<UserDto> findUserByAllianceId(Long allianceId) {
		log.info("Retrieving users by alliance id: {}", allianceId);
		return userRepository.findUserByAlliance_Id(allianceId)
		    .stream()
		    .map(userMapper::toDto)
		    .toList();
	}

	/**
	 * Retrieves the ID of the clan (alliance) associated with a user.
	 *
	 * @param userName The username of the user
	 * @return The ID of the clan associated with the user
	 * @throws UserNotFoundException If the user with the specified username is not found
	 */
	@Override
	public Long getClanIdByUserName(String userName) {
		log.info("Retrieving clan id by user name: {}", userName);
		var user = userRepository.findUserByUsername(userName).orElseThrow(
		    () -> new UserNotFoundException("username", userName)
		);
		return user.getAlliance().getId();
	}

	/**
	 * Saves the provided user entity.
	 *
	 * @param user The user entity to save
	 */
	@Override
	public void saveUser(User user) {
		log.info("Saving user: {}", user.getUsername());
		userRepository.save(user);
	}

	/**
	 * Updates the avatar of a user based on the provided avatar update DTO.
	 *
	 * @param updateUserAvatarDto The DTO containing the user ID and base64 encoded image
	 */
	@Override
	public void updateAvatar(UpdateUserAvatarDto updateUserAvatarDto) {
		log.info("Updating avatar for user with id: {}", updateUserAvatarDto.getUserId());
		User user = getUserEntityById(updateUserAvatarDto.getUserId()).orElseThrow(
		    () -> new UserNotFoundException("id", updateUserAvatarDto.getUserId().toString())
		);

		user.setAvatarUrl(imageService.saveImageBase64(updateUserAvatarDto.getBase64Image(),
		    user.getAvatarUrl(), DefaultImagePatch.USER_AVATAR));

		userRepository.save(user);
	}

	/**
	 * Removes a user from their alliance (clan).
	 *
	 * @param username The username of the user to leave from the alliance
	 * @return True if the user was successfully removed from the alliance, false otherwise
	 */
	@Override
	public boolean leaveUserWithClan(String username) {
		log.info("Leaving user from clan: {}", username);
		var user = getUserByUsername(username).orElseThrow();

		if (getUserEntityById(user.getId()).get().getAlliance() != null) {
			updateUserAlliance(null, user.getId());
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Adds a user to the specified alliance (clan).
	 *
	 * @param username The username of the user to join the alliance
	 * @param alliance The alliance to join
	 * @return True if the user successfully joined the alliance, false if the user is already in
	 * an alliance
	 */
	@Override
	public boolean joinUserWithClan(String username, Alliance alliance) {
		log.info("Joining user to clan: {}", username);
		var user = getUserByUsername(username).orElseThrow();

		if (getUserEntityById(user.getId()).get().getAlliance() == null) {
			updateUserAlliance(alliance, user.getId());
			return true;
		} else {
			return false;
		}
	}


	/**
	 * Updates the alliance (clan) of a user.
	 *
	 * @param alliance The new alliance to set for the user
	 * @param id       The ID of the user
	 */
	@Override
	public void updateUserAlliance(Alliance alliance, Long id) {
		log.info("Updating user alliance with id: {}", id);
		User user = getUserEntityById(id).orElseThrow(
		    () -> new UserNotFoundException("id", id.toString())
		);
		user.setAlliance(alliance);

		userRepository.save(user);
	}

	/**
	 * Updates the information of a user based on the provided update DTO and username.
	 *
	 * @param updateUserDto The DTO containing the updated user information
	 * @param username      The username of the user to update
	 */
	@Override
	public void updateUser(UpdateUserDto updateUserDto, String username) {
		log.info("Updating user: {}", username);
		var userForUpdate = getUserByUsername(username).orElseThrow();
		var user = getUserEntityById(userForUpdate.getId()).orElseThrow();

		if (updateUserDto.getEmail() != null) {
			user.setEmail(updateUserDto.getEmail());
		}

		if (updateUserDto.getPassword() != null && !updateUserDto.getPassword().isBlank()) {
			user.setPassword(passwordEncoder.encode(updateUserDto.getPassword()));
		}

		log.info("Ending updating user: {}", username);
		userRepository.save(user);
	}

	/**
	 * Deletes a user by their ID.
	 *
	 * @param id The ID of the user to delete
	 */
	public void deleteUserById(Long id) {
		userRepository.deleteById(id);
	}
}
