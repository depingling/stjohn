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
<bean:define id="siteid" name="SITE_DETAIL_FORM" property="id"/>

<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Application Administrator Home: Site Configuration</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>

<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/admSiteToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>

<div class="text">
<center>
<font color=red>
<html:errors/>
</font>
</center>

<table width="769"  class="mainbody">

<tr>

<td><b>Site&nbsp;Id:</b> </td>
<td>
<bean:write name="SITE_DETAIL_FORM" property="id" scope="session"/>
</td>

<td><b>Site&nbsp;Name:</b> </td>
<td>
<bean:write name="SITE_DETAIL_FORM" property="name" scope="session"/>
</td>

</tr><tr>
<td><b>Account&nbsp;Id:</b></td>
<td>
  <bean:write name="SITE_DETAIL_FORM" property="accountId" scope="session"/>
</td>

<td><b>Account&nbsp;Name:</b></td>
<td>
  <bean:write name="SITE_DETAIL_FORM" property="accountName" scope="session"/>
</td>
</tr><tr>

<td><b>Store&nbsp;Id:</b></td>
<td>
<bean:write name="SITE_DETAIL_FORM" property="storeId" scope="session"/>
</td>

<td><b>Store&nbsp;Name:</b></td>
<td>
<bean:write name="SITE_DETAIL_FORM" property="storeName" scope="session"/>
</td>

</tr>


<html:form name="SITE_CONFIG_FORM" action="adminportal/siteConfig.do"
 scope="session" type="com.cleanwise.view.forms.SiteMgrConfigForm">


<table>
  <tr> <td><b>Find</b></td>
       <td> 
          <html:select name="SITE_CONFIG_FORM" property="configType">
             <html:option value="Catalog">Catalog</html:option>
             <html:option value="Users">Users</html:option>
          </html:select>
       </td>
       <td colspan="2"> 
	  <html:text name="SITE_CONFIG_FORM" property="searchField"/>	
       </td>
  </tr>
  
  <tr> <td colspan="2">&nbsp;</td>
       <td colspan="3">
         <html:radio name="SITE_CONFIG_FORM" property="searchType" value="nameBegins" />
         Name(starts with)
         <html:radio name="SITE_CONFIG_FORM" property="searchType" value="nameContains" />
         Name(contains)
         </td>
  </tr>  
    
  <tr> <td colspan="2">&nbsp;</td>
       <td colspan="3">
        <html:hidden property="siteconfig" value="true"/>
	<html:submit property="action">
		<app:storeMessage  key="global.action.label.search"/>
	</html:submit>
	<html:submit property="action">
		<app:storeMessage  key="admin.button.viewall"/>
	</html:submit>
     </td>
  </tr>

  <tr><td colspan="4">&nbsp;</td>
  </tr>
  
</table>


<logic:present name="site.users.vector">
<bean:size id="rescount"  name="site.users.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">

<table width="769"  class="results">
<tr align=left>
<td><a class="tableheader" href="siteConfig.do?action=sort&siteconfig=true&configType=Users&sortField=id">User Id</td>
<td><a class="tableheader" href="siteConfig.do?action=sort&siteconfig=true&configType=Users&sortField=name">Login Name</td>
<td><a class="tableheader" href="siteConfig.do?action=sort&siteconfig=true&configType=Users&sortField=firstName">First Name</td>
<td><a class="tableheader" href="siteConfig.do?action=sort&siteconfig=true&configType=Users&sortField=lastName">Last Name</td>
<td><a class="tableheader" href="siteConfig.do?action=sort&siteconfig=true&configType=Users&sortField=type">User Type</td>
<td><a class="tableheader" href="siteConfig.do?action=sort&siteconfig=true&configType=Users&sortField=status">Status</td>
<td class="tableheader">Select</td>
</tr>
<logic:iterate id="arrele" name="site.users.vector">
<tr>
<td><bean:write name="arrele" property="userId"/></td>
<td>
<bean:define id="eleid" name="arrele" property="userId"/>
<bean:define id="eletype" name="arrele" property="userTypeCd"/>
<a href="usermgr.do?action=userdetail&searchType=userId&searchField=<%=eleid%>&type=<%=eletype%>">
<bean:write name="arrele" property="userName"/>
</a>
</td>
<td>
<bean:write name="arrele" property="firstName"/>
</td>
<td>
<bean:write name="arrele" property="lastName"/>
</td>
<td>
<bean:write name="arrele" property="userTypeCd"/>
</td>
<td><bean:write name="arrele" property="userStatusCd"/></td>
<td><html:multibox name="SITE_CONFIG_FORM" property="selectIds" value="<%=(\"\"+eleid)%>" /></td>
<html:hidden name="SITE_CONFIG_FORM" property="displayIds" value="<%=(\"\"+eleid)%>" />
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

