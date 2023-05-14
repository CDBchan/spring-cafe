package kr.codesqaud.cafe.repository;

import static kr.codesqaud.cafe.utils.UserTestUtils.*;
import static org.assertj.core.api.Assertions.*;

import java.util.List;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import kr.codesqaud.cafe.account.domain.User;
import kr.codesqaud.cafe.account.repository.impl.JDBCUserRepository;
import kr.codesqaud.cafe.utils.data.StringTestData;

@Transactional
@SpringBootTest
class JDBCUserRepositoryTest {

	@Autowired
	private JDBCUserRepository jDBCUserRepository;

	private static final String USER_ID = StringTestData.USER_ID.getValue();

	private User expectedUser;

	@BeforeEach
	void setUp(){
		expectedUser = createUser();
	}

	@Test
	@DisplayName("NICK_NAME, EMAIL, PASSWORD, USER_ID를 필드로 가지는 User 객체 저장시 findUserById 메서드를 통해 User를 찾을수있다.")
	void saveTest() {
		//given
		jDBCUserRepository.save(expectedUser);

		//when
		User actualUser = jDBCUserRepository.findUserById(USER_ID).orElseThrow();

		assertThat(actualUser.getUserId()).isEqualTo(expectedUser.getUserId());
		assertThat(actualUser.getEmail()).isEqualTo(expectedUser.getEmail());
		assertThat(actualUser.getPassword()).isEqualTo(expectedUser.getPassword());
		assertThat(actualUser.getNickName()).isEqualTo(expectedUser.getNickName());
	}

	@Test
	@DisplayName("userId를 통해 해당 Id의 중복 여부를 확인할수 있다.")
	void existTest(){
		//given
		jDBCUserRepository.save(expectedUser);

		//when
		boolean actual = jDBCUserRepository.exist(USER_ID);

		//then
		assertThat(actual).isTrue();
	}

	@Test
	@DisplayName("findAll 메서드를 통해 DB에 저장된 모든 User를 List 형태로 가져올수 있다.")
	void findAllTest(){
		//given
		User expectedUser2 = createUser2();
		jDBCUserRepository.save(expectedUser);
		jDBCUserRepository.save(expectedUser2);

		//when
		List<User> users = jDBCUserRepository.findAll();

		//then
		assertThat(users.size()).isEqualTo(2);
		assertThat(users.get(0).getUserId()).isEqualTo(expectedUser.getUserId());
		assertThat(users.get(0).getEmail()).isEqualTo(expectedUser.getEmail());
	}

	@Test
	@DisplayName("update메서드를 통해 해당 userId의 정보를 수정할수 있다.")
	void updateUserTest(){
		//given
		User user = new User("newNickName","new@email.com","newPassword","testId");
		jDBCUserRepository.save(expectedUser);

		//when
		jDBCUserRepository.updateUser(user);

		//then
		User actualUser = jDBCUserRepository.findUserById(USER_ID).orElseThrow();
		assertThat(actualUser.getUserId()).isEqualTo(user.getUserId());
		assertThat(actualUser.getEmail()).isEqualTo(user.getEmail());
		assertThat(actualUser.getPassword()).isEqualTo(user.getPassword());
	}
}