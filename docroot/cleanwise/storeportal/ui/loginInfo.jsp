<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>

<table cellpadding="3" cellspacing="0" border="0" width="769">
  <tr class="greenruleandlinks"> 
    <td><b>Login:</b></td>
    <td><%=session.getAttribute(Constants.USER_NAME)%></td>
    <td><b>Title:</b></td>
    <td><%=session.getAttribute(Constants.USER_TYPE)%></td>
  </tr>
</table>
