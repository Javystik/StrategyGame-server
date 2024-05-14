package com.zoi4erom.strategygame.spec;

import com.zoi4erom.strategygame.dto.search.AllianceSearch;
import com.zoi4erom.strategygame.entity.Alliance;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;

/**
 * Class representing a specification for searching alliances in the database. This class is used to
 * create search conditions for alliances based on various criteria, such as identifier and name.
 */
@AllArgsConstructor
@Slf4j
public class AllianceSpecification implements Specification<Alliance> {

	transient AllianceSearch allianceSearch; // Alliance search parameters

	/**
	 * Class representing a specification for searching alliances in the database. This class is
	 * used to create search conditions for alliances based on various criteria, such as
	 * identifier and name.
	 */
	@Override
	public Predicate toPredicate(@NonNull Root<Alliance> root, @NonNull CriteriaQuery<?> query,
	    CriteriaBuilder criteriaBuilder) {
		Predicate predicate = criteriaBuilder.conjunction();

		if (allianceSearch != null) {
			predicate = idPredicate(root, criteriaBuilder, predicate);
			predicate = namePredicate(root, criteriaBuilder, predicate);
		}

		log.info("Full predicate: {}", predicate);

		return predicate;
	}

	// Adds a condition for searching an alliance by identifier
	private Predicate idPredicate(Root<Alliance> root, CriteriaBuilder criteriaBuilder,
	    Predicate predicate) {
		if (allianceSearch.getId() != null) {
			predicate = criteriaBuilder.and(predicate,
			    criteriaBuilder.equal(root.get("id"), allianceSearch.getId()));
		}
		return predicate;
	}

	// Adds a condition for searching an alliance by name
	private Predicate namePredicate(Root<Alliance> root, CriteriaBuilder criteriaBuilder,
	    Predicate predicate) {
		if (allianceSearch.getName() != null) {
			predicate = criteriaBuilder.and(predicate,
			    criteriaBuilder.like(root.get("name"), "%" + allianceSearch.getName() + "%"));
		}
		return predicate;
	}
}
