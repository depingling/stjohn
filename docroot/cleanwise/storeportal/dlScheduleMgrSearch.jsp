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

<html:html>
<head>
<title>Delivery Schedule Search</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../externals/styles.css">
</head>
<bean:define id="mainForm" name="STORE_DIST_DETAIL_FORM" type="com.cleanwise.view.forms.StoreDistMgrDetailForm"/>
<bean:define id="theForm" name="STORE_DELIVERY_SCHEDULE_FORM"
  type="com.cleanwise.view.forms.StoreDeliveryScheduleMgrForm"/>
<body bgcolor="#cccccc">
<div class = "text">

<table ID=266 cellspacing="0" border="0" width="769"  class="mainbody">
<html:form styleId="267" action="storeportal/dlschdlmgr.do">
<html:hidden name="STORE_DELIVERY_SCHEDULE_FORM" property="contentPage"  value="dlScheduleMgrSearch.jsp"/>
<%
  String distIdS = mainForm.getId();
%>
<tr>
<td><b>Distributor&nbsp;Id:</b></td>
<td><%=""+distIdS%></td>
 <td><b>Name:</b></td>
<td>
<bean:write name="STORE_DIST_DETAIL_FORM" property="name"/>
</td>
</tr>
<tr>
  <td><b>Name:</b></td>
  <td><html:text name="STORE_DELIVERY_SCHEDULE_FORM" property="searchName" size="30"/></td>
  <td><b>City (starts with):</b></td>
  <td><html:text name="STORE_DELIVERY_SCHEDULE_FORM" property="searchCity" size="30"/></td>
</tr>
<tr>
  <td><b>Zip Code:</b></td>
  <td><html:text name="STORE_DELIVERY_SCHEDULE_FORM" property="searchZipCode" size="10"/></td>
  <td><b>State:</b></td>
  <td><html:text name="STORE_DELIVERY_SCHEDULE_FORM" property="searchState" size="3"/></td>
</tr>
<tr>
  <td>&nbsp;</td>
  <td>
    <html:radio name="STORE_DELIVERY_SCHEDULE_FORM" property="searchType"
      value="<%=\"\"+SearchCriteria.BEGINS_WITH_IGNORE_CASE%>"/>Begins With
    <html:radio name="STORE_DELIVERY_SCHEDULE_FORM" property="searchType"
      value="<%=\"\"+SearchCriteria.CONTAINS_IGNORE_CASE%>"/>Contains
  </td>
  <td><b>County (starts with):</b></td>
  <td><html:text name="STORE_DELIVERY_SCHEDULE_FORM" property="searchCounty" size="30"/></td>
</tr>
<tr>
  <td>&nbsp;</td>
  <td>&nbsp;</td>
  <td><b>Schedule Id:</b></td>
  <td><html:text name="STORE_DELIVERY_SCHEDULE_FORM" property="searchId" size="6"/></td>
</tr>
<tr>
  <td>&nbsp;</td>
  <td colspan="3">
  <html:submit property="action" value="Search"/>
  <html:submit property="action" value="Create New"/>
  </td>
</tr>
</table>
<logic:present name="STORE_DELIVERY_SCHEDULE_FORM" property="scheduleList">
<% int size = theForm.getScheduleList().size(); %>
Count: <%=size %>
<% if(size>0) { %>
<table ID=268 cellspacing="0" border="0" width="769">
<tr align=center>
<th class="stpTH"><a ID=269 href="dlschdlmgr.do?action=sort&sortField=ScheduleId"><b>Id</b></a></th>
<th class="stpTH"><a ID=270 href="dlschdlmgr.do?action=sort&sortField=ScheduleName"><b>Name</b></a></th>
<th class="stpTH"><a ID=271 href="dlschdlmgr.do?action=sort&sortField=ScheduleStatus"><b>Status</b></a></th>
<th class="stpTH"><a ID=272 href="dlschdlmgr.do?action=sort&sortField=ScheduleInfo"><b>Info</b></a></th>
<th class="stpTH"><a ID=273 href="dlschdlmgr.do?action=sort&sortField=ScheduleInfo"><b>Cutoff</b></a></th>
<th class="stpTH"><a ID=274 href="dlschdlmgr.do?action=sort&sortField=ScheduleInfo"><b>Next Delivery</b></a></th>
</tr>
<logic:iterate id="arrele" name="STORE_DELIVERY_SCHEDULE_FORM" property="scheduleList"
  type="com.cleanwise.service.api.value.DeliveryScheduleView"
  indexId="idx"
>
  <bean:define id="eleid" name="arrele" property="scheduleId"/>
<tr>
    <td class="stpTD"><bean:write name="arrele" property="scheduleId"/></td>
    <td class="stpTD">
      <a ID=275 href="dlschdldet.do?action=detail&scheduleId=<%=eleid%>">
      <bean:write name="arrele" property="scheduleName"/>
      </a>
    </td>
    <td class="stpTD"><bean:write name="arrele" property="scheduleStatus"/></td>
    <td class="stpTD"><bean:write name="arrele" property="scheduleInfo"/></td>
    <td class="stpTD"><bean:write name="arrele" property="cutoffInfo"/></td>
    <%
       java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("EEE, MM/dd/yyyy");
       String nextDel = (arrele.getNextDelivery()==null)?"":sdf.format(arrele.getNextDelivery());
    %>
    <td class="stpTD"><%=nextDel%></td>

</tr>
</logic:iterate>
</table>
<% } %>
</logic:present>
</html:form>
</div>
</body>
</html:html>

