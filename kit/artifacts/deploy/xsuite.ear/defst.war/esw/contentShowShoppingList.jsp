<%@page import="java.util.Iterator"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.cleanwise.view.utils.ShopTool"%>
<%@page import="com.cleanwise.view.forms.EditOrderGuideForm"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="com.cleanwise.service.api.util.SessionDataUtil"%>
<%@page import="java.util.List"%>
<%@page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@page import="com.cleanwise.service.api.util.Utility"%>
<%@page import="com.cleanwise.view.forms.UserShopForm"%>
<%@page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
<%@page import="com.cleanwise.view.utils.Constants"%>
<%@page import="com.cleanwise.view.utils.ClwCustomizer,com.cleanwise.service.api.value.*"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>
<bean:define id="shoppingForm" name="esw.ShoppingForm"  type="com.espendwise.view.forms.esw.ShoppingForm"/>
<script type="text/javascript" src="../../externals/dojo_1.1.0/dojo/dojo.js" djConfig="parseOnLoad: true, isDebug: false">
</script>
<style>
   .icon { 
     width  : 50px; 
     height : 50px; 
     }
</style>
<% 
String productSearchLink = "userportal/esw/shopping.do";
String PRODUCT_SEARCH_VALUE_DISPLAY_SIZE = "50";
CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(
        Constants.APP_USER);
SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
EditOrderGuideForm editOrderGuideForm = sessionDataUtil.getEditOrderGuideForm();
pageContext.setAttribute("editOrderGuideForm",editOrderGuideForm);
session.setAttribute("EDIT_ORDER_GUIDE_FORM",editOrderGuideForm);
UserShopForm userShopForm = sessionDataUtil.getUserShopForm();
pageContext.setAttribute("userShopForm",userShopForm);
List sCartItems = null;
if(editOrderGuideForm!=null)
 sCartItems = editOrderGuideForm.getItems();
String prevCategory="";
boolean showCorporateOrder = Utility.getSessionDataUtil(request).isConfiguredForCorporateOrders();
boolean isCorporateOrderOpen = ShopTool.hasInventoryCartAccessOpen(request);
//STJ - 4985
boolean isUserShoppingList = true;
String selectedShoppingListId= String.valueOf(editOrderGuideForm.getOrderGuideId());
ArrayList templateShoppingListIds = userShopForm.getTemplateOrderGuideIds();
Iterator templateShoppingListsIterator = templateShoppingListIds.iterator();
while(templateShoppingListsIterator.hasNext()){
	String shoppingListId = (String)templateShoppingListsIterator.next();
	if(selectedShoppingListId.equals(shoppingListId)){
		isUserShoppingList = false;
	}
}
%>
<%
    boolean
      quickOrderView = false,
      shoppingCartView = false,
      checkoutView = false,
      editCartItems = false ,
      f_showSelectCol = false,
      addToCartListView = false;
