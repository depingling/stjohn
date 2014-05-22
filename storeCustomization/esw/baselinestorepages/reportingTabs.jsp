<%@ page import="com.cleanwise.service.api.util.SessionDataUtil" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.view.utils.ShopTool" %>
<%@ page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>

<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%
	//assume that no subtab should be selected
	String ordersReportsTabClass = "";
	String budgetsReportsTabClass = "";
	String miscReportsTabClass = "";
	
	//determine if a subtab should be selected
    String SELECTED_TAB_CLASS = "class=\"selected\"";
 	String activeTab = Utility.getSessionDataUtil(request).getSelectedSubTab();
 	if (Constants.TAB_ORDERS_REPORTS.equalsIgnoreCase(activeTab)) {
 	    ordersReportsTabClass = SELECTED_TAB_CLASS;
 	}
 	else if (Constants.TAB_BUDGETS_REPORTS.equalsIgnoreCase(activeTab)) {
 	    budgetsReportsTabClass = SELECTED_TAB_CLASS;
 	}
 	else if (Constants.TAB_MISC_REPORTS.equalsIgnoreCase(activeTab)) {
 	   	miscReportsTabClass = SELECTED_TAB_CLASS;
	}
    
    StringBuilder orderReportsLink = new StringBuilder(50);
    orderReportsLink.append("userportal/esw/reporting.do?");
    orderReportsLink.append(Constants.PARAMETER_OPERATION);
    orderReportsLink.append("=");
    orderReportsLink.append(Constants.PARAMETER_OPERATION_VALUE_SHOW_ORDERS_REPORTS);
    
    StringBuilder budgetReportsLink = new StringBuilder(50);
    budgetReportsLink.append("userportal/esw/reporting.do?");
    budgetReportsLink.append(Constants.PARAMETER_OPERATION);
    budgetReportsLink.append("=");
    budgetReportsLink.append(Constants.PARAMETER_OPERATION_VALUE_SHOW_BUDGETS_REPORTS);
    
    StringBuilder miscReportsLink = new StringBuilder(50);
    miscReportsLink.append("userportal/esw/reporting.do?");
    miscReportsLink.append(Constants.PARAMETER_OPERATION);
    miscReportsLink.append("=");
    miscReportsLink.append(Constants.PARAMETER_OPERATION_VALUE_SHOW_MISC_REPORTS);
%>
	<div class="tabs clearfix">
    	<div>
        	<div class="top clearfix">
            	<span class="left">&nbsp;</span>
            	<span class="center">&nbsp;</span>
            	<span class="right">&nbsp;</span>
            </div>                        
			<ul>
           		<li <%=ordersReportsTabClass%>>
					<html:link action="<%=orderReportsLink.toString()%>">
   						<span>
   							<app:storeMessage key="header.label.reporting.orders" />
   						</span>
					</html:link>
				</li>
				<%
                    if (ShopTool.doesAnyAccountSupportsBudgets(request)) {
                %>
   				<li <%=budgetsReportsTabClass%>>
					<html:link action="<%=budgetReportsLink.toString()%>">
   						<span>
   							<app:storeMessage key="header.label.reporting.budgets" />
   						</span>
					</html:link>
   				</li>
   				<% } %>
   				<li <%=miscReportsTabClass%>>
     				<html:link action="<%=miscReportsLink.toString()%>">
   						<span>
   							<app:storeMessage key="header.label.reporting.standart" />
   						</span>
     				</html:link>
   				</li>
			</ul>
       </div>
	</div>