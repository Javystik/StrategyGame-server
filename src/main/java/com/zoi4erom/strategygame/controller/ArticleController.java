package com.zoi4erom.strategygame.controller;

import com.zoi4erom.strategygame.dto.ArticleDto;
import com.zoi4erom.strategygame.service.contract.ArticleService;
import com.zoi4erom.strategygame.service.contract.UserService;
import com.zoi4erom.strategygame.service.impl.ArticleServiceImpl;
import com.zoi4erom.strategygame.service.impl.UserServiceImpl;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/articles")
@AllArgsConstructor
@CrossOrigin
public class ArticleController {

	private final ArticleService articleService;
	private final UserService userService;

	@GetMapping
	public ResponseEntity<List<ArticleDto>> getAllArticles() {
		var articles = articleService.getAllArticle();
		return articles.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(articles);
	}

	@PostMapping
	@Secured("ROLE_ADMIN")
	public void createArticle(@RequestBody ArticleDto articleDto) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		var userDto = userService.getUserByUsername(authentication.getName()).orElseThrow();
		articleService.createArticle(articleDto, userDto);
	}

	@DeleteMapping("/{articleName}")
	@Secured("ROLE_ADMIN")
	public ResponseEntity<?> deleteArticle(@PathVariable String articleName){
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (articleService.deleteArticle(authentication.getName(), articleName)){
			return ResponseEntity.ok().build();
		}else {
			return ResponseEntity.status(403).build();
		}
	}
	@PutMapping
	@Secured("ROLE_ADMIN")
	public ResponseEntity<?> updateArticle(@RequestBody ArticleDto articleDto) {
		if (articleService.updateArticle(articleDto)) {
			return ResponseEntity.ok().build();
		} else {
			return ResponseEntity.notFound().build();
		}
	}
}
