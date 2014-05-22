<%@page import="org.apache.commons.lang.StringUtils"%>
<%@page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@page import="com.cleanwise.service.api.util.SessionDataUtil"%>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.service.api.value.ShoppingCartData" %>
<%@ page import="com.cleanwise.service.api.value.ShoppingCartItemData" %>
<%@ page import="com.cleanwise.service.api.value.SiteData" %>
<%@ page import="com.cleanwise.view.forms.UserShopForm" %>
<%@ page import="com.espendwise.view.forms.esw.EswForm" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List" %>

<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%
	CleanwiseUser user = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
	SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);	
	String userFullName = "";
	String accountName ="";
	if (user != null) {
		String firstName = user.getUser().getFirstName();
		String lastName = user.getUser().getLastName();
		if (user.getUserAccount() != null) 
		accountName = user.getUserAccount().getBusEntity().getShortDesc();
		if (Utility.isSet(firstName) && Utility.isSet(lastName)) {
			StringBuilder nameBuilder = new StringBuilder(50);
			nameBuilder.append(firstName);
			nameBuilder.append(" ");
			nameBuilder.append(lastName);
			userFullName = nameBuilder.toString();
		}
	}
	StringBuilder logoutLink = new StringBuilder(50);
	logoutLink.append("userportal/esw/logoff.do?");
	logoutLink.append(Constants.PARAMETER_OPERATION);
	logoutLink.append("=");
	logoutLink.append(Constants.PARAMETER_OPERATION_VALUE_LOGOUT);
	
	%>

          <div id="mobileWrapper">
                	
  			<h1><%=accountName%></h1>
	            <p class="right"><%=userFullName%> <html:link action="<%=logoutLink.toString()%>">
	                		(<app:storeMessage key="header.label.logout"/>)
	                	</html:link>
	            </p>
	     <%--  <div class="actionBar actionNav">
                <h2><%if(mainTab.equals(Constants.TAB_PENDING_ORDERS)) { %>
                		<app:storeMessage key= "mobile.esw.orders.label.pendingOrders"/>
                	<%} else {%>
                		<app:storeMessage key= "mobile.esw.orders.label.orders"/>
                	<% } %>
                </h2>
          </div>
		  
               <%
					String errorsAndMessagesPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "errorsAndMessages.jsp");
				%>
                <p><jsp:include page="<%=errorsAndMessagesPage %>"/></p>

          
			
		  <ul class="navigation clearfix">
		  <% if(mainTab.equals(Constants.TAB_PENDING_ORDERS)) { %>
			  <li class="selected">
			  	<a href="dashboard.do?operation=">
			  		<app:storeMessage key= "mobile.esw.orders.label.pendingOrders"/>
			  	</a>
			  </li>
              <li>
              	<a href="orders.do?operation=">
              		<app:storeMessage key= "mobile.esw.orders.label.orders"/>
              	</a>
              </li>
		  <% } else {%>
                <li>
                	<a href="dashboard.do?operation=">
                		<app:storeMessage key= "mobile.esw.orders.label.pendingOrders"/>
                	</a>
                </li>
                <li class="selected">
                	<a href="orders.do?operation=">
                		<app:storeMessage key= "mobile.esw.orders.label.orders"/>
                	</a>
                </li>
          <% } %>
          </ul> 
		  --%>
            
            
             <!-- Assumption - Not in phase 1 of mobile -->
            <!--
            <table class="noBorder">
                <tbody>
                    <tr>
                        <td class="hide"><strong>Location</strong><br />
                            JCPenney, West Oaks Mall<br />
                            9409 W Colonial Dr. <br />
                            Ocoee, FL 34761
                        </td>
                        <td>
                            <p class="right hide"><a href="#">(Change Location)</a></p>
                        </td>
                    </tr>
                </tbody>
            </table>
            -->



