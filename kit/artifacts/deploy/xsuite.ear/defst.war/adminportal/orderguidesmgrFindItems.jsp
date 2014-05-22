<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Locale" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<app:checkLogon/>
<script language="JavaScript1.2">
<!--
function SetChecked(val) {
 dml=document.ORDER_GUIDES_DETAIL_FORM;
 len = dml.elements.length;
 var i=0;
 for( i=0 ; i<len ; i++) {
   if (dml.elements[i].name=='selectItems') {
     dml.elements[i].checked=val;
   }
 }
}

//-->
</script>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Application Administrator Home: Order Guide: Add items</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>

<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/orderguidesToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>

<div class="text">
<center>
<font color=red>
<html:errors/>
</font>
</center>

<table width="769" cellpadding="2" cellspacing="0" border="0" class="mainbody">
<html:form 
name="ORDER_GUIDES_DETAIL_FORM" 
scope="session"
action="adminportal/orderguidesmgrDetail.do"
type="com.cleanwise.view.forms.OrderGuidesMgrDetailForm">

<tr>
<td class="largeheader">Order Guide Detail</td>
</tr>
<tr>
<td><b>Order Guide&nbsp;Id</b> </td>
<td>
<bean:write name="ORDER_GUIDES_DETAIL_FORM" 
property="orderGuideInfoData.orderGuideData.orderGuideId"
/>
<html:hidden name="ORDER_GUIDES_DETAIL_FORM" 
property="orderGuideInfoData.orderGuideData.orderGuideId"/>
</td>
<td><b>Catalog&nbsp;Id</b></td>
<td>
<bean:write 
name="ORDER_GUIDES_DETAIL_FORM" 
property="orderGuideInfoData.orderGuideData.catalogId"
/>
</td>

</tr>
<tr>
 <td><b>Order Guide Name</b> </td>
<td>
<bean:write 
name="ORDER_GUIDES_DETAIL_FORM" 
property="orderGuideInfoData.orderGuideData.shortDesc"
/>
</td>
<td><b>Catalog&nbsp;Name</b></td>
<td>
<bean:write 
name="ORDER_GUIDES_DETAIL_FORM" 
property="catalogName"
/>
</td>
</tr>

<tr>
<td><b>Order Guide&nbsp;Type</b></td>
<td>
<bean:write name="ORDER_GUIDES_DETAIL_FORM" 
property="orderGuideInfoData.orderGuideData.orderGuideTypeCd"/>
<html:hidden name="ORDER_GUIDES_DETAIL_FORM" property="viewMode"/>	  
<html:hidden name="ORDER_GUIDES_DETAIL_FORM" property="contractId"/>	  
<html:hidden name="ORDER_GUIDES_DETAIL_FORM" property="contractName"/>	  
</td>
<td colspan="2">&nbsp;</td>
</tr>
 
</table>

<table width="769" border="1" class="results">
<tr>
<td colspan="11"><b>Order Guide Entries: </b>
<bean:size id="ogsCount" 
name="ORDER_GUIDES_DETAIL_FORM" 
property="orderGuideInfoData.orderGuideItems"
/>
<bean:write name="ogsCount" />
</td>
</tr>
<tr>

<tr>
<td colspan="11" align=right>
<a href="javascript:SetChecked(1)">[Check&nbsp;All]</a>
<a href="javascript:SetChecked(0)">[&nbsp;Clear]</a> <br>
</td>
</tr>

<td><b><a href="orderguidesmgrFindItems.do?action=sortfinditems&sortField=cwSKU">CW #</b></td>
<td><b><a href="orderguidesmgrFindItems.do?action=sortfinditems&sortField=name">Product Name</b></td>
<td><b><a href="orderguidesmgrFindItems.do?action=sortfinditems&sortField=size">Item Size</b></td>
<td><b><a href="orderguidesmgrFindItems.do?action=sortfinditems&sortField=pack">Pack</b></td>
<td><b><a href="orderguidesmgrFindItems.do?action=sortfinditems&sortField=uom">UOM</b></td>
<td><b><a href="orderguidesmgrFindItems.do?action=sortfinditems&sortField=color">Color</b></td>
<td><b><a href="orderguidesmgrFindItems.do?action=sortfinditems&sortField=manufacturer">Manufacturer</b></td>
<td><b><a href="orderguidesmgrFindItems.do?action=sortfinditems&sortField=manufSKU">Manuf. SKU</b></td>
<td><b><a href="orderguidesmgrFindItems.do?action=sortfinditems&sortField=category">Category</b></td>
<td><b><a href="orderguidesmgrFindItems.do?action=sortfinditems&sortField=price">Price</b></td>
<td><b>Select</b></td>
</tr>

<logic:iterate id="itemele" 
indexId="i"
name="ORDER_GUIDES_DETAIL_FORM" 
property="orderGuideItemCollection"
scope="session">

<tr>

<td><bean:write name="itemele" property="cwSKU"/>&nbsp;</td>
<td><bean:write name="itemele" property="shortDesc"/>&nbsp;</td>
<td><bean:write name="itemele" property="sizeDesc"/>&nbsp;</td>
<td><bean:write name="itemele" property="packDesc"/>&nbsp;</td>
<td><bean:write name="itemele" property="uomDesc"/>&nbsp;</td>
<td><bean:write name="itemele" property="colorDesc"/>&nbsp;</td>
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
<tr>
<td align=center colspan=11>
      <html:submit property="action">
        <app:storeMessage  key="admin.button.chose"/>
      </html:submit>
</td>
</tr>

</tr>
</td>

</table>

</html:form>

</div>

<jsp:include flush='true' page="ui/admFooter.jsp"/>
</body>

</html:html>






