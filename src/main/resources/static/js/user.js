let index = {
	init: function(){
		$("#btn-save").on("click",()=>{ // function(){} , -> ()=> this를 바인딩 하기 위해서
			this.save();
		});
	},
	save:  function(){
		//alert('user의 save함수 호출됨.');
		let data = {
				username: $("#username").val(),
				password: $("#password").val(),
				email: $("#email").val()
		}
		//console.log(data);
		
		//ajax호출시 default가 비동기 호출
		// ajax통신을 이용해서 3개의 데이터를 json으로 변경해서 insert 요청
		// ajax가 통신 성공하고, json을 리턴하면 자동으로 자바 오브젝트로 변환해준다/
		$.ajax({
			type:"POST",
			url:"/auth/registerProc",
			data: JSON.stringify(data), //http body 데이터
			contentType : "application/json; charset=utf-8", // body 데이터 타입(MIME)
			dataType: "json" // 요청을 서버로해서 응답이 왔을 때 기본은 문자열 , 아래 response가 js object로 변경 
		}).done(function(res){
			console.log(res);
			alert("회원가입이 완료되었습니다.");
			location.href="/";
		}).fail(function(res){
			alert(JSON.stringify(res));
		}); 
		
		
	}
}

index.init();