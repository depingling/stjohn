<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@page import="com.cleanwise.view.forms.EditOrderGuideForm"%>
<%@page import="com.cleanwise.service.api.util.SessionDataUtil"%>
<%@page import="com.cleanwise.view.utils.ShopTool"%>
<%@page import="com.espendwise.view.logic.esw.ShoppingLogic"%>
<%@page import="com.cleanwise.view.forms.UserShopForm"%>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>

<%@ page import="java.util.Date" %>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.List" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>
<bean:define id="myForm" name="esw.ShoppingForm"  type="com.espendwise.view.forms.esw.ShoppingForm"/>
<script type="text/javascript" src="../../externals/dojo_1.1.0/dojo/dojo.js" djConfig="parseOnLoad: true, isDebug: false">
</script>
<style>
   .iconLarge { 
     width  : 180px; 
     height : 180px; 
     }
</style>

<% 
	CleanwiseUser appUser = (CleanwiseUser)session.getAttribute(Constants.APP_USER);
	StringBuilder shoppingCatalogLink = new StringBuilder(50);
	shoppingCatalogLink.append(session.getAttribute("pages.root"));
	shoppingCatalogLink.append("/userportal/esw/shopping.do?");
	shoppingCatalogLink.append(Constants.PARAMETER_OPERATION);
	shoppingCatalogLink.append("=");
	shoppingCatalogLink.append(Constants.PARAMETER_OPERATION_VALUE_SHOP_BY_CATALOG);
	UserShopForm userShopForm = myForm.getUserShopForm();
	EditOrderGuideForm editOrderGuideForm = myForm.getEditOrderGuideForm();
	pageContext.setAttribute("editOrderGuideForm",editOrderGuideForm);
	SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
	pageContext.setAttribute("userShopForm",userShopForm);
	session.setAttribute("SHOP_FORM",userShopForm);
	String prevCategory = "";
	List sCartItems = userShopForm.getCartItems();
	boolean showCorporateOrder = Utility.getSessionDataUtil(request).isConfiguredForCorporateOrders();
        boolean isCorporateOrderOpen = ShopTool.hasInventoryCartAccessOpen(request);
%>
<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<bean:define id="userShoppingForm" name="userShopForm" type="com.cleanwise.view.forms.UserShopForm"/>
<bean:define id="offset" name="userShopForm" property="offset"/>
<bean:define id="pagesize" name="userShopForm" property="pageSize"/>
<script language="JavaScript">
	$(document).ready(function(){	
		showMenu('<%=sessionDataUtil.getPanelProductCatalog()%>');		
	});
	
	function viewExcelFormat() {
	  var loc = "shopping.do?operation=excelPrintCatalog";
	<% if (userShopForm.getAppUser() != null && userShopForm.getAppUser().getUserAccount().isHideItemMfg()) { %>
	  loc = loc + "&showMfg=n&showMfgSku=n";
	<% } %>
	  prtwin = window.open(loc,"excel_format",
	    "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500, width=775,left=100,top=165");
	  prtwin.focus();
	}
	function submitForm(formId) {
		  	document.getElementById(formId).submit();
	}
	function displayCategory(catalogItemKey)
	{
			var catKey = document.getElementById('catalogItemKey');
			catKey.value = catalogItemKey;
			document.getElementById('categoryForm').submit();
	}
	function toggle(ulId, imgObj) {
		var categoryKey = ulId;
		if (document.getElementById(ulId).style.display == 'block') {
			document.getElementById(ulId).style.display = 'none';
		} else {
			document.getElementById(ulId).style.display = 'block';
		}
		var httpImagePath = imgObj.src;
		var lastIndexOfDot = httpImagePath.lastIndexOf(".");
		var imgName = httpImagePath.substring(lastIndexOfDot - 1,lastIndexOfDot);
		var lastIndexOfSlash = httpImagePath.lastIndexOf("/");
		var httpPath = httpImagePath.substring(0,lastIndexOfSlash + 1);
		var imgPath = "";  
		var addOrRemove="";
		if(imgName=='+')
		{
			imgObj.src = httpPath + "-.png";
			imgPath = httpPath + "-.png";
			addOrRemove="add";
		}
		else
		{
			imgObj.src = httpPath + "+.png";
			imgPath = httpPath + "+.png";
			addOrRemove="remove";
		}
	}
	
	function movePanel(panelOperation){
		dojo.byId("panelFormOperation").value  = '<%=Constants.PARAMETER_OPERATION_VALUE_MOVE_PANEL%>';
		dojo.byId("panelSelected").value = panelOperation;

		$.post("<%=request.getContextPath()%>/userportal/esw/shopping.do",
			$("#panelForm").serialize(),
			function(response, ioArgs) {
				showMenu(panelOperation);
		});
		
	}
	function submitFormToCreateShoppingList() {
		document.getElementById('ogId').value = document.getElementById('shoppingListId').value;
	    document.getElementById('operationId').value = 'groupItemCreateShoppingList';
	    document.getElementById('multiProductShoppingFormId').submit();
	}
	
