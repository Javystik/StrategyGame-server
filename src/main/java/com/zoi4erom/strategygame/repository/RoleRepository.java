package com.zoi4erom.strategygame.repository;

import com.zoi4erom.strategygame.entity.Role;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing Role entities. Provides methods for interacting with the Role
 * entity in the database.
 */
public interface RoleRepository extends JpaRepository<Role, Long> {

	/**
	 * Retrieves an optional Role entity by its name.
	 *
	 * @param roleName the name of the role to search for
	 * @return an optional containing the found Role entity, or empty if not found
	 */
	Optional<Role> findRoleByName(String roleName);
}
