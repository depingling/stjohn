<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.service.api.value.AddressData" %>
<%@ page import="com.cleanwise.service.api.value.ShoppingCartData" %>
<%@ page import="com.cleanwise.service.api.value.OrderAddressData" %>
<%@ page import="com.cleanwise.service.api.value.OrderStatusDescData" %>
<%@ page import="com.cleanwise.service.api.value.OrderData"%>
<%@ page import="com.cleanwise.service.api.value.OrderFreightData"%>
<%@ page import="com.cleanwise.service.api.value.OrderAddOnChargeData"%>
<%@ page import="com.cleanwise.service.api.value.OrderItemActionData"%>
<%@ page import="com.cleanwise.service.api.value.OrderItemDescData"%>

<%@ page import="com.cleanwise.view.utils.SessionTool" %>
<%@ page import="com.cleanwise.service.api.value.AccountData"%>
<%@ page import="com.cleanwise.service.api.value.SiteData"%>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="org.apache.struts.util.LabelValueBean" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.TimeZone" %>
<%@ page import="java.util.List" %>

<%@ page import="com.espendwise.view.forms.esw.OrdersForm"%>
<%@ page import="com.cleanwise.view.forms.OrderOpDetailForm"%>
<%@ page import="com.cleanwise.service.api.value.OrderItemDescDataVector"%>

<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
        
<script type="text/javascript" src="../../externals/dojo_1.1.0/dojo/dojo.js" djConfig="parseOnLoad: true, isDebug: false">
</script>

<script language="JavaScript1.2">
<!--

 function actionSubmit(actionDef, action) {
    var actions = document.getElementsByName('action');
    alert("actionDef: " + actionDef + "     actions.length:  " + actions.length);
    for(ii = 0; ii < actions.length; ii++) {
        alert("value: " + actions[ii].value);
        if (actions[ii].value ==  actionDef) {
            //alert("action: " + action);
            actions[ii].value = action;
            actions[ii].form.submit();
        break;
        }
    }
    return false;
 }

//-->

function saveOrderDetailInfo() {
	dojo.byId("orderFormOperation").value = '<%=Constants.PARAMETER_OPERATION_VALUE_UPDATE_ORDER_DETAIL%>';
	submitForm('orderForm');
}

function submitForm(operation) {
	var frm = document.forms['orderForm'];
	if (frm) {
		frm.elements['<%=Constants.PARAMETER_OPERATION%>'].value = operation;
		frm.submit();
	}
}

function reorder(operation, cartHasItems){
    if(cartHasItems=='true'){
	    var r = window.confirm('<%=ClwI18nUtil.getMessage(request, "dashboard.message.overwriteShoppingCart")%>');
		if(r == true){
		    submitForm(operation);
		}
    }else{
		submitForm(operation);
	}
}
</script>

<% String saveOrderDetailLink = "userportal/esw/orders.do"; %>
 
<% CleanwiseUser appUser = (CleanwiseUser)
     session.getAttribute(Constants.APP_USER); %>

<bean:define id="theForm" name="esw.OrdersForm" type="com.espendwise.view.forms.esw.OrdersForm"/>
<bean:define id="workflowRole" type="java.lang.String" name="ApplicationUser" property="user.workflowRoleCd"/>

<%
OrderStatusDescData orderStatusDetail = theForm.getOrderOpDetailForm().getOrderStatusDetail();
AccountData accountD = SessionTool.getAccountData(request,orderStatusDetail.getOrderDetail().getAccountId());
 if(accountD == null){
     accountD = appUser.getUserAccount();
 }
SiteData siteD = SessionTool.getSiteData(request,orderStatusDetail.getOrderDetail().getSiteId());
SiteData currSiteD = appUser.getSite();
if(siteD == null){
    siteD = currSiteD;
}
boolean allowPoEntry = true;
 if(RefCodeNames.CUSTOMER_SYSTEM_APPROVAL_CD.PUNCH_OUT_INLIN_NON_E_ORD_ONLY.
   equals(accountD.getCustomerSystemApprovalCd())){
   allowPoEntry = false;
 }

 if(!accountD.isCustomerRequestPoAllowed()){
      allowPoEntry=false;
    }

 boolean usingBlanketPo = false;
 if(siteD!=null && siteD.getBlanketPoNum() != null && siteD.getBlanketPoNum().getBlanketPoNumId() != 0){
   usingBlanketPo = true;
   allowPoEntry = false;
 }
 
  String approvalDate = theForm.getApprovalDate();
  if (!Utility.isSet(approvalDate)) {
	 Date dNow = new Date();
	 String pattern  = ClwI18nUtil.getDatePattern(request);
	 SimpleDateFormat formatterAD = new SimpleDateFormat(pattern);
	 approvalDate = formatterAD.format(dNow);
  }
  
