<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-4bw+/aepP/YC94hEpVNVgiZdgIC5+VKNBQNGCHeKRQN+PtmoHDEXuppvnDJzQIu9"
	crossorigin="anonymous">
</head>
<body>
	 <div class="container" style="padding-top: 50px">
        <form action="memberUpdate.do" method="post" onsubmit="return validateUpdateForm();">
            <div class="mb-3">
                <label for="membernoInput" class="form-label">회원번호</label>
                <input type="text" readonly name="memberno" value="${member.memberno}" class="form-control" id="membernoInput" placeholder="회원번호">
            </div>
            <div class="mb-3">
                <label for="idInput" class="form-label">아이디</label>
                <input type="text" name="id" value="${member.id}" class="form-control" id="idInput" placeholder="아이디">
            </div>
            <div class="mb-3">
                <label for="exampleFormControlInput1" class="form-label">이메일</label>
                <input type="email" name="email" value="${member.email}" class="form-control"
                    id="exampleFormControlInput1" placeholder="name@example.com">
            </div>
            <div class="mb-3">
                <label for="nameInput" class="form-label">이름</label>
                <input type="text" name="name" value="${member.name}" class="form-control" id="nameInput" placeholder="이름">
            </div>
            <button type="submit" class="btn btn-primary">수정</button>
        </form>
    </div>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm"
		crossorigin="anonymous"></script>
	<script>
    function validateUpdateForm() {
        var id = document.getElementById("idInput").value;
        var email = document.getElementById("exampleFormControlInput1").value;
        var name = document.getElementById("nameInput").value;
        if (id.trim() === "" || email.trim() === "" || name.trim() === "") {
            alert("아이디, 이메일, 이름을 모두 입력해주세요.");
            return false; // 폼 제출 중단
        }
        return true; // 폼 제출 계속
    }
</script>	
		
</body>
</html>