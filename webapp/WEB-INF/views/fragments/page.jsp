<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"
         import="com.suprun.periodicals.view.constants.ViewsPath" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${numberOfPages gt 1}">
    <nav>
        <ul >
            <li  class ="<c:if test="${page eq 1}">disabled</c:if>">
                <a href="<c:url value="?page=${page - 1}"/>">
                    <span aria-hidden="true">&laquo;</span>
                </a>
            </li>
            <c:forEach begin="1" end="${numberOfPages}" varStatus="counter">
                <li class="<c:if test="${page eq counter.count}">disabled</c:if>">
                    <a href="<c:url value="?page=${counter.count}"/>">
                            ${counter.count}
                    </a>
                </li>
            </c:forEach>
            <li class="<c:if test="${page eq numberOfPages}">disabled</c:if>">
                <a href="<c:url value="?page=${page + 1}"/>">
                    <span aria-hidden="true">&raquo;</span>
                </a>
            </li>
        </ul>
    </nav>
</c:if>