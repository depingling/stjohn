
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.forms.UserShopForm" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames"%>
<%@ page import="java.util.Date" %>
<%@ page import="java.io.File" %>


<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<style>
    td.secondaryNavButtonCurr {
  BACKGROUND-COLOR: #66CC66;
  color: #ffffff;
  text-transform: UPPERCASE;
    }

    A.secondaryNavButtonCurr:link {
  FONT-WEIGHT: bold; FONT-SIZE: 10px;
  COLOR: #ffffff;
  BACKGROUND-COLOR: #66CC66;
  TEXT-DECORATION: none
  text-transform: UPPERCASE;
}
A.secondaryNavButtonCurr:visited {
  FONT-WEIGHT: bold;
  FONT-SIZE: 10px;
  COLOR: #ffffff;
  BACKGROUND-COLOR: #66CC66;
  TEXT-DECORATION: none
  text-transform: UPPERCASE;
}

A.secondaryNavButtonCurr:active {
  FONT-WEIGHT: bold; FONT-SIZE: 10px;
  COLOR: #ffffff;
  BACKGROUND-COLOR: #003399;
  TEXT-DECORATION: none
  text-transform: UPPERCASE;
}
A.secondaryNavButtonCurr:hover {
  COLOR: #ffffff
  text-transform: UPPERCASE;
}


A.secondaryNavButton:link {
  text-transform: UPPERCASE;
  FONT-WEIGHT: bold; FONT-SIZE: 10px; COLOR: #ffffff; BACKGROUND-COLOR: #003399; TEXT-DECORATION: none
}
A.secondaryNavButton:visited {
  text-transform: UPPERCASE;
  FONT-WEIGHT: bold; FONT-SIZE: 10px; COLOR: #ffffff; BACKGROUND-COLOR: #003399; TEXT-DECORATION: none
}
A.secondaryNavButton:active {
  text-transform: UPPERCASE;
  FONT-WEIGHT: bold; FONT-SIZE: 10px; COLOR: #ffffff; BACKGROUND-COLOR: #66CC66; TEXT-DECORATION: none
}
A.secondaryNavButton:hover {
  text-transform: UPPERCASE;
  BACKGROUND-COLOR: #66CC66
}
.subHeader {
  FONT-WEIGHT: bold;
  FONT-SIZE: 14px;
  COLOR: #ffffff;
  BACKGROUND-COLOR: #66cc66;
  TEXT-DECORATION: none;
  text-transform: UPPERCASE;
  PADDING-LEFT: 5px;
}
A.thirdNavButtonCurr:link {
  FONT-SIZE: 10px;
  text-transform: UPPERCASE;
  COLOR: #66cc66;
  BACKGROUND-COLOR: #ffffff;
  PADDING: 5px;
  TEXT-DECORATION: none;
}
A.thirdNavButtonCurr:visited {
  FONT-SIZE: 10px;
  text-transform: UPPERCASE;
  COLOR: #66cc66;
  BACKGROUND-COLOR: #ffffff;
  PADDING: 5px;
  TEXT-DECORATION: none;
}
A.thirdNavButtonCurr:active {
  text-transform: UPPERCASE;
  COLOR: #66cc66;
  BACKGROUND-COLOR: #ffffff;
  PADDING: 5px;
}
A.thirdNavButtonCurr:hover {
  text-transform: UPPERCASE;
  COLOR: #66cc66;
  BACKGROUND-COLOR: #ffffff;
  PADDING: 5px;
}
A.thirdNavButton:link {
  text-transform: UPPERCASE;
  FONT-WEIGHT: bold;
  FONT-SIZE: 10px;
  COLOR: #ffffff;
  BACKGROUND-COLOR: #66cc66;
  TEXT-DECORATION: none;
  PADDING: 5px;
}
A.thirdNavButton:visited {
  text-transform: UPPERCASE;
  FONT-WEIGHT: bold;
  FONT-SIZE: 10px;
  COLOR: #ffffff;
  BACKGROUND-COLOR: #66cc66;
  TEXT-DECORATION: none;
  PADDING: 5px;
}
A.thirdNavButton:active {
  text-transform: UPPERCASE;
  FONT-WEIGHT: bold;
  FONT-SIZE: 10px;
  COLOR: #ffffff;
  BACKGROUND-COLOR: #66cc66;
  TEXT-DECORATION: none;
  PADDING: 5px;
}
A.thirdNavButton:hover {
  text-transform: UPPERCASE;
  BACKGROUND-COLOR: #ffffff;
  color: #66cc66;
  PADDING: 5px;
}
.toolbar1 {
  BACKGROUND-COLOR: #003366
}
.toolbar2 {
  BACKGROUND-COLOR: #66cc66
}


