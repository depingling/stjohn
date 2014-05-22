<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.service.api.util.*"%>
<%@ page import="java.util.Locale" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>


<bean:define id="IMGPath" type="java.lang.String" 
  name="pages.store.images"/>
<bean:define id="currSite" name="ApplicationUser" property="site"
  type="com.cleanwise.service.api.value.SiteData"/>

<div class="text">



<table cellSpacing=0 cellPadding=0 border=0 align=center>
<tr><td>

<html:form
  name="SITE_SHOPPING_CONTROL_FORM"
  action="userportal/siteShoppingControlEdit.do?action=update_site_controls"
  type="com.cleanwise.view.forms.SiteShoppingControlForm">

<% String thisSiteId = String.valueOf(currSite.getSiteId()); %>
<html:hidden name="SITE_SHOPPING_CONTROL_FORM"
  property="siteId"   value="<%=thisSiteId%>"/>


<br><br>				                        
<div class="bar_a"><app:storeMessage key="shop.site.text.shoppingRestrictions"/></div>
</td></tr>
<tr><td>
<table cellSpacing=0 cellPadding=2 align=center border=0>
<tr class="changeshippinglt">
<td class="cityhead"><app:storeMessage key="shoppingItems.text.sku"/></td>
<td class="cityhead"><app:storeMessage key="shoppingItems.text.productName"/></td>
<td class="cityhead"><app:storeMessage key="shoppingItems.text.size"/></td>
<td class="cityhead"><app:storeMessage key="shoppingItems.text.uom"/></td>
<td class="cityhead"><app:storeMessage key="shoppingItems.text.pack"/></td>
<td class="cityhead"><app:storeMessage key="shoppingItems.text.maxOrderQty"/></td>
<td class="cityhead">Restriction Days</td>
<td class="cityhead">&nbsp;</td>
</tr>

<% 

SiteSettingsData siteSettings = 
  SessionTool.getSiteSettings(request,currSite.getSiteId());

java.util.ArrayList sctrlv = null;
if (siteSettings.getShoppingControls() != null ) {
  sctrlv = new java.util.ArrayList(siteSettings.getShoppingControls()); 
}


for ( int i = 0; sctrlv != null && i < sctrlv.size(); i++ ) {
  ShoppingControlItemView siteItemControl = 
	(ShoppingControlItemView)sctrlv.get(i);

%>

<% 
	/* Product Info */
if ( siteItemControl.getSkuNum() != null &&
        siteItemControl.getSkuNum().length() > 0 ) { %>
<tr  class="rtext2">
<td class="rtext2"><%=siteItemControl.getSkuNum()%></td>
<td class="rtext2"><%=siteItemControl.getShortDesc()%></td>
<td class="rtext2"><%=siteItemControl.getSize()%>&nbsp;</td>
<td class="rtext2"><%=siteItemControl.getUOM()%></td>
<td class="rtext2"><%=siteItemControl.getPack()%></td>
<td class="rtext2">

<% 

  String thisFormVal = String.valueOf(
     siteItemControl.getShoppingControlData().getMaxOrderQty());
  if ( siteItemControl.getShoppingControlData().getMaxOrderQty() < 0 ||
       ! RefCodeNames.SIMPLE_STATUS_CD.ACTIVE.equals
       (siteItemControl.getShoppingControlData().getControlStatusCd() ) 
    ) {
    thisFormVal = " ";
  }
  
String 
  thisFormTag = "itemIdMaxAllowed("+ 
  String.valueOf(siteItemControl.getShoppingControlData().getItemId()) 
  + ")";

  String thisFormVal1 = String.valueOf(
    siteItemControl.getShoppingControlData().getRestrictionDays());
  if ( siteItemControl.getShoppingControlData().getRestrictionDays() < 0 ||
       ! RefCodeNames.SIMPLE_STATUS_CD.ACTIVE.equals
       (siteItemControl.getShoppingControlData().getControlStatusCd() ) 
    ) {
    thisFormVal1 = " ";

  }

String 
  thisFormTag1 = "itemIdRestrictionDays("+ 
  String.valueOf(siteItemControl.getShoppingControlData().getItemId()) 
  + ")";

%>

<% if ( !" ".equals(thisFormVal) ) { %>
<span class="changeshippinglt">
<% if ( siteItemControl.getShoppingControlData().getAccountId() > 0 ) {%>
<app:storeMessage key="shop.site.text.accountCtrl"/><br>
<% } else { %>
<app:storeMessage key="shop.site.text.siteCtrl"/><br>
<%}%>
</span>
<%}%>

<html:text name="SITE_SHOPPING_CONTROL_FORM"
  property="<%=thisFormTag%>"   value="<%=thisFormVal%>"  size='3'/>


</td>
<td class="rtext2">

<% if ( !" ".equals(thisFormVal1) ) { %>
<span class="changeshippinglt">
<% if ( siteItemControl.getShoppingControlData().getAccountId() > 0 ) {%>
<app:storeMessage key="shop.site.text.accountCtrl"/><br>
<% } else { %>
<app:storeMessage key="shop.site.text.siteCtrl"/><br>
<%}%>
</span>
<%}%>


<html:text name="SITE_SHOPPING_CONTROL_FORM"
  property="<%=thisFormTag1%>"   value="<%=thisFormVal1%>"  size='3'/>

</td>

<td class="rtext2">
<%=siteItemControl.getShoppingControlData().getModBy()%>
<br>
<br><%=ClwI18nUtil.formatDate(request, siteItemControl.getShoppingControlData().getModDate())%>
</td>
</tr>

<% } 	/* Product info */ %>

<% } %>



<tr >
<td ></td>
<td ></td>
<td ></td>
<td ></td>
<td >
<html:link href="javascript:{document.SITE_SHOPPING_CONTROL_FORM.submit();}">
[<app:storeMessage key="global.action.label.submit"/>]</html:link> 
</td>
<td></td>

</tr>

</table>

</html:form>
</td></tr>
</table>
