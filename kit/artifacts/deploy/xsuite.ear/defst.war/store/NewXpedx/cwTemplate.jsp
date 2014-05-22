<%@page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@page import="com.cleanwise.view.utils.Constants" %>
<%@taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@taglib uri='/WEB-INF/struts-logic.tld' prefix='logic' %>
<%@taglib uri='/WEB-INF/application.tld' prefix='app' %>
<html:html>
<head>
<title><template:getAsString name="title" ignore="true"/></title>
  <META HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=utf-8">
  <meta http-equiv="Pragma" content="no-cache">
  <META HTTP-EQUIV="CACHE-CONTROL" CONTENT="NO-CACHE">
  <meta http-equiv="Expires" content="-1">
  <jsp:include flush='true' page="<%=ClwCustomizer.getStoreFilePath(request, \"htmlHeaderIncludes.jsp\")%>"/>
</head>


<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0">
<table id="TopHeaderTable" align="center" border="0" cellpadding="0" cellspacing="0" height="100%">
<tr valign="top">
<td width="20" background='<%=ClwCustomizer.getSIP(request,"leftBorder.gif")%>'>&nbsp;</td>
<td bgcolor="white">&nbsp;&nbsp;&nbsp;</td>
<td bgcolor="white">

<!-- START PAGE HEADER SECTION -->
<div>
  <template:get name="header"/>
</div>
<!-- END HEADER SECTION -->

<!-- START PAGE BODY FRAMEWORK (INSTITUTION LEFT NAV AND CONTENT AREA) -->
<div>
<table align=center CELLSPACING=0 CELLPADDING=0 width="<%=Constants.TABLEWIDTH800%>">
  <tr>
    <!-- START COLUMN 1 OF BODY FRAMEWORK (INSTITUTION LEFT NAV) -->
	<!--
    <td width="140" valign="top" style="color:white;background:#663">
<% /*
      <template:get name="navbar"/>
*/ %>
    </td>
	-->
    <!-- END COLUMN 1 OF BODY FRAMEWORK (INSTITUTION LEFT NAV) -->

    <!-- START COLUMN 2 OF BODY FRAMEWORK (SPACER) -->
<!--    <td width="35"><img ID=216 src="images/1x1.gif" width="35" height="1" /></td>  -->
    <!-- END COLUMN 2 OF BODY FRAMEWORK -->

    <!-- START COLUMN 3 OF BODY FRAMEWORK  CONTENT AREA) -->
    <td width="100%" valign="top" align="center"><template:get name="content"/></td>
    <!-- END COLUMN 3 OF BODY FRAMEWORK -->

    <!-- START COLUMN 4 OF BODY FRAMEWORK (SPACER) -->
<!--    <td width="35"><img ID=217 src="images/1x1.gif" width="35" height="1" /></td>  -->
    <!-- END COLUMN 4 OF BODY FRAMEWORK -->

    <!-- START COLUMN 5 OF BODY FRAMEWORK (RIGHT SIDEBAR) -->
<!--    <td width="70" valign="top" style="background:#666">  -->
      <!-- a right band of color is a template option; could use this space for
       small debug msgs -->
<!--      <img ID=218 src="images/1x1.gif" width="70" height="1" />
    </td>
	-->
    <!-- END COLUMN 5 OF BODY FRAMEWORK -->
  </tr>
<tr><td>

<%if("true".equals(request.getAttribute("renderedErrors"))){%>
 <font color="red"><html:errors/></font>
 <%}%>
</td><tr>
</table>
</div>
<!-- END PAGE BODY FRAMEWORK -->

<!-- START PAGE FOOTER SECTION -->
<div>
  <template:get name="footer"/>
</div>
<!-- END FOOTER SECTION -->
</td>
<td bgcolor="white">&nbsp;&nbsp;&nbsp;</td>
<td width="20" background='<%=ClwCustomizer.getSIP(request,"rightBorder.gif")%>'>&nbsp;</td>
</tr>
</table>

</body>
</html:html>
