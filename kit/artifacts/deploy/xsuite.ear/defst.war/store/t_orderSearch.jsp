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
<bean:define id="workflowRole" type="java.lang.String" name="ApplicationUser" property="user.workflowRoleCd"/>
<bean:define id="theForm" name="ORDER_SEARCH_FORM" type="com.cleanwise.view.forms.OrderSearchForm" 
    scope="session"/>
<%boolean existPendingApprove = false; %>
<logic:iterate id="arrele" name="ORDER_SEARCH_FORM" property="resultList"
    indexId="i" type="com.cleanwise.service.api.value.OrderStatusDescData"><%
if (RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL.equals(arrele.getOrderDetail().getOrderStatusCd())) {
    existPendingApprove = true;
}%>
</logic:iterate>
<%
boolean approverFl = (workflowRole.indexOf("APPROVER") >= 0) ? true : false;
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
boolean pendingOrdersAction = "pending_orders".equals(sc_source);
boolean showControls = approverFl && pendingOrdersAction && existPendingApprove;   
TimeZone timeZone =  Utility.getTimeZoneFromUserData(appUser.getSite());
%>

<%if (showControls) {%>
<script language="JavaScript1.2">
<!--
function actionSubmit(paramValue, approveDateIndex) {
	var frm = document.forms['ORDER_SEARCH_FORM'];
	if (frm && frm.elements['subAction']) {
		frm.elements['subAction'].value = paramValue;
		frm.elements['approveDate'].value = frm.elements['approveDate' + approveDateIndex].value;
		frm.submit();
		
	    this.disabled=true;
	}
}
//-->
</script>
<%}%>
<table align=center CELLSPACING=0 CELLPADDING=2
  width="<%=Constants.TABLEWIDTH%>" class="tbstd">
<logic:equal name="ORDER_SEARCH_FORM" property="showSearchFields" value="true">
<html:form name="ORDER_SEARCH_FORM" action="/store/orderSearch.do"
    focus="orderDateRangeBegin"
    scope="session" type="com.cleanwise.view.forms.OrderSearchForm">

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
        <html:text name="ORDER_SEARCH_FORM" property="orderDateRangeBegin" />
        </td>
        <td>
        <app:storeMessage key="shop.orderStatus.text.endDateRange"/><br>
        <html:text name="ORDER_SEARCH_FORM" property="orderDateRangeEnd" />
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
      <html:text name="ORDER_SEARCH_FORM" property="siteZipCode" />
       </td>
     <td>&nbsp;</td>
  </tr>

  <tr>
       <td>&nbsp;</td>
       <td><b><app:storeMessage key="shop.orderStatus.text.city:"/></b></td>
     <td>
      <html:text name="ORDER_SEARCH_FORM" property="siteCity" />
       </td>
     <td>&nbsp;</td>
  </tr>
	<tr>
       <td>&nbsp;</td>
       <td><b><app:storeMessage key="shop.orderStatus.text.webOrder#/Confirmation#:"/></b></td>
     <td>
      <html:text name="ORDER_SEARCH_FORM" property="webOrderConfirmationNum" />
       </td>
     <td>&nbsp;</td>
  </tr>
	<tr>
       <td>&nbsp;</td>
       <td><b><app:storeMessage key="shop.orderStatus.text.customerPO#:"/></b></td>
     <td>
      <html:text name="ORDER_SEARCH_FORM" property="custPONum" />
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
      <html:text name="ORDER_SEARCH_FORM" property="siteState" size="2" maxlength="2"/>&nbsp;&nbsp;
      <%} %>
      <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.RECEIVING%>">
        <b><app:storeMessage key="shop.orderStatus.text.orderNotReceivedOnlyYN:"/></b>
        <html:radio property="ordersNotReceivedOnly" value="y"/>
        <html:radio property="ordersNotReceivedOnly" value="n"/>
      </app:authorizedForFunction>
       <html:hidden property="action" value="search_all_sites"/>
       <html:submit styleClass="store_fb" property="action">
       	<app:storeMessage key="global.action.label.search"/>
       </html:submit>
       </td>
     <td>&nbsp;</td>
  </tr>


