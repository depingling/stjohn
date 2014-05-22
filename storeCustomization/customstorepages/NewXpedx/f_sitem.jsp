<!--PAGESRCID=f_sitem.jsp -->
<%String itemLink = "shop.do?action=msItem&source=t_itemDetail.jsp&itemId="+itemId+"&qty="+sciD.getQuantity();
String styleClass = (((rowCount++) %2 )==0) ?  "evenRowColor" : "oddRowColor";
%>
<tr class="<%=styleClass%>">

<app:displayProd viewOptionEditCartItems="<%=editCartItems%>" viewOptionQuickOrderView="<%=quickOrderView%>" viewOptionAddToCartList="false" viewOptionOrderGuide="false"  viewOptionShoppingCart="<%=shoppingCartView%>" viewOptionCheckout="<%=checkoutView%>"
	name="sciD" link="<%=itemLink%>" index="<%=IDX%>" inputNameOnHand="<%=\"cartLine[\" + IDX + \"].inventoryQtyOnHandString\"%>" inputNameQuantity="<%= \"cartLine[\" + IDX + \"].quantityString\"%>"/>


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
<html:hidden name="SHOPPING_CART_FORM" property="<%=itemIdsEl%>" value="<%=\"\"+itemId %>"/>
  <html:hidden name="SHOPPING_CART_FORM" property="<%=orderNumbersEl%>" value="<%=\"\"+orderNumber %>"/>
