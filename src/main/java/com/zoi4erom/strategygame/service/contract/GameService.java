package com.zoi4erom.strategygame.service.contract;

import com.zoi4erom.strategygame.dto.GameDto;
import com.zoi4erom.strategygame.entity.Game;
import java.util.List;
import java.util.Optional;

public interface GameService {

	boolean createGame(String name, int maxPlayers);

	List<GameDto> getAllGames();

	Optional<GameDto> findGameById(Long gameId);

	Optional<Game> findGameEntityById(Long gameId);

	Optional<GameDto> findGameByName(String gameName);

	boolean joinGame(Long userId, Long gameId);

	void finishGame(Long gameId);
}
