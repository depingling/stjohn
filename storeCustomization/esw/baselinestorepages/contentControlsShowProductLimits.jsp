<%@page contentType="text/html; charset=UTF-8" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.cleanwise.service.api.util.SessionDataUtil"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.TimeZone"%>
<%@page import="java.util.Date"%>
<%@page import="com.cleanwise.service.api.value.*"%>
<%@page import="com.espendwise.view.forms.esw.ControlsForm"%>
<%@page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
<%@page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@page import="com.cleanwise.view.utils.Constants"%>
<%@page import="com.cleanwise.view.utils.ShopTool" %>
<%@page import="com.cleanwise.service.api.dto.LocationSearchDto" %>
<%@page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@page import="com.cleanwise.service.api.util.Utility" %>
<%@page import="com.cleanwise.service.api.value.AddressData" %>
<%@page import="com.cleanwise.service.api.value.BusEntityData" %>
<%@page import="com.cleanwise.service.api.value.OrderAddressData" %>
<%@page import="org.apache.commons.lang.StringUtils" %>
<%@page import="java.util.Iterator" %>
<%@page import="java.util.List" %>
<%@page import="com.cleanwise.service.api.util.ShoppingRestrictionsUtil"%>
<%@taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<bean:define id="myForm" name="esw.ControlsForm"  type="com.espendwise.view.forms.esw.ControlsForm"/>
<%
	CleanwiseUser user = ShopTool.getCurrentUser(request);
	boolean canViewProductLimits = user.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.VIEW_SHOPPING_CONTROLS);
	boolean canEditProductLimits = canViewProductLimits && user.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.EDIT_SITE_SHOPPING_CONTROLS);
    String controlsLink = "userportal/esw/controls.do";
%>
<app:setLocaleAndImages/>
<!-- Begin: Error Message -->
<%
    String errorsAndMessagesPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "errorsAndMessages.jsp");
%>
<jsp:include page="<%=errorsAndMessagesPage %>"/>
<!-- End: Error Message -->
<!-- Begin: Shopping Sub Tabs -->
<%
    String shoppingTabPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "shoppingTabs.jsp");
%>
<jsp:include page="<%=shoppingTabPage%>"/>
      	<!-- End: Shopping Sub Tabs -->
        <div class="singleColumn clearfix" id="contentWrapper">
            <div id="content">
				<div class="top clearfix topMargin"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
