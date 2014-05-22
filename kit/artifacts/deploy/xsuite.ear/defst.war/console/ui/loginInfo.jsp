<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>

<table cellpadding="3" cellspacing="0" border="0" width="769">
  <tr style="background-color : #000080; color: white;"> 
    <td><b>Login:</b></td>
    <td><%=session.getAttribute(Constants.USER_NAME)%></td>
    <td><b>Title:</b></td>
    <td><%=session.getAttribute(Constants.USER_TYPE)%></td>
  </tr>
</table>