</script>
<%
    boolean
      quickOrderView = false,
      shoppingCartView = false,
      checkoutView = false,
      editCartItems = true ,
      f_showSelectCol = false,
      addToCartListView = true;
	String selectOption = ClwI18nUtil.getMessageOrNull(request, "userportal.esw.shoppingCart.addShoppingList.selectOption");
	if (!Utility.isSet(selectOption)) {
		selectOption = Constants.SELECT_OPTION;
	}
	String createListOption = ClwI18nUtil.getMessageOrNull(request, "userportal.esw.shoppingCart.addShoppingList.createListOption");
	if (!Utility.isSet(createListOption)) {
		createListOption = Constants.CREATE_LIST_OPTION;
	}
%>
		<%
			String errorsAndMessagesPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "errorsAndMessages.jsp");
		%>
        <jsp:include page="<%=errorsAndMessagesPage %>"/>
		<!-- Begin: Shopping Sub Tabs -->
        <%
			String shoppingTabPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "shoppingTabsWide.jsp");
		%>
        <jsp:include page="<%=shoppingTabPage%>"/>
      	<!-- End: Shopping Sub Tabs -->
<div id="contentWrapper" class="clearfix collapsePanelWide wide">
            <div id="content">
		
                <!-- Start Right Column -->
                <div class="rightColumn">
                    <div class="rightColumnIndent collapsePanelRightCol">
                       <%
				      	String productSearchPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "productSearch.jsp");
					   %>
      					<jsp:include page="<%=productSearchPage%>"/>
                        <!-- Start Box -->
                        <div class="boxWrapper">
                            <div class="top clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
                            <div class="content clearfix">
                                <div class="left clearfix">
    	    	    	         <logic:present name="userShopForm" property="cartItems">
                                     <h2>
                                      <%
										String catalogBreadCrumbJsp = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "catalogBreadCrumbNavigator.jsp");
			             			  %>
								       <logic:greaterThan name="userShopForm" property="shopNavigatorInfo" value="0">
								       <jsp:include page="<%=catalogBreadCrumbJsp %>"/>
								       </logic:greaterThan>
								       </logic:present>
								  </h2>
								  
								  
