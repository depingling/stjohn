<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.service.api.session.Order" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.cleanwise.service.api.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<% CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER); %>

<%
//should use refcodes, hard coded for upgrade to running prod system  -Brook
boolean enhancedBackorder = false;
if(appUser.getUserAccount().getRuntimeDisplayOrderItemActionTypes().contains("ACK Backordered Enhanced")){
	enhancedBackorder = true;
}


%>

<script language="JavaScript1.2"> <!--

dojo.require(xmodule+".form.DateTextBox");

function commandMultiSubmit(actionDef, action) {
    var actionElements = document.getElementsByName('command');
    
    if (actionElements.length) {
        for (var i = actionElements.length - 1; i >= 0; i--) {

            var element = actionElements[i];
            if (actionDef == element.value) {
                element.value = action;
                element.form.submit();
                break;
            }
        }
    } else if (actionElements) {
        actionElements.value = action;
        actionElements.form.submit();
    }

    return false;
}

function commandTrackingNumSubmit(orderItemId, shipQtyCtrlName, shipTrackingNumCtrlName, actionDef, action) {
	document.getElementById('shippedOrderItemId').value = orderItemId;
	var elements = document.getElementById('shippedQty');
	elements.value = document.getElementById(shipQtyCtrlName).value;
	elements = document.getElementById('shippedTrackingNum');
	elements.value = document.getElementById(shipTrackingNumCtrlName).value;
	
    return commandMultiSubmit(actionDef, action);
}

function limitText(limitField, limitNum) {
	if (limitField.value.length > limitNum) {
		limitField.value = limitField.value.substring(0, limitNum);
	}
}

function displayOrderItemActionDetail(id)
{

    if (eval("document.getElementById('orderItemActionDetailTable_'+id)").style.display == 'none') {
        eval("document.getElementById('orderItemActionDetailTable_'+id)").style.display = 'block';
        eval("document.getElementById('orderItemActionImg'+id)").src = "<%=ClwCustomizer.getSIP(request,"minus.gif")%>";
    }

    else {
        eval("document.getElementById('orderItemActionDetailTable_'+id)").style.display = 'none';
        eval("document.getElementById('orderItemActionImg'+id)").src = "<%=ClwCustomizer.getSIP(request,"plus.gif")%>";
    }
}

function showInternalMessageBox(){
    eval("document.getElementById('CUSTOMER_ORDER_NOTES_LINK')").style.display = 'none';
    eval("document.getElementById('CUSTOMER_ORDER_NOTES_BOX')").style.display = 'block';
}

function viewPrinterFriendly(oid) {
  var loc = "printerFriendlyOrder.do?action=orderStatus&orderId=" + oid;

  prtwin = window.open(loc,"printer_friendly",
    "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500,width=775,left=100,top=165");
  prtwin.focus();

  return false;
}

//-->
</script>

<style type="text/css">

    TABLE#CUSTOMER_ORDER_NOTES_BOX {
        display:none;
    }
    TABLE#CUSTOMER_ORDER_NOTES_LINK {
        display:block;
    }
</style>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<bean:define id="theForm" name="ORDER_OP_DETAIL_FORM" type="com.cleanwise.view.forms.OrderOpDetailForm"/>

<bean:define id="clwWorkflowRole"
             type="java.lang.String"
             name="<%=Constants.APP_USER%>"
             property="user.workflowRoleCd"/>

<bean:define id="orderStatus"
             name="ORDER_OP_DETAIL_FORM"
             property="orderStatusDetail.orderDetail.orderStatusCd"
             type="String"/>

<html:form name="ORDER_OP_DETAIL_FORM"
           action="/store/userOrderDetail.do?action=xpedxProcess"
           type="com.cleanwise.view.forms.OrderOpDetailForm">

<logic:present name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail">

<bean:define id="lOrderId"
             name="ORDER_OP_DETAIL_FORM"
             property="orderStatusDetail.orderDetail.orderId"/>

<bean:define id="lOrderStatusDetail"
             name="ORDER_OP_DETAIL_FORM"
             property="orderStatusDetail"
             type="com.cleanwise.service.api.value.OrderStatusDescData"/>


