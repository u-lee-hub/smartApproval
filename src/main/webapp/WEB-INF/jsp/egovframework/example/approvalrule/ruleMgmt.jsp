<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>결재 규칙 관리</title>
    <style>
        body { font-family: Arial, sans-serif; margin: 20px; }
        .container { max-width: 1000px; margin: 0 auto; }
        table { width: 100%; border-collapse: collapse; margin: 20px 0; }
        th, td { border: 1px solid #ddd; padding: 12px; text-align: center; }
        th { background-color: #f5f5f5; font-weight: bold; }
        .btn { padding: 8px 16px; margin: 2px; border: none; border-radius: 4px; cursor: pointer; }
        .btn-primary { background-color: #007bff; color: white; }
        .btn-success { background-color: #28a745; color: white; }
        .btn-secondary { background-color: #6c757d; color: white; }
        .alert { padding: 15px; margin: 20px 0; border-radius: 4px; }
        .alert-success { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
        .alert-error { background-color: #f8d7da; color: #721c24; border: 1px solid #f5c6cb; }
        .form-inline { display: inline-block; margin-right: 10px; }
        .form-inline input, .form-inline select { margin: 0 5px; padding: 5px; }
    </style>
</head>
<body>
    <div class="container">
        <h1>🔧 결재 규칙 관리</h1>
        
        <div style="margin-bottom: 20px;">
            <span>관리자: <strong>${userName}</strong></span>
            <a href="/dashboard.do" class="btn btn-secondary" style="float: right;">대시보드로 돌아가기</a>
        </div>

        <!-- 성공/오류 메시지 -->
        <c:if test="${not empty success}">
            <div class="alert alert-success">${success}</div>
        </c:if>
        <c:if test="${not empty error}">
            <div class="alert alert-error">${error}</div>
        </c:if>

        <!-- 규칙 목록 테이블 -->
        <table>
            <thead>
                <tr>
                    <th>규칙 ID</th>
                    <th>문서 타입</th>
                    <th>금액 범위</th>
                    <th>결재자 수</th>
                    <th>규칙 설명</th>
                    <th>생성일</th>
                    <th>관리</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="rule" items="${rules}">
                <tr>
                    <td>${rule.ruleId}</td>
                    <td>${rule.documentType}</td>
                    <td>
                        <fmt:formatNumber value="${rule.minAmount}" pattern="#,###"/>원 ~ 
                        <fmt:formatNumber value="${rule.maxAmount}" pattern="#,###"/>원
                    </td>
                    <td>
                        <form action="/admin/rules/update.do" method="post" class="form-inline">
                            <input type="hidden" name="ruleId" value="${rule.ruleId}">
                            <select name="approverCount">
                                <option value="1" ${rule.approverCount == 1 ? 'selected' : ''}>1단계</option>
                                <option value="2" ${rule.approverCount == 2 ? 'selected' : ''}>2단계</option>
                                <option value="3" ${rule.approverCount == 3 ? 'selected' : ''}>3단계</option>
                            </select>
                        </form>
                    </td>
                    <td>
                        <form action="/admin/rules/update.do" method="post" class="form-inline">
                            <input type="hidden" name="ruleId" value="${rule.ruleId}">
                            <input type="hidden" name="approverCount" value="${rule.approverCount}">
                            <input type="text" name="ruleDescription" value="${rule.ruleDescription}" 
                                   style="width: 200px;" maxlength="100">
                            <input type="submit" value="수정" class="btn btn-success">
                        </form>
                    </td>
                    <td>${rule.createdAt}</td>
                    <td>
                        <span style="color: #666; font-size: 12px;">활성</span>
                    </td>
                </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- 규칙 설명 -->
        <div style="margin-top: 30px; padding: 15px; background-color: #f8f9fa; border-radius: 5px;">
            <h3>💡 규칙 설명</h3>
            <ul>
                <li><strong>1단계:</strong> 팀장만</li>
                <li><strong>2단계:</strong> 팀장 → 부서장</li>
                <li><strong>3단계:</strong> 팀장 → 부서장 → 대표</li>
            </ul>
            <p><em>금액 범위에 따라 자동으로 결재선이 설정됩니다.</em></p>
        </div>
    </div>
</body>
</html>
