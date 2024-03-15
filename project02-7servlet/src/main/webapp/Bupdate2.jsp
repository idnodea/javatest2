<%@ page language="java" contentType="text/html; charset=UTF-8"
     pageEncoding="UTF-8"%>
     
<%@ page import="java.sql.*" %>  
<%@ page import="java.time.*" %>
  
<%
    request.setCharacterEncoding("utf-8");

    // 전달받은 값 읽기
    int num = (int) session.getAttribute("num");
    System.out.print(num);
    String name = request.getParameter("name");
    String title = request.getParameter("title");
    String content = request.getParameter("content");
    
    String id = request.getParameter("id");
    String email = request.getParameter("email");
    System.out.println(name+title+content+email);

    // 빈 칸이 하나라도 있으면 오류 출력하고 종료
    if (name == null || name.length() == 0 ||
        title == null || title.length() == 0 ||
        content == null || content.length() == 0) {
    	System.out.print("123123");
%>
    <script>
        alert('모든 항목이 빈칸 없이 입력되어야 합니다.');
        history.back();
    </script>
<%
        return;
    }

    // 데이터베이스 연결 및 쿼리 실행
    try {
    	Class.forName("oracle.jdbc.driver.OracleDriver");     
        try (Connection conn = DriverManager.getConnection(
        		 "jdbc:oracle:thin:@localhost:1521:xe", "scott", "tiger")) {
            // 현재 시간 얻기
            String curTime = LocalDate.now() + " " +
                    LocalTime.now().toString().substring(0, 8);

            // 쿼리 실행
            String sql = "UPDATE board SET title=?, content=?, regtime=sysdate WHERE num=?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, title);
                pstmt.setString(2, content);
                pstmt.setInt(3, num);
                int result = pstmt.executeUpdate();
                System.out.print(result);
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }

    // 글 보기 화면으로 돌아감
    response.sendRedirect("Bview.jsp?num=" + num);
%>