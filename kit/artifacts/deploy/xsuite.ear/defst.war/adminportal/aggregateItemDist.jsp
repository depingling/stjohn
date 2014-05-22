<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
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

<html:form name="ITEM_AGGREGATE_MGR_FORM" action="adminportal/aggregateItem.do"
    scope="session" type="com.cleanwise.view.forms.AggregateItemMgrForm">

<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>
<jsp:include flush='true' page="ui/admAggItemToolbar.jsp"/>

<div class="text">
<font color=red>
<html:errors/>
</font>


<div class="text">

  <table width="769" cellspacing="0" border="0" class="mainbody">
    <html:hidden name="ITEM_AGGREGATE_MGR_FORM" property="wizardStep" value="distselect"/>
  <tr> <td class="largeheader">Find Distributor:</td>

<td colspan="3">
<html:text name="ITEM_AGGREGATE_MGR_FORM" property="distSearchField"/>
         <html:radio name="ITEM_AGGREGATE_MGR_FORM" property="distSearchType" value="id" />
         ID
         <html:radio name="ITEM_AGGREGATE_MGR_FORM" property="distSearchType" value="nameBegins" />
         Name(starts with)
         <html:radio name="ITEM_AGGREGATE_MGR_FORM" property="distSearchType" value="nameContains" />
         Name(contains)
</td>

  </tr>
  <!-- Territory search -->
  <tr><td><b>State:</b></td>
      <td colspan="3"><html:text name="ITEM_AGGREGATE_MGR_FORM" property="distSearchState" size="3"/>
      <b>County</b> (starts with):
      <html:text name="ITEM_AGGREGATE_MGR_FORM" property="distSearchCounty" size="20"/>
      <b>Zip Code:</b>
      <html:text name="ITEM_AGGREGATE_MGR_FORM" property="distSearchPostalCode" size="5"/>
      </td>
  </tr>
  <!-- Group Search -->
  <tr><td><b>Group:</b></td>
       <td colspan="3">
       <html:text name="ITEM_AGGREGATE_MGR_FORM" property="distSearchGroupId" size="20"/>
       <html:button property="locateGroup" onclick="popLocateFeedGlobal('../adminportal/grouplocate', 'distSearchGroupId', '');" value="Locate Group"/>
       </td>
  </tr>
<!---                 -->
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

<logic:present name="ITEM_AGGREGATE_MGR_FORM" property="distributors">
<bean:size id="rescount"  name="ITEM_AGGREGATE_MGR_FORM" property="distributors.values"/>
Search result count:  <bean:write name="rescount" />

<table cellspacing="0" border="0" width="769"  class="results">
<thead>
<tr align=left>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 0, false);">Distributor&nbsp;Id</a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 1, false);">ERP&nbsp;Num</a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 2, false);">Name</a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 3, false);">Address 1</a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 4, false);">City</a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 5, false);">State</a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 6, false);">Status</a></th>
<th class="tableheader">Select<br>
<a class="tableheader" href="javascript:SetCheckedGlobal(1,'distributors.selected')">[Check&nbsp;All]</a>
<br>
<a class="tableheader" href="javascript:SetCheckedGlobal(0,'distributors.selected')">[&nbsp;Clear]</a>
</th>
</tr>
</thead>

<tbody id="resTblBdy">
<logic:iterate id="arrele" name="ITEM_AGGREGATE_MGR_FORM" property="distributors.values" indexId="i">
        <tr>
        <td><bean:write name="arrele" property="busEntity.busEntityId"/></td>
        <td><bean:write name="arrele" property="busEntity.erpNum"/></td>
        <td>
                <bean:define id="eleid" name="arrele" property="busEntity.busEntityId"/>
                <a href="distmgr.do?action=distdetail&searchType=id&searchField=<%=eleid%>">
                  <bean:write name="arrele" property="busEntity.shortDesc"/>
                </a>
        </td>
        <td><bean:write name="arrele" property="primaryAddress.address1"/></td>
        <td><bean:write name="arrele" property="primaryAddress.city"/></td>
        <td><bean:write name="arrele" property="primaryAddress.stateProvinceCd"/></td>
        <td><bean:write name="arrele" property="busEntity.busEntityStatusCd"/></td>
        <%String selectedStr = "distributors.selected["+i+"]";%>
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

</logic:present>
</div>
</html:form>

<jsp:include flush='true' page="ui/admFooter.jsp"/>

</body>

</html:html>



