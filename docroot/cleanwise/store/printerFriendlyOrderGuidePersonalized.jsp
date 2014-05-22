
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.OrderGuideData" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<bean:define id="theForm" name="SHOP_FORM" type="com.cleanwise.view.forms.UserShopForm"/>

<html:html>
<head>

<title><%=theForm.getOrderGuideName() + " - Personalized Version"%></title>
<jsp:include flush='true' page="../userportal/htmlHeaderIncludes.jsp"/>
</head>
<body marginheight="0" topmargin="0" marginwidth="0" leftmargin="0">

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="storeLocale" name="pages.store.locale"/>
<bean:define id="storePrefix" name="pages.store.prefix"/>

<%
/*
index.jsp needs to be moved for this to work.
<app:checkLogon/>
*/
%>

<%
/*
Get the locale and the store prefix for this shopping request.
*/

 String lStoreHeader     = storeLocale + "/" + storePrefix + "/" + "t_cwHeader.jsp";
 String lStoreCatalog    = storeLocale + "/" + storePrefix + "/" + "shopTemplate.jsp";
 String lStoreOrderGuide = storeLocale + "/" + storePrefix + "/" + "printerFriendlyOrderGuidePersonalizedTemplate.jsp";
 String lItemRoot        = storeLocale + "/" + storePrefix + "/" + "shopItemTemplate.jsp";
 String lStoreFooter     = storeLocale + "/" + storePrefix + "/" + "t_footer.jsp";

%>


<%  String action = request.getParameter("action");
  if(action==null || !action.startsWith("item")) {  %>
  <logic:equal name="SHOP_FORM" property="shopMethod" 
     value="<%=\"\"+Constants.SHOP_BY_ORDER_GUIDE%>">    
    <bean:define id="shopLoc" value="orderGuide" 
     type="java.lang.String" toScope="request"/>
    <jsp:include flush='true' page="<%= lStoreOrderGuide %>"/>
	
  </logic:equal>
<% } else { %>
<% } %>


</body>
</html:html>






