<%@page import="com.cleanwise.service.api.value.AddressData"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>
<%@ page import="com.cleanwise.service.api.dto.LocationSearchDto" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.service.api.value.PropertyData" %>
<%@ page import="com.cleanwise.service.api.value.SiteData" %>
<%@ page import="org.apache.commons.lang.StringUtils" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>

<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri='/WEB-INF/eswI18n.tld' prefix='eswi18n' %>

<bean:define id="myForm" name="esw.DashboardForm"  type="com.espendwise.view.forms.esw.DashboardForm"/>

<%

	CleanwiseUser user = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

	int MINIMUM_LOCATION_AMOUNT_FILTER_RELEVANCE = 10;

    String locationSearchLink = "userportal/esw/dashboard.do";
    String locationSortLink = "userportal/esw/dashboard.do";
    String locationSelectLink = "userportal/esw/dashboard.do";
    
    //show filters if the user has access to 10 or more sites
    boolean showFilters = (user.getConfiguredLocationCount() >= MINIMUM_LOCATION_AMOUNT_FILTER_RELEVANCE);

	//state filter is only shown for users belonging to a store that uses states
	boolean showStateFilter = true;
	if (user != null && user.getUserStore() != null) {
		showStateFilter = user.getUserStore().isStateProvinceRequired();
	}
    
    LocationSearchDto locationSearchInfo = myForm.getLocationSearchInfo();
    
    //determine if any matching locations were found
    boolean locationsFound = (locationSearchInfo != null &&
    		locationSearchInfo.getMatchingLocations().size() > 0);
    
%>

