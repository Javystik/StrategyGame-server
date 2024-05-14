package com.zoi4erom.strategygame.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StatisticDto {

	private Long id;
	private int playerGames;
	private int winGames;
	private int enemyUnitsKilled;
	private int unitsDeaths;
	private int territoriesCaptured;
	private int territoriesLost;
}
