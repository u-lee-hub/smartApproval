<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<nav class="navbar navbar-expand-lg navbar-dark bg-primary fixed-top">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/dashboard.do">U0컴퍼니 전자 결재</a>
        
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/dashboard.do">대시보드</a>
                </li>
                <c:if test="${sessionScope.user != null}">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/document/form.do">문서작성</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/approval/inbox.do">나의 결재함</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/document/myDocuments.do">내 문서</a>
                    </li>
                </c:if>
            </ul>
            
            <ul class="navbar-nav">
                <c:choose>
                    <c:when test="${sessionScope.user != null}">
                    	<!-- 로그인 상태 -->
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
                                👤 ${sessionScope.user.userName}
                            </a>
                            <ul class="dropdown-menu">
                                <li><a class="dropdown-item" href="${pageContext.request.contextPath}/logout.do">로그아웃</a></li>
                            </ul>
                        </li>
                    </c:when>
                    <c:otherwise>
                    	<!-- 로그아웃 상태 -->
                        <li class="nav-item me-2">
                            <a class="nav-link btn btn-outline-light" href="${pageContext.request.contextPath}/register.do">회원가입</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link btn btn-light text-primary" href="${pageContext.request.contextPath}/login.do">🔑 로그인</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>