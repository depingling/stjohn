<%@ page import="java.util.Date" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.service.api.value.ShoppingCartItemData" %>
<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>
<%
    int rowIdx = 0;

    boolean inventoryShopping = ShopTool.isInventoryShoppingOn(request);
    Boolean filterVal=null;
    String itemFilter = request.getParameter("itemFilter");
    String keyName=  request.getParameter("keyName");
    if(keyName==null)
    {
        keyName="shop.og.table.header.title.regular";
    }
    if (itemFilter != null && "regular".equals(itemFilter)) {
        filterVal = Boolean.FALSE;
    } else if (itemFilter != null && "inventory".equals(itemFilter)) {
        filterVal = Boolean.TRUE;
    }

    int colIdx;
    int TABIDX = 0;
    boolean firstrow = true;
    String prevCategory = "";
    String thisItemCategory = "";
    String jspFormName = request.getParameter("jspFormName");
    int colCount = Integer.parseInt(request.getParameter("colCount"));
%>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<bean:define id="theForm" name="<%=jspFormName%>"  type="com.cleanwise.view.forms.UserShopForm"/>

<logic:iterate id="item" name="SHOP_FORM" property="cartItems"
               offset="0" indexId="kidx"
               type="com.cleanwise.service.api.value.ShoppingCartItemData">

