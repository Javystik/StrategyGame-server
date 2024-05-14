package com.zoi4erom.strategygame.controller;

import com.zoi4erom.strategygame.dto.AllianceDto;
import com.zoi4erom.strategygame.dto.ClanCreateDto;
import com.zoi4erom.strategygame.dto.UpdateClanAvatarDto;
import com.zoi4erom.strategygame.dto.search.AllianceSearch;
import com.zoi4erom.strategygame.exception.UserNotFoundException;
import com.zoi4erom.strategygame.service.contract.AllianceService;
import com.zoi4erom.strategygame.service.contract.UserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller class for managing alliances within the game.
 */
@RestController
@RequestMapping("/alliances")
@AllArgsConstructor
@CrossOrigin
public class AllianceController {

	private final AllianceService allianceService;
	private final UserService userService;

	/**
	 * Endpoint for creating a new alliance.
	 *
	 * @param clanCreateDto DTO containing information for creating the alliance
	 * @return ResponseEntity indicating the success or failure of the operation
	 */
	@PostMapping
	public ResponseEntity<String> createAlliance(@RequestBody ClanCreateDto clanCreateDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		var user = userService.getUserByUsername(authentication.getName())
		    .orElseThrow(() -> new UserNotFoundException("username", authentication.getName()));
		if (allianceService.createAlliance(clanCreateDto, user)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.badRequest().build();
		}
	}

	/**
	 * Endpoint for retrieving all alliances based on specified criteria and pagination.
	 *
	 * @param allianceSearch DTO containing search criteria
	 * @param pageNo         Page number
	 * @param pageSize       Number of items per page
	 * @return ResponseEntity containing a page of alliance DTOs
	 */
	@PostMapping("/by-specification")
	public ResponseEntity<Page<AllianceDto>> getAllAlliance(
	    @RequestBody AllianceSearch allianceSearch,
	    @RequestParam(name = "pageNo", required = false, defaultValue = "0") int pageNo,
	    @RequestParam(name = "pageSize", required = false, defaultValue = "10") int pageSize) {
		var allAlliances = allianceService.getAllAllianceBySpecificationAndPagination(
		    allianceSearch, pageNo, pageSize);

		return allAlliances.isEmpty() ? ResponseEntity.noContent().build()
		    : ResponseEntity.ok(allAlliances);
	}

	/**
	 * Endpoint for retrieving an alliance by its ID.
	 *
	 * @param id ID of the alliance
	 * @return ResponseEntity containing the alliance DTO if found, or indicating not found
	 */
	@GetMapping("/{id}")
	public ResponseEntity<AllianceDto> getAllianceById(@PathVariable Long id) {
		return allianceService.getAllianceById(id)
		    .map(ResponseEntity::ok)
		    .orElse(ResponseEntity.notFound().build());
	}

	/**
	 * Endpoint for changing the avatar of a clan within an alliance.
	 *
	 * @param updateClanAvatarDto DTO containing information for updating the clan avatar
	 * @return ResponseEntity indicating the success or failure of the operation
	 */
	@PutMapping
	public ResponseEntity<?> changeClanAvatar(
	    @RequestBody UpdateClanAvatarDto updateClanAvatarDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		var clanChanged = allianceService.changeClanAvatar(updateClanAvatarDto,
		    authentication.getName());

		if (clanChanged) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.status(403).build();
		}
	}
}