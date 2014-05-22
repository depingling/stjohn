<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template'%>
<%@ taglib uri='/WEB-INF/struts-bean.tld' prefix='bean'%>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<template:insert template='cwTemplate.jsp'>
<template:put name='title'><app:storeMessage  key="default.title"/></template:put>
<!--<template:put name='header'  content='cwHeader.jsp' /> -->
<template:put name='content' content='cwUserHomeBody.jsp'/>
<!-- <template:put name='footer'  content='cwFooter.jsp'/>  -->
</template:insert>
