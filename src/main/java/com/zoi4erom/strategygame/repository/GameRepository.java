package com.zoi4erom.strategygame.repository;

import com.zoi4erom.strategygame.entity.Game;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameRepository extends JpaRepository<Game, Long> {
	Optional<Game> findGameByName(String gameName);
}
