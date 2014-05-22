<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>
<script language="JavaScript1.2">
<!--
function popLocate(pLoc, name, pDesc) {
var loc = pLoc + ".do?feedField=" + name + "&amp;feedDesc=" + pDesc + "&amp;submitFl=true";
locatewin = window.open(loc,"Locate", "menubar=yes,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
locatewin.focus();
return false;
}
//-->
</script>




<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="DIST_DETAIL_FORM" type="com.cleanwise.view.forms.DistMgrDetailForm"/>
<bean:define id="Location" value="dist" type="java.lang.String" toScope="session"/>
<% String action = request.getParameter("action");%>
<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Application Administrator Home: Distributors</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>



<body>

<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/admDistToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>
<jsp:include flush='true' page="distmgrDetailBody.jsp">
   <jsp:param name="portal" 	value="adminportal" /> 
</jsp:include>

<jsp:include flush='true' page="f_distShipFromList.jsp">
   <jsp:param name="portal" 	value="adminportal" /> 
</jsp:include>

<jsp:include flush='true' page="ui/admFooter.jsp"/>

</body>

</html:html>

