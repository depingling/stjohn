<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>

<%@ page import="com.cleanwise.view.utils.SessionTool" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import= "com.cleanwise.service.api.APIAccess" %>
<%@ page import= "com.cleanwise.service.api.session.Request" %>
<%@ page import= "com.cleanwise.service.api.session.PropertyService" %>
<%@ page import="java.util.*" %>
<%@ page import="org.owasp.esapi.ESAPI" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="Location" value="contract" type="java.lang.String" toScope="session"/>
<bean:define id="theForm" name="ANALYTIC_REPORT_FORM" type="com.cleanwise.view.forms.AnalyticReportForm"/>
<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>ReportingConsole</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> 
<script src="../externals/lib.js" language="javascript"></script>
<script src="../externals/table-sort.js" language="javascript"></script>
<logic:present name="pages.css">
	<logic:equal name="pages.css" value="styles.css">
		<link rel="stylesheet" href='../externals/<app:custom pageElement="pages.css"/>'>
	</logic:equal>
<logic:notEqual name="pages.css" value="styles.css">
	<link rel="stylesheet" href='../externals/styles.css'>
		<link rel="stylesheet" href='../externals/<app:custom pageElement="pages.css"/>'>
</logic:notEqual>
</logic:present>
  <logic:notPresent name="pages.css">
    <link rel="stylesheet" href='../externals/styles.css'>
  </logic:notPresent>


</head>

<body>
<center>

<%
  CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
  String footerMsg = ClwI18nUtil.getMessage(request, "uncustomized.footer.copyright", null);
  if (appUser.isaReportingUser()){
    String val = (String)session.getAttribute("pages.logo1.image");
    footerMsg = (String)session.getAttribute("pages.footer.msg");
  }

%>
<%@ include file="ui/reportingToolbar.jsp"%>
<%--<jsp:include flush='true' page="ui/reportingToolbar.jsp"/>--%>
<div class="rptmid">
<%
   String action = (String) request.getAttribute("action");
   if(action==null) request.getParameter("action");

   if (action==null ||
       action.equals("Locate Site")||
       action.equals("init")||action.equals("Return Selected")||
       action.equals("Cancel")||action.equals("Set Filter")||
       action.equals("Clear Site Filter")||action.equals("Clear Filter")||
       action.equals("sort")||
       action.equals("Locate Account")||
       action.equals("Clear Account Filter")||
       action.equals("Locate Item")||
       action.equals("Clear Item Filter")||
       action.equals("Locate Manufacturer")||
       action.equals("Clear Manufacturer Filter")||
       action.equals("Locate Distributor")||
       action.equals("Clear Distributor Filter")||
       action.equals("Locate Catalog")||
       action.equals("Clear Catalog Filter")||
       action.equals("report")||
       action.equals("detail")||              // schedule action
       action.equals("Create Schedule")||     // schedule action
       action.equals("View Report")||
       action.equals("falseSubmit")||
       action.equals("checkReportState")||
       action.equals("Download Report")||
       action.equals("saveSelectedStore")||
       action.equals("saveSelectedStoreDW")||
       action.equals("saveSelectedUserDW")||
       action.equals("Run Report")){
%>
<% if(theForm.getReportId()<=0) {%>
<jsp:include flush='true' page="analyticRepBody.jsp"/>
<% } else { %>
<%    if(((action == null) ||
          (action != null && !(action.equals("init") || action.equals("report"))) )&&
          theForm.getScheduleFl()) {%>
<jsp:include flush='true' page="reportScheduleDetail.jsp"/>
<%    } else { %>
<jsp:include flush='true' page="analyticRepRequest.jsp"/>
<%    } %>
<% } %>
<% } else if(action.equals("prepared") ||
       action.equals("protect")||
       action.equals("Set Archive Filter")||
       action.equals("Clear Archive Filter")||
       action.equals("Delete Selected Results") ||
       action.equals("nextSheet"))
  {
%>
<jsp:include flush='true' page="analyticRepArchive.jsp"/>
<% } %>
</div>
<logic:notEmpty name="ANALYTIC_REPORT_FORM" property="reportResults"><jsp:include flush='true' page="f_customerGenericReport.jsp"/></logic:notEmpty>

<%@ include file="ui/reportingFooter.jsp"%>
<%--<jsp:include flush='true' page="ui/reportingFooter.jsp"/>--%>

</center>
</body>

<% String  errorMessage = (String) request.getAttribute("errorMessage");
  if(errorMessage!=null) {
	  errorMessage = ESAPI.encoder().decodeForHTML(errorMessage);
	  errorMessage = ESAPI.encoder().encodeForJavaScript(errorMessage);
%>
<script language="javascript">
alert('<%=errorMessage%>');
</script>
<%
}
%>
</html:html>
