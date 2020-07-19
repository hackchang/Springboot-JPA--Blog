package com.haechang.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
// 스프링이 com.haechang.blog 이하 스캔해서 
//특정 어노테이션이 붙어있는 클래스 파일을 new 해서(IOC) 스프링 컨테이너에서 관리해준다.
public class BlogControllerTest {
	
	//http://localhost:8080/test/hello
	@GetMapping("/test/hello")
	public String hello() {
		return "<h1>Hello spring boot</h1>";
	}
}
