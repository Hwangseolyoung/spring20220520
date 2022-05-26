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

<%-- 요구사항
1. 이메일 input에 변경 발생시 '이메일중복확인버튼 활성화'
   ->버튼클릭시 ajax로 요청/응답, 적절한 메시지 출력
2. 닉네임 input에 변경 발생시 '닉네임중복확인버튼 활성화'
   ->버튼클릭시 ajax로 요청/응답, 적절한 메시지 출력

3. 암호/암호확인일치, 이메일 중복확인 완료 ,닉네임 중복확인 완료 시에만
   수정버튼 활성화

 --%>
   
	$(document).ready(function() {
		// 암호, 암호 확인 일치여부
		let passwordCheck = true;
		// 이메일 중복 확인 여부
		let emailCheck = true;
		// 닉네임 중복 확인 여부
		let nickNameCheck = true;

		// 기존 이메일
		const oldEmail = $("#emailInput1").val();
		
		// 기존 닉네임
		const oldNickName = $("#nickNameInput1").val();
		
		// 수정버튼(modifySubmitButton1) 활성화 함수
		const enableModifyButton = function() {
			if(passwordCheck && emailCheck && nickNameCheck) {
				$("#modifyButton1").removeAttr("disabled");
			} else {
				$("#modifyButton1").attr("disabled", "");
			}
		};
		
		// 암호, 암호확인 요소 값 변경시
		$("#passwordInput2").keyup(function() {
			
			const pw1 = $("#passwordInput1").val();
			const pw2 = $("#passwordInput2").val();
			
			if (pw1 === pw2) {
				$("#passwordMessage1").text("입력하신 패스워드가 일치합니다.");
				passwordCheck = true;
			} else {
				$("#passwordMessage1").text("입력하신 패스워드가 일치하지 않습니다.");
				passwordCheck = false;
			}
			
			enableModifyButton();
		});
		
		
		// 이메일 input 요소에 text 변경시 이메일중복확인버튼 활성화
		$("#emailInput1").keyup(function() {
			const newEmail = $("#emailInput1").val();
			
			if (oldEmail === newEmail) {
				$("#checkEmailButton1").attr("disabled", "");
				$("#emailMessage1").text("");
				emailCheck = true;
			} else {
				$("#checkEmailButton1").removeAttr("disabled");
				emailCheck = false;
			}
			
			enableModifyButton();
		});
		
		// 이메일중복버튼 클릭시 ajax 요청 발생 
		$("#checkEmailButton1").click(function(e) {
			// 기본이벤트 진행 중지
			e.preventDefault();
			
			const data = {email : $("#emailInput1").val()};
			
			emailCheck = false;
			
			$.ajax ({
				url : "${appRoot}/member/check",
				type : "get",
				data : data,
				success : function(data) {
					switch (data) {
					case "ok" :
						$("#emailMessage1").text("변경 가능한 이메일 입니다.");
						emailCheck = true;
						break;
					case "notOk" :
						$("#emailMessage1").text("중복된 이메일 입니다.");
						break;
					}
				},
				error : function() {
					$("#emailMessage1").text("변경 중 문제 발생! 재시도 하세요.");
				},
				complete : function() {
					enableModifyButton();
				}
			});
		});
		
		
		// 닉네임 input 요소에 text 변경시 이메일중복버튼 활성화		
		$("#nickNameInput1").keyup(function() {
			const newNickName = $("#nickNameInput1").val();
			
			if(oldNickName === newNickName) {
				$("#checkNickNameButton1").attr("disabled", "");
				$("#nickNameMessage1").text("");
				nickNameCheck = true;
			} else {
				$("#checkNickNameButton1").removeAttr("disabled");
				nickNameCheck = false;
			}
			
			enableModifyButton();
		});
		
		// 닉네임중복버튼 클릭시 ajax 요청 발생 
		$("#checkNickNameButton1").click(function(e) {
			// 기본이벤트 진행 중지
			e.preventDefault();
			
			const data = {nickName : $("#nickNameInput1").val()};
			
			nickNameCheck = false;
			
			$.ajax ({
				url : "${appRoot}/member/check",
				type : "get",
				data : data,
				success : function(data) {
					switch (data) {
					case "ok" :
						$("#nickNameMessage1").text("변경 가능한 닉네임 입니다.");
						nickNameCheck = true;
						break;
					case "notOk" :
						$("#nickNameMessage1").text("중복된 닉네임 입니다.");
						break;
					}
				},
				error : function() {
					$("#nickNameMessage1").text("변경 중 문제 발생! 재시도 하세요.");
				},
				complete : function() {
					enableModifyButton();
				}
			});
		});
		
		// 수정 submit 버튼 ("modifySubmitButton2") 클릭시
		$("#modifySubmitButton2").click(function(e) {
			e.preventDefault();
			
			const form2 = $("#form2");
			
			// input 값 옮기기
			form2.find("[name=password]").val($("#passwordInput1").val());
			form2.find("[name=email]").val($("#emailInput1").val());
			form2.find("[name=nickName]").val($("#nickNameInput1").val());
			
			// submit
			form2.submit();
		});
		
	});
