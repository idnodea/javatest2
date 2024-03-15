<%@page import="java.sql.Connection"%>
<%@page import="java.sql.SQLException"%>
<%@page import="mvjsp.board.model.Board"%>
<%@page import="mvjsp.board.dao.BoardDao"%>
<%@page import="mvjsp.jdbc.connection.ConnectionProvider"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%
    String title = request.getParameter("title");
    String content = request.getParameter("content");
    // 세션에서 사용자의 정보를 가져와야 함. 여기서는 예시로 직접 값을 할당.
    String name = "사용자명"; // 예시 값
    int memberno = 1; // 예시 값, 실제로는 세션 등에서 사용자 번호를 가져와야 함.

    Connection conn = null;
    try {
        conn = ConnectionProvider.getConnection();
        BoardDao dao = BoardDao.getInstance();
        
        Board board = new Board();
        board.setTitle(title);
        board.setContent(content);
        board.setMemberno(memberno); // 실제 구현에서는 사용자 세션에서 이 값을 가져와야 함.
        board.setName(name); // 사용자의 실제 이름이나 식별자.
        
        dao.insert(conn, board);
        response.sendRedirect("adminBlist.do"); // 성공적으로 데이터 삽입 후 목록 페이지로 리다이렉트
    } catch (SQLException e) {
        e.printStackTrace(); // 데이터베이스 오류 처리
    } catch (Exception e) {
        e.printStackTrace(); // 기타 예외 처리
    } finally {
        if (conn != null) try { conn.close(); } catch(SQLException e) { /* 리소스 해제 실패 처리 */ }
    }
%>