<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<%
  int tabIndex = 0;
  Integer tabIndexInteger = (Integer)request.getAttribute("tabIndex");
  if (tabIndexInteger != null) {
	  tabIndex = tabIndexInteger.intValue();
  }
%>
<div style="width: <%=Constants.TABLEWIDTH%>;">
<bean:parameter id="editShipFromId" name="ship_from_id" value="0"/>

<logic:equal name="editShipFromId" value="-1">

<div style="background-color: #cc99cc;">
<b>Add a new ship from address.</b><br>
<hr>
<div align="right">

<table ID=297>
<tr>
<td><b>Ship From Name:</b></td>
<td>
<html:text
  name="STORE_DIST_DETAIL_FORM" property="shipFrom.name1"
  tabindex='<%=tabIndex++ + ""%>' maxlength="30" />
</td>

<td><b>City:</b></td>
<td>
<html:text
  name="STORE_DIST_DETAIL_FORM" property="shipFrom.city"
  tabindex='<%=tabIndex++ + ""%>' maxlength="40" />
</td>
</tr>

<tr>
<td><b>Street Address 1:</b></td>
<td>
<html:text
  name="STORE_DIST_DETAIL_FORM" property="shipFrom.address1"
  tabindex='<%=tabIndex++ + ""%>' maxlength="80" />
</td>

<td><b>State/Province:</b></td>
<td>
<html:text size="20" maxlength="80"
  name="STORE_DIST_DETAIL_FORM" property="shipFrom.stateProvinceCd"
  tabindex='<%=tabIndex++ + ""%>' />
</td>

</tr>

<tr>
<td><b></td>
<td>
</td>

<td><b>Zip/Postal Code:</b></td>
<td>
<html:text
  name="STORE_DIST_DETAIL_FORM" property="shipFrom.postalCode"
  tabindex='<%=tabIndex++ + ""%>' 	maxlength="15" />
</td>
</tr>

<tr>
<td>
</td>
<td>
</td>

<td><b>Country:</b></td>
<td>
<html:select name="STORE_DIST_DETAIL_FORM" property="shipFrom.countryCd"
  tabindex='<%=tabIndex++ + ""%>'>
<html:option value=""><app:storeMessage  key="admin.select.country"/></html:option>
<html:options  collection="countries.vector" property="value" />
</html:select>
</td>
</tr>

</table>

<html:submit property="action" tabindex='<%=tabIndex++ + ""%>' >
Add Ship From Address
</html:submit>

</div> <% /* alignment block (right). */ %>

</div> <% /* Color block. */ %>

</logic:equal>

<div width="<%=Constants.TABLEWIDTH%>" align="right">
<a tabindex='<%=tabIndex++ + ""%>' ID=298 href="distdet.do?action=edit_ship_from&amp;ship_from_id=0">
[List ship from address(es)]</a>&nbsp;&nbsp;
<%--<logic:notEqual name="editShipFromId" value="-1">
<a tabindex='<%=tabIndex++ + ""%>' ID=299 href="distdet.do?action=edit_ship_from&amp;ship_from_id=-1">
[Add a ship from address]
</a>
</div>
</logic:notEqual>--%>

</div>



<logic:present name="StoreDist.ship_from.vector">

<bean:size id="shipFromCount" name="StoreDist.ship_from.vector"/>
<b>Ship from location count:</b> <bean:write name="shipFromCount"/>

<% /* Start - Ship from locations. */ %>
<logic:greaterThan name="shipFromCount" value="0">
<table ID=300>

<% if (editShipFromId.toString().equals("0")) { %>

<tr>
<td><a tabindex='<%=tabIndex++ + ""%>' ID=301 class="tableheader" href="distdet.do?action=sortShipFrom&amp;sortField=ShipFromAddressId">Ship From Id    </a></td>
<td><a tabindex='<%=tabIndex++ + ""%>' ID=302 class="tableheader" href="distdet.do?action=sortShipFrom&amp;sortField=ShipFromName"> Ship From Name  </a></td>
<td><a tabindex='<%=tabIndex++ + ""%>' ID=303 class="tableheader" href="distdet.do?action=sortShipFrom&amp;sortField=ShipFromAddress1"> Address 1       </a></td>
<td><a tabindex='<%=tabIndex++ + ""%>' ID=304 class="tableheader" href="distdet.do?action=sortShipFrom&amp;sortField=ShipFromCity"> City            </a></td>
<td><a tabindex='<%=tabIndex++ + ""%>' ID=305 class="tableheader" href="distdet.do?action=sortShipFrom&amp;sortField=ShipFromState"> State           </a></td>
<td><a tabindex='<%=tabIndex++ + ""%>' ID=306 class="tableheader" href="distdet.do?action=sortShipFrom&amp;sortField=ShipFromStatus"> Status          </a></td>
</tr>

<% } %>

<logic:iterate id="shipFromAddr" name="StoreDist.ship_from.vector"
  type="com.cleanwise.service.api.value.DistShipFromAddressView"
  indexId="idx" >

<bean:define id="sfid" name="shipFromAddr" property="shipFromAddressId" />

