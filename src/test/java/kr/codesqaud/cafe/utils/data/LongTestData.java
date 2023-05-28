package kr.codesqaud.cafe.utils.data;

public enum LongTestData {
	ARTICLE_IDX(1L),
	REPLY_IDX(1L);

	private final Long value;

	LongTestData(Long value) {
		this.value = value;
	}

	public Long getValue() {
		return this.value;
	}
}