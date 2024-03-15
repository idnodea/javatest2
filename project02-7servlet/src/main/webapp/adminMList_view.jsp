<%@page import="mvjsp.board.dao.MemberDao"%>
<%@page import="mvjsp.board.model.Member"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Bootstrap demo</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9"
	crossorigin="anonymous">
</head>
<body>
	<nav class="navbar navbar-expand-lg bg-body-tertiary">
		<div class="container-fluid">
			<a class="navbar-brand" href="#">Navbar</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
				aria-controls="navbarSupportedContent" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav me-auto mb-2 mb-lg-0">
					<li class="nav-item"><a class="nav-link active"
						aria-current="page" href="#">Home</a></li>
					<li class="nav-item"><a class="nav-link" href="#">Link</a></li>
					<li class="nav-item dropdown"><a
						class="nav-link dropdown-toggle" href="#" role="button"
						data-bs-toggle="dropdown" aria-expanded="false"> 로그인메뉴 </a>
						<ul class="dropdown-menu">
							
							<li><a class="dropdown-item" href="adminLogout.do">로그아웃</a></li>
							
							<li><hr class="dropdown-divider"></li>
							<!--  <li><a class="dropdown-item" onClick="move()">회원가입</a></li>-->
						</ul></li>
					<li class="nav-item"><a class="nav-link disabled"
						aria-disabled="true">Disabled</a></li>
				</ul>
				<form class="d-flex" role="search">
					<input class="form-control me-2" type="search" placeholder="Search"
						aria-label="Search">
					<button class="btn btn-outline-success" type="submit">Search</button>
				</form>
			</div>
		</div>
	</nav>

	<div class="container" style="padding-top: 50px">
		<h2>회원 관리</h2>
        <table class="table table-bordered table-hover">
            <thead>
                <tr>
                    <th scope="col">회원번호</th>
                    <th scope="col">아이디</th>
                    <th scope="col">이메일</th>
                    <th scope="col">이름</th>
                    <th scope="col">수정</th>
                    <th scope="col">삭제</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="member" items="${adminMList}">
                <tr>
                    <th scope="row">${member.memberno}</th>
                    <td>${member.id}</td>
                    <td>${member.email}</td>
                    <td>${member.name}</td>
                    <td>
                        <button class="btn btn-warning" onClick='location.href="updateForm.do?memberno=${member.memberno}"'>수정</button>
                    </td>
                    <td>
                        <form action="memberDelete.do" method="post" onsubmit="return confirm('정말 삭제하시겠습니까?');">
                            <input type="hidden" name="memberno" value="${member.memberno}" />
                            <button type="submit" class="btn btn-danger">삭제</button>
                        </form>
                    </td>
                </tr>
                </c:forEach>
            </tbody>
        </table>
		<button class="btn btn-primary" onClick="move()">테스트용회원가입</button>
		<%
		String memberId = request.getParameter("memberId");
		String id = request.getParameter("id");
		String email = request.getParameter("email");
		Member member = MemberDao.getInstance().selectMember(id);
		if (member != null && member.getId().equals(id)) { 
	        %>
	            <button class="btn btn-primary" onClick="location.href='memberForm.do'">회원가입</button>
	        <% 
	        } 
	        %>
		
		<button class="btn btn-primary" onClick="move2()">게시판이동</button>
		<button class="btn btn-primary" onClick="move3()">타이틀로 복귀</button>
		<button class="btn btn-primary" onClick="move4()">관리자화면</button>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm"
		crossorigin="anonymous"></script>

	<script>
		function move() {
			//alert("나온다");
			location.href="memberForm.do";
		}
		function move2() {
			//alert("나온다");
			location.href="adminBlist.do";
		}
		function move3() {
			//alert("나온다");
			location.href="index.jsp";
		}
		function move4() {
			//alert("나온다");
			location.href="adminForm.jsp";
		}
	</script>
</body>
</html>