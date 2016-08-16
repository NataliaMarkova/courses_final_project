<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" %>
<%--<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" language="java" errorPage="exception.jsp" %>--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="elc" uri="elective_courses" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session" />
<fmt:bundle basename="resource.i18n" prefix="auth.">
<html>
  <jsp:include page="head_part.jsp"/>
  <body>
  <jsp:include page="languages.jsp"/>
	<div class="main">
	  <elc:admin>
		<jsp:include page="admin_main_page.jsp"/>
	  </elc:admin>
      <elc:anonymous>
        <h2><fmt:message key="reg.h2"/></h2>
	    <jsp:include page="go_back.jsp"/>
      </elc:anonymous>
	  <form action = "register" method="post">
		<fieldset>
		  <span class="error">${error}</span>
		  <table>
			<tr>
			  <td><fmt:message key="login.label"/>:</td><td><input id="login" class = "dataField" type="text" name="login" value="${login}"/><br/></td>
			</tr>
			<tr>
			  <td><fmt:message key="password.label"/>:</td><td><input class = "dataField" id="password" type="password" name="password"/><br/></td>
			</tr>
			<tr>
			  <td><fmt:message key="password_confirmation.label"/>:</td><td><input class = "dataField" id="password_confirmation" type="password" name="password_confirmation"/><br/></td>
			</tr>
			<tr>
			  <td><fmt:message key="familyName.label"/>:</td><td><input class = "dataField" id="family_name" type="text" name="family_name" value="${familyName}"/><br/></td>
			</tr>
			<tr>
			  <td><fmt:message key="name.label"/>:</td><td><input class = "dataField" id="name" type="text" name="name" value="${name}"/><br/></td>
			</tr>
			<tr>
			  <td><fmt:message key="patronymic.label"/>:</td><td><input class = "dataField" id="patronymic" type="text" name="patronymic"  value="${patronymic}"/><br/></td>
			</tr>
            <elc:admin>
                <tr>
                  <td><fmt:message key="user_type.label"/>:</td>
                    <td>
                      <input type="radio" name="user_type" value="admin" checked/> <fmt:message key="admin.label"/>
                      <input type="radio" name="user_type" value="professor"/> <fmt:message key="professor.label"/>
                      <input type="radio" name="user_type" value="student"/> <fmt:message key="student.label"/>
                      <br/>
                    </td>
                </tr>
            </elc:admin>
			<tr>
			  <td></td><td><input type="submit" class="myButton" value="<fmt:message key="register_new.button"/>"/></td>
			</tr>
		  </table>
		</fieldset>
	  </form>
	  </div>
  </body>
</html>
</fmt:bundle>