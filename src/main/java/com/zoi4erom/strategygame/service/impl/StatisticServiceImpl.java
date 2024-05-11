package com.zoi4erom.strategygame.service.impl;

import com.zoi4erom.strategygame.dto.StatisticDto;
import com.zoi4erom.strategygame.mapper.StatisticMapper;
import com.zoi4erom.strategygame.repository.StatisticRepository;
import com.zoi4erom.strategygame.service.contract.StatisticService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticServiceImpl implements StatisticService {

	private final StatisticRepository statisticRepository;
	private final StatisticMapper statisticMapper;

	@Override
	public void saveStatistic(StatisticDto statisticDto){
		statisticRepository.save(statisticMapper.toEntity(statisticDto));
	}
}
