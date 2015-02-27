<%@page import="com.cleanwise.view.forms.CheckoutForm"%>
<%@page import="com.cleanwise.service.api.value.SiteInventoryInfoView"%>
<%@page import="java.util.HashMap"%>
<%@page import="com.cleanwise.service.api.value.ShoppingCartDistDataVector"%>
<%@page import="com.cleanwise.service.api.util.RefCodeNames"%>
<%@page import="com.cleanwise.service.api.value.ShoppingCartData"%>
<%@page import="com.cleanwise.view.utils.ShopTool"%>
<%@page import="com.cleanwise.view.forms.ShoppingCartForm"%>
<%@page import="com.cleanwise.service.api.util.SessionDataUtil"%>
<%@page import="com.cleanwise.service.api.dto.OrderSearchDto"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="com.cleanwise.service.api.value.OrderStatusDescData"%>
<%@page import="java.util.TimeZone"%>
<%@page import="java.util.Date"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>
<%@ page import="com.cleanwise.service.api.dto.LocationSearchDto" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.service.api.value.AddressData" %>
<%@ page import="com.cleanwise.service.api.value.BusEntityData" %>
<%@ page import="com.cleanwise.service.api.value.OrderAddressData" %>
<%@ page import="com.cleanwise.service.api.value.ShoppingCartItemData" %>
<%@ page import="com.cleanwise.service.api.value.ShoppingInfoData" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>

<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%
String viewShoppingCart = request.getParameter("viewShoppingCartAction");

%>
<script type="text/javascript">
	function removeItemFromShoppingCart(valueToSet) {
		 document.getElementById('removeItemId').value=valueToSet;
		 document.getElementById('operationId').value='<%=Constants.PARAMETER_OPERATION_VALUE_REMOVE_ITEM_FROM_CART %>';
		 setActionAndSubmitForm('shoppingFormId','<%=viewShoppingCart  %>');
	}
	
	
</script>

<%
							//Begin:
							CleanwiseUser appUser = ShopTool.getCurrentUser(request);
	
							boolean editCartItems = false,
									quickOrderView = false,
									addToCartListView = false,
									orderGuideView = false,
									checkoutView = false,
									shoppingCartView = false,
									invShoppingCartView = false,
									inventoryListView = false;

							boolean isPhysicalCartAvailable = ShopTool.isPhysicalCartAvailable(request);
                            boolean isInventoryCartOpened = ShopTool.hasInventoryCartAccessOpen(request);
							
							String viewType = null;							
							viewType = request.getParameter("viewType");
							boolean showOnlyNonParItems = false;
							boolean showOnlyParItems = false;

							if(viewType!=null && viewType.equals("PAR")) { //PAR Items
								showOnlyParItems = true;
							}
							else if(viewType!=null && viewType.equals("NON-PAR")){ //Only NON-PAR Items
								showOnlyNonParItems = true;
							} 
							else if(viewType!=null && viewType.equals("checkOutView")) { //Check Out view
								checkoutView = true;
							}
							else if(viewType!=null && viewType.equals("viewShoppingCart")){ //Both PAR and Non-PAR items 
								showOnlyParItems = true;
								showOnlyNonParItems = true;
							}

							if(showOnlyParItems && (isPhysicalCartAvailable || isInventoryCartOpened)) {
								shoppingCartView = true;
								invShoppingCartView = true;
								inventoryListView = true;
							}

							if(showOnlyNonParItems) {
								editCartItems = true;
								shoppingCartView = true;
							}

							int countOfItems = 0;
							String formName = "";
							if(!checkoutView) { %>
<bean:define id="shoppingForm" name="esw.ShoppingForm"  type="com.espendwise.view.forms.esw.ShoppingForm"/> 
<html:hidden property="itemIdToRemove" styleId="removeItemId" />
							<%
								ShoppingCartForm shoppingCartForm = null;
								shoppingCartForm = shoppingForm.getShoppingCartForm();
								ShoppingCartData shoppingCart = shoppingCartForm.getShoppingCart();
								shoppingCartForm.setCartItems(shoppingCart.getItems());
							    List sCartItems = shoppingCartForm.getCartItems();
								if(sCartItems!=null)
									countOfItems = sCartItems.size();
							    request.setAttribute("shoppingCartItems",sCartItems);
								shoppingCart.orderByCategory(sCartItems);
								formName = "shoppingCartForm";
							 } else {
								List sCartItems = (List)request.getAttribute("shoppingCartItems");
							    if(sCartItems!=null)
									countOfItems = sCartItems.size();
							    formName = "checkOutForm";
							 }
							
							 String inputNameQty = "";
							 String inputNameOnHand = "";
							 String prevCategory = "";
						  
						//End: 
