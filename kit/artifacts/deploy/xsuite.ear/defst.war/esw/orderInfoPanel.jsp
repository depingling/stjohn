<%@page import="java.util.Formatter"%>
<%@page import="com.cleanwise.service.api.value.IdVector"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@page import="com.cleanwise.view.utils.Constants"%>
<%@page import="com.cleanwise.service.api.value.ConsolidatedCartView"%>
<%@page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
<%@page import="com.cleanwise.service.api.util.PropertyUtil"%>
<%@page import="com.cleanwise.service.api.value.OrderData"%>
<%@page import="com.cleanwise.service.api.value.ShoppingCartData"%>
<%@page import="com.cleanwise.service.api.util.RefCodeNames"%>
<%@page import="com.cleanwise.service.api.value.AccountData"%>
<%@page import="com.cleanwise.view.utils.ShopTool"%>
<%@page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@page import="com.cleanwise.view.forms.CheckoutForm"%>
<%@page import="com.cleanwise.service.api.util.Utility"%>
<%@page import="com.cleanwise.service.api.value.ShoppingCartDistDataVector"%>
<%@page import="com.cleanwise.service.api.value.ShoppingCartDistData"%>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>

<bean:define id="newCheckOutForm" name="esw.CheckOutEswForm"  type="com.espendwise.view.forms.esw.CheckOutForm"/>
<%
	String sourcePanel = request.getParameter("checkOutPanel");
	boolean isCheckOutPanel = false;
	if(Utility.isSet(sourcePanel)) {
		try{
			isCheckOutPanel = Boolean.valueOf(sourcePanel).booleanValue();
		}finally{}
	}
	//Get Check Out form.
	CheckoutForm checkOutForm = newCheckOutForm.getCheckOutForm();
	ShoppingCartData shoppingCartData = newCheckOutForm.getShoppingCartData();
	CleanwiseUser user = ShopTool.getCurrentUser(request);
	String userLocaleS = user.getPrefLocale().toString();
	userLocaleS = userLocaleS.replaceAll("_", "-");
	
	OrderData prevOrderData = shoppingCartData.getPrevOrderData();

	 AccountData account = user.getUserAccount();
	 boolean allowPoEntry = true,
	 		 allowAccountComments = true; 
	 if(RefCodeNames.CUSTOMER_SYSTEM_APPROVAL_CD.PUNCH_OUT_INLIN_NON_E_ORD_ONLY.
             equals(account.getCustomerSystemApprovalCd())){
		allowPoEntry = false;
		allowAccountComments = false;
	}
	 
	//Old code was written to compare store id with hard coded value.
	// Need to search for other ways to compare with ids from configuration files like thing.
	if(user.getUserStore().getStoreId() == 173243){
		allowPoEntry = true;
	}
	boolean allowPoByVender = false;
	if (allowPoEntry){
		allowPoByVender = Utility.isTrue(PropertyUtil.getPropertyValue(user.getUserStore().getMiscProperties(),
	    		 		RefCodeNames.PROPERTY_TYPE_CD.ALLOW_PO_NUM_BY_VENDER));
	}
	if(!user.getUserAccount().isCustomerRequestPoAllowed()){
	      allowPoEntry=false;
	}
	
	//PO Number.
	String poNumber="";
	boolean usingBlanketPo = false;
	if(checkOutForm.getSite().getBlanketPoNum()!= null && checkOutForm.getSite().getBlanketPoNum().getBlanketPoNumId()!=0) {
		usingBlanketPo=true;
		poNumber = "";
	    allowPoEntry=false;
	}else {
		poNumber = checkOutForm.getPoNumber();
		if(!Utility.isSet(poNumber)) {
			if(Utility.isSet(shoppingCartData.getSavedPoNumber())) {
				poNumber = shoppingCartData.getSavedPoNumber();
			} else {
				if(prevOrderData!=null && Utility.isSet(prevOrderData.getRefOrderNum())){
					poNumber = prevOrderData.getRefOrderNum();
				}
			}
		}
	}
	
	boolean showPoField = false;
	if(allowPoEntry  || (usingBlanketPo)){
	  showPoField = true;
	}
	boolean isShowGlobalRequired = false;
	if (user.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.PLACE_ORDER_MANDATORY_REQUEST_SHIP_DATE)) {
	    isShowGlobalRequired = true;
	}
	if(showPoField && user.getPoNumRequired()) {
	    isShowGlobalRequired = true;
	}
	ShoppingCartDistDataVector cartDistributors = newCheckOutForm.getCheckOutForm().getCartDistributors();
	for (int i = 0; cartDistributors != null && i < cartDistributors.size(); i++) {
	    ShoppingCartDistData cartDistributor = (ShoppingCartDistData) cartDistributors.get(i);
	    if(Utility.isSet(cartDistributor.getDistFreightOptions()) && cartDistributor.getDistFreightOptions().size() > 0) {
	        isShowGlobalRequired = true;
	        break;
	    }
	}

	// Begin: Place Order fields
	boolean showPlaceOrderFields = false,
			showPlaceOrderButton = true,
			showOrderSelected = false;
	if ( user.canMakePurchases() ) {
		 showPlaceOrderFields = true;
	}
	
	if ( ShopTool.isInventoryShoppingOn(request)) {

		showPlaceOrderFields = true;
		if ( user.canMakePurchases() ) {
			showOrderSelected = true;
		}
		if (ShopTool.isModernInventoryShopping(request)) {
			showPlaceOrderButton = true;
			showPlaceOrderFields = true;
			if(showOrderSelected) 
				showOrderSelected = false;
		} else {
			showPlaceOrderButton = false;
		}
	}
	//End: Place Order fields.
	
	//Begin: Order Consolidation.
	String allowOrderConsolidationS = account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_ORDER_CONSOLIDATION);
	boolean consolidatedOrderFl =(shoppingCartData instanceof ConsolidatedCartView)? true:false;

      boolean allowOrderConsolidationFl = Utility.isTrue(allowOrderConsolidationS);
	 if(consolidatedOrderFl) 
		 allowOrderConsolidationFl = false;
	 //End: Order Consolidation
	 
	//Placed By
	String orderContactName = checkOutForm.getOrderContactName();
    String orderContactPhoneNum = checkOutForm.getOrderContactPhoneNum();
    String orderContactEmail = checkOutForm.getOrderContactEmail();
    String orderMethod = checkOutForm.getOrderOriginationMethod();
    boolean isStoreAdmin = user.isaStoreAdmin();
    boolean crcManagerFl = user.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.CRC_MANAGER);
    
    if ( user.isaCustServiceRep() || crcManagerFl || isStoreAdmin ) {

        if ( prevOrderData != null ) {
          // Copy in the values from the previous order.
          orderContactName = prevOrderData.getOrderContactName();
          if ( orderContactName == null ) 
        	  orderContactName = "";

          orderContactPhoneNum = prevOrderData.getOrderContactPhoneNum();
          if ( orderContactPhoneNum == null ) 
        	  orderContactPhoneNum = "";

          orderContactEmail = prevOrderData.getOrderContactEmail();
          if ( orderContactEmail == null ) 
        	  orderContactEmail = "";

          orderMethod = prevOrderData.getOrderSourceCd();
          if ( orderMethod == null ) 
        	  orderMethod = "";
          /* Do not allow EDI as an option for web orders. */
          if ( orderMethod.equals(Constants.CHECK_OUT_ORDER_METHOD_EDI)) 
        	  orderMethod = Constants.CHECK_OUT_ORDER_METHOD_OTHER;
        }

      }
    
  //Reset Date fields to default user locale, if they are not set.
  String defaultDateFormat ="";
    if(isCheckOutPanel) {
    	defaultDateFormat = ClwI18nUtil.getUIDateFormat(request).toLowerCase();

    	if(!Utility.isSet(checkOutForm.getProcessOrderDate())){
    		checkOutForm.setProcessOrderDate(defaultDateFormat);
    	}
    	if(!Utility.isSet(checkOutForm.getRequestedShipDate())) {
    		checkOutForm.setRequestedShipDate(defaultDateFormat);
    	}
    }
    
    String deliveryDatesList = checkOutForm.getDeliveryDateList();
