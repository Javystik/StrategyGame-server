package com.zoi4erom.strategygame.service.impl;

import com.zoi4erom.strategygame.service.contract.VerificationCodeGenerator;
import java.util.Random;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * Implementation of the VerificationCodeGenerator interface for generating verification codes.
 */
@Component
@Slf4j
public class VerificationCodeGeneratorImpl implements VerificationCodeGenerator {

	private final Random random = new Random();

	/**
	 * Generates a verification code consisting of 6 digits.
	 *
	 * @return The generated verification code
	 */
	@Override
	public String generateVerificationCode() {
		log.info("Generating verification code");
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < 6; i++) {
			stringBuilder.append(random.nextInt(10));
		}
		String verificationCode = stringBuilder.toString();
		log.info("Generated verification code: {}", verificationCode);
		return verificationCode;
	}
}
