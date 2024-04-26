package com.zoi4erom.strategygame.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AllianceDto {
	private Integer id;
	private String name;
	private Integer membersCount;
	private Integer totalWins;
}
