
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Locale" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="Location" value="orderguide" type="java.lang.String" toScope="session"/>
<bean:define id="catalogkey" name="STORE_SITE_ORDER_GUIDE_FORM" property="orderGuideInfoData.orderGuideData.catalogId"/>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Order Guides</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<script language="JavaScript1.2">
<!--
function popLocate(pLoc, name, pDesc) {
  var loc = pLoc + ".do?reload=viewbycontract&catalogId=<%=catalogkey%>&feedField=" + name + "&amp;feedDesc=" + pDesc;
  locatewin = window.open(loc,"Locate", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();
  return false;
}

function SetChecked(val) {
 dml=document.forms["STORE_SITE_ORDER_GUIDE_FORM"];
 len = dml.elements.length;
 var i=0;
//alert('num of elements:' + len);
 for( i=0 ; i<len ; i++) {
   if (dml.elements[i].name == "selectItems") {
     dml.elements[i].checked=val;
   }
 }
}

//-->
</script>

<div class="text">

<table ID=1272 width="769" cellpadding="2" cellspacing="0" border="0" class="mainbody">
<html:form
name="STORE_SITE_ORDER_GUIDE_FORM"
scope="session"
action="storeportal/orderguidedet.do"
type="com.cleanwise.view.forms.StoreSiteOrderGuideDetForm">

<tr><td colspan="4"><b>Site Id:</b> <bean:write name="STORE_SITE_ORDER_GUIDE_FORM" property="siteId"/></td></tr>
<tr>
<td colspan="4" class="largeheader">Order Guide Detail</td>

</tr>
<tr>
<td><b>Order Guide&nbsp;Id:</b> </td>
<td>
<bean:write name="STORE_SITE_ORDER_GUIDE_FORM"
property="orderGuideInfoData.orderGuideData.orderGuideId"
/>
<html:hidden name="STORE_SITE_ORDER_GUIDE_FORM"
property="orderGuideInfoData.orderGuideData.orderGuideId"/>
</td>
<td><b>Catalog&nbsp;Id:</b></td>
<td>
<bean:write
name="STORE_SITE_ORDER_GUIDE_FORM"
property="orderGuideInfoData.orderGuideData.catalogId"
/>
</td>

</tr>
<tr>
 <td><b>Order Guide Name:</b> </td>
<td>
<html:text name="STORE_SITE_ORDER_GUIDE_FORM"
property="orderGuideInfoData.orderGuideData.shortDesc"
size="30" maxlength="30"/>
</td>
<td><b>Catalog&nbsp;Name:</b></td>
<td>
<bean:write
name="STORE_SITE_ORDER_GUIDE_FORM"
property="catalogName"
/>
</td>
</tr>

<tr>
<td><b>Order Guide&nbsp;Type:</b></td>
<td>
<html:select
 name="STORE_SITE_ORDER_GUIDE_FORM"
 property="orderGuideInfoData.orderGuideData.orderGuideTypeCd">
<html:option value="<%=RefCodeNames.ORDER_GUIDE_TYPE_CD.BUYER_ORDER_GUIDE%>">
<%=RefCodeNames.ORDER_GUIDE_TYPE_CD.BUYER_ORDER_GUIDE%>
</html:option>
<html:option value="<%=RefCodeNames.ORDER_GUIDE_TYPE_CD.SITE_ORDER_GUIDE_TEMPLATE%>">
<%=RefCodeNames.ORDER_GUIDE_TYPE_CD.SITE_ORDER_GUIDE_TEMPLATE%>
</html:option>
</html:select>
</td>

</tr>

<tr>
    <td colspan="4" align="center">
      <html:submit property="action">
        <app:storeMessage  key="global.action.label.save"/>
      </html:submit>
      <html:submit property="action">
        <app:storeMessage  key="global.action.label.delete"/>
      </html:submit>
        <html:hidden name="STORE_SITE_ORDER_GUIDE_FORM" property="viewMode"/>
        <html:hidden name="STORE_SITE_ORDER_GUIDE_FORM" property="contractId" />
        <html:hidden name="STORE_SITE_ORDER_GUIDE_FORM" property="contractName" />
    </td>
</tr>
</table>



<table ID=1273 width="769" border="0" class="results">
<tr>
<td colspan="7"><b>Order Guide Entries: </b>
<bean:size id="ogsCount"
name="STORE_SITE_ORDER_GUIDE_FORM"
property="orderGuideInfoData.orderGuideItems"
/>
<bean:write name="ogsCount" />
</td>
<td colspan="6" align="right"><b>Total Amount: </b>
<bean:write name="STORE_SITE_ORDER_GUIDE_FORM" property="orderGuideInfoData.totalAmount" />
</td>
</tr>

<tr>
<td><a ID=1274 class="tableheader" href="orderguidedet.do?action=sortitems&sortField=quantity">Quantity</td>
<td><a ID=1275 class="tableheader" href="orderguidedet.do?action=sortitems&sortField=amount">Extended Price</td>
<td><a ID=1276 class="tableheader" href="orderguidedet.do?action=sortitems&sortField=cwSKU">SKU</td>
<td><a ID=1277 class="tableheader" href="orderguidedet.do?action=sortitems&sortField=name">Product&nbsp;Name</td>
<td><a ID=1278 class="tableheader" href="orderguidedet.do?action=sortitems&sortField=size">Item&nbsp;Size</td>
<td><a ID=1279 class="tableheader" href="orderguidedet.do?action=sortitems&sortField=pack">Pack</td>
<td><a ID=1280 class="tableheader" href="orderguidedet.do?action=sortitems&sortField=uom">UOM</td>
<td><a ID=1281 class="tableheader" href="orderguidedet.do?action=sortitems&sortField=manufacturer">Mfg.</td>
<td><a ID=1282 class="tableheader" href="orderguidedet.do?action=sortitems&sortField=manufSKU"></b>Mfg.&nbsp;SKU</td>
<td><a ID=1283 class="tableheader" href="orderguidedet.do?action=sortitems&sortField=category">Category</td>
<td><a ID=1284 class="tableheader" href="orderguidedet.do?action=sortitems&sortField=price">MSRP</td>


<td class="tableheader">
<a ID=1285 href="javascript:SetChecked(1);">[Check&nbsp;All]</a>
<a ID=1286 href="javascript:SetChecked(0);">[Clear]</a>
<br>
Select</td>
</tr>

<logic:iterate id="itemele"
indexId="i"
name="STORE_SITE_ORDER_GUIDE_FORM"
property="orderGuideItemCollection"
scope="session">

<%
String lrc;
if ( ( i.intValue() % 2 ) == 0 ) {
  lrc = "rowa";
}
else {
  lrc = "rowb";
}
%>

<tr class="<%=lrc%>">


<td>

<html:hidden
name="STORE_SITE_ORDER_GUIDE_FORM"
property='<%= "orderGuideItemDesc[" + i + "].itemId" %>'/>

<html:text size="3" maxlength="6"
name="STORE_SITE_ORDER_GUIDE_FORM"
property='<%= "orderGuideItemDesc[" + i + "].quantity" %>'/>
</td>

<td><bean:write name="itemele" property="amount"/></td>
<td><bean:write name="itemele" property="cwSKU"/>&nbsp;</td>
<td><bean:write name="itemele" property="shortDesc"/>&nbsp;</td>
<td><bean:write name="itemele" property="sizeDesc"/>&nbsp;</td>
<td><bean:write name="itemele" property="packDesc"/>&nbsp;</td>
<td><bean:write name="itemele" property="uomDesc"/>&nbsp;</td>
<!--
<td><bean:write name="itemele" property="colorDesc"/>&nbsp;</td>
-->
<td><bean:write name="itemele" property="manufacturerCd"/>&nbsp;</td>
<td><bean:write name="itemele" property="manufacturerSKU"/>&nbsp;</td>
<td><bean:write name="itemele" property="categoryDesc"/>&nbsp;</td>
<bean:define id="price" name="itemele" property="price"/>
<td><i18n:formatCurrency value="<%=price%>" locale="<%=Locale.US%>"/></td>

<td>
<html:multibox property="selectItems">
  <bean:write name="itemele" property="itemId"/>
</html:multibox>
</td>
</tr>

</logic:iterate>

<script>
//SetChecked(1);
</script>
</html:form>

</table>

</div>







