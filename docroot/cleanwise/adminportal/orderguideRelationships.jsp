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
<bean:define id="Location" value="orderguide" type="java.lang.String" toScope="session"/>
<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Application Administrator Home: Order Guide Relationships</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

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

<table width="769"  class="mainbody">

<tr>


<td>
<table>
<tr>
<td><b>Order Guide Id:</b> </td>
<td>
<bean:write name="ORDER_GUIDES_DETAIL_FORM"
property="orderGuideInfoData.orderGuideData.orderGuideId"
/>
</td>
</tr><tr>
<td><b>Order Guide Name:</b> </td>
<td>
<bean:write name="ORDER_GUIDES_DETAIL_FORM"
property="orderGuideInfoData.orderGuideData.shortDesc"
/>
</td>
</tr>
</table>
</td>

<td>

<table>

<tr>
<td><b>Catalog Id:</b> </td>
<td>
<bean:write name="ORDER_GUIDES_DETAIL_FORM"
property="orderGuideInfoData.orderGuideData.catalogId"
/>
</td>
</tr>
<tr>
<td><b>Catalog Name:</b> </td>
<td>
<bean:write name="ORDER_GUIDES_DETAIL_FORM"
property="catalogName"
/>
</td>
</tr>

</table>

</td>
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

<html:hidden property="action" value="orderguide"/>

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


  
<logic:present name="Related.account.vector">
<bean:size id="rescount"  name="Related.account.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">
<table width="769"  class="results">
<tr align=left>
<td><a class="tableheader" href="orderguideRelationships.do?action=sort&type=account&name=Related.account.vector&field=id">Account&nbsp;Id </a>
<td><a class="tableheader" href="orderguideRelationships.do?action=sort&type=account&name=Related.account.vector&field=name">Name </a></td>
<td><a class="tableheader" href="orderguideRelationships.do?action=sort&type=account&name=Related.account.vector&field=city">City </a></td>
<td><a class="tableheader" href="orderguideRelationships.do?action=sort&type=account&name=Related.account.vector&field=state">State/Province </a></td>
<td><a class="tableheader" href="orderguideRelationships.do?action=sort&type=account&name=Related.account.vector&field=type">Type </a></td>
<td><a class="tableheader" href="orderguideRelationships.do?action=sort&type=account&name=Related.account.vector&field=status">Status</a></td>
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

<logic:present name="Related.site.vector">
<bean:size id="rescount"  name="Related.site.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">
<table width="769"  class="results">
<tr align=left>
<td><a class="tableheader" href="orderguideRelationships.do?action=sort&type=site&name=Related.site.vector&field=id">Site&nbsp;Id </a></td>
<td><a class="tableheader" href="orderguideRelationships.do?action=sort&type=site&name=Related.site.vector&field=name">Name </a></td>
<td><a class="tableheader" href="orderguideRelationships.do?action=sort&type=site&name=Related.site.vector&field=address">Street Address </a></td>
<td><a class="tableheader" href="orderguideRelationships.do?action=sort&type=site&name=Related.site.vector&field=city">City </a></td>
<td><a class="tableheader" href="orderguideRelationships.do?action=sort&type=site&name=Related.site.vector&field=state">State/Province </a></td>
<td><a class="tableheader" href="orderguideRelationships.do?action=sort&type=site&name=Related.site.vector&field=status">Status</a></td>
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


</div>

<jsp:include flush='true' page="ui/admFooter.jsp"/>
</body>

</html:html>






