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

<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Application Administrator Home: Catalog Relationships</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>

<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/catalogToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>
<jsp:include flush='true' page="ui/catalogInfo.jsp"/>

<div class="text">
<center>
<font color=red>
<html:errors/>
</font>
</center>

<table> <tr>
<html:form name="RELATED_FORM" action="adminportal/related.do"
    method="POST"
    scope="session" type="com.cleanwise.view.forms.RelatedForm">
<td><b>Find Relationships:</b></td>
<td>
<html:text name="RELATED_FORM" property="searchForName" size="10"/>	
<html:select name="RELATED_FORM" property="searchForType">
<html:options collection="Related.options.vector" 
property="label"
labelProperty="value" 
/>
</html:select>
</td>
</tr>
  <tr> <td><b>Search By:</b></td>
       <td colspan="3">
         <html:radio name="RELATED_FORM" property="searchType" value="nameBegins" />
         Name(starts with)
         <html:radio name="RELATED_FORM" property="searchType" value="nameContains" />
         Name(contains)
         </td>
  </tr>

<html:hidden property="action" value="catalog"/>

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

<logic:present name="Related.users.vector">
<bean:size id="rescount"  name="Related.users.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">
<table width="769"  class="results">
<tr align=left>
<td><a class="tableheader" href="catalogRelationships.do?action=sort&type=user&name=Related.users.vector&field=id">User&nbsp;Id </a></td>
<td><a class="tableheader" href="catalogRelationships.do?action=sort&type=user&name=Related.users.vector&field=name">Login Name </a></td>
<td><a class="tableheader" href="catalogRelationships.do?action=sort&type=user&name=Related.users.vector&field=firstName">First Name </a></td>
<td><a class="tableheader" href="catalogRelationships.do?action=sort&type=user&name=Related.users.vector&field=lastName">Last Name </a></td>
<td><a class="tableheader" href="catalogRelationships.do?action=sort&type=user&name=Related.users.vector&field=type">User Type Code </a></td>
<td><a class="tableheader" href="catalogRelationships.do?action=sort&type=user&name=Related.users.vector&field=status">Status Code </a></td>
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

<logic:present name="Related.contract.vector">
<bean:size id="rescount"  name="Related.contract.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">
<table width="769"  class="results">
<tr align=left>
<td><a class="tableheader" href="catalogRelationships.do?action=sort&type=contract&name=Related.contract.vector&field=id">Contract&nbsp;Id </a></td>
<td><a class="tableheader" href="catalogRelationships.do?action=sort&type=contract&name=Related.contract.vector&field=name">Name </a></td>
<td><a class="tableheader" href="catalogRelationships.do?action=sort&type=contract&name=Related.contract.vector&field=catalog">Catalog Name </a></td>
<td><a class="tableheader" href="catalogRelationships.do?action=sort&type=contract&name=Related.contract.vector&field=status">Status</a></td>
</tr>

<logic:iterate id="arrele" name="Related.contract.vector">
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

<logic:present name="Related.orderguide.vector">
<bean:size id="ogres"  name="Related.orderguide.vector"/>
Search result count:  <bean:write name="ogres" />
<logic:greaterThan name="ogres" value="0">
<table width="769"  class="results">
<tr>
<td><a class="tableheader" href="catalogRelationships.do?action=sort&type=orderguide&name=Related.contract.vector&field=id">Order Guide Id</a></td>
<td><a class="tableheader" href="catalogRelationships.do?action=sort&type=orderguide&name=Related.contract.vector&field=name">Name</a></td>
<td><a class="tableheader" href="catalogRelationships.do?action=sort&type=orderguide&name=Related.contract.vector&field=catalog">Catalog Name</a></td>
<td><a class="tableheader" href="catalogRelationships.do?action=sort&type=orderguide&name=Related.contract.vector&field=status">Status</a></td>
</tr>
<logic:iterate id="ogele" name="Related.orderguide.vector">
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


<logic:present name="Related.distributor.vector">
<bean:size id="rescount"  name="Related.distributor.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">
<table width="769"  class="results">
  <tr align=left>
    <td><a class="tableheader" href="catalogRelationships.do?action=sort&type=distributor&name=Related.distributor.vector&field=id">Distributor&nbsp;Id </a></td>
    <td><a class="tableheader" href="catalogRelationships.do?action=sort&type=distributor&name=Related.distributor.vector&field=name">Name </a></td>
    <td><a class="tableheader" href="catalogRelationships.do?action=sort&type=distributor&name=Related.distributor.vector&field=address">Address 1</a></td>
    <td><a class="tableheader" href="catalogRelationships.do?action=sort&type=distributor&name=Related.distributor.vector&field=city">City</a></td>
    <td><a class="tableheader" href="catalogRelationships.do?action=sort&type=distributor&name=Related.distributor.vector&field=state">State</a></td>
    <td><a class="tableheader" href="catalogRelationships.do?action=sort&type=distributor&name=Related.distributor.vector&field=status">Status</a></td>
  </tr>

