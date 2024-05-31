package com.zoi4erom.strategygame.service.contract;

import com.zoi4erom.strategygame.dto.UnitTemplateDto;
import com.zoi4erom.strategygame.entity.UnitTemplate;
import java.util.List;
import java.util.Optional;

public interface UnitTemplateService {

	Optional<UnitTemplateDto> getUnitTemplateById(Long unitTemplateId);

	Optional<UnitTemplate> getUnitTemplateEntityById(Long unitTemplateId);

	Optional<UnitTemplateDto> getUnitTemplateByUnitTypeName(String unitTypeName);

	Optional<UnitTemplate> getUnitTemplateEntityByUnitTypeName(String unitTypeName);

	List<UnitTemplateDto> getAllUnitTemplate();
}
