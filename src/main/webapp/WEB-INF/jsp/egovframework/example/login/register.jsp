<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>회원가입</title>
<!-- 공통 스타일과 라이브러리 -->
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet" />
<style>
body {
	padding-top: 70px;
	background-color: #f8f9fa;
}

.login-container {
	max-width: 400px;
	margin: 0 auto;
}
</style>
</head>
<body>
	<%@ include file="../frame/header.jsp"%>
	<%-- 메인 컨텐츠 --%>
	<main class="container mt-5" role="main">
		<h2>회원가입</h2>
		<form method="post"
			action="register.do">
			<div class="mb-3">
				<label for="userId" class="form-label">아이디</label> <input
					type="text" class="form-control" id="userId" name="userId" required />
			</div>
			<div class="mb-3">
				<label for="password" class="form-label">비밀번호</label> <input
					type="password" class="form-control" id="password" name="password"
					required />
			</div>
			<div class="mb-3">
				<label for="userName" class="form-label">이름</label> <input
					type="text" class="form-control" id="userName" name="userName"
					required />
			</div>
			<div class="mb-3">
				<label for="email" class="form-label">이메일</label> <input
					type="email" class="form-control" id="email" name="email" />
			</div>
			<div class="mb-3">
				<label for="phone" class="form-label">전화번호</label> <input type="tel"
					class="form-control" id="phone" name="phone" />
			</div>


			<input type="hidden" name="roleId" value="USER" /> 
			<input type="hidden" name="deptId" value="DEPT001" />
			<button type="submit" class="btn btn-primary">가입하기</button>
		</form>

		<c:if test="${not empty error}">
			<div class="alert alert-danger mt-3">${error}</div>
		</c:if>

		<c:if test="${not empty success}">
			<div class="alert alert-success mt-3">${success}</div>
		</c:if>
	</main>
	
	<%@ include file="../frame/footer.jsp"%>

	<!-- Bootstrap JS Bundle -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
