<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="exception.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="elc" uri="elective_courses"%>
<fmt:setLocale value="${sessionScope.locale}" scope="session" />
<fmt:bundle basename="resource.i18n" prefix="courses.">
<html>
    <jsp:include page="head_part.jsp"/>
  <body>
    <jsp:include page="languages.jsp"/>
      <div class="main">
          <jsp:include page="student_main_page.jsp"/>
          <elc:courseTitle type="${course_type}"/>
          <span>${message}</span>
          <span class="error">${error}</span>
          <table width="100%">
              <thead>
                  <tr>
                      <td><b><fmt:message key="department.label"/></b></td>
                      <td><b><fmt:message key="name.label"/></b></td>
                      <td><b><fmt:message key="start_date.label"/></b></td>
                      <td><b><fmt:message key="end_date.label"/></b></td>
                      <td><b><fmt:message key="professor.label"/></b></td>
                      <td></td>
                  </tr>
              </thead>
              <tbody>
                  <c:forEach items="${courses}" var="course">
                    <tr>
                        <td valign="center">${course.department.name}</td>
                        <td valign="center">${course.name}</td>
                        <td valign="center">${course.startDate}</td>
                        <td valign="center">${course.endDate}</td>
                        <td valign="center">${course.professor.fullName}</td>
                        <td>
                            <form action="enroll_on_course" method="post">
                                <elc:CSRFToken/>
                                <input type="hidden" name="course_id" value="${course.id}"/>
                                <input type="submit" class="myButton" value="<fmt:message key="enroll.button"/>"/>
                            </form>
                        </td>
                    </tr>
                  </c:forEach>
              </tbody>
          </table>
      </div>
  </body>
</html>
</fmt:bundle>