<%
    OrderStatusDescData orderStatusDetail = theForm.getOrderStatusDetail();
    String orderLocale = orderStatusDetail.getOrderDetail().getLocaleCd();
    TimeZone timeZone = Utility.getTimeZoneFromUserData(appUser.getSite());

    boolean consolidatedOrderFl =
            RefCodeNames.ORDER_TYPE_CD.CONSOLIDATED.equals(orderStatusDetail.getOrderDetail().getOrderTypeCd());

    boolean toBeConsolidatedFl =
            RefCodeNames.ORDER_TYPE_CD.TO_BE_CONSOLIDATED.equals(orderStatusDetail.getOrderDetail().getOrderTypeCd());

    OrderData consolidatedToOrderD = orderStatusDetail.getConsolidatedOrder();

    boolean useCustPo = true;
    String requestPoNum = orderStatusDetail.getOrderDetail().getRequestPoNum();

    if (requestPoNum == null
            || requestPoNum.equals("")
            || requestPoNum.equalsIgnoreCase("na")
            || requestPoNum.equalsIgnoreCase("n/a")) {

        useCustPo = false;

    }

    AccountData accountD = appUser.getUserAccount();
    SiteData siteD = appUser.getSite();
    SiteData orderSiteD = null;

	String isAllowReorder = accountD.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_REORDER);
    if (orderStatusDetail != null && orderStatusDetail.getOrderDetail() != null) {
        try {
            orderSiteD = SessionTool.getSiteData(request, orderStatusDetail.getOrderDetail().getSiteId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    if (orderSiteD == null) {
        orderSiteD = siteD;
    }

    int siteId = 0;
    if (orderSiteD != null) {
        siteId = orderSiteD.getBusEntity().getBusEntityId();
    }

    int itemActionSeq = 0;

	String  prefix  = null;
    if(appUser.getUserStore().getPrefix()!=null){
        prefix = appUser.getUserStore().getPrefix().getValue();
    }

	String oStatus = orderStatusDetail.getOrderDetail().getOrderStatusCd();

	String addServiceFee = accountD.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ADD_SERVICE_FEE);
	BigDecimal totServiceFee = new BigDecimal(0.0);
	BigDecimal subTot = new BigDecimal(0.0);
	
    boolean shipToOverride = false;
    OrderMetaData omData = orderStatusDetail.getMetaObject(RefCodeNames.ORDER_PROPERTY_TYPE_CD.SHIP_TO_OVERRIDE);
    if (omData != null && Utility.isTrue(omData.getValue())) {
        shipToOverride = true;
    }
 
%>

<table width="<%=Constants.TABLEWIDTH800%>">

<tr>
    <td>
        <table class="breadcrumb" width="<%=Constants.TABLEWIDTH800%>">
            <tr class="breadcrumb">
                <td class="breadcrumb"><a class="breadcrumb" href="../userportal/msbsites.do?action=goHome"><app:storeMessage key="breadcrumb.label.home"/></a></td>
                <td class="breadcrumb"><span class="breadcrumb">&nbsp;&gt;&nbsp;</span></td>
                <td class="breadcrumb"><a class="breadcrumb" href="orderstatus.do?action=initXPEDXListOrders&orderStatus=<%=oStatus%>"><app:storeMessage key="breadcrumb.label.trackOrder"/></a></td>
                <td class="breadcrumb"><span class="breadcrumb">&nbsp;&gt;&nbsp;</span></td>
                <td class="breadcrumb">
				   <%--<a class="breadcrumb" href="#"><app:storeMessage key="breadcrumb.label.orderDetail"/></a>--%>
				   <div class="breadcrumbCurrent"><app:storeMessage key="breadcrumb.label.orderDetail"/></div>
				</td>
				<td align="right" class="breadcrumb" width="75%">

					<table border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td align="right"  valign="middle"><img  src='NewXpedx/images/buttonLeft.png' border="0">
						</td><td align="center" valign="middle" nowrap class="xpdexGradientButton">
						<a class="xpdexGradientButton" href="#" onclick="viewPrinterFriendly('<%=lOrderId%>');"><app:storeMessage key="global.label.PrinterFriendly"/></a>
						</td><td align="left" valign="middle"><img  src='NewXpedx/images/buttonRight.png' border="0"></td>
                    </tr>
					</table>

				</td>
            </tr>
        </table>
    </td>
</tr>
<tr>
    <td>
        <%
            OrderMetaData omD = lOrderStatusDetail.getMetaObject(Order.MODIFICATION_STARTED);
            if (omD != null && omD.getModDate() != null) {
        %>
        <font color="red">
            <app:storeMessage key="shop.orderStatus.text.ModificationStartedOnBy"
                              arg0='<%=ClwI18nUtil.formatDateInp(request,omD.getModDate(),timeZone.getID())%>'
                              arg1='<%=omD.getValue()%>'/>
        </font>

        <% } %>
    </td>
</tr>
<tr>
<td>
<table width="100%">

<tr>
    <td><b>
        <%if (consolidatedOrderFl) { %>

        <app:storeMessage key="shop.catalog.text.consolidateOrder"/>

        <% } else { %>

        <app:storeMessage key="shop.orderStatus.text.orderInformation:"/>

        <% } %>
    </b></td>
    <td>&nbsp;</td>
    <td><b><app:storeMessage key="shop.orderStatus.text.shippingInformation:"/></b></td>
</tr>

<tr>
<td valign="top" width="55%" align="left">
    <table>

        <tr>
            <td class="orderStatusHeaderMessage"><app:storeMessage key="shop.orderStatus.text.order#"/>:</td>
            <td class="orderStatusHeaderData"><bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.orderDetail.orderNum"/></td>
        </tr>

        <% if (consolidatedToOrderD != null) {%>
        <tr>
            <td class="orderStatusHeaderMessage"><app:storeMessage key="shop.catalog.text.consolidatedToOrder"/>:</td>
            <td class="orderStatusHeaderData"><%=consolidatedToOrderD.getOrderNum()%></td>
        </tr>
        <% } %>

        <logic:present name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.refOrder">
            <tr>
                <td class="orderStatusHeaderMessage"><app:storeMessage key="shop.catalog.text.referenceOrderNum"/>:</td>
                <td class="orderStatusHeaderData"><bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.refOrder.orderNum"/></td>
            </tr>
        </logic:present>


        <tr>
            <td class="orderStatusHeaderMessage"><app:storeMessage key="shop.orderStatus.text.orderDate"/>:</td>
            <%
                Date date = orderStatusDetail.getOrderDetail().getOriginalOrderDate();
                Date time = orderStatusDetail.getOrderDetail().getOriginalOrderTime();
                Date orderDate = Utility.getDateTime(date, time);
            %>
            <td class="orderStatusHeaderData">
                <%=ClwI18nUtil.formatDateInp(request, orderDate, timeZone.getID())%>
            </td>
        </tr>

        <tr>
            <td class="orderStatusHeaderMessage"><app:storeMessage key="shop.orderStatus.text.orderStatus:"/></td>
            <%
                //String xlateStatus = com.cleanwise.view.utils.ShopTool.xlateStatus(orderStatusDetail);
				String xlateStatus = com.cleanwise.view.utils.ShopTool.xlateStatus(orderStatusDetail,request,prefix);
            %>
            <td class="orderStatusHeaderData">
                <%=xlateStatus%>
            </td>
        </tr>

        <logic:present name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.requestedShipDate">
            <bean:define id="ordreqd"
                         type="java.lang.String"
                         name="ORDER_OP_DETAIL_FORM"
                         property="orderStatusDetail.requestedShipDate"/>

            <% if (ordreqd != null && ordreqd.length() > 0) { %>
            <tr>
                <td class="orderStatusHeaderMessage"><b><app:storeMessage key="shop.orderStatus.text.requestedShipDate"/>:</b></td>
                <td class="orderStatusHeaderData"><bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.requestedShipDate"/></td>
            </tr>
            <% } %>
        </logic:present>

        <tr>
            <td class="orderStatusHeaderMessage"> <app:storeMessage key="shop.orderStatus.text.orderType"/>:</td>
            <td class="orderStatusHeaderData"><bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.orderDetail.orderSourceCd"/></td>
        </tr>

        <tr>
            <%
                boolean allowPoEntry = true;
                if (RefCodeNames.CUSTOMER_SYSTEM_APPROVAL_CD.PUNCH_OUT_INLIN_NON_E_ORD_ONLY.equals(accountD.getCustomerSystemApprovalCd())) {
                    allowPoEntry = false;
                }

                if (!accountD.isCustomerRequestPoAllowed()) {
                    allowPoEntry = false;
                }

                if (siteD != null && siteD.getBlanketPoNum() != null && siteD.getBlanketPoNum().getBlanketPoNumId() != 0) {
                    allowPoEntry = false;
                }
            %>

            <td class="orderStatusHeaderMessage"><app:storeMessage key="shop.orderStatus.text.PO#"/>:</td>
            <td class="orderStatusHeaderData">
                <%
                    String thisPoNum = Utility.strNN(orderStatusDetail.getOrderDetail().getRequestPoNum());
                    if (orderStatus.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL)
                            && appUser.canMakePurchases()
                            && allowPoEntry) { %>

                <html:text styleId="requestPoNum"
                           name="ORDER_OP_DETAIL_FORM"
                           property="requestPoNum"
                           value="<%=thisPoNum%>"
                           size="20"/>
                <% } else { %>
                <bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.orderDetail.requestPoNum"/>
                <% } %>
            </td>
        </tr>

    </table>
</td>

<td width="2%">&nbsp;</td>

<td width="43%" valign="top" align="left">
    <table>
        <tr>
            <td class="orderStatusHeaderMessage">Location:</td>
            <td class="orderStatusHeaderData"><bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.orderDetail.orderSiteName"/></td>
        </tr>
        <%
        if (shipToOverride) {
        %>
        <tr>
            <td class="orderStatusHeaderMessage"><app:storeMessage key="shop.orderStatus.text.phone:"/></td>
            <td class="orderStatusHeaderData"><bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.orderDetail.orderContactPhoneNum"/></td>
        </tr>
        <% } %>        
        <tr>
            <td class="orderStatusHeaderMessage"><app:storeMessage key="shop.orderStatus.text.address1:"/></td>
            <td class="orderStatusHeaderData"><bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.shipTo.address1"/></td>
        </tr>
        <tr>
            <td class="orderStatusHeaderMessage"><app:storeMessage key="shop.orderStatus.text.address2:"/></td>
            <td class="orderStatusHeaderData"><bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.shipTo.address2"/></td>
        </tr>
        <tr>
            <td class="orderStatusHeaderMessage"><app:storeMessage key="shop.orderStatus.text.address3:"/></td>
            <td class="orderStatusHeaderData"><bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.shipTo.address3"/></td>
        </tr>
        <tr>
            <td class="orderStatusHeaderMessage"><app:storeMessage key="shop.orderStatus.text.city:"/></td>
            <td class="orderStatusHeaderData"><bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.shipTo.city"/></td>
        </tr>
        <% if (appUser.getUserStore().isStateProvinceRequired()) { %>
        <tr>
            <td class="orderStatusHeaderMessage"><app:storeMessage key="shop.orderStatus.text.state:"/></td>
            <td class="orderStatusHeaderData"><bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.shipTo.stateProvinceCd"/></td>
        </tr>
        <%}%>
        <tr>
            <td class="orderStatusHeaderMessage"><app:storeMessage key="shop.orderStatus.text.zip:"/></td>
            <td class="orderStatusHeaderData"><bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.shipTo.postalCode"/></td>
        </tr>
        <tr>
            <td class="orderStatusHeaderMessage"><app:storeMessage key="shop.orderStatus.text.country:"/></td>
            <td class="orderStatusHeaderData"><bean:write name="ORDER_OP_DETAIL_FORM" property="orderStatusDetail.shipTo.countryCd"/></td>
        </tr>
    </table>
</td>
</tr>
<tr>
<td colspan="3">&nbsp;</td>
<%
if (!RefCodeNames.CUSTOMER_SYSTEM_APPROVAL_CD.PUNCH_OUT_INLIN_NON_E_ORD_ONLY.equals(accountD.getCustomerSystemApprovalCd())) {
%>

<tr>
<td>
    <%if ( appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ADD_CUSTOMER_ORDER_NOTES)){%>
    <b><app:storeMessage key="shop.orderStatus.text.internalComments"/>:</b>
    <% } %>
</td>
<td>&nbsp;</td>
<td valign="top" align="left">
    <%if ( appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ADD_SHIPPING_ORDER_COMMENTS)){%>
    <b><app:storeMessage key="shop.orderStatus.text.shippingComments"/>:</b>
    <%}%>
