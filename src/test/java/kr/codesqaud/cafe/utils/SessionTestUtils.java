package kr.codesqaud.cafe.utils;

import org.springframework.mock.web.MockHttpSession;

import kr.codesqaud.cafe.global.config.Session;
import kr.codesqaud.cafe.utils.data.StringTestData;

public class SessionTestUtils {

	public static MockHttpSession createMockHttpSession() {
		MockHttpSession httpSession = new MockHttpSession();
		Session session = new Session(StringTestData.SESSION_ID.getValue(), StringTestData.SESSION_NICK_NAME.getValue());
		httpSession.setAttribute(Session.LOGIN_USER, session);
		return httpSession;
	}
}
