
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>

<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.service.api.value.ShoppingCartData" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>


<%
    ShoppingCartData shoppingCart = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);

    boolean inventoryShopping = Boolean.getBoolean(request.getParameter("inventoryShopping"));
    int numberOfIventoryItems = Integer.parseInt(request.getParameter("numberOfIventoryItems"));
    int numberOfRegularItems = Integer.parseInt(request.getParameter("numberOfRegularItems"));
    String jspFormName = request.getParameter("jspFormName");
    String recalc_img = IMGPath + "/b_recalculate.gif";
    int COL_COUNT=0;
    String  ogListUI = ShopTool.getOGListUI(request,RefCodeNames.INVENTORY_OG_LIST_UI.SEPARATED_LIST);
   String itemsString =ClwI18nUtil.getShoppingItemsString(request,shoppingCart);
%>
<bean:define id="theForm" name="<%=jspFormName%>"  type="com.cleanwise.view.forms.UserShopForm"/>

 <html:form name="SHOP_FORM" action="/store/shop.do">

<table cellpadding="0" cellspacing="0" align="center"  class="tbstd" width="100%" >

      <tr>
        <td> </td>
       <logic:equal name="SHOP_FORM" property="allowPurchase" value="true">
       <td>
        <% String add_img = IMGPath + "/b_addtocart.gif"; %>
        <% if (ShopTool.isModernInventoryShopping(request)
              && !ShopTool.hasDiscretionaryCartAccessOpen(request)) { %>
              <a href="#" class="linkButton" onclick="actionMultiSubmit('BBBBBBB', 'og_addToModInvCart');">
              <img src="<%=add_img%>" border="0"/><app:storeMessage key="global.label.addToInventoryCart"/></a>
        <%} else { %>
                <a href="#" class="linkButton" onclick="actionMultiSubmit('BBBBBBB', 'og_addToCart');">
                    <img src="<%=add_img%>" border="0"/>
                    <app:storeMessage key="global.label.addToDiscretionaryCart"/>
                </a>
        <%} %>
       </td>
       </logic:equal>
        <td>
            <a href="shop.do?action=clear" class="linkButton">
                <img src="<%=IMGPath%>/b_clearqty.gif" border="0"/>
                <app:storeMessage key="global.label.clearQuantities"/>
            </a>
        </td>

        <td>
            <a href="#" class="linkButton" onclick="actionMultiSubmit('BBBBBBB', 'og_recalc');">
                <img src="<%=recalc_img%>" border="0"/>
                <app:storeMessage key="global.label.recalculate"/>
            </a>

        </td>

        <% if (ShopTool.hasDiscretionaryCartAccessOpen(request)) { %>
        <td>
            <a href="../store/shoppingcart.do" class="linkButton">
                <img src="<%=IMGPath%>/b_viewcart.gif" border="0">
                <app:storeMessage key="global.label.shoppingCart"/>
            </a>
        </td>
        <td>
            <%=itemsString%>
            <logic:notEqual name="shoppingCart" property="newItemsQty" value="0">
                <img src="<%=IMGPath%>/new.gif"/>&nbsp;<%=ClwI18nUtil.getNewShoppingItemsString(request, shoppingCart)%>
                <%  shoppingCart.clearNewItems(); %>
            </logic:notEqual>

            <logic:equal name="SHOP_FORM" property="allowPurchase" value="true">
                <a href="../store/checkout.do" class="linkButton"><img src=<%=IMGPath%>/b_checkout.gif
                                                                       border=0><app:storeMessage key="global.action.label.checkout"/></a>
            </logic:equal>
        </td>
       <%} %>
    </tr>
</table>



