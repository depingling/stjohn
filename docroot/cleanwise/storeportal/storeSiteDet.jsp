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
<jsp:include flush='true' page="locateStoreAccount.jsp">
   <jsp:param name="jspFormAction" 	value="/storeportal/sitedet.do" />
   <jsp:param name="jspFormName" 	value="STORE_ADMIN_SITE_FORM" />
   <jsp:param name="jspSubmitIdent" 	value="" />
   <jsp:param name="jspReturnFilterProperty" value="accountFilter"/>
</jsp:include>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="STORE_ADMIN_SITE_FORM" type="com.cleanwise.view.forms.StoreSiteMgrForm"/>
<bean:define id="Location" value="site" type="java.lang.String" toScope="session"/>
<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
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

<div class="text">

<table ID=1205 cellspacing="0" border="0" width="769"  class="mainbody">
<html:form styleId="1206" name="STORE_ADMIN_SITE_FORM" scope="session" action="storeportal/sitedet.do"
scope="session" type="com.cleanwise.view.forms.StoreSiteMgrForm">
  <html:hidden name="STORE_ADMIN_SITE_FORM" property="oldSiteNumber"/>

<tr>
<%
 AccountData accountD = null;
%>
<td><b>Account&nbsp;Id:</b></td>
<td>
  <logic:equal name="STORE_ADMIN_SITE_FORM" property="accountId" value="0">
  <%
   AccountDataVector accountDV = theForm.getAccountFilter();
   if(accountDV!=null && accountDV.size()>0) {
     accountD = (AccountData) accountDV.get(0);
  %>
      <%=accountD.getBusEntity().getBusEntityId()%>
  <html:hidden name="STORE_ADMIN_SITE_FORM" property="accountId"
             value='<%=""+accountD.getBusEntity().getBusEntityId()%>'/>
  <% } else { %>
   &nbsp;
  <% } %>
  <html:submit property="action" value="Locate Account" styleClass='text' />
  </logic:equal>

  <logic:notEqual name="STORE_ADMIN_SITE_FORM" property="accountId" value="0">
  <html:hidden name="STORE_ADMIN_SITE_FORM" property="accountId"/>
  <bean:write name="STORE_ADMIN_SITE_FORM" property="accountId"/>
  </logic:notEqual>
</td>

<td><b>Account&nbsp;Name:</b></td>
<logic:equal name="STORE_ADMIN_SITE_FORM" property="accountId" value="0">
<td>
<% if(accountD!=null) { %>
  <%=accountD.getBusEntity().getShortDesc()%>
<% } else { %>
  &nbsp;
<% } %>
</td>
</logic:equal>
<logic:notEqual name="STORE_ADMIN_SITE_FORM" property="accountId" value="0">
<td>
<bean:write name="STORE_ADMIN_SITE_FORM" property="accountName"/>
<html:hidden name="STORE_ADMIN_SITE_FORM" property="accountName"/>
</td>
</logic:notEqual>
</tr>

</tr>
<tr>
<td class="largeheader" colspan="4">Site Detail</td>
</tr>
<tr>
<td><b>Site&nbsp;Id:</b> </td>
<td>
<bean:write name="STORE_ADMIN_SITE_FORM" property="id"/>
<html:hidden property="id"/>
</td>
<td><b>Site Name:</b><span class="reqind">*</span></td>
<td>

<html:text name="STORE_ADMIN_SITE_FORM" property="name" size="30" maxlength="30"/>
</td>
</tr>
<tr>
<td><b>Status:</b><span class="reqind">*</span>
 </td>
<td>
<html:select name="STORE_ADMIN_SITE_FORM" property="statusCd">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:options  collection="Site.status.vector" property="value" />
</html:select>
</td>
<td><b>Building Services Contractor:</b></td>
<td>
<html:select name="STORE_ADMIN_SITE_FORM" property="subContractor">
<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
<html:options  collection="list.all.bsc" property="busEntityData.shortDesc" />
</html:select>

