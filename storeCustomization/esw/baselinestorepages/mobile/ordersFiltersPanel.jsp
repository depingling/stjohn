<%@page import="com.cleanwise.service.api.util.RefCodeNames"%>
<%@page import="com.cleanwise.service.api.dto.LocationSearchDto"%>
<%@page import="com.cleanwise.service.api.dto.OrderSearchDto"%>
<%@page import="com.cleanwise.service.api.util.SessionDataUtil"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.Calendar"%>
<%@page import="com.cleanwise.service.api.util.Utility"%>
<%@page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@page import="com.cleanwise.view.utils.Constants"%>
<%@page import="com.cleanwise.service.api.value.SiteData"%>
<%@page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<bean:define id="ordersForm" name="esw.OrdersForm"  type="com.espendwise.view.forms.esw.OrdersForm"/>

<script language="JavaScript1.2">

function clearFields(bDate,eDate,location) {
	document.getElementById('fromDateId').value = bDate;
	document.getElementById('toDateId').value = eDate;
	document.getElementById('notReceivedId').value = false;
	document.getElementById('ordersChooser').selectedIndex = 0;
	if(location == true) {
		document.getElementById('currentLocationId').checked = true;
	}
	else{
		document.getElementById('allLocationsId').checked = true;
	}
}

function viewWebsite(){
	document.getElementById('viewWebsiteOrderDetailForm').submit();
}
function setRadioValueAndSubmitForm(formId, hiddenFieldId, hiddenFieldValue) {
    //Set the action.
    //if a form id was specified submit it
    if (formId) {
        document.getElementById(hiddenFieldId).value = hiddenFieldValue;
        /* alert("selectedLocation "+selectedLocation);
        document.getElementById('currentLocationId').value = selectedLocation; */
        document.getElementById(formId).submit();
    }
    //otherwise submit the first form
    else {
        document.forms[0].hiddenFieldId.value = hiddenFieldValue;
        document.forms[0].submit();
    }
}
</script>

<% 
CleanwiseUser user = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
SiteData location = user.getSite();
SessionDataUtil sessionData = Utility.getSessionDataUtil(request);

boolean isSpecifiedLocation = sessionData.getSpecifiedLocation() != null; 

//STJ-5677
//LocationSearchDto locationSearchDto = sessionData.getLocationSearchDto();
LocationSearchDto locationSearchDto = sessionData.getLocationSearchDtoMap().get(Constants.CHANGE_LOCATION);
int locationSize = locationSearchDto.getMatchingLocations().size();

String selectedLocation = "allLocations";
if(user.getSite() != null && user.getSite().getSiteId() > 0) {
	selectedLocation = "currentLocation";
	isSpecifiedLocation = true;
}

OrderSearchDto orderSearchInfo = sessionData.getOrderSearchDto();

if(ordersForm != null && ordersForm.getOrdersSearchInfo() != null && ordersForm.getOrdersSearchInfo().getLocationSelected().equals("currentLocation")) {
	selectedLocation = "currentLocation";
} else {
	selectedLocation = "allLocations";
} 

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


String orderURL =  "userportal/esw/orders.do";
StringBuilder showOrderURL = new StringBuilder(50);
showOrderURL.append(orderURL);

Date endDate = new Date();
Calendar calendar = Calendar.getInstance();
calendar.setTime(endDate);
calendar.add(Calendar.DATE, -30);

Date beginDate = new Date(calendar.getTime().getTime());

String bDate =  ClwI18nUtil.formatDateInp(request,beginDate);
String eDate =  ClwI18nUtil.formatDateInp(request,endDate);

%>