java.util.Set<Integer> catsWithAutOrderedItems = new java.util.HashSet<Integer>();
				      %>
<logic:iterate id="sciD" name="shoppingCartItems" offset="0" indexId="IDX"
						   type="ShoppingCartItemData"><%
if ((isPhysicalCartAvailable || isInventoryCartOpened) && sciD.getIsaInventoryItem() && sciD.getAutoOrderEnable() && sciD.getInventoryParValue() > 0 && sciD.getCategory() != null) {
    catsWithAutOrderedItems.add(sciD.getCategory().getCatalogCategoryId());
}%></logic:iterate>
					  <logic:iterate id="sciD" name="shoppingCartItems" offset="0" indexId="IDX"
						   type="ShoppingCartItemData">

						     <bean:define id="orderNumber" name="sciD" property="orderNumber"/>
						     <bean:define id="itemId" name="sciD" property="product.productId" type="java.lang.Integer" />
						     <bean:define id="distName" name="sciD" property="product.catalogDistributorName" />

						<%
									if(!checkoutView) {
										inputNameQty = "cartLine[" + IDX + "].quantityString";				  
										inputNameOnHand = "cartLine[" + IDX + "].inventoryQtyOnHandString";
									} else {
										inputNameQty = "";
										inputNameOnHand="";
									}
										// close the table if category is changed.
										if( IDX==0 || !prevCategory.equals(sciD.getCategoryPath()) ) {

											if(IDX!=0 || IDX!=(countOfItems-1) ) { // End the table before starting new table.
													%>
														</tbody>
													</table>
													<%
											}									
										if(checkoutView || (showOnlyParItems && showOnlyNonParItems) || (showOnlyParItems && sciD.getIsaInventoryItem())
										|| (showOnlyNonParItems && !sciD.getIsaInventoryItem())) {
											
										%>
										
										 
											<div class="twoColBox">
											<% if (appUser.getUserAccount().isSupportsBudget()) { %>
												<div class="column width80">
												  <h3><bean:write name="sciD" property="categoryPathForNewUI"/> </h3>
												</div>
												<div class="column width20">
											      <h3>
											      <% if ( sciD.getProduct().getCostCenterId() > 0 ) { %>
														<%= sciD.getProduct().getCostCenterName() %>
													<% } %>
											      
											      </h3>
							
												</div>
												<%}else{%>
													<div>
												  <h3><bean:write name="sciD" property="categoryPathForNewUI"/> </h3>
												</div>
												<%}%>
											</div>
						<% // A message needs to be displyed over PAR Items table in PAR Order
						if (sciD.getCategory() != null && catsWithAutOrderedItems.contains(sciD.getCategory().getCatalogCategoryId()) 
								&& showOnlyParItems ) {%>
									<hr/>
										 <p class="right topMargin"><span class="alert">*</span> <app:storeMessage 		key="shoppingCart.text.itemOrderedAutomatically" /></p>
						<%}%>
										 <table class="order">
				                            		
				                                     <app:productCatalogHeader
													          viewOptionEditCartItems="<%=editCartItems%>"
													          viewOptionQuickOrderView="<%=quickOrderView%>"
													          viewOptionAddToCartList="<%=addToCartListView%>"
													          viewOptionOrderGuide="<%=orderGuideView%>"
													          viewOptionShoppingCart="<%=shoppingCartView%>"
													          viewOptionCheckout="<%=checkoutView%>"
													          viewOptionInventoryList="<%=inventoryListView%>"
															  viewOptionInvShoppingCart="<%=invShoppingCartView%>"
															  index="<%= IDX%>"
													   />
													   
													    
				                             <tbody>
											 <%
												 prevCategory = sciD.getCategoryPath();
												}
													
											}

											if(checkoutView || (showOnlyParItems && showOnlyNonParItems) || (showOnlyParItems && sciD.getIsaInventoryItem())
												|| (showOnlyNonParItems && !sciD.getIsaInventoryItem())) {
											%>
				                              	
													
												<!-- END: item display <%=IDX%> -->
												
													
												<%
												String sourceStr = "orderGuide";
												if(quickOrderView){
													sourceStr = "quickOrder";
												}
												//TODO: need to modify below link
												String itemLink1 = "shopping.do?operation="+Constants.PARAMETER_OPERATION_VALUE_ITEMS+"&source="+sourceStr+"&itemId="
												  +itemId+"&qty="+sciD.getQuantity();
												 // String trColor=ClwCustomizer.getEvenRowColor(request.getSession(),rowIdx++);
                                                String parHistory = getParItemActivity(sciD, IDX, request);
                                                String trClass = "";
                                                if (Utility.isSet(parHistory)){
                                                    trClass = " class=\"border-bot-none\"";
                                                }
												%>
				
				                                 <!-- Repeating Row - needs two rows for each entry to accomodate the comment line which needs colspan -->
                                 				<tr<%=trClass%>>
                                 					<app:displayProductCatalogItems
												        viewOptionEditCartItems="<%=editCartItems%>"
												        viewOptionQuickOrderView="<%=quickOrderView%>"
												        viewOptionAddToCartList="<%=addToCartListView%>"
												        viewOptionOrderGuide="<%=orderGuideView%>"
												        viewOptionShoppingCart="<%=shoppingCartView%>"
												        viewOptionCheckout="<%=checkoutView%>"
												        viewOptionInventoryList="<%=inventoryListView%>"
														viewOptionInvShoppingCart="<%=invShoppingCartView%>"
												        name="sciD"
												        link="<%=itemLink1%>"
												        index="<%=IDX%>"
												        inputNameQuantity="<%=inputNameQty %>"
														inputNameOnHand="<%=inputNameOnHand %>"
														formName="<%=formName %>"
														/>
                                 				</tr>
                                                <%=parHistory%>
                                        <% }
											if( IDX==(countOfItems-1)) { 
										%>
                                 			</tbody>
                                 		</table>
										<%
												 
										}
				%>
				</logic:iterate>
                
