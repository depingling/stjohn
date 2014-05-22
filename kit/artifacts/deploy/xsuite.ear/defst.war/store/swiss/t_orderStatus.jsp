<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames"%>
<%@ page import="com.cleanwise.service.api.util.Utility"%>

<%@ page import="java.util.Locale" %>
<%@ page import="java.text.*" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.TimeZone" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<%@ page import="java.math.*" %>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<%

SessionTool st = new SessionTool(request);
CleanwiseUser appUser = (CleanwiseUser) session.getAttribute("ApplicationUser");
SimpleDateFormat sdf;
  String userType = (String)session.getAttribute(Constants.USER_TYPE);
  if ( null == userType || "".equals(userType)) {
    userType = RefCodeNames.USER_TYPE_CD.CUSTOMER;
  }

  String reqAction = request.getParameter("action");
    if (null == reqAction) {
        reqAction="--none--";
    }
String sc_source = (String)session.getAttribute("sc_source");
if ( null == sc_source ) sc_source = "";

TimeZone timeZone =  Utility.getTimeZoneFromUserData(appUser.getSite());
%>

<table align=center CELLSPACING=0 CELLPADDING=2
  width="<%=Constants.TABLEWIDTH%>" class="tbstd">

<html:form name="USER_ORDER_STATUS_FORM" action="/store/orderstatus.do"
    focus="orderDateRangeBegin"
    scope="session" type="com.cleanwise.view.forms.UserOrderStatusForm">

  <tr>
       <td>&nbsp;</td>
       <td><b><app:storeMessage key="shop.orderStatus.text.orderDate:"/></b><br>
       (<%=ClwI18nUtil.getUIDateFormat(request)%>)
       </td>
       <td>
        <table align="left" CELLSPACING="0" CELLPADDING="0">
        <tr>
        <td>
         <app:storeMessage key="shop.orderStatus.text.beginDateRange"/><br>
        <html:text name="USER_ORDER_STATUS_FORM" property="orderDateRangeBegin" />
        </td>
        <td>
        <app:storeMessage key="shop.orderStatus.text.endDateRange"/><br>
        <html:text name="USER_ORDER_STATUS_FORM" property="orderDateRangeEnd" />
        <span class="reqind" valign="top">*</span>
        </td>
        </tr>
        </table>
       </td>
     <td>&nbsp;</td>
  </tr>

<% if (reqAction.indexOf("search_all_sites") >= 0 ||
  sc_source.equals( "search_all_sites" )
) {
%>

  <tr>
       <td>&nbsp;</td>
       <td><b><app:storeMessage key="shop.orderStatus.text.zipCode:"/></b></td>
     <td>
      <html:text name="USER_ORDER_STATUS_FORM" property="siteZipCode" />
       </td>
     <td>&nbsp;</td>
  </tr>

  <tr>
       <td>&nbsp;</td>
       <td><b><app:storeMessage key="shop.orderStatus.text.city:"/></b></td>
     <td>
      <html:text name="USER_ORDER_STATUS_FORM" property="siteCity" />
       </td>
     <td>&nbsp;</td>
  </tr>

  <tr>
       <td>&nbsp;</td>
       <td>
         <% if (appUser.getUserStore().isStateProvinceRequired()) { %>
         <b><app:storeMessage key="shop.orderStatus.text.state:"/></b>
         <%} else { %>
         &nbsp;
         <%} %>
       </td>
     <td>
      <% if (appUser.getUserStore().isStateProvinceRequired()) { %>
      <html:text name="USER_ORDER_STATUS_FORM" property="siteState" size="2" maxlength="2"/>&nbsp;&nbsp;
      <%} %>
      <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.RECEIVING%>">
        <b><app:storeMessage key="shop.orderStatus.text.orderNotReceivedOnlyYN:"/></b>
        <html:radio property="ordersNotReceivedOnly" value="y"/>
        <html:radio property="ordersNotReceivedOnly" value="n"/>
      </app:authorizedForFunction>
       <html:hidden property="action" value="search_all_sites"/>
       <html:submit styleClass="store_fb" property="action">
          <app:storeMessage key="global.action.label.search" />
       </html:submit>
       </td>
     <td>&nbsp;</td>
  </tr>


<% } else { %>
<tr>
       <td>&nbsp;</td>
       <td><b><app:storeMessage key="shop.orderStatus.text.webOrder#/Confirmation#:"/></b></td>
     <td>
      <html:text name="USER_ORDER_STATUS_FORM" property="webOrderConfirmationNum" />
       </td>
     <td>&nbsp;</td>
  </tr>
  <tr>
       <td>&nbsp;</td>
       <td><b><app:storeMessage key="shop.orderStatus.text.customerPO#:"/></b></td>
     <td>
      <html:text name="USER_ORDER_STATUS_FORM" property="custPONum" />
       <html:hidden property="action" value="search"/>
       <html:submit styleClass="store_fb" property="action">
          <app:storeMessage key="global.action.label.search" />
       </html:submit>
  </td>
       <td>&nbsp;</td>
</tr>


<% } %>


