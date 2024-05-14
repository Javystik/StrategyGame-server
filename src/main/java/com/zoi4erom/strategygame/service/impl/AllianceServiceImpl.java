package com.zoi4erom.strategygame.service.impl;

import com.zoi4erom.strategygame.dto.AllianceDto;
import com.zoi4erom.strategygame.dto.ClanCreateDto;
import com.zoi4erom.strategygame.dto.UpdateClanAvatarDto;
import com.zoi4erom.strategygame.dto.UserDto;
import com.zoi4erom.strategygame.dto.search.AllianceSearch;
import com.zoi4erom.strategygame.entity.Alliance;
import com.zoi4erom.strategygame.mapper.AllianceMapper;
import com.zoi4erom.strategygame.repository.AllianceRepository;
import com.zoi4erom.strategygame.service.contract.AllianceService;
import com.zoi4erom.strategygame.service.contract.ImageService;
import com.zoi4erom.strategygame.service.contract.UserService;
import com.zoi4erom.strategygame.service.impl.ImageServiceImpl.DefaultImagePatch;
import com.zoi4erom.strategygame.spec.AllianceSpecification;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


/**
 * Implementation of the alliance service. This service provides methods for creating, retrieving,
 * updating, and deleting alliances, as well as for changing the alliance avatar.
 */
@Service
@AllArgsConstructor
@Slf4j
public class AllianceServiceImpl implements AllianceService {

	private final AllianceRepository allianceRepository; // Alliance repository
	private final AllianceMapper allianceMapper; // Mapper for converting alliance objects
	private final UserService userService; // User service
	private final ImageService imageService; // Image service

	/**
	 * Creates a new alliance with the specified data and leader.
	 *
	 * @param clanCreateDto Data for creating the alliance
	 * @param leader        Leader of the alliance
	 * @return true if the alliance is created successfully, false otherwise
	 */
	@Override
	public boolean createAlliance(ClanCreateDto clanCreateDto, UserDto leader) {
		var user = userService.getUserEntityById(leader.getId()).orElseThrow();

		var alliance = Alliance.builder()
		    .name(clanCreateDto.getName())
		    .tag(clanCreateDto.getTag())
		    .leader(user)
		    .totalWins(leader.getStatisticDto().getWinGames())
		    .membersCount(1)
		    .build();

		Alliance savedAlliance = null;
		try {
			savedAlliance = allianceRepository.save(alliance);
		} catch (Exception e) {
			log.error("Error occurred while creating alliance: {}", e.getMessage());
			return false;
		}
		userService.updateUserAlliance(savedAlliance, user.getId());
		log.info("Alliance created successfully with id: {}", savedAlliance.getId());
		return true;
	}

	/**
	 * Retrieves a page of alliances based on the specified search criteria and pagination.
	 *
	 * @param allianceSearch Search criteria for alliances
	 * @param pageNo         Page number
	 * @param pageSize       Page size
	 * @return Page of alliances
	 */
	@Override
	public Page<AllianceDto> getAllAllianceBySpecificationAndPagination(
	    AllianceSearch allianceSearch, int pageNo, int pageSize) {
		log.info("Executing search query with specifications for alliance");
		AllianceSpecification allianceSpecification = new AllianceSpecification(allianceSearch);
		Sort sort = Sort.by(Sort.Direction.DESC, "totalWins");
		Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
		Page<Alliance> alliances = allianceRepository.findAll(allianceSpecification, pageable);

		alliances.forEach(alliance -> {
			Long allianceId = alliance.getId();
			Integer membersCount = allianceRepository.memberAllianceCount(allianceId);
			Integer totalWins = allianceRepository.totalWins(allianceId);

			alliance.setMembersCount(membersCount);
			alliance.setTotalWins(Objects.requireNonNullElse(totalWins, 0));
		});

		return alliances.map(allianceMapper::toDto);
	}

	/**
	 * Retrieves an alliance by the specified identifier and returns it as a DTO.
	 *
	 * @param id Identifier of the alliance
	 * @return Optional object containing the alliance DTO if found
	 */
	@Override
	public Optional<AllianceDto> getAllianceById(Long id) {
		log.info("Retrieving alliance with id: {}", id);
		return allianceRepository.findById(id)
		    .map(alliance -> {
			    Integer membersCount = allianceRepository.memberAllianceCount(id);
			    Integer totalWins = allianceRepository.totalWins(id);

			    alliance.setMembersCount(membersCount);
			    alliance.setTotalWins(totalWins);

			    return Optional.of(allianceMapper.toDto(alliance));
		    }).orElse(Optional.empty());
	}


	/**
	 * Retrieves an alliance by the specified identifier and returns it as an entity.
	 *
	 * @param id Identifier of the alliance
	 * @return Optional object containing the alliance entity if found
	 * @throws NoSuchElementException If the alliance with the specified identifier is not found
	 */
	@Override
	public Optional<Alliance> getAllianceEntityById(Long id) {
		log.info("Retrieving alliance entity with id: {}", id);
		var alliance = allianceRepository.findById(id).orElseThrow();

		Integer membersCount = allianceRepository.memberAllianceCount(id);
		Integer totalWins = allianceRepository.totalWins(id);

		alliance.setMembersCount(membersCount);
		alliance.setTotalWins(totalWins);

		return Optional.of(alliance);
	}

	/**
	 * Changes the avatar of the alliance with the specified user identifier.
	 *
	 * @param updateClanAvatarDto Object containing information about the alliance avatar change
	 * @param username            Username making the changes
	 * @return true if the alliance avatar is successfully changed, false otherwise
	 * @throws NoSuchElementException If the alliance with the specified identifier is not found
	 */
	@Override
	public boolean changeClanAvatar(UpdateClanAvatarDto updateClanAvatarDto, String username) {
		log.info("Changing clan avatar for clan with id: {} by user: {}",
		    updateClanAvatarDto.getClanId(), username);
		var alliance = getAllianceEntityById(updateClanAvatarDto.getClanId()).get();
		var user = userService.getUserByUsername(username).orElseThrow();

		if (alliance.getLeader().getId().equals(user.getId())) {
			alliance.setAvatarUrl(
			    imageService.saveImageBase64(updateClanAvatarDto.getBase64Image(),
				  alliance.getAvatarUrl(), DefaultImagePatch.CLAN_AVATAR));
			allianceRepository.save(alliance);
			log.info("Clan avatar changed successfully");
			return true;
		}
		log.warn("User {} is not the leader of clan with id: {}", username,
		    updateClanAvatarDto.getClanId());
		return false;
	}
}