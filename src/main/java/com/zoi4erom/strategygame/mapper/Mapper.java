package com.zoi4erom.strategygame.mapper;

/**
 * Interface defining the contract for mappers that convert entities to DTOs (Data Transfer Objects)
 * and vice versa.
 *
 * @param <E> The type of entity.
 * @param <D> The type of DTO.
 */
public interface Mapper<E, D> {

	/**
	 * Converts an entity to a DTO.
	 *
	 * @param entity The entity to be converted.
	 * @return The DTO representing the entity.
	 */
	D toDto(E entity);

	/**
	 * Converts a DTO to an entity.
	 *
	 * @param dto The DTO to be converted.
	 * @return The entity representing the DTO.
	 */
	E toEntity(D dto);
}
