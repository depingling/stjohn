
<%@page import="com.cleanwise.service.api.value.CustomerOrderRequestData"%>
<%@page import="com.cleanwise.service.api.value.ContractData"%>
<%@page import="com.cleanwise.view.forms.CheckoutForm"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.cleanwise.service.api.value.ShoppingCartDistDataVector"%>
<%@page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@page import="com.cleanwise.view.utils.ShopTool"%>
<%@page import="com.cleanwise.service.api.value.ShoppingCartData"%>
<%@page import="com.cleanwise.service.api.util.RefCodeNames"%>
<%@page import="com.cleanwise.service.api.util.Utility"%>
<%@page import="com.cleanwise.view.utils.Constants"%>
<%@page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
<%@page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.cleanwise.service.api.value.ShoppingCartItemData"%>
<%@page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@page import="java.util.List"%>
<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<bean:define id="newCheckOutForm" name="esw.CheckOutEswForm"  type="com.espendwise.view.forms.esw.CheckOutForm"/>
<bean:define id="checkOutForm" name="newCheckOutForm" property="checkOutForm" type="com.cleanwise.view.forms.CheckoutForm"/>
<bean:define id="scDistVector" name="checkOutForm" property="cartDistributors" type="ShoppingCartDistDataVector"/>
<bean:define id="appUser" name="ApplicationUser" type="com.cleanwise.view.utils.CleanwiseUser"/> 

<script type="text/javascript">
function refreshLocationBudgetGraph(formId, hiddenFieldId, hiddenFieldValue) {
    if (formId) {
        document.getElementById(hiddenFieldId).value = hiddenFieldValue;
        var selectBox = document.getElementById('distFreightVendorId');
        selectedItem = selectBox[selectBox.selectedIndex];
        document.getElementById('orderFeeId').value = selectedItem.value;
        document.getElementById(formId).submit();
    }
}
</script>

 <% 
 
 boolean f_paymetricsCC = "true".equals(session.getAttribute(Constants.PAYMETRICS_CC))?true:false;
 
 CleanwiseUser user = ShopTool.getCurrentUser(request);
 ShoppingCartData shoppingCartD = ShopTool.getCurrentShoppingCart(request);
 
 boolean crcManagerFl = appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.CRC_MANAGER);
 
 ShoppingCartDistDataVector distDV = checkOutForm.getCartDistributors(); 
 int distDVSize = 0;
 if(Utility.isSet(distDV)){
	 distDVSize = distDV.size();
 }
 boolean estimateOrderFl = false; 
 
 String utype = user.getUser().getUserTypeCd();
 boolean resaleItemsAllowed = ShopTool.isResaleItemsAllowed(request);

boolean pendingConsolidationFl = false;

CustomerOrderRequestData orderReq = checkOutForm.getOrderRequest();
String otype = "";

if ( orderReq != null && orderReq.getOrderType() != null ) {
  otype = orderReq.getOrderType();
}

if(RefCodeNames.ORDER_TYPE_CD.TO_BE_CONSOLIDATED.equals(otype)) {
  pendingConsolidationFl = true;
}
                            
int storeId = user.getUserStore().getStoreId();
ContractData contractData = user.getSite().getContractData();
ShoppingCartDistDataVector cartDistributorV = new ShoppingCartDistDataVector(shoppingCartD.getItems(), storeId, contractData);

BigDecimal totalDiscountCost = new BigDecimal(0);
totalDiscountCost = totalDiscountCost.setScale(2, BigDecimal.ROUND_HALF_UP);
totalDiscountCost = totalDiscountCost.add(cartDistributorV.getTotalDiscountCost());

HashMap discountPerDistHM = cartDistributorV.getDiscountCostPerDist();

Double selectedFreightAmount = 0.0;
Double selectedHandlingAmount = 0.0;
%>

<%
boolean distFreightFl = false;
boolean distFreightOptFl = false;
boolean distDiscountFl = false;
boolean distFuelSurchargeFl = false;
boolean distSmallOrderFeeFl = false;

BigDecimal orderSubTotal = new BigDecimal(0.0);
boolean estimateTax = false;

%>
 <logic:iterate id="scDistData" name="scDistVector" offset="0" indexId="DISTIDX"
       type="com.cleanwise.service.api.value.ShoppingCartDistData">
	
	
