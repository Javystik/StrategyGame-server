package com.zoi4erom.strategygame.dto.search;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class AllianceSearch {

	private String name;
	private Integer fromMembersCount;
	private Integer toMembersCount;
	private Integer fromTotalWins;
	private Integer toTotalWins;
}
