package com.zoi4erom.strategygame.repository;

import com.zoi4erom.strategygame.entity.Article;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {

	Optional<Article> findArticleByName(String articleName);
}
