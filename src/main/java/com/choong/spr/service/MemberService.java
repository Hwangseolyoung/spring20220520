package com.choong.spr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.choong.spr.domain.MemberDto;
import com.choong.spr.mapper.MemberMapper;

@Service
public class MemberService {
	
	@Autowired
	private MemberMapper mapper;
	
	// 회원가입 
	public boolean addMember(MemberDto member) {
		int cnt = mapper.insertMember(member);
		return cnt == 1;
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
		// member의 password와 입력받은 password가 일치하면 1을 리턴하고 true
		if(member.getPassword().equals(dto.getPassword())) {
			return mapper.deleteMemberById(dto.getId()) == 1;
		}
		
		return false;
	}

	
	

}
