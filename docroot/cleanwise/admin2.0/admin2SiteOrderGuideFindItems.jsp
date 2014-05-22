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
name="ADMIN2_SITE_ORDER_GUIDE_FORM"
scope="session"
action="admin2.0/admin2OrderGuideFindItems.do"
type="com.cleanwise.view.forms.Admin2SiteOrderGuideDetForm">

<tr>
<td class="largeheader">Order Guide Detail</td>
</tr>

<tr><td><b><app:storeMessage key="admin2.site.orderguidedet.label.siteId"/>:</b> <bean:write name="ADMIN2_SITE_ORDER_GUIDE_FORM" property="siteId"/></td></tr>

<tr>
<td><b><app:storeMessage key="admin2.site.orderguidedet.label.id"/></b> </td>
<td>
<bean:write name="ADMIN2_SITE_ORDER_GUIDE_FORM"
property="orderGuideInfoData.orderGuideData.orderGuideId"
/>
<html:hidden name="ADMIN2_SITE_ORDER_GUIDE_FORM"
property="orderGuideInfoData.orderGuideData.orderGuideId"/>
</td>
<td><b><app:storeMessage key="admin2.site.orderguidedet.label.catalogId"/></b></td>
<td>
<bean:write
name="ADMIN2_SITE_ORDER_GUIDE_FORM"
property="orderGuideInfoData.orderGuideData.catalogId"
/>
</td>

</tr>
<tr>
 <td><b><app:storeMessage key="admin2.site.orderguidedet.label.name"/></b> </td>
<td>
<bean:write
name="ADMIN2_SITE_ORDER_GUIDE_FORM"
property="orderGuideInfoData.orderGuideData.shortDesc"
/>
</td>
<td><b><app:storeMessage key="admin2.site.orderguidedet.label.catalogName"/></b></td>
<td>
<bean:write
name="ADMIN2_SITE_ORDER_GUIDE_FORM"
property="catalogName"
/>
</td>
</tr>

<tr>
<td><b><app:storeMessage key="admin2.site.orderguidedet.label.type"/></b></td>
<td>
<bean:write name="ADMIN2_SITE_ORDER_GUIDE_FORM"
property="orderGuideInfoData.orderGuideData.orderGuideTypeCd"/>
<html:hidden name="ADMIN2_SITE_ORDER_GUIDE_FORM" property="viewMode"/>
<html:hidden name="ADMIN2_SITE_ORDER_GUIDE_FORM" property="contractId"/>
<html:hidden name="ADMIN2_SITE_ORDER_GUIDE_FORM" property="contractName"/>
</td>
<td colspan="2">&nbsp;</td>
</tr>

</table>

<table ID=1259 width="769" border="1" class="results">
<tr>
<td colspan="11"><b><app:storeMessage key="admin2.site.orderguidedet.label.entries"/>: </b>
<bean:size id="ogsCount"
name="ADMIN2_SITE_ORDER_GUIDE_FORM"
property="orderGuideInfoData.orderGuideItems"
/>
<bean:write name="ogsCount" />
</td>
</tr>
<tr>

<tr>
<td colspan="11" align=right>
<a ID=1260 href="javascript:SetChecked(1)"><app:storeMessage key="admin2.label.selectCheckAll"/></a>
<a ID=1261 href="javascript:SetChecked(0)"><app:storeMessage key="admin2.label.clear"/></a> <br>
</td>
</tr>

<td><b><a ID=1262 href="admin2OrderGuideFindItems.do?action=sortfinditems&sortField=cwSKU"><app:storeMessage key="admin2.site.orderguidedet.label.cw"/></b></td>
<td><b><a ID=1263 href="admin2OrderGuideFindItems.do?action=sortfinditems&sortField=name"><app:storeMessage key="admin2.site.orderguidedet.label.productName"/></b></td>
<td><b><a ID=1264 href="admin2OrderGuideFindItems.do?action=sortfinditems&sortField=size"><app:storeMessage key="admin2.site.orderguidedet.label.itemSize"/></b></td>
<td><b><a ID=1265 href="admin2OrderGuideFindItems.do?action=sortfinditems&sortField=pack"><app:storeMessage key="admin2.site.orderguidedet.label.pack"/></b></td>
<td><b><a ID=1266 href="admin2OrderGuideFindItems.do?action=sortfinditems&sortField=uom"><app:storeMessage key="admin2.site.orderguidedet.label.uom"/></b></td>
<td><b><a ID=1267 href="admin2OrderGuideFindItems.do?action=sortfinditems&sortField=color"><app:storeMessage key="admin2.site.orderguidedet.label.color"/></b></td>
<td><b><a ID=1268 href="admin2OrderGuideFindItems.do?action=sortfinditems&sortField=manufacturer"><app:storeMessage key="admin2.site.orderguidedet.label.manufacturer"/></b></td>
<td><b><a ID=1269 href="admin2OrderGuideFindItems.do?action=sortfinditems&sortField=manufSKU"><app:storeMessage key="admin2.site.orderguidedet.label.manufSku"/></b></td>
<td><b><a ID=1270 href="admin2OrderGuideFindItems.do?action=sortfinditems&sortField=category"><app:storeMessage key="admin2.site.orderguidedet.label.category"/></b></td>
<td><b><a ID=1271 href="admin2OrderGuideFindItems.do?action=sortfinditems&sortField=price"><app:storeMessage key="admin2.site.orderguidedet.label.price"/></b></td>
<td><b>Select</b></td>
</tr>

<logic:iterate id="itemele"
indexId="i"
name="ADMIN2_SITE_ORDER_GUIDE_FORM"
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






