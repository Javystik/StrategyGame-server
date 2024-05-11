package com.zoi4erom.strategygame.repository;

import com.zoi4erom.strategygame.entity.Alliance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AllianceRepository extends JpaRepository<Alliance, Long>,
    JpaSpecificationExecutor<Alliance> {

	@Query("SELECT COUNT(u) FROM User u WHERE u.alliance.id = :allianceId")
	Integer memberAllianceCount(@Param("allianceId") Long id);

	@Query("SELECT SUM(u.statistic.winGames) FROM User u WHERE u.alliance.id = :allianceId")
	Integer totalWins(@Param("allianceId") Long allianceId);
}
