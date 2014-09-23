<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="org.apache.struts.util.LabelValueBean" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.*" %>

<%@ page import="com.espendwise.view.forms.esw.OrdersForm"%>
<%@ page import="com.cleanwise.view.forms.OrderOpDetailForm"%>
<%@ page import="com.cleanwise.service.api.value.OrderItemDescDataVector"%>
<%@ page import="com.cleanwise.service.api.value.ProductViewDefDataVector"%>
<%@ page import="com.cleanwise.service.api.value.ProductViewDefData"%>

<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
        
<app:setLocaleAndImages/>

<bean:define id="theForm" name="esw.OrdersForm" type="com.espendwise.view.forms.esw.OrdersForm"/>
<bean:define id="workflowRole" type="java.lang.String" name="ApplicationUser" property="user.workflowRoleCd"/>

<%
	CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
	
	OrderStatusDescData orderStatusDetail = theForm.getOrderOpDetailForm().getOrderStatusDetail();
	OrderData orderData = orderStatusDetail.getOrderDetail();
	
	String orderLocale = orderStatusDetail.getOrderDetail().getLocaleCd();
	Locale loc = Utility.parseLocaleCode(orderLocale);
	String language = loc.getDisplayLanguage();
	String country = loc.getCountry();

	ClwI18nUtil clwI18n = ClwI18nUtil.getInstance(request,orderLocale,-1);
	SimpleDateFormat sdfInter = new SimpleDateFormat("yyyy-MM-dd");
	TimeZone timeZone =  Utility.getTimeZoneFromUserData(appUser.getSite());
	SimpleDateFormat sdfEntry = new SimpleDateFormat("yyyy-MM-dd" +" " + Constants.SIMPLE_TIME24_PATTERN + ".S");
	sdfEntry.setTimeZone(timeZone);
	SimpleDateFormat sdfComment = new SimpleDateFormat("yyyy-MM-dd k:mm");
	sdfComment.setTimeZone(timeZone);
	boolean approverFl = (workflowRole.indexOf("APPROVER") >= 0) ? true : false;
	boolean consolidatedOrderFl =RefCodeNames.ORDER_TYPE_CD.CONSOLIDATED.equals(orderStatusDetail.getOrderDetail().getOrderTypeCd());

	boolean toBeConsolidatedFl =RefCodeNames.ORDER_TYPE_CD.TO_BE_CONSOLIDATED.equals(orderStatusDetail.getOrderDetail().getOrderTypeCd());

	OrderData consolidatedToOrderD = orderStatusDetail.getConsolidatedOrder();

	boolean useCustPo = true;
	String requestPoNum = orderStatusDetail.getOrderDetail().getRequestPoNum();

	if(requestPoNum == null || requestPoNum.equals("") || requestPoNum.equalsIgnoreCase("na") || requestPoNum.equalsIgnoreCase("n/a")){
		useCustPo = false;
	}
 
	String orderStatusItemsPanel = theForm.getOrderOpDetailForm().getOrderStatusDetail().getOrderDetail().getOrderStatusCd(); 

	boolean resaleItemsAllowed = ShopTool.isResaleItemsAllowed(request);
 
	boolean showStatus = false;
	if (!orderStatusItemsPanel.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL) &&
			!orderStatusItemsPanel.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION) &&
			!orderStatusItemsPanel.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_DATE)) {
				showStatus = true;
	}
	
	//Getting properly formatted Order Date: Begin
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	sdf.setTimeZone(timeZone);
	Date date = orderStatusDetail.getOrderDetail().getOriginalOrderDate();
	Date time = orderStatusDetail.getOrderDetail().getOriginalOrderTime();
	Date orderDate = Utility.getDateTime(date, time);

	String formattedODate = ClwI18nUtil.formatDateInp(request, orderDate, timeZone.getID() );
	//Getting  properly formatted Order Date: End
%>

<!-- Start Box -->

