<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="elc" uri="elective_courses" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session" />
<fmt:bundle basename="resource.i18n" prefix="student.">
<elc:h2/>
<ul id="main-menu">
    <li><a href="${pageContext.request.contextPath}"><fmt:message key="main.menu"/></a>
    <li><a href="courses?course_type=current"><fmt:message key="courses.menu"/></a>
    <li><a href="archive"><fmt:message key="archive.menu"/></a>
    <li><a href="available_courses"><fmt:message key="available_courses.menu"/></a>
    <li><a href="logout"><fmt:message key="logout.menu"/></a>
</ul>
</fmt:bundle>
