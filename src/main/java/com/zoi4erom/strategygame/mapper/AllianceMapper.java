package com.zoi4erom.strategygame.mapper;

import com.zoi4erom.strategygame.dto.AllianceDto;
import com.zoi4erom.strategygame.entity.Alliance;
import com.zoi4erom.strategygame.service.impl.ImageServiceImpl;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AllianceMapper implements Mapper<Alliance, AllianceDto> {

	private final ImageServiceImpl imageService;
	private final UserMapper userMapper;

	@Override
	public AllianceDto toDto(Alliance entity) {
		return AllianceDto.builder()
		    .id(entity.getId())
		    .name(entity.getName())
		    .avatarBytes(imageService.loadImageBase64(entity.getAvatarUrl()))
		    .tag(entity.getTag())
		    .leader(userMapper.toDto(entity.getLeader()))
		    .membersCount(entity.getMembersCount())
		    .totalWins(entity.getTotalWins())
		    .build();
	}

	@Override
	public Alliance toEntity(AllianceDto dto) {
		return Alliance.builder()
		    .id(dto.getId())
		    .name(dto.getName())
		    .tag(dto.getTag())
		    .leader(userMapper.toEntity(dto.getLeader()))
		    .membersCount(dto.getMembersCount())
		    .totalWins(dto.getTotalWins())
		    .build();
	}
}
