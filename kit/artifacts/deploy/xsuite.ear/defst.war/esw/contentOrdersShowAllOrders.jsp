<%@page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.cleanwise.service.api.util.SessionDataUtil"%>
<%@page import="com.cleanwise.service.api.dto.OrderSearchDto"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.cleanwise.service.api.value.OrderData"%>
<%@page import="com.cleanwise.service.api.value.OrderStatusDescData"%>
<%@page import="java.util.TimeZone"%>
<%@page import="java.util.Date"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>
<%@ page import="com.cleanwise.service.api.dto.LocationSearchDto" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.service.api.value.AddressData" %>
<%@ page import="com.cleanwise.service.api.value.BusEntityData" %>
<%@ page import="com.cleanwise.service.api.value.OrderAddressData" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>

<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri='/WEB-INF/eswI18n.tld' prefix='eswi18n' %>

<bean:define id="myForm" name="esw.OrdersForm"  type="com.espendwise.view.forms.esw.OrdersForm"/>

<%
//TODO - implement this page.  The HTML is basically set, but things like converting input
//to use struts tag libs, implementing links, etc all has to be done.  Best approach is to go
//through entire page line by line and implement whatever hasn't been done.

	CleanwiseUser user = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

	String ordersSortLink = "userportal/esw/orders.do"; 
	//Commented the below statement to work All Orders sorting functionality properly. 
	String pathToShowOrderDetail = "userportal/esw/showOrderDetail.do"; 
	
    StringBuilder showOrderLink = new StringBuilder(50);
	//showOrderLink.append(ordersSortLink);
	showOrderLink.append(pathToShowOrderDetail);
    showOrderLink.append("?");
    showOrderLink.append(Constants.PARAMETER_OPERATION);
    showOrderLink.append("=");
    showOrderLink.append(Constants.PARAMETER_OPERATION_VALUE_SHOW_ORDER);
    showOrderLink.append("&");
    showOrderLink.append(Constants.PARAMETER_ORDER_ID);
    showOrderLink.append("=");
   
    TimeZone timeZone =  Utility.getTimeZoneFromUserData(user.getSite());
    
    //Get OrderSearchInfo from the session.
    OrderSearchDto ordersSearchInfo = myForm.getOrdersSearchInfo();
    	
    if(ordersSearchInfo==null){
    	SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
    	ordersSearchInfo = sessionDataUtil.getOrderSearchDto();
    	myForm.setOrdersSearchInfo(ordersSearchInfo);
    } 
    
    
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
                <!-- Start Box -->
                <div class="boxWrapper">
                    <div class="content">
                      <div class="left budgets clearfix">
                            <div class="rightColumn">
                                <div class="rightColumnIndent">
                                
                               
		                    <!--  	<logic:empty name="esw.OrdersForm" property="ordersSearchInfo.matchingOrders">
                                   		<p>
                                   			<app:storeMessage  key="orders.search.noResults" />
	                                    </p>
                                 </logic:empty> -->
		                <h2 class="left"><%=ClwI18nUtil.getMessage(request, "shopping.orders.label.allOrders")%>
				</h2>
                                    <p class="right topMargin">
									
                                    <logic:notEmpty name="esw.OrdersForm" property="ordersSearchInfo.matchingOrders">
									<bean:size id="size" name="esw.OrdersForm" property="ordersSearchInfo.matchingOrders"/>
                                    <%
                                    	Object[] params = new Object[1];
	                                    params[0] = size;
	                                    String message = "";
	                                    if(size>Constants.LOCATION_SEARCH_RESULTS_MAX_DISPLAY){
	                                    	message = ClwI18nUtil.getMessage(request, "orders.search.maximumResultsMet", params);
	                                    } else {
	                                    	message = ClwI18nUtil.getMessage(request, "orders.search.results.viewingOrders", params);
	                                    }
                                    %>
                                    <%=message %>
                                    
                                    </logic:notEmpty>
                                    </p>
                                    <%
                                    
                                    String upArrowImg = "<img src='../../esw/images/upArrow.gif'/>";
                                    String downArrowImg = "<img src='../../esw/images/downArrow.gif'/>";
                                    
                               		String orderNumSortOrder = Constants.ORDERS_SORT_ORDER_ASCENDING;
                                   	String orderNumImage = StringUtils.EMPTY;
                                   	String poNumSortOrder = Constants.ORDERS_SORT_ORDER_ASCENDING;
                                   	String poNumImage = StringUtils.EMPTY;
                                   	String orderDateSortOrder = Constants.ORDERS_SORT_ORDER_ASCENDING;
                                   	String orderDateImage = StringUtils.EMPTY;
                                   	String statusSortOrder = Constants.ORDERS_SORT_ORDER_ASCENDING;
                                   	String statusImage = StringUtils.EMPTY;
                                   	String locationNameSortOrder = Constants.ORDERS_SORT_ORDER_ASCENDING;
                                   	String locationNameImage = StringUtils.EMPTY;
                                   	String placedBySortOrder = Constants.ORDERS_SORT_ORDER_ASCENDING;
                                   	String placedByImage = StringUtils.EMPTY;
                                   	String priceSortOrder = Constants.ORDERS_SORT_ORDER_ASCENDING;
                                   	String priceImage = StringUtils.EMPTY;
                                   	
                                   	String sortField = ordersSearchInfo.getSortField();
                                   	String sortOrder = ordersSearchInfo.getSortOrder();
                                   	
                                   	if (Constants.ORDERS_SORT_FIELD_ORDER_NUMBER.equalsIgnoreCase(sortField)) {//
                                       	if (Constants.LOCATION_SORT_ORDER_ASCENDING.equalsIgnoreCase(sortOrder)) {
                                       		orderNumSortOrder = Constants.ORDERS_SORT_ORDER_DESCENDING;
                                       		orderNumImage = upArrowImg;
                                       	}
                                       	else {
                                       		orderNumImage = downArrowImg;
                                       	}
                                   	}
                                   	if (Constants.ORDERS_SORT_FIELD_PO_NUMBER.equalsIgnoreCase(sortField)) {//
                                       	if (Constants.LOCATION_SORT_ORDER_ASCENDING.equalsIgnoreCase(sortOrder)) {
                                       		poNumSortOrder = Constants.ORDERS_SORT_ORDER_DESCENDING;
                                       		poNumImage = upArrowImg;
                                       	}
                                       	else {
                                       		poNumImage = downArrowImg;
                                       	}
                                   	}
                                   	if (Constants.ORDERS_SORT_FIELD_ORDER_DATE.equalsIgnoreCase(sortField)) {//
                                       	if (Constants.LOCATION_SORT_ORDER_ASCENDING.equalsIgnoreCase(sortOrder)) {
                                       		orderDateSortOrder = Constants.ORDERS_SORT_ORDER_DESCENDING;
                                       		orderDateImage = upArrowImg;
                                       	}
                                       	else {
                                       		orderDateImage = downArrowImg;
                                       	}
                                   	}
                                   	if (Constants.ORDERS_SORT_FIELD_STATUS.equalsIgnoreCase(sortField)) {//
                                       	if (Constants.LOCATION_SORT_ORDER_ASCENDING.equalsIgnoreCase(sortOrder)) {
                                       		statusSortOrder = Constants.ORDERS_SORT_ORDER_DESCENDING;
                                       		statusImage = upArrowImg;
                                       	}
                                       	else {
                                       		statusImage = downArrowImg;
                                       	}
                                   	}
                                	if (Constants.ORDERS_SORT_FIELD_LOCATION_NAME.equalsIgnoreCase(sortField)) {//
                                       	if (Constants.LOCATION_SORT_ORDER_ASCENDING.equalsIgnoreCase(sortOrder)) {
                                       		locationNameSortOrder = Constants.ORDERS_SORT_ORDER_DESCENDING;
                                       		locationNameImage = upArrowImg;
                                       	}
                                       	else {
                                       		locationNameImage = downArrowImg;
                                       	}
                                   	}
                                	if (Constants.ORDERS_SORT_FIELD_PLACED_BY.equalsIgnoreCase(sortField)) {//
                                       	if (Constants.LOCATION_SORT_ORDER_ASCENDING.equalsIgnoreCase(sortOrder)) {
                                       		placedBySortOrder = Constants.ORDERS_SORT_ORDER_DESCENDING;
                                       		placedByImage = upArrowImg;
                                       	}
                                       	else {
                                       		placedByImage = downArrowImg;
                                       	}
                                   	}
                                	if (Constants.ORDERS_SORT_FIELD_PRICE.equalsIgnoreCase(sortField)) {//
                                       	if (Constants.LOCATION_SORT_ORDER_ASCENDING.equalsIgnoreCase(sortOrder)) {
                                       		priceSortOrder = Constants.ORDERS_SORT_ORDER_DESCENDING;
                                       		priceImage = upArrowImg;
                                       	}
                                       	else {
                                       		priceImage = downArrowImg;
                                       	}
                                   	}
                                    
                                    %>
                                    <!-- hard code width to table for wider layouts style="width: 998px;" -->
                                    <table class="order">
                                        <colgroup>
                                            <col class="order">
											<col class="PONumber">
											<col class="orderDate">
											<col class="status">
											<col class="locationName">
											<col class="placedBy">
											<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">  
											   <col class="total">
											</logic:equal>
											
                                        </colgroup>
                                        <thead>
                                        
                                            <tr>
                                                <th>
                                                	<html:link href="javascript:submitForm('orderNum')">
                                                    		<app:storeMessage  key="orders.allOrders.table.header.orderNumber" />
                                                    	<%=orderNumImage%>
                                                    </html:link>
                                                	
                                                </th>
                                                <th>
                                                	<html:link href="javascript:submitForm('poNum')">
                                                    		<app:storeMessage  key="orders.filterPane.label.poNumber" />
                                                    	<%=poNumImage%>
                                                    </html:link>
                                                	
                                                </th>
                                                <th class="sort descending">
                                                	<html:link href="javascript:submitForm('orderDate')">
                                                    		<app:storeMessage  key="orders.allOrders.table.header.orderDate" />
                                                    	<%=orderDateImage%>
                                                    </html:link>
                                                	
                                                </th>
                                                <th>
                                                	<html:link href="javascript:submitForm('status')">
                                                    		<app:storeMessage  key="orders.allOrders.table.header.status" />
                                                    	<%=statusImage%>
                                                    </html:link>
                                                	
                                                </th>
                                                <th>
                                                	<html:link href="javascript:submitForm('locationName')">
                                                    		<app:storeMessage  key="orders.allOrders.table.header.location" />
                                                    	<%=locationNameImage%>
                                                    </html:link>
                                                	
                                                </th>
                                                <th>
                                                	<html:link href="javascript:submitForm('placedBy')">
                                                    		<app:storeMessage  key="orders.allOrders.table.header.placedBy" />
                                                    	<%=placedByImage%>
                                                    </html:link>
                                               		 
                                                </th>
                                                <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">  
                                                   <th>
                                                	<html:link href="javascript:submitForm('price')">
                                                    		<app:storeMessage  key="orders.allOrders.table.header.orderTotal" />
                                                    	<%=priceImage%>
                                                    </html:link>
                                                    
                                                   </th>
                                                </logic:equal>
                                                <html:form styleId="orderNum" action="<%=ordersSortLink%>">
                   		                            	<html:hidden property="<%=Constants.PARAMETER_OPERATION%>"
                    									value="<%=Constants.PARAMETER_OPERATION_VALUE_SORT_ORDERS%>"/>
                   		                            	<html:hidden property="ordersSearchInfo.sortField"
                    									value="<%=Constants.ORDERS_SORT_FIELD_ORDER_NUMBER%>"/>
                   		                            	<html:hidden property="ordersSearchInfo.sortOrder"
                    									value="<%=orderNumSortOrder%>"/>
			                                    	<%
			                                    		//include hidden fields to carry forward information about any 
			                                    		//previous search start
			                                    	%>
			                                        	
			                    						
			                                    	<%
			                                    		//include hidden fields to carry forward information about any 
			                                    		//previous search end
			                                    	%>
                    							</html:form>
                    							 <html:form styleId="poNum" action="<%=ordersSortLink%>">
                   		                            	<html:hidden property="<%=Constants.PARAMETER_OPERATION%>"
                    									value="<%=Constants.PARAMETER_OPERATION_VALUE_SORT_ORDERS%>"/>
                   		                            	<html:hidden property="ordersSearchInfo.sortField"
                    									value="<%=Constants.ORDERS_SORT_FIELD_PO_NUMBER%>"/>
                   		                            	<html:hidden property="ordersSearchInfo.sortOrder"
                    									value="<%=poNumSortOrder%>"/>
			                                    	<%
			                                    		//include hidden fields to carry forward information about any 
			                                    		//previous search start
			                                    	%>
			                                        	
			                    						
			                                    	<%
			                                    		//include hidden fields to carry forward information about any 
			                                    		//previous search end
			                                    	%>
                    							</html:form>
                    							<html:form styleId="orderDate" action="<%=ordersSortLink%>">
                   		                            	<html:hidden property="<%=Constants.PARAMETER_OPERATION%>"
                    									value="<%=Constants.PARAMETER_OPERATION_VALUE_SORT_ORDERS%>"/>
                   		                            	<html:hidden property="ordersSearchInfo.sortField"
                    									value="<%=Constants.ORDERS_SORT_FIELD_ORDER_DATE%>"/>
                   		                            	<html:hidden property="ordersSearchInfo.sortOrder"
                    									value="<%=orderDateSortOrder%>"/>
			                                    	<%
			                                    		//include hidden fields to carry forward information about any 
			                                    		//previous search start
			                                    	%>
			                                        	
			                    						
			                                    	<%
			                                    		//include hidden fields to carry forward information about any 
			                                    		//previous search end
			                                    	%>
                    							</html:form>
                    							<html:form styleId="status" action="<%=ordersSortLink%>">
                   		                            	<html:hidden property="<%=Constants.PARAMETER_OPERATION%>"
                    									value="<%=Constants.PARAMETER_OPERATION_VALUE_SORT_ORDERS%>"/>
                   		                            	<html:hidden property="ordersSearchInfo.sortField"
                    									value="<%=Constants.ORDERS_SORT_FIELD_STATUS%>"/>
                   		                            	<html:hidden property="ordersSearchInfo.sortOrder"
                    									value="<%=statusSortOrder%>"/>
			                                    	<%
			                                    		//include hidden fields to carry forward information about any 
			                                    		//previous search start
			                                    	%>
			                                        	
			                    						
			                                    	<%
			                                    		//include hidden fields to carry forward information about any 
			                                    		//previous search end
			                                    	%>
                    							</html:form>
                    							<html:form styleId="locationName" action="<%=ordersSortLink%>">
                   		                            	<html:hidden property="<%=Constants.PARAMETER_OPERATION%>"
                    									value="<%=Constants.PARAMETER_OPERATION_VALUE_SORT_ORDERS%>"/>
                   		                            	<html:hidden property="ordersSearchInfo.sortField"
                    									value="<%=Constants.ORDERS_SORT_FIELD_LOCATION_NAME%>"/>
                   		                            	<html:hidden property="ordersSearchInfo.sortOrder"
                    									value="<%=locationNameSortOrder%>"/>
			                                    	<%
			                                    		//include hidden fields to carry forward information about any 
			                                    		//previous search start
			                                    	%>
			                                        	
			                    						
			                                    	<%
			                                    		//include hidden fields to carry forward information about any 
			                                    		//previous search end
			                                    	%>
                    							</html:form>
                    							<html:form styleId="placedBy" action="<%=ordersSortLink%>">
                   		                            	<html:hidden property="<%=Constants.PARAMETER_OPERATION%>"
                    									value="<%=Constants.PARAMETER_OPERATION_VALUE_SORT_ORDERS%>"/>
                   		                            	<html:hidden property="ordersSearchInfo.sortField"
                    									value="<%=Constants.ORDERS_SORT_FIELD_PLACED_BY%>"/>
                   		                            	<html:hidden property="ordersSearchInfo.sortOrder"
                    									value="<%=placedBySortOrder%>"/>
			                                    	<%
			                                    		//include hidden fields to carry forward information about any 
			                                    		//previous search start
			                                    	%>
			                                        	
			                    						
			                                    	<%
			                                    		//include hidden fields to carry forward information about any 
			                                    		//previous search end
			                                    	%>
                    							</html:form>
                    							<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">  
                    							   <html:form styleId="price" action="<%=ordersSortLink%>">
                   		                            	<html:hidden property="<%=Constants.PARAMETER_OPERATION%>"
                    									value="<%=Constants.PARAMETER_OPERATION_VALUE_SORT_ORDERS%>"/>
                   		                            	<html:hidden property="ordersSearchInfo.sortField"
                    									value="<%=Constants.ORDERS_SORT_FIELD_PRICE%>"/>
                   		                            	<html:hidden property="ordersSearchInfo.sortOrder"
                    									value="<%=priceSortOrder%>"/>
			                                    	<%
			                                    		//include hidden fields to carry forward information about any 
			                                    		//previous search start
			                                    	%>
			                                        	
			                    						
			                                    	<%
			                                    		//include hidden fields to carry forward information about any 
			                                    		//previous search end
			                                    	%>
                    							   </html:form>
                    							</logic:equal>
                                            </tr>
                                        </thead>
                                        <logic:notEmpty name="esw.OrdersForm" property="ordersSearchInfo.matchingOrders">

                                        <tbody>
	                                        	<logic:iterate id="order" name="esw.OrdersForm" property="ordersSearchInfo.matchingOrders"
	 												indexId="orderIndex" type="com.cleanwise.service.api.value.OrderStatusDescData">
	 												<%
	 													if(orderIndex>=500) {
	 														break;	
	 													}
	 												%>
	                                            <tr>
	                                                <td>
	                                                    <%
	                                                       String orderStatus = ((OrderStatusDescData) order).getOrderDetail().getOrderStatusCd();
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
	                                                </td>
	                                                <td>
	                                                	<bean:write name="order" property="orderDetail.requestPoNum" />
	                                                </td>
	                                                <td>
	                                                <% 
	                                                	OrderData orderData = ((OrderStatusDescData) order).getOrderDetail();
	                                                	Date date = orderData.getOriginalOrderDate();
														Date time = orderData.getOriginalOrderTime();
														Date orderDate = Utility.getDateTime(date, time);
															
														String formattedODate = Utility.encodeForHTML(ClwI18nUtil.formatDateInp(request, orderDate));
													%>
	                                                
	                                                <%=formattedODate %>
	                                                </td>
	                                                <td>
	                                                <%  String style = "";
	                                                	if(orderStatus!=null && (orderStatus.trim().equals(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL) ||
	                                                			orderStatus.trim().equals(RefCodeNames.ORDER_STATUS_CD.PENDING_DATE) ||
	                                                			orderStatus.trim().equals(RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION))) {
	                                                		style = "alert";
	                                                	}
	                                                	String i18nStatusCode = Utility.encodeForHTML(ShopTool.xlateStatus(order, request));
								
								if(!style.equals("")){
	                                                %>	
								<span class="<%=style %>" >
													
							<% } %>
														<%=i18nStatusCode  %>
							
							
														<%
															//if the order status is "Pending Date", show the date that the order
															//will be submitted
															if (RefCodeNames.ORDER_STATUS_CD.PENDING_DATE.equals(orderStatus) &&
																Utility.isSet(order.getPendingDate())) {
																Date processOnDate = new SimpleDateFormat("MM/dd/yyyy").parse(order.getPendingDate());
														%>
															<br>(<%=Utility.encodeForHTML(ClwI18nUtil.formatDateInp(request, processOnDate))%>)
														<% 
															}
															if (RefCodeNames.ORDER_STATUS_CD.CANCELLED.equals(orderStatus) &&
      															order.getConsolidatedOrder()!= null) { 
      													%>
															<app:storeMessage key="shop.orderStatus.text.consolidated" />
														<% 
															} 
														%>
	                                                <%if(!style.equals("")){%>
							</span>
							<% } %>
	                                                </td>
	                                                
	                                                <td>
	                                                 <%
														//display information about the location
														//STJ-4689.
                                                		AddressData address;
	                                                 	//STJ-5755.
                                                		if (((OrderStatusDescData) order).getShipTo() != null) {
                                                			address = Utility.toAddress(((OrderStatusDescData) order).getShipTo());
                                                			//address = ((OrderStatusDescData) order).getSiteAddress();
                                                		}
                                                		else {
                                                			address = AddressData.createValue();
                                                		}
                                                		
                                                		String siteName = Utility.encodeForHTML(((OrderStatusDescData) order).getOrderDetail().getOrderSiteName());
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
								                   		
								                    %>
									               	<eswi18n:formatAddress name ="<%=siteName %>" address1="<%=address1 %>" postalCode="<%=postalCode %>"  
                                									city="<%=city %>" state="<%=state %>" 
                                									addressFormat="<%=addressFormat %>"/>
	                                               </td>
	                                                <td>
	                                                
														 <logic:present name="order" property="placedBy">
															   <logic:present name="order" property="placedBy.firstName">
															   		<bean:write name="order" property="placedBy.firstName" /> 
															   </logic:present>
															    &nbsp;
															   <logic:present name="order" property="placedBy.lastName">
															   	<bean:write name="order" property="placedBy.lastName" />
															   </logic:present>
														  </logic:present>
														  <!-- (<bean:write name="order" property="orderDetail.addBy" />)--> 
	                                                </td>
	                                                <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">  
	                                                   <td class="right">
	                                                   <%
														java.math.BigDecimal discountedTotal =  order.getEstimatedTotal();
														
														String orderLocale = order.getOrderDetail().getLocaleCd();
														%>
														<%=ClwI18nUtil.priceFormat(request, discountedTotal, (String) orderLocale," ")%>
														
	                                                   </td>
	                                                </logic:equal>
	                                            </tr>
	                                            </logic:iterate>
                                           
                                            
                                        </tbody>
										
                                        </logic:notEmpty>
                                    </table>
                                	<logic:notEmpty name="esw.OrdersForm" property="ordersSearchInfo.matchingOrders">
									<!--  TODO: Needs to impliment pagination
                                    <div class="pagination">
                                        <span class="display">Display <select>
                                            <option>10 per page</option>
                                            <option>20 per page</option>
                                            <option>50 per page</option>
                                        </select></span>
                                        <div class="pagination clearfix">
                                            <a class="btn nextBtn"><span>Next</span></a>
                                            <div class="pages">
                                                <a class="current" href="#">1</a>
                                                <a href="#">2</a>
                                                <a href="#">3</a>
                                                <a href="#">4</a>
                                                <a href="#">5</a> -->
                                                <!-- links to next set of 5 -->
                                          <!--        <a href="#">.....</a>
                                                <a href="#">100</a>
                                            </div>
                                            <a class="btn prevBtn"><span>Previous</span></a>
                                        </div>
                                    </div>
									-->
									 </logic:notEmpty>
                                </div>
                            </div>
                            <!-- Begin: Orders Left Panel (Filters Panel) -->
                            
                             <%
								String filterPanel = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "ordersFiltersPanel.jsp");
							 %>
							 
		                   	 <jsp:include page="<%=filterPanel %>" >
		                   	 	<jsp:param name="formBeanName" value="esw.OrdersForm"/>
		                   	 	<jsp:param name="panelTitle" value='<%=ClwMessageResourcesImpl.getMessage(request,"reporting.label.filters")%>'/>
		                   	 </jsp:include>
                   	
                            <!-- End: Orders Left Panel (Filters Panel) -->
                        </div>
                    </div>
                    <div class="bottom clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
                </div>
                <!-- End Box -->
            </div>
        </div>   
                