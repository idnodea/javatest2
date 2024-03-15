<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<%
    String adId = request.getParameter("adminId");
    String adEmail = request.getParameter("adminEmail");

    if ("admin".equals(adId) && "admin@korea.com".equals(adEmail)) {
        // 관리자 인증 성공, 세션에 관리자 정보 저장
        session.setAttribute("isAdmin", true);
        response.sendRedirect("adminForm.jsp");
    } else {
        // 관리자 인증 실패
        out.println("<script>alert('관리자 정보가 올바르지 않습니다.'); history.go(-1);</script>");
        
    }
%>