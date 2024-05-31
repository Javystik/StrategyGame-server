package com.zoi4erom.strategygame.repository;

import com.zoi4erom.strategygame.entity.UnitTemplate;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitTemplateRepository extends JpaRepository<UnitTemplate, Long> {
	Optional<UnitTemplate> findUnitTemplateByUnitType_Name(String unitTypeName);
}
