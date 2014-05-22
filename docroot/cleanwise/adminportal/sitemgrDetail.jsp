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
<bean:define id="theForm" name="SITE_DETAIL_FORM" type="com.cleanwise.view.forms.SiteMgrDetailForm"/>
<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Application Administrator Home: Sites</title>
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

<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<logic:notEqual name="SITE_DETAIL_FORM" property="id" value="0">
    <jsp:include flush='true' page="ui/admSiteToolbar.jsp"/>
</logic:notEqual>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>

<div class="text">
<center>
<font color=red>
<html:errors/>
</font>
</center>

<table cellspacing="0" border="0" width="769"  class="mainbody">
<html:form name="SITE_DETAIL_FORM" scope="session"
action="adminportal/sitemgrDetail.do"
scope="session" type="com.cleanwise.view.forms.SiteMgrDetailForm">
  <html:hidden name="SITE_DETAIL_FORM" property="oldSiteNumber"/>

<tr>
<td><b>Account&nbsp;Id:</b></td>
<td>
  <logic:equal name="SITE_DETAIL_FORM" property="id" value="0">
  <html:text name="SITE_DETAIL_FORM" property="accountId" size="5"/>
  <font color="red">*</font>
  <html:button
    onclick="return popLocate('accountlocate', 'accountId', 'accountName');"
    value="Locate Account" property="action"
  />
  </logic:equal>

  <logic:notEqual name="SITE_DETAIL_FORM" property="id" value="0">
  <html:hidden name="SITE_DETAIL_FORM" property="accountId"/>
  <bean:write name="SITE_DETAIL_FORM" property="accountId"/>
  </logic:notEqual>
</td>

<td><b>Account&nbsp;Name:</b></td>
<logic:equal name="SITE_DETAIL_FORM" property="id" value="0">
<td>
<html:text name="SITE_DETAIL_FORM" property="accountName" readonly="true" styleClass="mainbodylocatename"/>
</td>
</logic:equal>
<logic:notEqual name="SITE_DETAIL_FORM" property="id" value="0">
<td>
<bean:write name="SITE_DETAIL_FORM" property="accountName"/>
<html:hidden name="SITE_DETAIL_FORM" property="accountName"/>
</td>
</logic:notEqual>

<logic:notEqual name="SITE_DETAIL_FORM" property="id" value="0">
<tr>
<td><b>Store&nbsp;Id:</b></td>
<td>
<bean:write name="SITE_DETAIL_FORM" property="storeId"/>
<html:hidden name="SITE_DETAIL_FORM" property="storeId"/>
</td>
<td><b>Store&nbsp;Name:</b></td>
<td>
<bean:write name="SITE_DETAIL_FORM" property="storeName"/>
<html:hidden name="SITE_DETAIL_FORM" property="storeName"/>
</td>
</tr>

</logic:notEqual>



</tr>
<tr>
<td class="largeheader" colspan="4">Site Detail</td>
</tr>
<tr>
<td><b>Site&nbsp;Id:</b> </td>
<td>
<bean:write name="SITE_DETAIL_FORM" property="id"/>
<html:hidden property="id"/>
</td>
<td><b>Site Name:</b><span class="reqind">*</span></td>
<td>
<html:text name="SITE_DETAIL_FORM" property="name" size="30" maxlength="30"/>

</td>
</tr>
<tr>
<td><b>Status:</b><span class="reqind">*</span>
 </td>
<td>
<html:select name="SITE_DETAIL_FORM" property="statusCd">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:options  collection="Site.status.vector" property="value" />
</html:select>
</td>
<td><b>Building Services Contractor:</b></td>
<td>
<html:select name="SITE_DETAIL_FORM" property="subContractor">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:options  collection="list.all.bsc" property="busEntityData.shortDesc" />
<html:option value="-1">-- No Contractor --</html:option>
</html:select>

