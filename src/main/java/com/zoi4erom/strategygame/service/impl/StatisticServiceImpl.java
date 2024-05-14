package com.zoi4erom.strategygame.service.impl;

import com.zoi4erom.strategygame.dto.StatisticDto;
import com.zoi4erom.strategygame.mapper.StatisticMapper;
import com.zoi4erom.strategygame.repository.StatisticRepository;
import com.zoi4erom.strategygame.service.contract.StatisticService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Implementation of the interface responsible for managing user statistics. This implementation
 * provides methods for saving user statistics to the database.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticServiceImpl implements StatisticService {

	private final StatisticRepository statisticRepository; //Statistic repository
	private final StatisticMapper statisticMapper; // Statistic mapper

	/**
	 * Saves the provided statistic data to the database.
	 *
	 * @param statisticDto The DTO containing the statistic data to be saved
	 * @throws RuntimeException If an error occurs while saving the statistic
	 */
	@Override
	public void saveStatistic(StatisticDto statisticDto) {
		log.info("Saving statistic for user: {}", statisticDto.getId());
		try {
			statisticRepository.save(statisticMapper.toEntity(statisticDto));
			log.info("Statistic saved successfully");
		} catch (Exception e) {
			log.error("Error occurred while saving statistic: {}", e.getMessage());
			throw new RuntimeException(e);
		}
	}
}
