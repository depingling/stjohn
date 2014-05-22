<%@page import="com.cleanwise.service.api.value.OrderStatusDescData"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.cleanwise.service.api.value.OrderPropertyData"%>
<%@page import="com.cleanwise.service.api.value.IdVector"%>
<%@page import="com.espendwise.view.forms.esw.DashboardForm"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="com.cleanwise.service.api.util.RefCodeNames"%>
<%@page import="com.cleanwise.view.utils.ShopTool"%>
<%@page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@page import="java.util.List"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.cleanwise.service.api.util.Utility"%>
<%@page import="com.cleanwise.service.api.value.OrderAddressData"%>
<%@page import="com.cleanwise.service.api.value.AddressData"%>
<%@page import="com.cleanwise.view.utils.Constants"%>
<%@page import="com.cleanwise.view.utils.ClwCustomizer"%>

<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri='/WEB-INF/eswI18n.tld' prefix='eswi18n' %>

<bean:define id="myForm" name="esw.DashboardForm"  type="com.espendwise.view.forms.esw.DashboardForm"/>

<%
	final String COMMENT_FORM = "commentForm";
	final String COMMENT_FORM_ORDERID = "commentFormOrderId";
	final String COMMENT_FORM_COMMENT = "commentFormComment";
	final String COMMENT_ENTRY_CONTROL = "commentEntryControl"; 
	final String COMMENT_CONTAINER = "commentContainer";
	final String COMMENT_SAVE_LINK = "commentSaveLink";

    final int COMMENT_MAX_DISPLAY_LENGTH = 120;
    final String ELLIPSIS = "...";
    
    String approvalDate = myForm.getApprovalDate();
    if (!Utility.isSet(approvalDate)) {
    	approvalDate = ClwI18nUtil.getDatePattern(request);
    }
    //boolean disableOrderActionControls = !myForm.isFoundApprovablePendingOrder();
    boolean disableOrderActionControls = false;
    
    ArrayList pendingOrders = (ArrayList)myForm.getPendingOrderSearchInfo().getMatchingOrders();
	if (Utility.isSet(pendingOrders)) {
		Iterator<OrderStatusDescData> pendingOrderIterator = pendingOrders.iterator();
		while (pendingOrderIterator.hasNext()) {
			OrderStatusDescData pendingOrder = pendingOrderIterator.next();
			 if(!Utility.isSet(pendingOrder.getUserApprovableReasonCodeIds())){
				 disableOrderActionControls = true;
				 break;
			 }
			 else{
				 disableOrderActionControls = false;
			 }
		}
	}
	CleanwiseUser user = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
	//boolean accountHasFiscalCalendar = ShopTool.doesAnySiteSupportsBudgets(request);
	// check if orders accounts support budgets
	   boolean supportsBudgets = false;
	   if(myForm.getPendingOrderSearchInfo().getMatchingOrders() != null && myForm.getPendingOrderSearchInfo().isOrdersAccountsSupportBudget()){
		supportsBudgets = true;
	   }
	   
	String approveOrRejectOrdersLink = "userportal/esw/dashboard.do";
	StringBuilder showOrderLink = new StringBuilder(50);
	showOrderLink.append("showOrderDetail.do?"); 
	showOrderLink.append(Constants.PARAMETER_OPERATION);
	showOrderLink.append("=");
	showOrderLink.append(Constants.PARAMETER_OPERATION_VALUE_SHOW_ORDER);
	showOrderLink.append("&");
	showOrderLink.append(Constants.PARAMETER_ORDER_ID);
	showOrderLink.append("=");
	
	String pendingOrdersLimit = "50";
%>
<script type="text/javascript">


function rejectOrders() {
	document.getElementById("orderFormOperation").value = '<%=Constants.PARAMETER_OPERATION_VALUE_REJECT_ORDERS%>';
	submitForm('orderForm');
}
function approveOrder(orderId,chkBoxIndex) {
	 $('body').append('<div id="coverLayer">&nbsp;</div>');
     $('body').append('<div class="popUpWindow popUpSmall"><div class="popUpTop">&nbsp;</div><div class="popUpContent clearfix"><p>' +
    singleOrderApproval + '</p><div class="buttonRow"><a href="#" class="blueBtnLarge closePopUpBtn"><span>' 
    + (singleOrderApproval == noOrderSelected ? saveBtnText : cancelBtnText) + 
    '</span></a><a href="javascript:approveIndividualOrder(\''+ orderId +'\',\''+chkBoxIndex+'\')" class="blueBtnLarge ' 
    + ("*" == "#" ? 'hide' : '') + '" ><span>' + saveBtnText + '</span></a></div></div>');
  // Small pop ups do not require loading
     loadingPopUp = false;

     // Bind the click function to the pop up's close button
     $('div.popUpWindow a.closePopUpBtn').bind('click', closePopUp);
     // for ie6 hide select menus which will show through overlay
     $('body').addClass('hideSelects');
     // show popUp Instantly
     $('div#coverLayer').css('visibility', 'visible');
     $('div.popUpWindow').css('visibility', 'visible');
     // run resize script to center popUp
     setWindowSize();
}

