<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>
<%@ page import="com.cleanwise.service.api.value.ShoppingCartData" %>
<%@ page import="com.cleanwise.service.api.value.ShoppingCartItemData" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.HashMap" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<bean:define id="theForm" name="esw.CheckOutEswForm" type="com.espendwise.view.forms.esw.CheckOutForm"/>

<%
 CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
 String utype = appUser.getUser().getUserTypeCd();
 boolean resaleItemsAllowed = ShopTool.isResaleItemsAllowed(request);
%>
<%
            boolean pendingConsolidationFl = false;

            CustomerOrderRequestData orderReq = theForm.getCheckOutForm().getOrderRequest();
            String otype = "";
            if ( orderReq != null &&
            orderReq.getOrderType() != null ) {
              otype = orderReq.getOrderType();
            }
            if(RefCodeNames.ORDER_TYPE_CD.TO_BE_CONSOLIDATED.equals(otype)) {
              pendingConsolidationFl = true;
            }
 %>
 <% 
 //Create counter for the discountPerDist on the Request Level (f_confirmShoppingItems.jsp can be called several times)
 Integer ct = (Integer)request.getAttribute("discountCounterConf");
 %>
 <% 
 if (ct == null) {
	  ct = new Integer(0);
 }
 %>

<!--  NEW UI HTML Code + Business Logic  -->
    
