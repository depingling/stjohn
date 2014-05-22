<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>
<%@ page import="com.cleanwise.service.api.util.Utility"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.view.utils.*"%>

<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %> 

<bean:define id="theForm" name="esw.ShoppingForm"  type="com.espendwise.view.forms.esw.ShoppingForm"/>
<bean:define id="item" name="esw.ShoppingForm" property="userShopForm.itemDetail" type="com.cleanwise.service.api.value.ShoppingCartItemData"/>
<bean:define id="appUser" name="ApplicationUser" type="com.cleanwise.view.utils.CleanwiseUser"/>
<%
boolean readOnlyFlag = true;
boolean showCorporateOrder = Utility.getSessionDataUtil(request).isConfiguredForCorporateOrders();
boolean isCorporateOrderOpen = ShopTool.hasInventoryCartAccessOpen(request);
%>
<style>
	a.plain {
	    display: block;
	    float: left;
	    margin-left: 20px;
	    padding: 3px 0 3px 25px;
	}
	.greencerthead {
		font-size: 12px;
		font-weight: bold;
		color: black;
	}
	.greencert {
		font-size: 14px;
		font-weight: bold;
		color: #093;
	}
   .iconLarge { 
     width  : 180px; 
     height : 180px; 
   }
</style>

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
                <div class="topPadding">
<%
String productSearchPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "productSearch.jsp");
%>                    
<jsp:include page="<%=productSearchPage%>" />
                </div>
				<div class="twoColBox">
				<% if (appUser.getUserAccount().isSupportsBudget()) { %>
					<div class="column width80">
						<h3><bean:write name="item" property="categoryPathForNewUI"/></h3>
					</div>
					<div class="column width20">
						<logic:greaterThan name="item" property="product.costCenterId" value="0">
						<h3><bean:write name="item" property="product.costCenterName" /></h3>
						</logic:greaterThan>
					</div>
					<%}else{%>
						<div>
						<h3><bean:write name="item" property="categoryPathForNewUI"/></h3>
					</div>
					<%}%>
				</div>
                <!-- Start Box -->
                <div class="boxWrapper">
                    <div class="top clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
                    <div class="content">
                        <div class="left clearfix">
                            <div class="twoColBox">
                                        <a href="#" class="btn btnRight" onclick="window.print();return false;"><span><app:storeMessage key="global.action.label.printPage" /></span></a>
                                <div class="column width70">
<logic:notEmpty name="item" property="product.image">
                                    <img class="iconLarge" src="<%=request.getContextPath()%><bean:write name="item" property="product.image"/>" class="product" />
</logic:notEmpty>
                                    <h2><bean:write name="item" property="product.shortDesc"/></h2>
                                    <p style="word-wrap: break-word;"><bean:write name="item" property="product.longDesc"/></p>
                                    <table class="productDetail">
                                        <tr>
                                            <td><app:storeMessage key="shoppingItems.text.sku#" /></td>
                                            <td><bean:write name="item" property="actualSkuNum"/></td>
                                        </tr>
                                        <tr>
                                            <td><app:storeMessage key="shoppingItems.text.pack" /></td>
                                            <td><bean:write name="item" property="product.pack"/></td>
                                        </tr>
                                        <tr>
                                            <td><app:storeMessage key="shoppingItems.text.uom" /></td>
                                            <td><bean:write name="item" property="product.uom"/></td>
                                        </tr>
                                        <tr>
                                            <td><app:storeMessage key="shoppingItems.text.manufacturer" /></td>
                                            <td><bean:write name="item" property="product.manufacturer.shortDesc"/></td>
                                        </tr>
                                        <tr>
                                            <td><app:storeMessage key="shoppingItems.text.mfgSkuNum" /></td>
                                            <td><bean:write name="item" property="product.manufacturerSku"/></td>
                                        </tr>
                                        <tr>
                                            <td><app:storeMessage key="shoppingItems.text.size" /></td>
                                            <td><bean:write name="item" property="product.size"/></td>
                                        </tr>
                                        <tr>
                                            <td><app:storeMessage key="shoppingItems.text.color" /></td>
                                            <td><bean:write name="item" property="product.color"/></td>
                                        </tr>
                      					<% if (appUser.getUserAccount().isShowSPL()){ %>
											<tr>
												<td>
	                        						<app:storeMessage key="shoppingItems.text.spl" />
	                        					</td>
												<td>
												     <%
												     	boolean itemIsSPL = false;
								                        if (item.getProduct().getCatalogDistrMapping() != null && item.getProduct().getCatalogDistrMapping().getStandardProductList() != null) {
							                                if (Utility.isTrue(item.getProduct().getCatalogDistrMapping().getStandardProductList())) {
							                                	itemIsSPL = true;
							                                }
								                        }
												     	if (itemIsSPL) { %>
															<app:storeMessage key="global.text.yes" />
						                             <% }
												     	else
												     	{
												     %>
															<app:storeMessage key="global.text.no" />
						                             <% } %>
												</td>
											</tr>
                      					<% } %>
                                    </table>
