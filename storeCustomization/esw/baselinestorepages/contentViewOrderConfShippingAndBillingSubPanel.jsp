<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="java.util.GregorianCalendar" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>
<%@ page import="com.cleanwise.service.api.value.ShoppingCartData" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility"%>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Iterator" %>

<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="org.apache.struts.util.LabelValueBean" %>

<%@ page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
<%@ page import="com.espendwise.view.forms.esw.CheckOutForm"%>
<%@ page import="com.cleanwise.view.forms.CheckoutForm"%>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>
<%@ taglib uri='/WEB-INF/eswI18n.tld' prefix='eswi18n' %>

<app:setLocaleAndImages/>
 
<% CleanwiseUser appUser = (CleanwiseUser)
     session.getAttribute(Constants.APP_USER); %>

<bean:define id="theForm" name="esw.CheckOutEswForm" type="com.espendwise.view.forms.esw.CheckOutForm"/>
                                 		
<bean:define id="orderRes" name="esw.CheckOutEswForm" property="checkOutForm.orderResult" type="ProcessOrderResultData"/>
<%
    CheckoutForm checkOutForm = theForm.getCheckOutForm();
	//Billing Address Info
	//AddressData billingAddress = checkOutForm.getAccount().getBillingAddress();
	AddressData billingAddress = checkOutForm.getConfirmBillToAddress();
	//Shipping Address Info
	AddressData shippingAddress = checkOutForm.getSite().getSiteAddress();
	CountryPropertyDataVector countryProperties = appUser.getUserStore().getCountryProperties();
%>	
<%
 boolean usedCreditCard;
 String ccNumber = theForm.getCheckOutForm().getCcNumber();
 if(ccNumber!=null && ccNumber.trim().length()>0) {
   usedCreditCard = true;
 } else {
   usedCreditCard = false;
 }
