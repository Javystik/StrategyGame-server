package com.zoi4erom.strategygame.mapper;

import com.zoi4erom.strategygame.dto.AllianceDto;
import com.zoi4erom.strategygame.entity.Alliance;
import org.springframework.stereotype.Component;

@Component
public class AllianceMapper implements Mapper<Alliance, AllianceDto> {

	@Override
	public AllianceDto toDto(Alliance entity) {
		return AllianceDto.builder()
		    .id(entity.getId())
		    .name(entity.getName())
		    .membersCount(entity.getMembersCount())
		    .totalWins(entity.getTotalWins())
		    .build();
	}

	@Override
	public Alliance toEntity(AllianceDto dto) {
		return Alliance.builder()
		    .id(dto.getId())
		    .name(dto.getName())
		    .membersCount(dto.getMembersCount())
		    .totalWins(dto.getTotalWins())
		    .build();
	}
}
