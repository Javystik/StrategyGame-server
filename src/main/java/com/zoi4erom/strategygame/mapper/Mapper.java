package com.zoi4erom.strategygame.mapper;

public interface Mapper<E, D> {

	D toDto(E entity);

	E toEntity(D dto);
}
