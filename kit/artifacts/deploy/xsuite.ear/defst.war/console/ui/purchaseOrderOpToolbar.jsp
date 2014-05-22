<table border="0" cellpadding="0" cellspacing="0" width="769">
<%
   String uri = (String)request.getRequestURI();
   String style = "";
%>
<tr bgcolor="#000000">
<td class="tbartext">
    <% style = (uri.indexOf("LineTracker")<0)? "tbarOn":"tbar"; %>
<a class='<%= style %>' href="purchaseOrderOp.do">Search</a> |
    <% style = (uri.indexOf("LineTracker")>=0)? "tbarOn":"tbar"; %>
<a class='<%= style %>' href="purchaseOrderLineTrackerOp.do">Track Open PO Lines </a>
</td>
</tr>
</table>
