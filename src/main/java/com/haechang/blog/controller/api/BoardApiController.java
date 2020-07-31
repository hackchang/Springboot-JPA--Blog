package com.haechang.blog.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.haechang.blog.config.auth.PrincipalDetail;
import com.haechang.blog.controller.dto.ResponseDto;
import com.haechang.blog.controller.service.BoardService;
import com.haechang.blog.model.Board;
import com.haechang.blog.model.Reply;

@RestController
public class BoardApiController {

	@Autowired
	private BoardService boardService;

	@PostMapping("/api/board/writeProc")
	public ResponseDto<Integer> save(@RequestBody Board board, @AuthenticationPrincipal PrincipalDetail principal) {
		boardService.write(board, principal.getUser());
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}

	@DeleteMapping("/api/board/{id}")
	public ResponseDto<Integer> deleteById(@PathVariable int id) {
		boardService.delete(id);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}

	@PutMapping("/api/board/{id}")
	public ResponseDto<Integer> update(@PathVariable int id, @RequestBody Board board) {
		boardService.update(id, board);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}

	@PostMapping("/api/board/{boardId}/reply")
	public ResponseDto<Integer> replySave(@PathVariable int boardId, @RequestBody Reply reply,
			@AuthenticationPrincipal PrincipalDetail principal) {
		boardService.reply(principal.getUser(), boardId, reply);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
}
