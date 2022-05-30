package com.choong.spr.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.choong.spr.domain.BoardDto;
import com.choong.spr.domain.MemberDto;
import com.choong.spr.mapper.BoardMapper;
import com.choong.spr.mapper.MemberMapper;
import com.choong.spr.mapper.ReplyMapper;

@Service
public class MemberService {
	
	@Autowired
	private MemberMapper mapper;
	
	@Autowired
	private ReplyMapper replyMapper;
	
	@Autowired
	private BoardMapper boardMapper;
	
	// 평문암호를 암호화(encoding)
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	// 회원가입 
	public boolean addMember(MemberDto member) {
		System.out.println(member);
		// 평문암호를 암호화(encoding)
		String encodedPassword = passwordEncoder.encode(member.getPassword());
				
		// 암호화된 암호를 다시 세팅
		member.setPassword(encodedPassword);
		
		// insert member
		int cnt1 = mapper.insertMember(member);
		
		System.out.println(member);
		// 권한 테이블, 권한 추가 
		int cnt2 = mapper.insertAuth(member.getId(), "ROLE_USER");
		
		return cnt1 == 1 && cnt2 == 1;
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
	@Transactional
	public boolean removeMember(MemberDto dto) {// id(primary key)로 확인
		MemberDto member = mapper.selectMemberById(dto.getId());
		
		// 평문암호
		String rawPW = dto.getPassword();
		// 인코딩암호
		String encodedPW = member.getPassword();
		
		// matches(rawPassword, encodedPassword)
		// 사용자가 입력한 pw와 인코딩된 pw가 일치하는지 확인해주는 메소드
		if(passwordEncoder.matches(rawPW, encodedPW)) {
			// 삭제 순서가 중요하다!
			// 댓글 삭제
			replyMapper.deleteByMemberId(dto.getId());
			
			// 해당 멤버가 쓴 게시글에 달린 다른사람 댓글 삭제
			List<BoardDto> boardList = boardMapper.listByMemberId(dto.getId());
			for (BoardDto board : boardList) {
				replyMapper.deleteByBoardId(board.getId());
			}
			
			// 해당 멤버가 쓴 게시글 삭제
			boardMapper.deleteByMemberId(dto.getId());
			
			// 권한 테이블, 권한 삭제(FOREIGN KEY 제약사항으로 먼저 삭제해주기)
			mapper.deleteAuthById(dto.getId());
			// member 테이블 삭제
			int cnt = mapper.deleteMemberById(dto.getId());
			return cnt == 1 ; // 권한이 2개 이상일 수 있으므로 member테이블만 체크
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
