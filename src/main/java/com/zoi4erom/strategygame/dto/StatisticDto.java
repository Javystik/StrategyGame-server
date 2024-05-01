package com.zoi4erom.strategygame.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Builder
public class StatisticDto {

	private Integer id;
	private Integer playerGames;
	private Integer winGames;
	private Integer enemyUnitsKilled;
	private Integer unitsDeaths;
	private Integer territoriesCaptured;
	private Integer territoriesLost;
}
