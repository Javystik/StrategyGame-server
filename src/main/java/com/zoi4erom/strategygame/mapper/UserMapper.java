package com.zoi4erom.strategygame.mapper;

import com.zoi4erom.strategygame.dto.UserDto;
import com.zoi4erom.strategygame.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper implements Mapper<User, UserDto>{

	@Override
	public UserDto toDto(User entity) {
		return UserDto.builder()
		    .id(entity.getId())
		    .username(entity.getUsername())
		    .email(entity.getEmail())
		    .password(entity.getPassword())
		    .createdAt(entity.getCreatedAt())
		    .playerGames(entity.getPlayerGames())
		    .winGames(entity.getWinGames())
		    .enemyUnitsKilled(entity.getEnemyUnitsKilled())
		    .unitsDeaths(entity.getUnitsDeaths())
		    .territoriesCaptured(entity.getTerritoriesCaptured())
		    .territoriesLost(entity.getTerritoriesLost())
		    .build();
	}

	@Override
	public User toEntity(UserDto dto) {
		return User.builder()
		    .id(dto.getId())
		    .username(dto.getUsername())
		    .email(dto.getEmail())
		    .password(dto.getPassword())
		    .createdAt(dto.getCreatedAt())
		    .playerGames(dto.getPlayerGames())
		    .winGames(dto.getWinGames())
		    .enemyUnitsKilled(dto.getEnemyUnitsKilled())
		    .unitsDeaths(dto.getUnitsDeaths())
		    .territoriesCaptured(dto.getTerritoriesCaptured())
		    .territoriesLost(dto.getTerritoriesLost())
		    .build();
	}
}
