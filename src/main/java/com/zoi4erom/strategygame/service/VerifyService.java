package com.zoi4erom.strategygame.service;

import com.zoi4erom.strategygame.entity.User;
import com.zoi4erom.strategygame.mapper.UserMapper;
import com.zoi4erom.strategygame.service.contract.EmailService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VerifyService {

	private final UserService userService;
	private final EmailService emailService;
	private final UserMapper userMapper;

	public boolean sendVerifyCode(String email) {
		var user = userMapper.toEntity(userService.findUserByEmail(email).orElseThrow(
		    () -> new RuntimeException("Юзер за поштою: " + email + " не знайдено!")
		));

		user.setCodeDeathTime(LocalDateTime.now().plusSeconds(160));

		user.setVerificationCode(emailService.sendRegistrationConfirmation(user.getEmail()));

		userService.saveUser(user);
		return true;
	}

	public boolean verifyRegistration(String email, String code) {
		User user = userMapper.toEntity(userService.findUserByEmail(email)
		    .orElseThrow(
			  () -> new RuntimeException("Юзер за поштою: " + email + " не знайдено!")
		    ));

		if (user.getVerificationCode() != null && user.getVerificationCode().equals(code)
		    && user.getCodeDeathTime().isAfter(LocalDateTime.now())) {
			user.setIsVerified(true);
			userService.saveUser(user);
			return true;
		}
		return false;
	}

}