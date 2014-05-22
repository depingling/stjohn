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
<bean:define id="Location" value="site" type="java.lang.String" toScope="session"/>
<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Application Administrator Home: Site Relationships</title>
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
<bean:write name="SITE_DETAIL_FORM" property="id"/>
</td>

<td><b>Site&nbsp;Name:</b> </td>
<td>
<bean:write name="SITE_DETAIL_FORM" property="name"/>
</td>

</tr>

<tr>
<td><b>Account&nbsp;Id:</b></td>
<td>
  <bean:write name="SITE_DETAIL_FORM" property="accountId"/>
</td>

<td><b>Account&nbsp;Name:</b></td>
<td>
  <bean:write name="SITE_DETAIL_FORM" property="accountName"/>
</td>
</tr>

<tr>

<td><b>Store&nbsp;Id:</b></td>
<td>
<bean:write name="SITE_DETAIL_FORM" property="storeId"/>
</td>

<td><b>Store&nbsp;Name:</b></td>
<td>
<bean:write name="SITE_DETAIL_FORM" property="storeName"/>
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

<html:hidden property="action" value="site"/>

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
<td><a class="tableheader" href="siteRelationships.do?action=sort&type=user&name=Related.users.vector&field=id">User&nbsp;Id </a></td>
<td><a class="tableheader" href="siteRelationships.do?action=sort&type=user&name=Related.users.vector&field=name">Login Name </a></td>
<td><a class="tableheader" href="siteRelationships.do?action=sort&type=user&name=Related.users.vector&field=firstName">First Name </a></td>
<td><a class="tableheader" href="siteRelationships.do?action=sort&type=user&name=Related.users.vector&field=lastName">Last Name </a></td>
<td><a class="tableheader" href="siteRelationships.do?action=sort&type=user&name=Related.users.vector&field=type">User Type </a></td>
<td><a class="tableheader" href="siteRelationships.do?action=sort&type=user&name=Related.users.vector&field=status">Status </a></td>
<td><a class="tableheader" href="siteRelationships.do?action=sort&type=user&name=Related.users.vector&field=workflowRoleCd">Workflow Role </a></td>

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
<td><bean:write name="arrele" property="workflowRoleCd"/></td>
</tr>
</logic:iterate>
</table>

</logic:greaterThan>
</logic:present>

<logic:present name="Related.site.contract.vector">
<bean:size id="rescount"  name="Related.site.contract.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">
<table width="769"  class="results">
<tr align=left>
<td><a class="tableheader" href="siteRelationships.do?action=sort&type=contract&name=Related.site.contract.vector&field=id">Contract&nbsp;Id </a></td>
<td><a class="tableheader" href="siteRelationships.do?action=sort&type=contract&name=Related.site.contract.vector&field=name">Name </a></td>
<td><a class="tableheader" href="siteRelationships.do?action=sort&type=contract&name=Related.site.contract.vector&field=catalog">Catalog Name </a></td>
<td><a class="tableheader" href="siteRelationships.do?action=sort&type=contract&name=Related.site.contract.vector&field=status">Status</a></td>
</tr>

<logic:iterate id="arrele" name="Related.site.contract.vector">
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

<table width="769"  class="results">
<logic:present name="Related.site.catalog">
<tr>
<td><b>Site Catalog</b></td>
</tr>

<tr align="left">
<td class="tableheader">Catalog Id</td>
<td class="tableheader">Name </td>
<td class="tableheader">Type</td>
<td class="tableheader">Status</td>
</tr>

<tr>

<td><bean:write name="Related.site.catalog" property="catalogId"/></td>
<td>
<bean:define id="eleid" name="Related.site.catalog" property="catalogId"/>
<a href="catalogdetail.do?action=edit&id=<%=eleid%>">
<bean:write name="Related.site.catalog" property="shortDesc"/>
</a>
</td>
<td><bean:write name="Related.site.catalog" property="catalogTypeCd"/></td>
<td><bean:write name="Related.site.catalog" property="catalogStatusCd"/></td>
</tr>

</logic:present>

<logic:present name="Related.site.distributor">

<tr><td><b>Main Distributor</b></td></tr>
<tr align="left">
<td class="tableheader">Distributor Id</td>
<td class="tableheader">Name </td>
<td class="tableheader">ERP Num</td>
<td class="tableheader">Status</td>
</tr>

