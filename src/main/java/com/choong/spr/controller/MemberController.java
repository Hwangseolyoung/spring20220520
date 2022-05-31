package com.choong.spr.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
	public String getMember(String id, Principal principal, HttpServletRequest request, Model model) {
		// 회원권한 조건 CRUD 메소드 추가(admin, 회원만 수정 삭제 가능)
		if (hasAuthOrAdmin(id, principal, request)) {
			MemberDto member = service.getMemberById(id);
			model.addAttribute("member", member);
			
			// null로 리턴하면 요청 경로가 view(get)으로 간다.
			return null;
			
		}
		
		// 회원이 아니면 로그인 화면으로 이동
		return "redirect:/member/login"; 
		
		
	}
	
	// 회원권한 조건 CRUD 메소드 추가(여러코드에서 중복 사용됨)
	// admin이면 회원정보 볼수있도록 추가 HttpServletRequest request
	// 로그인한 회원만 자기 정보만 볼 수 있도록 수정 Principal principal
	private boolean hasAuthOrAdmin(String id, Principal principal, HttpServletRequest req) {
		return req.isUserInRole("ROLE_ADMIN") || 
				(principal != null && principal.getName().equals(id));
	}
	
	// get.jsp 회원 탈퇴
	@PostMapping("remove")
	public String removeMember(MemberDto dto, 
			Principal principal, 
			HttpServletRequest req,
			RedirectAttributes rttr) {
		// 회원권한 조건 CRUD 메소드 추가(admin, 회원만 수정 삭제 가능)
		if (hasAuthOrAdmin(dto.getId(), principal, req)) {
			boolean success = service.removeMember(dto);
			
			if (success) { // 암호 입력 후 성공하면 회원탈퇴
				rttr.addFlashAttribute("message", "회원 탈퇴 되었습니다.");
				return "redirect:/board/list";
			} else { // 암호 실패하면 회원정보 화면으로 돌아가도록
				// 돌아갈땐 id(primary key)로 확인, id 해당 회원 정보 화면으로 이동
				rttr.addAttribute("id", dto.getId()); 
				return "redirect:/member/get";
			}
			
		} else {
			// 회원이 아니면 로그인 화면으로 이동
			return "redirect:/member/login";
		}
		
	}
	
	// 수정된 회원 정보 업데이트
	@PostMapping("modify") // 파라미터로 넘어온 oldPassword 명시(기존 password와 같은지 확인해야함)
	public String modifyMember(MemberDto dto, String oldPassword, 
			Principal principal,
			HttpServletRequest req,
			RedirectAttributes rttr) {
		// 회원권한 조건 CRUD 메소드 추가(admin, 회원만 수정 삭제 가능)
		if (hasAuthOrAdmin(dto.getId(), principal, req)) {
			boolean success = service.modifyMember(dto, oldPassword);
			
			if(success) {
				rttr.addFlashAttribute("message", "회원 정보가 수정되었습니다.");
			} else {
				rttr.addFlashAttribute("message", "회원 정보가 수정되지 않았습니다.");
			}
			
			rttr.addFlashAttribute("member", dto); // model object 객체를 모델처렁
			// 리다이렉트시 수정된 회원 아이디 query string으로 전달
			rttr.addAttribute("id", dto.getId()); // query string 객체를 스트링으로 표현
			
			return "redirect:/member/get";
			
		} else {
			// 회원이 아니면 로그인 화면으로 이동
			return "redirect:/member/login";
		}
		
		
				
	}
	
	// 로그인 화면
	@GetMapping("login")
	public void loginPage() {
		
	}
	
	// admin 전용 암호 초기화 화면
	@GetMapping("initpw")
	public void initpwPage() {
		
	}
	
	// admin 전용 암호 초기화(아이디->암호)
	@PostMapping("initpw")
	public String initpwProcess(String id, RedirectAttributes rttr) {
		
		boolean success = service.initPassword(id);
		
		if (success) {
			rttr.addFlashAttribute("message", "회원정보가 초기화되었습니다.");
			
		} else {
			rttr.addFlashAttribute("message", "회원정보 초기화에 실패하였습니다.");
		}
		
		return "redirect:/board/list";
	}
	
}
