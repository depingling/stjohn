<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.service.api.value.AddressData" %>
<%@ page import="com.cleanwise.service.api.value.OrderAddressData" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="org.apache.struts.util.LabelValueBean" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>

<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri='/WEB-INF/eswI18n.tld' prefix='eswi18n' %>

<bean:define id="myForm" name="esw.DashboardForm"  type="com.espendwise.view.forms.esw.DashboardForm"/>

<%
	CleanwiseUser user = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

	//determine what label should be shown for the selected time period
	String dateRangeLabel = "";
	String selectedDateRange = myForm.getPreviousOrderSearchInfo().getDateRange();
	if (Utility.isSet(selectedDateRange)) {
		Iterator<LabelValueBean> dateRangeChoiceIterator = myForm.getPreviousOrderDateRangeChoices().iterator();
		while (dateRangeChoiceIterator.hasNext() && !Utility.isSet(dateRangeLabel)) {
			LabelValueBean dateRangeChoice = dateRangeChoiceIterator.next();
			if (Utility.isSet(dateRangeChoice.getValue()) 
					&& dateRangeChoice.getValue().equalsIgnoreCase(selectedDateRange)) {
				dateRangeLabel = dateRangeChoice.getLabel();
			}
		}
		//append the start/end dates to the label
		if (Utility.isSet(myForm.getPreviousOrderSearchInfo().getFrom()) &&
				Utility.isSet(myForm.getPreviousOrderSearchInfo().getTo())) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(ClwI18nUtil.getDatePattern(request));
			try {
				Date startDate = dateFormat.parse(myForm.getPreviousOrderSearchInfo().getFrom());
				Date endDate = dateFormat.parse(myForm.getPreviousOrderSearchInfo().getTo());
				StringBuilder labelBuilder = new StringBuilder(dateRangeLabel);
				labelBuilder.append(" (");
				labelBuilder.append(Utility.encodeForHTML(ClwI18nUtil.formatDateInp(request, startDate)));
				labelBuilder.append(" - ");
				labelBuilder.append(Utility.encodeForHTML(ClwI18nUtil.formatDateInp(request, endDate)));
				labelBuilder.append(")");
				dateRangeLabel = labelBuilder.toString();
			}
			catch (Exception e) {
				//nothing to do here - either the start or end date was not parseable, which means there was
				//an error upstream that should be displayed to the user.
			}
		}
	}
	
	String viewPreviousOrdersFormId = "viewPreviousOrdersFormId";
	String viewPreviousOrdersLink = "userportal/esw/dashboard.do";
    
    StringBuilder showOrderLink = new StringBuilder(50);
    //showOrderLink.append("orders.do?"); //commented by SVC on 8/17/2011
    showOrderLink.append("showOrderDetail.do?"); //added by SVC on 8/17/2011
    showOrderLink.append(Constants.PARAMETER_OPERATION);
    showOrderLink.append("=");
    showOrderLink.append(Constants.PARAMETER_OPERATION_VALUE_SHOW_ORDER);
    showOrderLink.append("&");
    showOrderLink.append(Constants.PARAMETER_ORDER_ID);
    showOrderLink.append("=");
    

	Map<String, String> orderLocales = new HashMap<String, String>();
    BigDecimal overallTotal = new BigDecimal(0);
%>