<app:setLocaleAndImages/>
        <%
        	String anchorTitle = ClwMessageResourcesImpl.getMessage(request,"message.label.message");
        %>
        <app:showInterstitialMessage
        	message="<%=myForm.getCurrentMessage()%>"
        	action="<%=Constants.GLOBAL_FORWARD_ESW_DASHBOARD%>"
        	operation="<%=Constants.PARAMETER_OPERATION_VALUE_SHOW_MESSAGE%>"
        	anchorCssStyle="popUpInterstitial"
        	anchorTitle="<%=anchorTitle%>"/>
        <div id="contentWrapper" class="clearfix">
            <div id="content">
				<%
					String errorsAndMessagesPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "errorsAndMessages.jsp");
				%>
			    <jsp:include page="<%=errorsAndMessagesPage %>"/>
                <!-- Start Right Column -->
                <div class="rightColumn">
                    <div class="rightColumnIndent">
                        <!-- Start Box -->
                        <div class="boxWrapper">
                            <div class="top clearfix">
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
                            <div class="content">
                                <div class="left clearfix">
                                    <h1><app:storeMessage key="header.label.selectLocation"/></h1>
                                <%
                                	if (showFilters) {
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
                                		//JEE TODO - restore default text behavior when user blanks out a previously populated filter.
                        		%>
                        		
										<script type="text/javascript">
											function initLocationSearchValueStyles()
											{
												var form = document.getElementById('locationSearchForm');
												if (form.elements['locationSearchInfo.keyword'].value != "<%=defaultValueKeyword%>") {
													form.elements['locationSearchInfo.keyword'].style.color = active_color;
												}
												if (form.elements['locationSearchInfo.city'].value != "<%=defaultValueCity%>") {
													form.elements['locationSearchInfo.city'].style.color = active_color;
												}
										<%
											if (showStateFilter) {
										%>
												if (form.elements['locationSearchInfo.state'].value != "<%=defaultValueState%>") {
													form.elements['locationSearchInfo.state'].style.color = active_color;
												}
										<%
											}
										%>
												if (form.elements['locationSearchInfo.postalCode'].value != "<%=defaultValuePostalCode%>") {
													form.elements['locationSearchInfo.postalCode'].style.color = active_color;
												}
												return true;
											}
											
											function clearDefaultLocationSearchValues()
											{
												var form = document.getElementById('locationSearchForm');
												if (form.elements['locationSearchInfo.keyword'].value == "<%=defaultValueKeyword%>") {
													form.elements['locationSearchInfo.keyword'].value = "";
												}
												if (form.elements['locationSearchInfo.city'].value == "<%=defaultValueCity%>") {
													form.elements['locationSearchInfo.city'].value = "";
												}
										<%
											if (showStateFilter) {
										%>
												if (form.elements['locationSearchInfo.state'].value == "<%=defaultValueState%>") {
													form.elements['locationSearchInfo.state'].value = "";
												}
										<%
											}
										%>
												if (form.elements['locationSearchInfo.postalCode'].value == "<%=defaultValuePostalCode%>") {
													form.elements['locationSearchInfo.postalCode'].value = "";
												}
												return true;
											}
										</script>
										
	                    				<html:form styleId="locationSearchForm" action="<%=locationSearchLink%>">
		                                    <p class="filters clearfix">
		                                    	<span>
		                                    		<app:storeMessage key="location.search.filters"/>
		                                    	</span>
		                                        	<html:hidden property="<%=Constants.PARAMETER_OPERATION%>"
		                    							value="<%=Constants.PARAMETER_OPERATION_VALUE_SEARCH_LOCATIONS%>"/>
		                                    		<html:text property="locationSearchInfo.keyword" 
		                                    			styleClass="<%=styleClassKeyword%>" styleId="keyword" 
		                                    			value="<%=valueKeyword%>" /> 
		                                    		<html:text property="locationSearchInfo.city" 
		                                    			styleClass="<%=styleClassCity%>" styleId="city" 
		                                    			value="<%=valueCity%>" /> 
										<%
											if (showStateFilter) {
										%>
		                                    		<html:text property="locationSearchInfo.state" 
		                                    			styleClass="<%=styleClassState%>" styleId="state" 
		                                    			value="<%=valueState%>" /> 
										<%
											}
										%>
		                                    		<html:text property="locationSearchInfo.postalCode" 
		                                    			styleClass="<%=styleClassPostalCode%>" styleId="postalCode" 
		                                    			value="<%=valuePostalCode%>" /> 
		                                    	<%
		                                    		//include hidden fields to carry forward information about any 
		                                    		//previous search start
		                                    	%>
		                                        	<html:hidden property="locationSearchInfo.userId"
		                    							value="<%=Integer.toString(user.getUserId())%>"/>
		                                        	<html:hidden property="locationSearchInfo.mostRecentlyVisitedSiteId"
		                    							value="<%=locationSearchInfo.getMostRecentlyVisitedSiteId()%>"/>
		                                        	<html:hidden property="locationSearchInfo.sortField"
		                    							value="<%=locationSearchInfo.getSortField()%>"/>
		                                        	<html:hidden property="locationSearchInfo.sortOrder"
		                    							value="<%=locationSearchInfo.getSortOrder()%>"/>
		                                    	<%
		                                    		//include hidden fields to carry forward information about any 
		                                    		//previous search end
		                                    	%>
			                                    	<html:link href="javascript:clearDefaultLocationSearchValues();javascript:submitForm('locationSearchForm')" styleClass="btn">
			                                    		<span>
			                                    			<app:storeMessage key="global.action.label.filter"/>
			                                    		</span>
			                                    	</html:link>
		                                    </p>
		                                    <hr/>
	                                    </html:form>
										<script type="text/javascript">
	                                    	initLocationSearchValueStyles();
	                                    </script>
	                                     
                                <%
                               		}
                                 	String upArrowImg = "<img src='../../esw/images/upArrow.gif'/>";
                                    String downArrowImg = "<img src='../../esw/images/downArrow.gif'/>";
                               		String nameSortOrder = Constants.LOCATION_SORT_ORDER_ASCENDING;
                                   	String nameImage = StringUtils.EMPTY;
                                   	String addressSortOrder = Constants.LOCATION_SORT_ORDER_ASCENDING;
                                   	String addressImage = StringUtils.EMPTY;
                                   	String numberSortOrder = Constants.LOCATION_SORT_ORDER_ASCENDING;
                                   	String numberImage = StringUtils.EMPTY;
                                   	String visitDateSortOrder = Constants.LOCATION_SORT_ORDER_ASCENDING;
                                   	String visitDateImage = StringUtils.EMPTY;
                                   	String sortField = locationSearchInfo.getSortField();
                                   	String sortOrder = locationSearchInfo.getSortOrder();
                                   	if (Constants.LOCATION_SORT_FIELD_NAME.equalsIgnoreCase(sortField)) {
                                       	if (Constants.LOCATION_SORT_ORDER_ASCENDING.equalsIgnoreCase(sortOrder)) {
                                       		nameSortOrder = Constants.LOCATION_SORT_ORDER_DESCENDING;
                                       		nameImage = upArrowImg;
                                       	}
                                       	else {
                                       		nameImage = downArrowImg;
                                       	}
                                   	}
                                   	if (Constants.LOCATION_SORT_FIELD_ADDRESS.equalsIgnoreCase(sortField)) {
                                       	if (Constants.LOCATION_SORT_ORDER_ASCENDING.equalsIgnoreCase(sortOrder)) {
                                       		addressSortOrder = Constants.LOCATION_SORT_ORDER_DESCENDING;
                                       		addressImage = upArrowImg;
                                       	}
                                       	else {
                                       		addressImage = downArrowImg;
                                       	}
                                   	}
                                   	if (Constants.LOCATION_SORT_FIELD_NUMBER.equalsIgnoreCase(sortField)) {
                                       	if (Constants.LOCATION_SORT_ORDER_ASCENDING.equalsIgnoreCase(sortOrder)) {
                                       		numberSortOrder = Constants.LOCATION_SORT_ORDER_DESCENDING;
                                       		numberImage = upArrowImg;
                                       	}
                                       	else {
                                       		numberImage = downArrowImg;
                                       	}
                                   	}
                                   	if (Constants.LOCATION_SORT_FIELD_LAST_VISIT.equalsIgnoreCase(sortField)) {
                                       	if (Constants.LOCATION_SORT_ORDER_ASCENDING.equalsIgnoreCase(sortOrder)) {
                                       		visitDateSortOrder = Constants.LOCATION_SORT_ORDER_DESCENDING;
                                       		visitDateImage = upArrowImg;
                                       	}
                                       	else {
                                       		visitDateImage = downArrowImg;
                                       	}
                                   	}
                                %>
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
                                                    <th class="first">
                                                    	&nbsp;
                                                    </th>
                                                    <th>
		                                            	<html:link href="javascript:submitForm('sortByName')">
                                                    		<app:storeMessage key="global.label.locationName"/>
                                                    	<%=nameImage%>
                                                    	</html:link>
                                                    </th>
                                                    <th>
		                                            	<html:link href="javascript:submitForm('sortByAddress')">
                                                    		<app:storeMessage key="location.search.label.locationAddress"/>
                                                    	<%=addressImage%>
                                                    	</html:link>
                                                    </th>
                                                    <th>
		                                            	<html:link href="javascript:submitForm('sortByNumber')">
                                                    		<app:storeMessage key="location.search.label.locationRefNumber"/>
                                                    	<%=numberImage%>
                                                    	</html:link>
                                                    </th>
                                                    <th>
		                                            	<html:link href="javascript:submitForm('sortByVisitDate')">
                                                    		<app:storeMessage key="location.search.label.lastVisited"/>
                                                    	<%=visitDateImage%>
                                                    	</html:link>
                                                    </th>
                                                    <th>
                                                    </th>
                                                    <th class="last">
                                                    	&nbsp;
                                                    </th>
                                                </tr>
                   								<html:form styleId="sortByName" action="<%=locationSortLink%>">
                   		                            	<html:hidden property="<%=Constants.PARAMETER_OPERATION%>"
                    									value="<%=Constants.PARAMETER_OPERATION_VALUE_SORT_LOCATIONS%>"/>
                   		                            	<html:hidden property="locationSearchInfo.sortField"
                    									value="<%=Constants.LOCATION_SORT_FIELD_NAME%>"/>
                   		                            	<html:hidden property="locationSearchInfo.sortOrder"
                    									value="<%=nameSortOrder%>"/>
			                                    	<%
			                                    		//include hidden fields to carry forward information about any 
			                                    		//previous search start
			                                    	%>
			                                        	<html:hidden property="locationSearchInfo.userId"
			                    							value="<%=Integer.toString(user.getUserId())%>"/>
			                                        	<html:hidden property="locationSearchInfo.mostRecentlyVisitedSiteId"
			                    							value="<%=locationSearchInfo.getMostRecentlyVisitedSiteId()%>"/>
			                                        	<html:hidden property="locationSearchInfo.keyword"
			                    							value="<%=locationSearchInfo.getKeyword()%>"/>
			                                        	<html:hidden property="locationSearchInfo.city"
			                    							value="<%=locationSearchInfo.getCity()%>"/>
			                                        	<html:hidden property="locationSearchInfo.state"
			                    							value="<%=locationSearchInfo.getState()%>"/>
			                                        	<html:hidden property="locationSearchInfo.postalCode"
			                    							value="<%=locationSearchInfo.getPostalCode()%>"/>
			                                    	<%
			                                    		//include hidden fields to carry forward information about any 
			                                    		//previous search end
			                                    	%>
                    							</html:form>
                   								<html:form styleId="sortByAddress" action="<%=locationSortLink%>">
                   		                            	<html:hidden property="<%=Constants.PARAMETER_OPERATION%>"
                    									value="<%=Constants.PARAMETER_OPERATION_VALUE_SORT_LOCATIONS%>"/>
                   		                            	<html:hidden property="locationSearchInfo.sortField"
                    									value="<%=Constants.LOCATION_SORT_FIELD_ADDRESS%>"/>
                   		                            	<html:hidden property="locationSearchInfo.sortOrder"
                    									value="<%=addressSortOrder%>"/>
			                                    	<%
			                                    		//include hidden fields to carry forward information about any 
			                                    		//previous search start
			                                    	%>
			                                        	<html:hidden property="locationSearchInfo.userId"
			                    							value="<%=Integer.toString(user.getUserId())%>"/>
			                                        	<html:hidden property="locationSearchInfo.mostRecentlyVisitedSiteId"
			                    							value="<%=locationSearchInfo.getMostRecentlyVisitedSiteId()%>"/>
			                                        	<html:hidden property="locationSearchInfo.keyword"
			                    							value="<%=locationSearchInfo.getKeyword()%>"/>
			                                        	<html:hidden property="locationSearchInfo.city"
			                    							value="<%=locationSearchInfo.getCity()%>"/>
			                                        	<html:hidden property="locationSearchInfo.state"
			                    							value="<%=locationSearchInfo.getState()%>"/>
			                                        	<html:hidden property="locationSearchInfo.postalCode"
			                    							value="<%=locationSearchInfo.getPostalCode()%>"/>
			                                    	<%
			                                    		//include hidden fields to carry forward information about any 
			                                    		//previous search end
			                                    	%>
                    							</html:form>
                   								<html:form styleId="sortByNumber" action="<%=locationSortLink%>">
                   		                            	<html:hidden property="<%=Constants.PARAMETER_OPERATION%>"
                    									value="<%=Constants.PARAMETER_OPERATION_VALUE_SORT_LOCATIONS%>"/>
                   		                            	<html:hidden property="locationSearchInfo.sortField"
                    									value="<%=Constants.LOCATION_SORT_FIELD_NUMBER%>"/>
                   		                            	<html:hidden property="locationSearchInfo.sortOrder"
                    									value="<%=numberSortOrder%>"/>
			                                    	<%
			                                    		//include hidden fields to carry forward information about any 
			                                    		//previous search start
			                                    	%>
			                                        	<html:hidden property="locationSearchInfo.userId"
			                    							value="<%=Integer.toString(user.getUserId())%>"/>
			                                        	<html:hidden property="locationSearchInfo.mostRecentlyVisitedSiteId"
			                    							value="<%=locationSearchInfo.getMostRecentlyVisitedSiteId()%>"/>
			                                        	<html:hidden property="locationSearchInfo.keyword"
			                    							value="<%=locationSearchInfo.getKeyword()%>"/>
			                                        	<html:hidden property="locationSearchInfo.city"
			                    							value="<%=locationSearchInfo.getCity()%>"/>
			                                        	<html:hidden property="locationSearchInfo.state"
			                    							value="<%=locationSearchInfo.getState()%>"/>
			                                        	<html:hidden property="locationSearchInfo.postalCode"
			                    							value="<%=locationSearchInfo.getPostalCode()%>"/>
			                                    	<%
			                                    		//include hidden fields to carry forward information about any 
			                                    		//previous search end
			                                    	%>
                    							</html:form>
                   								<html:form styleId="sortByVisitDate" action="<%=locationSortLink%>">
                   		                            	<html:hidden property="<%=Constants.PARAMETER_OPERATION%>"
                    									value="<%=Constants.PARAMETER_OPERATION_VALUE_SORT_LOCATIONS%>"/>
                   		                            	<html:hidden property="locationSearchInfo.sortField"
                    									value="<%=Constants.LOCATION_SORT_FIELD_LAST_VISIT%>"/>
                   		                            	<html:hidden property="locationSearchInfo.sortOrder"
                    									value="<%=visitDateSortOrder%>"/>
			                                    	<%
			                                    		//include hidden fields to carry forward information about any 
			                                    		//previous search start
			                                    	%>
			                                        	<html:hidden property="locationSearchInfo.userId"
			                    							value="<%=Integer.toString(user.getUserId())%>"/>
			                                        	<html:hidden property="locationSearchInfo.mostRecentlyVisitedSiteId"
			                    							value="<%=locationSearchInfo.getMostRecentlyVisitedSiteId()%>"/>
			                                        	<html:hidden property="locationSearchInfo.keyword"
			                    							value="<%=locationSearchInfo.getKeyword()%>"/>
			                                        	<html:hidden property="locationSearchInfo.city"
			                    							value="<%=locationSearchInfo.getCity()%>"/>
			                                        	<html:hidden property="locationSearchInfo.state"
			                    							value="<%=locationSearchInfo.getState()%>"/>
			                                        	<html:hidden property="locationSearchInfo.postalCode"
			                    							value="<%=locationSearchInfo.getPostalCode()%>"/>
			                                    	<%
			                                    		//include hidden fields to carry forward information about any 
			                                    		//previous search end
			                                    	%>
                    							</html:form>
                                            </thead>
                                            <tbody>
                                            <%
                                            	if (locationsFound) {
                                            		int count = 1;
                                            		Iterator<SiteData> locationIterator = locationSearchInfo.getMatchingLocations().iterator();
                                            		while (locationIterator.hasNext()) {
                                            			SiteData location = locationIterator.next();
                                            			String selectLocationFormId = "locationSelectForm" + count++;
                                            			boolean isMostRecentlyVisitedSite = Integer.toString(location.getSiteId()).equalsIgnoreCase(locationSearchInfo.getMostRecentlyVisitedSiteId());
                                            			if (isMostRecentlyVisitedSite) {
                                            %>
		                                                <tr class="mostViewed">
		                                    <%
                                            			}
                                            			else {
		                                    %>
		                                                <tr>
		                                    <%
                                            			}
		                                    %>
		                                                    <td class="first">
		                                                    	<span>
		                                                    		&nbsp;
		                                                    	</span>
		                                                    </td>
		                                                    <td>
		                                    <%
                                            			if (isMostRecentlyVisitedSite) {
                                            				String msg = ClwMessageResourcesImpl.getMessage(request,"location.search.label.lastVisited");
                                            				if (Utility.isSet(msg)) {
	                                            				msg = msg.replaceAll(" ","&nbsp;");
                                            				}
                                            %>
		                                                        <div class="mostVisited">
		                                                        	<span>
		                                                    			<%=msg%>
		                                                        	</span>
		                                                        </div>
		                                    <%
                                            			}
		                                    %>
		                                                        <%=Utility.encodeForHTML(location.getBusEntity().getShortDesc())%>
		                                                    </td>
		                                                    <td>
															<%	//STJ-4689.
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
		                                                    <td>
		                                                    <%
		                                                    	String locationNumber = StringUtils.EMPTY;
			                                                	PropertyData propertyData = location.getMiscProp(RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER);
			                                                	if (propertyData != null && Utility.isSet(propertyData.getValue())) {
			                                                		locationNumber = propertyData.getValue();
			                                                	}
		                                                    %>
		                                                    	<%=Utility.encodeForHTML(locationNumber)%>
		                                                    </td>
		                                                    <td>
		                                                    	<%=Utility.encodeForHTML(ClwI18nUtil.formatDateInp(request, location.getLastUserVisitDate()))%><br/>
		                                                    </td>
		                                                    <td>
							                           			<%
							                           				StringBuilder href = new StringBuilder(50);
							                           				href.append("javascript:submitForm('");
							                           				href.append(selectLocationFormId);
							                           				href.append("')");
							                           			%>
		                                                    	<html:link href="<%=href.toString()%>" styleClass="blueBtnMed">
		                                                    		<span>
		                                                    			<app:storeMessage key="global.action.label.select"/>
		                                                    		</span>
		                                                    	</html:link>
		                                                    </td>
		                                                    <td class="last">
		                                                    	<span>
		                                                    		&nbsp;
		                                                    	</span>
			                    								<html:form styleId="<%=selectLocationFormId%>" action="<%=locationSelectLink%>">
    		                 		                            	<html:hidden property="<%=Constants.PARAMETER_OPERATION%>"
				                    									value="<%=Constants.PARAMETER_OPERATION_VALUE_SELECT_LOCATION%>"/>
            		         		                            	<html:hidden property="location.busEntity.busEntityId"
		        		            									value="<%=Integer.toString(location.getSiteId())%>"/>
		            		        							</html:form>
		                                                    </td>
		                                                </tr>
                                            <%
                                            		}
                                            	}
                                            %>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <div class="bottom clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
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
                