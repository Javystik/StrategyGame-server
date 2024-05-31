package com.zoi4erom.strategygame.controller;

import com.zoi4erom.strategygame.dto.PlayerResourcesDto;
import com.zoi4erom.strategygame.service.contract.PlayerResourcesService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/playerResources")
@RequiredArgsConstructor
@CrossOrigin
public class PlayerResourcesController {

	private final PlayerResourcesService playerResourcesService;

	@PostMapping
	public ResponseEntity<String> createPlayerResources(
	    @RequestBody PlayerResourcesDto playerResourcesDto) {
		if (playerResourcesService.createPlayerResources(playerResourcesDto.getUserId(),
		    playerResourcesDto.getGameId())) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping
	public ResponseEntity<PlayerResourcesDto> getPlayerResources(@RequestParam Long gameId,
	    @RequestParam Long userId) {
		Optional<PlayerResourcesDto> playerResourcesDtoOptional = playerResourcesService.getPlayerResourcesByGameIdAndUserId(
		    gameId, userId);
		return playerResourcesDtoOptional.map(ResponseEntity::ok)
		    .orElseGet(() -> ResponseEntity.notFound().build());
	}

	@PostMapping("/update")
	public ResponseEntity<String> updatePlayerResources(
	    @RequestBody PlayerResourcesDto playerResourcesDto) {
		if (playerResourcesService.updatePlayerResources(playerResourcesDto)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	@PostMapping("/batchUpdate")
	public ResponseEntity<String> batchUpdatePlayerResources(
	    @RequestBody List<PlayerResourcesDto> playerResourcesDtos) {
		if (playerResourcesService.batchUpdatePlayerResources(playerResourcesDtos)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
}