</td>
</tr>
<tr>
<td><b>Effective Date:</b> </td>
<td><html:text name="SITE_DETAIL_FORM" property="effDate" size="30"/></td>
<td><b>Expiration Date:</b></td>
<td><html:text name="SITE_DETAIL_FORM" property="expDate" size="30"/></td>
</tr>
<tr>
<td><b>Site&nbsp;Budget&nbsp;Reference&nbsp;Number:</b></td>
<td><html:text name="SITE_DETAIL_FORM" property="siteReferenceNumber" size="30"/></td>
<td>&nbsp;</td>
<td>&nbsp;</td>
</tr>
<% if(theForm.isERPEnabled()) { %>
<tr>
<td><b>Site&nbsp;Number:</b></td>
<td colspan="3">
<html:text name="SITE_DETAIL_FORM" property="siteNumber" size="10" maxlength="30"/>
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
<html:text name="SITE_DETAIL_FORM" property="siteNumber" size="10" maxlength="30"/>
</td>
</tr>
<% } %>

<tr>
<td><b>Taxable:</b></td>
<td>
        Yes&nbsp;<html:radio name="SITE_DETAIL_FORM" property="taxableIndicator" value="Y"/>
        &nbsp;&nbsp;&nbsp;
        No&nbsp;<html:radio name="SITE_DETAIL_FORM" property="taxableIndicator" value="N"/>
</td>
<td colspan=2>
<html:checkbox name="SITE_DETAIL_FORM" property="inventoryShopping" />
<b>Enable Inventory Shopping/Shared cart</b></td>
</tr>
<tr>
        <td><b>Target Facility Rank:</b></td>
        <td><html:text name="SITE_DETAIL_FORM" property="targetFacilityRank" size="10" maxlength="30"/></td>
        <td colspan=2><html:checkbox name="SITE_DETAIL_FORM" property="bypassOrderRouting"/><b>Bypass Order Routing</b></td>
</tr>
<tr>
        <td><b>Site Line Level Code:</b></td>
        <td><html:text name="SITE_DETAIL_FORM" property="lineLevelCode" size="10" maxlength="30"/></td>
        <td colspan=2>
		<html:checkbox name="SITE_DETAIL_FORM" property="consolidatedOrderWarehouse"/>
		<b>Consolidated Order Warehouse:</b>
	</td>
</tr>


<logic:present name="Site.account.blanketPos" >
<tr>
  <td colspan="4"><b>Blanket Po Number:</b></td>
</tr>
  <td colspan="4">
  	None: <html:radio name="SITE_DETAIL_FORM" property="blanketPoNumId" value="0" />
	<logic:iterate id="bpo" name="Site.account.blanketPos">
		<bean:define id="bpoid" name="bpo" property="blanketPoNumId" type="java.lang.Integer"/>		
		<bean:write name="bpo" property="shortDesc"/> (<bean:write name="bpo" property="poNumber"/>):
		<html:radio name="SITE_DETAIL_FORM" property="blanketPoNumId" value="<%=bpoid.toString()%>" />
	</logic:iterate>
  </td>
</tr>
</logic:present>

<logic:present name="Site.account.fields">
<tr>
<logic:equal name="Site.account.fields" property="f1ShowAdmin" value="true">
  <td><b><bean:write name="Site.account.fields" property="f1Tag" /></b>
    <logic:equal name="Site.account.fields" property="f1Required" value="true">
       <span class="reqind">*</span>
    </logic:equal>
  </td>
  <td><html:text name="SITE_DETAIL_FORM" property="f1Value" size="30"/></td>
</logic:equal>
<logic:notEqual name="Site.account.fields" property="f1ShowAdmin" value="true">
  <td colspan=2>&nbsp;</td>
</logic:notEqual>
<logic:equal name="Site.account.fields" property="f2ShowAdmin" value="true">
  <td><b><bean:write name="Site.account.fields" property="f2Tag" /></b>
    <logic:equal name="Site.account.fields" property="f2Required" value="true">
       <span class="reqind">*</span>
    </logic:equal>
  </td>
  <td><html:text name="SITE_DETAIL_FORM" property="f2Value" size="30"/></td>
