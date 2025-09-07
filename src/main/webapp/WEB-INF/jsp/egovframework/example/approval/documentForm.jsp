<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>결재 문서 작성</title>
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
	<main class="container mt-5">
	    <div class="row justify-content-center">
	        <div class="col-md-8">
	            <h2 class="mb-4">결재 문서 작성</h2>
	            <form method="post" action="${pageContext.request.contextPath}/document/submit.do">
	                <div class="mb-3">
	                    <label for="documentType" class="form-label">문서 타입</label>
	                    <select class="form-select" id="documentType" name="documentType" required>
	                        <option value="">문서 타입을 선택하세요</option>
	                        <option value="출장비신청서">출장비신청서</option>
	                        <option value="휴가신청서">휴가신청서</option>
	                        <option value="구매요청서">구매요청서</option>
	                        <option value="교육신청서">교육신청서</option>
	                        <option value="기타">기타</option>
	                    </select>
	                </div>
	                <div class="mb-3">
	                    <label for="title" class="form-label">제목</label>
	                    <input type="text" class="form-control" id="title" name="title" maxlength="255" required />
	                </div>
	                <div class="mb-3">
	                    <label for="content" class="form-label">내용</label>
	                    <textarea class="form-control" id="content" name="content" rows="10" required></textarea>
	                </div>
	                <div class="d-grid gap-2 d-md-flex justify-content-md-end">
	                    <button type="submit" class="btn btn-primary">문서 등록</button>
	                    <a href="${pageContext.request.contextPath}/dashboard.do" class="btn btn-secondary">취소</a>
	                </div>
	            </form>
	
	            <c:if test="${not empty error}">
	                <div class="alert alert-danger mt-3">${error}</div>
	            </c:if>
	        </div>
	    </div>
	</main>
    <%@ include file="../frame/footer.jsp"%>
    <!-- Bootstrap JS Bundle -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</body>
</html>