<logic:iterate id="arrele" name="Related.distributor.vector">
<tr>
<td><bean:write name="arrele" property="busEntity.busEntityId"/></td>
<td>
<bean:define id="eleid" name="arrele" property="busEntity.busEntityId"/>
<a href="distmgr.do?action=distdetail&searchType=id&searchField=<%=eleid%>">
<bean:write name="arrele" property="busEntity.shortDesc"/>
</a>
</td>
<td>
<bean:write name="arrele" property="primaryAddress.address1"/>
</td>
<td>
<bean:write name="arrele" property="primaryAddress.city"/>
</td>
<td>
<bean:write name="arrele" property="primaryAddress.stateProvinceCd"/>
</td>

<td><bean:write name="arrele" property="busEntity.busEntityStatusCd"/></td>
</tr>
</logic:iterate>
</table>
</logic:greaterThan>
</logic:present>

<logic:present name="Related.site.vector">
<bean:size id="rescount"  name="Related.site.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">
<table width="769"  class="results">
  <tr align=left>
    <td><a class="tableheader" href="catalogRelationships.do?action=sort&type=site&name=Related.site.vector&field=id">Site Id</a></td>
    <td><a class="tableheader" href="catalogRelationships.do?action=sort&type=site&name=Related.site.vector&field=account">Account Name</a></td>
    <td><a class="tableheader" href="catalogRelationships.do?action=sort&type=site&name=Related.site.vector&field=name">Name</a></td>
    <td><a class="tableheader" href="catalogRelationships.do?action=sort&type=site&name=Related.site.vector&field=address">Street Address</a></td>
    <td><a class="tableheader" href="catalogRelationships.do?action=sort&type=site&name=Related.site.vector&field=city">City</a></td>
    <td><a class="tableheader" href="catalogRelationships.do?action=sort&type=site&name=Related.site.vector&field=state">State/Province</a></td>
    <td><a class="tableheader" href="catalogRelationships.do?action=sort&type=site&name=Related.site.vector&field=status">Status</a></td>
  </tr>
<logic:iterate id="arrele" name="Related.site.vector">
<tr>
<td><bean:write name="arrele" property="busEntity.busEntityId"/></td>
<td><bean:write name="arrele" property="accountBusEntity.shortDesc"/></td>
<td>
<bean:define id="eleid" name="arrele" property="busEntity.busEntityId"/>
<a href="sitemgr.do?action=sitedetail&searchType=id&searchField=<%=eleid%>">
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
<td><bean:write name="arrele" property="busEntity.busEntityStatusCd"/></td>
</tr>
</logic:iterate>
</table>
</logic:greaterThan>
</logic:present>


<logic:present name="Related.account.vector">
<bean:size id="rescount"  name="Related.account.vector"/>
 Search Account Relationships Results:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">
<table width="769"  class="results">
<tr align=left>
<td><a class="tableheader" href="catalogRelationships.do?action=sort&type=account&name=Related.account.vector&field=id">Account Id </a></td>
<td><a class="tableheader" href="catalogRelationships.do?action=sort&type=account&name=Related.account.vector&field=name">Name </a></td>
<td><a class="tableheader" href="catalogRelationships.do?action=sort&type=account&name=Related.account.vector&field=city">City </a></td>
<td><a class="tableheader" href="catalogRelationships.do?action=sort&type=account&name=Related.account.vector&field=state">State/Province </a></td>
<td><a class="tableheader" href="catalogRelationships.do?action=sort&type=account&name=Related.account.vector&field=type">Type </a></td>
<td><a class="tableheader" href="catalogRelationships.do?action=sort&type=account&name=Related.account.vector&field=status">Status </a></td>
</tr>

<logic:iterate id="arrele" name="Related.account.vector">
<tr>
<td><bean:write name="arrele" property="busEntity.busEntityId"/></td>
<td>
<bean:define id="eleid" name="arrele" property="busEntity.busEntityId"/>
<a href="accountmgr.do?action=accountdetail&searchType=id&searchField=<%=eleid%>">
<bean:write name="arrele" property="busEntity.shortDesc"/>
</a>
</td>
<td><bean:write name="arrele" property="primaryAddress.city"/></td>
<td><bean:write name="arrele" property="primaryAddress.stateProvinceCd"/></td>
<td><bean:write name="arrele" property="accountType.value"/></td>
<td><bean:write name="arrele" property="busEntity.busEntityStatusCd"/></td>
</tr>
</logic:iterate>
</table>
</logic:greaterThan>
</logic:present>


</div>

<jsp:include flush='true' page="ui/admFooter.jsp"/>
</body>

</html:html>






