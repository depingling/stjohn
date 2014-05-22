<%@page import="com.cleanwise.service.api.value.AddressData"%>
<%@page import="com.cleanwise.view.logic.OrcaAccessMgrLogic"%>
<%@page import="java.util.Formatter"%>
<%@page import="com.espendwise.view.forms.esw.RemoteAccessMgrForm"%>
<%@page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@page import="com.cleanwise.service.api.util.SessionDataUtil"%>
<%@ page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.SessionDataUtil" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.service.api.value.ShoppingCartData" %>
<%@ page import="com.cleanwise.service.api.value.ShoppingCartItemData" %>
<%@ page import="com.cleanwise.service.api.value.SiteData" %>
<%@ page import="com.cleanwise.view.forms.UserShopForm" %>
<%@page import="com.cleanwise.view.forms.ShoppingCartForm"%>
<%@ page import="com.espendwise.view.forms.esw.EswForm" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>
<%@ page import="java.math.BigDecimal" %>
<%@page import="com.cleanwise.view.utils.ShopTool"%>

<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri='/WEB-INF/eswI18n.tld' prefix='eswi18n' %>

<%
	CleanwiseUser user = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
	String userFullName = "";
	if (user != null) {
		//if this user is logged in as someone else, show their name (not the name of the user
		//they are logged in as)
		String firstName = user.getUser().getFirstName();
		String lastName = user.getUser().getLastName();
		if (user.getOriginalUser() != null) {
			firstName = user.getOriginalUser().getUser().getFirstName();
			lastName = user.getOriginalUser().getUser().getLastName();			
		}
		else {
			firstName = user.getUser().getFirstName();
			lastName = user.getUser().getLastName();			
		}
		if (Utility.isSet(firstName) && Utility.isSet(lastName)) {
			StringBuilder nameBuilder = new StringBuilder(50);
			nameBuilder.append(firstName);
			nameBuilder.append("&nbsp;");
			nameBuilder.append(lastName);
			userFullName = nameBuilder.toString();
		}
	}
	
	SiteData location = user.getSite();
	
	StringBuilder logoutLink = new StringBuilder(50);
	logoutLink.append("userportal/esw/logoff.do?");
	logoutLink.append(Constants.PARAMETER_OPERATION);
	logoutLink.append("=");
	logoutLink.append(Constants.PARAMETER_OPERATION_VALUE_LOGOUT);
	
    String userProfileLink = "userportal/esw/userProfile.do?operation=" + Constants.PARAMETER_OPERATION_VALUE_USER_PROFILE;
    
    String contactUsLink = "userportal/esw/contactUs.do";
    
    String landingPageLink = "userportal/esw/showModule.do";
    
    StringBuilder uiSelectionLink = new StringBuilder(50);
    uiSelectionLink.append("/userportal/esw/changeUI.do?");
    uiSelectionLink.append(Constants.PARAMETER_OPERATION);
    uiSelectionLink.append("=");
    uiSelectionLink.append(Constants.PARAMETER_OPERATION_VALUE_USE_CLASSIC_UI);
    
    StringBuilder changeLocationLink = new StringBuilder(50);
    changeLocationLink.append("userportal/esw/dashboard.do?");
    changeLocationLink.append(Constants.PARAMETER_OPERATION);
    changeLocationLink.append("=");
    changeLocationLink.append(Constants.PARAMETER_OPERATION_VALUE_SELECT_LOCATION_START); 
    
    StringBuilder assetsLink = new StringBuilder(50);
    assetsLink.append("/userportal/esw/showModule.do?");
    assetsLink.append(Constants.PARAMETER_OPERATION);
    assetsLink.append("=");
    assetsLink.append(Constants.PARAMETER_OPERATION_VALUE_SHOW_MODULE_ASSETS);
    
    StringBuilder servicesLink = new StringBuilder(50);
    servicesLink.append("/userportal/esw/showModule.do?");
    servicesLink.append(Constants.PARAMETER_OPERATION);
    servicesLink.append("=");
    servicesLink.append(Constants.PARAMETER_OPERATION_VALUE_SHOW_MODULE_SERVICES);
    
    StringBuilder shoppingLink = new StringBuilder(50);
    shoppingLink.append("/userportal/esw/showModule.do?"); 
    shoppingLink.append(Constants.PARAMETER_OPERATION);
    shoppingLink.append("=");
    shoppingLink.append(Constants.PARAMETER_OPERATION_VALUE_SHOW_MODULE_SHOPPING);
    
    StringBuilder reportingLink = new StringBuilder(50);
    reportingLink.append("/userportal/esw/showModule.do?");
    reportingLink.append(Constants.PARAMETER_OPERATION);
    reportingLink.append("=");
    reportingLink.append(Constants.PARAMETER_OPERATION_VALUE_SHOW_MODULE_REPORTING);
    
    StringBuilder documentationLink = new StringBuilder(50);
    documentationLink.append("/userportal/esw/showModule.do?");
    documentationLink.append(Constants.PARAMETER_OPERATION);
    documentationLink.append("=");
    documentationLink.append(Constants.PARAMETER_OPERATION_VALUE_SHOW_MODULE_DOCUMENTATION);
    
    //assume that no tab is selected
    String assetsTabClass = "class=\"noSubMenu\"";
    String servicesTabClass = "class=\"noSubMenu\"";
    String shoppingTabClass = "class=\"noSubMenu\"";
    String reportingTabClass = "class=\"noSubMenu\"";
    String documentationTabClass = "class=\"noSubMenu\"";
    String SELECTED_TAB_CLASS = "class=\"noSubMenu selected\"";
    
    //determine if a tab should be selected
    EswForm actionForm = (EswForm)request.getAttribute(Constants.ACTION_FORM);
    if (actionForm != null) {
 		String activeTab = Utility.getSessionDataUtil(request).getSelectedMainTab();
 	    if (Constants.TAB_ASSETS.equalsIgnoreCase(activeTab)) {
 	    	assetsTabClass = SELECTED_TAB_CLASS;
 	    }
 	    else if (Constants.TAB_SERVICES.equalsIgnoreCase(activeTab)) {
 	    	servicesTabClass = SELECTED_TAB_CLASS;
 	    }
 	    else if (Constants.TAB_SHOPPING.equalsIgnoreCase(activeTab)) {
 	    	shoppingTabClass = SELECTED_TAB_CLASS;
 	    }
 	    else if (Constants.TAB_REPORTING.equalsIgnoreCase(activeTab)) {
 	    	reportingTabClass = SELECTED_TAB_CLASS;
 	    }
 	    else if (Constants.TAB_DOCUMENTATION.equalsIgnoreCase(activeTab)) {
 	    	documentationTabClass = SELECTED_TAB_CLASS;
 	    }
    }
    
    
	
	StringBuilder cssStyles = new StringBuilder(50);
	cssStyles.append("clearfix");
    String additionalCssStyles = request.getParameter(Constants.PARAMETER_ADDITIONAL_CSS_STYLES);
    if (Utility.isSet(additionalCssStyles)) {
    	cssStyles.append(" ");
    	cssStyles.append(additionalCssStyles);
    }
    
    //put the header logo from the session data util object into the session, so the customize
    //tag below can find it
    String headerLogo = Utility.getSessionDataUtil(request).getHeaderLogo();
    request.getSession().setAttribute("pages.logo1.image", headerLogo );
    
	boolean authorizedForAssets = false;
	boolean authorizedForServices = false;
	boolean authorizedForShopping = user.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ACCESS_SHOPPING);
	boolean authorizedForReporting = !user.isNoReporting();
    String anchorTitle = ClwMessageResourcesImpl.getMessage(request,"message.label.message");
    
    //STJ-5265: Orca and New UI Communication.
    StringBuilder orcaHomePageLinkURL = null;
    SessionDataUtil sessionData = Utility.getSessionDataUtil(request);
    if(sessionData.isRemoteAccess()) {
    	//Show Assests and Services tab if StJohn is accessed from Remote Orca application.
    	authorizedForAssets = user.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ACCESS_ASSETS);
    	authorizedForServices = user.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.ACCESS_SERVICES);
    	
    	//Build Orca Home Page template Link URL.
    	RemoteAccessMgrForm remoteAccessMgrForm = (RemoteAccessMgrForm)session.getAttribute("esw.RemoteAccessMgrForm");
		if(remoteAccessMgrForm!=null) {
    		orcaHomePageLinkURL = new StringBuilder();
			orcaHomePageLinkURL.append(remoteAccessMgrForm.getContext());
        	orcaHomePageLinkURL.append(new Formatter().format(remoteAccessMgrForm.getBackUri(), String.valueOf(user.getSite().getSiteId())));
    	}
	}
        
