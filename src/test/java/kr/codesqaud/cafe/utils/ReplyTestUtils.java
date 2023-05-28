package kr.codesqaud.cafe.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kr.codesqaud.cafe.reply.domain.Reply;
import kr.codesqaud.cafe.reply.dto.LoadMoreReplyDto;
import kr.codesqaud.cafe.reply.dto.ReplyRequest;
import kr.codesqaud.cafe.reply.dto.ReplyResponse;
import kr.codesqaud.cafe.reply.dto.Result;
import kr.codesqaud.cafe.utils.data.LongTestData;
import kr.codesqaud.cafe.utils.data.StringTestData;

public class ReplyTestUtils {
	private static final String CONTENT = StringTestData.CONTENT.getValue();
	private static final String NICK_NAME = StringTestData.NICK_NAME.getValue();
	private static final String USER_ID = StringTestData.USER_ID.getValue();
	private static final Long articleIdx = LongTestData.ARTICLE_IDX.getValue();
	private static final String DATE = StringTestData.DATE.getValue();
	private static final Long REPLY_IDX = LongTestData.REPLY_IDX.getValue();

	public static Reply createReply() {
		return new Reply(USER_ID, articleIdx, NICK_NAME, CONTENT);
	}

	public static ReplyRequest createReplyRequest() {
		return new ReplyRequest(CONTENT, NICK_NAME);
	}

	public static ReplyResponse createReplyResponse() {
		return new ReplyResponse(NICK_NAME, CONTENT, DATE, articleIdx, REPLY_IDX);
	}

	public static LoadMoreReplyDto createLoadedReplyDto() {
		return new LoadMoreReplyDto(articleIdx, 2, 0);
	}

	public static List<Reply> createReplies() {
		return new ArrayList<>(Arrays.asList(createReply()));
	}

	public static Result createResult() {
		return new Result(true, null);
	}
}