<%
//String selectedMsdsType = null;  //was also commented in the Classic (Old) UI
String urlMsds = item.getProduct().getMsds();
//New code for MSDS Plug-in: Begin
String msdsJdUrl=item.getProduct().getProductJdWsUrl(); 
// New code for MSDS Plug-in: End
String urlDed = item.getProduct().getDed();
String urlSpec = item.getProduct().getSpec();
int itemId = item.getProduct().getItemData().getItemId();
String msdsStorageType = theForm.getMsdsStorageTypeCd();
if ((msdsStorageType == null) || msdsStorageType.trim().equals("")) {
    msdsStorageType = RefCodeNames.BINARY_DATA_STORAGE_TYPE.DATABASE; //Default
}

String dedStorageType = theForm.getDedStorageTypeCd();
if ((dedStorageType == null) || dedStorageType.trim().equals("")) {
	dedStorageType = RefCodeNames.BINARY_DATA_STORAGE_TYPE.DATABASE; //Default
}

String specStorageType = theForm.getSpecStorageTypeCd();
if ((specStorageType == null) || specStorageType.trim().equals("")) {
	specStorageType = RefCodeNames.BINARY_DATA_STORAGE_TYPE.DATABASE; //Default
}
if (Utility.isSet(urlMsds) || Utility.isSet(msdsJdUrl) || Utility.isSet(urlDed) || Utility.isSet(urlSpec)) {
		   %>                                 
   <p class="downloads clearfix"><span><app:storeMessage key="product.detail.downloads" /></span>	
<% 
if (msdsStorageType.trim().equals(RefCodeNames.BINARY_DATA_STORAGE_TYPE.DATABASE)) {
%>                                 
   <%if (Utility.isSet(urlMsds)) { //MSDS IS set: MSDS Pdf image rules, even if there IS an MSDS Plug-in %>
                    <% String documentTypeMsdsLinkFromDb = "userportal/esw/shopping.do?operation=itemDocumentFromDb&path=" + urlMsds; %>
		                                           <html:link action="<%=documentTypeMsdsLinkFromDb %>" target="_blank" styleClass="<%=getLinkClass(urlMsds)%>">
		                                           <app:storeMessage key="global.label.msds" />
		                                           </html:link>		                                           	 
   <%}%>
<% } else if (msdsStorageType.trim().equals(RefCodeNames.BINARY_DATA_STORAGE_TYPE.E3_STORAGE)){ //msds storage type = E3 %>
      <%if (Utility.isSet(urlMsds)) { //MSDS IS set: MSDS Pdf image rules, even if there IS an MSDS Plug-in %>
		            <%String documentTypeMsdsLinkFromE3 = "userportal/esw/shopping.do?operation=itemDocumentFromE3Storage&path=" + urlMsds; %>
		                                           <html:link action="<%=documentTypeMsdsLinkFromE3%>" target="_blank" styleClass="<%=getLinkClass(urlMsds)%>">
		                                           <app:storeMessage key="global.label.msds" />
		                                           </html:link>		                                           	
	  <%}%>
<% } %>
<% //JD Web Services piece of code (below) is independent from the storage type => it is always executed
if(urlMsds==null || urlMsds.trim().length()==0) { //MSDS IS NOT set
    if(msdsJdUrl!=null && msdsJdUrl.trim().length()>0) { //MSDS Plug-in IS set
%> 
                                        <a class="<%=getLinkClass(msdsJdUrl)%>" href="<%=request.
                                            getContextPath() + msdsJdUrl%>" target="_blank"><app:storeMessage key="global.label.msds" /></a>
   <%}%>
<%} %>
<% if (dedStorageType.trim().equals(RefCodeNames.BINARY_DATA_STORAGE_TYPE.DATABASE)) { %>
    <%if (Utility.isSet(urlDed)) {%> 
		        <% String documentTypeDedLinkFromDb = "userportal/esw/shopping.do?operation=itemDocumentFromDb&path=" + urlDed; %>
		                                           <html:link action="<%=documentTypeDedLinkFromDb%>" target="_blank" styleClass="<%=getLinkClass(urlDed)%>">
		                                           <app:storeMessage key="global.label.ded" />
		                                           </html:link>
                                        
                                                                             

       
   <%}%>
<% } else if (dedStorageType.trim().equals(RefCodeNames.BINARY_DATA_STORAGE_TYPE.E3_STORAGE)){ // ded storage type = E3 %>
     <%if (Utility.isSet(urlDed)) {%> 
		        <% String documentTypeDedLinkFromE3 = "userportal/esw/shopping.do?operation=itemDocumentFromE3Storage&path=" + urlDed; %>
		                                           <html:link action="<%=documentTypeDedLinkFromE3%>" target="_blank" styleClass="<%=getLinkClass(urlDed)%>">
		                                           <app:storeMessage key="global.label.ded" />
		                                           </html:link>
		                                           
		                                                                                

		          
	<%}%>
<% } %>
<% if (specStorageType.trim().equals(RefCodeNames.BINARY_DATA_STORAGE_TYPE.DATABASE)) { %>
     <%if (Utility.isSet(urlSpec)) {%>
                <% String documentTypeSpecFromDbLink = "userportal/esw/shopping.do?operation=itemDocumentFromDb&path=" + urlSpec;%>
		                                           <html:link action="<%=documentTypeSpecFromDbLink%>" target="_blank" styleClass="<%=getLinkClass(urlSpec)%>">
		                                           <app:storeMessage key="shop.msdsSpecs.text.specs" />
		                                           </html:link>		                                           		                                           
     <% } %>
<% } else if (specStorageType.trim().equals(RefCodeNames.BINARY_DATA_STORAGE_TYPE.E3_STORAGE)) { %>
     <%if (Utility.isSet(urlSpec)) {%>
		        <% String documentTypeSpecFromE3StorageLink = "userportal/esw/shopping.do?operation=itemDocumentFromE3Storage&path=" + urlSpec;%>
		                                           <html:link action="<%=documentTypeSpecFromE3StorageLink%>" target="_blank" styleClass="<%=getLinkClass(urlSpec)%>">
		                                           <app:storeMessage key="shop.msdsSpecs.text.specs" />
		                                           </html:link>		                                           		                                           
    <%}%>                                                                    
<%
   } 
%>
 </p>
<% } //if (Utility.isSet(urlMsds) || Utility.isSet(msdsJdUrl) || Utility.isSet(urlDed) || Utility.isSet(urlSpec)) { %>
                                </div>
                                <div class="column width30">
                                    <div class="pricingDetail">
                                      <logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">  
                                        <p><app:storeMessage key="shoppingItems.text.price" /></p>
                                        <h2><%=ClwI18nUtil.getPriceShopping(request, new BigDecimal(item.getPrice()),"&nbsp;")%></h2>
                                      </logic:equal>   
                                        <div class="qtyLine">
                                            <span><app:storeMessage key="shoppingItems.text.qty" /></span> <div class="qtyInput">
