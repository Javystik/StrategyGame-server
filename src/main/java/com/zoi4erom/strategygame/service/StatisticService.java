package com.zoi4erom.strategygame.service;

import com.zoi4erom.strategygame.dto.StatisticDto;
import com.zoi4erom.strategygame.entity.Statistic;
import com.zoi4erom.strategygame.mapper.StatisticMapper;
import com.zoi4erom.strategygame.repository.StatisticRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StatisticService {

	private final StatisticRepository statisticRepository;
	private final StatisticMapper statisticMapper;

	public StatisticDto createStatistic() {
		return statisticMapper.toDto(statisticRepository.save(new Statistic()));
	}
}
