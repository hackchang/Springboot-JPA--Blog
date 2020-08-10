<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="../layout/header.jsp"%>

<div class="container">
		<button class="btn btn-secondary" onclick="history.back()">뒤로가기</button>
		<c:if test="${board.user.id == principal.user.id }">
			<a href="/board/${board.id }/updateForm" class="btn btn-warning" }>수정</a>
			<button id="btn-delete" class="btn btn-danger">삭제</button>
		</c:if>
		<button id="btn-like-save" class="btn btn-primary">좋아요</button>
		<br /><br />
		<div>
			글 번호 : <span id="id"><i>${board.id }</i></span>
			작성자 : <span id="userid"><i>${board.user.username }</i></span>
			조회수 : <span id="viewcount"><i>${board.viewcount }</i></span>
			좋아요 :  <span id="likecount"><i>${board.likecount }</i></span>
		</div>
		<br />
		<div>
			<h3>${board.title }</h3>
		</div>
		<hr />
		<div>
			<h1>${board.content }</h1>
		</div>
		<hr />
		
		<div class="card">
			<form>			
			<input type="hidden" value="${principal.user.id }" id="userId">
			<input type="hidden" value="${board.id }" id="boardId">
			<div class="card-body">
			<textarea id ="reply-content" class="form-control" rows="1"></textarea></div>
			<div class="card-footer"><button type="button"  id ="btn-reply-save" class="btn btn-primary">등록</button></div>
			</form>
		</div>
		<br/>
		<div class="card">
			<div class="card-header">댓글 리스트</div>
			<ul id="reply-box" class="list-group">
				<c:forEach var="reply" items="${board.replys }">
				<li id="reply-${reply.id }" class="list-group-item d-flex justify-content-between">
					<div>${reply.content }</div>
					<div class="d-flex">
						<div class="font-italic">작성자 : ${reply.user.username } &nbsp;</div>
						<c:if test="${reply.user.id == principal.user.id }">
							<button onclick="index.replyDelete(${board.id},${reply.id })" class="badge">삭제</button>
					</c:if>
					</div>
			</li>
				</c:forEach>
			</ul>
		</div>
</div>
<script src="/js/board.js"></script>
<%@include file="../layout/footer.jsp"%>>
