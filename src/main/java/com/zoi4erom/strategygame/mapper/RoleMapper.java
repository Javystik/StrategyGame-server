package com.zoi4erom.strategygame.mapper;

import com.zoi4erom.strategygame.dto.RoleDto;
import com.zoi4erom.strategygame.entity.Role;
import java.util.Collection;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;

/**
 * Mapper class responsible for converting Role entities to RoleDto objects and vice versa. It also
 * provides methods for converting collections of Role entities to collections of RoleDto objects,
 * and vice versa.
 */
@Component
public class RoleMapper implements Mapper<Role, RoleDto> {

	/**
	 * Converts a Role entity to a RoleDto object.
	 *
	 * @param entity The Role entity to be converted.
	 * @return The RoleDto object representing the entity.
	 */
	@Override
	public RoleDto toDto(Role entity) {
		return RoleDto.builder()
		    .id(entity.getId())
		    .name(entity.getName())
		    .build();
	}

	/**
	 * Converts a RoleDto object to a Role entity.
	 *
	 * @param dto The RoleDto object to be converted.
	 * @return The Role entity representing the DTO.
	 */
	@Override
	public Role toEntity(RoleDto dto) {
		return Role.builder()
		    .id(dto.getId())
		    .name(dto.getName())
		    .build();
	}

	/**
	 * Converts a collection of Role entities to a collection of RoleDto objects.
	 *
	 * @param entities The collection of Role entities to be converted.
	 * @return The collection of RoleDto objects representing the entities.
	 */
	public Collection<RoleDto> toDtoList(Collection<Role> entities) {
		return entities.stream()
		    .map(this::toDto)
		    .collect(Collectors.toList());
	}

	/**
	 * Converts a collection of RoleDto objects to a collection of Role entities.
	 *
	 * @param dtos The collection of RoleDto objects to be converted.
	 * @return The collection of Role entities representing the DTOs.
	 */
	public Collection<Role> toEntityList(Collection<RoleDto> dtos) {
		return dtos.stream()
		    .map(this::toEntity)
		    .collect(Collectors.toList());
	}
}