<%
	List sCartItems = scDistData.getShoppingCartItems();
	if(Utility.isSet(sCartItems)) {
		String prevCategory = "";
%>
<% int distIdTest0 = scDistData.getDistId();
int frTblIdTest = scDistData.getFrTblId();
Integer distIdTest = new Integer (scDistData.getDistId(frTblIdTest));
%>

 <div class="boxWrapper smallMargin squareTop">
                    <div class="top clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
                    <div class="content">
                        <div class="left clearfix">
			 <div class="twoColBox">                                        
			    <div class="column width70">
                            <h3 class="clear float"><bean:write name="scDistData" property="distributorName"/></h3>
			    </div>
			    <div class="column width30">
			     <p class="right"><span class="selectNav">Resale:&nbsp;&nbsp; <a href="#" class="all">All</a> <span>|</span> <a href="#" class="none">None</a></span></p>
			    </div>
			     </div>	
							<hr>
							<%
								Double distSubTotal = new Double(0);
								for(int ind=0; sCartItems!=null && ind<sCartItems.size();ind++ ) {
									ShoppingCartItemData scid = (ShoppingCartItemData)sCartItems.get(ind);
									distSubTotal+=scid.getAmount();
								}
							 	request.setAttribute("shoppingCartItems",sCartItems);
							%>
<!-- Begin: Shopping Cart Items Info  -->
							<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,Constants.PORTAL_ESW,"shoppingCartItems.jsp")%>' >
		              			<jsp:param name="viewType" value="checkOutView"/>
				            </jsp:include>
