<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.language"/>

<html>
<head>
    <jsp:include page="/WEB-INF/views/fragments/head.jsp"/>
</head>
<body class="d-flex flex-column min-vh-100">
<jsp:include page="/WEB-INF/views/fragments/navbar.jsp"/>
<main role="main" class="container">
    <div class="text-center">
        <img src="<c:url value="/resources/images/logo.png"/>" alt="Logo"/>
        <div class="jumbotron jumbotron-fluid">
            <div class="container">
                <h1 class="display-4 text-break"><fmt:message key="title"/></h1>
                <hr class="my-4">
                <p class="h4 text-justify"><fmt:message key="site.description"/></p>
            </div>
        </div>
    </div>
</main>
<jsp:include page="/WEB-INF/views/fragments/footer.jsp"/>
</body>
</html>