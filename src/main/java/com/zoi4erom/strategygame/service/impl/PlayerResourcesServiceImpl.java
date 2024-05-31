package com.zoi4erom.strategygame.service.impl;

import com.zoi4erom.strategygame.dto.PlayerResourcesDto;
import com.zoi4erom.strategygame.entity.Game;
import com.zoi4erom.strategygame.entity.PlayerResource;
import com.zoi4erom.strategygame.entity.User;
import com.zoi4erom.strategygame.mapper.PlayerResourcesMapper;
import com.zoi4erom.strategygame.repository.PlayerResourcesRepository;
import com.zoi4erom.strategygame.service.contract.PlayerResourcesService;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayerResourcesServiceImpl implements PlayerResourcesService {

	private final PlayerResourcesRepository playerResourcesRepository;
	private final PlayerResourcesMapper playerResourcesMapper;

	@Override
	public boolean createPlayerResources(Long userId, long gameId) {
		try {
			playerResourcesRepository.save(PlayerResource.builder()
			    .user(User.builder().id(userId).build())
			    .game(Game.builder().id(gameId).build())
			    .resources(0)
			    .build());
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Optional<PlayerResourcesDto> getPlayerResourcesByGameIdAndUserId(Long gameId,
	    Long userId) {
		return Optional.ofNullable(playerResourcesMapper.toDto(
		    playerResourcesRepository.findByGame_IdAndUser_Id(gameId, userId).orElseThrow()));
	}

	public Optional<PlayerResource> getPlayerResourcesEntityByGameIdAndUserId(Long gameId,
	    Long userId) {
		return Optional.of(
		    playerResourcesRepository.findByGame_IdAndUser_Id(gameId, userId).orElseThrow());
	}

	@Override
	public boolean updatePlayerResources(PlayerResourcesDto playerResourcesDto) {
		var playerResources = playerResourcesRepository.findById(playerResourcesDto.getId()).orElseThrow();
		playerResources.setResources(playerResourcesDto.getResources());

		playerResourcesRepository.save(playerResources);
		return true;
	}

	@Override
	public boolean batchUpdatePlayerResources(List<PlayerResourcesDto> playerResourcesDtos) {
		try {
			for (PlayerResourcesDto dto : playerResourcesDtos) {
				Optional<PlayerResource> playerResourceOptional = getPlayerResourcesEntityByGameIdAndUserId(
				    dto.getGameId(), dto.getUserId());
				if (playerResourceOptional.isPresent()) {
					PlayerResource playerResource = playerResourceOptional.get();
					playerResource.setResources(dto.getResources());
					playerResourcesRepository.save(playerResource);
				} else {
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
