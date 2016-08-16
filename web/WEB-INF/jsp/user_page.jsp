<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" errorPage="exception.jsp" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" />
<html>
    <jsp:include page="head_part.jsp"/>
    <body>
        <jsp:include page="languages.jsp"/>
        <div class="main">
            <jsp:include page="${main_page}"/>
            <span>${message}</span>
            <jsp:include page="${data_page}"/>
        </div>
    </body>
</html>

