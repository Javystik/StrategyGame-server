package com.zoi4erom.strategygame.controller;

import com.zoi4erom.strategygame.dto.AllianceDto;
import com.zoi4erom.strategygame.dto.UpdateUserAvatarDto;
import com.zoi4erom.strategygame.dto.UpdateUserDto;
import com.zoi4erom.strategygame.dto.UserDto;
import com.zoi4erom.strategygame.service.AllianceService;
import com.zoi4erom.strategygame.service.UserService;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@CrossOrigin
public class UserController {

	private final UserService userService;
	private final AllianceService allianceService;

	@GetMapping
	public ResponseEntity<List<UserDto>> getAllUsers() {
		List<UserDto> users = userService.getAllUsers();
		return users.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(users);
	}

	@GetMapping("/{id}")
	public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
		return userService.getUserById(id)
		    .map(ResponseEntity::ok)
		    .orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/by-username/{username}")
	public ResponseEntity<UserDto> getUserByUsername(@PathVariable String username) {
		return userService.getUserByUsername(username)
		    .map(ResponseEntity::ok)
		    .orElse(ResponseEntity.notFound().build());
	}

	@PatchMapping("/update-avatar")
	public void updateUserAvatar(@RequestBody UpdateUserAvatarDto updateUserAvatarDto) {
		userService.updateAvatar(updateUserAvatarDto);
	}

	@GetMapping("/alliance/{clanId}")
	public ResponseEntity<List<UserDto>> getUsersByAllianceId(@PathVariable Long clanId) {
		List<UserDto> users = userService.findUserByAllianceId(clanId);
		if (users.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(users);
		}
	}

	@GetMapping("/alliance")
	public ResponseEntity<Long> getClanIdByUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		return ResponseEntity.ok(userService.getClanIdByUserName(authentication.getName()));
	}

	@GetMapping("/alliance/leave")
	public void leaveTheClan(){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		var user = userService.getUserByUsername(authentication.getName()).orElseThrow();
		userService.updateUserAlliance(null, user.getId());
	}
	@GetMapping("/alliance/join/{clanId}")
	public void joinTheClan(@PathVariable Long clanId){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		var user = userService.getUserByUsername(authentication.getName()).orElseThrow();
		var alliance = allianceService.getAllianceEntityById(clanId);

		userService.updateUserAlliance(alliance, user.getId());
	}
	@PatchMapping
	public void updateUser(@RequestBody UpdateUserDto updateUserDto){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		userService.updateUser(updateUserDto, authentication.getName());
	}
}
