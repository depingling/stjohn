<%@page import="java.util.Formatter"%>
<%@page import="java.text.DateFormat"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>
<%@ page import="com.cleanwise.view.utils.SessionTool" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.service.api.value.AddressData" %>
<%@ page import="com.cleanwise.service.api.value.OrderAddressData" %>
<%@ page import="com.cleanwise.service.api.value.ShoppingCartData" %>
<%@ page import="com.cleanwise.service.api.value.OrderStatusDescData" %>
<%@ page import="com.cleanwise.service.api.value.OrderData"%>
<%@ page import="com.cleanwise.service.api.value.AccountData"%>
<%@ page import="com.cleanwise.service.api.value.SiteData"%>
<%@ page import="com.cleanwise.service.api.value.OrderFreightData"%>
<%@ page import="com.cleanwise.service.api.value.OrderAddOnChargeData"%>
<%@ page import="com.cleanwise.service.api.value.OrderItemActionData"%>
<%@ page import="com.cleanwise.service.api.value.OrderItemDescData"%>
<%@ page import="com.cleanwise.service.api.value.PropertyData"%>
<%@ page import="com.cleanwise.service.api.value.OrderPropertyData"%>
<%@ page import="com.cleanwise.service.api.value.OrderPropertyDataVector"%>
<%@ page import="com.cleanwise.service.api.value.IdVector"%>
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
        
<%@page import="com.cleanwise.view.i18n.ClwI18nUtil"%>

<script type="text/javascript" src="../../externals/dojo_1.1.0/dojo/dojo.js" djConfig="parseOnLoad: true, isDebug: false">
</script>

<%
String addOrderCommentLink = "userportal/esw/showOrderDetail.do";
final int COMMENT_MAX_DISPLAY_LENGTH = 120;
    final String ELLIPSIS = "...";
%>
<script language="JavaScript1.2">
<!--

 function actionSubmit(actionDef, action) {
    var actions = document.getElementsByName('action');
    alert("actionDef: " + actionDef + "     actions.length:  " + actions.length);
    for(ii = 0; ii < actions.length; ii++) {
        alert("value: " + actions[ii].value);
        if (actions[ii].value ==  actionDef) {
            //alert("action: " + action);
            actions[ii].value = action;
            actions[ii].form.submit();
        break;
        }
    }
    return false;
 }

//-->

function saveOrderDetailInfo() {
	dojo.byId("orderFormOperation").value = '<%=Constants.PARAMETER_OPERATION_VALUE_UPDATE_ORDER_DETAIL%>';
	submitForm('orderForm');
}

function submitForm(operation) {
	var frm = document.forms['orderForm'];
	if (frm) {
		frm.elements['<%=Constants.PARAMETER_OPERATION%>'].value = operation;
		frm.submit();
	}
}

function saveOrderComment(){
	dojo.byId("commentFormOperation").value  = 'addComment';
	dojo.byId("commentFormComment").value = dojo.byId("commentText").value;

	//post the comment form
	dojo.xhrPost( { // 
        url: "<%=request.getContextPath()%>/<%=addOrderCommentLink%>",
        form: dojo.byId("commentForm"),
        handleAs: "<%=Constants.CONTENT_TYPE_JSON%>",
        preventCache: true,
        timeout: 90000, // Time in milliseconds
        // The LOAD function will be called on a successful response.
        load: function(response, ioArgs) { //
    		if (response.errors) {
        		var errors = "";
        		var includeComma = false;
        		for (var i = 0; i < response.errors.length; i++) {
            		if (includeComma) {
                		errors = errors + ",";
            		}
            		includeComma = true;
            		errors = errors + response.errors[i].error;
        		}
        		alert(errors);
    		}
			else if (response.comments) {
        		//build the html content that will be used to replace the existing comments.  HTML formatting here
        		//must be kept in synch with HTML formatting below when initially rendering the comments.
                var content = "";
                for (var i = 0; i< response.comments.length; i++) {
                    content = content + "<p>";
                    content = content + response.comments[i].date;
					content = content + "&nbsp;";
					var comment = response.comments[i].comment;
					if (comment.length > <%=COMMENT_MAX_DISPLAY_LENGTH%>) {
						comment = comment.substring(0, <%=COMMENT_MAX_DISPLAY_LENGTH%>-3) + "<%=ELLIPSIS%>";
					}
					//escape the comment before appending it to the content
					var div = document.createElement('div');
					var text = document.createTextNode(comment);
					div.appendChild(text);
					content = content + div.innerHTML;
					content = content + "&nbsp;(<strong>";
					content = content + response.comments[i].author;
					content = content + "</strong>)";
	                content = content + "</p>";
                }
               
                //clear the comment text box
                dojo.byId("commentFormComment").value = "";
		
	       $('p.commentEntry').addClass('hide');
	       $('p.addComment').removeClass('hide');
	       
	       submitForm('<%=Constants.PARAMETER_OPERATION_VALUE_SHOW_ORDER%>');
            }
			else {
				alert('<%=ClwI18nUtil.getMessage(request, "error.unExpectedError")%>'); //
			}  
          return response; // 
        },
        // The ERROR function will be called in an error case.
        error: function(response, ioArgs) { // 
          console.error("HTTP status code: ", ioArgs.xhr.status); //
          alert('<%=ClwI18nUtil.getMessage(request, "error.unExpectedError")%>'); //  
          return response; // 
          }
        });
}

