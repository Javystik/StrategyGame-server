package com.zoi4erom.strategygame.service.contract;

public interface EmailService {
	String sendRegistrationConfirmation(String to);
	String generateVerificationCode();
}