<!-- End: Shopping Cart Items Info -->

		<table class="orderTotal">
			<colgroup>
                        <col>
                        <col>
			<col width="50%">
			</colgroup>
                        <tbody>
                           <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
				<!-- Begin: Distributor Sub-total -->
                                <tr>
                                    <td>
                                       <app:storeMessage key="shop.orderdetail.table.summary.distributorSubtotal" />
                                     
                                    </td>
                                    <td class="right">
                                        <% BigDecimal distSubtotalBD = new BigDecimal(distSubTotal); %>
                                        <% distSubtotalBD = distSubtotalBD.setScale(2,BigDecimal.ROUND_HALF_UP); %>
                                        <%=ClwI18nUtil.getPriceShopping(request,distSubtotalBD,"&nbsp;")%>
					<% orderSubTotal = orderSubTotal.add(distSubtotalBD);  %>
                                    </td>
				    <td></td>
                                </tr>
				<!-- End: Distributor Sub-total -->
                                
				<!-- Begin: Freight AND Handling -->
			      <% boolean estimateFl = false; %> 
			      <% 
				BigDecimal freightAndHandlingAmount = BigDecimal.ZERO;
			      %>  
                              <%
                               if(Utility.isSet(scDistData.getDistFreightImplied())) {
				   distFreightFl = true; 
			      %>
				<bean:define id="freightImplied" name="scDistData" property="distFreightImplied" type="java.util.List"/>
				<logic:iterate id="impliedD" name="freightImplied" offset="0" indexId="impliedIDX"
							         	type="com.cleanwise.service.api.value.FreightTableCriteriaData">
				<%  
				boolean toBeDetermined = RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE.equals(impliedD.getFreightCriteriaTypeCd());
							         
				BigDecimal handlingAmount = BigDecimal.ZERO; 
				BigDecimal freightAmount = BigDecimal.ZERO;	 
							         
				if (!toBeDetermined) {
				    freightAmount = impliedD.getFreightAmount();
				    handlingAmount = impliedD.getHandlingAmount();
				}
				if (handlingAmount != null) {
				    handlingAmount = handlingAmount.setScale(2,BigDecimal.ROUND_HALF_UP);
				} else {
				    handlingAmount = new BigDecimal(0);
				}
				if (freightAmount != null) {
				     handlingAmount = handlingAmount.add(freightAmount.setScale(2,BigDecimal.ROUND_HALF_UP));    
				}
				freightAndHandlingAmount = freightAndHandlingAmount.add(handlingAmount.setScale(2,BigDecimal.ROUND_HALF_UP));
				if (toBeDetermined || handlingAmount.compareTo(BigDecimal.ZERO) != 0) {
				%>
                                <tr>
                                    <td>
                                         <!-- each entry in clw_freight_table_criteria DB table can have EITHER Freight OR Handling, not both at the same time ! -->
                                         <% if (impliedD.getFreightAmount() != null) { %>
                                    	       <app:storeMessage key="shop.orderdetail.table.summary.freight" />
                                         <% } %>
                                         <% if (impliedD.getHandlingAmount() != null) { %>
                                               <app:storeMessage key="shop.orderdetail.table.summary.handling" />
                                         <% } %>
                                         <logic:equal name="impliedD" property="freightCriteriaTypeCd" value="<%=RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE%>">
                                         	<% estimateFl = true; %> **
                                         </logic:equal>
                                    </td>
                                    <td class="right">
                                    	<logic:notEqual name="impliedD" property="freightCriteriaTypeCd" value="<%=RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE%>">
                                        	<%=ClwI18nUtil.getPriceShopping(request,handlingAmount,"&nbsp;")%>
                                        </logic:notEqual>
                                    </td>
				    <td> 
                                        <bean:write name="impliedD" property="shortDesc" /> 
                                    </td>
                                </tr>
                                <%} // checking not equals ZERO%>
    				</logic:iterate>
    				<%  }  %>
    				<%//If the Runtime Type is Selectable, then the dropdown containing the descriptions is displayed 
    				if(Utility.isSet(scDistData.getDistFreightOptions())) {
    					distFreightOptFl = true;
				%>
    				<bean:define id="freightOptions" name="scDistData" property="distFreightOptions" type="java.util.List"/>
    				<tr>
    				    <td>
                                    	 <app:storeMessage key="shop.orderdetail.table.summary.freight" />
                                    	  <%if(scDistData.getDistFreightOptions().size()>0) {%>
                                    	  	<span class="required">*</span>
                                    	  <%} %>
                                    </td>
                                    <td class="right">
                                         <bean:define id="selectedFrMsg" name="scDistData" property="distSelectableFreightMsg" type="String"/>
        								 <bean:define id="selectedFrAmt" name="scDistData" property="distSelectableFreightCost" />
        								 <bean:define id="selectedHandlingAmt" name="scDistData" property="distSelectableHandlingCost" />
        								 <%          if (null != selectedFrMsg && selectedFrMsg.trim().length() > 0) {  %>
										        		<bean:write name="scDistData" property="distSelectableFreightMsg" />
									    <%          } else {  
									    				if((Double)selectedFrAmt > 0.0) { 
									    					selectedFreightAmount = (Double)selectedFrAmt;				
									    %>
									    					<%=ClwI18nUtil.getPriceShopping(request,selectedFrAmt,"&nbsp;")%>				
									    <% 	} else {	selectedHandlingAmount = (Double)selectedHandlingAmt;
									    %>
									        			<%=ClwI18nUtil.getPriceShopping(request,selectedHandlingAmt,"&nbsp;")%>
									    <%          } } %>
                                    </td>
									<td> 
									
									  <%if(scDistData.getDistFreightOptions().size()>0) {%>
										 <% String select = ClwMessageResourcesImpl.getMessage(request,"shop.checkout.text.selectOption"); %>
                                         <html:select name="newCheckOutForm" property="checkOutForm.distFreightVendor"
                                         onchange="javascript:refreshLocationBudgetGraph('checkOutFormId','operationId','checkOut')" styleId="distFreightVendorId"> 
								          	<html:option value="<%=select %>" />
								          	<html:options collection="freightOptions" property="freightTableCriteriaId" labelProperty="shortDesc"  />
								        </html:select>
								         <%} %>
                                    </td>
                                 </tr>
                                
                               <%  } else {  %>
      					<html:hidden name="newCheckOutForm" property="checkOutForm.distFreightVendor" value=""/>
    				<%  }  %>
    
<!-- End: Freight AND Handling -->

