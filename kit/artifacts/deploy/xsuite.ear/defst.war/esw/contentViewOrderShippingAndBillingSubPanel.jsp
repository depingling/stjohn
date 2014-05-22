<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.service.api.value.OrderPropertyDataVector" %>
<%@ page import="com.cleanwise.service.api.value.OrderPropertyData" %>
<%@ page import="com.cleanwise.service.api.value.AddressData" %>
<%@ page import="com.cleanwise.service.api.value.OrderAddressData" %>
<%@ page import="com.cleanwise.service.api.value.OrderStatusDescData" %>
<%@ page import="com.cleanwise.service.api.value.OrderData"%>
<%@ page import="com.cleanwise.service.api.value.OrderFreightData"%>
<%@ page import="com.cleanwise.service.api.value.OrderAddOnChargeData"%>
<%@ page import="com.cleanwise.service.api.value.OrderItemActionData"%>
<%@ page import="com.cleanwise.service.api.value.OrderItemDescData"%>
<%@ page import="com.cleanwise.service.api.value.OrderMetaData"%>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="org.apache.struts.util.LabelValueBean" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.TimeZone" %>
<%@ page import="java.util.List" %>

<%@ page import="com.espendwise.view.forms.esw.OrdersForm"%>
<%@ page import="com.cleanwise.view.forms.OrderOpDetailForm"%>
<%@ page import="com.cleanwise.service.api.value.OrderItemDescDataVector"%>

<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri='/WEB-INF/eswI18n.tld' prefix='eswi18n' %>
        
<%@page import="com.cleanwise.view.i18n.ClwI18nUtil"%>

<bean:define id="theForm" name="esw.OrdersForm" type="com.espendwise.view.forms.esw.OrdersForm"/>

