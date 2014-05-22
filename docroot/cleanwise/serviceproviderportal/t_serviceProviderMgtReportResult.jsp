
<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="java.util.Locale" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<bean:define id="IMGPath" type="java.lang.String" 
  name="pages.store.images"/>

<div class="text"><font color=red><html:errors/></font></div>

<bean:define id="thisReportCd" name="SERVICE_PROVIDER_REPORT_FORM" 
 type="java.lang.String" property="reportTypeCd"/>
<% boolean genericReportFl = true; %>
<bean:define id="printMsg" value="<br><br>You may print this page by selecting File &gt; Print from your browser's menu bar.  <br><br>" 
  type="java.lang.String"/>




<html:form name="SERVICE_PROVIDER_REPORT_FORM" 
  action="/serviceproviderportal/serviceProviderMgtReportResult.do"
  type="com.cleanwise.view.forms.ServiceProviderMgtReportForm">
	  

<TABLE cellSpacing=0 cellPadding=0 border=0>
<TBODY>

<logic:equal name="SERVICE_PROVIDER_REPORT_FORM" property="reportTypeCd" value="<%=RefCodeNames.CUSTOMER_REPORT_TYPE_CD.TOTAL_VOLUME%>">									
<% genericReportFl = false; %>
<tr>
<td width="757" valign="top" class="text">
<div class="fivemargin"><img src="<%=IMGPath%>/cw_spacer.gif" height="5"><br>
<div class="subheadergeneric">
<bean:write name="SERVICE_PROVIDER_REPORT_FORM" property="reportTypeCd" filter="true"/>					
</div>
<%=printMsg%>

<table align="center" border="0" cellspacing="0" cellpadding="0" width="730">
<tr class="top3dk" valign="top">
<td class="columntitle" colspan="4">
<bean:write name="SERVICE_PROVIDER_REPORT_FORM" property="reportTypeCd" filter="true"/>
</td>
</tr>

<bean:define id="beginDate" name="SERVICE_PROVIDER_REPORT_FORM" property="beginDate" />
<bean:define id="endDate" name="SERVICE_PROVIDER_REPORT_FORM" property="endDate" />
<bean:define id="beginDateBefore" name="SERVICE_PROVIDER_REPORT_FORM" property="beginDateBefore" />
<bean:define id="endDateBefore" name="SERVICE_PROVIDER_REPORT_FORM" property="endDateBefore" />

<tr class="changeshippinglt" valign="top">
<td class="cityhead">Category</td>
<td class="cityhead">
<i18n:formatDate value="<%=beginDate%>" pattern="MMMMMMMM yyyy"/> - 
<i18n:formatDate value="<%=endDate%>" pattern="MMMMMMMM yyyy"/>
</td>
<td class="cityhead">
<i18n:formatDate value="<%=beginDateBefore%>" pattern="MMMMMMMM yyyy"/> - 
<i18n:formatDate value="<%=endDateBefore%>" pattern="MMMMMMMM yyyy"/>
</td>
<td class="cityhead">Change</td>
</tr>

<logic:iterate id="result" name="SERVICE_PROVIDER_REPORT_FORM" 
property="resultList" scope="session" type="com.cleanwise.service.api.value.CustomerReportResultData">					
<tr>
<td class="rtitle"><bean:write name="result" property="mainCategoryDesc" filter="true"/>
</td>
<td class="rtext">
<bean:define id="amountNow" name="result" property="amountNow" />
<i18n:formatCurrency value="<%=amountNow%>" locale="<%=Locale.US%>"/>
</td>
<td class="rtext">
<bean:define id="amountBefore" name="result" property="amountBefore" />
<i18n:formatCurrency value="<%=amountBefore%>" locale="<%=Locale.US%>"/>
</td>
<td class="rtext">
<bean:define id="amountChange" name="result" property="amountChange" />
<i18n:formatCurrency value="<%=amountChange%>" locale="<%=Locale.US%>"/>
</td>
</tr>
<tr class="top3dk">
<td colspan="4"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
</tr>
</logic:iterate>

