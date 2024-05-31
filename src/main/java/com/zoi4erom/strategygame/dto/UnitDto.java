package com.zoi4erom.strategygame.dto;

import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UnitDto{

	private Long id;
	private int currentHealthPoints;
	private boolean isAlive;
	private int x;
	private int y;
	private Long gameId;
	private Long userId;
	private String userName;
	private UnitTemplateDto unitTemplateDto;
	private String type;
}
