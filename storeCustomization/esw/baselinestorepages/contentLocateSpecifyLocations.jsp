<%@page import="com.cleanwise.service.api.value.AddressData"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@page import="com.cleanwise.service.api.util.SessionDataUtil"%>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="org.apache.struts.util.LabelValueBean" %>
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

<%@page import="com.cleanwise.service.api.value.SiteData"%>
<%@page import="com.cleanwise.service.api.value.PropertyData"%>
<%@page import="com.cleanwise.service.api.dto.LocationSearchDto"%>
<bean:define id="locateForm" name="esw.LocateForm"  type="com.espendwise.view.forms.esw.LocateForm"/>
<%
CleanwiseUser user = (CleanwiseUser) session.getAttribute(Constants.APP_USER);  
String locateLink = "userportal/esw/locate.do";
int MINIMUM_LOCATION_AMOUNT_FILTER_RELEVANCE = 10;
boolean showFilters = (user.getConfiguredLocationCount() >= MINIMUM_LOCATION_AMOUNT_FILTER_RELEVANCE);
boolean showStateFilter = true;
if (user != null && user.getUserStore() != null) {
	showStateFilter = user.getUserStore().isStateProvinceRequired();
}
LocationSearchDto locationSearchInfo = locateForm.getLocationSearchInfo();
boolean locationsFound = (locationSearchInfo != null &&
		locationSearchInfo.getMatchingLocations().size() > 0);
%>
<body class="largePopUp widePopUp" onload="loadDoc();">
<script type="text/javascript">
function loadDoc() {
	$('#buttonReturnSelected').bind('click', returnSelected);
	$('#buttonReturnSelected2').bind('click', returnSelected);
}
var specifyLocationsText = '<%=Utility.encodeForJavaScript(ClwMessageResourcesImpl.
        getMessage(request,"locate.specifyLocations.label.specifiedLocations"))%>';
function returnSelected(){
    var ids = new Array();
    var idString = '';
    var hiddenIds = '';
    $.each($("input[name='selected']:checked"), function() {
        ids.push($(this).val());
            idString += (idString.length > 0 ? ',' : '') + $(this).val();
            hiddenIds += "<input type='hidden' name='<%=locateForm.getHiddenName()%>' value='"
    		    + $(this).val() + "'/>"; 
        });
    var objInfo  = window.top.document.getElementById('specifyLocations<%=locateForm.getRandId()%>Info');
    var objLink = window.top.document.getElementById('specifyLocations<%=locateForm.getRandId()%>Link');
    var objOption = window.top.document.getElementById('specifyLocations<%=locateForm.getRandId()%>Option');
    var optText = specifyLocationsText.replace('({0})', '(' + ids.length + ')');
    if (objOption) {
        objOption.text = optText;
        objOption.selected = true;
        objInfo.innerHTML = hiddenIds;
    } else {
        if (ids.length == 0) optText = ''; 
        objInfo.innerHTML = optText + hiddenIds;
    }
    objLink.href = '<%=request.getContextPath()%>/userportal/esw/locate.do?operation=specifyLocations&randId=<%=locateForm.getRandId()%>&hiddenName=<%=locateForm.getHiddenName()%>&ids=' + idString;
    window.top.closePopUp();
}
function checkAll(value) {
	$.each($("input[name='selected']"), function() {
		if (value) {
			$(this).attr('checked', 'true');	
		} else {
			$(this).removeAttr('checked');
		}
		
	});
}
function submitSortBy(sortField) {
    if (sortField == $('#sortField').val()) {
        if ('<%=Constants.LOCATION_SORT_ORDER_ASCENDING%>' == $("#sortOrder").val()) {
            $('#sortOrder').val('<%=Constants.LOCATION_SORT_ORDER_DESCENDING%>');
        } else {
            $('#sortOrder').val('<%=Constants.LOCATION_SORT_ORDER_ASCENDING%>');
        }
    } else {
        $('#sortField').val(sortField);
        $('#sortOrder').val('<%=Constants.LOCATION_SORT_ORDER_ASCENDING%>');	
    }
	$("#sortLocateFormId").submit();
	return false;
}
</script>
<app:setLocaleAndImages/>
<html:form styleId="locateFormId" action="<%=locateLink %>">
<html:hidden property="<%=Constants.PARAMETER_OPERATION%>" styleId="operationId" value="<%=Constants.PARAMETER_OPERATION_SPECIFY_LOCATIONS_SEARCH%>" />
<html:hidden property="locationSearchInfo.sortField" value="<%=locationSearchInfo.getSortField()%>"/>
<html:hidden property="locationSearchInfo.sortOrder" value="<%=locationSearchInfo.getSortOrder()%>"/>
<html:hidden property="locationSearchInfo.userId" value="<%=Integer.toString(user.getUserId())%>"/>
<html:hidden property="locationSearchInfo.mostRecentlyVisitedSiteId" value="<%=locationSearchInfo.getMostRecentlyVisitedSiteId()%>"/>
<html:hidden property="randId" />
		<div class="clearfix">
            <div class="content">             
                <div class="left clearfix" style="width:820px;">
					<h1><app:storeMessage key="locate.specifyLocations.link.title" /></h1>
