package com.zoi4erom.strategygame.repository;

import com.zoi4erom.strategygame.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing User entities. Provides methods for interacting with the User
 * entity in the database.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

	/**
	 * Finds a user by their username.
	 *
	 * @param username The username of the user to find.
	 * @return An Optional containing the user if found, or empty otherwise.
	 */
	Optional<User> findUserByUsername(String username);

	/**
	 * Finds a user by their email address.
	 *
	 * @param email The email address of the user to find.
	 * @return An Optional containing the user if found, or empty otherwise.
	 */
	Optional<User> findUserByEmail(String email);

	/**
	 * Finds all users belonging to a specific alliance.
	 *
	 * @param id The ID of the alliance.
	 * @return A list of users belonging to the alliance.
	 */
	List<User> findUserByAlliance_Id(Long id);
}