function reorder(operation, cartHasItems){
    if(cartHasItems=='true'){
	    var r = window.confirm('<%=ClwI18nUtil.getMessage(request, "dashboard.message.overwriteShoppingCart")%>');
		if(r == true){
		    submitForm(operation);
		}
    }else{
		submitForm(operation);
	}
}
</script>

<app:setLocaleAndImages/>

<% String saveOrderDetailLink = "userportal/esw/showOrderDetail.do"; %>
 
<% CleanwiseUser appUser = (CleanwiseUser)
     session.getAttribute(Constants.APP_USER); %>

<bean:define id="theForm" name="esw.OrdersForm" type="com.espendwise.view.forms.esw.OrdersForm"/>
<bean:define id="workflowRole" type="java.lang.String" name="ApplicationUser" property="user.workflowRoleCd"/>
                                 		
<%

OrderStatusDescData orderStatusDetail = theForm.getOrderOpDetailForm().getOrderStatusDetail();
  
AccountData accountD = SessionTool.getAccountData(request,orderStatusDetail.getOrderDetail().getAccountId());
 if(accountD == null){
     accountD = appUser.getUserAccount();
 }
SiteData siteD = SessionTool.getSiteData(request,orderStatusDetail.getOrderDetail().getSiteId());
SiteData currSiteD = appUser.getSite();
if(siteD == null){
    siteD = currSiteD;
}
boolean allowPoEntry = true;
 if(RefCodeNames.CUSTOMER_SYSTEM_APPROVAL_CD.PUNCH_OUT_INLIN_NON_E_ORD_ONLY.
   equals(accountD.getCustomerSystemApprovalCd())){
   allowPoEntry = false;
 }
 if(!accountD.isCustomerRequestPoAllowed()){
      allowPoEntry=false;
    }
 boolean usingBlanketPo = false;
 if(siteD!=null && siteD.getBlanketPoNum() != null && siteD.getBlanketPoNum().getBlanketPoNumId() != 0){
   usingBlanketPo = true;
   allowPoEntry = false;
 }
  String approvalDate = theForm.getApprovalDate();
  if (!Utility.isSet(approvalDate)) {
		 Date dNow = new Date();
		 String patternAD  = ClwI18nUtil.getDatePattern(request);
		 SimpleDateFormat formatterAD = new SimpleDateFormat(patternAD);
		 approvalDate = formatterAD.format(dNow);
  }
  //String realOrderStatus   = ShopTool.xlateStatus(orderStatusDetail, request);

  String orderLocale = orderStatusDetail.getOrderDetail().getLocaleCd();
  ClwI18nUtil clwI18n = ClwI18nUtil.getInstance(request,orderLocale,-1);
  SimpleDateFormat sdfInter = new SimpleDateFormat("yyyy-MM-dd");
  TimeZone timeZone =  Utility.getTimeZoneFromUserData(appUser.getSite());
  SimpleDateFormat sdfEntry = new SimpleDateFormat("yyyy-MM-dd" +" " + Constants.SIMPLE_TIME24_PATTERN + ".S");
  sdfEntry.setTimeZone(timeZone);
  SimpleDateFormat sdfComment = new SimpleDateFormat("yyyy-MM-dd k:mm");
  sdfComment.setTimeZone(timeZone);
  boolean approverFl = (workflowRole.indexOf("APPROVER") >= 0) ? true : false;
  boolean consolidatedOrderFl =
   RefCodeNames.ORDER_TYPE_CD.CONSOLIDATED.equals(orderStatusDetail.getOrderDetail().getOrderTypeCd());

  boolean toBeConsolidatedFl =
   RefCodeNames.ORDER_TYPE_CD.TO_BE_CONSOLIDATED.equals(orderStatusDetail.getOrderDetail().getOrderTypeCd());

  OrderData consolidatedToOrderD = orderStatusDetail.getConsolidatedOrder();

  boolean useCustPo = true;
  String requestPoNum = orderStatusDetail.getOrderDetail().getRequestPoNum();

  if(requestPoNum == null || requestPoNum.equals("") ||
  requestPoNum.equalsIgnoreCase("na") ||	requestPoNum.equalsIgnoreCase("n/a")){
    useCustPo = false;
  }
  boolean enterPo = false;
 if(appUser.getUserAccount().isCustomerRequestPoAllowed()){
	enterPo=true;
 }
%>


