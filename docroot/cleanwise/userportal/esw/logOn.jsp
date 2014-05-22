<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@ page import="com.cleanwise.view.utils.Constants"%>

<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>

<%
String origIp="";
String currIp=null;
Cookie[] cc = request.getCookies();
if(cc != null){
for(int i=0;i<cc.length;i++){
	Cookie c = cc[i];
	if("ORIG_IP".equalsIgnoreCase(c.getName())){
		origIp = c.getValue();
	}
}
}
currIp = (String) request.getHeader("X-Forwarded-For");
if(currIp == null){
	currIp = request.getRemoteAddr();
}
StringBuilder ipAddressInfo = new StringBuilder(25);
ipAddressInfo.append("\n<!-- Current IP: ");
ipAddressInfo.append(currIp);
ipAddressInfo.append(" Original IP: ");
ipAddressInfo.append(origIp);
ipAddressInfo.append(" -->");
%>
<%
//no header for the login window;
%>
<%
//override the default <body> tag
StringBuilder body = new StringBuilder(25);
body.append("<body class=");
body.append("\"login\"");
body.append(">"); 
%>
<%
String content = ClwCustomizer.getStoreFilePath(request, Constants.PORTAL_ESW, "contentLogOn.jsp");
%>
<%
//no footer for the message window
%>

<template:insert template="template.jsp">
  <template:put name="title">
      <%= Constants.COMPANY_NAME %>
  </template:put>
  <template:put name="ipAddressInfo" content="<%=ipAddressInfo.toString()%>" />
  <template:put name="body">
  	<%=body.toString() %>
  </template:put>
  <template:put name="content" content="<%=content%>" />
</template:insert>
