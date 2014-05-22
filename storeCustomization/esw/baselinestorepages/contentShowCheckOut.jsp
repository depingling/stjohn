

<%@page import="com.cleanwise.view.utils.ShopTool"%>
<%@page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
<%@page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@page import="com.cleanwise.view.utils.Constants"%>
<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<bean:define id="newCheckOutForm" name="esw.CheckOutEswForm"  type="com.espendwise.view.forms.esw.CheckOutForm"/>

<%
//TODO - implement this page.  The HTML is basically set, but things like converting input
//to use struts tag libs, implementing links, etc all has to be done.  Best approach is to go
//through entire page line by line and implement whatever hasn't been done.


	String placeOrder = "userportal/esw/checkout.do"; 
	
    StringBuilder placeOrderLink = new StringBuilder(50);
	placeOrderLink.append(placeOrder);
    /*placeOrderLink.append("?");
    placeOrderLink.append(Constants.PARAMETER_OPERATION);
    placeOrderLink.append("=");
    placeOrderLink.append(Constants.PARAMETER_OPERATION_VALUE_PLACE_ORDER);*/
    
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
			String shoppingTabPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "shoppingTabs.jsp");
		%>
        <jsp:include page="<%=shoppingTabPage%>"/>
      	<!-- End: Shopping Sub Tabs -->
        <div class="singleColumn clearfix" id="contentWrapper">
            <div id="content">
			<html:form styleId="checkOutFormId" action="<%=placeOrderLink.toString() %>">
          			<html:hidden property="operation" styleId="operationId" />
          			<html:hidden property="orderFee" styleId="orderFeeId" />
          			
				<div>
                    <h1 class="main">
                    <app:storeMessage key="global.action.label.checkout" />
                    </h1>
                    <%
					
						boolean showPlaceOrderButton = true;
						if ( ShopTool.isInventoryShoppingOn(request)) {

							if (ShopTool.isModernInventoryShopping(request)) {
								showPlaceOrderButton = true;
							} else {
								showPlaceOrderButton = false;
							}
						}
						if (showPlaceOrderButton && newCheckOutForm.getPaymentTypeChoices().size() == 0){
							showPlaceOrderButton = false;
						}
						%>
						<%if(showPlaceOrderButton && ShopTool.hasDiscretionaryCartAccessOpen(request)) {%>
						<logic:notEqual name="esw.CheckOutEswForm" property="checkOutForm.itemsSize" value="0">
						<a onclick="javascript:setFieldsAndSubmitForm('checkOutFormId','operationId','<%=Constants.PARAMETER_OPERATION_VALUE_PLACE_ORDER  %>')" 
							class="blueBtnLargeExt">
							<span> <app:storeMessage key="global.action.label.placeOrder" /> </span>
						</a>
						</logic:notEqual>
					<%
						}
					 %>					
                </div>

<% 

if (ShopTool.hasDiscretionaryCartAccessOpen(request)) {
%> 
<logic:notEqual name="esw.CheckOutEswForm" property="checkOutForm.itemsSize" value="0">
                <!-- Start Box -->
<!-- Begin: Order Information  -->
				<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,Constants.PORTAL_ESW,"orderInfoPanel.jsp")%>' >
					<jsp:param value="true" name="checkOutPanel"/>
				</jsp:include>
<!-- End: Order Information  -->
                <!-- End Box -->                
				
                <!-- Start Box -->
                
<!-- Begin: Location Budget -->              
					 <%
						 String locationBudget = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "dashboardLocationBudget.jsp");
					 %>
               	 	<jsp:include page="<%=locationBudget %>" />
 <!-- End: Location Budget -->   
                
				
                <!-- End Box -->
            
                
				
				<!-- Start Box -->
<!-- Begin: Cart Items with Distributor Infomation -->
					<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,Constants.PORTAL_ESW,"itemWithDistributorInfoPanel.jsp")%>' />
<!-- End: Cart Items with Distributor Infomation -->
                <!-- End Box -->
				
				<!-- Start Box -->
<!-- Begin: Shipping and Billing Information --> 

					<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,Constants.PORTAL_ESW,"shippingAndBillingInfoPanel.jsp")%>' >
						<jsp:param value="true" name="checkOutPanel"/>
					</jsp:include>
              		
<!-- End: Shipping and Billing Information --> 
                <!-- End Box -->
                
				
				<!-- Start Box -->
<!-- Begin: Credit Card Information --> 

					<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,Constants.PORTAL_ESW,"creditCardInfoPanel.jsp")%>' >
						<jsp:param value="true" name="checkOutPanel"/>
					</jsp:include>
              		
<!-- End: Credit Card Information --> 
                <!-- End Box -->
				
				<!-- Start Box -->
                <div class="boxWrapper noBackground">
                    <div class="content">
                        <div class="right clearfix">
	                        <%if(showPlaceOrderButton) {%>
	                          	<a onclick="javascript:setFieldsAndSubmitForm('checkOutFormId','operationId','<%=Constants.PARAMETER_OPERATION_VALUE_PLACE_ORDER  %>')" 
									class="blueBtnLargeExt">
									<span> <app:storeMessage key="global.action.label.placeOrder" /> </span>
								</a>
							<%} %>
						</div>
                    </div>
                </div>
</logic:notEqual> 
<%} //End: if Discretionary Cart Access is open
%>    
          </html:form>      
                <!-- End Box -->
    
            </div>
        </div> 
                