<bean:define id="itemId" name="item" property="product.productId" type="java.lang.Integer" />
<bean:define id="quantityEl" value="<%=\"quantityElement[\"+kidx+\"]\"%>"/>
<bean:define id="onhandEl" value="<%=\"onhandElement[\"+kidx+\"]\"%>"/>
<bean:define id="itemIdsEl" value="<%=\"itemIdsElement[\"+kidx+\"]\"%>"/>
<%colIdx=0;%>
<% if(filterVal==null || ((ShoppingCartItemData)item).getIsaInventoryItem()==filterVal.booleanValue()) { %>

<%if(firstrow) {%>
<tr><td class="shopcategory"></td><td class="shopchartheadTitle" colspan="<%=colCount-1%>"><app:storeMessage key="<%=keyName%>"/></td></tr>
<%}%>

<% if (item.getIsaInventoryItem() == false) { %>

<% if(theForm.getOrderBy()==Constants.ORDER_BY_CATEGORY) { %>
<%
    thisItemCategory = item.getCategoryPath();
    if ( null == thisItemCategory ) {
        thisItemCategory = "";
    }
%>
<% if (firstrow) { %>


<tr>
    <td colspan="<%=colCount%>" class="shopcategory">&nbsp;&nbsp;&nbsp;&nbsp;
        <bean:write name="item" property="categoryPath"/></td>
    <% prevCategory = thisItemCategory; %>

</tr>
<%
    firstrow = false;
} else if(! prevCategory.equals(thisItemCategory)) { %>
<% prevCategory = thisItemCategory; %>
<tr>
    <td colspan="<%=colCount%>" class="shopcategory">&nbsp;&nbsp;&nbsp;&nbsp;
        <bean:write name="item" property="categoryPath"/></td>
</tr>
<% } %>
<% } %>
<% } else if (item.getIsaInventoryItem()) { %>

        <% if(theForm.getOrderBy()==Constants.ORDER_BY_CATEGORY) {

            thisItemCategory = item.getCategoryPath();
            if ( null == thisItemCategory ) {
                thisItemCategory = "";
            }
        %>
        <% if (firstrow) { %>
        <tr>
            <td colspan="<%=colCount%>" class="shopcategory">&nbsp;&nbsp;&nbsp;&nbsp;
                <bean:write name="item" property="categoryPath"/></td>
            <% prevCategory = thisItemCategory; %>
        </tr>
        <%
            firstrow = false;
        } else if(! prevCategory.equals(thisItemCategory)) { %>
        <% prevCategory = thisItemCategory;%>
        <tr>
            <td colspan="<%=colCount%>" class="shopcategory">&nbsp;&nbsp;&nbsp;&nbsp;
                <bean:write name="item" property="categoryPath"/></td>
        </tr>
        <% } %>

        <% } }%>


<tr bgcolor="<%=ClwCustomizer.getEvenRowColor(request.getSession(),rowIdx++)%>">


<%
    Date curDate = Constants.getCurrentDate();
    Date effDate = item.getProduct().getEffDate();
    Date expDate = item.getProduct().getExpDate();
    if (effDate != null && effDate.compareTo(curDate) <= 0 &&
            (expDate == null || expDate.compareTo(curDate) > 0)) {
%>
<% if (inventoryShopping && item.getIsaInventoryItem() ) { %>
<td>
<span class="inv_item">
i
<% if (ShopTool.getCurrentSite(request).isAnInventoryAutoOrderItem(item.getProduct().getProductId())) { %>
a
<% } %>
</span>
    <%colIdx++;%>
</td>

<td align="center">
    &nbsp;
    <!-- bean:write name="SHOP_FORM"
                property='<%= "cartLine[" + kidx + "].inventoryParValue" %>'/ -->
    <%colIdx++;%>
</td>


<% if (item.getInventoryParValue() > 0) { %>

<td align="center">
   
    <% if (item.getInventoryQtyIsSet()) {
        int newOnHandVal = 0;
        try {
            newOnHandVal = Integer.parseInt(item.getInventoryQtyOnHandString());
        }
        catch (Exception e) {
            newOnHandVal = 0;
        }
        item.setInventoryQtyOnHand(newOnHandVal);
    %>
    <% } %>
        &nbsp;

    <%colIdx++;%>
</td>
<% } else { %>
<td align="center">-<%colIdx++;%></td>

<% } %>
<%} else {%>
<td>&nbsp;<%colIdx++;%></td>
<td>&nbsp;<%colIdx++;%></td>
<td>&nbsp;<%colIdx++;%></td>
<%}%>

<logic:equal name="SHOP_FORM" property="allowPurchase" value="true">
    <td>
        <html:text name="SHOP_FORM"
                   property='<%= "cartLine[" + kidx + "].quantityString" %>'
                   size="3"
                   tabindex='<%=String.valueOf(TABIDX + 10)%>'
                   styleId='<%="IDX_" + TABIDX%>'/>
        <%colIdx++;%>
    </td>
</logic:equal>

<logic:equal name="SHOP_FORM" property="allowPurchase" value="false">
    <td>&nbsp;<%colIdx++;%></td>
</logic:equal>
<% }
else { %>
<td colspan="<%=colIdx%>  ">
    N/A
    <%colIdx=colIdx+colIdx;%>
</td>
<% } %>
<td class="text" align="right">
    <bean:write name="item" property="actualSkuNum"/>&nbsp;
    <%colIdx++;%>
</td>
<td class="text" align="left">
    <%String itemLink = "shop.do?action=item&source=orderGuide&itemId="
            +itemId+"&qty="+item.getQuantity();
    %>
    <html:link href="<%=itemLink%>">
        <bean:write name="item" property="product.catalogProductShortDesc"/>&nbsp;
    </html:link>
    <%colIdx++;%>
</td>
<td class="text" align="center">
    <bean:write name="item" property="product.size"/>&nbsp;
    <%colIdx++;%>
</td>
<td class="text" align="center">
    <bean:write name="item" property="product.pack"/>&nbsp;<%colIdx++;%>
</td>
<td class="text" align="center">
    <bean:write name="item" property="product.uom"/>&nbsp;
    <%colIdx++;%>
</td>
<td class="text" align="center">
    <bean:write name="item" property="product.manufacturerName"/>&nbsp;
    <%colIdx++;%>
</td>
<td class="text" align="center">
    <bean:write name="item" property="product.manufacturerSku"/>&nbsp;
    <%colIdx++;%>
</td>

<logic:equal name="SHOP_FORM" property="showPrice" value="true">
    <td class="text" ><div class="fivemargin">
        <bean:define id="price"  name="item" property="price"/>
        <%=ClwI18nUtil.getPriceShopping(request,price,"<br>")%>
        <logic:equal name="item" property="contractFlag" value="true">
            <logic:equal name="SHOP_FORM" property="showWholeCatalog" value="true">
                *
            </logic:equal>
        </logic:equal>
        <%colIdx++;%>
    </td>

    <%
        java.math.BigDecimal finalLineAmount = null;

        if ( item.getIsaInventoryItem() ) {
            finalLineAmount = new java.math.BigDecimal
                    ( item.getPrice() * item.getInventoryOrderQty());
        }
        else {
            finalLineAmount =
                    new java.math.BigDecimal(item.getAmount());
        }
    %>

    <td class="text" align="center">
        <bean:define id="amount"  name="item" property="amount"/>
        <%=ClwI18nUtil.getPriceShopping(request, finalLineAmount, "<br>")%>
     <%colIdx++;%>
    </td>
</logic:equal>

<logic:equal name="SHOP_FORM" property="showPrice" value="false">
    <td class="text" align="center"><%colIdx++;%>&nbsp;</td>
</logic:equal>


</tr> <% /* End of line item */ %>

<% TABIDX++ ; %>


<!-- END: item display <%=kidx%> -->

<%}%>

 </logic:iterate>

