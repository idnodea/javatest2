<%@ page contentType="text/html; charset=UTF-8" language="java" %>
<html>
<head>
    <title>관리자 로그인</title>
</head>
<body>
    <h2>관리자 로그인</h2>
    <form action="adminLoginCheck.jsp" method="post">
        <div>
            <label for="id">ID:</label>
            <input type="text" id="adminId" name="adminId" required>
        </div>
        <div>
            <label for="name">Name:</label>
            <input type="text" id="adminEmail" name="adminEmail" required>
        </div>
        <div>
            <button type="submit">로그인</button>
        </div>
    </form>
    <div class="goindex" style="border:1px solid black; width:50px; height:50px;">
    <a href="index.jsp" style="text-decoration-line:none;" text:"center center;">
      <p>복귀</p>
    </a>
  </div>
</body>
</html>