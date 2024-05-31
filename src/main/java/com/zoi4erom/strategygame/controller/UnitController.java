package com.zoi4erom.strategygame.controller;

import com.zoi4erom.strategygame.dto.UnitDto;
import com.zoi4erom.strategygame.mapper.UnitMapper;
import com.zoi4erom.strategygame.service.contract.UnitService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/units")
@RequiredArgsConstructor
@CrossOrigin
public class UnitController {

	private final UnitService unitService;
	private final SimpMessagingTemplate messagingTemplate;
	private final UnitMapper unitMapper;

	@PostMapping
	public ResponseEntity<?> createUnit(@RequestBody UnitDto unitDto) {
		var unit = unitService.createUnit(unitDto).get();
		if (unit != null) {
			unit.setType("unitDto");
			messagingTemplate.convertAndSend("/topic/game/" + unitDto.getGameId(), unit);
			return ResponseEntity.ok(unit);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("/{gameId}")
	public ResponseEntity<?> getAllUnitsByGame(@PathVariable Long gameId) {
		var allUnitsByGameId = unitService.getAllUnitsByGameId(gameId);
		if (allUnitsByGameId != null && !allUnitsByGameId.isEmpty()) {
			return ResponseEntity.ok(allUnitsByGameId);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("/user/{userId}/game/{gameId}")
	public ResponseEntity<?> getAllUnitsByUserIdAndGameId(@PathVariable Long userId,
	    @PathVariable Long gameId) {
		var allUnitsByUserIdAndGameId = unitService.getAllUnitsByUserIdAndGameId(userId,
		    gameId);
		if (allUnitsByUserIdAndGameId != null && !allUnitsByUserIdAndGameId.isEmpty()) {
			return ResponseEntity.ok(allUnitsByUserIdAndGameId);
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	@PostMapping("/send-movement")
	public ResponseEntity<?> unitMovement(@RequestBody UnitDto unitDto){
		if (unitDto != null){
			unitDto.setType("unitDto");
			messagingTemplate.convertAndSend("/topic/game/" + unitDto.getGameId(), unitDto);
			return ResponseEntity.ok().build();
		}else {
			return ResponseEntity.badRequest().build();
		}
	}

	@PostMapping("/batch/send-movement")
	public ResponseEntity<?> unitMovement(@RequestBody List<UnitDto> unitDtos){
		if (unitDtos != null && !unitDtos.isEmpty()){
			boolean success = unitService.batchUpdateUnitCoordinates(unitDtos);
			if (success) {
				return ResponseEntity.ok().build();
			} else {
				return ResponseEntity.badRequest().build();
			}
		} else {
			return ResponseEntity.badRequest().build();
		}
	}
}