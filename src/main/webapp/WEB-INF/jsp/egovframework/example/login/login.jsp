<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>로그인</title>
   	<!-- 공통 스타일과 라이브러리 -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
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
    <main class="container login-container" role="main">
        <h2 class="mb-4 text-center">로그인</h2>
        <form action="login.do" method="post" novalidate>
            <div class="mb-3">
                <label for="userId" class="form-label">아이디</label>
                <input type="text" class="form-control" id="userId" name="userId" required />
            </div>
            <div class="mb-3">
                <label for="password" class="form-label">비밀번호</label>
                <input type="password" class="form-control" id="password" name="password" required />
            </div>
            <button type="submit" class="btn btn-primary w-100">로그인</button>
        </form>
        <c:if test="${not empty error}">
            <div class="alert alert-danger mt-3" role="alert">
                ${error}
            </div>
        </c:if>
    </main>
    	
    <%@ include file="../frame/footer.jsp"%>
    <!-- Bootstrap JS Bundle -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