<%!
private String getParItemActivity(ShoppingCartItemData sciD, Integer IDX, HttpServletRequest request){
    StringBuffer strBuf = new StringBuffer();
    boolean firstFlag = true;
    if (ShopTool.isPhysicalCartAvailable(request)) {
        List itemHist = sciD.getItemShoppingCartHistory();
        if ( null != itemHist && itemHist.size() > 0) {
              int histLastIdx = itemHist.size() - 1;                  

              for (int idx = 0; idx < itemHist.size(); idx++) {
                ShoppingInfoData sid = (ShoppingInfoData) itemHist.get(idx);

                if (sid.getArg0() != null && sid.getArg0().equals("nothing")) {
                    // get previous. If it was the same then continue
                    if (idx < histLastIdx) {
                        ShoppingInfoData sidPrev = (ShoppingInfoData) itemHist.get(idx+1);
                        if ((sidPrev.getArg0() != null &&
                             sidPrev.getArg0().equals(sid.getArg0())) ||
                            (sidPrev.getArg1() != null &&
                             sidPrev.getArg1().equals(sid.getArg0()))
                            ) {
                            continue;
                        }
                    } else {
                        continue;
                    }
                }
                if (sid.getArg0() != null &&
                        sid.getArg1() != null &&
                        sid.getArg0().equals(sid.getArg1())) {
                    continue;
                }
                if (sid.getArg0() != null &&
                        sid.getArg0().trim().equals("0") &&
                        sid.getMessageKey().equals("shoppingMessages.text.onHandQtySet")) {
                    // get previous. If it was the same then continue
                    if (idx < histLastIdx) {
                        ShoppingInfoData sidPrev = (ShoppingInfoData) itemHist.get(idx+1);
                        if ((sidPrev.getArg0() != null &&
                             sidPrev.getArg0().equals(sid.getArg0())) ||
                            (sidPrev.getArg1() != null &&
                             sidPrev.getArg1().equals(sid.getArg0()))
                            ) {
                            continue;
                        }
                    }
                }
                String messKey = sid.getMessageKey();
                String message = null;
                if(messKey==null){ 
                    message = sid.getValue();
                } else {
                    message = ClwI18nUtil.getMessage(request, messKey, new Object[]{sid.getArg0(), sid.getArg1(), sid.getArg2(), sid.getArg3()});
                }
                String dateTime = ClwI18nUtil.formatDateTime(request,sid.getAddDate());
                String byStr = ClwI18nUtil.getMessage(request, "shoppingItems.text.by");
                
                if (firstFlag) { 
                    firstFlag = false;
                    strBuf.append("<!-- START: item audit " + IDX + " -->");
                    strBuf.append("<tr>");
                    strBuf.append("<td colspan=\"7\" class=\"addpadtop0\">");                        
                }
                strBuf.append("<div class=\"modification\">");
                strBuf.append("<div class=\"modify-comment\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + message + "</div>");
                strBuf.append("<div class=\"modify-date-userid\">" + dateTime + "</div>");
                strBuf.append("<div class=\"modify-date-userid\">" + byStr + "&nbsp;" + sid.getModBy() + "</div>");
                strBuf.append("</div>");
            }
        }
    }
    
    if (!firstFlag){
        strBuf.append("</div>");
        strBuf.append("</tr>");
        strBuf.append("<!-- END: item audit " + IDX + " -->");
    }

    return strBuf.toString();        
}
%>
				