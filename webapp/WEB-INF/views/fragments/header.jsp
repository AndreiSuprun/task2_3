<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         import="com.suprun.periodicals.view.constants.ViewsPath" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.language"/>

<c:set var="homeView" scope="page" value="${ViewsPath.HOME_VIEW}"/>
<c:set var="profileView" scope="page" value="${ViewsPath.PROFILE_VIEW}"/>
<c:set var="signInView" scope="page" value="${ViewsPath.SIGN_IN_VIEW}"/>
<c:set var="signUpView" scope="page" value="${ViewsPath.REGISTER_VIEW}"/>
<c:set var="catalogView" scope="page" value="${ViewsPath.CATALOG_VIEW}"/>
<c:set var="cartView" scope="page" value="${ViewsPath.BIN_VIEW}"/>
<c:set var="subscriptionsView" scope="page" value="${ViewsPath.SUBSCRIPTIONS_VIEW}"/>
<c:set var="adminCatalogView" scope="page" value="${ViewsPath.ADMIN_CATALOG_VIEW}"/>
<c:set var="adminCreatePeriodicalView" scope="page" value="${ViewsPath.CREATE_PERIODICAL_VIEW}"/>
<c:set var="adminPaymentsView" scope="page" value="${ViewsPath.PAYMENTS_VIEW}"/>
<c:set var="currView" scope="page" value="${pageContext.request.requestURL}"/>

<nav>
    <a href="<c:url value="/app/"/>">Periodicals</a>

    <div>
        <ul>
            <li
                    <c:if test="${catalogView.equals(currView)}">
                    </c:if>
            >
                <a href="<c:url value="/app/catalog"/>">
                    <fmt:message key="catalog"/>
                </a>
            </li>
            <li>
                <a  href="#">
                    ${sessionScope.locale.getLanguage().toUpperCase()}
                </a>
                <div>
                    <c:forEach items="${applicationScope.supportedLocales}" var="lang">
                        <a href="?lang=${lang}">${lang.toUpperCase()}</a>
                    </c:forEach>
                </div>
            </li>
        </ul>
        <ul>
            <c:if test="${sessionScope.user eq null}">
                <li
                        <c:if test="${signUpView.equals(currView)}">
                        </c:if>
                >
                    <a href="<c:url value="/app/signup"/>">
                        <fmt:message key="signup"/>
                    </a>
                </li>
                <li
                        <c:if test="${signInView.equals(currView)}">
                        </c:if>
                >
                    <a href="<c:url value="/app/signin"/>">
                        </i> <fmt:message key="signin"/>
                    </a>
                </li>
            </c:if>
            <c:if test="${not empty sessionScope.user}">
                <c:if test="${!sessionScope.user.isAdmin()}">
                    <li
                            <c:if test="${binView.equals(currView)}">
                            </c:if>
                    >
                        <a href="<c:url value="/app/bin"/>">
                            <fmt:message key="cart"/>&nbsp;
                            <span>
                                <c:choose>
                                    <c:when test="${not empty sessionScope.subscriptionBin}">
                                        ${sessionScope.subscriptionBin.items.size()}
                                    </c:when>
                                    <c:otherwise>
                                        0
                                    </c:otherwise>
                                </c:choose>
                            </span>
                        </a>
                    </li>
                    <li
                            <c:if test="${subscriptionsView.equals(currView)}"/>
                    >
                        <a href="<c:url value="/app/subscriptions"/>">
                            <fmt:message key="user.subscriptions"/>
                        </a>
                    </li>
                </c:if>
                <c:if test="${sessionScope.user.isAdmin()}">
                    <li>
                        <a href="#">
                            <fmt:message key="admin.management"/>
                        </a>
                        <div>
                            <a>
                            <c:if test="${adminCatalogView.equals(currView)}">
                                active
                            </c:if>"
                               href="<c:url value="/app/admin/catalog"/>">
                                <fmt:message key="admin.management.catalog"/>
                            </a>
                            <a class="dropdown-item
                            <c:if test="${adminCreatePeriodicalView.equals(currView)}">
                                active
                            </c:if>"
                               href="<c:url value="/app/admin/catalog/periodical-create"/>">
                                <fmt:message key="admin.management.create.periodical"/>
                            </a>
                        </div>
                    </li>
                    <li
                            <c:if test="${adminPaymentsView.equals(currView)}">
                                class="active"
                            </c:if>
                    >
                        <a href="<c:url value="/app/admin/payments"/>">
                            <fmt:message key="admin.payments"/>
                        </a>
                    </li>
                </c:if>
                <li>
                    <a href="#">
                        <c:out value="${sessionScope.user.firstName}"/>
                    </a>
                    <div>
                        <a>
                            <c:if test="${profileView.equals(currView)}">
                                    active
                            </c:if>"
                           href="<c:url value="/app/profile"/>"><fmt:message key="profile"/>
                        </a>
                        <a href="<c:url value="/app/signout"/>"><fmt:message key="signout"/></a>
                    </div>
                </li>
            </c:if>
        </ul>
    </div>
</nav>