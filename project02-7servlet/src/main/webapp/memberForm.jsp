<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
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
		<form action="memberInput.do" method="post"
			onsubmit="return validateForm();">
			<div class="mb-3">
				<label for="idInput" class="form-label">아이디</label> <input
					type="text" name="id" class="form-control" id="idInput"
					placeholder="아이디">
			</div>
			<div class="mb-3">
				<label for="emailInput" class="form-label">이메일</label> <input
					type="email" name="email" class="form-control" id="emailInput"
					placeholder="name@example.com">
			</div>
			<div class="mb-3">
				<label for="nameInput" class="form-label">이름</label> <input
					type="text" name="name" class="form-control" id="nameInput"
					placeholder="이름">
			</div>
			<button type="submit" class="btn btn-primary">등록</button>
		</form>
	</div>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.1/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-HwwvtgBNo3bZJJLYd8oVXjrBZt8cqVSpeBNS5n7C8IVInixGAoxmnlMuBnhbgrkm"
		crossorigin="anonymous"></script>
	<script>
		//function validateForm() {
		//	var id = document.getElementById("idInput").value;
		//	var email = document.getElementById("emailInput").value;
		//	var name = document.getElementById("nameInput").value;
		//	if (id.trim() === "" || email.trim() === "" || name.trim() === "") {
		//		alert("아이디, 이메일, 이름을 모두 입력해주세요.");
		//		return false; // 폼 제출 중단
		//	}
		//	return true; // 폼 제출 계속
		//}

		//function validateForm() {
		//var id = document.getElementById("idInput").value;
		//var email = document.getElementById("emailInput").value;
		//var name = document.getElementById("nameInput").value;

		// admin이라는 ID 사용 금지 검사
		//if (id.trim().toLowerCase() === "admin") {
		//alert("admin은 사용할 수 없는 아이디입니다.");
		//return false; // 폼 제출 중단
		//}

		//if (id.trim() === "" || email.trim() === "" || name.trim() === "") {
		//alert("아이디, 이메일, 이름을 모두 입력해주세요.");
		//return false; // 폼 제출 중단
		//}
		//return true; // 폼 제출 계속
		//}
		function validateForm() {
			var id = document.getElementById("idInput").value;
			var name = document.getElementById("nameInput").value;

			// "admin" 아이디 사용 금지 검사
			if (id.trim().toLowerCase() === "admin") {
				alert("admin은 사용할 수 없는 아이디입니다.");
				return false; // 폼 제출 중단
			}

			// 아이디와 이름이 일치하는지 검사
			if (id.trim() !== name.trim()) {
				alert("아이디와 이름이 일치해야 합니다.");
				return false; // 폼 제출 중단
			}

			// 아이디, 이메일, 이름 입력 여부 검사
			if (id.trim() === "" || name.trim() === "") {
				alert("아이디와 이름을 모두 입력해주세요.");
				return false; // 폼 제출 중단
			}

			return true; // 폼 제출 계속
		}
	</script>
</body>
</html>