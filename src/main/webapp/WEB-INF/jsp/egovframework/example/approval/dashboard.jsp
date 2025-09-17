<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8" />
	<title>전자 결재 시스템</title>
	
	<!-- 공통 스타일과 라이브러리 -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
	<!-- Font Awesome 아이콘 CSS 추가 -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" />
	
<style>
    body {
        padding-top: 70px;
        background-color: #f8f9fa;
    }
    
    /* 카드 높이 통일 */
    .equal-height {
        display: flex;
        align-items: stretch; /* 카드들을 같은 높이로 맞춤 */
    }
    
    .equal-height .card {
        display: flex;
        flex-direction: column;
        height: 100%; /* 부모 높이에 맞춤 */
    }
    
    .card-body {
        flex: 1; /* 남은 공간을 모두 차지 */
        display: flex;
        flex-direction: column;
    }
    
    .card-body .btn, .card-body .d-grid {
        margin-top: auto; /* 버튼들을 카드 하단으로 밀어냄 */
    }
    
    /* 리스트 영역 고정 높이 */
    .fixed-list-area {
        min-height: 200px; /* 최소 높이 설정 */
        max-height: 300px; /* 최대 높이 설정 */
        overflow-y: auto; /* 내용이 많을 때 스크롤 */
    }
    
    
    /* 문서 제목 링크 스타일 */
    .list-group-item a:not(.btn) {
        transition: color 0.2s ease;
    }
    
    .list-group-item a:not(.btn):hover {
        color: #0d6efd !important;
        text-decoration: underline !important;
    }
    
    .list-group-item:hover {
        background-color: #f8f9fa;
    }
    
    /* 버튼은 hover 시 기본 Bootstrap 스타일 유지 */
    .list-group-item .btn:hover {
        text-decoration: none !important;
    }

    .guest-overlay {
        position: relative;
        min-height: 400px; /* 고정 높이 */
    }
    .guest-overlay::before {
        content: '';
        position: absolute;
        top: 0;
        left: 0;
        right: 0;
        bottom: 0;
        background-color: rgba(255, 255, 255, 0.8);
        z-index: 1;
        border-radius: 0.375rem;
    }
    .guest-message {
        position: absolute;
        top: 50%;
        left: 50%;
        transform: translate(-50%, -50%);
        z-index: 2;
        text-align: center;
    }