</td>
</tr>

<tr>
<td>
    <%if ( appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ADD_CUSTOMER_ORDER_NOTES)){%>
        <logic:present name="ORDER_OP_DETAIL_FORM" property="customerOrderNotes">
            <bean:size id="custNotesCt" name="ORDER_OP_DETAIL_FORM" property="customerOrderNotes"/>
            <logic:greaterThan name="custNotesCt" value="0">
                <table>
                    <logic:iterate name="ORDER_OP_DETAIL_FORM" property="customerOrderNotes" id="note" type="com.cleanwise.service.api.value.OrderPropertyData">                    
                        <tr>
                            <td valign="top">
                                <bean:define id="commentDate" name="note" property="addDate" type="java.util.Date" />
                                <b>
                                    <i18n:formatDate value="<%=commentDate%>"  pattern="yyyy-MM-dd k:mm"/> -
                                    <bean:write name="note" property="addBy"/>
                                </b>
                            </td>
                            <td style="text-align:left;" valign="top">
                            <bean:define id="noteType" name="note" property="shortDesc" type="String" />
		                    <bean:define id="noteValue" name="note" property="value" type="String" />
		                    <% 
		                    if (noteType.equals("Split Order") || noteType.equals("Cancelled") || noteType.equals("Replacement")){
		                    	int idx = noteValue.indexOf(":");
		                		if (idx > 0){
		                			String temp = noteValue.substring(idx+1).trim();
		                			String[] orderNums = temp.split(",");
		                			for (String orderNum : orderNums){
		                				orderNum = orderNum.trim();
		                				String replaceOrderNum = "<a href=\"userOrderDetail.do?action=view&amp;orderNum=" + orderNum + "\">" + orderNum + "</a>";//<a href="userOrderDetail.do?action=view&amp;id=4215630">2170359</a>
		                				noteValue = noteValue.replace(orderNum, replaceOrderNum);
		                			}
		                		}
		              	   }
		                    %>
                                <%= noteValue%>
                            </td>
                        </tr>
                    </logic:iterate>
                </table>
            </logic:greaterThan>
        </logic:present>
    <%}%>
