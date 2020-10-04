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
<main role="main">
    <div>
        <div>
            <div class="h2 text-center">
                <fmt:message key="register"/>
            </div>
            <div>
                <form accept-charset="UTF-8" role="form" method="post">

                    <div class="form-group">
                        <label for="email"><fmt:message key="email"/></label>
                        <div class="input-group">
                            <input type="email" id="email"
                                   name="email"
                                   value="<c:out value="${requestScope.userDTO.email}"/>"
                                   class="form-control form-control-lg
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

                    <div>
                        <div>
                            <label for="firstName"><fmt:message key="firstname"/></label>
                            <div class="input-group">
                                <input type="text" id="firstName"
                                       name="firstName"
                                       value="<c:out value="${requestScope.userDTO.firstName}"/>"
                                       class="form-control form-control-lg
                                       <c:if test="${errors.errorFirstName}">
                                            is-invalid
                                       </c:if>"
                                       placeholder="<fmt:message key="firstname.placeholder"/>"
                                       required>
                                <c:if test="${errors.errorFirstName}">
                                    <div class="invalid-feedback">
                                        <fmt:message key="error.firstname"/>
                                    </div>
                                </c:if>
                            </div>
                        </div>

                        <div>
                            <label for="lastName"><fmt:message key="lastname"/></label>
                            <div class="input-group">
                                <input type="text" id="lastName"
                                       name="lastName"
                                       value="<c:out value="${requestScope.userDTO.lastName}"/>"
                                       class="form-control
                                       <c:if test="${errors.errorLastName}">
                                            is-invalid
                                       </c:if>"
                                       placeholder="<fmt:message key="lastname.placeholder"/>"
                                       required>
                                <c:if test="${errors.errorLastName}">
                                    <div class="invalid-feedback">
                                        <fmt:message key="error.lastname"/>
                                    </div>
                                </c:if>
                            </div>
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
                        <button type="submit"><fmt:message key="register"/></button>
                    </div>

                </form>
            </div>
            <c:if test="${errors.errorRegistration}">
                <div>
                    <div>
                        <fmt:message key="error.registration"/>
                        <button type="button" class="close">
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
