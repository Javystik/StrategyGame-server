package com.zoi4erom.strategygame.mapper;

import com.zoi4erom.strategygame.dto.UnitDto;
import com.zoi4erom.strategygame.entity.Unit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UnitMapper implements Mapper<Unit, UnitDto> {

	private final UnitTemplateMapper unitTemplateMapper;

	@Override
	public UnitDto toDto(Unit entity) {
		return UnitDto.builder()
		    .id(entity.getId())
		    .currentHealthPoints(entity.getCurrentHealthPoints())
		    .isAlive(entity.isAlive())
		    .x(entity.getX())
		    .y(entity.getY())
		    .gameId(entity.getGame().getId())
		    .userId(entity.getUser().getId())
		    .userName(entity.getUser().getUsername())
		    .unitTemplateDto(unitTemplateMapper.toDto(entity.getUnitTemplate()))
		    .build();
	}

	@Override
	public Unit toEntity(UnitDto dto) {
		return Unit.builder()
		    .id(dto.getId())
		    .currentHealthPoints(dto.getCurrentHealthPoints())
		    .isAlive(dto.isAlive())
		    .x(dto.getX())
		    .y(dto.getY())
		    .build();
	}
}