<%if (canViewProductLimits) {%>
<html:form name="esw.ControlsForm" styleId="controlsForm" 
	action="userportal/esw/controls.do"
	type="com.espendwise.view.forms.esw.ControlsForm">
<html:hidden name="esw.ControlsForm" property="<%=Constants.PARAMETER_OPERATION%>" styleId="operationId" />
<html:hidden name="esw.ControlsForm" property="sortField" styleId="sortField" />
<html:hidden name="esw.ControlsForm" property="sortOrder" styleId="sortOrder" />
				<!-- Start Box -->
				<div class="boxWrapper squareBottom smallMargin">
					<div class="top clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
					<div class="content">
						<div class="left clearfix">
							<h1 class="main"><app:storeMessage key="userportal.esw.label.productLimits" /></h1>
<logic:empty name="esw.ControlsForm" property="siteShoppingControlForm.shoppingRestrictionsViews">
							<table class="order">
                                <thead>
                                    <tr>
                                        <th><app:storeMessage key="userportal.esw.label.sku#" /></th>
                                        <th class="ecoFriendly"></th>
                                        <th><app:storeMessage key="userportal.esw.label.productName" /></th>
                                        <th><app:storeMessage key="userportal.esw.label.pack" /></th>
                                        <th><app:storeMessage key="userportal.esw.label.uom" /></th>
                                        <th><app:storeMessage key="userportal.esw.label.accountMaxQty" /></th>
                                        <th><app:storeMessage key="userportal.esw.label.locationMaxQty" /></th>
                                        <th><app:storeMessage key="userportal.esw.label.restrictedDays" /></th>
                                        <th><app:storeMessage key="userportal.esw.label.modified" /></th>
                                    </tr>
                                </thead>
							</table>
</logic:empty>
<logic:notEmpty name="esw.ControlsForm" property="siteShoppingControlForm.shoppingRestrictionsViews">
							<%if(canEditProductLimits) { %>
							<a class="blueBtnLargeExt" onclick="javascript:setFieldsAndSubmitForm('controlsForm', 'operationId', '<%=Constants.PARAMETER_OPERATION_VALUE_EDIT_PRODUCT_LIMITS%>');"><span><app:storeMessage key="global.action.label.edit" /></span></a><%}%>
							<table class="order">
                                <thead>
                                    <tr>
                                        <th><html:link href="<%=getSortLink(Constants.PRODUCT_LIMIT_SORT_FIELD_SKU)%>"><app:storeMessage
                                        	key="userportal.esw.label.sku#" />&nbsp;<%=getSortImage(
                                        		myForm, Constants.PRODUCT_LIMIT_SORT_FIELD_SKU)%></html:link></th>
                                        <th class="ecoFriendly"></th>
                                        <th><html:link href="<%=getSortLink(Constants.PRODUCT_LIMIT_SORT_FIELD_PRODUCT_NAME)%>"><app:storeMessage
                                        	key="userportal.esw.label.productName" />&nbsp;<%=getSortImage(
                                        		myForm, Constants.PRODUCT_LIMIT_SORT_FIELD_PRODUCT_NAME)%></html:link></th>
                                        <th><html:link href="<%=getSortLink(Constants.PRODUCT_LIMIT_SORT_FIELD_PACK)%>"><app:storeMessage
                                        	key="userportal.esw.label.pack" />&nbsp;<%=getSortImage(
                                        		myForm, Constants.PRODUCT_LIMIT_SORT_FIELD_PACK)%></html:link></th>
                                        <th><html:link href="<%=getSortLink(Constants.PRODUCT_LIMIT_SORT_FIELD_UOM)%>"><app:storeMessage
                                        	key="userportal.esw.label.uom" />&nbsp;<%=getSortImage(
                                        		myForm, Constants.PRODUCT_LIMIT_SORT_FIELD_UOM)%></html:link></th>
										<th><html:link href="<%=getSortLink(Constants.PRODUCT_LIMIT_SORT_FIELD_ACCOUNT_MAX_QTY)%>"><app:storeMessage
											key="userportal.esw.label.accountMaxQty" />&nbsp;<%=getSortImage(
                                        		myForm, Constants.PRODUCT_LIMIT_SORT_FIELD_ACCOUNT_MAX_QTY)%></html:link></th>
										<th><html:link href="<%=getSortLink(Constants.PRODUCT_LIMIT_SORT_FIELD_LOCATION_MAX_QTY)%>"><app:storeMessage
											key="userportal.esw.label.locationMaxQty" />&nbsp;<%=getSortImage(
                                        		myForm, Constants.PRODUCT_LIMIT_SORT_FIELD_LOCATION_MAX_QTY)%></html:link></th>
										<th><html:link href="<%=getSortLink(Constants.PRODUCT_LIMIT_SORT_FIELD_RESTRICTED_DAYS)%>"><app:storeMessage
											key="userportal.esw.label.restrictedDays" />&nbsp;<%=getSortImage(
                                        		myForm, Constants.PRODUCT_LIMIT_SORT_FIELD_RESTRICTED_DAYS)%></html:link></th>
										<th><html:link href="<%=getSortLink(Constants.PRODUCT_LIMIT_SORT_FIELD_MODIFIED)%>"><app:storeMessage
											key="userportal.esw.label.modified" />&nbsp;<%=getSortImage(
                                        		myForm, Constants.PRODUCT_LIMIT_SORT_FIELD_MODIFIED)%></html:link></th>
                                    </tr>
                                </thead>
                                <tbody>
<logic:iterate id="currentItemView" 
               name="esw.ControlsForm" 
               property="siteShoppingControlForm.shoppingRestrictionsViews" 
               indexId="currentIndexItemView" 
               type="ShoppingRestrictionsView"><%
int shoppingControlId = currentItemView.getShoppingControlId();
int itemId = currentItemView.getItemId();
int accountId = currentItemView.getAccountId();
int siteId = currentItemView.getSiteId();
java.util.Date controlModDate=null;
String controlModBy=null;
if(accountId > 0 && siteId == 0){
	controlModBy = currentItemView.getAcctControlModBy();
	controlModDate = currentItemView.getAcctControlModDate();
} else {
	controlModBy = currentItemView.getSiteControlModBy();
	controlModDate = currentItemView.getSiteControlModDate();
}
String siteMaxOrderQty = currentItemView.getSiteMaxOrderQty();
String accountMaxOrderQty = currentItemView.getAccountMaxOrderQty();
String siteQtyToDiplay = ShoppingRestrictionsUtil.getActualOrderQuantityToDisplay(siteMaxOrderQty, accountMaxOrderQty);
String accountQtyToDiplay = ShoppingRestrictionsUtil.getOrderQuantityToDisplay(accountMaxOrderQty);
String restrictionDays = currentItemView.getRestrictionDays();
if (restrictionDays == null) {
    restrictionDays = "";
} else { 
    restrictionDays = restrictionDays.trim();
    if ("-1".equals(restrictionDays) || "-999".equals(restrictionDays)) {
        restrictionDays = RefCodeNames.SHOP_UI_SPECIAL_PROD_ATTRIBUTE.INPUT_UNLIMITED_MAX_ORDER_QTY;
    }
}
String itemIdMaxAllowedInp = "siteShoppingControlForm.itemIdMaxAllowed(" + itemId + ")";
boolean showEcoFriendly = false;
if (myForm.getProductDataByItemIdMap() != null) {
    ProductData productD = myForm.getProductDataByItemIdMap().get(itemId);
    if (productD != null && productD.isCertificated()) {
        showEcoFriendly = true;
    }
}
String itemLink = request.getContextPath() + "/userportal/esw/shopping.do?operation=item&source=productLimit&itemId=" + itemId;
%>
                                    <tr>
                                        <td><bean:write name="currentItemView" property="itemSkuNum" /></td>
                                        <td class="ecoFriendly"><%if (showEcoFriendly) {%>
                                            <img src="<%=request.getContextPath()%>/esw/images/ecoFriendly.png" alt="eco-friendly" title="eco-friendly" /><%}%>
                                        </td>
                                        <td>
                                            <a href="<%=itemLink%>"><bean:write name="currentItemView" property="itemShortDesc" /></a>
                                        </td>
                                        <td><bean:write name="currentItemView" property="itemPack" /></td>
                                        <td><bean:write name="currentItemView" property="itemUOM" /></td>										
										<td class="right"><%=accountQtyToDiplay%></td>
										<td class="right"><%=siteQtyToDiplay%></td>
										<td class="right"><%=restrictionDays%></td>
										<td><%=controlModBy%>&nbsp;<%=ClwI18nUtil.formatDateInp(request, controlModDate)%></td>
                                    </tr>
</logic:iterate>
                                </tbody>
                            </table><%if(canEditProductLimits) { %>
                        <a class="blueBtnLargeExt" onclick="javascript:setFieldsAndSubmitForm('controlsForm', 'operationId', '<%=Constants.PARAMETER_OPERATION_VALUE_EDIT_PRODUCT_LIMITS%>');"><span><app:storeMessage key="global.action.label.edit" /></span></a><%}%>
</logic:notEmpty>
                        </div>
                    </div>    
                    <div class="bottom clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>                    
                </div> 
				<!-- End Box -->
</html:form>
<%}%>
            </div>
        </div>
