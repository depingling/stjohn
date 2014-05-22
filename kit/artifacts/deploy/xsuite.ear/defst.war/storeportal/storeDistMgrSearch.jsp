<%@ page language="java" %>
<%@ page import="com.cleanwise.service.api.util.LocatePropertyNames" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<SCRIPT TYPE="text/javascript" SRC="../externals/table-sort.js" CHARSET="ISO-8859-1"></SCRIPT>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="STORE_DIST_SEARCH_FORM" type="com.cleanwise.view.forms.StoreDistMgrSearchForm"/>

<jsp:include flush='true' page="locateStoreAccount.jsp">
    <jsp:param name="jspFormAction"           value="/storeportal/distmgr.do" />
    <jsp:param name="jspFormName"             value="STORE_DIST_SEARCH_FORM" />
    <jsp:param name="jspSubmitIdent"          value="" />
    <jsp:param name="jspReturnFilterProperty" value="filterDist" />
</jsp:include>

<jsp:include flush='true' page="locateStoreGroup.jsp">
    <jsp:param name="jspFormAction"           value="/storeportal/distmgr.do" />
    <jsp:param name="jspFormName"             value="STORE_DIST_SEARCH_FORM" />
    <jsp:param name="jspSubmitIdent"          value="" />
    <jsp:param name="isSingleSelect"          value="true" />
    <jsp:param name="jspReturnFilterProperty" value="storeGroupFilter"/>
</jsp:include>

<div class="text">

<html:form styleId="737" action="/storeportal/distmgr.do" scope="session">
<table ID=736 width="769" cellspacing="0" border="0" class="mainbody">
    <tr>
        <td><b>Find Distributor:</b></td>
        <td colspan="3">
            <html:text name="STORE_DIST_SEARCH_FORM" property="searchField"/>
            <html:radio name="STORE_DIST_SEARCH_FORM" property="searchType" value="id" />
            ID
            <html:radio name="STORE_DIST_SEARCH_FORM" property="searchType" value="nameBegins" />
            Name(starts with)
            <html:radio name="STORE_DIST_SEARCH_FORM" property="searchType" value="nameContains" />
            Name(contains)
        </td>
    </tr>
    <!-- Territory search -->
    <tr>
        <td style="padding-left: 10px"><b>Territory:</b></td>
        <td colspan="3"><b>City (starts with):</b>
            <html:text name="STORE_DIST_SEARCH_FORM" property="searchCity" size="10"/>
            <b>State:</b>
            <html:text name="STORE_DIST_SEARCH_FORM" property="searchState" size="3"/>
            <b>County (starts with)</b>:
            <html:text name="STORE_DIST_SEARCH_FORM" property="searchCounty" size="20"/>
            <b>Zip Code:</b>
            <html:text name="STORE_DIST_SEARCH_FORM" property="searchPostalCode" size="5"/>
        </td>
    </tr>
    <!-- Group Search -->
    <tr>
        <td style="padding-left: 10px"><b>Group:</b></td>
        <td colspan="3">
            <html:text name="STORE_DIST_SEARCH_FORM" property="searchGroupId" size="20"/>
            <html:submit property="action"><%=LocatePropertyNames.LOCATE_STORE_GROUP_ACTION%></html:submit>
        </td>
    <tr>
        <td>&nbsp;</td>
        <td colspan="3">
            <html:submit property="action"><app:storeMessage  key="global.action.label.search"/></html:submit>
            <html:submit property="action"><app:storeMessage  key="admin.button.create"/></html:submit>
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Show Inactive Distributors
            <html:checkbox name="STORE_DIST_SEARCH_FORM" property="searchShowInactiveFl"/>
        </td>
    </tr>
</table>

<logic:present name="StoreDist.found.vector">
<bean:size id="rescount"  name="StoreDist.found.vector"/>
Count:  <bean:write name="rescount" />

<table ID=738 cellspacing="0" border="0" width="769">
<thead>
<tr>
<th class="stpTH"><a ID=739 href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 0, false);">Distributor&nbsp;Id</a></th>
<th class="stpTH"><a ID=740 href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 1, false);">ERP&nbsp;Num</a></th>
<th class="stpTH"><a ID=741 href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 2, false);">Name</a></th>
<th class="stpTH"><a ID=742 href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 3, false);">Address 1</a></th>
<th class="stpTH"><a ID=743 href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 4, false);">City</a></th>
<th class="stpTH"><a ID=744 href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 5, false);">State</a></th>
<th class="stpTH"><a ID=745 href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 6, false);">Zip</a></th>
<th class="stpTH"><a ID=746 href="#pgsort" onclick="this.blur(); return f_sortTable('resTblBdy', 7, false);">Status</a></th>
</tr>
</thead>

<tbody id="resTblBdy">
<logic:iterate id="arrele" name="StoreDist.found.vector">
<tr>
<td class="stpTD"><bean:write name="arrele" property="busEntity.busEntityId"/></td>
<td class="stpTD"><bean:write name="arrele" property="busEntity.erpNum"/></td>
<td class="stpTD">
        <bean:define id="eleid" name="arrele" property="busEntity.busEntityId"/>
        <a ID=747 href="distdet.do?action=distdetail&searchType=id&searchField=<%=eleid%>">
          <bean:write name="arrele" property="busEntity.shortDesc"/>
        </a>
</td>
<td class="stpTD"><bean:write name="arrele" property="primaryAddress.address1"/></td>
<td class="stpTD"><bean:write name="arrele" property="primaryAddress.city"/></td>
<td class="stpTD"><bean:write name="arrele" property="primaryAddress.stateProvinceCd"/></td>
<td class="stpTD"><bean:write name="arrele" property="primaryAddress.postalCode"/></td>
<td class="stpTD"><bean:write name="arrele" property="busEntity.busEntityStatusCd"/></td>
</tr>
</logic:iterate>
</tbody>
</table>

</logic:present>

</html:form>
</div>
<script>
var focusControl = document.forms[0]['searchField'];
if('undefined' != typeof focusControl) {
    focusControl.focus();
}
function kH(e) {
    var keyCode = 0;
    if (window.event) {
        keyCode = window.event.keyCode;
    } else if (e) {
        keyCode = e.keyCode;
    }
    if (keyCode == 13) {
        var actionButton = document.forms[0]['action'];
        if('undefined' != typeof actionButton) {
            var len = actionButton.length;
            for(ii = 0; ii < len; ii++) {
                if('Search' == actionButton[ii].value) {
                    actionButton[ii].click();
                    break;
                }
            }
        }
        return false;
    }
    return true;
}
document.onkeypress = kH;
</script>