<%@page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.view.forms.QuickOrderForm" %>


<%@ taglib uri="/WEB-INF/struts-template.tld" prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<bean:define id="shoppingForm" name="esw.ShoppingForm"  type="com.espendwise.view.forms.esw.ShoppingForm"/> 


<%
QuickOrderForm quickOrderForm = shoppingForm.getQuickOrderForm();
String addToCartLink = "/userportal/esw/shopping.do";
CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
%>		 
		 
		 <!-- Start Box -->
                <div class="boxWrapper smallMargin">
                    <div class="top clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
                    <div class="content clearfix">

                        <div class="left clearfix ">                            
							<% 
								String divZone = "zone collapsed";
								if(shoppingForm.isShowQuickShop()){
									divZone = "zone";	
								}
							%>
							<div class="<%= divZone%>">
								<div class="title">
									<h2><a href="#"><app:storeMessage key="shop.quick.text.quickShop" /></a></h2>
								</div>									
												
								<div class="zoneContent">
	                  <%
	                    //STJ-6022 (handle browse only user)
	                    if (!appUser.isBrowseOnly()) {
	                  %>

									<p class="right">									
										<a href="javascript:setFieldsAndSubmitForm('quickFormId','quickShopOperation','addQuickShopItemsToCart')" class="blueBtnMed right" ><span><app:storeMessage key="global.action.label.addToShoppingCart" /></span></a> 	
									</p>
		            <% } %>
									<html:form styleId="quickFormId" action="userportal/esw/shopping.do">
									<html:hidden styleId ="quickShopOperation" property="<%= Constants.PARAMETER_OPERATION %>"/>
									<div class="twoColBox">
										<div class="column">
									<table class="order">
										<colgroup>
											<col />
											<col />
											<col />
										</colgroup>
										<thead>
											<tr>
												<th></th>
												<th>
													<app:storeMessage key="shoppingItems.text.sku#" />
												</th>   
												<th>
													<app:storeMessage key="shoppingItems.text.qty" />
												</th>
											</tr>
										</thead>
										<tbody>                  
										 <% int pageSize = quickOrderForm.getPageSize();
										    int pageSize1 = pageSize - pageSize / 2; 
												     for(int index = 0; index < pageSize1; index++) {
												       String itemSkusEl = "quickOrderForm.itemSkusElement["+index+"]";
												       String itemQtysEl = "quickOrderForm.itemQtysElement["+index+"]";
												  %>
											<tr>
												     <td>
												        <%=""+(index+1)%>
												     </td>
												     <td>
												        <html:text property="<%= itemSkusEl%>" size="10"/>
												     </td>
												     <td>
												         <div class="qtyInput">													
												             <html:text property="<%=itemQtysEl%>" size="4"/>
												         </div>
												     </td>
												</tr>
											<% } %>
										</tbody>
									</table>
									</div>
									<div class="column">
									<table class="order">
										<colgroup>
											<col />
											<col />
											<col />
										</colgroup>
										<thead>
											<tr>
												<th></th>
												<th>
													<app:storeMessage key="shoppingItems.text.sku#" />
												</th>   
												<th>
													<app:storeMessage key="shoppingItems.text.qty" />
												</th>
											</tr>
										</thead>
										<tbody>                  
										 <% for(int index = pageSize1; index < pageSize; index++) {
												       String itemSkusEl = "quickOrderForm.itemSkusElement["+index+"]";
												       String itemQtysEl = "quickOrderForm.itemQtysElement["+index+"]";
												  %>
											<tr>
												     <td>
												        <%=""+(index+1)%>
												     </td>
												     <td>
												        <html:text property="<%= itemSkusEl%>" size="10"/>
												     </td>
												     <td>
												         <div class="qtyInput right">
												            <html:text property="<%=itemQtysEl%>" size="4"/>
												         </div>
												     </td>
												</tr>
											<% } %>
										</tbody>
									</table>
									</div>
									</div>
									</html:form>
	                  <%
	                    //STJ-6022 (handle browse only user)
	                    if (!appUser.isBrowseOnly()) {
	                  %>
									<p class="right">
										<a href="javascript:setFieldsAndSubmitForm('quickFormId','quickShopOperation','addQuickShopItemsToCart')" class="blueBtnMed right" ><span><app:storeMessage key="global.action.label.addToShoppingCart" /></span></a>
									</p>
				    <% } %>
								</div>
							</div>

                        </div>
					</div>
					<div class="bottom clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
                </div>
                <!-- End Box -->
