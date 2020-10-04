<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<fmt:setLocale value="${sessionScope.locale}"/>
<fmt:setBundle basename="i18n.lang"/>

<html>
<head>
    <title>Periodicals</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/snippets/navbar.jsp"/>
<main>
    <div>
        <div>
            <div class="h2 text-center">
                <fmt:message key="signin"/>
            </div>
            <div>
                <form accept-charset="UTF-8" role="form" method="post">
                    <div class="form-group">
                        <label for="email"><fmt:message key="email"/></label>
                        <div class="input-group">
                            <input type="email" id="email"
                                   name="email"
                                   value="<c:out value="${requestScope.userDTO.email}"/>"
                                   class="form-control
                                   <c:if test="${errors.errorEmail}">
                                            is-invalid
                                   </c:if>"
                                   placeholder="<fmt:message key="email.placeholder"/>" required>
                            <c:if test="${errors.errorEmail}">
                                <div class="invalid-feedback">
                                    <fmt:message key="error.email"/>
                                </div>
                            </c:if>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="password"><fmt:message key="password"/></label>
                        <div class="input-group">
                            <input type="password" id="password"
                                   name="password"
                                   class="form-control
                                   <c:if test="${errors.errorPassword}">
                                            is-invalid
                                   </c:if>"
                                   minlength="5"
                                   placeholder="<fmt:message key="password.placeholder"/>" required>
                            <c:if test="${errors.errorPassword}">
                                <div class="invalid-feedback">
                                    <fmt:message key="error.password"/>
                                </div>
                            </c:if>
                        </div>

                    </div>
                    <div class="form-group text-center">
                        <button type="submit"><fmt:message key="signin"/></button>
                    </div>
                </form>
            </div>

            <c:if test="${errors.errorAuthentication}">
                <div class="card-footer text-muted">
                    <div>
                        <fmt:message key="error.authentication"/>
                        <button type="button">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
</main>
</body>
</html>