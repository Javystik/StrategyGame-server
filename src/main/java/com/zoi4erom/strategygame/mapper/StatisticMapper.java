package com.zoi4erom.strategygame.mapper;

import com.zoi4erom.strategygame.dto.StatisticDto;
import com.zoi4erom.strategygame.entity.Statistic;
import org.springframework.stereotype.Component;

@Component
public class StatisticMapper implements Mapper<Statistic, StatisticDto>{

	@Override
	public StatisticDto toDto(Statistic entity) {
		return StatisticDto.builder()
		    .id(entity.getId())
		    .playerGames(entity.getPlayerGames())
		    .winGames(entity.getWinGames())
		    .enemyUnitsKilled(entity.getEnemyUnitsKilled())
		    .unitsDeaths(entity.getUnitsDeaths())
		    .territoriesCaptured(entity.getTerritoriesCaptured())
		    .territoriesLost(entity.getTerritoriesLost())
		    .build();
	}

	@Override
	public Statistic toEntity(StatisticDto dto) {
		return Statistic.builder()
		    .id(dto.getId())
		    .playerGames(dto.getPlayerGames())
		    .winGames(dto.getWinGames())
		    .enemyUnitsKilled(dto.getEnemyUnitsKilled())
		    .unitsDeaths(dto.getUnitsDeaths())
		    .territoriesCaptured(dto.getTerritoriesCaptured())
		    .territoriesLost(dto.getTerritoriesLost())
		    .build();
	}
}
