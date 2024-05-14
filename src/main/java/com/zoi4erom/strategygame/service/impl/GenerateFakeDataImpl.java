package com.zoi4erom.strategygame.service.impl;

import com.github.javafaker.Faker;
import com.zoi4erom.strategygame.dto.AuthRequest;
import com.zoi4erom.strategygame.service.contract.GenerateFakeData;
import com.zoi4erom.strategygame.service.contract.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Implementation of a component responsible for generating fake data for authentication requests.
 * It utilizes the JavaFaker library to generate fake usernames, emails, and passwords.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class GenerateFakeDataImpl implements GenerateFakeData {

	private final UserService userService; // Service for user operations

	/**
	 * Generates the specified number of fake authentication requests.
	 *
	 * @param count The number of fake authentication requests to generate
	 */
	@Override
	public void generateFakeAuthRequests(int count) {
		log.info("Generating {} fake authentication requests", count);
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
			log.info("Fake authentication request generated: {}", username);
		}
		log.info("Fake authentication requests generation completed");
	}
}