<tr>
<td class="rtitle">Totals</td>
<td class="rtext">
<bean:define id="totalAmount" name="SERVICE_PROVIDER_REPORT_FORM" property="totalAmount" />
<i18n:formatCurrency value="<%=totalAmount%>" locale="<%=Locale.US%>"/>
</td>
<td class="rtext">
<bean:define id="totalAmountBefore" name="SERVICE_PROVIDER_REPORT_FORM" property="totalAmountBefore" />
<i18n:formatCurrency value="<%=totalAmountBefore%>" locale="<%=Locale.US%>"/>
</td>
<td class="rtext">
<bean:define id="totalAmountChange" name="SERVICE_PROVIDER_REPORT_FORM" property="totalAmountChange" />
<i18n:formatCurrency value="<%=totalAmountChange%>" locale="<%=Locale.US%>"/>					
</td>
</tr>
<tr class="top3dk">
<td colspan="4"><img src="<%=IMGPath%>/cw_spacer.gif" height="2" width="1"></td>
</tr>
</table>
</div>
</td>
</tr>
</logic:equal>
								

<logic:equal name="SERVICE_PROVIDER_REPORT_FORM" property="reportTypeCd" value="<%=RefCodeNames.CUSTOMER_REPORT_TYPE_CD.TOTAL_VOLUME_BY_LOCATION%>">									
<% genericReportFl = false; %>
<tr>
<td width="757" valign="top" class="text">
<div class="fivemargin"><img src="<%=IMGPath%>/cw_spacer.gif" height="5"><br>
<div class="subheadergeneric">
<bean:write name="SERVICE_PROVIDER_REPORT_FORM" property="reportTypeCd" filter="true"/>					
</div>
<%=printMsg%>

<table align="center" border="0" cellspacing="0" cellpadding="0" width="730">
<tr class="top3dk" valign="top">
<td class="columntitle" colspan="5">
<bean:write name="SERVICE_PROVIDER_REPORT_FORM" property="reportTypeCd" filter="true"/>					
</td>
</tr>
					
<bean:define id="beginDate" name="SERVICE_PROVIDER_REPORT_FORM" property="beginDate" />
<bean:define id="endDate" name="SERVICE_PROVIDER_REPORT_FORM" property="endDate" />
<bean:define id="beginDateBefore" name="SERVICE_PROVIDER_REPORT_FORM" property="beginDateBefore" />
<bean:define id="endDateBefore" name="SERVICE_PROVIDER_REPORT_FORM" property="endDateBefore" />

<tr class="changeshippinglt" valign="top">
  <td class="cityhead">Location</td>
  <td class="cityhead">Category</td>
  <td class="cityhead">
<i18n:formatDate value="<%=beginDate%>" pattern="MMMMMMMM yyyy"/> - 
<i18n:formatDate value="<%=endDate%>" pattern="MMMMMMMM yyyy"/>
</td>
<td class="cityhead">
<i18n:formatDate value="<%=beginDateBefore%>" pattern="MMMMMMMM yyyy"/> - 
<i18n:formatDate value="<%=endDateBefore%>" pattern="MMMMMMMM yyyy"/>
</td>
<td class="cityhead">Change</td>
</tr>

<% String formerSiteName = new String(""); %> 												
<logic:iterate id="result" name="SERVICE_PROVIDER_REPORT_FORM" 
property="resultList" scope="session" type="com.cleanwise.service.api.value.CustomerReportResultData">					
<bean:define id="siteName" name="result" property="siteName" type="String"/>
<% if (! siteName.equals(formerSiteName)) {
formerSiteName = siteName;
%>								
 <tr class="jcminheader">
 <td width="150" class="rtitle" colspan="5">
<bean:write name="result" property="siteName" filter="true"/>
</td>
</tr>
<% }  %>

<tr>
 <td class="rtitle">&nbsp;
