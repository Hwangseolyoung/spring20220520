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

	<my:navBar current="list"></my:navBar>
	
	<div class="container">
		<div class="row">
			<div class="col">
				<table class="table">
					<thead>
						<tr>
							<th>#</th>
							<th>ID</th>
							<th>PW</th>
							<th>EMAIL</th>
							<th>NICKNAME</th>
							<th>가입일시</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${memberList }" var="member" varStatus="status">
							<tr>
								<td>${status.count }</td>
								<td>
									<!-- 회원정보 수정링크 연결 -->
									<c:url value="/member/get" var="getMemberUrl">
										<c:param name="id" value="${member.id }"></c:param>
									</c:url>
									<a href="${getMemberUrl }" >
										${member.id }
									</a>
								</td>
								<td>${member.password }</td>
								<td>${member.email }</td>
								<td>${member.nickName }</td>
								<td>${member.inserted }</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</div>
	</div>

</body>
</html>