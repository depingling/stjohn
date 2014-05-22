<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Store Administrator Home: Select a Group</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body class="results">

<script language="JavaScript1.2">
<!--

function passIdAndName(id, name) {
  var feedBackFieldName = document.GROUP_FORM.feedField.value;
  var feedDesc = document.GROUP_FORM.feedDesc.value;
  var submitFl = document.GROUP_FORM.submitFl.value;

  if(feedBackFieldName && ""!= feedBackFieldName) {
    window.opener.document.forms[0].elements[feedBackFieldName].value = id;
  }
  if(feedDesc && ""!= feedDesc) {
    window.opener.document.forms[0].elements[feedDesc].value = unescape(name.replace(/\+/g, ' '));
  }
  if(submitFl && submitFl=="true") {
    window.opener.document.forms[0].submit();
  }

  self.close();
}

//-->
</script>

<jsp:include flush='true' page="../storeportal/groupmgrSearch.jsp"/>


</body>

</html:html>




