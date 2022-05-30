package com.choong.spr.controller;

import java.security.Principal;
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
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.choong.spr.domain.ReplyDto;
import com.choong.spr.service.ReplyService;

@RestController
@RequestMapping("reply")
public class ReplyController {

	@Autowired
	private ReplyService service;
	
	// 회원만 댓글 작성 가능하도록 수정(Principal principal)
	// Principal : security login 정보가 담겨있다.
	@PostMapping(path = "insert",  produces = "text/plain;charset=UTF-8")
	public ResponseEntity<String> insert(ReplyDto dto, Principal principal) {
		
		
		if (principal == null) { 
			// 회원이 아니라면 UNAUTHORIZED 오류 401(접근불가)
			// 페이지 검사-네트워크에 보면 status 401 메세지를 볼 수 있다.
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		} else {
			// 로그인 회원 아이디 얻어오기
			dto.setMemberId(principal.getName());
			
			/* 위 한줄 풀어쓰기
			 String memberId = principal.getName();
			 dto.setMemberId(memberId);
			 */
			
			boolean success = service.insertReply(dto);
			
			if (success) {
				return ResponseEntity.ok("새 댓글이 등록되었습니다.");
			} else { // 댓글이 등록되지 않으면 상태 메세지 출력 500 Internal Server Error
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error");
			}
			
		}
		

		
	}
	
	// 댓글 쓴 사람만 수정가능하도록 추가
	@PutMapping(path = "modify", produces = "text/plain;charset=UTF-8")
	// @RequestBody : json 형식의 객체로 변경해주는 어노테이션
	public ResponseEntity<String> modify(@RequestBody ReplyDto dto, Principal principal) { 
		
		// 회원이 아니라면 UNAUTHORIZED 오류 401(접근불가)
		// 페이지 검사-네트워크에 보면 status 401 메세지를 볼 수 있다.
		if (principal == null) {
			return ResponseEntity.status(401).build();
	
		} else {
			
			// 로그인 정보 넘겨주기
			boolean success = service.updateReply(dto, principal);
			
			if (success) {
				return ResponseEntity.ok("댓글이 변경되었습니다.");
			} 
			return ResponseEntity.status(500).body("");
		}
		

		/*rttr.addAttribute("id", dto.getBoardId());
		return "redirect:/board/get";*/
	}
	
	// 댓글 쓴 사람만 삭제 가능하도록 추가
	@DeleteMapping(path = "delete/{id}", produces = "text/plain;charset=UTF-8")
	public ResponseEntity<String> delete(@PathVariable("id") int id, Principal principal) {
		
		// 회원이 아니라면 UNAUTHORIZED 오류 401(접근불가)
		// 페이지 검사-네트워크에 보면 status 401 메세지를 볼 수 있다.
		if (principal == null) {
			return ResponseEntity.status(401).build();
		} else {
			
			boolean success = service.deleteReply(id, principal);
			
			if (success) {
				return ResponseEntity.ok("댓글을 삭제 하였습니다.");
			} else {
				return ResponseEntity.status(500).body("");
			}
		 
		}
		
		/*rttr.addAttribute("id", dto.getBoardId());
		return "redirect:/board/get";*/
	}
	
	// json 형식 응답으로 변경
	// 댓글 작성자만 수정 삭제 가능하도록 추가
	@GetMapping("list")
	public List<ReplyDto> list(int boardId, Principal principal) {
		
		if (principal == null) { // 로그인한 사람이 없으면 boardId만 넣어주기
			return service.getReplyByBoardId(boardId);
			
		} else { // 로그인한 사람이 있으면 파라미터로 boardId와 로그인 정보 넣어주기
			return service.getReplyWithOwnByBoardId(boardId, principal.getName());
		}

	}
}