<% //Getting properly formatted Order Date: Begin
   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
   sdf.setTimeZone(timeZone);
   Date date = orderStatusDetail.getOrderDetail().getOriginalOrderDate();
   Date time = orderStatusDetail.getOrderDetail().getOriginalOrderTime();
   Date orderDate = Utility.getDateTime(date, time);

   //String formattedODate = ClwI18nUtil.formatDateInp(request, orderDate, timeZone.getID() );
   //String formattedODate = ClwI18nUtil.formatDate(request,orderDate,DateFormat.FULL);
   String formattedODate = Utility.encodeForHTML(ClwI18nUtil.formatDateInp(request, orderDate));
   formattedODate = formattedODate.trim();  
   //Getting  properly formatted Order Date: End
   
   
   //Getting Revised Order Date: Begin
   String formattedRevisedODate = "";
   Date revisedOrderDate = orderStatusDetail.getOrderDetail().getRevisedOrderDate();
   boolean equalDatesFl = false;
   if (revisedOrderDate != null && !revisedOrderDate.equals("")) {
      if (date.equals(revisedOrderDate)) {  
    	  equalDatesFl = true;    	  
      } else {
    	   //Convert Revised Order Date to the proper format if it is different from the Original Order Date
    	   Date revisedOrderTime = orderStatusDetail.getOrderDetail().getRevisedOrderTime();
    	   Date revisedOrderFullDate = Utility.getDateTime(revisedOrderDate, revisedOrderTime);
    	   //formattedRevisedODate = ClwI18nUtil.formatDateInp(request, revisedOrderFullDate, timeZone.getID());
    	   //formattedRevisedODate = ClwI18nUtil.formatDate(request,revisedOrderFullDate,DateFormat.FULL);
    	   formattedRevisedODate = Utility.encodeForHTML(ClwI18nUtil.formatDateInp(request, revisedOrderFullDate));
      }
   }
    //Getting Revised Order Date: End
