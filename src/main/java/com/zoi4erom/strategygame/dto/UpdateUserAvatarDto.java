package com.zoi4erom.strategygame.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateUserAvatarDto {
	private Long userId;
	private String base64Image;
}