</td>
<td>&nbsp;</td>
<td valign="top">
    <%if ( appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ADD_SHIPPING_ORDER_COMMENTS)){%>
        <table>
            <tr>
                <td>
                    <logic:present name="ORDER_OP_DETAIL_FORM"  property="orderStatusDetail.orderDetail.comments">
                        <bean:write name="ORDER_OP_DETAIL_FORM"  property="orderStatusDetail.orderDetail.comments"/>
                    </logic:present>
                </td>
            </tr>
        </table>
     <%}%>
</td>
</tr>
<tr>
<td valign="top">
    <%if ( appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ADD_CUSTOMER_ORDER_NOTES)){%>
        <table id="CUSTOMER_ORDER_NOTES_LINK" >
            <tr>
                <td colspan="2">
                    <a href="#" onclick="showInternalMessageBox()">
                        <app:storeMessage key="shop.orderStatus.text.addInternalComments"/>
                    </a>
                </td>
            </tr>
        </table>

        <table id="CUSTOMER_ORDER_NOTES_BOX" >

            <tr>
                <td>
                    <html:textarea name="ORDER_OP_DETAIL_FORM" property="customerComment" rows="3" cols="40" onkeydown="limitText(this.form.customerComment,2000);"/>
                </td>
                <td valign="bottom">
                    <table border="0" cellpadding="0" cellspacing="0"><!-- save button -->
                        <tr>
                            <td align="right"  valign="middle"><img  src='<%=ClwCustomizer.getSIP(request,"buttonLeft.png")%>' border="0"
							></td><td align="center" valign="middle" nowrap class="xpdexGradientButton"
							><a class="xpdexGradientButton" href="#"
							onclick="commandMultiSubmit('hiddenCommand', 'addCustomerComment');"
							><app:storeMessage key="global.action.label.save"/></a></td
							><td align="left" valign="middle"><img  src='<%=ClwCustomizer.getSIP(request,"buttonRight.png")%>' border="0"></td>
                        </tr>
                    </table>
                </td>
            </tr>
        </table>
    <%}%>
</td>
<td>&nbsp;</td>
<td>&nbsp;</td>
</tr>
<% } %>
<tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
</tr>
</table>
</td>
</tr>
<tr>
<td>
<table width="100%" border="0" cellspacing="0">
<tr>
    <%int colCount = 0;%>

    <%colCount++;%>
    <td class="shopcharthead" height="30" width="8%" align="center">
        &nbsp;<app:storeMessage key="shoppingItems.text.orderQty"/>&nbsp;
    </td>

	<%colCount++;%>
    <td class="shopcharthead" width="8%">
        <app:storeMessage key="shoppingItems.text.uom"/>
    </td>

    <%colCount++;%>
    <td class="shopcharthead" width="12%">
        <app:storeMessage key="shoppingItems.text.item#"/>
    </td>

    <%colCount++;%>
    <td class="shopcharthead" width="30%">
        <app:storeMessage key="shoppingItems.text.productName"/>
    </td>


    <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
        <%colCount++;%>
        <td class="shopcharthead" width="12%" align="right">
            <app:storeMessage key="shoppingItems.text.price"/>
        </td>

        <%colCount++;%>
        <td class="shopcharthead" width="10%" align="right">
            <app:storeMessage key="shop.orderStatus.text.total"/>&nbsp;
        </td>
    </logic:equal>

    <%colCount++;%>
    <td class="shopcharthead" width="30%" style="padding-left:20px">
        <app:storeMessage key="shoppingItems.text.status"/>
    </td>

</tr>

<%String prevDist=null;%>
<% int colqty = 0; %>
<logic:iterate id="item"
               name="ORDER_OP_DETAIL_FORM"
               property="orderItemDescList"
               offset="0"
               indexId="oidx"
               type="com.cleanwise.service.api.value.OrderItemDescData">

<%colqty = 0; %>
<%
	boolean isCancelledItem = false;
	if( (RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED).equals(item.getOrderItem().getOrderItemStatusCd()) ){
		isCancelledItem = true;
	}
	if(addServiceFee.equals("true") && !isCancelledItem){
		BigDecimal thisFee = item.getOrderItem().getServiceFee();
		BigDecimal thisQty = new BigDecimal(item.getOrderItem().getTotalQuantityOrdered());
		BigDecimal res = new BigDecimal(0);
		if(thisFee!=null){
			res=(thisFee).multiply(thisQty);
		}
		if(res!=null && res.compareTo(new BigDecimal(0.0))>0){
			totServiceFee = totServiceFee.add(res);
		}
	}
	String thisDist = item.getDistName();

	if(prevDist==null){ %>
	<tr><td class="shopcategory" colspan=<%=colCount%>>
		<app:storeMessage key="shoppingItems.text.distributedBy"/>&nbsp;<%=thisDist%>
	</td></tr>
<%} else if(prevDist!=null && !prevDist.equals(thisDist)){%>

	<tr><td class="shopcategory" colspan=<%=colCount%>>
		<app:storeMessage key="shoppingItems.text.distributedBy"/>&nbsp;<%=thisDist%>
	</td></tr>

<%	}%>
<bean:define id="qty"
             name="item"
             property="orderItem.totalQuantityOrdered"
             type="Integer"/>

