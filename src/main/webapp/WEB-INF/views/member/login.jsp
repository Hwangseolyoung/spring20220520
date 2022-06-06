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
<script src="http://code.jquery.com/jquery-3.3.1.min.js"></script>

<title>Insert title here</title>

<!-- google font -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@500&display=swap" rel="stylesheet">

<style>
	/* reset style */
	*{
		margin: 0;
		padding: 0;
		box-sizing:border-box;
	}
	
	body {
		font-family: 'Noto Sans KR', sans-serif;
		display: flex;
		justify-content: center;
		align-items: center;
		height: 100vh;
		background-color:#e8f5e9;
		
	}
	
	body::before {
		content: "";
		position: absolute; z-index: 1;
		top:0; right: 0; botton:0; left: 0;
		background-color: rgba(0, 0, 0, .7);
	}
	
	.loginForm {position: relative; z-index: 2;}
	
	h1 {
		font-size:  40px; color: #00695c;
		text-align: center;
		margin-bottom: 60px;
	}
	.userInfo:first-child {margin-top: 0;}
	.userInfo {
		width: 400px; position: relative;
		margin-top: 20px;
	}
	.userInfo input {
		width: 100%;
		padding: 20px 10px 10px;
		background-color: transparent;
		border: none;
		border-bottom: 1px solid #999;
		font-size:  18px; color: #003d32; 
		outline: none;
	}
	.userInfo label {
		position: absolute; left: 10px; top:-13px;
		font-size: 18px; color: #999;
	}
	
	/* 실행 안됨
	.userInfo label.warning {
		color: red !important;
		animation: warning .3s ease;
		animation-interation-xount: 3;
	}
	@keyframes warning {
	    0% {transform: translateX(-8px);}
	    25% {transform: translateX(8px);}
	    50% {transform: translateX(-8px);}
	    75% {transform: translateX(8px);}
	}	
	*/
	
	.form-check {margin-top: 30px;}
	.form-check button {
		width: 100%; height: 50px;
		background-color: #003d32;
		border:none;
		border-radius: 25px;
		font-size: 20px; 
		color: white;
		cursor: pointer;
	}
	
	
	#rememberMeCheck1 {
		margin-top: 20px;
		font-size: 15px; 
	}
	
</style>

<script>
	let username =$('#usernameInput1');
	let password =$('#passwordInput1');
	let submitButton =$('#submitButton');
	
	$(submitButton).on('click', function(){
		if($(username).val() == "") {
			$(username).next('label').addClass('warning');
		} else if($(password).val() == "") {
			$(password).next('label').addClass('warning');
		}
	});
</script>

</head>
<body>
	
	<section class="loginForm">
		<h1>Board Login</h1>
		
		<form action="${appRoot }/login" method="post">
			<div class="userInfo">
				<label for="usernameInput1" class="form-label">
					아이디 
				</label>
				<input id="usernameInput1" type="text" name="username" autocomplete="off" required/>
			</div>
			
			<div class="userInfo">
				<label for="passwordInput1">
					비밀번호
				</label>
				<input id="passwordInput1" type="password" name="password"  autocomplete="off" required/>
			</div>
			
				<input type="checkbox" name="remember-me" id="rememberMeCheck1" />	
				<label for="rememberMeCheck1">
					자동로그인
				</label>
					
			<div class="form-check">
				<button id="submitButton" type="submit" value="로그인" >로그인</button>
			</div>
			
		</form>
	</section>

</body>
</html>