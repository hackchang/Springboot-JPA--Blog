package com.haechang.blog.controller.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haechang.blog.config.auth.PrincipalDetail;
import com.haechang.blog.model.Board;
import com.haechang.blog.model.RoleType;
import com.haechang.blog.model.User;
import com.haechang.blog.repository.BoardRepository;

//스프링이 컴포넌트 스캔을 통해 bean에 등록해줌 (IoC)
@Service
public class BoardService {
	
	@Autowired
	private BoardRepository boardRepository;
	
	@Transactional
	public void write(Board board, User user) { // title, content
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}

	public Page<Board> boardList(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}
}
