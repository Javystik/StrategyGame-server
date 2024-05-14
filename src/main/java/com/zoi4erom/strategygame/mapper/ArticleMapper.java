package com.zoi4erom.strategygame.mapper;

import com.zoi4erom.strategygame.dto.ArticleDto;
import com.zoi4erom.strategygame.entity.Article;
import com.zoi4erom.strategygame.service.contract.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Mapper class responsible for mapping Article entities to ArticleDto objects and vice versa.
 */
@Component
@RequiredArgsConstructor
public class ArticleMapper implements Mapper<Article, ArticleDto> {

	private final UserMapper userMapper; //User mapper
	private final ImageService imageService; //Image service

	/**
	 * Maps an Article entity to an ArticleDto object.
	 *
	 * @param entity The Article entity to map.
	 * @return The corresponding ArticleDto object.
	 */
	@Override
	public ArticleDto toDto(Article entity) {
		return ArticleDto.builder()
		    .id(entity.getId())
		    .name(entity.getName())
		    .imageBytes(imageService.loadImageBase64(entity.getImageUrl()))
		    .description(entity.getDescription())
		    .userDto(userMapper.toDto(entity.getUser()))
		    .build();
	}

	/**
	 * Maps an ArticleDto object to an Article entity.
	 *
	 * @param dto The ArticleDto object to map.
	 * @return The corresponding Article entity.
	 */
	@Override
	public Article toEntity(ArticleDto dto) {
		return Article.builder()
		    .id(dto.getId())
		    .name(dto.getName())
		    .imageUrl(imageService.saveImageBase64(dto.getImageBytes(), null, null))
		    .description(dto.getDescription())
		    .user(userMapper.toEntity(dto.getUserDto()))
		    .build();
	}
}
