<%@page import="java.sql.ResultSet"%>
<%@page import="java.sql.Statement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.util.List"%>
<%@page import="mvjsp.board.model.Board"%>
<%@page import="mvjsp.board.dao.BoardDao"%>
<%@page import="mvjsp.board.model.Member"%>
<%@page import="mvjsp.board.dao.MemberDao"%>
<%@page import="mvjsp.jdbc.connection.ConnectionProvider"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%
    // 게시글 목록을 가져오는 예제 코드
    List<Board> Blist = new ArrayList<>();
    int currentPage = request.getParameter("page") != null ? Integer.parseInt(request.getParameter("page")) : 1;
    int postsPerPage = 10; // 한 페이지에 표시할 게시글 수
    int totalPosts = 0; // 총 게시글 수 (데이터베이스에서 조회 필요)
    int totalPages = 0;

    // 데이터베이스에서 총 게시글 수 조회
    try {
        // 데이터베이스 연결 및 쿼리 실행 코드 작성
        Connection conn = ConnectionProvider.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS total FROM board");
        if(rs.next()) {
            totalPosts = rs.getInt("total");
        }
        totalPages = (int) Math.ceil((double) totalPosts / postsPerPage);

        // 현재 페이지에 해당하는 게시글 목록 조회
        int start = (currentPage - 1) * postsPerPage;
        rs = stmt.executeQuery("SELECT * FROM board ORDER BY num DESC LIMIT " + start + ", " + postsPerPage);
        while(rs.next()) {
            Board board = new Board();
            // board 객체 초기화
            Blist.add(board);
        }
    } catch(Exception e) {
        e.printStackTrace();
    }
%>

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
                    <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">로그인메뉴</a>
                    <ul class="dropdown-menu">
                        <c:if test="${sessionScope.MEMBERID == null}">
                            <li><a class="dropdown-item" href="login.do">로그인</a></li>
                        </c:if>
                        <c:if test="${sessionScope.MEMBERID != null}">
                            <li><a class="dropdown-item" href="logout.do">로그아웃</a></li>
                        </c:if>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" onClick="move()">회원가입</a></li>
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
                    <td>
                        <c:if test="${board.name == sessionScope.MEMBERID || sessionScope.MEMBERID == 'admin'}">
                            <button class="btn btn-warning" onClick='location.href="Bwrite.jsp?num=${board.num}"'>수정</button>
                        </c:if>
                    </td>
                    <td>
                        <c:if test="${board.name == sessionScope.MEMBERID || sessionScope.MEMBERID == 'admin'}">
                            <button class="btn btn-danger" onClick='location.href="Bdelete.do?num=${board.num}"'>삭제</button>
                        </c:if>
                    </td>
                </tr>
            </c:forEach>
       <!--   <c:forEach var="board" items="${Blist}">
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
        </c:forEach> -->
        </tbody>
    </table>
    <!-- 페이징 처리 -->
<nav aria-label="Page navigation example">
        <ul class="pagination">
            <% for(int i = 1; i <= totalPages; i++) { %>
                <li class="page-item <%= currentPage == i ? "active" : "" %>">
                    <a class="page-link" href="?page=<%=i%>"><%=i%></a>
                </li>
            <% } %>
        </ul>
    </nav>
  
    
    
    <button class="btn btn-primary" onClick="location.href='Bwrite.jsp'">글쓰기</button>
    
    <button class="btn btn-primary" onClick="move()">회원가입</button>
    <c:if test="${sessionScope.MEMBERID != null}">
    	<button class="btn btn-primary" onClick="move2()">회원정보</button>
    </c:if>
	<button class="btn btn-primary" onClick="move3()">타이틀로 복귀</button>
	
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js" integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm" crossorigin="anonymous"></script>
<script>
    function move() {
        location.href = "memberForm.do";
    }
    function move2() {
        location.href = "MList.do";
    }
    function move3() {
        location.href = "index.jsp";
    }
    function view() {
        // 클래스 선택자를 사용하여 요소 선택
        var viewElement = document.querySelector(".view");
        // 뷰 요소와 관련된 작업 수행
    }
</script>
</body>
</html>