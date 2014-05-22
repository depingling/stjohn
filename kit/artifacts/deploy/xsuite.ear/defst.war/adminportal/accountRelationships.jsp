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
<title>Application Administrator Home: Account Relationships</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>

<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/admAccountToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>

<div class="text">
<center>
<font color=red>
<html:errors/>
</font>
</center>

<table width="769" class="mainbody">

<tr>
<td><b>Store&nbsp;Id:</b> </td>
<td>
<bean:write name="ACCOUNT_DETAIL_FORM" property="storeId"/>
</td>
<td><b>Store&nbsp;Name:</b> </td>
<td>
<bean:write name="ACCOUNT_DETAIL_FORM" property="storeName"/>
</td>
</tr>

<tr>
<td><b>Account&nbsp;Id:</b> </td>
<td>
<bean:write name="ACCOUNT_DETAIL_FORM" property="id"/>
</td>
<td><b>Account&nbsp;Name:</b> </td>
<td>
<bean:write name="ACCOUNT_DETAIL_FORM" property="name"/>
</td>
</tr>

</table>

<table> <tr>
<html:form name="RELATED_FORM" action="adminportal/related.do" method="POST" scope="session" type="com.cleanwise.view.forms.RelatedForm">

<logic:equal name="RELATED_FORM" property="searchForType" value="users">
<tr>
  <td>
    <html:select name="RELATED_FORM" property="searchForType" onchange="document.forms[0].submit();">
      <html:options collection="Related.options.vector" property="label" labelProperty="value" />
    </html:select>
  </td>
</tr>
<tr> 
  <td align="right">
    <b>Search Users:</b>
  </td>
  <td>
    <html:text name="RELATED_FORM" property="searchForName"/>

     <html:radio name="RELATED_FORM" property="searchType" value="id" />  ID
     <html:radio name="RELATED_FORM" property="searchType" value="nameBegins" />  Name(starts with)
     <html:radio name="RELATED_FORM" property="searchType" value="nameContains" />  Name(contains)
  </td>
</tr>

<tr> 
  <td align="right"><b>First Name:</b></td>
  <td><html:text name="RELATED_FORM" property="firstName"/> </td>
</tr>

<tr> 
  <td align="right"><b>Last Name:</b></td>
  <td><html:text name="RELATED_FORM" property="lastName"/> </td>
</tr>

<tr>
  <td align="right"><b>Type:</b></td>
  <td>
    <html:select name="RELATED_FORM" property="userType" >
      <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
      <html:options  collection="Users.types.vector" property="value" />
    </html:select>
  </td>
</tr>

</logic:equal>

<logic:equal name="RELATED_FORM" property="searchForType" value="sites">
<tr>
  <td>
    <html:select name="RELATED_FORM" property="searchForType" onchange="document.forms[0].submit();">
      <html:options collection="Related.options.vector" property="label" labelProperty="value" />
    </html:select>
  </td>
</tr>
<tr> 
  <td align="right"><b>Find Site</b></td>
<td>
<html:text name="RELATED_FORM" property="searchForName"/>
<html:radio name="RELATED_FORM" property="searchType" 
    value="id" />  ID
<html:radio name="RELATED_FORM" property="searchType" 
    value="nameBegins" />  Name(starts with)
<html:radio name="RELATED_FORM" property="searchType" 
    value="nameContains" />  Name(contains)
</td>
</tr>

<tr> 
<td align="right"><b>City</b></td>
<td><html:text name="RELATED_FORM" property="city"/> </td>
</tr>

<tr> 
<td align="right"><b>State</b></td>
<td><html:text name="RELATED_FORM" property="state"/> </td>
</tr>



</logic:equal>

<logic:notEqual name="RELATED_FORM" property="searchForType" value="users">
<logic:notEqual name="RELATED_FORM" property="searchForType" value="sites">
<td><b>Find Relationships:</b></td>
<td>
<html:text name="RELATED_FORM" property="searchForName" size="10"/>
<html:select name="RELATED_FORM" property="searchForType" onchange="document.forms[0].submit();">
<html:options collection="Related.options.vector" 
property="label"
labelProperty="value" 
/>
</html:select>
</td></tr>
  <tr> <td><b>Search By:</b></td>
       <td colspan="3">
         <html:radio name="RELATED_FORM" property="searchType" value="nameBegins" />
         Name(starts with)
         <html:radio name="RELATED_FORM" property="searchType" value="nameContains" />
         Name(contains)
         </td>
  </tr>
