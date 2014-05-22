

<%@page import="com.cleanwise.view.forms.QuickOrderForm"%>
<%@page import="com.cleanwise.service.api.util.RefCodeNames"%>
<%@page import="com.cleanwise.service.api.value.AccountData"%>
<%@page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@page import="com.cleanwise.view.utils.Constants"%>
<%@page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@page import="com.cleanwise.view.forms.ShoppingCartForm"%>
<%@page import="com.cleanwise.service.api.util.Utility"%>
<%@page import="com.cleanwise.service.api.util.SessionDataUtil"%>
<%@page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@page import="com.cleanwise.view.utils.ShopTool"%>
<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<bean:define id="myForm" name="esw.ShoppingForm"  type="com.espendwise.view.forms.esw.ShoppingForm"/> 

<%

	String viewShoppingCart = "/userportal/esw/shopping.do";
	String checkOutLink = "userportal/esw/checkout.do";
	
	ShoppingCartForm shoppingCartForm = null;
	shoppingCartForm = myForm.getShoppingCartForm();
	
	String toolTip = ClwI18nUtil.getMessage(request,"userportal.esw.shoppingCart.addShoppingList.popUp.text");
	CleanwiseUser user = ShopTool.getCurrentUser(request);
	QuickOrderForm quickShopForm = myForm.getQuickOrderForm();
    CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
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
     <div id="contentWrapper" class="singleColumn clearfix">
         <div id="content">
<% 
		 if (ShopTool.hasDiscretionaryCartAccessOpen(request)) {
%>
				 <div>
					 <h1 class="main"><app:storeMessage  key="shopping.view.cart.heading" /> </h1>
          			<logic:notEmpty name="esw.ShoppingForm" 
								   property="shoppingCartForm.cartItems">
                     	<html:link href="javascript:submitCheckOut('shoppingFormId','operationId','checkOut')" styleClass="blueBtnLargeExt">
		                	<span><app:storeMessage  key="global.action.label.checkout" /></span>
						</html:link>
					</logic:notEmpty>
				 </div>
<%
		 }
%>         
             <!-- Start Box -->
<!-- Begin: Location Budget -->              
			 <%
				 String locationBudget = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "dashboardLocationBudget.jsp");
			 %>
               	 <jsp:include page="<%=locationBudget %>" />
 <!-- End: Location Budget -->              	 
		       <!-- End Box -->             	 
  <% 

		 if (ShopTool.hasDiscretionaryCartAccessOpen(request)) {
%>      

        <html:form styleId="shoppingFormId" action="<%=viewShoppingCart.toString() %>">
             <!-- Start Box -->
             <html:hidden property="operation" styleId="operationId" />
             <html:hidden property="addCatalogItems" value="false"/>
             <html:hidden property="userShopForm.orderGuideName" styleId="ogId"/>
             <input type="hidden" id="selectedShoppingListId" name="selectedShoppingList" value="<%= myForm.getUserShopForm().getSelectedShoppingListId() %>"/>
              <logic:notEmpty name="esw.ShoppingForm" 
							   property="shoppingCartForm.cartItems">
			  
             <div class="boxWrapper smallMargin squareCorners">
                 <div class="top clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
                 <div class="content">
                     <div class="left clearfix">
                         <!-- <h2>Category</h2> -->
	                  <%
	                    //STJ-6022 (handle browse only user)
	                    if (!appUser.isBrowseOnly()) {
	                  %>

                         <p> </p>
						<p class="clearfix">

                             <a onclick="javascript:setFieldsAndSubmitForm('shoppingFormId','operationId','<%=Constants.PARAMETER_OPERATION_VALUE_SAVE_CART  %>')" class="blueBtnMed">
                             	<span><app:storeMessage  key="shoppingCart.text.update" /></span></a> 
							 <a href="javascript:setFieldsAndSubmitForm('shoppingFormId','operationId','<%=Constants.PARAMETER_OPERATION_VALUE_REMOVE_ALL_FROM_CART  %>')" class="blueBtnMed ">
							 	<span><app:storeMessage  key="global.action.label.removeAll" /></span></a>
                             <a class="blueBtnMed" title="<%=toolTip %>" href="javascript:setFieldsAndSubmitForm('shoppingFormId','operationId','<%=Constants.PARAMETER_OPERATION_VALUE_ADD_ALL_TO_SHOPPING_LIST  %>')" class="blueBtnMed">
            	                <span><app:storeMessage  key="global.action.label.addAllToShoppingList" /></span></a>
             	                
             	                	<html:select property="userShopForm.selectedShoppingListId" styleClass="span" styleId="shoppingListsId">
	                            		<html:optionsCollection name="esw.ShoppingForm"
	                            			property="shoppingListOptions" label="label" value="value"/>
	                        		</html:select>
                        </p>       
						<p class="right clearfix">
									<label>
									 <% // Exclude order from budget
									if (user.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.EXCLUDE_ORDER_FROM_BUDGET)) {%>
                                        <html:checkbox name="esw.ShoppingForm" property="excludeOrderFromBudget" styleClass="chkBox right" />
                                        <app:storeMessage  key="userportal.esw.shoppingCart.excludeOrderFromBudget" />
									<% } %>
									</label>
						</p>
						
						<hr />
						<% } // if browse only %>
							
							 <div class="shoppingList hide">
                                        <h3><app:storeMessage  key="userportal.esw.shoppingCart.text.createShoppingList" /></h3>
                                        <div class="listName">
                                            <label><app:storeMessage  key="userportal.esw.shoppingCart.text.listName" /><br />
                                            </label>
                                        </div>								
                            </div>
						
                         
