<%--
<% { %>
<!-- START: item display <%=IDX%> -->

     <tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,rowCount++)%>">

<%
Date
  curDate = Constants.getCurrentDate(),
  effDate = sciD.getProduct().getEffDate(),
  expDate = sciD.getProduct().getExpDate();


if(effDate != null && effDate.compareTo(curDate)<=0 &&
   (expDate==null || expDate.compareTo(curDate)>0)) {     %>

<%
if (inventoryShopping) {
  if ( sciD.getIsaInventoryItem() ) { %>
    <td  width=15>
      <span class="inv_item">
        i
        <% if (ShopTool.getCurrentSite(request).isAnInventoryAutoOrderItem(itemId)) { %>
        a
        <% } %>
      </span>
    </td>
    <td width=30 align="center" class="invqty_box"><%=sciD.getInventoryParValue()%>  </td>
    <%
      String onHandQtyS = sciD.getInventoryQtyOnHandString();
      //if(onHandQtyS.trim().length()==0) onHandQtyS =
      //       ClwI18nUtil.getMessage(request,"shoppingCart.text.NA",null);
    %>
    <td width=30 align="center">
     <%=onHandQtyS%>
    </td>
<% } else { //inventory shopping is on but this is not an inventoy item%>
      <td width=30>&nbsp;</td>
      <td width=30 align="center" class="invqty_box">-</td>
      <td width=30 align="center">-</td>
  <% }//end sciD.getIsaInventoryItem()
} //end inventoryShopping%>

<%  Integer lineQty = new Integer(sciD.getQuantity()); %>
<td align="center">
  <logic:equal name="sciD" property="duplicateFlag" value="false">
  <% if (editCartItems) { %>
  <html:text name="SHOPPING_CART_FORM"
    property='<%="cartLine[" + IDX + "].quantityString"%>' size="3"
    value='<%=lineQty.toString()%>'
    tabindex='<%=String.valueOf(IDX.intValue()+10)%>'
    styleId='<%="IDX_" + IDX%>'
  />
  <% } else { %>
    <%=lineQty.toString()%>
  <% } %>
  </logic:equal>

  <logic:equal name="sciD" property="duplicateFlag" value="true">
    <html:text name="SHOPPING_CART_FORM" property='<%= "cartLine[" + IDX + "].quantityString" %>' readonly="true" styleClass="customerltbkground" size="3"/>
  </logic:equal>
  <html:hidden name="SHOPPING_CART_FORM" property="<%=itemIdsEl%>" value="<%=\"\"+itemId %>"/>
  <html:hidden name="SHOPPING_CART_FORM" property="<%=orderNumbersEl%>" value="<%=\"\"+orderNumber %>"/>

  <% } else { %>       N/A     <% } %>
</td>
 <!-- Distributor inventory Quantity -->
  <%
  String distInvQtyS = "-";
  if(RefCodeNames.DIST_INVENTORY_DISPLAY.SHOW_FLAG.equals(showDistInventory)) {
    int distInvQty = sciD.getDistInventoryQty();
    if(distInvQty==0) distInvQtyS = ClwI18nUtil.getMessage(request,"global.text.u",null);
    if(distInvQty>0) distInvQtyS = ClwI18nUtil.getMessage(request,"global.text.a",null);

  %>
<td class="text"><div ><%=distInvQtyS%>&nbsp;</div></td>
  <% } %>
  <%
  if(RefCodeNames.DIST_INVENTORY_DISPLAY.SHOW_QUANTITIES.equals(showDistInventory)) {
    int distInvQty = sciD.getDistInventoryQty();
    if (distInvQty>=0) distInvQtyS = String.valueOf(distInvQty);
  %>
<td class="text"><div ><%=distInvQtyS%>&nbsp;</div></td>
  <% } %>

<td class="text"><div ><bean:write name="sciD" property="actualSkuNum"/>&nbsp;</div></td>
<td class="text"><div >
     <%String itemLink = "shop.do?action=item&source=shoppingCart&itemId="
       +itemId+"&qty="+sciD.getQuantity();
     %>
     <html:link href="<%=itemLink%>" tabindex='<%=String.valueOf(IDX.intValue() + 1000)%>'>
    <bean:write name="sciD" property="product.catalogProductShortDesc"/>&nbsp;
     </html:link>
</div></td>
 <td class="text"><div ><bean:write name="sciD" property="product.size"/>&nbsp;</div></td>
 <td class="text"><div ><bean:write name="sciD" property="product.pack"/>&nbsp;</div></td>
<td class="text"><div ><bean:write name="sciD" property="product.uom"/>&nbsp;</div></td>
<td class="text"><div ><bean:write name="sciD" property="product.color"/>&nbsp;</div></td>

<td  align="right" class="text"><div >
<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
  <bean:define id="price"  name="sciD" property="price"/>
  <%=ClwI18nUtil.getPriceShopping(request,price,"<br>")%>
  <logic:equal name="sciD" property="contractFlag" value="true">
    <logic:equal name="<%=Constants.APP_USER%>" property="showWholeCatalog"
      value="true">*</logic:equal>
  </logic:equal>
</logic:equal>

<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="false">
&nbsp;</logic:equal>
</div></td>


<% BigDecimal finalLineAmount = new BigDecimal(sciD.getAmount()); %>
<td align="right" class="text"><div >
<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
<%=ClwI18nUtil.getPriceShopping(request,finalLineAmount,"<br>")%>
</logic:equal>
<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="false">
&nbsp;
</logic:equal>
</div></td>

