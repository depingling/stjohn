<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>
<%@ page import="com.cleanwise.service.api.value.ShoppingCartData" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<% CleanwiseUser appUser = (CleanwiseUser)
     session.getAttribute(Constants.APP_USER); %>

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
                       
<!-- Begin: Order Status Information  -->
                           <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,Constants.PORTAL_ESW,"contentViewOrderConfStatusInfoSubPanel.jsp")%>' />				             
<!-- End: Order Status Information  --> 
                                                                          	
<!-- Begin: Order Specific Information  -->
                <!-- Start Box -->
                <div class="boxWrapper squareCorners smallMargin firstBox">    
				<jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,Constants.PORTAL_ESW,"orderInfoPanel.jsp")%>' >
					<jsp:param value="false" name="checkOutPanel"/>
				</jsp:include>
<!-- End: Order Specific Information  -->
			   </div>
                <!-- End Box -->

<!-- Begin: Order Items and Distributor Information  -->                                                      
                <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,Constants.PORTAL_ESW,"contentViewOrderConfItemsSubPanel.jsp")%>' />				             
<!-- End: Order Items and Distributor Information  -->    

<!-- Begin: Order Shipping and Billing Information  -->                  			                                              
                <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,Constants.PORTAL_ESW,"contentViewOrderConfShippingAndBillingSubPanel.jsp")%>' />				                             
<!-- End: Order Shipping and Billing Information  -->                   
                								               
			</div>
			
	    </div>

    

<div id="ui-datepicker-div" class="ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all"></div></body></html>