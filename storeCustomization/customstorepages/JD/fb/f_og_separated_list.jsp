<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Locale" %>


<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<html:form name="SHOP_FORM" action="/store/shop.do">
<bean:define id="theForm" name="SHOP_FORM" type="com.cleanwise.view.forms.UserShopForm"/>
<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<%
    String numberOfIventoryItemsStr = request.getParameter("numberOfIventoryItems");
    int numberOfIventoryItems = Integer.parseInt(numberOfIventoryItemsStr);

    String numberOfRegularItemsStr = request.getParameter("numberOfRegularItems");
    int numberOfRegularItems = Integer.parseInt(numberOfRegularItemsStr);


    String TABIDX_STR = request.getParameter("TABIDX");
    int TABIDX = Integer.parseInt(TABIDX_STR);

    ShoppingCartData shoppingCart = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
    boolean inventoryShopping = ShopTool.isInventoryShoppingOn(request);

    String recalc_img = IMGPath + "/b_recalculate.gif";
    String upd_inv_img = IMGPath + "/b_updateinventory.gif";

    String prevCategory = "", thisItemCategory = "";
    boolean firstrow = true;
    int rowIdx = 0;

    String itemsString =ClwI18nUtil.getShoppingItemsString(request,shoppingCart);

%>
<% if (inventoryShopping && numberOfIventoryItems > 0) { %>
<app:commonDefineProdColumnCount
    id="colCountInt"
    viewOptionEditCartItems="true"
    viewOptionQuickOrderView="false"
    viewOptionAddToCartList="true"
    viewOptionOrderGuide="true"
    viewOptionShoppingCart="false"
    viewOptionCheckout="false"
    viewOptionInventoryList="true"

/>

<%int numCols = ((Integer)pageContext.getAttribute("colCountInt")).intValue();%>

<table cellpadding="0" cellspacing="0" align="center"
  class="tbstd" width="<%=Constants.TABLEWIDTH%>" >
  <tr>
<td >
</td>
<td>
<% if (theForm.getAppUser().getAllowPurchase() ) { %>
<a href="#" id="updinv" class="linkButton" onclick="actionMultiSubmit('BBBBBBB', 'update_inventory');"
  ><img src="<%=upd_inv_img%>" border="0"/><app:storeMessage key="global.label.updateInventory"/></a>
<% } %>

</td>
<td>
  <a href="../store/shoppingcart.do" class="linkButton"><img src="<%=IMGPath%>/b_viewcart.gif"
    border="0"><app:storeMessage key="global.label.shoppingCart"/></a>
</td>
<td valign="middle">
<%=itemsString%>
</td>
<td >
  <logic:notEqual name="shoppingCart" property="newItemsQty" value="0">
  <img src="<%=IMGPath%>/new.gif"/>&nbsp;<%=ClwI18nUtil.getNewShoppingItemsString(request,shoppingCart)%>
<%  shoppingCart.clearNewItems(); %>
  </logic:notEqual>


  <logic:equal name="SHOP_FORM" property="allowPurchase" value="true">
    <a href="../store/checkout.do" class="linkButton"><img src=<%=IMGPath%>/b_checkout.gif
      border=0><app:storeMessage key="global.action.label.checkout"/></a>
  </logic:equal>

</td>
</tr>
</table>


<table cellpadding="1" cellspacing="0" align="center"
  class="tbstd" width="<%=Constants.TABLEWIDTH%>" >
  <%--
<tr>

<td class="shopcharthead"><br></td>
<td class="shopcharthead" align="center"><app:storeMessage key="shop.og.table.header.par"/></td>
<td class="shopcharthead" align="center"><app:storeMessage key="shop.og.table.header.onHand"/></td>

<logic:equal name="SHOP_FORM" property="allowPurchase" value="true">
<td class="shopcharthead" align="center"><app:storeMessage key="shop.og.table.header.orderQty"/></td>
</logic:equal>

<logic:equal name="SHOP_FORM" property="allowPurchase" value="false">
<td class="shopcharthead">    &nbsp;</td>
</logic:equal>

  <td class="shopcharthead" align="center"><app:storeMessage key="shop.og.table.header.ourSkuNum"/></td>
  <td class="shopcharthead"><center><app:storeMessage key="shop.og.table.header.productName"/></center></td>
  <td class="shopcharthead" align="center"><app:storeMessage key="shop.og.table.header.size"/></td>
  <td class="shopcharthead" align="center"><app:storeMessage key="shop.og.table.header.pack"/></td>
  <td class="shopcharthead" align="center"><app:storeMessage key="shop.og.table.header.uom"/></td>
  <logic:equal name="SHOP_FORM" property="showPrice" value="true">
     <td class="shopcharthead" align="center"><app:storeMessage key="shop.og.table.header.price"/></td>
     <td class="shopcharthead" align="center"><app:storeMessage key="shop.og.table.header.amount"/></td>
  </logic:equal>

  <logic:equal name="SHOP_FORM" property="showPrice" value="false">
     <td class="shopcharthead" align="center">&nbsp;</td>
  </logic:equal>

  </tr>
--%>
          <%--------------------------------- HEADER -----------------------------------------%>
           <tr>
              <app:commonDisplayProdHeader
                  viewOptionEditCartItems="true"
                  viewOptionQuickOrderView="false"
                  viewOptionAddToCartList="true"
                  viewOptionOrderGuide="true"
                  viewOptionShoppingCart="false"
                  viewOptionCheckout="false"
                  viewOptionInventoryList="true"
                  />
           </tr>
          <%-----------------------------------------------------------------------------------------%>

<logic:iterate id="item" name="SHOP_FORM" property="cartItems"
   offset="0" indexId="kidx"
   type="com.cleanwise.service.api.value.ShoppingCartItemData">

     <bean:define id="itemId" name="item" property="product.productId"
       type="java.lang.Integer" />
     <bean:define id="quantityEl" value="<%=\"quantityElement[\"+kidx+\"]\"%>"/>
     <bean:define id="onhandEl" value="<%=\"onhandElement[\"+kidx+\"]\"%>"/>
     <bean:define id="itemIdsEl" value="<%=\"itemIdsElement[\"+kidx+\"]\"%>"/>

<% if (inventoryShopping && item.getIsaInventoryItem()) { %>

<% if(theForm.getOrderBy()==Constants.ORDER_BY_CATEGORY) {

thisItemCategory = item.getCategoryPath();
if ( null == thisItemCategory ) {
  thisItemCategory = "";
}
%>
 <% if (firstrow) { %>
<tr>
<td colspan="11" class="shopcategory">&nbsp;&nbsp;&nbsp;&nbsp;
<bean:write name="item" property="categoryPath"/></td>
<% prevCategory = thisItemCategory; %>
</tr>
 <%
firstrow = false;
} else if(! prevCategory.equals(thisItemCategory)) { %>
<% prevCategory = thisItemCategory;%>
<tr>
<td colspan="11" class="shopcategory">&nbsp;&nbsp;&nbsp;&nbsp;
<bean:write name="item" property="categoryPath"/></td>
</tr>
  <% } %>

<% } %>

<%@ include file="f_oitem.jsp" %>

<% } %>
</logic:iterate>
</table>

<table cellpadding="1" cellspacing="0" align="center"
  class="tbstd" width="<%=Constants.TABLEWIDTH%>" >
    <tr>  <td>
<% if (theForm.getAppUser().getAllowPurchase() ) { %>
<a href="#" id="upd2" class="linkButton" onclick="actionMultiSubmit('BBBBBBB', 'update_inventory');"
  ><img src="<%=upd_inv_img%>" border="0"/><app:storeMessage key="global.label.updateInventory"/></a>
<% } %>
  </td>  </tr>
  <tr>  <td class="tableoutline"><img src="<%=IMGPath%>/cw_spacer.gif"
           height="3"></td>  </tr>
</table>


<% } /* End of inventory items. */ %>

