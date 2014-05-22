<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.view.forms.ShoppingCartForm" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<%
String shoppingCartName = (String) request.getParameter("shoppingCartName");

if (shoppingCartName == null) {
   shoppingCartName = Constants.SHOPPING_CART;
}
ShoppingCartData shoppingCart = (ShoppingCartData) session.getAttribute(shoppingCartName);

if (ShopTool.isInventoryShoppingOn(request)) {
  java.util.List itemsRemoved = shoppingCart.getRemovedProductInfo();
  if ( itemsRemoved != null && itemsRemoved.size() > 0 ) {
%>

<table width=750 align=center cellpadding=2 cellspacing=1 bgcolor="#cccccc">
<tr><td colspan=2>
<b>
Current Order Cycle Shopping Cart Activity-Removed Items
</b>
<tr>
<td class="shopcharthead">Our Sku #
<td class="shopcharthead">Product Name
<td class="shopcharthead">Action
<td class="shopcharthead">Date
<td class="shopcharthead">Username
<%
int rowCt = 0;
String prevItemSku = "";
for ( int idx = 0; itemsRemoved != null && idx < itemsRemoved.size(); idx++ )
{
  String thisBG = "#ffffff";
  ShoppingCartData.ItemChangesEntry log =
    (ShoppingCartData.ItemChangesEntry)itemsRemoved.get(idx);
  ShoppingInfoData sid = log.getShoppingInfoData();
  String messKey = sid.getMessageKey();
%>

<%
if (! log.getSku().equals(prevItemSku)) {
  rowCt++;
 }

if (( rowCt % 2 ) == 0 ) {  thisBG = "#ffffff"; }
else {   thisBG = "#fffccc"; }
%>

<tr bgcolor="<%=thisBG%>">
<% if (! log.getSku().equals(prevItemSku)) { %>
<td><%=log.getSku()%>
<td><%=log.getProductDesc()%>
<% } else { %>
<td>
<td>
<% } %>
<td width=200>
  <% if(messKey==null){ %>
  <%=sid.getValue()%>
  <% } else { %>
  <app:storeMessage key="<%=messKey%>"
    arg0="<%=sid.getArg0()%>" arg1="<%=sid.getArg1()%>"
    arg2="<%=sid.getArg2()%>" arg3="<%=sid.getArg3()%>" />
  <% } %>
</td>
<td width=130>
<i18n:formatDate  value="<%=sid.getAddDate()%>"  pattern="yyyy-M-d k:mm"
locale="<%=ClwI18nUtil.getUserLocale(request)%>"/>
</td>
<td><%=sid.getAddBy()%>
</td>

<%

prevItemSku = log.getSku();

}
%>

</table>

<%
}
}
%>