</td>
 <td class="rtitle"><bean:write name="result" property="mainCategoryDesc" filter="true"/>
</td>
<td class="rtext">
<bean:define id="amountNow" name="result" property="amountNow" />
<i18n:formatCurrency value="<%=amountNow%>" locale="<%=Locale.US%>"/>
</td>
<td class="rtext">
<bean:define id="amountBefore" name="result" property="amountBefore" />
<i18n:formatCurrency value="<%=amountBefore%>" locale="<%=Locale.US%>"/>
</td>
<td class="rtext">
<bean:define id="amountChange" name="result" property="amountChange" />
<i18n:formatCurrency value="<%=amountChange%>" locale="<%=Locale.US%>"/>
</td>
</tr>
<tr class="top3dk">
<td colspan="5"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
</tr>
</logic:iterate>

<tr>
<td class="rtitle">Totals</td>
<td class="rtitle">&nbsp;</td>
<td class="rtext">
<bean:define id="totalAmount" name="SERVICE_PROVIDER_REPORT_FORM" property="totalAmount" />
<i18n:formatCurrency value="<%=totalAmount%>" locale="<%=Locale.US%>"/>
</td>
<td class="rtext">
<bean:define id="totalAmountBefore" name="SERVICE_PROVIDER_REPORT_FORM" property="totalAmountBefore" />
<i18n:formatCurrency value="<%=totalAmountBefore%>" locale="<%=Locale.US%>"/>
</td>
<td class="rtext">
<bean:define id="totalAmountChange" name="SERVICE_PROVIDER_REPORT_FORM" property="totalAmountChange" />
<i18n:formatCurrency value="<%=totalAmountChange%>" locale="<%=Locale.US%>"/>					
</td>
</tr>
<tr class="top3dk">
<td colspan="5"><img src="<%=IMGPath%>/cw_spacer.gif" height="2" width="1"></td>
</tr>
</table>
</div>
</td>
</tr>
</logic:equal>


<logic:equal name="SERVICE_PROVIDER_REPORT_FORM" property="reportTypeCd" value="<%=RefCodeNames.CUSTOMER_REPORT_TYPE_CD.VOLUME_BY_CATEGORY%>">									
<% genericReportFl = false; %>
<tr>
<td width="757" valign="top" class="text">
<div class="fivemargin"><img src="<%=IMGPath%>/cw_spacer.gif" height="5"><br>
<div class="subheadergeneric">
<bean:write name="SERVICE_PROVIDER_REPORT_FORM" property="reportTypeCd" filter="true"/>					
</div>
<%=printMsg%>
<table align="center" border="0" cellspacing="0" cellpadding="0" width="730">
<tr class="top3dk" valign="top">
<td class="columntitle" colspan="6">
<bean:write name="SERVICE_PROVIDER_REPORT_FORM" property="reportTypeCd" filter="true"/>					
</td>
</tr>
	
<bean:define id="beginDate" name="SERVICE_PROVIDER_REPORT_FORM" property="beginDate" />
<bean:define id="endDate" name="SERVICE_PROVIDER_REPORT_FORM" property="endDate" />
<bean:define id="beginDateBefore" name="SERVICE_PROVIDER_REPORT_FORM" property="beginDateBefore" />
<bean:define id="endDateBefore" name="SERVICE_PROVIDER_REPORT_FORM" property="endDateBefore" />
					
<tr class="changeshippinglt" valign="top">
<td class="cityhead">Location</td>
<td class="cityhead">Main Category</td>
<td class="cityhead">Sub Category</td>
<td class="cityhead">
<i18n:formatDate value="<%=beginDate%>" pattern="MMMMMMMM yyyy"/> - 
<i18n:formatDate value="<%=endDate%>" pattern="MMMMMMMM yyyy"/>
</td>
<td class="cityhead">
<i18n:formatDate value="<%=beginDateBefore%>" pattern="MMMMMMMM yyyy"/> - 
<i18n:formatDate value="<%=endDateBefore%>" pattern="MMMMMMMM yyyy"/>
</td>
<td class="cityhead">Change</td>
</tr>

