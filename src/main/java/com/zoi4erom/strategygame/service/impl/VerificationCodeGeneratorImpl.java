package com.zoi4erom.strategygame.service.impl;

import com.zoi4erom.strategygame.service.contract.VerificationCodeGenerator;
import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class VerificationCodeGeneratorImpl implements VerificationCodeGenerator {

	private final Random random = new Random();

	@Override
	public String generateVerificationCode() {
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < 6; i++) {
			stringBuilder.append(random.nextInt(10));
		}
		return stringBuilder.toString();
	}
}
