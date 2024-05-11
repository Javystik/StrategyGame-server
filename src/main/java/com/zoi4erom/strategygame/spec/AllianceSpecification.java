package com.zoi4erom.strategygame.spec;

import com.zoi4erom.strategygame.dto.search.AllianceSearch;
import com.zoi4erom.strategygame.entity.Alliance;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

@AllArgsConstructor
public class AllianceSpecification implements Specification<Alliance> {

	transient AllianceSearch allianceSearch;

	@Override
	public Predicate toPredicate(@NonNull Root<Alliance> root, @NonNull CriteriaQuery<?> query,
	    CriteriaBuilder criteriaBuilder) {
		Predicate predicate = criteriaBuilder.conjunction();

		if (allianceSearch != null) {
			predicate = idPredicate(root, criteriaBuilder, predicate);
			predicate = namePredicate(root, criteriaBuilder, predicate);
		}

		return predicate;
	}

	private Predicate idPredicate(Root<Alliance> root, CriteriaBuilder criteriaBuilder,
	    Predicate predicate) {
		if (allianceSearch.getId() != null) {
			predicate = criteriaBuilder.and(predicate,
			    criteriaBuilder.equal(root.get("id"), allianceSearch.getId()));
		}
		return predicate;
	}

	private Predicate namePredicate(Root<Alliance> root, CriteriaBuilder criteriaBuilder,
	    Predicate predicate) {
		if (allianceSearch.getName() != null) {
			predicate = criteriaBuilder.and(predicate,
			    criteriaBuilder.like(root.get("name"), "%" + allianceSearch.getName() + "%"));
		}
		return predicate;
	}
}