<%java.math.BigDecimal custItemPrice = new BigDecimal(0.00);%>
<logic:present name="item" property="orderItem.custContractPrice">
    <%
	if(addServiceFee.equals("true")){
		BigDecimal thisFee = item.getOrderItem().getServiceFee();
		if(thisFee!=null){
			custItemPrice = item.getOrderItem().getCustContractPrice().subtract(thisFee);
		}else{
			custItemPrice = item.getOrderItem().getCustContractPrice();
		}
	}else{
		custItemPrice = item.getOrderItem().getCustContractPrice();
	}
	%>
</logic:present>

<%-- %><tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)oidx)%>" valign="top">--%>
<%String styleClass = (((oidx) %2 )==0) ?  "evenRowColor" : "oddRowColor";%>
<tr class="<%=styleClass%>" valign="top">

<% colqty++; %>
<td align="center">
    <bean:write name="item" property="orderItem.totalQuantityOrdered"/>
</td>

<% colqty++; %>
<td>
    <%=item.getOrderItem().getDistItemUom()%>
</td>

    <%--//sku customization--%>
<% colqty++; %>
<td>
    <%=ShopTool.getRuntimeSku(item.getOrderItem(), request)%>
</td>

<% colqty++; %>
<td>
    <bean:write name="item" property="orderItem.itemShortDesc"/>
</td>

<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
    <% colqty++; %>
    <td align="right">
        <%=ClwI18nUtil.getPriceShopping(request,custItemPrice,orderLocale,null," ")%>
    </td>

    <% colqty++; %>
    <td align="right">
	<%
		BigDecimal itemTotal = custItemPrice.multiply(new BigDecimal(qty.doubleValue()));
		if(itemTotal.compareTo(new BigDecimal(0.0))>0 ){
            itemTotal = itemTotal.setScale(2,BigDecimal.ROUND_HALF_UP);
			if(!isCancelledItem){
				subTot = subTot.add(itemTotal);
			}
		}
	%>
        <%=ClwI18nUtil.getPriceShopping(request,itemTotal,orderLocale,null,"&nbsp;")%>&nbsp;
    </td>
</logic:equal>

<% colqty++; %>
<td style="padding-left:20px">
<logic:present name="item" property="orderItemActionList">
<table width="280" border="0" cellspacing="0" cellpadding="0">
<!-- groups by action cd -->
<%
    HashMap itemActionGroups = new HashMap();
	HashMap itemShippingGroups = new HashMap();
    int backorderedQty = 0;
    ArrayList itemActionKeys=new ArrayList();
%>
<logic:iterate id="action"
               name="item"
               property="orderItemActionList"
               offset="0"
               indexId="aidx"
               type="com.cleanwise.service.api.value.OrderItemActionData">

    <%
        {
            String actionCd = Utility.strNN(action.getActionCd()).trim();
            // Group list of action of type TRACKING_NUMBER by the related 'Dist Shipped' record.
			if(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.TRACKING_NUMBER.equals(actionCd)){
				String key = action.getAffectedTable()+":" + action.getAffectedId();
				if (itemShippingGroups.containsKey(key)) {
					List trackingActionList = (List) itemShippingGroups.get(key);
					trackingActionList.add(action);
				}else{
					List trackingActionList = new OrderItemActionDataVector();
					trackingActionList.add(action);
					itemShippingGroups.put(key, trackingActionList);
				}
			} else {
            	if (itemActionGroups.containsKey(actionCd)) {
	                List actionList = (List) itemActionGroups.get(actionCd);
	                actionList.add(action);
            	} else {
	                List actionList = new OrderItemActionDataVector();
	                actionList.add(action);
	                itemActionGroups.put(actionCd, actionList);
	                itemActionKeys.add(actionCd);
            	}
            }
        }
    %>

</logic:iterate>

