<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<app:checkLogon/>
<%
//init this variable for later
if (session.getAttribute(Constants.CW_LOGOUT_ENABLED) == null) {
    session.setAttribute(Constants.CW_LOGOUT_ENABLED, "false");
}
%>

<div class="headernavigation-container">
	<div class="headernavigation123">
        <div class="headernavigation"><a class="headernavigationL" href="../userportal/logon.do">
           <app:custom pageElement="pages.home.button"/></a></div>

        <div class="headernavigation"><a class="headernavigationL" href="<%=SessionTool.getActualRequestedURI(request) %>?mobile=false">
            <app:storeMessage key="shop.header.text.nonMobile"/></a></div>
    <logic:equal name="<%=Constants.CW_LOGOUT_ENABLED%>" value="true">
        <div class="headernavigation"><a class="headernavigationL" href="../userportal/logoff.do?mobile=true">
            <app:storeMessage key="shop.header.text.logOut"/></a></div>
    </logic:equal>
  </ul>
</div>
<br/>
<br/>