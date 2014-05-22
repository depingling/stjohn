<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

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
<title>Application Administrator Home: User Relationships</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>

<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/admUserToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>

<div class="text">
<center>
<font color=red>
<html:errors/>
</font>
</center>

<table width="769" class="mainbody">

<tr>
<td><b>User&nbsp;Id:</b> </td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.userData.userId"/>
</td>
<td><b>User&nbsp;Name:</b> </td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.userData.userName"/>
</td>
</tr>

<tr>
<td><b>User&nbsp;Type:</b> </td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.userData.userTypeCd"/>
</td>
<td><b>User&nbsp;Status:</b> </td>
<td>
<bean:write name="USER_DETAIL_FORM" property="detail.userData.userStatusCd"/>
</td>
</tr>

<tr><td colspan="4">&nbsp;</td></tr>

<bean:define id="userTypeCd" name="USER_DETAIL_FORM" property="detail.userData.userTypeCd" />

<% if ( ! RefCodeNames.USER_TYPE_CD.ADMINISTRATOR.equals(userTypeCd)){	
%>
<tr>
<td><b>Store&nbsp;Id:</b> </td>
<td>
<bean:write name="USER_DETAIL_FORM" property="storeId"/>
</td>
<td><b>Store&nbsp;Name:</b> </td>
<td>
<bean:write name="USER_DETAIL_FORM" property="storeName"/>
</td>
</tr>
<% }  %>


<% if (RefCodeNames.USER_TYPE_CD.ACCOUNT_ADMINISTRATOR.equals(userTypeCd) ||
		RefCodeNames.USER_TYPE_CD.CUSTOMER.equals(userTypeCd)) {	
%>
<tr>
<td><b>Account&nbsp;Id:</b> </td>
<td>
<bean:write name="USER_DETAIL_FORM" property="accountId"/>
</td>
<td><b>Account&nbsp;Name:</b> </td>
<td>
<bean:write name="USER_DETAIL_FORM" property="accountName"/>
</td>
</tr>
<% }  %>

</table>

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

<html:hidden property="action" value="user"/>

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



<logic:present name="Related.user.catalog.vector">
<bean:size id="rescount"  name="Related.user.catalog.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">
<table width="769"  class="results">
<tr align=left>
	<td><a class="tableheader" href="userRelationships.do?action=sort&type=catalog&name=Related.user.catalog.vector&field=id">Catalog&nbsp;Id </a></td>
	<td><a class="tableheader" href="userRelationships.do?action=sort&type=catalog&name=Related.user.catalog.vector&field=name">Name </a></td>
	<td><a class="tableheader" href="userRelationships.do?action=sort&type=catalog&name=Related.user.catalog.vector&field=type">Type </a></td>
	<td><a class="tableheader" href="userRelationships.do?action=sort&type=catalog&name=Related.user.catalog.vector&field=status">Status</a></td>
</tr>

<logic:iterate id="arrele" name="Related.user.catalog.vector">
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


<logic:present name="Related.user.contract.vector">
<bean:size id="rescount"  name="Related.user.contract.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">
<table width="769"  class="results">
<tr align=left>
	<td><a class="tableheader" href="userRelationships.do?action=sort&type=contract&name=Related.user.contract.vector&field=id">Contract&nbsp;Id </a></td>
	<td><a class="tableheader" href="userRelationships.do?action=sort&type=contract&name=Related.user.contract.vector&field=name">Name </a></td>
	<td><a class="tableheader" href="userRelationships.do?action=sort&type=contract&name=Related.user.contract.vector&field=catalog">Catalog Name</a></td>
	<td><a class="tableheader" href="userRelationships.do?action=sort&type=contract&name=Related.user.contract.vector&field=status">Status</a></td>
</tr>

<logic:iterate id="arrele" name="Related.user.contract.vector">
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


<logic:present name="Related.user.orderguide.vector">
<bean:size id="rescount"  name="Related.user.orderguide.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">
<table width="769"  class="results">
<tr align=left>
	<td><a class="tableheader" href="userRelationships.do?action=sort&type=orderguide&name=Related.user.orderguide.vector&field=id">Order Guide&nbsp;Id </a></td>
	<td><a class="tableheader" href="userRelationships.do?action=sort&type=orderguide&name=Related.user.orderguide.vector&field=name">Name </a></td>
	<td><a class="tableheader" href="userRelationships.do?action=sort&type=orderguide&name=Related.user.orderguide.vector&field=catalog">Catalog Name</a></td>
	<td><a class="tableheader" href="userRelationships.do?action=sort&type=orderguide&name=Related.user.orderguide.vector&field=status">Status</a></td>
