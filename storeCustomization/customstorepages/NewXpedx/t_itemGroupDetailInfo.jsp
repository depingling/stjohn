<%@ page import="com.cleanwise.service.api.util.Utility"%>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@ page import="com.cleanwise.service.api.value.ProductData"%>
<%@ page import="com.cleanwise.view.utils.ShopTool"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames"%>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page language="java"%>


<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template'%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app"%>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic"%>
<!--PAGESRCID=t_itemGroupDetailInfo.jsp-->

<%
    String storeDir=ClwCustomizer.getStoreDir();
	CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
	boolean allowOrderInventoryItems = true;
	if (ShopTool.isModernInventoryShopping(request) && !ShopTool.hasDiscretionaryCartAccessOpen(request) && session.getAttribute(Constants.INVENTORY_SHOPPING_CART) != null) {

		if(appUser.getUserAccount().getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_ORDER_INV_ITEMS).equals("false")){
				allowOrderInventoryItems = false;
		}
	}
%>

<% String formName = request.getParameter("formName"); %>
<bean:define id="theForm" name="<%=formName%>"
	type="com.cleanwise.view.forms.UserShopForm" />

<logic:present name="<%=formName%>" property="itemDetail">
	<logic:present name="<%=formName%>" property="itemDetail.product">

		<bean:define id="group" name="<%=formName%>"
			property="itemDetail.product"
			type="com.cleanwise.service.api.value.ProductData" />

		<bean:define id="size" name="<%=formName%>" property="cartItemsSize" />

		<logic:greaterThan name="size" value="0">



			<table cellspacing="0" width="100%">
				<tr>
					<app:displayProdHeader viewOptionEditCartItems="false"
						viewOptionQuickOrderView="false" viewOptionAddToCartList="false"
						viewOptionOrderGuide="false" viewOptionGroupedItemDetail="true"
						onClickDeleteAllCheckBox="SetCheckedGlobalRev('electBox')" />
				</tr>
				<logic:iterate id="sitem" name="<%=formName%>" property="cartItems"
					type="com.cleanwise.service.api.value.ShoppingCartItemData"
					indexId="index">

					<bean:define id="quantityEl"
						value="<%=\"quantityElement[\"+index+\"]\"%>" />
					<bean:define id="itemIdsEl"
						value="<%=\"itemIdsElement[\"+index+\"]\"%>" />
					<tr>
						<app:displayProd viewOptionEditCartItems="false"
							viewOptionQuickOrderView="false" viewOptionAddToCartList="false"
							viewOptionOrderGuide="false" viewOptionGroupedItemDetail="true"
							name="sitem" link="" index="<%=index%>"
							inputNameOnHand="<%=\"cartLine[\" + index + \"].inventoryQtyOnHandString\"%>"
							inputNameQuantity="<%=\"quantityElement[\" + index + \"]\"%>"
							inputNameItemId="<%=\"itemIdsElement[\" + index + \"]\"%>" />
					</tr>
				</logic:iterate>
			</table>
		</logic:greaterThan>
	</logic:present>
</logic:present>