<% String formerSiteName = new String(""); %> 												
<logic:iterate id="result" name="SERVICE_PROVIDER_REPORT_FORM" 
property="resultList" scope="session" type="com.cleanwise.service.api.value.CustomerReportResultData">					
<bean:define id="siteName" name="result" property="siteName" type="String"/>
<% if (! siteName.equals(formerSiteName)) {
formerSiteName = siteName;
%>								
<tr class="jcminheader">
<td width="300" class="rtitle" colspan="6">
<bean:write name="result" property="siteName" filter="true"/>
</td>
</tr>
<% }  %>
							
<tr>
<td class="rtitle">&nbsp;
</td>
<td class="rtitle"><bean:write name="result" property="mainCategoryDesc" filter="true"/>
</td>
<td class="rtitle"><bean:write name="result" property="subCategoryDesc" filter="true"/>
</td>
<td class="rtext">
<bean:define id="amountNow" name="result" property="amountNow" />
<i18n:formatCurrency value="<%=amountNow%>" locale="<%=Locale.US%>"/>
</td>
<td class="rtext">
<bean:define id="amountBefore" name="result" property="amountBefore" />
<i18n:formatCurrency value="<%=amountBefore%>" locale="<%=Locale.US%>"/>
</td>
<td class="rtext">
<bean:define id="amountChange" name="result" property="amountChange" />
<i18n:formatCurrency value="<%=amountChange%>" locale="<%=Locale.US%>"/>
</td>
</tr>
<tr class="top3dk">
<td colspan="6"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
</tr>
</logic:iterate>

<tr>
<td class="rtitle">Totals</td>
<td class="rtitle">&nbsp;</td>
<td class="rtitle">&nbsp;</td>
<td class="rtext">
<bean:define id="totalAmount" name="SERVICE_PROVIDER_REPORT_FORM" property="totalAmount" />
<i18n:formatCurrency value="<%=totalAmount%>" locale="<%=Locale.US%>"/>
</td>
<td class="rtext">
<bean:define id="totalAmountBefore" name="SERVICE_PROVIDER_REPORT_FORM" property="totalAmountBefore" />
<i18n:formatCurrency value="<%=totalAmountBefore%>" locale="<%=Locale.US%>"/>
</td>
<td class="rtext">
<bean:define id="totalAmountChange" name="SERVICE_PROVIDER_REPORT_FORM" property="totalAmountChange" />
<i18n:formatCurrency value="<%=totalAmountChange%>" locale="<%=Locale.US%>"/>					
</td>
</tr>
<tr class="top3dk">
<td colspan="6"><img src="<%=IMGPath%>/cw_spacer.gif" height="2" width="1"></td>
</tr>
</table>
</div>
</td>
</tr>
</logic:equal>


<logic:equal name="SERVICE_PROVIDER_REPORT_FORM" property="reportTypeCd" value="<%=RefCodeNames.CUSTOMER_REPORT_TYPE_CD.VOLUME_BY_ITEM%>">									
<% genericReportFl = false; %>
<tr>
<td width="757" valign="top" class="text">
<div class="fivemargin"><img src="<%=IMGPath%>/cw_spacer.gif" height="5"><br>
<div class="subheadergeneric">
<bean:write name="SERVICE_PROVIDER_REPORT_FORM" property="reportTypeCd" filter="true"/>					
</div>
<%=printMsg%>

<table align="center" border="0" cellspacing="0" cellpadding="0" width="730">
<tr class="top3dk" valign="top">
<td class="columntitle" colspan="5">
<bean:write name="SERVICE_PROVIDER_REPORT_FORM" property="reportTypeCd" filter="true"/>					
</td>
</tr>
	