</html:form>
</table>

<logic:present name="USER_ORDER_STATUS_FORM" property="resultList">
<bean:size id="rescount"  name="USER_ORDER_STATUS_FORM" property="resultList"/>

<table align=center CELLSPACING=0 CELLPADDING=0
  width="<%=Constants.TABLEWIDTH%>"  class="tbstd">
<tr>
<td>&nbsp; <b><app:storeMessage key="shop.orderStatus.text.resultCount:"/> <%=rescount%></b>
</td>
</tr>
</table>

<table align=center CELLSPACING=0 CELLPADDING=2
  width="<%=Constants.TABLEWIDTH%>" class="tbstd">
<tr align=left>
<td class="shopcharthead">
<div class="fivemargin">
<a class="shopchartheadlink" href="orderstatus.do?action=sort&sortField=webordernum">
  <app:storeMessage key="shop.orderStatus.text.order#"/></a>
</div></td>
<td class="shopcharthead">
<div class="fivemargin">
<a class="shopchartheadlink" href="orderstatus.do?action=sort&sortField=refordernum">
 <app:storeMessage key="shop.orderStatus.text.refOrder#"/></a>
</div></td>
<td width=80 class="shopcharthead"><div class="fivemargin">
<a class="shopchartheadlink" href="orderstatus.do?action=sort&sortField=orderdate">
 <app:storeMessage key="shop.orderStatus.text.date"/></a>
</div></td>
<td class="shopcharthead"><div class="fivemargin">
<a class="shopchartheadlink" href="orderstatus.do?action=sort&sortField=custponum">
  <app:storeMessage key="shop.orderStatus.text.poNum"/></a>
</div></td>
<td class="shopcharthead"><div class="fivemargin">
<a class="shopchartheadlink" href="orderstatus.do?action=sort&sortField=sitename">
 <app:storeMessage key="shop.orderStatus.text.siteName"/></a>
</div></td>
<td class="shopcharthead"><div class="fivemargin">
<a class="shopchartheadlink" href="orderstatus.do?action=sort&sortField=status">
  <app:storeMessage key="shop.orderStatus.text.status"/></a>
</div></td>
<td class="shopcharthead"><div class="fivemargin">
<a class="shopchartheadlink" href="orderstatus.do?action=sort&sortField=sitecity">
 <app:storeMessage key="shop.orderStatus.text.city"/></a>
</div></td>
<% if (appUser.getUserStore().isStateProvinceRequired()) { %>
<td class="shopcharthead"><div class="fivemargin">
  <a class="shopchartheadlink" href="orderstatus.do?action=sort&sortField=sitestate">
    <app:storeMessage key="shop.orderStatus.text.state"/></a>
  </div></td>
<%} %>
<td class="shopcharthead"><div class="fivemargin">
<a class="shopchartheadlink" href="orderstatus.do?action=sort&sortField=zipcode">
 <app:storeMessage key="shop.orderStatus.text.zipCode"/></a>
</div></td>
<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
<td class="shopcharthead">
<div class="fivemargin">
<a class="shopchartheadlink" href="orderstatus.do?action=sort&sortField=amount">
  <app:storeMessage key="shop.orderStatus.text.totalExcludingVOC"/></a>
</div></td>
</logic:equal>
<td class="shopcharthead">
<div class="fivemargin">
<a class="shopchartheadlink" href="orderstatus.do?action=sort&sortField=placedby">
 <app:storeMessage key="shop.orderStatus.text.placedBy"/></a>
</div></td>


</tr>

<logic:iterate id="arrele" name="USER_ORDER_STATUS_FORM" property="resultList"
 indexId="i" type="com.cleanwise.service.api.value.OrderStatusDescData">

<tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)i)%>">
<td>

<%
// All items were added by the system, this indicates and auto order
// placed by the inventory logic.  durval, 3/16/2006
%>
<logic:equal name="arrele" property="allOrderItemsAddedBySystemFlag"
  value="true">
<span class="inv_item">
&nbsp;A&nbsp;
</span>
</logic:equal>

<%
String orderLocale = arrele.getOrderDetail().getLocaleCd();
if(orderLocale==null) orderLocale = "";
%>
<bean:define id="oid" name="arrele" property="orderDetail.orderId"/>
<!-- bean:define id="orderLocale" name="arrele" property="orderDetail.localeCd"/ -->
<bean:define id="temps"  type="java.lang.String"  name="arrele" property="orderDetail.orderStatusCd"/>
<%

String xlateStatus = com.cleanwise.view.utils.ShopTool.xlateStatus(arrele, request);
boolean bakedFl = (xlateStatus==null || xlateStatus.equals("Ordered-Processing"))? false:true;
String viewOrderUrl = "userOrderDetail.do?action=view&amp;" +
 "id=" + oid;
