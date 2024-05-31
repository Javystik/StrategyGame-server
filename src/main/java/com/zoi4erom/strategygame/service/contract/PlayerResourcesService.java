package com.zoi4erom.strategygame.service.contract;

import com.zoi4erom.strategygame.dto.PlayerResourcesDto;
import java.util.List;
import java.util.Optional;

public interface PlayerResourcesService {

	boolean createPlayerResources(Long userId, long gameId);

	Optional<PlayerResourcesDto> getPlayerResourcesByGameIdAndUserId(Long gameId, Long userId);

	boolean updatePlayerResources(PlayerResourcesDto playerResourcesDto);

	boolean batchUpdatePlayerResources(List<PlayerResourcesDto> playerResourcesDtos);
}
