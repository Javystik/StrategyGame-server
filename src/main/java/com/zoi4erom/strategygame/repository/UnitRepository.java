package com.zoi4erom.strategygame.repository;

import com.zoi4erom.strategygame.entity.Unit;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UnitRepository extends JpaRepository<Unit, Integer> {
	Optional<Unit> findUnitByName(String unitName);
}
