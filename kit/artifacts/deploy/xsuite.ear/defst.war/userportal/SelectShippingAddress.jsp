
<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<app:checkLogon/>

<template:insert template="cwTemplate.jsp">
  <template:put name="title"><app:storeMessage  key="logon.title"/></template:put>
<!--
  <template:put name="content" content="SelectShippingAddressBody.jsp" />
-->
  <template:put name="footer"  content="cwFooter.jsp" />
</template:insert>

