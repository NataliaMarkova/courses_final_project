<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="lang" align="right">
  <table><tr>
    <td><form action="localization" method="post">
      <input type="hidden" name="command_name" value="${command_name}">
      <input type="hidden" name="lang" value="eng">
      <input type="submit" class="myButton" value="ENG">
    </form></td>
    <td><form action="localization" method="post">
      <input type="hidden" name="command_name" value="${command_name}">
      <input type="hidden" name="lang" value="rus">
      <input type="submit" class="myButton" value="РУС">
    </form></td>
  </tr></table>
</div>