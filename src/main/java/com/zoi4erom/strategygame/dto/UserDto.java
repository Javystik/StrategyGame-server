package com.zoi4erom.strategygame.dto;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
//TODO: при надобності в юзера вкласти AllianceDto
//TODO: забрати password field
public class UserDto {
	private Integer id;
	private String username;
	private String email;
	private String password;

	//private AllianceDto allianceDto;

	private LocalDate createdAt;
	private Integer playerGames;
	private Integer winGames;
	private Integer enemyUnitsKilled;
	private Integer unitsDeaths;
	private Integer territoriesCaptured;
	private Integer territoriesLost;
}