<% if (numberOfRegularItems > 0) { %>
<%
    boolean
      quickOrderView = false,
      shoppingCartView = false,
      checkoutView = false,
      editCartItems = true ,
      f_showSelectCol = false;

%>

<app:commonDefineProdColumnCount
    id="colCountInt"
    viewOptionEditCartItems="<%=editCartItems%>"
            viewOptionQuickOrderView="false"
            viewOptionAddToCartList="true"
            viewOptionOrderGuide="true"
            viewOptionShoppingCart="false"
            viewOptionCheckout="false"
            viewOptionInventoryList="false"

/>

<%int numCols = ((Integer)pageContext.getAttribute("colCountInt")).intValue();%>

<table cellpadding="1" cellspacing="0" align="center"
  class="tbstd" width="<%=Constants.TABLEWIDTH%>" >

<tr>
<td >
  <logic:equal name="SHOP_FORM" property="allowPurchase" value="true">
    <% String add_img = IMGPath + "/b_addtocart.gif"; %>
      <a href="#" class="linkButton" onclick="actionMultiSubmit('BBBBBBB', 'og_addToCart');"
      ><img src="<%=add_img%>" border="0"/><app:storeMessage key="global.label.addToCart"/></a>
  </logic:equal>

</td>
<td>
<a href="shop.do?action=clear" class="linkButton">
<img src="<%=IMGPath%>/b_clearqty.gif" border="0"/><app:storeMessage key="global.label.clearQuantities"/></a>
</td>
<td>
  <a href="#" class="linkButton" onclick="actionMultiSubmit('BBBBBBB', 'og_recalc');"
  ><img src="<%=recalc_img%>" border="0"/><app:storeMessage key="global.label.recalculate"/></a>
</td>
<td>
  <a href="../store/shoppingcart.do" class="linkButton"><img src="<%=IMGPath%>/b_viewcart.gif"
    border="0"><app:storeMessage key="global.label.shoppingCart"/></a>
</td>
<td valign="top"><%=itemsString%>
  <logic:notEqual name="shoppingCart" property="newItemsQty" value="0">
<br>
<img src="<%=IMGPath%>/new.gif"/>&nbsp;<%=ClwI18nUtil.getNewShoppingItemsString(request,shoppingCart)%>
<%
shoppingCart.clearNewItems();
%>
  </logic:notEqual>
</td>


<td>
  <logic:equal name="SHOP_FORM" property="allowPurchase" value="true">
    <a href="../store/checkout.do" class="linkButton"><img src=<%=IMGPath%>/b_checkout.gif
      border=0><app:storeMessage key="global.action.label.checkout"/></a>
  </logic:equal>

</td>
</tr>
<tr><td colspan=6>&nbsp;</td></tr>
</table>


<table cellpadding="1" cellspacing="0" align="center"
  class="tbstd" width="<%=Constants.TABLEWIDTH%>" >
<%--
<tr>

<logic:equal name="SHOP_FORM" property="allowPurchase" value="true">
<td class="shopcharthead" align="center"><app:storeMessage key="shop.og.table.header.orderQty"/></td>
</logic:equal>

<logic:equal name="SHOP_FORM" property="allowPurchase" value="false">
<td class="shopcharthead">    &nbsp;</td>
</logic:equal>

<td class="shopcharthead" align="center"><app:storeMessage key="shop.og.table.header.ourSkuNum"/></td>
  <td class="shopcharthead"><center><app:storeMessage key="shop.og.table.header.productName"/></center></td>
  <td class="shopcharthead" align="center"><app:storeMessage key="shop.og.table.header.size"/></td>
  <td class="shopcharthead" align="center"><app:storeMessage key="shop.og.table.header.pack"/></td>
  <td class="shopcharthead" align="center"><app:storeMessage key="shop.og.table.header.uom"/></td>
  <logic:equal name="SHOP_FORM" property="showPrice" value="true">
     <td class="shopcharthead" align="center"><app:storeMessage key="shop.og.table.header.price"/></td>
     <td class="shopcharthead" align="center"><app:storeMessage key="shop.og.table.header.amount"/></td>
  </logic:equal>

  <logic:equal name="SHOP_FORM" property="showPrice" value="false">
     <td class="shopcharthead" align="center">&nbsp;</td>
  </logic:equal>

  </tr>
--%>

<%
/* List non-inventory items. */
 firstrow = true;
 rowIdx = 0;
 prevCategory = "";
%>


<logic:iterate id="item" name="SHOP_FORM" property="cartItems"
   offset="0" indexId="kidx"
   type="com.cleanwise.service.api.value.ShoppingCartItemData">

     <bean:define id="itemId" name="item" property="product.productId"
       type="java.lang.Integer" />
     <bean:define id="quantityEl" value="<%=\"quantityElement[\"+kidx+\"]\"%>"/>
     <bean:define id="onhandEl" value="<%=\"onhandElement[\"+kidx+\"]\"%>"/>
     <bean:define id="itemIdsEl" value="<%=\"itemIdsElement[\"+kidx+\"]\"%>"/>
<!--
     <html:hidden name="SHOP_FORM" property="<%=itemIdsEl%>" value="<%=\"\"+itemId%>"/>
-->
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
<td colspan="11" class="shopcategory">&nbsp;&nbsp;&nbsp;&nbsp;
<bean:write name="item" property="categoryPath"/></td>
<% prevCategory = thisItemCategory; %>

</tr>
 <%
firstrow = false;
} else if(! prevCategory.equals(thisItemCategory)) { %>
<% prevCategory = thisItemCategory; %>
<tr>
<td colspan="11" class="shopcategory">&nbsp;&nbsp;&nbsp;&nbsp;
<bean:write name="item" property="categoryPath"/></td>
</tr>
  <% } %>
