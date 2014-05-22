<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="domainid" name="DOMAIN_ADM_DETAIL_FORM" property="id"/>

<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Application Administrator Home: Domain Configuration</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>

<table border=0 width="<%=Constants.TABLEWIDTH%>" cellpadding="0" cellspacing="0">
    <tr>
        <td>
            <jsp:include flush='true' page="ui/systemToolbar.jsp"/>
        </td>
    </tr>
    <tr>
        <td>
            <jsp:include flush='true' page="ui/loginInfo.jsp"/>
        </td>
    </tr>
    <tr>
        <td>
            <jsp:include flush='true' page="ui/admDomainToolbar.jsp"/>
        </td>
    </tr>
</table>

<div class="text">
<center>
<font color=red>
<html:errors/>
</font>
</center>

<table width="769"  class="mainbody">

<tr>

<td><b>Domain&nbsp;Id:</b> </td>
<td>
<bean:write name="DOMAIN_ADM_DETAIL_FORM" property="id" scope="session"/>
</td>

<td><b>Domain&nbsp;Name:</b> </td>
<td>
<bean:write name="DOMAIN_ADM_DETAIL_FORM" property="name" scope="session"/>
</td>

</tr>

<html:form name="DOMAIN_ADM_CONFIGURATION_FORM" action="adminportal/domainAdmConfiguration.do"
 scope="session" type="com.cleanwise.view.forms.DomainAdmConfigurationForm">


<table>
  <tr> <td><b>Find</b></td>
       <td> 
          <html:select name="DOMAIN_ADM_CONFIGURATION_FORM" property="configType">
             <html:option value="Stores">Store</html:option>
          </html:select>
       </td>
       <td colspan="2"> 
	  <html:text name="DOMAIN_ADM_CONFIGURATION_FORM" property="configSearchField"/>	
       </td>
  </tr>
  
  <tr> <td colspan="2">&nbsp;</td>
       <td colspan="3">
         <html:radio name="DOMAIN_ADM_CONFIGURATION_FORM" property="configSearchType" value="nameBegins" />
         Name(starts with)
         <html:radio name="DOMAIN_ADM_CONFIGURATION_FORM" property="configSearchType" value="nameContains" />
         Name(contains)
         </td>
  </tr>  
    
  <tr> <td colspan="2">&nbsp;</td>
       <td colspan="3">
        <html:hidden property="domainconfig" value="true"/>
	<html:submit property="action">
		<app:storeMessage  key="global.action.label.search"/>
	</html:submit><!--
	<html:submit property="action">
		<app:storeMessage  key="admin.button.viewall"/>
	</html:submit>
     --></td>
  </tr>

  <tr><td colspan="4">&nbsp;</td>
  </tr>
  
</table>


<logic:present name="domain.stores.vector">
<bean:size id="rescount"  name="domain.stores.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">

<table width="769"  class="results">
<tr align=left>
<td class="tableheader">Store Id</td>
<td class="tableheader">Name</td>
<td class="tableheader">Status</td>
<td class="tableheader">Select</td>
<td class="tableheader">Default</td>
</tr>
<logic:iterate id="arrele" name="domain.stores.vector">
<tr>
<td><bean:write name="arrele" property="busEntityId"/></td>
<td>
<bean:define id="eleid" name="arrele" property="busEntityId"/>

<bean:write name="arrele" property="shortDesc"/>

</td>
<td>
<bean:write name="arrele" property="busEntityStatusCd"/>
</td>

<td><html:multibox name="DOMAIN_ADM_CONFIGURATION_FORM" property="selectIds" value="<%=(\"\"+eleid)%>" /></td>
<td><html:radio name="DOMAIN_ADM_CONFIGURATION_FORM" property="defaultStoreId" value="<%=(\"\"+eleid)%>" /></td>
<html:hidden name="DOMAIN_ADM_CONFIGURATION_FORM" property="displayIds" value="<%=(\"\"+eleid)%>" />
<html:hidden name="DOMAIN_ADM_CONFIGURATION_FORM" property="displayDefaultStoreIds" value="<%=(\"\"+eleid)%>" />
</tr>
</logic:iterate>
<td colspan="6"></td>
<td>
<html:submit property="action">
<app:storeMessage  key="global.action.label.save"/>
</html:submit>
</td>
</table>

</logic:greaterThan>
</logic:present>

</div>


</html:form>

<jsp:include flush='true' page="ui/admFooter.jsp"/>

</body>

</html:html>






