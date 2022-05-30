package com.choong.spr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.choong.spr.domain.ReplyDto;

public interface ReplyMapper {

	int insertReply(ReplyDto dto);
	
	// 댓글 작성자만 수정 삭제 가능하도록 추가 
	List<ReplyDto> selectAllBoardId(@Param("boardId") int boardId, @Param("memberId") String memberId);

	int updateReply(ReplyDto dto);

	int deleteReply(int id);

	void deleteByBoardId(int boardId);
	
	// 댓글 쓴 사람만 수정가능하도록 추가
	ReplyDto selectReplyById(int id);
	
	
	void deleteByMemberId(String memberId);

}
