<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="elc" uri="elective_courses" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:bundle basename="resource.i18n" >
  <h3><fmt:message key="user.info"/></h3>
  <form action = "do_change_password" method="post">
    <elc:CSRFToken/>
    <fieldset>
      <span class="error">${error}</span>
      <table>
        <tr>
          <td><fmt:message key="auth.old_password.label"/>:</td><td><input id="login" class = "dataField" type="password" name="old_password"/><br/></td>
        </tr>
        <tr>
          <td><fmt:message key="auth.new_password.label"/>:</td><td><input class = "dataField" id="password" type="password" name="new_password"/><br/></td>
        </tr>
        <tr>
          <td><fmt:message key="auth.password_confirmation.label"/>:</td><td><input class = "dataField" id="password_confirmation" type="password" name="password_confirmation"/><br/></td>
        </tr>
        <tr>
          <td></td><td><input type="submit" class="myButton" value="<fmt:message key="auth.change_password.button"/>"></td>
        </tr>
      </table>
    </fieldset>
  </form>
</fmt:bundle>