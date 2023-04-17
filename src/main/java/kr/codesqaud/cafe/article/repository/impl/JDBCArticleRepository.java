package kr.codesqaud.cafe.article.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.article.domain.Article;
import kr.codesqaud.cafe.article.repository.ArticleRepository;

@Repository
@Qualifier("jdbcRepository")
public class JDBCArticleRepository implements ArticleRepository {

	private final JdbcTemplate jdbcTemplate;

	public JDBCArticleRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@Override
	public void save(Article article) {
		jdbcTemplate.update("INSERT INTO ARTICLE (title, content, date, id,nickName) VALUES (?, ?, ?, ?, ?)",
			article.getTitle(), article.getContent(), article.getDate(), article.getId(), article.getNickName());
	}

	@Override
	public List<Article> findAll() {
		return jdbcTemplate.query("SELECT * FROM ARTICLE", (rs, rn) -> new Article(rs));
	}

	@Override
	public Optional<Article> findArticleByIdx(Long idx) {
		List<Article> article = jdbcTemplate.query("SELECT * FROM ARTICLE WHERE idx = ?", (rs, rn) -> new Article(rs),
			idx);
		return article.stream().findFirst();
	}

	@Override
	public void updateArticle(Article article) {
		jdbcTemplate.update("UPDATE ARTICLE SET title = ?, content = ? WHERE idx = ?", article.getTitle(),
			article.getContent(), article.getIdx());
	}

	@Override
	public void deleteArticle(Long idx) {
		jdbcTemplate.update("DELETE FROM ARTICLE WHERE idx = ?", idx);
	}
}