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

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<div class="text">

<table width="769" cellspacing="0" border="0" class="mainbody">
  <html:form name="ACCOUNT_SEARCH_FORM" action="adminportal/accountmgr.do"
    scope="session" type="com.cleanwise.view.forms.AccountMgrSearchForm">
  <tr> <td><b>Find Account:</b></td>
       <td colspan="3">
                        <html:text name="ACCOUNT_SEARCH_FORM" property="searchField"/>
       </td>
  </tr>
  <tr> <td>&nbsp;</td>
       <td colspan="3">
         <html:radio name="ACCOUNT_SEARCH_FORM" property="searchType" value="id" />
         ID
         <html:radio name="ACCOUNT_SEARCH_FORM" property="searchType" value="nameBegins" />
         Name(starts with)
         <html:radio name="ACCOUNT_SEARCH_FORM" property="searchType" value="nameContains" />
         Name(contains)
         </td>
  </tr>
  <!-- Group Search -->
  <tr><td><b>Group:</b></td>
       <td colspan="3">
       <html:text name="ACCOUNT_SEARCH_FORM" property="searchGroupId" size="20"/>
       <html:button property="locateGroup" onclick="popLocateFeedGlobal('../adminportal/grouplocate', 'searchGroupId', '');" value="Locate Group"/>
       </td>
  </tr>
  <tr> <td>&nbsp;</td>
       <td colspan="3">
        <html:submit property="action">
                <app:storeMessage  key="global.action.label.search"/>
        </html:submit>
        <html:submit property="action">
                <app:storeMessage  key="admin.button.viewall"/>
        </html:submit>
        <html:submit property="action">
                <app:storeMessage  key="admin.button.create"/>
        </html:submit>
     </html:form>
    </td>
  </tr>
</table>

<logic:present name="Account.found.vector">
<bean:size id="rescount"  name="Account.found.vector"/>
Search result count:  <bean:write name="rescount" />
<logic:greaterThan name="rescount" value="0">


<table width="769" cellspacing="0" border="0" class="results">
<thead>
<tr align=left>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 0, false);">Account&nbsp;Id</a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 1, false);">Erp#</a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 2, false);">Name</a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 3, false);">City</a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 4, false);">State/Province</a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 5, false);">Type</a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 6, false);">Status</a></th>
<th>Select</th>
</tr>
</thead>

<tbody id="resTblBdy">
<logic:iterate id="arrele" name="Account.found.vector">
<tr>
<td><bean:write name="arrele" property="busEntity.busEntityId"/></td>
<td><bean:write name="arrele" property="busEntity.erpNum"/></td>
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
</tbody>
</table>

</logic:greaterThan>
</logic:present>

</div>