<bean:define id="beginDate" name="SERVICE_PROVIDER_REPORT_FORM" property="beginDate" />
<bean:define id="endDate" name="SERVICE_PROVIDER_REPORT_FORM" property="endDate" />
<bean:define id="beginDateBefore" name="SERVICE_PROVIDER_REPORT_FORM" property="beginDateBefore" />
<bean:define id="endDateBefore" name="SERVICE_PROVIDER_REPORT_FORM" property="endDateBefore" />
					
<tr class="changeshippinglt" valign="top">
<td class="cityhead">Location</td>
<td class="cityhead">Product</td>
<td class="cityhead">
<i18n:formatDate value="<%=beginDate%>" pattern="MMMMMMMM yyyy"/> - 
<i18n:formatDate value="<%=endDate%>" pattern="MMMMMMMM yyyy"/>
</td>
<td class="cityhead">
<i18n:formatDate value="<%=beginDateBefore%>" pattern="MMMMMMMM yyyy"/> - 
<i18n:formatDate value="<%=endDateBefore%>" pattern="MMMMMMMM yyyy"/>
</td>
<td class="cityhead">Change</td>
</tr>

<% String formerSiteName = new String(""); %> 												
<logic:iterate id="result" name="SERVICE_PROVIDER_REPORT_FORM" 
property="resultList" scope="session" type="com.cleanwise.service.api.value.CustomerReportResultData">					
<bean:define id="siteName" name="result" property="siteName" type="String"/>
<% if (! siteName.equals(formerSiteName)) {
formerSiteName = siteName;
%>								
<tr class="jcminheader">
<td width="150" class="rtitle" colspan="5">
<bean:write name="result" property="siteName" filter="true"/>
</td>
</tr>
<% }  %>
							
<tr>
<td class="rtitle">&nbsp;
</td>
<td class="rtitle"><bean:write name="result" property="productName" filter="true"/>
</td>
<td class="rtext">
<bean:define id="amountNow" name="result" property="amountNow" />
<i18n:formatCurrency value="<%=amountNow%>" locale="<%=Locale.US%>"/>
</td>
<td class="rtext">
<bean:define id="amountBefore" name="result" property="amountBefore" />
<i18n:formatCurrency value="<%=amountBefore%>" locale="<%=Locale.US%>"/>
</td>
<td class="rtext">
<bean:define id="amountChange" name="result" property="amountChange" />
<i18n:formatCurrency value="<%=amountChange%>" locale="<%=Locale.US%>"/>
</td>
</tr>
<tr class="top3dk">
<td colspan="5"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
</tr>
</logic:iterate>

<tr>
<td class="rtitle">Totals</td>
<td class="rtitle">&nbsp;</td>
<td class="rtext">
<bean:define id="totalAmount" name="SERVICE_PROVIDER_REPORT_FORM" property="totalAmount" />
<i18n:formatCurrency value="<%=totalAmount%>" locale="<%=Locale.US%>"/>
</td>
<td class="rtext">
<bean:define id="totalAmountBefore" name="SERVICE_PROVIDER_REPORT_FORM" property="totalAmountBefore" />
<i18n:formatCurrency value="<%=totalAmountBefore%>" locale="<%=Locale.US%>"/>
</td>
<td class="rtext">
<bean:define id="totalAmountChange" name="SERVICE_PROVIDER_REPORT_FORM" property="totalAmountChange" />
<i18n:formatCurrency value="<%=totalAmountChange%>" locale="<%=Locale.US%>"/>					
</td>
</tr>
<tr class="top3dk">
<td colspan="5"><img src="<%=IMGPath%>/cw_spacer.gif" height="2" width="1"></td>
</tr>
</table>
</div>
</td>
</tr>
</logic:equal>


