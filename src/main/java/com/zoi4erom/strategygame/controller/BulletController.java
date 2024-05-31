package com.zoi4erom.strategygame.controller;

import com.zoi4erom.strategygame.dto.BulletDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/bullets")
@RequiredArgsConstructor
@CrossOrigin
public class BulletController {

	private final SimpMessagingTemplate messagingTemplate;

	@PostMapping("/game/bullet")
	public ResponseEntity<?> handleBulletMessage(@RequestBody BulletDto bulletDto) {
		bulletDto.setType("bulletDto");
		messagingTemplate.convertAndSend("/topic/game/" + bulletDto.getGameId(), bulletDto);
		return ResponseEntity.ok().build();
	}
}