<% if ( sfid.toString().equals( editShipFromId.toString() ) ) { %>
<tr><td colspan=6>

<% /* Start - Edit this  ship from address. */ %>
<a ID=307>Edit ( <%=sfid%> ) </a><br>

<html:hidden name="STORE_DIST_DETAIL_FORM"
  property="shipFrom.addressId" value="<%=sfid.toString()%>" />

<table ID=308>
<tr>
<td><b>Ship From Name:</b></td>
<td>
<html:text name="STORE_DIST_DETAIL_FORM"
  property="shipFrom.name1"
  tabindex='<%=tabIndex++ + ""%>' maxlength="30"
  value="<%=shipFromAddr.getShipFromName()%>" />
</td>

<td><b>City:</b></td>
<td>
<html:text
  name="STORE_DIST_DETAIL_FORM" property="shipFrom.city"
  tabindex='<%=tabIndex++ + ""%>' maxlength="40"
  value="<%=shipFromAddr.getShipFromCity()%>" />

</td>
</tr>

<tr>
<td><b>Street Address 1:</b></td>
<td>
<html:text
  name="STORE_DIST_DETAIL_FORM" property="shipFrom.address1"
  tabindex='<%=tabIndex++ + ""%>' maxlength="80"
  value="<%=shipFromAddr.getShipFromAddress1()%>" />
</td>

<td><b>State/Province:</b></td>
<td>
<html:text size="20" maxlength="80"
  name="STORE_DIST_DETAIL_FORM" property="shipFrom.stateProvinceCd"
  tabindex='<%=tabIndex++ + ""%>'
  value="<%=shipFromAddr.getShipFromState()%>" />
</td>

</tr>

<tr>
<td><b>Street Address 2:</b></td>
<td>
<html:text
  name="STORE_DIST_DETAIL_FORM" property="shipFrom.address2"
  tabindex='<%=tabIndex++ + ""%>' maxlength="80"
  value="<%=shipFromAddr.getShipFromAddress2()%>" />
</td>

<td><b>Zip/Postal Code:</b></td>
<td>
<html:text
  name="STORE_DIST_DETAIL_FORM" property="shipFrom.postalCode"
  tabindex='<%=tabIndex++ + ""%>' maxlength="80"
  value="<%=shipFromAddr.getShipFromPostalCode()%>" />
</td>

</tr>

<tr>
<td><b>Street Address 3:</b></td>
<td>
<html:text
  name="STORE_DIST_DETAIL_FORM" property="shipFrom.address3"
  tabindex='<%=tabIndex++ + ""%>' maxlength="80"
  value="<%=shipFromAddr.getShipFromAddress3()%>" />
</td>

<td><b>Country:</b></td>
<td>
<html:select
  name="STORE_DIST_DETAIL_FORM" property="shipFrom.countryCd"
  tabindex='<%=tabIndex++ + ""%>'>
<html:option value="<%=shipFromAddr.getShipFromCountryCd()%>" />
<html:options  collection="countries.vector" property="value" />
</html:select>

</td>
</tr>
<tr>
<td><b>Status:</b></td>
<td>
<html:select
  name="STORE_DIST_DETAIL_FORM" property="shipFrom.addressStatusCd"
  tabindex='<%=tabIndex++ + ""%>'>
<html:option value="<%=shipFromAddr.getShipFromStatus()%>" />
<html:option value="<%=RefCodeNames.ADDRESS_STATUS_CD.ACTIVE%>"/>
<html:option value="<%=RefCodeNames.ADDRESS_STATUS_CD.INACTIVE%>"/>
</html:select>

</td>
</tr>

</table>

<html:submit property="action" tabindex='<%=tabIndex++ + ""%>' >
  Update Ship From Address</html:submit>
<html:submit property="action" tabindex='<%=tabIndex++ + ""%>' >
  Delete Ship From Address </html:submit>

<% /* End - Edit this  ship from address. */ %>

</td></tr>
<% } else if (editShipFromId.toString().equals("0")) { %>

<% if (( idx.intValue() % 2 ) == 0 ) { %> <tr class="rowa">
<% } else { %> <tr class="rowb"> <% } %>

<td>
<a tabindex='<%=tabIndex++ + ""%>' ID=309 href="distdet.do?action=edit_ship_from&amp;ship_from_id=<%=sfid%>">
<bean:write name="shipFromAddr" property="shipFromAddressId"/>
</a>
</td>
<td><bean:write name="shipFromAddr" property="shipFromName"/></td>
<td><bean:write name="shipFromAddr" property="shipFromAddress1"/></td>
<td><bean:write name="shipFromAddr" property="shipFromCity"/></td>
<td><bean:write name="shipFromAddr" property="shipFromState"/></td>
<td><bean:write name="shipFromAddr" property="shipFromStatus"/></td>
</tr>
<% } %>
</logic:iterate>

</table>

</logic:greaterThan>
</logic:present>
<%
	//add the tabIndex to the request so subsequent pages can make use of it
	tabIndexInteger = new Integer(tabIndex);
	request.setAttribute("tabIndex", tabIndexInteger);
%>


