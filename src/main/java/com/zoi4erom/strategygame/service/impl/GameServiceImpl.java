package com.zoi4erom.strategygame.service.impl;

import com.zoi4erom.strategygame.dto.GameDto;
import com.zoi4erom.strategygame.entity.Game;
import com.zoi4erom.strategygame.mapper.GameMapper;
import com.zoi4erom.strategygame.repository.GameRepository;
import com.zoi4erom.strategygame.service.contract.GameService;
import com.zoi4erom.strategygame.service.contract.PlayerResourcesService;
import com.zoi4erom.strategygame.service.contract.UnitService;
import com.zoi4erom.strategygame.service.contract.UserService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService {

	private final UserService userService;
	private final UnitService unitService;
	private final GameRepository gameRepository;
	private final GameMapper gameMapper;
	private final PlayerResourcesService playerResourcesService;

	@Override
	public boolean createGame(String name, int maxPlayers) {
		try {
			gameRepository.save(Game.builder()
			    .name(name)
			    .maxPlayers(maxPlayers)
			    .isActive(true)
			    .build());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public List<GameDto> getAllGames() {
		return gameRepository.findAll().stream().map(gameMapper::toDto).toList();
	}

	@Override
	public Optional<GameDto> findGameById(Long gameId) {
		return Optional.ofNullable(
		    gameMapper.toDto(gameRepository.findById(gameId).orElseThrow()));
	}

	@Override
	public Optional<Game> findGameEntityById(Long gameId) {
		return gameRepository.findById(gameId);
	}

	@Override
	public Optional<GameDto> findGameByName(String gameName) {
		return Optional.ofNullable(
		    gameMapper.toDto(gameRepository.findGameByName(gameName).orElseThrow()));
	}

	@Override
	public boolean joinGame(Long userId, Long gameId) {
		var user = userService.getUserEntityById(userId).orElseThrow();
		var game = findGameEntityById(gameId).orElseThrow();

		if (game.getUserList().contains(user)
		    || game.getUserList().size() >= game.getMaxPlayers()) {
			return false;
		}
		if (game.getCurrentPlayers() == game.getMaxPlayers()) {
			game.setActive(false);
		}

		playerResourcesService.createPlayerResources(userId, gameId);
		unitService.createQueenUnit(user, game);

		game.getUserList().add(user);

		game.setCurrentPlayers(game.getCurrentPlayers() + 1);

		gameRepository.save(game);
		return game.getUserList().add(user);
	}

	@Override
	public void finishGame(Long gameId) {
		findGameEntityById(gameId).orElseThrow().setActive(false);
	}
}
