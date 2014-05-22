<!-- START: item display <%=IDX%> -->

     <tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)IDX)%>">

<% 
Date 
  curDate = Constants.getCurrentDate(),
  effDate = sciD.getProduct().getEffDate(),
  expDate = sciD.getProduct().getExpDate();


if(effDate != null && effDate.compareTo(curDate)<=0 &&
   (expDate==null || expDate.compareTo(curDate)>0)) {     %>
     
<%
 if (inventoryShopping) {   
%>

 <% if ( sciD.getIsaInventoryItem() ) { %>
<td  width=15>
<span class="inv_item">
i
<% if (ShopTool.getCurrentSite(request).isAnInventoryAutoOrderItem(itemId)) { %>
a
<% } %>
</span>
</td>
  <td width=30 align="center" class="invqty_box">
<%=sciD.getInventoryParValue()%>  </td>
  <td width=30 align="center">   
<html:text name="SHOPPING_CART_FORM" 
property='<%= "cartLine[" + IDX + "].inventoryQtyOnHandString" %>' size="3"/>
</td>
  <% } else {%>

  <td width=30>&nbsp;</td>
  <td width=30 align="center" class="invqty_box">-</td>
  <td width=30 align="center">-</td>

  <% } %>
  <% } %>

  <td align="center">
<logic:equal name="sciD" property="duplicateFlag" value="false">
<% if (editCartItems) { %>
<html:text name="SHOPPING_CART_FORM" 
property='<%= "cartLine[" + IDX + "].quantityString" %>' size="3"/>
<% } else { %>
<bean:write name="SHOPPING_CART_FORM" 
property='<%= "cartLine[" + IDX + "].quantity" %>' />
<% } %>
</logic:equal>
     
<logic:equal name="sciD" property="duplicateFlag" value="true">
     <html:text name="SHOPPING_CART_FORM" 
property='<%= "cartLine[" + IDX + "].quantityString" %>'
	readonly="true" styleClass="customerltbkground" size="3"/>
</logic:equal>
     
<html:hidden name="SHOPPING_CART_FORM" property="<%=itemIdsEl%>" 
  value="<%=\"\"+itemId %>"/>
<html:hidden name="SHOPPING_CART_FORM" property="<%=orderNumbersEl%>" 
  value="<%=\"\"+orderNumber %>"/>

<% } else { %>       N/A     <% } %>
 </td>
 


     
     <td class="text"><div class="fivemargin">
       <bean:write name="sciD" property="actualSkuNum"/>&nbsp;
     </div></td>
     <td class="text"><div class="fivemargin">
     <%String itemLink = "shop.do?action=item&source=shoppingCart&itemId="+itemId+"&qty="+sciD.getQuantity();%>
     <html:link href="<%=itemLink%>">
       <bean:write name="sciD" property="product.catalogProductShortDesc"/>&nbsp;
     </html:link>
     </div></td>
     <td class="text"><div class="fivemargin">
       <bean:write name="sciD" property="product.size"/>&nbsp;
     </div></td>
     <td class="text"><div class="fivemargin">
       <bean:write name="sciD" property="product.pack"/>&nbsp;
     </div></td>
     <td class="text"><div class="fivemargin">
       <bean:write name="sciD" property="product.uom"/>&nbsp;
     </div></td>
     <td class="text"><div class="fivemargin">
       <bean:write name="sciD" property="product.color"/>&nbsp;
     </div></td>

<td  align="right" class="text"><div class="fivemargin">
<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
  <bean:define id="price"  name="sciD" property="price"/>
  <i18n:formatCurrency value="<%=price%>" locale="<%=Locale.US%>"/>
  <logic:equal name="sciD" property="contractFlag" value="true">
    <logic:equal name="<%=Constants.APP_USER%>" property="showWholeCatalog" 
      value="true">*</logic:equal>
  </logic:equal>
</logic:equal>

<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="false">
&nbsp;</logic:equal>

</div>
</td>


<bean:define id="finalLineAmount"  name="sciD" property="amount"/>
<td align="right" class="text"><div class="fivemargin">
<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
<i18n:formatCurrency value="<%=finalLineAmount%>" locale="<%=Locale.US%>"/>
</logic:equal>


<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="false">
&nbsp;
</logic:equal>  
</div></td>


<td class="text"><div class="fivemargin">
	<% if (editCartItems) { %>

<% if ( inventoryItemInfo != null && inventoryItemInfo.getItemId() > 0  ) { %>
&nbsp;
<% } else { %>
<html:multibox name="SHOPPING_CART_FORM" property="selectBox" 
  value="<%=\"\"+(((Integer)itemId).longValue()*10000+((Integer)orderNumber).longValue())%>" />
<% } %>

	<% } else if ( appUser.canMakePurchases() &&
				ShopTool.isInventoryShoppingOn(request)) { %>
<html:multibox name="SHOPPING_CART_FORM" property="orderSelectBox" 
  value="<%=String.valueOf(sciD.getProduct().getProductId())%>" 
/>
	<% } %>

</td>




</logic:equal>


<logic:equal name="sciD" property="duplicateFlag" value="true">
<td class="text"><div class="fivemargin">
<i18n:formatCurrency value="<%=finalLineAmount%>" locale="<%=Locale.US%>"/>
</div></td>
<td>Dupl</td>
</logic:equal>



</div></td>
</tr>

<!-- END: item display <%=IDX%> -->
