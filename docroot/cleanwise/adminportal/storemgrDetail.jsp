
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="Location" value="store" type="java.lang.String" toScope="session"/>
<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Application Administrator Home: Stores</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>

<bean:define id="BusEntityId" type="java.lang.String"
  name="STORE_DETAIL_FORM" property="id"
  toScope="session" />
<bean:define id="BusEntityName" type="java.lang.String"
  name="STORE_DETAIL_FORM" property="name"
  toScope="session" />


  <html:form name="STORE_DETAIL_FORM" action="adminportal/storemgrDetail.do"
scope="session" type="com.cleanwise.view.forms.StoreMgrDetailForm">

<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<logic:notEqual name="STORE_DETAIL_FORM" property="id" value="0">
<jsp:include flush='true' page="ui/admStoreToolbar.jsp"/>
</logic:notEqual>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>

<div class="text">
<center>
<font color=red>
<html:errors/>
</font>
</center>

<table cellspacing="0" border="0" width="769"  class="mainbody">


<tr>
<td class="largeheader" colspan="4">Store Detail</td>
</tr>
<tr>
<td><b>Store&nbsp;Id:</b></td>
<td>&nbsp;
<logic:notEqual name="STORE_DETAIL_FORM" property="id" value="0">
<bean:write name="STORE_DETAIL_FORM" property="id"/>
</logic:notEqual>
<html:hidden property="id"/>
</td>
<td><b>Name:</b></td>
<td>
<html:text name="STORE_DETAIL_FORM" property="name" size="30" maxlength="30"/>
<span class="reqind">*</span>
</td>
</tr>
<tr>
<td><b>Store&nbsp;Prefix:</b></td>
<td>
<bean:write name="STORE_DETAIL_FORM" property="prefix"/>
</td>
<td><b>Description:</b></td>
<td>
<html:text name="STORE_DETAIL_FORM" property="description" size="40" maxlength="80"/>
</td>
</tr>
<%--<tr>
<td><b>Store&nbsp;Prefix&nbsp;New:</b></td>
<td>
<bean:write name="STORE_DETAIL_FORM" property="prefixNew"/>
</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
</tr> --%>
<tr>
<td><b>Store&nbsp;Type:</b></td>
<td>
<html:select name="STORE_DETAIL_FORM" property="typeDesc">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:options  collection="Store.type.vector" property="value" />
</html:select>
<span class="reqind">*</span>
</td>
<td><b>Status:</b></td>
<td>
<html:select name="STORE_DETAIL_FORM" property="statusCd">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:options  collection="Store.status.vector" property="value" />
</html:select>
<span class="reqind">*</span>
</td>
</tr>
<tr>
<td><b>Customer&nbsp;Service&nbsp;Email:</b></td>
<td>
<html:text name="STORE_DETAIL_FORM" property="customerEmail" size="30" maxlength="80"/>
<span class="reqind">*</span>
</td>
<td><b>Default Locale:</b></td>
<td>
<html:select name="STORE_DETAIL_FORM" property="locale">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:options  collection="Store.locale.vector" property="value" />
</html:select>
<span class="reqind">*</span>
</td>
</tr>
<tr>
<td><b>Contact&nbsp;Us&nbsp;Email:</b></td>
<td>
<html:text name="STORE_DETAIL_FORM" property="contactEmail" size="30" maxlength="80"/>
<span class="reqind">*</span>
</td>
</tr>
<tr>
<td><b>Store Business Name</b></td>
<td>
<html:text name="STORE_DETAIL_FORM" property="storeBusinessName" size="30" maxlength="80"/>
<span class="reqind">*</span>
</td>
<td><b>Store Primary Web Address</b></td>
<td>
<html:text name="STORE_DETAIL_FORM" property="storePrimaryWebAddress" size="30" maxlength="80"/>
<span class="reqind">*</span>
</td>
<td colspan="2">&nbsp;</td>
</tr>
<tr>
<td><b>Store Contact Us Page Type</b></td>
<td>
<html:select name="STORE_DETAIL_FORM" property="contactUsType">
  <html:options  collection="contact.type.vector" property="value" />
</html:select>
<span class="reqind">*</span>
</td>
<td><b>Call Hours</b></td>
<td>
  <html:text name="STORE_DETAIL_FORM" property="callHours" size="30" maxlength="30"/>
  <span class="reqind">*</span>
</td>
</tr>
<tr>
<td><b>Even Row Color</b></td>
<td>
  <html:text name="STORE_DETAIL_FORM" property="evenRowColor" size="6"/>
</td>
<td><b>Odd Row Color</b></td>
<td>
  <html:text name="STORE_DETAIL_FORM" property="oddRowColor" size="6"/>
</td>
</tr>


