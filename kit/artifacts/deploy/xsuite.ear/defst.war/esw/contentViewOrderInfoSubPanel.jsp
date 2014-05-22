<%@page import="java.util.Formatter"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.service.api.util.*"%>
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

<app:setLocaleAndImages/>

<% CleanwiseUser appUser = (CleanwiseUser)
     session.getAttribute(Constants.APP_USER); %>

<bean:define id="theForm" name="esw.OrdersForm" type="com.espendwise.view.forms.esw.OrdersForm"/>

<bean:define id="workflowRole" type="java.lang.String" name="ApplicationUser" property="user.workflowRoleCd"/>
<bean:define id="orderStatus" name="esw.OrdersForm" property="orderOpDetailForm.orderStatusDetail.orderDetail.orderStatusCd" type="String"/>

                                 		
<%
  OrderStatusDescData orderStatusDetail = theForm.getOrderOpDetailForm().getOrderStatusDetail(); 
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
 
%>


<% //Getting properly formatted Order Date: Begin
   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
   sdf.setTimeZone(timeZone);
   Date date = orderStatusDetail.getOrderDetail().getOriginalOrderDate();
   Date time = orderStatusDetail.getOrderDetail().getOriginalOrderTime();
   Date orderDate = Utility.getDateTime(date, time);
   String formattedODate = ClwI18nUtil.formatDateInp(request, orderDate, timeZone.getID() );
   //Getting  properly formatted Order Date: End
