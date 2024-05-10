package com.zoi4erom.strategygame.dto;

import lombok.Getter;

@Getter
public class UpdateClanAvatarDto {
	private Long clanId;
	private String base64Image;
}