</logic:equal>
</tr>
</logic:present>


<logic:present name="Site.account.fields">
<tr>
<logic:equal name="Site.account.fields" property="f3ShowAdmin" value="true">
  <td><b><bean:write name="Site.account.fields" property="f3Tag" /></b>
    <logic:equal name="Site.account.fields" property="f3Required" value="true">
       <span class="reqind">*</span>
    </logic:equal>
  </td>
  <td><html:text name="SITE_DETAIL_FORM" property="f3Value" size="30"/></td>
</logic:equal>
<logic:notEqual name="Site.account.fields" property="f3ShowAdmin" value="true">
  <td colspan=2>&nbsp;</td>
</logic:notEqual>
<logic:equal name="Site.account.fields" property="f4ShowAdmin" value="true">
  <td><b><bean:write name="Site.account.fields" property="f4Tag" /></b>
    <logic:equal name="Site.account.fields" property="f4Required" value="true">
       <span class="reqind">*</span>
    </logic:equal>
  </td>
  <td><html:text name="SITE_DETAIL_FORM" property="f4Value" size="30"/></td>
</logic:equal>
</tr>
</logic:present>

<logic:present name="Site.account.fields">
<tr>
<logic:equal name="Site.account.fields" property="f5ShowAdmin" value="true">
  <td><b><bean:write name="Site.account.fields" property="f5Tag" /></b>
    <logic:equal name="Site.account.fields" property="f5Required" value="true">
       <span class="reqind">*</span>
    </logic:equal>
  </td>
  <td><html:text name="SITE_DETAIL_FORM" property="f5Value" size="30"/></td>
</logic:equal>
<logic:notEqual name="Site.account.fields" property="f5ShowAdmin" value="true">
  <td colspan=2>&nbsp;</td>
</logic:notEqual>
<logic:equal name="Site.account.fields" property="f6ShowAdmin" value="true">
  <td><b><bean:write name="Site.account.fields" property="f6Tag" /></b>
    <logic:equal name="Site.account.fields" property="f6Required" value="true">
       <span class="reqind">*</span>
    </logic:equal>
  </td>
  <td><html:text name="SITE_DETAIL_FORM" property="f6Value" size="30"/></td>
</logic:equal>
</tr>
</logic:present>

<logic:present name="Site.account.fields">
<tr>
<logic:equal name="Site.account.fields" property="f7ShowAdmin" value="true">
  <td><b><bean:write name="Site.account.fields" property="f7Tag" /></b>
    <logic:equal name="Site.account.fields" property="f7Required" value="true">
       <span class="reqind">*</span>
    </logic:equal>
  </td>
  <td><html:text name="SITE_DETAIL_FORM" property="f7Value" size="30"/></td>
</logic:equal>
<logic:notEqual name="Site.account.fields" property="f7ShowAdmin" value="true">
  <td colspan=2>&nbsp;</td>
</logic:notEqual>
<logic:equal name="Site.account.fields" property="f8ShowAdmin" value="true">
  <td><b><bean:write name="Site.account.fields" property="f8Tag" /></b>
    <logic:equal name="Site.account.fields" property="f8Required" value="true">
       <span class="reqind">*</span>
    </logic:equal>
  </td>
  <td><html:text name="SITE_DETAIL_FORM" property="f8Value" size="30"/></td>
</logic:equal>
</tr>
</logic:present>

<logic:present name="Site.account.fields">
<tr>
<logic:equal name="Site.account.fields" property="f9ShowAdmin" value="true">
  <td><b><bean:write name="Site.account.fields" property="f9Tag" /></b>
    <logic:equal name="Site.account.fields" property="f9Required" value="true">
       <span class="reqind">*</span>
    </logic:equal>
  </td>
  <td><html:text name="SITE_DETAIL_FORM" property="f9Value" size="30"/></td>
</logic:equal>
<logic:notEqual name="Site.account.fields" property="f9ShowAdmin" value="true">
  <td colspan=2>&nbsp;</td>
