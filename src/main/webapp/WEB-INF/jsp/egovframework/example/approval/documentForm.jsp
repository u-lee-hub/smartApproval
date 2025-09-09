<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>결재 문서 작성</title>
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
<main class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-10">
            <h2 class="mb-4">결재 문서 작성</h2>
            <form id="documentForm" method="post" action="${pageContext.request.contextPath}/document/submit.do">
                
                <!-- 문서 기본 정보 -->
                <div class="card mb-4">
                    <div class="card-header">
                        <h5 class="mb-0">문서 정보</h5>
                    </div>
                    <div class="card-body">
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
                            <textarea class="form-control" id="content" name="content" rows="8" required></textarea>
                        </div>
                    </div>
                </div>

                <!-- 결재선 지정 -->
                <div class="card mb-4">
                    <div class="card-header">
                        <h5 class="mb-0">결재선 지정</h5>
                    </div>
                    <div class="card-body">
                        <div class="mb-3">
                            <label for="approverSelect" class="form-label">결재자 선택</label>
                            <select class="form-select" id="approverSelect">
                                <option value="">결재자를 선택하세요</option>
                                <c:forEach var="user" items="${deptUsers}">
                                    <option value="${user.userId}" data-name="${user.userName}">${user.userName} (${user.userId})</option>
                                </c:forEach>
                            </select>
                        </div>
                        <button type="button" class="btn btn-outline-primary mb-3" onclick="addApprover()">
                            ➕ 결재자 추가
                        </button>
                        
                        <!-- 결재선 목록 -->
                        <div id="approvalLineList">
                            <h6>결재 순서</h6>
                            <div class="list-group" id="approverList">
                                <div class="text-muted text-center p-3" id="emptyMessage">
                                    결재자를 추가해주세요.
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- HTML 배열 형태로 결재선 데이터 전송 -->
                <div id="approvalInputs"></div>

                <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                    <button type="submit" class="btn btn-primary btn-lg">문서 등록</button>
                    <a href="${pageContext.request.contextPath}/dashboard.do" class="btn btn-secondary btn-lg">❌ 취소</a>
                </div>
            </form>

            <c:if test="${not empty error}">
                <div class="alert alert-danger mt-3">${error}</div>
            </c:if>
        </div>
    </div>
</main>

<script>
let approvalLineData = [];

// 결재자 추가
function addApprover() {
    const select = document.getElementById('approverSelect');
    const selectedOption = select.options[select.selectedIndex];
    
    if (selectedOption.value === '') {
        alert('결재자를 선택해주세요.');
        return;
    }
    
    const approverId = selectedOption.value;
    const approverName = selectedOption.getAttribute('data-name');
    
    // 중복 체크
    if (approvalLineData.some(item => item.approverId === approverId)) {
        alert('이미 추가된 결재자입니다.');
        return;
    }
    
    // 결재선 데이터 추가
    approvalLineData.push({
        approverId: approverId,
        approverName: approverName
    });
    
    // 화면에 표시 및 hidden input 생성
    updateApproverList();
    
    // 선택 초기화
    select.selectedIndex = 0;
}

// 결재자 제거
function removeApprover(index) {
    approvalLineData.splice(index, 1);
    updateApproverList();
}

// 결재자 목록 및 hidden input 업데이트
function updateApproverList() {
    const approverList = document.getElementById('approverList');
    const emptyMessage = document.getElementById('emptyMessage');
    const inputsDiv = document.getElementById('approvalInputs');
    
    // 목록 초기화
    approverList.innerHTML = '';
    inputsDiv.innerHTML = ''; // hidden input 초기화
    
    if (approvalLineData.length === 0) {
        // 빈 메시지 표시
    	const emptyDiv = document.createElement('div');
    	emptyDiv.className = 'text-muted text-center p-3';
    	emptyDiv.textContent = '결재자를 추가해주세요.';
    	approverList.appendChild(emptyDiv);
    } else {
        // 결재자 목록 표시
        approvalLineData.forEach((approver, index) => {
        	
            // 1. 화면에 결재자 목록 표시
            const listItem = document.createElement('div');
            listItem.className = 'list-group-item d-flex justify-content-between align-items-center';
            
         	// 대신 문자열 결합 방식 사용 (JSP 충동 방지)
            //const orderText = (index + 1) + '차';
            const nameText = approver.approverName || '(이름없음)';
            const idText = approver.approverId || '(ID없음)';
            
            listItem.innerHTML = 
			    '<div>' +
			        '<span class="badge bg-primary rounded-pill me-2">' + (index + 1) + '차</span>' +
			        '<strong>' + nameText + '</strong>' +
			        '<small class="text-muted">(' + idText + ')</small>' +
			    '</div>' +
			    '<button type="button" class="btn btn-sm btn-outline-danger" onclick="removeApprover(' + index + ')">제거</button>';

            approverList.appendChild(listItem);
            
            // 2. 서버 전송용 hidden input 생성
            const idInput = document.createElement('input');
            idInput.type = 'hidden';
            idInput.name = 'approverIds';
            idInput.value = approver.approverId;
            inputsDiv.appendChild(idInput);
            
        });
    }
}

// 폼 제출 전 검증
document.getElementById('documentForm').addEventListener('submit', function(e) {
    if (approvalLineData.length === 0) {
        e.preventDefault();
        alert('결재자를 최소 1명 이상 지정해주세요.');
        return false;
    }
});

// 초기 상태 설정
document.addEventListener('DOMContentLoaded', function() {
    updateApproverList();
});

</script>
<%@ include file="../frame/footer.jsp"%>
<!-- Bootstrap JS Bundle -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>