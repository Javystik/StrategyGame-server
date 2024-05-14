package com.zoi4erom.strategygame.repository;

import com.zoi4erom.strategygame.entity.UnitType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing UnitType entities. Provides methods for interacting with the
 * UnitType entity in the database.
 */
public interface UnitTypeRepository extends JpaRepository<UnitType, Long> {

	/**
	 * Finds a unit type by its name.
	 *
	 * @param unitTypeName The name of the unit type to find.
	 * @return An Optional containing the unit type if found, or empty otherwise.
	 */
	Optional<UnitType> findUnitTypeByName(String unitTypeName);
}

