package com.zoi4erom.strategygame.controller;

import com.zoi4erom.strategygame.dto.GameDto;
import com.zoi4erom.strategygame.service.contract.GameService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/games")
@RequiredArgsConstructor
@CrossOrigin
public class GameController {

	private final GameService gameService;

	@PostMapping
	public ResponseEntity<?> createGame(@RequestParam String name, @RequestParam int maxPlayers) {
		if (gameService.createGame(name, maxPlayers)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
	@GetMapping
	public ResponseEntity<List<GameDto>> getAllGames(){
		var allGames = gameService.getAllGames();
		if(allGames != null && !allGames.isEmpty()){
			return ResponseEntity.ok(allGames);
		}else{
			return ResponseEntity.noContent().build();
		}
	}
	@GetMapping("/{gameId}")
	public ResponseEntity<GameDto> findGameById(@PathVariable Long gameId) {
		Optional<GameDto> gameDtoOptional = gameService.findGameById(gameId);
		return gameDtoOptional.map(ResponseEntity::ok)
		    .orElse(ResponseEntity.notFound().build());
	}

	@GetMapping("/name/{gameName}")
	public ResponseEntity<GameDto> findGameByName(@PathVariable String gameName) {
		Optional<GameDto> gameDtoOptional = gameService.findGameByName(gameName);
		return gameDtoOptional.map(ResponseEntity::ok)
		    .orElse(ResponseEntity.notFound().build());
	}

	@PostMapping("/{userId}/{gameId}/join")
	public ResponseEntity<Void> joinGame(@PathVariable Long userId, @PathVariable Long gameId) {
		if (gameService.joinGame(userId, gameId)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
}
