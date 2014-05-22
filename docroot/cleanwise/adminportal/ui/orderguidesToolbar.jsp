
<%
   String s = (String)request.getRequestURI();
   String style = null;
   
%>

<table border="0" cellpadding="0" cellspacing="0" width="769">
<tr>
  <tr bgcolor="#000000"> 
<td class="tbartext">

<%
  if(s.indexOf("Detail") > 0){ 
    style="tbarOn";
  }else{ 
    style="tbar";
  }
%>
<a class=<%= style %> href=orderguidesmgrDetail.do>Order Guide Detail</a> |

<%
  if(s.indexOf("relate") > 0 || s.indexOf("Relationships") > 0){ 
    style="tbarOn";
  }else{ 
    style="tbar";
  }
%>
<a class=<%= style %> href="related.do?action=orderguide">
Order Guide Relationships</a>
</td>
</tr>
</table>
