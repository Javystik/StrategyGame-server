package com.zoi4erom.strategygame.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class AllianceDto {

	private Long id;
	private String name;
	private String avatarBytes;
	private String tag;
	private UserDto leader;
	private Integer membersCount;
	private Integer totalWins;
}
