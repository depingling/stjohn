
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

<%
   String color01 = "#FFFFFF";
   String color02 = "#003366";
   String color03 = "#006699";

%>

<style>
a.secondaryNavButtonCurr:link,a.secondaryNavButtonCurr:visited,a.secondaryNavButtonCurr:active   {
        color: <%=color01%>;
        background-color: <%=color03%>;
        font-size: 10px;
        font-weight: bold;
        TEXT-DECORATION: none;
        padding-left: .5em;
        padding-right: .5em;
        padding-bottom: 0em;
        border-top: solid 1px <%=color02%>;
        border-left: solid 1px <%=color02%>;
        border-right: solid 1px <%=color02%>;

}
a.secondaryNavButtonCurr:hover {
    color: <%=color01%>;
}

a.secondaryNavButton:link,a.secondaryNavButton:visited,a.secondaryNavButton:active   {
        color: <%=color01%>;
        background-color: <%=color02%>;
        font-size: 10px;
        font-weight: bold;
        TEXT-DECORATION: none;
        padding-left: .5em;
        padding-right: .5em;
        padding-bottom: 0em;
        border-top: solid 1px <%=color02%>;
        border-left: solid 1px <%=color02%>;
        border-right: solid 1px <%=color02%>;
}

a.secondaryNavButton:hover {
    background-color: <%=color03%>;
}
.subHeader   {
        color: <%=color01%>;
        background-color: <%=color03%>;
        font-size: 14px;
        font-weight: bold;
        TEXT-DECORATION: none;
        padding-left: .5em;
        padding-right: .5em;
}
.checkoutSubHeader {
    color: <%=color02%>;
    background-color:  #99cccc;
    font-size: 14px;
    font-weight: bold;
    height: 30px;
    TEXT-DECORATION: none;
    padding-left: .5em;
    padding-right: .5em;

}

a.thirdNavButtonCurr:link,a.thirdNavButtonCurr:visited,a.thirdNavButtonCurr:active   {
        color: <%=color02%>;
        background-color: <%=color01%>;
        font-size: 12px;
        font-weight: bold;
        TEXT-DECORATION: none;
        padding-left: .5em;
        padding-right: .5em;
        padding-bottom: 0em;

}
a.thirdNavButtonCurr:hover {
    color: <%=color02%>;
}

a.thirdNavButton:link,a.thirdNavButton:visited,a.thirdNavButton:active   {
        color: <%=color01%>;
        background-color:<%=color03%>;
        font-size: 12px;
        font-weight: bold;
        TEXT-DECORATION: none;
        padding-left: .5em;
        padding-right: .5em;
        padding-bottom: 0em;

}
a.thirdNavButton:hover {
    background-color: <%=color03%>;
}