</td>
</tr>
<tr>
<td><b>Effective Date:</b> </td>
<td><html:text name="STORE_ADMIN_SITE_FORM" property="effDate" size="30"/></td>
<td><b>Expiration Date:</b></td>
<td><html:text name="STORE_ADMIN_SITE_FORM" property="expDate" size="30" /></td>
</tr>
<tr>
<td><b>Site&nbsp;Budget&nbsp;Reference&nbsp;Number:</b></td>
<td><html:text name="STORE_ADMIN_SITE_FORM" property="siteReferenceNumber" size="30"/></td>
<td><b>Distributor&nbsp;Site&nbsp;Reference&nbsp;Number:</b></td>
<td><html:text name="STORE_ADMIN_SITE_FORM" property="distSiteReferenceNumber" size="30"/></td>
</tr>
<tr>
<td><b>Site Erp Number:</b></td>
<td colspan="3">
<bean:write name="STORE_ADMIN_SITE_FORM" property="siteNumber"/>
<html:hidden name="STORE_ADMIN_SITE_FORM" property="siteNumber" value=""/>
</td>
</tr>

<tr>
<td><b>Taxable:</b></td>
<td>
        Yes&nbsp;<html:radio name="STORE_ADMIN_SITE_FORM" property="taxableIndicator" value="Y"/>
        &nbsp;&nbsp;&nbsp;
        No&nbsp;<html:radio name="STORE_ADMIN_SITE_FORM" property="taxableIndicator" value="N"/>
</td>
<td colspan=2>
<html:checkbox name="STORE_ADMIN_SITE_FORM" property="inventoryShopping" />
<b>Enable Inventory Shopping</b>
</td>
</tr>

<%--
<tr>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td colspan=2>

<html:checkbox name="STORE_ADMIN_SITE_FORM" property="inventoryShoppingType" />
<b>Inventory Shopping(Modern/Classic)</b>

</td>
</tr>

<tr>
<td>&nbsp;</td>
<td>&nbsp;</td>
<td colspan=2>

<html:checkbox name="STORE_ADMIN_SITE_FORM" property="inventoryShoppingHoldOrderUntilDeliveryDate" />
<b>Hold processed order until delivery date</b></td>

</tr>
--%>

<tr>
        <td><b>Target Facility Rank:</b></td>
        <td><html:text name="STORE_ADMIN_SITE_FORM" property="targetFacilityRank" size="10" maxlength="30"/></td>
        <td colspan=2><html:checkbox name="STORE_ADMIN_SITE_FORM" property="bypassOrderRouting"/><b>Bypass Order Routing</b></td>
</tr>
<tr>
        <td><b>Site Line Level Code:</b></td>
        <td><html:text name="STORE_ADMIN_SITE_FORM" property="lineLevelCode" size="10" maxlength="30"/></td>
        <td colspan=2>
    <html:checkbox name="STORE_ADMIN_SITE_FORM" property="consolidatedOrderWarehouse"/>
    <b>Consolidated Order Warehouse:</b>
  </td>
</tr>

<tr>
        <td><b>&nbsp;</b></td>
        <td>&nbsp;</td>
        <td colspan=2>
    	<html:checkbox name="STORE_ADMIN_SITE_FORM" property="reBill"/>
    	<b>Automatic Rebill</b>
  </td>
</tr>
<tr>
        <td><b>&nbsp;</b></td>
        <td>&nbsp;</td>
        <td colspan=2>
    	<html:checkbox name="STORE_ADMIN_SITE_FORM" property="allowCorpSchedOrder"/>
    	<b>Allow Corporate Scheduled Order</b>
  </td>
</tr>


<logic:present name="Site.account.blanketPos" >
<tr>
  <td colspan="4"><b>Blanket Po Number:</b></td>
