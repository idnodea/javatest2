<%@page import="mvjsp.jdbc.connection.ConnectionProvider"%>
<%@page import="mvjsp.board.model.Member"%>
<%@page import="mvjsp.board.model.Board"%>
<%@page import="mvjsp.board.dao.BoardDao"%>
<%@page import="mvjsp.board.dao.MemberDao"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ page import="java.sql.*" %>

<%

    int num = 0; // 기본값 설정
    String numParam = request.getParameter("num");
    if (numParam != null && !numParam.isEmpty()) {
        try {
            num = Integer.parseInt(numParam);
        } catch (NumberFormatException e) {
            // 파라미터가 잘못된 경우 처리
            e.printStackTrace();
        }
    }
    
    String name  = "";
    String title   = "";
    String content = "";
    String regtime = "";
    int hits    = 0;
  /*  
    // 세션에서 회원 아이디를 가져옵니다.
    String memberId = (String)session.getAttribute("MEMBERID");
    if (memberId == null) {
        response.sendRedirect("loginForm.jsp");
        return; // 리다이렉션 이후 추가 코드 실행을 막기 위해 리턴합니다.
    }*/
    
    // 회원 정보를 가져옵니다.
    MemberDao memberDao = MemberDao.getInstance();
    Connection conn = ConnectionProvider.getConnection(); // Connection 객체 생성
  //  Member member = memberDao.select(conn, num); // 회원 정보 가져오기
    
    // 글 데이터를 가져옵니다.
    BoardDao boardDao = BoardDao.getInstance();
    Board board = boardDao.selectOne(conn, num, true); // 게시물 조회 (조회수 증가)
    
    // 글 데이터를 변수에 저장
    if (board != null) {
        name  = board.getName();
        title   = board.getTitle();
        content = board.getContent();
        regtime = board.getRegtime();
        hits    = board.getHits();
    }
    
    // 글 제목과 내용이 웹 페이지에 올바르게 출력되도록 
    // 공백과 줄 바꿈 처리
    title   = title.replace  (" ", "&nbsp;");
    content = content.replace(" ", "&nbsp;").replace("\n", "<br>");
    
    conn.close(); // Connection 객체 닫기
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <style>
        table { width:680px; text-align:center; }
        th    { width:100px; background-color:cyan; }
        td    { text-align:left; border:1px solid gray; }
    </style>
</head>
<body>

<table>
    <tr>
        <th>제목</th>
        <td><%=title%></td>
    </tr>
    <tr>
        <th>작성자</th>
        <td><%=name%></td>
    </tr>
    <tr>
        <th>작성일시</th>
        <td><%=regtime%></td>
    </tr>
    <tr>
        <th>조회수</th>
        <td><%=hits%></td>
    </tr>
    <tr>
        <th>내용</th>
        <td><%=content%></td>
    </tr>
</table>

<br>
<input type="button" value="목록보기" onclick="location.href='adminBlist.do'">
<input type="button" value="수정"
       onclick="location.href='adminBwrite.jsp?num=<%=num%>'">
<input type="button" value="삭제"
       onclick="location.href='adminBdelete.jsp?num=<%=num%>'">

</body>
</html>