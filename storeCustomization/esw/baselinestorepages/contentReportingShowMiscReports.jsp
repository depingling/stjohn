<%@page import="com.cleanwise.service.api.util.SessionDataUtil"%>
<%@page import="com.cleanwise.service.api.dto.OrderSearchDto"%>
<%@page import="com.cleanwise.service.api.dto.LocationBudgetChartDto"%>
<%@ page contentType="text/html; charset=UTF-8" %>
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
<%@page import="com.cleanwise.service.api.reporting.ReportingUtils"%>

<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<bean:define id="reportingForm" name="esw.ReportingForm"  type="com.espendwise.view.forms.esw.ReportingForm"/>
<%

CleanwiseUser user = (CleanwiseUser) session.getAttribute(Constants.APP_USER);  
String reportingLink = "userportal/esw/reporting.do";

%>

<script type="text/javascript">
	
	function showReportFilter(reportId){
		document.getElementById('reportTypeCodeId').value=reportTypeCode;
		document.getElementById('reportDescId').value=reportTypeDesc;
		submitForm('reportingStandardId');
	}
</script>


<app:setLocaleAndImages/>

<html:form styleId="reportingStandardId" action="<%=reportingLink %>">
     <html:hidden property="<%=Constants.PARAMETER_OPERATION%>" 
             value="<%=Constants.PARAMETER_OPERATION_VALUE_SHOW_STANDARD_FILTER_REPORTS%>"/>

	<html:hidden styleId="reportEncId" name="esw.ReportingForm" property="reportEncodedId" value="" />
	
<div id="contentWrapper" class="singleColumn clearfix">
    <div id="content">
				<!-- Begin: Error Message -->
				<%
					String errorsAndMessagesPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "errorsAndMessages.jsp");
				%>
				<jsp:include page="<%=errorsAndMessagesPage %>"/>
				<!-- End: Error Message -->

		<!-- Start Box -->
        <div class="boxWrapper squareCorners smallMargin firstBox">
<%
	String tabPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "reportingTabs.jsp");
%>
<jsp:include page="<%=tabPage%>"/>
			<div class="content">
				<div class="left clearfix">

					<h1  class="main"><app:storeMessage key="reporting.label.allReports" /></h1>
				</div>
			</div>	    
	    	<div class="bottom clearfix">
        		<span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span>
        	</div>
		</div>
	<!-- End Box -->
	<!-- Start Box -->
<%
String groupCategory = "";
long factor = Constants.MULTIPLICATION_FACTOR;
%>
	<div class="boxWrapper smallMargin squareBottom">
    	<div class="top clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span>
    	</div>
		<div class="content">
			<div class="content">
            	<div class="left clearfix">
<logic:iterate name="reportingForm" property="customerReportingForm.genericReportingInfo.matchedReports" type="com.cleanwise.service.api.value.GenericReportView" id="item">
<%
	String category = item.getReportCategory();
	String reportName = item.getReportName();
	String description = "";
	if(Utility.isSet(item.getLongDesc())) {
		description = item.getLongDesc();
	}

 	String reportName4mMR = ClwI18nUtil.getMessageOrNull(request, ReportingUtils.makeReportNameKey(reportName));
 	String reportDesc4mMR = ClwI18nUtil.getMessageOrNull(request, ReportingUtils.makeReportDescriptionKey(reportName));
	if (Utility.isSet(reportName4mMR)) {
	    reportName = reportName4mMR;
	}

	if (Utility.isSet(reportDesc4mMR)) {
	    description = reportDesc4mMR;
	}
	
	long reportId = (((Integer) item.getGenericReportId()).longValue() * factor) + factor ;
	if (groupCategory.equals(category) == false) {
	    if (groupCategory.equals("") == false) {
%>
						</tbody>
					</table>
<%
		}
	    groupCategory = category;
%>
					<div class="twoColBox">
						<div class="column width100">
							<h3><bean:write name="item" property="reportCategory" /></h3>
						</div>
					</div>
					<table>
                    	<colgroup>
							<col />
							<col />
						</colgroup>
						<thead>
                        	<tr>
								<th><app:storeMessage key="reporting.label.reportName" /></th>
								<th><app:storeMessage key="reporting.label.description" /></th>
							</tr>
						</thead>
						<tbody>
<%
	}
%>
							<tr>
                            	<td>
                            		<a href="javascript:setFieldsAndSubmitForm('reportingStandardId','reportEncId','<%=reportId %>')">
                                          <%=Utility.encodeForHTML(reportName) %>
                                     </a>
                            		
								</td>
								<td>
								  <%=Utility.encodeForHTML(description) %>
								</td>
							</tr>
</logic:iterate>
						</tbody>
					</table>
				</div>
			</div>
            <div class="bottom clearfix"><span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span></div>
		</div>
		<!-- End Box -->
		</div>
    </div>
</div>
</html:form>
