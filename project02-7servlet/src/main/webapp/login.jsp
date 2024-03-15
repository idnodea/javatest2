<%@page import="mvjsp.board.model.Member"%>
<%@page import="mvjsp.board.dao.MemberDao"%>
<%@ page contentType="text/html; charset=utf-8"%>
<%
//String memberId = request.getParameter("memberId");
//session.setAttribute("MEMBER", memberId);

String id = request.getParameter("id");
String email = request.getParameter("email");
Member member = MemberDao.getInstance().selectMember(id);

// "admin"이라는 ID를 사용하는 것을 금지하는 조건 추가
if("admin".equalsIgnoreCase(id)) {
    // "admin" ID 사용 금지에 대한 처리
    // 예: 로그인 폼으로 리다이렉트하며, 사용자에게 특정 메시지를 보여줄 수 있습니다.
    response.sendRedirect("loginForm.jsp?error=adminNotAllowed");
} else if (member != null && member.getId().equals(id)) {
    // 로그인 성공 시 세션에 사용자 정보 저장
    session.setAttribute("MEMBER", member);
    session.setAttribute("MEMBERID", member.getId());
    //response.sendRedirect("index.jsp");
    response.sendRedirect("MList.jsp");
} else {
    // 로그인 실패 시 메시지 출력 후 이전 페이지로 이동
    response.sendRedirect("loginForm.jsp?error=invalidCredentials");
}


//String id = request.getParameter("id");
//String email = request.getParameter("email");
//String ck = request.getParameter("ck");
//Member member = MemberDao.getInstance().selectMember(id);

//if (member != null && member.getId().equals(id)) {
	// 로그인 성공 시 세션에 사용자 정보 저장
	//session.setAttribute("MEMBER", member);
	//session.setAttribute("MEMBERID", member.getId()); // 로그인 상태 확인을 위해 ID도 세션에 저장
	//response.sendRedirect("index.jsp");
//} else {
	// 로그인 실패 시 메시지 출력 후 이전 페이지로 이동
//	response.sendRedirect("loginForm.jsp");
//}
%>
<html>
<head>
<title>로그인</title>
</head>
<body>로그인 처리
</body>
</html>


