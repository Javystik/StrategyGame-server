package com.zoi4erom.strategygame.dto.search;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSearch {

	private Long id;
	private String username;
	private String email;
	//private Range<LocalDateTime> createdAt;
	private StatisticSearch statisticSearch;
}
