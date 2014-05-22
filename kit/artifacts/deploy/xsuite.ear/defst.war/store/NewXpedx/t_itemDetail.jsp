<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.service.api.value.ShoppingCartData" %>
<%@ page import="com.cleanwise.service.api.value.AccountData" %>
<%@ page import="com.cleanwise.service.api.value.SiteData" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.OrderGuideData" %>
<%@ page import="com.cleanwise.view.forms.UserShopForm" %>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser"%>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<!--PAGESRCID=t_itemDetail.jsp-->
<script language="JavaScript1.2">
    <!--

    function actionMultiSubmit(actionDef, action, operation) {

        var actionElements = document.getElementsByName('action');
        if (actionElements.length) {
            for (var i = actionElements.length - 1; i >= 0; i--) {

                var element = actionElements[i];
                if (actionDef == element.value) {
                    element.value = action;

                    if (operation) {
                        var operationElements = document.getElementsByName('operation');
                        if (operationElements.length) {
                            for (var z = operationElements.length - 1; z >= 0; z--) {
                                var opelement = operationElements[z];
                                if ("hiddenOperation" == opelement.value) {
                                    opelement.value = operation;
                                }
                            }
                        } else {
                            if ("hiddenOperation" == operationElements.value) {
                                operationElements.value = action;
                            }
                        }
                    }

                    document.forms[0].submit();

                    break;
                }
            }
        } else if (actionElements) {
            actionElements.value = action;
            document.forms[0].submit();
        }

        return false;
    }

    //-->
</script>
<script type="text/javascript">
    // <![CDATA[
    function changeOrderGuideOp(obj, id1) {

        txt = obj.options[obj.selectedIndex].value;
        f_hideBox("ITEM_DETAIL_BOX__messageText");
        if (txt.match('-2')) {
            f_showBox("ITEM_DETAIL_BOX__createNewList");
            f_hideBox("ITEM_DETAIL_BOX__saveToList");
        } else if (txt.match('-1')) {
            f_hideBox("ITEM_DETAIL_BOX__createNewList");
            f_hideBox("ITEM_DETAIL_BOX__saveToList");
        } else {
            f_hideBox("ITEM_DETAIL_BOX__createNewList");
            f_showBox("ITEM_DETAIL_BOX__saveToList");
        }

    }

    function f_hideBox(boxid) {
        //  document.getElementById(boxid).style.display = 'none';
        if (document.getElementById(boxid) != null) {
            document.getElementById(boxid).style.display = 'none';
        }
    }

    function f_hideAll() {
        f_hideBox("ITEM_DETAIL_BOX__selectList");
        f_hideBox("ITEM_DETAIL_BOX__createNewList");
        f_hideBox("ITEM_DETAIL_BOX__messageText");
    }

    function f_showBox(boxid) {
        if (document.getElementById(boxid) != null) {
            document.getElementById(boxid).style.display = 'block';
            f_hideBox("ITEM_DETAIL_BOX__messageText");
        }
    }


    // ]]>
</script>
<%
    String storeDir = ClwCustomizer.getStoreDir();
    ShoppingCartData shoppingCart = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
	CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    AccountData accD = appUser.getUserAccount();
	String showMyShoppingLists = accD.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_MY_SHOPPING_LISTS);
    SiteData site = null;
    boolean viewAddInvCart = false;
    if (shoppingCart != null) {
        site = shoppingCart.getSite();
        if (site != null) {
            viewAddInvCart
                    = site.hasModernInventoryShopping() && site.hasInventoryShoppingOn();
        }
    }

	boolean allowOrderInventoryItems = true;
	if (ShopTool.isModernInventoryShopping(request) && !ShopTool.hasDiscretionaryCartAccessOpen(request) && session.getAttribute(Constants.INVENTORY_SHOPPING_CART) != null) {

		if(appUser.getUserAccount().getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_ORDER_INV_ITEMS).equals("false")){
				allowOrderInventoryItems = false;
		}
	}
    boolean isPhysicalCartAvailable = ShopTool.isPhysicalCartAvailable(request);

%>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>

<bean:define id="theForm" name="SHOP_FORM" type="com.cleanwise.view.forms.UserShopForm"/>

<%
    String source = request.getParameter("source");
    if (source == null) source = "";
    int itemId = theForm.getItemId();
    String addToCartUrl = "/store/shop.do";
%>
<html:form name="SHOP_FORM" action="<%=addToCartUrl%>">
<html:hidden name="SHOP_FORM" property="itemId" value="<%=\"\"+itemId%>"/>
<logic:present name="SHOP_FORM" property="itemDetail">

<!-- item picture and long description -->
<bean:define id="item" name="SHOP_FORM" property="itemDetail"
             type="com.cleanwise.service.api.value.ShoppingCartItemData"/>

<table border="0" cellpadding="0" cellspacing="0" width="100%>">

<tr>
<td align="center" valign="top" style="padding-left:15px" width="40%">
<table>
<tr>
    <td><% String image = item.getProduct().getImage();
        if (image != null && image.trim().length() > 0) {
    %>
        <img width="180" height="180" src="/<%=storeDir%>/<%=item.getProduct().getImage()%>">
        <% } else { %>
        <img width="180" height="180" src="/<%=storeDir%>/<%="/en/images/noManXpedxImg.gif"%>"/>
        <% } %></td>
