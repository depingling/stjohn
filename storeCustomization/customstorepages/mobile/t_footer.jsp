<%@ page language="java" %>

<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>


<div class="break"></div>
<% String storeDir=ClwCustomizer.getStoreDir(); 
CleanwiseUser appUser = (CleanwiseUser) session.getAttribute("ApplicationUser");
String shippingAddress = "";
String addr2 = "";
String customerName = "";
String accountName = "";

if ( null != appUser && null != appUser.getSite() &&
  ShopTool.isAnOCISession(request.getSession()) == false
  ) {
  AddressData address = appUser.getSite().getSiteAddress();

  if ( null != address ) {
    shippingAddress = address.getAddress1()+" ";
    addr2 = address.getAddress2();

    if(addr2!=null && addr2.trim().length()>0 ) shippingAddress+=addr2+" ";
    String addr3 = address.getAddress3();
    if(addr3!=null && addr3.trim().length()>0 ) shippingAddress+=addr3+" ";
    String addr4 = address.getAddress4();
    if(addr4!=null && addr4.trim().length()>0 ) shippingAddress+=addr4+" ";
    shippingAddress+=address.getCity()+", ";
    if (appUser.getUserStore().isStateProvinceRequired()) {
      shippingAddress+= address.getStateProvinceCd() != null ? address.getStateProvinceCd()+" " : "";
    }
    shippingAddress+=address.getPostalCode()+" ";
    shippingAddress+=Constants.getCountryName(address.getCountryCd());
    customerName = appUser.getUser().getFirstName()+" "+appUser.getUser().getLastName()+" ";
    accountName = appUser.getSite().getBusEntity().getShortDesc();
  }

}

%>

<div class="fivesidemargin shop_header_top_address">
    <% if ( shippingAddress.length() > 0 ) { %>
    <%=accountName%>&nbsp;<app:storeMessage key="global.text.at"/>&nbsp;<%=shippingAddress%>
    <% } else { %>
    <%=accountName%>
    <% } %>
</div>
<%
SiteData siteData = ShopTool.getCurrentSite(request);
 if ( siteData != null &&
      ShopTool.showScheduledDelivery(request) &&
      siteData.getNextDeliveryDate() != null ) {  //inventory display if statment begin %>

<div class="break"></div>
<div>
		<%
		if (siteData.hasInventoryShoppingOn()) {%>
		    <app:storeMessage key="shop.header.text.inventoryShopping"/>,
		<% } %>
		
		<% if ( siteData.getNextOrdercutoffDate() != null ) { %>
		<span class="textLabel"><app:storeMessage key="shop.header.text.orderCutOff"/></span>
		<!-- i18n:formatDate value="%=siteData.getNextOrdercutoffDate()%"  locale="%=appUser.getPrefLocale()%" style="medium"/ -->
		<%=ClwI18nUtil.formatDate(request,siteData.getNextOrdercutoffDate(),DateFormat.MEDIUM)%>
		<% } %>
		<% if ( siteData.getNextDeliveryDate() != null ) { %>
		&nbsp;&nbsp;<span class="textLabel"><app:storeMessage key="shop.header.text.schedDeliveryDate"/></span>
		<!-- i18n:formatDate value="%=siteData.getNextDeliveryDate()%" locale="%=appUser.getPrefLocale()%" style="medium"/ -->
		<%=ClwI18nUtil.formatDate(request,siteData.getNextDeliveryDate(),DateFormat.MEDIUM)%>
		<% } %>
</div>
		
<% } //inventory display if statment END%>
<div class="break"></div>
<div class="footer">
	<app:custom pageElement="pages.footer.msg"/>
</div>

<head>
  <meta http-equiv="Pragma" content="no-cache">
  <meta http-equiv="Expires" content="-1">
  <meta http-equiv="Cache-Control" content="no-cache">
</head>
