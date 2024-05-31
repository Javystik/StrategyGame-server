package com.zoi4erom.strategygame.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@ToString
public class MessageDto {

	private Long id;
	private String text;
	private Long userId;
	private String username;
	private String clanTag;
	private Long gameId;
	private String type;
}
