<%@ page contentType="text/html;charset=UTF-8" language="java" errorPage="exception.jsp" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="elc" uri="elective_courses" %>
<fmt:setLocale value="${sessionScope.locale}" scope="session" />
<fmt:bundle basename="resource.i18n" prefix="courses.">
<html>
    <jsp:include page="head_part.jsp"/>
  <body>
  <jsp:include page="languages.jsp"/>
      <div class="main">
          <jsp:include page="${main_page}"/>
          <jsp:include page="courses_menu.jsp"/>
          <elc:courseTitle type="${course_type}"/>
          <span>${message}</span>
          <elc:admin>
              <form action="new_course" method="post">
                  <elc:CSRFToken/>
                  <input class="myButton" type="submit" value="<fmt:message key="new.button"/>"/>
              </form>
          </elc:admin>
          <jsp:include page="courses_table.jsp"/>
      </div>
  </body>
</html>
</fmt:bundle>