</style>
</head>
<body>
	<%@ include file="../frame/header.jsp"%>
	
	<%-- 메인 컨텐츠 --%>
	<main class="container mt-5" role="main">
    <div class="row">
        <div class="col-12">
            <h1 class="mb-4">대시보드</h1>
            <c:choose>
                <c:when test="${isLoggedIn}">
                    <p class="text-muted mb-4">안녕하세요, <strong>${userName}</strong>님! 오늘도 좋은 하루 되세요.</p>
                </c:when>
                <c:otherwise>
                    <div class="alert alert-warning mb-4">
                        <div class="d-flex justify-content-between align-items-center">
                            <div>
                                <strong>⚠️ 로그인이 필요합니다!</strong>
                                <p class="mb-0">결재 시스템의 모든 기능을 사용하려면 로그인해주세요.</p>
                            </div>
                            <div>
				                <a href="${pageContext.request.contextPath}/register.do" class="btn btn-outline-primary me-2">회원가입</a>
				                <a href="${pageContext.request.contextPath}/login.do" class="btn btn-primary">🔑 로그인</a>
				            </div>
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
    
    <!-- 결재 현황 카드 -->
    <div class="row mb-4 equal-height">
        <div class="col-md-6">
            <div class="card shadow-sm mb-4 h-100 ${!isLoggedIn ? 'guest-overlay' : ''}">
                <div class="card-header bg-primary text-white">
                    <h5 class="mb-0">내가 올린 결재 현황</h5>
                </div>
                <div class="card-body">
                    <c:choose>
                        <c:when test="${isLoggedIn}">
                            <p class="text-muted">총 ${totalCount}건의 결재 요청서가 있습니다.</p>
                            
                            <!-- 상태별 통계 -->
                            <div class="row text-center mb-3">
                                <div class="col-4">
                                    <div class="card border-warning">
                                        <div class="card-body p-2">
                                            <h6 class="card-title text-warning">${pendingCount}</h6>
                                            <small class="text-muted">결재대기</small>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-4">
                                    <div class="card border-success">
                                        <div class="card-body p-2">
                                            <h6 class="card-title text-success">${approvedCount}</h6>
                                            <small class="text-muted">승인완료</small>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-4">
                                    <div class="card border-danger">
                                        <div class="card-body p-2">
                                            <h6 class="card-title text-danger">${rejectedCount}</h6>
                                            <small class="text-muted">반려</small>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            
                            <!-- 최근 문서 목록 -->
                            <div class="fixed-list-area">
	                            <c:choose>
	                                <c:when test="${empty myRecentDocuments}">
	                                    <div class="text-center text-muted py-3">
	                                        <p>작성한 결재 문서가 없습니다.</p>
	                                        <a href="${pageContext.request.contextPath}/document/form.do" class="btn btn-primary btn-sm">
	                                            문서 작성하기
	                                        </a>
	                                    </div>
	                                </c:when>
	                                <c:otherwise>
	                                    <ul class="list-group list-group-flush">
	                                        <c:forEach var="doc" items="${myRecentDocuments}">
	                                            <li class="list-group-item d-flex justify-content-between align-items-center">
	                                                <div>
                                                	    <strong>
                                                            <a href="${pageContext.request.contextPath}/document/detail.do?documentId=${doc.documentId}" 
                                                               class="text-decoration-none text-dark">
                                                                ${doc.title}
                                                            </a>
                                                        </strong>
	                                                    <br><small class="text-muted">${doc.documentType}</small>
	                                                </div>
	                                                <div class="text-end">
		                                                <!-- 상태 배지 -->
		                                                <c:choose>
		                                                    <c:when test="${doc.status == 'PENDING'}">
		                                                        <span class="badge bg-warning text-dark">결재대기</span>
		                                                    </c:when>
		                                                    <c:when test="${doc.status == 'APPROVED'}">
		                                                        <span class="badge bg-success">승인완료</span>
		                                                    </c:when>
		                                                    <c:when test="${doc.status == 'REJECTED'}">
		                                                        <span class="badge bg-danger">반려</span>
		                                                    </c:when>
		                                                    <c:otherwise>
		                                                        <span class="badge bg-secondary">${doc.status}</span>
		                                                    </c:otherwise>
		                                                </c:choose>
							                        </div>
	                                            </li>
	                                        </c:forEach>
	                                    </ul>
	                                </c:otherwise>
	                            </c:choose>
                            </div>
                            
                            <!-- 하단 버튼 -->
                            <div class="mt-3 text-center">
                                 <a href="${pageContext.request.contextPath}/document/form.do" class="btn btn-primary btn-sm me-2">문서 작성하기</a>
                                <a href="${pageContext.request.contextPath}/document/myDocuments.do" class="btn btn-outline-primary btn-sm">전체 보기</a>
                             </div>
                        </c:when>
                        <c:otherwise>
                            <!-- 로그아웃 상태 - 샘플 데이터 -->
                            <p class="text-muted">결재 현황을 확인하려면 로그인하세요.</p>
                            <div class="row text-center mb-3">
                                <div class="col-4">
                                    <div class="card border-warning">
                                        <div class="card-body p-2">
                                            <h6 class="card-title text-warning">-</h6>
                                            <small class="text-muted">결재대기</small>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-4">
                                    <div class="card border-success">
                                        <div class="card-body p-2">
                                            <h6 class="card-title text-success">-</h6>
                                            <small class="text-muted">승인완료</small>
                                        </div>
                                    </div>
                                </div>
                                <div class="col-4">
                                    <div class="card border-danger">
                                        <div class="card-body p-2">
                                            <h6 class="card-title text-danger">-</h6>
                                            <small class="text-muted">반려</small>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
                
                <!-- 로그아웃 상태일 때 오버레이 메시지 -->
                <c:if test="${!isLoggedIn}">
                    <div class="guest-message">
                        <div class="text-center">
                            <h5>🔒 로그인 필요</h5>
                            <p class="mb-3">결재 현황을 확인하려면<br>로그인해주세요</p>
                            <a href="${pageContext.request.contextPath}/login.do" class="btn btn-primary">
                                로그인
                            </a>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>

        <div class="col-md-6">
            <div class="card shadow-sm mb-4 ${!isLoggedIn ? 'guest-overlay' : ''}">
                <div class="card-header bg-danger text-white">
                    <h5 class="mb-0">내가 결재해야 할 문서</h5>
                </div>
                <div class="card-body">
                    <c:choose>
                        <c:when test="${isLoggedIn}">
                            <p class="text-muted">${pendingApprovalCount}건의 결재 문서가 대기 중입니다.</p>
                            
                            <!-- 결재 대기 문서 목록 (고정 높이 영역) -->
                            <div class="fixed-list-area">
	                            <c:choose>
	                                <c:when test="${empty pendingDocuments}">
	                                    <div class="text-center text-muted py-5">
	                                        <i class="bi bi-check-circle" style="font-size: 3rem;"></i>
	                                        <h6 class="mt-3">결재할 문서가 없습니다</h6>
	                                        <p>모든 결재가 완료되었습니다! 🎉</p>
	                                    </div>
	                                </c:when>
	                                <c:otherwise>
	                                    <ul class="list-group list-group-flush">
	                                        <c:forEach var="doc" items="${pendingDocuments}">
	                                            <li class="list-group-item">
	                                                <div class="d-flex justify-content-between align-items-center">
	                                                    <div>
	                                                        <strong>
	                                                            <a href="${pageContext.request.contextPath}/document/detail.do?documentId=${doc.documentId}" 
	                                                               class="text-decoration-none text-dark">
	                                                                ${doc.title}
	                                                            </a>
	                                                        </strong>
	                                                        <br>
	                                                        <small class="text-muted">
	                                                            ${doc.documentType} | 기안자: ${doc.authorId} | ${doc.createdAt}
	                                                        </small>
	                                                    </div>
	                                                    <a href="${pageContext.request.contextPath}/document/detail.do?documentId=${doc.documentId}" 
	                                                       class="btn btn-outline-danger btn-sm">
	                                                        처리
	                                                    </a>
	                                                </div>
	                                            </li>
	                                        </c:forEach>
	                                    </ul>
	                                </c:otherwise>
	                            </c:choose>
                            </div>
                            <!-- 하단 버튼 -->
                          	<div class="mt-3 text-center">
                                <a href="${pageContext.request.contextPath}/approval/inbox.do" class="btn btn-danger btn-sm">
                                    결재 대기함 보기
                                </a>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <!-- 로그아웃 상태 -->
                            <p class="text-muted">결재 대기 문서를 확인하려면 로그인하세요.</p>
                            <div class="text-center text-muted py-3">
                                <p>결재할 문서 현황을 확인할 수 있습니다.</p>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
                
                <!-- 로그아웃 상태일 때 오버레이 메시지 -->
                <c:if test="${!isLoggedIn}">
                    <div class="guest-message">
                        <div class="text-center">
                            <h5>🔒 로그인 필요</h5>
                            <p class="mb-3">결재 대기 문서를<br>확인하려면 로그인해주세요</p>
                            <a href="${pageContext.request.contextPath}/login.do" class="btn btn-primary">
                                로그인
                            </a>
                        </div>
                    </div>
                </c:if>
            </div>
        </div>
    </div>

    <!-- 메뉴 카드들 -->
    <!-- 
    <div class="row mt-4 equal-height">
        <div class="col-md-4">
            <div class="card h-100">
                <div class="card-body text-center">
                    <h5 class="card-title">문서 작성</h5>
                    <p class="card-text">새로운 결재 문서를 작성합니다.</p>
                    <c:choose>
                        <c:when test="${isLoggedIn}">
                            <a href="${pageContext.request.contextPath}/document/form.do" class="btn btn-primary">문서 작성</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/login.do" class="btn btn-outline-primary">로그인 후 이용</a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card h-100">
                <div class="card-body text-center">
                    <h5 class="card-title">나의 결재함</h5>
                    <p class="card-text">결재 대기 중인 문서를 처리합니다.</p>
                    <c:choose>
                        <c:when test="${isLoggedIn}">
                            <a href="${pageContext.request.contextPath}/approval/inbox.do" class="btn btn-success">결재함 이동</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/login.do" class="btn btn-outline-success">로그인 후 이용</a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        <div class="col-md-4">
            <div class="card h-100">
                <div class="card-body text-center">
                    <h5 class="card-title">내 문서</h5>
                    <p class="card-text">내가 작성한 문서를 조회합니다.</p>
                    <c:choose>
                        <c:when test="${isLoggedIn}">
                            <a href="${pageContext.request.contextPath}/document/myDocuments.do" class="btn btn-info">문서 목록</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/login.do" class="btn btn-outline-info">로그인 후 이용</a>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
    </div>
 	-->
</main>

	<%@ include file="../frame/footer.jsp"%>
	
	<!-- Bootstrap JS Bundle -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

