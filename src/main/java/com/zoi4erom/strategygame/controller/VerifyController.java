package com.zoi4erom.strategygame.controller;

import com.zoi4erom.strategygame.dto.VerificationDto;
import com.zoi4erom.strategygame.service.VerifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/verify")
@CrossOrigin
@RequiredArgsConstructor
public class VerifyController {

	private final VerifyService verifyService;

	@PostMapping("/registration")
	public ResponseEntity<?> verifyRegistration(@RequestBody VerificationDto verificationDto) {
		if (verifyService.verifyRegistration(verificationDto.getEmail(), verificationDto.getCode())) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	@PostMapping("/send-code")
	public ResponseEntity<?> sendVerifyCode(@RequestBody VerificationDto verificationDto) {
		if (verifyService.sendVerifyCode(verificationDto.getEmail())) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
}