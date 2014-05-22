

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
     //CleanwiseUser appUser = (CleanwiseUser)
         //session.getAttribute(Constants.APP_USER); 

     //boolean showPriceFl = appUser.getShowPrice();
%>

<%
//TODO - implement this page.  The HTML is basically set, but things like converting input
//to use struts tag libs, implementing links, etc all has to be done.  Best approach is to go
//through entire page line by line and implement whatever hasn't been done.


	String viewShoppingCart = "/userportal/esw/shopping.do";
	
	ShoppingCartForm shoppingCartForm = null;
	shoppingCartForm = myForm.getShoppingCartForm();

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
                <div class="boxWrapper squareCorners smallMargin firstBox">
                                           
                 <html:form styleId="shoppingFormId" action="<%=viewShoppingCart %>">
          			<html:hidden property="operation" styleId="operationId" />
                 <div class="content">

						<div class="left clearfix">
<%
				if (ShopTool.hasInventoryCartAccessOpen(request)) {
%>
					<logic:notEmpty name="esw.ShoppingForm" 
							   property="shoppingCartForm.cartItems">
                           <h1 class="main"><app:storeMessage  key="shopping.orders.label.parOrder" /></h1>	
                         		  
                             <a class="blueBtnLargeExt" 
                              	onclick="javascript:setFieldsAndSubmitForm('shoppingFormId','operationId','<%=Constants.PARAMETER_OPERATION_VALUE_SAVE_PAR_ORDER  %>')" >
                             	<span><app:storeMessage  key="shoppingCart.text.update" /></span>
                             </a> 
                             	
							  <div class="statusBar">
	                            <span class="open"><app:storeMessage  key="shoppingCart.text.open" />  
	                            	<span>
	                            		<bean:write name="esw.ShoppingForm" property="orderClosesInDays" filter="true" />
	                            	</span>
	                            </span>
	                            <span><app:storeMessage  key="shoppingCart.text.releaseDate" /> &nbsp;
	                            	<bean:write name="esw.ShoppingForm" property="orderReleaseDate" filter="true" />
	                            </span>
						  </div>
					</logic:notEmpty>	
<%
				}									
%>
                        </div>
                    </div>
                    <div class="bottom clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
                </div>
			    <!-- End Box -->   
<%
				if (ShopTool.hasInventoryCartAccessOpen(request)) {
%>
       <logic:notEmpty name="esw.ShoppingForm" 
							   property="shoppingCartForm.cartItems">       
                <div class="boxWrapper squareCorners smallMargin">
                    <div class="top clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
                    <div class="content">
                        <div class="left clearfix">
												 
                            <h2 class="left"><app:storeMessage  key="shoppingCart.text.parItems" /></h2>
                            <hr>

<!-- Begin: Inventory Shopping Cart Items -->
                            <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,Constants.PORTAL_ESW,"shoppingCartItems.jsp")%>' >
			              			<jsp:param name="viewType" value="PAR"/>
					            </jsp:include>
<!-- End: Inventory Shopping Cart Items-->
                            <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
                               <% Double parItemsTotalAmt = new Double(shoppingCartForm.getPARItemsTotal(request)); %>
                         	   <p class="total"><app:storeMessage  key="shoppingCart.text.parSubTotal" />&nbsp; <%=ClwI18nUtil.getPriceShopping(request,parItemsTotalAmt,"&nbsp;")%></p> 
						    </logic:equal>
                        </div>
                    </div>
					 
                    <div class="bottom clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
              
				 </div>
                <!-- End Box -->

                <!-- Start Box -->  
                <div class="boxWrapper squareTop smallMargin">
                    <div class="top clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
                    <div class="content">
                        <div class="left clearfix">
                            <h2 class="left"><app:storeMessage  key="shoppingCart.text.nonParItems" /></h2>

                            <hr>
							
<!--Begin: Regular Shopping Cart Items -->
			              		<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,Constants.PORTAL_ESW,"shoppingCartItems.jsp")%>' >
			              			<jsp:param name="viewType" value="NON-PAR"/>
					            </jsp:include>
<!-- End: Regular Shopping Cart Items  --> 
							<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">							
							   <% Double nonPARItemsTotalAmt = new Double(shoppingCartForm.getNonPARItemsTotal(request)); %>							
                         	   <p class="total"><app:storeMessage  key="shoppingCart.text.nonParSubTotal" />&nbsp; <%=ClwI18nUtil.getPriceShopping(request,nonPARItemsTotalAmt,"&nbsp;")%></p>
                            </logic:equal>
                        </div>						
                    </div>
                    <div class="bottom clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
                </div>
				 <div class="boxWrapper squareTop">
                    <div class="top clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
                    <div class="content">
                        <div class="left clearfix">
                         	<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">                        
                         	   <%	 Double itemsTotalAmt = new Double(shoppingCartForm.getItemsAmt(request)); %>                         	
                         	   <p class="largeTotal"><app:storeMessage  key="shoppingCart.text.orderSubTotal" />&nbsp; <%=ClwI18nUtil.getPriceShopping(request,itemsTotalAmt,"&nbsp;")%></p>
						 	</logic:equal> 
						 	 <a class="blueBtnLargeExt" 
                              	onclick="javascript:setFieldsAndSubmitForm('shoppingFormId','operationId','<%=Constants.PARAMETER_OPERATION_VALUE_SAVE_PAR_ORDER  %>')" >
                             	<span><app:storeMessage  key="shoppingCart.text.update" /></span>
                             </a> 						                                   
                            
						</div>						
                    </div>
                    <div class="bottom clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
                </div>						
		
                <!-- End Box -->

                <!-- Start Box -->
                <% 
                CleanwiseUser user = ShopTool.getCurrentUser(request);
           	 	AccountData account = user.getUserAccount();
                boolean enableInvOrderProcessing = Utility.isTrue(account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.SHOW_SCHED_DELIVERY),false);
                boolean allowOrdrInvItems = Utility.isTrue(account.getPropertyValue(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_ORDER_INV_ITEMS),false);
                boolean enableInvShopping = ShopTool.isInventoryShoppingOn(request);
	            if(enableInvOrderProcessing && allowOrdrInvItems && enableInvShopping){ %>
	             
<!--Begin: Par Activity -->
	            <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,Constants.PORTAL_ESW,"parActivity.jsp")%>'  />
<!-- End: Par Activity  -->
					
	          <% }
	          %>
			  </logic:notEmpty>
<%
		}									
%>
            </html:form>     
                <!-- End Box -->

            </div>
        </div>