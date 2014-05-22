
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.service.api.session.Site"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames"%>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<bean:define id="cipclass" type="java.lang.String" value = "rtext2"/>
<bean:define id="appUser" name="ApplicationUser" type="com.cleanwise.view.utils.CleanwiseUser"/>
<bean:define id="theForm" name="USER_DETAIL_FORM"  type="com.cleanwise.view.forms.UserMgrDetailForm"/>

<div class="text">

<table cellSpacing=2 cellPadding=0 border=0 align=center>
<td>
<br>
<app:storeMessage key="shop.userProfile.text.youMayPrintThisPage"/>

<logic:present 	name="ApplicationUser" property="site" >

<bean:define id="currSite" name="ApplicationUser" property="site" type="com.cleanwise.service.api.value.SiteData"/>


<app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.VIEW_SITE_PAR_VALUES%>">

<%  /* Start of the inventory section. */

if (null != currSite && currSite.hasInventoryShoppingOn()) { %>

<br><br>
<div class="bar_a"><app:storeMessage key="shop.site.text.siteParValues"/></div>

<bean:define id="budgetPeriods" name="ApplicationUser" property="site.budgetPeriods" type="java.util.Hashtable" />

<%Calendar cal = java.util.Calendar.getInstance(); %>
<%int numPeriods = ((java.util.Hashtable)budgetPeriods).size();%>

<table width="750" cellSpacing=0 cellPadding=2 align=center border=0>
<tr class="changeshippinglt">
<td class="cityhead"><app:storeMessage key="shoppingItems.text.sku"/></td>
<td class="cityhead"><app:storeMessage key="shoppingItems.text.productName"/></td>
<td class="cityhead"><app:storeMessage key="shoppingItems.text.uom"/></td>
<td class="cityhead"><app:storeMessage key="shoppingItems.text.pack"/></td>
<td class="cityhead" colspan=<%=numPeriods%>><app:storeMessage key="shop.site.text.parValues"/></td>
</tr>
<tr>
<td colspan=4>&nbsp;</td>

<% for (int bp = 1; bp <= numPeriods; bp++) {

    String drange = "";
    if (budgetPeriods.containsKey(bp)) {

        FiscalCalendarInfo.BudgetPeriodInfo bi = (FiscalCalendarInfo.BudgetPeriodInfo) budgetPeriods.get(bp);

        cal.setTime(bi.getStartDate());
        drange = "" + (cal.get(cal.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH) + " - ";

        cal.setTime(bi.getEndDate());
        drange += "" + (cal.get(cal.MONTH) + 1) + "/" + cal.get(Calendar.DAY_OF_MONTH);

    } %>

<% if ( drange != null && drange.length() > 0 ) { %>
<td valign=top class="cityhead"><%=bp%><br><%=drange%></td>
<% } %>

<% } %>

</tr>

<logic:present 	name="SITE_INVENTORY_FORM" >

    <logic:iterate id="siteInv"
                   indexId="i"
                   name="SITE_INVENTORY_FORM"
                   property="inventoryItems"
                   type="com.cleanwise.service.api.value.SiteInventoryConfigView">

        <logic:greaterThan name="siteInv" property="sumOfAllParValues" value="0">
            <tr class="rtext2">
                <td class="rtext2"><bean:write name="siteInv" property="actualSku"/></td>
                <td class="rtext2"><bean:write name="siteInv" property="itemDesc"/></td>
                <td class="rtext2"><bean:write name="siteInv" property="itemUom"/></td>
                <td class="rtext2"><bean:write name="siteInv" property="itemPack"/></td>
                <%for (int bp = 1; bp <= numPeriods; bp++) { %>
                <%cipclass = currSite.getCurrentInventoryPeriod() == bp ? "changeshippinglt" : "rtext2"; %>
                <td class="<%=cipclass%>"><%=Utility.getIntValueNN(((SiteInventoryConfigView) siteInv).getParValues(), bp)%></td>
                <%}%>
                <td  class="rtext2" style="white-space: nowrap;">
                    <bean:write name="siteInv" property="modBy"/><br>
                    <logic:present name="siteInv" property="modDate">
                        <bean:define id="mDate" name="siteInv" property="modDate" type="java.util.Date" />
                        <i18n:formatDate value="<%=mDate%>" pattern="yyyy-M-d"  locale="<%=Locale.US%>"/>
                    </logic:present>

                </td>

            </tr>
        </logic:greaterThan>


    </logic:iterate>

</logic:present>

</table>

<app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.EDIT_SITE_PAR_VALUES%>">
  <a href="siteInventoryEdit.do?action=inventory_update"
  >[&nbsp;<app:storeMessage key="global.action.label.edit"/>&nbsp;]</a>
</app:authorizedForFunction>

<% } /* End of the inventory section. */ %>
</app:authorizedForFunction>

<%  /* Start of shopping control section. */ %>

<% if (null != currSite && currSite.getShoppingControls() != null
  && currSite.getShoppingControls().size() > 0 ) { %>

<br><br>
<div class="bar_a"><app:storeMessage key="shop.site.text.shoppingRestrictions"/></div>

<table
  width="750" cellSpacing=0 cellPadding=2 align=center border=0>

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
  siteItemControl.getSkuNum().length() > 0 &&
  siteItemControl.getShoppingControlData().getMaxOrderQty() >= 0 ) { %>
<tr  class="rtext2">
<td class="rtext2"><%=siteItemControl.getSkuNum()%></td>
<td class="rtext2"><%=siteItemControl.getShortDesc()%></td>
<td class="rtext2"><%=siteItemControl.getSize()%>&nbsp;</td>
<td class="rtext2"><%=siteItemControl.getUOM()%></td>
<td class="rtext2"><%=siteItemControl.getPack()%></td>
<td class="rtext2">
<%=siteItemControl.getShoppingControlData().getMaxOrderQty()%></td>

<td class="rtext2">
<%=siteItemControl.getShoppingControlData().getRestrictionDays()%></td>

<td class="rtext2">
<%=siteItemControl.getShoppingControlData().getModBy()%>
<br>
<i18n:formatDate
  value="<%=siteItemControl.getShoppingControlData().getModDate()%>"
  pattern="yyyy-M-d"  locale="<%=Locale.US%>"/>

</td>
</tr>

<% } 	/* Product info */ %>

<% } %>

