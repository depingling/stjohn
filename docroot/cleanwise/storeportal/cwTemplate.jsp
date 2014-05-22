
<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri='/WEB-INF/struts-html.tld' prefix='html' %>
<%@ taglib uri='/WEB-INF/application.tld' prefix='app' %>


<html:html>
<head>
<% /*
  <title><template:get name='title'/></title>
 */
%>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<!--  <meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"> -->
<!--  <app:stylesheet/>  -->
  <link rel="stylesheet" href="../externals/styles.css">
  <script src="../externals/lib.js" language="javascript"></script>

</head>

<body leftmargin="0" topmargin="0" marginwidth="0" marginheight="0" bgcolor="white">

<!-- START PAGE HEADER SECTION -->
<div>
  <template:get name="header"/>
</div>
<!-- END HEADER SECTION -->

<!-- START PAGE BODY FRAMEWORK (INSTITUTION LEFT NAV AND CONTENT AREA) -->
<div>
<table ID=215 width="100%" border="0" cellspacing="0" cellpadding="0">
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
</table>
</div>
<!-- END PAGE BODY FRAMEWORK -->

<!-- START PAGE FOOTER SECTION -->
<div>
  <template:get name="footer"/>
</div>
<!-- END FOOTER SECTION -->

</body>
</html:html>
