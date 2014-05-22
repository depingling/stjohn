<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Locale" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>


<% 
String 
tmp_itemorder = request.getParameter("orderBy")
;
int orderby = 0;
if ( tmp_itemorder != null ) {
  orderby = Integer.parseInt(tmp_itemorder);
}

String 
  storeDir=ClwCustomizer.getStoreDir(), 
  requestNumber =  (String) session.getAttribute(Constants.REQUEST_NUM),
  IPTH = (String) session.getAttribute("pages.store.images");

  ShoppingCartData shoppingCart = ShopTool.getCurrentShoppingCart(request);
  CleanwiseUser appUser = ShopTool.getCurrentUser(request);

  int  COL_COUNT = 13;
%>

<!--
<br>
orderby = <%=orderby%> = orderby
<br>
-->

Verify the items for purchase.
