package com.zoi4erom.strategygame.repository;

import com.zoi4erom.strategygame.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Integer> {

}
