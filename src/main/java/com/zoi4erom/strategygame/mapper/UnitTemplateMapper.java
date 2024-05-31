package com.zoi4erom.strategygame.mapper;

import com.zoi4erom.strategygame.dto.UnitTemplateDto;
import com.zoi4erom.strategygame.entity.UnitTemplate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UnitTemplateMapper implements Mapper<UnitTemplate, UnitTemplateDto> {

	private final UnitTypeMapper unitTypeMapper;

	@Override
	public UnitTemplateDto toDto(UnitTemplate entity) {
		return UnitTemplateDto.builder()
		    .id(entity.getId())
		    .name(entity.getName())
		    .cost(entity.getCost())
		    .reload(entity.getReload())
		    .healthPoints(entity.getHealthPoints())
		    .speed(entity.getSpeed())
		    .damage(entity.getDamage())
		    .range(entity.getRange())
		    .unitType(unitTypeMapper.toDto(entity.getUnitType()))
		    .build();
	}

	@Override
	public UnitTemplate toEntity(UnitTemplateDto dto) {
		return UnitTemplate.builder()
		    .id(dto.getId())
		    .name(dto.getName())
		    .cost(dto.getCost())
		    .reload(dto.getReload())
		    .healthPoints(dto.getHealthPoints())
		    .speed(dto.getSpeed())
		    .damage(dto.getDamage())
		    .range(dto.getRange())
		    .unitType(unitTypeMapper.toEntity(dto.getUnitType()))
		    .build();
	}
}
