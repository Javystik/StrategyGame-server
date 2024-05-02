package com.zoi4erom.strategygame.mapper;

import com.zoi4erom.strategygame.dto.RoleDto;
import com.zoi4erom.strategygame.entity.Role;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper implements Mapper<Role, RoleDto> {

	@Override
	public RoleDto toDto(Role entity) {
		return RoleDto.builder()
		    .id(entity.getId())
		    .name(entity.getName())
		    .build();
	}

	@Override
	public Role toEntity(RoleDto dto) {
		return Role.builder()
		    .id(dto.getId())
		    .name(dto.getName())
		    .build();
	}
	public Collection<RoleDto> toDtoList(Collection<Role> entities) {
		return entities.stream()
		    .map(this::toDto)
		    .collect(Collectors.toList());
	}
	public Collection<Role> toEntityList(Collection<RoleDto> dtos) {
		return dtos.stream()
		    .map(this::toEntity)
		    .collect(Collectors.toList());
	}
}
