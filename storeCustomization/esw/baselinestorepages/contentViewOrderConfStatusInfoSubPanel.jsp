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
<%@ page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
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

<bean:define id="theForm" name="esw.CheckOutEswForm" type="com.espendwise.view.forms.esw.CheckOutForm"/>

<bean:define id="orderRes" name="esw.CheckOutEswForm" property="checkOutForm.orderResult" type="ProcessOrderResultData"/>
            
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
            String orderConfNumber = "";
            String sts = "";
            java.util.Date orderDate = null;
            while(orderRes != null){
              pageContext.setAttribute("orderRes",orderRes);
              orderDate = orderRes.getOrderDate();
             // sts = orderRes.getOrderStatusCd();
              //STJ-4876
              sts = com.cleanwise.view.utils.ShopTool.i18nStatus(orderRes.getOrderStatusCd(), request);
              orderConfNumber = orderRes.getOrderNum();
            %>                
            <%orderRes = orderRes.getNext();%>               
            <%} //end while(orderRes != null) %>

<!-- HTML for the NEW UI + Business Logic: Begin  -->

                <div>
                    <h1 class="main"><app:storeMessage key="shop.orderStatus.text.orderConfirmation" /></h1>				
                </div>
                <!-- Start Box -->
                <div>
                    <div class="boxWrapper squareCorners smallMargin firstBox">
                        <div class="top clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
                        
						<div class="content">
							<div class="left clearfix">
								<h1><app:storeMessage key="shop.catalog.text.orderNumber" />: <%=orderConfNumber%> (<%=sts%>)</h1>                            	
								<a href="#" class="btn rightMargin topMargin" onclick="window.print();return false;"><span><app:storeMessage key="global.action.label.printPage" /></span></a>
								<h3><app:storeMessage key="shop.orderdetail.label.created" />:
								<% if (orderDate != null){ %>
								      <%=ClwI18nUtil.formatDateInp(request,orderDate)%></h3>							
							    <% } %>
							</div>						       
						</div>	
					
                        <div class="bottom clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
                    </div>
                </div>
                <!-- End Box -->  
                
<!-- HTML for the NEW UI + Business Logic: End -->                          