<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<fmt:setLocale value="${sessionScope.locale}" scope="session" />
<fmt:setLocale value="${sessionScope.locale}" scope="session" />
<fmt:bundle basename="resource.i18n" prefix="courses.">
<table>
  <tr>
    <form action="courses?course_type=current" method="post">
      <input class="myButton" type="submit" value="<fmt:message key="current.title"/>"/>
    </form>
  </tr>
  <tr>
    <form action="courses?course_type=future" method="post">
      <input class="myButton" type="submit" value="<fmt:message key="future.title"/>"/>
    </form>
  </tr>
  <tr>
    <form action="courses?course_type=archive" method="post">
      <input class="myButton" type="submit" value="<fmt:message key="archive.title"/>"/>
    </form>
  </tr>
  <tr>
    <form action="courses" method="post">
      <input class="myButton" type="submit" value="<fmt:message key="all.title"/>"/>
    </form>
  </tr>
</table>
</fmt:bundle>