<logic:equal name="SERVICE_PROVIDER_REPORT_FORM" property="reportTypeCd" value="<%=RefCodeNames.CUSTOMER_REPORT_TYPE_CD.AVERAGE_ORDER_SIZE%>">									
<% genericReportFl = false; %>
<tr>
<td width="757" valign="top" class="text">
<div class="fivemargin"><img src="<%=IMGPath%>/cw_spacer.gif" height="5"><br>
<div class="subheadergeneric">
<bean:write name="SERVICE_PROVIDER_REPORT_FORM" property="reportTypeCd" filter="true"/>					
</div>
<%=printMsg%>
<table align="center" border="0" cellspacing="0" cellpadding="0" width="730">
<tr class="top3dk" valign="top">
<td class="columntitle" colspan="5">
<bean:write name="SERVICE_PROVIDER_REPORT_FORM" property="reportTypeCd" filter="true"/>					
</td>
</tr>
					
<bean:define id="beginDate" name="SERVICE_PROVIDER_REPORT_FORM" property="beginDate" />
<bean:define id="endDate" name="SERVICE_PROVIDER_REPORT_FORM" property="endDate" />
<bean:define id="beginDateBefore" name="SERVICE_PROVIDER_REPORT_FORM" property="beginDateBefore" />
<bean:define id="endDateBefore" name="SERVICE_PROVIDER_REPORT_FORM" property="endDateBefore" />
	
<tr class="changeshippinglt" valign="top">
<td class="cityhead">Location</td>
<td class="cityhead">&nbsp;</td>
<td class="cityhead">
<i18n:formatDate value="<%=beginDate%>" pattern="MMMMMMMM yyyy"/> - 
<i18n:formatDate value="<%=endDate%>" pattern="MMMMMMMM yyyy"/>
</td>
<td class="cityhead">
<i18n:formatDate value="<%=beginDateBefore%>" pattern="MMMMMMMM yyyy"/> - 
<i18n:formatDate value="<%=endDateBefore%>" pattern="MMMMMMMM yyyy"/>
</td>
<td class="cityhead">Change</td>
</tr>

<% String formerSiteName = new String(""); %> 												
<logic:iterate id="result" name="SERVICE_PROVIDER_REPORT_FORM" property="resultList" scope="session" type="com.cleanwise.service.api.value.CustomerReportResultData">
<bean:define id="siteName" name="result" property="siteName" type="String"/>
<% 
if (! siteName.equals(formerSiteName)) {
formerSiteName = siteName;
%>								
<tr class="jcminheader">
<td width="150" class="rtitle" colspan="5">
<bean:write name="result" property="siteName" filter="true"/>
</td>
</tr>
<% }  %>

<tr>
<td class="rtitle">&nbsp;
</td>
<td class="rtitle">Total Order Amount
</td>
<td class="rtext">
<bean:define id="amountNow" name="result" property="amountNow" />
<i18n:formatCurrency value="<%=amountNow%>" locale="<%=Locale.US%>"/>
</td>
<td class="rtext">
<bean:define id="amountBefore" name="result" property="amountBefore" />
<i18n:formatCurrency value="<%=amountBefore%>" locale="<%=Locale.US%>"/>
</td>
<td class="rtext">
<bean:define id="amountChange" name="result" property="amountChange" />
<i18n:formatCurrency value="<%=amountChange%>" locale="<%=Locale.US%>"/>
</td>
</tr>
<tr class="top3dk">
<td colspan="5"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
</tr>
<tr>
<td class="rtitle">&nbsp;
</td>
<td class="rtitle">
Total Number of Orders
</td>
<td class="rtext">
<bean:write name="result" property="orderNumNow" filter="true"/>
</td>
<td class="rtext">
<bean:write name="result" property="orderNumBefore" filter="true"/>
</td>
<td class="rtext">
<bean:write name="result" property="orderNumChange" filter="true"/>
</td>
</tr>
<tr class="top3dk">
<td colspan="5"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
</tr>
	