<%

	if (appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.TRACKING_MAINTENANCE) && itemActionGroups.get(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.DIST_SHIPPED) == null){
		itemActionKeys.add(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.DIST_SHIPPED);
	}
    boolean substitutedFlag = false;
    boolean shippedFlag = false;
    int shippedQty = 0;

    Iterator itemActionKeyIt = itemActionKeys.iterator();
    while (itemActionKeyIt.hasNext()) {

        String actionCd = (String) itemActionKeyIt.next();
        List actionList = (List) itemActionGroups.get(actionCd);

        int actionQty = 0;

        if (RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.SUBSTITUTED.equals(actionCd)) {
            substitutedFlag = true;
        } else if (RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.CUST_SHIPPED.equals(actionCd)) {
            shippedFlag = true;
        }
        if (actionList != null){
	        Iterator actionListIt = actionList.iterator();
	        while (actionListIt.hasNext()) {
	            OrderItemActionData oiad = ((OrderItemActionData) actionListIt.next());
	            actionQty += oiad.getQuantity();
	        }
        }

        if(shippedFlag){
            shippedQty = actionQty;
        }

%>

<app:shouldDisplayOrderItemAction name="<%=actionCd%>">
<%itemActionSeq++;%>

<!-- translate action cd -->
<%
    String actionCdMess;
    if (RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.CUST_INVOICED.equals(actionCd)) {
        actionCdMess =  ClwI18nUtil.getMessage(request, "order.detail.actionCode.Invoiced", null);
    } else if (RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.DIST_SHIPPED.equals(actionCd)) {
        actionCdMess = ClwI18nUtil.getMessage(request, "order.detail.actionCode.Shipped", null);
    } else if (RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.ACK_BACKORDERED.equals(actionCd)) {
        actionCdMess = ClwI18nUtil.getMessage(request, "order.detail.actionCode.ACK Backordered", null);
	} else if (RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.CANCELED.equals(actionCd)) {
        actionCdMess = ClwI18nUtil.getMessage(request, "order.detail.actionCode.Canceled", null);
    } else {
        actionCdMess = actionCd;
    }
%>

<tr>
    <td>
        <img id="orderItemActionImg<%=itemActionSeq%>" style="cursor:hand"
             src="<%=ClwCustomizer.getSIP(request,"plus.gif")%>" border="0" width="9"
             onClick="displayOrderItemActionDetail('<%=itemActionSeq%>');"/>
    </td>
    <td colspan="2" align="left" width="270">
        <%=actionCdMess%>
        <%=(actionQty > 0 ? String.valueOf(actionQty) : "")%>
    </td>
</tr>
<tr>
    <td></td>
    <td colspan="2">
        <table id="orderItemActionDetailTable_<%=itemActionSeq%>"
               width="260"
               style="display:none" border="0"
               cellspacing="0" cellpadding="0">

            <%
            	if (actionList != null){
                Iterator actionLisDetailIt = actionList.iterator();
                while (actionLisDetailIt.hasNext()) {
                    OrderItemActionData oiad = ((OrderItemActionData) actionLisDetailIt.next());
            %>
			<% if(enhancedBackorder && oiad.getActionCd().equals(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.ACK_BACKORDERED)){

				String estInStockDate = "  ";
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				APIAccess factory = new APIAccess();
				Order orderEjb = factory.getOrderAPI();

				int orderItemId = oiad.getOrderItemId();
				int orderId = oiad.getOrderId();

				BackorderDataVector bodv = orderEjb.getBackorderDetail(orderId, orderItemId);
				if(bodv!=null && bodv.size()>0){
					BackorderData bod = (BackorderData)bodv.get(0);
					Date est = bod.getEstInStock();
					if(est!=null){
						estInStockDate = sdf.format(est);
					}
				}
					%>
				<tr>

					<td width="20">&nbsp;</td>
					<td>
						Estimated In-Stock: <%=estInStockDate%>
					</td>

				</tr>

			<% }else if(enhancedBackorder && oiad.getActionCd().equals(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.CANCELED)){ %>

				<tr>
					<td width="20">&nbsp;</td>
					<td>
						Item Discontinued
					</td>
				</tr>
			<%
			}else{ %>
            <tr>
			<td width="20">&nbsp;</td>
                <td>
                    <%
                        String dateStr = null;
                        if (oiad.getActionDate() != null) {
                            dateStr = ClwI18nUtil.formatDateInp(request, oiad.getActionDate(), timeZone.getID());
                        }%>
                    <%=actionCdMess + " " + oiad.getQuantity() + (dateStr != null ? " on " + dateStr : "")%>
                </td>
            </tr>
            <%

                if (RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.DIST_SHIPPED.equals(actionCd)) {
                    int actionId = oiad.getOrderItemActionId();
					
                    String key = "CLW_ORDER_ITEM_ACTION:" + actionId;
                    List trackList = (List) itemShippingGroups.get(key);
                    if (trackList != null) {
					
                    	Iterator tracklistIt = trackList.iterator();
                        while (tracklistIt.hasNext()) {
                            OrderItemActionData oiaD = (OrderItemActionData) tracklistIt.next();
                            String actCd = oiaD.getActionCd();
                                String trackComment = oiaD.getComments();
								
                                if(trackComment == null || trackComment.trim().length() < 1){
                            		trackComment = "Tracking number not available.";
                            	}                            
                                ArrayList lastToken = new ArrayList();
                                if (trackComment != null) {
                                    StringTokenizer tok = new StringTokenizer(trackComment);
                                    int ctok = tok.countTokens();
                                    for (int i = 0; i < ctok; i++) {
                                        String prevToken = tok.nextToken();
                                        if (!tok.hasMoreTokens()) {
                                            lastToken.add(prevToken);
                                        }
                                    }
                                }
                                Locale loc = Utility.parseLocaleCode(orderLocale);
                                String language = loc.getDisplayLanguage();
                                String country = loc.getCountry();
            %>
            <%
                for (int t = 0; t < lastToken.size(); t++) {
                	String trackCommentToDisplay = "Ship via: ";
                	trackCommentToDisplay = trackCommentToDisplay + trackComment;
            %>
            <tr>
			<td width="20">&nbsp;</td>
                <td>
                
                <%if(trackComment.toUpperCase().contains("FEDEX")){ %>
                    <a href="http://www.fedex.com/Tracking?ascend_header=1&clienttype=dotcom&cntry_code=<%=country%>&language=<%=language%>&tracknumbers=<%=lastToken.get(t)%>" target="_blank">
                	    <%=trackCommentToDisplay%>
                    </a>
                    
                    <%} else {%>
                    	<%=trackCommentToDisplay%>
                    <%} %>
                   </td>
            </tr>
            <%  } // end of for 
                 
                 }
                    } else{//track list is null
                    %>	
                    	<tr>
	            			<td width="20">&nbsp;</td>
	            			<td>Ship via: Tracking number not available.</td>
                        </tr>
                 <%       
                    }
                }//actionCd is shipped
            } //else
            	
			}}%>
<%

                if (appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.TRACKING_MAINTENANCE)
                  && RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.DIST_SHIPPED.equals(actionCd)) {
                	String shipQtyCtrlName = "shippedQty_"+oidx;
                	String shipTrackingNumCtrlName = "shippedTrackingNum_"+oidx;
                	%>
                    <tr>
	            			<td width="20">&nbsp;</td>
	            			<td>
	            			<table>
	            			<tr>
	            				<td align="center" valign="middle">Qty</td>
	            				<td align="center" valign="middle">Tracking Number</td>
	            			</tr>
	            			<tr>
	            				<td><input type="text" name="<%=shipQtyCtrlName%>" id="<%=shipQtyCtrlName%>" maxlength="4" size="4" value=""></td>
	            				<td><input type="text" name="<%=shipTrackingNumCtrlName%>" id="<%=shipTrackingNumCtrlName%>" maxlength="45" size="20" value=""></td>
	            			</tr>
	            			<tr>
	            				<td>&nbsp;</td>
	            				<td align="right" valign="bottom">
			                    <table border="0" cellpadding="0" cellspacing="0"><!-- save button -->
			                        <tr>
			                            <td align="right"  valign="middle"><img  src='<%=ClwCustomizer.getSIP(request,"buttonLeft.png")%>' border="0"
										></td><td align="center" valign="middle" nowrap class="xpdexGradientButton"
										><a class="xpdexGradientButton" href="#"										
										onclick="commandTrackingNumSubmit('<%=item.getOrderItem().getOrderItemId()%>', '<%=shipQtyCtrlName%>','<%=shipTrackingNumCtrlName%>','hiddenCommand', 'addTrackingNumber');"
										><app:storeMessage key="global.action.label.save"/></a></td
										><td align="left" valign="middle"><img  src='<%=ClwCustomizer.getSIP(request,"buttonRight.png")%>' border="0"></td>
			                        </tr>
			                    </table>
			                	</td>
	            			</tr>
	            			</table>
	            			</td>
                    </tr>
                <%
                }
