<%@ page import="com.cleanwise.service.api.util.SessionDataUtil" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>
<%@ page import="com.espendwise.view.forms.esw.EswForm" %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%

	String tabSelectionLink = "userportal/esw/dashboard.do";
	
	//assume that no subtab should be selected
	String pendingOrdersTabClass = "";
	String mostRecentOrderTabClass = "";
	String previousOrdersTabClass = "";
	
	//determine if a subtab should be selected
    EswForm actionForm = (EswForm)request.getAttribute(Constants.ACTION_FORM);
    if (actionForm != null) {
        String SELECTED_TAB_CLASS = "class=\"selected\"";
 		String activeTab = Utility.getSessionDataUtil(request).getSelectedSubSubTab();
 	    if (Constants.TAB_PENDING_ORDERS.equalsIgnoreCase(activeTab)) {
 	    	pendingOrdersTabClass = SELECTED_TAB_CLASS;
 	    }
 	    else if (Constants.TAB_MOST_RECENT_ORDER.equalsIgnoreCase(activeTab)) {
 	    	mostRecentOrderTabClass = SELECTED_TAB_CLASS;
 	    }
 	    else if (Constants.TAB_PREVIOUS_ORDERS.equalsIgnoreCase(activeTab)) {
 	    	previousOrdersTabClass = SELECTED_TAB_CLASS;
 	    }
    }
%>
                            <div class="tabs clearfix">
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
                                <ul>
                                    <li <%=pendingOrdersTabClass%>>
      									<html:form styleId="pendingOrders" action="<%=tabSelectionLink%>">
      		                    			<html:hidden property="<%=Constants.PARAMETER_OPERATION%>"
       											value="<%=Constants.PARAMETER_OPERATION_VALUE_SHOW_PENDING_ORDERS%>"/>
       									</html:form>
                                    	<html:link href="javascript:submitForm('pendingOrders')" title="pendingOrders">
                                    		<span>
                                    			<app:storeMessage key="dashboard.label.pendingOrders" />
                                    		</span>
                                    	</html:link>
                                    </li>
                                    <li <%=mostRecentOrderTabClass%>>
      									<html:form styleId="mostRecentOrder" action="<%=tabSelectionLink%>">
      		                    			<html:hidden property="<%=Constants.PARAMETER_OPERATION%>"
       											value="<%=Constants.PARAMETER_OPERATION_VALUE_SHOW_MOST_RECENT_ORDER%>"/>
       									</html:form>
                                    	<html:link href="javascript:submitForm('mostRecentOrder')" title="mostRecentOrders">
                                    		<span>
                                    			<app:storeMessage key="dashboard.label.mostRecentOrder" />
                                    		</span>
                                    	</html:link>
                                    </li>
                                    <li <%=previousOrdersTabClass%>>
      									<html:form styleId="previousOrders" action="<%=tabSelectionLink%>">
      		                    			<html:hidden property="<%=Constants.PARAMETER_OPERATION%>"
       											value="<%=Constants.PARAMETER_OPERATION_VALUE_SHOW_PREVIOUS_ORDERS%>"/>
       									</html:form>
                                    	<html:link href="javascript:submitForm('previousOrders')" title="previousOrders">
                                    		<span>
                                    			<app:storeMessage key="dashboard.label.previousOrders" />
                                    		</span>
                                    	</html:link>
                                    </li>
                                </ul>
                            </div>