<div class="boxWrapper smallMargin squareCorners">
	<div class="top clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
	
		<div class="content">
			<div class="left clearfix">
			
				
			<%
			BigDecimal orderSubTotal = BigDecimal.ZERO;
                        OrderFreightData orderFreightData = null;
			OrderFreightData orderHandlingData = null;
                        OrderAddOnChargeData orderDiscountData = null; 
                        OrderAddOnChargeData orderSmallOrderFee = null; 
                        OrderAddOnChargeData orderFuelSurcharge = null;
                        BigDecimal custItemSubtotal = BigDecimal.ZERO;
                        
                        boolean estimateTax = false;
                        boolean estimateOrderFl = false;
			
			boolean estFreight = false;
			boolean estHandling = false;
			boolean estFuelSurcharge = false;
			boolean estSmallOrderFee = false;
                        boolean hidePack = theForm.getOrderOpDetailForm().isHidePack();
                        boolean hideUom = theForm.getOrderOpDetailForm().isHideUom();
                        boolean hideManufName = theForm.getOrderOpDetailForm().isHideManufName();
                        boolean hideManufSku = theForm.getOrderOpDetailForm().isHideManufSku();        
			boolean freightHandFl = false;
                        int cnt_unique_sku = 0;
			int receiveIdx = 0;
			boolean firstitem = true;
			Map distItemMap = theForm.getDistItemMap();
			
			Iterator it = distItemMap.entrySet().iterator();
			while(it.hasNext()){
				Map.Entry entry = (Map.Entry)it.next();
				OrderItemDescDataVector items = (OrderItemDescDataVector)entry.getValue();
				
				OrderItemDescData thisItem = (OrderItemDescData)items.get(0);
				String distName = thisItem.getDistRuntimeDisplayName();
                                if (distName != null) distName = distName.trim();
                                  else distName = "";
				  
				String erpPoNum = thisItem.getOrderItem().getErpPoNum();
				BigDecimal distSubtotal = BigDecimal.ZERO;
				BigDecimal distTotal = BigDecimal.ZERO;
				
			%>
			
			<div class="twoColBox">                        
				<div class="column width70"> 										
					<h3><%=distName%></h3>										
				</div>
				<% if (erpPoNum != null) { %>
					<div class="column width30">										
						<h3><app:storeMessage key="shop.orderdetail.label.purchaseOrder" /> <%=erpPoNum%></h3>
					</div>
				<% } %>
			</div>	                 
			
			<table class="order">
                            
                                <thead>
					<tr>
                                        
                                        <th>
                                            <app:storeMessage key="shop.orderdetail.table.header.ourSkuNum" />
                                        </th>
					
                                        <th class="ecoFriendly">
                                            
                                        </th>
					
                                        <th>
                                            <app:storeMessage key="shop.orderdetail.table.header.productName" />
                                        </th>
					
                                        <% if (!hidePack) {%>
	                                        <th>
	                                            <app:storeMessage key="shop.orderdetail.table.header.pack" />
	                                        </th>
                                        <% } %>
					
                                        <% if (!hideUom) {%>
											<th>
	                                            <app:storeMessage key="shop.orderdetail.table.header.uom" />
	                                        </th>
                                        <% } %>
					
                                        <% if (!hideManufName) {%>
	                                        <th>
	                                            <app:storeMessage key="shop.orderdetail.table.header.manufacturer" />
	                                        </th>
                                        <% } %>
					
                                        <% if (!hideManufSku) {%>
	                                        <th>
	                                            <app:storeMessage key="shop.orderdetail.table.header.mfgSkuNum" />
	                                        </th>
	                                <% } %>
					
                                        <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">  
						<th>
						    <app:storeMessage key="shop.orderdetail.table.header.price" />
						</th>
                                        </logic:equal>
					
                                        <th>
                                            <app:storeMessage key="shop.orderdetail.table.header.qty" />
                                        </th>
                                        
                                        <% if (showStatus) {  %>
						<th>
						    <app:storeMessage key="shop.orderdetail.table.header.status" />
						</th> 
					<% } %>
					 
					<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true"> 
						<th>
						    <app:storeMessage key="shop.orderdetail.table.header.subtotal" />
						</th>
                                        </logic:equal>
					
					<%if(showStatus){%>
						<app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.RECEIVING%>">
						  <% //set a hidden field on the form to initiate future processing of "Received" fields%>                                          
						  <html:hidden property="hasFunctionReceiving" value="true" />
						  <th>
						      <app:storeMessage key="shop.orderdetail.table.header.received" />
						  </th>
						</app:authorizedForFunction>
					
                                        <% } %>
					
                                        <%
                                           if(resaleItemsAllowed){ %>
						<th>
                                                 <app:storeMessage key="shoppingItems.text.reSale" />
						</th>
                                        <% } %>
                      <% if (appUser.getUserAccount().isShowSPL()){ %>
						<th>
                        	<app:storeMessage key="shoppingItems.text.spl" />
						</th>
                      <% } %>
					
					</tr>
                                </thead>
				
				<tbody>
					<%
					for(int oidx=0; oidx < items.size(); oidx++){
						OrderItemDescData item = (OrderItemDescData)items.get(oidx);
						BigDecimal custItemPrice = new BigDecimal(0.0);
						Integer qty = new Integer(item.getOrderItem().getTotalQuantityOrdered());
						
						orderFreightData = item.getOrderFreight();
						orderHandlingData = item.getOrderHandling();
						orderDiscountData = item.getOrderDiscount(); 
						orderSmallOrderFee = item.getOrderSmallOrderFee(); 
						orderFuelSurcharge = item.getOrderFuelSurcharge();
						
						if(!firstitem){
							receiveIdx++;
						}
						
					%>
					
					<bean:define id="quantityEl" value="<%=\"quantityElement[\"+oidx+\"]\"%>"/>
					<bean:define id="itemIdsEl" value="<%=\"itemIdsElement[\"+oidx+\"]\"%>"/>
					
					<tr class="noBorder">
                                        <%
					int colqty = 0;
					int colUptoQty = 0;
					%>
					
					<% colqty++; %>
                                        <td><%=ShopTool.getRuntimeSku(item.getOrderItem(),request)%></td>
					
					<% // Is the Product Item a "Certified" item ? 
                                           // if it is, show special symbol "eco-friendly" on the screen
	                                      boolean showEcoFriendly = false;
	                                      if (theForm.getProductDataByItemIdMap() != null) {
	                                         ProductData productD = theForm.getProductDataByItemIdMap().get(item.getOrderItem().getItemId());
	                                         if (productD != null && productD.isCertificated()) {
	                                            showEcoFriendly = true;
	                                         }
	                                      }
                                        %>
					
					<% colqty++; %>
                                        <td class="ecoFriendly">
                                            <%if(showEcoFriendly){%><img src="../../esw/images/ecoFriendly.png" alt="eco-friendly" title="eco-friendly" /><%} %>
                                        </td>
					
					<%
					int orderItemId = item.getOrderItem().getOrderItemId(); 
                                        String itemLink = "../../userportal/esw/shopping.do?operation=item&source=order&orderId="+orderData.getOrderId()+"&orderItemId="+orderItemId;
					%>
                                         
					<% colqty++; %>
					<td>
						<html:link href="<%=itemLink%>">
							<%=item.getOrderItem().getItemShortDesc()%>
						</html:link>
                                        </td>
					
                                        <% if (!hidePack) {%>
					 <% colqty++; %>
	                                <td>
	                                        <%=item.getOrderItem().getItemPack()%>
	                                </td>
                                        <% } %>
					
                                        <% if (!hideUom) {%>
					 <% colqty++; %>
	                                <td>
	                                        <%=item.getOrderItem().getItemUom()%>
	                                </td>
                                        <% } %>
					
                                        <% if (!hideManufName) {%>
					 <% colqty++; %>
	                                <td>
	                                        <%=item.getOrderItem().getManuItemShortDesc()%>
	                                </td>
	                                <% } %>
					    
                                        <% if (!hideManufSku) {%>
					 <% colqty++; %>
	                                <td>
	                                        <%=item.getOrderItem().getManuItemSkuNum()%>
	                                </td>
	                                <% } %>
					
                                        <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
					 <% colqty++; %>
					<%custItemPrice = item.getOrderItem().getCustContractPrice();%>
                                        <td class="right">
                                            <%=clwI18n.priceFormat(custItemPrice,"&nbsp;")%>
                                        </td>
                                        </logic:equal>
					
					 <% colqty++;
					 colUptoQty = colqty;%>
                                        <td class="extraLeading right">
                                            <%=item.getOrderItem().getTotalQuantityOrdered()%>                                    
                                        </td>
					
					<%if(showStatus){%>
						<% colqty++;%>
					<td>&nbsp;</td>
					<%}%>
					
                                        <!-- Items section, "Subtotal" column: Begin -->
                                        <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
					<% colqty++;%>
                                        <td class="right">
                                            
                                            <% custItemSubtotal = custItemPrice.multiply(new BigDecimal(qty.doubleValue())); %>
                                          
                                            <% distSubtotal =  Utility.addAmt(distSubtotal, custItemSubtotal.setScale(2, BigDecimal.ROUND_HALF_UP));%>
                                            <%=clwI18n.priceFormat(custItemPrice.multiply(new BigDecimal(qty.doubleValue())),"&nbsp;")%>
                                        </td>
                                        </logic:equal>
					
                                        <!-- Items section, "Subtotal" column: End -->
                                       
				       <%if(showStatus){%>
				       
						<app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.RECEIVING%>">
						<% colqty++;%>
						<td class="right">
                                                  <div class="qtyInput">
                                                      <% //piece of code, written by SVC: Begin %>                         
                                                      <% String received = item.getItemQuantityRecvdS(); //orderItemDescList["+receiveIdx+"].itemQuantityRecvdS; %>
                                                      <% //String prop = "orderOpDetailForm.orderItemDescList["+receiveIdx+"].itemQuantityRecvdS"; %>
                                                      <!--  <input value='<%//=received%>' class="qty triggerSelect" type="text"/> -->
                                                      <% //piece of code, written by SVC: End %>
                                                 
                                                      <%String prop = "orderOpDetailForm.orderItemDescList["+receiveIdx+"].itemQuantityRecvdS"; %>
                                                      <html:text readonly='<%=appUser.isBrowseOnly() %>' value='<%=received%>' name="esw.OrdersForm" styleClass="qty triggerSelect" property="<%=prop%>"/>
                                                  </div>
						</td>   
						</app:authorizedForFunction>                                        
				       
                                        <% }%>  
					<!-- Re-sale Column: Begin -->
					<% if(resaleItemsAllowed) { %>
						<td>
							
                                                     <% colqty++; %>
                                                     
						     <%if(item.getOrderItem().getSaleTypeCd().equalsIgnoreCase(RefCodeNames.ITEM_SALE_TYPE_CD.RE_SALE)){%>
							<app:storeMessage key="global.text.yes" />
                                                     <%}else{%>
							<app:storeMessage key="global.text.no" />
                                                     <%}%>
						</td>
					<% } %>
					<!-- Re-sale Column: End -->
                      <% if (appUser.getUserAccount().isShowSPL()){ %>
						<td>
                             <% colqty++; %>
						     <%
						     	boolean itemIsSPL = false;
	                            if (theForm.getProductDataByItemIdMap() != null) {
	                                 ProductData productD = theForm.getProductDataByItemIdMap().get(item.getOrderItem().getItemId());
	                                 if (productD != null && productD.getCatalogDistrMapping() != null &&
	                                		 productD.getCatalogDistrMapping().getStandardProductList() != null) {
	                                	 itemIsSPL = Utility.isTrue(productD.getCatalogDistrMapping().getStandardProductList());
	                                 }
	                            }
						     	if (itemIsSPL) { %>
									<app:storeMessage key="global.text.yes" />
                             <% }
						     	else
						     	{
						     %>
									<app:storeMessage key="global.text.no" />
                             <% } %>
						</td>
                      <% } %>
                                    </tr>
				    <%firstitem = false;%>
				    <!-- ORDER ITEM ACTIONS-->
				    
				    <%
				    int colAfterQty = colqty - colUptoQty;
				    
				   
				    if(item.getOrderItemActionList() != null){	
					for(int aidx=0; aidx < item.getOrderItemActionList().size(); aidx++){
						OrderItemActionData action = (OrderItemActionData)item.getOrderItemActionList().get(aidx);
						Integer actionqty = new Integer(action.getQuantity());
						String actioncd = action.getActionCd();
						if (null == actioncd) {
						    actioncd = new String("");
						}
						
						//Tracking
						String trackingStr = "";
						String trackingNum = "";
						if (RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.TRACKING_NUMBER.equals(actioncd)) {
							
							String trackingComments = action.getComments();
							
							if (Utility.isSet(trackingComments)) {
								
								StringTokenizer tok = new StringTokenizer(trackingComments);
								int ctok = tok.countTokens();
								for (int i = 0; i < ctok; i++) {
								    String prevToken = tok.nextToken();
								    if (!tok.hasMoreTokens()) {
									trackingNum = prevToken;
								    }
								}
								trackingStr += ClwMessageResourcesImpl.getMessage(request,"order.detail.shipVia")+trackingComments;
							}else{
								trackingStr += ClwMessageResourcesImpl.getMessage(request,"order.detail.shipVia")+
									ClwMessageResourcesImpl.getMessage(request,"order.detail.trackingNumberNotAvailable");
							}
						}
						
						String deliveryRefStr = "";
						if (RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.DELIVERY_REF_NUMBER.equals(actioncd)) {
							
							String deliveryComments = action.getComments();
							
							if (Utility.isSet(deliveryComments)) {
								
								deliveryRefStr += deliveryComments;
							}else{
								deliveryRefStr += ClwMessageResourcesImpl.getMessage(request,"order.detail.deliveryReferenceNumberNotAvailable");
							}
						}
						
						java.util.Date actiondate = Utility.getDateTime(action.getActionDate(), action.getActionTime()) ;
						
						if(actiondate == null){
                                                 actiondate = action.getAddDate();
                                                }
						String formattedActionDate ="";
						if(actiondate != null){
							formattedActionDate = ClwI18nUtil.formatDateInp(request, actiondate, timeZone.getID() );
						}
						
						%>
						<app:shouldDisplayOrderItemAction name="<%=actioncd%>">
						<%if (RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.CUST_INVOICED.equals(actioncd)) {
                                                    actioncd = "Invoiced";
                                                }
						%>
							<tr class="noBorder small">
							    <td class="right" colspan="<%=colUptoQty%>" >
							    <%if(!RefCodeNames.ORDER_ITEM_DETAIL_ACTION_CD.RECEIVED_AGAINST.equals(actioncd)){%>
								
								<%=action.getQuantity()%>
							    <%}else{%>
								&nbsp;
							    <%}%>
							    </td>
							    
							    <td colspan="<%=colAfterQty%>" >
							    <%
								if(action.getQuantity() >= 0) {
								String statusKey = Constants.ORDER_ITEM_ACTION_KEY + actioncd;
								%>
								 <%= formattedActionDate%>:
								 <%=ClwMessageResourcesImpl.getMessage(request,statusKey)%>
								 <%if(Utility.isSet(trackingStr)){
									if(trackingStr.toUpperCase().contains("FED")){
								 %>
									<a href="http://www.fedex.com/Tracking?ascend_header=1&clienttype=dotcom&cntry_code=<%=country%>&language=<%=language%>&tracknumbers=<%=trackingNum%>" target="_blank">
										<%=trackingStr%>
									</a>
									<%}else{%>
										<%=trackingStr%>
									<%}%>
								
								 <%}%>
								 <%if(Utility.isSet(deliveryRefStr)){%>
									<%=deliveryRefStr%>
								 <%}%>
								 
							   <%}else{%>
								&nbsp;
							    <%}%>	 
							    
							    </td>
							</tr>
							
						</app:shouldDisplayOrderItemAction>
						</tr>
					<%}
					}%>
				    
				    <tr class="small"><td colspan="<%=colqty%>">&nbsp;</td></tr>
                                    <!-- End Repeating Row -->                    
				    
					
					<%}%>
				</tbody>

			</table>
			
			<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
				<table class="orderTotal">
					<colgroup>
					<col>
					<col>
					<col width="50%">
					</colgroup>
					<tr>
						<% orderSubTotal = orderSubTotal.add(distSubtotal);  %>
						<td>
						    <app:storeMessage key="shop.orderdetail.table.summary.distributorSubtotal" />
						</td>
						<td class="right">
						    <%=clwI18n.priceFormat(distSubtotal,"&nbsp;")%>
						</td>
			
					</tr>
					
					<!--Additional Charges per distributor -->
					<%if(orderFreightData!=null){%>
					<tr>
					<% if (!orderFreightData.getFreightTypeCd().trim().equals(RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE)){ %>
			                        <td>
						<app:storeMessage key="shop.orderdetail.table.summary.freight" />
						</td> 
					<% } else { %> 
			                        <td>
						<% estFreight = true; %>
						<app:storeMessage key="shop.orderdetail.table.summary.freightEst" />
						</td>                                     
					<% } %>                                  
					<% if (!orderFreightData.getFreightTypeCd().trim().equals(RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE)){ %>
						<% if (orderFreightData.getAmount() != null) { %>
							<% BigDecimal famount = orderFreightData.getAmount(); %>
							<td class="right">
								<%=clwI18n.priceFormat(famount,"&nbsp;")%>
							</td>
			                        <% } else { %>
							<td></td>
			                        <% } %>
			                <% } else { %>
			                        <td></td>
			                <% } %>
					<% if(orderFreightData.getShortDesc() != null && !orderFreightData.getShortDesc().equals("")) { %>
							<td>     
								<%=orderFreightData.getShortDesc()%>
							</td>
			                <% } %> 
			                </tr>
					<%}%>
					
					<!-- The misc cost (Handling): Begin-->
					<%if(orderHandlingData!=null){%>
					<tr>
					<% if (!orderHandlingData.getFreightTypeCd().trim().equals(RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE)){ %>
			                        <td>
						<app:storeMessage key="shop.orderdetail.table.summary.handling" />
						</td> 
					<% } else { %> 
			                        <td>
						<% estHandling = true; %>
						<app:storeMessage key="shop.orderdetail.table.summary.handlingEst" />
						</td>                                     
					<% } %>                                  
					<% if (!orderHandlingData.getFreightTypeCd().trim().equals(RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE)){ %>
						<% if (orderHandlingData.getAmount() != null) { %>
							<% BigDecimal hamount = orderHandlingData.getAmount(); %>
							<td class="right">
								<%=clwI18n.priceFormat(hamount,"&nbsp;")%>
							</td>
			                        <% } else { %>
							<td></td>
			                        <% } %>
			                <% } else { %>
			                        <td></td>
			                <% } %>
					<% if(orderHandlingData.getShortDesc() != null && !orderHandlingData.getShortDesc().equals("")) { %>
							<td>     
								<%=orderHandlingData.getShortDesc()%>
							</td>
			                <% } %> 
			                </tr>
					<%}%>
					<!-- The misc cost (Handling): End-->
				
					<!-- Discount per Didtributor: Begin -->
					<% if(orderDiscountData != null && !orderDiscountData.equals("")) { %>
			                <tr>
						<td>
						<app:storeMessage key="shop.orderdetail.table.summary.discount" />
						</td>
						<td class="right negative">
						<% BigDecimal damount = orderDiscountData.getAmount(); %>
				                <%=clwI18n.priceFormat(damount,"&nbsp;")%>
						</td>
						<td>
						<% if(orderDiscountData.getShortDesc() != null && !orderDiscountData.getShortDesc().equals("")) { %>
							<%=orderDiscountData.getShortDesc()%>
			                        <% } %> 
						</td>                                   
					</tr>
					<% } %>
					<!-- Discount per Didtributor: End -->
				
					<!-- Small Order Fee per Didtributor: Begin -->
					<% if(orderSmallOrderFee != null && !orderSmallOrderFee.equals("")) { %>
			                <tr>
						<% if (!orderSmallOrderFee.getDistFeeTypeCd().trim().equals(RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE)){ %>
			                        <td>
						<app:storeMessage key="shop.orderdetail.table.summary.smallOrderFee" />
						</td> 
					<% } else { %> 
			                        <td>
						<% estSmallOrderFee = true; %>
						<app:storeMessage key="shop.orderdetail.table.summary.smallOrderFeeEst" />
						</td>                                     
					<% } %>                                  
					<% if (!orderSmallOrderFee.getDistFeeTypeCd().trim().equals(RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE)){ %>
					<% if (orderSmallOrderFee.getAmount() != null) { %>
				                <% BigDecimal sofamount = orderSmallOrderFee.getAmount(); %>
				                <td class="right">
				                        <%=clwI18n.priceFormat(sofamount,"&nbsp;")%>
				                </td>
			                        <% } else { %>
			                        <td></td>
			                        <% } %>
			                        <% } else { %>
			                        <td></td>
			                        <% } %>
						<% if(orderSmallOrderFee.getShortDesc() != null && !orderSmallOrderFee.getShortDesc().equals("")) { %>
							<td>     
								<%=orderSmallOrderFee.getShortDesc()%>
							</td>
			                        <% } %> 
			                   </tr>
			                <% } %>
					<!-- Small Order Fee per Didtributor: End -->
					
					<!-- Fuel Surcharge per Didtributor: Begin -->
					<% if(orderFuelSurcharge != null && !orderFuelSurcharge.equals("")) { %>
			                <tr>
						<% if (!orderFuelSurcharge.getDistFeeTypeCd().trim().equals(RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE)){ %>
			                        <td>
							<app:storeMessage key="shop.orderdetail.table.summary.fuelSurcharge" />
						</td>
						<% } else { %> 
			                        <td>
							<% estFuelSurcharge = true; %>
							<app:storeMessage key="shop.orderdetail.table.summary.fuelSurchargeEst" />
						</td>                                     
						<% } %> 
						<% if (!orderFuelSurcharge.getDistFeeTypeCd().trim().equals(RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE)){ %>                                                             
						<% if (orderFuelSurcharge.getAmount() != null) { %>
				                        <% BigDecimal fsgamount = orderFuelSurcharge.getAmount(); %>
				                        <td class="right">
				                        <%=clwI18n.priceFormat(fsgamount,"&nbsp;")%>
				                        </td>
				                <% } else { %>
			                                <td></td>
			                        <% } %>
			                        <% } else { %>
							<td></td>
			                        <% } %>				      
			                        <% if(orderFuelSurcharge.getShortDesc() != null && !orderFuelSurcharge.getShortDesc().equals("")) { %>
							<td>     
								<%=orderFuelSurcharge.getShortDesc()%>
							</td>
			                        <% } %> 			                       
			                </tr>
			                <% } %>									   
					<!-- Fuel Surcharge per Didtributor: End -->
					
					<!-- Distributor Total (= per Distributor): Begin --> 			   
					<tr class="total">
					<td>
					<% if (estFreight || estHandling || estFuelSurcharge || estSmallOrderFee) { %>
                                                <%=ClwMessageResourcesImpl.getMessage(request,"shop.orderdetail.table.summary.distributorTotalEst")%>
					<% } else { %>
                                                <%=ClwMessageResourcesImpl.getMessage(request,"shop.orderdetail.table.summary.distributorTotal")%>
					<% } %>
					</td>
					<td class="right">
                                        <% distTotal =  distTotal.add(distSubtotal); %>                                       
                                        
					<% if(orderFreightData != null && !orderFreightData.equals("")) { %>
						<% if (!orderFreightData.getFreightTypeCd().trim().equals(RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE)){ %>    
							<% if (orderFreightData.getAmount() != null) { %>
				                                <% distTotal = distTotal.add(orderFreightData.getAmount()); %>
				                        <% } %>
				                <% } %>
				        <% } %>
					<% if(orderHandlingData != null && !orderHandlingData.equals("")) { %>
						<% if (!orderHandlingData.getFreightTypeCd().trim().equals(RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE)){ %>    
							<% if (orderHandlingData.getAmount() != null) { %>
				                                <% distTotal = distTotal.add(orderHandlingData.getAmount()); %>
				                        <% } %>
				                <% } %>
				        <% } %>
                                        <% if(orderDiscountData != null && !orderDiscountData.equals("")) { %>
						<% if (orderDiscountData.getAmount() != null) { %>			                   					                                                                                             
							<% distTotal = distTotal.add(orderDiscountData.getAmount());  %>
						<% } %>
                                        <% } %>
                                        <% if(orderSmallOrderFee != null && !orderSmallOrderFee.equals("")) { %>
						<% if (!orderSmallOrderFee.getDistFeeTypeCd().trim().equals(RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE)){ %>
							<% if (orderSmallOrderFee.getAmount() != null) { %>
				                                <% distTotal = distTotal.add(orderSmallOrderFee.getAmount()); %>
				                        <% } %>
				                <% } %>
				        <% } %>				                       
				        
					<% if(orderFuelSurcharge != null && !orderFuelSurcharge.equals("")) { %>
						<% if (!orderFuelSurcharge.getDistFeeTypeCd().trim().equals(RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE)){ %>    
							<% if (orderFuelSurcharge.getAmount() != null) { %>
				                                <% distTotal = distTotal.add(orderFuelSurcharge.getAmount()); %>
				                        <% } %>
				                <% } %>
				        <% } %>
                                        <%=clwI18n.priceFormat(distTotal,"&nbsp;")%>
					</td>
					<td></td>
					</tr>
					<tr></tr>
			                <!-- Distributor Total (= per Distributor): End --> 
							   
				</table>

			</logic:equal>
			
			<%}//end while%>
			
			
			</div> <!--end left clearfix-->
		</div> <!--end content-->
		
	<div class="bottom clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
