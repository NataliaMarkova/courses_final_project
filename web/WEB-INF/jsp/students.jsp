<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="exception.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="elc" uri="elective_courses" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session" />
<fmt:bundle basename="resource.i18n" prefix="courses.">
<html>
    <jsp:include page="head_part.jsp"/>
  <body>
  <jsp:include page="languages.jsp"/>
      <div class="main">
          <jsp:include page="${main_page}"/>
          <jsp:include page="courses_menu.jsp"/>
          <h3><fmt:message key="name.label"/>: ${course.name}</h3>
          <div align="center">${course.startDate} - ${course.endDate}</br>
              <fmt:message key="professor.label"/>: ${course.professor.fullName}</div>
          <span>${message}</span>
          <jsp:include page="students_table.jsp"/>
      </div>
  </body>
</html>
</fmt:bundle>