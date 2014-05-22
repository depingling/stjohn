<%@page import="com.cleanwise.view.forms.EditOrderGuideForm"%>
<%@page import="com.cleanwise.service.api.value.OrderGuideData"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.cleanwise.service.api.value.OrderGuideDataVector"%>
<%@page import="java.util.Map"%>
<%@ page import="com.cleanwise.service.api.util.SessionDataUtil" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.service.api.value.SiteData" %>
<%@ page import="com.cleanwise.view.forms.UserShopForm" %>
<%@ page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>
<%@page import="com.cleanwise.view.utils.ShopTool"%>
<%@page import="com.cleanwise.service.api.util.RefCodeNames"%>

<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%
	//assume that no subtab should be selected
	String dashboardTabClass = "";
	String productsTabClass = "";
	String ordersTabClass = "";
	String controlsTabClass = "";
	String contentTabClass = "";
	
	//determine if a subtab should be selected
    String SELECTED_TAB_CLASS = "class=\"selected\"";
	String activeTab = Utility.getSessionDataUtil(request).getSelectedSubTab();
    if (Constants.TAB_DASHBOARD.equalsIgnoreCase(activeTab)) {
    	dashboardTabClass = SELECTED_TAB_CLASS;
    }
    else if (Constants.TAB_PRODUCTS.equalsIgnoreCase(activeTab)) {
    	productsTabClass = SELECTED_TAB_CLASS;
    }
    else if (Constants.TAB_ORDERS.equalsIgnoreCase(activeTab)) {
    	ordersTabClass = SELECTED_TAB_CLASS;
    }
    else if (Constants.TAB_CONTROLS.equalsIgnoreCase(activeTab)) {
        controlsTabClass = SELECTED_TAB_CLASS;
    }
    else if (Constants.TAB_CONTENT.equalsIgnoreCase(activeTab)) {
        contentTabClass = SELECTED_TAB_CLASS;
    }

 	String dashboardLink = "userportal/esw/dashboard.do";
    
   	String productsLink = "userportal/esw/shopping.do";
   	
   	String ordersLink = "userportal/esw/orders.do";
   	
   	String controlsLink = "userportal/esw/controls.do";
	
	String contentLink = "userportal/esw/logon.do?operation=" + Constants.PARAMETER_OPERATION_VALUE_SHOW_CONTENT;
	
    StringBuilder shoppingCatalogLink = new StringBuilder(50);
    shoppingCatalogLink.append(session.getAttribute("pages.root"));
    shoppingCatalogLink.append("/userportal/esw/shopping.do?");
    shoppingCatalogLink.append(Constants.PARAMETER_OPERATION);
    shoppingCatalogLink.append("=");
    shoppingCatalogLink.append(Constants.PARAMETER_OPERATION_VALUE_SHOP_BY_CATALOG);
    
    StringBuilder shoppingShoppingListsLink = new StringBuilder(50);
    shoppingShoppingListsLink.append("userportal/esw/shopping.do?");
    shoppingShoppingListsLink.append(Constants.PARAMETER_OPERATION);
    shoppingShoppingListsLink.append("=");
    shoppingShoppingListsLink.append(Constants.PARAMETER_OPERATION_VALUE_SHOP_BY_LISTS);
    
    StringBuilder allOrdersLink = new StringBuilder(50);
    allOrdersLink.append("userportal/esw/orders.do?");
    allOrdersLink.append(Constants.PARAMETER_OPERATION);
    allOrdersLink.append("=");
    allOrdersLink.append(Constants.PARAMETER_OPERATION_VALUE_SHOW_ALL_ORDERS);
    
    StringBuilder orderSchedulesLink = new StringBuilder(50);
    orderSchedulesLink.append("userportal/esw/orders.do?");
    orderSchedulesLink.append(Constants.PARAMETER_OPERATION);
    orderSchedulesLink.append("=");
    orderSchedulesLink.append(Constants.PARAMETER_OPERATION_VALUE_SHOW_ORDER_SCHEDULES);
    
    StringBuilder futureOrdersLink = new StringBuilder(50);
    futureOrdersLink.append("userportal/esw/orders.do?");
    futureOrdersLink.append(Constants.PARAMETER_OPERATION);
    futureOrdersLink.append("=");
    futureOrdersLink.append(Constants.PARAMETER_OPERATION_VALUE_SHOW_FUTURE_ORDERS);
    
    StringBuilder quickShopLink = new StringBuilder(50);
    quickShopLink.append("userportal/esw/shopping.do?");
    quickShopLink.append(Constants.PARAMETER_OPERATION);
    quickShopLink.append("=");
    quickShopLink.append(Constants.PARAMETER_OPERATION_VALUE_QUICK_SHOP);
    
    StringBuilder productLimitsLink = new StringBuilder(50);
    productLimitsLink.append("userportal/esw/controls.do");
    productLimitsLink.append('?');
    productLimitsLink.append(Constants.PARAMETER_OPERATION);
    productLimitsLink.append("=");
    productLimitsLink.append(Constants.PARAMETER_OPERATION_VALUE_SHOW_PRODUCT_LIMITS);
    
    StringBuilder parValuesLink = new StringBuilder(50);
    parValuesLink.append("userportal/esw/controls.do");
    parValuesLink.append('?');
    parValuesLink.append(Constants.PARAMETER_OPERATION);
    parValuesLink.append("=");
    parValuesLink.append(Constants.PARAMETER_OPERATION_VALUE_SHOW_PAR_VALUES);
    
    StringBuilder cssStyles = new StringBuilder(50);
	cssStyles.append("clearfix");
    String additionalCssStyles = request.getParameter(Constants.PARAMETER_ADDITIONAL_CSS_STYLES);
    if (Utility.isSet(additionalCssStyles)) {
    	cssStyles.append(" ");
    	cssStyles.append(additionalCssStyles);
    }

