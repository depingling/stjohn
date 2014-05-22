<%@ page language="java" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.forms.UiSiteMgrForm" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.UiGroupDataView" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>


<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="UI_HOME_MGR_FORM" type="com.cleanwise.view.forms.UiHomeMgrForm"/>

<html:html>

<%
    UiGroupDataView currentGroup = theForm.getCurrentGroup();
    String grTypeStrHeader = "Account";
    if (currentGroup.getGroupData().getGroupTypeCd().equals(RefCodeNames.GROUP_TYPE_CD.STORE_UI)) {
        grTypeStrHeader = "Store";
    }
%>

<body>

<div class="text">
<table cellspacing="0" border="0" width="<%=Constants.TABLEWIDTH800%>"  class="mainbody">


<bean:define id="managedGroup" name="UI_HOME_MGR_FORM" property="currentGroup" type="com.cleanwise.service.api.value.UiGroupDataView" />
<tr><td colspan="6" class="largeheader">Group Detail</td></tr>
<tr>
<td><b>Group Id:</b></td>
<td><bean:write name='managedGroup' property='groupData.groupId'/></td>

<td><b>Group Type:</b></td>
<td><bean:write name='managedGroup' property='groupData.groupTypeCd'/></td>
<td><b>Group Name:</b></td>
<td><bean:write name='managedGroup' property='groupData.shortDesc'/></td>
</tr>

<tr><td colspan=6>&nbsp;</td></tr>

<tr>
<td colspan=6 class="largeheader"><b><%=grTypeStrHeader%>s associated with the UI</b></td>
</tr>

<tr>
<td>Count: <%=currentGroup.getGroupAssociations().size()%></td>
</tr>

<tr>
<td colspan="6">
<table class="stpTable_sortable">
<thead>
<tr>
<th class="stpTH"><%=grTypeStrHeader%> Id</th>
<th class="stpTH"><%=grTypeStrHeader%> Name</th>
</tr>
</thead>
<tbody id="itemTblBdy">
<% int ii=0; %>
<logic:iterate id='assoc' name='managedGroup' property='groupAssociations' type='com.cleanwise.service.api.value.BusEntityData'>
    <% if ( ( ii % 2 ) == 0 ) { %>
    <tr>
    <% } else { %>
    <tr class="rowa">
    <% } %>
        <td class="stpTD"><bean:write name="assoc" property="busEntityId"/></td>
        <td class="stpTD"><bean:write name="assoc" property="shortDesc"/></td>
    </tr>
</logic:iterate>
    </tbody>
</table>
</td></tr>


<tr><td colspan=6>&nbsp;</td></tr>

<tr>
<td colspan=6 class="largeheader"><b>Users configured with the UI</b></td>
</tr>

<tr>
<td colspan=6>Count: <%=currentGroup.getUsers().size()%></td>
</tr>

<tr>
<td colspan="6">
<table class="stpTable_sortable">


<tr>
<td colspan="6">
    <table class="stpTable_sortable">
        <thead>
        <tr>
            <th class="stpTH">User Id</th>
            <th class="stpTH">User Name</th>
            <th class="stpTH">First Name</th>
            <th class="stpTH">Last Name</th>
            <th class="stpTH">User Type</th>
        </tr>
        </thead>
        <tbody id="itemTblBdy">
            <% int jj=0; %>
            <logic:iterate id='user' name='managedGroup' property='users' type='com.cleanwise.service.api.value.UserData'>
            <% if ( ( ii % 2 ) == 0 ) { %>
            <tr>
            <% } else { %>
            <tr class="rowa">
            <% } %>
                <td class="stpTD"><bean:write name="user" property="userId"/></td>
                <td class="stpTD"><bean:write name="user" property="userName"/></td>
                <td class="stpTD"><bean:write name="user" property="firstName"/></td>
                <td class="stpTD"><bean:write name="user" property="lastName"/></td>
                <td class="stpTD"><bean:write name="user" property="userTypeCd"/></td>
            </tr>
            </logic:iterate>

</tbody>
</table>
</td>

</tr>
<tr><td colspan=6>&nbsp;</td></tr>
</table>
</body>
</html:html>


