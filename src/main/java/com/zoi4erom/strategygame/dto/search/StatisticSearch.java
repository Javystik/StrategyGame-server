package com.zoi4erom.strategygame.dto.search;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StatisticSearch {

	private Range<Integer> playerGames;
	private Range<Integer> winGames;
	private Range<Integer> enemyUnitsKilled;
	private Range<Integer> unitsDeaths;
	private Range<Integer> territoriesCaptured;
	private Range<Integer> territoriesLost;
}
