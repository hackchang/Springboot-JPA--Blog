package com.haechang.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.haechang.blog.model.RoleType;
import com.haechang.blog.model.User;
import com.haechang.blog.repository.UserRepository;

//html파일이 아니라 데이터를 리턴해주는 controller
@RestController
public class DummyControllerTest {
	
	@Autowired //의존성 주입 ( DI )
	private UserRepository userRepository;
	
	// save함수는 id를 전달하지 않으면 insert해주고,
	// save함수는 id를 전달하면 해당 id에 대한 데이터가 있으면 update해주고,
	// save함수는 id를 전달하면 해당 id에 대한 데이터가 벗으면 insert를 한다.
	//email, pw

	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
		userRepository.deleteById(id);
		}
		catch(EmptyResultDataAccessException e){
			return "삭제에 실패했습니다. 해당 id는 존재하지 않습니다.";
		}
		
		return "삭제되었습니다. id : "+id;
	}
	
	@Transactional // 함수 종료시 자동 commit함.
	@PutMapping("/dummy/user/{id}") // GET PUT은 알아서 구분가능
	public User updateUser(@PathVariable int id,  @RequestBody User requestUser) { 
		//json 데이터요청 -> Java Object(Message Converter의 Jackson 라이브러리가)로 변환해서 받아줘요.
		System.out.println("id : "+id);
		System.out.println("password: "+requestUser.getPassword());
		System.out.println("email : "+requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패했습니다.");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		//userRepository.save(user);
		
		//더티 체킹
		return user;
	}
	
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}
	
	// 1페이지 당 2건에 데이터를 리턴받아 볼 예정
	// size로 수정
	@GetMapping("/dummy/user")
	public Page<User> pageList(@PageableDefault(size=2, sort="id", direction = Sort.Direction.DESC) Pageable pageable){
		Page<User> pagingUser = userRepository.findAll(pageable);
		
		if(pagingUser.isLast()) {
			System.out.println("last");
		}
		List<User> users = pagingUser.getContent();
		return pagingUser;
	}
	
	//{id} 주소로 파라미터를 전달받을 수 있다.
	// http://localhost:8000/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
//			람다식
//			User user = userRepository.findById(id).orElseThrow(()=>{
//				return new IllegalArgumentException("해당 사용자는 없습니다.");
//			});
		
		// user/4를 찾으면 내가 DB에서 못찾으면 user가 null이 되기 때문에
		// return이 null되어서 문제생길 수 있으니, 
		// Optional로 너의 User 객체 가져올테니 null인지 판단해서 return
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {
			@Override
			public IllegalArgumentException get() {
				return new IllegalArgumentException("해당 유저는 없습니다. id : "+id);
			}
		});
		// 요청 : 웹브라우저
		// user 객체 = 자바 Object 
		// 변환 ( 웹브라우저가 이해할 수 있는 데이터 ) -> Json (예전에는 GSON 라이브러리 사용)
		// 스프링부트 = MessageConverter라는 애가 응답시 자동으로 작동한다.
		// 만약 자바 Object 리턴하면 MessageConverter가 Jackson 라이브러리를 호출해서
		// user 오브젝트는 JSON으로 변환해서 브라우저에게 던져줍니다.
		return user;
	}

	// http://localhost:8000/blog/dummy/join ( 요청 )
	// http의 body에 username,password, email 데이터를 가지고 요청
	@PostMapping("/dummy/join")
	public String join(User user) { // key=value ( 약속된 규칙 )
		System.out.println("id : "+user.getId());
		System.out.println("username : "+user.getUsername());
		System.out.println("password : "+user.getPassword());
		System.out.println("email : "+user.getEmail());
		System.out.println("role : "+user.getRole());
		System.out.println("createDate : "+user.getCreateDate());
		
		user.setRole(RoleType.USER);
		userRepository.save(user);
		return "회원가입이 완료되었습니다.";
	}
}
