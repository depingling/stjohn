<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<% 
  String portal = request.getParameter("portal");
  boolean adminPortalFl = ("adminportal".equalsIgnoreCase(portal))?true:false;
  String upperLink = "distmgr.do";
  String thisLink = "distmgrDetail.do";
  boolean readOnlyFl = (adminPortalFl)?false:true;
  String disabledStr = (readOnlyFl)?"disabled='true'":"";
  
%>  

<div style="width: <%=Constants.TABLEWIDTH%>;">
<bean:parameter id="editShipFromId" name="ship_from_id" value="0"/>
<logic:equal name="editShipFromId" value="-1">

<div style="background-color: #cc99cc;">
<b>Add a new ship from address.</b><br>
<hr>
<div align="right">

<table>
<tr>
<td><b>Ship From Name:</b></td>
<td>
<html:text
  name="DIST_DETAIL_FORM" property="shipFrom.name1"
  tabindex="30" maxlength="30" readonly='<%=readOnlyFl%>' />
</td>

<td><b>City:</b></td>
<td>
<html:text
  name="DIST_DETAIL_FORM" property="shipFrom.city"
  tabindex="40"maxlength="40" readonly='<%=readOnlyFl%>' />
</td>
</tr>

<tr>
<td><b>Street Address 1:</b></td>
<td>
<html:text
  name="DIST_DETAIL_FORM" property="shipFrom.address1"
  tabindex="31" maxlength="80" readonly='<%=readOnlyFl%>' />
</td>

<td><b>State/Province:</b></td>
<td>
<html:text size="20" maxlength="80"
  name="DIST_DETAIL_FORM" property="shipFrom.stateProvinceCd"
  tabindex="41" readonly='<%=readOnlyFl%>' />
</td>

</tr>

<tr>
<td><b></td>
<td>
</td>

<td><b>Zip/Postal Code:</b></td>
<td>
<html:text
  name="DIST_DETAIL_FORM" property="shipFrom.postalCode"
  tabindex="42" 	maxlength="15" readonly='<%=readOnlyFl%>' />
</td>
</tr>

<tr>
<td>
</td>
<td>
</td>

<td><b>Country:</b></td>
<td>
<html:select name="DIST_DETAIL_FORM" property="shipFrom.countryCd"
  tabindex="43" disabled='<%=readOnlyFl%>'>
<html:option value=""><app:storeMessage  key="admin.select.country"/></html:option>
<html:options  collection="countries.vector" property="value" />
</html:select>
</td>
</tr>

</table>

<html:submit property="action" tabindex="44" >
Add Ship From Address
</html:submit>

</div> <% /* alignment block (right). */ %>

</div> <% /* Color block. */ %>

</logic:equal>

<div width="<%=Constants.TABLEWIDTH%>" align="right">
<a href="<%=upperLink%>?action=edit_ship_from&amp;ship_from_id=0">
[List ship from address(es)]</a>&nbsp;&nbsp;
<logic:notEqual name="editShipFromId" value="-1">
<% if(!readOnlyFl) { %>
<a href="<%=upperLink%>?action=edit_ship_from&amp;ship_from_id=-1">
[Add a ship from address]
</a>
<% } %>
</div>
</logic:notEqual>

</div>



<logic:present name="Dist.ship_from.vector">

<bean:size id="shipFromCount" name="Dist.ship_from.vector"/>
<b>Ship from location count:</b> <bean:write name="shipFromCount"/>

<% /* Start - Ship from locations. */ %>
<logic:greaterThan name="shipFromCount" value="0">
<table>

<% if (editShipFromId.toString().equals("0")) { %>

<tr>
<td><a class="tableheader" href="<%=thisLink%>?action=sortShipFrom&amp;sortField=ShipFromAddressId">Ship From Id    </a></td>
<td><a class="tableheader" href="<%=thisLink%>?action=sortShipFrom&amp;sortField=ShipFromName"> Ship From Name  </a></td>
<td><a class="tableheader" href="<%=thisLink%>?action=sortShipFrom&amp;sortField=ShipFromAddress1"> Address 1       </a></td>
<td><a class="tableheader" href="<%=thisLink%>?action=sortShipFrom&amp;sortField=ShipFromCity"> City            </a></td>
<td><a class="tableheader" href="<%=thisLink%>?action=sortShipFrom&amp;sortField=ShipFromState"> State           </a></td>
<td><a class="tableheader" href="<%=thisLink%>?action=sortShipFrom&amp;sortField=ShipFromStatus"> Status          </a></td>
</tr>

<% } %>

