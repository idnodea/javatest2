<%@ page contentType="text/html; charset=utf-8" %>
<%
    String memberId = (String)session.getAttribute("MEMBERID");
    // 세션에 MEMBERID가 존재하면 로그인된 상태로 간주
    boolean loggedIn = (memberId != null);
%>
<html>
<head>
    <title>로그인여부 확인</title>
</head>
<body>
    <h1>로그인 상태 확인</h1>
    <% if (loggedIn) { %>
        <p>안녕하세요, <%= memberId %>님. 로그인되었습니다.</p>
        <a href="login-sessionLogout.jsp">로그아웃</a>
    <% } else { %>
        <p>로그인되지 않았습니다.</p>
        <a href="login.jsp">로그인하기</a>
    <% } %>
</body>
</html>