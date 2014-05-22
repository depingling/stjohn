<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<bean:define id="theForm" name="PIPELINE_MGR_FORM" type="com.cleanwise.view.forms.PipelineMgrForm"/>
<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<html:html>
<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>System Administrator: Pipeline Management</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>
<jsp:include flush='true' page="ui/systemToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>
<jsp:include flush='true' page="pipelinStepsMgrBody.jsp"/>
<jsp:include flush='true' page="ui/admFooter.jsp"/>
</body>

</html:html>




