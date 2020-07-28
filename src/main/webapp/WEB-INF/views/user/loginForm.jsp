<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
	<form action="/auth/loginProc" method="post">
		<div class="form-group">
			<label for="username">Username:</label>
			 <input name="username" type="text" class="form-control" placeholder="Enter Username" id="username">
		</div>

		<div class="form-group">
			<label for="password">Password:</label> 
			<input name="password"  type="password" class="form-control" placeholder="Enter password" id="password">
		</div>
		
		<button id="btn-login" class="btn btn-primary">로그인</button>
		<a href="https://kauth.kakao.com/oauth/authorize?client_id=df9adbec236646c1c62d4e5945a9046d&redirect_uri=http://localhost:8000/auth/kakao/callback&response_type=code"><img height="38px" width="74px" src="/image/kakao_login_medium.png"></a>
	</form>
</div>
<%@include file="../layout/footer.jsp"%>>
