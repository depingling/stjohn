
<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html language='en_US'>
<head>

<title><app:storeMessage key="shop.heading.shopProductCatalog"/></title>
  <meta http-equiv="Pragma" content="no-cache">
  <META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
  <meta http-equiv="Expires" content="-1">
  <jsp:include flush='true' page="/userportal/htmlHeaderIncludes.jsp"/>
</head>
<body marginheight="0" topmargin="0" marginwidth="0" leftmargin="0">
<table id="TopHeaderTable" align="center" border="0" cellpadding="0" cellspacing="0">
<tr>
<td width="20" background='<%=ClwCustomizer.getSIP(request,"leftBorder.gif")%>'>&nbsp;</td>
<td bgcolor="white">&nbsp;&nbsp;&nbsp;</td>
<td bgcolor="white">


<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<%
/*
index.jsp needs to be moved for this to work.
<app:checkLogon/>
*/
%>

<%
String hs = ClwCustomizer.getStoreFilePath(request,
  "t_cwHeader.jsp");
String fs = ClwCustomizer.getStoreFilePath(request,
  "t_footer.jsp");

  String lStoreCatalog = ClwCustomizer.getStoreFilePath(request,
  "shopTemplate.jsp");

    String lStoreOrderGuide = ClwCustomizer.getStoreFilePath(request, "shopOrderGuideTemplate.jsp");

    if (ShopTool.isInventoryShoppingOn(request)&&ShopTool.isModernInventoryShopping(request)){

        lStoreOrderGuide = ClwCustomizer.getStoreFilePath(request, "shopOrderGuideTemplateMod.jsp");
    }

String lItemRoot = ClwCustomizer.getStoreFilePath(request,
  "shopItemTemplate.jsp");

String lGreenItemRoot = ClwCustomizer.getStoreFilePath(request,"t_greencleaning.jsp");

String lCategoryItemRoot = ClwCustomizer.getStoreFilePath(request,"shopCategoryItemTemplate.jsp");
%>
 <bean:define id ="mth" name="SHOP_FORM" property="shopMethod"/>


<%

  String action = request.getParameter("action");
  if(action==null ) { action = "init"; }

  if(!action.startsWith("item")) {  %>
  <logic:equal name="SHOP_FORM" property="shopMethod"
     value="<%=\"\"+Constants.SHOP_BY_CATALOG%>">

    <jsp:include flush='true' page="<%=hs%>"/>
    <jsp:include flush='true' page="<%= lStoreCatalog %>"/>

  </logic:equal>
   <logic:equal name="SHOP_FORM" property="shopMethod"   value="<%=\"\"+Constants.SHOP_BY_GREEN_PRODUCT%>">

    <jsp:include flush='true' page="<%=hs%>"/>
    <jsp:include flush='true' page="<%= lGreenItemRoot %>"/>

  </logic:equal>
  <logic:equal name="SHOP_FORM" property="shopMethod"   value="<%=\"\"+Constants.SHOP_BY_CATEGORY%>">

    <jsp:include flush='true' page="<%=hs%>"/>
    <jsp:include flush='true' page="<%=  lCategoryItemRoot %>">
        <jsp:param name="source" value="<%=Utility.strNN(request.getParameter(\"source\"))%>"/>
    </jsp:include>

  </logic:equal>
  <logic:notEqual name="SHOP_FORM" property="shopMethod" value="<%=\"\"+Constants.SHOP_BY_GREEN_PRODUCT%>">
  <logic:notEqual name="SHOP_FORM" property="shopMethod" value="<%=\"\"+Constants.SHOP_BY_CATALOG%>">
  <logic:notEqual name="SHOP_FORM" property="shopMethod" value="<%=\"\"+Constants.SHOP_BY_CATEGORY%>">
    <bean:define id="shopLoc" value="orderGuide"   type="java.lang.String" toScope="request"/>
    <jsp:include flush='true' page="<%= hs %>"/>
    <jsp:include flush='true' page="<%= lStoreOrderGuide %>"/>
 </logic:notEqual>
 </logic:notEqual>
 </logic:notEqual>
<% } else { %>
<logic:equal name="SHOP_FORM" property="shopMethod"   value="<%=\"\"+Constants.SHOP_BY_CATEGORY%>">

    <jsp:include flush='true' page="<%=hs%>"/>
    <jsp:include flush='true' page="<%=lCategoryItemRoot %>">
        <jsp:param name="source" value="<%=Utility.strNN(request.getParameter(\"source\"))%>"/>
    </jsp:include>

</logic:equal>
<logic:notEqual name="SHOP_FORM" property="shopMethod" value="<%=\"\"+Constants.SHOP_BY_CATEGORY%>">

    <jsp:include flush='true' page="<%=hs%>"/>
    <jsp:include flush='true' page="<%=lItemRoot%>"/>

</logic:notEqual>
<% } %>

<jsp:include flush='true' page="<%=fs%>"/>
</td>
<td bgcolor="white">&nbsp;&nbsp;&nbsp;</td>
<td width="20" background='<%=ClwCustomizer.getSIP(request,"rightBorder.gif")%>'>&nbsp;</td>
</tr>
</table>

</body>
</html>