<%
BigDecimal orderSubTotal = new BigDecimal(0.0);
boolean distFreightFl = false;
boolean distHandlingFl = false;
boolean distFreightOptFl = false;
boolean distDiscountFl = false;
boolean distFuelSurchargeFl = false;
boolean distSmallOrderFeeFl = false;
%>    
 <div class="boxWrapper smallMargin squareTop">
                    <div class="top clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
                    <div class="content">
                        <div class="left clearfix">                            
                            
                            <% int cnt_unique_sku = 0;%>
                            
                            <bean:define id="SCartDistV" name="esw.CheckOutEswForm" property="checkOutForm.cartDistributors" type="ShoppingCartDistDataVector"/>
                            <% ShoppingCartDistDataVector distDV = theForm.getCheckOutForm().getCartDistributors(); 
                               int distDVSize = distDV.size();
                            %>
                            
                            <% boolean estimateOrderFl = false; %>
                            
                            <logic:iterate id="scdistD" name="SCartDistV"
                              offset="0" indexId="DISTIDX"
                              type="com.cleanwise.service.api.value.ShoppingCartDistData">
                                     
                              <% double distSubtotal = 0.0; %>    
                                                        
                              <!-- Calculate number of different Product Sku's in the Order -->                          
                              <% cnt_unique_sku +=1; %> 
                            
                              <!-- Publish Distributor Name: Begin -->                            
                              <logic:notEmpty name="scdistD" property="distributorName">
							     <h3 class="clear float"><bean:write name="scdistD" property="distributorName" /></h3> 
							  </logic:notEmpty>
							  <!-- Publish Distributor Name: End -->  
							       							        		
                            <table class="order">
                            
                              <%--------------------------------- ITEMS TABLE HEADER -----------------------------------------%>

                              <app:productCatalogHeader
		                            viewOptionEditCartItems="false"
		                            viewOptionQuickOrderView="false"
		                            viewOptionAddToCartList="false"
		                            viewOptionOrderGuide="false"
		                            viewOptionShoppingCart="false"
		                            viewOptionCheckout="false"
                                    viewOptionConfirmCheckout="true"		  
		                            viewOptionCatalog="false"
		                            viewOptionInvShoppingCart="false"
		                            viewOptionInvShoppingCart="false"
									
		                      />   
   
                              <%
                                int rowIdx = 0;
                              %>
                              
                              <bean:define id="distItems" name="scdistD" property="shoppingCartItems"
                                   type="java.util.List"/>
                              
                              <logic:iterate id="item" name="distItems"
                                   offset="0" indexId="itemsIdx"
                                   type="com.cleanwise.service.api.value.ShoppingCartItemData">
                                   
                                   <bean:define id="orderNumber" name="item" property="orderNumber"/>
                                   <bean:define id="itemId" name="item" property="product.productId"/>
                                   <bean:define id="itemIdQtyElement" value="<%=\"itemIdQtyElement[\"+itemsIdx+\"]\"%>"/>
                                      
                                   <% //Calculating Distributor Subtotal
                                      distSubtotal += item.getAmount();
                                   %>   
                                   <tbody> <!-- Should I put this tag here ? Yes, I think so -->
                                      <% // Order Items are already sorted by Distributor %> 
                                      <!-- Repeating Row - needs two rows for each entry to accommodate the comment line which needs colspan -->
                                      <tr>
                                    
                                         <% //SVC: I probably need to modify the link below %>
                                         <%String itemLink1 = "shopping.do?operation=item&source=orderGuide&itemId="
                                           +itemId+"&qty="+item.getQuantity();
                                           //String trColor=ClwCustomizer.getEvenRowColor(request.getSession(),rowIdx++);
                                         %>
                                         
                                         <%--------------------------------- ITEMS TABLE DATA -----------------------------------------%>
                                         
                                         <app:displayProductCatalogItems
                                               viewOptionEditCartItems="false"
                                               viewOptionQuickOrderView="false"
                                               viewOptionAddToCartList="false"
                                               viewOptionOrderGuide="false"
                                               viewOptionShoppingCart="false"
                                               viewOptionCheckout="false"
                                               viewOptionConfirmCheckout="true"
                                               viewOptionInventoryList="false"
                                               viewOptionInvShoppingCart="false"
                                               name="item"
                                               link="<%=itemLink1%>"
                                               index="<%=itemsIdx%>"
                                               inputNameQuantity="<%= \"cartLine[\" + itemsIdx + \"].quantityString\" %>"
											   formName="checkOutForm"
                                         />
                                                                                                                           
                                      </tr>

                                    <!-- End Repeating Row -->    
                                  </logic:iterate>                                                                    
                                </tbody>
                            </table>
							
							<table class="orderTotal">
                                <colgroup>
                                    <col>
                                    <col>
									<col width="50%">
                                </colgroup>
								<tbody>
								<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">  
								<tr>
								    
                                          <td>
                                              <app:storeMessage key="shop.orderdetail.table.summary.distributorSubtotal" />
                                          </td>
                                       
                                    <td class="right">
                                        <% //show Distributor subTotal in local currency 
                                           //converting primitive "double" distSubtotal to "BigDecimal" distSubtotalBD 
                                        %>
                                        <% BigDecimal distSubtotalBD = new BigDecimal(distSubtotal); %>
                                        <% distSubtotalBD = distSubtotalBD.setScale(2,BigDecimal.ROUND_HALF_UP); %>
                                        <%=ClwI18nUtil.getPriceShopping(request,distSubtotalBD,"&nbsp;")%>
					<% orderSubTotal = orderSubTotal.add(distSubtotalBD);  %>
                                    </td>
									<td></td>
                                </tr>  
                                <% boolean estimateFl = false; %>                              
                                <!-- Freight AND Handling charges per Distributor Output: Begin --> 
                                <% 
							  	 BigDecimal freightAndHandlingAmount = BigDecimal.ZERO;
							     %>  
                                <% //BigDecimal handlingAmount = BigDecimal.ZERO; %> 
                                <% //BigDecimal freightAmount = BigDecimal.ZERO; %>                              
                                <bean:define id="freightImplied" name="scdistD" property="distFreightImplied"
                                  type="java.util.List"/>
                                <%  if(null != freightImplied && 0 < freightImplied.size()) {
                                	distFreightFl = true;
                                %>
                                <logic:iterate id="impliedD" name="freightImplied"
                                   offset="0" indexId="impliedIDX"
                                   type="com.cleanwise.service.api.value.FreightTableCriteriaData">
                                <%
                                  boolean toBeDetermined = RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE.equals(impliedD.getFreightCriteriaTypeCd());
                                  
                                  BigDecimal handlingAmount = BigDecimal.ZERO; 
                                  BigDecimal freightAmount = BigDecimal.ZERO;
                                
                                  if (!toBeDetermined) {
                                     freightAmount = impliedD.getFreightAmount();
                                     handlingAmount = impliedD.getHandlingAmount();
                                  }
                                  if (handlingAmount == null) {
                                      handlingAmount = new BigDecimal(0);
                                  } else {
                                      handlingAmount = handlingAmount.setScale(2,BigDecimal.ROUND_HALF_UP);
                                  }
                                  if (freightAmount != null) {
                                      handlingAmount = handlingAmount.add(freightAmount.setScale(2,BigDecimal.ROUND_HALF_UP));    
                                  }
						          freightAndHandlingAmount = freightAndHandlingAmount.add(handlingAmount.setScale(2,BigDecimal.ROUND_HALF_UP));
                                  if (toBeDetermined || handlingAmount.compareTo(BigDecimal.ZERO) != 0) {
                                %>                                
                                     <tr>
                                       <!-- each entry in clw_freight_table_criteria DB table can have EITHER Freight OR Handling Charge, not both at the same time ! -->
                                       <% if (impliedD.getFreightAmount() != null) { %>
                                         <logic:equal name="impliedD" property="freightCriteriaTypeCd" value="<%=RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE%>">
                                         <td>
                                             <app:storeMessage key="shop.orderdetail.table.summary.freightEst" />
                                         </td>
                                         </logic:equal>
                                         <logic:notEqual name="impliedD" property="freightCriteriaTypeCd" value="<%=RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE%>">
                                         <td>
                                             <app:storeMessage key="shop.orderdetail.table.summary.freight" />
                                         </td>                                         
                                         </logic:notEqual>
                                      <% } %>
                                      <% if (impliedD.getHandlingAmount() != null) { %>
                                      <%    distHandlingFl = true; %>
                                         <logic:equal name="impliedD" property="freightCriteriaTypeCd" value="<%=RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE%>">
                                         <td>
                                             <app:storeMessage key="shop.orderdetail.table.summary.handlingEst" />
                                         </td>
                                         </logic:equal>
                                         <logic:notEqual name="impliedD" property="freightCriteriaTypeCd" value="<%=RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE%>">
                                         <td>
                                             <app:storeMessage key="shop.orderdetail.table.summary.handling" />
                                         </td>                                         
                                         </logic:notEqual>
                                      <% } %>
                                         <td class="right">
                                            <logic:equal name="impliedD" property="freightCriteriaTypeCd" value="<%=RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE%>">
                                              <% estimateFl = true; %>
                                              <%//=ClwI18nUtil.getPriceShopping(request,handlingAmount,"&nbsp;")%>
                                              <!--  To Be Determined -->
                                            </logic:equal>
                                            <logic:notEqual name="impliedD" property="freightCriteriaTypeCd" value="<%=RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE%>">
                                              <%=ClwI18nUtil.getPriceShopping(request,handlingAmount,"&nbsp;")%>
                                            </logic:notEqual>
                                         </td>
									     <td> 
                                             <bean:write name="impliedD" property="shortDesc" />
                                         </td>						                                								
                                     </tr>
                                  <% } %> 
                                </logic:iterate>  
                                <% } //if(null != freightImplied && 0 < freightImplied.size()) { %>
                                <!-- Freight AND Handling charges per Distributor Output: End -->   
                                
                                <!-- Freight Options (former "Shipping Cost" from drop-down menu on the Checkout screen) per Distributor Output: Begin -->
                                <bean:define id="selectedFrMsg" name="scdistD" property="distSelectableFreightMsg" type="String"/>
                                <bean:define id="selectedFrAmt" name="scdistD" property="distSelectableFreightCost" />   
                                
                                <bean:define id="freightOptions" name="scdistD" property="distFreightOptions"
                                   type="java.util.List"/>
                                <%  if(null != freightOptions && 0 < freightOptions.size()) {
                                	distFreightOptFl = true;
                                %>
                                <tr>
                                    <td>
                                         <app:storeMessage key="shop.orderdetail.table.summary.freight" />
                                    </td>
                                    <td class="right">
                                       <!--  
                                       <bean:define id="selectedFrMsg" name="scdistD" property="distSelectableFreightMsg" type="String"/>
                                       <bean:define id="selectedFrAmt" name="scdistD" property="distSelectableFreightCost" />   
                                       -->
                                       <% if (null != selectedFrMsg && selectedFrMsg.trim().length() > 0) {  %>
                                          <bean:write name="scdistD" property="distSelectableFreightMsg" />
                                       <% } else {  %>
                                              <%=ClwI18nUtil.getPriceShopping(request,selectedFrAmt,"&nbsp;")%>
                                       <% } %>
                                    </td>
                                    <td>
                                        <bean:write name="scdistD" property="distSelectableFreightVendorName" />
                                    </td>                                    
                               </tr>
                               <%  } else {  %>
                                       <html:hidden name="esw.CheckOutEswForm" property="distFreightVendor" value=""/>
                               <%  }  %>
                                <!-- Freight Options (former "Shipping Cost" from drop-down menu on the Checkout screen) per Distributor Output: End -->
                                                             
                                <!-- Discount per Distributor Output: Begin -->
                                <% BigDecimal dAmt2 = BigDecimal.ZERO; %>
                                <% if(null != scdistD.getDistDiscountImplied() && 0 < scdistD.getDistDiscountImplied().size()) {
                                	distDiscountFl = true;
                                %>
                                   <bean:define id="chtForm" name="esw.CheckOutEswForm"
                                   type="com.espendwise.view.forms.esw.CheckOutForm"/>
                                   <%  
                                     HashMap discountPerDistHM = chtForm.getCheckOutForm().getDiscountAmtPerDist();
        
	                                 int frTblId = scdistD.getFrTblId();
	                                 int distId = scdistD.getDistId(frTblId); 
	                                 Integer distIdInt = new Integer( distId );
	                                 BigDecimal distIdBD = new BigDecimal(0);
	                                 distIdBD = (BigDecimal)discountPerDistHM.get(distIdInt);
	                               %>
                                   <bean:define id="discountImplied" name="scdistD" property="distDiscountImplied"
                                      type="java.util.List"/>
                                   <logic:iterate id="impliedDisc" name="discountImplied"
                                     offset="0" indexId="impliedIDX"
                                     type="com.cleanwise.service.api.value.FreightTableCriteriaData">
                                     <%
                                     if (distIdBD != null && distIdBD.compareTo(BigDecimal.ZERO) != 0) {
                                     %>
                                        <% 
                                          int cti = ct.intValue(); //convert ct Integer Object into primitive int  
                                        %>
                                        <logic:present name="impliedDisc" property="discount">
                                           <bean:define id="disAmt" name="impliedDisc" property="discount" />
                                           <% /*** new code for Discount in DOLLARS and PERCENTAGE-DOLLARS (multiple distributors) ***/ %>
                                           <%-- if (discountPerDist.size() > 0 ) { --%>
                                           <% dAmt2 = distIdBD; %>
                                           <% BigDecimal zeroValue1 = new BigDecimal(0); 
                                           if ( dAmt2.compareTo(zeroValue1)>0 ) {    	   
                                             dAmt2 = dAmt2.negate(); 
                                           } 
                                           dAmt2 = dAmt2.setScale(2, BigDecimal.ROUND_HALF_UP); 
                                           %>            
                                           <% if (dAmt2.compareTo(new BigDecimal(0))!=0 ) {  %>
                                                <tr>
                                                    <td>
                                                        <app:storeMessage key="shop.orderdetail.table.summary.discount" />
                                                    </td>
                                                    <td class="right negative">
                                                        <%=ClwI18nUtil.getPriceShopping(request,dAmt2,"&nbsp;")%> 
                                                    </td>
                                                    <td>
                                                        <bean:write name="impliedDisc" property="shortDesc" />
                                                    </td> 
                                                </tr>
                                           <% } %>
                                           <% 
                                             ct = new Integer(cti+1); //increment int cti and convert it into Integer Object ct
                                             request.setAttribute("discountCounterConf", ct);
                                           %>
                                           <%-- } --%>
                                        </logic:present>
                                     <%} %>
                                   </logic:iterate>
                                <%  }  %>                                  							
                                <!-- Discount per Distributor Output: End -->	
                                
                                <!-- Small Order Fee per Distributor Output: Begin -->	
                                <% BigDecimal smallOrderFeeAmt = BigDecimal.ZERO; %>                                
                                <%
                                  if (null != scdistD.getDistSmallOrderFeeList() && 0 < scdistD.getDistSmallOrderFeeList().size()) {
                                	  distSmallOrderFeeFl = true;
                                %>
                                <bean:define id="smallOrderFeeList" name="scdistD" property="distSmallOrderFeeList" type="java.util.List"/>
                                <logic:iterate id="smallOrderFeeCriteria" name="smallOrderFeeList" offset="0" indexId="impliedIDX" type="com.cleanwise.service.api.value.FreightTableCriteriaData">
                                   <%
                                   boolean toBeDetermined = RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE.equals(smallOrderFeeCriteria.getFreightCriteriaTypeCd());     
                                   if (!toBeDetermined) {
                                   	   // NOT "Estimated" value
                                	   smallOrderFeeAmt = smallOrderFeeCriteria.getHandlingAmount();
                                   }
                                   //smallOrderFeeAmt = smallOrderFeeCriteria.getHandlingAmount();
                                   if (toBeDetermined || (smallOrderFeeAmt != null && smallOrderFeeAmt.compareTo(BigDecimal.ZERO) != 0)) {
                                   %>        
                                     <tr>
                                         <logic:equal name="smallOrderFeeCriteria" property="freightCriteriaTypeCd" value="<%=RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE%>">
                                            <td>
                                                <app:storeMessage key="shop.orderdetail.table.summary.smallOrderFeeEst" />
                                            </td>
                                         </logic:equal>  
                                         <logic:notEqual name="smallOrderFeeCriteria" property="freightCriteriaTypeCd" value="<%=RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE%>">
                                            <td>
                                                <app:storeMessage key="shop.orderdetail.table.summary.smallOrderFee" />
                                            </td>
                                         </logic:notEqual>    
                                         <td class="right">
                                            <logic:equal name="smallOrderFeeCriteria" property="freightCriteriaTypeCd" value="<%=RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE%>">
                                                <%--should be i18n, but this is hard coded in the ShoppingCartDistData object as well.  Do both at same time to be consistent--%>
                                                <% estimateFl = true; %>
                                                <%//=ClwI18nUtil.getPriceShopping(request, smallOrderFeeAmt, "&nbsp;")%>
                                                <!--  To Be Determined -->
                                            </logic:equal>
                                            <logic:notEqual name="smallOrderFeeCriteria" property="freightCriteriaTypeCd" value="<%=RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE%>">
                                               <%=ClwI18nUtil.getPriceShopping(request, smallOrderFeeAmt, "&nbsp;")%>
                                            </logic:notEqual>
                                         </td>
                                         <td>
                                             <bean:write name="smallOrderFeeCriteria" property="shortDesc" /> 
                                         </td>
                                    </tr>
                                  <%}%>
                               </logic:iterate>
                               <% } %>
                               <!-- Small Order Fee per Distributor Output: End -->  
                                
                               <!-- Fuel Surcharge per Distributor Output: Begin --> 
                               <% BigDecimal fuelSurchargeAmt = BigDecimal.ZERO; %>                                                              
                               <% if (null != scdistD.getDistFuelSurchargeList() && 0 < scdistD.getDistFuelSurchargeList().size()) {
                            	   distFuelSurchargeFl = true;
                               %>
                                     <bean:define id="fuelSurchargeList" name="scdistD" property="distFuelSurchargeList" type="java.util.List"/>
                                     <logic:iterate id="fuelSurchargeCriteria" name="fuelSurchargeList" offset="0" indexId="impliedIDX" type="com.cleanwise.service.api.value.FreightTableCriteriaData"><%
                                        boolean toBeDetermined = RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE.equals(fuelSurchargeCriteria.getFreightCriteriaTypeCd());
                                        if (!toBeDetermined) {
                                        	// NOT "Estimated" value
                                        	fuelSurchargeAmt = fuelSurchargeCriteria.getHandlingAmount();
                                        }
                                        //fuelSurchargeAmt = fuelSurchargeCriteria.getHandlingAmount();
                                        if (toBeDetermined || (fuelSurchargeAmt != null && BigDecimal.ZERO.compareTo(fuelSurchargeAmt) != 0)) {
                               %>
                                            <tr>
                                                <logic:equal name="fuelSurchargeCriteria" property="freightCriteriaTypeCd" value="<%=RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE%>">
                                                <td>
                                                    <app:storeMessage key="shop.orderdetail.table.summary.fuelSurchargeEst" />
                                                </td>
                                                </logic:equal>
                                                <logic:notEqual name="fuelSurchargeCriteria" property="freightCriteriaTypeCd" value="<%=RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE%>">
                                                <td>
                                                    <app:storeMessage key="shop.orderdetail.table.summary.fuelSurcharge" />
                                                </td>
                                                </logic:notEqual>                                               
                                                <td class="right">
                                                    <logic:equal name="fuelSurchargeCriteria" property="freightCriteriaTypeCd" value="<%=RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE%>">
                                                       <%--should be i18n, but this is hard coded in the ShoppingCartDistData object as well.  Do both at the same time to be consistent--%>
                                                       <% estimateFl = true; %>
                                                       <%//=ClwI18nUtil.getPriceShopping(request, fuelSurchargeAmt, "&nbsp;")%>
                                                       <!--  To Be Determined --> 
                                                    </logic:equal>
                                                    <logic:notEqual name="fuelSurchargeCriteria" property="freightCriteriaTypeCd" value="<%=RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE%>">
                                                       <%=ClwI18nUtil.getPriceShopping(request, fuelSurchargeAmt, "&nbsp;")%>
                                                    </logic:notEqual>
                                                </td>
                                                <td>  
                                                    <bean:write name="fuelSurchargeCriteria" property="shortDesc" />
									            </td>
                                            </tr>
                                       <% } %>
                                    </logic:iterate>
                                <%
                                }
                                %>
                                <!-- Fuel Surcharge per Distributor Output: End -->
                               
							    <!-- Distributor Total: Begin -->
							    <% 
							            BigDecimal distTotal = BigDecimal.ZERO;
							            //BigDecimal distTotal = distSubtotalBD.add(handlingAmount);
							            if (freightAndHandlingAmount != null) {
                      	        	        distTotal = distSubtotalBD.add(freightAndHandlingAmount);
                      	                }					
							            if (selectedFrAmt != null) {
							                String str = selectedFrAmt.toString().trim(); 					                					            
                      	        	        distTotal = distTotal.add(new BigDecimal(str));
                      	                }
                                        distTotal = distTotal.add(dAmt2);
                                        distTotal = distTotal.add(smallOrderFeeAmt);
                                        distTotal = distTotal.add(fuelSurchargeAmt);
                                        distTotal = distTotal.setScale(2,BigDecimal.ROUND_HALF_UP);
                                %>
                                <% //show as "Distributor Total" if there are multiple Distributors
                                   //show as "Order Total" if there is only one Distributor
                                   if (distDVSize > 1) {
                                      %>                                          	                                
                                      <tr class="total">
                                         <td>
                                             <% if (estimateFl) { %>
                                                   <app:storeMessage key="shop.orderdetail.table.summary.distributorTotalEst" />
                                             <% } else { %>
                                                   <app:storeMessage key="shop.orderdetail.table.summary.distributorTotal" />
                                             <% } %>
                                         </td>
                                         <td class="right">  
                                             <%=ClwI18nUtil.getPriceShopping(request,distTotal,"&nbsp;")%>                                      
                                         </td>
									     <td></td>
								      </tr>
								   <% } %> 
								<tr></tr>
								<% if (estimateFl) { %>
								      <% estimateOrderFl = true; %>
								<%    if (DISTIDX < (distDVSize - 1)) { %>
								         <tr>
								              <td><app:storeMessage key="shop.orderdetail.table.summary.estimated" /></td>	
								         </tr>
								      <% } %>
                                <% } %>
                            </logic:equal>   
							</tbody>
							</table>
                            </logic:iterate>
                            
                           
                        </div>
                    </div>
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
			
			<!--  Order Total: Begin -->
			<!--Order Sub Total -->
				
				<tr>
					<td>
					<app:storeMessage key="shop.orderdetail.table.summary.orderSubtotal" />
					</td>
					<td class="right">
					    <%=ClwI18nUtil.getPriceShopping(request,orderSubTotal,"&nbsp;")%>
					</td>
					<td></td>
				</tr>
			<bean:define id="freightAmt" name="esw.CheckOutEswForm" property="checkOutForm.freightAmt"/>
		       <bean:define id="handlingAmt" name="esw.CheckOutEswForm" property="checkOutForm.handlingAmt"/>
		       
		       <% boolean freightHandlFl = false; %>
        <logic:greaterThan name="esw.CheckOutEswForm" property="checkOutForm.freightAmt" value="0">
        <tr> 
            <td>
                 <app:storeMessage key="shop.orderdetail.table.summary.freight" />
            </td>
            <td class="right">
         <%
         freightHandlFl = true;
         if(!pendingConsolidationFl) {
         %>
         <!-- freightAmt -->
         <%=ClwI18nUtil.getPriceShopping(request,freightAmt,"&nbsp;")%><bean:write name="esw.CheckOutEswForm" property="checkOutForm.estimateFreightString" />
         <% } else { %>
         *
         <% } %>
            </td>
         </tr>
         </logic:greaterThan>
                                <!-- Freight per Order: End  --> 
                                
                                <!-- Handling per Order: Begin  -->
                                                              
        <logic:greaterThan name="esw.CheckOutEswForm" property="checkOutForm.handlingAmt" value="0">
         <tr> 
             <td>
                 <app:storeMessage key="shop.orderdetail.table.summary.handling" />
             </td>
             <td class="right">
         <%
         freightHandlFl = true;
         if(!pendingConsolidationFl) {
         %>
         <%=ClwI18nUtil.getPriceShopping(request,handlingAmt,"&nbsp;")%>
         <% } else { %>
         *
         <% } %>
              </td>
         </tr>
        </logic:greaterThan>
                                <!-- Handling per Order: End  -->
			<!-- Discount per Order: begin  -->
                                
                                         <!-- cartDiscount -->
         <% BigDecimal discountAmt = theForm.getCheckOutForm().getDiscountAmt();// Getting the discount %>
         <% if (discountAmt != null && discountAmt.compareTo(new BigDecimal(0))!=0 ) { %>
               <tr>
                   <td>
                        <app:storeMessage key="shop.orderdetail.table.summary.discount" />
                   </td>
                   <td class="right negative">
                        <%=ClwI18nUtil.getPriceShopping(request,discountAmt,"&nbsp;")%>
                   <td> 
               </tr>
         <% } %>