%>
        </table>
    </td>
</tr>

</app:shouldDisplayOrderItemAction>

<% } %>


<%

    if (shippedFlag && !substitutedFlag) {
        backorderedQty = qty.intValue() - shippedQty;
        if (0 < backorderedQty) {
%>
<tr>
    <td></td>
    <td colspan="2">
        <app:storeMessage key="shop.orderStatus.text.backordered" />&nbsp;<%=backorderedQty%>
    </td>

</tr>

<% }
}
%>

</table>
</logic:present>
</td>

</tr>
<% prevDist = thisDist; %>
</logic:iterate>

<tr>
    <td colspan="<%=colqty%>">
        <table width="100%" height="3" cellspacing="0" cellpadding="0" border="0">
            <tr>
                <td class="shopcharthead" width="100%"></td>
            </tr>
        </table>
    </td>
</tr>
<tr>
<%int csPricecount = 0;%>
<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
    <%csPricecount = 2;%>
</logic:equal>
<td colspan="<%=colqty-1-csPricecount%>" align="left">
    <%
        if (
                clwWorkflowRole.indexOf("APPROVER") >= 0 &&
                        (orderStatus.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL) ||
                                orderStatus.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION) ||
                                orderStatus.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_DATE))
                ) {
            // Show the buttons to approve, reject, and modify
            // the order.
    %>
    <table>
        <tr>
            <td>
                <table>
                    <tr>
                        <% if (!toBeConsolidatedFl) { %>
                        <td>
                            <table border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td align="right" valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonLeft.png")%>' border="0"
									></td><td align="center" valign="middle" nowrap class="xpdexGradientButton"
									><a class="xpdexGradientButton" href="#"  onclick="commandMultiSubmit('hiddenCommand', 'approve')"
									><app:storeMessage key="global.action.label.approve"/></a></td
									><td align="left"  valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonRight.png")%>' border="0"
									></td>
                                </tr>
                            </table>
                        </td>
                        <% } %>

                        <%if (!consolidatedOrderFl) { %>
                        <td>
                            <table border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td align="right"  valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonLeft.png")%>'  border="0"
									></td><td align="center" valign="middle" nowrap class="xpdexGradientButton"
									><a class="xpdexGradientButton" href="#" onclick="commandMultiSubmit('hiddenCommand', 'modify')"
									><app:storeMessage key="shop.orderStatus.text.modify"/></a></td
									><td align="left"  valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonRight.png")%>' border="0"
									></td>
                                </tr>
                            </table>
                        </td>
                        <% } %>
                        <td>
                            <table border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                    <td align="right"  valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonLeft.png")%>' border="0"
									></td><td align="center" valign="middle" nowrap class="xpdexGradientButton"
									><a class="xpdexGradientButton" href="#" onclick="commandMultiSubmit('hiddenCommand', 'reject')"
									><app:storeMessage key="global.action.label.reject"/></a>
                                    </td><td align="left"  valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonRight.png")%>' border="0"
									></td>
                                </tr>
                            </table>
                        </td>

                    </tr>
                </table>
            </td>
        </tr>
        <%if (!consolidatedOrderFl) { %>
        <tr>
            <td>
                <app:storeMessage key="shop.orderStatus.text.approveOn" />:
				<%--<html:text  name="ORDER_OP_DETAIL_FORM" property="approveDate"  size='10'/>   --%>
				<app:dojoInputDate id="approveDate"
                           name="ORDER_OP_DETAIL_FORM"
                           property="approveDate"
                           module="clw.NewXpedx"
                           targets="DateApproveOn"/>

				<img id="DateApproveOn" src="../externals/images/showCalendar.gif"
					width=19  height=19 border=0
					onmouseover="window.status='Choose Date';return true"
					onmouseout="window.status='';return true">
            </td>
        </tr>
        <tr>
        	<td>
                &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(<%=ClwI18nUtil.getUIDateFormat(request)%>)<br>
            </td>
        </tr>
        <% } %>
    </table>
    <% } %>
