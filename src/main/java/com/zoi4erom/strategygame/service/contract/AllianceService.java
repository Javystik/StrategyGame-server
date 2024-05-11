package com.zoi4erom.strategygame.service.contract;


import com.zoi4erom.strategygame.dto.AllianceDto;
import com.zoi4erom.strategygame.dto.ClanCreateDto;
import com.zoi4erom.strategygame.dto.UpdateClanAvatarDto;
import com.zoi4erom.strategygame.dto.UserDto;
import com.zoi4erom.strategygame.dto.search.AllianceSearch;
import com.zoi4erom.strategygame.entity.Alliance;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface AllianceService {

	boolean createAlliance(ClanCreateDto clanCreateDto, UserDto leader);

	Page<AllianceDto> getAllAllianceBySpecificationAndPagination(AllianceSearch allianceSearch,
	    int pageNo, int pageSize);

	Optional<AllianceDto> getAllianceById(Long id);

	Optional<Alliance> getAllianceEntityById(Long id);

	boolean changeClanAvatar(UpdateClanAvatarDto updateClanAvatarDto, String username);
}
