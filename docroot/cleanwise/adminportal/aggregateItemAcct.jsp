<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<SCRIPT TYPE="text/javascript" SRC="../externals/table-sort.js"
  CHARSET="ISO-8859-1"></SCRIPT>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="Location" value="user" type="java.lang.String" toScope="session"/>
<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Application Administrator Home: Aggregate Item Manager</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body bgcolor="#FFFFFF">

<html:form name="ITEM_AGGREGATE_MGR_FORM" action="/adminportal/aggregateItem" type="com.cleanwise.view.forms.AggregateItemMgrForm">
<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>
<jsp:include flush='true' page="ui/admAggItemToolbar.jsp"/>

<div class="text">
<font color=red>
<html:errors/>
</font>

<table width="769" cellspacing="0" border="0" class="mainbody">
<html:hidden name="ITEM_AGGREGATE_MGR_FORM" property="wizardStep" value="accountselect"/>

<tr><td class="largeheader">Find Account:</td>
       <td colspan="3">
                        <html:text name="ITEM_AGGREGATE_MGR_FORM" property="acctSearchField"/>
       </td>
  </tr>
  <tr> <td>&nbsp;</td>
       <td colspan="3">
         <html:radio name="ITEM_AGGREGATE_MGR_FORM" property="acctSearchType" value="id" />
         ID
         <html:radio name="ITEM_AGGREGATE_MGR_FORM" property="acctSearchType" value="nameBegins" />
         Name(starts with)
         <html:radio name="ITEM_AGGREGATE_MGR_FORM" property="acctSearchType" value="nameContains" />
         Name(contains)
         </td>
  </tr>
  <!-- Group Search -->
  <tr><td><b>Group:</b></td>
       <td colspan="3">
       <html:text name="ITEM_AGGREGATE_MGR_FORM" property="acctSearchGroupId" size="20"/>
       <html:button property="locateGroup" onclick="popLocateFeedGlobal('../adminportal/grouplocate', 'acctSearchGroupId', '');" value="Locate Group"/>
       </td>
  </tr>
  <tr> <td>&nbsp;</td>
       <td colspan="3">
        <html:submit property="action">
                <app:storeMessage  key="global.action.label.search"/>
        </html:submit>
        <html:submit property="action">
                <app:storeMessage  key="admin.button.back"/>
        </html:submit>
        <html:submit property="action">
                <app:storeMessage  key="global.action.label.next"/>
        </html:submit>
    </td>
  </tr>
</table>


<logic:present name="ITEM_AGGREGATE_MGR_FORM" property="accounts">
<bean:size id="rescount"  name="ITEM_AGGREGATE_MGR_FORM" property="accounts.values"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">


<table width="769" cellspacing="0" border="0" class="results">
<thead>
<tr align=left>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 0, false);">Account&nbsp;Id</a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 1, false);">Name</a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 2, false);">City</a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 3, false);">State/Province</a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 4, false);">Type</a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 5, false);">Status</a></th>
<th class="tableheader">Select<br>
<a class="tableheader" href="javascript:SetCheckedGlobal(1,'accounts.selected')">[Check&nbsp;All]</a>
<br>
<a class="tableheader" href="javascript:SetCheckedGlobal(0,'accounts.selected')">[&nbsp;Clear]</a>
</th>
</tr>
</thead>

<tbody id="resTblBdy">
<logic:iterate id="arrele" name="ITEM_AGGREGATE_MGR_FORM" property="accounts.values" indexId="i">
   <tr>
        <td><bean:write name="arrele" property="busEntity.busEntityId"/></td>
        <td>
                <bean:define id="eleid" name="arrele" property="busEntity.busEntityId"/>
                <a href="accountmgr.do?action=accountdetail&searchType=id&searchField=<%=eleid%>">
                <bean:write name="arrele" property="busEntity.shortDesc"/>
        </td>
        <td><bean:write name="arrele" property="primaryAddress.city"/></td>
        <td><bean:write name="arrele" property="primaryAddress.stateProvinceCd"/></td>
        <td><bean:write name="arrele" property="accountType.value"/></td>
        <td><bean:write name="arrele" property="busEntity.busEntityStatusCd"/></td>
        <%String selectedStr = "accounts.selected["+i+"]";%>
        <td><html:multibox name="ITEM_AGGREGATE_MGR_FORM" property="<%=selectedStr%>" value="true"/></td>
   </tr>
</logic:iterate>
</tbody>
<tr>
        <td colspan="7">
                <html:submit property="action">
                <app:storeMessage  key="global.action.label.next"/>
                </html:submit>
        </td>
</tr>
</table>

</logic:greaterThan>
</logic:present>
</div>
</html:form>

<jsp:include flush='true' page="ui/admFooter.jsp"/>

</body>

</html:html>
