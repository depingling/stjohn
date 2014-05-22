<%@page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
<%@page import="java.util.List"%>
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

							<div class="zone">
								<div class="title">
									<h2><a href="#"><app:storeMessage key="shop.quick.text.quickShop" /></a></h2>
								</div>									
												
								<div class="zoneContent">	
	                  <%
	                    //STJ-6022 (handle browse only user)
	                    if (!appUser.isBrowseOnly()) {
	                  %>
									<p class="right">
										<a href="javascript:setFieldsAndSubmitForm('quickFormId','quickShopOperation','quickShopResolveSku')" class="blueBtnMed right" ><span><app:storeMessage key="global.action.label.addToShoppingCart" /></span></a> 	
									</p>
					<% } %>
									<table class="order">
										<colgroup>

											<col />
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
												<th>
													<app:storeMessage key="shoppingItems.text.productName" />
												</th> 
											</tr>
										</thead>
										<tbody>                  
										<html:form styleId="quickFormId" action="userportal/esw/shopping.do">
										<html:hidden styleId ="quickShopOperation" property="<%= Constants.PARAMETER_OPERATION %>"/>
										 
										  <% int pagesize = quickOrderForm.getPageSize();
										     for(int kkk=0; kkk<pagesize; kkk++) {
										       String itemSkusEl = "quickOrderForm.itemSkusElement["+kkk+"]";
										       String itemQtysEl = "quickOrderForm.itemQtysElement["+kkk+"]";
										       String itemIdsEl = "quickOrderForm.itemIdsElement["+kkk+"]";
										       String skuNum = quickOrderForm.getItemSkusElement(kkk);
										       String skuQty = quickOrderForm.getItemQtysElement(kkk);
										       if(skuNum!=null && skuNum.trim().length()>0) {
										  %>
											<tr>
												
												     <td>
												         <%=""+(kkk+1)%>
												     </td>
												     <td>
												        <%=skuNum%>
												     </td>
												     <td class="right">
												      <html:hidden property="<%=itemQtysEl%>" value="<%=skuQty%>"/>
												         <%=skuQty%>
												     </td>
												     <td>
												      <%
													        List shortDesc = quickOrderForm.getShortDescElement(kkk);
													        List itemMfgIds = quickOrderForm.getItemIdListsElement(kkk);
													        %>
													        <% if(shortDesc!= null && shortDesc.size()==1) { %>
													           <%
													            String mfgName = (String) shortDesc.get(0);
													            Integer itemIdI = (Integer) itemMfgIds.get(0);
													            int itemId = itemIdI.intValue();
													           %>
													          <html:hidden property="<%=itemIdsEl%>" value="<%=\"\"+itemId%>"/>
													          <%=mfgName%>
													        <% } else if(shortDesc!= null && shortDesc.size()>1) {  %>
													          <html:select property="<%=itemIdsEl%>" styleClass="text full">
													          <%
													          for(int jj=0; jj<shortDesc.size(); jj++) {
													            String mfgName = (String) shortDesc.get(jj);
													            Integer itemIdI = (Integer) itemMfgIds.get(jj);
													            int itemId = itemIdI.intValue();
													          %>
													          <html:option value="<%=\"\"+itemId%>"><%=mfgName%></html:option>
													          <% } %>
													          </html:select>
													        <% } %>
												     </td>
												  
												</tr>
											<% } }%>
											
										</html:form>
										</tbody>
									</table>
	                  <%
	                    //STJ-6022 (handle browse only user)
	                    if (!appUser.isBrowseOnly()) {
	                  %>
									<p class="right">
										<a href="javascript:setFieldsAndSubmitForm('quickFormId','quickShopOperation','quickShopResolveSku')" class="blueBtnMed right" ><span><app:storeMessage key="global.action.label.addToShoppingCart" /></span></a>
									</p>
					 <% } %>     
								</div>
							</div>

                        </div>
					</div>
					<div class="bottom clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
                </div>
                <!-- End Box -->
