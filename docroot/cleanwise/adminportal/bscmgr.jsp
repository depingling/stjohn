<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="Location" value="dist" type="java.lang.String" toScope="session"/>
<html:html>

<head>
<style>
.tt {
  color: white;
}
.tt1 {
  border-right: solid 1px black;
  border-bottom: solid 1px black;
}
</style>

<link rel="stylesheet" href="../externals/styles.css">
<title>Application Administrator Home: Building Services Contractors</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#FFFFFF">

<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>
<jsp:include flush='true' page="bscmgrBody.jsp"/>
<jsp:include flush='true' page="ui/admFooter.jsp"/>
</body>

</html:html>




