package com.zoi4erom.strategygame.controller;

import com.zoi4erom.strategygame.service.contract.VerifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for handling verification related endpoints.
 */
@RestController
@RequestMapping("/verify")
@CrossOrigin
@RequiredArgsConstructor
public class VerifyController {

	private final VerifyService verifyService;

	/**
	 * Endpoint for verifying user registration with a verification code.
	 *
	 * @param code Verification code
	 * @return ResponseEntity indicating success or failure of the verification process
	 */
	@GetMapping("/registration")
	public ResponseEntity<?> verifyRegistration(@RequestParam String code) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (verifyService.verifyRegistration(authentication.getName(), code)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	/**
	 * Endpoint for sending a verification code to the authenticated user.
	 *
	 * @return ResponseEntity indicating success or failure of sending the verification code
	 */
	@GetMapping("/send-code")
	public ResponseEntity<?> sendVerifyCode() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (verifyService.sendVerifyCode(authentication.getName())) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
}