</script>
</head>
<body>
	<my:navBar current="get"></my:navBar>
	
	<div>
		<p>${message }</p>
	</div>
	
	<div>
		아이디 : <input type="text" value="${member.id }" readonly/> <br /> <%-- readonly 변경불가능 --%>
		패스워드 : <input type="password" value="${member.password }" name="password" id="passwordInput1"> <br />
		패스워드 확인 : <input type="password" value=""${member.password } name="pwConfirm" id="passwordInput2"/> <br /> 
						<p id="passwordMessage1"></p> <br /> 
		이메일 : <input type="text" value="${member.email }" name="email" id="emailInput1"/> <button id="checkEmailButton1" disabled >이메일 중복확인</button> <br />
				<p id="emailMessage1"></p> <br /> 
		닉네임 : <input type="text" value="${member.nickName }" name="nickName" id="nickNameInput1"/> <button id="checkNickNameButton1" disabled >닉네임 중복확인</button> <br />
				<p id="nickNameMessage1"></p> <br /> 
		가입일시 : <input type="datetime-local" value="${member.inserted }" readonly/> <br /> <%-- readonly 변경불가능 --%>
	</div>
	
	<div>
		<!-- 회원 정보 수정&삭제  -->
		<button disabled id="modifyButton1" data-bs-toggle="modal" data-bs-target="#modal2" disabled>수정</button>
		<button data-bs-toggle="modal" data-bs-target="#modal1">삭제</button>
	</div>
	
	<!-- 삭제 Modal  -->
	<div class="modal fade" id="modal1" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel">탈퇴하시겠습니까?(암호입력)</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	      	<form id="form1" action="${appRoot }/member/remove" method="post">
				<input type="hidden" value="${member.id }" name="id" />
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
	
	<!-- 수정(modify) 기존 암호 확인 Modal  -->
	<div class="modal fade" id="modal2" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-header">
	        <h5 class="modal-title" id="exampleModalLabel2">수정하시겠습니까?(암호입력)</h5>
	        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
	      </div>
	      <div class="modal-body">
	      	<form id="form2" action="${appRoot }/member/modify" method="post">
				<!-- 수정하면 값들이 넘어가야함 -->
				<input type="hidden" value="${member.id }" name="id" />
				<input type="hidden" name="password" />
				<input type="hidden" name="email" />
				<input type="hidden" name="nickName" />
	      		기존 암호 : <input type="password" name="oldPassword" />
	      	</form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
	        <!-- form="form1" 명시해주면 위 form 루트로 submit 역할을 함 -->
	        <button id="modifySubmitButton2"form="form2" type="submit" class="btn btn-primary">수정</button>
	      </div>
	    </div>
	  </div>
	</div>
</body>
</html>