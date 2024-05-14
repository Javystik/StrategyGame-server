package com.zoi4erom.strategygame.mapper;

import com.zoi4erom.strategygame.dto.AllianceDto;
import com.zoi4erom.strategygame.entity.Alliance;
import com.zoi4erom.strategygame.service.impl.ImageServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper class responsible for mapping Alliance entities to AllianceDto objects and vice versa.
 */
@Component
@RequiredArgsConstructor
public class AllianceMapper implements Mapper<Alliance, AllianceDto> {

	private final ImageServiceImpl imageService; // Image service
	private final UserMapper userMapper; // User mapper

	/**
	 * Maps an Alliance entity to an AllianceDto object.
	 *
	 * @param entity The Alliance entity to map.
	 * @return The corresponding AllianceDto object.
	 */
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

	/**
	 * Maps an AllianceDto object to an Alliance entity.
	 *
	 * @param dto The AllianceDto object to map.
	 * @return The corresponding Alliance entity.
	 */
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
