package com.haechang.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.haechang.blog.controller.service.UserService;
import com.haechang.blog.model.KakaoProfile;
import com.haechang.blog.model.OAuthToken;
import com.haechang.blog.model.User;

//인증이 안된 사용자들이 들어갈 수 있는 경로  /auth/** 허용
// 그냥 주소가 / 이면 index.jsp 허용
// static이하에 있는 /js/**, /css/**, /image/** 허용

@Controller
public class UserController {

	@Value("${haechang.key")
	private String haechangKey;

	@Autowired
	private UserService userService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("/auth/registerForm")
	public String registerForm() {
		return "user/registerForm";
	}

	@GetMapping("/auth/loginForm")
	public String loginForm() {
		return "user/loginForm";
	}

	@GetMapping("/user/updateForm")
	public String updateForm() {
		return "user/updateForm";
	}

	@GetMapping("/auth/kakao/callback")
	public String kakaoCallback(String code) { // Data를 리턴해주는 컨트롤러 함수

		// POST방식으로 key=value 데이터를 요청(카카오쪽으로)
		// Retrofit2
		// OkHttp
		// RestTemplate

		RestTemplate rt = new RestTemplate();

		// HttpHeaders 오브젝트 생성
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// HttpBody 오브젝트 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "df9adbec236646c1c62d4e5945a9046d");
		params.add("redirect_uri", "http://localhost:8000/auth/kakao/callback");
		params.add("code", code);

		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

		// Http 요청하기 - Post방식으로, response 변수의 응답 받음.
		ResponseEntity<String> response = rt.exchange("https://kauth.kakao.com/oauth/token", HttpMethod.POST,
				kakaoTokenRequest, String.class

		);

		// GSON, JSON simple, ObjectMapper
		ObjectMapper objectMapper = new ObjectMapper();
		OAuthToken oauthToken = null;
		try {
			oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		System.out.println("카카오 액세스 토큰 : " + oauthToken.getAccess_token());

		RestTemplate rt2 = new RestTemplate();

		// HttpHeaders 오브젝트 생성
		HttpHeaders headers2 = new HttpHeaders();
		headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
		headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

		// HttpHeader와 HttpBody를 하나의 오브젝트에 담기
		HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 = new HttpEntity<>(headers2);

		// Http 요청하기 - Post방식으로, response 변수의 응답 받음.
		ResponseEntity<String> response2 = rt2.exchange("https://kapi.kakao.com/v2/user/me", HttpMethod.POST,
				kakaoProfileRequest2, String.class);

		ObjectMapper objectMapper2 = new ObjectMapper();
		KakaoProfile kakaoProfile = null;
		try {
			kakaoProfile = objectMapper.readValue(response2.getBody(), KakaoProfile.class);
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		System.out.println(response2.getBody());

		// User 오브젝트 : username, password, email
		System.out.println("아이디 : " + kakaoProfile.getId() + "\n이메일 : " + kakaoProfile.getKakao_account().getEmail());

		System.out.println("블로그서버 유저 네임 : " + kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId());
		System.out.println("블로그서버 유저 이메일 : " + kakaoProfile.getKakao_account().getEmail());
		// UUID 란 -> 중복되지 않는 어떤 특정 값을 만들어내는 알고리즘
		System.out.println("블로그서버 패스워드 : " + haechangKey);

		User kakaoUser = User.builder()
				.username(kakaoProfile.getKakao_account().getEmail() + "_" + kakaoProfile.getId())
				.password(haechangKey.toString()).email(kakaoProfile.getKakao_account().getEmail()).oauth("kakao")
				.build();

		User originUser = userService.user_find(kakaoUser.getUsername());
		if (originUser.getUsername() == null) {
			userService.user_reg(kakaoUser);
		}

		// 로그인 처리
		Authentication authentication = authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(kakaoUser.getUsername(), haechangKey));
		SecurityContextHolder.getContext().setAuthentication(authentication);

		return "redirect:/";
	}

}
