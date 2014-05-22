<%@page import="com.cleanwise.service.api.util.SessionDataUtil"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames"%>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.espendwise.view.forms.esw.ModuleIntegrationForm" %>
<%@ page import="com.cleanwise.service.api.value.StoreMessageView" %>

<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

  <meta http-equiv="Pragma" content="no-cache">  
  <meta http-equiv="Expires" content="-1">
  <meta http-equiv="Cache-Control" content="no-cache">

<%
			SessionDataUtil sessionData = Utility.getSessionDataUtil(request);
        	//If StJohn is accessed from Neptune application, Only Content section needs to be returned. 
        	//Sience following files are already included in Neptune application, No need to include them again.
        	//1. jquery.min.js, 2.jquery-ui.min.js, 3. jquery-ui-i18n.min.js and 4.library.js
        	if(!sessionData.isContentOnly()) {
%>
        <script type="text/javascript" src="../../externals/jquery/1.4.4/jquery.min.js"></script>
        <script type="text/javascript" src="../../externals/jqueryui/1.8.9/jquery-ui.min.js"></script>
		<script type="text/javascript" src="../../externals/jqueryui/1.8.14/i18n/jquery-ui-i18n.min.js"></script>
        
		<script type="text/javascript" src="../../externals/esw/library.js"></script>
		<script type="text/javascript" src="../../externals/esw/scripts.js"></script>
<%
		}
%>

		<script type="text/javascript" src="../../externals/esw/highcharts.js"></script>
		<script type="text/javascript" src="../../externals/esw/themes.js"></script>
		<script type="text/javascript" src="../../externals/esw/exporting.js"></script>
    
    	<%
    		//create a script to retrieve the various user-visible text strings and other
    		//values required by the Bridgeline scripts (scripts.js)
    		StringBuilder landingPageUrlBuilder = new StringBuilder(100);
    		landingPageUrlBuilder.append(pageContext.getSession().getAttribute("pages.root"));
    		landingPageUrlBuilder.append("/userportal/esw/showModule.do");
    		
    		//define variable that make the 'I accept'checkbox in forced read window.
    		ModuleIntegrationForm mForm = (ModuleIntegrationForm)request.getAttribute("esw.ModuleIntegrationForm");
    		String ackVisibility = "hidden";
    		String closeBtnClass = "blueBtnMed closePopUpBtn";
    		String closeBtnLink ="refreshLandingPage";
    		if(mForm != null){
    			StoreMessageView currentMsg = mForm.getCurrentMessage();
    			if(currentMsg != null){
      			if( currentMsg.getMessageType().equals(
      					RefCodeNames.MESSAGE_TYPE_CD.ACKNOWLEDGEMENT_REQUIRED)){
      				ackVisibility = "visible";
      				closeBtnClass = "blueBtnMed blueBtnMedDisabled closePopUpBtn";
      				closeBtnLink = "javascript:return false";
      			}
      		}
    		}
    		boolean includeBaseCss = !"showMessage".equals(request.getParameter("operation"));
    	%>
	    <script>
	    	//define variables and methods needed by scripts.js
	    	var ackVisibility = '<%=ackVisibility%>';
	    	var closeBtnClass = '<%=closeBtnClass%>';
	    	var closeBtnLink = '<%=closeBtnLink%>';
	    	var date_default = '<%=ClwI18nUtil.getDatePattern(request)%>';
	    	var noOrderSelectedForApproval = '<%=Utility.encodeForJavaScript(ClwMessageResourcesImpl.getMessage(request,"error.order.noOrderSelectedForApproval"))%>';
	    	var singleOrderApproval = '<%=Utility.encodeForJavaScript(ClwMessageResourcesImpl.getMessage(request,"message.order.confirmOrderApproval"))%>';

	    	// Following two lines are combined to complete the wording "Are you sure you want to approve (x) orders?"
	    	var orderApprovalPreSize = '<%=Utility.encodeForJavaScript(ClwMessageResourcesImpl.getMessage(request,"message.order.confirmMultipleOrderApprovalStart"))%>' + '&nbsp;';
	    	var orderApprovalPostSize = '&nbsp;' + '<%=Utility.encodeForJavaScript(ClwMessageResourcesImpl.getMessage(request,"message.order.confirmMultipleOrderApprovalEnd"))%>';
	    	
	    	var noOrderSelectedForRejection = '<%=Utility.encodeForJavaScript(ClwMessageResourcesImpl.getMessage(request,"error.order.noOrderSelectedForRejection"))%>';
	    	var singleOrderRejection = '<%=Utility.encodeForJavaScript(ClwMessageResourcesImpl.getMessage(request,"message.order.confirmOrderRejection"))%>';

	    	// Following two lines are combined to complete the wording "Are you sure you want to approve (x) orders?"
	    	var orderRejectionPreSize = '<%=Utility.encodeForJavaScript(ClwMessageResourcesImpl.getMessage(request,"message.order.confirmMultipleOrderRejectionStart"))%>' + '&nbsp;';
	    	var orderRejectionPostSize = '&nbsp;' + '<%=Utility.encodeForJavaScript(ClwMessageResourcesImpl.getMessage(request,"message.order.confirmMultipleOrderRejectionEnd"))%>';

	    	var saveBtnText = '<%=Utility.encodeForJavaScript(ClwMessageResourcesImpl.getMessage(request,"global.action.label.save"))%>';
	    	var okBtnText = '<%=Utility.encodeForJavaScript(ClwMessageResourcesImpl.getMessage(request,"global.label.ok"))%>';
	    	var cancelBtnText = '<%=Utility.encodeForJavaScript(ClwMessageResourcesImpl.getMessage(request,"global.action.label.cancel"))%>';
	    	var loadingText = '<%=Utility.encodeForJavaScript(ClwMessageResourcesImpl.getMessage(request,"global.label.loading"))%>';
	    	var closeBtnText = '<%=Utility.encodeForJavaScript(ClwMessageResourcesImpl.getMessage(request,"global.action.label.close"))%>';
	    	var acceptChkBoxText = '<%=Utility.encodeForJavaScript(ClwMessageResourcesImpl.getMessage(request,"global.label.iAccept"))%>';
	    	var loadingImageUrl = "../../esw/images/loading.gif";
	    	var landingPageUrl = '<%=landingPageUrlBuilder.toString()%>';

	    	var scheduleRecurrenceWeekly = '<%=RefCodeNames.ORDER_SCHEDULE_RULE_CD.WEEK%>';
	    	var scheduleRecurrenceMonthly = '<%=RefCodeNames.ORDER_SCHEDULE_RULE_CD.DAY_MONTH%>';
	    	var scheduleRecurrenceCustom = '<%=RefCodeNames.ORDER_SCHEDULE_RULE_CD.DATE_LIST%>';
	    	var dateSeperator = '<%=Constants.MULTIPLE_DATE_SEPARATOR%>';

	    	function initializeDatePickers() {
		    	<%=Utility.getDatePickerInitializationCalls(ClwI18nUtil.getUserLocale(request), ClwI18nUtil.getCountryDateFormat(request))%>
	    	}
	    </script>
        
