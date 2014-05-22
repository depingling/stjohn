<%@ page import="com.cleanwise.view.utils.Constants"%>
<%
    String
        sid = (String)session.getAttribute("Store.id"),
        sname = (String)session.getAttribute("Store.name");
   
%>
    <table ID=1309 border="0" cellpadding="3" cellspacing="1" width="<%=Constants.TABLEWIDTH%>" class="mainbody">
      <tr>
      <td><b>Store&nbsp;Id:</b></td><td><%=sid%></td>
      <td><b>Store&nbsp;Name:</b></td><td><%=sname%></td>
      </tr>

    </table>
