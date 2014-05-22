<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>
<%@ page import="com.cleanwise.service.api.dto.LocationSearchDto" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.service.api.value.BusEntityData" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.Iterator" %>
<%@page import="com.cleanwise.service.api.value.ProductData"%>
<%@page import="com.cleanwise.service.api.value.ShoppingCartData"%>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>

<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<bean:define id="myForm" name="esw.DashboardForm"  type="com.espendwise.view.forms.esw.DashboardForm"/>
<%
//TODO - implement this page.  The HTML is basically set, but things like converting input
//to use struts tag libs, implementing links, etc all has to be done.  Best approach is to go
//through entire page line by line and implement whatever hasn't been done.
//TODO - handle case where there is no recent order.
	CleanwiseUser user = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
	boolean includeOrderActionControls = !user.isBrowseOnly();
%>
<app:setLocaleAndImages/>
<script type="text/javascript">
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
<logic:notEmpty name="esw.DashboardForm" property="mostRecentOrderSearchInfo.matchingOrders">
<bean:define id="mostRecentOrder" name="esw.DashboardForm"
    property="mostRecentOrderSearchInfo.matchingOrders[0]"
    type="com.cleanwise.service.api.value.OrderStatusDescData" /><%
String orderLocaleCd = mostRecentOrder.getOrderDetail().getLocaleCd();
String orderId = String.valueOf(mostRecentOrder.getOrderDetail().getOrderId());
                               		//if no site is selected then do not render anything (there will be an
                               		//error displayed above telling the user they must select a site)
                               		if (user.getSite() != null) {
						
					boolean cartHasItems = false;
					ShoppingCartData scD = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
					if(scD!=null && scD.getItemsQty() > 0){
						cartHasItems = true;
					}
                               			String href = "javascript:reorder('" + Constants.PARAMETER_OPERATION_VALUE_REORDER + "', '"+cartHasItems+"');";
                              			if (includeOrderActionControls) {
%>
										<html:form styleId="<%=Constants.PARAMETER_OPERATION_VALUE_REORDER%>" action="userportal/esw/dashboard.do">
											<html:hidden property="<%=Constants.PARAMETER_OPERATION%>"
												value="<%=Constants.PARAMETER_OPERATION_VALUE_REORDER%>"/>
      		                    			<html:hidden property="selectedOrderIds"
       											value="<%=orderId%>"/>
      									</html:form>
	                                    <html:link href="<%=href%>" styleClass="blueBtnMed right bottomMargin">
	                                    	<span>
	                                    		<app:storeMessage key= "global.action.label.reorder"/>
	                                    	</span>
	                                    </html:link>
	                            <%
                               		}
	                            %>
<%
String orderLink = "showOrderDetail.do?operation=showOrder&orderId="  + orderId;
String orderStatus = ShopTool.xlateStatus(mostRecentOrder, request);
String style = "";
if (RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL.equals(orderStatus) ||
		RefCodeNames.ORDER_STATUS_CD.PENDING_DATE.equals(orderStatus) ||
		RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION.equals(orderStatus)) {
	style = "alert";
}
%>
                                    <p><nobr>
<app:storeMessage key="mostRecentOrder.label.orderNumber"/>:&nbsp;<html:link href="<%=orderLink%>"><bean:write 
    name="mostRecentOrder" property="orderDetail.orderNum" /></html:link>&nbsp;&nbsp;(<span class="<%=style%>"><%=Utility.
    encodeForHTML(orderStatus)%></span>)&nbsp;&nbsp;&nbsp;&nbsp;<app:storeMessage key=
    "mostRecentOrder.label.PO#"/>:&nbsp;<bean:write name="mostRecentOrder"
    property="orderDetail.requestPoNum" />&nbsp;&nbsp;&nbsp;&nbsp;<app:storeMessage key=
    "mobile.esw.orders.label.order.ordered"/>:&nbsp;
<logic:notEmpty name="mostRecentOrder" property="orderDetail.revisedOrderDate">
<%=Utility.encodeForHTML(ClwI18nUtil.formatDateInp(request, mostRecentOrder.getOrderDetail().getRevisedOrderDate()))%>
</logic:notEmpty>
<logic:empty name="mostRecentOrder" property="orderDetail.revisedOrderDate">
<%=Utility.encodeForHTML(ClwI18nUtil.formatDateInp(request, mostRecentOrder.getOrderDetail().getOriginalOrderDate()))%>
</logic:empty>
</nobr>
										<%
											//if the order status is "Pending Date", show the date that the order
											//will be submitted
											if (RefCodeNames.ORDER_STATUS_CD.PENDING_DATE.equals(orderStatus) &&
												Utility.isSet(mostRecentOrder.getPendingDate())) {
 													Date processOnDate = new SimpleDateFormat("MM/dd/yyyy").parse(mostRecentOrder.getPendingDate());
										%>
												<span class="<%=style%>">
													(<%=Utility.encodeForHTML(ClwI18nUtil.formatDateInp(request, processOnDate))%>)
                                                </span>
										<% 
											}
											if (RefCodeNames.ORDER_STATUS_CD.CANCELLED.equals(orderStatus) &&
													mostRecentOrder.getConsolidatedOrder()!= null) { 
                  						%>
												<app:storeMessage key= "shop.orderStatus.text.consolidated"/>
										<% 
											} 
										%>
                                    </p>
<% }
										%>
