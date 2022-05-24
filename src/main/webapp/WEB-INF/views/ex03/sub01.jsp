<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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

<script>
	$(document).ready(function() {
		/* 이벤트가 발생하면 하는 일을 넣어준다 */
		/* 클릭이되면 해당 url 경로의 controller가 일한 흔적을 남긴다. */
		$("#button1").click(function() {
			$.ajax({
				url : "/spr2/ex03/sub03"
			});
		});

		$("#button2").click(function() {
			$.ajax({
				url : "/spr2/ex03/sub04"
			});
		});

		/* 같은 상태에서 이벤트가 발생되면 일을 하기때문에 위에 합쳐서 작성함
		$(document).ready(function() {
			$("#button2").click(function() {
				$.ajax({url : "/spr2/ex03/sub04"});
			});
		});
		 */

		$("#button3").click(function() {
			/* type(method) 프로퍼티에 요청방식 명시해주면 된다.(기본값:get) */
			$.ajax({
				url : "/spr2/ex03/sub05",
				type : "get"
			});
		});
		$("#button4").click(function() {
			$.ajax({
				url : "/spr2/ex03/sub06",
				method : "post" // type과 같은 일(type=method)
			});
		});

		$("#button5").click(function() {
			$.ajax({
				url : "/spr2/ex03/sub07",
				type : "delete"
			});
		});

		$("#button6").click(function() {
			$.ajax({
				url : "/spr2/ex03/sub08",
				type : "put"
			});
		});

		$("#button7").click(function() {
			$.ajax({
				url : "/spr2/ex03/sub09",
				type : "get", // type의 기본값은 get이므로 생략가능
				// data를 Object에 담아 보내줄 수 있다.
				// 쿼리스트링 name=value 쌍으로 보내진다.
				// http://localhost:8080/spr2/ex03/sub09?title=epl&writer=son
				data : {
					title : "epl",
					writer : "son"
				}
			});
		})

		$("#button8").click(function() {
			$.ajax({
				url : "/spr2/ex03/sub10",
				type : "post",
				data : {
					name : "sujung",
					address : "Seoul"
				}
			});
		})

		$("#button9").click(function() {
			$.ajax({
				url : "/spr2/ex03/sub11",
				type : "post",
				data : { // data를 Book객체에 보내기
					title : "득점왕 되기",
					writer : "son"
				}
			});
		})

		$("#button10").click(function() {
			$.ajax({
				url : "/spr2/ex03/sub10",
				type : "post",
				// encoded string에 담아 data 보내기
				data : "name=donald&address=newyork"
			});
		})

		$("#button11").click(function() {
			$.ajax({
				url : "/spr2/ex03/sub11",
				type : "post",
				// encoded string에 담아 Book 객체에 data 보내기
				data : "title=coding life&writer=sunja"
			});
		})

		$("#button12").click(function(e) {
			// html 방식 전송 멈춤 상태로 먼저 만들어 준다.
			e.preventDefault();

			// const : 블록 범위의 상수를 선언
			//.serialize() : ajax 방식의 submit을 위한 string으로 만들어준다.(jQuery method)
			const dataString = $("#form1").serialize();

			$.ajax({
				url : "/spr2/ex03/sub10",
				type : "post",
				data : dataString
			});
		});

		$("#button13").click(function(e) {
			e.preventDefault();

			const data = $("#form2").serialize();

			$.ajax({
				url : "/spr2/ex03/sub11",
				type : "post",
				data : data
			});
		});

		$("#button14").click(function() {
			$.ajax({
				url : "/spr2/ex03/sub12",
				type : "post",
				// success : 응답을 잘 받으면 function(함수)에 담긴 일을 한다
				// function(파라미터에는 3가지를 받을 수 있다.)
				// function(1.response data, 2.String textStatus, 3.jqXHR jqXHR)
				// controller에서 return해주는 값이 data라는 이름으로 들어오는 것이다.
				// 값이 들어오는 이름은 자유롭게 변경가능하다.
				success : function(data) {
					console.log("요청 성공!!");
					console.log("받은 데이터", data);
				}
			});
		});

		$("#button15").click(function() {
			$.ajax({
				url : "/spr2/ex03/sub13",
				type : "get",
				success : function(data) {
					// controller에서 return해주는 값이 data라는 이름으로 들어오는 것이다.
					// 값이 들어오는 이름은 자유롭게 변경가능하다.
					// console.log(data);
					$("#result1").text(data);
				}
			});
		})

		$("#button16").click(function() {
			$.ajax({
				url : "/spr2/ex03/sub14",
				type : "get",
				success : function(book) {
					/* Book 객체를 json string으로 변환하여 출력함
					console.log(book); // {title: '스프링', writer: '김자반'}
					console.log(book.title);
					console.log(book.writer);
					 */
					$("#result2").text(book.title);
					$("#result3").text(book.writer);

				}
			});
		})

		$("#button17").click(function() {
			$.ajax({
				url : "/spr2/ex03/sub15",
				success : function(map) {
					// map으로 받은 객체를 
					// json 자바스크립트 객체 표현방법인 String으로 변환해서 보여준다.
					console.log(map); // {address: 'london', name: '손흥민', age: '30'}
				}
			});
		})

		$("#button18").click(function() {
			$.ajax({
				url : "/spr2/ex03/sub16",
				type : "get",
				success : function(data) {
					console.log(data);
				},
				error : function() {
					// error : 요청에 실패할 경우 
					// 값은 함수타입이여야 한다.
					console.log("무엇인가 잘못됨");
				}

			});
		});

		$("#button19").click(function() {
			$.ajax({
				url : "/spr2/ex03/sub16",
				type : "get",
				success : function(data) {
					console.log(data);
				},
				error : function() {
					$("#message19").show();
					$("#message19").text("처리 중 오류 발생").fadeOut(3000);
				}
			});
		});
		
		$("#button20").click(function() {
			$.ajax({
				url : "/spr2/ex03/sub17",
				type : "get",	
				success : function(data) {
					console.log("받은 데이터", data);
				},
				error : function() {
					console.log("무엇인가 잘못됨!!");
				}
			});
		});
		
		$("#button21").click(function() {
			$.ajax({
				url : "/spr2/ex03/sub18",
				type : "get",
				success : function(data) {
					$("#message20").show();
					$("#message20").removeClass("error").text(data).fadeOut(3000);
				},
				
				error : function(data) {
					$("#message20").show();
					$("#message20").addClass("error").text("무엇인가 잘못됨").fadeOut(3000);
				}
			});
		});
		
		$("#button22").click(function() {
			$.ajax({
				url : "/spr2/ex03/sub18",
				type : "get",
				success : function(data) {
					$("#message20").show();
					$("#message20").removeClass("error").text(data).fadeOut(3000);
				},
				
				error : function(data) {
					$("#message20").show();
					$("#message20").addClass("error").text("무엇인가 잘못됨").fadeOut(3000);
				},
				// complete : success, error가 아닌 항상 실행될 코드
				complete : function(data) {
					console.log("항상 실행됨!!");
				}
			});
		});
	});
