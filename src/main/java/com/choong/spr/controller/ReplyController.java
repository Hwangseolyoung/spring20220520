package com.choong.spr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.choong.spr.domain.ReplyDto;
import com.choong.spr.service.ReplyService;

@Controller
@RequestMapping("reply")
public class ReplyController {

	@Autowired
	private ReplyService service;

	@PostMapping(path = "insert",  produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public ResponseEntity<String> insert(ReplyDto dto) {

		boolean success = service.insertReply(dto);

		if (success) {
			return ResponseEntity.ok("새 댓글이 등록되었습니다.");
		} else {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
		}

		
	}

	@PutMapping(path = "modify", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	// @RequestBody : json 형식의 객체로 변경해주는 어노테이션
	public ResponseEntity<String> modify(@RequestBody ReplyDto dto) { 
		boolean success = service.updateReply(dto);

		if (success) {
			return ResponseEntity.ok("댓글이 변경되었습니다.");
		} 
			return ResponseEntity.status(500).body("");

		/*rttr.addAttribute("id", dto.getBoardId());
		return "redirect:/board/get";*/
	}
	
	@DeleteMapping(path = "delete/{id}", produces = "text/plain;charset=UTF-8")
	@ResponseBody
	public ResponseEntity<String> delete(@PathVariable("id") int id) {
		boolean success = service.deleteReply(id);
		
		if (success) {
			return ResponseEntity.ok("댓글이 삭제되었습니다.");
		} else {
			return ResponseEntity.status(500).body("");
		}
		
		/*rttr.addAttribute("id", dto.getBoardId());
		return "redirect:/board/get";*/
	}
	
	// json 형식 응답으로 변경
	@GetMapping("list")
	@ResponseBody
	public List<ReplyDto> list(int boardId) {
		return service.getReplyByBoardId(boardId);

	}
}






