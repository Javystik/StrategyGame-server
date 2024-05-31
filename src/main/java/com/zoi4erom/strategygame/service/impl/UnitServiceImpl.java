package com.zoi4erom.strategygame.service.impl;

import com.zoi4erom.strategygame.dto.UnitDto;
import com.zoi4erom.strategygame.entity.Game;
import com.zoi4erom.strategygame.entity.Unit;
import com.zoi4erom.strategygame.entity.UnitTemplate;
import com.zoi4erom.strategygame.entity.UnitType;
import com.zoi4erom.strategygame.entity.User;
import com.zoi4erom.strategygame.mapper.UnitMapper;
import com.zoi4erom.strategygame.repository.UnitRepository;
import com.zoi4erom.strategygame.service.contract.UnitService;
import com.zoi4erom.strategygame.service.contract.UnitTemplateService;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UnitServiceImpl implements UnitService {

	private final UnitRepository unitRepository;
	private final UnitMapper unitMapper;
	private final UnitTemplateService unitTemplateService;

	@Override
	public Optional<UnitDto> createUnit(UnitDto unitDto) {
		var unitTemplateDto = unitTemplateService.getUnitTemplateById(
		    unitDto.getUnitTemplateDto().getId()).orElseThrow();
		try {
			var unit = Unit.builder()
			    .currentHealthPoints(unitTemplateDto.getHealthPoints())
			    .isAlive(true)
			    .x(unitDto.getX())
			    .y(unitDto.getY())
			    .game(Game.builder().id(unitDto.getGameId()).build())
			    .user(User.builder().id(unitDto.getUserId()).build())
			    .unitTemplate(UnitTemplate.builder().id(unitTemplateDto.getId())
				  .unitType(UnitType.builder().id(unitTemplateDto.getUnitType().getId()).build()).build())
			    .build();

			var saved = unitRepository.save(unit);

			return getUnitById(saved.getId());
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public Optional<UnitDto> getUnitById(Long unitId) {
		return Optional.ofNullable(
		    unitMapper.toDto(unitRepository.findById(unitId).orElseThrow()));
	}

	@Override
	public List<UnitDto> getAllUnitsByGameId(Long gameId) {
		return unitRepository.findAllByGame_Id(gameId).stream()
		    .map(unitMapper::toDto)
		    .toList();
	}

	@Override
	public List<UnitDto> getAllUnitsByUserIdAndGameId(Long userId, Long gameId) {
		return unitRepository.findAllByUser_IdAndGame_Id(userId, gameId).stream()
		    .map(unitMapper::toDto)
		    .toList();
	}

	@Override
	public boolean updateUnitCoordinates(UnitDto unitDto) {
		try {
			Optional<Unit> optionalUnit = unitRepository.findById(unitDto.getId());
			if (optionalUnit.isPresent()) {
				Unit existingUnit = optionalUnit.get();
				existingUnit.setX(unitDto.getX());
				existingUnit.setY(unitDto.getY());
				unitRepository.save(existingUnit);
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}

	public boolean batchUpdateUnitCoordinates(List<UnitDto> unitDtos) {
		try {
			for (UnitDto unitDto : unitDtos) {
				Optional<Unit> optionalUnit = unitRepository.findById(unitDto.getId());
				if (optionalUnit.isPresent()) {
					Unit existingUnit = optionalUnit.get();
					existingUnit.setX(unitDto.getX());
					existingUnit.setY(unitDto.getY());
					if(unitDto.getCurrentHealthPoints() <= 0){
						existingUnit.setCurrentHealthPoints(0);
					}else {
						existingUnit.setCurrentHealthPoints(unitDto.getCurrentHealthPoints());
					}

					if(unitDto.getCurrentHealthPoints() <= 0){
						existingUnit.setAlive(false);
					}else {
						existingUnit.setAlive(true);
					}

					unitRepository.save(existingUnit);
				}
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	public boolean createQueenUnit(User user, Game game) {
		try {
			Random random = new Random();
			unitRepository.save(Unit.builder()
			    .user(user)
			    .game(game)
			    .unitTemplate(unitTemplateService.getUnitTemplateEntityById(1L).orElseThrow())
			    .currentHealthPoints(500)
			    .x(random.nextInt(21001))
			    .y(random.nextInt(21001))
			    .isAlive(true)
			    .build());
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
