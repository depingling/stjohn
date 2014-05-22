<%@page import="java.util.HashMap"%>
<%@page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.cleanwise.service.api.value.OrderAddressData"%>
<%@page import="com.cleanwise.service.api.value.AddressData"%>
<%@page import="com.cleanwise.service.api.value.OrderAddOnChargeData"%>
<%@page import="com.cleanwise.service.api.value.OrderFreightData"%>
<%@page import="com.cleanwise.view.utils.ShopTool"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.cleanwise.service.api.value.OrderData"%>
<%@page import="com.cleanwise.service.api.util.RefCodeNames"%>
<%@page import="com.cleanwise.view.utils.Constants"%>
<%@page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@page import="java.util.TimeZone"%>
<%@page import="com.cleanwise.service.api.value.OrderStatusDescData"%>
<%@page import="com.cleanwise.service.api.util.Utility"%>
<%@page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>

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
function approveOrder() {
	document.getElementById("orderFormOperation").value = '<%=Constants.PARAMETER_OPERATION_VALUE_APPROVE_ORDER%>';
	submitForm('orderForm');
}

function rejectOrder() {
	document.getElementById("orderFormOperation").value = '<%=Constants.PARAMETER_OPERATION_VALUE_REJECT_ORDER%>';
	submitForm('orderForm');
}
function viewWebsite(){
	document.getElementById('viewWebsiteOrderDetailForm').submit();
}
</script>
<%

  CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
  TimeZone timeZone =  Utility.getTimeZoneFromUserData(appUser.getSite());
  String approvalDate = theForm.getApprovalDate();
  boolean reasonCodeFl = false;
  if (!Utility.isSet(approvalDate)) {
		 Date dNow = new Date();
		 String patternAD  = ClwI18nUtil.getDatePattern(request);
		 SimpleDateFormat formatterAD = new SimpleDateFormat(patternAD);
		 approvalDate = formatterAD.format(dNow);
  }

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
%>

				<div class="actionBar actionNav">
	                <a href="#" class="homeBtn"><app:storeMessage key="mobile.esw.orders.label.home"/></a>
	                <h2><app:storeMessage key="mobile.esw.orders.label.orderDetail"/></h2>
	            </div>
	            <%
					String errorsAndMessagesPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "errorsAndMessages.jsp");
				%>
                <p><jsp:include page="<%=errorsAndMessagesPage %>"/></p>
           
			<a href="backToPendingOrders.do" 
			   class="btn bottomMargin rightMargin">
			   <span><app:storeMessage key="mobile.esw.orders.label.backToPendingOrders"/></span>
			</a>
			<hr />
             <html:form styleId="orderForm" name="esw.OrdersForm" action="userportal/esw/showOrderDetail.do"> 
                    <html:hidden name="esw.OrdersForm" property="operation" styleId="orderFormOperation"/>    
            
            <h2><app:storeMessage key="mobile.esw.orders.label.order#"/> <bean:write name="theForm" property="orderOpDetailForm.orderStatusDetail.orderDetail.orderNum"/> <span class="alert">&nbsp; (<%=com.cleanwise.view.utils.ShopTool.xlateStatus(orderStatusDetail, request)%>)</span></h2>
             <%   //STJ-4689
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
            
			<p><strong><app:storeMessage key= "mobile.esw.orders.label.order.ordered" />:</strong>
			 <logic:notEmpty name="order" property="orderDetail.revisedOrderDate">
              	<%=Utility.encodeForHTML(ClwI18nUtil.formatDate(request, orderStatusDetail.getOrderDetail().getRevisedOrderDate()))%>
 	            </logic:notEmpty>
          				<logic:empty name="order" property="orderDetail.revisedOrderDate">
         	    	<%=Utility.encodeForHTML(ClwI18nUtil.formatDate(request, orderStatusDetail.getOrderDetail().getOriginalOrderDate()))%>
             	</logic:empty>  
			&nbsp;&nbsp; 
			<strong><app:storeMessage key= "mobile.esw.orders.label.order.po#" />:</strong> 
			<%= requestPoNum %>
			<br />
			<strong> <app:storeMessage key="mobile.esw.orders.label.processOrderOn"/>: </strong>
			 <%
                 // --date conversion should be reviewed later
                 String processOnDateS = theForm.getOrderOpDetailForm().getOrderStatusDetail().getPendingDate();
                 SimpleDateFormat defaultFormat = new SimpleDateFormat("MM/dd/yyyy");
                 Date defaultDate = new Date();
                 if(processOnDateS != null && processOnDateS.trim().length()>0) {
                 	 defaultDate = defaultFormat.parse(processOnDateS);
                 }
				
                 String pattern = ClwI18nUtil.getDatePattern(request);
                 SimpleDateFormat sdfm = new SimpleDateFormat(pattern);

                 java.util.Date processOnDate = null;
                 if(processOnDateS!=null && processOnDateS.trim().length()>0) {
                    processOnDate = sdfm.parse(sdfm.format(defaultDate));
                    String formattedProcessOnDate = ClwI18nUtil.formatDateInp(request, processOnDate, timeZone.getID() );
             %>
                   <%=formattedProcessOnDate%>
             <% } %> 
			<br />
			<strong>
				<app:storeMessage key="mobile.esw.orders.label.orderTotal"/>:
			</strong>
			<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true"> 
				<bean:define id="total"  name="theForm" property="orderOpDetailForm.totalAmount"/>
	            <%=clwI18n.priceFormat(total,"&nbsp;")%> 
            </logic:equal>
			&nbsp;&nbsp;
			 	 		<% 
							 if (orderBudgetInfo.containsKey(siteId)) {   
								 Boolean siteSupportsBudgets = (Boolean)orderBudgetInfo.get(siteId);
							 		if(siteSupportsBudgets){
				 		%>
			<strong>
										<app:storeMessage key= "mobile.esw.orders.label.unspentBudget" />:
			</strong>
			<span> 
						<%
			            	  
			            		     if(orderBudgetInfo.containsKey("unspentAmount")){
			            			    BigDecimal remainingBudget = (BigDecimal)orderBudgetInfo.get("unspentAmount"); 
			               					if (remainingBudget != null && !remainingBudget.equals(BigDecimal.ZERO)) {
						%>
                         		           <app:formatPriceCurrency price="<%=remainingBudget%>"
                                                             orderId="<%=Integer.parseInt(theForm.getOrderId())%>"
                                                             checkNegative="true"
                                                             negativeClass="negative"/>
                        <%
                         		   			}
			            		     }
			            	   }
			               }
                        %>
			</span>
			
			<br />
            <strong> <app:storeMessage key="mobile.esw.orders.label.placedBy"/>: </strong>
            <%
          		String orderContactEmail = orderStatusDetail.getOrderDetail().getOrderContactEmail();
				if(orderContactEmail != null && orderContactEmail.trim().length() > 0) {          		
            %>
            <!-- STJ - 4932 -->
             <a href="mailto:<bean:write name="theForm" property="orderOpDetailForm.orderStatusDetail.orderDetail.orderContactEmail"/>">
             	<bean:write name="theForm" property="orderOpDetailForm.orderStatusDetail.orderDetail.orderContactName"/>
             </a> 
             <% } 
				else {  %>
             		<bean:write name="theForm" property="orderOpDetailForm.orderStatusDetail.orderDetail.orderContactName"/>
             <% }  %>
             &nbsp;&nbsp; 
             <strong><app:storeMessage key="mobile.esw.orders.label.phone"/>: </strong> 
             <%
             String orderContactPhoneNum = orderStatusDetail.getOrderDetail().getOrderContactPhoneNum();
				if(orderContactPhoneNum != null && orderContactPhoneNum.trim().length() > 0) {
             %>
             <a href="tel:<bean:write name="theForm" property="orderOpDetailForm.orderStatusDetail.orderDetail.orderContactPhoneNum"/>">
             	<bean:write name="theForm" property="orderOpDetailForm.orderStatusDetail.orderDetail.orderContactPhoneNum"/>
             </a>
             <% }  %>
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
											// STJ - 4944 
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
        	                             	<%=ClwI18nUtil.formatDate(request, orderProperty.getApproveDate())%>:
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
						                  <!-- STJ - 4947 -->
						                 
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
                                               <%=formattedComDate%> <!-- STJ - 4948 --> 
                                                 <app:writeHTML name="note" property="value"/>
											  (<bean:write name="note" property="addBy"/>) 
											<br/>
                                            </logic:iterate>
                                          </logic:greaterThan>
                                        </logic:present>
            	
            </p>
						
		<%  
             boolean isDisableApproveDate = true;
             if (approverFl || reasonCodeFl) {
             	isDisableApproveDate = false;
             }
        %>		
                  <% if (approverFl || reasonCodeFl) {  %>			
            <div class="buttonRow clearfix">
                <table>
                    <tr>
                        <td class="approveOn"><app:storeMessage key="mobile.esw.orders.label.approve.on"/> </td>
                        <td>
                        
                            <html:text property="approvalDate" value="<%=approvalDate%>" styleClass="dateMatch" disabled="<%= isDisableApproveDate%>"/>  
                        </td>
                        <td class="approveBtns">
              
                            <html:link href="javascript:approveOrder()" 
                        	   styleClass="blueBtnMed popUpOkSmall" 
                               title='<%=ClwMessageResourcesImpl.getMessage(request,"message.order.confirmOrderApproval") %>'>
                            	<span><app:storeMessage key="global.action.label.approve" /></span>
                            </html:link>
                            <html:link href="javascript:rejectOrder()" 
                            		   title='<%=ClwMessageResourcesImpl.getMessage(request,"message.order.confirmOrderRejection") %>'  
                               styleClass="blueBtnMed popUpOkSmall"> 
                                <span><app:storeMessage key="global.action.label.reject" /></span>
                            </html:link>
                        
                        </td>
                    </tr>
                </table>
            </div>
            <%  }  %>
            <table class="productList">
                <thead>
                    <tr>
                        <th class="first"><app:storeMessage key="mobile.esw.orders.label.product"/></th>
                        <th class="qty"><app:storeMessage key="mobile.esw.orders.label.qty"/></th>
                        <th class="last"> 
                        <app:storeMessage key="mobile.esw.orders.label.subtotal"/></th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="first"></td>
                        <td class="qty hide"><a href="#" class="smallBtn"><span>Update Qty</span></a></td>
                        <td class="last"></td>
                    </tr>
                     <%  
	                         OrderFreightData orderFreightData = null; 
	                         OrderAddOnChargeData orderDiscountData = null; 
	                         OrderAddOnChargeData orderSmallOrderFee = null; 
	                         OrderAddOnChargeData orderFuelSurcharge = null;
	                         BigDecimal custItemSubtotal = BigDecimal.ZERO;
	                         BigDecimal distSubtotal = BigDecimal.ZERO;
	                         BigDecimal distTotal = BigDecimal.ZERO;
                         %>
                    
                    <logic:iterate id="item" name="theForm" property="orderOpDetailForm.orderItemDescList"
                            offset="0" indexId="oidx" type="com.cleanwise.service.api.value.OrderItemDescData">
                            <bean:define id="qty" name="item" property="orderItem.totalQuantityOrdered" type="Integer"/>
                            <bean:define id="quantityEl" value="<%=\"quantityElement[\"+oidx+\"]\"%>"/>
							<bean:define id="itemIdsEl" value="<%=\"itemIdsElement[\"+oidx+\"]\"%>"/>
								<%  orderFreightData = item.getOrderFreight(); 
                                	orderDiscountData = item.getOrderDiscount(); 
                                	orderSmallOrderFee = item.getOrderSmallOrderFee(); 
                                	orderFuelSurcharge = item.getOrderFuelSurcharge();
                                %>
							<%java.math.BigDecimal custItemPrice=new BigDecimal(0.00);%>
							<logic:present  name="item" property="orderItem.custContractPrice">
                            	<%custItemPrice=item.getOrderItem().getCustContractPrice();%>
                            </logic:present>
                            <% custItemSubtotal = custItemPrice.multiply(new BigDecimal(qty.doubleValue())); %>
                            <% distSubtotal =  Utility.addAmt(distSubtotal, custItemSubtotal.setScale(2, BigDecimal.ROUND_HALF_UP));%>
	                    <tr>
	                        <td class="first"><strong><bean:write name="item" property="orderItem.itemShortDesc"/></strong><br />
	                            <span>
									<app:storeMessage key="mobile.esw.orders.label.sku"/>: 
									<%=ShopTool.getRuntimeSku(item.getOrderItem(),request)%>
									<br />
									<app:storeMessage key="mobile.esw.orders.label.price"/>: 
									<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">  
                                            <%=clwI18n.priceFormat(custItemPrice,"&nbsp;")%>
                                        </logic:equal>
									
								</span>
							</td>
	                        <td class="qty hide"><input type="text" value="1" /></td>
							<td><bean:write name="item" property="orderItem.totalQuantityOrdered"/></td>
							<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">  
	                        	<td class="last"><%=clwI18n.priceFormat(custItemPrice.multiply(new BigDecimal(qty.doubleValue())),"&nbsp;")%></td>
	                        </logic:equal>
	                    </tr>
                    </logic:iterate>
                    <tr>
                        <td class="first"></td>
                        <td class="qty hide"><a href="#" class="smallBtn"><span>Update Qty</span></a></td>
                        <td class="last"></td>
                    </tr>
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
            
             <% if (approverFl || reasonCodeFl) {  %>
            <div class="buttonRow lowerButtonRow clearfix">
                <p span class="error hide">Please enter format mm/dd/yyyy</p>
                <table>
                    <tr>
                        <td class="approveOn"><app:storeMessage key="mobile.esw.orders.label.approve.on"/> </td>
                        <td>
                            <html:text property="approvalDate" value="<%=approvalDate%>" styleClass="dateMatch" disabled="<%= isDisableApproveDate%>"/>
                        </td>
                         <td class="approveBtns">
                            <html:link href="javascript:approveOrder()" 
                        	   styleClass="blueBtnMed popUpOkSmall"  
                               title='<%=ClwMessageResourcesImpl.getMessage(request,"message.order.confirmOrderApproval") %>'>
                            	<span><app:storeMessage key="global.action.label.approve" /></span>
                            </html:link>
                            <html:link href="javascript:rejectOrder()" 
                            		   title='<%=ClwMessageResourcesImpl.getMessage(request,"message.order.confirmOrderRejection") %>'  
                               styleClass="blueBtnMed popUpOkSmall">
                                <span><app:storeMessage key="global.action.label.reject" /></span>
                            </html:link>
                        </td>
                    </tr>
                </table>
            </div>
            <%  }  %>
            
            <p><strong> <app:storeMessage key="mobile.esw.orders.label.shippingAddress"/>:</strong><br />
            
            
           							 <% 
                                             	String name=theForm.getOrderOpDetailForm().
                                 					getOrderStatusDetail().getShipTo().getShortDesc();
	                                            
                                             	address1 = theForm.getOrderOpDetailForm().
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
	                                        	String shortDesc = country;
	                                        	addressFormat = Utility.getAddressFormatFor(shortDesc); 
	                                        	
	                                        	city=theForm.getOrderOpDetailForm().getOrderStatusDetail().getShipTo().getCity();
	                                        	state = "";
	                                        	if (appUser.getUserStore().isStateProvinceRequired()) {
	                                        		state = theForm.getOrderOpDetailForm().
	                                        				getOrderStatusDetail().getShipTo().getStateProvinceCd();
	                                        	}
	                                        	postalCode=theForm.getOrderOpDetailForm().
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
                        	shortDesc = country;
                        	addressFormat = Utility.getAddressFormatFor(shortDesc); 
                        	
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
                        	shortDesc = country;
                        	addressFormat = Utility.getAddressFormatFor(shortDesc); 
                        	
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
        </html:form>
        <div class="footer">
        <html:form styleId="viewWebsiteOrderDetailForm" name="esw.OrdersForm" action="userportal/esw/showOrderDetail.do"> 
                    <html:hidden name="esw.OrdersForm" property="operation" value="<%= Constants.PARAMETER_OPERATION_VALUE_VIEW_WEB_ORDER_DETAIL %>"/>
                    <html:hidden name="esw.OrdersForm" property="orderId" value="<%= String.valueOf(orderStatusDetail.getOrderDetail().getOrderId())%>"/>
        <p><a href="javascript:viewWebsite()" ><app:storeMessage key="mobile.esw.orders.label.visitFullWebsite"/></a></p>
        </html:form>
