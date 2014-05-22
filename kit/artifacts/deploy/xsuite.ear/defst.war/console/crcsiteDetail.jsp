<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<% boolean clwSwitch = ClwCustomizer.getClwSwitch(); %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="Location" value="site" type="java.lang.String" toScope="session"/>
<bean:define id="theForm" name="CRC_SITE_DETAIL_FORM" type="com.cleanwise.view.forms.SiteMgrDetailForm"/>
<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Console Home: Sites</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">

<script language="JavaScript1.2">
<!--
function popLocate(pLoc, name, pDesc) {
  var loc = pLoc + ".do?feedField=" + name + "&amp;feedDesc=" + pDesc;
  locatewin = window.open(loc,"Locate", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();

  return false;
}
//-->
</script>

</head>

<body>

<jsp:include flush='true' page="ui/consoleToolbar.jsp"/>
<logic:notEqual name="CRC_SITE_DETAIL_FORM" property="id" value="0">
    <jsp:include flush='true' page="ui/consoleSiteToolbar.jsp"/>
</logic:notEqual>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>

<div class="text">
<center>
<font color=red>
<html:errors/>
</font>
</center>

<table cellspacing="0" border="0" width="769"  class="mainbody">
<html:form action="console/crcsiteDetail.do">
  <html:hidden  property="oldSiteNumber"/>

<tr>
<td><b>Account&nbsp;Id:</b></td>
<td>
  <html:hidden  property="accountId"/>
  <bean:write name="CRC_SITE_DETAIL_FORM" property="accountId"/>
</td>

<td><b>Account&nbsp;Name:</b></td>
<td>
<bean:write name="CRC_SITE_DETAIL_FORM" property="accountName"/>
<html:hidden  property="accountName"/>
</td>
</tr>

<tr>
<td><b>Store&nbsp;Id:</b></td>
<td>
<bean:write name="CRC_SITE_DETAIL_FORM" property="storeId"/>
<html:hidden  property="storeId"/>
</td>
<td><b>Store&nbsp;Name:</b></td>
<td>
<bean:write name="CRC_SITE_DETAIL_FORM" property="storeName"/>
<html:hidden  property="storeName"/>
</td>
</tr>
<tr>
<td class="largeheader" colspan="4">Site Detail</td>
</tr>
<tr>
<td><b>Site&nbsp;Id:</b> </td>
<td>
<bean:write name="CRC_SITE_DETAIL_FORM" property="id"/>
<html:hidden property="id"/>
</td>
<td><b>Site Name:</b></td>
<td>
<bean:write name="CRC_SITE_DETAIL_FORM" property="name"/>

</td>
</tr>
<tr>
<td><b>Status:</b></td>
<td>
<bean:write name="CRC_SITE_DETAIL_FORM" property="statusCd"/>
</td>
<td><b>Building Services Contractor:</b></td>
<td>
<bean:write name="CRC_SITE_DETAIL_FORM" property="subContractor"/>
</td>
</tr>
<tr>
<td><b>Effective Date:</b> </td>
<td><bean:write name="CRC_SITE_DETAIL_FORM" property="effDate"/></td>
<td><b>Expiration Date:</b></td>
<td><bean:write name="CRC_SITE_DETAIL_FORM" property="expDate"/></td>
</tr>
<tr>
<td><b>Site&nbsp;Budget&nbsp;Reference&nbsp;Number:</b></td>
<td><bean:write name="CRC_SITE_DETAIL_FORM" property="siteReferenceNumber"/></td>
<td>&nbsp;</td>
<td>&nbsp;</td>
</tr>
<% if(clwSwitch) { %>
<tr>
<td><b>Site&nbsp;Number:</b></td>
<td colspan="3">
<bean:write name="CRC_SITE_DETAIL_FORM" property="siteNumber"/>
<%
IdVector shiptos = theForm.getAvailableShipto();
if(shiptos!=null) {
%>
Available Numbers:
<%
  for(int ii=0; ii<shiptos.size(); ii++) {
  if(ii==0) {
%>
 <%=(Integer)shiptos.get(ii)%>
<% } else { %>
 ,<%=(Integer)shiptos.get(ii)%>
<%}}} %>
</td>
</tr>
<% } else { %>
<tr>
<td><b>Site&nbsp;Number:</b></td>
<td colspan="3">
<bean:write name="CRC_SITE_DETAIL_FORM" property="siteNumber"/>
</td>
</tr>
<% } %>

<tr>
<td colspan=2><b>Taxable:</b>
        Yes&nbsp;<html:radio  property="taxableIndicator" value="Y" disabled='true'/>
        &nbsp;&nbsp;&nbsp;
        No&nbsp;<html:radio  property="taxableIndicator" value="N" disabled='true'/>
</td>
<td colspan=2>
<html:checkbox  property="inventoryShopping" disabled='true'/>
<b>Enable Inventory Shopping/Shared cart</b></td>
</tr>
<tr>
        <td><b>Target Facility Rank:</b></td>
        <td><bean:write name="CRC_SITE_DETAIL_FORM" property="targetFacilityRank"/></td>
        <td><b>Bypass Order Routing</b></td>
        <td><html:checkbox  property="bypassOrderRouting" disabled='true'/></td>
</tr>
<%
   BusEntityFieldsData sfd = (BusEntityFieldsData) session.getAttribute("Site.account.fields");
   if(sfd!=null) {
%>
<tr>
<%
   if(sfd.getF1ShowAdmin()) {
   String val1 = theForm.getF1Value();
   if(val1==null ||val1.trim().length()==0 ) val1 = "no value";
%>
  <td><b><bean:write name="Site.account.fields" property="f1Tag" /></b></td>
  <td><%=val1%> </td>
<% } else { %>
  <td colspan=2>&nbsp;</td>
<% } %>
<%
   if(sfd.getF2ShowAdmin()) {
   String val2 = theForm.getF2Value();
   if(val2==null ||val2.trim().length()==0 ) val2 = "no value";
%>
  <td><b><bean:write name="Site.account.fields" property="f2Tag" /></b></td>
  <td><%=val2%> </td>
<% } else { %>
  <td colspan=2>&nbsp;</td>
<%} %>


<tr>
  <td colspan="2">&nbsp;</td>
<%
   if(sfd.getF3ShowAdmin()) {
   String val3 = theForm.getF3Value();
   if(val3==null ||val3.trim().length()==0 ) val3 = "no value";
%>
  <td><b><bean:write name="Site.account.fields" property="f3Tag" /></b></td>
  <td><%=val3%> </td>
<% } else { %>
  <td colspan=2>&nbsp;</td>
<%} %>
</tr>

<tr>
<%
   if(sfd.getF4ShowAdmin()) {
   String val4 = theForm.getF4Value();
   if(val4==null ||val4.trim().length()==0 ) val4 = "no value";
%>
  <td><b><bean:write name="Site.account.fields" property="f4Tag" /></b></td>
  <td><%=val4%> </td>
<% } else { %>
  <td colspan=2>&nbsp;</td>
<%} %>
<%
   if(sfd.getF5ShowAdmin()) {
   String val5 = theForm.getF5Value();
   if(val5==null ||val5.trim().length()==0 ) val5 = "no value";
%>
  <td><b><bean:write name="Site.account.fields" property="f5Tag" /></b></td>
  <td><%=val5%> </td>
<% } else { %>
  <td colspan=2>&nbsp;</td>
<%} %>
</tr>
<% } %>
<tr>
<td colspan="4" class="largeheader"><br>Address</td>
</td>
</tr>

<tr>
<td><b>First Name:</b>
 </td>
<td>
<bean:write name="CRC_SITE_DETAIL_FORM" property="name1"/>
</td>
<td colspan="2">&nbsp;</td>
</tr>
<tr>
<td><b>Last Name:</b>
 </td>
<td>
<bean:write name="CRC_SITE_DETAIL_FORM" property="name2"/>
</td>
<td colspan="2">&nbsp;</td>
</tr>
<tr>
<td><b>Street Address 1:</b>
</td>
<td>
<bean:write name="CRC_SITE_DETAIL_FORM" property="streetAddr1"/>
</td>
<td colspan="2">&nbsp;</td>
</tr>

<tr>
<td><b>Street Address 2:</b></td>
<td>
<bean:write name="CRC_SITE_DETAIL_FORM" property="streetAddr2"/>
</td>
<td><b>Country:</b>
 </td>
<td>
<bean:write name="CRC_SITE_DETAIL_FORM" property="country"/>
</td>
</tr>
<tr>
<td><b>Street Address 3:</b></td>
<td>
<bean:write name="CRC_SITE_DETAIL_FORM" property="streetAddr3"/>
</td>

<td><b>State/Province:</b>
 </td>
<td>
<bean:write  name="CRC_SITE_DETAIL_FORM"  property="stateOrProv" />
</td>
</tr>
<tr>
<td><b>Street Address 4:</b></td>
<td>
<bean:write name="CRC_SITE_DETAIL_FORM" property="streetAddr4"/>
</td>
<td colspan="2"> </td>
</tr>
<tr>
<td><b>City:</b>
</td>
<td>
<bean:write name="CRC_SITE_DETAIL_FORM" property="city"/>
</td>
<td><b>Zip/Postal Code:</b>
 </td>
<td>
<bean:write name="CRC_SITE_DETAIL_FORM" property="postalCode"/>
</td>
</tr>
<tr>
<td><b>County:</b></td>
<td>
<bean:write name="CRC_SITE_DETAIL_FORM" property="county"/>
</td>
</tr>
<tr>
<td><b>Order Guide Comments:</b> </td>
<td colspan=4>
<bean:write name="CRC_SITE_DETAIL_FORM" property="comments"/>
</td>
</tr>
<tr>
<td><b>Shipping Message:</b> </td>
<td colspan=4>
<bean:write name="CRC_SITE_DETAIL_FORM" property="shippingMessage"/>
</td>
</tr>

</html:form>

</table>

</div>

<jsp:include flush='true' page="ui/consoleFooter.jsp"/>

</body>

</html:html>






