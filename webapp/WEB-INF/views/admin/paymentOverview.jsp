<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
    <div class="row h-100 justify-content-md-center align-items-center">
        <div class="card border-dark mb-5 w-50">
            <ul class="list-group list-group-flush">
                <li class="list-group-item">
                    <dl class="row">
                        <dt class="col-sm-3"><fmt:message key="payments.user"/>:</dt>
                        <dd class="col-sm-9">
                            <a href="<c:url value="/app/admin/user?userId=${paymentDTO.user.id}"/>">
                                <i class="fa fa-user fa-lg" aria-hidden="true">&nbsp;</i>
                                <c:out value="${requestScope.paymentDTO.user.firstName}"/>
                                <c:out value="${requestScope.paymentDTO.user.lastName}"/>
                            </a>
                        </dd>
                    </dl>
                </li>
                <li class="list-group-item">
                    <dl class="row">
                        <dt class="col-sm-3"><fmt:message key="payments.date"/>:</dt>
                        <dd class="col-sm-9">
                            <fmt:formatDate value="${Timestamp.valueOf(requestScope.paymentDTO.paymentDateTime)}"
                                            type="date"
                                            pattern="yyyy-MM-dd/HH:mm"/>
                        </dd>
                    </dl>
                </li>
                <li class="list-group-item">
                    <dl class="row">
                        <dt class="col-sm-3"><fmt:message key="payments.price"/>:</dt>
                        <dd class="col-sm-9">
                            <c:out value="${requestScope.paymentDTO.totalPrice}"/> $
                        </dd>
                    </dl>
                </li>
            </ul>
        </div>
    </div>

    <div class="d-flex justify-content-center align-items-center">
        <h1 class="display-3">
            <strong>
                <fmt:message key="payment.subscriptions"/>
            </strong>
        </h1>
    </div>
    <div class="progress mb-5">
        <div class="progress-bar" role="progressbar" aria-valuenow="25" aria-valuemin="0" aria-valuemax="100"></div>
    </div>
    <div class=" table-responsive">
        <table class="table table-striped table-hover text-center align-middle">
            <thead>
            <tr class="bg-primary">
                <th scope="col" class="align-middle">№</th>
                <th scope="col" class="align-middle"><fmt:message key="bin.name"/></th>
                <th scope="col" class="align-middle"><fmt:message key="periodical.status"/></th>
                <th scope="col" class="align-middle"><fmt:message key="bin.type"/></th>
                <th scope="col" class="align-middle"><fmt:message key="bin.frequency"/></th>
                <th scope="col" class="align-middle"><fmt:message key="subscription.start.date"/></th>
                <th scope="col" class="align-middle"><fmt:message key="subscription.end.date"/></th>
                <th scope="col" class="align-middle"><fmt:message key="subscription.status"/></th>
                <th scope="col" class="align-middle"></th>
            </tr>
            </thead>
            <tbody>
            <c:forEach var="subscription" items="${requestScope.subscriptions}" varStatus="counter">
                <tr>
                    <th scope="row" class="align-middle">${counter.count}</th>
                    <td class="align-middle overflow-text">
                        <c:out value="${subscription.periodical.name}"/>
                    </td>
                    <td class="align-middle">
                        <c:if test="${subscription.periodical.status eq true}">
                                <span class="badge badge-success">
                                    <fmt:message key="periodical.status.available"/>
                                </span>
                        </c:if>
                        <c:if test="${subscription.periodical.status eq false}">
                                <span class="badge badge-warning">
                                    <fmt:message key="periodical.status.nonavailable"/>
                                </span>
                        </c:if>
                    </td>
                    <td class="align-middle">
                        <c:out value="${subscription.periodical.periodicalCategory.name}"/>
                    </td>
                    <td class="align-middle">
                        <c:out value="${subscription.periodical.frequency.name}"/>
                    </td>
                    <td class="align-middle">
                        <c:out value="${subscription.startDate}"/>
                    </td>
                    <td class="align-middle">
                        <c:out value="${subscription.endDate}"/>
                    </td>
                    <td class="align-middle">
                        <c:choose>
                            <c:when test="${subscription.endDate.isBefore(LocalDate.now())}">
                                <span class="badge badge-danger">
                                    <fmt:message key="subscription.status.expired"/>
                                </span>
                            </c:when>
                            <c:otherwise>
                                <span class="badge badge-success">
                                    <fmt:message key="subscription.status.active"/>
                                </span>
                            </c:otherwise>
                        </c:choose>
                    </td>
                    <td class="align-middle">
                        <a href="<c:url value="/app/periodical?periodicalId=${subscription.periodical.id}"/>">
                            <i class="fa fa-eye fa-lg" aria-hidden="true">&nbsp;</i>
                            <fmt:message key="subscription.see"/>
                        </a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <jsp:include page="/WEB-INF/views/fragments/pagination.jsp"/>
</main>

<jsp:include page="/WEB-INF/views/fragments/footer.jsp"/>
</body>
</html>
