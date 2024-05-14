package com.zoi4erom.strategygame.repository;

import com.zoi4erom.strategygame.entity.Statistic;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing Statistic entities. Provides methods for interacting with the
 * Statistic entity in the database.
 */
public interface StatisticRepository extends JpaRepository<Statistic, Long> {

}