<app:setLocaleAndImages/>
		<%
			String errorsAndMessagesPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "errorsAndMessages.jsp");
		%>
        <jsp:include page="<%=errorsAndMessagesPage %>"/>
		<!-- Begin: Shopping Sub Tabs -->
        <%
			String shoppingTabPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "shoppingTabs.jsp");
		%>
        <jsp:include page="<%=shoppingTabPage%>"/>
      	<!-- End: Shopping Sub Tabs -->
        <div id="contentWrapper" class="clearfix">
            <div id="content">
                <!-- Start Right Column -->
                <div class="rightColumn">
                    <div class="rightColumnIndent">
						<%
							String locationBudgetPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "dashboardLocationBudget.jsp");
						%>
                    	<jsp:include page="<%=locationBudgetPage %>"/>
                    
                        <!-- Start Box -->
                        <div class="boxWrapper">
							<%
								String tabPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "dashboardTabs.jsp");
							%>
	                    	<jsp:include page="<%=tabPage%>"/>
                            <div class="content">
                                <div class="left clearfix">
                               	<%
                               		//if no site is selected then do not render anything (there will be an
                               		//error displayed above telling the user they must select a site)
                               		if (user.getSite() != null) {
                               	%>
                           			<%
                           				StringBuilder href = new StringBuilder(50);
                           				href.append("javascript:submitForm('");
                           				href.append(viewPreviousOrdersFormId);
                           				href.append("')");
                           			%>
                                	<html:form styleId="<%=viewPreviousOrdersFormId%>" action="<%=viewPreviousOrdersLink%>">
       		                            <html:hidden property="<%=Constants.PARAMETER_OPERATION%>"
        									value="<%=Constants.PARAMETER_OPERATION_VALUE_SHOW_PREVIOUS_ORDERS%>"/>
                                       	<html:hidden property="previousOrderSearchInfo.userId"
                   							value="<%=Integer.toString(user.getUserId())%>"/>
                                	</html:form>
                                   
                                    <div class="tableWrapper">
                                        <!-- hard code width to table for wider layouts style="width: 998px;" -->
                                        <table class="order">
                                            <colgroup>
                                                <col class="order" />
                                                <col class="orderDate" />
                                                <col class="status" />
                                                <col class="locationName" />
                                                <col class="total" />
                                            </colgroup>
                                            <thead>
                                                <tr>
                                                    <th>
                                                        <app:storeMessage key="dashboard.label.orderNumber"/>
                                                    </th>
                                                    <th>
                                                        <app:storeMessage key="dashboard.label.ordered"/>
                                                    </th>
                                                    <th>
                                                        <app:storeMessage key="dashboard.label.status"/>
                                                    </th>
                                                    <th>
                                                        <app:storeMessage key="dashboard.label.location"/>
                                                    </th>
                                                    <th class="right">
                                                        <app:storeMessage key="dashboard.label.orderTotal"/>
                                                    </th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                            	<logic:notEmpty name="esw.DashboardForm" property="previousOrderSearchInfo.matchingOrders">
				                        			<logic:iterate id="order" indexId="orderIndex"
				                        		 		name="esw.DashboardForm" property="previousOrderSearchInfo.matchingOrders" 
				                        		 		type="com.cleanwise.service.api.value.OrderStatusDescData" length="10">
														<bean:define id="orderStatus"  type="java.lang.String"  
															name="order" property="orderDetail.orderStatusCd"/>
													<%
														String translatedOrderStatus = ShopTool.xlateStatus(order, request);
														boolean isOrderComplete = (orderStatus != null && 
																!orderStatus.equals(RefCodeNames.ORDER_STATUS_CD.ORDERED_PROCESSING));
														boolean isReceivedOrder = RefCodeNames.ORDER_STATUS_CD.RECEIVED.equalsIgnoreCase(orderStatus);
														String orderLocale = order.getOrderDetail().getLocaleCd();
														if (orderLocale==null) {
															orderLocale = "";
														}
													%>
                                                		<tr>
                                                    		<td>
                                                    		<%	if (!isOrderComplete || isReceivedOrder) {
                                                    		%>
                                                    				<bean:write name="order" property="orderDetail.orderNum"/>
	                                                		<%
                                                    			}
                                                    			else {
		                                                			String orderLink = showOrderLink.toString() + order.getOrderDetail().getOrderId();
	                                                		%>
	                                                    			<html:link href="<%=orderLink%>">
	                                                    				<bean:write name="order" property="orderDetail.orderNum"/>
	                                                    			</html:link>
	                                                    	<%
                                                    			}
	                                                    	%>
                                                    		</td>
                                                    		<td>
                                                    			<logic:notEmpty name="order" property="orderDetail.revisedOrderDate">
			                                                    	<%=Utility.encodeForHTML(ClwI18nUtil.formatDateInp(request, order.getOrderDetail().getRevisedOrderDate()))%>
			                                                    </logic:notEmpty>
                                                    			<logic:empty name="order" property="orderDetail.revisedOrderDate">
			                                                    	<%=Utility.encodeForHTML(ClwI18nUtil.formatDateInp(request, order.getOrderDetail().getOriginalOrderDate()))%>
			                                                    </logic:empty>
                                                    		</td>
                                                    		<td>
				                                                <%  String style = "";
				                                                	if (RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL.equals(orderStatus) ||
				                                                			RefCodeNames.ORDER_STATUS_CD.PENDING_DATE.equals(orderStatus) ||
				                                                			RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION.equals(orderStatus)) {
				                                                		style = "alert";
				                                                	}
											
											if(!style.equals("")){
				                                                %>
										
											<span class="<%=style%>">
												<%=Utility.encodeForHTML(translatedOrderStatus)%>
											</span>
										<% }else{ %>
												<%=Utility.encodeForHTML(translatedOrderStatus)%>
										<% } %>
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
		                	                						String country = Utility.encodeForHTML(address.getCountryCd());
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
									                        	<%-- <%=addressString.toString()%> --%>
									                        	<eswi18n:formatAddress name ="<%=locationName %>" address1="<%=address1 %>" postalCode="<%=postalCode %>"  
                                									city="<%=city %>" state="<%=state %>" 
                                									addressFormat="<%=addressFormat %>"/>
                                                    		</td>
                                                    		<td class="right">
																
																<%
																 	if (isOrderComplete && !isReceivedOrder) {
																 		overallTotal = overallTotal.add(order.getEstimatedTotal().setScale(2, BigDecimal.ROUND_HALF_UP));
																 		orderLocales.put(orderLocale.toLowerCase(), orderLocale);
																%>	
																		<app:formatPriceCurrency price="<%=order.getEstimatedTotal()%>"
																			orderId="<%=order.getOrderDetail().getOrderId()%>"/>
																<% 
																	} 
																 	else { 
																%>
																		&nbsp;
																<%
																	}
																%>
																
                                                    		</td>
                                                		</tr>
	                                            	</logic:iterate>
	                                    		</logic:notEmpty>
                                            </tbody>
                                        </table>
                                    </div>
                                    <%--
									<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
                                   		<logic:notEmpty name="esw.DashboardForm" property="previousOrderSearchInfo.matchingOrders">
                                   			<logic:notEmpty name="esw.DashboardForm" property="previousOrderSearchInfo.dateRange">
                                   				<%
                                   					if (orderLocales.size() == 1) {
                                   				%>
		                                    			<p class="total">
		                                    				<app:storeMessage key= "shop.orderStatus.text.total"/>:&nbsp;<%=ClwI18nUtil.formatAmount(overallTotal, orderLocales.values().iterator().next(), user.getUser().getPrefLocaleCd())%>
										
		                                    			</p>
                                    			<%
                                   					}
                                    			%>
                                    		</logic:notEmpty>
                                    	</logic:notEmpty>
                                    </logic:equal>
                                    --%>
                                <%
                               		}
                                %>
                                </div>
                            </div>
                            <div class="bottom clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
                        </div>
                        <!-- End Box -->
                    </div>
                </div>
                <!-- End Right Column -->
				<%
					String leftColumnPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "dashboardLeftColumn.jsp");
				%>
                <jsp:include page="<%=leftColumnPage%>"/>
            </div>
        </div>
                