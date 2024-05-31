package com.zoi4erom.strategygame.controller;

import com.zoi4erom.strategygame.dto.UnitTemplateDto;
import com.zoi4erom.strategygame.service.contract.UnitTemplateService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/unitTemplates")
@RequiredArgsConstructor
@CrossOrigin
public class UnitTemplateController {

	private final UnitTemplateService unitTemplateService;

	@GetMapping
	public ResponseEntity<List<UnitTemplateDto>> getAllUnitTemplates() {
		var allUnitTemplate = unitTemplateService.getAllUnitTemplate();
		if (allUnitTemplate != null && !allUnitTemplate.isEmpty()) {
			return ResponseEntity.ok(allUnitTemplate);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