</table>
<app:authorizedForFunction
  name="<%=RefCodeNames.APPLICATION_FUNCTIONS.EDIT_SITE_SHOPPING_CONTROLS%>">
<% String aurl = "siteShoppingControlEdit.do?action=shopping_update&siteid=" + currSite.getSiteId(); %>
<a href="<%=aurl%>">[&nbsp;<app:storeMessage key="global.action.label.edit"/>&nbsp;]</a>
</app:authorizedForFunction>

<% } %>

<% /* End of shopping control section. */ %>

</logic:present>

<br><br>
<div class="bar_a">
 <app:storeMessage key="shop.userProfile.text.yourProfile"/>
</div>
<br><br>
<table border="0" cellpadding="0" cellspacing="0">
<tr>
<td><app:storeMessage key="shop.userProfile.text.firstName"/></td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.userData.firstName"/>
</td> </tr>

<tr> <td><app:storeMessage key="shop.userProfile.text.lastName"/></td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.userData.lastName"/>
</td> </tr>

<tr> <td><app:storeMessage key="shop.userProfile.text.companyName"/></td>
<td>
<bean:write name="USER_DETAIL_FORM" property="accountName"/>
</td> </tr>

<tr>  <td><app:storeMessage key="shop.userProfile.text.userName"/> </td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.userData.userName"/>
</td> </tr>

<tr>
<td><app:storeMessage key="shop.userProfile.text.locale"/> </td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.userData.prefLocaleCd"/>
</td>

<tr>  <td><app:storeMessage key="shop.userProfile.text.phone"/></td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.phone.phoneNum" />
</td> </tr>