<% } %>

<!--  include file="f_oitem.jsp"  -->

<!-- START: item display <%=kidx%> -->
<%--
<tr bgcolor="<%=ClwCustomizer.getEvenRowColor(request.getSession(),rowIdx++)%>">

<logic:equal name="SHOP_FORM" property="allowPurchase" value="true">
<td>
<html:text name="SHOP_FORM"
property='<%= "cartLine[" + kidx + "].quantityString" %>' size="3"
 tabindex='<%=String.valueOf(TABIDX + 10)%>'
 styleId='<%="IDX_" + TABIDX%>'
/>

<% TABIDX++ ; %>

</td>
</logic:equal>
<logic:equal name="SHOP_FORM" property="allowPurchase" value="false">
<td>    &nbsp;</td>
</logic:equal>

     <td class="text" align="right">
       <bean:write name="item" property="actualSkuNum"/>&nbsp;
     </td>
     <td class="text" align="left">
     <%String itemLink = "shop.do?action=item&source=orderGuide&itemId="
       +itemId+"&qty="+item.getQuantity();
     %>
     <html:link href="<%=itemLink%>">
       <bean:write name="item" property="product.catalogProductShortDesc"/>&nbsp;
     </html:link>
     </td>
     <td class="text" align="center">
       <bean:write name="item" property="product.size"/>&nbsp;
     </td>
     <td class="text" align="center">
       <bean:write name="item" property="product.pack"/>&nbsp;
     </td>
     <td class="text" align="center">
       <bean:write name="item" property="product.uom"/>&nbsp;
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
    <%=ClwI18nUtil.getPriceShopping(request,finalLineAmount,"<br>")%>
