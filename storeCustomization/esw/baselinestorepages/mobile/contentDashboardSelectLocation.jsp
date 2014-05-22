<%@page import="com.cleanwise.service.api.util.RefCodeNames"%>
<%@page import="com.cleanwise.service.api.value.AddressData"%>
<%@page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@page import="java.util.List"%>
<%@page import="com.cleanwise.service.api.util.SessionDataUtil"%>
<%@page import="com.cleanwise.view.utils.Constants"%>
<%@page import="com.cleanwise.service.api.util.Utility"%>
<%@page import="com.cleanwise.service.api.value.SiteData"%>
<%@page import="java.util.Iterator"%>
<%@page import="com.cleanwise.service.api.dto.LocationSearchDto"%>
<%@page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri='/WEB-INF/eswI18n.tld' prefix='eswi18n' %>

<bean:define id="myForm" name="esw.DashboardForm"  type="com.espendwise.view.forms.esw.DashboardForm"/>
<% 
	LocationSearchDto locationSearchInfo = myForm.getLocationSearchInfo();
	CleanwiseUser user = (CleanwiseUser) session.getAttribute(Constants.APP_USER);

    //determine if any matching locations were found
    boolean locationsFound = (locationSearchInfo != null && locationSearchInfo.getMatchingLocations() != null && locationSearchInfo.getMatchingLocations().size() > 0);
    String locationSelectLink = "userportal/esw/dashboard.do";
    
%>
   
            <div class="actionBar noMargin noBorder actionNav">
                <h2> <app:storeMessage key="header.label.selectLocation" /></h2>
            </div>
            <table class="noBorder">
                <colgroup>
                    <col class="location" />
                    <col class="select" />
                </colgroup>
                <tbody>
                    <%
                        	if (locationsFound) {
                        		int count = 1;
                        		SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
                        		int locationsSize = sessionDataUtil.getLocationsLength();
                        		for( int i = 0; i < locationsSize && i < locationSearchInfo.getMatchingLocations().size(); i++ ) {
                        			SiteData location = (SiteData)locationSearchInfo.getMatchingLocations().get(i);
                        		
                        		
                        			String selectLocationFormId = "locationSelectForm" + count++;
                        			boolean isMostRecentlyVisitedSite = Integer.toString(location.getSiteId()).equalsIgnoreCase(locationSearchInfo.getMostRecentlyVisitedSiteId());
                        			if (isMostRecentlyVisitedSite) {
                    %>
                              <tr class="details mostViewed">
                  		<%
                        	}
                        	else {
                  	    %>
                              <tr class="details">
                  		<%	
                        	}
                  		%>
                              
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
                       	String userCountry = Constants.DEFAULT_LOCALE.getCountry();
                       	
                       	if (user.getSite() != null && user.getSite().getSiteAddress() != null ) {                      
                       		userCountry = user.getSite().getSiteAddress().getCountryCd();
                       	}
                       	String addressFormat = Utility.getAddressFormatFor(userCountry); 
				    %>
                        
                            <%=Utility.encodeForHTML(location.getBusEntity().getShortDesc())%><br>
                          <eswi18n:formatAddress address1="<%=address1 %>" postalCode="<%=postalCode %>"  
                                			  city="<%=city %>" state="<%=state %>" 
                                			  addressFormat="<%=addressFormat %>"/> 
                           </td>
                           <%
	              				StringBuilder href = new StringBuilder(50);
	              				href.append("javascript:submitForm('");
	              				href.append(selectLocationFormId);
	              				href.append("')");
              			   %>
                           <td>
                           		 <html:link href="<%=href.toString()%>" styleClass="blueBtnMed">
                                  		<span>
                                  			<app:storeMessage key="global.action.label.select" />
                                  		</span>
                                  	</html:link>
                           </td>
                           <td class="last">
		                                                    	<span>
		                                                    		&nbsp;
		                                                    	</span>
			                    								<html:form styleId="<%=selectLocationFormId%>" action="<%=locationSelectLink%>">
    		                 		                            	<html:hidden property="<%=com.cleanwise.view.utils.Constants.PARAMETER_OPERATION%>"
				                    									value="<%=Constants.PARAMETER_OPERATION_VALUE_SELECT_LOCATION%>"/>
            		         		                            	<html:hidden property="location.busEntity.busEntityId"
		        		            									value="<%=Integer.toString(location.getSiteId())%>"/>
		            		        							</html:form>
		                                                    </td>
                              </tr>
                              <% } %>
                </tbody>
            </table>
            <%if(locationsSize < locationSearchInfo.getMatchingLocations().size()) { %>
            <p class="centeredButton clearfix">
                 <a href="dashboard.do?operation=showTenMoreLocations" class="blueBtnMed">
                 	<span><app:storeMessage key="mobile.esw.orders.label.showMore"/></span>
                 </a>
            </p>
            <% } }%>
            <div class="footer">
        		<p>
        			<a href="dashboard.do?operation=viewFullWebsiteSelectLocation" >
        				<app:storeMessage key="mobile.esw.orders.label.visitFullWebsite"/>
        			</a>
        		</p>
        
