<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>결재 문서 상세</title>
<!-- 공통 스타일과 라이브러리 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
	<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
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
<main>
<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-10">
            <h2 class="mb-4">📄 결재 문서 상세</h2>
            
            <!-- 성공/에러 메시지 -->
            <c:if test="${not empty success}">
                <div class="alert alert-success">${success}</div>
            </c:if>
            <c:if test="${not empty error}">
                <div class="alert alert-danger">${error}</div>
            </c:if>
            
            <!-- 문서 정보 -->
            <div class="card mb-4">
                <div class="card-header">
                    <h5 class="mb-0">📋 문서 정보</h5>
                </div>
                <div class="card-body">
                    <table class="table table-bordered">
                        <tr>
                            <th style="width: 150px; background-color: #f8f9fa;">문서 번호</th>
                            <td>${document.documentId}</td>
                            <th style="width: 150px; background-color: #f8f9fa;">문서 타입</th>
                            <td>${document.documentType}</td>
                        </tr>
                        <tr>
                            <th style="background-color: #f8f9fa;">제목</th>
                            <td colspan="3">${document.title}</td>
                        </tr>
                        <tr>
                            <th style="background-color: #f8f9fa;">기안자</th>
                            <td>${document.authorId}</td>
                            <th style="background-color: #f8f9fa;">작성일</th>
                            <td>${document.createdAt}</td>
                        </tr>
                        <tr>
                            <th style="background-color: #f8f9fa;">결재 상태</th>
                            <td colspan="3">
                                <c:choose>
                                    <c:when test="${document.status == 'PENDING'}">
                                        <span class="badge bg-warning text-dark">결재 대기</span>
                                    </c:when>
                                    <c:when test="${document.status == 'APPROVED'}">
                                        <span class="badge bg-success">승인 완료</span>
                                    </c:when>
                                    <c:when test="${document.status == 'REJECTED'}">
                                        <span class="badge bg-danger">반려</span>
                                    </c:when>
                                </c:choose>
                            </td>
                        </tr>
                        <tr>
                            <th style="background-color: #f8f9fa;">내용</th>
                            <td colspan="3">
                                <div style="min-height: 100px; white-space: pre-line;">${document.content}</div>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
            
            <!-- 결재선 정보 -->
            <div class="card mb-4">
                <div class="card-header">
                    <h5 class="mb-0">👥 결재선 현황</h5>
                </div>
                <div class="card-body">
                    <table class="table table-bordered">
                        <thead class="table-light">
                            <tr>
                                <th style="width: 80px;">순서</th>
                                <th>결재자</th>
                                <th style="width: 120px;">상태</th>
                                <th style="width: 150px;">처리일시</th>
                                <th>의견</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="line" items="${approvalLines}">
                                <tr>
                                    <td class="text-center">${line.approvalOrder}차</td>
                                    <td>${line.approverName} (${line.approverId})</td>
                                    <td class="text-center">
                                        <c:choose>
                                            <c:when test="${line.status == 'PENDING'}">
                                                <c:if test="${currentPendingLine.lineId == line.lineId}">
                                                    <span class="badge bg-primary">진행중</span>
                                                </c:if>
                                                <c:if test="${currentPendingLine.lineId != line.lineId}">
                                                    <span class="badge bg-secondary">대기</span>
                                                </c:if>
                                            </c:when>
                                            <c:when test="${line.status == 'APPROVED'}">
                                                <span class="badge bg-success">승인</span>
                                            </c:when>
                                            <c:when test="${line.status == 'REJECTED'}">
                                                <span class="badge bg-danger">반려</span>
                                            </c:when>
                                        </c:choose>
                                    </td>
                                    <td class="text-center">
                                        <c:if test="${not empty line.approvedAt}">
                                            ${line.approvedAt}
                                        </c:if>
                                    </td>
                                    <td>
                                        <c:if test="${not empty line.comment}">
                                            ${line.comment}
                                        </c:if>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            
            <!-- 결재 버튼 (권한이 있을 때만 표시) -->
            <c:if test="${canApprove && document.status == 'PENDING'}">
                <div class="card mb-4">
                    <div class="card-header">
                        <h5 class="mb-0">✅ 결재 처리</h5>
                    </div>
                    <div class="card-body">
                        <!-- 승인 -->
                        <form method="post" action="${pageContext.request.contextPath}/document/approve.do" class="d-inline">
                            <input type="hidden" name="documentId" value="${document.documentId}">
                            <div class="mb-3">
                                <label for="approveComment" class="form-label">승인 의견 (선택사항)</label>
                                <textarea class="form-control" id="approveComment" name="comment" rows="2" 
                                         placeholder="승인 의견을 입력하세요 (선택사항)"></textarea>
                            </div>
                            <button type="submit" class="btn btn-success me-2" 
                                   onclick="return confirm('결재를 승인하시겠습니까?')">
                                ✅ 승인
                            </button>
                        </form>
                        
                        <!-- 반려 -->
                        <button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#rejectModal">
                            ❌ 반려
                        </button>
                    </div>
                </div>
            </c:if>
            
            <!-- 하단 버튼 -->
            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                <a href="${pageContext.request.contextPath}/approval/inbox.do" class="btn btn-primary">📥 나의 결재함</a>
                <a href="${pageContext.request.contextPath}/dashboard.do" class="btn btn-secondary">🏠 대시보드</a>
            </div>
        </div>
    </div>
</div>

<!-- 반려 모달 -->
<div class="modal fade" id="rejectModal" tabindex="-1">
    <div class="modal-dialog">
        <div class="modal-content">
            <form method="post" action="${pageContext.request.contextPath}/document/reject.do">
                <input type="hidden" name="documentId" value="${document.documentId}">
                <div class="modal-header">
                    <h5 class="modal-title">❌ 결재 반려</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"></button>
                </div>
                <div class="modal-body">
                    <div class="mb-3">
                        <label for="rejectComment" class="form-label">반려 사유 <span class="text-danger">*</span></label>
                        <textarea class="form-control" id="rejectComment" name="comment" rows="4" 
                                 placeholder="반려 사유를 입력하세요" required></textarea>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">취소</button>
                    <button type="submit" class="btn btn-danger" 
                           onclick="return confirm('결재를 반려하시겠습니까?')">반려</button>
                </div>
            </form>
        </div>
    </div>
</div>
</main>
<%@ include file="../frame/footer.jsp"%>
<!-- Bootstrap JS Bundle -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>