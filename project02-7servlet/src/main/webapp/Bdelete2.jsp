<%@page import="mvjsp.board.dao.MemberDao"%>
<%@page import="mvjsp.jdbc.connection.ConnectionProvider"%>
<%@page import="mvjsp.board.dao.BoardDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ page import="java.sql.*"%>

<%
String num = request.getParameter("num");
if (num == null || num.isEmpty()) {
    response.sendRedirect("Bview.jsp"); // num이 없으면 Bview.jsp로 이동
} else {
    int boardNum = Integer.parseInt(num);
    String memberId = (String) session.getAttribute("MEMBERID");
    if (memberId == null) {
        response.sendRedirect("loginForm.jsp");
    } else {
        Connection conn = ConnectionProvider.getConnection();
        BoardDao Bdao = BoardDao.getInstance();
        Bdao.delete(conn, boardNum); // 게시물 삭제

        response.sendRedirect("Bview.jsp");
    }
}
%>

<script>
    alert('삭제가 되었습니다.');
    location.href="Bview.jsp";
</script>