</tr>
<tr><td>&nbsp;</td></tr>
<tr>
    <td>
        <table width="100%">
		<%
		String qtyV = theForm.getQuantityDetail();
		if(qtyV.equals("0")){
			qtyV="";
		}
		%>
           <tr>
             <td align="center">
                <%if (item.getMaxOrderQty() >= 0) {%>
                <app:storeMessage key="shoppingItems.text.maxOrderQtyDetail"/>
                :
                <%=item.getMaxOrderQty()%>
                <%}%>  &nbsp;
             </td>
             <td align="center">
                <logic:equal name="SHOP_FORM" property="allowPurchase" value="true">
				<%
				if(!isPhysicalCartAvailable){ //if in physical inventory then dont show qty to order field.
				if(!item.getIsaInventoryItem()){%>
					<app:storeMessage key="shoppingItems.text.QTY"/>:
                    <html:text name="SHOP_FORM"
                       property="quantityDetail"
                       value="<%=qtyV%>"
                       size="3"/>
                    <%=Utility.strNN(item.getProduct().getUom())%>
				<%}else{
				    if ((item.getIsaInventoryItem() && allowOrderInventoryItems) || isPhysicalCartAvailable) { %>
                    <app:storeMessage key="shoppingItems.text.QTY"/>:
                    <html:text name="SHOP_FORM"
                       property="quantityDetail"
                       value="<%=qtyV%>"
                       size="3"/>
                    <%=Utility.strNN(item.getProduct().getUom())%>
				    <%}
				}
				}
				%>
                </logic:equal>
              </td>
            </tr>
          </table>
    </td>
</tr>
<tr><td>&nbsp;</td></tr>
<tr>
<td>
<logic:equal name="SHOP_FORM" property="allowPurchase" value="true">
<table>

	<tr align="center">

    <td align="center">
         <table cellpadding="0" cellspacing="0" border="0">

            <tr>
			<td>
				<table cellpadding="0" cellspacing="0" border="0">
				<tr>
				<% if (!isPhysicalCartAvailable) {
					if(!item.getIsaInventoryItem() || (ShopTool.isRegularCart(request,session))){
				%>
                    <!----   itemToCart -->
					<app:xpedxButton label='global.label.addToCart'
					url="#"
					onClick='actionMultiSubmit("hiddenAction", "addItemToActiveXpedxCart");'
					/>
                    <td>&nbsp;&nbsp;</td>
					<app:xpedxButton label='global.action.label.checkout'
					url="#"
					onClick='actionMultiSubmit("hiddenAction", "addItemToActiveXpedxCartViewCart");'
					/>
					<%
					}
					} else {%>
					<td>
					<span style="color:#FF0000;" class="shoppingCartButton">
          				<app:storeMessage key="shoppingCart.text.physicalCart"/>
        			</span>
        </td>

					<% } %>
				</tr>
				</table>
            </td>
            </tr>

		</table>
    </td>
    </tr>


            <tr>
			<td>
				<%if (showMyShoppingLists.equals("true")) { %>
					<table cellpadding="0" cellspacing="0" border="0">
						<tr><td><img src="<%=ClwCustomizer.getSIP(request,"spacer.gif")%>" border="0" height="4"></td></tr>
						<tr>
							<app:xpedxButton label='shoppingCart.text.addCart'
							url="#"
							onClick='f_showBox("ITEM_DETAIL_BOX__selectList");'
							/>
						</tr>
					</table>
				<%}%>
			</td>
			</tr>

<tr>
<td align="center">
<table align="center" border="0" cellpadding="0" cellspacing="0">
<tr>
<td>
<table border="0" cellpadding="0" cellspacing="0">
       <%--Confirmation/Errors/Warnings message Area--%>
    <%
        String messCd = "";
        String messStyle = "color:#FFFFFF;";
        String mess = "&nbsp;";
        if (ShopTool.requestContainsErrors(request)) {
            messCd = "errors";
            messStyle = "color:#FF0000; white-space: normal; ";
        } else if ((ShopTool.cartContainsWarnings(request))) {
            messCd = "warnings";
            messStyle = "color:#FF0000; white-space: normal;";
        } else if (Utility.isSet(((UserShopForm) theForm).getConfirmMessage())) {
            messCd = "confirmation";
            messStyle = "color:#003399; ";
            mess = ((UserShopForm) theForm).getConfirmMessage();
        }
    %>
    <tr>
        <td align="center">
            <div id="ITEM_DETAIL_BOX__messageText" style="visibility:visible">
                <table  border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td align="center" style="<%=messStyle%>">
                            <%if (messCd.equals("errors")) {%>
                            <html:errors/>
                            <%} else if (messCd.equals("warnings")) {%>
                            <jsp:include flush='true'
                                         page='<%=ClwCustomizer.getStoreFilePath(request,"f_shopping_msgs.jsp")%>'/>
                            <%} else {%>
                            <b><%=mess%></b>
                            <%} %>
                        </td>

                    </tr>
                </table>
            </div>
        </td>
    </tr>
    <%-------------------------------------%>
