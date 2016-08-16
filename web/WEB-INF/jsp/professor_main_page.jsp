<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="elective_courses" prefix="elc"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session" />
<fmt:bundle basename="resource.i18n" prefix="professor.">
<elc:h2/>
<ul id="main-menu">
    <li><a href="${pageContext.request.contextPath}"><fmt:message key="main.menu"/></a>
    <li><a href="courses?course_type=current"><fmt:message key="courses.menu"/></a>
    <li><a href="logout"><fmt:message key="logout.menu"/></a>
</ul>
</fmt:bundle>
