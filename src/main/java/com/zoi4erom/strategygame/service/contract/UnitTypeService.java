package com.zoi4erom.strategygame.service.contract;

import com.zoi4erom.strategygame.dto.UnitTypeDto;
import com.zoi4erom.strategygame.entity.UnitType;
import java.util.Optional;

public interface UnitTypeService {

	Optional<UnitTypeDto> getUnitTypeByName(String unitTypeName);

	Optional<UnitTypeDto> getUnitTypeById(Long unitTypeId);

	Optional<UnitType> getUnitTypeEntityByName(String unitTypeName);

	Optional<UnitType> getUnitTypeEntityById(Long unitTypeId);
}