</logic:notEqual>
<logic:equal name="Site.account.fields" property="f10ShowAdmin" value="true">
  <td><b><bean:write name="Site.account.fields" property="f10Tag" /></b>
    <logic:equal name="Site.account.fields" property="f10Required" value="true">
       <span class="reqind">*</span>
    </logic:equal>
  </td>
  <td><html:text name="SITE_DETAIL_FORM" property="f10Value" size="30"/></td>
</logic:equal>
</tr>
</logic:present>



<tr>
<td colspan="4" class="largeheader"><br>Address</td>
</td>
</tr>

<tr>
<td><b>First Name:</b><span class="reqind">*</span>
 </td>
<td>
<html:text name="SITE_DETAIL_FORM" property="name1" size="40"/>
</td>
<td colspan="2">&nbsp;</td>
</tr>
<tr>
<td><b>Last Name:</b><span class="reqind">*</span>
 </td>
<td>
<html:text name="SITE_DETAIL_FORM" property="name2" size="40"/>
</td>
<td colspan="2">&nbsp;</td>
</tr>
<tr>
<td><b>Street Address 1:</b><span class="reqind">*</span>
</td>
<td>
<html:text name="SITE_DETAIL_FORM" property="streetAddr1" size="30" maxlength="40"/>
</td>
<td colspan="2">&nbsp;</td>
</tr>

<tr>
<td><b>Street Address 2:</b></td>
<td>
<html:text name="SITE_DETAIL_FORM" property="streetAddr2" size="30" maxlength="40"/>
</td>
<td><b>Country:</b><span class="reqind">*</span>
 </td>
<td>
<html:select name="SITE_DETAIL_FORM" property="country">
<html:option value=""><app:storeMessage  key="admin.select.country"/></html:option>
<html:options  collection="countries.vector" property="value" />
</html:select>
</td>
</tr>
<tr>
<td><b>Street Address 3:</b></td>
<td>
<html:text name="SITE_DETAIL_FORM" property="streetAddr3" size="30" maxlength="40"/>
</td>

<td><b>State/Province:</b><span class="reqind">*</span>
 </td>
<td>
<html:text size="20" maxlength="20" name="SITE_DETAIL_FORM"
  property="stateOrProv" />
</td>
</tr>
<tr>
<td><b>Street Address 4:</b></td>
<td>
<html:text name="SITE_DETAIL_FORM" property="streetAddr4" size="30" maxlength="40"/>
</td>
<td colspan="2"> </td>
</tr>
<tr>
<td><b>City:</b><span class="reqind">*</span>
</td>
<td>
<html:text name="SITE_DETAIL_FORM" property="city" maxlength="40"/>
</td>
<td><b>Zip/Postal Code:</b><span class="reqind">*</span>
 </td>
<td>
<html:text name="SITE_DETAIL_FORM" property="postalCode"/>
</td>
</tr>
<tr>
<td><b>County:</b></td>
<td>
<!--
<html:text name="SITE_DETAIL_FORM" property="county" size="50" readonly="true" styleClass="textreadonly"/>
-->
<bean:write name="SITE_DETAIL_FORM" property="county"/>
</td>
</tr>
<tr>
<td><b>Order Guide Comments:</b> </td>
<td colspan=4>
<html:textarea name="SITE_DETAIL_FORM" cols="60"
  property="comments"/>
</td>
</tr>
<tr>
<td><b>Shipping Message:</b> </td>
<td colspan=4>
<html:textarea name="SITE_DETAIL_FORM" cols="60"
  property="shippingMessage"/>
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
<logic:notEqual name="SITE_DETAIL_FORM" property="id" value="0">
<html:submit property="action">
<app:storeMessage  key="global.action.label.delete"/>
</html:submit>
</logic:notEqual>
</td>
</tr>

</html:form>

</table>

</div>

<jsp:include flush='true' page="ui/admFooter.jsp"/>

</body>

</html:html>






