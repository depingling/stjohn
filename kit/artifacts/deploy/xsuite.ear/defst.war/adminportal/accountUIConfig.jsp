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
<title>Account UI Configuration Options </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>
<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/admAccountToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>
<jsp:include flush='true' page="ui/accountCtx.jsp"/>

<div class="text">
  <html:form action="adminportal/accountUIConfig.do" 
enctype="multipart/form-data"
name="UI_CONFIG_FORM"
scope="session" 
type="com.cleanwise.view.forms.UIConfigForm">


<bean:define id="BusEntityId" type="java.lang.String" name="Account.id"
  toScope="session"/>
<bean:define id="BusEntityType" type="java.lang.String" value="account"
  toScope="session"/>
  
<html:hidden name="UI_CONFIG_FORM" property="busEntityId"
  value="<%=BusEntityId%>"/>

<table width="769" class="results" border="1">

<tr>
<td colspan=2 valign=top>
Center text:<br>
<html:textarea name="UI_CONFIG_FORM" 
property="config.mainMsg" cols="50" rows="13"/>
</td>

<td>
Tips text:<br>
<html:textarea name="UI_CONFIG_FORM" 
property="config.tipsMsg" rows="13"/>
</td>
</tr>

<tr>
<td>
Logo Image:
</td>
<td>
   	<html:file name="UI_CONFIG_FORM" 
    property="logo1ImageFile"
	      accept="image/jpeg,image/gif"/>
</td>
<td>
<bean:define id="img1" type="java.lang.String"
name="UI_CONFIG_FORM" property="config.logo1"/>
<% if (img1.length() > 0) { %>
<img src="../<%=ip%>images/<%=img1%>">
<br><%=img1%></br>
<% } %>
</td>
</tr>

<tr>
<td>
Tips Image:
</td>
<td>
   	<html:file name="UI_CONFIG_FORM" 
    property="logo2ImageFile"
	      accept="image/jpeg,image/gif"/>
</td>
<td>
<bean:define id="img2" type="java.lang.String"
name="UI_CONFIG_FORM" property="config.logo2"/>
<% if (img2.length() > 0) { %>
<img src="../<%=ip%>images/<%=img2%>">
<br><%=img2%></br>
<% } %>
</td>
</tr>

<tr>
<td>
Toolbar style:
</td>
<td colspan=2>
<html:radio name="UI_CONFIG_FORM" 
  property="config.toolbarStyle" value="textOnly"/>
Text

<html:radio name="UI_CONFIG_FORM" 
  property="config.toolbarStyle" value="textAndImages"/>
Text and Images

</td>
</tr>

<tr>
<td>
Style sheet: 
</td>
<td>
<html:select name="UI_CONFIG_FORM" property="config.styleSheet">
<html:option value="">
-- Select a style sheet --
</html:option>
<html:options  name="admin.css.filenames" />
</html:select>
</td>
<td></td>
</tr>
<tr>
<td colspan=3 align=center>
<html:submit property="action">
<app:storeMessage  key="admin.button.useStoreSettings"/>
</html:submit>
<html:submit property="action">
<app:storeMessage  key="admin.button.preview"/>
</html:submit>
</td>
</tr>
</html:form>
</table>


<jsp:include flush='true' page="ui/admFooter.jsp"/>

</div>

</body>
</html:html>





