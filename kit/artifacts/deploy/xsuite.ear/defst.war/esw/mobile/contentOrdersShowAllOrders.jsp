<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.cleanwise.service.api.value.OrderAddressData"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.cleanwise.service.api.util.SessionDataUtil"%>
<%@page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
<%@page import="java.util.Date"%>
<%@page import="com.cleanwise.service.api.value.OrderData"%>
<%@page import="com.cleanwise.view.utils.ShopTool"%>
<%@page import="com.cleanwise.service.api.util.RefCodeNames"%>
<%@page import="com.cleanwise.service.api.value.OrderStatusDescData"%>
<%@page import="com.cleanwise.service.api.value.AddressData"%>
<%@page import="com.cleanwise.service.api.value.SiteData"%>
<%@page import="com.cleanwise.view.utils.Constants"%>
<%@page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@page import="com.cleanwise.service.api.util.Utility"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@page import="java.util.List"%>

<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri='/WEB-INF/eswI18n.tld' prefix='eswi18n' %>

<bean:define id="ordersForm" name="esw.OrdersForm"  type="com.espendwise.view.forms.esw.OrdersForm"/>

    <%
                        CleanwiseUser user = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
                        SiteData location = user.getSite();
                        
                        SessionDataUtil sessionData = Utility.getSessionDataUtil(request);
                        
                       // SiteData specifiedLocation = sessionData.getSpecifiedLocation();
                        String ordersLength = (String.valueOf(sessionData.getOrdersLength()));
                        
                        boolean accountHasFiscalCalendar = ShopTool.doesAnySiteSupportsBudgets(request);
                        
                        StringBuilder changeLocationLink = new StringBuilder(50);
                        changeLocationLink.append("userportal/esw/dashboard.do?");
                        changeLocationLink.append(Constants.PARAMETER_OPERATION);
                        changeLocationLink.append("=");
                        changeLocationLink.append(Constants.PARAMETER_OPERATION_VALUE_SELECT_LOCATION_START); 
                        
                        String formBeanName = request.getParameter(Constants.PARAMETER_FORM_BEAN_NAME);
                        final int COMMENT_MAX_DISPLAY_LENGTH = 120;
                        final String ELLIPSIS = "...";
                        
                        //display information about the location
                        StringBuilder addressString = new StringBuilder(100);
                        List<String> addressShort = null;
                        Iterator<String> iterator = null;
                        String address1 = "";
                        String city = "";
                        String state = "";
                        String postalCode = "";
                        String addressFormat = "";
                        String locationName = "";
                        if(location != null) {
                            locationName = Utility.encodeForHTML(location.getBusEntity().getShortDesc());
                        	address1 = Utility.encodeForHTML(location.getSiteAddress().getAddress1());
                        	city = Utility.encodeForHTML(location.getSiteAddress().getCity());
                        	
                           	if (user.getUserStore().getCountryProperties() != null) {
                        	    if (Utility.isTrue(Utility.getCountryPropertyValue(
                        	    		user.getUserStore().getCountryProperties(), 
                        	    		RefCodeNames.COUNTRY_PROPERTY.USES_STATE))) {
                        	    	state = Utility.encodeForHTML(location.getSiteAddress().getStateProvinceCd());
                        	    }
                        	}
                           	postalCode = Utility.encodeForHTML(location.getSiteAddress().getPostalCode());
                           	addressFormat = Utility.getAddressFormatFor(location.getSiteAddress().getCountryCd());
                        	
                        }
                       	StringBuilder showOrderLink = new StringBuilder(50);
                    	showOrderLink.append("userportal/esw/showOrderDetail.do?"); //new stmt, added by SVC on 08/03/2011
                    	showOrderLink.append(Constants.PARAMETER_OPERATION);
                    	showOrderLink.append("=");
                    	showOrderLink.append(Constants.PARAMETER_OPERATION_VALUE_SHOW_ORDER);
                    	showOrderLink.append("&");
                    	showOrderLink.append(Constants.PARAMETER_ORDER_ID);
                    	showOrderLink.append("=");
                    	
                    	String defaultDateFormat = ClwI18nUtil.getDatePattern(request);
						if(Utility.isSet(defaultDateFormat)) {
							defaultDateFormat = defaultDateFormat.toLowerCase();
						}
						String toDateValue = defaultDateFormat;
						String fromDateValue = defaultDateFormat;
						if(Utility.isSet(ordersForm.getOrdersSearchInfo().getTo())) {
							toDateValue = ordersForm.getOrdersSearchInfo().getTo();
						}
						if(Utility.isSet(ordersForm.getOrdersSearchInfo().getFrom())) {
							fromDateValue = ordersForm.getOrdersSearchInfo().getFrom();
						}
						
						int ordersSize = 0;
						if(ordersForm.getOrdersSearchInfo() != null && ordersForm.getOrdersSearchInfo().getMatchingOrders() != null){
							ordersSize = ordersForm.getOrdersSearchInfo().getMatchingOrders().size();	
						}
						
                    %>
   
		    	<div class="actionBar actionNav">
	                <h2>
	                		<app:storeMessage key="mobile.esw.orders.label.allorders" />
	                </h2>
        	    </div>
		  
               <%
					String errorsAndMessagesPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "errorsAndMessages.jsp");
				%>
                <p><jsp:include page="<%=errorsAndMessagesPage %>"/></p>

          
			
		  <ul class="navigation clearfix">
                <li>
                	<a href="dashboard.do?operation=showPendingOrders">
                		<app:storeMessage key="mobile.esw.orders.label.pendingOrders" />
                	</a>
                </li>
                <li class="selected">
                	<a href="orders.do?operation=">
                		<app:storeMessage key="mobile.esw.orders.label.orders" />
                	</a>
                </li>
          </ul> 
          <hr />
          
			<table class="noBorder">
                <tbody>
                <% if(location != null) { %>
                    <tr>
                        <td>
                    	<strong><app:storeMessage key="global.label.location" /></strong><br />
                        <p>
                        	<eswi18n:formatAddress name="<%=locationName %>" address1="<%=address1 %>" postalCode="<%=postalCode %>"  
                                			  city="<%=city %>" state="<%=state %>" 
                                			  addressFormat="<%=addressFormat %>"/> 
                        </p>
                        </td>
                        <td>
                        <% 
                        		boolean showChangeLocationLink = user.getConfiguredLocationCount() > 1 || location == null;
                            	if (showChangeLocationLink) { 
                            %>
                            	<p class="right">
                            		<html:link action="<%=changeLocationLink.toString()%>">
                            			<span>
                            				(<app:storeMessage key="mobile.esw.orders.label.changeLocation" />)
                            			</span>
                            		</html:link>
                            	</p>
                            <% 
                            	} 
                            %>
                        </td>
                    </tr>
                    <% } else {%>
					<tr>
                        <td>
							<strong><app:storeMessage key="header.label.locationNotSpecified" /></strong>
                        </td>
                        <td>
	                         <%
	                        	String selectLocationString = ClwMessageResourcesImpl.getMessage(request,"header.label.selectLocation");
	                         %>
	                        <p>
								<html:link title="<%=selectLocationString%>" 
									action="<%=changeLocationLink.toString()%>" 
									styleClass="blueBtnMed">
									<span>
		                				<%=selectLocationString%>
									</span>
								</html:link>	
							</p>
                        </td>
                    </tr>
                    <% } %>
                </tbody>
            </table>			
			 <!-- Begin: Orders Left Panel (Filters Panel) -->
                            
                             <%
								String filterPanel = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "ordersFiltersPanel.jsp");
							 %>
							 
		                   	 <jsp:include page="<%=filterPanel %>" >
		                   	 	<jsp:param name="formBeanName" value="esw.OrdersForm"/>
		                   	 </jsp:include>
                   	
             <!-- End: Orders Left Panel (Filters Panel) -->
             <html:form styleId="allOrdersForm" action="/userportal/esw/orders">
                	<html:hidden property="operation" styleId="operationId" value=""/>
            <table class="noBorder">
                <colgroup>
                    <col class="location" />
                    <col class="viewOrder" />
                </colgroup>
                 <logic:notEmpty name="esw.OrdersForm" property="ordersSearchInfo.matchingOrders">
                 <logic:iterate id="order" name="esw.OrdersForm" property="ordersSearchInfo.matchingOrders"
	 												indexId="orderIndex" type="com.cleanwise.service.api.value.OrderStatusDescData"
	 												length="<%=ordersLength %>">
                <tbody>
                    <!-- Repeating Row - needs two rows for each entry to accomodate the details line which needs colspan -->
                    <tr class="noBorder">
                        <td>
                        <%
							//display information about the location
						   addressString = new StringBuilder(100);
                      	   OrderAddressData orderAddress = ((OrderStatusDescData) order).getShipTo();
                    	   if(orderAddress != null) {
		                       locationName = Utility.encodeForHTML(orderAddress.getShortDesc());
			                   address1 = Utility.encodeForHTML(orderAddress.getAddress1());
		                       city = Utility.encodeForHTML(orderAddress.getCity());
		                       //STJ-5509
		                       if(Utility.isSet(orderAddress.getStateProvinceCd())) {
			                       state = Utility.encodeForHTML(orderAddress.getStateProvinceCd());
		                       }
		                       postalCode = Utility.encodeForHTML(orderAddress.getPostalCode());
		                   	addressFormat = Utility.getAddressFormatFor(orderAddress.getCountryCd());
                    	   }
	                    %>
		               	<eswi18n:formatAddress name="<%=locationName %>" address1="<%=address1 %>" postalCode="<%=postalCode %>"  
                                			  city="<%=city %>" state="<%=state %>" 
                                			  addressFormat="<%=addressFormat %>"/> 
                        </td>
                        <td>
                        
                         		<%
                                    String orderStatus = ((OrderStatusDescData) order).getOrderDetail().getOrderStatusCd();
                                    if (!orderStatus.trim().equals(RefCodeNames.ORDER_STATUS_CD.ORDERED_PROCESSING)) {
                                    	String orderLink = showOrderLink.toString() + (order.getOrderDetail().getOrderId());
                                 %>
                                    <html:link action="<%=orderLink %>" styleClass="blueBtnMed">
                              	       <span><app:storeMessage key="mobile.esw.orders.label.viewOrder" /></span>
     								</html:link>
                                 
                             	<% } else { %>
	                             	 <a href="#" class="blueBtnMed blueBtnMedDisabled">
	                              	   <span><app:storeMessage key="mobile.esw.orders.label.viewOrder" /></span>
	     							 </a>
                             	<% } %>
                        
                        </td>
                    </tr>
                    <tr class="details">
                        <td colspan="2">
                            <p><strong><app:storeMessage key="mobile.esw.orders.label.order#" />:</strong> 
                             <%
                                    if (orderStatus.trim().equals(RefCodeNames.ORDER_STATUS_CD.ORDERED_PROCESSING)) {
                                 %>
                                        <bean:write name="order" property="orderDetail.orderNum"/>
                             	<% } else {
                            			   String orderLink = showOrderLink.toString() + (order.getOrderDetail().getOrderId());
                            		%>
                                     <html:link action="<%=orderLink %>">
                              	       <bean:write name="order" property="orderDetail.orderNum"/>
     </html:link>
                                 <% } %>
                            
                             <%  
                             	String style = "";
                               	if(orderStatus != null && (orderStatus.trim().equals(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL))) {
                               		style = " class=\"alert\"";
                               	}
                               	String i18nStatusCode = Utility.encodeForHTML(ShopTool.xlateStatus(order, request));
	                         %>
                            <span<%=style %>>&nbsp;	
                            	(<%= i18nStatusCode%>)
                            </span><br />
							<strong><app:storeMessage key="mobile.esw.orders.label.order.ordered" />:</strong>
							<% 
	                            OrderData orderData = ((OrderStatusDescData) order).getOrderDetail();
	                            Date date = orderData.getOriginalOrderDate();
								Date time = orderData.getOriginalOrderTime();
								Date orderDate = Utility.getDateTime(date, time);
									
								String formattedODate = Utility.encodeForHTML(ClwI18nUtil.formatDate(request, orderDate));
							%>
	                                                
	                        <%=formattedODate %> 
							&nbsp;&nbsp;
							<strong><app:storeMessage key="mobile.esw.orders.label.order.po#" />:</strong> 
								<bean:write name="order" property="orderDetail.requestPoNum" />
							<br />
							
                            <strong><app:storeMessage key="mobile.esw.orders.label.orderTotal" />:</strong> 
                             <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">  
	                                                   
                                <%
									java.math.BigDecimal discountedTotal =  order.getEstimatedTotal();
									
									String orderLocale = order.getOrderDetail().getLocaleCd();
								%>
								<%=ClwI18nUtil.priceFormat(request, discountedTotal, (String) orderLocale," ")%>
														
	                         </logic:equal>
	                         &nbsp;&nbsp; 
	                         <% 
                             	if(accountHasFiscalCalendar && orderStatus.trim().equals(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL)) {
                             %>
	                        <strong><app:storeMessage key="mobile.esw.orders.label.unspentBudget" />:</strong>
	                          <span>
                              	<%
                              		BigDecimal remainingBudget = null;
                              		if(order.getOrderSiteData() != null){
                              			remainingBudget = order.getOrderSiteData().getTotalBudgetRemaining();
                              		}
	                                //remainingBudget = order.getOrderSiteData().getTotalBudgetRemaining();
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
                            </span>
                            </p>
                        </td>
                    </tr>
                    <!-- End Repeating Row -->
                    <!-- Repeating Row - needs two rows for each entry to accomodate the details line which needs colspan -->
                   
                    <!-- End Repeating Row -->
                </tbody>
                </logic:iterate>
                </logic:notEmpty>
            </table>
            
            <% if(sessionData.getOrdersLength() < ordersSize) {%>
            <p class="centeredButton clearfix">
            <a href="orders.do?operation=showTenMoreOrders" class="blueBtnMed">
            	 <span>
	            	 <app:storeMessage key="mobile.esw.orders.label.showMore" />
            	 </span>
            </a>   
            </p>
            <% } %>
            </html:form>
             <div class="footer">
        		<p>
        			<a href="orders.do?operation=viewWebsite" >
        				<app:storeMessage key="mobile.esw.orders.label.visitFullWebsite" />
        			</a>
        		</p>
        
  


