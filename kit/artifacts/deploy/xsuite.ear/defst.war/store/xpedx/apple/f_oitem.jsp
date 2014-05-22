<!-- START: item display <%=kidx%> -->

<tr bgcolor="<%=ClwCustomizer.getEvenRowColor(request.getSession(),rowIdx++)%>">


<%

Date curDate = Constants.getCurrentDate();
Date effDate = item.getProduct().getEffDate();
Date expDate = item.getProduct().getExpDate();
if(effDate != null && effDate.compareTo(curDate)<=0 &&
   (expDate==null || expDate.compareTo(curDate)>0)) {
%>
<% if (inventoryShopping && item.getIsaInventoryItem() ) { %>
<td>
<span class="inv_item">
i
<% if (ShopTool.getCurrentSite(request).isAnInventoryAutoOrderItem
  (item.getProduct().getProductId())) { %>
a
<% } %>
</span>

</td>

<td align="center">
<bean:write name="SHOP_FORM"
property='<%= "cartLine[" + kidx + "].inventoryParValue" %>'/>
</td>

<td align="center">
<% if ( item.getInventoryParValue() <= 0 &&
        item.getInventoryQtyOnHand() <= 0 ) { %>

<html:text name="SHOP_FORM"
 property='<%= "cartLine[" + kidx + "].inventoryQtyOnHandString" %>'
 value="0" size="3"
 tabindex='<%=String.valueOf(TABIDX + 10)%>'
 styleId='<%="IDX_" + TABIDX%>'
/>

<% } else { %>

<html:text name="SHOP_FORM"
 property='<%= "cartLine[" + kidx + "].inventoryQtyOnHandString" %>'
  size="3"
 tabindex='<%=String.valueOf(TABIDX + 10)%>'
 styleId='<%="IDX_" + TABIDX%>'
/>


<% } %>

</td>

<% if ( item.getInventoryParValue() > 0 ) { %>

<td align="center">

<% if ( item.getInventoryQtyIsSet() ) {
int newOnHandVal = 0;
try { newOnHandVal = Integer.parseInt(item.getInventoryQtyOnHandString()); }
catch (Exception e) { newOnHandVal = 0; }
item.setInventoryQtyOnHand(newOnHandVal);
%>

<bean:write name="SHOP_FORM"
property='<%= "cartLine[" + kidx + "].inventoryOrderQty" %>' />
<% } %>

</td>
<% }
   else
   { %>
<td align="center"> </td>
<% } %>


  <% } else { %>

<logic:equal name="SHOP_FORM" property="allowPurchase" value="true">
<td>
<html:text name="SHOP_FORM"
property='<%= "cartLine[" + kidx + "].quantityString" %>'
 size="3"
 tabindex='<%=String.valueOf(TABIDX + 10)%>'
 styleId='<%="IDX_" + TABIDX%>'
/>

</td>
</logic:equal>
<logic:equal name="SHOP_FORM" property="allowPurchase" value="false">
<td>    &nbsp;</td>
</logic:equal>
  <% } %>

<% } else { %>
    <td>
       N/A
     </td>
     <% } %>
     <td class="text" width="50">
    <% String thumbnail=item.getProduct().getThumbnail();
       if(thumbnail!=null && thumbnail.trim().length()>0) {
    %>
      <img width="50" height="50" src="/<%=storeDir%>/<%=thumbnail%>">
    <% } else { %>
      &nbsp;
    <% } %>
     </td>

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

<% TABIDX++ ; %>


<!-- END: item display <%=kidx%> -->