</div>
<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
<div class="boxWrapper smallMargin squareCorners">
	<div class="top clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
	
		<div class="content">
			<div class="left clearfix">
			
			<h2><app:storeMessage key="shop.orderStatus.text.orderSummary" /></h2>
			
			<table class="orderTotal">
				<colgroup>
                                    <col>
                                    <col>
				<col width="50%">
                                </colgroup>
			<tbody>
				<!--Order Sub Total -->
				
				<tr>
					<td>
					<app:storeMessage key="shop.orderdetail.table.summary.orderSubtotal" />
					</td>
					<td class="right">
					    <%=clwI18n.priceFormat(orderSubTotal,"&nbsp;")%>
					</td>
					<td></td>
				</tr>
				
				<!-- The Freight per Order: Begin-->
				<bean:define id="freightcost"  name="esw.OrdersForm" property="orderOpDetailForm.totalFreightCost" type="BigDecimal"/>
				<% if (freightcost!=null && 0.001 < java.lang.Math.abs(freightcost.doubleValue()) && consolidatedToOrderD==null) {
							    freightHandFl = true;
				%>
				<tr>
				    <%if(estFreight){%>
				    <td>
					<app:storeMessage key="shop.orderdetail.table.summary.freightEst" />
				    </td>
				    <%}else{%>
				    <td>
					<app:storeMessage key="shop.orderdetail.table.summary.freight" />
				    </td>
				    <%}%>
				    <td  class="right">
					<% if(!toBeConsolidatedFl) { %>
						<%=clwI18n.priceFormat(freightcost,"&nbsp;")%>
					<% } else { %>
						<% //for future: Consolidated Order: was * %> 
					<% } %>
				    </td>
				</tr>
				<% }  %>
				<!-- The Freight per Order: Begin-->
						   
				<!-- The misc cost (Handling): Begin-->
				<% if ( theForm.getOrderOpDetailForm().getTotalMiscCost() != null ) { %>
				    <bean:define id="misc"  name="esw.OrdersForm" property="orderOpDetailForm.totalMiscCost" type="BigDecimal"/>
				    <% if (misc!=null  && 0.001 < java.lang.Math.abs(misc.doubleValue()) && consolidatedToOrderD==null) {
							    freightHandFl = true;
				    %>
				    <tr>
					<%if(estHandling){%>
					<td>
					    <app:storeMessage key="shop.orderdetail.table.summary.handlingEst" />
					</td>
					<%}else{%>
					<td>
					    <app:storeMessage key="shop.orderdetail.table.summary.handling" />
					</td>
					<%}%>
					<td class="right">
					    <% if(!toBeConsolidatedFl) { %>
						    <%=clwI18n.priceFormat(misc,"&nbsp;")%>
					    <% } else { %>
						    <% //for future: Consolidated Order %> 
					    <% } %>
					</td>
				    </tr>
				    <% }  %>
				<% }  %>
				<!-- The misc cost (Handling): End-->
						   
				<!-- Discount per Order: Begin -->
				
				    <% BigDecimal discountAmt = theForm.getOrderOpDetailForm().getDiscountAmt();// I have to get the discount %>
				    <% if (discountAmt != null && discountAmt.compareTo(new BigDecimal(0))!=0 ) { %>
					<tr>
					    <% if (discountAmt.compareTo(new BigDecimal(0)) >0 ) {    	   
						    discountAmt = discountAmt.negate(); 
						} 
							       
						discountAmt = discountAmt.setScale(2, BigDecimal.ROUND_HALF_UP); 
					    %>
					    <td>
						<app:storeMessage key="shop.orderdetail.table.summary.discount" />   
					    </td>
					    <td class="right negative">    
						<%=clwI18n.priceFormat(discountAmt,"&nbsp;")%>                                                                          
					    </td>
					</tr>  
				    <% } %>
				
				<!-- Discount per Order: End -->
						   
				<!-- Small Order Fee per Order: Begin -->
				
				<logic:present name="esw.OrdersForm" property="orderOpDetailForm.smallOrderFeeAmt">
				<bean:define id="smallOrderFee" name="esw.OrdersForm" property="orderOpDetailForm.smallOrderFeeAmt"
						type="java.math.BigDecimal"/>
				<% if (smallOrderFee != null && 0.001 < java.lang.Math.abs(smallOrderFee.doubleValue())) { %>
				<tr>
					<%if(estSmallOrderFee){%>
					<td>
					    <app:storeMessage key="shop.orderdetail.table.summary.smallOrderFeeEst" />
					</td>
					<%}else{%>
					<td>
					    <app:storeMessage key="shop.orderdetail.table.summary.smallOrderFee" />
					</td>
					<%}%>
					<td class="right">
					    <%=clwI18n.priceFormat(smallOrderFee, "&nbsp;")%>
					</td>
				</tr>
				<% } %>
				</logic:present>
				
				<!-- Small Order Fee per Order: End -->
						   
				<!-- Fuel Srcharge per Order: Begin -->
				
				    <logic:present name="esw.OrdersForm" property="orderOpDetailForm.fuelSurchargeAmt">
				    <bean:define id="fuelSurcharge" name="esw.OrdersForm" property="orderOpDetailForm.fuelSurchargeAmt" type="java.math.BigDecimal"/>
				    <% if (fuelSurcharge != null && 0.001 < java.lang.Math.abs(fuelSurcharge.doubleValue())) { %>
				    <tr>
					<%if(estFuelSurcharge){%>
					<td>
					    <app:storeMessage key="shop.orderdetail.table.summary.fuelSurchargeEst" />
					</td>
					<%}else{%>
					<td>
					    <app:storeMessage key="shop.orderdetail.table.summary.fuelSurcharge" />
					</td>
					<%}%>
					<td class="right">
					    <%=clwI18n.priceFormat(fuelSurcharge, "&nbsp;")%>
					</td>        
				    </tr>
				<% } %>
				</logic:present>
				
				<!-- Fuel Srcharge per Order: End -->
												       
				<!-- Tax Total: Begin--> 							 
				<logic:present name="theForm" property="orderOpDetailForm.totalTaxCost">
				    <bean:define id="tax"  name="theForm" property="orderOpDetailForm.totalTaxCost" type="Double"/>
				    <% if (tax!=null && 0.001 < java.lang.Math.abs(tax.doubleValue())) { %>
					<tr>
					    <td>
						<% estimateTax = true; %>
						<app:storeMessage key="shop.orderdetail.table.summary.tax" />
					    </td>
					    <td class="right">
						<%=clwI18n.priceFormat(tax,"&nbsp;")%>
					    </td>
					    <td></td>
					</tr>
				    <% }  %>
				</logic:present>
				<!-- Tax Total: End--> 
					
				<%
					if(estimateTax || estFreight || estHandling || estSmallOrderFee || estFuelSurcharge){
						estimateOrderFl = true;
					}
				%>
				<!--  Order Total: Begin -->			                   
				<% if(consolidatedToOrderD==null && (!toBeConsolidatedFl || !freightHandFl)) { %>
				    <tr class="total">
					<td>
					    <% if (estimateOrderFl) { %>
						<app:storeMessage key="shop.orderdetail.table.summary.orderTotalEst" />
					    <% } else { %>
						<app:storeMessage key="shop.orderdetail.table.summary.orderTotal" />
					    <% } %>
					</td>
					<td class="right">
					    <bean:define id="total"  name="theForm" property="orderOpDetailForm.totalAmount"/>
					    <%=clwI18n.priceFormat(total,"&nbsp;")%>
					</td>
					<td></td>
				    </tr>
				    <tr></tr>
				    <% if (estimateOrderFl) { %>								
				    <tr>
					<td>
					    <app:storeMessage key="shop.orderdetail.table.summary.estimated" />
					</td>
				    </tr>
				    <% } %>                           	      	
				<% } %>	
				<!--  Order Total: End -->     	         
			</tbody>
			</table>
				
			</div> <!--end left clearfix-->
		</div> <!--end content-->
		
	<div class="bottom clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
</div>
</logic:equal>