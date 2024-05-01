package com.zoi4erom.strategygame.repository;

import com.zoi4erom.strategygame.entity.Alliance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AllianceRepository extends JpaRepository<Alliance, Integer> {

	@Query("SELECT COUNT(u) FROM User u WHERE u.alliance.id = :allianceId")
	int memberAllianceCount(@Param("allianceId") Integer id);

	@Query("SELECT SUM(u.statistic.winGames) FROM User u WHERE u.alliance.id = :allianceId")
	int totalWins(@Param("allianceId") Integer allianceId);
}
