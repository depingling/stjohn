<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.view.forms.StoreAccountMgrDetailForm" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="java.util.Locale" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.SearchCriteria" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<app:checkLogon/>

<%@page import="com.cleanwise.service.api.util.Utility"%><html:html>
<%@include file="storeAccountShopRestrictionsAjax.jsp"%>
<!-- required: the default dijit theme: -->
<link id="themeStyles" rel="stylesheet" href="<%=request.getContextPath()%>/externals/dojo_1.1.0/dijit/themes/tundra/tundra.css">
<link rel="stylesheet" href="../externals/styles.css">

<script type="text/javascript" src="<%=request.getContextPath()%>/externals/dojo_1.1.0/dojo/dojo.js"
    djConfig="parseOnLoad: true"></script>
<script src="../externals/lib.js" language="javascript"></script>
    
<script language="javascript" type="text/javascript">

dojo.require("dijit.form.Button");
function getSiteControls(id) {
    storeAccountShopRestrictionsAjax.initialize(false, id);	 
	if( dojo.isIE ){
		eval("document.getElementById('panel_'+id)").style.display = 'block';
	}else{
		eval("document.getElementById('panel_'+id)").style.display = 'table-row';
	}	 
	return false;
}       

</script>
<div class="text">
<table ID=489 class="stpTable">
<tr>
<td class=stpLabel>Account&nbsp;Id</td>
<td><bean:write name="STORE_ACCOUNT_DETAIL_FORM"
   property="accountData.busEntity.busEntityId"
   scope="session"/>
</td>
<td class=stpLabel>Account&nbsp;Name</td>
<td><bean:write name="STORE_ACCOUNT_DETAIL_FORM"
   property="accountData.busEntity.shortDesc"
   scope="session"/>
</td>
</tr>
</table>

<bean:define id="thisAccountId" name="STORE_ACCOUNT_DETAIL_FORM"
   property="accountData.accountId"
   type="java.lang.Integer" />
<html:form
 styleId="490"  name="STORE_ACCOUNT_SHOPPING_CONTROL_FORM"
  action="/storeportal/accountShoppingRestrictions?action=search_shopping_controls"
  type="com.cleanwise.view.forms.StoreAccountShoppingControlForm">
<html:hidden name="STORE_ACCOUNT_SHOPPING_CONTROL_FORM" property="accountId"
  value="<%=thisAccountId.toString()%>"/>

