package com.choong.spr.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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
	
	// 회원 정보 수정 업데이트
	int updateMember(MemberDto dto);
	
	// 권한 테이블 권한추가 코드
	int insertAuth(@Param("id") String id, @Param("auth") String auth);
	
	// 권한 테이블, 권한 삭제
	int deleteAuthById(String id);


}