<div class="actionBar filters clearfix">
                <html:form styleId="ordersFilterSearchForm" action="<%=showOrderURL.toString() %>">
                	<html:hidden property="operation" styleId="operationId" />
                    <h2><app:storeMessage key="mobile.esw.orders.label.filters"/></h2>
                    <a href="javascript:clearFields('<%=bDate %>','<%=eDate %>',<%=isSpecifiedLocation%>)" class="right clearForm"><app:storeMessage key="mobile.esw.orders.label.clearFields"/></a>
                    					
					<table>
						<colgroup>
                            <col />
                            <col width="50%" />
                        </colgroup>                        
						<%if(locationSize > 1) { %> 
						<tr>
							<td><app:storeMessage key="global.label.location"/></td>
						</tr>
						<tr>
							<td colspan="2">
								<p class="inputRow">
								<% 
									if(selectedLocation.equals("currentLocation")) {
								%>
									<label><html:radio styleId="currentLocationId" styleClass="chkBox" property="ordersSearchInfo.locationSelected" 
									disabled="false" value="currentLocation" /> <app:storeMessage key="mobile.esw.orders.label.current"/></label>
									<label><html:radio styleId="allLocationsId" styleClass="chkBox" property="ordersSearchInfo.locationSelected" value="allLocations" /> <app:storeMessage key="mobile.esw.orders.label.all"/></label>
								<% } else {
									selectedLocation = "allLocations";
								%>
									<label><html:radio styleId="currentLocationId" styleClass="chkBox" property="ordersSearchInfo.locationSelected" value="currentLocation" /> <app:storeMessage key="mobile.esw.orders.label.current"/></label>
									<label><html:radio styleId="allLocationsId" styleClass="chkBox" property="ordersSearchInfo.locationSelected" value="allLocations" disabled="false"/><app:storeMessage key="mobile.esw.orders.label.all"/></label>
								<% } %>
								</p>
							</td>
						</tr>
						<% } %>
						<tr>
							<td>
                                <app:storeMessage  key="orders.filterPane.label.orderNumber" /><br />
                                <html:text property="ordersSearchInfo.orderNumber" />
							</td>
							<td>
                                <app:storeMessage  key="orders.filterPane.label.poNumber" /><br />
                                <html:text property="ordersSearchInfo.purchaseOrderNumber" />
							</td>
                        </tr>							
						<tr>
                            <td colspan="2">
                              <app:storeMessage key="orders.filterPane.label.orderStatus"/>
                                 <html:select property="ordersSearchInfo.orderStatus" styleId='ordersChooser'>
	                            	<html:optionsCollection name="esw.OrdersForm"
	                            		property="ordersStatusFieldChoices" label="label" value="value"/>
	                        	</html:select> 
                            </td>
						</tr>
						<tr>
                            <td colspan="2">
                                <app:storeMessage key="mobile.esw.orders.label.dateRange"/><br />
                                <table>
                                    <tr>
                                        <td><span> <app:storeMessage  key="orders.filterPane.label.from" />: </span>
                                        
                                            <html:text value="<%=fromDateValue %>" property="ordersSearchInfo.from" styleId="fromDateId" />
                                        </td>
                                        <td><span> <app:storeMessage  key="orders.filterPane.label.to" />: </span>
                                            <html:text  value="<%=toDateValue %>" property="ordersSearchInfo.to" styleId="toDateId" />
                                        </td>
                                    </tr>
                                </table>
                            </td>
						</tr>	
						<%
                            if(user.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.RECEIVING)){
                        %>
						<tr>
							<td colspan="2">
								<p class="inputRow">
									<label>  <app:storeMessage key="mobile.esw.orders.label.ordersNotReceived"/> 
									<html:checkbox property="ordersSearchInfo.ordersNotReceived" styleClass="chkBox"  styleId="notReceivedId"  /></label>
								</p>
							</td>
						</tr>
						<% } %>						
						<tr>
                            <td colspan="2">
                            <a onclick="javascript:setRadioValueAndSubmitForm('ordersFilterSearchForm','operationId','<%=Constants.PARAMETER_OPERATION_VALUE_FILTER_ALL_ORDERS  %>')" class="blueBtnMed">
                            <span><app:storeMessage  key="global.action.label.filter" /></span></a>
                            </td>
                        </tr>
						
                    </table>
														
                </html:form>
            </div>