</logic:notEqual>
</logic:notEqual>


<html:hidden property="action" value="account"/>

<tr>
<td></td>
<td colspan="3">
<html:submit property="viewAll">
<app:storeMessage  key="global.action.label.search"/>
</html:submit>
<html:submit property="viewAll">
<app:storeMessage  key="admin.button.viewall"/>
</html:submit>
</td>
</tr>

</html:form>

</table>


<logic:present name="Related.sites.vector">
<bean:size id="rescount"  name="Related.sites.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">
<table width="769"  class="results">
<tr align=left>
<td><a class="tableheader" href="accountRelationships.do?action=sort&type=site&name=Related.sites.vector&field=id">Site Id</td>
<td><a class="tableheader" href="accountRelationships.do?action=sort&type=site&name=Related.sites.vector&field=name">Name</td>
<td><a class="tableheader" href="accountRelationships.do?action=sort&type=site&field=address&name=Related.sites.vector&field=address">Street Address</td>
<td><a class="tableheader" href="accountRelationships.do?action=sort&type=site&name=Related.sites.vector&field=city">City</td>
<td><a class="tableheader" href="accountRelationships.do?action=sort&type=site&name=Related.sites.vector&field=state">State<br>Province</td>
<td><b>Zip</b></td>
<td><b>County</b></td>
<td><a class="tableheader" href="accountRelationships.do?action=sort&type=site&name=Related.sites.vector&field=status">Status</td>
</tr>
<logic:iterate id="arrele" name="Related.sites.vector">
<tr>
<td><bean:write name="arrele" property="busEntity.busEntityId"/></td>
<td>
<bean:define id="eleid" name="arrele" property="busEntity.busEntityId"/>
<a href="sitemgrDetail.do?action=sitedetail&searchType=id&searchField=<%=eleid%>">
<bean:write name="arrele" property="busEntity.shortDesc"/>
</a>
</td>
<td>
<bean:write name="arrele" property="siteAddress.address1"/>
</td>
<td>
<bean:write name="arrele" property="siteAddress.city"/>
</td>
<td>
<bean:write name="arrele" property="siteAddress.stateProvinceCd"/>
</td>
    <td><bean:write name="arrele" property="siteAddress.postalCode"/></td>
    <td><bean:write name="arrele" property="siteAddress.countyCd"/></td>

<td><bean:write name="arrele" property="busEntity.busEntityStatusCd"/></td>
</tr>
</logic:iterate>
</table>
</logic:greaterThan>
</logic:present>

<logic:present name="Related.catalogs.vector">
<bean:size id="rescount"  name="Related.catalogs.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">
<table width="769" border="0" class="results">
<tr align=left>
<td><a class="tableheader" href="accountRelationships.do?action=sort&type=catalog&name=Related.catalogs.vector&field=id">Catalog Id</td>
<td><a class="tableheader" href="accountRelationships.do?action=sort&type=catalog&name=Related.catalogs.vector&field=name">Name</td>
<td><a class="tableheader" href="accountRelationships.do?action=sort&type=catalog&name=Related.catalogs.vector&field=type">Type</td>
<td><a class="tableheader" href="accountRelationships.do?action=sort&type=catalog&name=Related.catalogs.vector&field=status">Status</td>
</tr>
<logic:iterate id="arrele" name="Related.catalogs.vector">
<tr>
<td><bean:write name="arrele" property="catalogId"/></td>
<td>
<bean:define id="eleid" name="arrele" property="catalogId"/>
<a href="catalogdetail.do?action=edit&id=<%=eleid%>">
<bean:write name="arrele" property="shortDesc"/>
</a>
</td>
<td><bean:write name="arrele" property="catalogTypeCd"/></td>
<td><bean:write name="arrele" property="catalogStatusCd"/></td>
</tr>
</logic:iterate>
</table>
</logic:greaterThan>
</logic:present>


