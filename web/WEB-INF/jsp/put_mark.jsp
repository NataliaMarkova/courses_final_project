<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="exception.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="elc" uri="elective_courses" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session" />
<fmt:bundle basename="resource.i18n">
<html>
    <jsp:include page="head_part.jsp"/>
  <body>
  <jsp:include page="languages.jsp"/>
      <div class="main">
          <jsp:include page="professor_main_page.jsp"/>
          <span class="error">${error}</span>
          <h3><fmt:message key="courses.name.label"/>: ${course.name}</h3>
          <div align="center">
              ${course.startDate} - ${course.endDate}</br>
                  <fmt:message key="courses.professor.label"/>: ${course.professor.fullName}
          </div>
          <form action="do_put_mark" method="post">
              <elc:CSRFToken/>
              <input type="hidden" name="student_id" value="${student.id}"/>
              <input type="hidden" name="course_id" value="${course.id}"/>
              <table>
                <tr>
                    <td>${student.fullName}</td>
                    <td>
                        <input class="smallDataField" type="number" name="mark" value="${mark}">
                    </td>
                    <td>
                        <input class="myButton" type="submit" value="Ok">
                    <td>
                </tr>
              </table>
          </form>
      </div>
  </body>
</html>
</fmt:bundle>