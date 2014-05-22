<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Locale" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Application Administrator Home: Sites</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>

<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/admSiteToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>

<div class="text">
<center>
<font color=red>
<html:errors/>
</font>
</center>

<jsp:include flush='true' page="ui/siteInfo.jsp"/>
<jsp:include flush='true' page="profilingFoundInc.jsp">
        <jsp:param name="detailHref" value="sitemgrProfilingDetail.do" />
</jsp:include>
<jsp:include flush='true' page="ui/admFooter.jsp"/>
</div>
</body>

</html:html>