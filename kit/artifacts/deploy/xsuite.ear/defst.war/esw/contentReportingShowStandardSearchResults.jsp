<%@page import="com.cleanwise.view.utils.Constants"%>
<%@page import="com.cleanwise.service.api.value.CurrencyData"%>
<%@page import="java.math.BigDecimal"%>
<%@page import="java.util.Date"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Map"%>
<%@page import="com.cleanwise.service.api.value.GenericReportColumnView"%>
<%@page import="com.cleanwise.service.api.value.GenericReportColumnViewVector"%>
<%@page import="com.cleanwise.service.api.value.ContractData"%>
<%@page import="com.cleanwise.service.api.value.SiteData"%>
<%@page import="com.cleanwise.service.api.value.AccountData"%>
<%@page import="com.cleanwise.service.api.util.Utility"%>
<%@page import="java.util.Locale"%>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@page import="com.cleanwise.view.utils.ShopTool"%>
<%@page import="com.cleanwise.view.utils.CleanwiseUser"%>
<%@page import="com.cleanwise.service.api.value.GenericReportResultView"%>
<%@page import="com.cleanwise.service.api.value.GenericReportResultViewVector"%>
<%@page import="com.cleanwise.service.api.reporting.ReportingUtils"%>
<%@page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>


<bean:define id="myForm" name="esw.ReportingForm"  type="com.espendwise.view.forms.esw.ReportingForm"/>
<bean:define id="thisReportCd" name="esw.ReportingForm" type="java.lang.String" property="customerReportingForm.reportTypeCd"/>

<%
	String reportingLink = "userportal/esw/reporting.do";
	String reportName = ClwI18nUtil.getMessageOrNull(request, ReportingUtils.makeReportNameKey(thisReportCd));
	
	  if ( reportName == null ) {
	    // No translation found, use the default
	    reportName = thisReportCd;
	}
	boolean isGenericReport = true;
%>

<html:form styleId="reportingStandardId" action="<%=reportingLink %>">
     <html:hidden property="<%=Constants.PARAMETER_OPERATION%>" styleId="operationId"
             value="<%=Constants.PARAMETER_OPERATION_VALUE_REPORT_REULSTS_SUB_TAB%>"/>
	<html:hidden property="reportResultsSubTab" value="" styleId="selectedTabId" />

        <div id="contentWrapper" class="singleColumn clearfix">
            <div id="content">
				<div class="clearfix">
                    <h1 class="main"><%= reportName %></h1>   
					<a href="javascript:setFieldsAndSubmitForm('reportingStandardId','operationId','<%=Constants.PARAMETER_OPERATION_VALUE_DOWNLOAD_REPORT %>')"  class="blueBtnLargeExt">
						<span><app:storeMessage key="global.action.label.exportExcelSheet" /></span>
					</a>
					<a href="javascript:setFieldsAndSubmitForm('reportingStandardId2','operationId2','<%=Constants.PARAMETER_OPERATION_VALUE_CHANGE_STANDARD_FILTER_REPORTS%>')"  class="blueBtnLargeExt">
						<span><app:storeMessage key="global.action.label.changeFilter" /></span>
					</a>	
                </div>             
                
				<!-- Start Box -->
	<!-- Begin: Order Information Report -->
                <logic:match name="esw.ReportingForm" property="customerReportingForm.reportTypeCd" value="<%=Constants.REPORT_NAME_ORDER_INFORMATION %>">									
				 <% isGenericReport = false; 
				 	String orderInfoReport = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "orderInformationReportResults.jsp");
				 %>
					<jsp:include page="<%=orderInfoReport %>"/>
					
				</logic:match>
	<!-- End: Order Information Report -->
	<!-- Begin: Generic Report -->
                 <%
                 	if(isGenericReport) {
                 		String genericReport = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "genericReportResults.jsp");
                 %>
                 	<jsp:include page="<%=genericReport %>"/>
                 <% 		
                 	}
                 %>
    <!-- End: Generic Report -->
                 <!-- End Box -->
                
            </div>
        </div>
   </html:form>
<html:form styleId="reportingStandardId2" action="<%=reportingLink %>">
<%
Map<String, String[]> parameterMap = request.getParameterMap();
if (Utility.isSet(parameterMap)) {
    for (Map.Entry <String, String[]> e : parameterMap.entrySet()) {
        String ev = null;
        for (String v : e.getValue()) {
            ev = Utility.encodeForHTMLAttribute(v);
            %><input type="hidden" name="<%=e.getKey()%>" value="<%=ev%>"<%if (Constants.PARAMETER_OPERATION.equals(e.getKey())) {%> id="operationId2"<%} %> /><%
        }
    }
}
%>
</html:form>