%>

                    <div class="content">
                        <div class="left budgets clearfix"> 
                            <% //retrieve the  %> 
                            <% String orderStatus3 = theForm.getOrderOpDetailForm().getOrderStatusDetail().getOrderDetail().getOrderStatusCd(); %>    
                            <% if (orderStatus3.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL) ||
                                    orderStatus3.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION) ||
                                    orderStatus3.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_DATE)) { %>
				    
                                  <h1><app:storeMessage key="shop.catalog.text.orderNumber" />: <bean:write name="theForm" property="orderOpDetailForm.orderStatusDetail.orderDetail.orderNum"/><span class="alert"> (<%=com.cleanwise.view.utils.ShopTool.xlateStatus(orderStatusDetail, request)%>)</span></h1>
                    	    <%
				if( orderStatus3.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL)
						&& !appUser.isBrowseOnly()
                        && appUser.canMakePurchases() && allowPoEntry && enterPo){
			    %>
				<a href="#" class="blueBtnMed right leftMargin topMargin" onclick="submitForm('<%=Constants.PARAMETER_OPERATION_VALUE_UPDATE_ORDER%>')">
							<span><app:storeMessage key="global.action.label.update" /></span>
				</a>
					
			   <%}%>
			    
                            <% } else if (!appUser.isBrowseOnly()) {
                                    boolean cartHasItems = false;
                                    ShoppingCartData scD = (ShoppingCartData) session.getAttribute(Constants.SHOPPING_CART);
                                    if(scD!=null && scD.getItemsQty() > 0){
                                        cartHasItems = true;
                                    }
                                    String href = "javascript:reorder('" + Constants.PARAMETER_OPERATION_VALUE_REORDER + "', '"+cartHasItems+"');";
                            %>
                                  <h1><app:storeMessage key="shop.catalog.text.orderNumber" />: <bean:write name="theForm" property="orderOpDetailForm.orderStatusDetail.orderDetail.orderNum"/> (<%=com.cleanwise.view.utils.ShopTool.xlateStatus(orderStatusDetail, request)%>)</h1>
                    	    	  <app:authorizedForFunction name="<%=RefCodeNames.APPLICATION_FUNCTIONS.RECEIVING%>">
                    	          <a href="#" class="blueBtnMed right rightMargin topMargin" onclick="submitForm('<%=Constants.PARAMETER_OPERATION_VALUE_UPDATE_RECEIVE%>')"><span><app:storeMessage key="global.action.label.receive" /></span></a>                            
							      </app:authorizedForFunction>
                                  <html:link href="<%=href%>" styleClass="blueBtnMed right rightMargin topMargin">
                                      <span><app:storeMessage key="global.action.label.reorder" /></span>
                                  </html:link>
                            <% } %>
			    <a href="#" class="btn rightMargin topMargin" onclick="window.print();return false;"><span><app:storeMessage key="global.action.label.printPage" /></span></a>

                            <% // if "Revised" Date is different from "Created" Date, show it on the screen;
							   // if "Revised" Date is null or = "" or is the same as "Created" date, DO NOT show it on the screen %>
                            <% if (!equalDatesFl && revisedOrderDate != null && !revisedOrderDate.equals("")) { %>
							      <h3><app:storeMessage key="shop.orderdetail.label.created" />: <%=formattedODate%>&nbsp;&nbsp;&nbsp;&nbsp;<app:storeMessage key="shop.orderdetail.label.revised" />: <%=formattedRevisedODate%></h3>							      
							<% } else { %>
							      <h3><app:storeMessage key="shop.orderdetail.label.created" />: <%=formattedODate%></h3>						  
                            <% } %>     
                            
                            <!-- Checking Workflow properties: the result will allow to show/not to show Approve, Reject, Modify buttons -->

                            <% boolean reasonCodeFl = false; %>
                            <logic:iterate id="orderProperty" indexId="orderPropertyIndex"
							  name="theForm" property="orderOpDetailForm.orderPropertyList" 
							  type="com.cleanwise.service.api.value.OrderPropertyData">
                              <% //Workflow properties %>
							  <logic:equal name="orderProperty" property="shortDesc" value="<%=Constants.WORKFLOW_NOTE%>">							            
							             <%											
											//if the reason hasn't been approved and is one the user is authorized to handle,
											//show Approve, Reject, Modify buttons
											if (orderProperty.getApproveDate() == null &&
											   theForm.getNotesUserApproveIdV().contains(orderProperty.getOrderPropertyId())) {
												    reasonCodeFl = true;
											}
										 %>
							  </logic:equal>
                            </logic:iterate>	
                            <% if (orderStatus3.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL)) { //buttons below - for Pending Approval Orders ONLY %>
			    <p class="left approveRow">
				
				<%
                                  if (!appUser.isBrowseOnly() && (approverFl || reasonCodeFl)) {
                            %>                                	  
                            <p class="left approveRow">
                              <a href="#" class="blueBtnMed topMargin calendarBtn" onclick="submitForm('<%=Constants.PARAMETER_OPERATION_VALUE_APPROVE_ORDER%>')"><span><app:storeMessage key="global.action.label.approve" /></span></a> 
                                        <span class="calendarInput right topMargin">
	    	                                        <html:text property="approvalDate" value="<%=approvalDate%>" styleClass="datepicker2Col dateMirror approveCal" />
	        	                       </span>
                                <a href="#" class="blueBtnMed  topMargin" onclick="submitForm('<%=Constants.PARAMETER_OPERATION_VALUE_REJECT_ORDER%>')"><span><app:storeMessage key="global.action.label.reject" /></span></a>
								<a href="#" class="blueBtnMed  topMargin" onclick="submitForm('<%=Constants.PARAMETER_OPERATION_VALUE_MODIFY_ORDER%>')"><span><app:storeMessage key="shop.orderStatus.text.modify" /></span></a>										
                            
                                  <% } %>
				 </p> 
				  
                            <% } %>	
						</div>
						       
                    </div>
                <!-- Order Info Sub-Panel: Begin -->    
                <!-- Start Box -->
                <div class="boxWrapper smallMargin squareCorners">
                    <div class="top clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
                    <div class="content">
                        <div class="left clearfix">
                                <h2><app:storeMessage key="shop.catalog.text.orderInformation" /></h2>
                            
                            <hr>
                            
                            <div class="twoColBox">
                                <div class="column">
                                    <table>
                                        <colgroup>
                                            <col>
                                            <col width="55%">
                                        </colgroup>                                        
                                        <tbody><tr>
                                            <td>
                                        <!--Order Type: Begin -->
                                                <app:storeMessage key="shop.orderStatus.text.method" />
                                            </td>
                                            <td>
                                            <% String orderSourceCd = theForm.getOrderOpDetailForm().getOrderStatusDetail().getOrderDetail().getOrderSourceCd(); %>
                                                <app:i18nStatus refCodeName="<%=orderSourceCd%>" refCodeGroup="refcode.ORDER_SOURCE_CD" />
                                            
											<%-- <bean:write name="theForm" property="orderOpDetailForm.orderStatusDetail.orderDetail.orderSourceCd"/> --%>
                                            </td>
                                        </tr>
                                        <!-- Order Type: End -->
                                        
                                        <!-- Process Order On: Begin -->
                                        <% String orderStatus5 = theForm.getOrderOpDetailForm().getOrderStatusDetail().getOrderDetail().getOrderStatusCd(); %>
                                        <% if(RefCodeNames.ORDER_STATUS_CD.PENDING_DATE.equals(orderStatus5)) {%>
                                        <tr>
                                            <td>                                                
                                                <app:storeMessage key="shop.orderdetail.label.processOrderOn" />
                                            </td>                                            
                                            <td>
                                                <%
                                                   // --date conversion should be reviewed later
                                                   String processOnDateS = Utility.safeTrim(theForm.getOrderOpDetailForm().getOrderStatusDetail().getPendingDate());
                                                   if (Utility.isSet(processOnDateS)) {
                                                   		SimpleDateFormat defaultFormat = new SimpleDateFormat("MM/dd/yyyy");
                                                   		Date defaultDate = defaultFormat.parse(processOnDateS);

                                                   		String pattern = ClwI18nUtil.getDatePattern(request);
                                                   		SimpleDateFormat sdfm = new SimpleDateFormat(pattern);
 
		                                               	java.util.Date processOnDate = null;
                                                   		if(processOnDateS!=null && processOnDateS.trim().length()>0) {
		                                                      processOnDate = sdfm.parse(sdfm.format(defaultDate));
		                                                      String formattedProcessOnDate = ClwI18nUtil.formatDateInp(request, processOnDate, timeZone.getID() );
                                                %>
                                                      <%=formattedProcessOnDate%>
                                                   <% 	} 
                                                   }
                                                   %>
                                            </td>
                                        </tr>
                                        <% } %>
                                        <!-- Process Order On: End -->
                                        
                                        <!-- Requested Delivery Date: Begin -->
                                        <%
                                        String requestedDeliveryDate = theForm.getOrderOpDetailForm().getOrderStatusDetail().getRequestedShipDate();
                                        %>
                                        <logic:present name="theForm" property="orderOpDetailForm.orderStatusDetail.requestedShipDate">
                                           <bean:define id="ordreqd" type="java.lang.String"
                                             name="theForm"
                                             property="orderOpDetailForm.orderStatusDetail.requestedShipDate"/>
                                             <% if ( ordreqd != null && ordreqd.length() > 0) { %>
                                             <tr>
                                                  <td>
                                                     <app:storeMessage key="shop.orderdetail.label.requestedDeliveryDate" />
                                                  </td>
                                                   <td>
                                                        <%
                                                          String reqShipDate =orderStatusDetail.getRequestedShipDate();
                                                          String dateFormat = ClwI18nUtil.getCountryDateFormat(request);
                                                          SimpleDateFormat userSdf = new SimpleDateFormat(dateFormat);
                                                          SimpleDateFormat baseSdf = new SimpleDateFormat("MM/dd/yyyy");

                                                          Date shipDate = baseSdf.parse(reqShipDate);
                                                          reqShipDate = userSdf.format(shipDate);
                                                        %>
                                                        <%=reqShipDate%>
                                                  </td>
                                            </tr> 
                                            <% } %>
                                        </logic:present> 
                                        <!-- Requested Delivery Date: End -->  
                                                         
                                        <tr>
