package com.zoi4erom.strategygame.repository;

import com.zoi4erom.strategygame.entity.Article;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repository interface for managing Article entities. Provides methods for interacting with the
 * Article entity in the database.
 */
public interface ArticleRepository extends JpaRepository<Article, Long> {

	/**
	 * Retrieves an optional Article entity by its name.
	 *
	 * @param articleName the name of the article to search for
	 * @return an optional containing the found Article entity, or empty if not found
	 */
	Optional<Article> findArticleByName(String articleName);
}
