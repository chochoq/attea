package com.example.controller;

import java.io.File;
import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.util.WebUtils;

import com.example.domain.Criteria;
import com.example.domain.PageMaker;
import com.example.domain.UserVO;
import com.example.mapper_oracle.GroupMapper;
import com.example.mapper_oracle.MypageMapper;
import com.example.service.ClubService;
import com.example.service.GroupService;
import com.example.service.MasterService;

@Controller
public class MypageController {

	@Resource(name = "uploadPath")
	String path;

	@Autowired
	MypageMapper mapper;
	@Autowired
	GroupMapper gmapper;
	@Autowired
	MasterService mservice;
	@Autowired
	ClubService cservice;
	@Autowired
	GroupService gservice;

	// 회원정보 수정page로 연결한다
	@RequestMapping("myPage_userUpdate")
	public void myPage_userUpdate() {
	}

	// 내 정보 수정
	@RequestMapping(value = "myinfoUpdate", method = RequestMethod.POST)
	public String myinfoupdate(HttpSession session, UserVO vo, MultipartHttpServletRequest multi) throws Exception {
		String id = (String) session.getAttribute("id");

		// 사진 삭제 부분
		UserVO oldvo = mapper.myinfo(id);
		new File(path + "/" + oldvo.getImage()).delete();

		// 사진
		MultipartFile file = multi.getFile("file");
		if (file.isEmpty()) {
			vo.setImage("default.jpg");
		} else if (!file.isEmpty()) {
			String image = System.currentTimeMillis() + "_" + file.getOriginalFilename();
			file.transferTo(new File(path + "/" + image));
			vo.setImage(image);
		}

		vo.setId(id);
		mapper.myinfoupdate(vo);
		return "redirect:/";
	}

	// 회원 탈티
	@RequestMapping(value = "removeUser")
	public String removeuser(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		String id = (String) session.getAttribute("id");
		// session 삭제
		session.invalidate();
		// 쿠키삭제
		Cookie cookie1 = WebUtils.getCookie(request, "id");
		if (cookie1 != null) {
			cookie1.setPath("/");
			cookie1.setMaxAge(0);
			response.addCookie(cookie1);
		}
		mservice.mudelete(id);
		return "redirect:/";
	}

	// Mygroup 마이페이지에 연결
	@RequestMapping("myPage_club")
	public void myPage_club() {
	}

	// 내가 가입한 동아리 list를 보내준다._ 검색기능 포함
	@RequestMapping("myclubinfo")
	@ResponseBody
	public HashMap<String, Object> myclist(int page, Criteria cri,HttpSession session) {
		HashMap<String, Object> map = new HashMap<>();
		cri.setId((String) session.getAttribute("id"));
		
		PageMaker pm = new PageMaker();
		cri.setPage(page);
		pm.setCri(cri);
		pm.setTotalCount(mapper.myctotalCount(cri));

		map.put("pm", pm);
		map.put("myclist", mapper.myclist(cri));
		return map;
	}

	// 동아리 탈퇴 및 삭제
	@RequestMapping(value = "removeclub")
	@ResponseBody
	public String removeclub(String c_code,HttpSession session) {
		String id = (String) session.getAttribute("id");
		cservice.deletemyClub(id,c_code);
		return "redirect:myclub";
	}

//	
//	// 관리자 group관리 page에 연결
//	@RequestMapping("myPage_group")
//	public void myPage_group() {
//	}
//
//	// group list를 가져온다_ 검색기능까지 포함
//	@RequestMapping("myglist")
//	@ResponseBody
//	public HashMap<String, Object> mYglist(int page, Criteria cri,HttpSession session) {
//		String id = (String) session.getAttribute("id");
//		HashMap<String, Object> map = new HashMap<>();
//		PageMaker pm = new PageMaker();
//		cri.setPage(page);
//		pm.setCri(cri);
//		pm.setTotalCount(mapper.mygtotalCount(cri));
//
//		map.put("pm", pm);
//		map.put("myglist", gmapper.mygroup(id));
//
//		return map;
//	}
//
//	// group을 삭제한다.
//	@RequestMapping(value = "removegroup", method = RequestMethod.POST)
//	@ResponseBody
//	public void removegroup(String id, int g_code) {
//		gservice.deletemygroup(id, g_code);
//	}
}