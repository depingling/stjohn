<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<SCRIPT TYPE="text/javascript" SRC="../externals/table-sort.js" CHARSET="ISO-8859-1"></SCRIPT>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Application Administrator Home: Groups</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#FFFFFF">

<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>
<jsp:include flush='true' page="ui/admGroupToolbar.jsp"/>
<jsp:include flush='true' page="ui/groupInfo.jsp"/>


<div class="text">
<font color=red>
<html:errors/>
</font>

<table> <tr>
<html:form name="RELATED_FORM" action="adminportal/related.do"
    method="POST"
    scope="session" type="com.cleanwise.view.forms.RelatedForm">
<td><b>Find Relationships:</b></td>
<td>
<html:text name="RELATED_FORM" property="searchForName" size="10"/>
<html:select name="RELATED_FORM" property="searchForType">
<html:options collection="Related.options.vector" property="label" labelProperty="value"/>
</html:select>
</td>
</tr>
<tr>
        <td><b>Search By:</b></td>
        <td colspan="3">
          <html:radio name="RELATED_FORM" property="searchType" value="nameBegins" />
          Name(starts with)
          <html:radio name="RELATED_FORM" property="searchType" value="nameContains" />
          Name(contains)
        </td>
</tr>

<html:hidden property="action" value="group"/>

<tr>
        <td>&nbsp;</td>
        <td colspan="3">
                <html:submit property="viewAll">
                <app:storeMessage  key="global.action.label.search"/>
                </html:submit>
        </td>
</tr>

</html:form>

</table>

<%/****************Display Found Users Of Group****************/%>
<logic:present name="Related.group.users.vector">
<bean:size id="rescount"  name="Related.group.users.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">
<table width="769"  class="results">
<tr align=left>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 0, false);">User&nbsp;Id </a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 1, false);">Login Name </a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 2, false);">First Name </a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 3, false);">Last Name </a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 4, false);">User Type </a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 5, false);">Status </a></th>
</tr>
<tbody id="resTblBdy">
<logic:iterate id="arrele" name="Related.group.users.vector">
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
</tbody>
</table>

</logic:greaterThan>
</logic:present>
<%/****************End Display Found Users****************/%>

<%/****************Display Found Reports Of Group****************/%>
<logic:present name="Related.group.reports.vector">
<bean:size id="rescount"  name="Related.group.reports.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">
<table width="769"  class="results">
        <tr align=left>
                <th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 0, false);">Id </a></th>
                <th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 1, false);">Category </a></th>
                <th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 2, false);">Name </a></th>
        </tr>
        <tbody id="resTblBdy">
        <logic:iterate id="arrele" name="Related.group.reports.vector">
                <tr>
                        <td><bean:write name="arrele" property="genericReportId"/></td>
                        <td><bean:write name="arrele" property="category"/></td>
                        <td><bean:write name="arrele" property="name"/></td>
                </tr>
        </logic:iterate>
        </tbody>
</table>

</logic:greaterThan>
</logic:present>
<%/****************End Display Found Reports****************/%>

<%/****************Display Found Bus Entitys Of Group****************/%>
<logic:present name="Related.group.bus.entity.vector">
        <bean:size id="rescount"  name="Related.group.bus.entity.vector"/>
        Search result count:  <bean:write name="rescount" />
        <logic:greaterThan name="rescount" value="0">
                <table width="769"  class="results">
                        <tr align=left>
                                <th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 0, false);">Id</a></th>
                                <th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 0, false);">Name</a></th>
                                <th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 0, false);">Type</a></th>
                                <th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 0, false);">Status</a></th>
                                <th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 0, false);">ERP Num</a></th>
                        </tr>
                        <tbody id="resTblBdy">
                        <logic:iterate id="arrele" name="Related.group.bus.entity.vector">
                                <tr>
                                <td><bean:write name="arrele" property="busEntity.busEntityId"/></td>
                                <td><bean:write name="arrele" property="busEntity.shortDesc"/></td>
                                <td><bean:write name="arrele" property="busEntity.busEntityTypeCd"/></td>
                                <td><bean:write name="arrele" property="busEntity.busEntityStatusCd"/></td>
                                <td><bean:write name="arrele" property="busEntity.erpNum"/></td>
                                </tr>
                        </logic:iterate>
                        </tbody>
                </table>
        </logic:greaterThan>
</logic:present>
<%/****************End Display Found Bus Entitys Of Group****************/%>

<jsp:include flush='true' page="ui/admFooter.jsp"/>

</body>

</html:html>
