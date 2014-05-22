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
 dml=document.STORE_SITE_ORDER_GUIDE_FORM;
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

<div class="text">
<center>
<font color=red>
<html:errors/>
</font>
</center>

<table ID=1258 width="769" cellpadding="2" cellspacing="0" border="0" class="mainbody">
<html:form
name="STORE_SITE_ORDER_GUIDE_FORM"
scope="session"
action="storeportal/orderguidefinditems.do"
type="com.cleanwise.view.forms.StoreSiteOrderGuideDetForm">

<tr>
<td class="largeheader">Order Guide Detail</td>
</tr>

<tr><td><b>Site Id:</b> <bean:write name="STORE_SITE_ORDER_GUIDE_FORM" property="siteId"/></td></tr>

<tr>
<td><b>Order Guide&nbsp;Id</b> </td>
<td>
<bean:write name="STORE_SITE_ORDER_GUIDE_FORM"
property="orderGuideInfoData.orderGuideData.orderGuideId"
/>
<html:hidden name="STORE_SITE_ORDER_GUIDE_FORM"
property="orderGuideInfoData.orderGuideData.orderGuideId"/>
</td>
<td><b>Orger Guide Catalog&nbsp;Id</b></td>
<td>
<bean:write
name="STORE_SITE_ORDER_GUIDE_FORM"
property="orderGuideInfoData.orderGuideData.catalogId"
/>
</td>

</tr>
<tr>
 <td><b>Order Guide Name</b> </td>
<td>
<bean:write
name="STORE_SITE_ORDER_GUIDE_FORM"
property="orderGuideInfoData.orderGuideData.shortDesc"
/>
</td>
<td><b>Order Guide Catalog&nbsp;Name</b></td>
<td>
<bean:write
name="STORE_SITE_ORDER_GUIDE_FORM"
property="catalogName"
/>
</td>
</tr>

<tr>
<td><b>Order Guide&nbsp;Type</b></td>
<td>
<bean:write name="STORE_SITE_ORDER_GUIDE_FORM"
property="orderGuideInfoData.orderGuideData.orderGuideTypeCd"/>
<html:hidden name="STORE_SITE_ORDER_GUIDE_FORM" property="viewMode"/>
<html:hidden name="STORE_SITE_ORDER_GUIDE_FORM" property="contractId"/>
<html:hidden name="STORE_SITE_ORDER_GUIDE_FORM" property="contractName"/>
</td>
<td colspan="2">&nbsp;</td>
</tr>

</table>

<table ID=1259 width="769" border="1" class="results">
<tr>
<td colspan="11"><b>Catalog Entries: </b>
<bean:size id="ogsCount"
name="STORE_SITE_ORDER_GUIDE_FORM"
property="orderGuideInfoData.orderGuideItems"
/>
<bean:write name="ogsCount" />
</td>
</tr>
<tr>

<tr>
<td colspan="11" align=right>
<a ID=1260 href="javascript:SetChecked(1)">[Check&nbsp;All]</a>
<a ID=1261 href="javascript:SetChecked(0)">[&nbsp;Clear]</a> <br>
</td>
</tr>

<td><b><a ID=1262 href="orderguidefinditems.do?action=sortfinditems&sortField=cwSKU">CW #</b></td>
<td><b><a ID=1263 href="orderguidefinditems.do?action=sortfinditems&sortField=name">Product Name</b></td>
<td><b><a ID=1264 href="orderguidefinditems.do?action=sortfinditems&sortField=size">Item Size</b></td>
<td><b><a ID=1265 href="orderguidefinditems.do?action=sortfinditems&sortField=pack">Pack</b></td>
<td><b><a ID=1266 href="orderguidefinditems.do?action=sortfinditems&sortField=uom">UOM</b></td>
<td><b><a ID=1267 href="orderguidefinditems.do?action=sortfinditems&sortField=color">Color</b></td>
<td><b><a ID=1268 href="orderguidefinditems.do?action=sortfinditems&sortField=manufacturer">Manufacturer</b></td>
<td><b><a ID=1269 href="orderguidefinditems.do?action=sortfinditems&sortField=manufSKU">Manuf. SKU</b></td>
<td><b><a ID=1270 href="orderguidefinditems.do?action=sortfinditems&sortField=category">Category</b></td>
<td><b><a ID=1271 href="orderguidefinditems.do?action=sortfinditems&sortField=price">Price</b></td>
<td><b>Select</b></td>
</tr>

<logic:iterate id="itemele"
indexId="i"
name="STORE_SITE_ORDER_GUIDE_FORM"
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






