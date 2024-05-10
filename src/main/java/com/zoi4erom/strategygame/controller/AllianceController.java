package com.zoi4erom.strategygame.controller;

import com.zoi4erom.strategygame.dto.AllianceDto;
import com.zoi4erom.strategygame.dto.ClanCreateDto;
import com.zoi4erom.strategygame.dto.UpdateClanAvatarDto;
import com.zoi4erom.strategygame.exception.UserNotFoundException;
import com.zoi4erom.strategygame.service.AllianceService;
import com.zoi4erom.strategygame.service.UserService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alliances")
@AllArgsConstructor
@CrossOrigin
public class AllianceController {

	private final AllianceService allianceService;
	private final UserService userService;

	@PostMapping
	public ResponseEntity<String> createAlliance(@RequestBody ClanCreateDto clanCreateDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		var user = userService.getUserByUsername(authentication.getName())
		    .orElseThrow(() -> new UserNotFoundException("username", authentication.getName()));
		allianceService.createAlliance(clanCreateDto, user);
		return ResponseEntity.ok("Alliance successfully created");
	}

	@GetMapping
	public ResponseEntity<List<AllianceDto>> getAllAlliance() {
		var allAlliances = allianceService.getAllAlliance();

		return allAlliances.isEmpty() ? ResponseEntity.noContent().build()
		    : ResponseEntity.ok(allAlliances);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AllianceDto> getAllianceById(@PathVariable Long id) {
		return allianceService.getAllianceById(id)
		    .map(ResponseEntity::ok)
		    .orElse(ResponseEntity.notFound().build());
	}

	@PutMapping
	public ResponseEntity<?> changeClanAvatar(
	    @RequestBody UpdateClanAvatarDto updateClanAvatarDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		var clanChanged = allianceService.changeClanAvatar(updateClanAvatarDto,
		    authentication.getName());

		if (clanChanged) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.status(403).build();
		}
	}
}