<% CleanwiseUser appUser = (CleanwiseUser)
     session.getAttribute(Constants.APP_USER); %>
     
				<!-- Start Box -->
                <div class="boxWrapper smallMargin squareCorners">
                    <div class="top clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
                    <div class="content"> 
                        <div class="left clearfix">
                        
                            <h2 class="width50"><app:storeMessage key="shop.orderStatus.text.shippingInformation" /></h2>
                            <h2 class="width50"><app:storeMessage key="shop.orderStatus.text.billingInformation" /></h2>
                            <hr>

                            <div class="twoColBox">
                                <div class="column width50">
                                    <table>
                                        <colgroup>
                                            <col>
                                            <col width="50%">
                                        </colgroup>
                                        <tbody><tr>
                                        	<%  
                                        	boolean shipToOverride = false;
                                            OrderMetaData omData = theForm.getOrderOpDetailForm().
                         						getOrderStatusDetail().getMetaObject(RefCodeNames.ORDER_PROPERTY_TYPE_CD.SHIP_TO_OVERRIDE);
                                            if (omData != null && Utility.isTrue(omData.getValue())) {
                                                shipToOverride = true;
                                            }
                                        	String shippingAddressTitleKey = shipToOverride ? "shop.orderdetail.label.alternateAddress" : "shop.orderdetail.label.shippingAddress";
                                        	%>
                                            <td>
                                                <app:storeMessage key="<%=shippingAddressTitleKey %>" />
                                            </td>
                                            <td>
                                             <% 
                                             	String name = theForm.getOrderOpDetailForm().
                                 					getOrderStatusDetail().getShipTo().getShortDesc();
	                                            
                                             	String address1 = theForm.getOrderOpDetailForm().
	                                            	getOrderStatusDetail().getShipTo().getAddress1();
	                                        	String address2 = theForm.getOrderOpDetailForm().
	                                        		getOrderStatusDetail().getShipTo().getAddress2();
	                                        	String address3 = theForm.getOrderOpDetailForm().
	                                        			getOrderStatusDetail().getShipTo().getAddress3();
	                                        	String address4 = theForm.getOrderOpDetailForm().
	                                        			getOrderStatusDetail().getShipTo().getAddress4();
	                                        	
	                                        	//Here we retrieve address format specific to a country, in this case it is shipping address
	                                        	//country.
	                                        	String country = theForm.getOrderOpDetailForm().
	                                        			getOrderStatusDetail().getShipTo().getCountryCd();
	                                        	String addressFormat = Utility.getAddressFormatFor(country); 
	                                        	
	                                        	String city = theForm.getOrderOpDetailForm().getOrderStatusDetail().getShipTo().getCity();
	                                        	String state = "";
	                                        	if (appUser.getUserStore().isStateProvinceRequired()) {
	                                        		state = theForm.getOrderOpDetailForm().
	                                        				getOrderStatusDetail().getShipTo().getStateProvinceCd();
	                                        	}
	                                        	String postalCode = theForm.getOrderOpDetailForm().
	                                        			getOrderStatusDetail().getShipTo().getPostalCode();
	                                        	
                                        	
                                        	%>
                                        	<% if (shipToOverride) { %>
	                                			<eswi18n:formatAddress postalCode="<%=postalCode %>" name="<%=address1 %>" 
	                                				address1="<%=address2 %>" address2="<%=address3 %>" address3="" 
	                                				address4="" city="<%=city %>" state="<%=state %>" 
	                                				country="<%=country %>" addressFormat="<%=addressFormat %>"/> 
                                				<br><br>
                                			   	<bean:write name="esw.OrdersForm" property="orderOpDetailForm.orderStatusDetail.orderDetail.orderContactPhoneNum"/>
                                            <% } else { %>
	                                            <eswi18n:formatAddress postalCode="<%=postalCode %>" name="<%=name %>" 
	                                				address1="<%=address1 %>" address2="<%=address2 %>" address3="<%=address3 %>" 
	                                				address4="<%=address4 %>" city="<%=city %>" state="<%=state %>" 
	                                				country="<%=country %>" addressFormat="<%=addressFormat %>"/> 
                                            <% } %>
                                            </td>
                                        </tr>
                                        <!--  
                                        <tr>
                                            <td>
                                                <%//=ClwMessageResourcesImpl.getMessage(request,"shop.orderdetail.label.shippingMessage")%>
                                            </td>
                                            <td>
                                                ?????
                                            </td>
                                        </tr>
                                        -->
										<tr>
                                            <td>
                                                <app:storeMessage key="shop.orderdetail.label.shippingComments" />
                                            </td>
                                            <td>
                                                <bean:write name="esw.OrdersForm"
                                                   property="orderOpDetailForm.orderStatusDetail.orderDetail.comments"/>
                                            </td>
                                        </tr>
                                    </tbody></table>
                                </div>
                                <div class="column width50">
                                    <table>
                                        <colgroup>
                                            <col>
                                            <col width="50%">
                                        </colgroup>
                                        <tbody><tr>
                                            <td>
                                                <app:storeMessage key="shop.orderdetail.label.billingAddress" />
                                            </td>
                                            <td>
                                            
                                            <% if (theForm.getOrderOpDetailForm().getOrderStatusDetail().hasCreditCard()) { 
    	                                         	
                                                 	address1 = theForm.getOrderOpDetailForm().
    														getOrderStatusDetail().getOrderCreditCardData().getAddress1();
                                                 	address2 = theForm.getOrderOpDetailForm().
    														getOrderStatusDetail().getOrderCreditCardData().getAddress2();
                                                 	address3 = theForm.getOrderOpDetailForm().
    														getOrderStatusDetail().getOrderCreditCardData().getAddress3();
                                                 	address4 = theForm.getOrderOpDetailForm().
    														getOrderStatusDetail().getOrderCreditCardData().getAddress4();
                                                 	
    	                                        	
    	                                        	//Here we retrieve address format specific to a country, in this case it is shipping address
    	                                        	//country.
    	                                        	country = theForm.getOrderOpDetailForm().
    														getOrderStatusDetail().getOrderCreditCardData().getCountryCd();
    	                                        	addressFormat = Utility.getAddressFormatFor(country); 
    	                                        	
    	                                        	city = theForm.getOrderOpDetailForm().
    														getOrderStatusDetail().getOrderCreditCardData().getCity();
    	                                        	state = "";
    	                                        	if (appUser.getUserStore().isStateProvinceRequired()) {
    	                                        		state = theForm.getOrderOpDetailForm().
    															getOrderStatusDetail().getOrderCreditCardData().getStateProvinceCd();
    	                                        	}
    	                                        	postalCode = theForm.getOrderOpDetailForm().
    														getOrderStatusDetail().getOrderCreditCardData().getPostalCode();
    	                                        	
                                            %> 
                                                 <eswi18n:formatAddress postalCode="<%=postalCode %>" 
                                					address1="<%=address1 %>" address2="<%=address2 %>" address3="<%=address3 %>" 
                                					address4="<%=address4 %>" city="<%=city %>" state="<%=state %>" 
                                					country="<%=country %>" addressFormat="<%=addressFormat %>"/> 
                                            <% } else { 
                                            		name = theForm.getOrderOpDetailForm().
                                     					getOrderStatusDetail().getBillTo().getShortDesc();
    	                                         	
                                                 	address1 = theForm.getOrderOpDetailForm().
    														getOrderStatusDetail().getBillTo().getAddress1();
                                                 	address2 = theForm.getOrderOpDetailForm().
    														getOrderStatusDetail().getBillTo().getAddress2();
                                                 	address3 = theForm.getOrderOpDetailForm().
    														getOrderStatusDetail().getBillTo().getAddress3();
                                                 	address4 = theForm.getOrderOpDetailForm().
    														getOrderStatusDetail().getBillTo().getAddress4();
                                                 	
    	                                        	
    	                                        	//Here we retrieve address format specific to a country, in this case it is shipping address
    	                                        	//country.
    	                                        	country = theForm.getOrderOpDetailForm().
    														getOrderStatusDetail().getBillTo().getCountryCd();
    	                                        	addressFormat = Utility.getAddressFormatFor(country); 
    	                                        	
    	                                        	city = theForm.getOrderOpDetailForm().
    														getOrderStatusDetail().getBillTo().getCity();
    	                                        	state = "";
    	                                        	if (appUser.getUserStore().isStateProvinceRequired()) {
    	                                        		state = theForm.getOrderOpDetailForm().
    															getOrderStatusDetail().getBillTo().getStateProvinceCd();
    	                                        	}
    	                                        	
    	                                        	postalCode = theForm.getOrderOpDetailForm().
    														getOrderStatusDetail().getBillTo().getPostalCode();
    	                                        	
                                            	
                                            %>
                                           		<eswi18n:formatAddress postalCode="<%=postalCode %>" name="<%=name %>" 
                                					address1="<%=address1 %>" address2="<%=address2 %>" address3="<%=address3 %>" 
                                					address4="<%=address4 %>" city="<%=city %>" state="<%=state %>" 
                                					country="<%=country %>" addressFormat="<%=addressFormat %>"/> 
                                            <% } %>
                                            </td>
                                        </tr>
                                        <!-- Payment Type -->
										<tr>
                                            <td>
                                                <app:storeMessage key="shop.orderdetail.label.paymentType" />
                                            </td>
                                            <% 
                                              String paymentType="";
                                              if (theForm.getOrderOpDetailForm().getOrderStatusDetail().hasCreditCard()) {
                                            	  // payment type = Credit Card payment
                                            	  paymentType = ClwMessageResourcesImpl.getMessage(request,"shop.checkout.text.creditCard");
                                              } else {
                                            	  OrderPropertyDataVector orderPropertyList = theForm.getAllOrderProperties(); 
  											      boolean recordOfCallFl = false;
											      if (orderPropertyList != null && orderPropertyList.size() > 0 ) { 
											          for (int j1 = 0; j1 < orderPropertyList.size(); j1++) { 
											    	      OrderPropertyData orderPropertyData = (OrderPropertyData) orderPropertyList.get(j1);
											    	      if (orderPropertyData.getShortDesc().equals(RefCodeNames.ORDER_PROPERTY_TYPE_CD.OTHER_PAYMENT_INFO)
											    	    	 )
											    	      {
											    	    	  // payment type = Record of Call
											    	    	  recordOfCallFl = true;
											    	    	  paymentType = ClwMessageResourcesImpl.getMessage(request,"shop.checkout.text.recordOfCall"); 
											    	          break;
											    	      }
											          } // for
											      } // if
                                            	  
                                            	  if (!recordOfCallFl) {
                                            		  // payment type = On Account
                                            		  paymentType = ClwMessageResourcesImpl.getMessage(request,"shop.orderdetail.text.onAccount"); 
                                            	  }
                                              } //endif
                                            %>
                                            <td>
                                                <%=paymentType%>
                                            </td>
                                        </tr>
                                    </tbody></table>
                                </div>
                            </div>
                            
                        </div>
                    </div>
                    <div class="bottom clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
                </div>
                <!-- End Box -->				
            