<%

 int siteAccountId = accountD.getBusEntity().getBusEntityId();
 boolean warehouseFl = false;
 int siteId = 0;
 if(siteD != null){
    siteId = siteD.getBusEntity().getBusEntityId();
 }
 if(currSiteD != null){
   PropertyData warehousePropD = currSiteD.
     getMiscProp(RefCodeNames.PROPERTY_TYPE_CD.CONSOLIDATED_ORDER_WAREHOUSE);
   String warehouseVal = null;
   if(warehousePropD!=null) warehouseVal = warehousePropD.getValue();
   if ( null != warehouseVal ) { warehousePropD.getValue(); }
   warehouseFl = (warehouseVal != null && warehouseVal.length()>0 &&
      "T".equalsIgnoreCase(warehouseVal.substring(0, 1)))?true:false;
 }
%>
                                            <td>
                                                <% //PO# %>
                                                <app:storeMessage key="shop.orderStatus.text.PO#" />
                                            </td>
                                            <td>
                                            <%
                                               String thisPoNum = orderStatusDetail.getOrderDetail().getRequestPoNum();
                                               if ( null == thisPoNum ) thisPoNum = "";
                                               if (
                                                 orderStatus5.equals(RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL)
                                                 && appUser.canMakePurchases() && allowPoEntry && enterPo
                                                ) { %>
                                                <html:text name="theForm" property="orderOpDetailForm.requestPoNum"
                                                value="<%=thisPoNum%>" size="30" maxlength="22"
                                                />
                                                <% } else { %>
                                                  <bean:write name="theForm" property="orderOpDetailForm.orderStatusDetail.orderDetail.requestPoNum"/>
                                                  <html:hidden name="theForm" property="orderOpDetailForm.requestPoNum" value="<%=thisPoNum%>"/>
                                                <% } %>
                                            </td>
                                        </tr>                                        
                                       	<!--  Rebill Order: Begin -->
                                       	<%
                                          if (accountD.isShowReBillOrder() || theForm.getIsRebillOrder()) {
                                           %>
										    <tr>
                                                <td><app:storeMessage key="shop.orderdetail.label.rebillOrder" /></td>
											    <% if (theForm.getIsRebillOrder()) {
											    %>	
											      <td><app:storeMessage key="global.text.yes" /></td>
											    <% } else { %>
											      <td><app:storeMessage key="global.text.no" /></td>
											    <% } %>
                                           </tr>
                                           <% } %>
                                        <!--  Rebill Order: End -->
                                        <% //Hold Order for Consolidation %>
                                        <%
                                           boolean allowOrderConsolidationFl = false;
                                           for (int i = 0; i < accountD.getMiscProperties().size(); i++ ){
                                            PropertyData miscProp = (PropertyData) accountD.getMiscProperties().get(i);
                                        	if (miscProp.getShortDesc().equals(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_ORDER_CONSOLIDATION)
                                        			&& miscProp.getValue().equalsIgnoreCase("true")
                                        			&& miscProp.getPropertyTypeCd().equals(RefCodeNames.STATUS_CD.ACTIVE)
                                        	   )
                                        	{
                                        		allowOrderConsolidationFl = true;
                                        		break;
                                        	}
                                           }

                                            boolean allowOrderConsolidationFl2 = toBeConsolidatedFl &&
                                                RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION.equals(orderStatusDetail.getOrderDetail().getOrderStatusCd());

                                            if (allowOrderConsolidationFl || allowOrderConsolidationFl2) {
                                        %>
										<tr>
                                            <td><app:storeMessage key="shop.orderdetail.label.holdOrderForConsolidation" /></td>
											<!-- I still have a question - what do we want to show here: an ALREADY Consolidated Order in ERP Released Status -->
											<!-- or a CANCELLED ORDER with ORDER_TYPE_CD = "TO_BE_CONSOLIDATED" or ( an Order with ORDER_STATUS_CD="PENDING CONSOLIDATION" -->
											<!-- AND ORDER_TYPE_CD = "TO_BE_CONSOLIDATED")-->
											<!-- According to Functional Spec 1.10, we should show an Order with ORDER_STATUS_CD="PENDING CONSOLIDATION" and ORDER_TYPE_CD = "TO_BE_CONSOLIDATED"  -->
											<% if (allowOrderConsolidationFl2) { %>
											      <td><app:storeMessage key="global.text.yes" /></td>
											<% } else { %>
											      <td><app:storeMessage key="global.text.no" /></td>
											<% } %>
                                        </tr>
                                        <% } %>
                                         <% //Confirmation Only Order %>
                                        <% if (appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.PLACE_CONFIRMATION_ONLY_ORDER) ||
                                            theForm.getIsBillingOrder()) { %>
										<tr>

                                            <td><app:storeMessage key="shop.orderdetail.label.confirmationOnlyOrder" /></td>									
											    <% //boolean confOnlyOrderFl = false; %>                                              
											    <% //OrderPropertyDataVector orderPropertyList = theForm.getOrderOpDetailForm().getOrderPropertyList(); %>
											    <% //if (orderPropertyList != null && orderPropertyList.size() > 0 ) { %>
											       <% //for (int j1 = 0; j1 < orderPropertyList.size(); j1++) { 
											    	      //OrderPropertyData orderPropertyData = (OrderPropertyData) orderPropertyList.get(j1);
											    	      //if (orderPropertyData.getShortDesc().equals(RefCodeNames.ORDER_PROPERTY_TYPE_CD.BILLING_ORDER)
											    	    	  //&& orderPropertyData.getValue().equalsIgnoreCase("true")
											    	    	 //)
											    	      //{
											    	    	  //confOnlyOrderFl = true;
											    	    	  //break;
											    	      //}
											       //} // for
											    //} // if

											    if (theForm.getIsBillingOrder()) {%>
											      <td><app:storeMessage key="global.text.yes" /></td>
											    <% } else { %>
											      <td><app:storeMessage key="global.text.no" /></td>
											    <% } %>										
                                        </tr>
                                        <% } %>
                                        
                                        
                                        <% // Exclude order from budget
										if(appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.EXCLUDE_ORDER_FROM_BUDGET)) { %>
                                        <tr>
                                            <td>
                                                <app:storeMessage key="userportal.esw.checkout.text.excludeOrderFromBudget" />
											</td>
                                            <td>
                                            <% String excludeOrderFromBudget = theForm.getOrderOpDetailForm().getOrderStatusDetail().getOrderDetail().getOrderBudgetTypeCd();
                                            	if (Utility.isSet(excludeOrderFromBudget) && RefCodeNames.ORDER_BUDGET_TYPE_CD.NON_APPLICABLE.equalsIgnoreCase(excludeOrderFromBudget) ) { %>
                                                          <app:storeMessage key="global.text.yes" />
                                                  <% } else { %>
                                                      <app:storeMessage key="global.text.no" />
                                             	 <% } %>
                                            </td>
                                        </tr>
                                        <%} %>	
                                        
                                        <% // Change Order Budget Period
										if(appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.CHANGE_ORDER_BUDGET_PERIOD)) { 
											String budgetYearPeriod = theForm.getOrderOpDetailForm().getOrderStatusDetail().getBudgetYearPeriod();
											if (Utility.isSet(budgetYearPeriod)) {%>
                                        <tr>
                                            <td>
                                                <app:storeMessage key="userportal.esw.checkout.text.orderBudgetPeriod" />
											</td>
                                            <td>
                                            	<%=budgetYearPeriod %>
                                            </td>
                                        </tr>
                                        <%}} %>	
                                        		
                                       <% // Remote Access - Display Service Tickets Info
										if(appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ACCESS_SHOPPING) &&
												theForm.isRemoteAccess()) { 
										%>
                                        <tr>
                                            <td>
                                            	<app:storeMessage key="shop.text.serviceticket.serviceTicketNumbers" />
											</td>
                                            <td>
                                            	<%
                                            		IdVector serviceTickets = null;
                                            		try {
                                            			serviceTickets = theForm.getOrderOpDetailForm().getAssociatedServiceTickets();
                                            		} catch(Exception e){
                                            		}
                                            		
                                            		if(Utility.isSet(serviceTickets)) {
                                            		%>
                                            			<bean:define id="remoteAccessMgrForm" name="esw.RemoteAccessMgrForm" type="com.espendwise.view.forms.esw.RemoteAccessMgrForm"/>
									                	<bean:define id="context" name="remoteAccessMgrForm" property="context" type="java.lang.String"/>
									                	<bean:define id="stDetailUri" name="remoteAccessMgrForm" property="serviceTicketDetailUri" type="java.lang.String"/>
                     
                                            		<%
                                            			for(int x = 0 ; x < serviceTickets.size(); x++){ 
                                            				Integer serviceTicketNumber = (Integer) serviceTickets.get(x);
                                            				if (theForm.getOrderOpDetailForm().getUnavailableServiceTickets()!=null &&
                                            						!theForm.getOrderOpDetailForm().getUnavailableServiceTickets().contains(serviceTicketNumber)) {
	                                            				String link = context + new Formatter().format(stDetailUri, String.valueOf(serviceTicketNumber));
	                                            				%>
	                                            				<html:link href="<%=link%>"><b><%=serviceTicketNumber%></b></html:link>
	                                            				<%
	                                            				if(x < serviceTickets.size()-1) {
	                                            					%>
	                                            					,&nbsp;
	                                            					<%
	                                            				}
                                            				}
                                            			}
                                            		} else {
                                            			%>
                                            			-
                                            			<%
                                            		}
                                            	 %>
                        	                                                  
                                            </td>
                                        </tr>
                                        <%} %> 		
                                        																				

                                    </tbody></table>
                                </div>
                                <div class="column">
                                    <table>
                                        <colgroup>
                                            <col>
                                            <col width="65%">
                                        </colgroup>
                                        <tbody><tr>
                                            <td>
                                                <% //"Placed By" section%>
                                                <app:storeMessage key="shop.orderStatus.text.placedBy" />
                                            </td>
                                            <td>
                                                <bean:write name="theForm" property="orderOpDetailForm.orderStatusDetail.orderDetail.orderContactName"/><br>
                                                <logic:notEmpty name="theForm" property="orderOpDetailForm.orderStatusDetail.orderDetail.orderContactPhoneNum">
                                                    <bean:write name="theForm" property="orderOpDetailForm.orderStatusDetail.orderDetail.orderContactPhoneNum"/><br>
                                                </logic:notEmpty>
                                                <logic:notEmpty name="theForm"
                           									    property="orderOpDetailForm.orderStatusDetail.orderDetail.orderContactEmail">		                  
                                                <a href="mailto:<bean:write name="theForm" property="orderOpDetailForm.orderStatusDetail.orderDetail.orderContactEmail"/>">
									             	<bean:write name="theForm" property="orderOpDetailForm.orderStatusDetail.orderDetail.orderContactEmail"/>
									            </a> 
									            </logic:notEmpty>
                                            </td>
                                        </tr>
								      
									</tbody>
									</table>	
								</div>
							</div>
							<hr />
							<% //Approval Activity %>
                            <h3><app:storeMessage key="shop.orderdetail.label.approvalactivity" /></h3>
                            <div class="twoColBox">
                                <div class="column width80">
                                    <table>      
                                        <logic:iterate id="orderProperty" indexId="orderPropertyIndex"
								         name="theForm" property="orderOpDetailForm.orderPropertyList" 
								         type="com.cleanwise.service.api.value.OrderPropertyData">
                                         <% //Workflow properties %>
							             <logic:equal name="orderProperty" property="shortDesc" value="<%=Constants.WORKFLOW_NOTE%>">
							            
							             <%
											String classString = "class=\"reasonCode\"";
											//if the reason hasn't been approved and is one the user is authorized to handle,
											//or the user is an Approver
											//show it as such in red ink
											
											if( orderProperty.getApproveDate() != null ||
											(!approverFl && !theForm.getNotesUserApproveIdV().contains(orderProperty.getOrderPropertyId()))
											){
													classString = "";
											}
											
											//Show workflow rule in grey if order status is Rejected
											if(orderStatus3.equals(RefCodeNames.ORDER_STATUS_CD.REJECTED) ||
												orderStatus3.equals(RefCodeNames.ORDER_STATUS_CD.CANCELLED)){
												classString = "";
											}
										 %>
        	                             <tr>
        	                             <td <%=classString%>>        					
								         <%
						        	      String messageKey = orderProperty.getMessageKey();
						        	      if (!Utility.isSet(messageKey)) {
						                 %>
						        	     <%=orderProperty.getValue()%>
						                 <%
						        	      } 
						        	      else { 
						                 %>
							            <app:storeMessage key="<%=messageKey%>"
							            arg0="<%=orderProperty.getArg0()%>"
							            arg0TypeCd="<%=orderProperty.getArg0TypeCd()%>"
							            arg1="<%=orderProperty.getArg1()%>"
							            arg1TypeCd="<%=orderProperty.getArg1TypeCd()%>"
							            arg2="<%=orderProperty.getArg2()%>"
							            arg2TypeCd="<%=orderProperty.getArg2TypeCd()%>"
							            arg3="<%=orderProperty.getArg3()%>"
							            arg3TypeCd="<%=orderProperty.getArg3TypeCd()%>"/>
								         <%
						        	     }
								         %>
								         <logic:notEmpty name="orderProperty" property="approveDate">
									       <logic:notEmpty name="orderProperty" property="modBy">
                						       (<app:storeMessage key="shop.orderStatus.text.clearedOnBy"
												arg0="<%=ClwI18nUtil.formatDateInp(request, orderProperty.getApproveDate())%>"
												arg1="<%=orderProperty.getModBy()%>"/>)
                					       </logic:notEmpty>
            					         </logic:notEmpty>
            					         <!-- Initially I put here a piece of code to show Approval Activity (new style) -->         
                                         </td>
                                         </tr>
						                 </logic:equal>
                                        </logic:iterate>
                                    </table>
					            </div>								
					        </div>																			
							<hr />
							<h3><app:storeMessage key="shop.orderdetail.label.customerComments" /></h3>			
							<div class="twoColBox">
                                <div class="column width80">
							
                                    <table>
                                        <colgroup>
                                            <col width="30%" />
											<col />
										</colgroup>  							
									    <logic:present name="theForm" property="orderOpDetailForm.customerOrderNotes">
                                          <bean:size id="custNotesCt" name="theForm" property="orderOpDetailForm.customerOrderNotes"/>
                                          <logic:greaterThan name="custNotesCt" value="0">
                                            <logic:iterate name="theForm" property="orderOpDetailForm.customerOrderNotes" id="note" type="com.cleanwise.service.api.value.OrderPropertyData">

									        <tr>
											   <td><a href="#" class="userNameDisabled"><bean:write name="note" property="addBy"/> <br>
											   <bean:define id="commentDate" name="note" property="addDate"
                                               type="java.util.Date" />
                                               <%-- <i18n:formatDate value="<%=commentDate%>"  pattern="yyyy-MM-dd k:mm"/>--%>
                                               <%
	                                           Date comDate = commentDate;
	                                           String formattedComDate = ClwI18nUtil.formatDateInp(request, comDate, timeZone.getID() );
                                               %>	
                                               <%=formattedComDate%></a>                                          
                                               </td>
											   <td>
											   <bean:define id="noteType" name="note" property="shortDesc" type="String" />
							                    <bean:define id="noteValue" name="note" property="value" type="String" />
							                    <% 
							                    if (noteType.equals("Split Order") || noteType.equals("Cancelled") || noteType.equals("Replacement")){
							                    	int idx = noteValue.indexOf(":");
							                		if (idx > 0){
							                			String temp = noteValue.substring(idx+1).trim();
							                			String[] orderNums = temp.split(",");
							                			for (String orderNum : orderNums){
							                				orderNum = orderNum.trim();
							                				String replaceOrderNum = "<a href=\"showOrderDetail.do?operation=showOrder&amp;orderNum=" + orderNum + "\">" + orderNum + "</a>";//<a href="userOrderDetail.do?action=view&amp;id=4215630">2170359</a>
							                				noteValue = noteValue.replace(orderNum, replaceOrderNum);
							                			}
							                		}
							              	    }
							                    %>
					                           <%= noteValue%>
											   </td>
											</tr>
                                            </logic:iterate>
                                          </logic:greaterThan>
                                        </logic:present>
					
					<% if (!appUser.isBrowseOnly() && appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ADD_CUSTOMER_ORDER_NOTES)) {

					%>				    
					<tr class="comment">
                                            <td colspan="2">
						<p class="addComment">
		                                        <html:link href="#" styleClass="smallBtn">
		                                                <span>
		                                                    	<app:storeMessage key="global.action.label.addComment" />
		                                                </span>
			                                </html:link>
			                        </p>
                                                <p class="commentEntry hide">
		    	                                <span class="textBox">
							        <input type="text" id="commentText" maxlength="<%=Integer.toString(Constants.MAX_LENGTH_ORDER_PROPERTY_VALUE)%>"/>
		            	                        </span>
		                	                
		                                        <html:link href="javascript:saveOrderComment()" styleClass="smallBtn">
		                                            	<span>
		                                                	<app:storeMessage key="global.action.label.save" />
		                                                </span>
		                                        </html:link> 
		                                        <html:link href="#" styleClass="smallBtn cancel">
		                                                <span>
		                                                    	<app:storeMessage key="global.action.label.cancel" />
			                                        </span>
			                                </html:link>
			                        </p>
                                            </td>
                                        </tr>
					<% } %>
					
                                    </table>
								</div>								
							</div>
                        </div>
                    </div>
                    <div class="bottom clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
                </div>
                <!-- Order Info Sub-Panel: End -->    
                <!-- End Box -->   