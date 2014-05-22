<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer"%>

<%@ taglib uri='/WEB-INF/struts-tiles.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>
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
%>


<%
String cs = ClwCustomizer.getStoreFilePath(request,"t_logOnBody.jsp");
%>


<template:insert template="cwTemplate.jsp">
  <template:put name="title">
      <app:custom pageElement="pages.title"/>
  </template:put>
<!-- Current IP: <%=currIp%> Original IP: <%=origIp%>		-->
  <template:put name="content" content="<%=cs%>" />
</template:insert>
