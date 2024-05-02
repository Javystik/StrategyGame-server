package com.zoi4erom.strategygame;

import static org.junit.jupiter.api.Assertions.assertTrue;

import com.zoi4erom.strategygame.service.impl.EmailServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class StrategyGameApplicationTests {

	@Autowired
	private EmailServiceImpl emailServiceImpl;

	@Test
	void sendSimpleMessageTest() {
		String to = "vladik.miroi@gmail.com";

		emailServiceImpl.sendRegistrationConfirmation(to);

		assertTrue(true);
	}
}