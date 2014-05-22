<%@page import="com.cleanwise.service.api.value.OrderItemActionData"%>
<%@page import="com.cleanwise.service.api.value.OrderItemActionDataVector"%>
<%@page import="com.cleanwise.service.api.util.RefCodeNames"%>
<%@page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.TimeZone"%>
<%@page import="com.cleanwise.view.utils.ShopTool"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.cleanwise.service.api.value.OrderStatusDescData"%>
<%@page import="com.cleanwise.view.utils.Constants"%>
<%@page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.cleanwise.service.api.util.Utility"%>
<%@page import="com.cleanwise.service.api.value.OrderAddressData"%>
<%@page import="com.cleanwise.service.api.value.AddressData"%>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/eswI18n.tld' prefix='eswi18n' %>

<bean:define id="theForm" name="esw.OrdersForm" type="com.espendwise.view.forms.esw.OrdersForm"/>
<bean:define id="workflowRole" type="java.lang.String" name="ApplicationUser" property="user.workflowRoleCd"/>
<script language="JavaScript1.2">

function submitForm(operation) {
	var frm = document.forms['orderForm'];
	if (frm) {
		frm.elements['<%=Constants.PARAMETER_OPERATION%>'].value = operation;
		frm.submit();
	}
}

function viewWebsite(){
	document.getElementById('viewWebsiteOrderDetailForm').submit();
}
</script>
<%! 
	public Date getLatestReceivedDate(OrderItemActionDataVector oitad) {
	Date latestReceivedDate = null;
	Date actionDate = null;
	String receivedLineStatus = null;
		for(int i = 0; oitad != null && i < oitad.size();i++) {
			OrderItemActionData item = (OrderItemActionData)oitad.get(i);
			if(RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.RECEIVED_AGAINST.equals(item.getActionCd())) {
				if(latestReceivedDate == null ){
					if(item.getActionDate() == null) {
						latestReceivedDate = item.getAddDate();
					} else {
						latestReceivedDate = item.getActionDate();
					}
				} else {
					actionDate = item.getActionDate();
					if(actionDate == null) {
						actionDate = item.getAddDate();
					}
					if(actionDate.after(latestReceivedDate)) {
						latestReceivedDate  = actionDate;
					}
				}
			}
		}
		return latestReceivedDate;
}
%>
<%
	CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);

	OrderStatusDescData orderStatusDetail = theForm.getOrderOpDetailForm().getOrderStatusDetail();
	int siteId = orderStatusDetail.getOrderSiteData().getSiteId();
	int accountId = orderStatusDetail.getOrderSiteData().getAccountId();
	HashMap orderBudgetInfo = ShopTool.getUnspentAmount(request,siteId,accountId);
	pageContext.setAttribute("order",orderStatusDetail);
	String orderLocale = orderStatusDetail.getOrderDetail().getLocaleCd();
	ClwI18nUtil clwI18n = ClwI18nUtil.getInstance(request,orderLocale,-1);
	boolean approverFl = (workflowRole.indexOf("APPROVER") >= 0) ? true : false;
	String requestPoNum = orderStatusDetail.getOrderDetail().getRequestPoNum();
	//If PO Num is null then display "" for PO # label
	requestPoNum = (requestPoNum != null) ? requestPoNum :"";
	
	TimeZone timeZone =  Utility.getTimeZoneFromUserData(appUser.getSite());
	  String approvalDate = theForm.getApprovalDate();
	  boolean reasonCodeFl = false;
	  if (!Utility.isSet(approvalDate)) {
			 Date dNow = new Date();
			 String patternAD  = ClwI18nUtil.getDatePattern(request);
			 SimpleDateFormat formatterAD = new SimpleDateFormat(patternAD);
			 approvalDate = formatterAD.format(dNow);
	  }
	  
	//Getting properly formatted Order Date: Begin
	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	   sdf.setTimeZone(timeZone);
	   Date date = orderStatusDetail.getOrderDetail().getOriginalOrderDate();
	   Date time = orderStatusDetail.getOrderDetail().getOriginalOrderTime();
	   Date orderDate = Utility.getDateTime(date, time);

	   //String formattedODate = ClwI18nUtil.formatDateInp(request, orderDate, timeZone.getID() );
	   //String formattedODate = ClwI18nUtil.formatDate(request,orderDate,DateFormat.FULL);
	   String formattedODate = Utility.encodeForHTML(ClwI18nUtil.formatDate(request, orderDate));
	   formattedODate = formattedODate.trim();  
	   //Getting  properly formatted Order Date: End
	   
	   
	   //Getting Revised Order Date: Begin
	   String formattedRevisedODate = "";
	   Date revisedOrderDate = orderStatusDetail.getOrderDetail().getRevisedOrderDate();
	   boolean equalDatesFl = false;
	   if (revisedOrderDate != null && !revisedOrderDate.equals("")) {
	      if (date.equals(revisedOrderDate)) {  
	    	  equalDatesFl = true;    	  
	      } else {
	    	   //Convert Revised Order Date to the proper format if it is different from the Original Order Date
	    	   Date revisedOrderTime = orderStatusDetail.getOrderDetail().getRevisedOrderTime();
	    	   Date revisedOrderFullDate = Utility.getDateTime(revisedOrderDate, revisedOrderTime);
	    	   //formattedRevisedODate = ClwI18nUtil.formatDateInp(request, revisedOrderFullDate, timeZone.getID());
	    	   //formattedRevisedODate = ClwI18nUtil.formatDate(request,revisedOrderFullDate,DateFormat.FULL);
	    	   formattedRevisedODate = Utility.encodeForHTML(ClwI18nUtil.formatDate(request, revisedOrderFullDate));
	    	   formattedRevisedODate = formattedRevisedODate.trim();
	      }
	   }
	    //Getting Revised Order Date: End
	  
