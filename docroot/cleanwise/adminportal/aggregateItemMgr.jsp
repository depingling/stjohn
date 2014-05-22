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
<html:form name="ITEM_AGGREGATE_MGR_FORM" action="/adminportal/aggregateItemMgr" type="com.cleanwise.view.forms.AggregateItemMgrForm">
<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>
<jsp:include flush='true' page="ui/admAggItemToolbar.jsp"/>

<div class="text">
<font color=red>
<html:errors/>
</font>

<table border="0" width="769" class="mainbody">
  <tr>
        <td>&nbsp;</td><td colspan="4"><b>Stores:</b></td>
  </tr>
  <tr>
        <td>&nbsp;</td>
        <td colspan="4">
        <logic:present name="ITEM_AGGREGATE_MGR_FORM" property="storesNewlySelected">
                <logic:iterate id="elle" name="ITEM_AGGREGATE_MGR_FORM" property="storesNewlySelected">
                        <bean:write name="elle" property="busEntity.shortDesc"/>,
                </logic:iterate>
        </logic:present>
        </td>
  </tr>
  <tr>
        <td>&nbsp;</td><td colspan="4"><b>Distributors:</b></td>
  </tr>
  <tr>
        <td>&nbsp;</td>
        <td colspan="4">
        <logic:present name="ITEM_AGGREGATE_MGR_FORM" property="distributorsNewlySelected">
                <logic:iterate id="elle" name="ITEM_AGGREGATE_MGR_FORM" property="distributorsNewlySelected">
                        <bean:write name="elle" property="busEntity.shortDesc"/>,
                </logic:iterate>
        </logic:present>
        </td>
  </tr>
  <tr>
        <td>&nbsp;</td><td colspan="4"><b>Accounts:</b></td>
  </tr>
  <tr>
        <td>&nbsp;</td>
        <td colspan="4">
        <logic:present name="ITEM_AGGREGATE_MGR_FORM" property="accountsNewlySelected">
                <logic:iterate id="elle" name="ITEM_AGGREGATE_MGR_FORM" property="accountsNewlySelected">
                        <bean:write name="elle" property="busEntity.shortDesc"/>,
                </logic:iterate>
        </logic:present>
        </td>
  </tr>
  <tr>
        <td>&nbsp;</td><td colspan="4"><b>Item:</b></td>
  </tr>
  <tr>
        <td>&nbsp;</td>
        <td><b>Sku:</b></td><td><bean:write name="ITEM_AGGREGATE_MGR_FORM" property="currManagingItem.skuNum"/></td>
        <td><b>Item Id:</b></td><td><bean:write name="ITEM_AGGREGATE_MGR_FORM" property="currManagingItem.productId"/></td>
  </tr>
  <tr>
        <td>&nbsp;</td>
        <td><b>Item Name:</b></td><td><bean:write name="ITEM_AGGREGATE_MGR_FORM" property="currManagingItem.shortDesc"/></td>
        <td>&nbsp;</td><td>&nbsp;</td>
  </tr>
  <tr>
        <td>&nbsp;</td>
        <td><b>Manufacturer:</b></td><td><bean:write name="ITEM_AGGREGATE_MGR_FORM" property="currManagingItem.manufacturerName"/></td>
        <td><b>Manufacturer Sku:</b></td><td><bean:write name="ITEM_AGGREGATE_MGR_FORM" property="currManagingItem.manufacturerSku"/></td>
  </tr>
  <tr>
        <td colspan="5">&nbsp;</td>
  </tr>
  <tr>
        <td>&nbsp;</td>
        <td>
                <b>Category Action:</b>
        </td>
        <td colspan="3">
                <html:select name="ITEM_AGGREGATE_MGR_FORM" property="categoryAction">
                        <html:option value="admin.aggregate.items.addItemsToCat"><app:storeMessage  key="admin.aggregate.items.addItemsToCat"/></html:option>
                        <html:option value="admin.aggregate.items.addItemsAndCatToCat"><app:storeMessage  key="admin.aggregate.items.addItemsAndCatToCat"/></html:option>
                        <html:option value="admin.aggregate.items.remItemsFromCat"><app:storeMessage  key="admin.aggregate.items.remItemsFromCat"/></html:option>
                        <html:option value="admin.aggregate.items.remItemsFromAllCats"><app:storeMessage  key="admin.aggregate.items.remItemsFromAllCats"/></html:option>
                </html:select>
        </td>
   </tr>
   <tr>
        <td>&nbsp;</td>
        <td>
                <b>Category:</b>
        </td>
        <td colspan="3">
                <html:select name="ITEM_AGGREGATE_MGR_FORM" property="category">
                        <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
                        <html:options collection="aggregate.item.available.categories" property="value"/>
                </html:select>
        </td>
  </tr>
  <tr>
        <td>&nbsp;</td>
        <td>
                <b>Categroy Cost Center</b>
        </td>
        <td>
                <html:text name="ITEM_AGGREGATE_MGR_FORM" property="categoryCostCenter" size="6"/>
        </td>
        <td colspan="3">
                <html:submit property="action">
                        <app:storeMessage  key="admin.button.preformCategoryMod"/>
                </html:submit>
        </td>
  </tr>
  <tr>
        <td>&nbsp;</td>
        <td><b>Dist Id</b></td>
        <td><b>Cost</b></td>
        <td><b>Price</b></td>
        <td><b>Dist Base Cost</b></td>
        <td><b>Cust Sku</b></td>
  </tr>
  <tr valign="top">
        <td>&nbsp;</td>
        <td>
                <input type="text" name="distributorIdDummy" size="5"/>
                <html:button styleClass="smallbutton" property="action" onclick="SetValuesGlobal(document.forms[0].distributorIdDummy.value,'distId')" value="Set All"/>
                <br>
                <html:button styleClass="smallbutton" property="action" onclick="popLocateFeedGlobal('../adminportal/distlocate', 'distributorIdDummy','');" value="Locate Distributor"/>
        </td>
        <td>
                <input type="text" name="costDummy" size="5"/>
                <html:button styleClass="smallbutton" property="action" onclick="SetValuesGlobal(document.forms[0].costDummy.value,'itemCost')" value="Set All"/>
        </td>
        <td>
                <input type="text" name="priceDummy" size="5"/>
                <html:button styleClass="smallbutton" property="action" onclick="SetValuesGlobal(document.forms[0].priceDummy.value,'itemPrice')" value="Set All"/>
        </td>
        <td>
                <input type="text" name="distBaseCostDummy" size="5"/>
                <html:button styleClass="smallbutton" property="action" onclick="SetValuesGlobal(document.forms[0].distBaseCostDummy.value,'distBaseCost')" value="Set All"/>
        </td>
        <td>
                <input type="text" name="custSkuDummy" size="5"/>
                <html:button styleClass="smallbutton" property="action" onclick="SetValuesGlobal(document.forms[0].custSkuDummy.value,'custSku')" value="Set All"/>
        </td>
  </tr>
  <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td colspan="4"><b>Action:</b></td>
  </tr>
  <tr>
        <td>&nbsp;</td>
        <td>&nbsp;</td>
        <td colspan="4">
                <b>Items To Catalog:</b>
                <html:multibox name="ITEM_AGGREGATE_MGR_FORM" property="tickItemsToCatalog" value="true"/>

                <b>Items To Contract:</b>
                <html:multibox name="ITEM_AGGREGATE_MGR_FORM" property="tickItemsToContract" value="true"/>

                <b>Items To Order Guide:</b>
                <html:multibox name="ITEM_AGGREGATE_MGR_FORM" property="tickItemsToOrderGuide" value="true"/>
        </td>
  </tr>
  <tr>
        <td colspan="5" align="center">
                <html:submit property="action">
                        <app:storeMessage  key="admin.button.addItems"/>
                </html:submit>
                <html:submit property="action">
                        <app:storeMessage  key="admin.button.removeSelected"/>
                </html:submit>
                <html:submit property="action">
                        <app:storeMessage  key="admin.button.back"/>
                </html:submit>
        </td>
  </tr>
