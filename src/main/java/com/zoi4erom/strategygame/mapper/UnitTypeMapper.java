package com.zoi4erom.strategygame.mapper;

import com.zoi4erom.strategygame.dto.UnitTypeDto;
import com.zoi4erom.strategygame.entity.UnitType;
import org.springframework.stereotype.Component;

@Component
public class UnitTypeMapper implements Mapper<UnitType, UnitTypeDto> {

	@Override
	public UnitTypeDto toDto(UnitType entity) {
		return UnitTypeDto.builder()
		    .id(entity.getId())
		    .name(entity.getName())
		    .size(entity.getSize())
		    .build();
	}

	@Override
	public UnitType toEntity(UnitTypeDto dto) {
		return UnitType.builder()
		    .id(dto.getId())
		    .name(dto.getName())
		    .size(dto.getSize())
		    .build();
	}
}
