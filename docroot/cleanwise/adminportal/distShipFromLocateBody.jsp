
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<%
        String feedField =  (String)request.getParameter("feedField");
        if(null == feedField) {
                feedField = new String("");
        }
        String feedDesc =  (String)request.getParameter("feedDesc");
        if(null == feedDesc) {
                feedDesc = new String("");
        }
        String distributorId = (String)request.getParameter("distributorId");
        if(null == distributorId) {
                distributorId = new String("");
        }
%>

<script language="JavaScript1.2">
<!--

function passId(id) {
  var feedBackFieldName = document.forms[0].feedField.value;

  if(feedBackFieldName && ""!= feedBackFieldName) {
    window.opener.document.forms[0].elements[feedBackFieldName].value = id;
  }
  self.close();
}

//-->
</script>


<div class="text">

<html:form name="DIST_SHIP_SEARCH_FORM"
action="/adminportal/distShipFromLocate.do" focus="searchField"
type="com.cleanwise.view.forms.DistMgrLocateShipFrom">

<input type="hidden" name="feedField" value="<%=feedField%>">
<input type="hidden" name="feedDesc" value="<%=feedDesc%>">
<input type="hidden" name="distributorId" value="<%=distributorId%>">
<table bgcolor="#cccccc" >
<tr> <td><b>Find ship from location:</b></td>
<td colspan="3">
<html:text name="DIST_SHIP_SEARCH_FORM" property="searchField"/>
</td>
</tr>

<tr> <td>&nbsp;</td>
<td colspan="3">
<html:radio name="DIST_SHIP_SEARCH_FORM" property="searchType"
  value="id" />ID
<html:radio name="DIST_SHIP_SEARCH_FORM" property="searchType"
  value="nameBegins" />Name(starts with)
<html:radio name="DIST_SHIP_SEARCH_FORM" property="searchType"
  value="nameContains" />Name(contains)
</td>
</tr>
<tr>
<td></td>
<td>
<html:submit property="action">
                <app:storeMessage  key="global.action.label.search"/>
</html:submit>
<html:submit property="action">
                <app:storeMessage  key="admin.button.viewall"/>
</html:submit>
</td></tr>
</table>

</html:form>

<logic:present name="Dist.ship_from.vector">
<bean:size id="shipFromCount" name="Dist.ship_from.vector"/>
<b>Ship from location count:</b> <bean:write name="shipFromCount"/>
<div style="width: <%=Constants.TABLEWIDTH%>; margin-left: 20px;" class="results">

<logic:greaterThan name="shipFromCount" value="0">
<table>

<tr>
<td><a class="tableheader" href="distShipFromLocate.do?action=sortShipFrom&amp;sortField=DistributorId">Distributor Id</a></td>

<td><a class="tableheader" href="distShipFromLocate.do?action=sortShipFrom&amp;sortField=DistName">Distributor Name</a></td>

<td><a class="tableheader" href="distShipFromLocate.do?action=sortShipFrom&amp;sortField=ShipFromAddressId">Ship From Id    </a></td>
<td><a class="tableheader" href="distShipFromLocate.do?action=sortShipFrom&amp;sortField=ShipFromName"> Ship From Name  </a></td>
<td><a class="tableheader" href="distShipFromLocate.do?action=sortShipFrom&amp;sortField=ShipFromAddress1"> Address 1       </a></td>
<td><a class="tableheader" href="distShipFromLocate.do?action=sortShipFrom&amp;sortField=ShipFromCity"> City            </a></td>
<td><a class="tableheader" href="distShipFromLocate.do?action=sortShipFrom&amp;sortField=ShipFromState"> State           </a></td>
<td><a class="tableheader" href="distShipFromLocate.do?action=sortShipFrom&amp;sortField=ShipFromStatus"> Status          </a></td>
</tr>


<logic:iterate id="shipFromAddr" name="Dist.ship_from.vector"
  type="com.cleanwise.service.api.value.DistShipFromAddressView"
  indexId="idx" >

<bean:define id="sfid" name="shipFromAddr" property="shipFromAddressId" />

<% if (( idx.intValue() % 2 ) == 0 ) { %> <tr class="rowa">
<% } else { %> <tr class="rowb"> <% } %>

<td><bean:write name="shipFromAddr" property="distributorId"/></td>
<td><bean:write name="shipFromAddr" property="distName"/></td>

<td>
<%
  String onClick = "return passId('" + sfid + "');" ;
%>

<a href="javascript:void(0);" onclick="<%=onClick%>">
<bean:write name="shipFromAddr" property="shipFromAddressId"/>
</a>
</td>
<td><bean:write name="shipFromAddr" property="shipFromName"/></td>
<td><bean:write name="shipFromAddr" property="shipFromAddress1"/></td>
<td><bean:write name="shipFromAddr" property="shipFromCity"/></td>
<td><bean:write name="shipFromAddr" property="shipFromState"/></td>
<td><bean:write name="shipFromAddr" property="shipFromStatus"/></td>
</tr>

</logic:iterate>

</table>

</logic:greaterThan>
</logic:present>

</div>


