package com.zoi4erom.strategygame.service.contract;

import com.zoi4erom.strategygame.dto.AuthRequest;
import com.zoi4erom.strategygame.dto.UpdateUserAvatarDto;
import com.zoi4erom.strategygame.dto.UpdateUserDto;
import com.zoi4erom.strategygame.dto.UserDto;
import com.zoi4erom.strategygame.dto.search.UserSearch;
import com.zoi4erom.strategygame.entity.Alliance;
import com.zoi4erom.strategygame.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface UserService {

	boolean createUser(AuthRequest authRequest);

	List<UserDto> getAllUsers();

	Page<UserDto> getAllUsersBySpecificationAndPagination(UserSearch userSearch, int pageNo,
	    int pageSize);

	Optional<UserDto> getUserByUsername(String username);

	Optional<UserDto> getUserById(Long id);

	Optional<User> getUserEntityById(Long id);

	Optional<UserDto> findUserByEmail(String email);

	List<UserDto> findUserByAllianceId(Long allianceId);

	Long getClanIdByUserName(String userName);

	void saveUser(User user);

	void updateAvatar(UpdateUserAvatarDto updateUserAvatarDto);

	boolean leaveUserWithClan(String username);

	boolean joinUserWithClan(String username, Alliance alliance);

	void updateUserAlliance(Alliance alliance, Long id);

	void updateUser(UpdateUserDto updateUserDto, String username);
	void deleteUserById(Long id);
}
