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
<title>Application Administrator Home: Contract Relationships</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>

<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/admContractToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>

<div class="text">
<center>
<font color=red>
<html:errors/>
</font>
</center>

<table width="769" class="mainbody">

<tr>
<td><b>Contract&nbsp;Id:</b> </td>
<td>
<bean:write name="CONTRACT_DETAIL_FORM" property="detail.contractId"/>
</td>
<td><b>Contract&nbsp;Name:</b> </td>
<td>
<bean:write name="CONTRACT_DETAIL_FORM" property="detail.shortDesc"/>
</td>
</tr>

<tr><td colspan="4">&nbsp;</td></tr>

<tr>
<td><b>Store&nbsp;Id:</b> </td>
<td>
<bean:write name="CONTRACT_DETAIL_FORM" property="storeId"/>
</td>
<td><b>Store&nbsp;Name:</b> </td>
<td>
<bean:write name="CONTRACT_DETAIL_FORM" property="storeName"/>
</td>
</tr>

<tr>
<td><b>Catalog&nbsp;Id:</b> </td>
<td>
<bean:write name="CONTRACT_DETAIL_FORM" property="detail.catalogId"/>
</td>
<td><b>Catalog&nbsp;Name:</b> </td>
<td>
<bean:write name="CONTRACT_DETAIL_FORM" property="catalogName"/>
</td>
</tr>

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

<html:hidden property="action" value="contract"/>

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


<logic:present name="Related.contract.account.vector">
<bean:size id="rescount"  name="Related.contract.account.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">
<table width="769"  class="results">
<tr align=left>
<td><a class="tableheader" href="contractRelationships.do?action=sort&type=account&name=Related.contract.account.vector&field=id">Account Id</td>
<td><a class="tableheader" href="contractRelationships.do?action=sort&type=account&name=Related.contract.account.vector&field=name">Name </td>
<td><a class="tableheader" href="contractRelationships.do?action=sort&type=account&name=Related.contract.account.vector&field=city">City</td>
<td><a class="tableheader" href="contractRelationships.do?action=sort&type=account&name=Related.contract.account.vector&field=state">State/Province</td>
<td><a class="tableheader" href="contractRelationships.do?action=sort&type=account&name=Related.contract.account.vector&field=type">Type</td>
<td><a class="tableheader" href="contractRelationships.do?action=sort&type=account&name=Related.contract.account.vector&field=status">Status</td>
</tr>

<logic:iterate id="arrele" name="Related.contract.account.vector">
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


<logic:present name="Related.contract.orderguide.vector">
<bean:size id="ogres"  name="Related.contract.orderguide.vector"/>
Search result count:  <bean:write name="ogres" />
<logic:greaterThan name="ogres" value="0">
<table width="769"  class="results">
<tr>
<td><a class="tableheader" href="contractRelationships.do?action=sort&type=orderguide&name=Related.contract.orderguide.vector&field=id">Order Guide Id</a></td>
<td><a class="tableheader" href="contractRelationships.do?action=sort&type=orderguide&name=Related.contract.orderguide.vector&field=name">Name</a></td>
<td><a class="tableheader" href="contractRelationships.do?action=sort&type=orderguide&name=Related.contract.orderguide.vector&field=catalog">Catalog Name</a></td>
<td><a class="tableheader" href="contractRelationships.do?action=sort&type=orderguide&name=Related.contract.orderguide.vector&field=status">Status</a></td>
</tr>
<logic:iterate id="ogele" name="Related.contract.orderguide.vector">
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


<logic:present name="Related.contract.user.vector">
<bean:size id="rescount"  name="Related.contract.user.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">
<table width="769"  class="results">
<tr align=left>
<td><a class="tableheader" href="contractRelationships.do?action=sort&type=user&name=Related.contract.user.vector&field=id">User&nbsp;Id </a></td>
<td><a class="tableheader" href="contractRelationships.do?action=sort&type=user&name=Related.contract.user.vector&field=name">Login Name </a></td>
<td><a class="tableheader" href="contractRelationships.do?action=sort&type=user&name=Related.contract.user.vector&field=firstName">First Name </a></td>
<td><a class="tableheader" href="contractRelationships.do?action=sort&type=user&name=Related.contract.user.vector&field=lastName">Last Name </a></td>
<td><a class="tableheader" href="contractRelationships.do?action=sort&type=user&name=Related.contract.user.vector&field=type">User Type </a></td>
<td><a class="tableheader" href="contractRelationships.do?action=sort&type=user&name=Related.contract.user.vector&field=status">Status </a></td>
</tr>

<logic:iterate id="arrele" name="Related.contract.user.vector">
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
</logic:iterate>
</table>
</logic:greaterThan>
</logic:present>


<logic:present name="Related.contract.site.vector">
<bean:size id="rescount"  name="Related.contract.site.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">
<table width="769"  class="results">
<tr align=left>
<td><a class="tableheader" href="contractRelationships.do?action=sort&type=site&name=Related.contract.site.vector&field=id">Site&nbsp;Id</td>
<td><a class="tableheader" href="contractRelationships.do?action=sort&type=site&name=Related.contract.site.vector&field=account">Account Name</td>
<td><a class="tableheader" href="contractRelationships.do?action=sort&type=site&name=Related.contract.site.vector&field=name">Site Name</td>
<td><a class="tableheader" href="contractRelationships.do?action=sort&type=site&name=Related.contract.site.vector&field=address">Street Address</td>
<td><a class="tableheader" href="contractRelationships.do?action=sort&type=site&name=Related.contract.site.vector&field=city">City</td>
<td><a class="tableheader" href="contractRelationships.do?action=sort&type=site&name=Related.contract.site.vector&field=state">State/Province</td>
<td><a class="tableheader" href="contractRelationships.do?action=sort&type=site&name=Related.contract.site.vector&field=status">Status</td>
</tr>

<logic:iterate id="arrele" name="Related.contract.site.vector">
<tr>
<td><bean:write name="arrele" property="busEntity.busEntityId"/></td>
<td>
<bean:write name="arrele" property="accountBusEntity.shortDesc"/>
</td>
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



<jsp:include flush='true' page="ui/admFooter.jsp"/>

</body>

</html:html>





