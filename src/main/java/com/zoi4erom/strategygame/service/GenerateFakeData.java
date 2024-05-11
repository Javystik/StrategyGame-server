package com.zoi4erom.strategygame.service;

import com.github.javafaker.Faker;
import com.zoi4erom.strategygame.dto.AuthRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GenerateFakeData {

	private final UserService userService;

	public void generateFakeAuthRequests(int count) {
		Faker faker = new Faker();

		for (int i = 0; i < count; i++) {
			String username = faker.name().username();
			String email = faker.internet().emailAddress();
			String password = faker.internet().password();

			userService.createUser(AuthRequest.builder()
			    .username(username)
			    .email(email)
			    .password(password)
			    .build());
		}
	}
}