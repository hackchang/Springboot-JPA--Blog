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
		String encPassword = encoder.encode(rawPassword); // 1234 해쉬
		user.setRole(RoleType.USER);
		user.setPassword(encPassword);
		userRepository.save(user);
	}

	@Transactional
	public void user_update(User user) {
		User persistance = userRepository.findById(user.getId()).orElseThrow(() -> {
			return new IllegalArgumentException("수정 실패 : 아이디를 찾을 수 없습니다.");
		}); // 영속화

		// Validate 체크 = > oauth값 없을 때만 email, password 변경가능.
		if (persistance.getOauth() == null || persistance.getOauth().equals("")) {
			String encPassword = encoder.encode(user.getPassword());
			persistance.setPassword(encPassword);
			persistance.setEmail(user.getEmail());
		}

	}

	@Transactional
	public User user_find(String username) {
		User user = userRepository.findByUsername(username).orElseGet(() -> {
			return new User();
		});
		return user;
	}
}
