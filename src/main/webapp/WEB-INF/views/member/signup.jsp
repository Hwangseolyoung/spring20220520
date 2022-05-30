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
<script>
	$(document).ready(function() {
		// 중복, 암호 확인 변수
		let idOk = false;
		let pwOk = false;
		let emailOk = false;
		let nickNameOk = false;
		
		// 아이디 중복 체크 버튼 클릭시
		$("#checkIdButton1").click(function(e) {
			e.preventDefault();
			
			$(this).attr("disabled", "");// 버튼을 여러번 누르지 못하도록
			const data = { // find : 안쪽 엘레멘트를 얻어오는 메소드
					id : $("#form1").find("[name=id]").val(),
			};
			
			// 중복, 암호 확인 변수
			idOk = false;
			$.ajax({
				url : "${appRoot}/member/check",
				type : "get",
				data : data, // to server 보내는 값
				success : function(data) { // from server 서버로부터 받은 값
					switch (data) {
					case "ok" :
						$("#idMessage1").text("사용 가능한 아이디입니다.");
						// 중복, 암호 확인 변수
						idOk = true;
						break;
					case "notOk" :
						$("#idMessage1").text("사용 불가능한 아이디입니다.");
						
						break;
					}
				},
				error : function() {
					$("#idMessage1").text("중복 확인 중 문제 발생!, 다시 시도해주세요.");
					
				},
				complete : function() {
					$("#checkIdButton1").removeAttr("disabled");
					// 회원가입 submit 버튼 활성화/비활성화 함수
					enableSubmit();
				}
			});
		});
		
		// 이메일 중복 체크 버튼 클릭시
		$("#checkEmailButton2").click(function(e) {
			e.preventDefault();
			
			$("#checkEmailButton2").attr("disabled", "");// 버튼을 여러번 누르지 못하도록
			const data = {
					email : $("#form1").find("[name=email]").val()
			};
			
			// 중복, 암호 확인 변수
			emailOk = false;
			$.ajax({
				url : "${appRoot}/member/check",
				type : "get",
				data : data, // to server 보내는 값
				success : function(data) { // from server 서버로부터 받은 값
					switch (data) {
					case "ok" :
						$("#emailMessage1").text("사용 가능한 이메일입니다.");
						// 중복, 암호 확인 변수
						emailOk = true;
						break;
					case "notOk" :
						$("#emailMessage1").text("중복된 이메일입니다.");
						break;
					}
				},
				error : function() {
					$("#emailMessage1").text("중복 확인 중 문제 발생!, 다시 시도해주세요.");
				},
				complete : function() {
					$("#checkEmailButton2").removeAttr("disabled");
					// 회원가입 submit 버튼 활성화/비활성화 함수
					enableSubmit();
				}
			});
		});
		
		// 닉네임 중복 체크 버튼 클릭시
		$("#checkNickNameButton3").click(function(e) {
			e.preventDefault();
			
			$("#checkNickNameButton3").attr("disabled", "");// 버튼을 여러번 누르지 못하도록
			const data = {
					// 객체의 프로퍼티 값엔 세미콜론 붙이면 안됨
					nickName : $("#form1").find("[name=nickName]").val()
			};
			
			// 중복, 암호 확인 변수
			nickName = false;
			$.ajax({
				url : "${appRoot}/member/check",
				type : "get",
				data : data, // to server 보내는 값
				success : function(data) { // from server 서버로부터 받은 값
					switch (data) {
					case "ok" :
						$("#nickNameMessage1").text("사용 가능한 닉네임입니다.");
						// 중복, 암호 확인 변수
						nickNameOk = true;
						break;
					case "notOk" :
						$("#nickNameMessage1").text("중복된 닉네임입니다.");
						break;
					}
				},
				error : function() {
					$("#nickNameMessage1").text("중복 확인 중 문제 발생!, 다시 시도해주세요.");
				},
				complete : function() {
					$("#checkNickNameButton3").removeAttr("disabled");
					// 회원가입 submit 버튼 활성화/비활성화 함수
					enableSubmit();
				}
			});
		});
		
		// 패드워드 오타 확인
		// 키보드 입력되는 이벤트가 발생되면 일어나는 일
		$("#passwordInput1, #passwordInput2").keyup(function() {
			// 명령문이 끝났으니 세미콜론 붙여야함
			const pw1 = $("#passwordInput1").val();
			const pw2 = $("#passwordInput2").val();
			
			// 중복, 암호 확인 변수
			pwOk = false;
			if(pw1 === pw2) {
				$("#passwordMessage1").text("패스워드가 일치합니다.");
				// 중복, 암호 확인 변수
				pwOk = true;
			} else {
				$("#passwordMessage1").text("패스워드가 일치하지 않습니다.");
			}
			
			// 회원가입 submit 버튼 활성화/비활성화 함수
			enableSubmit();
		});
		
		// 회원가입 submit 버튼 활성화/비활성화 함수
		const enableSubmit = function() {
			if(idOk && pwOk && emailOk && nickNameOk) {
				$("#submitButton1").removeAttr("disabled");
			} else {
				$("#submitButton1").attr("disabled", "");
			}
		}
	});
	

</script>

</head>
<body>
	
	<my:navBar current="signup"></my:navBar>
	<div class="container">
		<div class="row">
			<div class="col">
				<form id="form1" action="${appRoot }/member/signup" method="post">
					
				
					아이디 : <input type="text" name="id" /> 
					<!-- submit 역할을 하지 않도록 type="button" 명시 -->
					<button id="checkIdButton1" type="button">아이디 중복 확인</button>
					<p id="idMessage1"></p>
					<br/>
					
					패스워드 : <input id="passwordInput1" type="password" name="password" /> <br/>
					
					패스워드 확인 : <input id="passwordInput2" type="text" name="passwordConfirm" /> <br/>
					<p id="passwordMessage1"></p>
					<br/>
					
					이메일 : <input type="email" name="email" /> 
					<button id="checkEmailButton2" type="button">이메일 중복 확인</button>
					<p id="emailMessage1"></p>
					<br/>
					
					닉네임 : <input type="text" name="nickName" />
					<button id="checkNickNameButton3" type="button">닉네임 중복 확인</button>
					<p id="nickNameMessage1"></p>
					<br/>
					
					<button id="submitButton1" disabled>회원가입</button>
				</form>
			</div>
		</div>
	</div>
	
	
</body>
</html>