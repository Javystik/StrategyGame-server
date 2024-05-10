package com.zoi4erom.strategygame.mapper;

import com.zoi4erom.strategygame.dto.ArticleDto;
import com.zoi4erom.strategygame.entity.Article;
import com.zoi4erom.strategygame.service.contract.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ArticleMapper implements Mapper<Article, ArticleDto> {

	private final UserMapper userMapper;
	private final ImageService imageService;

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