<% if (appUser.getUserAccount().isShowSPL()) { %>
<td align="center" class="text"><div>
      <logic:present name="sciD" property="product.catalogDistrMapping.standardProductList">
        <bean:define id="spl" name="sciD" property="product.catalogDistrMapping.standardProductList" type="java.lang.String"/>
        <%if(com.cleanwise.service.api.util.Utility.isTrue(spl)){%>
          <app:storeMessage key="shoppingItems.text.y"/>
        <%}else{%>
          <app:storeMessage key="shoppingItems.text.n"/>
        <%}%>
      </logic:present>
      <logic:notPresent name="sciD" property="product.catalogDistrMapping.standardProductList">
        <app:storeMessage key="shoppingItems.text.n"/>
      </logic:notPresent>
</div></td>
<%} %>

<td class="text"><div >
        <% if (editCartItems ) { %>

    <% if ( inventoryItemInfo != null && inventoryItemInfo.getItemId() > 0 && !modernInvShopping ) { %>
    &nbsp;
    <% } else { %>
    <html:multibox name="SHOPPING_CART_FORM" property="selectBox"
      value="<%=\"\"+(((Integer)itemId).longValue()*10000+((Integer)orderNumber).longValue())%>"
         tabindex='<%=String.valueOf(IDX.intValue() + 2000)%>'
    />
    <% } %>

        <% } else if ( appUser.canMakePurchases() && quickOrderView == false &&
                    ShopTool.isInventoryShoppingOn(request)&& !modernInvShopping) { %>
    <html:multibox name="SHOPPING_CART_FORM" property="orderSelectBox"
      value="<%=String.valueOf(sciD.getProduct().getProductId())%>"
      tabindex='<%=String.valueOf(IDX.intValue() + 3000)%>'
    />
        <% } %>
</div></td>

<%
boolean itemIsResale = sciD.getReSaleItem();
String productId = Integer.toString(sciD.getProduct().getProductId());
%>

<% if(resaleItemsAllowed && !editCartItems && !quickOrderView) { %>

  <td class="text" align="center"><div>
    <%if(resaleItemsAllowedViewOnly){%>
      <%if(itemIsResale){%>Y<%}else{%>N<%}%>
    <%}else{%>
        <html:multibox property="reSaleSelectBox">
          <bean:write name="sciD" property="product.productId"/>
        </html:multibox>
   <%}%>
  </div></td>

<% } %>

<%
if(
  !resaleItemsAllowed &&
  itemIsResale &&
  !editCartItems &&
  !quickOrderView)	{
/*
If the item is taxable, then this is always renderred.
*/
%>
  <%String prop="reSaleSelectBox["+IDX+"]";%>
  <html:hidden property="<%=prop%>" value="<%=productId%>"/>
<%}%>



<logic:equal name="sciD" property="duplicateFlag" value="true">
<td class="text"><div >
  <%=ClwI18nUtil.getPriceShopping(request,finalLineAmount,"<br>")%>
</div></td>
<td>Dupl</td>
</logic:equal>



</tr>
<input type="hidden"
 name="NEW_ITEM-DESCRIPTION[<%=IDX.intValue() + 1%>]"
 value='<bean:write name="sciD" property="product.catalogProductShortDesc"/>'>

<input type="hidden"
 name="NEW_ITEM-QUANTITY[<%=IDX.intValue() + 1%>]"
 value='<bean:write name="sciD" property="quantity"/>'>

<input type="hidden"
 name="NEW_ITEM-UNIT[<%=IDX.intValue() + 1%>]"
 value='<bean:write name="sciD" property="product.uom"/>'>

<input type="hidden"
 name="NEW_ITEM-PRICE[<%=IDX.intValue() + 1%>]"
 value='<bean:write name="sciD" property="price"/>'>

<input type="hidden"
 name="NEW_ITEM-CURRENCY[<%=IDX.intValue() + 1%>]"
 value="USD">

<!-- sku num -->
<input type="hidden"
 name="NEW_ITEM-VENDORMAT[<%=IDX.intValue() + 1%>]"
 value='<bean:write name="sciD" property="actualSkuNum"/>'>

<!-- item id -->
<input type="hidden"
 name="NEW_ITEM-EXT_PRODUCT_ID[<%=IDX.intValue() + 1%>]"
 value='<%=String.valueOf(itemId)%>' >


<!-- END: item display <%=IDX%> -->
<% } %>
--%>
<%String itemLink1 = "shop.do?action=item&source=orderGuide&itemId="
  +itemId+"&qty="+sciD.getQuantity();
  String trColor=ClwCustomizer.getEvenRowColor(request.getSession(),rowIdx++);
%>

<tr bgcolor="<%=trColor%>">
<app:commonDisplayProd
        viewOptionEditCartItems="<%=editCartItems%>"
        viewOptionQuickOrderView="<%=quickOrderView%>"
        viewOptionAddToCartList="false"
        viewOptionOrderGuide="false"
        viewOptionShoppingCart="<%=shoppingCartView%>"
        viewOptionCheckout="<%=checkoutView%>"
        viewOptionInventoryList="<%=viewOptionInventoryList%>"
        name="sciD"
        link="<%=itemLink1%>"
        index="<%=IDX%>"
        inputNameQuantity="<%= \"cartLine[\" + IDX + \"].quantityString\" %>"/>
</tr>
