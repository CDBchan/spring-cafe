package kr.codesqaud.cafe.article.repository.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import kr.codesqaud.cafe.article.domain.Article;
import kr.codesqaud.cafe.article.repository.ArticleRepository;
import kr.codesqaud.cafe.mainPage.PaginationDto;

@Repository
public class JDBCArticleRepository implements ArticleRepository {

	private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	public JDBCArticleRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
		this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public void save(Article article) {
		namedParameterJdbcTemplate.update(
			"INSERT INTO ARTICLE (title, content, user_id) VALUES (:title, :content, :userId)",
			new MapSqlParameterSource()
				.addValue("title", article.getTitle())
				.addValue("content", article.getContent())
				.addValue("userId", article.getUserId())
		);

	}

	@Override
	public List<Article> findAll(PaginationDto paginationDto) {
		return namedParameterJdbcTemplate.query(
			"SELECT A.nickName, B.* FROM USER A JOIN ARTICLE B ON A.user_id = B.user_id "
				+ "WHERE is_visible = true ORDER BY B.article_idx DESC LIMIT :start,:recordSize",
			new MapSqlParameterSource()
				.addValue("start", paginationDto.getOffset())
				.addValue("recordSize", paginationDto.getRecordSize()),
			(rs, rn) -> new Article(rs));
	}

	@Override
	public Optional<Article> findArticleByIdx(Long articleIdx) {
		List<Article> article = namedParameterJdbcTemplate.query(
			"SELECT A.nickName, B.* FROM USER A JOIN ARTICLE B ON A.user_id = B.user_id WHERE B.article_idx = :articleIdx AND B.is_visible = true",
			new MapSqlParameterSource("articleIdx", articleIdx),
			(rs, rn) -> new Article(rs));
		return article.stream().findFirst();
	}

	@Override
	public void updateArticle(Article article) {
		namedParameterJdbcTemplate.update(
			"UPDATE ARTICLE SET title = :title, content = :content WHERE article_idx = :articleIdx",
			new MapSqlParameterSource()
				.addValue("title", article.getTitle())
				.addValue("content", article.getContent())
				.addValue("articleIdx", article.getArticleIdx())
		);
	}

	@Override
	public boolean deleteArticle(Long articleIdx, String userId) {
		// Update ARTICLE table
		int rowsAffected = namedParameterJdbcTemplate.update(
			"UPDATE ARTICLE "
				+ "SET is_visible = FALSE "
				+ "WHERE article_idx = :articleIdx "
				+ "AND NOT EXISTS (SELECT 1 FROM REPLY WHERE article_idx = :articleIdx AND is_visible = TRUE AND user_id != :userId)",
			new MapSqlParameterSource()
				.addValue("articleIdx", articleIdx)
				.addValue("userId", userId)
		);

		// Update REPLY table
		if (rowsAffected > 0) {
			namedParameterJdbcTemplate.update(
				"UPDATE REPLY "
					+ "SET is_visible = FALSE "
					+ "WHERE article_idx = :articleIdx",
				new MapSqlParameterSource()
					.addValue("articleIdx", articleIdx)
			);
			return true;
		}
		return false;
	}

	@Override
	public Long getCountOfArticles() {
		return namedParameterJdbcTemplate.queryForObject(
			"SELECT COUNT(is_visible) FROM ARTICLE WHERE is_visible = true",
			new MapSqlParameterSource(),
			Long.class
		);
	}
}