<script>
function submitSortBy(sortField) {
    if (sortField == $('#sortField').val()) {
        if ('<%=Constants.PRODUCT_LIMIT_SORT_ORDER_ASCENDING%>' == $("#sortOrder").val()) {
            $('#sortOrder').val('<%=Constants.PRODUCT_LIMIT_SORT_ORDER_DESCENDING%>');
        } else {
            $('#sortOrder').val('<%=Constants.PRODUCT_LIMIT_SORT_ORDER_ASCENDING%>');
        }
    } else {
        $('#sortField').val(sortField);
        $('#sortOrder').val('<%=Constants.PRODUCT_LIMIT_SORT_ORDER_ASCENDING%>');	
    }
    $('#operationId').val('<%=Constants.PARAMETER_OPERATION_VALUE_SHOW_PRODUCT_LIMITS%>');
	$("#controlsForm").submit();
	return false;
}
</script>
<%!
	private final String upArrowImg = "<img src='../../esw/images/upArrow.gif'/>";
	private final String downArrowImg = "<img src='../../esw/images/downArrow.gif'/>";

    private String getSortImage(ControlsForm form, String sortFieldName) {
        String sortField = form.getSortField();
   	    String sortOrder = form.getSortOrder();
   	    if (sortFieldName.equalsIgnoreCase(sortField)) {
       	    if (Constants.PRODUCT_LIMIT_SORT_ORDER_ASCENDING.equalsIgnoreCase(sortOrder)) {
       		    return upArrowImg;
       	    } else {
       	        return downArrowImg;
       	    }
   	   }
       return "";
    }
    
    private String getSortLink(String sortField) {
        return "javascript:submitSortBy('" + sortField + "');";
    }
%>