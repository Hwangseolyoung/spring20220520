package com.choong.spr.domain;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ReplyDto {
	private int id;
	private int boardId;
	// 로그인 회원 memberId 추가
	private String memberId;
	// member nickName 추가
	private String writerNickName;
	private String content;
	// 댓글 작성자만 수정 삭제 가능하도록 추가
	private boolean own;
	private LocalDateTime inserted;

	public String getPrettyInserted() {
		// 24시간 이내면 시간만
		// 이전이면 년-월-일
		LocalDateTime now = LocalDateTime.now();
		if (now.minusHours(24).isBefore(inserted)) {
			return inserted.toLocalTime().toString();
		} else {
			return inserted.toLocalDate().toString();
		}
	}
}
