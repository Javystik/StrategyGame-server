package com.zoi4erom.strategygame.spec;

import com.zoi4erom.strategygame.dto.search.AllianceSearch;
import com.zoi4erom.strategygame.entity.Alliance;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
public class AllianceSpecification implements Specification<Alliance> {

	transient AllianceSearch allianceSearch;

	@Override
	public Predicate toPredicate(Root<Alliance> root, CriteriaQuery<?> query,
	    CriteriaBuilder criteriaBuilder) {
		Predicate predicate = criteriaBuilder.conjunction();

		if (allianceSearch.getName() != null && !allianceSearch.getName().isEmpty()) {
			predicate = criteriaBuilder.and(predicate,
			    criteriaBuilder.equal(root.get("name"), allianceSearch.getName()));
		}

		if (allianceSearch.getFromMembersCount() != null) {
			predicate = criteriaBuilder.and(predicate,
			    criteriaBuilder.greaterThanOrEqualTo(root.get("membersCount"),
				  allianceSearch.getFromMembersCount()));
		}

		if (allianceSearch.getToMembersCount() != null) {
			predicate = criteriaBuilder.and(predicate,
			    criteriaBuilder.lessThanOrEqualTo(root.get("membersCount"),
				  allianceSearch.getToMembersCount()));
		}

		if (allianceSearch.getFromTotalWins() != null) {
			predicate = criteriaBuilder.and(predicate,
			    criteriaBuilder.greaterThanOrEqualTo(root.get("totalWins"),
				  allianceSearch.getFromTotalWins()));
		}

		if (allianceSearch.getToTotalWins() != null) {
			predicate = criteriaBuilder.and(predicate,
			    criteriaBuilder.lessThanOrEqualTo(root.get("totalWins"),
				  allianceSearch.getToTotalWins()));
		}

		return predicate;
	}
}
