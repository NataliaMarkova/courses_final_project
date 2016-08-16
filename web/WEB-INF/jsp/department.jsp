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
          <h3><fmt:message key="title"/></h3>
          <form action="update_department" method="post">
              <elc:CSRFToken/>
              <input type="hidden" name="department_id" value="${department.id}"/>
              <fieldset>
                  <span class="error">${error}</span>
                  <table>
                      <tr>
                          <td><fmt:message key="name.label"/>:</td><td><input class = "dataField" type="text" name="department_name" value="${department.name}"/><br/></td>
                      </tr>
                      <tr>
                          <td></td><td><input type="submit" class="myButton" value="Ok"/></td>
                      </tr>
                  </table>
              </fieldset>
          </form>
      </div>
  </body>
</html>
</fmt:bundle>
