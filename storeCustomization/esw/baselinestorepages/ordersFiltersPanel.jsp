<%@page import="com.cleanwise.service.api.util.RefCodeNames"%>
<%@page import="com.cleanwise.service.api.util.Utility"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.cleanwise.service.api.value.SiteData"%>
<%@page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.service.api.util.SessionDataUtil" %>

<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<bean:define id="ordersForm" name="esw.OrdersForm"  type="com.espendwise.view.forms.esw.OrdersForm"/>
<%
	String orderURL =  "userportal/esw/orders.do";
	StringBuilder showOrderURL = new StringBuilder(50);
	showOrderURL.append(orderURL);

	String orderNumSearchLink = "userportal/esw/orders.do";
	String ORDER_SEARCH_VALUE_DISPLAY_SIZE = "50";
	String activeTab = Utility.getSessionDataUtil(request).getSelectedSubTab();
		
	String formBeanName = request.getParameter(Constants.PARAMETER_FORM_BEAN_NAME);
	CleanwiseUser user = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
	SiteData location = user.getSite();
	boolean showChangeLocationLink = user.getConfiguredLocationCount() > 1 || location == null;
	
	String panelTitle = request.getParameter("panelTitle");
	if (!Utility.isSet(panelTitle)) {
		panelTitle = ClwMessageResourcesImpl.getMessage(request,"location.search.filters");
	}


%>
<style type="text/css">

div.leftColumn div.searchOrder_1 a {
    background: url("../../externals/esw/images/productSearchBtn.png") no-repeat scroll left top transparent;
    display: block;
    float: left;
    height: 31px;
    text-indent: -9000em;
    width: 41px;
}

div.leftColumn div.searchOrder_1 div.textBox { 
	background: url("../../externals/esw/images/productSearchBg.png") no-repeat scroll left top transparent;
    display: block;
    float: left;
    height: 27px;
    margin-left: 0px;
    padding: 4px 0 0 6px;
    width: 138px;	
}

div.leftColumn div.searchOrder_1 a:hover {
	background-position: left -31px;
}

div.leftColumn div.searchOrder_1 input {
    background: none repeat scroll 0 0 transparent;
    border: 0 none;
    padding: 4px;
    width: 138px;
}

input {
    color: #575757;
    font-family: Arial,Helvetica,Sans-Serif;
    font-size: 12px;
}

div.boxWrapper div.leftColumn h4 {
    background: url("../../externals/esw/images/leftCol-TitleBg.png") no-repeat scroll left top transparent;
    color: #FFFFFF;
    font-size: 1.8em;
    font-weight: normal;
    padding: 7px 10px 22px;
    text-transform: uppercase;
	
}
h4 {
    font-family: Arial,Helvetica,Sans-Serif;
    font-size: 1.6em;
    font-weight: normal;
    line-height: 1.3em;
    margin-bottom: 15px;
    padding-top: 10px;
}

