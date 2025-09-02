<%-- 미사용(사용시 컨트롤러에서 contentPage 처리 필요) --%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>${pageTitle != null ? pageTitle : 'SmartApproval'}</title>
    <!-- 공통 스타일과 라이브러리 -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body>
    <%@ include file="header.jsp" %>

    <main class="container my-4" role="main">
        <jsp:include page="${contentPage}" />
    </main>

    <%@ include file="footer.jsp" %>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>