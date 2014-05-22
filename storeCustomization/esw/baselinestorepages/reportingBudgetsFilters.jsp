<%@page import="com.cleanwise.service.api.util.Utility"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="com.cleanwise.service.api.value.SiteData"%>
<%@page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>
<%@ page import="com.cleanwise.service.api.util.SessionDataUtil" %>

<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%
	String reportingURL =  "userportal/esw/reporting.do";
	StringBuilder showReportURL = new StringBuilder(50);
	showReportURL.append(reportingURL);
		
	String formBeanName = request.getParameter(Constants.PARAMETER_FORM_BEAN_NAME);
	CleanwiseUser user = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
	SiteData location = user.getSite();
	
%>

<div class="leftColumn">
	<html:form styleId="reportingFilterForm" action="<%=showReportURL.toString() %>">
	<html:hidden property="operation" styleId="operationId" />
	
	<h3><app:storeMessage key="reporting.label.filters" /></h3>
                                <hr />
                                
<%--	<p><%=ClwMessageResourcesImpl.getMessage(request,"reporting.label.location")%><br />
		<html:select property="budgetsGlanceReportingInfo.locationSelected" >
	        <html:optionsCollection name="<%=formBeanName%>"
	                            		property="locationFilterChoices" label="label" value="value"/>
	        </html:select><br />
	</p>--%>
	<%
							    SessionDataUtil sessionDataUtil = Utility.getSessionDataUtil(request);
							    String locationSelected = "";
							    String idString = new String();
							    if(sessionDataUtil.getBudgetsGlanceReportingDto()!=null){
								int siteId[] = sessionDataUtil.getBudgetsGlanceReportingDto().getSiteId();
							    
							    for (int i = 0; siteId != null && i < siteId.length; i++) {
								if(Utility.isSet(idString)){
									idString  = idString + "," + Integer.toString(siteId[i]);
								}else{
									idString = Integer.toString(siteId[i]);
								}
								
							    }
							    
							    locationSelected = sessionDataUtil.getBudgetsGlanceReportingDto().getLocationSelected();
							    
							    }
			%>
	
	<app:specifyLocations hiddenName="budgetsGlanceReportingInfo.siteId" locationIds="<%=idString%>" locationSelected="<%=locationSelected%>"
							    selectName="budgetsGlanceReportingInfo.locationSelected" useSelect="true" layout="V" 
							    pageForSpecifyLocation="<%=Constants.SPECIFY_LOCATIONS_BUDGET_AT_A_GLANCE %>" />
	<hr />
                              
                                
        <p><app:storeMessage key="reporting.label.fiscalPeriod" /><br />
		<html:select property="budgetsGlanceReportingInfo.fiscalPeriod" >
	        <html:optionsCollection name="<%=formBeanName%>"
	                            		property="fiscalPeriodFilterChoices" label="label" value="value"/>
	         </html:select>
	</p>
                                
        <hr />
                                
        <a onclick="javascript:setFieldsAndSubmitForm('reportingFilterForm','operationId','<%=Constants.PARAMETER_OPERATION_VALUE_FILTER_BUDGETS_REPORT%>')" class="blueBtnLarge"><span><app:storeMessage key="global.action.label.filter" /></span></a>
			
	</html:form>
</div>			