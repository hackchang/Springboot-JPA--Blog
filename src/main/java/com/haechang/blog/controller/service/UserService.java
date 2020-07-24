package com.haechang.blog.controller.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haechang.blog.model.RoleType;
import com.haechang.blog.model.User;
import com.haechang.blog.repository.UserRepository;

//스프링이 컴포넌트 스캔을 통해 bean에 등록해줌 (IoC)
@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder encoder;
	
	@Transactional
	public void user_reg(User user) {
			String rawPassword = user.getPassword(); // 1234 원문
			String encPassword = encoder.encode(rawPassword); //1234 해쉬
			user.setRole(RoleType.USER);
			user.setPassword(encPassword);
			userRepository.save(user);
	}
}