%>
<script type="text/javascript">
internationalizeDatePickers("<%=userLocaleS%>");
</script>
<div class="boxWrapper smallMargin squareBottom">
                    <div class="top clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
                    <div class="content">
                        <div class="left clearfix">
                            <h2>
                            <app:storeMessage key="userportal.esw.checkout.text.orderInformation" />
                            </h2>
                            <% if(isCheckOutPanel && isShowGlobalRequired) { %>
                                  <p class="required right">* 
                                  <app:storeMessage key="global.text.required" />
                                  </p>
                            <% } %>
                            <hr>
                            
                            <div class="twoColBox">
                                <div class="column rightPadding">
                                    <table>
                                        <colgroup>
                                            <col>
                                            <col width="55%">
                                        </colgroup>
                                        <tbody><tr>
											<td>
                                                <app:storeMessage key="userportal.esw.checkout.text.orderType" />
                                            </td>
                                            <td>
                                            	<%if(isCheckOutPanel) { 
                                            		orderMethod = RefCodeNames.ORDER_SOURCE_CD.WEB;
											        //orderMethod = ClwI18nUtil.getMessageOrNull(request, "userportal.esw.checkout.orderMethodWeb");
											        //if (!Utility.isSet(orderMethod)) {
											        //	orderMethod = Constants.CHECK_OUT_ORDER_METHOD_WEB;
											        //}
                                            	%>
                                                	<html:hidden name="esw.CheckOutEswForm" property="checkOutForm.orderOriginationMethod" value="<%=orderMethod%>" />
                                                <%} %>
                                                <app:i18nStatus refCodeName="<%=orderMethod%>" refCodeGroup="refcode.ORDER_SOURCE_CD" />
