<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="Location" value="contract" type="java.lang.String" toScope="session"/>
<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Operations Store Admin: Returns</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#FFFFFF">

<%
request.setAttribute("detailURI",request.getContextPath() + "/storeportal/storeReturnsOpDetail.do");
//if ("Create New".equals(request.getParameter("action"))){
        request.setAttribute("detailAction","createReturn");
        request.setAttribute("mode","return");
//}else{
        //request.setAttribute("mode","return");
//}
%>
<jsp:include flush='true' page="storeReturnsOpBody.jsp"/>
</body>
</html:html>