<%if (showFilters) {%>
<%
String defaultValueKeyword = ClwMessageResourcesImpl.getMessage(request,"location.search.defaultValue.nameAndReferenceNumber");
String defaultValueCity = ClwMessageResourcesImpl.getMessage(request,"location.search.defaultValue.city");
String defaultValueState = ClwMessageResourcesImpl.getMessage(request,"location.search.defaultValue.state");
String defaultValuePostalCode = ClwMessageResourcesImpl.getMessage(request,"location.search.defaultValue.postalCode");
String styleClassKeyword = "default-value";
String valueKeyword = defaultValueKeyword;
if (locationSearchInfo != null && Utility.isSet(locationSearchInfo.getKeyword())) {
	styleClassKeyword = StringUtils.EMPTY;
	valueKeyword = locationSearchInfo.getKeyword();
}
String styleClassCity = "default-value";
String valueCity = defaultValueCity;
if (locationSearchInfo != null && Utility.isSet(locationSearchInfo.getCity())) {
	styleClassCity = StringUtils.EMPTY;
	valueCity = locationSearchInfo.getCity();
}
String styleClassState = "default-value";
String valueState = defaultValueState;
if (locationSearchInfo != null && Utility.isSet(locationSearchInfo.getState())) {
	styleClassState = StringUtils.EMPTY;
	valueState = locationSearchInfo.getState();
}
String styleClassPostalCode = "default-value";
String valuePostalCode = defaultValuePostalCode;
if (locationSearchInfo != null && Utility.isSet(locationSearchInfo.getPostalCode())) {
	styleClassPostalCode = StringUtils.EMPTY;
	valuePostalCode = locationSearchInfo.getPostalCode();
}
%>
<script>
function initSearchValueStyle(id, value) {
    if ($(id).val() != value) {
        $(id).css('color', active_color);
    }
}
function initSearchValueStyles() {
	initSearchValueStyle('#keyword','<%=defaultValueKeyword%>');
	initSearchValueStyle('#city', '<%=defaultValueCity%>');<%
if (showStateFilter) {%>
    initSearchValueStyle('#state','<%=defaultValueState%>');<%
}%>
    initSearchValueStyle('#postalCode','<%=defaultValuePostalCode%>');
	return true;
}
function clearDefaultSearchValue(id, value) {
    if ($(id).val() == value) {
    	$(id).val('');
    }
}
function clearDefaultSearchValues() {
	clearDefaultSearchValue('#keyword','<%=defaultValueKeyword%>');
	clearDefaultSearchValue('#city','<%=defaultValueCity%>');<%
if (showStateFilter) {%>
    clearDefaultSearchValue('#state','<%=defaultValueState%>');<%
}%>
    clearDefaultSearchValue('#postalCode','<%=defaultValuePostalCode%>');
	return true;
}
</script>
                    <p class="filters clearfix">
						<span><app:storeMessage key="location.search.filters" /></span> 
						<html:text property="locationSearchInfo.keyword" styleClass="<%=styleClassKeyword%>" styleId="keyword" value="<%=valueKeyword%>" />
						<html:text property="locationSearchInfo.city" styleClass="<%=styleClassCity%>" styleId="city" value="<%=valueCity%>" />
    <%if (showStateFilter) {%>
						<html:text property="locationSearchInfo.state" styleClass="<%=styleClassState%>" styleId="state" value="<%=valueState%>" />
    <%}%>
						<html:text property="locationSearchInfo.postalCode" styleClass="<%=styleClassPostalCode%>" styleId="postalCode" value="<%=valuePostalCode%>" />
                        <html:link href="javascript:clearDefaultSearchValues();javascript:submitForm('locateFormId');" styleClass="btn">
                            <span><app:storeMessage key="global.action.label.filter" /></span>
                        </html:link>
					</p>
					<p>	
                        <label><html:checkbox property="showInactiveFl" styleClass="chkBox" /><app:storeMessage key="global.label.showInactive" /></label>	
					</p>
<%} %>
                    <hr />
