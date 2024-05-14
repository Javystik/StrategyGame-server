package com.zoi4erom.strategygame.mapper;

import com.zoi4erom.strategygame.dto.StatisticDto;
import com.zoi4erom.strategygame.entity.Statistic;
import org.springframework.stereotype.Component;

/**
 * Mapper class responsible for converting Statistic entities to StatisticDto objects and vice
 * versa.
 */
@Component
public class StatisticMapper implements Mapper<Statistic, StatisticDto> {

	/**
	 * Converts a Statistic entity to a StatisticDto object.
	 *
	 * @param entity The Statistic entity to be converted.
	 * @return The StatisticDto object representing the entity.
	 */
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

	/**
	 * Converts a StatisticDto object to a Statistic entity.
	 *
	 * @param dto The StatisticDto object to be converted.
	 * @return The Statistic entity representing the DTO.
	 */
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
