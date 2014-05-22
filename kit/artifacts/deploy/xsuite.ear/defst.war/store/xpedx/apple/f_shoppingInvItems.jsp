<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="com.cleanwise.view.forms.ShoppingCartForm" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<script language="JavaScript1.2">
    <!--
    function f_SetChecked(val, prop) {
        dml = document.CHECKOUT_FORM;
        len = dml.elements.length;
        var i = 0;
        for (i = 0; i < len; i++) {
            if (dml.elements[i].name == prop && dml.elements[i].disabled != true) {
                dml.elements[i].checked = val;
            }
        }
    }

    //-->
</script>

<bean:define id="theForm" name="INVENTORY_SHOPPING_CART_FORM" type="com.cleanwise.view.forms.ShoppingCartForm"
             scope="session"/>

<%

    CleanwiseUser appUser = ShopTool.getCurrentUser(request);
    String prevCategory = "";
    String tmp_itemorder = request.getParameter("orderBy");
    int orderby = 0;
    if (tmp_itemorder != null) {
        orderby = Integer.parseInt(tmp_itemorder);
    }

    String
            storeDir = ClwCustomizer.getStoreDir(),
            requestNumber = (String) session.getAttribute(Constants.REQUEST_NUM),
            IPTH = (String) session.getAttribute("pages.store.images");

    ShoppingCartData shoppingCart = ((ShoppingCartForm) theForm).getShoppingCart();

    int COL_COUNT = 11;
    int rowCount = 0;
%>

<table cellpadding="0" cellspacing="0" align="center"    width="<%=Constants.TABLEWIDTH_m2%>">

<tr>
    <% boolean inventoryShopping = true;
       boolean resaleItemsAllowed = false;
    %>
    <%
        if (resaleItemsAllowed) {
            COL_COUNT++;
        }
        if (appUser.getUserAccount().isShowSPL()) {
            COL_COUNT++;
        }
    %>

    <% if (inventoryShopping) {
        COL_COUNT = COL_COUNT + 3;
    %>

    <td width=15 align="left" class="shopcharthead">&nbsp;</td>

    <td width=30 align="center" class="shopcharthead">
         <app:storeMessage key="shoppingItems.text.par"/>
     </td>
    <% }
/* Remove the select column */
        COL_COUNT = COL_COUNT - 1;

    %>
    <%
        String showDistInventory = ShopTool.getShowDistInventoryCode(request);
        if (RefCodeNames.DIST_INVENTORY_DISPLAY.SHOW_FLAG.equals(showDistInventory) ||
                RefCodeNames.DIST_INVENTORY_DISPLAY.SHOW_QUANTITIES.equals(showDistInventory)) {
            COL_COUNT++;
    %>
    <td class="shopcharthead">
        <div class="fivemargin">
            <app:storeMessage key="shoppingItems.text.distInv"/>
        </div>
    </td>
    <% } %>
    <td class="shopcharthead">&nbsp;</td>
    <td class="shopcharthead">
        <div class="fivemargin">
            <app:storeMessage key="shoppingItems.text.ourSkuNum"/>
        </div>
    </td>

    <td class="shopcharthead">
        <div class="fivemargin">
            <app:storeMessage key="shoppingItems.text.productName"/>
        </div>
    </td>

    <td class="shopcharthead">
        <div class="fivemargin">
            <app:storeMessage key="shoppingItems.text.size"/>
        </div>
    </td>

    <td class="shopcharthead">
        <div class="fivemargin">
            <app:storeMessage key="shoppingItems.text.pack"/>
        </div>
    </td>

    <td class="shopcharthead">
        <div class="fivemargin">
            <app:storeMessage key="shoppingItems.text.uom"/>
        </div>
    </td>

    <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">

        <td class="shopcharthead">
            <div class="fivemargin">
                <app:storeMessage key="shoppingItems.text.price"/>
            </div>
        </td>

        <td class="shopcharthead">
            <div class="fivemargin">
                <app:storeMessage key="shoppingItems.text.amount"/>
            </div>
        </td>

    </logic:equal>

    <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="false">
        <td class="shopcharthead" align="center" colspan="2">&nbsp;</td>
    </logic:equal>

    <% if (appUser.getUserAccount().isShowSPL()) { %>
    <td class="shopcharthead">
        <div class="fivemargin">
            <app:storeMessage key="shoppingItems.text.spl"/>
        </div>
    </td>
    <%} %>

    <td class="shopcharthead">&nbsp;</td>


    <% if (resaleItemsAllowed) { %>
    <td class="shopcharthead">
        <div class="fivemargin">
            <font color="red">
                <app:storeMessage key="shoppingItems.text.reSaleItem"/>
            </font>
        </div>
    </td>
    <% } %>

