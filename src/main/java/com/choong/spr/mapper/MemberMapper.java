package com.choong.spr.mapper;

import java.util.List;

import com.choong.spr.domain.MemberDto;

public interface MemberMapper {
	
	// 회원가입 
	int insertMember(MemberDto member);
	
	// 회원가입 id 중복체크
	int countMemberId(String id);

	// 회원가입 email 중복체크
	int countMemberEmail(String email);

	// 회원가입 nickName 중복체크
	int countMemberNickName(String nickName);
	
	// 회원가입 목록보기
	List<MemberDto> selectAllMember();
	
	// 선택 아이디 회원정보
	MemberDto selectMemberById(String id);

	// get.jsp 회원 탈퇴
	int deleteMemberById(String id);


}