<!-- ---------------------------------XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX -->
<div class="rptmid">
Find Items
<table ID=365 width="767" border="0"  class="mainbody">
  <tr>
    <td><b><app:storeMessage key="locateItem.label.category"/>:</b></td>
    <td><html:text property="categoryTempl"/> </td>
    <td>
      &nbsp;
    </td>
  </tr>
  <tr>
    <td><b><app:storeMessage key="locateItem.label.shortDesc"/>:<b></td>
    <td><html:text property="shortDescTempl"/></td>    
    <td><b><app:storeMessage key="locateItem.label.itemProp"/>:</b>
      <html:select property="itemPropertyName" >
        <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
        <html:option value="SIZE"><app:storeMessage key="locateItem.itemProp.text.size"/></html:option>
        <html:option value="COLOR"><app:storeMessage key="locateItem.itemProp.text.color"/></html:option>
        <html:option value="UOM"><app:storeMessage key="locateItem.itemProp.text.uom"/></html:option>
        <html:option value="HAZMAT"><app:storeMessage key="locateItem.itemProp.text.hazmat"/></html:option>
        <html:option value="OTHER_DESC"><app:storeMessage key="locateItem.itemProp.text.otherDesc"/></html:option>
        <html:option value="PACK"><app:storeMessage key="locateItem.itemProp.text.pack"/></html:option>
        <html:option value="PKG_UPC_NUM"><app:storeMessage key="locateItem.itemProp.text.pkgUpcNumber"/></html:option>
        <html:option value="PSN"><app:storeMessage key="locateItem.itemProp.text.psn"/></html:option>
        <html:option value="SCENT"><app:storeMessage key="locateItem.itemProp.text.scent"/></html:option>
        <html:option value="SHIP_WEIGHT"><app:storeMessage key="locateItem.itemProp.text.shipWeight"/></html:option>
        <html:option value="UNSPSC_CD"><app:storeMessage key="locateItem.itemProp.text.unspsc"/></html:option>
        <html:option value="UPC_NUM"><app:storeMessage key="locateItem.itemProp.text.upcNumber"/></html:option>
        <html:option value="PACK_PROBLEM_SKU"><app:storeMessage key="locateItem.itemProp.text.packProblem"/></html:option>
      </html:select>
      <html:text  property="itemPropertyTempl"/>
    </td>
  </tr>
  <tr>
    <td><b><app:storeMessage key="locateItem.label.longDesc"/>:</b></td>
    <td colspan="2"><html:text  property="longDescTempl" size="80"/></td>
  </tr>
  <tr><td><b><app:storeMessage key="locateItem.label.manuf"/>:</b></td>
      <td colspan='2'><html:text  property="manuNameTempl" size="20"/></td>
  </tr>
	<tr>
		<td><b><app:storeMessage key="locateItem.label.sku"/>:</b></td>
		<td><html:text  property="skuTempl" size="20"/></td>
		<td>
		<table>
	   	<tr>
	   	<td>
			<html:radio property="skuType" value="<%=SearchCriteria.STORE_SKU_NUMBER%>"
			onclick="return showSearchNumType('hidden');"/>
			<app:storeMessage key="locateItem.radio.text.store"/>
			<html:radio property="skuType" value="<%=SearchCriteria.MANUFACTURER_SKU_NUMBER%>"
			onclick="return showSearchNumType('visible');"/>
			<app:storeMessage key="locateItem.radio.text.manuf"/>
			
			<html:radio property="skuType" value="<%=SearchCriteria.DISTRIBUTOR_SKU_NUMBER%>"
			onclick="return showSearchNumType('visible');"/>
			<app:storeMessage key="locateItem.radio.text.distr"/>	
			<html:radio property="skuType" value="<%=SearchCriteria.CUST_SKU_NUMBER%>"
			onclick="return showSearchNumType('visible');"/>
			<app:storeMessage key="locateItem.radio.text.storeCust"/>
		</td>
		</tr>		
	   </table>
	 </tr>
  <tr class="results">
    <td colspan="3"></td>
  </tr>
  <tr>
    <td colspan="3"></td>
  </tr>
  <tr>
    <td colspan="3">
     <html:submit><app:storeMessage  key="global.action.label.search"/>
     </html:submit>&nbsp;&nbsp;
      </td>
    </td>
  </tr>
</table>
<bean:size id="acctCtrlSize" name="STORE_ACCOUNT_SHOPPING_CONTROL_FORM" property="shoppingControlItemViewList"/>
Search Result Count: <%=acctCtrlSize%>
</html:form>
<logic:greaterThan name="acctCtrlSize" value="0">
<html:form
 styleId="490"  name="STORE_ACCOUNT_SHOPPING_CONTROL_FORM"
  action="/storeportal/accountShoppingRestrictions?action=update_shopping_controls"
  type="com.cleanwise.view.forms.StoreAccountShoppingControlForm">
<html:hidden name="STORE_ACCOUNT_SHOPPING_CONTROL_FORM" property="accountId"
  value="<%=thisAccountId.toString()%>"/>
<table class="stpTable_sortable" id="ts1">
<thead>
<tr>
<th class="stpTH">Item Is Controlled</th>
<th class="stpTH">Show Site Controls</th>
<th class="stpTH">Sku</th>
<th class="stpTH">Description</th>
<th class="stpTH">Size</th>
<th class="stpTH">UOM</th>
<th class="stpTH">Pack</th>
<th class="stpTH">Max Order Qty</th>
<th class="stpTH">Restriction Days</th>
</tr>
</thead>
<tbody>
<bean:define id="siteScDV" name="STORE_ACCOUNT_SHOPPING_CONTROL_FORM"
   property="siteShoppingControlItemViewList"
   type="ArrayList" />
