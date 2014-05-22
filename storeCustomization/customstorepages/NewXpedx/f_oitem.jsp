<!-- PAGESRCID=f_oitem.jsp -->
<%String itemLink ="shop.do?action=msItem&source=t_itemDetail.jsp&itemId="+item.getItemId()+"&qty="+item.getQuantity();
String styleClass = (((rowIdx++) %2 )==0) ?  "evenRowColor" : "oddRowColor";%>
<tr class="<%=styleClass%>">

<app:displayProd viewOptionEditCartItems="<%=editCartItems%>" viewOptionQuickOrderView="<%=quickOrderView%>" viewOptionAddToCartList="false" viewOptionOrderGuide="false"
	name="sciD" link="<%=itemLink%>" index="<%=kidx%>" inputNameOnHand="<%=\"cartLine[\" + kidx + \"].inventoryQtyOnHandString\"%>" inputNameQuantity="<%= \"cartLine[\" + kidx + \"].quantityString\"%>"/>

</tr>