%>
	<logic:present name="esw.ModuleIntegrationForm" property="currentMessage">
		<bean:define id="myForm" name="esw.ModuleIntegrationForm" type="com.espendwise.view.forms.esw.ModuleIntegrationForm"/>
        <app:showInterstitialMessage
        	message="<%=myForm.getCurrentMessage()%>"
        	action="/userportal/esw/showMessage.do"
        	operation="<%=Constants.PARAMETER_OPERATION_VALUE_SHOW_MESSAGE%>"
        	anchorCssStyle="popUpInterstitial"
        	anchorTitle="<%=anchorTitle%>"/>
	</logic:present>
        <div id="headerWrapper" class="<%=cssStyles%>">

            <div id="header" class="tabbedHeader">
                <!-- Custom Logo -->
                <% if (Utility.isSet(headerLogo)) { %>
                <html:link action="<%=landingPageLink%>" styleClass="logo">
                	<img src='<app:custom pageElement="pages.logo1.image" addImagePath="true" encodeForHTML="true"/>'/>
                </html:link>
                <% } %>
                <!-- Utility Nav -->
                <p class="utilityNav">
                	<%=userFullName%>
                	<%
                		Boolean logoutEnabled = (Boolean)session.getAttribute(Constants.CW_LOGOUT_ENABLED);
                		if (logoutEnabled == null) {
                			logoutEnabled = Boolean.TRUE;
                		}
                		if (Utility.isTrue(logoutEnabled.toString(), true)) {
                	%>
		                	<html:link action="<%=logoutLink.toString() %>">
		                		(<app:storeMessage key="header.label.logout" />)
		                	</html:link>
                	<%
                		}
                	%>
                	<span>|</span>
                	<html:link action="<%=userProfileLink%>">
                		<app:storeMessage key="header.label.userProfile" />
                	</html:link>
                	<span>|</span>
                	<html:link action="<%=contactUsLink%>">
                		<app:storeMessage key="header.label.contactUs" />
                	</html:link>
                </p>
		<%
			//if the user is logged in as somebody else, then display a "logged in as" label.
        	if (user.getOriginalUser() != null) {
        		StringBuilder labelText = new StringBuilder(50);
        		String firstName = user.getUser().getFirstName();
        		String lastName = user.getUser().getLastName();
        		String userName = user.getLoggedInUserName();
        		//strip out the 
        		if (Utility.isSet(firstName) && Utility.isSet(lastName)) {
        			labelText.append(firstName);
        			labelText.append("&nbsp;");
        			labelText.append(lastName);
        			labelText.append("&nbsp;(");
        		}
        		labelText.append(userName);
        		if (Utility.isSet(firstName) && Utility.isSet(lastName)) {
        			labelText.append(")");
        		}
        %>
				<p class="utilityNav">
                	<app:storeMessage key="header.label.loggedInAs" />:&nbsp;<%=labelText %>
				</p>
        <%
        	}
			//if the user is not logged in as somebody else, has the switch ui privilege, and is 
			//using the shopping module then provide the link to switch between the old and new UI
        	else if (user != null && (shoppingTabClass.equals(SELECTED_TAB_CLASS)) &&
        			user.isAuthorizedForFunction(RefCodeNames.APPLICATION_FUNCTIONS.NEW_UI_ACCESS)) {
		%>
				<%=ShopTool.buildClassicUIHTML(request) %>
		<%
			}
				%>
				
				<%boolean showCorporateOrder = Utility.getSessionDataUtil(request).isConfiguredForCorporateOrders(); 
				  if(showCorporateOrder){
				%>
				<%=ShopTool.buildCorporateOrderHTML(request) %>
				<%}else{ %>
				<%=ShopTool.buildSiteDeliveryHTML(request) %>
				<%} %>
		
                <!-- Start Main Navigation -->
                <ul class="clearfix">
            <%
				//only show the assets tab if the user is configured for assets
            	if (authorizedForAssets) {
				%>
					<li <%=assetsTabClass%>>
					<%
					//If the communication is between Orca and StJohn New UI, return
					// Orca Home page Link URL if the assets tab has been selected.
					if(orcaHomePageLinkURL!=null && orcaHomePageLinkURL.length()>0) {
					%>
							<html:link href="<%=orcaHomePageLinkURL.toString() %>">
								<app:storeMessage key="header.label.assets" />
							</html:link>
								
					<%
					} else {// Services URL of StJohn.
					%>
							<html:link action="<%=assetsLink.toString() %>">
								<app:storeMessage key="header.label.assets" />
							</html:link>
					<%
					}
					%>
						</li>
					<%
            	}
				//only show the services tab if the user is configured for services
        		if (authorizedForServices) {
        		%>
        			<li <%=servicesTabClass%>>
        		<%
					//If the communication is between Orca and StJohn New UI, return
					// Orca Home page Link URL if the Services tab has been selected.
					if(orcaHomePageLinkURL!=null && orcaHomePageLinkURL.length()>0) {
						%>
						
							<html:link href="<%=orcaHomePageLinkURL.toString() %>">
								<app:storeMessage key="header.label.services" />
							</html:link>
						
						<%
					} else {// Services URL of StJohn.
						%>
						
							<html:link action="<%=servicesLink.toString() %>">
								<app:storeMessage key="header.label.services" />
							</html:link>
						
						<%
					}
        			%>
        				</li>
        			<%
					
            	}
				//only show the shopping tab if the user is configured for shopping
        		if (authorizedForShopping) {
            %>
                    <li <%=shoppingTabClass%>>
                		<html:link action="<%=shoppingLink.toString()%>">
                			<app:storeMessage key="header.label.shopping" />
                		</html:link>
                    </li>
			<%
        		}
				//only show the reporting tab if the user is configured for reporting
				if (authorizedForReporting) {
            %>
                    <li <%=reportingTabClass%>>
                		<html:link action="<%=reportingLink.toString()%>">
                			<app:storeMessage key="header.label.reporting" />
                		</html:link>
                    </li>
			<%
				}
			%>
            <!--  STJ-4390
                    <li <%=documentationTabClass%>>
                		<html:link action="<%=documentationLink.toString()%>">
                			<app:storeMessage key="header.label.documentation" />
                		</html:link>
                    </li>
            -->
                </ul>
       <%
       		if (location == null) {
       %>
                <div class="location locationSelect">
                    <div class="content clearfix">
                        <div class="title">
                            <h3>
                				<app:storeMessage key="header.label.locationNotSpecified" />
                            </h3>
                        </div>
                        <%
                        	if (Utility.isTrue((String)session.getAttribute(Constants.CHANGE_LOCATION), true)) {
                            	String selectLocationString = ClwMessageResourcesImpl.getMessage(request,"header.label.selectLocation");
                        %>
								<html:link title="<%=selectLocationString%>" 
									action="<%=changeLocationLink.toString()%>" 
									styleClass="wideBtn blueBtn">
									<span>
		                				<%=selectLocationString%>
									</span>
								</html:link>
						<%
                        	}
						%>
                    </div>
                </div>
		<%
       		}
       		else {
		%>
                <div class="location">
                    <div class="content clearfix">
                        <div class="title">
                            <h3>
                            	<app:storeMessage key="global.label.location" />
                            </h3>
                            <% 
                        		boolean showChangeLocationLink = user.getConfiguredLocationCount() > 1 || location == null;
                        		showChangeLocationLink = showChangeLocationLink && Utility.isTrue((String)session.getAttribute(Constants.CHANGE_LOCATION), true);
                            	if (showChangeLocationLink) { 
                            %>
                            		<html:link action="<%=changeLocationLink.toString()%>">
                            			<span>
                            				(<app:storeMessage key="header.label.changeLocation" />)
                            			</span>
                            		</html:link>
                            <% 
                            	} 
                            %>
                        </div>
					<%
						//display information about the location
						//STJ-4689
						AddressData locationAddress = location.getSiteAddress();
						String locationName = Utility.encodeForHTML(location.getBusEntity().getShortDesc());
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
                        <p>
                        	 <eswi18n:formatAddress name ="<%=locationName %>" address1="<%=address1 %>" postalCode="<%=postalCode %>"  
                                				city="<%=city %>" state="<%=state %>" 
                                				addressFormat="<%=addressFormat %>"/> 
                        </p>
                    <%
        	//STJ-5021 - only show the cart if the user is authorized for shopping
	        	if (authorizedForShopping) {
	        		
	        		// Begin: View Cart Panel 
	        	%>
	        	<%=ShopTool.buildViewCartHTML(request) %>
	        	<%	
	        		//End: View Cart Panel
	        	}
			%>
                    </div>
                </div>
		<%
      		}
		%>
            </div>
        </div>
