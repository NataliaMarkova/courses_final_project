<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="elc" uri="elective_courses" %>
<fmt:setLocale value="${sessionScope.locale}" />
<fmt:bundle basename="resource.i18n" >
  <h3><fmt:message key="user.info"/></h3>
  <form action = "do_edit_user" method="post">
    <elc:CSRFToken/>
    <fieldset>
      <span class="error">${error}</span>
      <table>
        <tr>
          <td><fmt:message key="auth.familyName.label"/>:</td><td><input class = "dataField" id="family_name" type="text" name="family_name" value="${familyName}"/><br/></td>
        </tr>
        <tr>
          <td><fmt:message key="auth.name.label"/>:</td><td><input class = "dataField" id="name" type="text" name="name" value="${name}"/><br/></td>
        </tr>
        <tr>
          <td><fmt:message key="auth.patronymic.label"/>:</td><td><input class = "dataField" id="patronymic" type="text" name="patronymic"  value="${patronymic}"/><br/></td>
        </tr>
          <tr>
              <td><fmt:message key="auth.confirm_password.label"/>:</td><td><input class = "dataField" id="password" type="password" name="password"/><br/></td>
          </tr>
        <tr>
          <td></td><td><input type="submit" class="myButton" value="<fmt:message key="auth.save.button"/>"/></td>
        </tr>
      </table>
    </fieldset>
  </form>
</fmt:bundle>