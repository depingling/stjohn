<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="java.util.Locale" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<bean:define id="theForm" name="CHECKOUT_FORM" type="com.cleanwise.view.forms.CheckoutForm"/>
<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<bean:define id="clwWorkflowRole" type="java.lang.String" name="ApplicationUser" property="user.workflowRoleCd"/>

<%
 CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
 ShoppingCartData shoppingCart = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
 OrderData prevOrder = shoppingCart.getPrevOrderData();
 boolean showPlaceOrderFields = ("true".equals(request.getParameter("showPlaceOrderFields")))?true:false;
 boolean showPlaceOrderButton = ("true".equals(request.getParameter("showPlaceOrderButton")))?true:false;
 boolean showOrderSelected = ("true".equals(request.getParameter("showOrderSelected")))?true:false;
%>


<table align=left>
<tr>
<% if ( ShopTool.isAnOCISession(request.getSession()) ) {
  // In this case, always have a submit button.
 %>
  <td>
    <a href="#" class="linkButton" onclick="setAndSubmit('CHECKOUT_FORM','command','placeOrderForAll');"
    ><img src='<%=IMGPath + "/b_placeorder.gif"%>' border="0"/><app:storeMessage key="global.label.placeOrder"/></a>
  </td>
<%
}
else if ( showPlaceOrderFields && showPlaceOrderButton )
{   %>
<td>
    <a href="#" class="linkButton" onclick="setAndSubmit('CHECKOUT_FORM','command','placeOrderForAll');"
    ><img src='<%=IMGPath + "/b_placeorder.gif"%>' border="0"/><app:storeMessage key="global.label.placeOrder"/></a>

    <a href="#" class="linkButton" onclick="setAndSubmit('CHECKOUT_FORM','command','recalc');"
    ><img src='<%=IMGPath + "/b_recalculate.gif"%>' border="0"/><app:storeMessage key="global.label.recalculate"/></a>
  </td>
<% } %>

<% if ( showOrderSelected ) {   %>
<td>
    <a href="#" class="linkButton" onclick="setAndSubmit('CHECKOUT_FORM','command','placeOrderForAll');"
    ><img src='<%=IMGPath + "/b_placeorder.gif"%>' border="0"/><app:storeMessage key="global.label.placeOrder"/></a>
</td>
<%
  } else {
  String workflowRole = appUser.getUser().getWorkflowRoleCd();
  boolean approverFl =
     (workflowRole.indexOf(RefCodeNames.WORKFLOW_ROLE_CD.ORDER_APPROVER)>=0)?true:false;
  if(prevOrder != null ) {
    if(RefCodeNames.ORDER_SOURCE_CD.INVENTORY.equals(prevOrder.getOrderSourceCd()) &&
       approverFl) {
%>
<td>
    <a href="#" class="linkButton" onclick="setAndSubmit('CHECKOUT_FORM','command','placeOrderForAll');"
    ><img src='<%=IMGPath + "/b_placeorder.gif"%>' border="0"/><app:storeMessage key="global.label.placeOrder"/></a>
</td>
<% }}} %>
</tr></table>
