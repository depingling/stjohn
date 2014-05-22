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
<script language="JavaScript1.2">
<!--
function popManufLocate(name,name1) {
  var loc = "manuflocate.do?feedField=" + name+"&feedDesc="+name1;
  locatewin = window.open(loc,"tickersearch", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();

  return false;
}
//-->
</script>

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

<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>
<jsp:include flush='true' page="ui/admAggItemToolbar.jsp"/>

<div class="text">
<font color=red>
<html:errors/>
</font>

<table border="0" width="769" class="mainbody">
<html:form name="ITEM_AGGREGATE_MGR_FORM" action="/adminportal/aggregateItem" type="com.cleanwise.view.forms.AggregateItemMgrForm">
<html:hidden name="ITEM_AGGREGATE_MGR_FORM" property="wizardStep" value="itemselect"/>
  <tr><td class="largeheader">Search Item</td>
  <td colspan="3">
    &nbsp;
  </td>
  </tr>
  <td><b>Short Description:<b></td>
  <td><html:text name="ITEM_AGGREGATE_MGR_FORM" property="itemSearchShortDesc"/></td>
  <td>&nbsp;</td><td>&nbsp;</td>
  </tr>
  <tr><td><b>Item Property:</b></td>
  <td colspan="3">
   <html:select name="ITEM_AGGREGATE_MGR_FORM" property="itemSearchItemPropertyName" >
           <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
           <html:option value="SIZE">Size</html:option>
           <html:option value="COLOR">Color</html:option>
           <html:option value="UOM">Uom</html:option>
           <html:option value="HAZMAT">Hazmat (true)</html:option>
           <html:option value="OTHER_DESC">Other Desc</html:option>
           <html:option value="PACK">Pack</html:option>
           <html:option value="PKG_UPC_NUM">Pkg UPC Number</html:option>
           <html:option value="PSN">PSN</html:option>
           <html:option value="SCENT">Scent</html:option>
           <html:option value="SHIP_WEIGHT">Ship Weight</html:option>
           <html:option value="UNSPSC_CD">UNSPSC</html:option>
           <html:option value="UPC_NUM">UPC Number</html:option>
           <html:option value="PACK_PROBLEM_SKU">Pack Problem (true)</html:option>
   </html:select>
   <html:text name="ITEM_AGGREGATE_MGR_FORM" property="itemSearchItemProperty"/>
  </td>
  </tr>
  <tr><td><b>Long Description:</b></td>
  <td colspan="3"><html:text name="ITEM_AGGREGATE_MGR_FORM" property="itemSearchLongDesc" size="80"/>
  </td>
  </tr>
  <tr><td><b>Manufacturer Id:</b></td>
      <td><html:text name="ITEM_AGGREGATE_MGR_FORM" property="itemSearchManuId" size="10"/>
      <html:button onclick="return popManufLocate('itemSearchManuId','');" value="Locate Manufacturer" property="action"/>
      </td>
  <tr><td><b>Sku:</b></td>
       <td colspan="3">
       <html:text name="ITEM_AGGREGATE_MGR_FORM" property="itemSearchSku"/>
       <html:radio name="ITEM_AGGREGATE_MGR_FORM" property="itemSearchSkuType" value="SystemCustomer"/>
       System + Customer
       <html:radio name="ITEM_AGGREGATE_MGR_FORM" property="itemSearchSkuType" value="Manufacturer"/>
       Manufacturer
       <html:radio name="ITEM_AGGREGATE_MGR_FORM" property="itemSearchSkuType" value="Distributor"/>
       Distributor
       <html:radio name="ITEM_AGGREGATE_MGR_FORM" property="itemSearchSkuType" value="Id"/>
       Item Id
       </td>
  </tr>
  <tr> <td></td>
       <td colspan="3">
          <html:submit property="action">
                <app:storeMessage  key="global.action.label.search"/>
          </html:submit>

          <html:submit property="action">
                <app:storeMessage  key="admin.button.back"/>
          </html:submit>
    </td>
  </tr>
</table>



<logic:present name="ITEM_AGGREGATE_MGR_FORM" property="items">
<bean:size id="listSize" name="ITEM_AGGREGATE_MGR_FORM"  property="items"/>
Search result count: <bean:write name="listSize"/>
<logic:greaterThan name="listSize" value="0">


<table width="100%" class="results">
<tr align=center>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 0, false);">Id</a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 1, false);">Sku</a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 2, false);">Name</a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 3, false);">Size</a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 4, false);">Pack</a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 5, false);">UOM</a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 6, false);">Color</a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 7, false);">Mfg.</a></th>
<th><a href="#pgsort" class="tableheader" onclick="this.blur(); return f_sortTable('resTblBdy', 8, false);">Mfg.&nbsp;Sku</a></th>
</tr>
<tbody id="resTblBdy">
   <logic:iterate id="product" name="ITEM_AGGREGATE_MGR_FORM" property="items" indexId="prodIdx">
    <bean:define id="key"  name="product" property="productId"/>
    <bean:define id="sku" name="product" property="skuNum"/>
    <bean:define id="name" name="product" property="shortDesc"/>
    <bean:define id="size" name="product" property="size"/>
    <bean:define id="pack" name="product" property="pack"/>
    <bean:define id="uom" name="product" property="uom"/>
    <bean:define id="color" name="product" property="color"/>
    <bean:define id="manuName" name="product" property="manufacturerName"/>
    <bean:define id="manuSku" name="product" property="manufacturerSku"/>

    <% String linkHref = "aggregateItemMgr.do?action=fetch&itemId=" + key;%>

<%--
<% if (( prodIdx.intValue() % 2 ) == 0 ) { %>
<tr class="rowa">
<% } else { %>
<tr class="rowb">
<% } %>
--%>
<tr>

    <td><bean:write name="key"/></td>
    <td><bean:write name="sku"/></td>
    <td><html:link href="<%=linkHref%>"><bean:write name="name"/></html:link></td>
    <td><bean:write name="size"/></td>
    <td><bean:write name="pack"/></td>
    <td><bean:write name="uom"/></td>
    <td><bean:write name="color"/></td>
    <td><bean:write name="manuName"/></td>
    <td><bean:write name="manuSku"/></td>
 </tr>
 </logic:iterate>
</tbody>


</logic:greaterThan>
</logic:present>
</div>


</html:form>
<script language="JavaScript" type="text/javascript">
  <!--
    document.forms["ITEM_AGGREGATE_MGR_FORM"].elements["itemSearchSku"].focus();
   -->
</script>

<jsp:include flush='true' page="ui/admFooter.jsp"/>

</body>

</html:html>
