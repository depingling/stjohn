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
   document.CHECKOUT_FORM.submit();
}
</script>
<jsp:include flush='true' page="../userportal/htmlHeaderIncludes.jsp"/>
</head>
<body>
Please Wait...
<%String submit = (String) session.getAttribute(Constants.CUSTOMER_SYSTEM_URL);%>
<form name="CHECKOUT_FORM" method="POST" action="<%=submit%>">
<bean:define id="theForm" name="CHECKOUT_FORM" type="com.cleanwise.view.forms.CheckoutForm"/>
<input  type="hidden" name="<%=theForm.getInlinePostFieldName()%>" value="<%=theForm.getInlinePostData()%>">
</form>
</body>
</html>