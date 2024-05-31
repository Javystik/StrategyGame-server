package com.zoi4erom.strategygame.mapper;

import com.zoi4erom.strategygame.dto.PlayerResourcesDto;
import com.zoi4erom.strategygame.entity.PlayerResource;
import org.springframework.stereotype.Component;

@Component
public class PlayerResourcesMapper implements Mapper<PlayerResource, PlayerResourcesDto> {

	@Override
	public PlayerResourcesDto toDto(PlayerResource entity) {
		return PlayerResourcesDto.builder()
		    .id(entity.getId())
		    .userId(entity.getUser().getId())
		    .gameId(entity.getGame().getId())
		    .resources(entity.getResources())
		    .build();
	}

	@Override
	public PlayerResource toEntity(PlayerResourcesDto dto) {
		return PlayerResource.builder()
		    .id(dto.getId())
		    .resources(dto.getResources())
		    .build();
	}
}