</script>

<style>
.error {
	background-color: red;
	color: yellow;
}
</style>

<title>Insert title here</title>
</head>
<body>
	<button id="button1">ajax 요청 보내기</button> <br />
	
	<%-- 이 버튼을 클릭하면 /spr2/ex03/sub04로 ajax 요청보내기 --%>
	<%-- controller에도 해당 경로 요청에 일하는 method 추가 --%>
	<button id="button2">ajax 요청보내기2</button> <br />
	
	<!-- 요청 방식에 따른 방법 -->
	<%-- /spr2/ex03/sub05 get 방식 요청 보내기 --%>
	<button id="button3">get 방식 요청 보내기</button> <br />
	
	<%-- /spr2/ex03/sub06 post 방식 요청 보내기 --%>
	<button id="button4">post 방식 요청 보내기</button> <br />
	
	<%-- /spr2/ex03/sub07 delete 방식 요청 보내기 --%>
	<button id="button5">delete 방식 요청 보내기</button> <br />
	
	<%-- /spr2/ex03/sub08 put 방식 요청 보내기 --%>
	<button id="button6">put 방식 요청 보내기</button>
	
	<hr />
	
	<p>서버로 데이터 보내기(Object 방식)</p>
	
	<%-- /spr2/ex03/sub09 get 방식 요청 보내기 --%>
	<%-- 전송될 데이터는 title, writer --%>
	<button id="button7">get방식으로 데이터 보내기</button>
	
	<br />
	
	<%-- /spr2/ex03/sub10 post 방식 요청 보내기 --%>
	<%-- 전송될 데이터는 name, address --%>
	<button id="button8">post방식으로 데이터 보내기</button>
	
	<br />
	
	<%-- /spr2/ex03/sub11 post 방식 요청 보내기 --%>
	<%-- 전송될 데이터는 title, writer --%>
	<button id="button9">post 방식으로 데이터 보내기2</button>
		
	<hr />
	
	<p>서버로 데이터 보내기(String 방식)</p>
	
	<%-- /spr2/ex03/sub10 post 방식 데이터 보내기 --%>
	<%-- 전송될 데이터는 name, address --%>
	<button id="button10">post 방식으로 데이터 보내기(encoded string)</button>
		
	<br />
	
	<%-- /spr2/ex03/sub11 post 방식 요청 보내기 --%>
	<%-- 전송될 데이터는 title, writer --%>
	<button id="button11">post 방식으로 데이터 보내기2(encoded string)</button>
	
	<hr />
	
	<p>폼 데이터 보내기</p>
	
	<form action="/spr2/ex03/sub10" id="form1" method="post">
		name : <input type="text" name="name" id="" /> <br />
		address : <input type="text" name="address" /> <br />
		<!-- <input type="submit" value="전송" /> -->
		<input id="button12" type="submit" value="전송" />
	</form>
	
	<br />
	
	<form id="form2">
		title : <input type="text" name="title" /> <br />
		writer : <input type="text" name="writer" /> <br /> 
		<input type="submit" value="전송" id="button13"/>
	</form>
	
	<hr />
	
	<p>응답 처리하기</p>
	
	<%-- url : /spr2/ex03/sub12, type : post --%>
	<button id="button14">응답처리1</button>
	
	<br />
	
	<button id="button15">로또 번호 받기</button>
	<p>받은 번호 : <span id="result1"></span></p>
	
	<br />
	
	<button id="button16">json 데이터 받기</button>
	<p>책 제목 : <span id="result2"></span></p>
	<p>책 저자 : <span id="result3"></span></p>
	
	<br />
	<p>map 객체를 json string으로 출력하기</p>
	<button id="button17">map to json</button>
	
	<hr />
	
	<p>요청이 실패할 경우</p>
	
	<button id="button18">잘못된 요청</button>
	
	<br />
	
	<button id="button19">잘못된 요청2</button>
	<p class="error" id="message19"></p>
	
	<button id="button20">서버에서 에러 응답</button>
	
	<br />
	
	<button id="button21">50%확률로 ok</button>
	
	<p id="message20"></p>
	
	<button id="button22">50%확률로 ok</button>
</body>
</html>





