<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames"%>
<%@ page import="com.cleanwise.service.api.util.Utility" %>

<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
   		<meta name="viewport" content="width=device-width; initial-scale=1.0; maximum-scale=1.0; user-scalable=0;" />
        <meta name="apple-mobile-web-app-capable" content="yes" />
         <META content="MSHTML 6.00.2900.3698" name=GENERATOR>
        <script type="text/javascript" src="../../externals/jquery/1.4.4/jquery.min.js"></script> 
        <script type="text/javascript" src="../../externals/esw/mobile/library.js"></script>
        <!-- YK duplicated -- script type="text/javascript" src="../../externals/esw/mobile/jquery.min.js"></script --> 
		<script type="text/javascript" src="../../externals/esw/mobile/scripts.js"></script> 
		<script type="text/javascript" src="../../externals/esw/scripts.js"></script>
        <link href="../../externals/esw/mobile/mobile.css" rel="Stylesheet" type="text/css" media="all" />
        <jsp:include page='<%=ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "htmlHeadersStore.jsp")%>'/>
		<script>
		//define variables and methods needed by mobile/scripts.js
		var date_default = '<%=ClwI18nUtil.getDatePattern(request)%>';
		var noOrderSelected = '<%=Utility.encodeForJavaScript(ClwMessageResourcesImpl.getMessage(request,"error.order.noOrderSelected"))%>';
    	var noOrderSelectedForApproval = '<%=Utility.encodeForJavaScript(ClwMessageResourcesImpl.getMessage(request,"error.order.noOrderSelectedForApproval"))%>';
    	var singleOrderApproval = '<%=Utility.encodeForJavaScript(ClwMessageResourcesImpl.getMessage(request,"message.order.confirmOrderApproval"))%>';

    	// Following two lines are combined to complete the wording "Are you sure you want to approve (x) orders?"
    	var orderApprovalPreSize = '<%=Utility.encodeForJavaScript(ClwMessageResourcesImpl.getMessage(request,"message.order.confirmMultipleOrderApprovalStart"))%>' + '&nbsp;';
    	var orderApprovalPostSize = '&nbsp;' + '<%=Utility.encodeForJavaScript(ClwMessageResourcesImpl.getMessage(request,"message.order.confirmMultipleOrderApprovalEnd"))%>';
    	
    	var noOrderSelectedForRejecting = '<%=Utility.encodeForJavaScript(ClwMessageResourcesImpl.getMessage(request,"error.order.noOrderSelectedForRejection"))%>';
    	var singleOrderRejection = '<%=Utility.encodeForJavaScript(ClwMessageResourcesImpl.getMessage(request,"message.order.confirmOrderRejection"))%>';

    	// Following two lines are combined to complete the wording "Are you sure you want to approve (x) orders?"
    	var orderRejectionPreSize = '<%=Utility.encodeForJavaScript(ClwMessageResourcesImpl.getMessage(request,"message.order.confirmMultipleOrderRejectionStart"))%>' + '&nbsp;';
    	var orderRejectionPostSize = '&nbsp;' + '<%=Utility.encodeForJavaScript(ClwMessageResourcesImpl.getMessage(request,"message.order.confirmMultipleOrderRejectionEnd"))%>';

    	var saveBtnText = '<%=Utility.encodeForJavaScript(ClwMessageResourcesImpl.getMessage(request,"global.action.label.save"))%>';
    	var okBtnText = '<%=Utility.encodeForJavaScript(ClwMessageResourcesImpl.getMessage(request,"global.label.ok"))%>';
    	var cancelBtnText = '<%=Utility.encodeForJavaScript(ClwMessageResourcesImpl.getMessage(request,"global.action.label.cancel"))%>';
    	var loadingText = '<%=Utility.encodeForJavaScript(ClwMessageResourcesImpl.getMessage(request,"global.label.loading"))%>';
    	var closeBtnText = '<%=Utility.encodeForJavaScript(ClwMessageResourcesImpl.getMessage(request,"global.action.label.close"))%>';
		</script>


