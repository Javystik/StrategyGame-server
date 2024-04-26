package com.zoi4erom.strategygame.mapper;

import com.zoi4erom.strategygame.dto.ArticleDto;
import com.zoi4erom.strategygame.entity.Article;
import org.springframework.stereotype.Component;

@Component
public class ArticleMapper implements Mapper<Article, ArticleDto>{

	@Override
	public ArticleDto toDto(Article entity) {
		return ArticleDto.builder()
		    .id(entity.getId())
		    .name(entity.getName())
		    .description(entity.getDescription())
		    .build();
	}

	@Override
	public Article toEntity(ArticleDto dto) {
		return Article.builder()
		    .id(dto.getId())
		    .name(dto.getName())
		    .description(dto.getDescription())
		    .build();
	}
}
