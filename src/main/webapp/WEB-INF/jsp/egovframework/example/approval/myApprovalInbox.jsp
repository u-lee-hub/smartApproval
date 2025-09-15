<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>나의 결재함</title>
	<!-- 공통 스타일과 라이브러리 -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
	<style>
	    body {
	        padding-top: 70px;
	        background-color: #f8f9fa;
	    }
	</style>
</head>
<body>
<%@ include file="../frame/header.jsp"%>

<%-- 메인 컨텐츠 --%>
<main class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-12">
            <h2 class="mb-4">나의 결재함</h2>
            
            <!-- 사용자 정보 -->
            <div class="alert alert-info mb-4">
                <strong>${userName}</strong>님에게 온 결재 대기 문서입니다.
            </div>
            
            <!-- 결재 대기 문서 목록 -->
            <div class="card">
                <div class="card-header">
                    <h5 class="mb-0">결재 대기 문서 목록</h5>
                </div>
                <div class="card-body">
                    <c:choose>
                        <c:when test="${empty inboxDocuments}">
                            <div class="text-center text-muted py-5">
                                <i class="bi bi-inbox-fill" style="font-size: 3rem;"></i>
                                <h5 class="mt-3">결재 대기 문서가 없습니다.</h5>
                                <p>새로운 결재 요청이 있을 때까지 기다려주세요.</p>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="table-responsive">
                                <table class="table table-hover">
                                    <thead class="table-light">
                                        <tr>
                                            <th style="width: 80px;">번호</th>
                                            <th style="width: 120px;">문서타입</th>
                                            <th>제목</th>
                                            <th style="width: 120px;">기안자</th>
                                            <th style="width: 100px;">상태</th>
                                            <th style="width: 120px;">작성일</th>
                                            <th style="width: 100px;">처리</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="document" items="${inboxDocuments}" varStatus="status">
                                            <tr>
                                                <td class="text-center">${status.count}</td>
                                                <td>
                                                    <span class="badge bg-secondary">${document.documentType}</span>
                                                </td>
                                                <td>
                                                    <a href="${pageContext.request.contextPath}/document/detail.do?documentId=${document.documentId}" 
                                                       class="text-decoration-none">
                                                        ${document.title}
                                                    </a>
                                                </td>
                                                <td>${document.authorId}</td>
                                                <td class="text-center">
                                                    <span class="badge bg-warning text-dark">결재대기</span>
                                                </td>
                                                <td class="text-center">
                                                    ${document.createdAt}
                                                </td>
                                                <td class="text-center">
                                                    <a href="${pageContext.request.contextPath}/document/detail.do?documentId=${document.documentId}" 
                                                       class="btn btn-primary btn-sm">
                                                        처리
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            
            <!-- 하단 버튼 -->
            <div class="d-grid gap-2 d-md-flex justify-content-md-end mt-4">
                <a href="${pageContext.request.contextPath}/dashboard.do" class="btn btn-secondary">대시보드</a>
            </div>
        </div>
    </div>
</main>

<%@ include file="../frame/footer.jsp"%>
<!-- Bootstrap JS Bundle -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>