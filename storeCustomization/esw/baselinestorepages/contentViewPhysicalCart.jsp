<%@page import="com.cleanwise.service.api.util.RefCodeNames"%>
<%@page import="com.cleanwise.view.utils.Constants"%>
<%@page import="com.cleanwise.view.forms.ShoppingCartForm"%>
<%@page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@page import="com.cleanwise.view.utils.ShopTool"%>
<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<bean:define id="myForm" name="esw.ShoppingForm"  type="com.espendwise.view.forms.esw.ShoppingForm"/> 

<%
	String viewPhysicalCart = "/userportal/esw/shopping.do";	
	ShoppingCartForm shoppingCartForm = myForm.getShoppingCartForm();
%>

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
<div class="singleColumn clearfix" id="contentWrapper">
    <div id="content">
	<!-- Start Box -->
	<div class="boxWrapper squareCorners smallMargin firstBox">                                           

	<html:form styleId="shoppingFormId" action="<%=viewPhysicalCart %>">
  	<html:hidden property="operation" styleId="operationId" />
	
	<div class="content">
		<div class="left clearfix">
		<%
	    if (ShopTool.isPhysicalCartAvailable(request)) {
		%>
            <h1 class="main"><app:storeMessage  key="shoppingCart.text.physicalCart" /></h1>	
            <logic:notEmpty name="esw.ShoppingForm" property="shoppingCartForm.cartItems">                         		  
            <a class="blueBtnLargeExt" 
               onclick="javascript:setFieldsAndSubmitForm('shoppingFormId','operationId','<%=Constants.PARAMETER_OPERATION_VALUE_SAVE_PHYSICAL_CART  %>')" >
               <span><app:storeMessage  key="shoppingCart.text.update" /></span>
            </a> 
            </logic:notEmpty>
		<%
		}									
		%>
        </div>
    </div>
    <div class="bottom clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
    </div>
    
    <%
    if (ShopTool.isUsedPhysicalInventoryAlgorithm(request) && ShopTool.isPhysicalCartAvailable(request)) {
    %>
       <logic:notEmpty name="esw.ShoppingForm" property="shoppingCartForm.cartItems">							   
		<!-- show PAR items section only if inventory items exist -->
		<%if(shoppingCartForm.getFormCartIL()!=null && 
					(shoppingCartForm.getFormCartIL().getItems()!=null && shoppingCartForm.getFormCartIL().getItems().size()>0)){%>
            <div class="boxWrapper squareCorners smallMargin">
                <div class="top clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
                <div class="content">
                    <div class="left clearfix">
<!-- Begin: Inventory Shopping Cart Items -->
                        <jsp:include flush='true' page='<%=ClwCustomizer.getStoreFilePath(request,Constants.PORTAL_ESW,"shoppingCartItems.jsp")%>' >
		              			<jsp:param name="viewType" value="PAR"/>
				            </jsp:include>
<!-- End: Inventory Shopping Cart Items-->                           
					  <logic:notEmpty name="esw.ShoppingForm" property="shoppingCartForm.cartItems">                                
                        <a class="blueBtnLargeExt" 
                           onclick="javascript:setFieldsAndSubmitForm('shoppingFormId','operationId','<%=Constants.PARAMETER_OPERATION_VALUE_SAVE_PHYSICAL_CART  %>')" >
                           <span><app:storeMessage  key="shoppingCart.text.update" /></span>
                        </a> 
                    </logic:notEmpty>
                    </div>
                </div>				 
                <div class="bottom clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>          
			 </div>
		<%}%>                
        </logic:notEmpty>
    <%
	}									
    %>
    </html:form>     
    <!-- End Box -->
    </div>
</div>