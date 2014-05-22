<%@ page language="java" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Text Edit</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<script language="JavaScript1.2">
<!--
function passBackValue() {
  var text = document.getElementById("theText").value;
  window.opener.document.forms[0].elements["<%=request.getParameter("parm")%>"].value = text;
  self.close();
}

function populateTheText() {
  document.getElementById("theText").value = window.opener.document.forms[0].elements["<%=request.getParameter("parm")%>"].value;
}
-->
</script>

<body bgcolor="#FFFFFF" onLoad="javascript:populateTheText();">
<div class="text">
<from name="textForm">
  <textarea id="theText" name="theText" rows="5" cols="80">
  </textarea>
  <br>
  <a href="javascript:void(0);" onclick="return passBackValue()">[ Post Changes ]</a>&nbsp;
  <a href="javascript:void(0);" onclick="javascript:self.close();">[ Close Window ]</a>
</from>
</div>
</html:html>
