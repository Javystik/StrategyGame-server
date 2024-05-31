package com.zoi4erom.strategygame.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class GameDto {

	private Long id;
	private String name;
	private Integer maxPlayers;
	private Integer currentPlayers;
	private boolean isActive;
}