%>
<%
  
  String orderLocale = orderStatusDetail.getOrderDetail().getLocaleCd();
  String orderId = Integer.toString(orderStatusDetail.getOrderDetail().getOrderId());
  ClwI18nUtil clwI18n = ClwI18nUtil.getInstance(request,orderLocale,-1);
  SimpleDateFormat sdfInter = new SimpleDateFormat("yyyy-MM-dd");
  TimeZone timeZone =  Utility.getTimeZoneFromUserData(appUser.getSite());
  SimpleDateFormat sdfEntry = new SimpleDateFormat("yyyy-MM-dd" +" " + Constants.SIMPLE_TIME24_PATTERN + ".S");
  sdfEntry.setTimeZone(timeZone);
  SimpleDateFormat sdfComment = new SimpleDateFormat("yyyy-MM-dd k:mm");
  sdfComment.setTimeZone(timeZone);
  boolean approverFl = (workflowRole.indexOf("APPROVER") >= 0) ? true : false;
  boolean consolidatedOrderFl =
   RefCodeNames.ORDER_TYPE_CD.CONSOLIDATED.equals(orderStatusDetail.getOrderDetail().getOrderTypeCd());

  boolean toBeConsolidatedFl =
   RefCodeNames.ORDER_TYPE_CD.TO_BE_CONSOLIDATED.equals(orderStatusDetail.getOrderDetail().getOrderTypeCd());

  OrderData consolidatedToOrderD = orderStatusDetail.getConsolidatedOrder();

  boolean useCustPo = true;
  String requestPoNum = orderStatusDetail.getOrderDetail().getRequestPoNum();

  if(requestPoNum == null || requestPoNum.equals("") ||
  requestPoNum.equalsIgnoreCase("na") ||	requestPoNum.equalsIgnoreCase("n/a")){
    useCustPo = false;
  }
 
 boolean enterPo = false;
 if(appUser.getUserAccount().isCustomerRequestPoAllowed()){
	enterPo=true;
 }
%>


<% //Getting properly formatted Order Date: Begin
   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
   sdf.setTimeZone(timeZone);
   Date date = orderStatusDetail.getOrderDetail().getOriginalOrderDate();
   Date time = orderStatusDetail.getOrderDetail().getOriginalOrderTime();
   Date orderDate = Utility.getDateTime(date, time);

   String formattedODate = ClwI18nUtil.formatDateInp(request, orderDate, timeZone.getID() );
   //Getting  properly formatted Order Date: End
%>

