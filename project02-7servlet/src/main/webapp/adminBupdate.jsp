<%@page import="mvjsp.board.model.Board"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.Connection"%>
<%@page import="mvjsp.board.dao.BoardDao"%>
<%@page import="mvjsp.jdbc.connection.ConnectionProvider"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    int num = Integer.parseInt(request.getParameter("num"));
    String title = request.getParameter("title");
    String content = request.getParameter("content");

    Connection conn = null;
    try {
        conn = ConnectionProvider.getConnection();
        BoardDao dao = BoardDao.getInstance();
        
        dao.update(conn, new Board(num, title, content, null, 0, 0, null)); // regtime, hits, memberno, name 업데이트가 필요하지 않은 경우 null 또는 0 처리
        
        response.sendRedirect("adminBview.do?num=" + num);
    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        if (conn != null) try { conn.close(); } catch(SQLException e) {}
    }
%>