package com.zoi4erom.strategygame.service.impl;

import com.zoi4erom.strategygame.dto.UnitTypeDto;
import com.zoi4erom.strategygame.entity.UnitType;
import com.zoi4erom.strategygame.mapper.UnitTypeMapper;
import com.zoi4erom.strategygame.repository.UnitTypeRepository;
import com.zoi4erom.strategygame.service.contract.UnitTypeService;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnitTypeServiceImpl implements UnitTypeService {

	private final UnitTypeRepository unitTypeRepository;
	private final UnitTypeMapper unitTypeMapper;

	@Override
	public Optional<UnitTypeDto> getUnitTypeByName(String unitTypeName) {
		return Optional.ofNullable(
		    unitTypeMapper.toDto(
			  unitTypeRepository.findUnitTypeByName(unitTypeName).orElseThrow()));
	}

	@Override
	public Optional<UnitTypeDto> getUnitTypeById(Long unitTypeId) {
		return Optional.ofNullable(
		    unitTypeMapper.toDto(unitTypeRepository.findById(unitTypeId).orElseThrow()));
	}

	@Override
	public Optional<UnitType> getUnitTypeEntityByName(String unitTypeName) {
		return unitTypeRepository.findUnitTypeByName(unitTypeName);
	}

	@Override
	public Optional<UnitType> getUnitTypeEntityById(Long unitTypeId) {
		return unitTypeRepository.findById(unitTypeId);
	}
}
