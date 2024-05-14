package com.zoi4erom.strategygame.service.impl;

import com.zoi4erom.strategygame.entity.User;
import com.zoi4erom.strategygame.service.contract.EmailService;
import com.zoi4erom.strategygame.service.contract.UserService;
import com.zoi4erom.strategygame.service.contract.VerifyService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Implementation of the VerifyService interface for managing user verification.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class VerifyServiceImpl implements VerifyService {

	private final UserService userService; //User service
	private final EmailService emailService;//Email service

	/**
	 * Sends a verification code to the user's email address.
	 *
	 * @param username The username of the user
	 * @return true if the verification code was sent successfully, false otherwise
	 */
	@Override
	public boolean sendVerifyCode(String username) {
		try {
			var userOptional = userService.getUserByUsername(username).orElseThrow();

			var user = userService.getUserEntityById(
			    userOptional.getId()).orElseThrow();

			user.setCodeDeathTime(LocalDateTime.now().plusSeconds(180));
			String verificationCode = emailService.sendRegistrationConfirmation(
			    user.getEmail());
			user.setVerificationCode(verificationCode);

			userService.saveUser(user);
			log.info("Verification code sent successfully to user: {}", username);
			log.debug("Verification code sent: {}", verificationCode);
			return true;
		} catch (Exception e) {
			log.error("Error sending verification code to user: {}", username, e);
			return false;
		}
	}

	/**
	 * Verifies the registration of a user based on the provided verification code.
	 *
	 * @param username The username of the user
	 * @param code     The verification code
	 * @return true if the registration was successfully verified, false otherwise
	 */
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
			log.warn("Invalid verification code or expired for user: {}", username);
			return false;
		} catch (Exception e) {
			log.error("Error verifying registration for user: {}", username, e);
			return false;
		}
	}

	/**
	 * Checks if the provided verification code is valid and has not expired.
	 *
	 * @param user The user entity
	 * @param code The verification code
	 * @return true if the verification code is valid and not expired, false otherwise
	 */
	private boolean isVerificationCodeValid(User user, String code) {
		return user.getVerificationCode() != null &&
		    user.getVerificationCode().equals(code) &&
		    user.getCodeDeathTime().isAfter(LocalDateTime.now());
	}

}