</tr>

<tr>
    <td colspan="<%=COL_COUNT%>" class="tableoutline"><img src="/images/cw_spacer.gif" height="1"></td>
</tr>

<bean:define id="SCARTITEMS" name="INVENTORY_SHOPPING_CART_FORM" property="cartItems" type="java.util.List"/>

<%
    if (orderby == Constants.ORDER_BY_CATEGORY) {
        shoppingCart.orderByCategory(SCARTITEMS);
    }
%>


<%
    {
%>

<logic:iterate id="sciD" name="SCARTITEMS"
               offset="0" indexId="IDX"
               type="com.cleanwise.service.api.value.ShoppingCartItemData">

<bean:define id="orderNumber" name="sciD" property="orderNumber"/>
<bean:define id="itemId" name="sciD" property="product.productId" type="java.lang.Integer"/>
<bean:define id="distName" name="sciD" property="product.catalogDistributorName"/>

<% if (orderby == Constants.ORDER_BY_CATEGORY) { %>
<% if (!prevCategory.equals(sciD.getCategoryPath())) { %>
<tr>
    <td class="shopcategory" colspan="<%=COL_COUNT%>">

        <table cellpadding="0" cellspacing="0" align="center"
               width="<%=Constants.TABLEWIDTH_m4%>">
            <tr>
                <td class="shopcategory">
                    <bean:write name="sciD" property="categoryPath"/>
                </td>
                <td class="shopcategory" align="right">
                    <% if (sciD.getProduct().getCostCenterId() > 0) { %>
                    <%= sciD.getProduct().getCostCenterName() %>
                    <% } %>
                </td>
            </tr>
        </table>
    </td>
</tr>
<%
        }
        prevCategory = sciD.getCategoryPath();
    } %>

<tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,rowCount++)%>">

<%
    Date
            curDate = Constants.getCurrentDate(),
            effDate = sciD.getProduct().getEffDate(),
            expDate = sciD.getProduct().getExpDate();


    if (effDate != null && effDate.compareTo(curDate) <= 0 &&
            (expDate == null || expDate.compareTo(curDate) > 0)) { %>

<%
    if (inventoryShopping) {
        if (sciD.getIsaInventoryItem()) { %>

<td width=15>
       <span class="inv_item">
        i
        <% if (sciD.getAutoOrderEnable()) { %>
        a
        <% } %>
      </span>
</td>
<td width=30 align="center" class="invqty_box"><%=sciD.getInventoryParValue()%>
</td>
<%
    String onHandQtyS = sciD.getInventoryQtyOnHandString();
    //if(onHandQtyS.trim().length()==0) onHandQtyS =
    //       ClwI18nUtil.getMessage(request,"shoppingCart.text.NA",null);
%>
<%--
<td>
<% String thumbnail=sciD.getProduct().getThumbnail();
       if(thumbnail!=null && thumbnail.trim().length()>0) {
    %>
      <img width="50" height="50" src="/<%=storeDir%>/<%=thumbnail%>">
    <% } else { %>
      &nbsp;
    <% } %>
</td>
--%>
<% } else { //inventory shopping is on but this is not an inventoy item%>
<td width=30 align="center" class="invqty_box">-</td>
<td width=30 align="center">-</td>
<% }//end sciD.getIsaInventoryItem()
} //end inventoryShopping%>

<% Integer lineQty = new Integer(sciD.getQuantity());
    String emptyQty = "";%>
<td align="center">
    <logic:equal name="sciD" property="duplicateFlag" value="false">

        <%=lineQty.intValue() == 0 ? emptyQty : lineQty.toString()%>

    </logic:equal>

    <logic:equal name="sciD" property="duplicateFlag" value="true">
        <html:text name="INVENTORY_SHOPPING_CART_FORM" property='<%= "cartLine[" + IDX + "].quantityString" %>'
                   readonly="true" styleClass="customerltbkground" size="3"/>
    </logic:equal>

    <% } else { %>       N/A     <% } %>
