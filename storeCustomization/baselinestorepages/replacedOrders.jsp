<%
  if(replacedOrders!=null && replacedOrders.size()>0) {
%>
<table width='750' cellpadding=0 cellspacing=0 align='center'>
 <tr class='shopcharthead'> <td>&nbsp;</td><td colspan='8'><app:storeMessage key="shop.checkout.text.replacedOrders:"/>  </td>
 </tr>
 <tr  class='shopcharthead'><td>&nbsp;</td> 
   <td> <app:storeMessage key="shop.checkout.text.shipTo"/> </td>
   <td> <app:storeMessage key="shop.checkout.text.order#"/> </td>
   <td> <app:storeMessage key="shop.checkout.text.referenceOrder#"/> </td>
   <td> <app:storeMessage key="shop.checkout.text.requestPo#"/> </td>
   <td> <app:storeMessage key="shop.checkout.text.orderQty"/> </td>
   <td> <app:storeMessage key="shop.checkout.text.ourSkuNum"/> </td>
   <td> <app:storeMessage key="shop.checkout.text.productName"/> </td><td>&nbsp;</td>
 </tr>
<%
  for(Iterator iter = replacedOrders.iterator(); iter.hasNext();) {
  ReplacedOrderView replOrdeVw = (ReplacedOrderView) iter.next();
  String orderNum = replOrdeVw.getOrderNum();
  String refOrderNum = replOrdeVw.getRefOrderNum();
  if(refOrderNum==null) refOrderNum="";
  String reqPoNum = replOrdeVw.getRequestPoNum(); 
  if(reqPoNum==null) reqPoNum="";
  String siteName= replOrdeVw.getOrderSiteName();
%>
 <%
   int ind = 0;
   ReplacedOrderItemViewVector orderItems = replOrdeVw.getItems();
   for(Iterator iter1 = orderItems.iterator(); iter1.hasNext();) {
     ReplacedOrderItemView roiVw = (ReplacedOrderItemView) iter1.next();
     String skuNum = roiVw.getCustItemSkuNum();
	 if(skuNum==null) {
        //CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
        String storeType = null;
        if(appUser.getUserStore().getStoreType() != null){
            storeType = appUser.getUserStore().getStoreType().getValue();
        }
        if(RefCodeNames.STORE_TYPE_CD.DISTRIBUTOR.equals(storeType)){
              skuNum = roiVw.getDistItemSkuNum();
        } else {
              skuNum = ""+roiVw.getItemSkuNum();
		}
	 }
     String shortDesc = roiVw.getCustItemShortDesc();
     int qty = roiVw.getQuantity();
     ind ++;
     if(ind==1) {
 %>
 <tr>
 <td class="shopcharthead" colspan='9'><img src="/images/cw_spacer.gif" height="3"></td>
 </tr>
<tr  class='text' valign='top'> <td>&nbsp;</td>
  <td ><%=siteName%></td><td><%=orderNum%> </td><td> <%=refOrderNum%> </td> <td> <%=reqPoNum%></td>
  <td align='center'><%=qty%> </td><td> <%=skuNum%> </td> <td> <%=shortDesc%></td><td>&nbsp;</td>
  </tr>
 <% } else {%>
 <tr  class='text' valign='top'> <td>&nbsp;</td>
 <td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
  <td align='center'><%=qty%> </td><td> <%=skuNum%> </td> <td> <%=shortDesc%></td><td>&nbsp;</td>
  </tr>
<% } %>

<% }} %>
</table>
<% } %>


