<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="elc" uri="elective_courses" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session" />
<fmt:bundle basename="resource.i18n" prefix="admin.">
<elc:h2/>
<ul id="main-menu">
    <li><a href="${pageContext.request.contextPath}"><fmt:message key="main.menu"/></a>
    <li><a href="users"><fmt:message key="users.menu"/></a>
    <li><a href="courses?course_type=current"><fmt:message key="courses.menu"/></a>
    <li><a href="departments"><fmt:message key="departments.menu"/></a>
    <li><a href="logout"><fmt:message key="logout.menu"/></a>
</ul>
</fmt:bundle>