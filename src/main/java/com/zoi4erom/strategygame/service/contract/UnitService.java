package com.zoi4erom.strategygame.service.contract;

import com.zoi4erom.strategygame.dto.UnitDto;
import com.zoi4erom.strategygame.entity.Game;
import com.zoi4erom.strategygame.entity.User;
import java.util.List;
import java.util.Optional;

public interface UnitService {

	Optional<UnitDto> createUnit(UnitDto unitDto);

	List<UnitDto> getAllUnitsByGameId(Long gameId);

	List<UnitDto> getAllUnitsByUserIdAndGameId(Long userId, Long gameId);

	boolean updateUnitCoordinates(UnitDto unitDto);

	boolean batchUpdateUnitCoordinates(List<UnitDto> unitDtos);

	boolean createQueenUnit(User user, Game game);
}
