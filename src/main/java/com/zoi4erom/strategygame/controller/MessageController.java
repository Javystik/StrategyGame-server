package com.zoi4erom.strategygame.controller;

import com.zoi4erom.strategygame.dto.MessageDto;
import com.zoi4erom.strategygame.service.contract.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("messages")
@RequiredArgsConstructor
@CrossOrigin
public class MessageController {

	private final MessageService messageService;

	@PostMapping
	public ResponseEntity<?> createMessage(@RequestParam String text,
	    @RequestParam Long userId,
	    @RequestParam Long gameId) {
		MessageDto messageDto = MessageDto.builder()
		    .text(text)
		    .userId(userId)
		    .gameId(gameId)
		    .build();

		if (messageService.createMessage(messageDto)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}


	@GetMapping("/{gameId}")
	public ResponseEntity<?> getAllMessageByGameId(@PathVariable Long gameId) {
		var messageByGameId = messageService.getAllMessageByGameId(gameId);

		if (messageByGameId != null) {
			return ResponseEntity.ok(messageByGameId);
		} else {
			return ResponseEntity.noContent().build();
		}
	}
}
