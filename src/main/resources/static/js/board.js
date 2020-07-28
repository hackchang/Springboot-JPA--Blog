let index = {
	init: function(){
		$("#btn-save").on("click",()=>{ // function(){} , -> ()=> this를 바인딩 하기 위해서
			this.save();
		});
		$("#btn-delete").on("click",()=>{
			this.deleteById();
		});
		$("#btn-update").on("click",()=>{
			this.update();
		});
	},
	save:  function(){
		//alert('user의 save함수 호출됨.');
		let data = {
				title: $("#title").val(),
				content: $("#content").val()
		}
		
		$.ajax({
			type:"POST",
			url:"/api/board/writeProc",
			data: JSON.stringify(data),
			contentType : "application/json; charset=utf-8", 
			dataType: "json" 
		}).done(function(res){
			alert("글쓰기가 완료되었습니다.");
			location.href="/";
		}).fail(function(res){
			alert(JSON.stringify(res));
		}); 
	},
	deleteById:  function(){
	
		let id = $("#id").text();
	
		$.ajax({
			type:"DELETE",
			url:"/api/board/"+id,
			dataType: "json" 
		}).done(function(res){
			alert("삭제가 완료되었습니다.");
			location.href="/";
		}).fail(function(res){
			alert(JSON.stringify(res));
		}); 
	},
	
	update:  function(){
		
		let data = {
				id: $("#id").val(),
				title: $("#title").val(),
				content: $("#content").val()
		}
		
		$.ajax({
			type:"PUT",
			url:"/api/board/"+data.id,
			data: JSON.stringify(data),
			contentType : "application/json; charset=utf-8", 
			dataType: "json" 
		}).done(function(res){
			alert("글수정이 완료되었습니다.");
			location.href="/";
		}).fail(function(res){
			alert(JSON.stringify(res));
		}); 
	},
}

index.init();