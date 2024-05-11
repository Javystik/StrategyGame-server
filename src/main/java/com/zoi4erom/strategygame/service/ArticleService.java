package com.zoi4erom.strategygame.service;

import com.zoi4erom.strategygame.dto.ArticleDto;
import com.zoi4erom.strategygame.dto.UserDto;
import com.zoi4erom.strategygame.entity.Article;
import com.zoi4erom.strategygame.mapper.ArticleMapper;
import com.zoi4erom.strategygame.mapper.UserMapper;
import com.zoi4erom.strategygame.repository.ArticleRepository;
import com.zoi4erom.strategygame.service.contract.ImageService;
import com.zoi4erom.strategygame.service.impl.ImageServiceImpl.DefaultImagePatch;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ArticleService {

	private final ArticleRepository articleRepository;
	private final ArticleMapper articleMapper;
	private final UserService userService;
	private final UserMapper userMapper;
	private final ImageService imageService;

	public void createArticle(ArticleDto articleDto, UserDto userDto) {
		articleDto.setUserDto(userDto);
		articleRepository.save(articleMapper.toEntity(articleDto));
	}

	public List<ArticleDto> getAllArticle() {
		Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
		return articleRepository.findAll(sort)
		    .stream()
		    .map(articleMapper::toDto)
		    .toList();
	}

	public Optional<ArticleDto> getArticleById(Long id) {
		return articleRepository.findById(id)
		    .map(articleMapper::toDto);
	}

	public Optional<ArticleDto> getArticleByName(String name) {
		return articleRepository.findArticleByName(name)
		    .map(articleMapper::toDto);
	}
	public Optional<Article> getArticleEntityByName(String name) {
		return articleRepository.findArticleByName(name);
	}

	public boolean updateArticle(ArticleDto updateArticleDto) {
		Article article = getArticleEntityByName(updateArticleDto.getName())
		    .orElseThrow(() -> new EntityNotFoundException("Article not found"));

		if (updateArticleDto.getImageBytes() != null) {
			article.setImageUrl(imageService.saveImageBase64(updateArticleDto.getImageBytes(),
			    article.getImageUrl(), DefaultImagePatch.ARTICLE_AVATAR));
		}
		if (updateArticleDto.getDescription() != null) {
			article.setDescription(updateArticleDto.getDescription());
		}
		articleRepository.save(article);
		return true;
	}

	public boolean deleteArticle(String username, String articleName) {
		var userByUsername = userMapper.toEntity(
		    userService.getUserByUsername(username).orElseThrow());
		var articleById = getArticleByName(articleName)
		    .map(articleMapper::toEntity).orElseThrow();

		if (articleById.getUser().getId().equals(userByUsername.getId())) {
			articleRepository.deleteById(articleById.getId());
			return true;
		}
		return false;
	}
}