%>
    <body>
    <div class="actionBar actionNav">
	                <a href="#" class="homeBtn"><app:storeMessage key="mobile.esw.orders.label.home"/></a>
	                <h2><app:storeMessage key="mobile.esw.orders.label.orderDetail"/></h2>
	            </div>
	            <%
					String errorsAndMessagesPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "errorsAndMessages.jsp");
				%>
                <p><jsp:include page="<%=errorsAndMessagesPage %>"/></p>
			<a href="backToOrders.do" class="btn bottomMargin rightMargin">
				<span><app:storeMessage key="mobile.esw.orders.label.backToOrders"/></span>
			</a>
			<hr />
              <html:form styleId="orderForm" name="esw.OrdersForm" action="userportal/esw/showOrderDetail.do"> 
                    <html:hidden name="esw.OrdersForm" property="operation" styleId="orderFormOperation"/>        
            <h2><app:storeMessage key="mobile.esw.orders.label.order#"/>: 
            	<bean:write name="theForm" property="orderOpDetailForm.orderStatusDetail.orderDetail.orderNum"/> (<%=com.cleanwise.view.utils.ShopTool.xlateStatus(orderStatusDetail,request)%>)
            </h2>
            <%
                   		AddressData address;
                   		String locationName;
               			OrderAddressData oad = orderStatusDetail.getShipTo();
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
                       	if (appUser.getUserStore().getCountryProperties() != null) {
                    	    if (Utility.isTrue(Utility.getCountryPropertyValue(
                    	    		appUser.getUserStore().getCountryProperties(), 
                    	    		RefCodeNames.COUNTRY_PROPERTY.USES_STATE))) {
                    	    	state = Utility.encodeForHTML(address.getStateProvinceCd());
                    	    }
                    	}
                       	String postalCode = Utility.encodeForHTML(address.getPostalCode());
                       	String addressFormat = Utility.getAddressFormatFor(appUser.getPrefLocale().getDisplayCountry().toUpperCase());
                   		
             	   		/* List<String> addressShort = ClwI18nUtil.formatAddressShort(address, 
                					appUser.getUserStore().getCountryProperties(), locationName);
                    		StringBuilder addressString = new StringBuilder(100);
                     	Iterator<String> iterator = addressShort.iterator();
                     	while (iterator.hasNext()) {
                     		addressString.append(Utility.encodeForHTML(iterator.next()));
                     		addressString.append("<br>");
                     	} */
             %>				
			<p><%-- <%= addressString.toString() %> --%>
				 <eswi18n:formatAddress name="<%=locationName %>" address1="<%=address1 %>" postalCode="<%=postalCode %>"  
                                			  city="<%=city %>" state="<%=state %>" 
                                			  addressFormat="<%=addressFormat %>"/> 
			</p>
            
			<p><strong><app:storeMessage key="mobile.esw.orders.label.created"/>: </strong> 
			<%=formattedODate%> &nbsp;&nbsp;
			<% 
			   if(!equalDatesFl && formattedRevisedODate.length() > 0) {
			%> 
				<strong><app:storeMessage key="mobile.esw.orders.label.revised"/>: </strong><%=formattedRevisedODate%> <br />
			<% } %>
			<strong><app:storeMessage key="mobile.esw.orders.label.order.po#"/>:</strong> <%=requestPoNum %> <br />
			<strong><app:storeMessage key="mobile.esw.orders.label.orderTotal"/>:</strong> 
				<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true"> 
				<bean:define id="total"  name="theForm" property="orderOpDetailForm.totalAmount"/>
	            <%=clwI18n.priceFormat(total,"&nbsp;")%> 
             </logic:equal><br />
            <strong><app:storeMessage key="mobile.esw.orders.label.placedBy"/>: </strong> 
            	 <%
          		String orderContactEmail = orderStatusDetail.getOrderDetail().getOrderContactEmail();
				if(orderContactEmail != null && orderContactEmail.trim().length() > 0) {          		
            %>
         
             <a href="mailto:<bean:write name="theForm" property="orderOpDetailForm.orderStatusDetail.orderDetail.orderContactEmail"/>">
             	<bean:write name="theForm" property="orderOpDetailForm.orderStatusDetail.orderDetail.orderContactName"/>
             </a> 
             <% } 
				else {  %>
             		<bean:write name="theForm" property="orderOpDetailForm.orderStatusDetail.orderDetail.orderContactName"/>
             <% }  %>&nbsp;&nbsp; 
            	<strong><app:storeMessage key="mobile.esw.orders.label.phone"/>: </strong>  
            	<%
             String orderContactPhoneNum = orderStatusDetail.getOrderDetail().getOrderContactPhoneNum();
				if(orderContactPhoneNum != null && orderContactPhoneNum.trim().length() > 0) {
             %>
             <a href="tel:<bean:write name="theForm" property="orderOpDetailForm.orderStatusDetail.orderDetail.orderContactPhoneNum"/>">
             	<bean:write name="theForm" property="orderOpDetailForm.orderStatusDetail.orderDetail.orderContactPhoneNum"/>
             </a>
             <% }  %><br />
          
			<br />
			<logic:iterate id="orderProperty" indexId="orderPropertyIndex"
								         name="theForm" property="orderOpDetailForm.orderPropertyList" 
								         type="com.cleanwise.service.api.value.OrderPropertyData">
                                         <% //Workflow properties %>
							             <logic:equal name="orderProperty" property="shortDesc" value="<%=Constants.WORKFLOW_NOTE%>">
							            
							             <%
											String classString = "";
											//if the reason hasn't been approved and is one the user is authorized to handle,
											//or the user is an Approver
											//show it as such in red ink
											if (approverFl ||  
											     (orderProperty.getApproveDate() == null &&
											     theForm.getNotesUserApproveIdV().contains(orderProperty.getOrderPropertyId()))) {
													classString = "class=\"reasonCode\"";
											}
											if (orderProperty.getApproveDate() == null &&
													   theForm.getNotesUserApproveIdV().contains(orderProperty.getOrderPropertyId())) {
														    reasonCodeFl = true;
													}
										 %>
										 <% if(orderProperty.getApproveDate() != null){
											 classString = "";
										 }
										 %>
        	                             <span <%=classString%>>   
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
								         </span>
								         <logic:notEmpty name="orderProperty" property="approveDate">
									       <logic:notEmpty name="orderProperty" property="modBy">
                						       (<%=orderProperty.getModBy()%>)
                					       </logic:notEmpty>
            					         </logic:notEmpty>
            					         <!-- Initially I put here a piece of code to show Approval Activity (new style) -->      
            					            
            					         &nbsp;
        					             <br/>
						                 </logic:equal>
						                 
						                 
                                        </logic:iterate>
            							<logic:present name="theForm" property="orderOpDetailForm.customerOrderNotes">
                                          <bean:size id="custNotesCt" name="theForm" property="orderOpDetailForm.customerOrderNotes"/>
                                          <logic:greaterThan name="custNotesCt" value="0">
                                            <logic:iterate name="theForm" property="orderOpDetailForm.customerOrderNotes" id="note" type="com.cleanwise.service.api.value.OrderPropertyData">

									        <bean:define id="commentDate" name="note" property="addDate"
                                               type="java.util.Date" />
                                               <%-- <i18n:formatDate value="<%=commentDate%>"  pattern="yyyy-MM-dd k:mm"/>--%>
                                               <%
	                                           Date comDate = commentDate;
	                                           String formattedComDate = ClwI18nUtil.formatDateInp(request, comDate, timeZone.getID() );
                                               %>	
                                               <%=formattedComDate%>  
                                                 <app:writeHTML name="note" property="value"/>
											  (<bean:write name="note" property="addBy"/>) 
											<br/>
                                            </logic:iterate>
                                          </logic:greaterThan>
                                        </logic:present></p>
                                        <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.RECEIVING%>">
											<a href="#" class="blueBtnMed bottomMargin right" 
								            	onclick="submitForm('<%=Constants.PARAMETER_OPERATION_VALUE_UPDATE_RECEIVE%>')">
								            		<span><app:storeMessage key="global.action.label.update"/></span>
								            </a>
            							</app:authorizedForFunction>
											                       
            <table class="productList">
                <thead>
                    <tr>
                        <th class="first"><app:storeMessage key="mobile.esw.orders.label.product"/></th>
                        <th class="qty"><app:storeMessage key="mobile.esw.orders.label.qty"/></th>
                        <th class="last"><app:storeMessage key="mobile.esw.orders.label.subtotal"/></th>
                    </tr>
                </thead>
                <tbody> <logic:iterate id="item" name="theForm" property="orderOpDetailForm.orderItemDescList"
                            offset="0" indexId="oidx" type="com.cleanwise.service.api.value.OrderItemDescData">
                            <bean:define id="qty" name="item" property="orderItem.totalQuantityOrdered" type="Integer"/>
                            <bean:define id="quantityEl" value="<%=\"quantityElement[\"+oidx+\"]\"%>"/>
							<bean:define id="itemIdsEl" value="<%=\"itemIdsElement[\"+oidx+\"]\"%>"/>
							<%BigDecimal custItemPrice=new BigDecimal(0.00);%>
							<logic:present  name="item" property="orderItem.custContractPrice">
                            	<%custItemPrice=item.getOrderItem().getCustContractPrice();%>
                            </logic:present>
                    <tr>
                        <td class="first"><strong><bean:write name="item" property="orderItem.itemShortDesc"/></strong><br />
                            	<strong><app:storeMessage key="mobile.esw.orders.label.sku"/>:</strong> 
                            	<%=ShopTool.getRuntimeSku(item.getOrderItem(),request)%><br />
								<strong><app:storeMessage key="mobile.esw.orders.label.price"/>: </strong>   
								<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">  
                                            <%=clwI18n.priceFormat(custItemPrice,"&nbsp;")%>
                                </logic:equal><br />
                                
                                  
                                 <!-- "Received" Column, configured on the Admin Portal: Begin -->
                                  <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.RECEIVING%>">
                                   <html:hidden property="hasFunctionReceiving" value="true" />
                                  <% String received = item.getItemQuantityRecvdS(); //orderItemDescList["+oidx+"].itemQuantityRecvdS; %>
                                   <%String prop = "orderOpDetailForm.orderItemDescList["+oidx+"].itemQuantityRecvdS"; %>
									<strong><app:storeMessage key="shop.orderdetail.table.header.received"/>:</strong> 
									<class="qty">
									
									<html:text value='<%=received%>' name="esw.OrdersForm" property="<%=prop%>"/><br />
								 </app:authorizedForFunction>   
								 <!-- Received Column, configured on the Admin Portal: End -->	
								  <% if(RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED.
								                         equals(item.getOrderItem().getOrderItemStatusCd())) { %>
														    <app:storeMessage key="shoppingItems.text.cancelled"/>
													  <% } %><br/>
								 <logic:present name="item" property="orderItemActionList">  
                                            <% int shippedQty = 0; 
                                             boolean substitutedFlag = false; 
                                             boolean shippedFlag = false; 
                                             Date latestReceivedDate = getLatestReceivedDate(item.getOrderItemActionList()); 
                                             	if(latestReceivedDate != null) {
                                             		String receivedAgainstStatusKey = Constants.ORDER_ITEM_ACTION_KEY + RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.RECEIVED_AGAINST;
                                             %>
                                             <%=Utility.encodeForHTML(ClwI18nUtil.formatDateInp(request, latestReceivedDate))%>: <app:storeMessage key="<%=receivedAgainstStatusKey %>" /><br/>
                                             <% } %>
                                            <logic:iterate id="action" name="item" property="orderItemActionList"
                                                    offset="0" indexId="aidx"
                                                    type="com.cleanwise.service.api.value.OrderItemActionData">
                                                     <% String actioncd = action.getActionCd();%> 
                                                     
                                            	<app:shouldDisplayOrderItemAction name="<%=actioncd%>">
                                            	<%  boolean showQuantity = true;
                                            		if(!RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.RECEIVED_AGAINST.equals(actioncd) || 
                                            				action.getQuantity() > 0) {
                                            		 
                                            		  
		                                              Integer actionqty = new Integer(action.getQuantity());
		                                            
		                                             
		                                              Date actiondate = Utility.getDateTime(action.getActionDate(), action.getActionTime()) ; 
		                                            
		                                              if(actiondate == null){
		                                                 actiondate = action.getAddDate();
		                                              }
		                                              if (null == actioncd) {
		                                                  actioncd = new String("");
		                                              }
		                                              if (RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.SUBSTITUTED.equals(actioncd) || null == actionqty) {
		                                                  substitutedFlag = true;
		                                              }
		                                              else if (RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.CUST_SHIPPED.equals(actioncd)) {
		                                                  shippedQty += actionqty.intValue();
		                                                  shippedFlag = true;
		                                              }
		                                             
		                                              if (RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.CUST_INVOICED.equals(actioncd)) {
		                                                    actioncd = "Invoiced";
		                                              }
		                                              String statusKey = Constants.ORDER_ITEM_ACTION_KEY + actioncd;
		                                            %>
		                                            
                                                 <%=Utility.encodeForHTML(ClwI18nUtil.formatDateInp(request, actiondate))%>: <app:storeMessage key="<%=statusKey%>" />  
                                                 <% if(showQuantity){ %>
                                                 	(<%= actionqty%>) 
                                                 <%  } else {  %>
                                                 	<%=""%> 
                                                 <% } %><br />
                                                 <% } %>
                                            	
                                            	</app:shouldDisplayOrderItemAction>                                                 
                                            </logic:iterate> 
								</logic:present>  		
							</td>
                        <td class="qty hide"><input type="text" value="1" /></td>
						<td><bean:write name="item" property="orderItem.totalQuantityOrdered"/></td>
                        <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">  
	                        <td class="last"><%=clwI18n.priceFormat(custItemPrice.multiply(new BigDecimal(qty.doubleValue())),"&nbsp;")%></td>
	                         </logic:equal>
                    </tr>
                    </logic:iterate>
                  
                </tbody>
                <tfoot>
                    <tr>
                        <td colspan="3">
                           <bean:define id="total"  name="theForm" property="orderOpDetailForm.totalAmount"/>
                            <app:storeMessage key="shop.orderdetail.table.summary.orderTotal"/>:
                            <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true"> 
                            	<%=clwI18n.priceFormat(total,"&nbsp;")%>
                            </logic:equal>
                        </td>
                    </tr>
                </tfoot>
            </table>
            <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.RECEIVING%>">
            <a href="#" class="blueBtnMed bottomMargin right" 
            	onclick="submitForm('<%=Constants.PARAMETER_OPERATION_VALUE_UPDATE_RECEIVE%>')">
            		<span><app:storeMessage key="global.action.label.update"/></span>
            </a>
            </app:authorizedForFunction>
			</html:form>
            <hr />
			
            <p><strong> <app:storeMessage key="mobile.esw.orders.label.shippingAddress"/>:</strong><br />
            <% 
                                             	String name=theForm.getOrderOpDetailForm().
                                 					getOrderStatusDetail().getShipTo().getShortDesc();
	                                            
                                             	String address1 = theForm.getOrderOpDetailForm().
	                                            	getOrderStatusDetail().getShipTo().getAddress1();
	                                        	String address2 = theForm.getOrderOpDetailForm().
	                                        		getOrderStatusDetail().getShipTo().getAddress2();
	                                        	String address3 = theForm.getOrderOpDetailForm().
	                                        			getOrderStatusDetail().getShipTo().getAddress3();
	                                        	String address4 = theForm.getOrderOpDetailForm().
	                                        			getOrderStatusDetail().getShipTo().getAddress4();
	                                        	
	                                        	//Here we retrieve address format specific to a country, in this case it is shipping address
	                                        	//country.
	                                        	String country = theForm.getOrderOpDetailForm().
	                                        			getOrderStatusDetail().getShipTo().getCountryCd();
	                                        	String addressFormat = Utility.getAddressFormatFor(country); 
	                                        	
	                                        	String city=theForm.getOrderOpDetailForm().getOrderStatusDetail().getShipTo().getCity();
	                                        	String state = "";
	                                        	if (appUser.getUserStore().isStateProvinceRequired()) {
	                                        		state = theForm.getOrderOpDetailForm().
	                                        				getOrderStatusDetail().getShipTo().getStateProvinceCd();
	                                        	}
	                                        	String postalCode=theForm.getOrderOpDetailForm().
	                                        			getOrderStatusDetail().getShipTo().getPostalCode();
           							%> 
           							<eswi18n:formatAddress postalCode="<%=postalCode %>" name="<%=name %>" 
                                				address1="<%=address1 %>" address2="<%=address2 %>" address3="<%=address3 %>" 
                                				address4="<%=address4 %>" city="<%=city %>" state="<%=state %>" 
                                				country="<%=country %>" addressFormat="<%=addressFormat %>"/> 
            </p>
            
            <p><strong><app:storeMessage key="mobile.esw.orders.label.billingAddress"/>:</strong><br />
            		<% if (theForm.getOrderOpDetailForm().getOrderStatusDetail().hasCreditCard()) {  
                         	
                         	address1 = theForm.getOrderOpDetailForm().
									getOrderStatusDetail().getOrderCreditCardData().getAddress1();
                         	address2 = theForm.getOrderOpDetailForm().
									getOrderStatusDetail().getOrderCreditCardData().getAddress2();
                         	address3 = theForm.getOrderOpDetailForm().
									getOrderStatusDetail().getOrderCreditCardData().getAddress3();
                         	address4 = theForm.getOrderOpDetailForm().
									getOrderStatusDetail().getOrderCreditCardData().getAddress4();
                         	
                        	
                        	//Here we retrieve address format specific to a country, in this case it is shipping address
                        	//country.
                        	country = theForm.getOrderOpDetailForm().
									getOrderStatusDetail().getOrderCreditCardData().getCountryCd();
                        	addressFormat = Utility.getAddressFormatFor(country); 
                        	
                        	city = theForm.getOrderOpDetailForm().
									getOrderStatusDetail().getOrderCreditCardData().getCity();
                        	state = "";
                        	if (appUser.getUserStore().isStateProvinceRequired()) {
                        		state = theForm.getOrderOpDetailForm().
										getOrderStatusDetail().getOrderCreditCardData().getStateProvinceCd();
                        	}
                        	postalCode = theForm.getOrderOpDetailForm().
									getOrderStatusDetail().getOrderCreditCardData().getPostalCode();
            			
            			%>
            			<eswi18n:formatAddress postalCode="<%=postalCode %>"  
                                					address1="<%=address1 %>" address2="<%=address2 %>" address3="<%=address3 %>" 
                                					address4="<%=address4 %>" city="<%=city %>" state="<%=state %>" 
                                					country="<%=country %>" addressFormat="<%=addressFormat %>"/> 
                    <% } else {   
                    	name = theForm.getOrderOpDetailForm().
             					getOrderStatusDetail().getBillTo().getShortDesc();
                         	
                         	address1 = theForm.getOrderOpDetailForm().
									getOrderStatusDetail().getBillTo().getAddress1();
                         	address2 = theForm.getOrderOpDetailForm().
									getOrderStatusDetail().getBillTo().getAddress2();
                         	address3 = theForm.getOrderOpDetailForm().
									getOrderStatusDetail().getBillTo().getAddress3();
                         	address4 = theForm.getOrderOpDetailForm().
									getOrderStatusDetail().getBillTo().getAddress4();
                         	
                        	
                        	//Here we retrieve address format specific to a country, in this case it is shipping address
                        	//country.
                        	country = theForm.getOrderOpDetailForm().
									getOrderStatusDetail().getBillTo().getCountryCd();
                        	addressFormat = Utility.getAddressFormatFor(country); 
                        	
                        	city = theForm.getOrderOpDetailForm().
									getOrderStatusDetail().getBillTo().getCity();
                        	state = "";
                        	if (appUser.getUserStore().isStateProvinceRequired()) {
                        		state = theForm.getOrderOpDetailForm().
										getOrderStatusDetail().getBillTo().getStateProvinceCd();
                        	}
                        	postalCode = theForm.getOrderOpDetailForm().
									getOrderStatusDetail().getBillTo().getPostalCode();
                    %>
                     		<eswi18n:formatAddress postalCode="<%=postalCode %>" name="<%=name %>" 
                                					address1="<%=address1 %>" address2="<%=address2 %>" address3="<%=address3 %>" 
                                					address4="<%=address4 %>" city="<%=city %>" state="<%=state %>" 
                                					country="<%=country %>" addressFormat="<%=addressFormat %>"/> 
                    <% } %>
           </p>
          
            
            <div class="footer">
        <html:form styleId="viewWebsiteOrderDetailForm" name="esw.OrdersForm" action="userportal/esw/showOrderDetail.do"> 
                    <html:hidden name="esw.OrdersForm" property="operation" value="<%= Constants.PARAMETER_OPERATION_VALUE_VIEW_WEB_ORDER_DETAIL %>"/>
                    <html:hidden name="esw.OrdersForm" property="orderId" value="<%= String.valueOf(orderStatusDetail.getOrderDetail().getOrderId())%>"/>
        <p><a href="javascript:viewWebsite()" ><app:storeMessage key="mobile.esw.orders.label.visitFullWebsite"/></a></p>
        </html:form>