function rejectOrder(orderId,chkBoxIndex) {
	 $('body').append('<div id="coverLayer">&nbsp;</div>');
     $('body').append('<div class="popUpWindow popUpSmall"><div class="popUpTop">&nbsp;</div><div class="popUpContent clearfix"><p>' +
     singleOrderRejection + '</p><div class="buttonRow"><a href="#" class="blueBtnLarge closePopUpBtn"><span>' 
    + (singleOrderRejection == noOrderSelected ? saveBtnText : cancelBtnText) + 
    '</span></a><a href="javascript:rejectIndividualOrder(\''+ orderId +'\',\''+chkBoxIndex+'\')" class="blueBtnLarge ' 
    + ("*" == "#" ? 'hide' : '') + '" ><span>' + saveBtnText + '</span></a></div></div>');
  // Small pop ups do not require loading
     loadingPopUp = false;

     // Bind the click function to the pop up's close button
     $('div.popUpWindow a.closePopUpBtn').bind('click', closePopUp);
     // for ie6 hide select menus which will show through overlay
     $('body').addClass('hideSelects');
     // show popUp Instantly
     $('div#coverLayer').css('visibility', 'visible');
     $('div.popUpWindow').css('visibility', 'visible');
     // run resize script to center popUp
     setWindowSize();
}

function approveIndividualOrder(orderId,chkBoxIndex) {
	var checkBoxes = document.getElementsByName('selectedOrderIds');
	for(var i = 0;i < checkBoxes.length;i++){
		if(chkBoxIndex != i){
			checkBoxes[i].checked = 0;
		}else{
			checkBoxes[i].checked = 1;
		}
	}
	document.getElementById("orderFormOperation").value = '<%=Constants.PARAMETER_OPERATION_VALUE_APPROVE_ORDERS%>';
	document.getElementById("selectedOrderId").value = orderId;
	submitForm('orderForm');
}
function rejectIndividualOrder(orderId,chkBoxIndex) {
	var checkBoxes = document.getElementsByName('selectedOrderIds');
	for(var i = 0;i < checkBoxes.length;i++){
		if(chkBoxIndex != i){
			checkBoxes[i].checked = 0;
		}else{
			checkBoxes[i].checked = 1;
		}
	}
	document.getElementById("orderFormOperation").value = '<%=Constants.PARAMETER_OPERATION_VALUE_REJECT_ORDERS%>';
	document.getElementById("selectedOrderId").value = orderId;
	submitForm('orderForm');	
}