<!-- Begin: Discount -->
 							<% BigDecimal dAmt2 = BigDecimal.ZERO; %>
							 <%if(Utility.isSet(scDistData.getDistDiscountImplied())) {
								 
								 distDiscountFl = true;

								    int frTblId = scDistData.getFrTblId();
								    int distId = scDistData.getDistId(frTblId); 
								    Integer distIdInt = new Integer( distId );
								    BigDecimal distIdBD = new BigDecimal(0);
								    distIdBD = (BigDecimal)discountPerDistHM.get(distIdInt);
							   %>
							   <%  if (totalDiscountCost != null) { %>
							    <%
							    BigDecimal zeroValue = new BigDecimal(0); 
							     if ( totalDiscountCost.compareTo(zeroValue)>0 ) {     	   
							        totalDiscountCost = totalDiscountCost.negate(); 
							     } 
							     totalDiscountCost = totalDiscountCost.setScale(2, BigDecimal.ROUND_HALF_UP);
							     checkOutForm.setDiscountAmt(totalDiscountCost);
							     checkOutForm.setDiscountAmtPerDist(discountPerDistHM); 
							     %>
							     <bean:define id="discountImplied" name="scDistData" property="distDiscountImplied"
							      type="java.util.List"/>
							      
							      <logic:iterate id="impliedDisc" name="discountImplied"
							         offset="0" indexId="impliedIDX"
							         type="com.cleanwise.service.api.value.FreightTableCriteriaData">
							    <%
							        if (distIdBD != null && distIdBD.compareTo(BigDecimal.ZERO) != 0) {
								%>
								
                                <tr>
                                    <logic:present name="impliedDisc" property="discount">
						                <bean:define id="disAmt" name="impliedDisc" property="discount" />
						                                
						                 <% /*** new code for Discount in DOLLARS and PERCENTAGE-DOLLARS ***/ %>
						                 <% dAmt2 = distIdBD; %>
						                 <%	if (distIdBD != null && distIdBD.compareTo(BigDecimal.ZERO) != 0) { 
						                	 	BigDecimal zeroValue1 = new BigDecimal(0); 
							                    if( dAmt2.compareTo(zeroValue1)>0 ) {    	   
							                     	dAmt2 = dAmt2.negate(); 
							                    } 
							                    dAmt2 = dAmt2.setScale(2, BigDecimal.ROUND_HALF_UP);
						                    %>
						                    <td>
		                                       <app:storeMessage key="shop.orderdetail.table.summary.discount" />
		                                    </td>
		                                    <td class="right negative">
                                        		<%=ClwI18nUtil.getPriceShopping(request,dAmt2,"&nbsp;")%> 
                                        	</td>
											<td>
		                                       <bean:write name="impliedDisc" property="shortDesc" />
		                                    </td> 
                                        	<%} %>
                                       </logic:present>
                                                                      
                                </tr>
                            <%} %>
			    
					    </logic:iterate>
					    <%  } %>    
					   <%  } %>
<!-- End: Discount -->

