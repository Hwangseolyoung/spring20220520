<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- security tag 설정 -->
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ attribute name="current" %>

<!-- 경로 목록 -->
<c:url value="/board/list" var="listUrl"></c:url>
<c:url value="/board/insert" var="insertUrl"></c:url>
<c:url value="/member/signup" var="signupUrl"></c:url>
<c:url value="/member/list" var="memberListUrl"></c:url>
<c:url value="/member/login" var="loginUrl"></c:url>
<c:url value="/logout" var="logoutUrl"></c:url>


<nav class="navbar navbar-expand-md navbar-light bg-light mb-3">
  <div class="container">
    <a class="navbar-brand" href="${listUrl }"><i class="fa-solid fa-house"></i></a>
    
    <button class="navbar-toggler" 
    		data-bs-toggle="collapse"
    		data-bs-target="#navbarSupportedContent">
    	<span class="navbar-toggler-icon"></span>
    </button>
    
    <div class="collapse navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav me-auto mb-2 mb-lg-0">
        <li class="nav-item">
          <a class="nav-link ${current == 'list' ? 'active' : '' }" href="${listUrl }">목록보기</a>
        </li>
        
        <!-- login user만 글쓰기 보여주기 -->
        <sec:authorize access="isAuthenticated()">
	        <li class="nav-item">
	          <a class="nav-link ${current == 'insert' ? 'active' : '' }" href="${insertUrl }">글쓰기</a>
	        </li>
        </sec:authorize>
        
        <!-- 회원가입 추가 -->
        <!-- li.nav-item>a.nav-link{회원가입} -->
        <li class="nav-item">
        	<a href="${signupUrl }" class="nav-link ${current == 'signupUrl' ? 'active' : '' }">회원가입</a>
        </li>
        
        <!-- 관리자에게만 회원목록 보여주기 -->
        <sec:authorize access="hasRole('ADMIN')">
        	<!-- 회원목록 추가 -->
	        <li class="nav-item">
	        	<a href="${memberListUrl }" class="nav-link ${current == 'memberList' ? 'active' : '' }">회원목록</a>
	        </li>
        </sec:authorize>
        
        <!-- 로그인 하면 로그인 안보이기 -->
        <!-- li.nav-item>a.nav-link{로그인} -->
        <sec:authorize access="not isAuthenticated()">
	        <li class="nav-item">
	        	<a href="${loginUrl }" class="nav-link">로그인</a>
	        </li>  
        </sec:authorize>
        
        <!-- 로그아웃 하면 로그아웃 안보이기 -->
        <sec:authorize access="isAuthenticated()">
	        <li class="nav-item">
	        	<button class="nav-link" type="submit" form="logoutform1">로그아웃</button>
	        </li>
        </sec:authorize>
      </ul>
      
      <!-- 로그아웃 -->
      <!-- div.d-none>form#form1 -->
      <div class="d-none">
      	<form action="${logoutUrl }" id="logoutform1" method="post"></form>
      </div>
      
      <!-- 검색기능 추가(동적 SQL사용) -->
      <!-- from.d-flex>input.form-control.me-2[type=search]+button.btn.btn-outline-success -->
      <form class="d-flex" action="${listUrl }">
      	<!-- 조건별 검색기능 추가 -->
      	<!-- select.form-select>option*3 -->
      	<select name="type" id="" class="form-select">
      		<!-- selected추가로 파라미터가 조건에 맞으면 화면에 해당선택 옵션 표시됨 -->
      		<option value="all" ${param.type != 'title' && param.type != 'body' ? 'selected' : '' }>전체</option> 
      		<option value="title" ${param.type == 'title' ? 'selected' : '' }>제목</option>
      		<option value="body" ${param.type == 'body' ? 'selected' : '' }>본문</option>
      	</select>
        <input class="form-control me-2" type="search" name="keyword" />
        <button class="btn btn-outline-success" type="submit"><i class="fa-solid fa-magnifying-glass"></i></button>
      </form>
      
    </div>
  </div>
</nav>