<logic:iterate id="shipFromAddr" name="Dist.ship_from.vector"
  type="com.cleanwise.service.api.value.DistShipFromAddressView"
  indexId="idx" >

<bean:define id="sfid" name="shipFromAddr" property="shipFromAddressId" />

<% if ( sfid.toString().equals( editShipFromId.toString() ) ) { %>
<tr><td colspan=6>

<% /* Start - Edit this  ship from address. */ %>
<a>Edit ( <%=sfid%> ) </a><br>

<html:hidden name="DIST_DETAIL_FORM"
  property="shipFrom.addressId" value="<%=sfid.toString()%>" />

<table>
<tr>
<td><b>Ship From Name:</b></td>
<td>
<html:text name="DIST_DETAIL_FORM"
  property="shipFrom.name1"
  tabindex="50" maxlength="30"
  value="<%=shipFromAddr.getShipFromName()%>" readonly='<%=readOnlyFl%>' />
</td>

<td><b>City:</b></td>
<td>
<html:text
  name="DIST_DETAIL_FORM" property="shipFrom.city"
  tabindex="60" maxlength="40"
  value="<%=shipFromAddr.getShipFromCity()%>" readonly='<%=readOnlyFl%>' />

</td>
</tr>

<tr>
<td><b>Street Address 1:</b></td>
<td>
<html:text
  name="DIST_DETAIL_FORM" property="shipFrom.address1"
  tabindex="51" maxlength="80"
  value="<%=shipFromAddr.getShipFromAddress1()%>" readonly='<%=readOnlyFl%>' />
</td>

<td><b>State/Province:</b></td>
<td>
<html:text size="20" maxlength="80"
  name="DIST_DETAIL_FORM" property="shipFrom.stateProvinceCd"
  tabindex="61"
  value="<%=shipFromAddr.getShipFromState()%>" readonly='<%=readOnlyFl%>' />
</td>

</tr>

<tr>
<td><b>Street Address 2:</b></td>
<td>
<html:text
  name="DIST_DETAIL_FORM" property="shipFrom.address2"
  tabindex="52" maxlength="80"
  value="<%=shipFromAddr.getShipFromAddress2()%>" readonly='<%=readOnlyFl%>' />
</td>

<td><b>Zip/Postal Code:</b></td>
<td>
<html:text
  name="DIST_DETAIL_FORM" property="shipFrom.postalCode"
  tabindex="62" maxlength="80"
  value="<%=shipFromAddr.getShipFromPostalCode()%>" readonly='<%=readOnlyFl%>' />
</td>

</tr>

<tr>
<td><b>Street Address 3:</b></td>
<td>
<html:text
  name="DIST_DETAIL_FORM" property="shipFrom.address3"
  tabindex="53" maxlength="80"
  value="<%=shipFromAddr.getShipFromAddress3()%>" readonly='<%=readOnlyFl%>' />
</td>

<td><b>Country:</b></td>
<td>
<html:select
  name="DIST_DETAIL_FORM" property="shipFrom.countryCd"
  tabindex="62" disabled='<%=readOnlyFl%>'>
<html:option value="<%=shipFromAddr.getShipFromCountryCd()%>" />
<html:options  collection="countries.vector" property="value" />
</html:select>

</td>
</tr>
<tr>
<td><b>Status:</b></td>
<td>
<html:select
  name="DIST_DETAIL_FORM" property="shipFrom.addressStatusCd"
  tabindex="54" disabled='<%=readOnlyFl%>'>
<html:option value="<%=shipFromAddr.getShipFromStatus()%>" />
<html:option value="<%=RefCodeNames.ADDRESS_STATUS_CD.ACTIVE%>"/>
<html:option value="<%=RefCodeNames.ADDRESS_STATUS_CD.INACTIVE%>"/>
</html:select>

</td>
</tr>

</table>

<html:submit property="action" tabindex="63" >
  Update Ship From Address</html:submit>
<html:submit property="action" tabindex="15" >
  Delete Ship From Address </html:submit>

<% /* End - Edit this  ship from address. */ %>

</td></tr>
<% } else if (editShipFromId.toString().equals("0")) { %>

<% if (( idx.intValue() % 2 ) == 0 ) { %> <tr class="rowa">
<% } else { %> <tr class="rowb"> <% } %>

<td>
<a href="<%=upperLink%>?action=edit_ship_from&amp;ship_from_id=<%=sfid%>">
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


