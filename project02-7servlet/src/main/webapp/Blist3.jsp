<%@page import="mvjsp.board.model.Board"%>
<%@page import="mvjsp.board.dao.BoardDao"%>
<%@page import="mvjsp.board.model.Member"%>
<%@page import="mvjsp.board.dao.MemberDao"%>
<%@page import="mvjsp.jdbc.connection.ConnectionProvider"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Bootstrap demo</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-expand-lg bg-light">
    <div class="container-fluid">
        <a class="navbar-brand" href="#">Navbar</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                <li class="nav-item"><a class="nav-link active" href="#">Home</a></li>
                <li class="nav-item"><a class="nav-link" href="#">Link</a></li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">Dropdown</a>
                    <ul class="dropdown-menu">
                        <li><a class="dropdown-item" href="#">Action</a></li>
                        <li><a class="dropdown-item" href="#">Another action</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="#">Something else here</a></li>
                    </ul>
                </li>
                <li class="nav-item"><a class="nav-link disabled">Disabled</a></li>
            </ul>
            <form class="d-flex" role="search">
                <input class="form-control me-2" type="search" placeholder="Search" aria-label="Search">
                <button class="btn btn-outline-success" type="submit">Search</button>
            </form>
        </div>
    </div>
</nav>

<div class="container" style="padding-top: 50px">
    <table class="table table-bordered table-hover">
        <thead>
            <tr>
                <th scope="col">번호</th>
                <th scope="col">작성자</th>
                <th scope="col">제목 </th>
                <th scope="col">내용</th>
                <th scope="col">등록일시</th>
                <th scope="col">조회수</th>
                <th scope="col">수정</th>
                <th scope="col">삭제</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="board" items="${Blist}">
            <tr>
                <th scope="row">${board.num}</th>
                <td>${board.name}</td>
                <td class="view"> <a href="Bview.do?num=${board.num}">${board.title}</a> </td>
                <td>${board.content}</td>
                <td>${board.regtime}</td>
                <td>${board.hits}</td>
                <td><button class="btn btn-warning" onClick='location.href="Bwrite.jsp?num=${board.num}"'>수정</button></td>
                <td><button class="btn btn-danger" onClick='location.href="Bdelete.do?num=${board.num}"'>삭제</button></td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <button class="btn btn-primary" onClick="location.href='Bwrite.jsp'">글쓰기</button>
    <button class="btn btn-primary" onClick="move()">회원가입</button>
	<button class="btn btn-primary" onClick="move3()">타이틀로 복귀</button>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous">
	function move() {
			//alert("나온다");
			location.href="memberForm.do";
		}
		function move2() {
			//alert("나온다");
			location.href="Blist.do";
		}
		function move3() {
			//alert("나온다");
			location.href="index.do";
		}
		
		function view() {
			document.getElementId(".view");
			
			
		}
		</script>
</body>
</html>