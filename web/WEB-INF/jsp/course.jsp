<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="elc" uri="elective_courses" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session" />
<fmt:bundle basename="resource.i18n" prefix="courses.">
<html>
  <jsp:include page="head_part.jsp"/>
  <body>
  <jsp:include page="languages.jsp"/>
    <div class="main">
      <jsp:include page="admin_main_page.jsp"/>
      <span>${message}</span>
      <form action = "add_course" method="post">
        <elc:CSRFToken/>
        <fieldset>
          <span class="error">${error}</span>
          <table>
            <tr>
              <td><fmt:message key="name.label"/>:</td><td><input class = "dataField" id="course_name" type="text" name="course_name" value="${course.name}"/><br/></td>
            </tr>
            <tr>
              <td><fmt:message key="start_date.label"/>:</td><td><input class = "dataField" id="start_date" type="date" name="start_date" value="${course.startDate}"/><br/></td>
            </tr>
            <tr>
              <td><fmt:message key="end_date.label"/>:</td><td><input class = "dataField" id="end_date" type="date" name="end_date"  value="${course.endDate}"/><br/></td>
            </tr>
            <tr>
              <td><fmt:message key="professor.label"/>:</td><td>
              <select name="professor_id" class="selectField">
                <c:forEach items="${professors}" var="professor">
                  <option value="${professor.id}">${professor.fullName}</option>
                </c:forEach>
              </select>
              <br/></td>
            </tr>
            <tr>
              <td><fmt:message key="department.label"/>:</td><td>
              <select name="department_id" class="selectField">
                <c:forEach items="${departments}" var="department">
                  <option value="${department.id}">${department.name}</option>
                </c:forEach>
              </select>
              <br/></td>
            </tr>
            <tr>
              <td></td><td><input type="submit" class="myButton" value="<fmt:message key="add_new.button"/>"/></td>
            </tr>
          </table>
        </fieldset>
      </form>
    </div>
  </body>
</html>
</fmt:bundle>