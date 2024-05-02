package com.zoi4erom.strategygame.service;

import com.zoi4erom.strategygame.entity.User;
import com.zoi4erom.strategygame.exception.UserNotFoundException;
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
		    () -> new UserNotFoundException("email", email)
		));

		user.setCodeDeathTime(LocalDateTime.now().plusSeconds(180));
		user.setVerificationCode(emailService.sendRegistrationConfirmation(user.getEmail()));

		userService.saveUser(user);
		return true;
	}

	public boolean verifyRegistration(String email, String code) {
		User user = userMapper.toEntity(userService.findUserByEmail(email)
		    .orElseThrow(
			  () -> new UserNotFoundException("email", email)
		    ));

		if (isVerificationCodeValid(user, code)) {
			user.setIsVerified(true);
			userService.saveUser(user);
			return true;
		}
		return false;
	}

	private boolean isVerificationCodeValid(User user, String code) {
		return user.getVerificationCode() != null &&
		    user.getVerificationCode().equals(code) &&
		    user.getCodeDeathTime().isAfter(LocalDateTime.now());
	}

}