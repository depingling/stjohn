<%@ page language="java" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>


<%
   String s = (String)request.getRequestURI();
   String style = null;
%>


<table border="0" cellpadding="0" cellspacing="0" width="769">
<tr bgcolor="#000000">
<td class="tbartext">
<%
  if(s.indexOf("profilingSurveyMgr") > 0){
    style="tbarOn";
  }else{
    style="tbar";
  }
%>
<a class=<%= style %> href="profilingSurveyMgr.do?action=view">Profile Detail</a> |

<%
  if(s.indexOf("relate") > 0 || s.indexOf("Relationships") > 0){
    style="tbarOn";
  }else{
    style="tbar";
  }
%>
<a class=<%= style %> href="related.do?action=profiling">Profile Relationships</a> |

<%
  if(s.indexOf("profilingConfiguration") > 0){
    style="tbarOn";
  }else{
    style="tbar";
  }
%>
<a class=<%= style %> href="profilingConfiguration.do">Profile Configuration</a>

</td>
</tr>
</table>


