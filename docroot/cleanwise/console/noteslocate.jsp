<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<%
String parm = request.getParameter("parm");
%>

<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Application Console Home: Notes</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body class="results">

<%if(parm.equals("locate")){
%>
  <jsp:include flush='true' page="noteslocateBody.jsp"/>
  <jsp:include flush='true' page="ui/consoleFooter.jsp"/>
<%}else if(parm.equals("add")){%>
  <jsp:include flush='true' page="notesaddBody.jsp"/>
  <jsp:include flush='true' page="ui/consoleFooter.jsp"/>
<%}else{%>
  <jsp:include flush='true' page="orderOpDetailPrint.jsp"/>
  <!-- We dont need the footer for a printable version -->
<%}%>



</body>

</html:html>




