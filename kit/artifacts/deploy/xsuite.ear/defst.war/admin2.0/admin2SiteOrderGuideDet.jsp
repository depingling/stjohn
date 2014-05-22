
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
<bean:define id="catalogkey" name="ADMIN2_SITE_ORDER_GUIDE_FORM" property="orderGuideInfoData.orderGuideData.catalogId"/>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Order Guides</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<script language="JavaScript1.2">
<!--
function SetChecked(val) {
 dml=document.forms["ADMIN2_SITE_ORDER_GUIDE_FORM"];
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
<jsp:include flush='true' page="/storeportal/locateStoreItem.jsp">
   <jsp:param name="jspFormAction" 	value="/admin2.0/admin2OrderGuideDet.do" />
   <jsp:param name="jspFormName" 	value="ADMIN2_SITE_ORDER_GUIDE_FORM" />
   <jsp:param name="jspSubmitIdent" 	value="" />
   <jsp:param name="jspReturnFilterProperty" value="itemFilter"/>
   <jsp:param name="jspCatalogListProperty" value="storeSiteCatalog"/>
   <jsp:param name="jspRestrictCatalog" value="yes" />
</jsp:include>



<table ID=1243 width="769" cellpadding="2" cellspacing="0" border="0" class="mainbody">
<html:form
name="ADMIN2_SITE_ORDER_GUIDE_FORM"
scope="session"
action="admin2.0/admin2OrderGuideDet.do"
type="com.cleanwise.view.forms.Admin2SiteOrderGuideDetForm">

<tr>
<td colspan="4" class="largeheader"><app:storeMessage key="admin2.site.orderguide.label.orderGuideDetail"/></td>

</tr>
<tr><td colspan="4"><b><app:storeMessage key="admin2.site.orderguidedet.label.siteId"/>:</b> <bean:write name="ADMIN2_SITE_ORDER_GUIDE_FORM" property="siteId"/></td></tr>

<tr>
<td><b><app:storeMessage key="admin2.site.orderguidedet.label.id"/>:</b> </td>
<td>
<bean:write name="ADMIN2_SITE_ORDER_GUIDE_FORM"
property="orderGuideInfoData.orderGuideData.orderGuideId"
/>
<html:hidden name="ADMIN2_SITE_ORDER_GUIDE_FORM"
property="orderGuideInfoData.orderGuideData.orderGuideId"/>
</td>
<td><b><app:storeMessage key="admin2.site.orderguidedet.label.catalogId"/>:</b></td>
<td>
<bean:write
name="ADMIN2_SITE_ORDER_GUIDE_FORM"
property="orderGuideInfoData.orderGuideData.catalogId"
/>
</td>

</tr>
<tr>
 <td><b><app:storeMessage key="admin2.site.orderguidedet.label.name"/>:</b> </td>
<td>
<html:text name="ADMIN2_SITE_ORDER_GUIDE_FORM"
property="orderGuideInfoData.orderGuideData.shortDesc"
size="30" maxlength="30"/>
</td>
<td><b><app:storeMessage key="admin2.site.orderguidedet.label.catalogName"/>:</b></td>
<td>
<bean:write
name="ADMIN2_SITE_ORDER_GUIDE_FORM"
property="catalogName"
/>
</td>
</tr>

<tr>
<td><b><app:storeMessage key="admin2.site.orderguidedet.label.type"/>:</b></td>
<td>
<html:select
 name="ADMIN2_SITE_ORDER_GUIDE_FORM"
 property="orderGuideInfoData.orderGuideData.orderGuideTypeCd">
<html:option value="<%=RefCodeNames.ORDER_GUIDE_TYPE_CD.BUYER_ORDER_GUIDE%>">
<%=RefCodeNames.ORDER_GUIDE_TYPE_CD.BUYER_ORDER_GUIDE%>
</html:option>
<html:option value="<%=RefCodeNames.ORDER_GUIDE_TYPE_CD.SITE_ORDER_GUIDE_TEMPLATE%>">
<%=RefCodeNames.ORDER_GUIDE_TYPE_CD.SITE_ORDER_GUIDE_TEMPLATE%>
</html:option>
</html:select>
</td>

<td><b><app:storeMessage key="admin2.site.orderguidedet.label.shareBuyerOrderGuides"/>:</b></td>
<td>
  <html:radio name="ADMIN2_SITE_ORDER_GUIDE_FORM"
    property="shareBuyerOrderGuides" value="true"
    disabled="true" /> <app:storeMessage key="admin2.site.orderguidedet.label.yes"/>  <html:radio name="ADMIN2_SITE_ORDER_GUIDE_FORM"
      property="shareBuyerOrderGuides" value="false"  disabled="true" /> <app:storeMessage key="admin2.site.orderguidedet.label.no"/><br />
      <app:storeMessage key="admin2.site.orderguidedet.label.clickDetailToConfigureProp"/>
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

        <html:hidden name="ADMIN2_SITE_ORDER_GUIDE_FORM" property="viewMode"/>
        <html:hidden name="ADMIN2_SITE_ORDER_GUIDE_FORM" property="contractId" />
        <html:hidden name="ADMIN2_SITE_ORDER_GUIDE_FORM" property="contractName" />
    </td>
</tr>
</table>

<table ID=1244 width="769" border="0" class="results">
<tr>
<td colspan="7"><b><app:storeMessage key="admin2.site.orderguidedet.label.entries"/>: </b>
<bean:size id="ogsCount"
name="ADMIN2_SITE_ORDER_GUIDE_FORM"
property="orderGuideInfoData.orderGuideItems"
/>
<bean:write name="ogsCount" />
</td>
<td colspan="6" align="right"><b><app:storeMessage key="admin2.site.orderguidedet.label.totalAmount"/>: </b>
<bean:write name="ADMIN2_SITE_ORDER_GUIDE_FORM" property="orderGuideInfoData.totalAmount" />
</td>
</tr>

<tr>
<td><a ID=1245 class="tableheader" href="admin2OrderGuideDet.do?action=sortitems&sortField=quantity"><app:storeMessage key="admin2.site.orderguidedet.label.quantity"/></td>
<td><a ID=1246 class="tableheader" href="admin2OrderGuideDet.do?action=sortitems&sortField=amount"><app:storeMessage key="admin2.site.orderguidedet.label.extendedPrice"/></td>
<td><a ID=1247 class="tableheader" href="admin2OrderGuideDet.do?action=sortitems&sortField=cwSKU"><app:storeMessage key="admin2.site.orderguidedet.label.sku"/></td>
<td><a ID=1248 class="tableheader" href="admin2OrderGuideDet.do?action=sortitems&sortField=name"><app:storeMessage key="admin2.site.orderguidedet.label.productName"/></td>
<td><a ID=1249 class="tableheader" href="admin2OrderGuideDet.do?action=sortitems&sortField=size"><app:storeMessage key="admin2.site.orderguidedet.label.itemSize"/></td>
<td><a ID=1250 class="tableheader" href="admin2OrderGuideDet.do?action=sortitems&sortField=pack"><app:storeMessage key="admin2.site.orderguidedet.label.pack"/></td>
<td><a ID=1251 class="tableheader" href="admin2OrderGuideDet.do?action=sortitems&sortField=uom"><app:storeMessage key="admin2.site.orderguidedet.label.uom"/></td>
<td><a ID=1252 class="tableheader" href="admin2OrderGuideDet.do?action=sortitems&sortField=manufacturer"><app:storeMessage key="admin2.site.orderguidedet.label.mfg"/></td>
<td><a ID=1253 class="tableheader" href="admin2OrderGuideDet.do?action=sortitems&sortField=manufSKU"></b><app:storeMessage key="admin2.site.orderguidedet.label.mfgSku"/></td>
<td><a ID=1254 class="tableheader" href="admin2OrderGuideDet.do?action=sortitems&sortField=category"><app:storeMessage key="admin2.site.orderguidedet.label.category"/></td>
<td><a ID=1255 class="tableheader" href="admin2OrderGuideDet.do?action=sortitems&sortField=price"><app:storeMessage key="admin2.site.orderguidedet.label.msrp"/></td>

<td class="tableheader">
<a ID=1256 href="javascript:SetChecked(1);"><app:storeMessage key="admin2.label.selectCheckAll"/></a>
<a ID=1257 href="javascript:SetChecked(0);"><app:storeMessage key="admin2.label.clear"/></a>
<br>
<app:storeMessage key="global.action.label.select"/></td>
</tr>
<logic:iterate id="itemele"
indexId="i"
name="ADMIN2_SITE_ORDER_GUIDE_FORM"
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
name="ADMIN2_SITE_ORDER_GUIDE_FORM"
property='<%= "orderGuideItemDesc[" + i + "].orderGuideStructureId" %>'/>

<html:text size="3" maxlength="6"
name="ADMIN2_SITE_ORDER_GUIDE_FORM"
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
<html:multibox name="ADMIN2_SITE_ORDER_GUIDE_FORM" property="selectItems">
  <bean:write name="itemele" property="orderGuideStructureId"/>
</html:multibox>
</td>
</tr>
</logic:iterate>
<tr>
<td align=center colspan=13>
      <%--html:submit property="action">
        <app:storeMessage  key="admin.button.findItems"/>
      </html:submit --%>
      <html:submit property="action">
        <app:storeMessage  key="admin.button.locateItem"/>
      </html:submit>
      <html:submit property="action">
        <app:storeMessage  key="admin.button.remove"/>
      </html:submit>


</td>
</tr>
</html:form>

</table>

</div>







