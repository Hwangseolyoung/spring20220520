package com.choong.spr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.choong.spr.domain.BoardDto;
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
	
	@PostMapping("insert")
	public String insert(BoardDto board, RedirectAttributes rttr) {
		boolean success = service.insertBoard(board);
		
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
		model.addAttribute("replyList", replyList);
		
	}
	
	@PostMapping("modify")
	public String modify(BoardDto dto, RedirectAttributes rttr) {
		boolean success = service.updateBoard(dto);
		
		if (success) {
			rttr.addFlashAttribute("message", "글이 수정되었습니다.");
		} else {
			rttr.addFlashAttribute("message", "글이 수정되지 않았습니다.");
		}
		
		rttr.addAttribute("id", dto.getId());
		
		return "redirect:/board/get";
	}
	
	@PostMapping("remove")
	public String remove(BoardDto dto, RedirectAttributes rttr) {
		
		boolean success = service.deleteBoard(dto.getId());
		
		if (success) {
			rttr.addFlashAttribute("message", "글이 삭제 되었습니다.");
			
		} else {
			rttr.addFlashAttribute("message", "글이 삭제 되지않았습니다.");
		}
		
		return "redirect:/board/list";
	}
	
	
}










