<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8" />
	<title>메인 대시보드</title>
	
	<!-- 공통 스타일과 라이브러리 -->
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
	<!-- Font Awesome 아이콘 CSS 추가 -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" />
	
	<style>
        body { padding-top: 70px; }
    </style>
</head>
<body>
	<%@ include file="../frame/header.jsp"%>
	
	<%-- 메인 컨텐츠 --%>
	<main class="container my-4" role="main">
		<h2>${userName}님으로 로그인하셨습니다!</h2>
		<a href="logout.do">로그아웃</a>
		<div class="row">
			<div class="col-12">
				<h1 class="mb-4">전자결재 시스템 대시보드</h1>
			</div>
		</div>
<%-- 		<div class="mt-3 text-center">
			<a href="${pageContext.request.contextPath}/document/form.do"
				class="btn btn-success btn-lg"> 
				<i class="fas fa-plus"></i> 새 결재 문서 작성
			</a>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="card shadow-sm mb-4">
					<div class="card-header bg-primary text-white">
						<h5 class="mb-0">내가 올린 결재 현황</h5>
					</div>
					<div class="card-body">
						<p class="text-muted">총 3건의 결재 요청서가 진행 중입니다.</p>
						<ul class="list-group list-group-flush">
							<li
								class="list-group-item d-flex justify-content-between align-items-center">
								출장비 신청서 <span class="badge bg-warning text-dark">결재 대기</span>
							</li>
							<li
								class="list-group-item d-flex justify-content-between align-items-center">
								휴가 신청서 <span class="badge bg-info text-white">진행 중</span>
							</li>
							<li
								class="list-group-item d-flex justify-content-between align-items-center">
								물품 구매 요청서 <span class="badge bg-success">완료</span>
							</li>
						</ul>
					</div>
				</div>
			</div>

			<div class="col-md-6">
				<div class="card shadow-sm mb-4">
					<div class="card-header bg-danger text-white">
						<h5 class="mb-0">내가 결재해야 할 문서</h5>
					</div>
					<div class="card-body">
						<p class="text-muted">2건의 결재 문서가 대기 중입니다.</p>
						<ul class="list-group list-group-flush">
							<li class="list-group-item">
								<div class="d-flex justify-content-between">
									<strong>교육비 지원 신청</strong> <small class="text-muted">기안자:
										홍길동</small>
								</div>
							</li>
							<li class="list-group-item">
								<div class="d-flex justify-content-between">
									<strong>장비 구매 신청</strong> <small class="text-muted">기안자:
										김영희</small>
								</div>
							</li>
						</ul>
						<div class="mt-3">
							<a href="/approval/pendingList.do"
								class="btn btn-outline-danger btn-sm">결재 대기함 보기</a>
						</div>
					</div>
				</div>
			</div>
		</div> --%>

		<div class="row mt-4">
			<div class="col-md-6">
				<div class="card h-100">
					<div class="card-body text-center">
						<h5 class="card-title">📝 문서 작성</h5>
						<p class="card-text">새로운 결재 문서를 작성합니다.</p>
						<a href="${pageContext.request.contextPath}/document/form.do"
							class="btn btn-primary">문서 작성</a>
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="card h-100">
					<div class="card-body text-center">
						<h5 class="card-title">📥 나의 결재함</h5>
						<p class="card-text">결재 대기 중인 문서를 처리합니다.</p>
						<a href="${pageContext.request.contextPath}/approval/inbox.do"
							class="btn btn-success">결재함 이동</a>
					</div>
				</div>
			</div>
		</div>






		<div class="row">
			<div class="col-12">
				<div class="card shadow-sm">
					<div class="card-header bg-secondary text-white">
						<h5 class="mb-0">📢 최근 공지사항 / 알림</h5>
					</div>
					<div class="card-body">
						<ul class="list-unstyled">
							<li class="mb-2"><span class="badge bg-primary">2025-08-28</span>
								<strong>시스템 점검 안내</strong> - 8월 30일 오후 2시~6시 점검 예정</li>
							<li class="mb-2"><span class="badge bg-success">2025-08-27</span>
								<strong>신규 결재양식 배포</strong> - 연구개발비 신청 양식이 추가되었습니다</li>
							<li class="mb-0"><span class="badge bg-info">2025-08-26</span>
								<strong>개인정보 처리방침 변경</strong> - 개정된 정책을 확인해주세요</li>
						</ul>
					</div>
				</div>
			</div>
		</div>
	</main>

	<%@ include file="../frame/footer.jsp"%>
	
	<!-- Bootstrap JS Bundle -->
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>

