<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<% boolean clwSwitch = ClwCustomizer.getClwSwitch(); %>
<% String storeDir = ClwCustomizer.getStoreDir(); %>
<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="Location" value="account" type="java.lang.String" toScope="session"/>
<script language="JavaScript1.2">
<!--


//-->
</script>

<style>
.tt {
  color: white;
  background-color: black;
}
.ttcell {
  border-right: solid 1px black;
  border-bottom: solid 1px black;
}
.ttcell_highlight {
  background-color: #cccccc;
  border-right: solid 1px black;
  border-bottom: solid 1px black;
}

a.tt:link, a.tt:visited, a.tt:active	{
 color: #ffffff;
 font-weight: bold;    font-size: 10pt;
 text-decoration: none
}

.invhead {
 background-color: #cccccc;
}
</style>

<SCRIPT TYPE="text/javascript" SRC="../externals/table-sort.js"
  CHARSET="ISO-8859-1">
  </SCRIPT>

<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Application Administrator Home: Inventory Items</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<body>

<html:form styleId="837" name="STORE_ACCOUNT_DETAIL_FORM"
action="/storeportal/storeInventoryMgr.do"
scope="session"
type="com.cleanwise.view.forms.StoreAccountMgrDetailForm">




<jsp:include flush='true' page="storeAccountCtx.jsp"/>

<div style="{width: 769; text-align: center}">
<center><font color=red><html:errors/></font></center>

<bean:size id="numberOfItemsToAdd" name="STORE_ACCOUNT_DETAIL_FORM"
  property="inventoryItemsAvailable" />

<%
String action = request.getParameter("action");
if (action == null) action = "";
if (action.startsWith("select") == false ) {
/* Show the current list of inventory items. */
%>

<p class="invhead">
Inventory items configured:
<bean:write name="STORE_ACCOUNT_DETAIL_FORM" property="accountData.numberOfInventoryItems" />
</p>

<logic:greaterThan name="STORE_ACCOUNT_DETAIL_FORM" property="accountData.numberOfInventoryItems"
  value="0">

<table ID=838><thead><tr>
<th  class="tt">
<a ID=839 class="tt" href="#pgsort" onclick="this.blur(); return f_sortTable('itemTblBdy1', 0, false);"
  title="-- Sort by SKU">SKU</a></th>
<th  class="tt">
<a ID=840 class="tt" href="#pgsort" onclick="this.blur(); return f_sortTable('itemTblBdy1', 1, false);"
  title="-- Sort by Description">Description</a></th>
<th  class="tt">
<a ID=841 class="tt" href="#pgsort" onclick="this.blur(); return f_sortTable('itemTblBdy1', 2, false);"
  title="-- Sort by UOM">UOM</a></th>
<th  class="tt">
<a ID=842 class="tt" href="#pgsort" onclick="this.blur(); return f_sortTable('itemTblBdy1', 3, false);"
  title="-- Sort by Pack">Pack</a></th>
<th  class="tt">
<a ID=843 class="tt" href="#pgsort" onclick="this.blur(); return f_sortTable('itemTblBdy1', 4, false);"
  title="-- Sort by Auto order">Auto Order</a></th>
</tr>
</thead>
<tbody id="itemTblBdy1">



  <logic:iterate id="currItem" name="STORE_ACCOUNT_DETAIL_FORM"
  property="accountData.inventoryItemsData"
    type="com.cleanwise.service.api.value.InventoryItemDataJoin">

  <tr>
  <td class="ttcell"><bean:write name="currItem" property="productData.skuNum"/></td>
  <td class="ttcell"><bean:write name="currItem" property="productData.shortDesc"/></td>
  <td class="ttcell"><bean:write name="currItem" property="productData.uom"/></td>
  <td class="ttcell"><bean:write name="currItem" property="productData.pack"/></td>
<logic:equal name="currItem" property="inventoryItemsData.enableAutoOrder" value="Y">
  <td class="ttcell_highlight">
<bean:write name="currItem" property="inventoryItemsData.enableAutoOrder"/></td>
</logic:equal>
<logic:notEqual name="currItem" property="inventoryItemsData.enableAutoOrder" value="Y">
  <td class="ttcell"><bean:write name="currItem" property="inventoryItemsData.enableAutoOrder"/></td>
</logic:notEqual>
  <td>
    <html:multibox name="STORE_ACCOUNT_DETAIL_FORM" property="selectedInventoryItems"
      value="<%=String.valueOf(currItem.getProductData().getProductId())%>" />
  </td>
  </tr>
</logic:iterate>

</logic:greaterThan>

</tbody>
</table>
<div style="{text-align:left; padding-left: 1cm;}">
<bean:define id="accountId" name="STORE_ACCOUNT_DETAIL_FORM" property="id"/>
<a ID=844 href="storeInventoryMgr.do?action=selectInventoryItems&accountId=<%=accountId%>">
[Add items.]</a>
&nbsp;&nbsp;  For the items selected:
<html:submit property="action">Enable auto order</html:submit>
<html:submit property="action">Disable auto order</html:submit>
<html:submit property="action">Remove from inventory</html:submit>
</div>

<%
} else {
/* select items from the list for inventory. */
%>

<logic:greaterThan name="numberOfItemsToAdd" value="0">
<p class="invhead">
Items available for inventory: <bean:write name="numberOfItemsToAdd"/>
</p>

<table ID=845><thead><tr>
<th  class="tt">
<a ID=846 class="tt" href="#pgsort" onclick="this.blur(); return f_sortTable('itemTblBdy', 0, false);"
  title="-- Sort by SKU">SKU</a></th>
<th  class="tt">
<a ID=847 class="tt" href="#pgsort" onclick="this.blur(); return f_sortTable('itemTblBdy', 1, false);"
  title="-- Sort by Description">Description</a></th>
<th  class="tt">
<a ID=848 class="tt" href="#pgsort" onclick="this.blur(); return f_sortTable('itemTblBdy', 2, false);"
  title="-- Sort by UOM">UOM</a></th>
<th  class="tt">
<a ID=849 class="tt" href="#pgsort" onclick="this.blur(); return f_sortTable('itemTblBdy', 3, false);"
  title="-- Sort by Pack">Pack</a></th>
</tr>
</thead>
<tbody id="itemTblBdy">
<logic:iterate id="itemToAdd" name="STORE_ACCOUNT_DETAIL_FORM"
  property="inventoryItemsAvailable"
  type="com.cleanwise.service.api.value.InventoryItemDataJoin">
  <tr>
  <td class="ttcell"><bean:write name="itemToAdd" property="productData.skuNum"/></td>
  <td class="ttcell"><bean:write name="itemToAdd" property="productData.shortDesc"/></td>
  <td class="ttcell"><bean:write name="itemToAdd" property="productData.uom"/></td>
  <td class="ttcell"><bean:write name="itemToAdd" property="productData.pack"/></td>
  <td>
  <logic:notEqual name="itemToAdd" property="inventoryItemsData.statusCd"
    value="<%=RefCodeNames.ITEM_STATUS_CD.ACTIVE%>" >
    <html:multibox name="STORE_ACCOUNT_DETAIL_FORM" property="itemsToAddToInventory"
      value="<%=String.valueOf(itemToAdd.getProductData().getProductId())%>" />
  </logic:notEqual>
  </td>
  </tr>
</logic:iterate>
</tbody>
</table>

<div style="{text-align:left; padding-left: 2cm;}">
<html:submit property="action">Add to inventory.</html:submit>
</div>

</logic:greaterThan>
<% } /* End of if on init check */ %>


</div>


</html:form>
</body>

</html:html>






