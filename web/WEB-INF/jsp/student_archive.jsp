<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="exception.jsp" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionScope.locale}" scope="session" />
<fmt:bundle basename="resource.i18n">
<html>
    <jsp:include page="head_part.jsp"/>
  <body>
  <jsp:include page="languages.jsp"/>
      <div class="main">
          <jsp:include page="student_main_page.jsp"/>
          <h3><fmt:message key="student.archive.menu"/></h3>
          <span>${message}</span>
          <table width="100%">
              <thead>
                  <tr>
                      <td><b><fmt:message key="courses.department.label"/></b></td>
                      <td><b><fmt:message key="courses.name.label"/></b></td>
                      <td><b><fmt:message key="courses.start_date.label"/></b></td>
                      <td><b><fmt:message key="courses.end_date.label"/></b></td>
                      <td><b><fmt:message key="courses.professor.label"/></b></td>
                      <td><b><fmt:message key="students.mark.label"/></b></td>
                  </tr>
              </thead>
              <tbody>
                  <c:forEach items="${items}" var="item">
                    <tr>
                        <td valign="center">${item.course.department.name}</td>
                        <td valign="center">${item.course.name}</td>
                        <td valign="center">${item.course.startDate}</td>
                        <td valign="center">${item.course.endDate}</td>
                        <td valign="center">${item.course.professor.fullName}</td>
                        <td valign="center">${item.mark}</td>
                    </tr>
                  </c:forEach>
              </tbody>
          </table>
      </div>
  </body>
</html>
</fmt:bundle>