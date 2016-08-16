<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="exception.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="elc" uri="elective_courses" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session" />
<fmt:bundle basename="resource.i18n" prefix="students.">
<table width="100%">
  <thead>
  <tr>
    <td><b><fmt:message key="student.label"/></b></td>
    <td><b><fmt:message key="mark.label"/></b></td>
  </tr>
  </thead>
  <tbody>
  <c:forEach items="${items}" var="item">
    <tr>
      <td valign="center">${item.student.fullName}</td>
      <td valign="center">${item.mark}</td>
      <c:if test="${put_mark}">
        <td>
          <form action="put_mark" method="post">
            <elc:CSRFToken/>
            <input type="hidden" name="student_id" value="${item.student.id}"/>
            <input type="hidden" name="course_id" value="${course.id}"/>
            <input type="submit" class="myButton" value="<fmt:message key="edit_mark.button"/>"/>
          </form>
        </td>
      </c:if>
    </tr>
  </c:forEach>
  </tbody>
</table>
</fmt:bundle>