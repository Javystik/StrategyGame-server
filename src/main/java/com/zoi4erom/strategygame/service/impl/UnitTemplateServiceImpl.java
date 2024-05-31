package com.zoi4erom.strategygame.service.impl;

import com.zoi4erom.strategygame.dto.UnitTemplateDto;
import com.zoi4erom.strategygame.entity.UnitTemplate;
import com.zoi4erom.strategygame.mapper.UnitTemplateMapper;
import com.zoi4erom.strategygame.repository.UnitTemplateRepository;
import com.zoi4erom.strategygame.service.contract.UnitTemplateService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnitTemplateServiceImpl implements UnitTemplateService {

	private final UnitTemplateRepository unitTemplateRepository;
	private final UnitTemplateMapper unitTemplateMapper;

	@Override
	public Optional<UnitTemplateDto> getUnitTemplateById(Long unitTemplateId) {
		return Optional.ofNullable(unitTemplateMapper.toDto(
		    unitTemplateRepository.findById(unitTemplateId).orElseThrow()));
	}

	@Override
	public Optional<UnitTemplate> getUnitTemplateEntityById(Long unitTemplateId) {
		return unitTemplateRepository.findById(unitTemplateId);
	}

	@Override
	public Optional<UnitTemplateDto> getUnitTemplateByUnitTypeName(String unitTypeName) {
		return Optional.ofNullable(unitTemplateMapper.toDto(
		    unitTemplateRepository.findUnitTemplateByUnitType_Name(unitTypeName)
			  .orElseThrow()));
	}

	@Override
	public Optional<UnitTemplate> getUnitTemplateEntityByUnitTypeName(String unitTypeName) {
		return unitTemplateRepository.findUnitTemplateByUnitType_Name(unitTypeName);
	}

	@Override
	public List<UnitTemplateDto> getAllUnitTemplate() {
		return unitTemplateRepository.findAll().stream()
		    .map(unitTemplateMapper::toDto)
		    .toList();
	}
}