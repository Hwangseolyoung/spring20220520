package com.choong.spr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.choong.spr.domain.MemberDto;
import com.choong.spr.service.MemberService;

@Controller
@RequestMapping("member")
public class MemberController {
	
	@Autowired
	private MemberService service;
	
	// 회원가입 form
	@GetMapping("signup")
	public void signupForm() {
		
	}
	
	// 회원가입 
	@PostMapping("signup")
	public String signupProcess(MemberDto member, RedirectAttributes rttr) {
		boolean success = service.addMember(member);
		
		if (success) {
			rttr.addFlashAttribute("message", "회원가입이 완료되었습니다.");
			return "redirect:/board/list";
		} else {
			rttr.addFlashAttribute("message", "회원가입이 실패하였습니다.");
			rttr.addFlashAttribute("member", member);
			return "redirect:/board/signup";
		}
	}
		
		// TODO : MemberService 작성
		//                      addMember method 작성
		//        MemberMapper.java, xml 작성
		//                      insertMember method 작성
	
	// 회원가입 id 중복체크
	@GetMapping(path="check", params="id")
	@ResponseBody
	public String idCheck(String id) {
		boolean exist = service.hasMemberId(id);
		
		if (exist) {
			return "notOk";
		} else {
			return "ok";
		}
	}
	
	// 회원가입 email 중복체크
	@GetMapping(path="check", params="email")
	@ResponseBody
	public String emailCheck(String email) {
		boolean exist = service.hasMemberEmail(email);
		
		if (exist) {
			return "notOk";
		} else {
			return "ok";
		}
	}
	
	// 회원가입 nickName 중복체크
	@GetMapping(path="check", params="nickName")
	@ResponseBody
	public String nickNameCheck(String nickName) {
		boolean exist = service.hasMemberNickName(nickName);
		
		if (exist) {
			return "notOk";
		} else {
			return "ok";
		}
	}
	
	// 회원가입 목록보기
	@GetMapping("list")
	public void list(Model model) {
		List<MemberDto> list = service.listMember();
		model.addAttribute("memberList", list);
		
		// jsp (id, password, email, nickName, inserted) table로 보여주세요.
		// ORDER BY inserted DESC
	}
	
	// 선택 아이디 회원정보
	@GetMapping("get")
	public void getMember(String id, Model model) {
		MemberDto member = service.getMemberById(id);
		
		model.addAttribute("member", member);
	}
	
	// get.jsp 회원 탈퇴
	@PostMapping("remove")
	public String removeMember(MemberDto dto, RedirectAttributes rttr) {
		boolean success = service.removeMember(dto);
		
		if (success) { // 암호 입력 후 성공하면 회원탈퇴
			rttr.addFlashAttribute("message", "회원 탈퇴 되었습니다.");
			return "redirect:/board/list";
		} else { // 암호 실패하면 회원정보 화면으로 돌아가도록
			// 돌아갈땐 id(primary key)로 확인, id 해당 회원 정보 화면으로 이동
			rttr.addAttribute("id", dto.getId()); 
			return "redirect:/member/get";
		}
	}
}