%>
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
                                                <bean:write name="theForm" property="orderOpDetailForm.orderStatusDetail.orderDetail.orderSourceCd"/>
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
                                                   String processOnDateS = theForm.getOrderOpDetailForm().getOrderStatusDetail().getPendingDate();
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
                                                   <% } %>
                                            </td>
                                        </tr>
                                        <% } %>
                                        <!-- Process Order On: End -->
                                        
                                        <!-- Requested Delivery Date: Begin -->
                                        <logic:present name="esw.OrdersForm.OrderOpDetailForm" property="orderStatusDetail.requestedShipDate">
                                           <bean:define id="ordreqd" type="java.lang.String"
                                             name="esw.OrdersForm.OrderOpDetailForm"
                                             property="orderStatusDetail.requestedShipDate"/>
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
                                                 && appUser.canMakePurchases() //&& allowPoEntry
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
                                        <logic:present name="esw.OrdersForm" property="accountMiscProperties">
                                           <% 
                                           boolean rebillOrderFl1 = false; %>
                                           <% 
                                           for (int i = 0; i < theForm.getAccountMiscProperties().size(); i++ ){
                                            PropertyData miscProp = (PropertyData) theForm.getAccountMiscProperties().get(i);
                                        	if (miscProp.getShortDesc().equals(RefCodeNames.PROPERTY_TYPE_CD.SHOW_REBILL_ORDER)
                                        			&& miscProp.getValue().equalsIgnoreCase("true")
                                        			&& miscProp.getPropertyTypeCd().equals(RefCodeNames.STATUS_CD.ACTIVE)
                                        	   )
                                        	{
                                        		rebillOrderFl1 = true;
                                        		break;
                                        	}
                                           }
                                            %>
                                            <% boolean rebillOrderFl2 = false; %>
										    <% OrderPropertyDataVector orderPropertyList = theForm.getOrderOpDetailForm().getOrderPropertyList(); %>
											<% if (orderPropertyList != null && orderPropertyList.size() > 0 ) { %>
											    <% for (int j = 0; j < orderPropertyList.size(); j++) {
											            OrderPropertyData orderPropertyData = (OrderPropertyData) orderPropertyList.get(j);
											    	    if (orderPropertyData.getShortDesc().equals(RefCodeNames.ORDER_PROPERTY_TYPE_CD.REBILL_ORDER)
											    	        && orderPropertyData.getValue().equalsIgnoreCase("true")) {
											    	    	  rebillOrderFl2 = true;
											    	    	  break;
											    	    }
											       } // for
											    } // if

											    if (rebillOrderFl1 || rebillOrderFl2) {
											  %>
										            <tr>
                                                    <td><app:storeMessage key="shop.orderdetail.label.rebillOrder" /></td>
    											    <% if (rebillOrderFl2) {  %>
											            <app:storeMessage key="global.text.yes" />
											        <% } else { %>
											            <app:storeMessage key="global.text.no" />
                                                    <% } %>
                                                    </tr>
                                               <% } //  %>
                                         </logic:present>
                                        <!--  Rebill Order: End -->

									    <% //Hold Order for Consolidation %>
										<%
										   boolean allowOrderConsolidationFl = false;
                                           for (int i = 0; i < theForm.getAccountMiscProperties().size(); i++ ){
                                            PropertyData miscProp = (PropertyData) theForm.getAccountMiscProperties().get(i);
                                        	if (miscProp.getShortDesc().equals(RefCodeNames.PROPERTY_TYPE_CD.ALLOW_ORDER_CONSOLIDATION)
                                        			&& miscProp.getValue().equalsIgnoreCase("true")
                                        			&& miscProp.getPropertyTypeCd().equals(RefCodeNames.STATUS_CD.ACTIVE)
                                        	   )
                                        	{
                                        		allowOrderConsolidationFl = true;
                                        		break;
                                        	}
                                           }


                                            boolean holdOrderForConsolidation =
                                                toBeConsolidatedFl && RefCodeNames.ORDER_STATUS_CD.PENDING_CONSOLIDATION.equals(orderStatusDetail.getOrderDetail().getOrderStatusCd());
                                            if (allowOrderConsolidationFl || holdOrderForConsolidation) {

										   %>
										   <tr>
                                           <td><app:storeMessage key="shop.orderdetail.label.holdOrderForConsolidation" /></td>
  										    <!-- I still have a question - what do we want to show here: an ALREADY Consolidated Order in ERP Released Status -->
											<!-- or a CANCELLED ORDER with ORDER_TYPE_CD = "TO_BE_CONSOLIDATED" or ( an Order with ORDER_STATUS_CD="PENDING CONSOLIDATION" -->
											<!-- AND ORDER_TYPE_CD = "TO_BE_CONSOLIDATED")-->
											<!-- According to Functional Spec 1.10, we should show an Order with ORDER_STATUS_CD="PENDING CONSOLIDATION" and ORDER_TYPE_CD = "TO_BE_CONSOLIDATED"  -->
											<% if (holdOrderForConsolidation) { %>
											     <app:storeMessage key="global.text.yes" />
											<% } else { %>
											      <app:storeMessage key="global.text.no" />
											<% } %>
                                            </tr>
                                          <% } // allow or 'yes' %>
										    <% //Confirmation Only Order %>
										<%
										   boolean allowConfirmationOnlyOrderFl = false;
										   if (appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.PLACE_CONFIRMATION_ONLY_ORDER)) {
                                        %>
										<tr>
                                            <td><app:storeMessage key="shop.orderdetail.label.confirmationOnlyOrder" /></td>
											<app:storeMessage key="global.text.yes" />
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
                                            		}else {
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
                                                <logic:notEmpty name="theForm" property="orderOpDetailForm.orderStatusDetail.orderDetail.orderContactEmail">
                                                    <bean:write name="theForm" property="orderOpDetailForm.orderStatusDetail.orderDetail.orderContactEmail"/>
                                                </logic:notEmpty>
                                            </td>
                                        </tr>
								        <% //Customer Comment %>
					                    <% if (appUser.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ADD_CUSTOMER_ORDER_NOTES)) { %>
										<tr>
										    <td colspan="2">
												   <app:storeMessage key="shop.orderdetail.label.customerComments" /><br><br>											 
											       <textarea name="orderOpDetailForm.customerComment">
                                                   </textarea>
											</td>
										</tr>
										<% } %>										
									</tbody>
									</table>	
								</div>
							</div>					
							<hr>			
							<div class="twoColBox">
                                <div class="column width80">				
                                    <table>
                                        <colgroup>
                                            <col>
											<col>
										</colgroup>  							
										<tbody><tr>														
							                <td colspan="2"><app:storeMessage key="shop.orderdetail.label.customerComments" /></td>
									    </tr>
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
											   <app:writeHTML name="note" property="value"/>
											   </td>
											</tr>
                                            </logic:iterate>
                                          </logic:greaterThan>
                                        </logic:present>
										<!--<tr class="comment">
                                                <td colspan="2">
                                                    <p class="addComment"><a href="#" class="smallBtn"><span>Add a Comment</span></a></p>
                                                    <p class="commentEntry hide"><span class="textBox"><input type="text" /></span><a href="#" class="smallBtn"><span>Save</span></a> <a href="#" class="smallBtn cancel"><span>Cancel</span></a></p>
                                                </td>
                                        </tr>-->
                                    </tbody></table>
								</div>								
							</div>

                        </div>
                    </div>
                    <div class="bottom clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
                </div>
                <!-- End Box -->