</td>
</logic:equal>

<logic:equal name="SHOP_FORM" property="showPrice" value="false">
<td class="text" align="center">&nbsp;</td>
</logic:equal>


     </tr> <% /* End of line item */ %>
--%>

     <%itemLink = "shop.do?action=item&source=orderGuide&itemId="
       +itemId+"&qty="+item.getQuantity();
       String trColor=ClwCustomizer.getEvenRowColor(request.getSession(),rowIdx++);
     %>

<tr bgcolor="<%=trColor%>">
<app:commonDisplayProd
  viewOptionShoppingCart="<%=shoppingCartView%>"
  viewOptionCheckout="<%=checkoutView%>"
  viewOptionEditCartItems="<%=editCartItems%>"
  viewOptionQuickOrderView="false"
  viewOptionAddToCartList="true"
  viewOptionOrderGuide="true"
  viewOptionInventoryList="false"
  viewOptionShoppingCart="false"
  viewOptionCheckout="false"
  name="item"
  link="<%=itemLink%>"
  index="<%=kidx%>"
  inputNameQuantity="<%= \"cartLine[\" + kidx + \"].quantityString\" %>"/>
</tr>



<!-- END: item display <%=kidx%> -->

<% } %>

   </logic:iterate>
</table>

<table cellpadding="1" cellspacing="0" align="center"
  class="tbstd" width="<%=Constants.TABLEWIDTH%>" >

  <tr>
  <td class="tableoutline"><img src="<%=IMGPath%>/cw_spacer.gif" height="3"></td>
  </tr>
</table>

<table cellpadding="1" cellspacing="0" align="center"
  class="tbstd" width="<%=Constants.TABLEWIDTH%>" >

<tr>
  <td>
  <logic:equal name="SHOP_FORM" property="allowPurchase" value="true">
    <% String add_img = IMGPath + "/b_addtocart.gif"; %>
      <a href="#" class="linkButton" onclick="actionMultiSubmit('BBBBBBB', 'og_addToCart');"
      ><img src="<%=add_img%>" border="0"/><app:storeMessage key="global.label.addToCart"/></a>
  </logic:equal>
  &nbsp;&nbsp;
  <a href="shop.do?action=clear" class="linkButton">
    <img src="<%=IMGPath%>/b_clearqty.gif" border="0"/><app:storeMessage key="global.label.clearQuantities"/></a>
  &nbsp;&nbsp;
  <a href="#" class="linkButton" onclick="actionMultiSubmit('BBBBBBB', 'og_recalc');"
    ><img src="<%=recalc_img%>" border="0"/><app:storeMessage key="global.label.recalculate"/></a>
  &nbsp;&nbsp;
  <% String refreshLink = "shop.do?action=refreshGuide&templateOrderGuideId="+theForm.getOrderGuideId(); %>
  <a href="<%=refreshLink%>" class="linkButton">
    <img src="<%=IMGPath%>/b_refresh.gif" border="0"/><app:storeMessage key="global.label.refresh"/></a>
  </td>
  <logic:equal name="SHOP_FORM" property="showPrice" value="true">
  <td class="text" align="rigth"><div class="fivemargin">
  <b><app:storeMessage key="shop.og.text.total"/><b>
     </div></td>
     <td class="text"><div class="fivemargin">
       <bean:define id="itemsAmt"  name="SHOP_FORM" property="itemsAmt"/>
       <%=ClwI18nUtil.getPriceShopping(request,itemsAmt,"<br>")%>
     </div></td>
     </logic:equal>
     <logic:notEqual name="SHOP_FORM" property="showPrice" value="true">
     <td class="text" colspan=2><div class="fivemargin">&nbsp;</div></td>
     </logic:notEqual>
  </tr>
</table>
<% } %>
<html:hidden property="action" value="BBBBBBB"/>
<html:hidden property="action" value="CCCCCCC"/>
</html:form>
