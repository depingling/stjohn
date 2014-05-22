
<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="java.util.Locale" %>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<script language="JavaScript">
<!--
function actionMultiSubmit(actionDef, action) {
  var aaal = document.getElementsByName('action');
  for ( var i = 0; i < 1; i++ ) {
    var aaa = aaal[i];
    aaa.value = action;
    aaa.form.submit();
  }

 return false;
}

function setAndSubmit(fid, vv, value) {
  var aaa = document.forms[fid].elements[vv];
  aaa.value=value;
  aaa.form.submit();
  return false;

}
-->
</script>

<bean:define id="theForm" name="LAST_ORDER_FORM" type="com.cleanwise.view.forms.LastOrderForm"/>
<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>

<%
    boolean viewAddInvCart = false;
    CleanwiseUser appUser = ShopTool.getCurrentUser(request);
    String shoppingCartName = Constants.SHOPPING_CART;

    if (appUser != null) {
        SiteData site = appUser.getSite();
        viewAddInvCart = (appUser.readyToShop()
                && site != null
                && ShopTool.isModernInventoryCartAvailable(request)
                && session.getAttribute(Constants.INVENTORY_SHOPPING_CART) != null
                && site.hasModernInventoryShopping()
                && site.hasInventoryShoppingOn()
        );
    }

    if (viewAddInvCart) {
        shoppingCartName = Constants.INVENTORY_SHOPPING_CART;
    }
    ShoppingCartData shoppingCart = (ShoppingCartData) session.getAttribute(shoppingCartName);
    String add_img = IMGPath + "/b_addtocart.gif";
    int colCount = 11;
    if (appUser.getUserAccount().isHideItemMfg()) {
      colCount = colCount - 2;
    }
%>



<!-- taken wholesale from the estore demo -->

<table align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH%>">
<tr>
<td class="tableoutline" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
<td><jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"t_shopToolbar.jsp")%>'/></td>
<td class="tableoutline" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
</tr>
</table>
<!-- Order guide select section -->

<!-- List of items -->
<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_shopping_msgs.jsp")%>'>
    <jsp:param name="shoppingCartName" value="<%=shoppingCartName%>"/>
</jsp:include>

<table align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH%>">
<logic:equal name="LAST_ORDER_FORM" property="cartItemsSize" value="0">
<tr>
<td class="tableoutline" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
<td class="text" align="center">
  <b><app:storeMessage key="shop.lastOrder.text.noOrderFound"/></b>
</td>
<td class="tableoutline" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
</tr>
</logic:equal>

<logic:greaterThan name="LAST_ORDER_FORM" property="cartItemsSize" value="0">

