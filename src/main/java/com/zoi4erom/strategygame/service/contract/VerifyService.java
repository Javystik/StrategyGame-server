package com.zoi4erom.strategygame.service.contract;

public interface VerifyService {
	boolean sendVerifyCode(String username);

	boolean verifyRegistration(String username, String code);
}