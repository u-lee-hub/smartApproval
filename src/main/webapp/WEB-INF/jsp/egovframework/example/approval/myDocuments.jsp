<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>내 문서 목록</title>
<!-- 공통 스타일과 라이브러리 -->
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
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
            <h2 class="mb-4">내가 작성한 문서</h2>
            
            <!-- 사용자 정보 -->
            <div class="alert alert-primary mb-4">
                <strong>${userName}</strong>님이 작성한 문서 목록입니다.
            </div>
            
            <!-- 문서 목록 -->
            <div class="card">
                <div class="card-header">
                    <h5 class="mb-0">문서 목록</h5>
                </div>
                <div class="card-body">
                    <c:choose>
                        <c:when test="${empty myDocuments}">
                            <div class="text-center text-muted py-5">
                                <i class="bi bi-file-earmark-text" style="font-size: 3rem;"></i>
                                <h5 class="mt-3">작성한 문서가 없습니다.</h5>
                                <p>새로운 문서를 작성해보세요.</p>
                                <a href="${pageContext.request.contextPath}/document/form.do" class="btn btn-primary">
                                    문서 작성하기
                                </a>
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
                                            <th style="width: 120px;">상태</th>
                                            <th style="width: 150px;">작성일</th>
                                            <th style="width: 100px;">상세</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="document" items="${myDocuments}" varStatus="status">
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
                                                <td class="text-center">
                                                    <c:choose>
                                                        <c:when test="${document.status == 'PENDING'}">
                                                            <span class="badge bg-warning text-dark">결재대기</span>
                                                        </c:when>
                                                        <c:when test="${document.status == 'APPROVED'}">
                                                            <span class="badge bg-success">승인완료</span>
                                                        </c:when>
                                                        <c:when test="${document.status == 'REJECTED'}">
                                                            <span class="badge bg-danger">반려</span>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="badge bg-secondary">${document.status}</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td class="text-center">
                                                    ${document.createdAt}
                                                </td>
                                                <td class="text-center">
                                                    <a href="${pageContext.request.contextPath}/document/detail.do?documentId=${document.documentId}" 
                                                       class="btn btn-outline-primary btn-sm">
                                                        보기
                                                    </a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                                <!-- 페이징 처리 -->
                                <c:if test="${not empty myDocuments}">
							    <!-- 페이징 네비게이션 -->
							    <nav aria-label="Page navigation" class="mt-4">
							        <ul class="pagination justify-content-center">
							            <!-- 이전 버튼 -->
							            <c:if test="${currentPage > 1}">
							                <li class="page-item">
							                    <a class="page-link" href="?page=${currentPage - 1}">이전</a>
							                </li>
							            </c:if>
							            
							            <!-- 페이지 번호들 -->
							            <c:forEach begin="${startPage}" end="${endPage}" var="pageNum">
							                <li class="page-item ${pageNum == currentPage ? 'active' : ''}">
							                    <a class="page-link" href="?page=${pageNum}">${pageNum}</a>
							                </li>
							            </c:forEach>
							            
							            <!-- 다음 버튼 -->
							            <c:if test="${currentPage < totalPages}">
							                <li class="page-item">
							                    <a class="page-link" href="?page=${currentPage + 1}">다음</a>
							                </li>
							            </c:if>
							        </ul>
							    </nav>
							    
							    <!-- 페이징 정보 -->
							    <div class="text-center text-muted">
							        <small>총 ${totalDocuments}건 중 ${(currentPage-1) * pageSize + 1}~${currentPage * pageSize > totalDocuments ? totalDocuments : currentPage * pageSize}건 표시 (${currentPage}/${totalPages} 페이지)</small>
							    </div>
							</c:if>
                                
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
            
            <!-- 하단 버튼 -->
            <div class="d-grid gap-2 d-md-flex justify-content-md-end mt-4">
                <a href="${pageContext.request.contextPath}/document/form.do" class="btn btn-primary">새 문서 작성</a>
                <a href="${pageContext.request.contextPath}/dashboard.do" class="btn btn-secondary">대시보드</a>
            </div>
        </div>
    </div>
</main>

<%@ include file="../frame/footer.jsp"%>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