</td>
<!-- Distributor inventory Quantity -->
<%
    String distInvQtyS = "-";
    if (RefCodeNames.DIST_INVENTORY_DISPLAY.SHOW_FLAG.equals(showDistInventory)) {
        int distInvQty = sciD.getDistInventoryQty();
        if (distInvQty == 0) distInvQtyS = ClwI18nUtil.getMessage(request, "global.text.u", null);
        if (distInvQty > 0) distInvQtyS = ClwI18nUtil.getMessage(request, "global.text.a", null);

%>
<td class="text">
    <div><%=distInvQtyS%>&nbsp;</div>
</td>
<% } %>
<%
    if (RefCodeNames.DIST_INVENTORY_DISPLAY.SHOW_QUANTITIES.equals(showDistInventory)) {
        int distInvQty = sciD.getDistInventoryQty();
        if (distInvQty >= 0) distInvQtyS = String.valueOf(distInvQty);
%>
<td class="text">
    <div><%=distInvQtyS%>&nbsp;</div>
</td>
<% } %>

<td class="text">
    <div>
            <bean:write name="sciD" property="actualSkuNum"/>&nbsp;
</td>
<td class="text">
    <div>
        <%
            String itemLink = "shop.do?action=item&source=shoppingCart&itemId="
                    + itemId + "&qty=" + sciD.getQuantity();
        %>
        <html:link href="<%=itemLink%>" tabindex='<%=String.valueOf(IDX.intValue() + 1000)%>'>
            <bean:write name="sciD" property="product.catalogProductShortDesc"/>
            &nbsp;
        </html:link>
    </div>
</td>
<td class="text">
    <div>
        <bean:write name="sciD" property="product.size"/>
        &nbsp;</div>
</td>
<td class="text">
    <div>
        <bean:write name="sciD" property="product.pack"/>
        &nbsp;</div>
</td>
<td class="text">
    <div>
        <bean:write name="sciD" property="product.uom"/>
        &nbsp;</div>
</td>

<td align="right" class="text">
    <div>
        <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
            <bean:define id="price" name="sciD" property="price"/>
            <%=ClwI18nUtil.getPriceShopping(request, price, "<br>")%>
            <logic:equal name="sciD" property="contractFlag" value="true">
                <logic:equal name="<%=Constants.APP_USER%>" property="showWholeCatalog"
                             value="true">*
                </logic:equal>
            </logic:equal>
        </logic:equal>

        <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="false">
            &nbsp;
        </logic:equal>
    </div>
</td>


<% BigDecimal finalLineAmount = new BigDecimal(sciD.getAmount()); %>
<td align="right" class="text">
    <div>
        <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
            <%=ClwI18nUtil.getPriceShopping(request, finalLineAmount, "<br>")%>
        </logic:equal>
        <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="false">
            &nbsp;
        </logic:equal>
    </div>
</td>

<% if (appUser.getUserAccount().isShowSPL()) { %>
<td align="center" class="text">
    <div>
        <logic:present name="sciD" property="product.catalogDistrMapping.standardProductList">
            <bean:define id="spl" name="sciD" property="product.catalogDistrMapping.standardProductList"
                         type="java.lang.String"/>
            <%if (com.cleanwise.service.api.util.Utility.isTrue(spl)) {%>
            <app:storeMessage key="shoppingItems.text.y"/>
            <%} else {%>
            <app:storeMessage key="shoppingItems.text.n"/>
            <%}%>
        </logic:present>
        <logic:notPresent name="sciD" property="product.catalogDistrMapping.standardProductList">
            <app:storeMessage key="shoppingItems.text.n"/>
        </logic:notPresent>
    </div>
</td>
<%} %>

<td class="text">&nbsp;</td>

<%
    boolean itemIsResale = sciD.getReSaleItem();
%>

<% if (resaleItemsAllowed) { %>
<td class="text" align="center">
    <div>
        <%if (itemIsResale) {%>Y<%} else {%>N<%}%>
    </div>
</td>
<% } %>


<logic:equal name="sciD" property="duplicateFlag" value="true">
    <td class="text">
        <div>
            <%=ClwI18nUtil.getPriceShopping(request, finalLineAmount, "<br>")%>
        </div>
    </td>
    <td>Dupl</td>
</logic:equal>


</tr>


</logic:iterate>

<%
    }
%>

<script type="text/javascript" language="JavaScript">
    <!--
    var ix = document.getElementById("IDX_0");
    if (ix != null) {
        ix.focus();
    }
    // -->
</script>

</table>

