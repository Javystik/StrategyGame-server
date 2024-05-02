package com.zoi4erom.strategygame.service;

import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class VerificationCodeGenerator {

	private final Random random = new Random();

	public String generateVerificationCode() {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < 6; i++) {
			stringBuilder.append(random.nextInt(10));
		}
		return stringBuilder.toString();
	}
}
