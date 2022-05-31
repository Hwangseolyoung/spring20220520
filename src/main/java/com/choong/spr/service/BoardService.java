package com.choong.spr.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

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
	
	// file upload 추가
	@Transactional // 여러 테이블에서 한꺼번에 일어나야 하는 일을 묶음
	public boolean insertBoard(BoardDto board, MultipartFile file) {
		// 한국 표준시간으로 세팅 변경
//		board.setInserted(LocalDateTime.now());
		
		// 게시글 등록
		int cnt = mapper.insertBoard(board);
		
		// 파일 등록
		if (file.getSize() > 0) {
			mapper.insertFile(board.getId(), file.getOriginalFilename());
			// 파일 저장 코드 추가(폴더만들기용:board.getId())
			saveFile(board.getId(), file);
		}
		
		return cnt == 1;
	}
	
	// 파일 저장 메소드
	private void saveFile(int id, MultipartFile file) {
		// 디렉토리 만들기
		String pathStr = "C:/imgtmp/board/" + id + "/";
		File path = new File(pathStr);
		path.mkdir();
		
		// 작성할 파일
		File des = new File(pathStr + file.getOriginalFilename());
		
		// Exception 처리 해주기
		try {
			// 파일 저장
			file.transferTo(des);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
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
		// 파일 목록 읽기
		String fileName = mapper.selectFileByBoardId(id);
		
		// 실제 파일 삭제
		if(fileName != null && !fileName.isEmpty()) {
			String folder = "C:/imgtmp/board/" + id + "/";
			String path = folder + fileName;
			
			// 파일 삭제
			File file = new File(path);
			file.delete();
			
			// 폴더 삭제
			File dir = new File(folder);
			dir.delete();
			
		}
		
		// 파일 테이블 삭제
		mapper.deleteFileByBoardId(id);
		
		// 댓글 테이블 삭제
		replyMapper.deleteByBoardId(id);
		
		return mapper.deleteBoard(id) == 1;
	}
	
	

}





