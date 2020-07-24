package com.haechang.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.haechang.blog.controller.dto.ResponseDto;
import com.haechang.blog.controller.service.UserService;
import com.haechang.blog.model.RoleType;
import com.haechang.blog.model.User;

@RestController
public class UserApiController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/auth/registerProc")
	public ResponseDto<Integer> save(@RequestBody User user) { // username, password, email
		System.out.println("UserApiController : save 호출됨.");
		//실제 DB insert하고 아래에서 리턴해주면됨.
		userService.user_reg(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}

}