<app:setLocaleAndImages/>
		<!-- Begin: Error Message -->
		<%
			String errorsAndMessagesPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "errorsAndMessages.jsp");
		%>
		<jsp:include page="<%=errorsAndMessagesPage %>"/>
		<!-- End: Error Message -->
		<!-- Begin: Shopping Sub Tabs -->
        <%
			String shoppingTabPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "shoppingTabsWide.jsp");
		%>
        <jsp:include page="<%=shoppingTabPage%>"/>
      	<!-- End: Shopping Sub Tabs -->
                <!-- Start Main Navigation -->
        <div id="contentWrapper" class="singleColumn clearfix prodCat wide">
            <div id="content">
                <!-- Start Box -->
                <div class="boxWrapper squareCorners smallMargin firstBox">
		
					<html:form styleId="commentForm" action="userportal/esw/showOrderDetail.do">
					<html:hidden styleId="commentFormOperation" property="<%=Constants.PARAMETER_OPERATION%>"/>
               				<html:hidden styleId="commentFormComment" property="orderComment.value" value=""/>
					<html:hidden styleId="commentFormOrderId" property="orderComment.orderId" value="<%=orderId%>"/>
					</html:form>
			        <html:form styleId="orderForm" name="esw.OrdersForm" action="userportal/esw/showOrderDetail.do"> 
                    <html:hidden name="esw.OrdersForm" property="operation"/>                   	                   	                                        
                    
                    <%
				       String contentOrderBasicInfoPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "contentViewOrderBasicInfoSubPanel.jsp");
				    %>
                    <jsp:include page="<%=contentOrderBasicInfoPage%>"/>			                    					
					     
			   </div>
                <!-- End Box -->
                <%
				       //String contentOrderInfoPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "contentViewOrderInfoSubPanel.jsp");
				%>
				<!--  
                <jsp:include page="<%//=contentOrderInfoPage%>"/>
                -->
                <% String orderStatus0 = theForm.getOrderOpDetailForm().getOrderStatusDetail().getOrderDetail().getOrderStatusCd(); %>
                <% 
                  boolean pendingFl =
                  (orderStatus0.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL) ||
                  orderStatus0.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_DATE)) ? true : false;
                  if (pendingFl) {
                      // In case of "Pending Approval" or "Pending Date" Order show LocationBudget panel(screen)
					  String locationBudgetPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "dashboardLocationBudget.jsp");
				%>
                      <jsp:include page="<%=locationBudgetPage %>"/>
                <% } %>
                
                <%
				       String contentOrderItemsPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "contentViewOrderItemsSubPanel.jsp");
				%>
                <jsp:include page="<%=contentOrderItemsPage%>"/>	
                			                                              
                <%
				   String contentOrderShippingAndBillingPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "contentViewOrderShippingAndBillingSubPanel.jsp");
				%>
                <jsp:include page="<%=contentOrderShippingAndBillingPage%>"/>
                								
				<!-- Start Box -->
		                            <% boolean reasonCodeFl = false; %>
                            <logic:iterate id="orderProperty" indexId="orderPropertyIndex"
							  name="theForm" property="orderOpDetailForm.orderPropertyList" 
							  type="com.cleanwise.service.api.value.OrderPropertyData">
                              <% //Workflow properties %>
							  <logic:equal name="orderProperty" property="shortDesc" value="<%=Constants.WORKFLOW_NOTE%>">							            
							             <%											
											//if the reason hasn't been approved and is one the user is authorized to handle,
											//set the reasonCodeFl
											if (orderProperty.getApproveDate() == null &&
											   theForm.getNotesUserApproveIdV().contains(orderProperty.getOrderPropertyId())) {
												    reasonCodeFl = true;
											}
										 %>
							  </logic:equal>
                            </logic:iterate>

		<%
		boolean showBoxF = false;
		boolean pendingApprF = false;
		boolean notPendingOrderF = false;
		boolean approver = false;
		if (orderStatus0.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL)){
			pendingApprF = true;
		}
		if(approverFl || reasonCodeFl){
			approver = true;
		}
		if(!orderStatus0.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION)
			&& !orderStatus0.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_DATE)
			&& !orderStatus0.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL)){
			notPendingOrderF = true;
		}
		
		
		if((pendingApprF && approver)|| notPendingOrderF || (pendingApprF && allowPoEntry && appUser.canMakePurchases() && enterPo)){
			showBoxF = true;
		}
		
		if(showBoxF){
		%>
						
		
                <div class="boxWrapper squareCorners">
                    <div class="top clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
                    <div class="content">
                        <div class="left clearfix">
				<%if(pendingApprF){ %>
				
						<%
						if(allowPoEntry && !appUser.isBrowseOnly() && appUser.canMakePurchases() && enterPo){
						%>
					
						<a href="#" class="blueBtnMed right topMargin" onclick="submitForm('<%=Constants.PARAMETER_OPERATION_VALUE_UPDATE_ORDER%>')">
							<span><app:storeMessage key="global.action.label.update" /></span>
						</a>
						<%
						}
						%>
						<%if(approver && !appUser.isBrowseOnly()){ %>
						
					<p class="left approveRow">                               																	
						<a href="#" class="blueBtnMed topMargin calendarBtn" onclick="submitForm('<%=Constants.PARAMETER_OPERATION_VALUE_APPROVE_ORDER%>')">
							<span><app:storeMessage key="global.action.label.approve" /></span>
						</a> 
						<span class="calendarInput right topMargin">
							<html:text property="approvalDate" value="<%=approvalDate%>" styleClass="datepicker2Col dateMirror approveCal" />
	        	                        </span>
						<a href="#" class="blueBtnMed  topMargin" onclick="submitForm('<%=Constants.PARAMETER_OPERATION_VALUE_REJECT_ORDER%>')">
							<span><app:storeMessage key="global.action.label.reject" /></span>
						</a>
						<a href="#" class="blueBtnMed  topMargin" onclick="submitForm('<%=Constants.PARAMETER_OPERATION_VALUE_MODIFY_ORDER%>')">
							<span><app:storeMessage key="shop.orderStatus.text.modify" /></span>
						</a>
						
					</p>
					<%}%>
				<%}else if (!appUser.isBrowseOnly()) {
					boolean cartHasItems = false;
					ShoppingCartData scD = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
					if(scD!=null && scD.getItemsQty() > 0){
						cartHasItems = true;
					}
                    String href = "javascript:reorder('" + Constants.PARAMETER_OPERATION_VALUE_REORDER + "', '"+cartHasItems+"');";
    				if(notPendingOrderF  && appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.RECEIVING)){%>
                        <a href="#" class="blueBtnMed right" onclick="submitForm('<%=Constants.PARAMETER_OPERATION_VALUE_UPDATE_RECEIVE%>')">
                            <span><app:storeMessage key="global.action.label.receive" /></span>
                        </a>
					<%}%>
	                <html:link href="<%=href%>" styleClass="blueBtnMed right">
	                    <span><app:storeMessage key= "global.action.label.reorder"/></span>
	                </html:link>
				<%}%>
                        
			</div>
                    </div>
                    <div class="bottom clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
		</div>
		<% } %>
                <!-- End Box --> 
                
                </html:form>
			</div>
			
	    </div>

    

<div id="ui-datepicker-div" class="ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all"></div>