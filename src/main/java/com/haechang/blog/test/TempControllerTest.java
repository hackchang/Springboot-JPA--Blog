package com.haechang.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

//사용자 요청 -> 응답(HTML)
//@Controller

//사용자 요청 -> 응답 ( Data )
@Controller
public class TempControllerTest {
	
	@GetMapping("/temp/home")
	//http://localhost:8000/blog/temp/home
	public String tempHome() {
		System.out.println("tempHome()");
		
		//파일리턴 기본경로 : src/main/resources/static 이므로 /추가
		return "/home.html";
	}
	
	@GetMapping("/temp/img")
	public String tempImage() {
		return "/a.png";
	}
	
	@GetMapping("/temp/jsp")
	public String tempJsp() {
		//prefix: /WEB-INF/views/
		//suffix: .jsp
		//풀네임 : /WEB-INF/views/home.jsp.jsp
		return "home";
	}

}
