package com.zoi4erom.strategygame.repository;

import com.zoi4erom.strategygame.entity.Alliance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Repository interface for managing Alliance entities.
 * Provides methods for interacting with the Alliance entity in the database.
 */
public interface AllianceRepository extends JpaRepository<Alliance, Long>,
    JpaSpecificationExecutor<Alliance> {

	/**
	 * Retrieves the count of members belonging to a specific alliance identified by its ID.
	 *
	 * @param allianceId the ID of the alliance for which the member count is calculated
	 * @return the count of members belonging to the alliance
	 */
	@Query("SELECT COUNT(u) FROM User u WHERE u.alliance.id = :allianceId")
	Integer memberAllianceCount(@Param("allianceId") Long allianceId);

	/**
	 * Calculates the total number of win games for all users belonging to a specific alliance identified by its ID.
	 *
	 * @param allianceId the ID of the alliance for which the total wins are calculated
	 * @return the total number of win games for users belonging to the alliance
	 */
	@Query("SELECT SUM(u.statistic.winGames) FROM User u WHERE u.alliance.id = :allianceId")
	Integer totalWins(@Param("allianceId") Long allianceId);
}
