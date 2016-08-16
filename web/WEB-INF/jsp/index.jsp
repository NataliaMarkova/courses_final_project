<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" errorPage="exception.jsp" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session" />
<fmt:bundle basename="resource.i18n" prefix="auth.">
<html>
  <jsp:include page="head_part.jsp"/>
  <body>
  <jsp:include page="languages.jsp"/>
  <div class="main">
  <h2><fmt:message key="h2"/></h2>
    <span>${message}</span>
    <form action="registration"><button class="myButton"><fmt:message key="register.button"/></button></form>
    <form action = "authenticate" method="post">
    <fieldset>
      <span class="error">${error}</span>
      <table>
        <tr>
          <td><fmt:message key="login.label"/>:</td><td><input id="login" class = "dataField" type="text" name="login" value="${login}"/></td>
        </tr>
        <tr>
          <td><fmt:message key="password.label"/>:</td><td><input id="password" class = "dataField" type="password" name="password" value=""/><br/></td>
        </tr>
        <tr>
          <td></td><td align="right"><input class="myButton" type="submit" value="<fmt:message key="login.button"/>"/></td>
        </tr>
      </table>
    </fieldset>
  </form>
  </div>
  </body>
</html>
</fmt:bundle>