<html:form styleId="detailFormId" action="userportal/esw/shopping.do">
<html:hidden name="esw.ShoppingForm" property="<%=Constants.PARAMETER_OPERATION%>" styleId="operationId" />
<html:hidden name="esw.ShoppingForm" property="itemId" value="<%=\"\" + item.getProduct().getProductId()%>"/>
<html:text name="esw.ShoppingForm" property="quantityDetail" styleClass="qty"/>
</html:form>
                                            </div>
                                        </div>
                                       
<%
	if (!appUser.isBrowseOnly()) { //STJ-6022 (handle browse only user)
	    if (showCorporateOrder && isCorporateOrderOpen) {
%>
                <a href="#" class="blueBtnMed" onclick="javascript:setFieldsAndSubmitForm('detailFormId','operationId','<%=Constants.PARAMETER_OPERATION_VALUE_ITEM_TO_CORPORATE_ORDER%>');"><span><app:storeMessage key="global.action.label.addToCorporateOrder" /></span></a>

<%
	    }else{
%>
		 <a href="#" class="blueBtnMed" onclick="javascript:setFieldsAndSubmitForm('detailFormId','operationId','<%=Constants.PARAMETER_OPERATION_VALUE_ITEM_TO_CART%>');"><span><app:storeMessage key="global.action.label.addToShoppingCart" /></span></a>
<%      }
    } // if browse only %>
                                    </div>
                                </div>
                            </div>
<logic:present name="item" property="product.certCompaniesBusEntityDV">
<bean:size  id="certCompSize"  name="item" property="product.certCompaniesBusEntityDV"/>
<logic:greaterThan name="certCompSize" value="0">
                            <div class="twoColBox">
								<div class="column">
									<p><img src="<%=request.getContextPath()%>/esw/images/ecoFriendly.png" /> <app:storeMessage key="product.detail.greenProductCertification" />:</p>
									<table  class="greenDetail">
                                     <tr>
<logic:iterate id="certComp" name="item" property="product.certCompaniesBusEntityDV" type="com.cleanwise.service.api.value.BusEntityData" indexId="ik">
									    <td width="1%"><img src="<%=request.getContextPath()%>/esw/images/green-check.png" alt="green-check" /></td>
										<td><bean:write name="certComp" property="shortDesc"/></td><%
	if (ik % 3 == 2) {%>
									  </tr>
									  <tr><%
	}%>
</logic:iterate>
									  </tr>
									</table>
								</div>
							</div>
</logic:greaterThan>
</logic:present>

                        </div>
                    </div>
                    <div class="bottom clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
                </div>
                <!-- End Box -->
            </div>
        </div>
<%!private String getLinkClass(String url) {
    if (url != null) {
        if (url.endsWith(".pdf")) {
            return "pdf";
        } else if (url.endsWith(".doc")) {
            return "word";
        } else if (url.endsWith(".xls")) {
            return "excel";
        }
    }
    return "plain";
}
%>