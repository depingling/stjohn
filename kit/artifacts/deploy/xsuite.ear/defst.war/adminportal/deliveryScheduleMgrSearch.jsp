<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.util.SearchCriteria" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>
<% String storeDir=ClwCustomizer.getStoreDir(); 
  String portal = request.getParameter("portal");
  boolean adminPortalFl = ("adminportal".equalsIgnoreCase(portal))?true:false;
  String actionStr = (adminPortalFl)?"/adminportal/deliveryScheduleMgr.do":"/console/crcDeliverySchedule.do";  
  String thisLink = (adminPortalFl)?"deliveryScheduleMgr.do":"crcDeliverySchedule.do";
  boolean readOnlyFl = (adminPortalFl)?false:true;
  String disabledStr = (readOnlyFl)?"disabled='true'":"";
  
%>  

<html:html>
<head>
<title>Delivery Schedule Search</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../externals/styles.css">
</head>
<bean:define id="mainForm" name="DIST_DETAIL_FORM" type="com.cleanwise.view.forms.DistMgrDetailForm"/>
<bean:define id="theForm" name="DELIVERY_SCHEDULE_FORM" type="com.cleanwise.view.forms.DeliveryScheduleMgrForm"/>
<body bgcolor="#cccccc">
<div class = "text">

<font color=red><html:errors/></font>
<table cellspacing="0" border="0" width="769"  class="mainbody">
<html:form action="<%=actionStr%>">
<html:hidden name="DELIVERY_SCHEDULE_FORM" property="contentPage"  value="deliveryScheduleMgrSearch.jsp"/>
<%
  String distIdS = mainForm.getId();
%>
<tr>
<td><b>Distributor&nbsp;Id:</b></td>
<td><%=""+distIdS%></td>
 <td><b>Name:</b></td>
<td>
<bean:write name="DIST_DETAIL_FORM" property="name"/>
</td>
</tr>
<tr>
  <td><b>Name:</b></td>
  <td><html:text name="DELIVERY_SCHEDULE_FORM" property="searchName" size="30"/>
  </td>
  <td><b>County:</b></td>
  <td><html:text name="DELIVERY_SCHEDULE_FORM" property="searchCounty" size="30"/>
  </td>
  <tr><td><b>Zip Code:</b></td>
  <td><html:text name="DELIVERY_SCHEDULE_FORM" property="searchZipCode" size="10"/>
  </td>
  <td><b>State:</b></td>
  <td><html:text name="DELIVERY_SCHEDULE_FORM" property="searchState" size="3"/>
  </td>
  </tr>
  <tr><td>&nbsp;</td>
   <td>
    <html:radio name="DELIVERY_SCHEDULE_FORM" property="searchType" 
      value="<%=\"\"+SearchCriteria.BEGINS_WITH_IGNORE_CASE%>"/>Begins With
    <html:radio name="DELIVERY_SCHEDULE_FORM" property="searchType" 
      value="<%=\"\"+SearchCriteria.CONTAINS_IGNORE_CASE%>"/>Contains
  </td>
  <td><b>Schedule Id:</b></td>
  <td><html:text name="DELIVERY_SCHEDULE_FORM" property="searchId" size="6"/>
  </td>
  </tr>
  <tr> <td>&nbsp;</td>
  <td colspan="3">
  <html:submit property="action" value="Search"/>
  <html:submit property="action" value="View All"/>
  <% if(!readOnlyFl) { %>
  <html:submit property="action" value="Create New"/>
  <% } %>
  </td>
  </tr>
</table>
<logic:present name="DELIVERY_SCHEDULE_FORM" property="scheduleList">
<% int size = theForm.getScheduleList().size(); %>
Search result count: <%=size %>
<% if(size>0) { %>
<table cellspacing="0" border="0" width="769"  class="results">
<tr align=center>
<td><a class="tableheader" href="<%=thisLink%>?action=sort&sortField=ScheduleId"><b>Id</b></a> </td>
<td><a class="tableheader" href="<%=thisLink%>?action=sort&sortField=ScheduleName"><b>Name</b></a> </td>
<td><a class="tableheader" href="<%=thisLink%>?action=sort&sortField=ScheduleStatus"><b>Status</b></a> </td>
<td><a class="tableheader" href="<%=thisLink%>?action=sort&sortField=ScheduleInfo"><b>Info</b></a> </td>
<td><a class="tableheader" href="<%=thisLink%>?action=sort&sortField=ScheduleInfo"><b>Cutoff</b></a> </td>
<td><a class="tableheader" href="<%=thisLink%>?action=sort&sortField=ScheduleInfo"><b>Next Delivery</b></a> </td>
</tr>
<logic:iterate id="arrele" name="DELIVERY_SCHEDULE_FORM" property="scheduleList"
  type="com.cleanwise.service.api.value.DeliveryScheduleView"
  indexId="idx"
>
  <bean:define id="eleid" name="arrele" property="scheduleId"/>
   <% if ( idx.intValue()%2==0 ) { %>
     <tr class="rowa">
   <% } else { %>
     <tr class="rowb">
   <% } %>
    <td><bean:write name="arrele" property="scheduleId"/></td>
    <td>
      <a href="<%=thisLink%>?action=detail&scheduleId=<%=eleid%>">
      <bean:write name="arrele" property="scheduleName"/>
      </a>
    </td>
    <td><bean:write name="arrele" property="scheduleStatus"/></td>
    <td><bean:write name="arrele" property="scheduleInfo"/></td>
    <td><bean:write name="arrele" property="cutoffInfo"/></td>
    <% 
       java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("EEE, MM/dd/yyyy");
       String nextDel = (arrele.getNextDelivery()==null)?"":sdf.format(arrele.getNextDelivery());
    %>
    <td><%=nextDel%></td>

</tr>
</logic:iterate>
</table>
<% } %>
</logic:present>
</html:form>
</div>
</body>
</html:html>

