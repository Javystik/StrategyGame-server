package com.zoi4erom.strategygame.spec;

import com.zoi4erom.strategygame.dto.search.Range;
import com.zoi4erom.strategygame.dto.search.StatisticSearch;
import com.zoi4erom.strategygame.dto.search.UserSearch;
import com.zoi4erom.strategygame.entity.User;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
public class UserSpecification implements Specification<User> {

	transient UserSearch userSearch;

	@Override
	public Predicate toPredicate(@NonNull Root<User> root, @NonNull CriteriaQuery<?> query,
	    @NonNull CriteriaBuilder criteriaBuilder) {
		Predicate predicate = criteriaBuilder.conjunction();

		if (userSearch != null) {
			predicate = addIdPredicate(predicate, root, criteriaBuilder);
			predicate = addUsernamePredicate(predicate, root, criteriaBuilder);
			predicate = addEmailPredicate(predicate, root, criteriaBuilder);
			predicate = addWinGamesPredicate(predicate, root, criteriaBuilder);
		}

		return predicate;
	}


	//Finds user by id
	private Predicate addIdPredicate(Predicate predicate, Root<User> root,
	    CriteriaBuilder criteriaBuilder) {
		if (userSearch.getId() != null) {
			return criteriaBuilder.and(predicate,
			    criteriaBuilder.equal(root.get("id"), userSearch.getId()));
		}
		return predicate;
	}

	// Finds users by username using a like operation with '%' wildcard both before and after the username
	private Predicate addUsernamePredicate(Predicate predicate, Root<User> root,
	    CriteriaBuilder criteriaBuilder) {
		if (userSearch.getUsername() != null && !userSearch.getUsername().isEmpty()) {
			return criteriaBuilder.and(predicate,
			    criteriaBuilder.like(root.get("username"),
				  "%" + userSearch.getUsername() + "%"));
		}
		return predicate;
	}

	// Finds users by email using a like operation with '%' wildcard both before and after the username
	private Predicate addEmailPredicate(Predicate predicate, Root<User> root,
	    CriteriaBuilder criteriaBuilder) {
		if (userSearch.getEmail() != null && !userSearch.getEmail().isEmpty()) {
			return criteriaBuilder.and(predicate,
			    criteriaBuilder.like(root.get("email"), "%" + userSearch.getEmail() + "%"));
		}
		return predicate;
	}

	/*
	 Search for users based on the number of games won.
	 If both minimum and maximum values are provided,
	 search is performed within that range.

	 If only the minimum value is provided,
	 search is performed from the specified minimum to the maximum available value.

	 If only the maximum value is provided,
	 search is performed from the minimum available value (0) to the specified maximum.
	*/
	private Predicate addWinGamesPredicate(Predicate predicate, Root<User> root,
	    CriteriaBuilder criteriaBuilder) {
		StatisticSearch statisticSearch = userSearch.getStatisticSearch();
		if (statisticSearch != null && statisticSearch.getWinGames() != null) {
			Range<Integer> winGamesRange = statisticSearch.getWinGames();
			if (winGamesRange.getFrom() != null && winGamesRange.getTo() != null) {
				return criteriaBuilder.and(predicate,
				    criteriaBuilder.between(root.get("statistic").get("winGames"),
					  winGamesRange.getFrom(), winGamesRange.getTo()));
			} else if (winGamesRange.getFrom() != null) {
				return criteriaBuilder.and(predicate,
				    criteriaBuilder.greaterThanOrEqualTo(
					  root.get("statistic").get("winGames"),
					  winGamesRange.getFrom()));
			} else if (winGamesRange.getTo() != null) {
				return criteriaBuilder.and(predicate,
				    criteriaBuilder.lessThanOrEqualTo(root.get("statistic").get("winGames"),
					  winGamesRange.getTo()));
			}
		}
		return predicate;
	}
}
