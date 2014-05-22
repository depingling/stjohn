
<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<html:html>
<head>

  <title>Troubleshooting</title>
  <jsp:include flush='true' page="../userportal/htmlHeaderIncludes.jsp"/>
</head>
<body marginheight="0" topmargin="0" marginwidth="0" leftmargin="0">

<bean:define id="ip" type="java.lang.String" name="pages.store.images"/>

<%
/* determine the correct area of Troubleshooting  */

String section = (String)request.getParameter("section");
if (section == null){
  section="troubleshooter";
  }
%>

<% 
String hs = ClwCustomizer.getFilePath(session,"t_cwHeader.jsp");
String fs = ClwCustomizer.getFilePath(session,"t_footer.jsp");
String cs = ClwCustomizer.getFilePath(session,"f_troubleshootingController.jsp");
%>
<jsp:include flush='true' page="<%=hs%>"/>


<table align="center" border="0" cellpadding="0" cellspacing="0" width="<%=Constants.TABLEWIDTH%>">
  <tr>
    <td class="tableoutline" width="1" bgcolor="black">
      <img src="<%=ip%>images/cw_spacer.gif" height="1" width="1">
    </td>
    <td>
      <jsp:include flush='true' page="/troubleshooting/troubleshootingToolbar.jsp"/>
    </td>
    <td class="tableoutline" width="1" bgcolor="black">
      <img src="<%=ip%>images/cw_spacer.gif" height="1" width="1">
    </td>
  </tr>

  <tr>
    <td class="tableoutline" width="1" bgcolor="black">
      <img src="<%=ip%>images/cw_spacer.gif" height="1" width="1">
    </td>
    <td>
      &nbsp;
      <!----- begin main content  -->

<jsp:include flush='true' page="<%=cs%>"/>

<jsp:include flush='true' page="/troubleshooting/troubleshooting_bottom.jsp"/>

<jsp:include flush='true' page="<%=fs%>"/>


</body>
</html:html>






