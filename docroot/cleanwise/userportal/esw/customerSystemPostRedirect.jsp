<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<html>
<head>

<script>
window.onload = function(){
   javascript:submitForm();
}
</script>
<jsp:include flush='true' page="htmlHeaderIncludes.jsp"/>
</head>
<body>
Please Wait...
<%String submit = (String) session.getAttribute(Constants.CUSTOMER_SYSTEM_URL);
  String operation = (String)request.getParameter("operation");
%>
<form method="POST" action="<%=submit%>">
<% if (operation!=null && operation.equals(Constants.PARAMETER_OPERATION_VALUE_END_SHOPPING)) {%>
<input  type="hidden" name="cXML-urlencoded" value="">
<% } else { %>
<bean:define id="theForm" name="esw.CheckOutEswForm"
    type="com.espendwise.view.forms.esw.CheckOutForm"/>
<input  type="hidden" name="<%=theForm.getCheckOutForm().getInlinePostFieldName()%>" value="<%=theForm.getCheckOutForm().getInlinePostData()%>">
<% } %>
</form>
</body>
</html>