package com.haechang.blog.controller.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.haechang.blog.controller.dto.ReplySaveRequestDto;
import com.haechang.blog.model.Board;
import com.haechang.blog.model.Reply;
import com.haechang.blog.model.User;
import com.haechang.blog.repository.BoardRepository;
import com.haechang.blog.repository.ReplyRepository;
import com.haechang.blog.repository.UserRepository;

//스프링이 컴포넌트 스캔을 통해 bean에 등록해줌 (IoC)
@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ReplyRepository replyRepository;

	@Transactional
	public void write(Board board, User user) { // title, content
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}

	@Transactional(readOnly = true)
	public Page<Board> boardList(Pageable pageable) {
		return boardRepository.findAll(pageable);
	}

	@Transactional(readOnly = true)
	public Board detail(int id) {
		return boardRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("글 상세보기 실패 : 아이디를 찾을 수 없습니다.");
		});
	}

	@Transactional
	public void delete(int id) {
		boardRepository.deleteById(id);
	}

	@Transactional
	public void update(int id, Board requestBoard) {
		Board board = boardRepository.findById(id).orElseThrow(() -> {
			return new IllegalArgumentException("글 찾기 실패 : 아이디를 찾을 수 없습니다.");
		}); // 영속화
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		// 해당 함수 종료시에 (Service가 종료될 때) 트랜잭션이 종료된다. 이 때 더티체킹이 일어난다. ( 자동업데이트 (db -
		// flush))
	}

	@Transactional
	public void reply(ReplySaveRequestDto replySaveRequestDto) {
		User user = userRepository.findById(replySaveRequestDto.getUserId()).orElseThrow(() -> {
			return new IllegalArgumentException("댓글 쓰기 실패 : 유저 ID를 찾을 수 없습니다.");
		}); // 영속화
		Board board = boardRepository.findById(replySaveRequestDto.getBoardId()).orElseThrow(() -> {
			return new IllegalArgumentException("댓글 쓰기 실패 : 게시글 ID를 찾을 수 없습니다.");
		}); // 영속화

		Reply reply = new Reply();
		reply.write(user, board, replySaveRequestDto.getContent());

		replyRepository.save(reply);
	}
}
