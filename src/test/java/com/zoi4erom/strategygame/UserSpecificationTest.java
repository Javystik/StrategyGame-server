package com.zoi4erom.strategygame;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.zoi4erom.strategygame.dto.UserDto;
import com.zoi4erom.strategygame.dto.search.Range;
import com.zoi4erom.strategygame.dto.search.StatisticSearch;
import com.zoi4erom.strategygame.dto.search.UserSearch;
import com.zoi4erom.strategygame.entity.Statistic;
import com.zoi4erom.strategygame.entity.User;
import com.zoi4erom.strategygame.service.UserService;
import com.zoi4erom.strategygame.spec.UserSpecification;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserSpecificationTest {

	private final UserService userService;

	@Autowired
	public UserSpecificationTest(UserService userService) {
		this.userService = userService;
	}

	@Test
	public void testToPredicate() {
		StatisticSearch statisticSearch = StatisticSearch.builder()
		    .build();

		UserSearch userSearch = UserSearch.builder()
		    .statisticSearch(statisticSearch)
		    .build();

		UserSpecification userSpecification = new UserSpecification(userSearch);
		List<UserDto> filteredUsers = userService.getAllUsersBySpecification(userSpecification);

		for (UserDto user: filteredUsers) {
			System.out.println(user);
		}
	}
}
