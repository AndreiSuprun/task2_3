<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.language"/>

<html>
<head>
    <title>Periodicals</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/fragments/header.jsp"/>
<main>
    <div class="text-center">
        <img src="<c:url value="/resources/images/"/>" alt="Logo"/>
        <div>
            <div>
                <h1><fmt:message key="title"/></h1>
                <hr>
                <p><fmt:message key="site.description"/></p>
            </div>
        </div>
    </div>
</main>
</body>
</html>
