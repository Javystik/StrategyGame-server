package com.zoi4erom.strategygame.controller;

import com.zoi4erom.strategygame.dto.UpdateUserAvatarDto;
import com.zoi4erom.strategygame.dto.UpdateUserDto;
import com.zoi4erom.strategygame.dto.UserDto;
import com.zoi4erom.strategygame.dto.search.UserSearch;
import com.zoi4erom.strategygame.service.AllianceService;
import com.zoi4erom.strategygame.service.GenerateFakeData;
import com.zoi4erom.strategygame.service.UserService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
@CrossOrigin
public class UserController {

	private final UserService userService;
	private final AllianceService allianceService;
	private final GenerateFakeData generateFakeData;

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
	@PostMapping("/by-specification")
	public ResponseEntity<Page<UserDto>> getUsersBySpecification(
	    @RequestBody UserSearch userSearch,
	    @RequestParam(name = "pageNo",  required = false, defaultValue = "0") int pageNo,
	    @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize) {
		Page<UserDto> usersPage = userService.getAllUsersBySpecificationAndPagination(
		    userSearch, pageNo, pageSize);
		return usersPage.isEmpty() ? ResponseEntity.noContent().build()
		    : ResponseEntity.ok(usersPage);
	}
	@GetMapping("/alliance")
	public ResponseEntity<Long> getClanIdByUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		return ResponseEntity.ok(userService.getClanIdByUserName(authentication.getName()));
	}

	@GetMapping("/alliance/leave")
	public ResponseEntity<?> leaveTheClan() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (userService.leaveUserWithClan(authentication.getName())){
			return ResponseEntity.ok().build();
		}else{
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("/alliance/join/{clanId}")
	public ResponseEntity<?> joinTheClan(@PathVariable Long clanId) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		var alliance = allianceService.getAllianceEntityById(clanId);

		if(userService.joinUserWithClan(authentication.getName(), alliance)){
			return ResponseEntity.ok().build();
		}else{
			return ResponseEntity.badRequest().build();
		}
	}

	@PatchMapping
	public void updateUser(@RequestBody UpdateUserDto updateUserDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		userService.updateUser(updateUserDto, authentication.getName());
	}

	@PostMapping("/generate-fake")
	@Secured("ROLE_ADMIN")
	public ResponseEntity<?> generateFakeUsers(@RequestParam int count) {
		generateFakeData.generateFakeAuthRequests(count);
		return ResponseEntity.ok().build();
	}
}