%>
	<script language="JavaScript">
	  <!--
	 $(document).ready(function(){	
		showMenu('<%=sessionDataUtil.getPanelShoppingList()%>');		
	 });
	  function viewExcelFormat() {
          var loc = "shopping.do?operation=excelPrintPers";
          prtwin = window.open(loc,"excel_format",
            "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500, width=775,left=100,top=165");
          prtwin.focus();
        }

		 function submitFormForOGSelect(operationValue,ogId) {
			    document.getElementById('setOperation').value=operationValue;
			    document.getElementById('ogId').value=ogId;
			    document.getElementById('shoppingListForm').submit();
	      }
		  function editAndSubmitForm(operationValue,ogId) {
			    document.getElementById('setOperation').value=operationValue;
			    document.getElementById('iogId').value=ogId;
			    document.getElementById('shoppingListForm').submit();
		  }
	  function submitFormForRemoving(operationValue,operationFieldId,formId,itemId) {
		  document.getElementById('itemId').value=itemId;
		    document.getElementById(operationFieldId).value=operationValue;
		    document.getElementById(formId).submit();
	  }
	  function submitFormForNewOrderGuide() {
		    document.getElementById('setItemsOperation').value='saveUserOrderGuide';
		    document.getElementById('ogName').value=document.getElementById('newOrderGuideName').value;
		    document.getElementById('orderGuideItems').submit();
	  }
	  
	  function resetOGName() {
  		document.getElementById('newOrderGuideName').value="";
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
	
	function submitEnterForNewOrderGuide(e)
    {
        var keycode;
        if (window.event) keycode = window.event.keyCode;
        else if (e) keycode = e.which;
        else return true;

        if (keycode == 13) {
            submitFormForNewOrderGuide();
            return false;
        } else{
            return true;
        }
    }
	
		-->
		</script>
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
								CleanwiseUser user = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
								SiteData location = user.getSite();
								if (location != null) {
						%>
	                          <div class="searchBoxWrapper">
                            <div class="searchBox">
                                <table>
                                    <tr>
                                        <td class="title">
                                            <h3>
                                            	<app:storeMessage key="product.search.label.productSearch" />
                                            </h3>
                                        </td>
				                    	<html:form styleId="productSearchForm" action="<%=productSearchLink%>">
				                    		<html:hidden property="<%=Constants.PARAMETER_OPERATION%>"
				                    			value="<%=Constants.PARAMETER_OPERATION_VALUE_SEARCH_SHOPPING_LSIT_PRODUCTS%>"/>
				                    			  <logic:present name="editOrderGuideForm" property="items">
	                                    			   <logic:iterate id="item" name="editOrderGuideForm"  property="items"
										               	offset="0" indexId="index"
										               	type="com.cleanwise.service.api.value.ShoppingCartItemData">
										               <bean:define id="itemId" name="item" property="product.productId" type="java.lang.Integer"/>
										               <bean:define id="quantityEl" value="<%=\"editOrderGuideForm.quantityElement[\"+index+\"]\"%>"/>
										               	<bean:define id="itemIdsEl" value="<%=\"editOrderGuideForm.itemIdsElement[\"+index+\"]\"%>"/>
										               	 <bean:define id="orderNumber" name="item" property="orderNumber"/>
										         <bean:define id="orderNumbersEl" value="<%=\"editOrderGuideForm.orderNumbersElement[\"+index+\"]\"%>"/>
								     <html:hidden name="esw.ShoppingForm" property="<%=quantityEl%>" value="<%=item.getQuantityString()%>"/>
								      <html:hidden name="esw.ShoppingForm" property="<%=itemIdsEl%>" value="<%=\"\"+itemId%>"/>
								      <html:hidden name="esw.ShoppingForm" property="<%=orderNumbersEl%>" value="<%=\"\"+orderNumber%>"/>
								     
										               </logic:iterate>
										            </logic:present>
	                                        <td class="search">
	                                            <div class="inputWrapper">
						                        	<html:text property="productSearchValue"/>
	                                            </div>
	                                        </td>
	                                        <td class="limit">
					                        	<html:select property="productSearchField">
					                            	<html:optionsCollection name="<%=Constants.ACTION_FORM%>"
					                            		property="productSearchFieldChoices" label="label" value="value"/>
					                        	</html:select>
	                                        </td>
	                                        <td>
												<label>
													<html:checkbox property ="greenCertified" styleClass="chkBox right" /> 
													 <app:storeMessage key="product.search.label.greenCertified" />
												</label>
											</td>
	                                    </html:form>
                                        <td class="button">
					                        <html:link styleClass="btn greyBtn" href="javascript:submitForm('productSearchForm')">
					                        	<span>
						                        	<app:storeMessage key="global.action.label.search" />
						                        </span>
					                        </html:link>
                                        </td>
                                    </tr>
                                </table>
                            </div>
                        </div>
                        <% }  %>
	                        <!-- Start Box -->
	                        <div class="boxWrapper">
	                            <div class="top clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
	                            <div class="content clearfix">
	                                <div class="left clearfix">
	                                 <h1><%= Utility.encodeForHTML(editOrderGuideForm.getShortDesc())%></h1>
	                                 <% 
	                                    	StringBuffer saveBtnClass = new StringBuffer("blueBtnMed");
	                                 		StringBuilder onClickReturnType = new StringBuilder(20);
	                                    	if(!isUserShoppingList){
	                                    		saveBtnClass.append(" blueBtnMedDisabled");
	                                    		onClickReturnType.append(" return false; ");
	                                    	} else {
	                                    		onClickReturnType.append(" return true; ");
	                                    	}
	                                 %>
	                                  <logic:present name="editOrderGuideForm" property="items">
	                                <bean:size id="size" name="editOrderGuideForm" property="items"/>
	                                <logic:greaterThan name="size" value="0">
	                                    <p class="right">
	                                        <a href="javascript:viewExcelFormat()" class="btn rightMargin"><span>
													<app:storeMessage key="global.action.label.exportExcelSheet" /></span></a> 
	                                        <a href="#" class="btn rightMargin" onclick="window.print();return false;"><span>
	                                        <app:storeMessage key="global.action.label.printPage" /></span></a>
	                                    </p>
	                                    <hr/>
	                                <%
	                                	//STJ-4521 (handle browse only user)
	                                	if (!appUser.isBrowseOnly()) {
	                                %>

	                                    <p class="left clear clearfix">
	                                  
					   	<a href="javascript:setFieldsAndSubmitForm('orderGuideItems','setItemsOperation','updateQuantity')" class="<%= saveBtnClass.toString()%>" onclick ="<%=onClickReturnType %>" >
					   		<span><app:storeMessage key="global.action.label.update" /></span>
					   	</a>
					   
					    <a href="javascript:setFieldsAndSubmitForm('orderGuideItems','setItemsOperation','removeSelectedItems')" class="<%= saveBtnClass.toString()%>" onclick ="<%=onClickReturnType %>" >
                       		<span><app:storeMessage key="admin.button.removeSelected" /></span>
                       	</a>
                       	
                      <% if (showCorporateOrder && isCorporateOrderOpen) {
					
						 %>		
						                                      
						<a href="javascript:setFieldsAndSubmitForm('orderGuideItems','setItemsOperation','addShoppingListItemsToCorporateOrder')" class="blueBtnMed " onclick="return true"><span><app:storeMessage key="global.action.label.addToCorporateOrder" /></span></a>
						<%
						} else { 
						%> 
						
						  <a href="javascript:setFieldsAndSubmitForm('orderGuideItems','setItemsOperation','addToCartFromOrderGuide')" class="blueBtnMed"><span><app:storeMessage key="global.action.label.addToShoppingCart" /></span></a>
						<%   }   %>
									
	                                    </p>
	                     <%if(isUserShoppingList){ %>               
	                      <p class="clearfix right">
		                      <span class="selectNav"> 
			                      <app:storeMessage key="shoppinglist.og.label.select" />
			                      &nbsp;&nbsp; 
			                      <a class="all" href="#"><app:storeMessage key="global.label.all" /></a> <span>|</span> 
			                      <a class="none" href="#"><app:storeMessage key="global.label.none" /></a>
		                      </span>                                        
						  </p>
	                      <%} %>               <hr/>

	                      <% } %>
	                      
	                                    </logic:greaterThan>
	                                     </logic:present>
	                                   
	                                     <html:form styleId="orderGuideItems" action="userportal/esw/shopping.do">
	                                     <html:hidden property="<%=Constants.PARAMETER_OPERATION%>" value="orderGuideSelect" styleId="setItemsOperation"/>
	                                      <html:hidden property="editOrderGuideForm.shortDesc" styleId="ogName" value="<%= editOrderGuideForm.getShortDesc()%>"/>
	                                      <html:hidden property="itemIdToRemove" styleId="itemId" value=""/>
	                                      <logic:present name="editOrderGuideForm" property="items">
	                                    <logic:iterate id="item" name="editOrderGuideForm"  property="items"
										               offset="0" indexId="index"
										               type="com.cleanwise.service.api.value.ShoppingCartItemData">
										        <bean:define id="orderNumber" name="item" property="orderNumber"/>
										         <bean:define id="orderNumbersEl" value="<%=\"orderNumbersElement[\"+index+\"]\"%>"/>
												<bean:define id="itemId" name="item" property="product.productId" type="java.lang.Integer"/>
												<bean:define id="quantityEl" value="<%=\"quantityElement[\"+index+\"]\"%>"/>
												<bean:define id="selectEl" value="<%=\"selectElement[\"+index+\"]\"%>"/>
												<bean:define id="itemIdsEl" value="<%=\"itemIdsElement[\"+index+\"]\"%>"/>
												<%  
													if( index==0 || !prevCategory.equals(item.getCategoryPathForNewUI()) ) {
	
													if(index!=0 || index!=(sCartItems.size()-1)) { // End the table before starting new table.
												%>
														</tbody>
														</table>
												<%
													}
												%>
									    <div class="twoColBox">
										<% if (user.getUserAccount().isSupportsBudget()) { %>
												<div class="column width80">
												  <h3><app:storeMessage key="productCatalog.label.allProducts" /> > <bean:write name="item" property="categoryPathForNewUI"/> </h3>
												</div>
												<div class="column width20">
											      <h3>
											      <% if ( item.getProduct().getCostCenterId() > 0 ) {  %>
														<%= item.getProduct().getCostCenterName() %>
													<% } %>
											      
											      </h3>
							
												</div>
											
									    <% }else{%>
											<div>
												  <h3><app:storeMessage key="productCatalog.label.allProducts" /> > <bean:write name="item" property="categoryPathForNewUI"/> </h3>
												</div>
									    <%}%>
									    </div>
	                                        <table class="order">
	                                           <app:productCatalogHeader
												        viewOptionEditCartItems="false"
												        viewOptionQuickOrderView="false"
												        viewOptionAddToCartList="false"
												        viewOptionOrderGuide="false"
												        viewOptionEditOrderGuide="true"
												        viewOptionShoppingCart="false"
												        viewOptionCheckout="false"
												        viewOptionInventoryList="false"
														userShoppingList="<%= isUserShoppingList%>"/>
	                                            <tbody>
	                                            <%
													 prevCategory = item.getCategoryPathForNewUI();
													}
												%>
											   <%String operation = ((item.getProduct().isItemGroup()) ? "groupItem" : "item") ;%>	
	                                           <%String itemLink = "../../userportal/esw/shopping.do?operation="+operation+"&source=catalog&itemId="+itemId;%>
	                							<bean:define id="price" name="item" property="price"/>
										<%if (  isUserShoppingList ||
											  (!isUserShoppingList && !item.getProduct().hasItemGroup())) { %>
	                 
	                                            	<tr>
                                 					<app:displayProductCatalogItems
												        viewOptionEditCartItems="true"
												        viewOptionQuickOrderView="false"
												        viewOptionAddToCartList="false"
												        viewOptionOrderGuide="false"
												        viewOptionEditOrderGuide="true"
												        viewOptionShoppingCart="<%=shoppingCartView%>"
												        viewOptionCheckout="<%=checkoutView%>"
												        viewOptionCatalog="false"
												        name="item"
												        link="<%=itemLink%>"
												        index="<%=index%>"
												        inputNameQuantity="<%=quantityEl%>"
												        formName="editOrderGuideForm"
												        userShoppingList="<%= isUserShoppingList%>"/>
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
	                                        </html:form>
	                                    
	                                      <logic:present name="editOrderGuideForm" property="items">
										<logic:greaterThan name="size" value="0">
	                                <%
	                                	//STJ-4521 (handle browse only user)
	                                	if (!appUser.isBrowseOnly()) {
	                                %>

	                                    <p class="left clearfix">
	                                    
                      <a href="javascript:setFieldsAndSubmitForm('orderGuideItems','setItemsOperation','updateQuantity')" class="<%= saveBtnClass.toString()%>" onclick ="<%=onClickReturnType %>" >
					   		<span><app:storeMessage key="global.action.label.update" /></span>
					   	</a>
					   
					   <a href="javascript:setFieldsAndSubmitForm('orderGuideItems','setItemsOperation','removeSelectedItems')" class="<%= saveBtnClass.toString()%>" onclick ="<%=onClickReturnType %>" >
                       		<span><app:storeMessage key="admin.button.removeSelected" /></span>
                       	</a>
                       	
                      <% if (showCorporateOrder && isCorporateOrderOpen) {
					
						 %>		
							                                      
						<a href="javascript:setFieldsAndSubmitForm('orderGuideItems','setItemsOperation','addShoppingListItemsToCorporateOrder')" class="blueBtnMed " onclick="return true"><span><app:storeMessage key="global.action.label.addToCorporateOrder" /></span></a>
						<%
						} else { 
						%> 
						
						  <a href="javascript:setFieldsAndSubmitForm('orderGuideItems','setItemsOperation','addToCartFromOrderGuide')" class="blueBtnMed"><span><app:storeMessage key="global.action.label.addToShoppingCart" /></span></a>
						<%   }   %>
	                                    </p>
	                  <%if(isUserShoppingList){ %>                   
	                   <p class="clearfix right">
		                      <span class="selectNav"> 
			                      <app:storeMessage key="shoppinglist.og.label.select" />
			                      &nbsp;&nbsp; 
			                      <a class="all" href="#"><app:storeMessage key="global.label.all" /></a> <span>|</span> 
			                      <a class="none" href="#"><app:storeMessage key="global.label.none" /></a>
		                      </span>                                        
						  </p>
						<%} %>	
						<% } // is browse only %>
	                                    </logic:greaterThan>
	                                    </logic:present>
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
		        <html:hidden styleId="panelSelected" property="panelShoppingList" />
		    
			<td class="collapsePanel">
				<a href="javascript:movePanel('<%=Constants.COLLAPSE_PANEL%>')"> <img src="<%=request.getContextPath()%>/esw/images/collapseMenu.png"/></a>		
			</td>
			<td class="expandPanel hide">
				<a href="javascript:movePanel('<%=Constants.EXPAND_PANEL%>')"> <img src="<%=request.getContextPath()%>/esw/images/expandMenu.png" /></a>
			</td>
			</html:form>
			<td>
			<div class="collapsePanelLeftCol">
	                    <div class="whiteBox shoppingLists clearfix">
	                        <div class="whiteBoxTop">&nbsp;</div>
	                        <div class="whiteBoxBg clearfix">
	                         <html:form styleId="shoppingListForm" action="userportal/esw/shopping.do" onsubmit="return false;">
	                             <html:hidden property="<%=Constants.PARAMETER_OPERATION%>" value="orderGuideSelect" styleId="setOperation"/>
		       					 <html:hidden property="editOrderGuideForm.orderGuideId" styleId="ogId" value="0"/>
		       					 <html:hidden property="editOrderGuideForm.inputOrderGuideId" styleId="iogId" value="0"/>
	                       <% 
	                        	if((editOrderGuideForm.getUserOrderGuides()!=null && editOrderGuideForm.getUserOrderGuides().size()>0) 
	                        			|| (userShopForm.getTemplateOrderGuides()!=null && userShopForm.getTemplateOrderGuides().size()>0))
	                        	{
	                        
	                        %>
	                            <h3 class="clearfix"><app:storeMessage key="shoppinglist.label" /></h3>
	                        <%  }   %>
	                             <hr />
	                              <%
	                                SessionDataUtil sessionData = Utility.getSessionDataUtil(request);
		                            Map<Integer,Integer> templateShoppingListItemsCount = sessionData.getTemplateShoppingListIds();
	                         		int countOfItemsInTemplateShopList = 0;
	                            %>
	                             
	                            <ul>
									<logic:present name="userShopForm" property="templateOrderGuides">
	  							  <logic:iterate id="templateOrderGuide" property="templateOrderGuides" name="userShopForm" indexId="index">
	  							                           
	                             	<%
	                             	String selectedListClass = "";
	                             		if(((OrderGuideData)templateOrderGuide).getOrderGuideId() == shoppingForm.getSelectedShoppingList()){
	                             			selectedListClass = "selected";
	                             		}
	                             		if(templateShoppingListItemsCount != null && !templateShoppingListItemsCount.isEmpty()){
	                             			countOfItemsInTemplateShopList = (Integer)templateShoppingListItemsCount.get(((OrderGuideData)templateOrderGuide).getOrderGuideId());
	                             		}	
	                             		 
	                             	%>
	                                <li class="<%= selectedListClass%>">
	                                <a href="javascript:submitFormForOGSelect('orderGuideSelect','<%= ((OrderGuideData)templateOrderGuide).getOrderGuideId()%>')">
	                                	<bean:write name="templateOrderGuide" property="shortDesc"/>
	                                	 <span>(<%= countOfItemsInTemplateShopList%>)</span>
	                                	</a>
	                                </li>
	                              </logic:iterate>
	                              <hr class="smallMargin" /> 
	                              </logic:present>	                            	
                           
	                            <%
		                            Map<Integer,Integer> userShoppingListsItemsCount = sessionData.getUserShoppingListIds();
	                         		int countOfItemsInUserShopList = 0;
	                            %>
	                             <logic:present name="editOrderGuideForm" property="userOrderGuides">
	  							  <logic:iterate id="userOrderGuide" property="userOrderGuides" name="editOrderGuideForm" indexId="index">
	  							  <input type="hidden" name="userOrdGuideIds" value="<%= ((OrderGuideData)userOrderGuide).getOrderGuideId()%>" />
	                                <input type="hidden" name="userOrdGuideNames" value="<%=((OrderGuideData)userOrderGuide).getShortDesc() %>"/>                          
	                             	<%
	                             	String selectedListClass = "";
	                             		if(((OrderGuideData)userOrderGuide).getOrderGuideId() == shoppingForm.getSelectedShoppingList()){
	                             			selectedListClass = "selected";
	                             		}
	                             		if(userShoppingListsItemsCount != null){
	                             			countOfItemsInUserShopList = (Integer)userShoppingListsItemsCount.get(((OrderGuideData)userOrderGuide).getOrderGuideId());
	                             		}	 
	                             	%>
	                                <li class="<%= selectedListClass%>">
	                                <a href="javascript:submitFormForOGSelect('orderGuideSelect','<%= ((OrderGuideData)userOrderGuide).getOrderGuideId()%>')">
	                                	<bean:write name="userOrderGuide" property="shortDesc"/>
	                                 <span>(<%= countOfItemsInUserShopList%>)</span>
	                                </a>
	                                </li>
	                                <li class="edit"><html:text property="orderGuideNames" maxlength="30" value="<%= ((OrderGuideData)userOrderGuide).getShortDesc()%>" style="*float:left;"/> 
	                                <a href="javascript:editAndSubmitForm('deleteOrderGuide','<%= ((OrderGuideData)userOrderGuide).getOrderGuideId()%>')" class="btn">
	                                <span><app:storeMessage key="global.action.label.delete" /></span></a></li>
	                                
	                              </logic:iterate>
	                               <hr class="smallMargin" /> 
	                              </logic:present>
	                            </ul>
	                           
	                             <logic:present name="editOrderGuideForm" property="userOrderGuides">
	                              <bean:size id="size" name="editOrderGuideForm" property="userOrderGuides"/>
	                                <logic:greaterThan name="size" value="0">
	                                
	                            <div class="addNewList hide clearfix">
	                            
	                                <input type="text" name="orderGuideName" id="newOrderGuideName" maxlength="30" onkeypress="return submitEnterForNewOrderGuide(event)"/>
	                                <a href="javascript:submitFormForNewOrderGuide()" class="btn rightMargin blueBtnMed leftBtn"><span><app:storeMessage key="global.action.label.save" /></span></a>
	                                <a onclick="javascript:resetOGName()" class="btn greyBtnBg leftBtn cancel"><span><app:storeMessage key="global.action.label.cancel" /></span></a>
	                            </div>
	                            <a href="javascript:setFieldsAndSubmitForm('shoppingListForm','setOperation','renameOrderGuide')" class="blueBtnMed saveBtn hide"><span><app:storeMessage key="global.action.label.save" /></span></a>
	                            <a onclick="javascript:submitFormForOGSelect('orderGuideSelect','<%= shoppingForm.getSelectedShoppingList()%>')" class="btn greyBtnBg cancelBtn hide">
		                            <span>
		                            	<app:storeMessage key="global.action.label.cancel" />
		                            </span>
	                            </a>
	                            <a href="#" class="btn leftBtn newListBtn"><span><app:storeMessage key="global.action.label.create" /></span></a>
	                            <a href="#" class="btn leftBtn leftMargin topMargin editBtn">
	                            <span><app:storeMessage key="global.action.label.edit" /></span>
	                            </a>	                            
	                            </logic:greaterThan>
	                            </logic:present>
	                            
	                             <%
	                                	if((editOrderGuideForm.getUserOrderGuides()== null || editOrderGuideForm.getUserOrderGuides().size()==0)
	                                	   && (userShopForm.getTemplateOrderGuides()!=null && userShopForm.getTemplateOrderGuides().size()>0))
	                                	{
	                                %>
	                                <div class="addNewList hide clearfix">
	                                <input type="text" name="orderGuideName" id="newOrderGuideName" maxlength="30" onkeypress="return submitEnterForNewOrderGuide(event)"/>
	                                <a href="javascript:submitFormForNewOrderGuide()" class="btn rightMargin blueBtnMed leftBtn"><span><app:storeMessage key="global.action.label.save" /></span></a>
	                                <a onclick="javascript:submitFormForOGSelect('orderGuideSelect','<%= shoppingForm.getSelectedShoppingList()%>')" class="btn greyBtnBg leftBtn cancel">
	                                	<span><app:storeMessage key="global.action.label.cancel" /></span>
	                                </a>
	                            </div>
	                           
	                            <a href="#" class="btn leftBtn newListBtn"><span><app:storeMessage key="global.action.label.create" /></span></a>
	                           
	                            <% }  %>
	                                
	                                
	                            
	                              </html:form>
	                        </div>
	                        <div class="whiteBoxBottom">&nbsp;</div>
	                    </div>
	                </div>
			</td>
			</tr>
			</table>
	                </div>
	                <!-- End Left Column -->
	            </div>
	        </div>
	       
	  