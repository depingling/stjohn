<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="org.apache.struts.action.ActionErrors" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<app:checkLogon/>

<script language="JavaScript1.2">
<!--
function popLocate(pLoc, name, pDesc) {
var loc = pLoc + ".do?feedField=" + name + "&amp;feedDesc=" + pDesc + "&amp;submitFl=true";
locatewin = window.open(loc,"Locate", "menubar=yes,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
locatewin.focus();

return false;
}

//-->
</script>

<% String storeDir=ClwCustomizer.getStoreDir();
%>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="SHARE_REPORT_FORM" type="com.cleanwise.view.forms.ShareReportForm"/>
<% PreparedReportView prVw = theForm.getReport();
   SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm");
%>
<table cellpadding="2" cellspacing="0" border="0" width="769" class="mainbody">
 <tbody>
  <tr> <td align="center"><font color=red><html:errors/></font></td></tr>
 </tbody>
</table>

<table cellpadding="0" cellspacing="0" border="0" width="769" class="mainbody">
<html:form name="SHARE_REPORT_FORM" action="reporting/shareRep"
    scope="session" type="com.cleanwise.view.forms.ShareReportForm">
 <tbody>
  <tr><td>
  <table cellpadding="2" cellspacing="0" border="0" width="769" class="mainbody">
  <tr>  <td colspan="4" class="tableheader"><b>Prepared Report Sharing</b>
  </td>
  </tr>
  <tr>
  <td><b>Id:</b>
  <% int reportId = prVw.getReportResultId();
  %>
  <%=reportId%>
  </td>
  <td><b>Date:</b>
  <% Date reportDate = prVw.getReportDate();
     String reportDateS = (reportDate==null)?"":sdf.format(reportDate);
  %>
  <%=reportDateS%>
  </td>
  <td><b>Category:</b>
  <%=prVw.getReportCategory()%>  
  </td>
  <td><b>Name:</b>
  <%=prVw.getReportName()%>  
  </td>
  </tr>
  <tr>
  <td colspan="4"><b>Parameters:</b>
  <%=prVw.getReportParameters()%>
  </td>
  </tr>
  </table> 
  <!-- Groups -->
  <table cellpadding="2" cellspacing="0" border="0" width="769" class="results">
  <tr>  <td colspan="5" class="tableheader"><b>User Groups</b>
  </td>
  <% GroupDataVector gDV = theForm.getGroups(); 
  int grQty = (gDV==null)?0:gDV.size();
  if(grQty>0){ %>
  <tr class="tableheader">
  <td>Group Id</td>
  <td>Short desc</td>
  <td>Group Type</td>
  <td>Group Status</td>
  <td>Select</td>
  </tr>
  <%for (int ii=0; ii<grQty; ii++) {
    GroupData groupD = (GroupData) gDV.get(ii);
    String groupIdS = ""+groupD.getGroupId();
  %>
  <tr>
  <td><%=groupD.getGroupId()%></td>
  <td><%=groupD.getShortDesc()%></td>
  <td><%=groupD.getGroupTypeCd()%></td>
  <td><%=groupD.getGroupStatusCd()%></td>
  <td><html:multibox name="SHARE_REPORT_FORM" property="groupSelected" value="<%=groupIdS%>" /></td>
  </tr>
  <%}}%>
  <tr><td  colspan="5">
  <html:hidden name="SHARE_REPORT_FORM" property="groupToAdd" value=""/>  
  <% String grouplocateS = "return popLocate('/"+storeDir+"/adminportal/grouplocate','groupToAdd','');";%>
  <html:button styleClass="smalltext" onclick="<%=grouplocateS%>" value="Add Group" property="action" /> 
  <% if(grQty>0) { %>
    <html:submit styleClass="smalltext" property="action" value="Remove Selected Groups"/>
  <%}%>
  </td>
  </tr>
  </table> 
   <!-- Users -->
  <table cellpadding="2" cellspacing="0" border="0" width="769" class="results">
  <tr>  <td colspan="6" class="tableheader"><b>Users</b>
  </td>
  <%
  UserDataVector uDV = theForm.getUsers(); 
  int userQty = (uDV==null)?0:uDV.size();
  if(userQty>0) { %>
  <tr class="tableheader">
  <td>User Id</td>
  <td>User Name</td>
  <td>Login Name</td>
  <td>User Type</td>
  <td>User Status</td>
  <td>Select</td>
  </tr>
  <%for (int ii=0; ii<userQty; ii++) {
    UserData userD = (UserData) uDV.get(ii);
    String userIdS = ""+userD.getUserId();
  %>
  <tr>
  <td><%=userD.getUserId()%></td>
  <td><%=userD.getFirstName()+" "+userD.getLastName()%></td>
  <td><%=userD.getUserName()%></td>
  <td><%=userD.getUserTypeCd()%></td>
  <td><%=userD.getUserStatusCd()%></td>
  <td><html:multibox name="SHARE_REPORT_FORM" property="userSelected" value="<%=userIdS%>" /></td>
  </tr>
  <%}}%>
  <tr><td colspan="6">
  <html:hidden name="SHARE_REPORT_FORM" property="userToAdd" value=""/>  
  <% String userlocateS = "return popLocate('/"+storeDir+"/adminportal/userlocate','userToAdd','');";%>
  <html:button styleClass="smalltext" onclick="<%=userlocateS%>" value="Add User" property="action" /> 
  <% if(userQty>0) { %>
    <html:submit styleClass="smalltext" property="action" value="Remove Selected Users"/>
  <%}%>
  </td>
  </tr>
  </table> 

  
  </td>
  </tr>


</tbody></html:form>
</table>