<tr>
<td class="rtitle">&nbsp;
</td>
<td class="rtitle">
Average Order Size						
</td>
<td class="rtext">
<bean:define id="sizeNow" name="result" property="avgOrderSizeNow" />
<i18n:formatCurrency value="<%=sizeNow%>" locale="<%=Locale.US%>"/><br>
</td>
<td class="rtext">
<bean:define id="sizeBefore" name="result" property="avgOrderSizeBefore" />
<i18n:formatCurrency value="<%=sizeBefore%>" locale="<%=Locale.US%>"/><br>							
</td>
<td class="rtext">
<bean:define id="sizeChange" name="result" property="avgOrderSizeChange" />
<i18n:formatCurrency value="<%=sizeChange%>" locale="<%=Locale.US%>"/><br>														
</td>
</tr>
<tr class="top3dk">
<td colspan="5"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
</tr>
</logic:iterate>

<tr class="jcminheader">
<td width="150" class="rtitle" colspan="5">
&nbsp;
</td>
</tr>
	
<tr>
<td class="rtitle">Totals</td>
<td class="rtitle">Total Order Amount</td>
<td class="rtext">
<bean:define id="totalAmount" name="SERVICE_PROVIDER_REPORT_FORM" property="totalAmount" />
<i18n:formatCurrency value="<%=totalAmount%>" locale="<%=Locale.US%>"/>
</td>
<td class="rtext">
<bean:define id="totalAmountBefore" name="SERVICE_PROVIDER_REPORT_FORM" property="totalAmountBefore" />
<i18n:formatCurrency value="<%=totalAmountBefore%>" locale="<%=Locale.US%>"/>
</td>
<td class="rtext">
<bean:define id="totalAmountChange" name="SERVICE_PROVIDER_REPORT_FORM" property="totalAmountChange" />
<i18n:formatCurrency value="<%=totalAmountChange%>" locale="<%=Locale.US%>"/>					
</td>
</tr>
<tr class="top3dk">
<td colspan="5"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
</tr>

<tr>
<td class="rtitle">&nbsp;
</td>
<td class="rtitle">
Total Number of Orders
</td>
<td class="rtext">
<bean:write name="SERVICE_PROVIDER_REPORT_FORM" property="totalOrderNum" filter="true"/>
</td>
<td class="rtext">
<bean:write name="SERVICE_PROVIDER_REPORT_FORM" property="totalOrderNumBefore" filter="true"/>
</td>
<td class="rtext">
<bean:write name="SERVICE_PROVIDER_REPORT_FORM" property="totalOrderNumChange" filter="true"/>
</td>
</tr>
<tr class="top3dk">
<td colspan="5"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
</tr>
	
<tr>
<td class="rtitle">&nbsp;
</td>
<td class="rtitle">
Average Order Size						
</td>
<td class="rtext">
<bean:define id="totalSizeNow" name="SERVICE_PROVIDER_REPORT_FORM" property="avgOrderSize" />
<i18n:formatCurrency value="<%=totalSizeNow%>" locale="<%=Locale.US%>"/><br>
</td>
<td class="rtext">
<bean:define id="totalSizeBefore" name="SERVICE_PROVIDER_REPORT_FORM" property="avgOrderSizeBefore" />
<i18n:formatCurrency value="<%=totalSizeBefore%>" locale="<%=Locale.US%>"/><br>							
</td>
<td class="rtext">
<bean:define id="totalSizeChange" name="SERVICE_PROVIDER_REPORT_FORM" property="avgOrderSizeChange" />
<i18n:formatCurrency value="<%=totalSizeChange%>" locale="<%=Locale.US%>"/><br>														
</td>
</tr>					
	
<tr class="top3dk">
<td colspan="5"><img src="<%=IMGPath%>/cw_spacer.gif" height="2" width="1"></td>
</tr>
									
</table>
</div>
</td>
</tr>
</logic:equal>


<logic:match name="SERVICE_PROVIDER_REPORT_FORM" property="reportTypeCd" 
  value="Order Information">									
 <% genericReportFl = false; %>
<jsp:include flush='true' page="f_orderInformation.jsp"/>

</logic:match>


<% if(genericReportFl) { %>
<jsp:include flush='true' page="f_serviceProviderGenericReport.jsp"/>
<% } %>
								
</TBODY>
</TABLE>
</html:form>


