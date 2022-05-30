package com.choong.spr.service;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choong.spr.domain.ReplyDto;
import com.choong.spr.mapper.ReplyMapper;

@Service
public class ReplyService {

	@Autowired
	private ReplyMapper mapper;
	
	public boolean insertReply(ReplyDto dto) {
//		dto.setInserted(LocalDateTime.now());
		return mapper.insertReply(dto) == 1;
	}

	// 댓글 쓴 사람만 수정가능하도록 추가
	public boolean updateReply(ReplyDto dto, Principal principal) {
		ReplyDto old = mapper.selectReplyById(dto.getId());
				
		if (old.getMemberId().equals(principal.getName())) {
			// 댓글 작성자와 로그인한 유저가 같을 때만 수정
			return mapper.updateReply(dto) == 1;
		} else {
			// 그렇지 않으면 return false;
			return false;
		}
	}
	
	// 댓글 쓴 사람만 삭제 가능하도록 추가
	public boolean deleteReply(int id, Principal principal) {
		
		ReplyDto old = mapper.selectReplyById(id);
		
		if (old.getMemberId().equals(principal.getName())) {
			// 댓글 작성자와 로그인한 유저가 같을 때만 삭제
			return mapper.deleteReply(id) == 1;
		} else {
			// 그렇지 않으면 return false;
			return false;
		}
		
	}
	
	public List<ReplyDto> getReplyByBoardId(int boardId) {
		// 댓글 작성자만 수정 삭제 가능하도록 파라미터에 null 추가
		return mapper.selectAllBoardId(boardId, null);
	}
	
	// 댓글 작성자만 수정 삭제 가능하도록 추가 
	public List<ReplyDto> getReplyWithOwnByBoardId(int boardId, String memberId) {
		return mapper.selectAllBoardId(boardId, memberId);
	}



}