<tr>
<td align="center">
<div id="ITEM_DETAIL_BOX__selectList" style="display:none">

<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0">
    <tr>
        <td>
            <table>
                <tr>
                    <td>
                        <!-- Order guide select section -->

                        <html:select name="SHOP_FORM"
                                     property="selectedShoppingListId"
                                     onchange="changeOrderGuideOp(this,'-2')">
                            <html:option value="-1">
                                <app:storeMessage
                                        key="shoppingCart.text.selectList"/>
                            </html:option>
                            <html:option value="-2">
                                <app:storeMessage
                                        key="shoppingCart.text.createNewList"/>
                            </html:option>
                            <logic:present name="SHOP_FORM"
                                           property="userOrderGuides">
                                <logic:iterate id="og" name="SHOP_FORM"
                                               property="userOrderGuides"
                                               type="com.cleanwise.service.api.value.OrderGuideData">
                                    <html:option
                                            value="<%=String.valueOf(((OrderGuideData)og).getOrderGuideId())%>"><%=
                                        ((OrderGuideData) og).getShortDesc()%>
                                    </html:option>
                                </logic:iterate>
                            </logic:present>
                        </html:select>
                    </td>
                    <td>
                        <div id="ITEM_DETAIL_BOX__saveToList" style="display:none">
                            <table border="0" cellpadding="0"
                                   cellspacing="0">
                                <tr>
                                    <td align="right" valign="middle"><img
                                            src='<%=ClwCustomizer.getSIP(request,"buttonLeft.png")%>'
                                            border="0"></td>
                                    <td align="center" valign="middle" nowrap
                                        class="xpdexGradientButton">
                                        <a class="xpdexGradientButton" href="#"
                                           onclick="actionMultiSubmit('hiddenAction', 'addItemToOrderGuide','updateOrderGuide');">
                                            <app:storeMessage
                                                    key="global.action.label.save"/>

                                        </a>
                                    </td>
                                    <td align="left" valign="middle"><img
                                            src='<%=ClwCustomizer.getSIP(request,"buttonRight.png")%>'
                                            border="0"></td>
                                </tr>
                            </table>
                        </div>
                    </td>
                </tr>
            </table>

        </td>

    </tr>
    <tr>
        <td>
            <div id="ITEM_DETAIL_BOX__createNewList" style="display:none">
                <table  border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td>

                            <html:text name="SHOP_FORM" property="orderGuideName"
                                       maxlength="30"/>
                        </td>
                        <td>&nbsp;</td>
                        <td>
                            <table align="right" border="0" cellpadding="0"
                                   cellspacing="0">
                                <tr>
                                    <td align="right" valign="middle"><img
                                            src='<%=ClwCustomizer.getSIP(request,"buttonLeft.png")%>'
                                            border="0"></td>
                                    <td align="center" valign="middle" nowrap
                                        class="xpdexGradientButton">
                                        <a class="xpdexGradientButton" href="#"
                                           onclick="actionMultiSubmit('hiddenAction', 'addItemToOrderGuide','saveOrderGuide');">
                                            <app:storeMessage key="global.action.label.save"/>
                                        </a>
                                    </td>
                                    <td align="left" valign="middle"><img
                                            src='<%=ClwCustomizer.getSIP(request,"buttonRight.png")%>'
                                            border="0"></td>
                                </tr>
                            </table>
                        </td>
                    </tr>
                </table>
            </div>
        </td>
    </tr>
</table>
</div>

</td>
</tr>
</table>
</td>
</tr>
</table>
</td>
</tr>
</table>
</logic:equal>
</td>
</tr>
</table>

</td>
<td valign="top" style="padding-left:5px">
    <table align="left" valign="top" width="250">
        <tr>
            <td class="itemDetailShortDesc">
                <% String skuDesc = item.getProduct().getCustomerProductShortDesc();
                    if (skuDesc == null || skuDesc.trim().length() == 0) {
                        skuDesc = item.getProduct().getShortDesc();
                    }
                %> <b><%=Utility.strNN(skuDesc)%></b>
            </td>

        </tr>
        <tr>
            <td>
                <bean:write name="item" property="product.longDesc"/>
            </td>
        </tr>
		<tr><td>&nbsp;</td></tr>
        <tr>
            <td>
                <jsp:include page='<%=ClwCustomizer.getStoreFilePath(request,"t_itemDetailInfo.jsp")%>'>
                    <jsp:param name="formName" value="SHOP_FORM"/>
                </jsp:include>
            </td>
        </tr>

    </table>
</td>
</tr>
</table>
<% if (RefCodeNames.SHOP_UI_TYPE.B2B.equals(ShopTool.getShopUIType(request, RefCodeNames.SHOP_UI_TYPE.B2C))) {%>
	</td></tr>

</table>
<%}%>
</logic:present>
<html:hidden property="operation" value="hiddenOperation"/>
<html:hidden property="action" value="hiddenAction"/>
<html:hidden property="source" value="t_itemDetail.jsp"/>
</html:form>


