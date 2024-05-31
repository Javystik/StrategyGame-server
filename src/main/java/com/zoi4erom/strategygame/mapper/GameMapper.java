package com.zoi4erom.strategygame.mapper;

import com.zoi4erom.strategygame.dto.GameDto;
import com.zoi4erom.strategygame.entity.Game;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GameMapper implements Mapper<Game, GameDto> {

	@Override
	public GameDto toDto(Game entity) {
		return GameDto.builder()
		    .id(entity.getId())
		    .name(entity.getName())
		    .maxPlayers(entity.getMaxPlayers())
		    .currentPlayers(entity.getCurrentPlayers())
		    .isActive(entity.isActive())
		    .build();
	}

	@Override
	public Game toEntity(GameDto dto) {
		return Game.builder()
		    .id(dto.getId())
		    .name(dto.getName())
		    .maxPlayers(dto.getMaxPlayers())
		    .currentPlayers(dto.getCurrentPlayers())
		    .isActive(dto.isActive())
		    .build();
	}
}
