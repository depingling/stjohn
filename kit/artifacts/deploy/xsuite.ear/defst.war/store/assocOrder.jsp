<%@ page language="java" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ArrayList" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>
<% String storeDir=ClwCustomizer.getStoreDir(); %>
<% String requestNumber =  (String) session.getAttribute(Constants.REQUEST_NUM);%>
<% ShoppingCartData shoppingCart = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART); %>
<% CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER); %>
<% String userType = appUser.getUser().getUserTypeCd(); %>


<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<% 
if(shoppingCart!=null && (shoppingCart instanceof ConsolidatedCartView)) {
  ArrayList orders = ((ConsolidatedCartView) shoppingCart).getOrders();
  if(orders!=null && orders.size()>0) {
%>
<table class="tbstd" width="<%=Constants.TABLEWIDTH%>" cellpadding=0 cellspacing=0>
<!--
 <tr>
 <td>
 <table  cellpadding=0 cellspacing=0>
-->
 
 <tr class='shopcharthead'> <td>&nbsp;</td><td colspan='8'> <app:storeMessage key="shop.checkout.text.replacedOrders:"/>  </td>
 </tr>
 <tr  class='shopcharthead'><td>&nbsp;</td> 
   <td> <app:storeMessage key="shop.checkout.text.shipTo"/> </td>
   <td> <app:storeMessage key="shop.checkout.text.order#"/> </td>
   <td> <app:storeMessage key="shop.checkout.text.referenceOrder#"/> </td>
   <td> <app:storeMessage key="shop.checkout.text.requestPoNum"/> </td>
   <td> <app:storeMessage key="shop.checkout.text.orderQty"/></td>
   <td> <app:storeMessage key="shop.checkout.text.ourSkuNum"/></td>
   <td> <app:storeMessage key="shop.checkout.text.productName"/></td><td>&nbsp;</td>
 </tr>
<%
  for(Iterator iter = orders.iterator(); iter.hasNext();) {
  OrderJoinData orderJD = (OrderJoinData) iter.next();
  OrderData orderD = orderJD.getOrder();
  String orderNum = orderD.getOrderNum();
  String refOrderNum = orderD.getRefOrderNum();
  if(refOrderNum==null) refOrderNum="";
  String reqPoNum = orderD.getRequestPoNum(); 
  if(reqPoNum==null) reqPoNum="";
  String siteName= orderD.getOrderSiteName();
  String linkedOrderTitle = "Order #"+orderNum+" Reference order #"+refOrderNum+
        " Request Po #"+reqPoNum+" Ship to: "+siteName;
%>
 <%
   int ind = 0;
   OrderItemJoinDataVector orderItems = orderJD.getOrderJoinItems();
   for(Iterator iter1 = orderItems.iterator(); iter1.hasNext();) {
     OrderItemJoinData orderItemJD = (OrderItemJoinData) iter1.next();
     OrderItemData orderItemD = orderItemJD.getOrderItem();
     String skuNum = ShopTool.getRuntimeSku(orderItemD,request);
     String shortDesc = orderItemD.getItemShortDesc();
     int qty = orderItemD.getTotalQuantityOrdered();
     ind ++;
     if(ind==1) {
 %>
 <tr>

<td class="shopcharthead" colspan='9'>
<img src="<%=IMGPath%>/cw_spacer.gif" height="3">
</td>

 </tr>
<tr  class='text' valign='top'> <td>&nbsp;</td>
  <td ><%=siteName%></td><td><%=orderNum%> </td><td> <%=refOrderNum%> </td> <td> <%=reqPoNum%></td>
  <td><%=qty%> </td><td> <%=skuNum%> </td> <td> <%=shortDesc%></td><td>&nbsp;</td>
  </tr>
 <% } else {%>
 <tr  class='text' valign='top'> <td>&nbsp;</td>
 <td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td>
  <td><%=qty%> </td><td> <%=skuNum%> </td> <td> <%=shortDesc%></td><td>&nbsp;</td>
  </tr>
<% } %>

<% }} %>
<!--
</table>
</td>
</tr>
-->
</table>
<% }} %>

