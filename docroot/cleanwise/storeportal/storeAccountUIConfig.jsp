<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ page import="java.util.Iterator"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<script type="text/javascript" language="JavaScript1.2">

function actionSubmit(formNum, action) {
 var actions;
 actions=document.forms[formNum]["action"];
 //alert(actions.length);
 for(ii=actions.length-1; ii>=0; ii--) {
   if(actions[ii].value=='hideAction') {
     actions[ii].value=action;
     document.forms[formNum].submit();
     break;
   }
 }
 return false;
 }

</script>
<script type="text/javascript" src="../externals/ckeditor_3.6/ckeditor.js"></script>

<app:checkLogon/>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<html:html>
<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Account UI Configuration Options </title>
</head>

<body>

<jsp:include flush='true' page="storeAccountCtx.jsp"/>

<div class="text">
  <html:form styleId="494" action="storeportal/storeAccountUIConfig.do"
enctype="multipart/form-data"
name="STORE_UI_CONFIG_FORM"
scope="session"
type="com.cleanwise.view.forms.StoreUIConfigForm">


<bean:define id="BusEntityId" type="java.lang.String" name="Account.id"
  toScope="session"/>
<bean:define id="BusEntityType" type="java.lang.String" value="account"
  toScope="session"/>

<html:hidden name="STORE_UI_CONFIG_FORM" property="busEntityId"
  value="<%=BusEntityId%>"/>

<table ID=495 width="769" class="results" border="0">
  <tr>
  <td colspan=4 valign=top>
    <b>Locale: </b>
    <html:select name="STORE_UI_CONFIG_FORM" property="localeCd" onchange="actionSubmit(0,'localeChange');">
    <html:option value='<%=""%>'>Default</html:option>
    <% for(Iterator iter= ClwI18nUtil.getAllLocales().iterator(); iter.hasNext();){
       String localeCd = (String) iter.next();
    %>
    <html:option value="<%=localeCd%>"><%=localeCd%></html:option>
    <% } %>
    </html:select>
  </td>
</tr>
<tr>
  <td colspan="3"><b><app:storeMessage  key="user.currentUser"/>:</b>
      <html:text name="STORE_UI_CONFIG_FORM" property="userNameForSearch"
           style="width:200px;"/>
      <html:submit property="action"><app:storeMessage key="user.setUser"/></html:submit>
      <logic:notEmpty name="STORE_UI_CONFIG_FORM" property="userInfo">
        <bean:write name="STORE_UI_CONFIG_FORM" property="userInfo.userName"/>
      </logic:notEmpty>
  </td>
</tr>


<tr>
  <td colspan="3" valign="top">
    <b>Center text:</b><br>
    <html:textarea name="STORE_UI_CONFIG_FORM"  styleId="centerText" property="config.mainMsg" rows="10" cols="80" style="width: 100%; height: 200px"/>
	<script type="text/javascript">
			CKEDITOR.replace( 'centerText',
				{
					fullPage : true,
					extraPlugins : 'docprops'
				});
	</script>
  </td>
</tr>
<tr>
  <td colspan="3" valign="top">
    <b>Tips text:</b><br>
    <html:textarea name="STORE_UI_CONFIG_FORM" styleId="tipsText"  property="config.tipsMsg" rows="10" cols="80" style="width: 100%; height: 200px"/>
	<script type="text/javascript">
			CKEDITOR.replace( 'tipsText',
				{
					fullPage : true,
					extraPlugins : 'docprops'
				});
	</script>
  </td>
</tr>
<tr>
  <td colspan="3" valign="top">
    <b>Contact Us text (will overide the default functionality):</b><br>
    <html:textarea name="STORE_UI_CONFIG_FORM" styleId="contactUsText" property="config.contactUsMsg" rows="10" cols="80" style="width: 100%; height: 200px"/>
	<script type="text/javascript">
			CKEDITOR.replace( 'contactUsText',
				{
					fullPage : true,
					extraPlugins : 'docprops'
				});
	</script>
  </td>
