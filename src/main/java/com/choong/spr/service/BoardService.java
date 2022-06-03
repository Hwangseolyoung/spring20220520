package com.choong.spr.service;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.choong.spr.domain.BoardDto;
import com.choong.spr.mapper.BoardMapper;
import com.choong.spr.mapper.ReplyMapper;

import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.Bucket;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.ObjectCannedACL;
import software.amazon.awssdk.services.s3.model.PutObjectAclRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

@Service
public class BoardService {

	@Autowired
	private BoardMapper mapper;
	
	@Autowired
	private ReplyMapper replyMapper;
	
	// AWS 파일 업로드 
	private S3Client s3;
	
	// @Value : 프로퍼티즈 내용을 쓸 수 있는 어노테이션 
	@Value("${aws.s3.bucketName}")
	private String bucketName;
	
	public List<BoardDto> listBoard(String type, String keyword) {
		// 검색 기능 추가 파라미터 값 할당해주기
		return mapper.selectBoardAll(type, "%" + keyword +"%");
	}
	
	@PostConstruct // 객체가 생성되자 마자 실행되는 메소드
	public void init() {
		Region region = Region.AP_NORTHEAST_2;
		this.s3 = S3Client.builder().region(region).build();
	}
	
	@PreDestroy // s3 자원 닫아주기
	public void destory() {
		this.s3.close();
	}
	
	// file upload 추가
	@Transactional // 여러 테이블에서 한꺼번에 일어나야 하는 일을 묶음
	public boolean insertBoard(BoardDto board, MultipartFile[] files) { // MultipartFile[] 배열타입으로 변경함
		// 한국 표준시간으로 세팅 변경
//		board.setInserted(LocalDateTime.now());
		
		// 게시글 등록
		int cnt = mapper.insertBoard(board);
		
		// 여러파일 등록 메소드 사용
		addFiles(board.getId(), files);
		
		return cnt == 1;
	}
	
	// 여러파일 등록 메소드 
	// insertBoard, modify에서 사용함
	private void addFiles(int id, MultipartFile[] files) {
		// 파일 등록(여러개 파일 등록으로 수정)
		if (files != null) {
			for (MultipartFile file : files) {
				if (file.getSize() > 0) {
					mapper.insertFile(id, file.getOriginalFilename());
					// 파일 저장 코드 추가(폴더만들기용:board.getId())
					/* saveFile(board.getId(), file); */
					saveFileAwsS3(id, file); // s3에 업로드
				}
	
			}
		}
	}
	
	// s3에 업로드 메소드(addFiles에서 사용함)
	private void saveFileAwsS3(int id, MultipartFile file) {
		// 파일 객체마다 키를 가지고 있다.
		// 키 - 폴더명과 파일명의 조합
		String key = "board/" + id + "/" + file.getOriginalFilename();
		
		// 버킷, key, 권한 
		PutObjectRequest putObjectRequest = PutObjectRequest.builder()
				.acl(ObjectCannedACL.PUBLIC_READ) // 권한
				.bucket(bucketName) // 버킷
				.key(key) // key
				.build(); // 빌드 하면 만들어짐
		
		// 파일 자체가 들어가면됨 ( , 파일 사이즈)
		RequestBody requestBody;
		try {                                           // getInputStream() 체크드 익셉션 발생 가능성 있음
			requestBody = RequestBody.fromInputStream(file.getInputStream(), file.getSize());
			s3.putObject(putObjectRequest, requestBody);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException(e);
		}
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
	
	// 여러파일 가져오기 
	public BoardDto getBoardById(int id) {
		BoardDto board = mapper.selectBoardById(id);
		List<String> fileNames =  mapper.selecteFileNameByBoard(id);
		
		board.setFileName(fileNames);
		
		return board;
	}
	
	// 게시글 수정시 파일 추가기능 파라미터 MultipartFile[] addFileList
	// 게시글 수정시 파일 삭제기능 파라미터 String[] removeFileList
	@Transactional
	public boolean updateBoard(BoardDto dto, List<String> removeFileList, MultipartFile[] addFileList) {
		if (removeFileList != null) {
			// 게시글 수정시 선택한 파일만 지우기 
			for (String fileName : removeFileList) {
				deleteFromAwsS3(dto.getId(), fileName);
				mapper.deleteFileByBoardIdAndFileName(dto.getId(), fileName);
			}
		}
		
		if (addFileList != null) {
			// File 테이블에 추가된 파일 insert
			// s3에 upload
			// 파일 저장 메소드 사용함
			addFiles(dto.getId(), addFileList);
			
		}
		// Board 테이블 update
		int cnt = mapper.updateBoard(dto);
		
		return cnt == 1;
	}
	
	@Transactional
	public boolean deleteBoard(int id) {
		/* 파일 목록 읽기(1개 파일)
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
		
		// aws s3에서 지우기(1개 파일)
		deleteFromAwsS3(id, fileName);
		
		*/
		
		// 여러 파일 목록 읽기(List로 수정)
		List<String> fileList = mapper.selecteFileNameByBoard(id);
		
		// 게시글에서 여러파일 지우는 메소드 사용
		removeFiles(id, fileList);
		
		// 댓글 테이블 삭제
		replyMapper.deleteByBoardId(id);
		
		return mapper.deleteBoard(id) == 1;
	}
	
	// 게시글에서 여러파일 지우는 메소드(deleteBoard 사용됨)
	private void removeFiles(int id, List<String> fileList) {
		// aws s3에서 여러파일 지우기(수정)
		for(String fileName : fileList) {
			deleteFromAwsS3(id, fileName);
			
		}
		
		// 파일 테이블 삭제
		mapper.deleteFileByBoardId(id);
	}
	
	// aws s3에서 파일 지우는 메소드
	private void deleteFromAwsS3(int id, String fileName) {
		String key = "board/" + id + "/" +fileName;
		
		// 버킷이름, 키
		DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
				.bucket(bucketName)
				.key(key)
				.build();
		
		s3.deleteObject(deleteObjectRequest);
		
	}

}