</style>

<!-- adjust images to current state -->
<% String storeDir=ClwCustomizer.getStoreDir(); %>
<%
UserShopForm theForm = (UserShopForm) session.getAttribute("SHOP_FORM");
CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
boolean punchOutFl = false;
if(appUser.getUserAccount() != null &&
  RefCodeNames.CUSTOMER_SYSTEM_APPROVAL_CD.PUNCH_OUT_INLIN_NON_E_ORD_ONLY.
        equals(appUser.getUserAccount().getCustomerSystemApprovalCd())) {
   punchOutFl = true;
}
String uri = request.getRequestURI();
String action = request.getParameter("action");
if (null == action) {
  action="--none--";
}
String source = request.getParameter("source");
if(source==null) source = "";

/* Screen source value. */
String sc_source = (String)session.getAttribute("sc_source");
if(sc_source==null) sc_source = "";


int tabi = 0;
if ( uri.indexOf("orderstatus") >= 0 ||
  uri.indexOf("userOrderDetail") >= 0 ) {

  if (action.indexOf("search_all_sites") >= 0 ||
    sc_source.indexOf("search_all_sites") >= 0) {
    tabi = 32;
  } else {
    tabi = 3;
  }

} else if ( uri.indexOf("pendingOrders") >= 0 ||
  uri.indexOf("handleOrder") >= 0 ) {
  tabi = 31;
} else {
if (action.equals("guide") ) {
  tabi = 0;
}
else if (action.equals("catalog") ) {
  tabi = 1;
}
else if (action.equals("onsite_services") ||
 action.equals("send_onsite_service_request")) {
  // on site services request.
  tabi = 2;
}
else if (action.equals("order_status") ) {
  // order status interface
  tabi = 3;
}
else if (action.startsWith("janitorCloset")) {
  // janitor closet express order
  tabi = 4;
}
else if (action.startsWith("lastOrder")) {
  // last order express order
  tabi = 5;
}
else if (action.startsWith("quickOrder")) {
  // quick order express order
  tabi = 6;
}
else if (action.startsWith("orderScheduler")) {
  // order schedule
  tabi = 7;
}
else if (action.startsWith("editOrderGuide")) {
  // edit order guide
  tabi = 8;
}
else if (action.startsWith("item")) {
  //item detail
  if(source.equalsIgnoreCase("catalog") || source.equalsIgnoreCase("orderGuide")) {
    tabi = 0;
  }
  else if(source.equalsIgnoreCase("orderGuide")) {
    tabi = 0;
  }
  else if(source.equalsIgnoreCase("janitorCloset")) {
    tabi = 4;
  }
  else if(source.equalsIgnoreCase("lastOrder")) {
    tabi = 5;
  }
  else if(source.equalsIgnoreCase("quickOrder")) {
    tabi = 6;
  }
  else if(source.equalsIgnoreCase("editOrderGuide")) {
    tabi = 8;
  }
  else {
    tabi = 99;
  }
}
}

String img_row2 = "";

String img0_row2 = ClwI18nUtil.getMessage(request,"shop.heading.siteOrders",null);
String img1_row2 = ClwI18nUtil.getMessage(request,"shop.heading.pendingOrders",null);
String img2_row2 = ClwI18nUtil.getMessage(request,"shop.heading.allOrders",null);

String expressCl = "secondaryNavButton";
String cw_shopsecondaryexpress = ClwI18nUtil.getMessage(request,"shop.menu.expressOrder",null);
String onsiteCl = "secondaryNavButton";
String cw_shopsecondaryonsite = ClwI18nUtil.getMessage(request,"shop.menu.onSiteServices",null);
String ostatusCl = "secondaryNavButton";
String cw_shopsecondaryorderstatus = ClwI18nUtil.getMessage(request,"shop.menu.orderStatus",null);
String guideCl = "secondaryNavButton";
String cw_shopsecondaryguide = ClwI18nUtil.getMessage(request,"shop.menu.orderGuide",null);
String productCl = "secondaryNavButton";
String cw_shopsecondaryproduct = ClwI18nUtil.getMessage(request,"shop.menu.productCatalog",null);
String janitorCl = "thirdNavButton";
String cw_janitorcloset = ClwI18nUtil.getMessage(request,"shop.menu.historicalItems",null);
String lastorderCl = "thirdNavButton";
String cw_lastorder = ClwI18nUtil.getMessage(request,"shop.menu.lastOrder",null);
String quickorderCl = "thirdNavButton";
String cw_quickorder = ClwI18nUtil.getMessage(request,"shop.menu.quickOrder",null);

