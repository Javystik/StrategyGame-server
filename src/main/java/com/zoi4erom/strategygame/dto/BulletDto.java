package com.zoi4erom.strategygame.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BulletDto {

	private Long id;
	private int x;
	private int y;
	private int gameId;
	private int userId;
	private int damage;
	private String type;
}
