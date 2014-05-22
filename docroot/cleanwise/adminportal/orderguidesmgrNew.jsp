
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Application Administrator Home: Order Guides, New</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<script language="JavaScript1.2">
<!--
function popLocate(pLoc, name, pDesc) {
  var loc = pLoc + ".do?feedField=" + name + "&amp;feedDesc=" + pDesc;
  locatewin = window.open(loc,"Locate", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();
  return false;
}

//-->
</script>

<body>

<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/orderguidesToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>

<div class="text">
<center>
<font color=red>
<html:errors/>
</font>
</center>

<table border="0" cellpadding="1" cellspacing="0" width="769" class="mainbody">
<html:form name="ORDER_GUIDES_NEW_FORM" 
action="adminportal/orderguidesmgrNew.do"
scope="session"
type="com.cleanwise.view.forms.OrderGuidesMgrNewForm"
>

<tr>
<td colspan="2" class="largeheader">Order Guide Detail</td>
</tr>

<logic:greaterThan name="ORDER_GUIDES_NEW_FORM" property="id" value="0">
<tr>
<td><b>Order Guide&nbsp;Id:</b></td>
<td>
<bean:write name="ORDER_GUIDES_NEW_FORM" property="id"/>
<html:hidden property="id"/>
</td>
</tr>
</logic:greaterThan>

<tr>
 <td><b>Name:</b></td>
<td>
<html:text name="ORDER_GUIDES_NEW_FORM" property="name" size="30" maxlength="30"/>
<span class="reqind">*</span>
</td>
<tr>
<td><b>Order Guide&nbsp;Type:</b></td>
<td>
<html:select 
 name="ORDER_GUIDES_NEW_FORM" 
 property="newOrderGuideType">
<html:option 
value="<%=RefCodeNames.ORDER_GUIDE_TYPE_CD.ORDER_GUIDE_TEMPLATE%>">
<%=RefCodeNames.ORDER_GUIDE_TYPE_CD.ORDER_GUIDE_TEMPLATE%>
</html:option>
</html:select><span class="reqind">*</span>
</td>

</tr>
</table>

<table border="0" cellpadding="1" cellspacing="0" width="769" class="mainbody">
<tr>
<td colspan=4 class="largeheader">
Create an Order Guide from an existing <span class="reqind">*</span>
</td>
</tr>
<tr>

<tr class=rowa>
<td>
<html:radio name="ORDER_GUIDES_NEW_FORM" property="createFrom" 
value="OrderGuide"><b>Order Guide</b>
</html:radio>
</td>
<td><b>Order Guide Id:</b></td>
<td>
<html:text size="6" name="ORDER_GUIDES_NEW_FORM" property="parentOrderGuideId"/>	
<html:button property="action"
onclick="popLocate('orderguidelocate', 'parentOrderGuideId', 'parentOrderGuideName');
document.ORDER_GUIDES_NEW_FORM.createFrom[0].checked = true;
"
value="Locate Order Guide"
/>
</td>
</tr>
<tr class=rowa>
<td>&nbsp;</td>
<td><b>Order Guide Name:</b></td>
<td>
<html:text readonly="true" name="ORDER_GUIDES_NEW_FORM" property="parentOrderGuideName" size="30" styleClass="rowalocatename"/>
</td>
</tr>


<tr class=rowb>
<td>
<html:radio name="ORDER_GUIDES_NEW_FORM" property="createFrom" 
value="Catalog">
<b>Catalog</b>
</html:radio>
</td>

<td><b>Catalog Id:</b></td>
<td>
<html:text size="6" name="ORDER_GUIDES_NEW_FORM" property="parentCatalogId" />	
<html:button property="action"
onclick="popLocate('cataloglocate', 'parentCatalogId', 'parentCatalogName');
document.ORDER_GUIDES_NEW_FORM.createFrom[1].checked = true;
"
value="Locate Catalog"
/>
</td>
<tr class=rowb>
<td>&nbsp;</td>
<td><b>Catalog Name:</b></td>
<td>
<html:text readonly="true" name="ORDER_GUIDES_NEW_FORM" property="parentCatalogName" size="30" styleClass="rowblocatename"/>
</td>
</tr>

<tr class=rowa>
<td>
<html:radio name="ORDER_GUIDES_NEW_FORM" property="createFrom" 
value="Contract"><b>Contract</b>
</html:radio>
</td>
<td><b>Contract Id:</b></td><td>
<html:text size="6" name="ORDER_GUIDES_NEW_FORM" property="parentContractId" />	
<html:button onclick="popLocate('contractlocate', 'parentContractId', 'parentContractName');
document.ORDER_GUIDES_NEW_FORM.createFrom[2].checked = true;
"
value="Locate Contract" property="action"/>
</td>
</tr>
<tr class=rowa>
<td>&nbsp;</td>
<td><b>Contract Name:</b></td>
<td>
<html:text readonly="true" name="ORDER_GUIDES_NEW_FORM" property="parentContractName" size="30" styleClass="rowalocatename"/>
</td>
</tr>

<tr>

<td colspan="3" align="center">
<html:reset>
<app:storeMessage  key="admin.button.reset"/>
</html:reset>
<html:hidden name="ORDER_GUIDES_NEW_FORM" property="action" 
value="createorderguide"/>
<html:submit>
Create Order Guide
</html:submit>
</td>  
</tr>
    
</html:form>

</table>

</div>

<jsp:include flush='true' page="ui/admFooter.jsp"/>
</body>

</html:html>