.toolbar1{
    background-color : <%=color02%>;
}
.toolbar2{
    background-color : <%=color03%>;
}
TABLE.viewcart,A.linkButton:link, A.linkButton:visited, A.linkButton:active {
  FONT-WEIGHT: bold;
  background-color:<%=color01%>;
  COLOR: <%=color02%>;
  FONT-FAMILY: Arial, Helvetica, sans-serif;
  TEXT-DECORATION: none
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
  uri.indexOf("orderSearch") >= 0 ||
  uri.indexOf("orderDetail") >= 0 ||
  uri.indexOf("userOrderDetail") >= 0 ) {

  if (action.indexOf("search_all_sites") >= 0 ||
    sc_source.indexOf("search_all_sites") >= 0) {
    tabi = 32;
  } else if (action.indexOf("pending_orders") >= 0 ||
    sc_source.indexOf("pending_orders") >= 0) {
    tabi = 31;
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
String cw_janitorcloset = ClwI18nUtil.getMessage(request,"shop.menu.janitorCloset",null);
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

String cw_shopsecondarydownspacerPath=ClwCustomizer.getSIP(request,"cw_shopsecondarydownspacer.gif");
String cw_shopsecondaryleftPath=ClwCustomizer.getSIP(request,"cw_shopsecondaryleft.gif");
String cw_shopsecondaryupspacerPath=ClwCustomizer.getSIP(request,"cw_shopsecondaryupspacer.gif");
String cw_shopsecondaryvertPath=ClwCustomizer.getSIP(request,"cw_shopsecondaryvert.gif");
String cw_shopunderlogoPath=ClwCustomizer.getSIP(request,"cw_shopunderlogo.gif");
String cw_spacer=ClwCustomizer.getSIP(request,"cw_spacer.gif");

String ogshoppingCl = "thirdNavButtonCurr";
String orderGuideShopping = ClwI18nUtil.getMessage(request,"shop.menu.orderGuideShopping",null);

String ogserviceCl = "thirdNavButton";
String orderGuideService = ClwI18nUtil.getMessage(request,"shop.menu.orderGuideService",null);

String ogscheduleCl = "thirdNavButton";
String orderScheduler = ClwI18nUtil.getMessage(request,"shop.menu.orderGuideShcedule",null);

String toolLink01 = "shop.do?action=guide";
String toolLable01 = "shop.menu.orderGuide";

String toolLink02 = "shop.do?action=catalog";
String toolLable02 = "shop.menu.productCatalog";

String toolLink03 = "quickOrder.do?action=quickOrderInit";
String toolLable03 = "shop.menu.expressOrder";

String toolLink04 = "../store/orderSearch.do?action=order_status";
String toolLable04 = "shop.menu.orderStatus";

%>


<table align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH1%>">
<% Object errors =request.getAttribute("org.apache.struts.action.ERROR");
   if(errors!=null) {
%>
<tr><td colspan="2" class="rederror" align="center"><html:errors/></td></tr>
<%
}
%>
  <tr><!-- the following row contains a the border between navigation and content -->
	<td class="toolbar1" colspan="2">
    <img src='<%=ClwCustomizer.getSIP(request,"cw_spacer.gif")%>' WIDTH ="100%" HEIGHT="3">
	</td>
  </tr>
  <tr>
   <td class="toolbar1" align="left" colspan="2">
    <table  style="padding-bottom: 0em;" cellpadding="0"  cellspacing="0" class="headernavtable" style="border-collapse: collapse;">
      <tr>
        <td class="toolbar1" valign="bottom">
              <a class="<%=guideCl%>" href="<%=toolLink01%>"><%if(toolLable01!=null && toolLable01.length()>0){%><app:storeMessage key="<%=toolLable01%>"/><%}%></a>&nbsp;&nbsp;
        </td>
        <td class="toolbar1" valign="bottom">
              <a class="<%=productCl%>" href="<%=toolLink02%>"><%if(toolLable02!=null && toolLable02.length()>0){%><app:storeMessage key="<%=toolLable02%>"/><%}%></a>&nbsp;&nbsp;
        </td>
        <td class="toolbar1" valign="bottom">
              <a class="<%=expressCl%>" href="<%=toolLink03%>"><%if(toolLable03!=null && toolLable03.length()>0){%><app:storeMessage key="<%=toolLable03%>"/><%}%></a>&nbsp;&nbsp;
        </td>
        <td class="toolbar1" valign="bottom">
              <a class="<%=ostatusCl%>" href="<%=toolLink04%>"><%if(toolLable04!=null && toolLable04.length()>0){%><app:storeMessage key="<%=toolLable04%>"/><%}%></a>
        </td>

      </tr>
    </table>
   </td>
  </tr>

<tr>
<% if(tabi==2 ) { %>
  <td class="subHeader"><%=img_row2%></td>
  <td><img src="<%=cw_spacer%>" border="0" height="25" WIDTH="1"></td>
<% } else if ( tabi == 3 || tabi == 31 || tabi == 32 ) { %>
  <td class="subHeader"><%=img_row2%></td>
  <td class="subHeader" align="right">
    <img src="<%=cw_spacer%>" border="0" height="25" WIDTH="1">

    <a href="orderSearch.do?action=order_status" class="<%=siteorderCl%>"><%=img0_row2%></a>
    <a href="orderSearch.do?action=pending_orders" class="<%=pendingorderCl%>"><%=img1_row2%></a>
    <a href="orderSearch.do?action=search_all_sites_init" class="<%=allorderCl%>"><%=img2_row2%></a>
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
