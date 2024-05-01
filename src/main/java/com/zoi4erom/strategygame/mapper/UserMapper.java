package com.zoi4erom.strategygame.mapper;

import com.zoi4erom.strategygame.dto.UserDto;
import com.zoi4erom.strategygame.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper implements Mapper<User, UserDto> {

	private final StatisticMapper statisticMapper;

	@Override
	public UserDto toDto(User entity) {
		return UserDto.builder()
		    .id(entity.getId())
		    .username(entity.getUsername())
		    .email(entity.getEmail())
		    .password(entity.getPassword())
		    .createdAt(entity.getCreatedAt())
		    .statisticDto(statisticMapper.toDto(entity.getStatistic()))
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
		    .statistic(statisticMapper.toEntity(dto.getStatisticDto()))
		    .build();
	}
}
