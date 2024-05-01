package com.zoi4erom.strategygame.repository;

import com.zoi4erom.strategygame.entity.UnitType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitTypeRepository extends JpaRepository<UnitType, Integer> {
	Optional<UnitType> findUnitTypeByName(String unitTypeName);
}
