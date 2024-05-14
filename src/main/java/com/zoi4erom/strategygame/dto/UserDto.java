package com.zoi4erom.strategygame.dto;

import java.time.LocalDateTime;
import java.util.Collection;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
public class UserDto {

	private Long id;
	private String username;
	private String email;
	private String password;
	private String avatarBytes;
	private String clanTag;
	private LocalDateTime createdAt;
	private StatisticDto statisticDto;
	private Collection<RoleDto> roles;
	private String verificationCode;
	private Boolean isVerified;
	private LocalDateTime codeDeathTime;
}
