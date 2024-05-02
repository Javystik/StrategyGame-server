package com.zoi4erom.strategygame.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ArticleDto {
	private Long id;
	private String name;
	private String description;
	private UserDto userDto;
}
