package com.choong.spr.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.choong.spr.domain.BoardDto;
import com.choong.spr.mapper.BoardMapper;
import com.choong.spr.mapper.ReplyMapper;

@Service
public class BoardService {

	@Autowired
	private BoardMapper mapper;
	
	@Autowired
	private ReplyMapper replyMapper;
	
	public List<BoardDto> listBoard(String type, String keyword) {
		// 검색 기능 추가 파라미터 값 할당해주기
		return mapper.selectBoardAll(type, "%" + keyword +"%");
	}

	public boolean insertBoard(BoardDto board) {
		// 한국 표준시간으로 세팅 변경
//		board.setInserted(LocalDateTime.now());
		return mapper.insertBoard(board) == 1;
	}

	public BoardDto getBoardById(int id) {
		// TODO Auto-generated method stub
		return mapper.selectBoardById(id);
	}

	public boolean updateBoard(BoardDto dto) {
		// TODO Auto-generated method stub
		return mapper.updateBoard(dto) == 1;
	}
	
	@Transactional
	public boolean deleteBoard(int id) {
		
		replyMapper.deleteByBoardId(id);
		
		return mapper.deleteBoard(id) == 1;
	}
	
	

}





