

<%
   String s = (String)request.getParameter("dir");   
   if ( s == null ) s = "inbound";
   String style = "tbar";
%>


<table border="0" cellpadding="0" cellspacing="0" width="769">
  <tr bgcolor="#000000"> 
    <td class="tbartext">

<% if(s.equals("inbound") ){ style="tbarOn"; }
   else{  style="tbar";  } %>
<a class=<%= style %> href="ediInbound.do"> Inbound Files </a>

<% if(s.equals("archive") ){ style="tbarOn"; }
   else{  style="tbar";  } %>
| <a class=<%= style %> href="ediMgr.jsp?dir=processed"> Inbound Archive </a>

<% if(s.equals("processed_log") ){  style="tbarOn";  }
   else{  style="tbar"; } %>
| <a class=<%= style %> href="ediMgr.jsp?dir=processed_log"> Inbound Processing Logs </a>
<br>&nbsp;&nbsp;&nbsp;&nbsp;
<% if(s.equals("outbound") ){  style="tbarOn"; }
   else{  style="tbar"; }%>
| <a class=<%= style %> href="ediMgr.jsp?dir=outbound"> Files Pending Delivery </a>
<% if(s.equals("sent") ){  style="tbarOn"; }
   else{  style="tbar"; } %>
| <a class=<%= style %> href="ediMgr.jsp?dir=sent"> Outbound Archive </a>
<% if(s.equals("outbound_log") ){  style="tbarOn"; }
   else{  style="tbar"; } %>
| <a class=<%= style %> href="ediMgr.jsp?dir=outbound_log"> Outbound File Generation Logs </a>



</td>
  </tr>
</table>