<%--                                                 <%=Utility.encodeForHTML(orderMethod) %> --%>
                                            </td>
										</tr>
										<% if (showPlaceOrderFields && showPlaceOrderButton){%>
										
										<%  
											if(user.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.PLACE_ORDER_PROCESS_ORDER_ON)) {
										%>
										
										<tr>
                                            <td>
                                            	<app:storeMessage key="userportal.esw.checkout.text.processOrderOn" />
                                            </td>
                                            <td>
                                            	
                                            	<%if(isCheckOutPanel) {
							String processDateClass = "standardCal";
							if(checkOutForm.getProcessOrderDate().equals(defaultDateFormat)){
								processDateClass += " default-value";
							}
						%>
	                                            	<html:text name="esw.CheckOutEswForm" property="checkOutForm.processOrderDate" 
	                                            	styleClass="<%=processDateClass%>"/>
                                            	<%} else { %>
                                            		<bean:write name="esw.CheckOutEswForm" property="checkOutForm.processOrderDate"/>
                                            	<%} %>
                                            </td>
                                        </tr>
                                        <%} %>
                                        <% 
                                           //OLD UI: shows 'Requested Delivery Date' when 'Show Dist. Delivery Date' is checked in Accounts > Details.
                                           //New UI: Needs the following functions as checked to display 'Requested Delivery Date'.
                                           //1. Admin > Groups > Functions > Place Order Request Ship Date
                                           //2. Admin > Groups > Functions > Place Order MANDATORY Request Ship Date 
                                          /* OLD UI:
                                           String showDistDeliveryDateS =  account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_DIST_DELIVERY_DATE);
                                           if(Utility.isTrue(showDistDeliveryDateS,false)) {*/
                                        	  
                                        	if(user.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.PLACE_ORDER_REQUEST_SHIP_DATE) ||
                                        			user.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.PLACE_ORDER_MANDATORY_REQUEST_SHIP_DATE)) {
                                        %>
                                        <tr>
                                            <td>
                                                <app:storeMessage key="userportal.esw.checkout.text.requestedDeliveryDate" />
                                                <%if(user.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.PLACE_ORDER_MANDATORY_REQUEST_SHIP_DATE) && isCheckOutPanel){%>
                                                	<span class="required">*</span>
                                                <%} %>
                                            </td>
                                            <td>
                                            	
                                            	<%if(isCheckOutPanel) {
							String requestDateClass = "standardCal";
							if(checkOutForm.getRequestedShipDate().equals(defaultDateFormat)){
								requestDateClass += " default-value";
							}
						%>
                                            		<html:text property="checkOutForm.requestedShipDate" name="esw.CheckOutEswForm" 
                                            		styleClass="<%=requestDateClass%>" styleId="unique1" />
                                            	<%} else { %>
                                            		<bean:write name="esw.CheckOutEswForm" property="checkOutForm.requestedShipDate"/>
                                            	<%} %>
                                            </td>
                                        </tr>
                                        <%} %>
                                        <%} %>
                                        <% // PO field.
                                        if(showPoField) { %>
                                        <tr>
                                            <td>
                                                <app:storeMessage key="userportal.esw.checkout.text.poNum" /> 
                                                <%if(user.getPoNumRequired() && isCheckOutPanel){%>
                                                	<span class="required">*</span>
                                                <%} %>
                                            </td>
                                            
                                            <td>
                                            <%if(isCheckOutPanel) { %>
                                            	 <%if(!allowPoByVender && allowPoEntry ){%>
                                            	 <html:text name="esw.CheckOutEswForm" property="checkOutForm.poNumber" size="20" maxlength="22" value="<%=poNumber%>" />
                                                <%} else if(usingBlanketPo){%>
                                                	<bean:write name="esw.CheckOutEswForm" property="checkOutForm.site.blanketPoNum.poNumber"/>
    												<html:hidden name="esw.CheckOutEswForm" property="checkOutForm.poNumber" value="<%=poNumber%>" />
                                                <%} else { %>
                                                	<html:hidden name="esw.CheckOutEswForm" property="checkOutForm.poNumber" value="<%=poNumber%>" />
                                                <%} %>
                                             <%} else { %>
                                             	<bean:write name="esw.CheckOutEswForm" property="checkOutForm.poNumber"/>
                                             <%} %>
                                            </td>
                                           
                                        </tr>
										<%} %>
										
										<% //Re Bill
										if (account.isShowReBillOrder()) { %>
										<tr>
                                            <td>
                                             <app:storeMessage key="shop.checkout.text.rebillOrder" /> 
                                            </td>
											<td>
                                                <label>
                                                	<%
                                                	String rebillOrderVal = newCheckOutForm.getCheckOutForm().getRebillOrder();
                                                	if(isCheckOutPanel) { 
                                                		if(Utility.isTrue(rebillOrderVal) || Utility.isOn(rebillOrderVal)) {
                                                		%>
                                                			<app:storeMessage key="global.text.yes" />
                                                			<html:hidden name="esw.CheckOutEswForm" property="checkOutForm.rebillOrder"/>
                                                		<%} else {
	                                                		%>
	                                                		<html:checkbox name="esw.CheckOutEswForm" property="checkOutForm.rebillOrder" 
	                                                		styleClass="chkBox rebill" /> 
	                                                		<%
                                                		}	
                                                	} else { %>
                                                	 <% 
	                                                	if (Utility.isTrue(rebillOrderVal) || Utility.isOn(rebillOrderVal)) { %>
	                                                      	<app:storeMessage key="global.text.yes" />
	                                                    <% } else { %>
	                                                        <app:storeMessage key="global.text.no" />
	                                                    <% }
                                                	 }%>
                                                </label>                                        
                                            </td>
                                        </tr>
                                        <%} %>
                                        <% // Consolidation
                                        if (allowOrderConsolidationFl) { %>
										<tr>
                                            <td>
                                             <app:storeMessage key="userportal.esw.checkout.text.holdOrderForConsolidation" /> </td>
											<td>
                                                <label> 
                                                <%if(isCheckOutPanel) { %>
                                                	<html:checkbox name="esw.CheckOutEswForm" property="checkOutForm.pendingConsolidation"
                                                	styleClass="chkBox consolidate"
                                                	/>
                                                <%} else { %>
                                                	<% if (newCheckOutForm.getCheckOutForm().getPendingConsolidation()) { %>
                                                      	<app:storeMessage key="global.text.yes" />
                                                    <% } else { %>
                                                        <app:storeMessage key="global.text.no" />
                                                    <% } %>  
                                                <%} %>
                                                </label>                                        
                                            </td>
                                        </tr>
										<%} %>
										<% // Confirmation Only Order
										if(user.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.PLACE_CONFIRMATION_ONLY_ORDER)) {
											String confirmClass = "confirm hide";
											boolean isBillingOrder = newCheckOutForm.getCheckOutForm().isBillingOrder();
											if (isBillingOrder) {
												confirmClass = "confirm";
											}
										%>
										<tr>
                                            <td>
                                             <app:storeMessage key="userportal.esw.checkout.text.confirmationOnlyOrder" />
                                            </td>
											<td>
                                                <label>
                                                <%if(isCheckOutPanel) { 
													String checked="";
                                                	if(isBillingOrder) {
                                                		checked = "checked";
													}
                                                	//STJ-4749: Hidden field is added to fix the issue with select/deselect of Confirm Order Checkbox.
												%>
                                                	<input type="checkbox" class="chkBox confirm" <%=checked %> />
													<html:hidden name="esw.CheckOutEswForm" property="checkOutForm.billingOrder" styleId="confirmOrderId"/>
                                                <%} else { %>
                                                   		<% if (newCheckOutForm.getCheckOutForm().isBillingOrder()) { %>
                                                          <app:storeMessage key="global.text.yes" />
                                                        <% } else { %>
                                                            <app:storeMessage key="global.text.no" />
                                                        <% } %>                                              
                                                <%} %>
                                                </label>                                        
                                            </td>
                                        </tr>
                                        <tr class="<%=confirmClass %>" >
                                            <td>
                                                <app:storeMessage key="userportal.esw.checkout.text.originalPO" />
                                            </td>
                                            <td>
                                             <%if(isCheckOutPanel) { %>
                                            	<html:text name="esw.CheckOutEswForm" property="checkOutForm.billingOriginalPurchaseOrder"/>
                                             <%} else { %>
                                                		<bean:write name="esw.CheckOutEswForm" property="checkOutForm.billingOriginalPurchaseOrder"/>
                                                <%} %>
                                            </td>
                                        </tr>
                                        <tr class="<%=confirmClass %>" >
                                            <td>
                                                <app:storeMessage key="userportal.esw.checkout.text.distributorInvoiceNumber" />
                                            </td>
                                            <td>
                                            	<%if(isCheckOutPanel) { %>
                                                	<html:text name="esw.CheckOutEswForm" property="checkOutForm.billingDistributorInvoice"/>
                                            	<%} else { %>
                                                		<bean:write name="esw.CheckOutEswForm" property="checkOutForm.billingDistributorInvoice"/>
                                                <%} %>
                                            </td>
                                        </tr>
                                        <%} %>
                                        
                                        <% // Exclude order from budget
										if(user.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.EXCLUDE_ORDER_FROM_BUDGET)  ) { %>
                                        <tr>
                                            <td>
                                                <app:storeMessage key="userportal.esw.checkout.text.excludeOrderFromBudget" />
                                            </td>
                                            <td>
                                                   <% if (newCheckOutForm.getCheckOutForm().isBypassBudget()) { %>
                                                          <app:storeMessage key="global.text.yes" />
                                                     <% } else { %>
                                                         <app:storeMessage key="global.text.no" />
                                                     <% } %>

                                            </td>
                                        </tr>
                                        <%} %>
                                        
                                        <% // Change Order Budget Period
										if(user.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.CHANGE_ORDER_BUDGET_PERIOD) &&
												newCheckOutForm.getCheckOutForm().getBudgetPeriodChoices() != null) { 
												
											boolean cataogHasCostCenters = (ShopTool.getAllCostCenters(request) != null 
													&& ShopTool.getAllCostCenters(request).size() > 0) ? true : false;
												%>
                                        <tr>
                                            <td>
                                            	<%if(isCheckOutPanel) {
                                            	if(cataogHasCostCenters) { %>
                                                	<app:storeMessage key="userportal.esw.checkout.text.changeOrderBudgetPeriod" />
                                                <% } } else { %>
                                                	<app:storeMessage key="userportal.esw.checkout.text.orderBudgetPeriod" />
                                                <%} %>
											</td>
                                            <td>
                                            	<%if(isCheckOutPanel) {
                                            		if(cataogHasCostCenters) {
                                            		boolean viewOnly = newCheckOutForm.getCheckOutForm().isBypassBudget();%>   
                                            		<html:select disabled="<%=viewOnly%>" property="checkOutForm.budgetYearPeriod">
						                            	<html:optionsCollection name="esw.CheckOutEswForm"
						                            		property="checkOutForm.budgetPeriodChoices" label="label" value="value"/>
						                        	</html:select>
                                            	<%} } else { %>
                                            		<bean:write name="esw.CheckOutEswForm" property="checkOutForm.budgetYearPeriodLabel"/>
                                            	<%} %>
                        	                                                  
                                            </td>
                                        </tr>
                                        <%} %>
                                        
                                        <% // Remote Access - Display Service Tickets Info
										if(user.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ACCESS_SHOPPING) &&
												newCheckOutForm.isRemoteAccess()) { 
										%>
                                        <tr>
                                            <td>
                                            	<app:storeMessage key="shop.text.serviceticket.serviceTicketNumbers" />
											</td>
                                            <td>
                                            	<%if(isCheckOutPanel) {
                                            	%>   
                                            		<html:text name="esw.CheckOutEswForm" property="checkOutForm.orderServiceTicketNumbers"/>
                                            		
                                            	<%} else { 
                                            		//If it is CheckOut Confirmation Page.
                                            		IdVector serviceTickets = null;
                                            		try {
                                            			serviceTickets = newCheckOutForm.getCheckOutForm().getOrderRequest().getServiceTicketOrderRequest().getServiceTickets();
                                            		} catch(Exception e){
                                            		}
                                            		
                                            		if(Utility.isSet(serviceTickets)) {
                                            		%>
                                            			<bean:define id="remoteAccessMgrForm" name="esw.RemoteAccessMgrForm" type="com.espendwise.view.forms.esw.RemoteAccessMgrForm"/>
									                	<bean:define id="context" name="remoteAccessMgrForm" property="context" type="java.lang.String"/>
									                	<bean:define id="stDetailUri" name="remoteAccessMgrForm" property="serviceTicketDetailUri" type="java.lang.String"/>
                                            		<%
                                            			for(int x = 0 ; x < serviceTickets.size(); x++){
                                            				Integer serviceTicketNumber = (Integer) serviceTickets.get(x);
                                            				String link = context + new Formatter().format(stDetailUri, String.valueOf(serviceTicketNumber));
                                            				%>
                                            				<html:link href="<%=link%>"><b><%=serviceTicketNumber%></b></html:link>
                                            				<%
                                            				if(x < serviceTickets.size()-1) {
                                            					%>
                                            					,&nbsp;
                                            					<%
                                            				}
                                            			}
                                            		} else {
                                            			%>
                                            			-
                                            			<%
                                            		}
                                            	
                                            	} %>
                        	                                                  
                                            </td>
                                        </tr>
                                        <%} %>
                                               
                                        <% if(Utility.isSet(deliveryDatesList)){%>
										  <tr>
										    <td>
										      <app:storeMessage key="shop.checkout.text.deliveryDate"/>
										    </td>
										    <td>
										    <%if(isCheckOutPanel){%>
										    <input class="specificDays" type="hidden" value="<%=deliveryDatesList%>" />
										    
                                            <html:text property="checkOutForm.deliveryDate"
                                                		styleClass="specificDatesCal" styleId="specificDates" 
                                                		readonly="true"/>
                                              
                                            <%}else{%>
                                            		<bean:write name="esw.CheckOutEswForm" property="checkOutForm.deliveryDate"/>
                                            <%} %>
                                            </td>
					
										  </tr>
  										<%}%>
                                        
                                        
                                    </tbody></table>
                                </div>
								
								<div class="column">
                                    <table>
                                        <colgroup>
                                            <col>
                                            <col width="65%">
                                        </colgroup>
                                        <tbody>
                                        <%if(Utility.isSet(orderContactName)) { %>
                                        <tr>
                                            <td>
                                                <app:storeMessage key="shop.orderStatus.text.placedBy" />
                                            </td>
                                            <td>                                                
                                                <%=Utility.encodeForHTML(orderContactName) %><br>
                                                <% if (Utility.isSet(orderContactPhoneNum)) { %>
                                                    <%=Utility.encodeForHTML(orderContactPhoneNum) %><br>
                                                <% } %>
                                                <% if (Utility.isSet(orderContactEmail)) { %>
                                                    <%=Utility.encodeForHTML(orderContactEmail) %>
                                                <%}%>
                                                <%if(isCheckOutPanel) { %>
                                                	<html:hidden name="esw.CheckOutEswForm" property="checkOutForm.orderContactName" value="<%=orderContactName%>" />
                                                	<html:hidden name="esw.CheckOutEswForm" property="checkOutForm.orderContactPhoneNum" value="<%=orderContactPhoneNum%>" />
                                                	<html:hidden name="esw.CheckOutEswForm" property="checkOutForm.orderContactEmail" value="<%=orderContactEmail%>" />
                                                <%} %>
                                            </td>
                                        </tr>
                                        <%} %>
                                        <%//Customer Comments.
                                        if(isCheckOutPanel) {
	                                        if (allowAccountComments && user.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ADD_CUSTOMER_ORDER_NOTES)){%>
											<tr>
											    <td colspan="2">
													<app:storeMessage key="userportal.esw.checkout.text.customerComments" /><br><br>
													<html:textarea name="esw.CheckOutEswForm" property="checkOutForm.customerComment" />
												</td>
											</tr>
										<%	}
                                        } %>											
									</tbody></table>
								</div>
							</div>
							<% if(!isCheckOutPanel) { %>
							<% if ( allowAccountComments &&
							         user.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ADD_CUSTOMER_ORDER_NOTES) &&
							         newCheckOutForm.getCheckOutForm().getCustomerComment()!= null &&
							         newCheckOutForm.getCheckOutForm().getCustomerComment().trim().length() > 0) {%>
								<hr>
								<div class="twoColBox">
									<div class="column width80">
										<table>
	                                        <colgroup>
	                                        </colgroup><colgroup>
	                                            <col>
												<col>
											</colgroup>  									
											<tbody>
												<tr>														
									                <td colspan="2"><app:storeMessage key="userportal.esw.checkout.text.customerComments" />
									                </td>
											    </tr>
											    <tr>
													<td><a class="userNameDisabled" href="#">
													<%=user.getUser().getUserName() %> <br>
													<%=ClwI18nUtil.formatDateInp(request,new Date())%> 
													</a>                                          
		                                            </td>
													<td>
														<bean:write name="esw.CheckOutEswForm" property="checkOutForm.customerComment"/>
		                                            </td>
												</tr>
	                                    </tbody>
                                    </table>
									</div>
								</div>
							   <%} %>
							<%} %>
                        </div>
					</div>
                    <div class="bottom clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
                </div>