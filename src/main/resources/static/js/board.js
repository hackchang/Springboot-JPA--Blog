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
		$("#btn-reply-save").on("click",()=>{
			this.replySave();
		});
		$("#btn-like-save").on("click",()=>{
			this.likeSave();
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
	replySave:  function(){
		let data = {
				userId: $("#userId").val(),
				boardId: $("#boardId").val(),
				content: $("#reply-content").val()
		}
		$.ajax({
			type:"POST",
			url: `/api/board/${data.boardId}/reply`,
			data: JSON.stringify(data),
			contentType : "application/json; charset=utf-8", 
			dataType: "json" 
		}).done(function(res){
			alert("댓글 작성이 완료되었습니다.");
			location.href=`/board/${data.boardId}`;
		}).fail(function(res){
			alert(JSON.stringify(res));
		}); 
	},
	replyDelete:  function(boardId, replyId){
		$.ajax({
			type:"DELETE",
			url: `/api/board/${boardId}/reply/${replyId}`,
			dataType: "json" 
		}).done(function(res){
			alert("댓글 삭제가 완료되었습니다.");
			location.href=`/board/${boardId}`;
		}).fail(function(res){
			alert(JSON.stringify(res));
		}); 
	},
	likeSave: function(){
		let data= {
			userId: $("#userId").val(),
			boardId: $("#boardId").val()
		}
		$.ajax({
			type:"POST",
			url: `/api/board/{boardId}/like`,
			data: JSON.stringify(data),
			contentType : "application/json; charset=utf-8", 
			dataType: "json" 
		}).done(function(res){
			alert("좋아요가 완료되었습니다.");
			location.href=`/board/${data.boardId}`;
		}).fail(function(res){
			alert(JSON.stringify(res));
		}); 
	},
	likeDelete: function(){
		let data= {
			userId: $("#userId").val(),
			boardId: $("#boardId").val()
		}
		$.ajax({
			type:"DELETE",
			url: `/api/board/{boardId}/like`,
			dataType: "json" 
		}).done(function(res){
			alert("좋아요가 취소되었습니다.");
			location.href=`/board/${data.boardId}`;
		}).fail(function(res){
			alert(JSON.stringify(res));
		}); 
	}
}

index.init();