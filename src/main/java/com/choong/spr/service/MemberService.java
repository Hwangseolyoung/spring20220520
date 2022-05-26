package com.choong.spr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.choong.spr.domain.MemberDto;
import com.choong.spr.mapper.MemberMapper;

@Service
public class MemberService {
	
	@Autowired
	private MemberMapper mapper;
	
	// 평문암호를 암호화(encoding)
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	// 회원가입 
	public boolean addMember(MemberDto member) {
		
		// 평문암호를 암호화(encoding)
		String encodedPassword = passwordEncoder.encode(member.getPassword());
				
		// 암호화된 암호를 다시 세팅
		member.setPassword(encodedPassword);
				
		return mapper.insertMember(member) == 1;
	}
	
	// 회원가입 id 중복체크
	public boolean hasMemberId(String id) {
		// TODO Auto-generated method stub
		return mapper.countMemberId(id) > 0;
	}
	
	// 회원가입 email 중복체크
	public boolean hasMemberEmail(String email) {
		int cnt = mapper.countMemberEmail(email);
		return cnt > 0;
	}
	
	// 회원가입 nickName 중복체크
	public boolean hasMemberNickName(String nickName) {
		// TODO Auto-generated method stub
		return mapper.countMemberNickName(nickName) > 0;
	}
	
	// 회원가입 목록보기
	public List<MemberDto> listMember() {
		
		return mapper.selectAllMember();
	}
	
	// 선택 아이디 회원정보
	public MemberDto getMemberById(String id) {
		// TODO Auto-generated method stub
		return mapper.selectMemberById(id);
	}
	
	// get.jsp 회원 탈퇴
	public boolean removeMember(MemberDto dto) {// id(primary key)로 확인
		MemberDto member = mapper.selectMemberById(dto.getId());
		
		// 평문암호
		String rawPW = dto.getPassword();
		// 인코딩암호
		String encodedPW = member.getPassword();
		
		// matches(rawPassword, encodedPassword)
		// 사용자가 입력한 pw와 인코딩된 pw가 일치하는지 확인해주는 메소드
		if(passwordEncoder.matches(rawPW, encodedPW)) {
			return mapper.deleteMemberById(dto.getId()) == 1;
		}
		
		/* 암호 인코더 하기전 코드
		// member의 password와 입력받은 password가 일치하면 1을 리턴하고 true
		if(member.getPassword().equals(dto.getPassword())) {
			return mapper.deleteMemberById(dto.getId()) == 1;
		}
		*/
		
		return false;
	}

	public boolean modifyMember(MemberDto dto, String oldPassword) {
		// db에서 member 읽어서 기존 정보를 oldMember로 담고
		MemberDto oldMember = mapper.selectMemberById(dto.getId());
		
		// 기존 인코딩암호 String으로 담기
		String encodedPW = oldMember.getPassword();
		
		// 기존 password가 파라미터로 넘어온 oldPassword와 일치할때만 계속 진행
		if(passwordEncoder.matches(oldPassword, encodedPW)) {
			// 수정 암호 인코딩 코드 추가
			dto.setPassword(passwordEncoder.encode(dto.getPassword()));
			
			return mapper.updateMember(dto) == 1;
		}
		
		
		/* 암호 인코더 하기전 코드
		// 기존 password가 파라미터로 넘어온 oldPassword와 일치할때만 계속 진행
		if(oldMember.getPassword().equals(oldPassword)) {
			return mapper.updateMember(dto) == 1;
		}
		*/
		
		return false;
	}

	
	

}