<%if (locationsFound) {%>
					<a id="buttonReturnSelected" href="#" class="blueBtnMed right rightMargin bottomMargin"><span><app:storeMessage key="locate.label.returnSelected" /></span></a>
<div style="overflow-y: auto;height: 300px; width:800px"><br />
                    <div class="tableWrapper">
                        <table class="mostVisited">
                        	<colgroup>
                            	<col />
                                <col class="locationName" />
                                <col class="address" />
                                <col class="locationNumber" />
                                <col class="distNumber" />
                                <col class="select" />
                                <col />
                            </colgroup>
                            <thead>
                            	<tr>
                                    <th class="first">&nbsp;</th>
                                    <th><html:link href="<%=getSortLink(Constants.LOCATION_SORT_FIELD_NAME)%>"><app:storeMessage key="global.label.locationName" /><%=getSortImage(locationSearchInfo,
                                                Constants.LOCATION_SORT_FIELD_NAME)%></html:link></th>
                                    <th><html:link href="<%=getSortLink(Constants.LOCATION_SORT_FIELD_ADDRESS)%>"><app:storeMessage key="location.search.label.locationAddress" /><%=getSortImage(locationSearchInfo,
                                                Constants.LOCATION_SORT_FIELD_ADDRESS)%></html:link></th>
                                    <th><html:link href="<%=getSortLink(Constants.LOCATION_SORT_FIELD_NUMBER)%>"><app:storeMessage key="location.search.label.locationRefNumber" /><%=getSortImage(locationSearchInfo,
                                                Constants.LOCATION_SORT_FIELD_NUMBER)%></html:link></th>
                                    <th><html:link href="<%=getSortLink(Constants.LOCATION_SORT_FIELD_LAST_VISIT)%>"><app:storeMessage key="location.search.label.lastVisited" /><%=getSortImage(locationSearchInfo,
                                                Constants.LOCATION_SORT_FIELD_LAST_VISIT)%></html:link></th>
                                    <th><label class="chkBoxSm resale"><input type="checkbox" onclick="checkAll(this.checked);"/></label></th>
                                    <th class="last">&nbsp;</th>
                                </tr>
                            </thead>
							<tbody>
<%
    Iterator<SiteData> locationIterator = locationSearchInfo.getMatchingLocations().iterator();
    while (locationIterator.hasNext()) {
        SiteData location = locationIterator.next();
        boolean isMostRecentlyVisitedSite = Integer.toString(location.getSiteId()).equalsIgnoreCase(locationSearchInfo.getMostRecentlyVisitedSiteId());
        if (isMostRecentlyVisitedSite) {
%>
                                <tr class="mostViewed">
<%      } else { %>
                                <tr>
<%      }%>
                                    <td class="first"><span>&nbsp;</span></td>
                                    <td>
<%
        if (isMostRecentlyVisitedSite) {
            String msg = ClwMessageResourcesImpl.getMessage(request,"location.search.label.lastVisited");
            if (Utility.isSet(msg)) {
	            msg = msg.replaceAll(" ","&nbsp;");
            }
%>
                                    <div class="mostVisited"><span><%=msg%></span></div>
<%
        }
%>							<%=Utility.encodeForHTML(location.getBusEntity().getShortDesc())%>
                                    </td>
                                    <td><%
                                  //STJ-4689.
			                       	AddressData locationAddress = location.getSiteAddress();
			                       	String address1 = Utility.encodeForHTML(locationAddress.getAddress1());
			                       	String city = Utility.encodeForHTML(locationAddress.getCity());
			                       	String state = "";
			                       	if (user.getUserStore().getCountryProperties() != null) {
			                    	    if (Utility.isTrue(Utility.getCountryPropertyValue(
			                    	    		user.getUserStore().getCountryProperties(), 
			                    	    		RefCodeNames.COUNTRY_PROPERTY.USES_STATE))) {
			                    	    	state = Utility.encodeForHTML(locationAddress.getStateProvinceCd());
			                    	    }
			                    	}
			                       	String postalCode = Utility.encodeForHTML(locationAddress.getPostalCode());
			                       	String addressFormat = Utility.getAddressFormatFor(locationAddress.getCountryCd());  
        					%>
        						<eswi18n:formatAddress address1="<%=address1 %>" postalCode="<%=postalCode %>"  
                                									city="<%=city %>" state="<%=state %>" 
                                									addressFormat="<%=addressFormat %>"/> 
                                    </td>
                                    <td><%
        String locationNumber = StringUtils.EMPTY;
        PropertyData propertyData = location.getMiscProp(RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER);
        if (propertyData != null && Utility.isSet(propertyData.getValue())) {
            locationNumber = propertyData.getValue();
        }
        %><%=Utility.encodeForHTML(locationNumber)%>
                                    </td>
                                    <td><%=Utility.encodeForHTML(ClwI18nUtil.formatDateInp(request, location.getLastUserVisitDate()))%><br/></td>
                                    <td><label class="chkBox"><html:multibox name="locateForm"
                                            property="selected" value="<%=String.valueOf(location.getSiteId())%>"/></label></td>
                                    <td class="last"><span>&nbsp;</span></td>
                                </tr>
<%    } %>                                
                    		</tbody>
                        </table>
                    </div>
</div><br />
					<a id="buttonReturnSelected2" href="#" class="blueBtnMed right rightMargin bottomMargin" onclick="javascript:returnSelected();"><span><app:storeMessage key="locate.label.returnSelected" /></span></a>
<%} else {%>
					<div style="overflow-y: auto;height: 300px; width:800px"><br />
						<h3 align="center"><app:storeMessage key="location.search.noResults" /></h3>
					</div>
<%}%>
                </div>                
            </div>
        </div>
