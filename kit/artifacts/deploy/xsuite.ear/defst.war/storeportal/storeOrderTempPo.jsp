<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>


<html:html>

<head>
<title>Operations Console Home: Temporary PO</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../externals/styles.css">
</head>

<body bgcolor="#FFFFFF">

<jsp:include flush='true' page="ui/loginInfo.jsp"/>

<div class="text">
<font color=red>
<html:errors/>
</font>

<table ID=1129 border="0" cellpadding="1" cellspacing="0" width="769" class="mainbody">
<html:form styleId="1130" name="STORE_ORDER_TEMP_PO_FORM" action="/storeportal/storeOrderTempPo.do?action=select"
	type="com.cleanwise.view.forms.StoreOrderTemporaryPoForm">

  	<tr>
    	<td colspan="2" class="mediumheader">Select Distributor</td>
		<td colspan="2" class="mediumheader">&nbsp;</td>
	</tr>

	<tr><td colspan="4">&nbsp;</td></tr>

	<tr>
		<td colspan="4">
			<bean:define id="distList" name="STORE_ORDER_TEMP_PO_FORM" property="distList" />
			<html:select name="STORE_ORDER_TEMP_PO_FORM" property="distIdS" onchange="if(this.value !='') { return document.forms[0].submit();} ">
				<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
		        <html:options collection="distList"
                             				property="busEntity.busEntityId" labelProperty="busEntity.shortDesc" />
			</html:select>
		</td>
	</tr>

	<tr><td colspan="4">&nbsp;</td></tr>
	<tr><td colspan="4">&nbsp;</td></tr>

</html:form>
</table>

</div>

<jsp:include flush='true' page="ui/consoleFooter.jsp"/>

</body>
</html:html>
