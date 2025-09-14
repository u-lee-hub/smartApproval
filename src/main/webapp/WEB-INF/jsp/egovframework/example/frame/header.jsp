<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<nav class="navbar navbar-expand-lg navbar-light bg-light fixed-top shadow-sm">
    <div class="container">
        <a class="navbar-brand" href="/dashboard.do">SmartApproval</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navmenu">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navmenu">
            <ul class="navbar-nav me-auto">
                <li class="nav-item"><a class="nav-link" href="/dashboard.do">대시보드</a></li>
                
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/document/form.do">📝 문서작성</a></li>
                <li class="nav-item"><a class="nav-link" href="${pageContext.request.contextPath}/approval/inbox.do">📥 나의 결재함</a></li>
                
                
                <li class="nav-item"><a class="nav-link" href="/approval/form.do">결재 등록</a></li>
                <li class="nav-item"><a class="nav-link" href="/approval/pendingList.do">결재 대기함</a></li>
                <li class="nav-item"><a class="nav-link" href="/document/drafts.do">기안 문서함</a></li>
                <li class="nav-item"><a class="nav-link" href="/admin/users.do">관리자</a></li>
            </ul>
            <ul class="navbar-nav ms-auto">
                <li class="nav-item"><a class="nav-link" href="/logout.do">로그아웃</a></li>
            </ul>
        </div>
    </div>
</nav>
