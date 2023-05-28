package kr.codesqaud.cafe.utils.data;

public enum StringTestData {
	USER_ID("testId"),
	NEW_USER_ID("testId2"),
	NICK_NAME("tester"),
	TITLE("title"),
	NEW_TITLE("newTitle"),
	CONTENT("content"),
	NEW_CONTENT("newContent"),
	DATE("2023-4-27"),
	REPLY_CONTENT("reply content"),
	PASSWORD("password123"),
	EMAIL("test@Email.com"),
	SESSION_ID("testId"),
	SESSION_NICK_NAME("tester");

	private final String value;

	StringTestData(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}
}