%>
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
                                        <tbody>
                                        <tr>
                                        	<%  boolean alternateAddr = checkOutForm.getAllowAlternateShipTo() && Utility.isSet(checkOutForm.getAlternateAddress1()); 
                                        		String shippingAddressTitleKey = alternateAddr ? "shop.orderdetail.label.alternateAddress" : "shop.orderdetail.label.shippingAddress";
                                        	%>
                                            <td>
                                                <app:storeMessage key="<%=shippingAddressTitleKey %>" />
                                            </td>
                                            <td>
                                            <%                                                  
                                            
                                            //Shipping Address
	                                            String address1 = alternateAddr ? Utility.encodeForHTML(checkOutForm.getAlternateAddress2()) : Utility.encodeForHTML(shippingAddress.getAddress1());
	                                        	String address2 = alternateAddr ? Utility.encodeForHTML(checkOutForm.getAlternateAddress3()) : Utility.encodeForHTML(shippingAddress.getAddress2());
	                                        	String address3 = alternateAddr ? "" : Utility.encodeForHTML(shippingAddress.getAddress3());
	                                        	String address4 = alternateAddr ? "" : Utility.encodeForHTML(shippingAddress.getAddress4());
	                                        	String city = alternateAddr ? Utility.encodeForHTML(checkOutForm.getAlternateCity()) : Utility.encodeForHTML(shippingAddress.getCity());
	                                        	String state = "";
						                       	if (appUser.getUserStore().getCountryProperties() != null) {
						                    	    if (Utility.isTrue(Utility.getCountryPropertyValue(
						                    	    		appUser.getUserStore().getCountryProperties(), 
						                    	    		RefCodeNames.COUNTRY_PROPERTY.USES_STATE))) {
						                    	    	state = alternateAddr ? Utility.encodeForHTML(checkOutForm.getAlternateStateProvince()) : Utility.encodeForHTML(shippingAddress.getStateProvinceCd());
						                    	    }
						                    	}
	                                        	String postalCode = alternateAddr ? Utility.encodeForHTML(checkOutForm.getAlternatePostalCode()) : Utility.encodeForHTML(shippingAddress.getPostalCode());
	                                        	String country = alternateAddr ? Utility.encodeForHTML(checkOutForm.getAlternateCountry()) : Utility.encodeForHTML(shippingAddress.getCountryCd());
	                                        	String addressFormat = Utility.getAddressFormatFor(country);		
                                            
	                                        	String siteName = alternateAddr ? Utility.encodeForHTML(checkOutForm.getAlternateAddress1()) : Utility.encodeForHTML(checkOutForm.getSite().getBusEntity().getShortDesc());

                                            %>
                                              <%--   <%=sAddressString.toString()%> --%>
                                               <eswi18n:formatAddress postalCode="<%=postalCode %>" name="<%=siteName %>" 
                                					address1="<%=address1 %>" address2="<%=address2 %>" address3="<%=address3 %>" 
                                					address4="<%=address4 %>" city="<%=city %>" state="<%=state %>" 
                                					country="<%=country %>" addressFormat="<%=addressFormat %>"/> 
                                			   <% if (alternateAddr) { %>
                                			   		<br><br>
                                			   		<bean:write name="esw.CheckOutEswForm" property="checkOutForm.alternatePhoneNum"/>
                                			   	<% } %>   
                                            </td>
                                        </tr>
                                        <bean:define id="shipmsg" type="java.lang.String"
                                          name="esw.CheckOutEswForm" property="checkOutForm.shippingMessage" />
                                        <% if (shipmsg.length() > 0) { %>
                                          <tr>
                                            <td>
                                                <app:storeMessage key="shop.orderdetail.label.shippingMessage" />
                                            </td>
                                            <td>
                                                <%= shipmsg %>
                                            </td>
                                          </tr>
                                        <% } %>
                                        <% if (!appUser.getUserAccount().hideShippingComments()) { %>
										<tr>
                                            <td>
                                                <app:storeMessage key="shop.orderdetail.label.shippingComments" />
                                            </td>
                                            <td>
                                               <bean:write name="esw.CheckOutEswForm" property="checkOutForm.comments"/>
                                            </td>
                                        </tr>
                                        <% } %>
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
                                            <%if(!usedCreditCard){%><%-- find me "on account" bill to --%>
                                            <%  //Billing Address
	                                            address1 = Utility.encodeForHTML(billingAddress.getAddress1());
	                                        	address2 = Utility.encodeForHTML(billingAddress.getAddress2());
	                                        	address3 = Utility.encodeForHTML(billingAddress.getAddress3());
	                                        	address4 = Utility.encodeForHTML(billingAddress.getAddress4());
	                                        	city = Utility.encodeForHTML(billingAddress.getCity());
	                                        	state = "";
						                       	if (appUser.getUserStore().getCountryProperties() != null) {
						                    	    if (Utility.isTrue(Utility.getCountryPropertyValue(
						                    	    		appUser.getUserStore().getCountryProperties(), 
						                    	    		RefCodeNames.COUNTRY_PROPERTY.USES_STATE))) {
						                    	    	state = Utility.encodeForHTML(billingAddress.getStateProvinceCd());
						                    	    }
						                    	}
	                                        	postalCode = Utility.encodeForHTML(billingAddress.getPostalCode());
	                                        	country = Utility.encodeForHTML(billingAddress.getCountryCd());
	                                        	addressFormat = Utility.getAddressFormatFor(country);
                                            	
                                            	/* List<String> bAddress = ClwI18nUtil.formatAddressWithCountryCd(billingAddress,countryProperties);
	                                            StringBuilder bAddressString = new StringBuilder(300); */
	                                            String accountName = "";
	                                            try {
	                                            	accountName = Utility.encodeForHTML(checkOutForm.getAccount().getBusEntity().getShortDesc());
	                                            }finally{}
	                                           /*  bAddressString.append(Utility.encodeForHTML(accountName));
	                                            for(String addressLine:bAddress) {
	                                            	bAddressString.append("<br>");
	                                            	bAddressString.append(Utility.encodeForHTML(addressLine));
	                                            } */
                                            %>
                                               <%--  <%=bAddressString.toString()%>        --%> 
                                               <eswi18n:formatAddress postalCode="<%=postalCode %>" name="<%=accountName %>" 
                                					address1="<%=address1 %>" address2="<%=address2 %>" address3="<%=address3 %>" 
                                					address4="<%=address4 %>" city="<%=city %>" state="<%=state %>" 
                                					country="<%=country %>" addressFormat="<%=addressFormat %>"/>                                                                                         
                                            <% } else { %> <%-- END "on account" bill to --%>
                                                   <!-- Credit Card was used to make a payment -->
                                                   <% 
                                                   		address1 = theForm.getCheckOutForm().getCcStreet1();
                                                   		address2 = theForm.getCheckOutForm().getCcStreet2();
                                                   		city = theForm.getCheckOutForm().getCcCity();
                                                   		state = "";
                                                   
                                                   	   if (appUser.getUserStore().isStateProvinceRequired()) { 
                                                	  	 state = theForm.getCheckOutForm().getCcState();
                                                    	} 
                                                       postalCode = theForm.getCheckOutForm().getCcZipCode();
                                                    %>
                                                   <eswi18n:formatAddress address1="<%=address1 %>" address2="<%=address2 %>"
                                                   		city="<%=city %>" state="<%=state %>" postalCode="<%=postalCode %>" addressFormat="<%=addressFormat %>"/>                                                  
                                            <% } %> <!-- End Credit Card -->
                                            </td>
                                        </tr>
                                        <!-- Payment Type -->
										<tr>
                                            <td>
                                                <app:storeMessage key="shop.orderdetail.label.paymentType" />
                                            </td>
                                            <% 
                                              String paymentType="";                                             
                                              if (usedCreditCard) {
                                            	  // payment type = Credit Card 
                                            	  paymentType = ClwMessageResourcesImpl.getMessage(request,"shop.checkout.text.creditCard");
                                              } else {
                                            	  if (theForm.getCheckOutForm().getOtherPaymentInfo() != null && theForm.getCheckOutForm().getOtherPaymentInfo().trim().length() > 0) {
                                            		  // payment type = Record of Call
                                            		  paymentType = ClwMessageResourcesImpl.getMessage(request,"shop.checkout.text.recordOfCall"); 
                                            	  } else {
                                            		  // payment type = On Account
                                            		  paymentType = ClwMessageResourcesImpl.getMessage(request,"shop.orderdetail.text.onAccount"); 
                                            	  }
                                              }
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