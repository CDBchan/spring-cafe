package kr.codesqaud.cafe.repository;

import static kr.codesqaud.cafe.utils.ArticleTestUtils.*;
import static kr.codesqaud.cafe.utils.ReplyTestUtils.*;
import static kr.codesqaud.cafe.utils.UserTestUtils.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import kr.codesqaud.cafe.account.domain.User;
import kr.codesqaud.cafe.account.repository.impl.JDBCUserRepository;
import kr.codesqaud.cafe.article.domain.Article;
import kr.codesqaud.cafe.article.repository.impl.JDBCArticleRepository;
import kr.codesqaud.cafe.reply.domain.Reply;
import kr.codesqaud.cafe.reply.dto.LoadMoreReplyDto;
import kr.codesqaud.cafe.reply.repository.impl.JDBCReplyRepository;

@Transactional
@SpringBootTest
class JDBCReplyRepositoryTest {

	@Autowired
	private JDBCReplyRepository jDBCReplyRepository;

	@Autowired
	private JDBCUserRepository jDBCUserRepository;

	@Autowired
	private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

	private User expectedUser;

	private Reply expectedReply;

	@BeforeEach
	void setUp(){
		namedParameterJdbcTemplate.getJdbcOperations().execute("ALTER TABLE REPLY AUTO_INCREMENT = 1");

		expectedUser = createUser();
		expectedReply = createReply();
		jDBCUserRepository.save(expectedUser);
	}

	@Test
	@DisplayName("댓글 저장시 저장된 댓글을 가져올수 있다.")
	void testSave(){
		//when
		Reply actualReply = jDBCReplyRepository.saveReply(expectedReply);

		//then
		Assertions.assertThat(expectedReply.getContent()).isEqualTo(actualReply.getContent());
		Assertions.assertThat(expectedReply.getNickName()).isEqualTo(actualReply.getNickName());
	}

	@Test
	@DisplayName("findAllReply를 통해 해당 article에 존재하는 모든 댓글을 가져올수있다.")
	void findAllReplyTest(){
		//given
		LoadMoreReplyDto loadMoreReplyDto = createLoadedReplyDto();
		jDBCReplyRepository.saveReply(expectedReply);
		jDBCReplyRepository.saveReply(expectedReply);

		//when
		List<Reply> actualReply = jDBCReplyRepository.findAllReply(loadMoreReplyDto);

		//then
		Assertions.assertThat(actualReply.size()).isEqualTo(2);
	}

	@Test
	@DisplayName("사용자가 작성한 자신의 댓글은 삭제할수 있다.")
	void deleteReplyTest(){
		//given
		Reply savedReply = jDBCReplyRepository.saveReply(expectedReply);
		Reply savedReply2 = jDBCReplyRepository.saveReply(expectedReply);

		//when
		jDBCReplyRepository.deleteReply(savedReply.getUserId(), savedReply.getReplyIdx());

		//then
		Assertions.assertThat(jDBCReplyRepository.findAllReply(createLoadedReplyDto()).size()).isEqualTo(1);
	}

	@Test
	@DisplayName("findReplyIdByIdx 메서드를 통해 해당 idx의 id를 찾을수 있다.")
	void findReplyIdByIdxTest(){
		//given
		Reply savedReply = jDBCReplyRepository.saveReply(expectedReply);

		//when
		String actualReplyUserId = jDBCReplyRepository.findReplyIdByIdx(savedReply.getReplyIdx());

		//then
		Assertions.assertThat(actualReplyUserId).isEqualTo(expectedReply.getUserId());
	}

	@Test
	@DisplayName("getCountOfReplies 메서드를 통해 해당 article의 댓글의 갯수를 가져올수있다.")
	void getCountOfRepliesTest(){
		//given
		jDBCReplyRepository.saveReply(expectedReply);
		jDBCReplyRepository.saveReply(expectedReply);

		//when
		Integer actualReplyCount = jDBCReplyRepository.getCountOfReplies(expectedReply.getArticleIdx());

		//then
		Assertions.assertThat(actualReplyCount).isEqualTo(2);
	}



}