<%
        	//Since base.css and profile.css are already included in the Neptune application, 
			//No need to include them.
			if(!sessionData.isContentOnly()) {
				if (includeBaseCss) {
%>
					<link href="../../externals/esw/base.css" rel="Stylesheet" type="text/css" media="all" />
<%              } // base.css not need for the show message pages %>

		        <link href="../../externals/esw/profile.css" rel="Stylesheet" type="text/css" media="all" />
		
<%
			} 
%>
		<link href="../../externals/esw/jquery.ui.datepicker.css" rel="Stylesheet" type="text/css" media="all" />
		<link href="../../externals/esw/jquery.ui.datepicker.esw.css" rel="Stylesheet" type="text/css" media="all" />
        <link href="../../externals/esw/print.css" rel="Stylesheet" type="text/css" media="print" />
        <!--[if IE 8]><link href="../../externals/esw/ie8.css" rel="stylesheet" type="text/css" media="screen" /><![endif]-->
        <!--[if IE 7]><link href="../../externals/esw/ie7.css" rel="stylesheet" type="text/css" media="screen" /><![endif]-->
        <!--[if IE 6]><link href="../../externals/esw/ie6.css" rel="stylesheet" type="text/css" media="screen" /><![endif]-->
        
		<jsp:include page='googleAnalytics.jsp'/>

		<jsp:include page='<%=ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "htmlHeadersStore.jsp")%>'/>