<logic:present name="site.catalogs.vector">
<bean:size id="rescount"  name="site.catalogs.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">
<table width="769" border="0" class="results">
<tr align=left>
<td><a class="tableheader" href="siteConfig.do?action=sort&siteconfig=true&configType=Catalog&sortField=id">Catalog Id</td>
<td><a class="tableheader" href="siteConfig.do?action=sort&siteconfig=true&configType=Catalog&sortField=name">Name</td>
<td><a class="tableheader" href="siteConfig.do?action=sort&siteconfig=true&configType=Catalog&sortField=status">Status</td>
<td><a class="tableheader" href="siteConfig.do?action=sort&siteconfig=true&configType=Catalog&sortField=type">Type</td>
<td class="tableheader">Select</td>
</tr>

<logic:iterate id="arrele" name="site.catalogs.vector">
<tr>
<td><bean:write name="arrele" property="catalogId"/></td>
<td>
<bean:define id="eleid" name="arrele" property="catalogId"/>
<a href="catalogdetail.do?action=edit&id=<%=eleid%>">
<bean:write name="arrele" property="shortDesc"/>
</a>
</td>
<td><bean:write name="arrele" property="catalogStatusCd"/></td>
<td><bean:write name="arrele" property="catalogTypeCd"/></td>
<td>
  <html:radio name="SITE_CONFIG_FORM" property="catalogId" value="<%=String.valueOf(eleid)%>"/>
</td>
</tr>

</logic:iterate>
<td colspan="4"></td>
<td>
<html:submit property="action">
<app:storeMessage  key="global.action.label.save"/>
</html:submit>
</td>
</table>
</logic:greaterThan>
</logic:present>

</div>

<table cellpadding=0 class="adm_panel">
<tr  class="aton"><td colspan=4>Misc Properties</td></tr>
<logic:iterate id="siteProp" name="SITE_DETAIL_FORM" 
  property="siteData.miscProperties">
<tr>
<td><bean:write name="siteProp" property="propertyTypeCd"/></td>
<td><bean:write name="siteProp" property="shortDesc"/></td>
<td><bean:write name="siteProp" property="value"/></td>
<td><bean:write name="siteProp" property="modDate"/></td>
</tr>
</logic:iterate>
</table>

<table cellpadding=0 class="adm_panel">
<tr  class="aton"><td colspan=4>Data Fields</td></tr>
<logic:iterate id="siteProp" name="SITE_DETAIL_FORM"
  indexId="propIdx" 
  property="siteData.dataFieldProperties"
  type="PropertyData" >
<tr>

<%
String propName = "generalPropertyVal(" + siteProp.getPropertyId() + ")";
%>

<td><bean:write name="siteProp" property="propertyTypeCd"/></td>
<td><bean:write name="siteProp" property="shortDesc"/></td>
<td><html:text name="SITE_CONFIG_FORM"
      property="<%=propName%>"   value="<%=siteProp.getValue()%>" />
</td>
<td><bean:write name="siteProp" property="modDate"/></td>
</tr>
</logic:iterate>
</table>
<html:hidden name="SITE_DETAIL_FORM"
  property="action" value="saveFieldValues"/>
<html:submit value="Save Field Values"/>

<table cellpadding=0 class="adm_panel">
<tr  class="aton"><td colspan=4>Data Fields, Runtime</td></tr>
<logic:iterate id="siteProp" name="SITE_DETAIL_FORM" 
  property="siteData.dataFieldPropertiesRuntime">
<tr>
<td><bean:write name="siteProp" property="propertyTypeCd"/></td>
<td><bean:write name="siteProp" property="shortDesc"/></td>
<td><bean:write name="siteProp" property="value"/></td>
<td><bean:write name="siteProp" property="modDate"/></td>
</tr>
</logic:iterate>
</table>

<bean:define id="siteD" type="SiteData"
  name="SITE_DETAIL_FORM" property="siteData"/>
<pre class="adm_panel"><%=siteD.toBasicInfo()%></pre>

</html:form>

<jsp:include flush='true' page="ui/admFooter.jsp"/>




</body>

</html:html>






