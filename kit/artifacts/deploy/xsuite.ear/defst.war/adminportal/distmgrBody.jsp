<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<SCRIPT TYPE="text/javascript" SRC="../externals/table-sort.js"
  CHARSET="ISO-8859-1"></SCRIPT>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<% 
  String portal = request.getParameter("portal");
  boolean adminPortalFl = ("adminportal".equalsIgnoreCase(portal))?true:false;
  String actionStr = (adminPortalFl)?"adminportal/distmgr.do":"console/crcdist";
  String detailLinkAction = (adminPortalFl)?"distItemMgr.do":"crcdistItem.do";
  boolean readOnlyFl = (adminPortalFl)?false:true;

%>
<div class="text">

  <table width="769" cellspacing="0" border="0" class="mainbody">
  <html:form name="DIST_SEARCH_FORM" action="<%=actionStr%>"
    scope="session" type="com.cleanwise.view.forms.DistMgrSearchForm">
  <tr> <td><b>Find Distributor:</b></td>
<td colspan="3">
<html:text name="DIST_SEARCH_FORM" property="searchField"/>
         <html:radio name="DIST_SEARCH_FORM" property="searchType" value="id" />
         ID
         <html:radio name="DIST_SEARCH_FORM" property="searchType" value="nameBegins" />
         Name(starts with)
         <html:radio name="DIST_SEARCH_FORM" property="searchType" value="nameContains" />
         Name(contains)
</td>

  </tr>
  <!-- Territory search -->
  <tr><td style="padding-left: 10px"><b>Territory:</b> </td>
      <td colspan="3"><b>City (starts with):</b>
<html:text name="DIST_SEARCH_FORM" property="searchCity" size="10"/>
<b>State:</b>
<html:text name="DIST_SEARCH_FORM" property="searchState" size="3"/>
 <b>County (starts with)</b>:
 <html:text name="DIST_SEARCH_FORM" property="searchCounty" size="20"/>
 <b>Zip Code:</b>
 <html:text name="DIST_SEARCH_FORM" property="searchPostalCode" size="5"/>
</td>

  </tr>
  <!-- Group Search -->
  <tr><td style="padding-left: 10px">
<b>Group:</b></td>
       <td colspan="3">
<html:text name="DIST_SEARCH_FORM" property="searchGroupId" size="20"/>
       <html:button property="locateGroup" onclick="popLocateFeedGlobal('../adminportal/grouplocate', 'searchGroupId', '');" value="Locate Group"/>
       </td>
  <tr> <td>&nbsp;</td>
       <td colspan="3">
        <html:submit property="action">
                <app:storeMessage  key="global.action.label.search"/>
        </html:submit>
        <html:submit property="action">
                <app:storeMessage  key="admin.button.viewall"/>
        </html:submit>
<% if(!readOnlyFl) { %>        
        <html:submit property="action">
                <app:storeMessage  key="admin.button.create"/>
        </html:submit>
<% } %>     
     </html:form>
    </td>
  </tr>
</table>

<logic:present name="Dist.found.vector">
<bean:size id="rescount"  name="Dist.found.vector"/>
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
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 6, false);">Zip</a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 7, false);">Status</a></th>
</tr>
</thead>

<tbody id="resTblBdy">
<logic:iterate id="arrele" name="Dist.found.vector">
<tr>
<td><bean:write name="arrele" property="busEntity.busEntityId"/></td>
<td><bean:write name="arrele" property="busEntity.erpNum"/></td>
<td>
        <bean:define id="eleid" name="arrele" property="busEntity.busEntityId"/>
<!--
        <a href="distmgr.do?action=distdetail&searchType=id&searchField=<%=eleid%>">
-->
        <a href="<%=detailLinkAction%>?action=init&searchType=id&searchField=<%=eleid%>">
          <bean:write name="arrele" property="busEntity.shortDesc"/>
        </a>
</td>
<td><bean:write name="arrele" property="primaryAddress.address1"/></td>
<td><bean:write name="arrele" property="primaryAddress.city"/></td>
<td><bean:write name="arrele" property="primaryAddress.stateProvinceCd"/></td>
<td><bean:write name="arrele" property="primaryAddress.postalCode"/></td>
<td><bean:write name="arrele" property="busEntity.busEntityStatusCd"/></td>
</tr>
</logic:iterate>
</tbody>
</table>

</logic:present>


</div>