String siteorderCl = "thirdNavButton";
String pendingorderCl = "thirdNavButton";
String allorderCl = "thirdNavButton";

switch (tabi) {
  case 99:
    img_row2 = ClwI18nUtil.getMessage(request,"shop.heading.shopProductItem",null);
    break;

  case 8:
    productCl = "secondaryNavButton";
    guideCl = "secondaryNavButtonCurr";
    img_row2 = ClwI18nUtil.getMessage(request,"shop.heading.shopProductCatalog",null);
    break;

  case 7:
    productCl = "secondaryNavButton";
    guideCl = "secondaryNavButtonCurr";
    img_row2 = ClwI18nUtil.getMessage(request,"shop.heading.shopProductCatalog",null);
    break;

  case 6:
    expressCl = "secondaryNavButtonCurr";
    quickorderCl = "thirdNavButtonCurr";
    img_row2 = ClwI18nUtil.getMessage(request,"shop.heading.shopExpressOrder",null);
    break;

  case 5:
    expressCl = "secondaryNavButtonCurr";
    lastorderCl = "thirdNavButtonCurr";
    img_row2 = ClwI18nUtil.getMessage(request,"shop.heading.shopExpressOrder",null);
    break;

  case 4:
    expressCl = "secondaryNavButtonCurr";
    janitorCl = "thirdNavButtonCurr";
    img_row2 = ClwI18nUtil.getMessage(request,"shop.heading.shopExpressOrder",null);
    break;

  case 3:
    productCl = "secondaryNavButton";
    ostatusCl = "secondaryNavButtonCurr";
    img_row2 = ClwI18nUtil.getMessage(request,"shop.heading.shopOrderStatus",null);
    siteorderCl = "thirdNavButtonCurr";
    pendingorderCl = "thirdNavButton";
    allorderCl = "thirdNavButton";
  break;

  case 31:
    productCl = "secondaryNavButton";
    ostatusCl = "secondaryNavButtonCurr";
    img_row2 = ClwI18nUtil.getMessage(request,"shop.heading.shopOrderStatus",null);
    siteorderCl = "thirdNavButton";
    pendingorderCl = "thirdNavButtonCurr";
    allorderCl = "thirdNavButton";
  break;

  case 32:
    productCl = "secondaryNavButton";
    ostatusCl = "secondaryNavButtonCurr";
    img_row2 = ClwI18nUtil.getMessage(request,"shop.heading.shopOrderStatus",null);
    siteorderCl = "thirdNavButton";
    pendingorderCl = "thirdNavButton";
    allorderCl = "thirdNavButtonCurr";
  break;

  case 2:
    onsiteCl = "secondaryNavButtonCurr";
    productCl = "secondaryNavButton";
    img_row2 = ClwI18nUtil.getMessage(request,"shop.heading.shopOnSiteService",null);
  break;

  default:
    if(theForm!=null) {
      productCl = (theForm.getShopMethod()==Constants.SHOP_BY_CATALOG)? "secondaryNavButtonCurr" : "secondaryNavButton";
      guideCl = (theForm.getShopMethod()==Constants.SHOP_BY_ORDER_GUIDE)? "secondaryNavButtonCurr" : "secondaryNavButton";
    } else {
      productCl = "secondaryNavButton";
      guideCl = "secondaryNavButtonCurr";
    }
    img_row2 = ClwI18nUtil.getMessage(request,"shop.heading.shopProductCatalog",null);
  break;
}

%>

<!-- pickup images -->
<%
String cw_shopproductcatalog = ClwI18nUtil.getMessage(request,"shop.heading.shopProductCatalog",null);
String cw_shoporderguide  = ClwI18nUtil.getMessage(request,"shop.heading.shopOrderGuide",null);

String cw_shopsecondarydownspacerPath=ClwCustomizer.getSIP(request,"cw_spacer.gif");
String cw_shopsecondaryleftPath=ClwCustomizer.getSIP(request,"cw_spacer.gif");
String cw_shopsecondaryupspacerPath=ClwCustomizer.getSIP(request,"cw_spacer.gif");
String cw_shopsecondaryvertPath=ClwCustomizer.getSIP(request,"cw_spacer.gif");
String cw_shopunderlogoPath=ClwCustomizer.getSIP(request,"cw_shopunderlogo.gif");
String cw_spacer=ClwCustomizer.getSIP(request,"cw_spacer.gif");

