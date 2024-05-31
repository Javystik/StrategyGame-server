package com.zoi4erom.strategygame.dto;

import java.io.Serializable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UnitTypeDto{

	private Long id;
	private String name;
	private String size;
}
