package com.zoi4erom.strategygame.service.impl;

import com.zoi4erom.strategygame.dto.ArticleDto;
import com.zoi4erom.strategygame.dto.UserDto;
import com.zoi4erom.strategygame.entity.Article;
import com.zoi4erom.strategygame.mapper.ArticleMapper;
import com.zoi4erom.strategygame.mapper.UserMapper;
import com.zoi4erom.strategygame.repository.ArticleRepository;
import com.zoi4erom.strategygame.service.contract.ArticleService;
import com.zoi4erom.strategygame.service.contract.ImageService;
import com.zoi4erom.strategygame.service.contract.UserService;
import com.zoi4erom.strategygame.service.impl.ImageServiceImpl.DefaultImagePatch;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


/**
 * Implementation of the article service. This service provides methods for creating, retrieving,
 * updating, and deleting articles.
 */
@Service
@AllArgsConstructor
@Slf4j
public class ArticleServiceImpl implements ArticleService {

	private final ArticleRepository articleRepository; // Article repository
	private final ArticleMapper articleMapper; // Mapper for converting article objects
	private final UserService userService; // User service
	private final UserMapper userMapper; // Mapper for converting user objects
	private final ImageService imageService; // Image service

	/**
	 * Creates a new article with the specified data and author.
	 *
	 * @param articleDto Data for creating the article
	 * @param userDto    Author of the article
	 */
	@Override
	public void createArticle(ArticleDto articleDto, UserDto userDto) {
		log.info("Creating article: {} by user: {}", articleDto.getName(),
		    userDto.getUsername());
		articleDto.setUserDto(userDto);
		try {
			articleRepository.save(articleMapper.toEntity(articleDto));
			log.info("Article created successfully");
		} catch (Exception e) {
			log.error("Error occurred while creating article: {}", e.getMessage());
		}
	}

	/**
	 * Retrieves all articles, ordered by creation date in descending order.
	 *
	 * @return List of article DTOs
	 */
	@Override
	public List<ArticleDto> getAllArticle() {
		log.info("Retrieving all articles");
		Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
		return articleRepository.findAll(sort)
		    .stream()
		    .map(articleMapper::toDto)
		    .toList();
	}

	/**
	 * Retrieves an article by the specified identifier as a DTO.
	 *
	 * @param id Identifier of the article
	 * @return Optional article DTO object
	 */
	@Override
	public Optional<ArticleDto> getArticleById(Long id) {
		log.info("Retrieving article with id: {}", id);
		return articleRepository.findById(id)
		    .map(articleMapper::toDto);
	}

	/**
	 * Retrieves an article by the specified name as a DTO.
	 *
	 * @param name Name of the article
	 * @return Optional article DTO object
	 */
	@Override
	public Optional<ArticleDto> getArticleByName(String name) {
		log.info("Retrieving article with name: '{}'", name);
		return articleRepository.findArticleByName(name)
		    .map(articleMapper::toDto);
	}

	/**
	 * Retrieves an article by the specified name as an entity.
	 *
	 * @param name Name of the article
	 * @return Optional article entity object
	 */
	@Override
	public Optional<Article> getArticleEntityByName(String name) {
		log.info("Retrieving article entity with name: '{}'", name);
		return articleRepository.findArticleByName(name);
	}

	/**
	 * Updates the article with the specified data.
	 *
	 * @param updateArticleDto Data for updating the article
	 * @return true if the article is successfully updated, false otherwise
	 */
	@Override
	public boolean updateArticle(ArticleDto updateArticleDto) {
		log.info("Updating article with name: '{}'", updateArticleDto.getName());
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
		log.info("Article '{}' updated successfully", updateArticleDto.getName());
		return true;
	}

	/**
	 * Deletes the article with the specified name and username.
	 *
	 * @param username    Username deleting the article
	 * @param articleName Name of the article to delete
	 * @return true if the article is successfully deleted, false otherwise
	 */
	@Override
	public boolean deleteArticle(String username, String articleName) {
		log.info("Deleting article '{}' by user: '{}'", articleName, username);
		var userByUsername = userMapper.toEntity(
		    userService.getUserByUsername(username).orElseThrow());
		var articleById = getArticleByName(articleName)
		    .map(articleMapper::toEntity).orElseThrow();

		if (articleById.getUser().getId().equals(userByUsername.getId())) {
			articleRepository.deleteById(articleById.getId());
			log.info("Article '{}' deleted successfully", articleName);
			return true;
		}
		log.warn(
		    "User '{}' is not authorized to delete article '{}' because the article was not "
			  + "created by this user",
		    username, articleName);
		return false;
	}
}