<tr>  <td><app:storeMessage key="shop.userProfile.text.mobile"/></td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.mobile.phoneNum"/>
</td> </tr>

<tr>  <td><app:storeMessage key="shop.userProfile.text.fax"/></td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.fax.phoneNum"/>
</td> </tr>

<tr>  <td><app:storeMessage key="shop.userProfile.text.email"/></td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.emailData.emailAddress"/>
</td></tr>

<tr valign=top>
<td><app:storeMessage key="shop.userProfile.text.contactAddress"/></td>
</tr>
<tr>
<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
<td>
<table>
<tr>
<td> <app:storeMessage key="shop.userProfile.text.address1"/>: </td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.addressData.address1"/>
</td>
</tr><tr>
<td> <app:storeMessage key="shop.userProfile.text.address2"/>: </td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.addressData.address2"/>
</td>
</tr>

<tr>
<td> <app:storeMessage key="shop.userProfile.text.city"/>: </td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.addressData.city"/>
</td>
</tr>
<% if (appUser.getUserStore().isStateProvinceRequired()) { %>
<tr>
<td> <app:storeMessage key="shop.userProfile.text.stateProvince"/>: </td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.addressData.stateProvinceCd"/>
</td>
</tr>
<% } %>

<tr>
<td> <app:storeMessage key="shop.userProfile.text.zipPostalCode"/>: </td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.addressData.postalCode"/>
</td>
</tr><tr>
<td> <app:storeMessage key="shop.userProfile.text.country"/>: </td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.addressData.countryCd"/>
</td>
</tr>
</table>
</td>
</tr>
<tr>
<td>&nbsp;</td>
</tr>


<logic:present 	name="USER_DETAIL_FORM" property="accountAddress" >

<tr valign=top>
<td><app:storeMessage key="shop.userProfile.text.billingAddress"/></td>
</tr>
<tr>
<td>&nbsp;&nbsp;&nbsp;&nbsp;</td>
<td>
<table>
<tr>
<td><app:storeMessage key="shop.userProfile.text.address1"/>: </td>
<td>
<bean:write name="USER_DETAIL_FORM" property="accountAddress.address1"/>
</td>
</tr><tr>
<td><app:storeMessage key="shop.userProfile.text.address2"/>: </td>
<td>
<bean:write name="USER_DETAIL_FORM" property="accountAddress.address2"/>
</td>
</tr><tr>
<td><app:storeMessage key="shop.userProfile.text.city"/>: </td>
<td>
<bean:write name="USER_DETAIL_FORM" property="accountAddress.city"/>
</td>
</tr>
<% if (appUser.getUserStore().isStateProvinceRequired()) { %>
<tr>
<td><app:storeMessage key="shop.userProfile.text.stateProvince"/>: </td>
<td>
<bean:write name="USER_DETAIL_FORM" property="accountAddress.stateProvinceCd"/>
</td>
</tr>
<% } %>
<tr>
<td><app:storeMessage key="shop.userProfile.text.zipPostalCode"/>: </td>
<td>
<bean:write name="USER_DETAIL_FORM" property="accountAddress.postalCode"/>
</td>
</tr><tr>
<td><app:storeMessage key="shop.userProfile.text.country"/>: </td>
<td>
<bean:write name="USER_DETAIL_FORM" property="accountAddress.countryCd"/>
</td>
</tr>
</table>
</td>
</tr>
<tr>
<td>&nbsp;</td>
</tr>
</logic:present>

<tr>
<td colspan=2>
<a href="customerAccountManagementProfileEdit.do?action=customer_profile">
  [&nbsp;<app:storeMessage key="global.action.label.edit"/>&nbsp;]</a>
<br></br>
</td>
</tr>
</table>

</td>
</tr>
</table>



<% if (null != appUser.getSite() && appUser.isaAdmin()) { %>
<app:storeMessage key="shop.site.text.adminOnlySiteInfo"/>
<div style="text-align: left; font-size: 10pt;width: 700;">
<pre>
<%=appUser.getSite().toBasicInfo()%>
</pre>
</div>

<% } %>

</div>

