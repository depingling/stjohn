<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<% boolean clwSwitch = ClwCustomizer.getClwSwitch(); %>

<%
   String s = (String)request.getRequestURI();
   String style = null;
   
%>

<table border="0" cellpadding="0" cellspacing="0" width="769">
<tr>
  <tr bgcolor="#000000"> 
<td class="tbartext">
<% String key = request.getParameter("id");
   if (null == key || "".equals(key)) {
   		key = (String)session.getAttribute("Contract.id");
		if (null == key || "".equals(key)) {
   			key = new String("0");
		}
   }	
   
   String action = request.getParameter("action");
   if("add".equals(action)) {
   	key = new String("0");
   }
   
   session.setAttribute("Contract.id", key);
%>
<% if ("0".equals(key)) { %>
	Contract Detail
<% } else {  %>	

<%
  if(s.indexOf("Detail") > 0){ 
    style="tbarOn";
  }else{ 
    style="tbar";
  }
%>
<a class=<%= style %> href="contractdetail.do?action=edit&id=<%=key%>">Contract Detail</a> |

<%
  if(s.indexOf("relate") > 0 || s.indexOf("Relationships") > 0){ 
    style="tbarOn";
  }else{ 
    style="tbar";
  }
%>
<a class=<%= style %> href="related.do?action=contract">Contract Relationships</a> |

<%
  if(s.indexOf("itemSubstMgr") > 0){ 
    style="tbarOn";
  }else{ 
    style="tbar";
  }
%>
<% if(clwSwitch) { %>
<!--
  <a class=<%= style %> href="itemsubstmgr.do?id=<%=key%>">Item Substitutions</a> |
-->
<% } %>
<%
  if(s.indexOf("freight") > 0){ 
    style="tbarOn";
  }else{ 
    style="tbar";
  }
%>
<a class=<%= style %> href="freighttablemgr.do">Freight Table</a>
<% }  %>
</td>
  </tr>
</table>
