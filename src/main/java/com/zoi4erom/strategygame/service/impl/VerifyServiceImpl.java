package com.zoi4erom.strategygame.service.impl;

import com.zoi4erom.strategygame.entity.User;
import com.zoi4erom.strategygame.service.contract.EmailService;
import com.zoi4erom.strategygame.service.contract.UserService;
import com.zoi4erom.strategygame.service.contract.VerifyService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class VerifyServiceImpl implements VerifyService {

	private final UserService userService;
	private final EmailService emailService;

	@Override
	public boolean sendVerifyCode(String username) {
		try {
			var userOptional = userService.getUserByUsername(username).orElseThrow();

			var user = userService.getUserEntityById(
			    userOptional.getId()).orElseThrow();

			user.setCodeDeathTime(LocalDateTime.now().plusSeconds(180));
			user.setVerificationCode(emailService.sendRegistrationConfirmation(user.getEmail()));

			userService.saveUser(user);
			log.info("Verification code sent successfully to user: {}", username);
			return true;
		} catch (Exception e) {
			log.error("Error sending verification code: {}", e.getMessage());
			return false;
		}
	}

	@Override
	public boolean verifyRegistration(String username, String code) {
		try {
			var userOptional = userService.getUserByUsername(username).orElseThrow();

			var user = userService.getUserEntityById(
			    userOptional.getId()).orElseThrow();

			if (isVerificationCodeValid(user, code)) {
				user.setIsVerified(true);
				userService.saveUser(user);
				log.info("User {} has been successfully verified.", username);
				return true;
			}
			return false;
		} catch (Exception e) {
			log.error("Error verifying registration: {}", e.getMessage());
			return false;
		}
	}

	private boolean isVerificationCodeValid(User user, String code) {
		return user.getVerificationCode() != null &&
		    user.getVerificationCode().equals(code) &&
		    user.getCodeDeathTime().isAfter(LocalDateTime.now());
	}

}