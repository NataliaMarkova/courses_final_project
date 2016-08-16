<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:bundle basename="resource.i18n" >
  <h3><fmt:message key="user.info"/></h3>
  <table>
    <tr>
      <td><b><fmt:message key="auth.login.label"/>:</b></td><td>${user.email}<br/></td>
    </tr>
    <tr>
      <td><b><fmt:message key="auth.familyName.label"/>:</b></td><td>${user.familyName}<br/></td>
    </tr>
    <tr>
      <td><b><fmt:message key="auth.name.label"/>:</b></td><td>${user.name}<br/></td>
    </tr>
    <tr>
      <td><b><fmt:message key="auth.patronymic.label"/>:</b></td><td>${user.patronymic}<br/></td>
    </tr>
    <tr>
        <form action="change_password" method="get">
            <td><input type="submit" class="myButton" value="<fmt:message key="auth.change_password.button"/>"/></td>
        </form>
      <form action="edit_user" method="get">
        <td><input type="submit" class="myButton" value="<fmt:message key="auth.edit.button"/>"/></td>
      </form>
    </tr>
  </table>
</fmt:bundle>