<!-- Discount per Order: End  -->
			
			
			<!-- Small Order Fee per Order: Begin  -->

            <logic:greaterThan name="esw.CheckOutEswForm" property="checkOutForm.smallOrderFeeAmt" value="0">
              <tr valign=top>
                  <bean:define id="smallOrderFeeAmt" name="esw.CheckOutEswForm" property="checkOutForm.smallOrderFeeAmt"/>
                  <!-- smallOrderFee -->
                  <td>
                       <app:storeMessage key="shop.orderdetail.table.summary.smallOrderFee" />
                  </td>
                  <td class="right">
                       <%=ClwI18nUtil.getPriceShopping(request, smallOrderFeeAmt, "&nbsp;")%>
                  </td>
              </tr>
          </logic:greaterThan>

<!-- Small Order Fee per Order: End  -->

<!-- Fuel Surcharge per Order: Begin  -->

          <logic:greaterThan name="esw.CheckOutEswForm" property="checkOutForm.fuelSurchargeAmt" value="0">
              <tr>
                  <bean:define id="fuelSurchargeAmt" name="esw.CheckOutEswForm" property="checkOutForm.fuelSurchargeAmt"/>
                  <!-- fuelSurchargeAmt -->
                  <td> 
                      <app:storeMessage key="shop.orderdetail.table.summary.fuelSurcharge" />
                  </td>
                  <td class="right">
                      <%=ClwI18nUtil.getPriceShopping(request, fuelSurchargeAmt, "&nbsp;")%>
                  </td>
              </tr>
          </logic:greaterThan>
		   
