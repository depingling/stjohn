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
	String feedField =  (String)request.getParameter("feedField");
	if(null == feedField) {
		feedField = new String("");
	}
%>

<script language="JavaScript1.2">
<!--

function selfClose() {

	var feedBackFieldName = '<%=feedField%>';
	if(feedBackFieldName && ""!= feedBackFieldName) {
      window.opener.document.forms[0].elements[feedBackFieldName].value='distributorAssign';
      window.opener.document.forms[0].submit();
      self.close();
    }
}

//-->
</script>


<html:html>

<head>
<script language="JavaScript1.2">
<!--
 selfClose();
//-->
</script>
<input type="hidden" name="feedField" value="<%=feedField%>">

</html:html>




