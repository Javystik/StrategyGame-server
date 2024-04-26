package com.zoi4erom.strategygame.service;

import com.zoi4erom.strategygame.dto.ArticleDto;
import com.zoi4erom.strategygame.mapper.ArticleMapper;
import com.zoi4erom.strategygame.repository.ArticleRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ArticleService {

	private final ArticleRepository articleRepository;
	private final ArticleMapper articleMapper;

	public void createArticle(ArticleDto articleDto) {
		articleRepository.save(articleMapper.toEntity(articleDto));
	}

	public List<ArticleDto> getAllArticle() {
		return articleRepository.findAll()
		    .stream()
		    .map(articleMapper::toDto)
		    .toList();
	}
}
