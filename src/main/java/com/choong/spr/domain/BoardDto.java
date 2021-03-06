package com.choong.spr.domain;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class BoardDto {

	private int id;
	private String title;
	private String body;
	private LocalDateTime inserted;
	// 로그인 memberId 추가
	private String memberId;
	// member nickName 추가
	private String writerNickName;
	private int numOfReply;
	// 1개 파일 업로드 추가
	// 여러파일 선택할 수 있도록 변경 String -> List<String>
	private List<String> fileName;
	// 파일 유무 파악
	private boolean hasFile;

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