<%-- 									<% 
	   									ShoppingCartItemData itemG = userShopForm.getItemDetail();
										String categoryGPath = itemG.getCategoryPathWithLinksForNewUI(request);
									%>
								  <h2><%= categoryGPath%></h2>
 --%>								  
								  
	                                <logic:present name="userShopForm" property="cartItems">
	                                <bean:size id="size" name="userShopForm" property="cartItems"/>
	                                <logic:greaterThan name="size" value="0">
								   <p class="right">
                                        <a href="javascript:viewExcelFormat()" class="btn rightMargin"><span>
												<app:storeMessage key="global.action.label.exportExcelSheet" /></span></a> 
                                        <a href="#" class="btn rightMargin" onclick="window.print();return false;"><span>
                                        <app:storeMessage key="global.action.label.printPage" /></span></a>
                                    </p>
                                    <hr />
                                   	</logic:greaterThan>
                                   	</logic:present>
                                    <html:form styleId="multiProductShoppingFormId" action="userportal/esw/shopping.do">
                                    <html:hidden property="userShopForm.orderGuideName" styleId="ogId"/>
                                     <logic:present name="userShopForm" property="cartItems">
	                                <bean:size id="size" name="userShopForm" property="cartItems"/>
	                                <logic:greaterThan name="size" value="0">
	                                <html:hidden property="<%=Constants.PARAMETER_OPERATION%>" value="addToCart" styleId="operationId"/>
                                       <html:hidden property="addCatalogItems" value="true"/>
                                        <input type="hidden" id="selectedShoppingListId" name="selectedShoppingList" value="<%= userShopForm.getSelectedShoppingListId() %>"/>
	                                <%
	                                	//STJ-4521 (handle browse only user)
	                                	if (!appUser.isBrowseOnly()) {
	                                %>
                                    <p class="clearfix">

	                                <a href="javascript:setFieldsAndSubmitForm('multiProductShoppingFormId','operationId','clearGroupItemQty')" class="blueBtnMed" onclick="return true"><span><app:storeMessage key="global.action.label.clearQuantities" /></span></a>
				     <% 
                                    	if (showCorporateOrder && isCorporateOrderOpen) {
					
				     %>			                                      
	                                <a href="javascript:setFieldsAndSubmitForm('multiProductShoppingFormId','operationId','groupItemAddCatalogItemsToCorporateOrder')" class="blueBtnMed" onclick="return true"><span><app:storeMessage key="global.action.label.addToCorporateOrder" /></span></a>
						
				   <%
					} else { 
				   %> 
                                        <a href="javascript:setFieldsAndSubmitForm('multiProductShoppingFormId','operationId','groupItemAddToCart')" class="blueBtnMed" >
	                                        <span><app:storeMessage key="global.action.label.addToShoppingCart" /></span>
                                        </a>
				   <%   }   %>	
                                        <a href="javascript:setFieldsAndSubmitForm('multiProductShoppingFormId','operationId','groupItemAddToExistingShoppingList')" class="blueBtnMed" >
                                        <span><app:storeMessage key="global.action.label.addToShoppingList" /></span>
                                        </a>
                                         <html:select styleClass="span" property="userShopForm.selectedShoppingListId" styleId="shoppingListsId">
                                         <html:option value="select"><%=Utility.encodeForHTML(selectOption) %></html:option>
                                         <logic:present name="editOrderGuideForm" property="userOrderGuides">
	  							  	<logic:iterate id="userOrderGuide" property="userOrderGuides" name="editOrderGuideForm" indexId="index">
                                            <html:option value="<%=String.valueOf(((OrderGuideData)userOrderGuide).getOrderGuideId())%>">
                                            	<bean:write name="userOrderGuide" property="shortDesc"/>
                                            </html:option>
                                            </logic:iterate>
                                            </logic:present>
                                            <html:option value="newList"><%=Utility.encodeForHTML(createListOption) %></html:option>
                                        </html:select>
                                    
                                    </p>
                                    <hr />
                                    <%
	                                	}
                                    %>
                                    <div class="shoppingList hide">
                                    <%
	                                    String listName = ClwI18nUtil.getMessageOrNull(request, "userportal.esw.shoppingList.listName");
								        if (!Utility.isSet(listName)) {
								        	listName = Constants.SHOPPING_LIST_NAME;
								        }
                                    %>
                                        <h3><app:storeMessage key="productCatalog.label.select.shoppingList" /></h3>
                                        <div class="listName">
                                            <label><%=Utility.encodeForHTML(listName) %><br />
                                            </label>
                                        </div>
                                    </div>
                                    </logic:greaterThan>
                                    </logic:present>
                                    <%String categoryName =""; %>
                                     <%String categoryPath =""; %>
                                     <%boolean showGroupFlag = false; %>
                                    <logic:present name="userShopForm" property="cartItems">
                                    <logic:iterate id="item" name="userShopForm" property="cartItems"
   													  offset="0"  indexId="index"
   													  type="com.cleanwise.service.api.value.ShoppingCartItemData">

								     <bean:define id="itemId" name="item" property="product.productId"/>
								     <bean:define id="quantityEl" value="<%=\"quantityElement[\"+index+\"]\"%>"/>
								     <bean:define id="itemIdsEl" value="<%=\"itemIdsElement[\"+index+\"]\"%>"/>

										<!-- item picture and long description -->
										<%	
											 ShoppingCartItemData itemDet = userShopForm.getItemDetail();
//											 String image=itemDet.getProduct().getImage();
//											 String srcImage = "/"+ ClwCustomizer.getStoreDir() + "/en/images/noManXpedxImg.gif" ;
//											 if(image!=null && image.trim().length()>0) { 
//												 srcImage ="/"+ ClwCustomizer.getStoreDir() + "/en/images/"+itemDet.getProduct().getImage();
//											  }
// 										     String skuDesc = item.getProduct().getCustomerProductShortDesc();
// 		           						     if(skuDesc==null || skuDesc.trim().length()==0) {
// 		           						           skuDesc = item.getProduct().getShortDesc();
// 	           							     }
		           						     //String itemGroupName = userShopForm.getItemGroupCachedView().getProduct().getShortDesc();
		           						     String itemGroupName = itemDet.getProduct().getShortDesc();
											 String image=itemDet.getProduct().getImage();
											 String srcImage = "/"+ ClwCustomizer.getStoreDir() + "/en/images/noManXpedxImg.gif" ;
											 if(image!=null && image.trim().length()>0) { 
												 srcImage ="/"+ ClwCustomizer.getStoreDir() + "/"+image;
											 }
              							%>   
              							<% if (!showGroupFlag) { showGroupFlag = true; %>
										<div class="twoColBox">                                        
											<div class="column width30">
												<!-- <a href="#"><img src="images/largeMascot.png" class="iconLarge" /> </a> -->
												<a href="#"><img class="iconLarge" src="<%=srcImage%>" /> </a>
											</div>
											<div class="column width70">
												<h2><%=Utility.strNN(itemGroupName)%></h2>
												<p style="word-wrap: break-word;"><%=Utility.strNN(itemDet.getProduct().getLongDesc())%></p>
											</div>
										</div>		
										<% } %>
										<!-- -------------------------------- -->								

								     <%  
										//if(orderby==Constants.ORDER_BY_CATEGORY) { 