</tr>
  <td colspan="4">
    None: <html:radio name="STORE_ADMIN_SITE_FORM" property="blanketPoNumId" value="0" />
  <logic:iterate id="bpo" name="Site.account.blanketPos">
    <bean:define id="bpoid" name="bpo" property="blanketPoNumId" type="java.lang.Integer"/>
    <bean:write name="bpo" property="shortDesc"/> (<bean:write name="bpo" property="poNumber"/>):
    <html:radio name="STORE_ADMIN_SITE_FORM" property="blanketPoNumId" value="<%=bpoid.toString()%>" />
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
  <td><html:text name="STORE_ADMIN_SITE_FORM" property="f1Value" size="30"/></td>
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
  <td><html:text name="STORE_ADMIN_SITE_FORM" property="f2Value" size="30"/></td>
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
  <td><html:text name="STORE_ADMIN_SITE_FORM" property="f3Value" size="30"/></td>
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
  <td><html:text name="STORE_ADMIN_SITE_FORM" property="f4Value" size="30"/></td>
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
  <td><html:text name="STORE_ADMIN_SITE_FORM" property="f5Value" size="30"/></td>
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
  <td><html:text name="STORE_ADMIN_SITE_FORM" property="f6Value" size="30"/></td>
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
  <td><html:text name="STORE_ADMIN_SITE_FORM" property="f7Value" size="30"/></td>
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
  <td><html:text name="STORE_ADMIN_SITE_FORM" property="f8Value" size="30"/></td>
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
  <td><html:text name="STORE_ADMIN_SITE_FORM" property="f9Value" size="30"/></td>
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
  <td><html:text name="STORE_ADMIN_SITE_FORM" property="f10Value" size="30"/></td>
</logic:equal>
</tr>
</logic:present>

<tr>
<td><b>Share buyer order guides:</b></td>
<td>
        Yes&nbsp;<html:radio name="STORE_ADMIN_SITE_FORM" property="shareBuyerOrderGuides" value="true"/>
        &nbsp;&nbsp;&nbsp;
        No&nbsp;<html:radio name="STORE_ADMIN_SITE_FORM" property="shareBuyerOrderGuides" value="false"/>
</td>
    <td><b>Product Bundle:</b></td>
    <td><b>
        <html:select name="STORE_ADMIN_SITE_FORM" property="productBundle">
        <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
        <html:options  collection="store.site.productbundle.vector" property="value"  />
    </html:select>
    </b></td>
</tr>