</tr>

<logic:iterate id="arrele" name="Related.user.orderguide.vector">
<tr>
<td><bean:write name="arrele" property="orderGuideId"/></td>
<td>
<bean:define id="eleid" name="arrele" property="orderGuideId"/>
<a href="orderguidesmgr.do?action=detail&searchType=id&searchField=<%=eleid%>">
<bean:write name="arrele" property="orderGuideName"/>
</a>
</td>
<td><bean:write name="arrele" property="catalogName"/></td>
<td><bean:write name="arrele" property="status"/></td>
</tr>
</logic:iterate>
</table>
</logic:greaterThan>
</logic:present>


<logic:present name="Related.user.account.vector">
<bean:size id="rescount"  name="Related.user.account.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">
<table width="769"  class="results">
<tr align=left>
	<td><a class="tableheader" href="userRelationships.do?action=sort&type=account&name=Related.user.account.vector&field=id">Account&nbsp;Id </a></td>
	<td><a class="tableheader" href="userRelationships.do?action=sort&type=account&name=Related.user.account.vector&field=name">Name </a></td>
	<td><a class="tableheader" href="userRelationships.do?action=sort&type=account&name=Related.user.account.vector&field=city">City </a></td>
	<td><a class="tableheader" href="userRelationships.do?action=sort&type=account&name=Related.user.account.vector&field=state">State/Province </a></td>
	<td><a class="tableheader" href="userRelationships.do?action=sort&type=account&name=Related.user.account.vector&field=type">Type </a></td>
	<td><a class="tableheader" href="userRelationships.do?action=sort&type=account&name=Related.user.account.vector&field=status">Status</a></td>
</tr>

<logic:iterate id="arrele" name="Related.user.account.vector">
<tr>
<td><bean:write name="arrele" property="busEntity.busEntityId"/></td>
<td>
<bean:define id="eleid" name="arrele" property="busEntity.busEntityId"/>
<a href="accountmgr.do?action=accountdetail&searchType=accountId&searchField=<%=eleid%>">
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


<logic:present name="Related.user.site.vector">
<bean:size id="rescount"  name="Related.user.site.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">
<table width="769"  class="results">
<tr align=left>
	<td><a class="tableheader" href="userRelationships.do?action=sort&type=site&name=Related.user.site.vector&field=id">Site&nbsp;Id </a></td>
	<td><a class="tableheader" href="userRelationships.do?action=sort&type=site&name=Related.user.site.vector&field=account">Account Name </a></td>
	<td><a class="tableheader" href="userRelationships.do?action=sort&type=site&name=Related.user.site.vector&field=name">Name </a></td>
	<td><a class="tableheader" href="userRelationships.do?action=sort&type=site&name=Related.user.site.vector&field=address">Address</a></td>
	<td><a class="tableheader" href="userRelationships.do?action=sort&type=site&name=Related.user.site.vector&field=city">City</a></td>
	<td><a class="tableheader" href="userRelationships.do?action=sort&type=site&name=Related.user.site.vector&field=state">State</a></td>
	<td><a class="tableheader" href="userRelationships.do?action=sort&type=site&name=Related.user.site.vector&field=status">Status</a></td>
	<td><b>BSC</b></td>
</tr>

<logic:iterate id="arrele" name="Related.user.site.vector"
  type="SiteData">
<tr>
<td><bean:write name="arrele" property="busEntity.busEntityId"/></td>
<td>
<bean:write name="arrele" property="accountBusEntity.shortDesc"/>
</td>
<td>
<bean:define id="eleid" name="arrele" property="busEntity.busEntityId"/>
<a href="sitemgr.do?action=sitedetail&searchType=siteId&searchField=<%=eleid%>">
<bean:write name="arrele" property="busEntity.shortDesc"/>
</a>
</td>
<td><bean:write name="arrele" property="siteAddress.address1"/></td>
<td><bean:write name="arrele" property="siteAddress.city"/></td>
<td><bean:write name="arrele" property="siteAddress.stateProvinceCd"/></td>
<td><bean:write name="arrele" property="busEntity.busEntityStatusCd"/></td>
<td>

<% String bscname = ""; %>
<%
  if ( arrele.getBSC() != null &&
        arrele.getBSC().getBusEntityData() != null &&
        arrele.getBSC().getBusEntityData().getShortDesc() != null 
      ) {
    bscname = arrele.getBSC().getBusEntityData().getShortDesc();

 } %>

<%=bscname%>

</td>
</tr>
</logic:iterate>
</table>
</logic:greaterThan>
</logic:present>


<jsp:include flush='true' page="ui/admFooter.jsp"/>

</body>

</html:html>






