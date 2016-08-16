<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="exception.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="elc" uri="elective_courses" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session" />
<fmt:bundle basename="resource.i18n" prefix="department.">
<html>
    <jsp:include page="head_part.jsp"/>
  <body>
  <jsp:include page="languages.jsp"/>
      <div class="main">
          <jsp:include page="admin_main_page.jsp"/>
          <h3><fmt:message key="plural.title"/></h3>
          <form action="new_department" method="post">
              <elc:CSRFToken/>
              <input type="submit" class="myButton" value="<fmt:message key="new.button"/>">
          </form>
          <span>${message}</span>
          <span class="error">${error}</span>
          <table width="100%">
              <thead>
                  <tr>
                      <td><b><fmt:message key="name.label"/></b></td>
                      <td></td>
                  </tr>
              </thead>
              <tbody>
                  <c:forEach items="${departments}" var="department">
                    <tr>
                        <td valign="center">${department.name}</td>
                        <td><form action="edit_department" method="post">
                            <elc:CSRFToken/>
                            <input type="hidden" name="department_id" value="${department.id}">
                            <input type="submit" class="myButton" value="<fmt:message key="edit.button"/>">
                        </form></td>
                    </tr>
                  </c:forEach>
              </tbody>
          </table>
      </div>
  </body>
</html>
</fmt:bundle>