<% String tdHeight = "24"; %>
<tr>
<td colspan="4" class="largeheader"><br>Address</td>
</td>
</tr>
<tr>
  <td valign="top"><!-- headers1 -->
    <table ID=1207 cellpadding=0 cellspacing=0 border="0">
      <tr><td height="<%=tdHeight%>"><b>First Name:</b></td></tr>
      <tr><td height="<%=tdHeight%>"><b>Last Name:</b></td></tr>
      <tr><td height="<%=tdHeight%>"><b>Street Address 1:</b><span class="reqind">*</span></td></tr>
      <tr><td height="<%=tdHeight%>"><b>Street Address 2:</b></td></tr>
      <tr><td height="<%=tdHeight%>"><b>Street Address 3:</b></td></tr>
      <tr><td height="<%=tdHeight%>"><b>Street Address 4:</b></td></tr>
      <tr><td height="<%=tdHeight%>"><b>City:</b><span class="reqind">*</span></td></tr>
    </table>
  </td>
  <td valign="top"><!-- fields1 -->
    <table ID=1208 cellpadding=0 cellspacing=0 border="0">
      <tr><td height="<%=tdHeight%>"><html:text name="STORE_ADMIN_SITE_FORM" property="name1" size="40"/></td></tr>
      <tr><td height="<%=tdHeight%>"><html:text name="STORE_ADMIN_SITE_FORM" property="name2" size="40"/></td></tr>
      <tr><td height="<%=tdHeight%>"><html:text name="STORE_ADMIN_SITE_FORM" property="streetAddr1" size="30" maxlength="40"/></td></tr>
      <tr><td height="<%=tdHeight%>"><html:text name="STORE_ADMIN_SITE_FORM" property="streetAddr2" size="30" maxlength="40"/></td></tr>
      <tr><td height="<%=tdHeight%>"><html:text name="STORE_ADMIN_SITE_FORM" property="streetAddr3" size="30" maxlength="40"/></td></tr>
      <tr><td height="<%=tdHeight%>"><html:text name="STORE_ADMIN_SITE_FORM" property="streetAddr4" size="30" maxlength="40"/></td></tr>
      <tr><td height="<%=tdHeight%>"><html:text name="STORE_ADMIN_SITE_FORM" property="city" maxlength="40"/></td></tr>
    </table>
  </td>
  <td valign="top"><!-- headers2 -->
    <table ID=1209 cellpadding=0 cellspacing=0 border="0">
      <tr><td height="<%=tdHeight%>">&nbsp;</td></tr>
      <tr><td height="<%=tdHeight%>">&nbsp;</td></tr>
      <tr><td height="<%=tdHeight%>"><b>Phone:</b></td></tr>
      <tr><td height="<%=tdHeight%>"><b>State/Province:</b><span class="reqind">*</span></td></tr>
      <tr><td height="<%=tdHeight%>"><b>Zip/Postal Code:</b><span class="reqind">*</span></td></tr>
      <tr><td height="<%=tdHeight%>">&nbsp;</td></tr>
      <tr><td height="<%=tdHeight%>"><b>Country:</b><span class="reqind">*</span></td></tr>
    </table>
  </td>
  <td valign="top"><!-- fields2 -->
    <table ID=1210 cellpadding=0 cellspacing=0 border="0">
      <tr><td height="<%=tdHeight%>">&nbsp;</td></tr>
      <tr><td height="<%=tdHeight%>">&nbsp;</td></tr>
      <tr><td height="<%=tdHeight%>"><html:text name="STORE_ADMIN_SITE_FORM" property="phone" maxlength="15"/></td></tr>
      <tr><td height="<%=tdHeight%>"><html:text size="20" maxlength="20" name="STORE_ADMIN_SITE_FORM"  property="stateOrProv"/></td></tr>
      <tr><td height="<%=tdHeight%>"><html:text name="STORE_ADMIN_SITE_FORM" property="postalCode"/></td></tr>
      <tr><td height="<%=tdHeight%>">&nbsp;</td></tr>
      <tr>
        <td height="<%=tdHeight%>">
          <html:select name="STORE_ADMIN_SITE_FORM" property="country">
            <html:option value=""><app:storeMessage  key="admin.select.country"/></html:option>
            <html:options  collection="country.vector" labelProperty="uiName" property="shortDesc" />
          </html:select>
        </td>
      </tr>
    </table>
  </td>
</tr>

<tr>
<td><b>County:</b></td>
<td>
<!--
<html:text name="STORE_ADMIN_SITE_FORM" property="county" size="50" readonly="true" styleClass="textreadonly"/>
-->
<bean:write name="STORE_ADMIN_SITE_FORM" property="county"/>
</td>
</tr>
<tr>
<td><b>Order Guide Comments:</b> </td>
<td colspan=4>
<html:textarea name="STORE_ADMIN_SITE_FORM" cols="60"
  property="comments"/>
</td>
</tr>
<tr>
<td><b>Shipping Message:</b> </td>
<td colspan=4>
<html:textarea name="STORE_ADMIN_SITE_FORM" cols="60"
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
<logic:notEqual name="STORE_ADMIN_SITE_FORM" property="id" value="0">
<html:submit property="action">
<app:storeMessage  key="global.action.label.delete"/>
</html:submit>
&nbsp;&nbsp;&nbsp;<html:submit property="action">
<app:storeMessage  key="admin.button.createClone"/>
</html:submit>
</logic:notEqual>
</td>
</tr>

</html:form>

</table>

</div>

</body>

</html:html>


