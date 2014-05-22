<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="java.util.Iterator"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<script language="JavaScript1.2">
 <!--
function actionSubmit(formNum, action) {
 var actions;
 actions=document.forms[formNum]["action"];
 //alert(actions.length);
 for(ii=actions.length-1; ii>=0; ii--) {
   if(actions[ii].value=='BBBBBBB') {
     actions[ii].value=action;
     document.forms[formNum].submit();     
     break;
   }
 }
 return false;
 }
-->
</script>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<html:html>
<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Store UI Configuration Options </title>
</head>


<!-- store ui cfg -->

<body>
<html:form action="adminportal/storeUIConfig.do?cfg=store"
enctype="multipart/form-data"
name="UI_CONFIG_FORM"
scope="request"
type="com.cleanwise.view.forms.UIConfigForm">
<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/admStoreToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>

<div class="text">

<bean:define id="BusEntityId" type="java.lang.String"
  name="STORE_DETAIL_FORM" property="id"
  toScope="session"/>
<bean:define id="BusEntityType" type="java.lang.String" value="store"
  toScope="session"/>

<html:hidden name="UI_CONFIG_FORM" property="busEntityId"
  value="<%=BusEntityId%>"/>

<table border="0" cellpadding="0" cellspacing="0" width="769" class="mainbody">
  <tr>
  <td><b>Store&nbsp;Id:</b></td><td><%=BusEntityId%></td>
  <td><b>Store&nbsp;Name:</b></td><td>
<bean:write name="STORE_DETAIL_FORM" property="name"/></td>
  </tr>
</table>


<table width="769" class="results" border="0">
<tr>
  <td colspan=4 valign=top>
    <b>Locale: </b>
    <html:select name="UI_CONFIG_FORM" property="localeCd" onchange="actionSubmit(0,'localeChange');">
    <html:option value='<%=""%>'>Default</html:option>
    <% for(Iterator iter=ClwI18nUtil.getAllLocales().iterator(); iter.hasNext();){ 
       String localeCd = (String) iter.next();
    %>
    <html:option value="<%=localeCd%>"><%=localeCd%></html:option>
    <% } %>
    </html:select>
  </td>
</tr>                      

<tr>
  <td colspan=2 valign=top>
    <b>Center text:</b><br>
    <html:textarea name="UI_CONFIG_FORM" property="config.mainMsg" cols="50" rows="13"/>
  </td>
  <td>
    <b>Tips text:</b><br>
    <html:textarea name="UI_CONFIG_FORM" property="config.tipsMsg" rows="13"/>
  </td>
</tr>

<tr>
  <td colspan="2" valign="top">
    <b>Footer text:</b><br>
    <html:textarea name="UI_CONFIG_FORM" property="config.footerMsg" cols="50" rows="13"/>
  </td>
  <td>
    <b>Page Title:</b><br>
    <html:text  name="UI_CONFIG_FORM" property="config.pageTitle"/>
    <br>
    <b>Home Button Label:</b><br>
    <html:text  name="UI_CONFIG_FORM" property="config.homePageButtonLabel"/>
    <br>
    <b>Customer Service Alias:</b> (What you call your customer service department)<br>
    <html:text  name="UI_CONFIG_FORM" property="config.customerServiceAlias"/>
  </td>
</tr>

<tr>
  <td>
    <b>Logo Image:</b>
  </td>
  <td>
        <html:file name="UI_CONFIG_FORM"  property="logo1ImageFile" accept="image/jpeg,image/gif"/>
  </td>
  <td>
    <bean:define id="img1" type="java.lang.String" name="UI_CONFIG_FORM" property="config.logo1"/>
    <% if (img1.length() > 0) { %>
      <img src="../<%=ip%>images/<%=img1%>">
      <br><%=img1%></br>
    <% } %>
  </td>
</tr>

<tr>
  <td>
    <b>Tips Image:</b>
  </td>
  <td>
    <html:file name="UI_CONFIG_FORM" property="logo2ImageFile"accept="image/jpeg,image/gif"/>
  </td>
  <td>
    <bean:define id="img2" type="java.lang.String"name="UI_CONFIG_FORM" property="config.logo2"/>
    <% if (img2.length() > 0) { %>
      <img src="../<%=ip%>images/<%=img2%>">
      <br><%=img2%></br>
    <% } %>
  </td>
</tr>

<tr>
  <td>
    <b>Toolbar style:</b><br>
    This will indicate wheather you want images along the runtime menu (shop, training, product selector, troubleshooter, MSDS)
  </td>
  <td colspan=2>
    <html:radio name="UI_CONFIG_FORM" property="config.toolbarStyle" value="textOnly"/>Text
    <html:radio name="UI_CONFIG_FORM" property="config.toolbarStyle" value="textAndImages"/>Text and Images
  </td>
</tr>

<tr>
  <td>
    <b>Style sheet:</b>
  </td>
  <td>
    <html:select name="UI_CONFIG_FORM" property="config.styleSheet">
    <html:options  name="admin.css.filenames" />
    </html:select>
  </td>
  <td></td>
</tr>
<tr>
  <td colspan=3 align=center>
    <html:submit property="action">
      <app:storeMessage  key="admin.button.preview"/>
    </html:submit>
    <!-- html:submit property="action"
      bean:message key="global.action.label.save"
    html:submit -->
  </td>
</tr>


</table>
</div>
  <html:hidden  property="action" value="BBBBBBB"/>
</html:form>
</body>
</html:html>
