<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<%
   String s = (String)request.getRequestURI();
   s += " " + request.getParameter("action");
   String style = null;
%>
<table border="0" cellpadding="0" cellspacing="0" width="769">
  <tr bgcolor="#000000">
<td class="tbartext">
<%
if(s.indexOf("viewDetail") > 0){
style="tbarOn";
}else{
style="tbar";
}
%>
<a class=<%=style%> href="groupmgr.do?action=viewDetail">Group Detail</a>|
<%
if(s.indexOf("Relationships") > 0){
style="tbarOn";
}else{
style="tbar";
}
%>
<a class=<%=style%> href="related.do?action=group">Group Relationships</a>|
<%
if(s.indexOf("Config") > 0){
style="tbarOn";
}else{
style="tbar";
}
%>
<a class=<%=style%> href="groupmgrConfig.do">Group Config</a>
<%--If the group type is a user group display the link to do additional user group configuration--%>
<logic:present name="GROUP_FORM">
<logic:equal name="GROUP_FORM" property="groupData.groupTypeCd" value="<%=RefCodeNames.GROUP_TYPE_CD.USER%>">
        <%
        if(s.indexOf("groupmgrUser") > 0){
        style="tbarOn";
        }else{
        style="tbar";
        }
        %>
        |<a class=<%=style%> href="groupmgrUser.do">Additional User Group Configuration</a>
</logic:equal>
</logic:present>
</td>
  </tr>
</table>
