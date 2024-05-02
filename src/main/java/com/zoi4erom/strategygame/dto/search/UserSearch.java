package com.zoi4erom.strategygame.dto.search;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserSearch {
	private String username;
	private String email;
	private Range<LocalDateTime> createdAt;
	private StatisticSearch statisticSearch;
}
