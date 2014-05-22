<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.i18n.ClwMessageResourcesImpl"%>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser"%>

<%@page import="com.cleanwise.view.utils.ShopTool"%>
<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%
CleanwiseUser user = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
String userName = user.getUser().getUserName();
String resetPasswordLink= "userportal/esw/resetPassword.do?operation=" + Constants.PARAMETER_OPERATION_VALUE_RESET_PASSWORD+"&userId="+user.getUser().getUserId();

%>

<app:setLocaleAndImages/>
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
				<html:form action="<%=resetPasswordLink%>" focus="oldPassword">
              		<label class="login">
              			<app:storeMessage key="login.label.username" />&nbsp;&nbsp;
              			<%=userName %>
              		</label>
              		<label class="login">
              			<app:storeMessage key="shop.userProfile.text.oldPassword" />
              		</label>
              		<div class="inputWrapper">
              			<html:password property="oldPassword" tabindex="1"/>
              		</div>
              		<label class="login">
              			<app:storeMessage key="shop.userProfile.text.newPassword" />
              		</label>
              		<div class="inputWrapper">
              			<html:password property="newPassword" tabindex="2"/>
              		</div>
              		<label class="login">
              			<app:storeMessage key="shop.userProfile.text.confirmPassword" />
              		</label>
              		<div class="inputWrapper">
              			<html:password property="confirmPassword" tabindex="3"/>
              		</div>
              		
              		<html:link href="javascript:submitForm()" styleClass="loginBtn" tabindex="4">
                		<span>
                			<app:storeMessage key="global.action.label.submit" />
                		</span>
                	</html:link>
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