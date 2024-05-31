package com.zoi4erom.strategygame.dto;

import jakarta.validation.constraints.NotNull;
import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UnitTemplateDto{

	private Long id;
	private String name;
	private int cost;
	private int reload;
	private int healthPoints;
	private int damage;
	private int speed;
	private int range;
	private UnitTypeDto unitType;
}
