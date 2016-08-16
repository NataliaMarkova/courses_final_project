<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="elc" uri="elective_courses" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session" />
<fmt:bundle basename="resource.i18n" >
<form action = "${pageContext.request.contextPath}" method="get">
  <input class="myButton" type="submit" value="<fmt:message key="back.button"/>"/>
</form>
</fmt:bundle>