</tr>

<bean:define id="theForm" name="STORE_UI_CONFIG_FORM" type="com.cleanwise.view.forms.StoreUIConfigForm"/>

<% String logo1StorageTypeCd = theForm.getConfig().getLogo1StorageTypeCd(); %>
<% String logo2StorageTypeCd = theForm.getConfig().getLogo2StorageTypeCd(); %>
 
<tr>
<td>
Logo Image:
</td>
<td>
   	<html:file name="STORE_UI_CONFIG_FORM"
    property="logo1ImageFile"
	      accept="image/jpeg,image/gif"/>
</td>
<td>
<bean:define id="img1" type="java.lang.String" name="STORE_UI_CONFIG_FORM" property="config.logo1"/>
<% if (Utility.isSet(img1)) { %>
      <% if (logo1StorageTypeCd==null || logo1StorageTypeCd.equals("") || logo1StorageTypeCd.equals(RefCodeNames.BINARY_DATA_STORAGE_TYPE.DATABASE)){ %>
      <%                      
                     String actionImageSubmitStringForDb1 = "/storeportal/storeStoreUIConfig.do?action=itemDocumentFromDb&path=/en/images/" + img1;                             
      %>
                     <br>Database:
                     <html:img styleId="941" action="<%=actionImageSubmitStringForDb1%>"/>
                     </br>
      <% } else { // E3 Storage %>
      <%                      
                     String actionImageSubmitStringForE3Storage1 = "/storeportal/storeStoreUIConfig.do?action=itemDocumentFromE3Storage&path=/en/images/" + img1;                             
      %>
                     <br>E3 Storage:
                     <html:img styleId="941" action="<%=actionImageSubmitStringForE3Storage1%>"/>
                     </br>
      <% } %>
<% } %>
</td>
</tr>

<tr>
<td>
Tips Image:
</td>
<td>
   	<html:file name="STORE_UI_CONFIG_FORM"
    property="logo2ImageFile"
	      accept="image/jpeg,image/gif"/>
</td>
<td>
<bean:define id="img2" type="java.lang.String"
name="STORE_UI_CONFIG_FORM" property="config.logo2"/>
<% if (Utility.isSet(img2)) { %>
      <% if (logo2StorageTypeCd==null || logo2StorageTypeCd.equals("") || logo2StorageTypeCd.equals(RefCodeNames.BINARY_DATA_STORAGE_TYPE.DATABASE)){ %>
       <%                      
                     String actionImageSubmitStringForDb2 = "/storeportal/storeStoreUIConfig.do?action=itemDocumentFromDb&path=/en/images/" + img2;                             
      %>
                     <br>Database:
                     <html:img styleId="941" action="<%=actionImageSubmitStringForDb2%>"/>
                     </br>
      <% } else { // E3 Storage %>
      <%                      
                     String actionImageSubmitStringForE3Storage2 = "/storeportal/storeStoreUIConfig.do?action=itemDocumentFromE3Storage&path=/en/images/" + img2;                             
      %>
                     <br>E3 Storage:
                     <html:img styleId="941" action="<%=actionImageSubmitStringForE3Storage2%>"/>
                     </br>
      
      <% } %>
<% } %>
</td>
</tr>

<tr>
<td>
Toolbar style:
</td>
<td colspan=2>
<html:radio name="STORE_UI_CONFIG_FORM"
  property="config.toolbarStyle" value="textOnly"/>
Text

<html:radio name="STORE_UI_CONFIG_FORM"
  property="config.toolbarStyle" value="textAndImages"/>
Text and Images

</td>
</tr>

<tr>
<td>
Style sheet:
</td>
<td>
<html:select name="STORE_UI_CONFIG_FORM" property="config.styleSheet">
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
  <html:hidden  property="action" value="hideAction"/>
</html:form>
</table>
</div>

</body>
</html:html>
