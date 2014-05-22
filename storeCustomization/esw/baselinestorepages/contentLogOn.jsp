<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>
<%@ page import="com.cleanwise.service.api.util.Utility" %>

<%@page import="com.cleanwise.view.utils.ShopTool"%>
<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<app:setLocaleAndImages/>

<script type="text/javascript">
	function forgottenPassword()
	{
		document.forms[0].action = "sendPassword.do";
		document.forms[0].elements["<%=Constants.PARAMETER_OPERATION%>"].value = "<%=Constants.PARAMETER_OPERATION_VALUE_SEND_PASSWORD%>";
		submitForm();
	}
	
	function confirmRememberMeCookie() {
		if ($('input[name=rememberUserName]').is(':checked')) {
	    	var confirmText = '<%=Utility.encodeForJavaScript(ClwMessageResourcesImpl.getMessage(request,"login.text.confirmRememberMeCookieStart"))%>'
				+ '\n'
				+ '<%=Utility.encodeForJavaScript(ClwMessageResourcesImpl.getMessage(request,"login.text.confirmRememberMeCookieEnd"))%>'
			var confirmation = confirm(confirmText);
			if (!confirmation) {
				$('input[name=rememberUserName]').attr('checked', false);				
			}
		}
	}
</script>
<%
String referer = request.getHeader("referer");
if(!Utility.isSet(referer)){
	referer = ShopTool.getCustomContentURL(request);
}
%>
        <div id="loginWrapper">
            <div class="top">
            	&nbsp;
            </div>
            <div class="loginBox clearfix">
                <img src="../../esw/images/lock-Icon.png" alt="Mascot" title="Mascot" class="mascot" />
     <% String storeLogo = (String)session.getAttribute("pages.store.logo1.image");
      if (Utility.isSet(storeLogo)) { %>
                <img src='<app:custom pageElement="pages.store.logo1.image" addImagePath="true"/>'/>
	<% } %>
				<%
					String errorsAndMessagesPage = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "errorsAndMessages.jsp");
				%>
                 <jsp:include page="<%=errorsAndMessagesPage %>"/>
				<html:form action="userportal/esw/logon.do" focus="username">
              		<label class="login">
              			<app:storeMessage key="login.label.username" />
              		</label>
              		<div class="inputWrapper">
              			<html:text property="username" tabindex="1"/>
              		</div>
              		<label class="login">
              			<app:storeMessage key="login.label.password" />
              		</label>
              		<div class="inputWrapper">
              			<html:password property="password" tabindex="2"/>
              		</div>
                	<p class="forgotPassword">
                  		<html:link href="javascript:forgottenPassword()" tabindex="99">
                  			<app:storeMessage key="login.text.forgotPasswordQst" />
                  		</html:link>
                	</p>
                	<input type="hidden" name="PageVisitTime" value="<%=new java.util.Date()%> ">
                	<html:link href="javascript:submitForm()" styleClass="loginBtn" tabindex="4">
                		<span>
                			<app:storeMessage key="login.login" />
                		</span>
                	</html:link>
                	<p>
                		<label>
                			<html:checkbox property="rememberUserName" tabindex="3" onclick="confirmRememberMeCookie();"/>
              					&nbsp;<app:storeMessage key="login.label.rememberUserName" />
              			</label>
                	</p>
                	<html:hidden property="<%=Constants.PARAMETER_OPERATION%>" value="<%=Constants.PARAMETER_OPERATION_VALUE_LOGIN%>"/>
			<html:hidden property="referer" value="<%=referer%>"/>
			
                	<%
                		//provide a hidden submit button to accomodate user pressing <Enter>
                	%>
                	<html:submit onclick="javascript:submitForm()" style="height:0px; width:0px; border:0px;"/>
				</html:form>
            </div>
            <div class="bottom">
            	&nbsp;
            </div>
            <p>
				<app:custom pageElement="pages.footer.msg"/>
            </p>
        </div>