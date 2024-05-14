package com.zoi4erom.strategygame.repository;

import com.zoi4erom.strategygame.entity.Unit;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing Unit entities. Provides methods for interacting with the Unit
 * entity in the database.
 */
public interface UnitRepository extends JpaRepository<Unit, Long> {

	/**
	 * Finds a unit by its name.
	 *
	 * @param unitName The name of the unit to find.
	 * @return An Optional containing the unit if found, or empty otherwise.
	 */
	Optional<Unit> findUnitByName(String unitName);
}
