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


<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<bean:define id="currSite" name="ApplicationUser" property="site" type="com.cleanwise.service.api.value.SiteData"/>

<script language="JavaScript" type="text/JavaScript">
    function doOrderQtyUnlimited(controlId) {
        document.getElementById(controlId).value = "*";
    }
    function doOrderQtyResetToAccount(controlId, accountQty) {
        document.getElementById(controlId).value = accountQty;
    }
</script>

<div class="text">
<html:form name="SITE_SHOPPING_CONTROL_FORM" action="userportal/siteShoppingControlEdit.do?action=update_site_controls" type="com.cleanwise.view.forms.SiteShoppingControlForm">
<html:hidden name="SITE_SHOPPING_CONTROL_FORM" property="siteId" value="<%=String.valueOf(currSite.getSiteId())%>"/>
<table cellSpacing=0 cellPadding=0 border=0 align=center>
    <tbody id="shoppingControlsTableBody">
        <tr>
            <td>
                <br>
                <br>                                        
                <div class="bar_a"><app:storeMessage key="shop.site.text.shoppingControl"/></div>
            </td>
        </tr>
        <tr>
            <td>
                <table cellSpacing=0 cellPadding=2 align=center border=0>
                    <tr class="changeshippinglt">
                        <td class="cityhead"><app:storeMessage key="shoppingItems.text.sku"/></td>
                        <td class="cityhead"><app:storeMessage key="shoppingItems.text.productName"/></td>
                        <td class="cityhead"><app:storeMessage key="shoppingItems.text.size"/></td>
                        <td class="cityhead"><app:storeMessage key="shoppingItems.text.uom"/></td>
                        <td class="cityhead"><app:storeMessage key="shoppingItems.text.pack"/></td>
                        <td colspan="2" class="cityhead"><app:storeMessage key="shoppingItems.text.maxOrderQty"/></td>
                        <td class="cityhead"><app:storeMessage key="shoppingItems.text.accountMaxOrderQty"/></td>
                        <logic:equal name="SITE_SHOPPING_CONTROL_FORM" property="existRestrictionDays" value="true">
                            <td class="cityhead"><app:storeMessage key="shoppingItems.text.restrictionDays"/></td>
                        </logic:equal>
                        <td class="cityhead">&nbsp;</td>
                    </tr>
                    <logic:present name="SITE_SHOPPING_CONTROL_FORM" property="shoppingRestrictionsViews">
                    <logic:iterate 
                        id="currentItemView" 
                        name="SITE_SHOPPING_CONTROL_FORM" 
                        property="shoppingRestrictionsViews" 
                        indexId="currentIndexItemView" 
                        type="ShoppingRestrictionsView">
                    <%--<logic:notEmpty name="currentItemView" property="skuNum">--%>
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
                    String restrictionDays = currentItemView.getRestrictionDays();
                    String siteMaxOrderQty = currentItemView.getSiteMaxOrderQty();
                    String accountMaxOrderQty = currentItemView.getAccountMaxOrderQty();
                    String itemIdMaxAllowedInp = "itemIdMaxAllowed(" + String.valueOf(itemId) + ")";
                    String maxOrderQtyControlId = "maxOrderQtyControlId_" + itemId;
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
                        <td class="rtext2">
                        
                            <span class="changeshippinglt">
                            <% 
                            if (accountId > 0) {
                            %>
                            <app:storeMessage key="shop.site.text.accountCtrl"/><br>
                            <% 
                            } 
                            else { 
                            %>
                            <app:storeMessage key="shop.site.text.siteCtrl"/><br>
                            <%
                            }
                            %>
                            </span>
				
                        <html:text styleId="<%=maxOrderQtyControlId%>" name="SITE_SHOPPING_CONTROL_FORM" property="<%=itemIdMaxAllowedInp%>" value="<%=siteQtyToDiplay%>" size='3'/>
                        </td>
                        <td class="rtext2">
                            <table border="0" cellpadding="1" cellspacing="0">
                                <tbody>
                                    <tr><td>
                                        <input 
                                            class="changeshippinglt" 
                                            style="font-size: 9px; cursor: pointer;" 
                                            type="button" 
                                            value='<app:storeMessage key="shoppingItems.text.unlimitedMaxOrderQty"/>' 
                                            onClick="javascript:doOrderQtyUnlimited('<%=maxOrderQtyControlId%>')">
                                    </td></tr>
                                    <tr><td>
                                        <input 
                                            class="changeshippinglt" 
                                            style="font-size: 9px; cursor: pointer;" 
                                            type="button" 
                                            value='<app:storeMessage key="shoppingItems.text.resetToAccountMaxOrderQty"/>' 
                                            onClick="javascript:doOrderQtyResetToAccount('<%=maxOrderQtyControlId%>', '')">   
                                    </td></tr>
                                </tbody>
                            </table>
                        </td>
                        <td class="rtext2"><%=accountQtyToDiplay%>&nbsp;</td>
                        <logic:equal name="SITE_SHOPPING_CONTROL_FORM" property="existRestrictionDays" value="true">
                            <td class="rtext2"><%=restrictionDays%>&nbsp;</td>
                        </logic:equal>
                        <td class="rtext2">
                            <%=controlModBy%>
                            <br>
                            <%=ClwI18nUtil.formatDate(request, controlModDate)%>
                        </td>
                    </tr>
                    <%--</logic:notEmpty>--%>
                    </logic:iterate>
                    </logic:present>
                    <tr>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td>
                            <%--<html:link href="javascript:{document.SITE_SHOPPING_CONTROL_FORM.submit();}">
                                [<app:storeMessage key="global.action.label.submit"/>]
                            </html:link> --%>
							<input type="submit" value="Submit" />
                        </td>
                        <td></td>
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                </table>
            </td>
        </tr>
    </tbody>
</table>
</html:form>
</div>
