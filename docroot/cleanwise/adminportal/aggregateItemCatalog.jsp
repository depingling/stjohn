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
<html:hidden name="ITEM_AGGREGATE_MGR_FORM" property="wizardStep" value="catalogselect"/>

  <tr> <td><b>Find Catalog:</b></td>
     <td colspan="3">
          <html:text name="ITEM_AGGREGATE_MGR_FORM" property="catalogSearchField"/>
     </td>
  </tr>
  <tr> <td>&nbsp;</td>
       <td colspan="3">
         <html:radio name="ITEM_AGGREGATE_MGR_FORM" property="catalogSearchType" value="catalogId" />
         ID
         <html:radio name="ITEM_AGGREGATE_MGR_FORM" property="catalogSearchType" value="catalogNameStarts" />
         Name(starts with)
         <html:radio name="ITEM_AGGREGATE_MGR_FORM" property="catalogSearchType" value="catalogNameContains" />
         Name(contains)
         </td>
  </tr>
  <tr> <td>&nbsp;</td>
       <td colspan="3">
               <html:submit property="action">
                        <app:storeMessage  key="global.action.label.search"/>
                </html:submit>
                <html:submit property="action">
                        <app:storeMessage  key="global.action.label.next"/>
                </html:submit>
        </td>
  </tr>
</table>

<logic:present name="ITEM_AGGREGATE_MGR_FORM" property="catalogs">
<bean:size id="rescount"  name="ITEM_AGGREGATE_MGR_FORM" property="catalogs.values"/>
Search result count:  <bean:write name="rescount" />

<table cellspacing="0" border="0" width="769"  class="results">
<thead>
<tr align=left>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 0, false);">Catalog&nbsp;Id</a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 1, false);">Name</a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 2, false);">Type</a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 3, false);">Status</a></th>
<th class="tableheader">Select<br>
<a class="tableheader" href="javascript:SetCheckedGlobal(1,'catalogs.selected')">[Check&nbsp;All]</a>
<br>
<a class="tableheader" href="javascript:SetCheckedGlobal(0,'catalogs.selected')">[&nbsp;Clear]</a>
</th>
</tr>
</thead>
   <logic:iterate id="catalog" name="ITEM_AGGREGATE_MGR_FORM" indexId="i" property="catalogs.values" type="com.cleanwise.service.api.value.CatalogData">
    <bean:define id="key"  name="catalog" property="catalogId"/>
    <bean:define id="name" name="catalog" property="shortDesc"/>
    <% String linkHref = new String("catalogdetail.do?action=edit&id=" + key);%>
    <tr>
        <td><bean:write name="catalog" property="catalogId"/></td>
        <td><html:link href="<%=linkHref%>"><bean:write name="catalog" property="shortDesc" filter="true"/></html:link></td>
        <td><bean:write name="catalog" property="catalogTypeCd"/></td>
        <td><bean:write name="catalog" property="catalogStatusCd"/></td>
        <%String selectedStr = "catalogs.selected["+i+"]";%>
        <td><html:multibox name="ITEM_AGGREGATE_MGR_FORM" property="<%=selectedStr%>" value="true"/></td>
    </tr>
   </logic:iterate>

   <tr align=center>
        <td colspan="4">
                <html:submit property="action">
                        <app:storeMessage  key="global.action.label.next"/>
                </html:submit>
        </td>
   </tr>

</table>
</logic:present>
</div>
</div>
</html:form>


<jsp:include flush='true' page="ui/admFooter.jsp"/>

</body>

</html:html>


