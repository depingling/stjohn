<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>
<%@ page import="com.cleanwise.service.api.dto.LocationSearchDto" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>

<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri='/WEB-INF/eswI18n.tld' prefix='eswi18n' %>

<script type="text/javascript" src="../../externals/dojo_1.1.0/dojo/dojo.js" djConfig="parseOnLoad: true, isDebug: false">
</script>

<bean:define id="myForm" name="esw.DashboardForm"  type="com.espendwise.view.forms.esw.DashboardForm"/>

<%

	final String COMMENT_FORM = "commentForm";
	final String COMMENT_FORM_ORDERID = "commentFormOrderId";
	final String COMMENT_FORM_COMMENT = "commentFormComment";
	final String COMMENT_ENTRY_CONTROL = "commentEntryControl"; 
	final String COMMENT_CONTAINER = "commentContainer";
	final String COMMENT_SAVE_LINK = "commentSaveLink";

    final int COMMENT_MAX_DISPLAY_LENGTH = 120;
    final String ELLIPSIS = "...";
    
    String approvalDate = myForm.getApprovalDate();
    if (!Utility.isSet(approvalDate)) {
    	approvalDate = ClwI18nUtil.getDatePattern(request);
    }

	CleanwiseUser user = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

   //boolean accountHasFiscalCalendar = ShopTool.doesAnySiteSupportsBudgets(request);
   
   // check if orders accounts support budgets
   boolean supportsBudgets = false;
   if(myForm.getPendingOrderSearchInfo().getMatchingOrders() != null &&
      myForm.getPendingOrderSearchInfo().isOrdersAccountsSupportBudget()){
	supportsBudgets = true;
   }

	boolean includeCommentControls = !user.isBrowseOnly();
	boolean includeOrderActionControls = !user.isBrowseOnly(); 
	boolean disableOrderActionControls = !myForm.isFoundApprovablePendingOrder();
	int columnCount = 6;
	if (includeOrderActionControls) {
		columnCount++;
	}
	if(!supportsBudgets){
        columnCount--;
    }

	String addOrderCommentLink = "userportal/esw/dashboard.do";
	String approveOrRejectOrdersLink = "userportal/esw/dashboard.do";

	StringBuilder showOrderLink = new StringBuilder(50);
	//showOrderLink.append("orders.do?"); //old stmt, commented by SVC on 08/03/2011
	showOrderLink.append("showOrderDetail.do?"); //new stmt, added by SVC on 08/03/2011
	showOrderLink.append(Constants.PARAMETER_OPERATION);
	showOrderLink.append("=");
	showOrderLink.append(Constants.PARAMETER_OPERATION_VALUE_SHOW_ORDER);
	showOrderLink.append("&");
	showOrderLink.append(Constants.PARAMETER_ORDER_ID);
	showOrderLink.append("=");
%>

<script type="text/javascript">