%>
<script language="JavaScript">
	function submitShoppingList(operationValue,ogId) {
		document.getElementById('slOperation').value=operationValue;
		document.getElementById('orderGuideId').value=ogId;
		document.getElementById('shoppingListFormId').submit();
	}
</script>

    <div id="headerWrapper" class="<%=cssStyles%>">
		<div id="header" class="tabbedHeader">
			<ul class="clearfix">
				<li <%=dashboardTabClass%>>
           			<html:link action="<%=dashboardLink %>">
   						<span>
   							<app:storeMessage key="shopping.label.dashboard" />
   						</span>
     				</html:link>
           		</li>
				<li <%=productsTabClass%>>
     				<html:link action="<%=productsLink %>">
   						<span>
   							<app:storeMessage key="shopping.label.products" />
   						</span>
     				</html:link>
                    <ul>
                	<%
						//if a location with a product catalog has been selected, display Catalog links
						CleanwiseUser user = ShopTool.getCurrentUser(request);
						SiteData location = user.getSite();
						if (location != null) {
							SessionDataUtil sessionData = Utility.getSessionDataUtil(request);
							UserShopForm userShopForm = sessionData.getUserShopForm();
							if (userShopForm != null) {
								String menuLabel = ClwMessageResourcesImpl.getMessage(request,"shopping.product.label.catalog");
								
								String cssStyle;
								if((userShopForm.getCatalogMenu().getSubItems())!= null) {
								     cssStyle = "subNav";
								}
								else {
								     cssStyle = "";
								}
               		%>
               				<app:productCatalogMenu
               					menuItems="<%=userShopForm.getCatalogMenu()%>"
               					categoryToItemMap="<%=userShopForm.getCatalogMenuCategoryToItemMap()%>"
               					parentCssStyle="<%=cssStyle%>"
               					noLinkCssStyle="noLink"
               					rootUrl="<%=shoppingCatalogLink.toString()%>"
               					rootText="<%=menuLabel%>"
               					menuItemParameterName="<%=Constants.PARAMETER_CATALOG_ITEM_KEY%>"/>
                		<%
								}
							}
                		%>
							<%
								SessionDataUtil sessionData = Utility.getSessionDataUtil(request);
		                    	Map<Integer,Integer> templateShoppingListItemsCount = sessionData.getTemplateShoppingListIds();
								Map<Integer,Integer> userShoppingListsItemsCount = sessionData.getUserShoppingListIds();
								boolean shoppingListsExist = (templateShoppingListItemsCount != null && 
														templateShoppingListItemsCount.size() > 0) || 
													 (userShoppingListsItemsCount != null && 
													 	userShoppingListsItemsCount.size() > 0);
								String shoppingListLinkClass = null;
								if (shoppingListsExist) {
									shoppingListLinkClass = "class=\"subNav\"";
								}
								else {
									shoppingListLinkClass = "";
								}
								%>
                 
                        <li <%=shoppingListLinkClass%>>
							<html:link action="<%=shoppingShoppingListsLink.toString()%>">
                				<app:storeMessage key="shopping.product.label.shoppingLists" />
                			</html:link>
							<%
								if (shoppingListsExist) {
							%>
                			<html:form styleId="shoppingListFormId" action="userportal/esw/shopping.do">
	                           	
	                           	<html:hidden property="<%=Constants.PARAMETER_OPERATION%>" value="orderGuideSelect" styleId="slOperation"/>
		       					<html:hidden property="editOrderGuideForm.orderGuideId" styleId="orderGuideId" value="0"/>
		       							                                    	
                    		</html:form>
                    							
                			 <ul style="visibility: hidden; display: none;" class="sf-js-enabled">
                			<%
	                               
		                            if(templateShoppingListItemsCount != null && templateShoppingListItemsCount.size()>0) {
		                            	Integer countOfItemsInTemplateShopList = new Integer(0);
		                         		
		                         		UserShopForm userShopForm = sessionData.getUserShopForm();
		                         		OrderGuideDataVector ogVector = userShopForm.getTemplateOrderGuides();
		                         		Iterator itr = ogVector.iterator();
		                         		OrderGuideData orderGuideData;
		                         		while(itr.hasNext()) {
		                         			orderGuideData = (OrderGuideData)itr.next();
		                             		countOfItemsInTemplateShopList = templateShoppingListItemsCount.get(orderGuideData.getOrderGuideId());
											if(countOfItemsInTemplateShopList==null) {
												countOfItemsInTemplateShopList = new Integer(0);
											}
		                             		%>
		                             		
		                             		 <li>
				                                <a href="javascript:submitShoppingList('orderGuideSelect','<%= (orderGuideData.getOrderGuideId())%>')">
				                                	<%=Utility.encodeForHTML(orderGuideData.getShortDesc()) %>
				                                	 <span>(<%=countOfItemsInTemplateShopList.intValue() %>)</span>
				                                </a>
	                                		</li>
		                             		<%
		                         		}
		                            }
		                           
		                            if(userShoppingListsItemsCount!=null && userShoppingListsItemsCount.size()>0) {
		                            	Integer countOfItemsInUserShopList = new Integer(0);
		                            	
		                            	EditOrderGuideForm editOGForm = sessionData.getEditOrderGuideForm();
		                            	OrderGuideDataVector uOgVector = editOGForm.getUserOrderGuides();
		                            	Iterator itr = uOgVector.iterator();
		                         		OrderGuideData orderGuideData;
		                         		while(itr.hasNext()) {
		                         			orderGuideData = (OrderGuideData)itr.next();
		                         			countOfItemsInUserShopList = userShoppingListsItemsCount.get(orderGuideData.getOrderGuideId());
											if(countOfItemsInUserShopList==null) {
												countOfItemsInUserShopList = new Integer(0);
											}
		                             		%>
		                             		
		                             		 <li>
				                                <a href="javascript:submitShoppingList('orderGuideSelect','<%= (orderGuideData.getOrderGuideId())%>')">
				                                	<%=Utility.encodeForHTML(orderGuideData.getShortDesc()) %>
				                                	 <span>(<%=countOfItemsInUserShopList.intValue() %>)</span>
				                                </a>
	                                		</li>
		                             		<%
		                         		}
		                            }
	                        %>
                                                                   
                                </ul>
							<% } %>
                                           			
                        </li>
                         <li>
                			<html:link action="<%=quickShopLink.toString()%>">
                				<app:storeMessage key="shop.quick.text.quickShop" />
                			</html:link>
                        </li>
                	</ul>
   				</li>
   				<li <%=ordersTabClass%>>
     				<html:link action="<%=ordersLink %>">
   						<span>
   							<app:storeMessage key="shopping.label.orders" />
   						</span>
     				</html:link>
     				<ul>
     					<li>
                			<html:link action="<%=allOrdersLink.toString()%>">
                				<app:storeMessage key="shopping.orders.label.allOrders" />
                			</html:link>
     					</li>
     					<li>
                			<html:link action="<%=orderSchedulesLink.toString()%>">
                				<app:storeMessage key="shopping.orders.label.schedules" />
                			</html:link>
     					</li>
     					<li>
                			<html:link action="<%=futureOrdersLink.toString()%>">
                				<app:storeMessage key="shopping.orders.label.futureOrders" />
                			</html:link>
     					</li>
     				</ul>
   				</li>