<!-- Fuel Surcharge per Order: End  -->		          
			<!-- Tax For the Whole Order (Tax Total): Begin -->
                                <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
								  <logic:present name="esw.CheckOutEswForm" property="checkOutForm.salesTax">
                                   <bean:define id="tax" name="esw.CheckOutEswForm" property="checkOutForm.salesTax"/>
                                   <logic:greaterThan name="esw.CheckOutEswForm" property="checkOutForm.salesTax" value="0">
                                      <% estimateOrderFl = true; %>
                                      <tr>
                                          <td>
                                              <app:storeMessage key="shop.orderdetail.table.summary.tax" />
                                          </td>
                                          <td class="right">
                                              <%=ClwI18nUtil.getPriceShopping(request,tax,"&nbsp;")%>
                                          </td>
									      <td></td>
                                      </tr>							         
                                   </logic:greaterThan>
                                  </logic:present>
				  </logic:equal>
                                <!-- Tax For the Whole Order (Tax Total): End -->	
                                
			
			       <% freightHandlFl = false; %>
                                <bean:define id="freightAmt" name="esw.CheckOutEswForm" property="checkOutForm.freightAmt"/>
                                <logic:greaterThan name="esw.CheckOutEswForm" property="checkOutForm.freightAmt" value="0">       
                                <%
                                  freightHandlFl = true;
                                %>
                                </logic:greaterThan>
                                <bean:define id="handlingAmt" name="esw.CheckOutEswForm" property="checkOutForm.handlingAmt"/>
                                <logic:greaterThan name="esw.CheckOutEswForm" property="checkOutForm.handlingAmt" value="0">         
                                <%
                                  freightHandlFl = true;
                                %>
                                </logic:greaterThan>
				
                                <% if(!pendingConsolidationFl || !freightHandlFl) { %>
                                      <tr class="total">
                                         <td>
                                              <% if (estimateOrderFl) { %>
                                                    <app:storeMessage key="shop.orderdetail.table.summary.orderTotalEst" />
                                              <% } else { %>
                                                    <app:storeMessage key="shop.orderdetail.table.summary.orderTotal" />
                                              <% } %>
                                         </td>
                                         <% BigDecimal lTotal = theForm.getCheckOutForm().getCartAmt().add(theForm.getCheckOutForm().getFreightAmt());
                                            lTotal = lTotal.add(theForm.getCheckOutForm().getHandlingAmt());
                                            lTotal = lTotal.add(theForm.getCheckOutForm().getFuelSurchargeAmt()!=null?theForm.getCheckOutForm().getFuelSurchargeAmt():new BigDecimal(0));
                                            lTotal = lTotal.add(theForm.getCheckOutForm().getSmallOrderFeeAmt()!=null?theForm.getCheckOutForm().getSmallOrderFeeAmt():new BigDecimal(0));
                                            lTotal = lTotal.add(theForm.getCheckOutForm().getDiscountAmt()); 
                                            if(theForm.getCheckOutForm().getSalesTax() != null){
                                               lTotal = lTotal.add(theForm.getCheckOutForm().getSalesTax());
                                            }
                                         %>
                                         <td class="right">
                                             <%=ClwI18nUtil.getPriceShopping(request,lTotal,"&nbsp;")%>
                                         </td>
                                         <td></td>
                                      </tr>
                                <% } %>
                                <!--  Order Total: End -->			 
                                <tr></tr>
                                <% if (estimateOrderFl) { %>
					<tr>
					       <td><app:storeMessage key="shop.orderdetail.table.summary.estimated" /></td>	
					</tr>
				<% } %>	
			
			</tbody>
			</table>
			</div>
		</div>
	<div class="bottom clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
</div>
</logic:equal>