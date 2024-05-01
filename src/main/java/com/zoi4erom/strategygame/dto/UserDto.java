package com.zoi4erom.strategygame.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDto {

	private Integer id;
	private String username;
	private String email;
	private String password;
	private LocalDateTime createdAt;
	private StatisticDto statisticDto;
}
