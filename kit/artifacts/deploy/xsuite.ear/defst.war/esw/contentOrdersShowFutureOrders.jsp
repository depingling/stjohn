<%@page import="com.cleanwise.service.api.util.SessionDataUtil"%>
<%@page import="com.cleanwise.service.api.dto.OrderSearchDto"%>
<%@page import="com.cleanwise.service.api.dto.FutureOrderDto"%>
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
<%@ page import="com.cleanwise.service.api.value.OrderAddressData" %>
<%@ page import="com.cleanwise.service.api.value.OrderMetaData" %>
<%@ page import="com.cleanwise.service.api.value.ShoppingCartData" %>
<%@ page import="com.cleanwise.service.api.value.ContractData" %>
<%@ page import="com.cleanwise.service.api.value.SiteData" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="org.apache.struts.util.LabelValueBean" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>

<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<bean:define id="myForm" name="esw.OrdersForm"  type="com.espendwise.view.forms.esw.OrdersForm"/>
<%

    CleanwiseUser user = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
    int contractId = 0;
    SiteData currentLocation = ShopTool.getCurrentSite(request);
    if(currentLocation != null){
        contractId = currentLocation.getContractData().getContractId();
    }
    
    ShoppingCartData inventoryCart =(ShoppingCartData) session.getAttribute(Constants.INVENTORY_SHOPPING_CART);
    //Get OrderSearchInfo from the session.
    SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
    OrderSearchDto futureOrderSearchInfo = sessionDataUtil.getFutureOrderSearchDto();

    if(futureOrderSearchInfo==null){
    	futureOrderSearchInfo = myForm.getFutureOrdersSearchInfo();
    } 
    myForm.setFutureOrdersSearchInfo(futureOrderSearchInfo);
    
    String dateRangeLabel = "";
	String selectedDateRange = myForm.getFutureOrdersSearchInfo().getDateRange();
	if (Utility.isSet(selectedDateRange)) {
		Iterator<LabelValueBean> dateRangeChoiceIterator = myForm.getFutureOrdersDateRangeFieldChoices().iterator();
		/*while (dateRangeChoiceIterator.hasNext() && !Utility.isSet(dateRangeLabel)) {
			LabelValueBean dateRangeChoice = dateRangeChoiceIterator.next();
			if (Utility.isSet(dateRangeChoice.getValue()) 
					&& dateRangeChoice.getValue().equalsIgnoreCase(selectedDateRange)) {
				dateRangeLabel = dateRangeChoice.getLabel();
			}
		}*/
		//append the start/end dates to the label
		if (Utility.isSet(myForm.getFutureOrdersSearchInfo().getFrom()) &&
				Utility.isSet(myForm.getFutureOrdersSearchInfo().getTo())) {
			SimpleDateFormat dateFormat = new SimpleDateFormat(ClwI18nUtil.getDatePattern(request));
			try {
				Date startDate = dateFormat.parse(myForm.getFutureOrdersSearchInfo().getFrom());
				Date endDate = dateFormat.parse(myForm.getFutureOrdersSearchInfo().getTo());
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
    
    String viewFutureOrdersFormId = "viewFutureOrdersFormId";
    String viewFutureOrdersLink = "userportal/esw/orders.do";
    
    StringBuilder showOrderLink = new StringBuilder(50);
    showOrderLink.append("showOrderDetail.do?");
    showOrderLink.append(Constants.PARAMETER_OPERATION);
    showOrderLink.append("=");
    showOrderLink.append(Constants.PARAMETER_OPERATION_VALUE_SHOW_ORDER);
    showOrderLink.append("&");
    showOrderLink.append(Constants.PARAMETER_ORDER_ID);
    showOrderLink.append("=");
    
    StringBuilder showCorporateOrderLink = new StringBuilder(50);
    showCorporateOrderLink.append("shopping.do?");
    showCorporateOrderLink.append(Constants.PARAMETER_OPERATION);
    showCorporateOrderLink.append("=");
    showCorporateOrderLink.append(Constants.PARAMETER_OPERATION_VALUE_SHOW_CORPORATE_ORDER);
    
    
    StringBuilder showOrderScheduleLink = new StringBuilder(50);
    showOrderScheduleLink.append("orders.do?");
    showOrderScheduleLink.append(Constants.PARAMETER_OPERATION);
    showOrderScheduleLink.append("=");
    showOrderScheduleLink.append(Constants.PARAMETER_OPERATION_VALUE_SHOW_ORDER_SCHEDULE);
    showOrderScheduleLink.append("&");
    showOrderScheduleLink.append(Constants.PARAMETER_ORDER_SCHEDULE_ID);
    showOrderScheduleLink.append("=");
    
    StringBuilder showCorporateOrderScheduleLink = new StringBuilder(50);
    showCorporateOrderScheduleLink.append("orders.do?");
    showCorporateOrderScheduleLink.append(Constants.PARAMETER_OPERATION);
    showCorporateOrderScheduleLink.append("=");
    showCorporateOrderScheduleLink.append(Constants.PARAMETER_OPERATION_VALUE_SHOW_CORPORATE_ORDER_SCHEDULE);
    showCorporateOrderScheduleLink.append("&");
    showCorporateOrderScheduleLink.append(Constants.PARAMETER_ORDER_SCHEDULE_ID);
    showCorporateOrderScheduleLink.append("=");
    
    StringBuilder showShoppingListLink = new StringBuilder(50);
    showShoppingListLink.append("shopping.do?");
    showShoppingListLink.append(Constants.PARAMETER_OPERATION);
    showShoppingListLink.append("=");
    showShoppingListLink.append("orderGuideSelect");
    showShoppingListLink.append("&");
    showShoppingListLink.append("editOrderGuideForm.orderGuideId");
    showShoppingListLink.append("=");

    Iterator i =  futureOrderSearchInfo.getMatchingOrders().iterator();

    boolean orderWillNotBePlaced = false;
    if (user.getShowPrice()) {
        while (i.hasNext()) {
            FutureOrderDto order = (FutureOrderDto)i.next();
            BigDecimal orderTotal;
			if (Utility.isSet(order.getOrderTotal())) {
				try {
					orderTotal = new BigDecimal(order.getOrderTotal());
				} catch (Exception e) {
					orderTotal = new BigDecimal(0);
				}
				if (Utility.isZeroValue(orderTotal)) {
					orderWillNotBePlaced = true;
				}
			}
        }
    }
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

        <div id="contentWrapper" class="singleColumn noHeader clearfix">
            <div id="content">      
                <!-- Start Box -->
                <div class="boxWrapper">
			<div class="content">
                        <div class="left clearfix">
                            <%
                               	//if no site is selected then do not render anything (there will be an
                               	//error displayed above telling the user they must select a site)
                               	if (user.getSite() != null) {
                                
                                             
                           %>
                        
			    			<h2>
								<app:storeMessage key="shopping.orders.label.futureOrders" />  <%=dateRangeLabel%>
							</h2>
                            <%
                           	StringBuilder href = new StringBuilder(50);
                           	href.append("javascript:submitForm('");
                           	href.append(viewFutureOrdersFormId);
                           	href.append("')");
                            %>
                           <html:form styleId="<%=viewFutureOrdersFormId%>" action="<%=viewFutureOrdersLink%>">
                           <html:hidden property="<%=Constants.PARAMETER_OPERATION%>"
        									value="<%=Constants.PARAMETER_OPERATION_VALUE_SHOW_FUTURE_ORDERS%>"/>
                             <p class="right topMargin"> 
                   		<app:storeMessage key="previousOrder.search.label.dateRange" />&nbsp;
        			<html:select property="futureOrdersSearchInfo.dateRange"
        									onchange="<%=href.toString()%>">
	                        <html:optionsCollection name="esw.OrdersForm"
	                            				property="futureOrdersDateRangeFieldChoices" label="label" value="value"/>
        			</html:select>
        		    </p>
                            </html:form>
                            <hr class="smallMargin"/>
                            <logic:notEmpty name="esw.OrdersForm" property="futureOrdersSearchInfo.matchingOrders">
                            <%    if (orderWillNotBePlaced) { %>
                                <p class="required topMargin right"><app:storeMessage key="futureOrders.label.orderWillNotBePlaced" /></p>
                            <%   } %>
                            </logic:notEmpty>

                            <!--<table class="mostVisited verticalCenter">-->

				<table class="order">
                                <colgroup>
				    <col />
                                    <col class="openDate" />	
				    <col />
                                    <col class="ordersubtotal" />						
                                </colgroup>
                                <thead>
                                    <tr>
                                       								
					<th><app:storeMessage key="futureOrders.label.schedule" /></th>
                                        <% if(user.getSite().hasInventoryShoppingOn()){ %>
                                            <th><app:storeMessage key="futureOrders.label.openCorporateOrderDates" /></th>
                                        <% } %>
                                        <th><app:storeMessage key="futureOrders.label.releaseDate" /></th>
                                        <th><app:storeMessage key="futureOrders.label.orderContents" /></th>
                                        <th><app:storeMessage key="futureOrders.label.estimatedTotal" /></th>
                                        
                                    </tr>
                                </thead>
                                <tbody>
                                    <logic:notEmpty name="esw.OrdersForm" property="futureOrdersSearchInfo.matchingOrders">
				    <logic:iterate id="order" indexId="orderIndex"
				                    name="esw.OrdersForm" property="futureOrdersSearchInfo.matchingOrders" 
				                    type="com.cleanwise.service.api.dto.FutureOrderDto">
                                    
                                    <%
                                        String orderType = order.getOrderType();
                                        String orderTotal = order.getOrderTotal();
                                        String scheduleLink = null;
                                        String orderLink = null;
                                        String contentName = null;
                                        
                                        if(orderType.equals(RefCodeNames.FUTURE_ORDER_TYPE.PENDING_DATE_ORDER)){
                                            orderLink = showOrderLink.toString() + order.getContentId();
                                            contentName = order.getOrderContent();
                                        }else if(orderType.equals(RefCodeNames.FUTURE_ORDER_TYPE.CORPORATE_ORDER_SCHEDULE)){
                                            if(Utility.isSet(order.getOrderTotal())){
                                                orderLink = showCorporateOrderLink.toString();
                                                scheduleLink = showCorporateOrderScheduleLink.toString() + order.getContentId();
                                            }
                                            contentName = order.getOrderContent();
                                        }else if(orderType.equals(RefCodeNames.FUTURE_ORDER_TYPE.SHOPPING_LIST_SCHEDULE)){
                                            String ogId = order.getOrderContent().split("-")[0];
                                            contentName = order.getOrderContent().split("-")[1];
                                            if(order.getUserSharedList()){                 
                                                orderLink = showShoppingListLink.toString() + ogId;
                                                scheduleLink = showOrderScheduleLink.toString() + order.getContentId();
                                            }
                                        }
                                        
                                    %>
                                    
                                    <tr>
                                        <!--Schedule -->
                                        <td>
                                        <%if(scheduleLink!=null){ %>                                           
                                            <html:link href="<%=scheduleLink%>"><bean:write name="order" property="schedule"/>
                                            <%if(orderType.equals(RefCodeNames.FUTURE_ORDER_TYPE.SHOPPING_LIST_SCHEDULE)){%>
                                            <br /> (<bean:write name="order" property="scheduleBeginDate"/> - <bean:write name="order" property="scheduleEndDate"/>)
                                            <%}%>
                                            </html:link>     
                                        <% }else{%>
                                            <bean:write name="order" property="schedule"/>
                                        <%} %>
                                        </td>
                                        
                                        <!--Open PAR Dates -->
                                        <% if(user.getSite().hasInventoryShoppingOn()){ 
                                                if(orderType.equals(RefCodeNames.FUTURE_ORDER_TYPE.CORPORATE_ORDER_SCHEDULE)){ %>
                                        
                                                <td><bean:write name="order" property="scheduleBeginDate"/> - <bean:write name="order" property="scheduleEndDate"/></td>
    
                                                <%}else{%>
                                                <td></td>
                                                <%}
                                        } %>
                                        
                                        <!--Release Date -->
                                        
                                        <td><bean:write name="order" property="releaseDate"/></td>
                                        
                                        <!--Order Content -->
                                        <td>
                                            <%if(orderLink != null){%>
                                            <html:link href="<%=orderLink%>"><%=Utility.encodeForHTML(contentName)%></html:link>
                                            <%}else{%>
                                                <%=contentName%>
                                            <%}%>
                                        </td>
                                        
                                        <!--Estimated Total -->
                                        <td class="right">
                                            <%if(orderType.equals(RefCodeNames.FUTURE_ORDER_TYPE.PENDING_DATE_ORDER)){
                                                int orderId = order.getContentId();
                                                
                                            %>
                                                <app:formatPriceCurrency price="<%=orderTotal%>"
                                                        orderId="<%=orderId%>"
                                                        checkZero="true"
                                                        zeroClass="required"
                                                        appendSymbol="*"/>
                                            
                                            <% }else{ %>
                                                <app:formatPriceCurrency price="<%=orderTotal%>"
                                                        contractId="<%=contractId%>"
                                                        checkZero="true"
                                                        zeroClass="required"
                                                        appendSymbol="*"/>
                                            <% } %>
                                        </td>
                                        
                                    </tr>
                                    </logic:iterate>
                                    </logic:notEmpty>
                                    
                                </tbody>

                            </table>
							<!--<a href="#" class="blueBtnLarge right" class="right"><span>Create Auto Order</span></a>-->
                       
			 <%
                             }
                        %>		
                    </div>
                </div>
                    <div class="bottom clearfix">
                        <span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span>
                    </div>
					
                </div>
                
                
            </div>

        </div>