</logic:notEmpty>
                                    <div class="tableWrapper">
                                        <!-- hard code width to table for wider layouts style="width: 998px;" -->
                                        <table class="order">
                                            <colgroup>
                                                <col class="sku" />
                                                <col class="ecoFriendly" />
                                                <col class="productName" />
                                                <col class="price" />
                                                <col class="quantity" />
                                                <col class="subtotal" />
                                            </colgroup>
                                            <thead>
                                                <tr>
                                                    <th><app:storeMessage key= "mostRecentOrder.label.sku#"/></th>
                                                    <th class="ecoFriendly">
                                                    </th>
                                                    <th><app:storeMessage key= "mostRecentOrder.label.productName"/></th>
                                                    <th><app:storeMessage key= "mostRecentOrder.label.price"/></th>
                                                    <th><app:storeMessage key= "mostRecentOrder.label.qty"/></th>
                                                    <th><app:storeMessage key= "mostRecentOrder.label.subtotal"/></th>
                                                </tr>
                                            </thead>
 <!-- STJ-5609: Modified To Display Empty headers of the table if there are no most recent orders for a site. -->                                           
<logic:notEmpty name="esw.DashboardForm" property="mostRecentOrderSearchInfo.matchingOrders">
<% if (user.getSite() != null) { %>

<bean:define id="mostRecentOrder" name="esw.DashboardForm"
    property="mostRecentOrderSearchInfo.matchingOrders[0]"
    type="com.cleanwise.service.api.value.OrderStatusDescData" />


                                            <tbody>
<logic:iterate id="orderItem" name="mostRecentOrder" property="orderItemList"
	type="com.cleanwise.service.api.value.OrderItemData"><%
	boolean showEcoFriendly = false;
	if (myForm.getProductDataByItemIdMap() != null) {
	    ProductData productD = myForm.getProductDataByItemIdMap().get(orderItem.getItemId());
	    if (productD != null && productD.isCertificated()) {
	        showEcoFriendly = true;
	    }
	}
%>
                                                <tr>
                                                     <td><%=ShopTool.getRuntimeSku(orderItem,request)%></td>
                                                    <td class="ecoFriendly">
                                                    	<%if(showEcoFriendly){%><img src="../../esw/images/ecoFriendly.png" alt="eco-friendly" title="eco-friendly" /><%} %>
                                                    </td>
                                                    <td>
                                                    <%String productLink = "shopping.do?operation=item&source=mostRecentOrder&itemId=" + orderItem.getItemId();%>
                                                    	<html:link href="<%=productLink%>">
                                                    		<bean:write name="orderItem" property="itemShortDesc" />
                                                    	</html:link>
                                                    </td><%
BigDecimal custItemPrice = new BigDecimal(0.00);
BigDecimal subTotal = new BigDecimal(0.00);
%>                                                    <td class="right">
	                                     					                                     				<%
	                                     					if (orderItem.getCustContractPrice() != null) {
	                                     					   custItemPrice = orderItem.getCustContractPrice();
	                                     					}
	                                     				subTotal = custItemPrice.multiply(new BigDecimal(orderItem.getTotalQuantityOrdered()));
	                                     				%>
									
									<app:formatPriceCurrency price="<%=custItemPrice%>"
										orderId="<%=mostRecentOrder.getOrderDetail().getOrderId()%>"/>
                                                    	
                                                    </td>
                                                    <td class="right"><bean:write name="orderItem" property="totalQuantityOrdered" />
                                                    	
                                                    </td>
                                                    <td class="right">
	                                     			<app:formatPriceCurrency price="<%=subTotal%>"
										orderId="<%=mostRecentOrder.getOrderDetail().getOrderId()%>"/>
                                                    	
                                                    </td>
                                                </tr>
</logic:iterate>
                                            </tbody>
								<%
                               		}
                                %>
</logic:notEmpty>
                                        </table>
                                    </div>

                                   		<logic:notEmpty name="esw.DashboardForm" property="mostRecentOrderSearchInfo.matchingOrders">
										<bean:define id="mostRecentOrder" name="esw.DashboardForm"
											property="mostRecentOrderSearchInfo.matchingOrders[0]"
											type="com.cleanwise.service.api.value.OrderStatusDescData" />
                                   			<p class="total">
                                   				<app:storeMessage key= "dashboard.label.orderTotal"/>:&nbsp;
								<app:formatPriceCurrency price="<%=mostRecentOrder.getEstimatedTotal()%>"
										orderId="<%=mostRecentOrder.getOrderDetail().getOrderId()%>"/>
								
                                   			</p>
                                    	</logic:notEmpty>
                               
                               

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
                