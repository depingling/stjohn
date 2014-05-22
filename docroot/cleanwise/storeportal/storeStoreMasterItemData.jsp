<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

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
   if(actions[ii].value=='hideAction') {
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
<title>Store Master Item Data Configuration Options </title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>
<jsp:include  flush ='true' page="storeStoreCtx.jsp" />
<html:form  action="storeportal/storeStoreMasterItemData.do"
            name="STORE_STORE_DETAIL_FORM"
            scope="session"
            type="com.cleanwise.view.forms.StoreStoreMgrDetailForm">

<div class="text">
<table width="769" class="results" border="0">

<tr>
<td>Show in Admin</td>
<td></td>
<td>Field Id</td>
<td>Field name</td>
</tr>

<tr>
<td><html:checkbox name="STORE_STORE_DETAIL_FORM" property="masterItemFieldsData.f1ShowAdmin"/></td>
<td></td>
<td>Field 1</td>
<td><html:text name="STORE_STORE_DETAIL_FORM" property="masterItemFieldsData.f1Tag"/></td>
</tr>

<tr>
<td><html:checkbox name="STORE_STORE_DETAIL_FORM" property="masterItemFieldsData.f2ShowAdmin"/></td>
<td></td>
<td>Field 2</td>
<td><html:text name="STORE_STORE_DETAIL_FORM" property="masterItemFieldsData.f2Tag"/></td>
</tr>

<tr>
<td><html:checkbox name="STORE_STORE_DETAIL_FORM" property="masterItemFieldsData.f3ShowAdmin"/></td>
<td></td>
<td>Field 3</td>
<td><html:text name="STORE_STORE_DETAIL_FORM" property="masterItemFieldsData.f3Tag"/></td>
</tr>

<tr>
<td><html:checkbox name="STORE_STORE_DETAIL_FORM" property="masterItemFieldsData.f4ShowAdmin"/></td>
<td></td>
<td>Field 4</td>
<td><html:text name="STORE_STORE_DETAIL_FORM" property="masterItemFieldsData.f4Tag"/></td>
</tr>

<tr>
<td><html:checkbox name="STORE_STORE_DETAIL_FORM" property="masterItemFieldsData.f5ShowAdmin"/></td>
<td></td>
<td>Field 5</td>
<td><html:text name="STORE_STORE_DETAIL_FORM" property="masterItemFieldsData.f5Tag"/></td>
</tr>

<tr>
<td><html:checkbox name="STORE_STORE_DETAIL_FORM" property="masterItemFieldsData.f6ShowAdmin"/></td>
<td></td>
<td>Field 6</td>
<td><html:text name="STORE_STORE_DETAIL_FORM" property="masterItemFieldsData.f6Tag"/></td>
</tr>

<tr>
<td><html:checkbox name="STORE_STORE_DETAIL_FORM" property="masterItemFieldsData.f7ShowAdmin"/></td>
<td></td>
<td>Field 7</td>
<td><html:text name="STORE_STORE_DETAIL_FORM" property="masterItemFieldsData.f7Tag"/></td>
</tr>

<tr>
<td><html:checkbox name="STORE_STORE_DETAIL_FORM" property="masterItemFieldsData.f8ShowAdmin"/></td>
<td></td>
<td>Field 8</td>
<td><html:text name="STORE_STORE_DETAIL_FORM" property="masterItemFieldsData.f8Tag"/></td>
</tr>

<tr>
<td><html:checkbox name="STORE_STORE_DETAIL_FORM" property="masterItemFieldsData.f9ShowAdmin"/></td>
<td></td>
<td>Field 9</td>
<td><html:text name="STORE_STORE_DETAIL_FORM" property="masterItemFieldsData.f9Tag"/></td>
</tr>

<tr>
<td><html:checkbox name="STORE_STORE_DETAIL_FORM" property="masterItemFieldsData.f10ShowAdmin"/></td>
<td></td>
<td>Field 10</td>
<td><html:text name="STORE_STORE_DETAIL_FORM" property="masterItemFieldsData.f10Tag"/></td>
</tr>

<tr>
<td><html:checkbox name="STORE_STORE_DETAIL_FORM" property="masterItemFieldsData.f11ShowAdmin"/></td>
<td></td>
<td>Field 11</td>
<td><html:text name="STORE_STORE_DETAIL_FORM" property="masterItemFieldsData.f11Tag"/></td>
</tr>

<tr>
<td><html:checkbox name="STORE_STORE_DETAIL_FORM" property="masterItemFieldsData.f12ShowAdmin"/></td>
<td></td>
<td>Field 12</td>
<td><html:text name="STORE_STORE_DETAIL_FORM" property="masterItemFieldsData.f12Tag"/></td>
</tr>

<tr>
<td><html:checkbox name="STORE_STORE_DETAIL_FORM" property="masterItemFieldsData.f13ShowAdmin"/></td>
<td></td>
<td>Field 13</td>
<td><html:text name="STORE_STORE_DETAIL_FORM" property="masterItemFieldsData.f13Tag"/></td>
</tr>

<tr>
<td><html:checkbox name="STORE_STORE_DETAIL_FORM" property="masterItemFieldsData.f14ShowAdmin"/></td>
<td></td>
<td>Field 14</td>
<td><html:text name="STORE_STORE_DETAIL_FORM" property="masterItemFieldsData.f14Tag"/></td>
</tr>

<tr>
<td><html:checkbox name="STORE_STORE_DETAIL_FORM" property="masterItemFieldsData.f15ShowAdmin"/></td>
<td></td>
<td>Field 15</td>
<td><html:text name="STORE_STORE_DETAIL_FORM" property="masterItemFieldsData.f15Tag"/></td>
</tr>

<tr>
<td colspan=4 align=center>
<html:button  property="action"  value ="Save" onclick="actionSubmit(0,'saveMasterItemFields');">
</html:button>
</td>
</tr>

</table>
 <html:hidden  property="action" value="hideAction"/>
</div>
</html:form>
</body>
</html:html>





