package kr.codesqaud.cafe.global.mapper;

import org.springframework.stereotype.Component;

import kr.codesqaud.cafe.article.domain.Article;
import kr.codesqaud.cafe.article.dto.ArticleListItem;
import kr.codesqaud.cafe.article.dto.ArticlePostRequest;
import kr.codesqaud.cafe.article.dto.ArticleResponse;
import kr.codesqaud.cafe.article.dto.ArticleTitleAndContentResponse;
import kr.codesqaud.cafe.article.dto.ArticleUpdateRequest;

@Component
public class ArticleMapper {
	public Article toArticle(ArticlePostRequest articlePostRequest) {
		return new Article(articlePostRequest.getTitle(), articlePostRequest.getContent(),
			articlePostRequest.getUserId(), articlePostRequest.getNickName());
	}

	public Article toArticle(ArticleUpdateRequest articleUpdateRequest) {
		return new Article(articleUpdateRequest.getTitle(), articleUpdateRequest.getContent(),
			articleUpdateRequest.getArticleIdx());
	}

	public ArticleResponse toArticleResponse(Article article) {
		return new ArticleResponse(article.getTitle(), article.getContent(),
			article.getArticleIdx(), article.getDate(), article.getNickName());
	}

	public ArticleTitleAndContentResponse toArticleTitleAndContentResponse(Article article) {
		return new ArticleTitleAndContentResponse(article.getTitle(), article.getContent());
	}

	public ArticleListItem toArticleResponseForList(Article article) {
		return new ArticleListItem(article.getTitle(), article.getArticleIdx(), article.getDate(),
			article.getNickName());
	}

}
