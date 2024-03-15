<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%@ page import="java.lang.Boolean" %>
<%
    // 세션에서 관리자 로그인 여부 확인
    Boolean isAdmin = (Boolean) session.getAttribute("isAdmin");

    if (isAdmin == null || !isAdmin) {
        // 관리자 로그인 상태가 아니면 로그인 페이지로 리다이렉트
        response.sendRedirect("adminLogin.jsp");
        return;
    }
%>

<html>
<head>
    <title>관리자 페이지</title>
</head>
<body>
    <h2>관리자 페이지</h2>
    <p>관리자만 볼 수 있는 정보입니다.</p>
    <a href="adminLogout.jsp">로그아웃</a>
    
    <div>
   		 <button onclick="move()">회원정보</button>
   		 <button onclick="move2()">게시판리스트</button>
    </div>
    <script>
    	function move() {
    		location.href="adminMList.do"
		}
    	function move2() {
    		location.href="adminBlist.do"
		}
    	
    </script>
</body>
</html>