<tr>
<td> <bean:write name="Related.site.distributor" property="busEntity.busEntityId"/> </td>

<td> 
<bean:define id="mainDistid" name="Related.site.distributor" property="busEntity.busEntityId"/>
<a href="distmgr.do?action=distdetail&searchType=id&searchField=<%=mainDistid%>">
<bean:write name="Related.site.distributor" property="busEntity.shortDesc"/> </a> </td>

<td> <bean:write name="Related.site.distributor" 
  property="busEntity.erpNum"/> </td>
<td> <bean:write name="Related.site.distributor" 
  property="busEntity.busEntityStatusCd"/> </td>
</tr>

</logic:present>
</table>

<logic:present name="Related.site.orderguide.vector">
<bean:size id="ogres"  name="Related.site.orderguide.vector"/>
Search result count:  <bean:write name="ogres" />
<logic:greaterThan name="ogres" value="0">
<table width="769"  class="results">
<tr>
<td><a class="tableheader" href="siteRelationships.do?action=sort&type=orderguide&name=Related.site.orderguide.vector&field=id">Order Guide Id</td>
<td><a class="tableheader" href="siteRelationships.do?action=sort&type=orderguide&name=Related.site.orderguide.vector&field=name">Name</td>
<td><a class="tableheader" href="siteRelationships.do?action=sort&type=orderguide&name=Related.site.orderguide.vector&field=catalog">Catalog Name</td>
<td><a class="tableheader" href="siteRelationships.do?action=sort&type=orderguide&name=Related.site.orderguide.vector&field=status">Status</td>
</tr>

<logic:iterate id="ogele" name="Related.site.orderguide.vector">
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


<logic:present name="Related.site.distschedules.vector">
<bean:size id="dsres"  name="Related.site.distschedules.vector"/>
Search result count:  <bean:write name="dsres" />
<logic:greaterThan name="dsres" value="0">
<table width="769"  class="results">
<tr>
<td><a class="tableheader" href="deliveryScheduleMgr.do?action=sort&sortField=ScheduleId"><b>Schedule Id</b></a> </td>
<td><a class="tableheader" href="deliveryScheduleMgr.do?action=sort&sortField=BusEntityShortDesc"><b>Distributor Name</b></a> </td>
<td><a class="tableheader" href="deliveryScheduleMgr.do?action=sort&sortField=BusEntityErpNum"><b>Dist. Erp#</b></a> </td>
<td><a class="tableheader" href="deliveryScheduleMgr.do?action=sort&sortField=ScheduleName"><b>Schedule Name</b></a> </td>
<td><a class="tableheader" href="deliveryScheduleMgr.do?action=sort&sortField=ScheduleStatus"><b>Status</b></a> </td>
<td><a class="tableheader" href="deliveryScheduleMgr.do?action=sort&sortField=ScheduleInfo"><b>Info</b></a> </td>
<td><a class="tableheader" href="deliveryScheduleMgr.do?action=sort&sortField=ScheduleInfo"><b>Cutoff</b></a> </td>
<td><a class="tableheader" href="deliveryScheduleMgr.do?action=sort&sortField=ScheduleInfo"><b>Next Delivery</b></a> </td>
</tr>

<logic:iterate id="arrele" name="Related.site.distschedules.vector"
  type="com.cleanwise.service.api.value.DeliveryScheduleView"
  indexId="idx"
>
  <bean:define id="eleid" name="arrele" property="scheduleId"/>
    <td><bean:write name="arrele" property="scheduleId"/></td>
    <td><bean:write name="arrele" property="busEntityShortDesc"/></td>
    <td><bean:write name="arrele" property="busEntityErpNum"/></td>
    <td><bean:write name="arrele" property="scheduleName"/></td>
    <td><bean:write name="arrele" property="scheduleStatus"/></td>
    <td><bean:write name="arrele" property="scheduleInfo"/></td>
    <td><bean:write name="arrele" property="cutoffInfo"/></td>
    <% java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("EEE, MM/dd/yyyy");
       String nextDel = sdf.format(arrele.getNextDelivery());
    %>
    <td><%=nextDel%></td>
</tr>
</logic:iterate>
</table>
</logic:greaterThan>
</logic:present>

</div>

<jsp:include flush='true' page="ui/admFooter.jsp"/>

</body>

</html:html>



