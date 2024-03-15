<%@page import="mvjsp.board.model.Member"%>
<%@page import="mvjsp.board.model.Board"%>
<%@page import="mvjsp.board.dao.BoardDao"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.*"%>

<%



HttpSession sessHttpSession = request.getSession();
Member loginId = (Member) session.getAttribute("MEMBER");
int memberno = loginId.getMemberno(); // 로그인한 사용자의 memberno를 가져옴
    String tmp = request.getParameter("num");
    int num = (tmp != null && !tmp.isEmpty()) ? Integer.parseInt(tmp) : 0;

    //String name = ""; 
	String name = loginId != null ? loginId.getName() : "익명"; // 로그인하지 않은 경우 '익명'으로 설정
    String title = "";
    String content = "";
    String action = "adminBinsert.do"; // 기본적으로 삽입 액션을 가정

    if (num > 0) {
        BoardDao dao = BoardDao.getInstance();
        try (Connection conn = dao.getConnection()) {
            Board board = dao.selectOne(conn, num, false); // 글 수정 모드에서는 조회수를 증가시키지 않음

            if (board != null) {
                name = board.getName();
                title = board.getTitle();
                content = board.getContent();
                action = "adminBupdate.do"; // 글 번호가 주어졌으므로 수정 액션으로 변경
            }
        } catch (Exception e) {
            e.printStackTrace(); // 오류 처리
        }
    }
%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>글 작성/수정</title>
    <style>
        table { width:680px; text-align:center; }
        th    { width:100px; background-color:cyan; }
        input[type=text], textarea { width:100%; }
    </style>
</head>
<body>

<form id="postForm" method="post" action="<%= action %>">
    <% if (num > 0) { %>
        <input type="hidden" name="num" value="<%= num %>">
    <% } %>
    <table>
        <tr>
            <th>제목</th>
            <td><input type="text" name="title" value="<%= title %>" maxlength="80" id="title"></td>
        </tr>
        <tr>
          <th>작성자</th>
        	<td><input type="text" name="name" value="<%= name %>" maxlength="20" id="name" readonly></td>
        </tr>
        <tr>
            <th>내용</th>
            <td><textarea name="content" rows="10" id="content"><%= content %></textarea></td>
        </tr>
    </table>
    <br>
    <input type="button" value="저장" onclick="saveForm()"> <!-- saveForm() 함수 호출 -->
    <input type="button" value="취소" onclick="history.back();">
</form>
	<script>
	function saveForm() {
	    // 폼 요소 가져오기
	    var title = document.getElementById('title').value;
	    var name = document.getElementById('name').value;
	    var content = document.getElementById('content').value;

	    // 폼 요소 검증
	    if (title.trim() === "" || content.trim() === "") {
	        alert('제목,내용을 모두 입력해주세요.');
	        return false; // 제출 과정 중단
	    }

	    // 폼 제출
	    var form = document.getElementById("postForm");
	    form.submit();

	    // 서버 측 처리를 위한 지연 후 페이지 이동
	    setTimeout(function() {
	        window.location.href = 'adminBlist.do';
	    }, 1000); // 필요에 따라 지연 시간 조정
	}
	
	</script>
</body>
</html>