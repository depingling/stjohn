
<%@ page language="java" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.ShoppingRestrictionsUtil"%>
<%@ page import="com.cleanwise.service.api.util.Utility"%>
<%@ page import="com.cleanwise.service.api.value.FiscalCalendarInfo"%>
<%@ page import="com.cleanwise.service.api.value.SiteInventoryConfigView"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Locale" %>
<%@page import="java.text.DateFormat"%>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>

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

<logic:present  name="ApplicationUser" property="site">
<bean:define id="currSite" name="ApplicationUser" property="site" type="com.cleanwise.service.api.value.SiteData"/>
<app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.VIEW_SITE_PAR_VALUES%>">

</app:authorizedForFunction>

<%--  
//////////////////////////////////////////////////////////////////////////////////////////
//
// Start of shopping control section
//
//////////////////////////////////////////////////////////////////////////////////////////
--%>

<% if (null != currSite && currSite.getShoppingControls() != null
  && currSite.getShoppingControls().size() > 0 ) { 
%>

<br>
<br>
<div class="bar_a"><app:storeMessage key="shop.site.text.shoppingControl"/></div>
<table width="750" cellSpacing="0" cellPadding="2" align="center" border="0">
    <tr class="changeshippinglt">
        <td class="cityhead"><app:storeMessage key="shoppingItems.text.sku"/></td>
        <td class="cityhead"><app:storeMessage key="shoppingItems.text.productName"/></td>
        <td class="cityhead"><app:storeMessage key="shoppingItems.text.size"/></td>
        <td class="cityhead"><app:storeMessage key="shoppingItems.text.uom"/></td>
        <td class="cityhead"><app:storeMessage key="shoppingItems.text.pack"/></td>
        <td class="cityhead"><app:storeMessage key="shoppingItems.text.siteMaxOrderQty"/></td>
        <td class="cityhead"><app:storeMessage key="shoppingItems.text.accountMaxOrderQty"/></td>
        <logic:equal name="USER_DETAIL_FORM" property="existRestrictionDays" value="true">
            <td class="cityhead"><app:storeMessage key="shoppingItems.text.restrictionDays"/></td>
        </logic:equal>
        <td class="cityhead">&nbsp;</td>
    </tr>
    <logic:present name="USER_DETAIL_FORM" property="shoppingRestrictionsViews">
    <logic:iterate 
        id="currentItemView" 
        name="USER_DETAIL_FORM" 
        property="shoppingRestrictionsViews" 
        indexId="currentIndexItemView" 
        type="com.cleanwise.service.api.value.ShoppingRestrictionsView">
    <% 
    int shoppingControlId = currentItemView.getShoppingControlId();
    int itemId = currentItemView.getItemId();
    int accountId = currentItemView.getAccountId();
    int siteId = currentItemView.getSiteId();
	String controlModBy=null;
	java.util.Date controlModDate=null;
	if(accountId > 0 && siteId == 0){
		controlModBy = currentItemView.getAcctControlModBy();
		controlModDate = currentItemView.getAcctControlModDate();
	}else{
		controlModBy = currentItemView.getSiteControlModBy();
		controlModDate = currentItemView.getSiteControlModDate();
	}
    String statusCd = currentItemView.getControlStatusCd();
    String restrictionDays = currentItemView.getRestrictionDays();
    String siteMaxOrderQty = currentItemView.getSiteMaxOrderQty();
    String accountMaxOrderQty = currentItemView.getAccountMaxOrderQty();
    String siteQtyToDiplay = ShoppingRestrictionsUtil.getActualOrderQuantityToDisplay(siteMaxOrderQty, accountMaxOrderQty);
    String accountQtyToDiplay = ShoppingRestrictionsUtil.getOrderQuantityToDisplay(accountMaxOrderQty);
    if (restrictionDays == null) {
        restrictionDays = "";
    }
    else { 
        restrictionDays = restrictionDays.trim();
        if ("-1".equals(restrictionDays) || "-999".equals(restrictionDays)) {
            restrictionDays = RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.INPUT_UNLIMITED_MAX_ORDER_QTY;
        }
    }
    %>
    <tr>
        <td class="rtext2"><bean:write name="currentItemView" property="itemSkuNum"/></td>
        <td class="rtext2"><bean:write name="currentItemView" property="itemShortDesc"/></td>
        <td class="rtext2"><bean:write name="currentItemView" property="itemSize"/>&nbsp;</td>
        <td class="rtext2"><bean:write name="currentItemView" property="itemUOM"/></td>
        <td class="rtext2"><bean:write name="currentItemView" property="itemPack"/></td>
        <td class="rtext2"><%=siteQtyToDiplay%>&nbsp;</td>
        <td class="rtext2"><%=accountQtyToDiplay%>&nbsp;</td>
        <logic:equal name="USER_DETAIL_FORM" property="existRestrictionDays" value="true">
            <td class="rtext2"><%=restrictionDays%>&nbsp;</td>
        </logic:equal>
        <td class="rtext2">
            <%=controlModBy%>
            <br/>
            <%=ClwI18nUtil.formatDate(request, controlModDate)%>
        </td>
    </tr>                   
    </logic:iterate>
    </logic:present>
</table>
<app:authorizedForFunction
  name="<%=RefCodeNames.APPLICATION_FUNCTIONS.EDIT_SITE_SHOPPING_CONTROLS%>">
<% String aurl = "siteShoppingControlEdit.do?action=shopping_update&siteid=" + currSite.getSiteId(); %>
<a href="<%=aurl%>">[&nbsp;<app:storeMessage key="global.action.label.edit"/>&nbsp;]</a>
</app:authorizedForFunction>

<% 
} 
%>
<%--  
//////////////////////////////////////////////////////////////////////////////////////////
//
// End of shopping control section
//
//////////////////////////////////////////////////////////////////////////////////////////
--%>

</logic:present>