String ogshoppingCl = "thirdNavButtonCurr";
String orderGuideShopping = ClwI18nUtil.getMessage(request,"shop.menu.orderGuideShopping",null);

String ogserviceCl = "thirdNavButton";
String orderGuideService = ClwI18nUtil.getMessage(request,"shop.menu.orderGuideService",null);

String ogscheduleCl = "thirdNavButton";
String orderScheduler = ClwI18nUtil.getMessage(request,"shop.menu.orderGuideShcedule",null);

%>


<table align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH1%>">
<% Object errors =request.getAttribute("org.apache.struts.action.ERROR");
   if(errors!=null) {
%>
<tr><td colspan="2" class="rederror" align="center"><html:errors/></td></tr>
<%
}
%>

<tr>
<% String longRow1 =
  "<td colspan='2' valign='middle' class=\"shoponsiteservemed\" width=\"+Constants.TABLEWIDTH1+\">"+
  "<table border=0 cellpadding='0' cellspacing='0'><tr><td class="+guideCl+">" +
  "<img src="+cw_shopsecondaryleftPath+" WIDTH=\"11\" HEIGHT=\"25\">"+
  "</td><td class="+guideCl+">"+
  "<a href=\"shop.do?action=guide\" class=\""+guideCl+"\">"+
  cw_shopsecondaryguide+
  "</a>"+
  "</td><td class="+guideCl+">"+
  "<img src="+cw_shopsecondaryupspacerPath+" WIDTH=\"8\" HEIGHT=\"25\">"+
  "</td><td class="+productCl+">"+
  "<img src="+cw_shopsecondaryupspacerPath+" WIDTH=\"8\" HEIGHT=\"25\">"+
  "</td><td class="+productCl+">"+
  "<a href=\"shop.do?action=catalog\" class=\""+productCl+"\">"+
  cw_shopsecondaryproduct+
  "</a>"+
  "</td><td class="+productCl+">"+
  "<img src="+cw_shopsecondarydownspacerPath+" WIDTH=\"8\" HEIGHT=\"25\">"+
  "</td><td class="+expressCl+">"+
  "<img src="+cw_shopsecondarydownspacerPath+" WIDTH=\"8\" HEIGHT=\"25\">"+
  "</td><td class="+expressCl+">"+
  "<a href=\"quickOrder.do?action=quickOrderInit\"  class=\""+expressCl+"\">"+
  cw_shopsecondaryexpress+
  "</a>"+
  "</td><td class="+expressCl+">"+
  "<img src="+cw_shopsecondarydownspacerPath+" WIDTH=\"8\" HEIGHT=\"25\">"+
  "</td><td class="+ostatusCl+">"+
  "<img src="+cw_shopsecondarydownspacerPath+" WIDTH=\"8\" HEIGHT=\"25\">"+
  "</td><td class="+ostatusCl+">"+
  "<a href=\"../store/orderstatus.do?action=order_status\"  class=\""+ostatusCl+"\">"+
  cw_shopsecondaryorderstatus+
  "</a>"+
  "</td></tr></table>"+
  "</td>";
%>
<%=longRow1%>
</tr>