<% } else { %>
<tr>
       <td>&nbsp;</td>
       <td><b><app:storeMessage key="shop.orderStatus.text.webOrder#/Confirmation#:"/></b></td>
     <td>
      <html:text name="ORDER_SEARCH_FORM" property="webOrderConfirmationNum" />
       </td>
     <td>&nbsp;</td>
  </tr>
  <tr>
       <td>&nbsp;</td>
       <td><b><app:storeMessage key="shop.orderStatus.text.customerPO#:"/></b></td>
     <td>
      <html:text name="ORDER_SEARCH_FORM" property="custPONum" />
       <html:hidden property="action" value="search"/>
       <html:submit styleClass="store_fb" property="action"
          value='<%=ClwI18nUtil.getMessage(request,"global.action.label.search",null)%>'/>
  </td>
       <td>&nbsp;</td>
</tr>


<% } %>


</html:form>
</logic:equal>
</table>

<logic:present name="ORDER_SEARCH_FORM" property="resultList">
<html:form name="ORDER_SEARCH_FORM" action="/store/orderSearch.do"
    scope="session" type="com.cleanwise.view.forms.OrderSearchForm">
<%if (showControls) {%>
<html:hidden property="action" value="pending_orders"/>
<html:hidden property="subAction" value=""/>
<html:hidden property="approveDate" value=""/>
<table cellspacing="0" cellpadding="2" class="tbstd">
<tr>
<td><input type="button" class="store_fb" onclick="actionSubmit('Reject',1);" 
	value="<app:storeMessage key="global.action.label.reject" />"/>
<input type="button" class="store_fb" onclick="this.disabled=true;actionSubmit('Approve',1);" 
	value="<app:storeMessage key="global.action.label.approve" />"/>
<input type="button" class="store_fb" onclick="this.disabled=true;actionSubmit('ApproveOn',1);" 
	value="<app:storeMessage key="shop.orderStatus.text.approveOn" />"/>
<input type="text" name='approveDate1' value = '<%=ClwI18nUtil.formatDateInp(request, 
        new java.util.Date(), timeZone.getID() )%>' size='10'/>(<%=ClwI18nUtil.getUIDateFormat(request)%>)</td>
</tr>
</table>
<%}%>
    
<bean:size id="rescount"  name="ORDER_SEARCH_FORM" property="resultList"/>

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
<a class="shopchartheadlink" href="orderSearch.do?action=sort&sortField=webordernum">
  <app:storeMessage key="shop.orderStatus.text.order#"/></a>
</div></td>
<td class="shopcharthead">
<div class="fivemargin">
<a class="shopchartheadlink" href="orderSearch.do?action=sort&sortField=refordernum">
 <app:storeMessage key="shop.orderStatus.text.refOrder#"/></a>
</div></td>
<td width=80 class="shopcharthead"><div class="fivemargin">
<a class="shopchartheadlink" href="orderSearch.do?action=sort&sortField=orderdate">
 <app:storeMessage key="shop.orderStatus.text.date"/></a>
</div></td>
<td class="shopcharthead"><div class="fivemargin">
<a class="shopchartheadlink" href="orderSearch.do?action=sort&sortField=custponum">
  <app:storeMessage key="shop.orderStatus.text.poNum"/></a>
</div></td>
<td class="shopcharthead"><div class="fivemargin">
<a class="shopchartheadlink" href="orderSearch.do?action=sort&sortField=sitename">
 <app:storeMessage key="shop.orderStatus.text.siteName"/></a>
</div></td>
<td class="shopcharthead"><div class="fivemargin">
<a class="shopchartheadlink" href="orderSearch.do?action=sort&sortField=status">
  <app:storeMessage key="shop.orderStatus.text.status"/></a>
</div></td>
<td class="shopcharthead"><div class="fivemargin">
<a class="shopchartheadlink" href="orderSearch.do?action=sort&sortField=sitecity">
 <app:storeMessage key="shop.orderStatus.text.city"/></a>
</div></td>
<% if (appUser.getUserStore().isStateProvinceRequired()) { %>
<td class="shopcharthead"><div class="fivemargin">
  <a class="shopchartheadlink" href="orderSearch.do?action=sort&sortField=sitestate">
    <app:storeMessage key="shop.orderStatus.text.state"/></a>
  </div></td>
<%} %>
<td class="shopcharthead"><div class="fivemargin">
<a class="shopchartheadlink" href="orderSearch.do?action=sort&sortField=zipcode">
 <app:storeMessage key="shop.orderStatus.text.zipCode"/></a>
</div></td>
<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
<td class="shopcharthead">
<div class="fivemargin">
<a class="shopchartheadlink" href="orderSearch.do?action=sort&sortField=amount">
  <app:storeMessage key="shop.orderStatus.text.total"/></a>
</div></td>
</logic:equal>
<td class="shopcharthead">
<div class="fivemargin">
<a class="shopchartheadlink" href="orderSearch.do?action=sort&sortField=placedby">
 <app:storeMessage key="shop.orderStatus.text.placedBy"/></a>
