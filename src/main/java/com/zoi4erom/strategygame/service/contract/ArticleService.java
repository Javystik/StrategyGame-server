package com.zoi4erom.strategygame.service.contract;

import com.zoi4erom.strategygame.dto.ArticleDto;
import com.zoi4erom.strategygame.dto.UserDto;
import com.zoi4erom.strategygame.entity.Article;
import java.util.List;
import java.util.Optional;

public interface ArticleService {

	void createArticle(ArticleDto articleDto, UserDto userDto);

	List<ArticleDto> getAllArticle();

	Optional<ArticleDto> getArticleById(Long id);

	Optional<ArticleDto> getArticleByName(String name);

	Optional<Article> getArticleEntityByName(String name);

	boolean updateArticle(ArticleDto updateArticleDto);

	boolean deleteArticle(String username, String articleName);
}
