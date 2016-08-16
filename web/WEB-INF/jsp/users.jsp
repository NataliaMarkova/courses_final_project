<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="exception.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="elc" uri="elective_courses" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session" />
<fmt:bundle basename="resource.i18n" prefix="auth.">
<html>
    <jsp:include page="head_part.jsp"/>
  <body>
  <jsp:include page="languages.jsp"/>
      <div class="main">
          <jsp:include page="admin_main_page.jsp"/>
          <h3><fmt:message key="users.label"/></h3>
          <span>${message}</span>
          <form action="registration" method="post">
              <elc:CSRFToken/>
              <input class="myButton" type="submit" value="<fmt:message key="new_user.button"/>"/>
          </form>
          <table width="100%">
              <thead>
                  <tr>
                      <td><b><fmt:message key="login.label"/></b></td>
                      <td><b><fmt:message key="familyName.label"/></b></td>
                      <td><b><fmt:message key="name.label"/></b></td>
                      <td><b><fmt:message key="patronymic.label"/></b></td>
                      <td><b><fmt:message key="user_type.label"/></b></td>
                  </tr>
              </thead>
              <tbody>
                  <c:forEach items="${users}" var="user">
                    <tr>
                        <td valign="center">${user.email}</td>
                        <td valign="center">${user.familyName}</td>
                        <td valign="center">${user.name}</td>
                        <td valign="center">${user.patronymic}</td>
                        <td valign="center">${user.userType}</td>
                    </tr>
                  </c:forEach>
              </tbody>
          </table>
      </div>
  </body>
</html>
</fmt:bundle>