</div></td><%if (showControls) {%>
<td class="shopcharthead" align="center"><div class="fivemargin">
<a href="#" onclick="return f_setCheckClearAll(document.forms['ORDER_SEARCH_FORM'], 'selectIds', true)"><app:storeMessage key="shop.orderStatus.text.text.CheckAll" /></a><br />
<a href="#" onclick="return f_setCheckClearAll(document.forms['ORDER_SEARCH_FORM'], 'selectIds', false)"><app:storeMessage key="shop.orderStatus.text.text.Clear" /></a>
</div></td><%}%>
</tr>

<logic:iterate id="arrele" name="ORDER_SEARCH_FORM" property="resultList"
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
boolean isPendingApproval = RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL.equals(temps);
boolean isShowCheckbox = isPendingApproval && showControls; 
boolean isReceivedOrder = RefCodeNames.ORDER_STATUS_CD.RECEIVED.equalsIgnoreCase(temps);
String viewOrderUrl = "orderDetail.do?action=view&amp;" +
 "id=" + oid;
%>

<% if ( /*(appUser.isaCustomer() || appUser.isaMSB()) ||*/ !bakedFl || isReceivedOrder) { %>
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
	
	String formattedODate = ClwI18nUtil.formatDateInp(request, orderDate, timeZone.getID() );
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
<% if(bakedFl && !isReceivedOrder) {
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
<%if (isShowCheckbox) {%>
   <%String orderId = "" + oid; %>
   <td align="center"><html:multibox name="ORDER_SEARCH_FORM" property="selectIds" value="<%=orderId%>" /></td>
<%} else if (showControls) {%><td>&nbsp;</td><%}%>
</tr>
<logic:present name="arrele" property="orderPropertyList">
<logic:notEmpty name="arrele" property="orderPropertyList">
<bean:define id="opDV " type="com.cleanwise.service.api.value.OrderPropertyDataVector"
   name="arrele" property="orderPropertyList"/>
<tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)i)%>"><td colspan="12"><%
boolean needShowLabel = true;
for(int ii=0; ii<opDV.size(); ii++) {
    OrderPropertyData opD = (OrderPropertyData) opDV.get(ii);
    if("Workflow Note".equals(opD.getShortDesc())){
        if (needShowLabel) {
            %><b><app:storeMessage key="shop.orderStatus.text.notes:"/></b>&nbsp;<%
            needShowLabel = false;
        }
        String messKey = opD.getMessageKey();
        if(messKey==null){
            %><%=opD.getValue()%><br/><%
        } else { 
            %><app:storeMessage key="<%=messKey%>"
          arg0="<%=opD.getArg0()%>"
          arg0TypeCd="<%=opD.getArg0TypeCd()%>"
          arg1="<%=opD.getArg1()%>"
          arg1TypeCd="<%=opD.getArg1TypeCd()%>"
          arg2="<%=opD.getArg2()%>"
          arg2TypeCd="<%=opD.getArg2TypeCd()%>"
          arg3="<%=opD.getArg3()%>"
          arg3TypeCd="<%=opD.getArg3TypeCd()%>"
          /><br/><%
        }
    }
}
%>
</td></tr></logic:notEmpty></logic:present>
</logic:iterate>
</table>
<%if (showControls) {%>
<table cellspacing="0" cellpadding="2" class="tbstd">
<tr>
<td><input type="button" class="store_fb" onclick="actionSubmit('Reject',2);" 
	value="<app:storeMessage key="global.action.label.reject" />"/>
<input type="button" class="store_fb" onclick="this.disabled=true;actionSubmit('Approve',2);" 
	value="<app:storeMessage key="global.action.label.approve" />"/>
<input type="button" class="store_fb" onclick="this.disabled=true;actionSubmit('ApproveOn',2);" 
	value="<app:storeMessage key="shop.orderStatus.text.approveOn" />"/>
<input type="text" name='approveDate2' value = '<%=ClwI18nUtil.formatDateInp(request, 
        new java.util.Date(), timeZone.getID() )%>' size='10'/>(<%=ClwI18nUtil.getUIDateFormat(request)%>)</td>
</tr>
</table>
<%}%>
</html:form>
</logic:present>
<table align=center CELLSPACING=0 CELLPADDING=0
  width="<%=Constants.TABLEWIDTH%>" class="tbstd">
<tr><td><br></td></tr>
</table>


<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,"f_table_bottom1.jsp")%>'/>
