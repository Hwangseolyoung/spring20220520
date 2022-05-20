<%@ tag language="java" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ attribute name="current" %>

<c:url value="/board/list" var="listUrl"></c:url>
<c:url value="/board/insert" var="insertUrl"></c:url>

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
        <li class="nav-item">
          <a class="nav-link ${current == 'insert' ? 'active' : '' }" href="${insertUrl }">글쓰기</a>
        </li>
      </ul>
      
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