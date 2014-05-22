<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>

<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.forms.QuickOrderForm" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<bean:define id="theForm" name="QUICK_ORDER_FORM" type="QuickOrderForm"/>
<html>
<head>

<title><app:storeMessage key="shop.heading.shopExpressOrder"/></title>
<jsp:include flush='true' page="../userportal/htmlHeaderIncludes.jsp"/>
</head>
<body marginheight="0" topmargin="0" marginwidth="0" leftmargin="0">

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<%
/*
index.jsp needs to be moved for this to work.
<app:checkLogon/>
*/
%>

<%
String hs = ClwCustomizer.getStoreFilePath(request,"t_cwHeader.jsp"); 

String fs = ClwCustomizer.getStoreFilePath(request,"t_footer.jsp");

 String lQuickOrder = "";
 if(theForm.getDuplicationFlag()==false) {
   lQuickOrder = ClwCustomizer.getStoreFilePath(request,"quickOrderTemplate.jsp");
 } else {
   lQuickOrder = ClwCustomizer.getStoreFilePath(request,      "quickOrderCollisionTemplate.jsp" );
 }

%>

<jsp:include flush='true' page="<%=hs%>"/>
<jsp:include flush='true' page="<%=lQuickOrder%>"/>

<!-- CRC INFO -->

<%
  CleanwiseUser appUser = (CleanwiseUser) session.getAttribute("ApplicationUser");
  String userType = appUser.getUser().getUserTypeCd();

  String accountName = "";
  String accountNumber = "";
  String siteErp = "";
  String contractName = "";  
  String siteName = "";

  if (null != appUser && null != appUser.getSite()){
      
      siteName = appUser.getSite().getBusEntity().getShortDesc();      
      accountName = appUser.getUserAccount().getBusEntity().getShortDesc();
      accountNumber = appUser.getUserAccount().getBusEntity().getErpNum();
      siteErp = appUser.getSite().getBusEntity().getErpNum();  
      contractName = (String)session.getAttribute("ContractName");
      
  }

  if(userType.equals(RefCodeNames.USER_TYPE_CD.CUSTOMER_SERVICE) || 
     userType.equals(RefCodeNames.USER_TYPE_CD.ADMINISTRATOR) ||
     userType.equals(RefCodeNames.USER_TYPE_CD.STORE_ADMINISTRATOR) ||
     userType.equals(RefCodeNames.USER_TYPE_CD.CRC_MANAGER)){
%>
<table align="center" border=0" cellspacing="0" cellpadding="0" width="Constants.TABLEWIDTH" class="textreadonly">
<tr><td>
<b><app:storeMessage key="shop.quick.text.accounName:"/></b>  <%=accountName%> </td></tr>
<tr><td>
<b><app:storeMessage key="shop.quick.text.accounErp:"/></b> <%=accountNumber%>  </td></tr>
<tr><td><b><app:storeMessage key="shop.quick.text.siteName:"/></b> <%=siteName%> </td></tr>
<tr><td><b><app:storeMessage key="shop.quick.text.siteErp:"/></b> <%=siteErp%> </td></tr>
<tr><td><b><app:storeMessage key="shop.quick.text.contractName:"/></b> <%=contractName%> </td></tr>
</table>
<% } %>

<jsp:include flush='true' page="<%=fs%>"/>
</body>
</html>