function saveOrderComment(orderId) {
	//set the field values on the common comment form
    dojo.byId("<%=COMMENT_FORM_ORDERID%>").value = orderId;
    dojo.byId("<%=COMMENT_FORM_COMMENT%>").value = dojo.byId("<%=COMMENT_ENTRY_CONTROL%>" + orderId).value;
	//post the comment form
	dojo.xhrPost( { // 
        url: "<%=request.getContextPath()%>/<%=addOrderCommentLink%>",
        form: dojo.byId("<%=COMMENT_FORM%>"),
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
                //replace the existing comments with the new content, which includes the comment just added
                dojo.byId("<%=COMMENT_CONTAINER%>" + orderId).innerHTML = content;
                //clear the comment text box
                dojo.byId("<%=COMMENT_ENTRY_CONTROL%>" + orderId).value = "";
                //clear the common comment controls
                dojo.byId("<%=COMMENT_FORM_ORDERID%>").value = "";
                dojo.byId("<%=COMMENT_FORM_COMMENT%>").value = "";
                //hide the save/cancel buttons and restore the add a comment button
                var saveButtonId = "<%=COMMENT_SAVE_LINK%>" + orderId;
                $("'a[id="+saveButtonId+"]'").parents('p.commentEntry').addClass('hide').siblings('p.addComment').removeClass('hide');
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

function approveOrders() {
	<%
		if (!disableOrderActionControls) {
	%>
			dojo.byId("orderFormOperation").value = '<%=Constants.PARAMETER_OPERATION_VALUE_APPROVE_ORDERS%>';
			submitForm('orderForm');
	<%
		}
	%>
}

function rejectOrders() {
	<%
		if (!disableOrderActionControls) {
	%>
			dojo.byId("orderFormOperation").value = '<%=Constants.PARAMETER_OPERATION_VALUE_REJECT_ORDERS%>';
			submitForm('orderForm');
	<%
		}
	%>
}

</script>

<app:setLocaleAndImages/>
		<%
			String errorsAndMessagesPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "errorsAndMessages.jsp");
		%>
        <jsp:include page="<%=errorsAndMessagesPage %>"/>
		<!-- Begin: Shopping Sub Tabs -->
        <%
			String shoppingTabPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "shoppingTabs.jsp");
		%>
        <jsp:include page="<%=shoppingTabPage%>"/>
      	<!-- End: Shopping Sub Tabs -->
        <div id="contentWrapper" class="clearfix">
            <div id="content">
                <!-- Start Right Column -->
                <div class="rightColumn">
                    <div class="rightColumnIndent">
						<%
							String locationBudgetPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "dashboardLocationBudget.jsp");
						%>
                    	<jsp:include page="<%=locationBudgetPage %>"/>
                    
                        <!-- Start Box -->
                        <div class="boxWrapper">
							<%
								String tabPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "dashboardTabs.jsp");
							%>
	                    	<jsp:include page="<%=tabPage%>"/>
                            <div class="content">
                            	<div class="left clearfix">
                                    <logic:notEmpty name="esw.DashboardForm" property="pendingOrderSearchInfo.matchingOrders">
                                    	<%
                                    	//Create a single form used for adding commments
                                    	%>
              							<html:form styleId="<%=COMMENT_FORM%>" action="<%=addOrderCommentLink%>">
              		                    	<html:hidden property="<%=Constants.PARAMETER_OPERATION%>"
               									value="<%=Constants.PARAMETER_OPERATION_VALUE_ADD_ORDER_COMMENT%>"/>
              		                        <html:hidden styleId="<%=COMMENT_FORM_ORDERID%>" property="orderComment.orderId"
               									value=""/>
               								<html:hidden styleId="<%=COMMENT_FORM_COMMENT%>" property="orderComment.value" value=""/>
               							</html:form>
	                                </logic:notEmpty>
                                    <p class="clearfix">
                               		<html:form styleId="orderForm" action="<%=approveOrRejectOrdersLink%>">
                                 		<html:hidden styleId="orderFormOperation" property="<%=Constants.PARAMETER_OPERATION%>"/>
	                                    <%
    	                                	if (includeOrderActionControls) {
        	                            %>
            	                            <logic:notEmpty name="esw.DashboardForm" property="pendingOrderSearchInfo.matchingOrders">
	            	                        	<span class="selectNav">
	                	                    		<app:storeMessage key="global.action.label.select"/>:&nbsp;&nbsp; 
		                                    	<%
		                                    		if (!disableOrderActionControls) {
		                                    	%>
	                    	                		<html:link href="#" styleClass="all">
	                        	            			<app:storeMessage key="global.label.all"/>
	                            	        		</html:link> 
	                                	    		<span>
	                                    				|
	                                    			</span> 
	                                    			<html:link href="#" styleClass="none">
	                                    				<app:storeMessage key="global.label.none"/>
		                                    		</html:link>
		                                    	<%
		                                    		}
		                                    		else {
		                                    	%>
	                        	            			<app:storeMessage key="global.label.all"/>
	                                	    		<span>
	                                    				|
	                                    			</span> 
	                                    				<app:storeMessage key="global.label.none"/>
		                                    	<%
		                                    		}
		                                    	%>
		                                    	</span>
		                                    	<%
		                                    		StringBuilder style = new StringBuilder(50);
	                                    			style.append("blueBtnMed");
		                                    		if (!disableOrderActionControls) {
		                                    			style.append(" calendarBtn");
		                                    		}
		                                    		else {
		                                    			style.append(" blueBtnMedDisabled");
		                                    		}
		                                    	%>
	    	                                    <html:link href="#" styleClass="<%=style.toString()%>">
	        	                                	<span>
	            	                            		<app:storeMessage key="global.action.label.approve"/>
	                	                        	</span>
	                    	                    </html:link>
		                                    	<%
		                                    		style = new StringBuilder(50);
		                                    		if (!disableOrderActionControls) {
		                                    			style.append("approveBtn");
		                                    		}
		                                    	%>
	                        	                <html:link href="javascript:approveOrders()" onclick="return false;" styleClass="<%=style.toString() %>"/>
		                                    	<%
		                                    		style = new StringBuilder(50);
		                                    		if (!disableOrderActionControls) {
		                                    			style.append("rejectBtn");
		                                    		}
		                                    	%>
	                            	            <html:link href="javascript:rejectOrders()" onclick="return false;" styleClass="rejectBtn"/>
		                                        <span class="calendarInput">
	    	                                        <html:text property="approvalDate" value="<%=approvalDate%>" disabled="<%=disableOrderActionControls%>" styleClass="datepicker2Col dateMirror approveCal" />
	        	                                </span>
		                                    	<%
		                                    		style = new StringBuilder(50);
	                                    			style.append("blueBtnMed");
		                                    		if (!disableOrderActionControls) {
		                                    			style.append(" rejectOrderControl");
		                                    		}
		                                    		else {
		                                    			style.append(" blueBtnMedDisabled");
		                                    		}
		                                    	%>
	            	                            <html:link href="#" styleClass="<%=style.toString() %>">
	                	                        	<span>
	                    	                    		<app:storeMessage key="global.action.label.reject"/>
	                        	                	</span>
	                            	            </html:link> 
	                                		</logic:notEmpty>
		                                <%
    	                                	}
	    	                            %>
            	                        </p>

	                                    <table class="order">
    	                                    <colgroup>
        	                            <%
            	                        	if (includeOrderActionControls) {
                	                    %>
                    	                        <col/>
                        	            <%
                            	        	}
                                	    %>
                                    	        <col/>
                                        	    <col/>
                                                <col/>
                                            	<col/>
	                                            <col/>
    	                                        <col/>
        	                                </colgroup>
            	                            <thead>
                	                            <tr>
                    	                        <%
                        	                    	if (includeOrderActionControls) {
                            	                %>
                                	                <th>
                                    	                <app:storeMessage key="global.action.label.select"/>
                                        	        </th>
                                            	<%
	                                            	}
    	                                        %>
        	                                        <th>
            	                                        <app:storeMessage key="dashboard.label.orderNumber"/>
                	                                </th>
                    	                            <th>
                        	                            <app:storeMessage key="dashboard.label.ordered"/>
                            	                    </th>
                                                    <th>
                                                        <app:storeMessage key="dashboard.label.status"/>
                                                    </th>
                                	                <th>
                                    	                <app:storeMessage key="dashboard.label.location"/>
                                        	        </th>
                                            	    <th>
                                                	    <app:storeMessage key="dashboard.label.orderTotal"/>
	                                                </th>
	                                                <% if (supportsBudgets) { %>
    	                                            <th>
        	                                            <app:storeMessage key="dashboard.label.unusedBudget"/>
            	                                    </th>
            	                                    <%}%>
                	                            </tr>
                    	                    </thead>
                        	                <tbody>
                            	            	<logic:notEmpty name="esw.DashboardForm" property="pendingOrderSearchInfo.matchingOrders">
				                	        		<logic:iterate id="order" indexId="orderIndex"
				                    	    		 	name="esw.DashboardForm" property="pendingOrderSearchInfo.matchingOrders" 
				                        			 	type="com.cleanwise.service.api.value.OrderStatusDescData">
		                                    	        <!-- Repeating Row - needs two rows for each entry to accomodate the comment line which needs colspan -->
		                                        	    <tr class="noBorder">
		                                            	<%
			                                             	if (includeOrderActionControls) {
			                                            %>
	    	                                            	<td>
	        	                                        		<label class="chkBox">
	                                    	            			<html:multibox property="selectedOrderIds" disabled="<%=!Utility.isSet(order.getUserApprovableReasonCodeIds())%>" value="<%=Integer.toString(order.getOrderDetail().getOrderId())%>"/>
	                                        	        		</label>
	                                            	    	</td>
	                                                	<%
		                                             		}
		                                                %>
		                                                	<td>
	    	                                            		<%
	    	                                            			String orderStatus = order.getOrderDetail().getOrderStatusCd();
	    	                                            			boolean orderHasBeenProcessed = (orderStatus != null && !orderStatus.equals(RefCodeNames.ORDER_STATUS_CD.ORDERED_PROCESSING));
	    	                                            			if (orderHasBeenProcessed) {
		        	                                        			String orderLink = showOrderLink.toString() + order.getOrderDetail().getOrderId();
	            	                                    		%>
	                	                                				<html:link href="<%=orderLink%>">
	                    	                            					<bean:write name="order" property="orderDetail.orderNum"/>
	                        	                        				</html:link>
	                        	                        		<%	
	    	                                            			}
	    	                                            			else {
	                        	                        		%>
	                    	                            					<bean:write name="order" property="orderDetail.orderNum"/>
																<%
	    	                                            			}
																%>	                        	                        		
	                            	                    	</td>
	                                	                	<td>
                                        	           			<logic:notEmpty name="order" property="orderDetail.revisedOrderDate">
		                                    	                	<%=Utility.encodeForHTML(ClwI18nUtil.formatDateInp(request, order.getOrderDetail().getRevisedOrderDate()))%>
		                                        	            </logic:notEmpty>
                                                   				<logic:empty name="order" property="orderDetail.revisedOrderDate">
		                                                	    	<%=Utility.encodeForHTML(ClwI18nUtil.formatDateInp(request, order.getOrderDetail().getOriginalOrderDate()))%>
		                                                    	</logic:empty>
		                                                	</td>
                                                    		<td>
				                                                <%  String style = "";
				                                                	if (RefCodeNames.ORDER_STATUS_CD.PENDING_APPROVAL.equals(orderStatus) ||
				                                                			RefCodeNames.ORDER_STATUS_CD.PENDING_DATE.equals(orderStatus) ||
				                                                			RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION.equals(orderStatus)) {
				                                                		style = "alert";
				                                                	}
				                                                %>	
																<span class="<%=style%>">
                                                    				<%=Utility.encodeForHTML(ShopTool.xlateStatus(order, request))%>
                                                    			</span>
																<%
																	//if the order status is "Pending Date", show the date that the order
																	//will be submitted
																	if (RefCodeNames.ORDER_STATUS_CD.PENDING_DATE.equals(orderStatus) &&
																			Utility.isSet(order.getPendingDate())) {
 																		Date processOnDate = new SimpleDateFormat("MM/dd/yyyy").parse(order.getPendingDate());
																%>
																	<span class="<%=style%>">
																		<br>(<%=Utility.encodeForHTML(ClwI18nUtil.formatDateInp(request, processOnDate))%>)
                                                    				</span>
																<% 
																	}
																	if (RefCodeNames.ORDER_STATUS_CD.CANCELLED.equals(orderStatus) &&
                  														order.getConsolidatedOrder()!= null) { 
                  												%>
																		<app:storeMessage key= "shop.orderStatus.text.consolidated" />
																<% 
																	} 
																%>
                                                    		</td>
		                                                	<%
	    	                                            		AddressData address;
	        	                                        		String locationName;
	            	                                			OrderAddressData oad = order.getShipTo();
	                	                                		if (oad != null) {
	                    	                            			address = Utility.toAddress(oad);
	                        	                        			locationName = Utility.encodeForHTML(oad.getShortDesc());
	                            	                    		}
	                                	                		else {
		                                	                		address = AddressData.createValue();
		                                    	            		locationName = StringUtils.EMPTY;
	                                            	    		}
	                	                                		
	                	                                		String address1 = Utility.encodeForHTML(address.getAddress1());
	                	                                		String city = Utility.encodeForHTML(address.getCity());
	                	                                		String state = "";
	                	                                		if (user.getUserStore().getCountryProperties() != null) {
	                	                							if (Utility.isTrue(Utility.getCountryPropertyValue(
	                	                								user.getUserStore().getCountryProperties(), 
	                	                								RefCodeNames.COUNTRY_PROPERTY.USES_STATE))) {
	                	                								state = Utility.encodeForHTML(address.getStateProvinceCd());
	                	                							}
	                	                						}
	                	                						String postalCode = Utility.encodeForHTML(address.getPostalCode());
	                	                						//String country = Utility.encodeForHTML(address.getCountryCd());
	                	                						String addressFormat = Utility.getAddressFormatFor(address.getCountryCd()); 
										                    %>
	                    	                            	<td>
										                        <%-- <%=addressString.toString()%> --%>
										                        <eswi18n:formatAddress name ="<%=locationName %>" address1="<%=address1 %>" postalCode="<%=postalCode %>"  
                                									city="<%=city %>" state="<%=state %>" 
                                									addressFormat="<%=addressFormat %>"/>
	                            	                    	</td>
                                                                
                                                                <td class="right">
	                                	                	<%
                                                				if (orderHasBeenProcessed) { 
	            	                                    	%>
	                	                                	<app:formatPriceCurrency price="<%=order.getEstimatedTotal()%>"
										orderId="<%=order.getOrderDetail().getOrderId()%>"/>
                                                                 <% } %> 
                                                                </td>
                                                        <%
                                                            if(supportsBudgets) {
                                                        %>
			                                        <td class="right">
	                                                		<%
	                                                		    if (order.getOrderSiteData() != null) {
	                                                			    BigDecimal remainingBudget = order.getOrderSiteData().getTotalBudgetRemaining();
	                                                			    if (remainingBudget!= null && !remainingBudget.equals(BigDecimal.ZERO)) {
		                                                	%>
                                                                                <app:formatPriceCurrency price="<%=remainingBudget%>"
                                                                                        orderId="<%=order.getOrderDetail().getOrderId()%>"
                                                                                        checkNegative="true"
                                                                                        negativeClass="negative"/>
                                                            <%
                                                                    }
                                                                }
                                                            %>
		        	                                     
		                                	        </td>
                                                    <% } // supportsBudgets %>
	                                        	    	</tr>
		                                            	<tr class="comment">
	    	                                            	<td colspan="<%=columnCount%>">
																<logic:notEmpty name="order" property="orderPropertyList">
								                	        		<logic:iterate id="orderProperty" indexId="orderPropertyIndex"
								                    	    		 	name="order" property="orderPropertyList" 
								                        			 	type="com.cleanwise.service.api.value.OrderPropertyData">
																		<logic:equal name="orderProperty" property="shortDesc" value="<%=Constants.WORKFLOW_NOTE%>">
																			<%
																				String classString = "";
																				//if the reason hasn't been approved and is one the user is authorized to handle,
																				//show it as such
																				if (orderProperty.getApproveDate() == null &&
																						order.getUserApprovableReasonCodeIds().contains(orderProperty.getOrderPropertyId())) {
																					classString = "class=\"reasonCode\"";
																				}
																			%>
        	                                            					<p <%=classString%>>
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
																			          <% if (orderProperty.getArg0().equals("BudgetYTD")) { %>
																			          <br>
																			          <%
												                                    	Object[] params = new Object[1];
													                                    params[0] = ClwI18nUtil.getPriceShopping(request,ShopTool.getBudgetExceededAmountWithOrder(request, order.getOrderDetail()),"&nbsp;");
													                                    String message = ClwI18nUtil.getMessage(request, "location.pendingOrders.budgetExceededBy", params);
												                                      %>
												                                      <%=message %>
																			          <% } %>
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
	        	                                            					<br/>
        	                                            					</p>
																		</logic:equal>
																	</logic:iterate>
																	<br/>
																</logic:notEmpty>
                        	                           			<div id="<%=COMMENT_CONTAINER + order.getOrderDetail().getOrderId()%>">
                            	                       				<logic:notEmpty name="order" property="orderPropertyList">
										                        		<logic:iterate id="comment" indexId="commentIndex"
										                        		 	name="order" property="orderPropertyList" 
									    	                    		 	type="com.cleanwise.service.api.value.OrderPropertyData">
																			<logic:equal name="comment" property="orderPropertyTypeCd" value="<%=RefCodeNames.ORDER_PROPERTY_TYPE_CD.CUSTOMER_CART_COMMENTS%>">
										        	                		 	<%
										            	                			//if the comment size exceeds the max number of characters to display then trim it and 
										                	            			//append "..."
							                            							if (Utility.isSet(comment.getValue()) && comment.getValue().length() > COMMENT_MAX_DISPLAY_LENGTH) {
							                            								comment.setValue(comment.getValue().substring(0, COMMENT_MAX_DISPLAY_LENGTH) + ELLIPSIS);
							                            							}
										                        			 	%>
										                        		 		<%
											                            			//NOTE: HTML formatting here must be kept in synch with HTML formatting above when rendering a newly
											                            			//added comment.
											                        		 	%>
																				<p>
																					<%=Utility.encodeForHTML(ClwI18nUtil.formatDateInp(request, comment.getAddDate()))%>
																					&nbsp;<bean:write name="comment" property="value"/>
																					&nbsp;(<strong><bean:write name="comment" property="addBy"/></strong>)
											                        		 	</p>
											                        		 </logic:equal>
										                        		</logic:iterate>
	                                	                   			</logic:notEmpty>
									    	                    </div>
				                            	                <%
				                                	             	if (includeCommentControls) {
				                                    	        %>
		                                                	    	<p class="addComment">
		                                                    			<html:link href="#" styleClass="smallBtn">
		                                                    				<span>
		                                                    					<app:storeMessage key="global.action.label.addComment"/>
		                                                    				</span>
			                                                    		</html:link>
			                                                    	</p>
			                                                    	<p class="commentEntry hide">
		    	                                                		<span class="textBox">
							                    							<input type="text" id="<%=COMMENT_ENTRY_CONTROL + order.getOrderDetail().getOrderId()%>" maxlength="<%=Integer.toString(Constants.MAX_LENGTH_ORDER_PROPERTY_VALUE)%>"/>
		            	                                        		</span>
		                	                                    		<%
		                    	                                			StringBuilder commentHref = new StringBuilder(50);
		                        	                            			commentHref.append("javascript:saveOrderComment('");
		                            	                        			commentHref.append(order.getOrderDetail().getOrderId());
		                                	                    			commentHref.append("')");
		                                    	                		%>
		                                        	            		<html:link styleId="<%=COMMENT_SAVE_LINK + order.getOrderDetail().getOrderId()%>" href="<%=commentHref.toString()%>" styleClass="smallBtn">
		                                            	        			<span>
		                                                	    				<app:storeMessage key="global.action.label.save"/>
		                                                    				</span>
		                                                    			</html:link> 
		                                                    			<html:link href="#" styleClass="smallBtn cancel">
		                                                    				<span>
		                                                    					<app:storeMessage key="global.action.label.cancel"/>
			                                                    			</span>
			                                                    		</html:link>
			                                                    	</p>
		    	                                                <%
					                                             	}
		            	                                        %>
	                    	                            	</td>
	                        	                    	</tr>
	                            	                	<!-- End Repeating Row -->
	                                	            </logic:iterate>
	                                    		</logic:notEmpty>
                                            
	                                        </tbody>
    	                                </table>
        	                            <p class="clearfix">
            	                        <%
                	                    	if (includeOrderActionControls) {
                    	                %>
                        	                <logic:notEmpty name="esw.DashboardForm" property="pendingOrderSearchInfo.matchingOrders">
	                        	            	<span class="selectNav">
	                	                    		<app:storeMessage key="global.action.label.select"/>:&nbsp;&nbsp; 
		                                    	<%
		                                    		if (!disableOrderActionControls) {
		                                    	%>
	                    	                		<html:link href="#" styleClass="all">
	                        	            			<app:storeMessage key="global.label.all"/>
	                            	        		</html:link> 
	                                	    		<span>
	                                    				|
	                                    			</span> 
	                                    			<html:link href="#" styleClass="none">
	                                    				<app:storeMessage key="global.label.none"/>
		                                    		</html:link>
		                                    	<%
		                                    		}
		                                    		else {
		                                    	%>
	                        	            			<app:storeMessage key="global.label.all"/>
	                                	    		<span>
	                                    				|
	                                    			</span> 
	                                    				<app:storeMessage key="global.label.none"/>
		                                    	<%
		                                    		}
		                                    	%>
	            	                        	</span>
		                                    	<%
		                                    		StringBuilder style = new StringBuilder(50);
	                                    			style.append("blueBtnMed");
		                                    		if (!disableOrderActionControls) {
		                                    			style.append(" calendarBtn");
		                                    		}
		                                    		else {
		                                    			style.append(" blueBtnMedDisabled");
		                                    		}
		                                    	%>
	    	                                    <html:link href="#" styleClass="<%=style.toString()%>">
	        	                                	<span>
	            	                            		<app:storeMessage key="global.action.label.approve"/>
	                	                        	</span>
	                    	                    </html:link>
		                                        <span class="calendarInput">
	    	                                        <html:text property="approvalDate" value="<%=approvalDate%>" disabled="<%=disableOrderActionControls%>" styleClass="datepicker2Col dateMirror approveCal" />
	        	                                </span>
		                                    	<%
		                                    		style = new StringBuilder(50);
	                                    			style.append("blueBtnMed");
		                                    		if (!disableOrderActionControls) {
		                                    			style.append(" rejectOrderControl");
		                                    		}
		                                    		else {
		                                    			style.append(" blueBtnMedDisabled");
		                                    		}
		                                    	%>
	            	                            <html:link href="#" styleClass="<%=style.toString() %>">
	                	                        	<span>
	                    	                    		<app:storeMessage key="global.action.label.reject"/>
	                        	                	</span>
	                            	            </html:link> 
		                                	</logic:notEmpty>
		                                <%
        	                            	}
	        	                        %>
                	                    </p>
                                    </html:form>
                                </div>
                            </div>
                            <div class="bottom clearfix">
                            	<span class="left">
                            		&nbsp;
                            	</span>
                            	<span class="center">
                            		&nbsp;
                            	</span>
                            	<span class="right">
                            		&nbsp;
                            	</span>
                            </div>
                        </div>
                        <!-- End Box -->
                    </div>
                </div>
                <!-- End Right Column -->
				<%
					String leftColumnPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "dashboardLeftColumn.jsp");
				%>
                <jsp:include page="<%=leftColumnPage%>"/>
            </div>
        </div>        
                