<%
List siteItemIdList = new ArrayList();
for (int jj = 0; jj < siteScDV.size(); jj++) {
	ShoppingControlData scD = (ShoppingControlData)siteScDV.get(jj);
	if (!siteItemIdList.contains(scD.getItemId()))
		siteItemIdList.add(scD.getItemId());
}
%>

<logic:iterate id="itemControl" name="STORE_ACCOUNT_SHOPPING_CONTROL_FORM" indexId="i" property="shoppingControlItemViewList" type="ShoppingControlItemView">
<% /* Product Info */
int itemSeq = itemControl.getItemId();
String visibility ="hidden";
if ( itemControl.getSkuNum() != null && itemControl.getSkuNum().length() > 0 ) {
	boolean isItemInControlCfg = false;
%>
<tr class=stpTD>
<td class=stpTD>
<%if (itemControl.getShoppingControlData().getControlStatusCd() != null &&
       itemControl.getShoppingControlData().getControlStatusCd().equals(RefCodeNames.SIMPLE_STATUS_CD.ACTIVE))
{%>
Yes
<%  if (siteItemIdList.contains(itemSeq)) {
        isItemInControlCfg = true;
    }
} else { %>
No
<% } %>
</td>
<td class=stpTD align="center">
<%if(isItemInControlCfg){%>
<button dojoType="dijit.form.Button">Yes<script type="dojo/method" event="onClick">getSiteControls(<%=itemSeq%>);</script></button>
<%}else{%>
&nbsp;
<%}%>
</td>
<td class=stpTD><%=itemControl.getSkuNum()%></td>
<td class=stpTD><%=itemControl.getShortDesc()%></td>
<td class=stpTD><%=itemControl.getSize()%>&nbsp;</td>
<td class=stpTD><%=itemControl.getUOM()%></td>
<td class=stpTD><%=itemControl.getPack()%></td>
<%int maxqty = itemControl.getShoppingControlData().getMaxOrderQty();
int restDays = itemControl.getShoppingControlData().getRestrictionDays();
String maxqtyS = null;
String restDaysS = null;
if(maxqty < 0){
	maxqtyS="*";
}else{
	maxqtyS = itemControl.getMaxOrderQty();
}
if(restDays < 0){
	restDaysS="*";
}else{
	restDaysS = itemControl.getRestrictionDays();
}
//If the item is 0 in the db and the shopping control is not set and the shopping control data object has an id
//(indicating it is in the database) then set it to 0
if(maxqty == 0 && itemControl.getShoppingControlData().getShoppingControlId() > 0 && RefCodeNames.SIMPLE_STATUS_CD.ACTIVE.equals(itemControl.getShoppingControlData().getControlStatusCd())){
	if(!Utility.isSet(maxqtyS)){
		maxqtyS="0";
	}
	if(!Utility.isSet(restDaysS)){
		restDaysS="0";
	}
}%>
<td class=stpTD align="right"><html:text property="<%= \"shoppingControlItemViewList[\" + i + \"].maxOrderQty\" %>" value='<%=maxqtyS%>' size='3'/></td>
<td class=stpTD align="right"><html:text property="<%= \"shoppingControlItemViewList[\" + i + \"].restrictionDays\" %>" value='<%=restDaysS%>' size='3'/>
</td>
</tr>
<%}//end not product info%>
<tr id="panel_<%=itemSeq%>" style="display:none">
<td valign="top"><a href="#" onclick="storeAccountShopRestrictionsAjax.closeit(<%=itemSeq%>);return false;">CLOSE</a></td>
<td id="panel_td_<%=itemSeq%>" colspan="8"></td>
</tr>
</logic:iterate>
</tbody>
</table>

<table ID=491>
<tr><td width=600 align=right><html:submit value="Submit"/>
</td></tr>
</table>
</html:form>
</logic:greaterThan>
</div>
<!-- -----------------------------XXXXXXXXXXXXXXXXXXXX -->


</html:html>