</td>
<%if (csPricecount > 0) {%>
<td colspan="2" valign="top">
<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">

<table width="100%">


<% boolean freightHandFl = false; %>
<tr>
<td valign="top">
<table width="200">

<tr>
    <td class="smalltext" align="right">
        <b>
            <app:storeMessage key="shop.checkout.text.sTotal"/>
        </b>
    </td>
    <td align=right>
        <bean:define id="subtotal" name="ORDER_OP_DETAIL_FORM" property="subTotal"/>
        <%=ClwI18nUtil.getPriceShopping(request,subTot,orderLocale,null," ")%>
    </td>
</tr>

<%if(addServiceFee.equals("true")){%>
	<tr>
		<td align="right" class="smalltext"><b>Service Fee:</b></td>
		<td align="right" class="checkoutInformation"><%=ClwI18nUtil.getPriceShopping(request,totServiceFee,orderLocale,null,"&nbsp;")%></td>
	</tr>
<%}%>

<bean:define id="freightcost"
             name="ORDER_OP_DETAIL_FORM"
             property="totalFreightCost"
             type="BigDecimal"/>
<%
	boolean showFreight = false;
    if(theForm.getCartDistributors().size() > 0){
		for(int c=0; c<theForm.getCartDistributors().size(); c++){
			ShoppingCartDistData scdd = (ShoppingCartDistData)theForm.getCartDistributors().get(c);
			if(scdd.getDistFreightOptions()!=null && scdd.getDistFreightOptions().size() > 0) {
					showFreight=true;
			}
		}
	}
	if (freightcost != null //&& 0.001 < java.lang.Math.abs(freightcost.doubleValue())
			&& consolidatedToOrderD == null
			&& showFreight==true) {
        freightHandFl = true;
%>
<tr>

    <td class="smalltext" align="right">
        <b><app:storeMessage key="shop.orderStatus.text.freight:"/></b>
    </td>

    <td align=right>
        <% if (!toBeConsolidatedFl) { %>
        <%=ClwI18nUtil.getPriceShopping(request,freightcost,orderLocale,null," ")%>
        <% } else { %>
        *
        <% } %>
    </td>

</tr>
<% } %>

<%--
<% if (theForm.getRushOrderCharge() != null) { %>

<bean:define id="rushCharge"
             name="ORDER_OP_DETAIL_FORM"
             property="rushOrderCharge"
             type="BigDecimal"/>

<% if (rushCharge != null && consolidatedToOrderD == null) { %>
<tr>
    <td class="smalltext" align="right">
        <b><app:storeMessage key="shop.orderStatus.text.rushOrderCharge:"/></b>

    </td>
    <td align=right>
        <% if (!toBeConsolidatedFl) { %>
        <%=ClwI18nUtil.getPriceShopping(request,rushCharge,orderLocale,null," ")%>
        <% } else { %>
        *
        <% } %>
    </td>
</tr>
<% } %>
<% }  /* rush order charge block */ %>--%>

<!-- the misc cost-->
<% if (theForm.getTotalMiscCost() != null) { %>

<bean:define id="misc"
             name="ORDER_OP_DETAIL_FORM"
             property="totalMiscCost"
             type="BigDecimal"/>

<% if (misc != null //&& 0.001 < java.lang.Math.abs(misc.doubleValue())
       && consolidatedToOrderD == null) {
    freightHandFl = true;
%>
<tr>
    <td class="text" align="right">
        <b><app:storeMessage key="shop.orderStatus.text.handling:"/></b>
    </td>
    <td align=right>
        <% if (!toBeConsolidatedFl) { %>
        <%=ClwI18nUtil.getPriceShopping(request,misc,orderLocale,null," ")%>
        <% } else { %>
        *
        <% } %>
    </td>
</tr>
<% } %>
<% } %>

<!-- the tax cost-->
<logic:present name="ORDER_OP_DETAIL_FORM" property="totalTaxCost">

    <bean:define id="tax"
                 name="ORDER_OP_DETAIL_FORM"
                 property="totalTaxCost"
                 type="Double"/>

    <% //if (tax != null && 0.001 < java.lang.Math.abs(tax.doubleValue())) {
		if (tax != null){%>
    <tr>
        <td class="smalltext" align="right">
            <b><app:storeMessage key="shop.orderStatus.text.tax:"/></b>
        </td>
        <td align=right>
            <%=ClwI18nUtil.getPriceShopping(request,tax,orderLocale,null," ")%>
        </td>
    </tr>
    <% } %>
</logic:present>
<% if (consolidatedToOrderD == null && (!toBeConsolidatedFl || !freightHandFl)) { %>
<tr>
    <td class="smalltext" align="right">
        <b><app:storeMessage key="shop.orderStatus.text.grandTotal:"/></b>
    </td>
    <td align=right>
        <bean:define id="total" name="ORDER_OP_DETAIL_FORM" property="totalAmount"/>
        <%=ClwI18nUtil.getPriceShopping(request,total,orderLocale,null," ")%>
    </td>
</tr>
<% } %>
</table>

</td>

</tr>


</table>
</logic:equal>
</td>
<%}%>
<td valign="top" align="right">
    <table>
        <tr>
            <% if (siteD.getSiteId() != siteId) { %>
            <td>
                <table border="0" cellpadding="0" cellspacing="0">
                    <tr>
                        <td align="right"  valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonLeft.png")%>' border="0"
						></td><td align="center" valign="middle" nowrap class="xpdexGradientButton"
						><a class="xpdexGradientButton" href="#" onclick="commandMultiSubmit('hiddenCommand', 'goToOrderLocation')"
						><app:storeMessage key="shop.orderStatus.text.goToOrderLocation" /></a></td
						><td align="left"  valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonRight.png")%>' border="0"
						></td>
                    </tr>
                </table>
            </td>
            <% } %>
            <logic:equal name="<%=Constants.APP_USER%>" property="allowPurchase" value="true">

                <%
                    if (orderStatus.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL) == false
                            && !consolidatedOrderFl && isAllowReorder.equals("true")) {
                        // Show the buttons reorder button.
                %>

                <td>
                    <table border="0" cellpadding="0" cellspacing="0">
                        <tr>
                            <td align="right"  valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonLeft.png")%>' border="0"
							></td><td align="center" valign="middle" nowrap class="xpdexGradientButton"
							><a class="xpdexGradientButton" href="#" onclick="commandMultiSubmit('hiddenCommand', 'reorder')"
							><app:storeMessage key="global.action.label.reorder"/></a></td
							><td align="left"   valign="middle"><img src='<%=ClwCustomizer.getSIP(request,"buttonRight.png")%>' border="0"
							></td>
                        </tr>
                    </table>
                </td>

                <% } %>
            </logic:equal>
        </tr>
    </table>
</td>

</tr>
<tr>

</table>

</td>
</tr>
<td align="center">
        <font color="red"><html:errors/></font>
    </td>
</tr>
</table>

</logic:present>
<html:hidden property="command" value="hiddenCommand"/>
<html:hidden property="approveAll" value="true"/>
<%if ( appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.TRACKING_MAINTENANCE)){%>
    <input type="hidden" name="shippedOrderItemId" value="" id="shippedOrderItemId">
    <input type="hidden" name="shippedQty" value="" id="shippedQty">
    <input type="hidden" name="shippedTrackingNum" value="" id="shippedTrackingNum">
<% } %>
</html:form>
