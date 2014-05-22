<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.view.forms.QuickOrderForm" %>


<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
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

<%-- Redirecting to place order page --%>

<script language="javascript">location.href="shop.do?action=initAndSelectOrderGuide"</script>

<% String storeDir=ClwCustomizer.getStoreDir(); %>
<% ShoppingCartData shoppingCart = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);%>
<bean:define id="theForm" name="QUICK_ORDER_FORM" type="com.cleanwise.view.forms.QuickOrderForm"/>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>

<table align="center" class="tbstd" cellpadding="0" cellspacing="0"
  width="<%=Constants.TABLEWIDTH%>">
<tr>
<td>  <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"t_shopToolbar.jsp")%>'/></td>
</tr>
</table>

<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_shopping_msgs.jsp")%>'>
    <jsp:param name="shoppingCartName" value="<%=Constants.SHOPPING_CART%>"/>
</jsp:include>
<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_shopping_msgs.jsp")%>'>
    <jsp:param name="shoppingCartName" value="<%=Constants.INVENTORY_SHOPPING_CART%>"/>
</jsp:include>

<table align="center" class="tbstd" cellpadding="0" cellspacing="0"
  width="<%=Constants.TABLEWIDTH%>">
<tr>
<td>
<logic:equal name="<%=Constants.APP_USER%>" property="allowPurchase" value="true">
<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_quick_order_items.jsp")%>' />
</logic:equal>
</td>
</tr>






<%
ShoppingCartData scdx = ShopTool.getCurrentShoppingCart(request);
if ( scdx.getItemsQty() > 0 ) {
%>

<tr><td  align="center" class="shopcharthead">
<app:storeMessage key="shop.quick.text.currentShoppingCartItems"/>
  <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_shoppingItems.jsp")%>'>
  <jsp:param name="allowEdits" value="false"/>
    <jsp:param name="view" value="quickOrder"/>
  </jsp:include>
</td></tr>

<% } %>

</table>


<%@ include file="f_table_bottom.jsp" %>






