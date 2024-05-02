package com.zoi4erom.strategygame.spec;

import com.zoi4erom.strategygame.dto.search.Range;
import com.zoi4erom.strategygame.dto.search.StatisticSearch;
import com.zoi4erom.strategygame.dto.search.UserSearch;
import com.zoi4erom.strategygame.entity.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
public class UserSpecification implements Specification<User> {

	transient UserSearch userSearch;

	@Override
	public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query,
	    CriteriaBuilder criteriaBuilder) {
		Predicate predicate = criteriaBuilder.conjunction();

		if (userSearch.getUsername() != null) {
			predicate = criteriaBuilder.and(predicate,
			    criteriaBuilder.like(root.get("username"),
				  "%" + userSearch.getUsername() + "%"));
		}

		if (userSearch.getEmail() != null) {
			predicate = criteriaBuilder.and(predicate,
			    criteriaBuilder.like(root.get("email"), "%" + userSearch.getEmail() + "%"));
		}

		if (userSearch.getCreatedAt() != null) {
			LocalDateTime fromCreatedAt = userSearch.getCreatedAt().getFrom();
			LocalDateTime toCreatedAt = userSearch.getCreatedAt().getTo();
			if (fromCreatedAt != null && toCreatedAt != null) {
				predicate = criteriaBuilder.and(predicate,
				    criteriaBuilder.between(root.get("createdAt"), fromCreatedAt,
					  toCreatedAt));
			} else if (fromCreatedAt != null) {
				predicate = criteriaBuilder.and(predicate,
				    criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"),
					  fromCreatedAt),
				    criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"),
					  LocalDateTime.now()));
			}
		}

		StatisticSearch statisticSearch = userSearch.getStatisticSearch();
		if (statisticSearch != null) {
			Range<Integer> playerGamesRange = statisticSearch.getPlayerGames();
			if (playerGamesRange != null) {
				Integer fromPlayerGames = playerGamesRange.getFrom();
				Integer toPlayerGames = playerGamesRange.getTo();
				if (fromPlayerGames != null && toPlayerGames != null) {
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(
					    root.get("statistic").get("playerGames"), fromPlayerGames,
					    toPlayerGames));
				} else if (fromPlayerGames != null) {
					predicate = criteriaBuilder.and(predicate,
					    criteriaBuilder.greaterThanOrEqualTo(
						  root.get("statistic").get("playerGames"),
						  fromPlayerGames));
				}
			}

			Range<Integer> winGamesRange = statisticSearch.getWinGames();
			if (winGamesRange != null) {
				Integer fromWinGames = winGamesRange.getFrom();
				Integer toWinGames = winGamesRange.getTo();
				if (fromWinGames != null && toWinGames != null) {
					predicate = criteriaBuilder.and(predicate,
					    criteriaBuilder.between(root.get("statistic").get("winGames"),
						  fromWinGames, toWinGames));
				} else if (fromWinGames != null) {
					predicate = criteriaBuilder.and(predicate,
					    criteriaBuilder.greaterThanOrEqualTo(
						  root.get("statistic").get("winGames"), fromWinGames));
				}
			}

			Range<Integer> enemyUnitsKilledRange = statisticSearch.getEnemyUnitsKilled();
			if (enemyUnitsKilledRange != null) {
				Integer fromEnemyUnitsKilled = enemyUnitsKilledRange.getFrom();
				Integer toEnemyUnitsKilled = enemyUnitsKilledRange.getTo();
				if (fromEnemyUnitsKilled != null && toEnemyUnitsKilled != null) {
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(
					    root.get("statistic").get("enemyUnitsKilled"),
					    fromEnemyUnitsKilled, toEnemyUnitsKilled));
				} else if (fromEnemyUnitsKilled != null) {
					predicate = criteriaBuilder.and(predicate,
					    criteriaBuilder.greaterThanOrEqualTo(
						  root.get("statistic").get("enemyUnitsKilled"),
						  fromEnemyUnitsKilled));
				}
			}

			Range<Integer> unitsDeathsRange = statisticSearch.getUnitsDeaths();
			if (unitsDeathsRange != null) {
				Integer fromUnitsDeaths = unitsDeathsRange.getFrom();
				Integer toUnitsDeaths = unitsDeathsRange.getTo();
				if (fromUnitsDeaths != null && toUnitsDeaths != null) {
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(
					    root.get("statistic").get("unitsDeaths"), fromUnitsDeaths,
					    toUnitsDeaths));
				} else if (fromUnitsDeaths != null) {
					predicate = criteriaBuilder.and(predicate,
					    criteriaBuilder.greaterThanOrEqualTo(
						  root.get("statistic").get("unitsDeaths"),
						  fromUnitsDeaths));
				}
			}

			Range<Integer> territoriesCapturedRange = statisticSearch.getTerritoriesCaptured();
			if (territoriesCapturedRange != null) {
				Integer fromTerritoriesCaptured = territoriesCapturedRange.getFrom();
				Integer toTerritoriesCaptured = territoriesCapturedRange.getTo();
				if (fromTerritoriesCaptured != null && toTerritoriesCaptured != null) {
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(
					    root.get("statistic").get("territoriesCaptured"),
					    fromTerritoriesCaptured, toTerritoriesCaptured));
				} else if (fromTerritoriesCaptured != null) {
					predicate = criteriaBuilder.and(predicate,
					    criteriaBuilder.greaterThanOrEqualTo(
						  root.get("statistic").get("territoriesCaptured"),
						  fromTerritoriesCaptured));
				}
			}

			Range<Integer> territoriesLostRange = statisticSearch.getTerritoriesLost();
			if (territoriesLostRange != null) {
				Integer fromTerritoriesLost = territoriesLostRange.getFrom();
				Integer toTerritoriesLost = territoriesLostRange.getTo();
				if (fromTerritoriesLost != null && toTerritoriesLost != null) {
					predicate = criteriaBuilder.and(predicate, criteriaBuilder.between(
					    root.get("statistic").get("territoriesLost"),
					    fromTerritoriesLost, toTerritoriesLost));
				} else if (fromTerritoriesLost != null) {
					predicate = criteriaBuilder.and(predicate,
					    criteriaBuilder.greaterThanOrEqualTo(
						  root.get("statistic").get("territoriesLost"),
						  fromTerritoriesLost));
				}
			}
		}
		return predicate;
	}
}
