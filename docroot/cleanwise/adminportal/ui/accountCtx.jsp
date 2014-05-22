<%
String 
	sid = (String)session.getAttribute("Store.id"),
	sname = (String)session.getAttribute("Store.name"),
	aid = (String)session.getAttribute("Account.id"),
	aname = (String)session.getAttribute("Account.name")
;
%>
<table border="0" cellpadding="3" cellspacing="1" width="769" class="mainbody">
  <tr>
  <td><b>Store&nbsp;Id:</b></td><td><%=sid%></td>
  <td><b>Store&nbsp;Name:</b></td><td><%=sname%></td>
  </tr>
  <tr>
  <td><b>Account&nbsp;Id:</b></td><td><%=aid%></td>
  <td><b>Account&nbsp;Name:</b></td><td><%=aname%></td>
  </tr>
</table>