%>

<% if ( /*(appUser.isaCustomer() || appUser.isaMSB()) ||*/ !bakedFl) { %>
<bean:write name="arrele" property="orderDetail.orderNum"/>
<% } else { %>
<a href="<%=viewOrderUrl%>">
<bean:write name="arrele" property="orderDetail.orderNum"/> </a>
<% } %>
</td>
<td>

<bean:write name="arrele" property="orderDetail.refOrderNum" />
</td>
<%-- <td><bean:write name="arrele" property="orderDetail.originalOrderDate" /></td> --%>
<%
   //sdf = new SimpleDateFormat("yyyy-MM-dd");
   //sdf.setTimeZone(timeZone);
   Date date = ((OrderStatusDescData) arrele).getOrderDetail().getOriginalOrderDate();
   Date time = ((OrderStatusDescData) arrele).getOrderDetail().getOriginalOrderTime();
   Date orderDate = Utility.getDateTime(date, time);
	
	String formattedODate = ClwI18nUtil.formatDateInp(request, date, timeZone.getID() );
%>
<td><%=formattedODate%></td>
<td style="padding: 3px;">
<bean:write name="arrele" property="orderDetail.requestPoNum" /></td>
<td><bean:write name="arrele" property="orderDetail.orderSiteName" /></td>
<td> 
<%=xlateStatus%>
<%
if(RefCodeNames.ORDER_STATUS_CD.PENDING_DATE.equals(temps)) {
 String processOnDateS = ((OrderStatusDescData) arrele).getPendingDate();
 SimpleDateFormat defaultFormat = new SimpleDateFormat("MM/dd/yyyy");
 Date defaultDate = defaultFormat.parse(processOnDateS);
 
 String pattern = ClwI18nUtil.getDatePattern(request);
 sdf = new SimpleDateFormat(pattern);
 
 //sdf.setTimeZone(timeZone); -- date convertation should be reviewed later (YK)
 Date processOnDate = null;
 if(processOnDateS!=null && processOnDateS.trim().length()>0) {
   processOnDate = sdf.parse(sdf.format(defaultDate));
   
   //sdf = new SimpleDateFormat("yyyy-MM-dd");
   //sdf.setTimeZone(timeZone);  -- date convertation should be reviewed later (YK)
   String formattedProcessOnDate = ClwI18nUtil.formatDateInp(request,processOnDate, timeZone.getID() );
   
%>
<br><%=formattedProcessOnDate%>
<% } %>
<% } %>
<%if(RefCodeNames.ORDER_STATUS_CD.CANCELLED.equals(temps) &&
                  arrele.getConsolidatedOrder()!=null) { %>
Consolidated
<% } %>
</td>
<td><bean:write name="arrele" property="shipTo.city" /></td>
<% if (appUser.getUserStore().isStateProvinceRequired()) { %>
<td style="text-align: center;">
<bean:write name="arrele" property="shipTo.stateProvinceCd" /></td>
<%} %>
<td>
<bean:write name="arrele" property="shipTo.postalCode" /></td>

<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
<td style="padding-left: 3px; text-align: right;">
<%
java.math.BigDecimal otot =  arrele.getSumOfAllOrderCharges();
%>
<% //added by SVC for Discount: %>
<% String stringDiscountAmt = arrele.getDiscountAmount(); %>
<% BigDecimal v_discountAmt = new BigDecimal(0); %>
<% if ( stringDiscountAmt != null && !stringDiscountAmt.trim().equals("")) {
	v_discountAmt = new BigDecimal(stringDiscountAmt);
   }
   v_discountAmt = v_discountAmt.setScale(2, BigDecimal.ROUND_HALF_UP); 
   if (v_discountAmt.compareTo(new BigDecimal(0))!=0 ) { // it is not 0
      otot = otot.add(v_discountAmt);
   }
%>
<% if(bakedFl) {
%>
<%=ClwI18nUtil.priceFormat(request, otot, (String) orderLocale," ")%>
<% } else { %>
&nbsp;
<% } %>
</td>
</logic:equal>

<td style="padding-left: 3px;">
 <logic:present name="arrele" property="placedBy">
   <logic:present name="arrele" property="placedBy.firstName"><bean:write name="arrele" property="placedBy.firstName" /> </logic:present>
   <br>
   <logic:present name="arrele" property="placedBy.lastName"><bean:write name="arrele" property="placedBy.lastName" /></logic:present>
  </logic:present>
   (<bean:write name="arrele" property="orderDetail.addBy" />)</td>
</tr>

</logic:iterate>
</table>

<table align=center CELLSPACING=0 CELLPADDING=0
  width="<%=Constants.TABLEWIDTH%>" class="tbstd">
<tr>
<td>

</logic:present>

<br>
</td>
</tr>

</table>

<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_table_bottom1.jsp")%>'/>