</script>
				<div class="actionBar actionNav">
	                <h2>
	                		<app:storeMessage key= "mobile.esw.orders.label.pendingOrders"/>
	                </h2>
        	    </div>
		  
               <%
					String errorsAndMessagesPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "errorsAndMessages.jsp");
				%>
                <p><jsp:include page="<%=errorsAndMessagesPage %>"/></p>

          
			
		  <ul class="navigation clearfix">
                <li class="selected">
                	<a href="dashboard.do?operation=">
                		<app:storeMessage key= "mobile.esw.orders.label.pendingOrders"/>
                	</a>
                </li>
                <li>
                	<a href="orders.do?operation=">
                		<app:storeMessage key= "mobile.esw.orders.label.orders"/>
                	</a>
                </li>
          </ul> 
          <hr />

               <logic:notEmpty name="esw.DashboardForm" property="pendingOrderSearchInfo.matchingOrders">
			<div class="actionBar clearfix">
                <p class="clearfix">
                
                    <span class="selectNav"><app:storeMessage key="global.action.label.select"/>: &nbsp;&nbsp;
                   <% if (!disableOrderActionControls) { %> 
	              		<a href="#" class="all">
	 	            			<app:storeMessage key="mobile.esw.orders.label.all"/>
	     	        		</a> 
	         	    		<span>
	             				|
	             			</span> 
	             			<a href="#" class="none">
	             				<app:storeMessage key="mobile.esw.orders.label.none"/>
	              		</a>
	              	<% } else { %>
	              				<app:storeMessage key="mobile.esw.orders.label.all"/>
	              			<span>
	             				|
	             			</span> 
	              				<app:storeMessage key="mobile.esw.orders.label.none"/>
	              	<% } %>
                    </span>  
                       <%
                  		StringBuilder styleForApproveBtn = new StringBuilder(50);
                    	styleForApproveBtn.append("blueBtnMed");
                  		if (!disableOrderActionControls) {
                  			styleForApproveBtn.append(" approveBtn");
                  		}
                  		else {
                  			styleForApproveBtn.append(" blueBtnMedDisabled");
                  		}
                  		
                  		StringBuilder styleForRejectBtn = new StringBuilder(50);
                  		styleForRejectBtn.append("blueBtnMed");
                  		if (!disableOrderActionControls) {
                  			styleForRejectBtn.append(" rejectBtn");
                  		}
                  		else {
                  			styleForRejectBtn.append(" blueBtnMedDisabled");
                  		}
		            %>
		            <% if (!disableOrderActionControls) {  %>
	                    <a href="javascript:approveOrders()" class="<%= styleForApproveBtn.toString()%>" ><span><app:storeMessage key="global.action.label.approve"/></span></a> 
	                    <a href="javascript:rejectOrders()" class="<%= styleForRejectBtn.toString()%>"><span><app:storeMessage key="global.action.label.reject"/></span></a>
                    <% } else {  %>
                     	<a href="#"  class="<%= styleForApproveBtn.toString()%>" ><span><app:storeMessage key="global.action.label.approve"/></span></a>  
 	                    <a href="#"  class="<%= styleForRejectBtn.toString()%>"><span><app:storeMessage key="global.action.label.reject"/></span></a>                   	

	                <%  }  %>
					                    
                     
                </p>
            </div>
            </logic:notEmpty>                  	
            
            <html:form styleId="orderForm" action="<%=approveOrRejectOrdersLink%>">
                    <html:hidden styleId="orderFormOperation" property="<%=Constants.PARAMETER_OPERATION%>"/>
                   
                                 		
            <table class="noBorder">
                <tbody>
                    <!-- Repeating Row - needs two rows for each entry to accomodate the details line which needs colspan -->
                    <logic:notEmpty name="esw.DashboardForm" property="pendingOrderSearchInfo.matchingOrders">
				         <logic:iterate id="order" indexId="orderIndex"
				                    	name="esw.DashboardForm" property="pendingOrderSearchInfo.matchingOrders" 
				                        type="com.cleanwise.service.api.value.OrderStatusDescData"
				                        length="<%= pendingOrdersLimit%>">
                    <tr class="noBorder">
                        <td class="select">
                        	<label class="chkBox">
                        	
                        			<html:multibox property="selectedOrderIds" value="<%=Integer.toString(order.getOrderDetail().getOrderId())%>" styleId="selectedOrderId"/>
                        	</label>
                        	
                        </td>
                        <%
                   		AddressData address;
                   		String locationName;
               			OrderAddressData oad = order.getShipTo();
                   		if (oad != null) {
                   			address = Utility.toAddress(oad);
                   			locationName = Utility.encodeForHTML(oad.getShortDesc());
                   		}
  	                	else {
   	                		address = AddressData.createValue();
       	            		locationName = StringUtils.EMPTY;
              	    	}
                   		String address1 = Utility.encodeForHTML(address.getAddress1());
                   		String city = Utility.encodeForHTML(address.getCity());
                   		String state = "";
                       	if (user.getUserStore().getCountryProperties() != null) {
                    	    if (Utility.isTrue(Utility.getCountryPropertyValue(
                    	    		user.getUserStore().getCountryProperties(), 
                    	    		RefCodeNames.COUNTRY_PROPERTY.USES_STATE))) {
                    	    	state = Utility.encodeForHTML(address.getStateProvinceCd());
                    	    }
                    	}
                       	String postalCode = Utility.encodeForHTML(address.getPostalCode());
                       	String addressFormat = Utility.getAddressFormatFor(address.getCountryCd());
                   		
             	   		/* List<String> addressShort = ClwI18nUtil.formatAddressShort(address, 
                					user.getUserStore().getCountryProperties(), locationName);
                    		StringBuilder addressString = new StringBuilder(100);
                     	Iterator<String> iterator = addressShort.iterator();
                     	while (iterator.hasNext()) {
                     		addressString.append(Utility.encodeForHTML(iterator.next()));
                     		addressString.append("<br>");
                     	} */
                  %>
                        <td>
                           <%--  <%= addressString.toString() %> --%>
                           <eswi18n:formatAddress name="<%=locationName %>" address1="<%=address1 %>" postalCode="<%=postalCode %>"  
                                			  city="<%=city %>" state="<%=state %>" 
                                			  addressFormat="<%=addressFormat %>"/> 
                        </td>
                    </tr>
                    <tr class="details">
                        <td colspan="2">
                            <p><strong><app:storeMessage key="mobile.esw.orders.label.order#"/>:</strong> 
                             <%
	                  			String orderStatus = order.getOrderDetail().getOrderStatusCd();
	                  			boolean orderHasBeenProcessed = (orderStatus != null && !orderStatus.equals(RefCodeNames.ORDER_STATUS_CD.ORDERED_PROCESSING));
	                  			if (orderHasBeenProcessed) {
	                   			String orderLink = showOrderLink.toString() + order.getOrderDetail().getOrderId();
                        	%>
                            <html:link href="<%=orderLink%>">
        	                     <bean:write name="order" property="orderDetail.orderNum"/>
            	            </html:link>
                       		<%	
                       		  }
                       		  else {
                       		%>
        	                    <bean:write name="order" property="orderDetail.orderNum"/>
							<%
				              }
							%>
                            <span class="alert">&nbsp; 
                            		 <%  String style = "";
			                                  if (RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL.equals(orderStatus) ||
			                                  		RefCodeNames.ORDER_STATUS_CD.PENDING_DATE.equals(orderStatus) ||
			                                           RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION.equals(orderStatus)) {
			                                    style = "alert";
			                                  }
                                     %>	
								<span class="<%=style%>">(<%=Utility.encodeForHTML(ShopTool.xlateStatus(order, request))%>)</span>
								<%
									//if the order status is "Pending Date", show the date that the order
									//will be submitted
									if (RefCodeNames.ORDER_STATUS_CD.PENDING_DATE.equals(orderStatus) &&
											Utility.isSet(order.getPendingDate())) {
											Date processOnDate = new SimpleDateFormat("MM/dd/yyyy").parse(order.getPendingDate());
								%>
									<span class="<%=style%>">
										<br>(<%=Utility.encodeForHTML(ClwI18nUtil.formatDateInp(request, processOnDate))%>)
                                    </span>
								<% 
									}
									if (RefCodeNames.ORDER_STATUS_CD.CANCELLED.equals(orderStatus) &&
          														order.getConsolidatedOrder()!= null) { 
          						%>
										<app:storeMessage key= "shop.orderStatus.text.consolidated"/>
								<% 
									} 
								%>
                            </span>
                            <br />
                            <strong><app:storeMessage key= "mobile.esw.orders.label.order.ordered"/></strong>
                            <logic:notEmpty name="order" property="orderDetail.revisedOrderDate">
		                                    	                	<%=Utility.encodeForHTML(ClwI18nUtil.formatDateInp(request, order.getOrderDetail().getRevisedOrderDate()))%>
		                                        	            </logic:notEmpty>
                                                   				<logic:empty name="order" property="orderDetail.revisedOrderDate">
		                                                	    	<%=Utility.encodeForHTML(ClwI18nUtil.formatDateInp(request, order.getOrderDetail().getOriginalOrderDate()))%>
		                                                    	</logic:empty> 
                            &nbsp;&nbsp; 
                            <strong><app:storeMessage key= "mobile.esw.orders.label.order.po#"/>:</strong>
                            <%= 
                            	(order.getOrderDetail().getRequestPoNum() != null) ? order.getOrderDetail().getRequestPoNum() : ""
                            %>
                             <br />
                            <strong><app:storeMessage key= "mobile.esw.orders.label.orderTotal"/>:</strong>
                            <%
	            				if (orderHasBeenProcessed && user.getShowPrice()) { 
                    		%>
    		                	<app:formatPriceCurrency price="<%=order.getEstimatedTotal()%>"
														 orderId="<%=order.getOrderDetail().getOrderId()%>"/>
                            <% } %>                                          
                             &nbsp;&nbsp; 
                             <% 
                             	if(supportsBudgets) {
                             %>
                             <strong><app:storeMessage key= "mobile.esw.orders.label.unspentBudget"/>:</strong>
                             <span>
                              	<%
	                                BigDecimal remainingBudget = order.getOrderSiteData().getTotalBudgetRemaining();
	                                if (remainingBudget!= null && !remainingBudget.equals(BigDecimal.ZERO)) {
		                        %>
                                    <app:formatPriceCurrency price="<%=remainingBudget%>"
                                                             orderId="<%=order.getOrderDetail().getOrderId()%>"
                                                             checkNegative="true"
                                                             negativeClass="negative"/>
                                <%
	                                }
                                %>
                              </span>
								<% }  %>
                            <br />
							
							<logic:notEmpty name="order" property="orderPropertyList">
								   <logic:iterate id="orderProperty" indexId="orderPropertyIndex"
								   				  name="order" property="orderPropertyList" 
								                  type="com.cleanwise.service.api.value.OrderPropertyData">
									<logic:equal name="orderProperty" property="shortDesc" value="<%=Constants.WORKFLOW_NOTE%>">
									 <%
										String classString = "";
											//if the reason hasn't been approved and is one the user is authorized to handle,
											//show it as such
											if (orderProperty.getApproveDate() == null &&
													order.getUserApprovableReasonCodeIds().contains(orderProperty.getOrderPropertyId())) {
												classString = "class=\"reasonCode\"";
											}
									 %>
                                    <span <%= classString%>>
                                    <logic:notEmpty name="orderProperty" property="approveDate">
                                    	<%=ClwI18nUtil.formatDateInp(request, orderProperty.getApproveDate())%>:
                                    </logic:notEmpty>
									<%
							        	String messageKey = orderProperty.getMessageKey();
									
							        	if (!Utility.isSet(messageKey)) {
							        %>
							        	<%=orderProperty.getValue()%>
							        <%
							        	} 
							        	else { 
							        %>
								        <app:storeMessage key="<%=messageKey%>"
								          arg0="<%=orderProperty.getArg0()%>"
								          arg0TypeCd="<%=orderProperty.getArg0TypeCd()%>"
								          arg1="<%=orderProperty.getArg1()%>"
								          arg1TypeCd="<%=orderProperty.getArg1TypeCd()%>"
								          arg2="<%=orderProperty.getArg2()%>"
								          arg2TypeCd="<%=orderProperty.getArg2TypeCd()%>"
								          arg3="<%=orderProperty.getArg3()%>"
								          arg3TypeCd="<%=orderProperty.getArg3TypeCd()%>"/>
									<%
							        	}
									%>
									<logic:notEmpty name="orderProperty" property="approveDate">
										<logic:notEmpty name="orderProperty" property="modBy">
                                            						(<%=orderProperty.getModBy()%>)
                                        </logic:notEmpty>
                                    </logic:notEmpty>
                                      
                                      <br/>
                                      </span>
									 </logic:equal>
									</logic:iterate>
								  
						</logic:notEmpty>
					
				
																
                            	                       				<logic:notEmpty name="order" property="orderPropertyList">
										                        		<logic:iterate id="comment" indexId="commentIndex"
										                        		 	name="order" property="orderPropertyList" 
									    	                    		 	type="com.cleanwise.service.api.value.OrderPropertyData">
																			<logic:equal name="comment" property="orderPropertyTypeCd" value="<%=RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_CART_COMMENTS%>">
										        	                		 	<%
										            	                			//if the comment size exceeds the max number of characters to display then trim it and 
										                	            			//append "..."
							                            							if (Utility.isSet(comment.getValue()) && comment.getValue().length() > COMMENT_MAX_DISPLAY_LENGTH) {
							                            								comment.setValue(comment.getValue().substring(0, COMMENT_MAX_DISPLAY_LENGTH) + ELLIPSIS);
							                            							}
										                        			 	%>
										                        		 		<%
											                            			//NOTE: HTML formatting here must be kept in synch with HTML formatting above when rendering a newly
											                            			//added comment.
											                        		 	%>
																				
																					<%=Utility.encodeForHTML(ClwI18nUtil.formatDateInp(request, comment.getAddDate()))%>
																					&nbsp;<bean:write name="comment" property="value"/>
																					&nbsp;(<strong><bean:write name="comment" property="addBy"/></strong>)
											                        		 	<br/>
											                        		 </logic:equal>
										                        		</logic:iterate>
	                                	                   			</logic:notEmpty>
									    	                    
						</p>
			
							
							<p class="clearfix">


			<% 	
				StringBuffer styleForDisableBtn = new StringBuffer("smallBtn");
          		if (disableOrderActionControls) {
          			styleForDisableBtn.append(" blueBtnMedDisabled");
          		}
				if (!disableOrderActionControls) { %>
						<a href="javascript:approveOrder('<%= order.getOrderDetail().getOrderId()%>','<%= orderIndex%>')" class="<%=styleForDisableBtn%>" >
							<span><app:storeMessage key="global.action.label.approve"/></span>
						</a>
						<a href="javascript:rejectOrder('<%= order.getOrderDetail().getOrderId()%>','<%= orderIndex%>')" class="<%=styleForDisableBtn%>">
								<span><app:storeMessage key="global.action.label.reject"/></span>
						</a>
			   <%	} else {
				%>
						<a href="#" class="<%=styleForDisableBtn%>" >
							<span><app:storeMessage key="global.action.label.approve"/></span>
						</a>
						<a href="#" class="<%=styleForDisableBtn%>" >
							<span><app:storeMessage key="global.action.label.reject"/></span>
						</a>
			   <% } %>
							</p> 
                        </td>
                        
                        
                    </tr>
                    	</logic:iterate>
                    </logic:notEmpty>
                    <!-- End Repeating Row -->
                    <!-- Repeating Row - needs two rows for each entry to accomodate the details line which needs colspan -->
                </tbody>
            </table>
            </html:form>
			
		  <logic:notEmpty name="esw.DashboardForm" property="pendingOrderSearchInfo.matchingOrders">
			<div class="actionBar lowerActions clearfix">
                <p class="clearfix">
                    <span class="selectNav"><app:storeMessage key="global.action.label.select"/>:&nbsp;&nbsp;
                       <% if (!disableOrderActionControls) { %> 
	              		<a href="#" class="all">
	 	            			<app:storeMessage key="mobile.esw.orders.label.all"/>
	     	        		</a> 
	         	    		<span>
	             				|
	             			</span> 
	             			<a href="#" class="none">
	             				<app:storeMessage key="mobile.esw.orders.label.none"/>
	              		</a>
	              		<% } else { %>
	              				<app:storeMessage key="mobile.esw.orders.label.all"/>
	              			<span>
	             				|
	             			</span> 
	              				<app:storeMessage key="mobile.esw.orders.label.none"/>
	              		<% } %>
                  </span>  
                     <%
                  		StringBuilder styleForApproveBtn = new StringBuilder(50);
                    	styleForApproveBtn.append("blueBtnMed");
                  		if (!disableOrderActionControls) {
                  			styleForApproveBtn.append(" approveBtn");
                  		}
                  		else {
                  			styleForApproveBtn.append(" blueBtnMedDisabled");
                  		}
                  		
                  		StringBuilder styleForRejectBtn = new StringBuilder(50);
                  		styleForRejectBtn.append("blueBtnMed");
                  		if (!disableOrderActionControls) {
                  			styleForRejectBtn.append(" rejectBtn");
                  		}
                  		else {
                  			styleForRejectBtn.append(" blueBtnMedDisabled");
                  		}
		            %>
		            <% if (!disableOrderActionControls) {  %>
	                    <a href="javascript:approveOrders()" class="<%= styleForApproveBtn.toString()%>" ><span><app:storeMessage key="global.action.label.approve"/></span></a> 
	                    <a href="javascript:rejectOrders()" class="<%= styleForRejectBtn.toString()%>"><span><app:storeMessage key="global.action.label.reject"/></span></a>
                    <% } else {  %>
                    	<a href="#"  class="<%= styleForApproveBtn.toString()%>" ><span><app:storeMessage key="global.action.label.approve"/></span></a> 
	                    <a href="#"  class="<%= styleForRejectBtn.toString()%>"><span><app:storeMessage key="global.action.label.reject"/></span></a>
	                <%  }  %>
                </p>
            </div>
          </logic:notEmpty>
          <div class="footer">
        <p><a href="dashboard.do?operation=viewWebsite" ><app:storeMessage key="mobile.esw.orders.label.visitFullWebsite"/></a></p>   			 

