package com.zoi4erom.strategygame.dto;

import com.zoi4erom.strategygame.entity.Role;
import java.time.LocalDateTime;
import java.util.Collection;
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
	private Collection<RoleDto> roles;
	private String verificationCode;
	private Boolean isVerified;
	private LocalDateTime codeDeathTime;
}