<!--Begin: Shopping Cart Items -->
              		<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "shoppingCartItems.jsp")%>' >
              			<jsp:param name="viewShoppingCartAction" value="<%=viewShoppingCart.toString() %>"/>
              			<jsp:param value="viewShoppingCart" name="viewType"/>
		            </jsp:include>
<!-- End: Shopping Cart Items  --> 
				
                       
						<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">  
				     	   <%
				     	   Double itemsAmt = new Double(shoppingCartForm.getItemsAmt(request)); %>
                           <p class="largeTotal"><app:storeMessage  key="shoppingCart.text.orderSubTotal" />&nbsp; <%=ClwI18nUtil.getPriceShopping(request,itemsAmt,"&nbsp;")%></p>
                        </logic:equal>
	                  <%
	                    //STJ-6022 (handle browse only user)
	                    if (!appUser.isBrowseOnly()) {
	                  %>

                         <p class="clearfix">

                             <a onclick="javascript:setFieldsAndSubmitForm('shoppingFormId','operationId','<%=Constants.PARAMETER_OPERATION_VALUE_SAVE_CART  %>')" class="blueBtnMed">
                             	<span><app:storeMessage  key="shoppingCart.text.update" /></span></a> 
							 <a href="javascript:setFieldsAndSubmitForm('shoppingFormId','operationId','<%=Constants.PARAMETER_OPERATION_VALUE_REMOVE_ALL_FROM_CART  %>')" class="blueBtnMed ">
							 	<span><app:storeMessage  key="global.action.label.removeAll" /></span></a>
                             <a class="blueBtnMed" title="<%=toolTip %>" href="javascript:setFieldsAndSubmitForm('shoppingFormId','operationId','<%=Constants.PARAMETER_OPERATION_VALUE_ADD_ALL_TO_SHOPPING_LIST  %>')" class="blueBtnMed">
             	                <span><app:storeMessage  key="global.action.label.addAllToShoppingList" /></span></a>
             	                
             	                	<html:select property="userShopForm.selectedShoppingListId" styleClass="span" styleId="shoppingListsId2" >
	                            		<html:optionsCollection name="esw.ShoppingForm"
	                            			property="shoppingListOptions" label="label" value="value"/>
	                        		</html:select>
                        </p>
                        <% } // if browse only %>
                     </div>
                 </div>
                 <div class="bottom clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
             </div>
			 
			  
             </logic:notEmpty>
               </html:form>
             <!-- End Box -->
               <!--Begin: Quick Shop Form -->
             <% if(quickShopForm.getDuplicationFlag() == false){ %>
              	<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "quickShop.jsp")%>' />
             <% }else { %>
             	<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "quickShopSkuCollisions.jsp")%>' />
			 <!-- End: Quick Shop Form -->
            <% }  %>
            <logic:notEmpty name="esw.ShoppingForm" 
							   property="shoppingCartForm.cartItems">
              <html:link href="javascript:submitCheckOut('shoppingFormId','operationId','checkOut')" styleClass="blueBtnLargeExt bottomMargin">
		                  <span><app:storeMessage  key="global.action.label.checkout" /></span>
			  </html:link>
			</logic:notEmpty>

            
<%
		}
%>
             
         </div>
     </div>
 
                