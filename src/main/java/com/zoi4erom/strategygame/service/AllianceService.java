package com.zoi4erom.strategygame.service;

import com.zoi4erom.strategygame.dto.AllianceDto;
import com.zoi4erom.strategygame.mapper.AllianceMapper;
import com.zoi4erom.strategygame.repository.AllianceRepository;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
//TODO пофіксити костиль при створенні
public class AllianceService {

	private final AllianceRepository allianceRepository;
	private final AllianceMapper allianceMapper;

	public void createAlliance(AllianceDto allianceDto) {
		allianceDto.setMembersCount(0);
		allianceDto.setTotalWins(0);
		allianceRepository.save(allianceMapper.toEntity(allianceDto));
	}

	public List<AllianceDto> getAllAlliance() {
		return allianceRepository.findAll()
		    .stream()
		    .map(allianceMapper::toDto)
		    .toList();
	}

	public Optional<AllianceDto> getAllianceById(Integer id) {
		return allianceRepository.findById(id)
		    .map(allianceMapper::toDto);
	}
}
