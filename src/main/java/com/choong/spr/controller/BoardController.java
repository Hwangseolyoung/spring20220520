package com.choong.spr.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.choong.spr.domain.BoardDto;
import com.choong.spr.domain.MemberDto;
import com.choong.spr.domain.ReplyDto;
import com.choong.spr.service.BoardService;
import com.choong.spr.service.ReplyService;

@Controller
@RequestMapping("board")
public class BoardController {
	
	@Autowired
	private BoardService service;
	
	@Autowired
	private ReplyService replyService;
	
	// 타입과 키워드가 입력되면 화면에 출력하도록 listBoard()에 담기
	// defaultValue="" 기본값으로 빈스트링을 넣어주면
	// 검색하지 않아도 기존 리스트가 출력됨
	@RequestMapping("list")
	public void list(@RequestParam(name = "keyword", defaultValue = "") String keyword,
					 @RequestParam(name = "type", defaultValue = "") String type, 
					 Model model){
		
		List<BoardDto> list = service.listBoard(type, keyword);
		
		model.addAttribute("boardList", list);
	}
	
	/* 위 코드에 합쳐서 작성함(중복코드 제거)
	// 위 list 메소드와 동일한 일을 한다면 같이 작성하지만
	// 각각 다른일을 하므로 분리해서 작성하기
	@RequestMapping(path="list", params="keyword")
	public void search(String keyword, Model model) {
		List<BoardDto> list = service.searchBoard(keyword);
		model.addAttribute("boardList", list);
	}
	*/
	
	@GetMapping("insert")
	public void insert() {
		
	}
	
	// 로그인 정보 추가
	@PostMapping("insert") 
	public String insert(BoardDto board, 
			// 여러파일 선택할 수 있도록 설정
			// 배열로 변경함 MultipartFile file -> MultipartFile[] file
			MultipartFile[] file, // file upload 추가
			Principal principal, // Principal : security login 정보가 담겨있다.
			RedirectAttributes rttr) {
		/*
		System.out.println(principal);
		System.out.println(principal.getName()); // username
		
		System.out.println(file); // 파일 넘어오는지 확인
		System.out.println(file.getOriginalFilename()); // 파일 이름
		System.out.println(file.getSize()); // 파일 사이즈
		 */
		
//		if (file.getSize() > 0) { // file이 넘어오지 않으면 size가 0이다.
//			// fileName setting
//			board.setFileName(file.getOriginalFilename());			
//		}
		
		// 여러파일 선택할 수 있도록 설정
		if (file != null) {
			List<String> fileList = new ArrayList<String>();
			for(MultipartFile f : file) {
				fileList.add(f.getOriginalFilename());
			}
			
			board.setFileName(fileList);
		}
		
		// 로그인 username 얻기
		board.setMemberId(principal.getName());
		// insertBoard 배열타입으로 변경함(파일 여러개 업로드)                  
		boolean success = service.insertBoard(board, file); // file upload 추가
		
		if (success) {
			rttr.addFlashAttribute("message", "새 글이 등록되었습니다.");
		} else {
			rttr.addFlashAttribute("message", "새 글이 등록되지 않았습니다.");
		}
		
		return "redirect:/board/list";
	}
	
	@GetMapping("get")
	public void get(int id, Model model) {
		BoardDto dto = service.getBoardById(id);
		List<ReplyDto> replyList = replyService.getReplyByBoardId(id);
		
		model.addAttribute("board", dto);
		
		// 게시물 로딩 후 ajax로 처리하기 위해 삭제
//		model.addAttribute("replyList", replyList);
		
	}
	
	// 게시글 작성자만 수정 가능하도록 추가
	@PostMapping("modify") // Principal : security login 정보가 담겨있다.
	public String modify(BoardDto dto, Principal principal, RedirectAttributes rttr) {
		// 수정하는 게시글 정보 얻기
		BoardDto oldBoard = service.getBoardById(dto.getId());
		// 게시물 작성자(memberId)와 principal의 name과 비교해서 같을 때만 진행
		if(oldBoard.getMemberId().equals(principal.getName())) {
			boolean success = service.updateBoard(dto);
			
			if (success) {
				rttr.addFlashAttribute("message", "글이 수정되었습니다.");
			} else {
				rttr.addFlashAttribute("message", "글이 수정되지 않았습니다.");
			}
			
		} else {
			rttr.addFlashAttribute("message", "수정 권한이 없습니다.");
		}
		
		rttr.addAttribute("id", dto.getId());
		
		return "redirect:/board/list"; // 권한이 없으면 list로 이동
	}
	
	// 게시글 작성자만 삭제 가능하도록 추가
	@PostMapping("remove") // Principal : security login 정보가 담겨있다.
	public String remove(BoardDto dto, Principal principal, RedirectAttributes rttr) {
		
		// 게시물 정보 얻고
		BoardDto oldBoard = service.getBoardById(dto.getId());
		// 게시물 작성자(memberId)와 principal의 name과 비교해서 같을 때만 진행
		if (oldBoard.getMemberId().equals(principal.getName())) {
			boolean success = service.deleteBoard(dto.getId());
			
			if (success) {
				rttr.addFlashAttribute("message", "글이 삭제 되었습니다.");
				
			} else {
				rttr.addFlashAttribute("message", "글이 삭제 되지않았습니다.");
			}
			
		} else {
			rttr.addFlashAttribute("message", "삭제 권한이 없습니다.");
			rttr.addAttribute("id", dto.getId());
			return "redirect:/board/get"; // 권한이 없으면 게시글 화면으로
		}
		
		return "redirect:/board/list";
	}
	
	
}










