<%@page import="mvjsp.board.model.Member"%>
<%@page import="mvjsp.board.dao.MemberDao"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%
//String memberId = request.getParameter("memberId");
//session.setAttribute("MEMBER", memberId);

String id = request.getParameter("adminId");
String email = request.getParameter("adminEmail");

Member member = MemberDao.getInstance().selectMember(id);

if (member != null && member.getId().equals(id)) {
	// 로그인 성공 시 세션에 사용자 정보 저장
	session.setAttribute("MEMBER", member);
	session.setAttribute("MEMBERID", member.getId()); // 로그인 상태 확인을 위해 ID도 세션에 저장
	response.sendRedirect("index.jsp");
} else {
	// 로그인 실패 시 메시지 출력 후 이전 페이지로 이동
	response.sendRedirect("adminLoginForm.jsp");
}
%>
<html>
<head>
<title>로그인</title>
</head>
<body>로그인 처리
</body>
</html>