</table>

<logic:present name="ITEM_AGGREGATE_MGR_FORM" property="aggregateItems">
<bean:size id="listSize" name="ITEM_AGGREGATE_MGR_FORM"  property="aggregateItems.values"/>
count: <bean:write name="listSize"/>
<logic:greaterThan name="listSize" value="0">


<table width="100%" class="results">
<tr align=center>
<th class="tableheader">Select<br>
<a class="tableheader" href="javascript:SetCheckedGlobal(1,'aggregateItems.selected')">[Check&nbsp;All]</a>
<br>
<a class="tableheader" href="javascript:SetCheckedGlobal(0,'aggregateItems.selected')">[&nbsp;Clear]</a>
</th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 1, false);">Catalog Id</a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 2, false);">Catalog</a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 3, false);">Dist</a></th>
<th><a class="tableheader">Dist Id</a></th>
<th><a class="tableheader">Cost</a></th>
<th><a class="tableheader">Price</a></th>
<th><a class="tableheader">Dist Base Cost</a></th>
<th><a class="tableheader">Cust Sku</a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 9, false);">Catalog Type</a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 10, false);">Catalog Status</a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 11, false);">Contract</a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 12, false);">Contract Status</a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 13, false);">Order Guide</a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 14, false);">In Catalog</a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 15, false);">In Contract</a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 16, false);">In Order Guide</a></th>
<th class="tableheader">Categories</th>
</tr>
<tbody id="resTblBdy">
   <logic:iterate id="elle" name="ITEM_AGGREGATE_MGR_FORM" property="aggregateItems.values" indexId="i">
   <tr>
   <%String prop = "aggregateItems.selected["+i+"]";%>
   <td align="center"><html:multibox name="ITEM_AGGREGATE_MGR_FORM" property="<%=prop%>" value="true"/></td>
   <td><bean:write name="elle" property="catalogId"/></td>
   <td><bean:write name="elle" property="catalogDesc"/></td>
   <td><bean:write name="elle" property="distDesc"/></td>
   <%prop = "aggregateItems.value["+i+"].distIdStr";%>
   <td><html:text name="ITEM_AGGREGATE_MGR_FORM" property="<%=prop%>" size="6"/></td>
   <%prop = "aggregateItems.value["+i+"].itemCostStr";%>
   <td><html:text name="ITEM_AGGREGATE_MGR_FORM" property="<%=prop%>" size="6"/></td>
   <%prop = "aggregateItems.value["+i+"].itemPriceStr";%>
   <td><html:text name="ITEM_AGGREGATE_MGR_FORM" property="<%=prop%>" size="6"/></td>
   <%prop = "aggregateItems.value["+i+"].distBaseCostStr";%>
   <td><html:text name="ITEM_AGGREGATE_MGR_FORM" property="<%=prop%>" size="6"/></td>
   <%prop = "aggregateItems.value["+i+"].custSku";%>
   <td><html:text name="ITEM_AGGREGATE_MGR_FORM" property="<%=prop%>" size="6"/></td>
   <td><bean:write name="elle" property="catalogType"/></td>
   <td><bean:write name="elle" property="catalogStatus"/></td>
   <td><bean:write name="elle" property="contractDesc"/></td>
   <td><bean:write name="elle" property="contractStatus"/></td>
   <td><bean:write name="elle" property="orderGuideDesc"/></td>
   <td align="center">
        <logic:equal name="elle" property="catalogStructureId" value="0">N</logic:equal>
        <logic:notEqual name="elle" property="catalogStructureId" value="0">Y</logic:notEqual>
   </td>
   <td align="center">
        <logic:equal name="elle" property="contractItemId" value="0">N</logic:equal>
        <logic:notEqual name="elle" property="contractItemId" value="0">Y</logic:notEqual>
   </td>
   <td align="center">
        <logic:equal name="elle" property="orderGuideStructureId" value="0">N</logic:equal>
        <logic:notEqual name="elle" property="orderGuideStructureId" value="0">Y</logic:notEqual>
   </td>
   <td align="center">
           <logic:iterate id="cat" name="elle" property="categories">
                <bean:write name="cat"/><br>
           </logic:iterate>
   </td>
   </tr>
   </logic:iterate>
</tbody>
</table>
</logic:greaterThan>
</logic:present>

</div>

</html:form>
<jsp:include flush='true' page="ui/admFooter.jsp"/>

</body>

</html:html>
