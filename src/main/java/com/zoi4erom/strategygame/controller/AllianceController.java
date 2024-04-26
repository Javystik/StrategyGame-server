package com.zoi4erom.strategygame.controller;

import com.zoi4erom.strategygame.dto.AllianceDto;
import com.zoi4erom.strategygame.service.AllianceService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/alliances")
@AllArgsConstructor
public class AllianceController {

	private final AllianceService allianceService;

	@PostMapping
	public void createAlliance(@RequestBody AllianceDto allianceDto) {
		allianceService.createAlliance(allianceDto);
	}

	@GetMapping
	public ResponseEntity<List<AllianceDto>> getAllAlliance() {
		var allAlliances = allianceService.getAllAlliance();

		return allAlliances.isEmpty() ? ResponseEntity.noContent().build()
		    : ResponseEntity.ok(allAlliances);
	}

	@GetMapping("/{id}")
	public ResponseEntity<AllianceDto> getAllianceById(@PathVariable Integer id) {
		return allianceService.getAllianceById(id)
		    .map(ResponseEntity::ok)
		    .orElse(ResponseEntity.notFound().build());
	}
}