<tr>
<td class="tableoutline" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
<td>
<html:form styleId='lot' action="/store/lastOrder.do?action=lastOrderSubmit">
  <table align="center" border="0" cellpadding="0" cellspacing="0" width="98%">

  <tr bgcolor="#F0FFFF">
  <td colspan="<%=colCount%>" class="text" align="center">
  <b><app:storeMessage key="shop.lastOrder.text.orderBy"/></b>&nbsp;
    <html:select name="LAST_ORDER_FORM" property="orderBy" onchange="setAndSubmit(0,'command','sort');">
          <html:option value="<%=\"\"+Constants.ORDER_BY_CATEGORY%>">
            <app:storeMessage key="shop.lastOrder.text.category"/>
          </html:option>
          <html:option value="<%=\"\"+Constants.ORDER_BY_CUST_SKU%>">
            <app:storeMessage key="shop.lastOrder.text.ourSku#"/>
          </html:option>
          <html:option value="<%=\"\"+Constants.ORDER_BY_NAME%>">
            <app:storeMessage key="shop.lastOrder.text.productName"/>
          </html:option>
        </html:select>
  </td>
  </tr>
  <tr>
  <td colspan="<%=colCount%>" class="tableoutline"><img src="/images/cw_spacer.gif" height="3"></td>  </tr>
  </tr>
  <!-- page contral bar -->
  <logic:equal name="<%=Constants.APP_USER%>" property="allowPurchase" value="true">
    <tr>
    <td colspan="<%=colCount%>" class="text">
    <b><app:storeMessage key="shop.lastOrder.text.lastOrderDate:"/></b>&nbsp;&nbsp;
    <bean:define id="orderDate" name="LAST_ORDER_FORM" property="order.originalOrderDate" type="Date"/>
    <%=ClwI18nUtil.formatDateInp(request,orderDate)%>
    &nbsp;&nbsp;&nbsp;&nbsp;
    <b><app:storeMessage key="shop.lastOrder.text.placedBy:"/></b>&nbsp;&nbsp;
          <bean:write name="LAST_ORDER_FORM" property="orderPlacedBy"/> (<bean:write name="LAST_ORDER_FORM" property="order.addBy"/>)
    </td>
    </tr>
    <tr>
    <td colspan="<%=colCount%>">
    <table border="0" cellpadding="2" cellspacing="0">
    <tr>
    <td>
    <%if (viewAddInvCart) {%>
        <a href="#" class="linkButton" onclick="setAndSubmit('lot','command','lot_addToModInvCart');">
           <img src="<%=add_img%>" border="0"/>
           <app:storeMessage key="global.label.addToInventoryCart"/>
        </a>
    <%} else {%>
        <a href="#" class="linkButton" onclick="setAndSubmit('lot','command','addToCart');"
         ><img src='<%=add_img%>' border="0"/>
        <app:storeMessage key="global.label.addToCart"/></a>
    <% } %>
    </td>
    <td>
    <a href='lastOrder.do?action=lastOrderClear' class="linkButton"
    ><img src="<%=IMGPath%>/b_clearqty.gif" border="0"/><app:storeMessage key="global.label.clearQuantities"/></a>
    </td>

    <%if (viewAddInvCart) {%>
    <td>
        <a href="../store/scheduledCart.do?action=showScheduledCart" class="linkButton"><img src="<%=IMGPath%>/b_viewcart.gif"
        border="0"><app:storeMessage key="global.label.inventoryShoppingCart"/></a>
    </td>
    <% } else { %>
        <td>
        <a href="../store/shoppingcart.do" class="linkButton"><img src="<%=IMGPath%>/b_viewcart.gif"
        border="0"><app:storeMessage key="global.label.shoppingCart"/></a>
        </td>

       <td>
        <%=ClwI18nUtil.getShoppingItemsString(request,shoppingCart)%>
        <logic:notEqual name="shoppingCart" property="newItemsQty" value="0">
        <br>
        <img src="<%=IMGPath%>/new.gif"/>&nbsp;<%=ClwI18nUtil.getNewShoppingItemsString(request,shoppingCart)%>
        <%
        shoppingCart.clearNewItems();
        %>
        </logic:notEqual>
        </td>

        <td>
        <a href="../store/checkout.do" class="linkButton"><img src=<%=IMGPath%>/b_checkout.gif
          border=0><app:storeMessage key="global.action.label.checkout"/></a>
        </td>
    <% } %>
   </tr>
   </table>
   </td>
    </tr>
  </logic:equal>
  <tr>
  <!-- Column heanders -->
  <td colspan="<%=colCount%>" class="tableoutline"><img src="/images/cw_spacer.gif" height="1"></td>
  </tr>
  <tr>
  <td class="shopcharthead"><div class="fivemargin">
    <logic:equal name="<%=Constants.APP_USER%>" property="allowPurchase" value="true">
      <app:storeMessage key="shoppingItems.text.qty"/>
    </logic:equal>
    <logic:equal name="<%=Constants.APP_USER%>" property="allowPurchase" value="false">
    &nbsp;
    </logic:equal>
   </div></td>
  <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.ourSkuNum"/></div></td>
  <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.productName"/></div></td>
  <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.size"/></div></td>
  <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.pack"/></div></td>
  <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.uom"/></div></td>
  <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.color"/></div></td>
  <% if (!appUser.getUserAccount().isHideItemMfg()) {    %>
         <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.manufacturer"/></div></td>
       <td class="shopcharthead"><div class="fivemargin"><app:storeMessage key="shoppingItems.text.mfgSkuNum"/></div></td>
  <% } %>
  <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
    <td class="shopcharthead"><div class="fivemargin">
      <app:storeMessage key="shoppingItems.text.price"/>
    </div></td>
    <td class="shopcharthead"><div class="fivemargin">
      <app:storeMessage key="shoppingItems.text.amount"/>
    </div></td>
 </logic:equal>
 <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="false">
   <td  colspan="2" class="shopcharthead"><div class="fivemargin">
      &nbsp;
    </div></td>
 </logic:equal>

  </tr>
  <tr>
  <!-- Main table -->
  <td colspan="<%=colCount%>" class="tableoutline"><img src="/images/cw_spacer.gif" height="1"></td>  </tr>
  </tr>

  <logic:iterate id="item" name="LAST_ORDER_FORM" property="cartItems"
   indexId="IDX"   type="com.cleanwise.service.api.value.ShoppingCartItemData">
     <bean:define id="itemId" name="item" property="product.productId"/>
     <bean:define id="quantityEl" value="<%=\"quantityElement[\"+IDX+\"]\"%>"/>
     <bean:define id="itemIdsEl" value="<%=\"itemIdsElement[\"+IDX+\"]\"%>"/>
     <html:hidden name="LAST_ORDER_FORM" property="<%=itemIdsEl%>" value="<%=\"\"+itemId%>"/>
     <% if(theForm.getOrderBy()==Constants.ORDER_BY_CATEGORY) { %>
     <% if(theForm.isCategoryChanged(((Integer)IDX).intValue())) { %>
       <tr>
       <td class="shopcategory" colspan = "<%=colCount%>"><bean:write name="item" property="categoryPath"/></td>
       </tr>
     <% } %>
     <% } %>
     <tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)IDX)%>">
     <td class="text"><div class="fivemargin">
     <% Date curDate = Constants.getCurrentDate();
        Date effDate = item.getProduct().getEffDate();
        Date expDate = item.getProduct().getExpDate();
        if(effDate != null && effDate.compareTo(curDate)<=0 &&
          (expDate==null || expDate.compareTo(curDate)>0)) {
     %>
      <logic:equal name="<%=Constants.APP_USER%>" property="allowPurchase" value="true">
        <html:text name="LAST_ORDER_FORM" property="<%=quantityEl%>" size="3"/>
      </logic:equal>
      <logic:equal name="<%=Constants.APP_USER%>" property="allowPurchase" value="false">
       &nbsp;
      </logic:equal>
     <% } else { %>
       <app:storeMessage key="shoppingItems.text.n_sl_a"/>
     <% } %>
     </div></td>
     <td class="text"><div class="fivemargin">
     <%=item.getActualSkuNum()%>
     </div></td>
     <td class="text"><div class="fivemargin">
       <% String skuDesc = item.getProduct().getCustomerProductShortDesc();
          if(skuDesc==null || skuDesc.trim().length()==0) {
            skuDesc = item.getProduct().getShortDesc();
          }
       %>
     <%String itemLink = "shop.do?action=item&source=lastOrder&itemId="+itemId+"&qty="+item.getQuantity();%>
     <html:link href="<%=itemLink%>">
       <%=skuDesc%>
     </html:link>
     </div></td>
     <td class="text"><div class="fivemargin">
       <bean:write name="item" property="product.size"/>&nbsp;
     </div></td>
     <td class="text"><div class="fivemargin">
       <bean:write name="item" property="product.pack"/>&nbsp;
     </div></td>
     <td class="text"><div class="fivemargin">
       <bean:write name="item" property="product.uom"/>&nbsp;
     </div></td>
     <td class="text"><div class="fivemargin">
       <bean:write name="item" property="product.color"/>&nbsp;
     </div></td>
     <% if (!appUser.getUserAccount().isHideItemMfg()) { %>
       <td class="text"><div class="fivemargin">
         <bean:write name="item" property="product.manufacturerName"/>&nbsp;
       </div></td>
       <td class="text"><div class="fivemargin">
         <bean:write name="item" property="product.manufacturerSku"/>&nbsp;
       </div></td>
     <% } %>
     <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
       <bean:define id="price"  name="item" property="price"/>
       <td class="text"><div class="fivemargin">
         <%=ClwI18nUtil.getPriceShopping(request,price,"<br>")%>
         <logic:equal name="item" property="contractFlag" value="true">
         <logic:equal name="<%=Constants.APP_USER%>" property="showWholeCatalog" value="true">
           *
         </logic:equal>
         </logic:equal>
       </div></td>
       <td class="text"><div class="fivemargin">
         <bean:define id="lastAmt"  name="item" property="shoppingHistory.lastAmt"/>
         <%=ClwI18nUtil.getPriceShopping(request,lastAmt,"<br>")%>
         <logic:equal name="item" property="contractFlag" value="true">
         <logic:equal name="<%=Constants.APP_USER%>" property="showWholeCatalog" value="true">
           *
         </logic:equal>
         </logic:equal>
       </div></td>

     </logic:equal>
     <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="false">
       <td colspan="2"></td>
     </logic:equal>

     </tr>
   </logic:iterate>
  <tr>
  <!-- Second control bar -->
  <td colspan="<%=colCount%>" class="tableoutline"><img src="/images/cw_spacer.gif" height="3"></td>  </tr>
  </tr>
  <logic:equal name="<%=Constants.APP_USER%>" property="allowPurchase" value="true">
    <tr>
    <td colspan="<%=(colCount-3)%>">
    <%if (viewAddInvCart) {%>
        <a href="#" class="linkButton" onclick="setAndSubmit('lot','command','lot_addToModInvCart');">
            <img src="<%=add_img%>" border="0"/>
            <app:storeMessage key="global.label.addToInventoryCart"/>
        </a>
    <%} else {%>
        <a href="#" class="linkButton" onclick="setAndSubmit('lot','command','addToCart');"
        ><img src='<%=add_img%>' border="0"/><app:storeMessage key="global.label.addToCart"/></a>
    <% } %>
    &nbsp;&nbsp;
    <a href='lastOrder.do?action=lastOrderClear' class="linkButton"
    ><img src="<%=IMGPath%>/b_clearqty.gif" border="0"/><app:storeMessage key="global.label.clearQuantities"/></a>

    </td>
    </tr>
  </logic:equal>

  </table>
  <html:hidden property="command" value="addToCart"/>
  </html:form>
  </td>
  <td class="tableoutline" width="1"><img src="<%=IMGPath%>/cw_spacer.gif" height="1" width="1"></td>
  </tr>
</logic:greaterThan>
</table>
</td>
</tr>
</table>
<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_table_bottom.jsp")%>'/>


