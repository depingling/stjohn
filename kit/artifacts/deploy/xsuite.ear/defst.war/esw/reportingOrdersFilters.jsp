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
	boolean hideMfg = false;
	if(user.getUserAccount().isHideItemMfg()){
		hideMfg = true;
	}
	SiteData location = user.getSite();
	
%>

<div class="leftColumn">
	<html:form styleId="reportingFilterForm" action="<%=showReportURL.toString() %>">
	<html:hidden property="operation" styleId="operationId" />
	
	<h3><app:storeMessage key="reporting.label.filters" /></h3>
                                <hr />
                                
	<p><app:storeMessage key="reporting.label.location" /><br />
		<html:select property="reportingInfo.locationSelected" >
	        <html:optionsCollection name="<%=formBeanName%>"
	                            		property="locationFilterChoices" label="label" value="value"/>
	        </html:select>						    
	</p>
	
	<hr />
        
	
	<p><app:storeMessage  key="orders.filterPane.label.dateRange" /><br>
             <html:select property="reportingInfo.dateRange" styleClass="dateRange" >
	      <html:optionsCollection name="<%=formBeanName%>"
	                            		property="dateRangeFieldChoices" label="label" value="value"/>
	      </html:select>
        </p>
	<table class="calendar hide">
           <tbody><tr>
                <td><app:storeMessage  key="orders.filterPane.label.from" /></td>
                <td>
                 <html:text styleClass="datepicker2Col standardCal" property="reportingInfo.from"/>
                 </td>
                </tr>
                <tr>
                <td><app:storeMessage  key="orders.filterPane.label.to" /></td>
                <td>
                <html:text styleClass="datepicker2Col standardCal" property="reportingInfo.to"/>
                 </td>
                </tr>
        </tbody></table>
	
                                
        <a onclick="javascript:setFieldsAndSubmitForm('reportingFilterForm','operationId','<%=Constants.PARAMETER_OPERATION_VALUE_FILTER_ORDERS_REPORT%>')" class="blueBtnLarge"><span><app:storeMessage key="global.action.label.filter" /></span></a>
				
	</html:form>
</div>			