
<%@page import="com.cleanwise.service.api.util.RefCodeNames"%>
<%@page import="com.cleanwise.service.api.session.Account"%>
<%@page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
<%@page import="com.cleanwise.view.utils.Constants"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@page import="com.cleanwise.service.api.value.CountryPropertyDataVector"%>
<%@page import="com.cleanwise.service.api.util.Utility"%>
<%@page import="com.cleanwise.service.api.value.AddressData"%>
<%@page import="com.cleanwise.view.utils.ShopTool"%>
<%@page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@page import="com.cleanwise.service.api.value.OrderData"%>
<%@page import="com.cleanwise.service.api.value.ShoppingCartData"%>
<%@page import="com.cleanwise.view.forms.CheckoutForm"%>
<%@ page import="org.apache.struts.util.LabelValueBean" %>

<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri='/WEB-INF/eswI18n.tld' prefix='eswi18n' %>

<bean:define id="newCheckOutForm" name="esw.CheckOutEswForm"  type="com.espendwise.view.forms.esw.CheckOutForm"/>

<%
	String sourcePanel = request.getParameter("checkOutPanel");
	boolean isCheckOutPanel = false;
	if(Utility.isSet(sourcePanel)) {
		try{
			isCheckOutPanel = Boolean.valueOf(sourcePanel).booleanValue();
		}finally{}
	}
	
	CheckoutForm checkOutForm = newCheckOutForm.getCheckOutForm();
	ShoppingCartData shoppingCartData = newCheckOutForm.getShoppingCartData();
	OrderData prevOrderData = shoppingCartData.getPrevOrderData();
	CleanwiseUser user = ShopTool.getCurrentUser(request);
	boolean useState = (user.getUserStore().getCountryProperties() != null) && Utility.isTrue(Utility.getCountryPropertyValue(user.getUserStore().getCountryProperties(), 
    		RefCodeNames.COUNTRY_PROPERTY.USES_STATE));
	
	//Billing Address Info
	AddressData billingAddress = checkOutForm.getAccount().getBillingAddress();
	//Shipping Address Info
	AddressData shippingAddress = checkOutForm.getSite().getSiteAddress();
	CountryPropertyDataVector countryProperties = user.getUserStore().getCountryProperties();
	
	//Shipping Message
	String shippingMessage = checkOutForm.getShippingMessage();
	
	//Begin: Shipping Comments
	String shippingComments = checkOutForm.getComments();
	
    if ( !Utility.isSet(shippingComments) ) {
    	if(Utility.isSet(shoppingCartData.getSavedComments())){
    		shippingComments = shoppingCartData.getSavedComments();
    	} 
    	if(!Utility.isSet(shippingComments) && prevOrderData!=null ) {
    		StringBuilder comments = new StringBuilder(256);
    		
    		if(Utility.isSet(prevOrderData.getRequestPoNum())){
    			comments.append(prevOrderData.getRequestPoNum());
    		}    		
    		if(Utility.isSet(prevOrderData.getRefOrderNum())) {
    			if(Utility.isSet(comments.toString())) {
    				comments.append("/");
    			}
    			comments.append(prevOrderData.getRefOrderNum());
    		}
    		if(Utility.isSet(prevOrderData.getComments())) {
    			if(Utility.isSet(comments.toString())) {
    				comments.append("/");
    			}
    			comments.append(prevOrderData.getComments());
    		}
    		shippingComments = comments.toString();
    	}
    }
	//End: Shipping Comments
	
	//Payment Type drop down set selected value.
	String selectedPayMentOption = "";
    if(user.canMakePurchases()) {
    	selectedPayMentOption = checkOutForm.getReqPmt();
    	if(!Utility.isSet(selectedPayMentOption) || selectedPayMentOption.equals("0") ) {
    		if(user.getCreditCardFlag()){
    			selectedPayMentOption = Constants.CHECK_OUT_PAYMENT_CREDIT_CARD;
    		} else {
    			selectedPayMentOption = Constants.CHECK_OUT_PAYMENT_TYPE_PO;
    		}
    		checkOutForm.setReqPmt(selectedPayMentOption);
    	}
    }
    
	