<tr>
<td colspan=4 class="largeheader"><br>Primary Contact</td>
</td>
<tr>
<td><b>First Name:</b></td>
<td>
<html:text name="STORE_DETAIL_FORM" property="name1" maxlength="30"/>
<span class="reqind">*</span>
</td>
<td><b>Phone:</b></td>
<td>
<html:text name="STORE_DETAIL_FORM" property="phone" maxlength="15"/>
<span class="reqind">*</span>
</td>
</tr>
<tr>
<td><b>Last Name:</b></td>
<td>
<html:text name="STORE_DETAIL_FORM" property="name2" maxlength="30"/>
<span class="reqind">*</span>
</td>
<td><b>Fax:</b></td>
<td>
<html:text name="STORE_DETAIL_FORM" property="fax" maxlength="15"/>
<span class="reqind">*</span>
</td>
</tr>
<tr>
<td><b>Street Address 1:</b></td>
<td>
<html:text name="STORE_DETAIL_FORM" property="streetAddr1" maxlength="80"/>
<span class="reqind">*</span>
</td>
<td><b>Email:</b></td>
<td>
<html:text name="STORE_DETAIL_FORM" property="emailAddress" maxlength="40"/>
<span class="reqind">*</span>
</td>
</tr>
<tr>
<td><b>Street Address 2:</b></td>
<td>
<html:text name="STORE_DETAIL_FORM" property="streetAddr2" maxlength="80"/>
</td>
<td><b>Country:</b></td>
<td>
<html:select name="STORE_DETAIL_FORM" property="country">
  <html:option value=""><app:storeMessage  key="admin.select.country"/></html:option>
  <html:options  collection="country.vector" labelProperty="uiName" property="shortDesc" />
</html:select>
<span class="reqind">*</span>
</td>
</tr>
<tr>
<td><b>Street Address 3:</b></td>
<td>
<html:text name="STORE_DETAIL_FORM" property="streetAddr3" maxlength="80"/>
</td>

<td><b>State/Province:</b></td>
<td>
<html:text size="20" maxlength="80" name="STORE_DETAIL_FORM"
  property="stateOrProv" />
<span class="reqind">*</span>
</td>
</tr>
<tr>
<td><b>City:</b></td>
<td>
<html:text name="STORE_DETAIL_FORM" property="city" maxlength="40"/>
<span class="reqind">*</span>
</td>

<td><b>Zip/Postal Code:</b></td>
<td>
<html:text name="STORE_DETAIL_FORM" property="postalCode" maxlength="15"/>
<span class="reqind">*</span>
</td>
</tr>
<tr>
<td colspan='2'>
<html:checkbox name="STORE_DETAIL_FORM" property="cleanwiseOrderNumFlag"/>
<b>Use System Shared Order Number</b>
<BR>
<html:checkbox name="STORE_DETAIL_FORM" property="autoSkuAssign"/>
<b>Automatically Assign Sku Numbers</b>
<BR>
<html:checkbox name="STORE_DETAIL_FORM" property="requireExternalSysLogon"/>
<b>Require that a customer user type (i.e. MSB Customer) logs in through an external system (NOT our logon page)</b>
<BR>
<html:checkbox name="STORE_DETAIL_FORM" property="includeAccountNameInSiteAddress"/>
<b>When sending the site shipping to address include the account name as part of the address</b>
</td>
<td valign="top"><b>Erp System:</b></td>
<td>
<html:select name="STORE_DETAIL_FORM" property="erpSystem">
<html:option value="<%=RefCodeNames.ERP_SYSTEM_CD.CLW_JCI%>"><%=RefCodeNames.ERP_SYSTEM_CD.CLW_JCI%></html:option>
</html:select>
<BR>
<html:checkbox name="STORE_DETAIL_FORM" property="showDistrNotes"/>
<b>Show distributor PO notes to the customer</b>
<BR>
<html:checkbox name="STORE_DETAIL_FORM" property="taxableIndicator"/>
<b>Taxable</b>
<BR>
<html:checkbox name="STORE_DETAIL_FORM" property="orderProcessingSplitTaxExemptOrders"/>
<b>Split taxable and non-taxable orders into 2 seperate orders</b>
</td>
</tr>
<tr>
<td colspan=4 align=center>
<html:submit property="action">
<app:storeMessage  key="global.action.label.save"/>
</html:submit>
<html:reset>
<app:storeMessage  key="admin.button.reset"/>
</html:reset>
<logic:notEqual name="STORE_DETAIL_FORM" property="id" value="0">
<html:submit property="action">
<app:storeMessage  key="global.action.label.delete"/>
</html:submit>
</logic:notEqual>
</td>
</tr>


</table>

</div>

</html:form>

<jsp:include flush='true' page="ui/admFooter.jsp"/>
</body>

</html:html>