</style>
                <!-- Start Left Column - columns are reversed to allow expanding right column -->
                <div class="leftColumn">

                    <h4>
                    	<app:storeMessage key="orders.filterPane.label.orderNumber" />
                    </h4>
					
                    <div class=searchOrder_1>
                    	<html:form styleId="orderSearchForm" action="<%=orderNumSearchLink%>">
                    		<html:hidden property="<%=Constants.PARAMETER_OPERATION%>"
                    			value="<%=Constants.PARAMETER_OPERATION_VALUE_SEARCH_ORDERS%>"/>
                    		 <html:hidden property="activeTab" value="<%=activeTab%>"/>
                         <div class="textBox">
	                        <html:text property="orderNumSearchValue" size="<%=ORDER_SEARCH_VALUE_DISPLAY_SIZE%>"/>
	                     </div>
	                        <html:link href="javascript:submitForm('orderSearchForm')">
	                        	<app:storeMessage key="global.action.label.search" />
	                        </html:link>
	                     
	                	</html:form>

                        </div>
	                	<p>
                            &nbsp;&nbsp;
                            &nbsp;
                        </p>

						<hr>

	                	<html:form styleId="ordersFilterSearchForm" action="<%=showOrderURL.toString() %>">
                	<html:hidden property="operation" styleId="operationId" />

                                <h4><%=panelTitle%>
                                </h4>
								
                                <hr>
                                <%--<p><app:storeMessage  key="orders.filterPane.label.locations" />
                                <br>
                                
                                 							    
					<html:select property="ordersSearchInfo.locationSelected">
	                            	<html:optionsCollection name="<%=formBeanName%>"
	                            		property="ordersLocationFieldChoices" label="label" value="value"/>
	                        	</html:select>
							    
							    </p>--%>
							    <%
							    SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
							    String idString = new String();
							    int siteId[] = sessionDataUtil.getOrderSearchDto().getSiteId();
							    
							    for (int i = 0; siteId != null && i < siteId.length; i++) {
								if(Utility.isSet(idString)){
									idString  = idString + "," + Integer.toString(siteId[i]);
								}else{
									idString = Integer.toString(siteId[i]);
								}
								
							    }
							    
							    String locationSelected = sessionDataUtil.getOrderSearchDto().getLocationSelected();
							    %>
							    <app:specifyLocations hiddenName="ordersSearchInfo.siteId" locationIds="<%=idString%>" locationSelected="<%=locationSelected%>"
							    selectName="ordersSearchInfo.locationSelected" useSelect="true" layout="V" 
							    pageForSpecifyLocation="<%=Constants.SPECIFY_LOCATIONS_ALL_ORDERS %>"/>
                              <!--  <html:text styleClass="flyout" property="ordersSearchInfo.locations" 
                                	 /></p>
                                 <div class="flyout">
                                    <div class="flyoutTop">&nbsp;</div>
                                    <div class="flyoutContent">
                                        <div class="flyoutPointer">
                                            <div class="block bottomMargin clearfix">
                                                <div class="blockTop">
                                                    <a href="#">
														 <bean:write name="<%=formBeanName %>" property="ordersSearchInfo.locations" filter="true" />
                                                    </a>
                                                </div>
                                            </div>
                                            <div class="block clearfix">
                                                <div class="blockTop">
                                                    <p>
                                                   <html:link action="<%=showOrderURL.toString() %>">
                                                    	<app:storeMessage  key="orders.filterPane.label.flyOut.currentLocation" />
                                                   </html:link>
                                                    <% 
						                            	if (showChangeLocationLink) { 
						                            %>
						                            <%
						                            String titleToolTip = ClwMessageResourcesImpl.getMessage(request,"orders.filterPane.label.flyOut.selectALocation");
						                            %>
						                            	 	<html:link action="<%=showOrderURL.toString() %>" title="<%=titleToolTip%>">
						                            			<span>
						                            				(<app:storeMessage  key="header.label.changeLocation" />)
						                            			</span>
						                            		</html:link>
						                            <% 
						                            	} 
						                            %>
                                                     </p>
                                                     
                                                     <%
													 	StringBuilder addressString = new StringBuilder(100);
														if(location!=null){
															//display information about the location
															List<String> addressShort = ClwI18nUtil.formatAddressShort(location.getSiteAddress(),
																	user.getUserStore().getCountryProperties(),
																	location.getBusEntity().getShortDesc());
														   
															Iterator<String> iterator = addressShort.iterator();
															while (iterator.hasNext()) {
																addressString.append(Utility.encodeForHTML(iterator.next()));
																addressString.append("<br>");
															}
														}
								                    %>
								                        <p>
								                        	<%=addressString.toString()%>
								                        </p>
                                                    
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="flyoutBottom">&nbsp;</div>
                                </div> -->
                                <hr>
                                
                                <p><app:storeMessage  key="orders.filterPane.label.orderStatus" />
                                <br>
                                <html:select property="ordersSearchInfo.orderStatus">
	                            	<html:optionsCollection name="<%=formBeanName%>"
	                            		property="ordersStatusFieldChoices" label="label" value="value"/>
	                        	</html:select>
	                        	</p>
                                <hr>
                                
                                <p><app:storeMessage  key="orders.filterPane.label.dateRange" /><br>
                                <html:select property="ordersSearchInfo.dateRange" styleClass="dateRange" >
	                            	<html:optionsCollection name="<%=formBeanName%>"
	                            		property="ordersDateRangeFieldChoices" label="label" value="value"/>
	                        	</html:select>
                                </p>
                                
								<%
									String defaultDateFormat = ClwI18nUtil.getDatePattern(request);
									if(Utility.isSet(defaultDateFormat)) {
										defaultDateFormat = defaultDateFormat.toLowerCase();
									}
									String toDateValue = defaultDateFormat;
									String fromDateValue = defaultDateFormat;
									if(Utility.isSet(ordersForm.getOrdersSearchInfo().getTo())) {
										toDateValue = ordersForm.getOrdersSearchInfo().getTo();
									}
									if(Utility.isSet(ordersForm.getOrdersSearchInfo().getFrom())) {
										fromDateValue = ordersForm.getOrdersSearchInfo().getFrom();
									}
								%>
                                <table class="calendar hide">
                                    <tbody><tr>
                                        <td><app:storeMessage  key="orders.filterPane.label.from" /></td>
                                        <td>
                                            <html:text styleClass="default-value standardCal" value="<%=fromDateValue %>" property="ordersSearchInfo.from"
                                            />
                                        </td>
                                    </tr>
                                    <tr>
                                        <td><app:storeMessage  key="orders.filterPane.label.to" /></td>
                                        <td>
                                            <html:text styleClass="default-value standardCal" value="<%=toDateValue %>" property="ordersSearchInfo.to" 
                                              />
                                        </td>
                                    </tr>
                                </tbody></table>
                                
                                <hr>
                                
                                <p><app:storeMessage  key="orders.filterPane.label.poNumber" /><br>
                                <html:text property="ordersSearchInfo.purchaseOrderNumber" /></p>
                                
                                <hr>
                            <%
                            if(user.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.RECEIVING)){
                            %>
                                <p>
									<label>
										<html:checkbox property="ordersSearchInfo.ordersNotReceived" styleClass="chkBox" />  
											<app:storeMessage  key="orders.filterPane.label.ordersNotReceived" />
									</label> 
									<br>
								</p>
						  <%} %>
										<a onclick="javascript:setFieldsAndSubmitForm('ordersFilterSearchForm','operationId','<%=Constants.PARAMETER_OPERATION_VALUE_FILTER_ALL_ORDERS  %>')" class="blueBtnLarge" >
                             	<span><app:storeMessage  key="global.action.label.filter" /></span></a> 
													
                    </html:form>
                </div>

                <!-- End Left Column -->
                    