package com.zoi4erom.strategygame.service;

import com.zoi4erom.strategygame.dto.AllianceDto;
import com.zoi4erom.strategygame.dto.ClanCreateDto;
import com.zoi4erom.strategygame.dto.UpdateClanAvatarDto;
import com.zoi4erom.strategygame.dto.UserDto;
import com.zoi4erom.strategygame.entity.Alliance;
import com.zoi4erom.strategygame.mapper.AllianceMapper;
import com.zoi4erom.strategygame.repository.AllianceRepository;
import com.zoi4erom.strategygame.service.contract.ImageService;
import com.zoi4erom.strategygame.service.impl.ImageServiceImpl.DefaultImagePatch;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AllianceService {

	private final AllianceRepository allianceRepository;
	private final AllianceMapper allianceMapper;
	private final UserService userService;
	private final ImageService imageService;

	public void createAlliance(ClanCreateDto clanCreateDto, UserDto leader) {
		var user = userService.getUserEntityById(leader.getId()).orElseThrow();

		var alliance = Alliance.builder()
		    .name(clanCreateDto.getName())
		    .tag(clanCreateDto.getTag())
		    .leader(user)
		    .totalWins(leader.getStatisticDto().getWinGames())
		    .membersCount(1)
		    .build();

		var savedAlliance = allianceRepository.save(alliance);
		userService.updateUserAlliance(savedAlliance, user.getId());
	}


	public List<AllianceDto> getAllAlliance() {
		List<Alliance> alliances = allianceRepository.findAll();

		alliances.forEach(alliance -> {
			Long allianceId = alliance.getId();
			Integer membersCount = allianceRepository.memberAllianceCount(allianceId);
			Integer totalWins = allianceRepository.totalWins(allianceId);

			alliance.setMembersCount(membersCount);
			alliance.setTotalWins(Objects.requireNonNullElse(totalWins, 0));
		});

		return alliances.stream()
		    .map(allianceMapper::toDto)
		    .toList();
	}


	public Optional<AllianceDto> getAllianceById(Long id) {
		return allianceRepository.findById(id)
		    .map(alliance -> {
			    Integer membersCount = allianceRepository.memberAllianceCount(id);
			    Integer totalWins = allianceRepository.totalWins(id);

			    alliance.setMembersCount(membersCount);
			    alliance.setTotalWins(totalWins);

			    return Optional.of(allianceMapper.toDto(alliance));
		    }).orElse(Optional.empty());
	}

	public Alliance getAllianceEntityById(Long id) {
		var alliance = allianceRepository.findById(id).orElseThrow();

		Integer membersCount = allianceRepository.memberAllianceCount(id);
		Integer totalWins = allianceRepository.totalWins(id);

		alliance.setMembersCount(membersCount);
		alliance.setTotalWins(totalWins);

		return alliance;
	}

	public boolean changeClanAvatar(UpdateClanAvatarDto updateClanAvatarDto, String username) {
		var alliance = getAllianceEntityById(updateClanAvatarDto.getClanId());
		var user = userService.getUserByUsername(username).orElseThrow();

		if (alliance.getLeader().getId().equals(user.getId())) {
			alliance.setAvatarUrl(imageService.saveImageBase64(updateClanAvatarDto.getBase64Image(),
			    alliance.getAvatarUrl(), DefaultImagePatch.CLAN_AVATAR));
			allianceRepository.save(alliance);
			return true;
		}
		return false;
	}
}