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
<title>Application Administrator Home: Store Relationships</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>

<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/admStoreToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>

<div class="text">
<center>
<font color=red>
<html:errors/>
</font>
</center>

<table width="769" class="mainbody">

<jsp:include flush='true' page="ui/storeInfo.jsp"/>

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

<html:hidden property="action" value="store"/>

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

<pg:pager maxPageItems="<%= Constants.MAX_PAGE_ITEMS %>"
          maxIndexPages="<%= Constants.MAX_INDEX_PAGES %>">
  <pg:param name="pg"/>
  <pg:param name="q"/>
  
<logic:present name="Related.store.user.vector">
<bean:size id="rescount"  name="Related.store.user.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">
<table width="769"  class="results">
<tr align=left>
<td><a class="tableheader" href="storeRelationships.do?action=sort&type=user&name=Related.store.user.vector&field=id">User&nbsp;Id <a/></td>
<td><a class="tableheader" href="storeRelationships.do?action=sort&type=user&name=Related.store.user.vector&field=name">Login Name </a></td>
<td><a class="tableheader" href="storeRelationships.do?action=sort&type=user&name=Related.store.user.vector&field=firstName">First Name </a></td>
<td><a class="tableheader" href="storeRelationships.do?action=sort&type=user&name=Related.store.user.vector&field=lastName">Last Name</a> </td>
<td><a class="tableheader" href="storeRelationships.do?action=sort&type=user&name=Related.store.user.vector&field=type">User Type </a> </td>
<td><a class="tableheader" href="storeRelationships.do?action=sort&type=user&name=Related.store.user.vector&field=status">Status </a></td>
</tr>

<logic:iterate id="arrele" name="Related.store.user.vector">
<pg:item>
<tr>
<td><bean:write name="arrele" property="userId"/></td>
<td>
<bean:define id="eleid" name="arrele" property="userId"/>
<bean:define id="eletype" name="arrele" property="userTypeCd"/>
<a href="usermgr.do?action=userdetail&searchType=userId&searchField=<%=eleid%>&type=<%=eletype%>">
<bean:write name="arrele" property="userName"/>
</a>
</td>
<td><bean:write name="arrele" property="firstName"/></td>
<td><bean:write name="arrele" property="lastName"/></td>
<td><bean:write name="arrele" property="userTypeCd"/></td>
<td><bean:write name="arrele" property="userStatusCd"/></td>
</tr>
</pg:item>
</logic:iterate>
</table>

</logic:greaterThan>
</logic:present>


<logic:present name="Related.store.account.vector">
<bean:size id="rescount"  name="Related.store.account.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">
<table width="769"  class="results">
<tr align=left>
	<td><a class="tableheader" href="storeRelationships.do?action=sort&type=account&name=Related.store.account.vector&field=id">Accounts&nbsp;Id </a></td>
	<td><a class="tableheader" href="storeRelationships.do?action=sort&type=account&name=Related.store.account.vector&field=name">Name </a></td>
	<td><a class="tableheader" href="storeRelationships.do?action=sort&type=account&name=Related.store.account.vector&field=city">City </a></td>
	<td><a class="tableheader" href="storeRelationships.do?action=sort&type=account&name=Related.store.account.vector&field=state">State/Province </a></td>
	<td><a class="tableheader" href="storeRelationships.do?action=sort&type=account&name=Related.store.account.vector&field=type">Type</a></td>
	<td><a class="tableheader" href="storeRelationships.do?action=sort&type=account&name=Related.store.account.vector&field=status">Status</a></td>
</tr>

<logic:iterate id="arrele" name="Related.store.account.vector">
<pg:item>
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
</pg:item>
</logic:iterate>
</table>
</logic:greaterThan>
</logic:present>


<logic:present name="Related.store.catalog.vector">
<bean:size id="rescount"  name="Related.store.catalog.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">
<table width="769"  class="results">
<tr align=left>
	<td><a class="tableheader" href="storeRelationships.do?action=sort&type=catalog&name=Related.store.catalog.vector&field=id">Catalogs&nbsp;Id </a></td>
	<td><a class="tableheader" href="storeRelationships.do?action=sort&type=catalog&name=Related.store.catalog.vector&field=name">Name </a></td>
	<td><a class="tableheader" href="storeRelationships.do?action=sort&type=catalog&name=Related.store.catalog.vector&field=type">Type </a></td>
	<td><a class="tableheader" href="storeRelationships.do?action=sort&type=catalog&name=Related.store.catalog.vector&field=status">Status</a></td>