<!-- Begin: Small Order Fee -->
								
								<% BigDecimal smallOrderFeeAmt = BigDecimal.ZERO; %>                                
                                <%  if (Utility.isSet(scDistData.getDistSmallOrderFeeList())) {

                                	  distSmallOrderFeeFl = true;
                                %>
                                <bean:define id="smallOrderFeeList" name="scDistData" property="distSmallOrderFeeList" type="java.util.List"/>
                                <logic:iterate id="smallOrderFeeCriteria" name="smallOrderFeeList" offset="0" indexId="impliedIDX" type="com.cleanwise.service.api.value.FreightTableCriteriaData">
                                   <%
                                   boolean toBeDetermined = RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE.equals(smallOrderFeeCriteria.getFreightCriteriaTypeCd());
                                   if (!toBeDetermined) {
                                   		smallOrderFeeAmt = smallOrderFeeCriteria.getHandlingAmount();
                                   }
                                   if (toBeDetermined || (smallOrderFeeAmt != null && smallOrderFeeAmt.compareTo(BigDecimal.ZERO) != 0)) {
                                   %>        
                                     <tr>
                                     	<td>
                                     	 <app:storeMessage key="shop.orderdetail.table.summary.smallOrderFee" />
                                         <logic:equal name="smallOrderFeeCriteria" property="freightCriteriaTypeCd" value="<%=RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE%>">
                                            	<% estimateFl = true; %>**
                                         </logic:equal> 
                                          </td>   
                                         <td class="right">
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
<!-- End: Small Order Fee -->

<!-- Begin:  Fuel Surcharge -->
								<% BigDecimal fuelSurchargeAmt = BigDecimal.ZERO; %> 
                               <% if (Utility.isSet(scDistData.getDistFuelSurchargeList())) {

                            	   distFuelSurchargeFl = true;
                               %>
                                     <bean:define id="fuelSurchargeList" name="scDistData" property="distFuelSurchargeList" type="java.util.List"/>
                                     <logic:iterate id="fuelSurchargeCriteria" name="fuelSurchargeList" offset="0" indexId="impliedIDX" type="com.cleanwise.service.api.value.FreightTableCriteriaData"><%
                                        boolean toBeDetermined = RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE.equals(fuelSurchargeCriteria.getFreightCriteriaTypeCd());
                                     	if(!toBeDetermined) {
                                     		fuelSurchargeAmt = fuelSurchargeCriteria.getHandlingAmount();
                                     	}
                                        if (toBeDetermined || (fuelSurchargeAmt != null && BigDecimal.ZERO.compareTo(fuelSurchargeAmt) != 0)) {
                               %>
                                            <tr>
                                            	<td>
	                                            	<app:storeMessage key="shop.orderdetail.table.summary.fuelSurcharge" />
	                                                <logic:equal name="fuelSurchargeCriteria" property="freightCriteriaTypeCd" value="<%=RefCodeNames.FREIGHT_CRITERIA_TYPE_CD.ESTIMATE%>">
	                                                	<% estimateFl = true; %>**
	                                                </logic:equal>
                                                 
                                                </td>                                             
                                                <td class="right">
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
<!-- End:  Fuel Surcharge --> 
<!-- Begin:  Distributor Total ** --> 
                              	 <%    
                              	        BigDecimal distTotal = BigDecimal.ZERO;
                              	        /***
                              	        if (handlingAmount != null) {
                              	        	distTotal = distSubtotalBD.add(handlingAmount);
                              	        }  
                              	        ***/
                              	        if (freightAndHandlingAmount != null) {
                              	        	distTotal = distSubtotalBD.add(freightAndHandlingAmount);
                              	        }
                                        distTotal = distTotal.add(dAmt2);
                                        distTotal = distTotal.add(smallOrderFeeAmt);
                                        distTotal = distTotal.add(fuelSurchargeAmt);
                                        distTotal = distTotal.setScale(2,BigDecimal.ROUND_HALF_UP);
                                        
                                %>
                                <% //show as "Distributor Total" if there are multiple Distributors;
                                   //show as "Order Total" if there is only one Distributor
                                   if (distDVSize > 1) {
                                      %>                                          	                                
                                      <tr class="total">
                                         <td>
                                        	 <app:storeMessage key="shop.orderdetail.table.summary.distributorTotal" />
                                             <% if (estimateFl) { %>
                                                   **
                                             <% } %>
                                         </td>
                                         <td class="right">  
                                             <%=ClwI18nUtil.getPriceShopping(request,distTotal,"&nbsp;")%>                                      
                                         </td>
									     <td></td>
								      </tr>
								   <% } %> 
								 
<!-- End:  Distributor Total ** --> 
								<% if (estimateFl) { %>
								      <% estimateOrderFl = true; %>
								<%    if (DISTIDX < (distDVSize - 1)) { %>
								         <tr>
								              <td><app:storeMessage key="shop.orderdetail.table.summary.estimated" /></td>	
								         </tr>
								      <% } %>
                                <% } %>
                            </logic:equal> 
							</tbody></table>

                        </div>
                    </div>
                    <div class="bottom clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
                </div>
   <%}%>
 </logic:iterate>
 <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
<div class="boxWrapper smallMargin squareCorners">
	<div class="top clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
	
		<div class="content">
			<div class="left clearfix">
			
			<h2><app:storeMessage key="shop.orderStatus.text.orderSummary" /></h2>
			<bean:define id="freightAmt" name="checkOutForm" property="freightAmt"/>                                
			<bean:define id="handlingAmt" name="checkOutForm" property="handlingAmt"/>                                  
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
					    <%=ClwI18nUtil.getPriceShopping(request,orderSubTotal,"&nbsp;")%>
					</td>
					<td></td>
				</tr>
				<!-- Freight per Order: Begin  -->                                                        
				      <% if ((appUser.isaCustServiceRep() || crcManagerFl) && !f_paymetricsCC) { %>
				      <tr>
					  <td>
					       <app:storeMessage key="shop.orderdetail.table.summary.freight" />
					  </td>
					  <td class="right"> 
					       <html:text name="checkOutForm" property="freightAmtString" size="8"/>
					  <td>
				      </tr>
				      <% } else { %>
				     <html:hidden name="checkOutForm" property="freightAmtString" value="<%=checkOutForm.getFreightAmtString()%>" />
					  <logic:greaterThan name="checkOutForm" property="freightAmt" value="0">
					   <tr>
					       <td>
						    <app:storeMessage key="shop.orderdetail.table.summary.freight" />
					       </td>
					       <!-- freightAmt -->
					       <td class="right">
						    <%=ClwI18nUtil.getPriceShopping(request,freightAmt,"&nbsp;")%><bean:write name="checkOutForm" property="estimateFreightString" />
					       </td>
				       
					   </tr>
					  </logic:greaterThan>
				     <% } %>
				<!-- Freight per Order: End  -->
				
				<!-- Handling per Order: Begin  -->
				<%

				 String applyHandlingCharge = null;
				 try{
				    applyHandlingCharge = checkOutForm.getSite().getSiteFieldValue("Apply contract handling charges");
				  }catch(Exception e){e.printStackTrace();}
				 
				 if (applyHandlingCharge != null && applyHandlingCharge.toLowerCase().equals("n")) {
				   handlingAmt = new java.math.BigDecimal("0");
				   checkOutForm.setHandlingAmt((BigDecimal) handlingAmt);
				 }
				 
				 %>
				<% if ((appUser.isaCustServiceRep() || crcManagerFl) && !f_paymetricsCC) { %>
				    <tr>
					<td>
					     <app:storeMessage key="shop.orderdetail.table.summary.handling" />  
					</td>
					<td class="right"> 
					    <html:text name="checkOutForm" property="handlingAmtString" size="8"/>
					</td>
				    </tr>
				 <% } else { %>
				    <html:hidden name="checkOutForm" property="handlingAmtString" value="<%=checkOutForm.getHandlingAmtString()%>" />
					      <logic:greaterThan name="checkOutForm" property="handlingAmt" value="0">
				    <tr>
					<td>
					     <app:storeMessage key="shop.orderdetail.table.summary.handling" />
					</td>
					<!-- handlingAmt -->
					<td class="right">
					     <%=ClwI18nUtil.getPriceShopping(request,handlingAmt,"&nbsp;")%>
					</td>
				    </tr>
					      </logic:greaterThan>
				 <% } %>                                 
			       <!-- Handling per Order: End  -->
			       
			       <!-- Discount per Order: begin  -->
				
				<tr>
			       <!-- cartDiscount -->
			       <% BigDecimal discountAmt = checkOutForm.getDiscountAmt();// I have to calculate discount here %>
			       <% if (discountAmt != null && discountAmt.compareTo(new BigDecimal(0))!=0 ) { %>
				<td>
				    <app:storeMessage key="shop.orderdetail.table.summary.discount" />
				</td>
				<td class="right negative">
				    <%=ClwI18nUtil.getPriceShopping(request,discountAmt,"&nbsp;")%> 
				</td>	   
			       <% } %>
			       </tr>
			       <!-- Discount per Order: End  -->
			       
				<!-- Small Order Fee per Order: Begin  -->
			      <logic:greaterThan name="checkOutForm" property="smallOrderFeeAmt" value="0">
				  <tr valign=top>
				      <bean:define id="smallOrderFeeAmt" name="checkOutForm" property="smallOrderFeeAmt"/>
				      <!-- smallOrderFee -->
				      <td>
					   <app:storeMessage key="shop.orderdetail.table.summary.smallOrderFee" />
				      </td>
				      <td class="right">
					   <%=ClwI18nUtil.getPriceShopping(request, smallOrderFeeAmt, "&nbsp;")%>
				      <td>
				  </tr>
			      </logic:greaterThan>
			    <!-- Small Order Fee per Order: End  -->
			    
			    <!-- Fuel Surcharge per Order: Begin  -->
			       <% if (appUser.isaCustServiceRep() || crcManagerFl) { %>
				 <% BigDecimal fuelSurchargeAmt = checkOutForm.getFuelSurchargeAmt();%>
			 <% if (fuelSurchargeAmt != null && fuelSurchargeAmt.compareTo(new BigDecimal(0))!=0 ) { %>
				     <tr>
			       <td>
				    <app:storeMessage key="shop.orderdetail.table.summary.fuelSurcharge" />
			       </td>
			       <td class="right">
				    <%=ClwI18nUtil.getPriceShopping(request, fuelSurchargeAmt, "&nbsp;")%>
			       <td>							
				     </tr>
				     <% } %>
			       <% } else { %>
			   <tr>
			     <bean:define id="fuelSurchargeAmt" name="checkOutForm" property="fuelSurchargeAmt"/>
			     <!-- fuelSurchargeAmt -->
			     
			     <% BigDecimal fuelSurchargeAmt2 = checkOutForm.getFuelSurchargeAmt();%>
			     <% if (fuelSurchargeAmt2 != null && fuelSurchargeAmt2.compareTo(new BigDecimal(0))!=0 ) { %>
			       <td>
				    <app:storeMessage key="shop.orderdetail.table.summary.fuelSurcharge" />
			       </td>
			       <td class="right">
				    <%=ClwI18nUtil.getPriceShopping(request, fuelSurchargeAmt, "&nbsp;")%>
			       <td>							                  
			     <% } %>
			   </tr>
				<%}%>
				
			<!-- Fuel Surcharge per Order: End  -->
			
			<!-- Tax For the Whole Order (Tax Total): Begin -->
								<logic:present name="checkOutForm" property="salesTax">
                                   <bean:define id="tax" name="checkOutForm" property="salesTax"/>
                                   <logic:greaterThan name="checkOutForm" property="salesTax" value="0">
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
                        <!-- Tax For the Whole Order (Tax Total): End -->
			
			       <% boolean freightHandlFl = false; %>
                                <bean:define id="freightAmt" name="checkOutForm" property="freightAmt"/>
                                <logic:greaterThan name="checkOutForm" property="freightAmt" value="0">       
                                <%
                                  freightHandlFl = true;
                                %>
                                </logic:greaterThan>
                                <bean:define id="handlingAmt" name="checkOutForm" property="handlingAmt"/>
                                <logic:greaterThan name="checkOutForm" property="handlingAmt" value="0">         
                                <%
                                  freightHandlFl = true;
                                %>
                                </logic:greaterThan>
			       
			       <!--  Order Total: Begin -->
                                <% if(!pendingConsolidationFl || !freightHandlFl) { %>
                                      <tr class="total">
                                         <td>
                                         	 <app:storeMessage key="shop.orderdetail.table.summary.orderTotal" />
                                              <% if (estimateOrderFl) { %>
                                                   **                                              
                                              <% } %>
                                         </td>
                                         <% BigDecimal lTotal = checkOutForm.getCartAmt().add(checkOutForm.getFreightAmt());
                                            lTotal = lTotal.add(checkOutForm.getHandlingAmt());
                                            lTotal = lTotal.add(checkOutForm.getFuelSurchargeAmt()!=null?checkOutForm.getFuelSurchargeAmt():new BigDecimal(0));
                                            lTotal = lTotal.add(checkOutForm.getSmallOrderFeeAmt()!=null?checkOutForm.getSmallOrderFeeAmt():new BigDecimal(0));
                                            lTotal = lTotal.add(checkOutForm.getDiscountAmt());
                                            if(selectedFreightAmount > 0.0){
                                            	lTotal = lTotal.add(new BigDecimal(selectedFreightAmount));
                                            } else if(selectedHandlingAmount > 0.0){
                                            	lTotal = lTotal.add(new BigDecimal((Double)selectedHandlingAmount));
                                            }
                                            if(checkOutForm.getSalesTax() != null){
                                               lTotal = lTotal.add(checkOutForm.getSalesTax());
                                            }
                                         %>
                                         <td class="right">
                                             <%=ClwI18nUtil.getPriceShopping(request,lTotal,"&nbsp;")%>
                                         </td>
					 <td></td>
                                      </tr>
                                <% } %>
                                <!--  Order Total: End -->			
				
			</tbody>
			</table>
			</div>
		</div>
	<div class="bottom clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
</div>
</logic:equal>