%>
<div class="boxWrapper smallMargin squareCorners">
                    <div class="top clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
                    <div class="content">
                        <div class="left clearfix">

                            <h2 class="width50"><app:storeMessage key="userportal.esw.checkout.text.shoppingInformation" /></h2>
                            <h2 class="width50"><app:storeMessage key="userportal.esw.checkout.text.billingInformation" /></h2>
                            <hr>
                            
                            <div class="twoColBox">
                                <div class="column width50">
                                    <table>
                                        <colgroup>
                                            <col>
                                            <col width="50%">
                                        </colgroup>
                                         <tbody><tr>
                                            <td>
                                                <app:storeMessage key="userportal.esw.checkout.text.shippingAddress" />
                                            </td>
                                            <td>
                                            <%  //Shipping Address
                                            	String address1 = Utility.encodeForHTML(shippingAddress.getAddress1());
                                            	String address2 = Utility.encodeForHTML(shippingAddress.getAddress2());
                                            	String address3 = Utility.encodeForHTML(shippingAddress.getAddress3());
                                            	String address4 = Utility.encodeForHTML(shippingAddress.getAddress4());
                                            	String city = Utility.encodeForHTML(shippingAddress.getCity());
                                            	String state = "";
						                       	if (useState) {
						                    	    state = Utility.encodeForHTML(shippingAddress.getStateProvinceCd());
						                    	}
                                            	String postalCode = Utility.encodeForHTML(shippingAddress.getPostalCode());
                                            	String country = Utility.encodeForHTML(shippingAddress.getCountryCd());
                                            	String addressFormat = Utility.getAddressFormatFor(shippingAddress.getCountryCd());
                                            	
	                                            String siteName = ""; 
	                                             
	                                             
	                                            try {
	                                            	siteName = Utility.encodeForHTML(checkOutForm.getSite().getBusEntity().getShortDesc());
	                                            }finally{}
                                            %>
                                                <%-- <%=sAddressString.toString()%> --%>
                                                <eswi18n:formatAddress postalCode="<%=postalCode %>" name="<%=siteName %>" 
                                					address1="<%=address1 %>" address2="<%=address2 %>" address3="<%=address3 %>" 
                                					address4="<%=address4 %>" city="<%=city %>" state="<%=state %>" 
                                					country="<%=country %>" addressFormat="<%=addressFormat %>"/> 
                                            </td>
                                        </tr>	
                                        <%if(Utility.isSet(shippingMessage)) {%>									                                     
                                        <tr>
                                            <td>
                                                <app:storeMessage key="userportal.esw.checkout.text.shippingMessage" />
                                            </td>
                                            <td>
                                            	<%if(isCheckOutPanel) { %>
                                            		<html:hidden name="esw.CheckOutEswForm" property="checkOutForm.shippingMessage" value="<%=shippingMessage%>" />
                                            	<%} %>
                                                <%=Utility.encodeForHTML(shippingMessage) %>
                                            </td>
                                        </tr>
                                        <%} %>
										<tr>
                                            <td>
                                            	<app:storeMessage key="userportal.esw.checkout.text.shippingComments" />
                                            </td>
                                            <td>
                                            	<%if(isCheckOutPanel) { %>
	                                                <html:textarea name="esw.CheckOutEswForm" property="checkOutForm.comments" rows="5" cols="35" 
												      	value="<%=shippingComments%>"/>
												<%} else { %>
													<%=Utility.encodeForHTML(shippingComments)%>
												<%} %>
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
                                                 <app:storeMessage key="userportal.esw.checkout.text.billingAddress" />
                                            </td>
                                            <td>
                                            <%  //Billing Address
	                                            address1 = Utility.encodeForHTML(billingAddress.getAddress1());
	                                        	address2 = Utility.encodeForHTML(billingAddress.getAddress2());
	                                        	address3 = Utility.encodeForHTML(billingAddress.getAddress3());
	                                        	address4 = Utility.encodeForHTML(billingAddress.getAddress4());
	                                        	city = Utility.encodeForHTML(billingAddress.getCity());
	                                        	state = "";
	                                        	if (useState) {
						                    	    state = Utility.encodeForHTML(shippingAddress.getStateProvinceCd());
						                    	}
	                                        	postalCode = Utility.encodeForHTML(billingAddress.getPostalCode());
	                                        	country = Utility.encodeForHTML(billingAddress.getCountryCd());
	                                        	addressFormat = Utility.getAddressFormatFor(billingAddress.getCountryCd());
	                                        	
	                                            String accountName = "";
	                                            try {
	                                            	accountName = Utility.encodeForHTML(checkOutForm.getAccount().getBusEntity()
	                                            			.getShortDesc());
	                                            }finally{}
                                            %>
                                               <%--  <%=bAddressString.toString()%> --%>
                                                <eswi18n:formatAddress postalCode="<%=postalCode %>" name="<%=accountName %>" 
                                					address1="<%=address1 %>" address2="<%=address2 %>" address3="<%=address3 %>" 
                                					address4="<%=address4 %>" city="<%=city %>" state="<%=state %>" 
                                					country="<%=country %>" addressFormat="<%=addressFormat %>"/> 
                                                
                                            </td>
                                        </tr>
                                    <%if(isCheckOutPanel) { %>
										<tr>
                                            <td>
                                                 <app:storeMessage key="userportal.esw.checkout.text.paymentType" />
                                            </td>
                                            <td>
                                                <% List<LabelValueBean> pChoices = newCheckOutForm.getPaymentTypeChoices(); %>
                                                <% if (pChoices.size() > 1)  { %>
                                                <html:select property="checkOutForm.reqPmt">
					                            	<html:optionsCollection name="esw.CheckOutEswForm"
					                            		property="paymentTypeChoices" label="label" value="value"/>
					                        	</html:select>
					                        	<% }
                                                   else if (pChoices.size() == 1 && Utility.isSet(checkOutForm.getReqPmt()) &&
                                                			checkOutForm.getReqPmt().equalsIgnoreCase(pChoices.get(0).getValue())) {
                                                		String paymentChoiceText = pChoices.get(0).getLabel();
                                                %>
                                                	<%=paymentChoiceText%>
					                        		<html:hidden property="checkOutForm.reqPmt"/>
                                                <%
                                                   }
                                                   else { %>
					                        	<html:hidden property="checkOutForm.reqPmt"/>
					                        	<% } %>
                                            </td>
                                        </tr>
                                     <%} else { %>
                                        <tr>
											<td>  
											 <app:storeMessage key="userportal.esw.checkout.text.paymentType" />
											</td>
											<td> <bean:write name="esw.CheckOutEswForm" property="checkOutForm.reqPmt"/> </td>
										</tr>
										<%if(Boolean.parseBoolean(String.valueOf(request.getSession(false).getAttribute(Constants.PAYMETRICS_CC)))){ %>
										<%} %>
									<%} %>	
                                    </tbody></table>
                                </div>
                            </div>
                        </div>
                        <%-------Alternate ShipTo Address #-------%>
                        <% if (checkOutForm.getAllowAlternateShipTo()) { %>
                         <div class="left clearfix">
                            <h2 class="width50"><app:storeMessage key="userportal.esw.checkout.header.alternateAddress" /></h2>
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
                                         <td>
                                             <app:storeMessage key="userportal.esw.checkout.text.attention" />&nbsp;<span class="required">*</span>
                                         </td>
                                         <td>
                                         	<html:text name="esw.CheckOutEswForm" property="checkOutForm.alternateAddress1" size="20" maxlength="73"/>
                                         </td>
                                     </tr>
                                     <tr>
                                         <td>
                                             <app:storeMessage key="userportal.esw.checkout.text.phone" />&nbsp;<span class="required">*</span>
                                         </td>
                                         <td>
<!--                                          	<input type="text" name="checkOutForm.alternatePhoneNum" maxlength="20" size="20"> -->
                                         	<html:text name="esw.CheckOutEswForm" property="checkOutForm.alternatePhoneNum" size="20" maxlength="20"/>
                                         </td>
                                     </tr>	
                                     <tr>
                                         <td>
                                             <app:storeMessage key="userportal.esw.checkout.text.address1" />
                                         </td>
                                         <td>
                                         	<html:text name="esw.CheckOutEswForm" property="checkOutForm.alternateAddress2" size="20" maxlength="79"/>
                                         </td>
                                     </tr>	
                                     <tr>
                                         <td>
                                             <app:storeMessage key="userportal.esw.checkout.text.address2" />
                                         </td>
                                         <td>
                                         	<html:text name="esw.CheckOutEswForm" property="checkOutForm.alternateAddress3" size="20" maxlength="79"/>
                                         </td>
                                     </tr>	
                                     <tr>
                                         <td>
                                             <app:storeMessage key="userportal.esw.checkout.text.city" />
                                         </td>
                                         <td>
                                         	<html:text name="esw.CheckOutEswForm" property="checkOutForm.alternateCity" size="20" maxlength="39"/>
                                         </td>
                                     </tr>	
                                     <% if (useState) { %>
                                     <tr>
                                         <td>
                                             <app:storeMessage key="userportal.esw.checkout.text.stateProvince" />
                                         </td>
                                         <td>
                                         	<html:text name="esw.CheckOutEswForm" property="checkOutForm.alternateStateProvince" size="20" maxlength="29"/>
                                         </td>
                                     </tr>
                                     <% } %>
                                     <tr>
                                         <td>
                                             <app:storeMessage key="userportal.esw.checkout.text.zipPostalCode" />
                                         </td>
                                         <td>
                                         	<html:text name="esw.CheckOutEswForm" property="checkOutForm.alternatePostalCode" size="20" maxlength="14"/>
                                         </td>
                                     </tr>	
                                     <tr>
                                         <td>
                                             <app:storeMessage key="userportal.esw.checkout.text.country" />
                                         </td>
                                         <td class="limit">
                                             <html:select name="esw.CheckOutEswForm" property="checkOutForm.alternateCountry">
                                             <html:option value=""><app:storeMessage  key="admin.select.country"/></html:option>
											 <html:options  collection="country.vector" labelProperty="uiName" property="shortDesc" />
											 </html:select>
										</td>
                                     </tr>												
                                     </tbody>
                                 </table>
                             </div>
                             <div class="column width50">
                             </div>
                         </div>
                        <% } %>
                    </div>
                    <div class="bottom clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
                </div>