<tr>
<% if(tabi==2 ) { %>
  <td class="subHeader"><%=img_row2%></td>
  <td><img src="<%=cw_spacer%>" border="0" height="25" WIDTH="1"></td>
<% } else if ( tabi == 3 || tabi == 31 || tabi == 32 ) { %>
  <td class="subHeader"><%=img_row2%></td>
  <td class="subHeader" align="right">
    <img src="<%=cw_spacer%>" border="0" height="25" WIDTH="1">

    <a href="orderstatus.do?action=order_status" class="<%=siteorderCl%>"><%=img0_row2%></a>
    <a href="pendingOrders.do" class="<%=pendingorderCl%>"><%=img1_row2%></a>
    <a href="orderstatus.do?action=search_all_sites_init" class="<%=allorderCl%>"><%=img2_row2%></a>
  </td>
<% } else if(tabi==4 || tabi==5 || tabi==6) { %>
  <td class="subHeader"> <%=img_row2%></td>
  <td class="subHeader" align="right">
    <img src="<%=cw_spacer%>" border="0" height="25" WIDTH="1">
    <a href="quickOrder.do?action=quickOrderInit" class="<%=quickorderCl%>"><%=cw_quickorder%> </a>
    <a href="janitorCloset.do?action=janitorClosetInit" class="<%=janitorCl%>"><%=cw_janitorcloset%> </a>
    <a href="lastOrder.do?action=lastOrderInit" class="<%=lastorderCl%>"><%=cw_lastorder%> </a>
  </td>
<% } else if(tabi==7) {
  ogshoppingCl = "thirdNavButton";
  ogserviceCl = "thirdNavButton";
  ogscheduleCl = "thirdNavButtonCurr";
%>
  <td class="subHeader"><%=cw_shoporderguide%></td>
  <td class="subHeader" align="right">
    <img src="<%=cw_spacer%>" border="0" height="25" WIDTH="1">
    <a href="/<%=storeDir%>/store/shop.do?action=guide" class="<%=ogshoppingCl%>"><%=orderGuideShopping%></a>
    <a href="editOrderGuide.do?action=editOrderGuideInit" class="<%=ogserviceCl%>"><%=orderGuideService%></a>
    <%if(!punchOutFl){%>
      <a href="/<%=storeDir%>/store/orderScheduler.do?action=orderSchedulerInit" class="<%=ogscheduleCl%>">
      <%=orderScheduler%></a>
    <% } %>
  </td>
<% } else if(tabi==8) {
  ogshoppingCl = "thirdNavButton";
  ogserviceCl = "thirdNavButtonCurr";
  ogscheduleCl = "thirdNavButton";
%>
  <td class="subHeader"><%=cw_shoporderguide%></td>
  <td class="subHeader" align="right">
    <img src="<%=cw_spacer%>" border="0" height="25" WIDTH="1">
    <a href="/<%=storeDir%>/store/shop.do?action=guide" class="<%=ogshoppingCl%>"><%=orderGuideShopping%></a>
    <a href="editOrderGuide.do?action=editOrderGuideInit" class="<%=ogserviceCl%>"><%=orderGuideService%></a>
    <%if(!punchOutFl){%>
      <a href="/<%=storeDir%>/store/orderScheduler.do?action=orderSchedulerInit" class="<%=ogscheduleCl%>">
      <%=orderScheduler%></a>
    <% } %>
  </td>
<% } else if(tabi==99) { %>
  <td class="subHeader"><%=img_row2%></td>
  <td class="subHeader" align="right">
   <img src="<%=cw_spacer%>" border="0" height="25" WIDTH="1">
  </td>
<% } else { %>

  <% if(theForm!=null) { %>
    <% if(theForm.getShopMethod()==Constants.SHOP_BY_CATALOG) { %>
      <td class="subHeader"><%=cw_shopproductcatalog%></td>
      <td class="subHeader" align="right">
        <img src="<%=cw_spacer%>" border="0" height="25" WIDTH="1">
      </td>
    <% } else { %>
      <td class="subHeader"><%=cw_shoporderguide%></td>
      <td class="subHeader" align="right">
        <img src="<%=cw_spacer%>" border="0" height="25" WIDTH="1">
        <a href="/<%=storeDir%>/store/shop.do?action=guide" class="<%=ogshoppingCl%>"><%=orderGuideShopping%></a>
        <a href="editOrderGuide.do?action=editOrderGuideInit" class="<%=ogserviceCl%>"><%=orderGuideService%></a>
        <%if(!punchOutFl){%>
          <a href="/<%=storeDir%>/store/orderScheduler.do?action=orderSchedulerInit" class="<%=ogscheduleCl%>">
          <%=orderScheduler%></a>
        <% } %>
      </td>
    <% } %>
  <% } else { %>
  <td class="subHeader"><%=cw_shoporderguide%></td>
  <td class="subHeader" align="right">
    <img src="<%=cw_spacer%>" border="0" height="25" WIDTH="1">
    <a href="/<%=storeDir%>/store/shop.do?action=guide" class="<%=ogshoppingCl%>"><%=orderGuideShopping%></a>
    <a href="editOrderGuide.do?action=editOrderGuideInit" class="<%=ogserviceCl%>"><%=orderGuideService%></a>
    <%if(!punchOutFl){%>
      <a href="/<%=storeDir%>/store/orderScheduler.do?action=orderSchedulerInit" class="<%=ogscheduleCl%>">
      <%=orderScheduler%></a>
    <% } %>
  </td>
  <% } %>
  <% } %>
</tr>

</table>
