package com.zoi4erom.strategygame.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class PlayerResourcesDto {

	private Long id;
	private Long userId;
	private Long gameId;
	private int resources;
}
