package kr.codesqaud.cafe.repository;

import static kr.codesqaud.cafe.utils.ArticleTestUtils.*;
import static kr.codesqaud.cafe.utils.UserTestUtils.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import kr.codesqaud.cafe.account.domain.User;
import kr.codesqaud.cafe.account.repository.impl.JDBCUserRepository;
import kr.codesqaud.cafe.article.domain.Article;
import kr.codesqaud.cafe.article.repository.impl.JDBCArticleRepository;
import kr.codesqaud.cafe.mainPage.PaginationDto;
import kr.codesqaud.cafe.utils.data.LongTestData;
import kr.codesqaud.cafe.utils.data.StringTestData;

@Transactional
@SpringBootTest
public class JDBCArticleRepositoryTest {

	@Autowired
	private JDBCArticleRepository jdbcArticleRepository;

	@Autowired
	private JDBCUserRepository jDBCUserRepository;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private User expectedUser;

	private Article expectedArticle;

	@BeforeEach
	void setUp(){
		namedParameterJdbcTemplate.getJdbcOperations().execute("ALTER TABLE ARTICLE AUTO_INCREMENT = 1");

		expectedUser = createUser();
		expectedArticle = createArticle();
		jDBCUserRepository.save(expectedUser);
	}

	@Test
	@DisplayName("article 저장시 해당 article의 idx를 통해 게시글을 가져올수 있다. ")
	void saveTest() {
		Long ARTICLE_IDX = LongTestData.ARTICLE_IDX.getValue();

		//when
		jdbcArticleRepository.save(expectedArticle);

		//then
		Article actualArticle = jdbcArticleRepository.findArticleByIdx(ARTICLE_IDX).orElseThrow();

		assertThat(actualArticle.getUserId()).isEqualTo(expectedArticle.getUserId());
		assertThat(actualArticle.getTitle()).isEqualTo(expectedArticle.getTitle());
		assertThat(actualArticle.getContent()).isEqualTo(expectedArticle.getContent());
	}

	@Test
	@DisplayName("findAll 메서드를 통해 db에 저장된 article을 list의 형태로 가져올수 있다.")
	void findAllTest(){
		//given
		PaginationDto paginationDto = new PaginationDto();
		jdbcArticleRepository.save(expectedArticle);
		jdbcArticleRepository.save(expectedArticle);

		//when
		List<Article> articles = jdbcArticleRepository.findAll(paginationDto);

		//then
		assertThat(articles.size()).isEqualTo(2);
		assertThat(articles.get(0).getUserId()).isEqualTo(expectedArticle.getUserId());
		assertThat(articles.get(0).getTitle()).isEqualTo(expectedArticle.getTitle());
		assertThat(articles.get(0).getContent()).isEqualTo(expectedArticle.getContent());
	}


	@Test
	@DisplayName("db에 존재하는 article의 내용을 수정 할수 있다.")
	void updateArticleTest(){
		//given
		jdbcArticleRepository.save(expectedArticle);
		Article updateArticle = createArticleForUpdate();

		//when
		jdbcArticleRepository.updateArticle(updateArticle);

		//then
		Article actualArticle = jdbcArticleRepository.findArticleByIdx(updateArticle.getArticleIdx()).orElseThrow();

		assertThat(actualArticle.getTitle()).isEqualTo(updateArticle.getTitle());
		assertThat(actualArticle.getContent()).isEqualTo(updateArticle.getContent());
	}

	@Test
	@DisplayName("해당 게시글을 작성한 사용자는 게시글에 자신외 사용자가 작성한 댓글이 없다면 게시글을 삭제할수 있다.")
	void deleteArticleTest(){
		//given
		jdbcArticleRepository.save(expectedArticle);

		//when
		jdbcArticleRepository.deleteArticle(expectedArticle.getArticleIdx(),StringTestData.USER_ID.getValue());

		//then
		Assertions.assertThat(jdbcArticleRepository.findArticleByIdx(expectedArticle.getArticleIdx())).isEmpty();
	}

	@Test
	@DisplayName("getCountOfArticles 메서드를 통해 db에 저장된 게시글의 개수를 얻을수 있다.")
	void getCountOfArticles(){
		//given
		jdbcArticleRepository.save(expectedArticle);
		jdbcArticleRepository.save(expectedArticle);

		//when
		Long count = jdbcArticleRepository.getCountOfArticles();

		//then
		Assertions.assertThat(count).isEqualTo(2);
	}

}



