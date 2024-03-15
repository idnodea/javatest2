<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.Connection"%>
<%@page import="mvjsp.board.dao.BoardDao"%>
<%@page import="mvjsp.jdbc.connection.ConnectionProvider"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    int num = Integer.parseInt(request.getParameter("num"));
    Connection conn = null;
    try {
        conn = ConnectionProvider.getConnection();
        BoardDao dao = BoardDao.getInstance();
        
        dao.delete(conn, num);
        
        response.sendRedirect("Blist.do");
    } catch (Exception e) {
        e.printStackTrace();
        response.sendRedirect("errorPage.jsp");
    } finally {
        if (conn != null) try { conn.close(); } catch(SQLException e) {}
    }
%>