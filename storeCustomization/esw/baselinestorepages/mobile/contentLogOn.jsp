<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>

<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<app:setLocaleAndImages/>
<% request.getHeader("User-Agent"); %>
<script type="text/javascript">
	function forgottenPassword()
	
	{
		document.forms[0].action = "sendPassword.do";
		document.forms[0].elements["<%=Constants.PARAMETER_OPERATION%>"].value = "<%=Constants.PARAMETER_OPERATION_VALUE_SEND_PASSWORD%>";
		submitForm();
	}
	function viewWebsite()
	{
		alert("in ");
		document.getElementById('operationId').value = 'viewWebsite';
		document.forms[0].action = "dashboard.do";
		submitForm();
	}
</script>
        <div id="loginWrapper">
                <div class="logo">
                	<!--<img src="images/mobile/Janpak-Logo.png" alt="eSpendwise" title="eSpendwise" />-->
                	<img src='<app:custom pageElement="pages.logo1.image" addImagePath="true" encodeForHTML="true"/>'/>
            	</div>
            	<div class="message hide">
                	<p>Please enter user name and password to log in.</p>
            	</div>
            	<%
					String errorsAndMessagesPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "errorsAndMessages.jsp");
				%>
                 <jsp:include page="<%=errorsAndMessagesPage %>"/>
					<html:form action="userportal/esw/logon.do" focus="username">
                	<input type="hidden" name="PageVisitTime" value="<%=new java.util.Date()%> ">
                	<html:hidden property="<%=Constants.PARAMETER_OPERATION%>" value="<%=Constants.PARAMETER_OPERATION_VALUE_LOGIN%>" 
                				 styleId="operationId"/>
              		<label><app:storeMessage key="login.label.username"/></label>
              		<div class="inputWrapper"><html:text property="username" tabindex="1"/></div>
              		<label><app:storeMessage key="login.label.password"/></label>
              		<div class="inputWrapper"><html:password property="password" tabindex="2"/></div>
                <a href="javascript:submitForm()" class="loginBtn" tabIndex="3"><span><app:storeMessage key="login.login"/></span></a>                	
				</html:form>
				
<!-- 				<div class="footer" style="float:left; clear: both; "> -->
        		 <p><a href="logon.do?web=true" tabIndex="4"><app:storeMessage key="mobile.esw.orders.label.visitFullWebsite"/></a></p>
                	<p><app:custom pageElement="pages.footer.msg"/></p>
<!--             	</div> -->
        </div>