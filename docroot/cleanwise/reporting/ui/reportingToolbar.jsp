
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>

<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<%
String storeDir=ClwCustomizer.getStoreDir();
boolean clwSwitch = ClwCustomizer.getClwSwitch();
CleanwiseUser user = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
String userType = user.getUser().getUserTypeCd();
%>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<script src="../externals/lib.js" language="javascript"></script>
<%
/* Get the page being accessed. */
String pg = request.getRequestURI();
String action1 = (String) request.getAttribute("action");
if(action1==null) request.getParameter("action");
if(action1==null) {
  action1="init";
}

boolean showScheduleFl = false, showAdminButton = false;
boolean showProfileFl = false, showConsoleFl = false;
boolean showEstimatorFl = false;

if ((user.isaReportingUser()|| user.isaCustServiceRep() || user.isaAdmin()) && !user.isaStoreAdmin()) {
    showScheduleFl = true;
    showConsoleFl = true;
}
if (user.isaAdmin() && !user.isaStoreAdmin()){
	showAdminButton = true;
}
if(user.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.RUN_SPENDING_ESTIMATOR)) {
	showEstimatorFl = false;
}
if (user.isaReportingUser()){
showProfileFl = true;
}

%>

<table border="0" cellspacing="0" width="769" align="center">
<tr>
<%--// Temporary for Demo DW //
<td align="left" rowspan=2><img src="../<%=ip%>images/cw_logo.gif"></td>
--%>


 <td align="left" valign="middle">
<% String storeLogo = (String)session.getAttribute("pages.store.logo1.image");
   if (Utility.isSet(storeLogo)) { %>
     <img ID=524 src='<app:custom
      pageElement="pages.store.logo1.image" addImagePath="true" encodeForHTML="true"/>'
      border="0"><BR>
   <% } %>
<%=user.getUserStore().getBusEntity().getShortDesc()%>
</td>

<td align="right" colspan='5'>
<jsp:include flush='true' page="/general/navMenu.jsp"/>
      <br>
      <% java.util.Date currd = new java.util.Date(); %>
      <%= currd.toString() %>
      <br>
      Server: <%=java.net.InetAddress.getLocalHost()%>
</td>
</tr>
<tr>
<td style="border-right: solid 1px #006699;">&nbsp;</td>
   <%
   String tabClass = ""; String linkClass = "";
   if (pg.indexOf("analyticRep")>=0 &&
       (action1.equals("init")||
       action1.equals("Set Filter")||
       action1.equals("Clear Filter")||
       action1.equals("sort")||
       action1.equals("report")||
       action1.equals("Run Report"))){
      tabClass = "aton"; linkClass = "tbar";
   }
   else
   {
     tabClass = "atoff"; linkClass = "tbar2";
   }
   %>
   <td class="<%=tabClass%>"><a class="<%=linkClass%>" href="analyticRep.do">reporting</a></td>
   <%
   if ((pg.indexOf("analyticRep")>=0 &&
       (action1.equals("prepared") ||
       action1.equals("Set Archive Filter")||
       action1.equals("protect")||
       action1.equals("Clear Archive Filter"))||
       action1.equals("Delete Selected Results"))||
       pg.indexOf("shareRep")>=0)
    { tabClass = "aton"; linkClass = "tbar"; }
    else
    { tabClass = "atoff"; linkClass = "tbar2";}
   %>
   <td class="<%=tabClass%>"><a class="<%=linkClass%>" href="analyticRep.do?action=prepared">report library</a></td>
   <%
   if(showScheduleFl) {
   if(pg.indexOf("reportSchedule")>=0) {
     tabClass = "aton"; linkClass = "tbar";
   } else {
     tabClass = "atoff"; linkClass = "tbar2";
   }
   %>
   <td class="<%=tabClass%>"><a class="<%=linkClass%>" href="reportSchedule.do?action=init">schedules</a></td>
   <%}%>
   <%
   if(showEstimatorFl) {
   if(pg.indexOf("estimator")>=0) {
     tabClass = "aton"; linkClass = "tbar";
   } else {
     tabClass = "atoff"; linkClass = "tbar2";
   }
   %>
   <td class="<%=tabClass%>"><a class="<%=linkClass%>" href="estimator.do?action=init">spending estimator</a></td>
   <%}%>
   <%
   if(showProfileFl) {
   if(pg.indexOf("reportingUserProfile")>=0) {
     tabClass = "aton"; linkClass = "tbar";
   } else {
     tabClass = "atoff"; linkClass = "tbar2";
   }
//   String tabName = "profile";
   String tabName = "password";
   %>
   <td class="<%=tabClass%>"><a class="<%=linkClass%>" href="reportingUserProfile.do?action=customer_profile"><%= tabName%></a></td>
   <%}%>
<!-- ss1 --></tr>
</table>
