<%@ taglib uri="/WEB-INF/struts-template.tld" prefix="template" %>

<template:insert template="serviceProviderTemplate.jsp">
    <template:put name="header"  content="t_cwHeader.jsp" />
    <template:put name="tabBar01"  content="f_serviceProviderWorkOrderToolbar.jsp" />
    <template:put name="content" content="t_serviceProviderPartsOrder.jsp"/>
    <template:put name="footer"  content="../store/t_footer.jsp" />
</template:insert>