<table cellpadding="1" cellspacing="0" align="center" class="tbstd" width="<%=Constants.TABLEWIDTH%>" >

    <tr>
        <td class="shopcharthead"><%COL_COUNT++;%><br></td>
         <%--
        <td class="shopcharthead" align="center">
        <app:storeMessage key="shop.og.table.header.orderInvQty"/>
        <%COL_COUNT++;%>
        </td>
        --%>

        <td class="shopcharthead" align="center">
        <app:storeMessage key="shop.og.table.header.maxOrderQty"/>
        <%COL_COUNT++;%>
        </td>

        <logic:equal name="SHOP_FORM" property="allowPurchase" value="true">
            <td class="shopcharthead" align="center">
            <app:storeMessage key="shop.og.table.header.orderQtyToAdd"/>
             <%COL_COUNT++;%>
            </td>
        </logic:equal>

        <logic:equal name="SHOP_FORM" property="allowPurchase" value="false">
            <td class="shopcharthead"><%COL_COUNT++;%>&nbsp;</td>
        </logic:equal>
        <td class="shopcharthead" align="center">
        <app:storeMessage key="shop.og.table.header.ourSkuNum"/>
        <%COL_COUNT++;%>
        </td>
        <td class="shopcharthead">
            <center><app:storeMessage key="shop.og.table.header.productName"/></center>
         <%COL_COUNT++;%>
         </td>
        <td class="shopcharthead" align="center">
        <app:storeMessage key="shop.og.table.header.size"/>
            <%COL_COUNT++;%>
        </td>
        <td class="shopcharthead" align="center">
        <app:storeMessage key="shop.og.table.header.pack"/>
        <%COL_COUNT++;%>
        </td>
        <td class="shopcharthead" align="center">
        <app:storeMessage key="shop.og.table.header.uom"/>
        <%COL_COUNT++;%>
        </td>
        <logic:equal name="SHOP_FORM" property="showPrice" value="true">
            <td class="shopcharthead" align="center">
            <app:storeMessage key="shop.og.table.header.price"/>
                <%COL_COUNT++;%>
            </td>
            <td class="shopcharthead" align="center">
            <app:storeMessage key="shop.og.table.header.amount"/>
                <%COL_COUNT++;%>
            </td>
        </logic:equal>

        <logic:equal name="SHOP_FORM" property="showPrice" value="false">
            <td class="shopcharthead" align="center"><%COL_COUNT++;%>
            &nbsp;</td>
        </logic:equal>

    </tr>

  <% if(RefCodeNames.INVENTORY_OG_LIST_UI.COMMON_LIST.equals(ogListUI))  {%>

     <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_oitems.jsp")%>'>
        <jsp:param name="jspFormName"value="SHOP_FORM"/>
        <jsp:param name="colCount" value="<%=COL_COUNT%>"/>
        <jsp:param name="keyName" value="shop.og.table.header.title.regular"/>
    </jsp:include>

    <%} else { %>

    <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_oitems.jsp")%>'>
        <jsp:param name="jspFormName"value="SHOP_FORM"/>
        <jsp:param name="itemFilter" value="regular"/>
        <jsp:param name="colCount" value="<%=COL_COUNT%>"/>
        <jsp:param name="keyName" value="shop.og.table.header.title.regular"/>
    </jsp:include>

    <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_oitems.jsp")%>'>
        <jsp:param name="jspFormName"value="SHOP_FORM"/>
        <jsp:param name="itemFilter" value="inventory"/>
        <jsp:param name="colCount" value="<%=COL_COUNT%>"/>
        <jsp:param name="keyName" value="shop.og.table.header.title.inventory"/>
    </jsp:include>

    <%}%>
 </table>
<html:hidden property="action" value="BBBBBBB"/>
<html:hidden property="action" value="CCCCCCC"/>

<table cellpadding="0" cellspacing="0" align="center"  class="tbstd" width="100%" >

      <tr>
        <td> </td>
        <logic:equal name="SHOP_FORM" property="allowPurchase" value="true">
            <% String add_img = IMGPath + "/b_addtocart.gif"; %>
        <td>
        <% if (ShopTool.isModernInventoryShopping(request)
              && !ShopTool.hasDiscretionaryCartAccessOpen(request)) { %>
                <a href="#" class="linkButton" onclick="actionMultiSubmit('BBBBBBB', 'og_addToModInvCart');">
                 <img src="<%=add_img%>" border="0"/><app:storeMessage key="global.label.addToInventoryCart"/></a>
            <%} else { %>
                <a href="#" class="linkButton" onclick="actionMultiSubmit('BBBBBBB', 'og_addToCart');">
                    <img src="<%=add_img%>" border="0"/>
                    <app:storeMessage key="global.label.addToDiscretionaryCart"/>
                </a>
             <%} %>
        </td>
        </logic:equal>
        <td>
            <a href="shop.do?action=clear" class="linkButton">
                <img src="<%=IMGPath%>/b_clearqty.gif" border="0"/>
                <app:storeMessage key="global.label.clearQuantities"/>
            </a>
        </td>

        <td>
            <a href="#" class="linkButton" onclick="actionMultiSubmit('BBBBBBB', 'og_recalc');">
                <img src="<%=recalc_img%>" border="0"/>
                <app:storeMessage key="global.label.recalculate"/>
            </a>

        </td>

        <td>
            <logic:notEqual name="shoppingCart" property="newItemsQty" value="0">
                <img src="<%=IMGPath%>/new.gif"/>&nbsp;<%=ClwI18nUtil.getNewShoppingItemsString(request, shoppingCart)%>
                <%  shoppingCart.clearNewItems(); %>
            </logic:notEqual>


            <logic:equal name="SHOP_FORM" property="allowPurchase" value="true">
        <% if (ShopTool.hasDiscretionaryCartAccessOpen(request)) { %>
                <a href="../store/checkout.do" class="linkButton"><img src=<%=IMGPath%>/b_checkout.gif
                                                                       border=0><app:storeMessage key="global.action.label.checkout"/></a>
            <%} %>
            </logic:equal>
        </td>
    </tr>
</table>

</html:form>
