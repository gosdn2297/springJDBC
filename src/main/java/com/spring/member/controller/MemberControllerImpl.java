package com.spring.member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.spring.member.service.MemberService;
import com.spring.member.vo.MemberVO;

public class MemberControllerImpl extends MultiActionController implements MemberController{
	private MemberService memberService;
	
	
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	@Override
	public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName=getViewName(request);
		List memberList=memberService.listMembers();
		ModelAndView mav=new ModelAndView(viewName);
		mav.addObject("memberList", memberList);
		return mav;
	}
	
	//request 객체에서 URL 요청명을 가져와서 .do와 폴더를 제외한 요청명을 구하는 메서드
	 private String getViewName(HttpServletRequest request)throws Exception{
	      String contextPath=request.getContextPath();//호출한 context 받게됌 /springMVC값을 받음
	      String uri=(String)request.getAttribute("javax.servlet.include.request_uri"); //실제 uri값을 넘겨줌
	      if(uri == null || uri.trim().equals("")) {//uri가 null값이거나 공백일떼
	         uri=request.getRequestURI();//uri를 값을 받아온다
	      }
	      int begin=0, end;
	      if(!((contextPath == null) || "".equals(contextPath))) { //contextPath=/springMVC가 null값이 아니고 "".equals(contextPath)가 공백이 아닐시 ()왜 하나 더있는지 몰?루
	         begin=contextPath.length(); //contextPath의 길이를 넣어줌 why?
	      }
	      if(uri.indexOf(";") != -1) {   //;이 없으면 통과
	         end=uri.indexOf(";");       //;가 있으면 ;뒤로 end위치를 변경
	      }else if(uri.indexOf("?") != -1) { //;이 없으면 통과
	         end=uri.indexOf(";");          //?가 있으면 ?뒤로 end위치를 변경
	      }else {
	         end=uri.length();
	      }
	      String fileName=uri.substring(begin,end);
	      if(fileName.lastIndexOf(".") != -1) {
	         fileName=fileName.substring(0,fileName.lastIndexOf(".")); //.을 제와한 나머지 문자 memberform.do일시 memberform가져옴
	      }
	      if(fileName.lastIndexOf("/") != -1) {
	    	//주소에 메소드 이름과 같은걸 뽑아냄 주소 맵핑관련인듯 naver.com/login이듯이 login으로 만들어줌
	         fileName=fileName.substring(fileName.lastIndexOf("/"), fileName.length());
	      }
	      return fileName;
	      
	   }

	 @Override
	   public ModelAndView addMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
	      request.setCharacterEncoding("utf-8");
	      MemberVO memVO = new MemberVO();
	      String id = request.getParameter("id");
	      String pwd = request.getParameter("pwd");
	      String name = request.getParameter("name");
	      String email = request.getParameter("email");
	      memVO.setId(id);
	      memVO.setPwd(pwd);
	      memVO.setName(name);
	      memVO.setEmail(email);
	      memberService.addMember(memVO);
	      List memberList = memberService.listMembers();
	      ModelAndView mav = new ModelAndView("listMembers");
	      mav.addObject("memberList",memberList);
	      return mav;
	   }


	@Override
	public ModelAndView memberForm(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName=getViewName(request);
		ModelAndView mav=new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}

}
