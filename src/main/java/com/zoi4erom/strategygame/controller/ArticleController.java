package com.zoi4erom.strategygame.controller;

import com.zoi4erom.strategygame.dto.ArticleDto;
import com.zoi4erom.strategygame.service.ArticleService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
@AllArgsConstructor
public class ArticleController {

	private final ArticleService articleService;

	@GetMapping
	public ResponseEntity<List<ArticleDto>> getAllArticles() {
		var articles = articleService.getAllArticle();
		return articles.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(articles);
	}

	@PostMapping
	public void createArticle(@RequestBody ArticleDto articleDto) {
		articleService.createArticle(articleDto);
	}
}
