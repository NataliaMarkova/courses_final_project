<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="elc" uri="elective_courses"%>
<fmt:setLocale value="${sessionScope.locale}" scope="session" />
<fmt:bundle basename="resource.i18n" prefix="courses.">
<table width="100%">
  <thead>
  <tr>
    <td><b><fmt:message key="department.label"/></b></td>
    <td><b><fmt:message key="name.label"/></b></td>
    <td><b><fmt:message key="start_date.label"/></b></td>
    <td><b><fmt:message key="end_date.label"/></b></td>
    <td><b><fmt:message key="professor.label"/></b></td>
  </tr>
  </thead>
  <tbody>
  <c:forEach items="${courses}" var="course">
    <tr>
      <td valign="center">${course.department.name}</td>
      <elc:admin>
        <td valign="center"><a href="course_information?course_id=${course.id}">${course.name}</a></td>
      </elc:admin>
      <elc:professor>
        <td valign="center"><a href="course_information?course_id=${course.id}">${course.name}</a></td>
      </elc:professor>
      <elc:student>
        <td valign="center">${course.name}</td>
      </elc:student>
      <td valign="center">${course.startDate}</td>
      <td valign="center">${course.endDate}</td>
      <td valign="center">${course.professor.fullName}</td>
    </tr>
  </c:forEach>
  </tbody>
</table>
</fmt:bundle>