<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="my" tagdir="/WEB-INF/tags" %>
<%@ page import="java.util.*"%>
<% request.setCharacterEncoding("utf-8"); %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css" integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap/5.1.3/css/bootstrap.min.css" integrity="sha512-GQGU0fMMi238uA+a/bdWJfpUGKUkBdgfFdgBm72SUQ6BeyWjoY/ton0tEjH+OSH9iP4Dfh+7HM0I9f5eR0L/4w==" crossorigin="anonymous" referrerpolicy="no-referrer" />
<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" referrerpolicy="no-referrer"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>

<title>Insert title here</title>
</head>
<body>
	<my:navBar current="get"></my:navBar>
	
	<div>
	아이디 : <input type="text" value="${member.id }"/> <br />
	패스워드 : <input type="text" value="${member.password }"/> <br />
	이메일 : <input type="text" value="${member.email }"/> <br />
	닉네임 : <input type="text" value="${member.nickName }"/> <br />
	가입일시 : <input type="datetime-local" value="${member.inserted }"/> <br />
	</div>
	
	<div>
		<!-- bootstrap Modal 사용  -->
		<button data-bs-toggle="modal" data-bs-target="#modal1">삭제</button>
	</div>
	
	<!-- bootstrap Modal -->
	<div class="modal fade" id="modal1" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">탈퇴하시겠습니까?(암호입력)</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	      	<form id="form1" action="${appRoot }/member/remove" method="post">
				<input type="hidden" value="${member.id }"name="id" />
	      		암호 : <input type="password" name="password" />
	      	</form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
	        <!-- form="form1" 명시해주면 위 form 루트로 submit 역할을 함 -->
	        <button form="form1" type="submit" class="btn btn-danger">탈퇴</button>
	      </div>
	    </div>
	  </div>
	</div>
</body>
</html>