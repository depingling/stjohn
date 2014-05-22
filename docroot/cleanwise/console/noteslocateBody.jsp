<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Locale" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<html:html>

<head>
<title>Operations Console Home: Order Status</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../externals/styles.css">
</head>

<body>
<pg:pager>
<html:form name="ORDER_OP_DETAIL_FORM" action="/console/orderOpDetail.do"
	type="com.cleanwise.view.forms.OrderOpDetailForm">	
	
  
  <%int index = 1;%>
  <logic:iterate id="itemele" indexId="i" name="ORDER_OP_DETAIL_FORM" property="orderPropertyList" scope="session" type="com.cleanwise.service.api.value.OrderPropertyData">
  <pg:item>  
  <bean:define id="date" name="itemele" property="addDate"/>
  <table>
    <tr><td><b>Note:</b> <%=index%></td></tr>  
	<tr>
	  <td><b>Added By</b>: <bean:write name="itemele" property="addBy"/></td>
	</tr> 
	<tr> 
	  <td><b>Added Date:</b> <i18n:formatDate value="<%=date%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/></td>
	</tr>	
	<tr>
	  <td colspan="2"><bean:write name="itemele" property="value"/></td>	
	</tr>
  </table>
  <hr>	
   	
	<%index++;%>
  </pg:item>
  </logic:iterate>
  
</html:form>
</pg:pager>
</body>
</html:html>
