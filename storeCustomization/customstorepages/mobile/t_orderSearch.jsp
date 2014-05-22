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
<bean:define id="theForm" name="ORDER_SEARCH_FORM" type="com.cleanwise.view.forms.OrderSearchForm" 
    scope="session"/>
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




<logic:present name="ORDER_SEARCH_FORM" property="resultList">
<bean:size id="rescount"  name="ORDER_SEARCH_FORM" property="resultList"/>

<div class="listResultsHeader">
<app:storeMessage key="shop.orderStatus.text.resultCount:"/> <%=rescount%>
</div>

<ul>
<%--<li class="listResultsHeader">
<div class="listResults">
  <a  href="orderSearch.do?action=sort&sortField=webordernum"><app:storeMessage key="shop.orderStatus.text.order#"/></a>
</div>
<div class="listResults">
  <a  href="orderSearch.do?action=sort&sortField=refordernum"><app:storeMessage key="shop.orderStatus.text.refOrder#"/></a>
</div>
<div class="listResults">
  <a href="orderSearch.do?action=sort&sortField=custponum"><app:storeMessage key="shop.orderStatus.text.poNum"/></a>
</div>

<div class="listResults">
  <a class="shopchartheadlink" href="orderSearch.do?action=sort&sortField=orderdate"><app:storeMessage key="shop.orderStatus.text.date"/></a>
</div>

<div class="listResults">
  <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
   <a class="shopchartheadlink" href="orderSearch.do?action=sort&sortField=amount"><app:storeMessage key="shop.orderStatus.text.total"/></a>
  </logic:equal>
</div>
</li>
--%>

<logic:iterate id="arrele" name="ORDER_SEARCH_FORM" property="resultList"
 indexId="i" type="com.cleanwise.service.api.value.OrderStatusDescData">
<li class='<%=((i+1) % 2==0)?"even" : "odd"%>'>
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
boolean isReceivedOrder = RefCodeNames.ORDER_STATUS_CD.RECEIVED.equalsIgnoreCase(temps);
String viewOrderUrl = "orderDetail.do?action=view&amp;" +
 "id=" + oid;
%>

<% if ( !bakedFl || isReceivedOrder) { %>
<div class="listResults"><bean:write name="arrele" property="orderDetail.orderNum"/></div>
<% } else { %>
<div class="listResults"><a href="<%=viewOrderUrl%>"><bean:write name="arrele" property="orderDetail.orderNum"/> </a></div>
<% } %>
<logic:present name="arrele" property="orderDetail.refOrderNum">
  <div class="listResults"><bean:write name="arrele" property="orderDetail.refOrderNum" /></div>
</logic:present>
<logic:present name="arrele" property="orderDetail.requestPoNum">
  <div class="listResults"><bean:write name="arrele" property="orderDetail.requestPoNum" /></div>
</logic:present>
<%-- <td><bean:write name="arrele" property="orderDetail.originalOrderDate" /></td> --%>
<%
   //sdf = new SimpleDateFormat("yyyy-MM-dd");
   //sdf.setTimeZone(timeZone);
   Date date = ((OrderStatusDescData) arrele).getOrderDetail().getOriginalOrderDate();
   Date time = ((OrderStatusDescData) arrele).getOrderDetail().getOriginalOrderTime();
   Date orderDate = Utility.getDateTime(date, time);
	
	String formattedODate = ClwI18nUtil.formatDateInp(request, orderDate, timeZone.getID() );
%>
<div class="listResults"><%=formattedODate%></div>

<div class="listResults">
<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
<%
java.math.BigDecimal otot =  arrele.getSumOfAllOrderCharges();
%>
    <%
// STJ-3972
// get small order fee and fuel charge and add it to order total
    OrderMetaData oSmallOrderFee = arrele.getMetaObject(RefCodeNames.CHARGE_CD.SMALL_ORDER_FEE);
    if (oSmallOrderFee != null) {
        BigDecimal smallOrderFee = new BigDecimal(oSmallOrderFee.getValue());
        otot = otot.add(smallOrderFee);
    }
    OrderMetaData oFuelCharge = arrele.getMetaObject(RefCodeNames.CHARGE_CD.FUEL_SURCHARGE);
    if (oFuelCharge != null) {
        BigDecimal fuelCharge = new BigDecimal(oFuelCharge.getValue());
        otot = otot.add(fuelCharge);
    }
    %>
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
<% if(bakedFl && !isReceivedOrder) {
%>
<%=ClwI18nUtil.priceFormat(request, otot, (String) orderLocale," ")%>
<% } else { %>
&nbsp;
<% } %>
</logic:equal>
</div>
<div class="text">
  <span class="textLabel"><app:storeMessage key="shop.orderStatus.text.siteName"/>:</span> <bean:write name="arrele" property="orderDetail.orderSiteName" />
</div>
</li>
</logic:iterate>
</ul> <%--listResultsTable--%>


</logic:present>