//										if( index == 0 || !prevCategory.equals(item.getCategoryPath()) ) {
										if( index == 0  ) {
											if(index != 0 && index != (sCartItems.size())) { // End the table before starting new table.
												%>
													</tbody>
												</table>
												<%
											}
										%>
										
<!-- -------------------------------- -->										
										<div class="twoColBox">
											<% if (userShopForm.getAppUser().getUserAccount().isSupportsBudget()) { %>
												<div class="column width80">
												<% 
													String catePath = itemDet.getCategoryPathWithLinksForNewUI(request);
												%>
												  <h3><%= catePath%></h3>
												</div>
												<div class="column width20">
											      <h3>
											      <% if ( itemDet.getProduct().getCostCenterId() > 0 ) {  %>
														<%= itemDet.getProduct().getCostCenterName() %>
													<% } %>
											      
											      </h3>
							
												</div>
											<% }else{ %>
												<div>
												<% 
													String catePath = itemDet.getCategoryPathWithLinksForNewUI(request);
												%>
												  <h3><%= catePath%></h3>
												</div>
											<%} %>
										</div>
 <!-- -------------------------------- -->						
										 <table class="order">
				                                    <app:productCatalogHeader
												        viewOptionEditCartItems="<%=editCartItems%>"
												        viewOptionQuickOrderView="<%=quickOrderView%>"
												        viewOptionAddToCartList="<%=addToCartListView%>"
												        viewOptionOrderGuide="false"
												        viewOptionShoppingCart="<%=shoppingCartView%>"
												        viewOptionCheckout="<%=checkoutView%>"
												        viewOptionCatalog="true"
												        viewOptionGroupItemDetail="true"/>
				                             <tbody>
											 <%
												 prevCategory = item.getCategoryPath();	
												}
											%>
											<%String operation = ((item.getProduct().isItemGroup()) ? "groupItem" : "item") ;%>
											<%String itemLink = "../../userportal/esw/shopping.do?operation="+operation+"&source=catalog&itemId="+itemId  ;%>
							
										<%if ( item.getProduct().hasItemGroup()) { %>
											<tr>
	                                 		<app:displayProductCatalogItems
										        viewOptionEditCartItems="<%=editCartItems%>"
										        viewOptionQuickOrderView="<%=quickOrderView%>"
										        viewOptionAddToCartList="<%=addToCartListView%>"
										        viewOptionOrderGuide="false"
										        viewOptionShoppingCart="<%=shoppingCartView%>"
										        viewOptionCheckout="<%=checkoutView%>"
										        viewOptionCatalog="true"
										        viewOptionGroupItemDetail="true"
										        name="item"
										        link="<%=itemLink%>"
										        index="<%=index%>"
										        inputNameQuantity="<%=quantityEl%>"
										        formName="userShopForm"/>
	                                 		</tr>
                                 		<% } %>		
										<%
											if( index==(sCartItems.size()-1) ) { 
											
										%>
                                 			</tbody>
                                 		</table>
										<% 
											 
										}  %>
                                      </logic:iterate>
                                      </logic:present>
                                          
                                     <logic:present name="userShopForm" property="cartItems">
	                                <bean:size id="size" name="userShopForm" property="cartItems"/>
	                                <logic:greaterThan name="size" value="0">
	                                <%
	                                	//STJ-4521 (handle browse only user)
	                                	if (!appUser.isBrowseOnly()) {
	                                %>
                                    <p class="clearfix">

	                                <a href="javascript:setFieldsAndSubmitForm('multiProductShoppingFormId','operationId','clearGroupItemQty')" class="blueBtnMed" onclick="return true"><span><app:storeMessage key="global.action.label.clearQuantities" /></span></a>
                                    
                     <% 
                                    	if (showCorporateOrder && isCorporateOrderOpen) {
					
				     %>			                                      
	                                <a href="javascript:setFieldsAndSubmitForm('multiProductShoppingFormId','operationId','groupItemAddCatalogItemsToCorporateOrder')" class="blueBtnMed" onclick="return true"><span><app:storeMessage key="global.action.label.addToCorporateOrder" /></span></a>
						
				   <%
					} else { 
				   %> 
                                        <a href="javascript:setFieldsAndSubmitForm('multiProductShoppingFormId','operationId','groupItemAddToCart')" class="blueBtnMed" >
	                                        <span><app:storeMessage key="global.action.label.addToShoppingCart" /></span>
                                        </a>
				   <%   }   %>	
                                        
                                       <a href="javascript:setFieldsAndSubmitForm('multiProductShoppingFormId','operationId','groupItemAddToExistingShoppingList')" class="blueBtnMed" >
                                       		 <span><app:storeMessage key="global.action.label.addToShoppingList" /></span>
                                       </a>
                                         <html:select styleClass="span" property="userShopForm.selectedShoppingListId" styleId="shoppingListsId2">
                                         <html:option value="select"><%=Utility.encodeForHTML(selectOption) %></html:option>
                                         <logic:present name="editOrderGuideForm" property="userOrderGuides">
	  							  	<logic:iterate id="userOrderGuide" property="userOrderGuides" name="editOrderGuideForm" indexId="index">
                                            <html:option value="<%=String.valueOf(((OrderGuideData)userOrderGuide).getOrderGuideId())%>">
                                            	<bean:write name="userOrderGuide" property="shortDesc"/>
                                            </html:option>
                                            </logic:iterate>
                                            </logic:present>
                                            <html:option value="newList"><%=Utility.encodeForHTML(createListOption) %></html:option>
                                        </html:select>
                                    
                                    </p>
									<%
	                                	}
									%>
                                 </logic:greaterThan>
                                 </logic:present>
                                    </html:form>
                                </div>
                            </div>
                            <div class="bottom clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
                        </div>
                        <!-- End Box -->
                    </div>
                </div>
                <!-- End Right Column -->
                <!-- Start Left Column - columns are reversed to allow expanding right column -->
		
		<div class="leftColumn">
		<table>
		<tr>
		<html:form styleId="panelForm" action="userportal/esw/shopping.do">
		    <html:hidden styleId="panelFormOperation" property="<%=Constants.PARAMETER_OPERATION%>"/>
		    <html:hidden styleId="panelSelected" property="panelProductCatalog" />
		    
		<td class="collapsePanel"> <%--left menu--%>
			<a href="javascript:movePanel('<%=Constants.COLLAPSE_PANEL%>')"> <img src="<%=request.getContextPath()%>/esw/images/collapseMenu.png"/></a>		
		</td>
		<td class="expandPanel hide"> <%--NO left menu--%>
			<a href="javascript:movePanel('<%=Constants.EXPAND_PANEL%>')"> <img src="<%=request.getContextPath()%>/esw/images/expandMenu.png" /></a>
		</td>
		</html:form>
		<td>
			<div class="collapsePanelLeftCol">
				<div class="whiteBox clearfix">
			
					<div class="whiteBoxTop">&nbsp;</div>
						
					<div class="whiteBoxBg">
						
						<html:form styleId="categoryForm" action="userportal/esw/shopping.do">
						<html:hidden property="<%=Constants.PARAMETER_OPERATION%>"
												       value="<%=Constants.PARAMETER_OPERATION_VALUE_SHOP_BY_CATALOG%>"/>
						<html:hidden property="catalogItemKey" styleId="catalogItemKey"/>
							       
						<h3 class="clearfix"><app:storeMessage key="productCatalog.label" /></h3>
						<hr />
						<ul>
							<app:productCatalogMenu
								       menuItems="<%=userShopForm.getCatalogMenu()%>"
								       categoryToItemMap="<%=userShopForm.getCatalogMenuCategoryToItemMap()%>"
								       noLinkCssStyle="noLink"
								       parentCssStyle="expand"
								       rootUrl="<%=shoppingCatalogLink.toString()%>"
								       menuItemParameterName="<%=Constants.PARAMETER_CATALOG_ITEM_KEY%>"
								       displayLeftMenu="true" />
						</ul>
						</html:form>
					</div>
					<div class="whiteBoxBottom">&nbsp;</div>
				</div>
						
			</div>
		</td>
		</tr>
		</table>
					</div>
		
			</div>
	    
			<!-- End Left Column -->
			</div>
		</div>

		
		