<logic:present name="Related.users.vector">
<bean:size id="rescount"  name="Related.users.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">
<table width="769"  class="results">
<tr align=left>
<td><a class="tableheader" href="accountRelationships.do?action=sort&type=user&name=Related.users.vector&field=id">User Id</td>
<td><a class="tableheader" href="accountRelationships.do?action=sort&type=user&name=Related.users.vector&field=name">Login Name</td>
<td><a class="tableheader" href="accountRelationships.do?action=sort&type=user&name=Related.users.vector&field=firstname">First Name</td>
<td><a class="tableheader" href="accountRelationships.do?action=sort&type=user&name=Related.users.vector&field=lastname">Last Name</td>
<td><a class="tableheader" href="accountRelationships.do?action=sort&type=user&name=Related.users.vector&field=type">Type</td>
<td><a class="tableheader" href="accountRelationships.do?action=sort&type=user&name=Related.users.vector&field=status">Status</td>
</tr>

<logic:iterate id="arrele" name="Related.users.vector">
<tr>
<td><bean:write name="arrele" property="userId"/></td>
<td>
<bean:define id="eleid" name="arrele" property="userId"/>
<a href="usermgr.do?action=userdetail&searchType=userId&searchField=<%=eleid%>">
<bean:write name="arrele" property="userName"/>
</a>
</td>
<td><bean:write name="arrele" property="firstName"/></td>
<td><bean:write name="arrele" property="lastName"/></td>
<td><bean:write name="arrele" property="userTypeCd"/></td>
<td><bean:write name="arrele" property="userStatusCd"/></td>
</tr>
</logic:iterate>
</table>
</logic:greaterThan>
</logic:present>


<logic:present name="Related.contracts.vector">
<bean:size id="rescount"  name="Related.contracts.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">
<table width="769"  class="results">
<tr align=left>
<td><a class="tableheader" href="accountRelationships.do?action=sort&type=contract&name=Related.contracts.vector&field=id">Contract Id</td>
<td><a class="tableheader" href="accountRelationships.do?action=sort&type=contract&name=Related.contracts.vector&field=name">Name</td>
<td><a class="tableheader" href="accountRelationships.do?action=sort&type=contract&name=Related.contracts.vector&field=catalog">Catalog Name</td>
<td><a class="tableheader" href="accountRelationships.do?action=sort&type=contract&name=Related.contracts.vector&field=status">Status</td>
</tr>

<logic:iterate id="arrele" name="Related.contracts.vector">
<tr>
<td><bean:write name="arrele" property="contractId"/></td>
<td>
<bean:define id="eleid" name="arrele" property="contractId"/>
<a href="contractdetail.do?action=edit&id=<%=eleid%>">
<bean:write name="arrele" property="contractName"/>
</a>
</td>
<td><bean:write name="arrele" property="catalogName"/></td>
<td><bean:write name="arrele" property="status"/></td>
</tr>
</logic:iterate>
</table>
</logic:greaterThan>
</logic:present>


<logic:present name="Related.guides.vector">
<bean:size id="ogres"  name="Related.guides.vector"/>
Search result count:  <bean:write name="ogres" />
<logic:greaterThan name="ogres" value="0">
<table width="769"  class="results">
<tr>
<td><a class="tableheader" href="accountRelationships.do?action=sort&type=orderguide&name=Related.guides.vector&field=id">Order Guide Id</td>
<td><a class="tableheader" href="accountRelationships.do?action=sort&type=orderguide&name=Related.guides.vector&field=name">Name</td>
<td><a class="tableheader" href="accountRelationships.do?action=sort&type=orderguide&name=Related.guides.vector&field=catalog">Catalog Name</td>
<td><a class="tableheader" href="accountRelationships.do?action=sort&type=orderguide&name=Related.guides.vector&field=status">Status</td>
</tr>
<logic:iterate id="ogele" name="Related.guides.vector">
<tr>
<td><bean:write name="ogele" property="orderGuideId"/></td>
<td>
<bean:define id="ogid" name="ogele" property="orderGuideId"/>
<a href="orderguidesmgr.do?action=detail&searchType=id&searchField=<%=ogid%>">
<bean:write name="ogele" property="orderGuideName"/>
</a>
</td>
<td><bean:write name="ogele" property="catalogName"/></td>
<td><bean:write name="ogele" property="status"/></td>
</tr>
</logic:iterate>
</table>
</logic:greaterThan>
</logic:present>

<jsp:include flush='true' page="ui/admFooter.jsp"/>

</div>

</body>

</html:html>