</html:form>
<%if (showFilters) {%>
<script type="text/javascript">initSearchValueStyles();</script>
<%}%>
<html:form styleId="sortLocateFormId" action="<%=locateLink%>">
<html:hidden property="<%=Constants.PARAMETER_OPERATION%>" value="<%=Constants.PARAMETER_OPERATION_VALUE_SORT_LOCATIONS%>" />
<html:hidden property="locationSearchInfo.userId" value="<%=Integer.toString(user.getUserId())%>" />
<html:hidden property="locationSearchInfo.mostRecentlyVisitedSiteId" value="<%=locationSearchInfo.getMostRecentlyVisitedSiteId()%>" />
<html:hidden property="locationSearchInfo.keyword" value="<%=locationSearchInfo.getKeyword()%>" />
<html:hidden property="locationSearchInfo.city" value="<%=locationSearchInfo.getCity()%>" />
<html:hidden property="locationSearchInfo.state" value="<%=locationSearchInfo.getState()%>" />
<html:hidden property="locationSearchInfo.postalCode" value="<%=locationSearchInfo.getPostalCode()%>" />
<html:hidden property="locationSearchInfo.sortField" value="<%=locationSearchInfo.getSortField()%>" styleId="sortField" />
<html:hidden property="locationSearchInfo.sortOrder" value="<%=locationSearchInfo.getSortOrder()%>" styleId="sortOrder" />
<html:hidden property="randId" />
</html:form>
</body>
<%!
	private final String upArrowImg = "<img src='../../esw/images/upArrow.gif'/>";
	private final String downArrowImg = "<img src='../../esw/images/downArrow.gif'/>";

    private String getSortImage(LocationSearchDto locationSearchInfo, String locationSortFieldName) {
        String sortField = locationSearchInfo.getSortField();
   	    String sortOrder = locationSearchInfo.getSortOrder();
   	    if (locationSortFieldName.equalsIgnoreCase(sortField)) {
       	    if (Constants.LOCATION_SORT_ORDER_ASCENDING.equalsIgnoreCase(sortOrder)) {
       		    return upArrowImg;
       	    } else {
       	        return downArrowImg;
       	    }
   	   }
       return "";
    }
    
    private String getSortLink(String sortField) {
        return "javascript:submitSortBy('" + sortField + "');";
    }
%>