<%@page import="com.cleanwise.service.api.util.SessionDataUtil"%>
<%@page import="com.cleanwise.service.api.dto.ReportingDto"%>
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

<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<bean:define id="myForm" name="esw.ReportingForm"  type="com.espendwise.view.forms.esw.ReportingForm"/>

<%
    CleanwiseUser user = (CleanwiseUser) session.getAttribute(Constants.APP_USER);  
%>

<app:setLocaleAndImages/>

<div id="contentWrapper" class="singleColumn clearfix">
    <div id="content">
		    <%
			String errorsAndMessagesPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "errorsAndMessages.jsp");
		    %>
		    <jsp:include page="<%=errorsAndMessagesPage %>"/>
		    
	<!-- Start Box -->
        <div class="boxWrapper">
            <%
		String tabPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "reportingTabs.jsp");
	    %>
	    <jsp:include page="<%=tabPage%>"/>
	    
	    <div class="content">
                <div class="left budgets clearfix">
		    <%
                               	//if no site is selected then do not render anything (there will be an
                               	//error displayed above telling the user they must select a site)
                               	//if (user.getSite() != null) {//STJ-5135 Even if user do not have a current location selected,
    														   //Orders at-a-glance / Budget at-a-glance - Should be available
				
		    %>
		    
		    <div class="rightColumn">
                        <div class="rightColumnIndent">
                            
				<%
				    String graphPanel = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "reportingBudgetsGraph.jsp");
				%>
                                <jsp:include page="<%=graphPanel %>" >
				    <jsp:param name="formBeanName" value="esw.ReportingForm"/>
				</jsp:include>
                      
                        </div>
                    </div>
		    
		    <%
			String filterPanel = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "reportingBudgetsFilters.jsp");
		    %>
		       <jsp:include page="<%=filterPanel %>" >
		             <jsp:param name="formBeanName" value="esw.ReportingForm"/>
		        </jsp:include>
		    
		    <% // } %>
		</div>
	    </div>
	    <div class="bottom clearfix">
                        <span class="left">&nbsp;</span><span class="center">&nbsp;</span><span class="right">&nbsp;</span>
            </div>
	    
	</div>

    </div>
</div>


        