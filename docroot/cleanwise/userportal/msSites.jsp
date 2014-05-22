
<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<app:checkLogon/>

<template:insert template="cwTemplate.jsp">
  <template:put name="title">
    <app:custom pageElement="pages.title"/>
  </template:put>
  <template:put name='header' content='cwHeader.jsp'/>
  <template:put name='content' content='msbhomeBody.jsp'/>
</template:insert>
