package com.spring.member.service;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.spring.member.dao.MemberDAO;
import com.spring.member.vo.MemberVO;

public class MemberServiceImpl implements MemberService {
	private MemberDAO memberDAO;
	
	
	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}

	@Override
	public List listMembers() throws DataAccessException {
		List memberList=memberDAO.selectAllMembers();
		return memberList;
	}

	@Override
	public void addMember(MemberVO memVO) throws DataAccessException {
		memberDAO.addMember(memVO);
	
	}
	
}