</tr>

<logic:iterate id="arrele" name="Related.store.catalog.vector">
<pg:item>
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
</pg:item>
</logic:iterate>
</table>
</logic:greaterThan>
</logic:present>


<logic:present name="Related.store.contract.vector">
<bean:size id="rescount"  name="Related.store.contract.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">
<table width="769"  class="results">
<tr align=left>
	<td><a class="tableheader" href="storeRelationships.do?action=sort&type=contract&name=Related.store.contract.vector&field=id">Contracts&nbsp;Id </a></td>
	<td><a class="tableheader" href="storeRelationships.do?action=sort&type=contract&name=Related.store.contract.vector&field=name">Name </a></td>
	<td><a class="tableheader" href="storeRelationships.do?action=sort&type=contract&name=Related.store.contract.vector&field=catalog">Catalog Name </a></td>
	<td><a class="tableheader" href="storeRelationships.do?action=sort&type=contract&name=Related.store.contract.vector&field=status">Status</a></td>
</tr>

<logic:iterate id="arrele" name="Related.store.contract.vector">
<pg:item>
<tr>
<td><bean:write name="arrele" property="contractId"/></td>
<td>
<bean:define id="eleid" name="arrele" property="contractId"/>
<a href="contract.do?action=edit&id=<%=eleid%>">
<bean:write name="arrele" property="contractName"/>
</a>
</td>
<td><bean:write name="arrele" property="catalogName"/></td>
<td><bean:write name="arrele" property="status"/></td>
</tr>
</pg:item>
</logic:iterate>
</table>
</logic:greaterThan>
</logic:present>


<logic:present name="Related.store.site.vector">
<bean:size id="rescount"  name="Related.store.site.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">
<table width="769"  class="results">
<tr align=left>
	<td><a class="tableheader" href="storeRelationships.do?action=sort&type=site&name=Related.store.site.vector&field=id">Site&nbsp;Id </a></td>
	<td><a class="tableheader" href="storeRelationships.do?action=sort&type=site&name=Related.store.site.vector&field=account">Account Name </a></td>
	<td><a class="tableheader" href="storeRelationships.do?action=sort&type=site&name=Related.store.site.vector&field=name">Site Name </a></td>
	<td><a class="tableheader" href="storeRelationships.do?action=sort&type=site&name=Related.store.site.vector&field=address">Street Address </a></td>
	<td><a class="tableheader" href="storeRelationships.do?action=sort&type=site&name=Related.store.site.vector&field=city">City </a></td>
	<td><a class="tableheader" href="storeRelationships.do?action=sort&type=site&name=Related.store.site.vector&field=state">State/Province </a></td>
	<td><a class="tableheader" href="storeRelationships.do?action=sort&type=site&name=Related.store.site.vector&field=status">Status</a></td>
</tr>

<logic:iterate id="arrele" name="Related.store.site.vector">
<pg:item>
<tr>
<td><bean:write name="arrele" property="busEntity.busEntityId"/></td>
<td>
<bean:write name="arrele" property="accountBusEntity.shortDesc"/>
</td>
<td>
<bean:define id="eleid" name="arrele" property="busEntity.busEntityId"/>
<a href="sitemgr.do?action=sitedetail&searchType=userId&searchField=<%=eleid%>">
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
</pg:item>
</logic:iterate>
</table>
</logic:greaterThan>
</logic:present>


  <pg:index>
    Result Pages:
    <pg:prev>&nbsp;<a href="<%= pageUrl %>">[<< Prev]</a></pg:prev>
    <pg:pages><%
      if (pageNumber.intValue() < 10) { 
        %>&nbsp;<%
      }
      if (pageNumber == pagerPageNumber) { 
        %><b><%= pageNumber %></b><%
      } else { 
        %><a href="<%= pageUrl %>"><%= pageNumber %></a><%
      }
    %>
    </pg:pages>
    <pg:next>&nbsp;<a href="<%= pageUrl %>">[Next >>]</a></pg:next>
    <br></font>
  </pg:index>
</pg:pager>

<jsp:include flush='true' page="ui/admFooter.jsp"/>
</body>

</html:html>






