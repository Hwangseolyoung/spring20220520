package com.choong.spr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.choong.spr.domain.BoardDto;

public interface BoardMapper {
	
	// 검색 기능 추가로 파라미터에서 받은 키워드 입력
	// 컴파일시 파라미터 이름이 사라짐으로 @Param에 이름 명시하기(파라미터로 2개이상 받을시)
	List<BoardDto> selectBoardAll(@Param("type") String type, @Param("keyword") String keyword);

	int insertBoard(BoardDto board);

	BoardDto selectBoardById(int id);

	int updateBoard(BoardDto dto);

	int deleteBoard(int id);
	
	// 해당 멤버가 쓴 게시글에 달린 다른사람 댓글 select 후 delete
	List<BoardDto> listByMemberId(String memberId);

	// 해당 멤버가 쓴 게시글 삭제
	void deleteByMemberId(String memberId);
	
	

}