<%if (user.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.VIEW_SITE_PAR_VALUES)
        || user.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.VIEW_SHOPPING_CONTROLS)) {
%>
   				<li <%=controlsTabClass%>>
     				<html:link action="<%=controlsLink%>">
   						<span>
   							<app:storeMessage key="userportal.esw.label.controls" />
   						</span>
     				</html:link>
     				<ul>
<%if (user.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.VIEW_SHOPPING_CONTROLS)) { %>
     					<li>
                			<html:link action="<%=productLimitsLink.toString()%>">
                				<app:storeMessage key="userportal.esw.label.productLimits" />
                			</html:link>
     					</li>
<%}%>
<%if (user.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.VIEW_SITE_PAR_VALUES)) { %>

     					<li>
                			<html:link action="<%=parValuesLink.toString()%>">
                				<app:storeMessage key="userportal.esw.label.parValues" />
                			</html:link>
     					</li>
<%} %>
     				</ul>
   				</li>
<%} %>
<%if (user.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ACCESS_CONTENT) && location!=null) {
%>
				<li <%=contentTabClass%>>
				<html:link action="<%=contentLink.toString()%>" target="_blank">
                			<app:storeMessage key="userportal.esw.label.content" />
                		</html:link>
     				</li>
				
	
<%} %>
			</ul>
		</div>
	</div>