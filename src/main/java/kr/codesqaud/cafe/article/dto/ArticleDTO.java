package kr.codesqaud.cafe.article.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

public class ArticleDTO {
	@NotBlank
	@Pattern(regexp = "^\\s*[\\S\\s]+\\s*$")
	private final String title;

	@NotBlank
	@Pattern(regexp = "^[\\s\\S]{3,1000}$")
	private final String content;

	private final Long idx;

	private final String date;

	private String id;

	private String nickName;

	public ArticleDTO(String title, String content, Long idx, String date) {
		this.title = title;
		this.content = content;
		this.idx = idx;
		this.date = date;
	}

	public String getTitle() {
		return title;
	}

	public String getContent() {
		return content;
	}

	public String getId() {
		return id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
}