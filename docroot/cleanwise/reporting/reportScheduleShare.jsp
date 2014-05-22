<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.RequestPropertyNames" %>
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
function popLocateSubmit(pLoc, name, pDesc) {
var loc = pLoc + ".do?feedField=" + name + "&amp;feedDesc=" + pDesc + "&amp;submitFl=true";
locatewin = window.open(loc,"Locate", "menubar=yes,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
locatewin.focus();
//
return false;
}


//-->
</script>

<% String storeDir=ClwCustomizer.getStoreDir();%>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="REPORT_SCHEDULE_FORM" type="com.cleanwise.view.forms.ReportScheduleForm"/>

  <!-- Groups -->
  <table cellpadding="2" cellspacing="0" border="0" width="769" class="results">
  <tr>  <td colspan="5" class="tableheader"><b>User Groups</b>
  </td>
  <% ReportSchedGroupShareViewVector gVwV = theForm.getGroups();
  int grQty = (gVwV==null)?0:gVwV.size();
  if(grQty>0){ %>
  <tr class="tableheader">
  <td>Group Id</td>
  <td>Short desc</td>
  <td>Group Type</td>
  <td>Group Status</td>
  <td>Select</td>
  </tr>
  <%for (int ii=0; ii<grQty; ii++) {
    ReportSchedGroupShareView groupVw = (ReportSchedGroupShareView) gVwV.get(ii);
    String groupIdS = ""+groupVw.getGroupId();
  %>
  <tr>
  <td><%=groupVw.getGroupId()%></td>
  <td><%=groupVw.getGroupShortDesc()%></td>
  <td><%=groupVw.getGroupTypeCd()%></td>
  <td><%=groupVw.getGroupStatusCd()%></td>
  <td><html:multibox name="REPORT_SCHEDULE_FORM" property="groupSelected" value="<%=groupIdS%>" /></td>
  </tr>
  <%}}%>
  <tr><td  colspan="5">
  <html:hidden name="REPORT_SCHEDULE_FORM" property="groupToAdd" value=""/>
  <%// String grouplocateS = "return popLocateSubmit('/"+storeDir+"/adminportal/grouplocate','groupToAdd','');";%>
  <%//<html:button styleClass="smalltext" onclick="<%=grouplocateS% >" value="Add Group" property="action" />
  %>
  <html:button property="action" onclick="actionSubmit('REPORT_SCHEDULE_FORM_ID','action','Locate Group')">Locate Group</html:button>&nbsp;

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
  ReportSchedUserShareViewVector uVwV = theForm.getUsers();
  int userQty = (uVwV==null)?0:uVwV.size();
  if(userQty>0) { %>
  <tr class="tableheader">
  <td>User Id</td>
  <td>User Name</td>
  <td>Login Name</td>
  <td>User Type</td>
  <td>User Status</td>
  <td>Report Owner</td>
  <td>Select</td>
  </tr>
  <%for (int ii=0; ii<userQty; ii++) {
    ReportSchedUserShareView userVw = (ReportSchedUserShareView) uVwV.get(ii);
    String userIdS = ""+userVw.getUserId();
  %>
  <tr>
  <td><%=userVw.getUserId()%></td>
  <td><%=userVw.getUserFirstName()+" "+userVw.getUserLastName()%></td>
  <td><%=userVw.getUserLoginName()%></td>
  <td><%=userVw.getUserTypeCd()%></td>
  <td><%=userVw.getUserStatusCd()%></td>
  <td><html:radio name="REPORT_SCHEDULE_FORM" property="reportOwner" value="<%=userIdS%>" /></td>
  <td><html:multibox name="REPORT_SCHEDULE_FORM" property="userSelected" value="<%=userIdS%>" /></td>
  </tr>
  <%}}%>
  <tr><td colspan="6">
  <html:hidden name="REPORT_SCHEDULE_FORM" property="userToAdd" value=""/>
  <%// String userlocateS = "return popLocateSubmit('/"+storeDir+"/adminportal/userlocate','userToAdd','');";%>
 <% /*
  <html:button styleClass="smalltext" onclick="<%=userlocateS% >" value="Add User" property="action" />
  <html:button property="buttonSubmit" onclick="actionSubmit('REPORT_SCHEDULE_FORM_ID','action','Locate User')">Locate User</html:button>&nbsp;
  <html:button onclick="doSubmit('Locate User');" style="width: 150px;" property="buttonSubmit" value="Locate User"/>


*/%>
    <html:button property="action" onclick="actionSubmit('REPORT_SCHEDULE_FORM_ID','action','Locate User')">Locate User</html:button>&nbsp;

  <% if(userQty>0) { %>
  <% /*   <html:submit styleClass="smalltext" property="action" value="Remove Selected Users"/>*/%>
    <button styleClass="store_fb" property="submit" onclick="setAndSubmitB('REPORT_SCHEDULE_FORM_ID','command','Remove Selected Users');">Remove Selected Users</button>

    <%}%>
    <html:hidden styleId="hiddenAction" name="REPORT_SCHEDULE_FORM" property="action" value="BBBBBB"/>
  </td>

  <td>&nbsp;</td>
  <td>
     </td>


  </tr>
  </table>



