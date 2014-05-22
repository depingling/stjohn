<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>
<% String storeDir=ClwCustomizer.getStoreDir(); %>

<script language="JavaScript1.2">
<!--
function SetChecked(val) {
 dml=document.ITEM_SEARCH_FORM;
 len = dml.elements.length;
 var i=0;
 for( i=0 ; i<len ; i++) {
   if (dml.elements[i].name=='selectorBox') {
     dml.elements[i].checked=val;
   }
 }
}

function popLocate(name) {
  var loc = "catalogitemadd.do?feedField=" + name;
  locatewin = window.open(loc,"tickersearch", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();

  return false;
}

function popManufLocate(name,name1) {
  var loc = "manuflocate.do?feedField=" + name+"&feedDesc="+name1;
  locatewin = window.open(loc,"tickersearch", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();

  return false;
}

function popDistLocate(name,name1) {
  var loc = "distlocate.do?feedField=" + name+"&feedDesc="+name1;
  locatewin = window.open(loc,"tickersearch", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();

  return false;
}

//-->
</script>

<html:html>
<head>
<title>Search Item</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../externals//styles.css">
</head>

<body bgcolor="#cccccc">
<div class = "text">
<html:form action="/adminportal/itemsearch.do" >
<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/catalogToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>
<jsp:include flush='true' page="ui/catalogInfo.jsp"/>
<font color=red>
<html:errors/>
</font>

<%
com.cleanwise.view.forms.CatalogMgrDetailForm catForm =
  (com.cleanwise.view.forms.CatalogMgrDetailForm)
    session.getAttribute("CATALOG_DETAIL_FORM");
int thisCatalogId = catForm.getDetail().getCatalogId();
%>

<html:hidden name="ITEM_SEARCH_FORM" property="catalogId"
  value="<%= String.valueOf(thisCatalogId) %>" />


Master Catalog Id = 
<bean:write name="CATALOG_DETAIL_FORM" property="masterCatalogId"/>

  <table border="0" width="769" class="results">
  <tr><td class="largeheader">Search Item</td>
  <td colspan="3">
    &nbsp; <html:hidden name="ITEM_SEARCH_FORM" property="whereToSearch" value="this"/>
  </td>
  </tr>
  <tr><td><b>Category:</b></td>
  <td><html:text name="ITEM_SEARCH_FORM" property="categoryTempl"/> </td>
  </tr>
  <tr>
  <td><b>Short Description:<b></td>
  <td><html:text name="ITEM_SEARCH_FORM" property="shortDescTempl"/></td>
   <td colspan=4><b>Item Property:</b>
   <html:select name="ITEM_SEARCH_FORM" property="itemPropertyName" >
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
   <html:text name="ITEM_SEARCH_FORM" property="itemPropertyTempl"/>
  </td>
  </tr>
  <tr><td><b>Long Description:</b></td>
  <td colspan="3"><html:text name="ITEM_SEARCH_FORM" property="longDescTempl" size="80"/>
  </td>
  </tr>
  <tr><td><b>Manufacturer Id:</b></td>
      <td><html:text name="ITEM_SEARCH_FORM" property="manuId" size="10"/>
      <html:button onclick="return popManufLocate('manuId','manuName');" value="Locate Manufacturer" property="action"/>
      </td>
     <td><b>Manufacturer Name:</b></td>
     <td>
        <html:text name="ITEM_SEARCH_FORM" property="manuName" size="30" readonly="true" styleClass="resultslocatename"/>
     </td>
   </tr>
      <tr>
      <td><b>Distributor Id:</b><br>(0 for none)</td>
      <td><html:text name="ITEM_SEARCH_FORM" property="distributorId" size="10"/>
          <html:button onclick="return popDistLocate('distributorId','distributorName');" value="Locate Distributor" property="action" />
      </td>
      <td><b>Distributor Name:<b></td>
      <td>
          <html:text name="ITEM_SEARCH_FORM" property="distributorName" size="30" readonly="true" styleClass="resultslocatename"/>
      </td>
  </tr>
  <tr><td><b>Sku:</b></td>
       <td colspan="3">
       <html:text name="ITEM_SEARCH_FORM" property="skuTempl"/>
       <html:radio name="ITEM_SEARCH_FORM" property="skuType" value="SystemCustomer"/>
       System + Customer
       <html:radio name="ITEM_SEARCH_FORM" property="skuType" value="Manufacturer"/>
       Manufacturer
       <html:radio name="ITEM_SEARCH_FORM" property="skuType" value="Distributor"/>
       Distributor
       <html:radio name="ITEM_SEARCH_FORM" property="skuType" value="Id"/>
       Item Id
       </td>
  </tr>
  <tr class="results"> <td></td>
       <td colspan="3">
         </td>
  </tr>
  <tr> <td></td>
       <td colspan="3">
       <html:submit property="action" value="Search"/>
       <logic:equal name="CATALOG_DETAIL_FORM" property="detail.catalogTypeCd" value="SYSTEM">
         <html:submit property="action" value="Create New"/>
       </logic:equal>
       <logic:greaterThan name="ITEM_SEARCH_FORM" property="listSize" value="0">
       <html:submit property="action" value="Find In Catalog"/>
       <logic:notEqual name="CATALOG_DETAIL_FORM" property="detail.catalogTypeCd" value="SYSTEM">
         <html:submit property="action" value="Remove From Catalog" 
		 onclick="return confirm('You are trying to remove these selected items from catalog,\\nthose items will be removed from related contracts and order guides as well.\\nDo you wish to remove them?');"/>
       </logic:notEqual>
       </logic:greaterThan>

       <logic:notEqual name="CATALOG_DETAIL_FORM" property="masterCatalogId" value="0">
       <html:hidden name="ITEM_SEARCH_FORM" property="outServiceName"/>
       <html:button onclick="return popLocate('outServiceName');" property="action">
		<app:storeMessage  key="admin.button.addItems"/>
	</html:button>

       </logic:notEqual>
    </td>
  </tr>
</table>


</div>

<div>
Search result count: <bean:write name="ITEM_SEARCH_FORM" property="listSize"/>
<logic:greaterThan name="ITEM_SEARCH_FORM" property="listSize" value="0">
</div>


<div>

<table width="100%" class="results">
<tr align=center>
<td width="5%"><a class="tableheader" href="itemsearch.do?action=sort&sortField=id"><b>Id</b></a> </td>
<td width="8%"><a class="tableheader" href="itemsearch.do?action=sort&sortField=sku"><b>Sku</b></a> </td>
<td width="30%"><a class="tableheader" href="itemsearch.do?action=sort&sortField=name"><b>Name</b></a> </td>
<td width="8%"><a class="tableheader" href="itemsearch.do?action=sort&sortField=size"><b>Size</b></a> </td>
<td width="5%"><a class="tableheader" href="itemsearch.do?action=sort&sortField=pack"><b>Pack</b></a> </td>
<td width="5%"><a class="tableheader" href="itemsearch.do?action=sort&sortField=uom"><b>UOM</b> </td>
<td width="5%"><a class="tableheader" href="itemsearch.do?action=sort&sortField=color"><b>Color</b> </td>
<td width="18%"><a class="tableheader" href="itemsearch.do?action=sort&sortField=manufacturer"><b>Mfg.</b></a> </td>
<td width="8%"><a class="tableheader" href="itemsearch.do?action=sort&sortField=msku"><b>Mfg.&nbsp;Sku</b></a> </td>
<td bgcolor="#cccccc"><b>Category</b> </td>
<logic:notEqual name="CATALOG_DETAIL_FORM" property="detail.catalogTypeCd" value="SYSTEM">
<td width="4%"><a class="tableheader" href="itemsearch.do?action=sort&sortField=distid"><b>Dist.Id</b></a> </td>
</logic:notEqual>
<td width="4%" class="tableheader">Select</td>
</tr>
   <logic:iterate id="product" name="ITEM_SEARCH_FORM" property="products" 
    indexId="prodIdx">
    

    <bean:define id="key"  name="product" property="productId"/>
    <bean:define id="sku" name="product" property="skuNum"/>
    <bean:define id="name" name="product" property="shortDesc"/>
    <bean:define id="size" name="product" property="size"/>
    <bean:define id="pack" name="product" property="pack"/>
    <bean:define id="uom" name="product" property="uom"/>
    <bean:define id="color" name="product" property="color"/>
    <bean:define id="manuName" name="product" property="manufacturerName"/>
    <bean:define id="manuSku" name="product" property="manufacturerSku"/>

    <% String linkHref = new String("itemmaster.do?action=edit&retaction=finditem&itemId=" + key);%>
    <logic:notEqual name="CATALOG_DETAIL_FORM" property="detail.catalogTypeCd" value="SYSTEM">
      <% linkHref = new String("itemcatalog.do?action=edit&retaction=finditem&itemId=" + key);%>
    </logic:notEqual>

<% if (( prodIdx.intValue() % 2 ) == 0 ) { %> <tr class="rowa">
<% } else { %> <tr class="rowb"> <% } %>

    <td><bean:write name="key"/></td>
    <td><bean:write name="sku"/></td>
    <td><html:link href="<%=linkHref%>"><bean:write name="name"/></html:link></td>
    <td><bean:write name="size"/></td>
    <td><bean:write name="pack"/></td>
    <td><bean:write name="uom"/></td>
    <td><bean:write name="color"/></td>
    <td><bean:write name="manuName"/></td>
    <td><bean:write name="manuSku"/></td>
    <td>

<% /* Start - List the categories for this product. */ %>

<table bgcolor="#cccccc">

<bean:size id="categs_len" name="product" property="catalogCategories"/>
<logic:greaterThan name="categs_len" value="0">

<logic:iterate id="categs" 
name="product" property="catalogCategories">

<tr bgcolor="#cccccc">

<bean:define id="I_prodId" type="java.lang.Integer"
name="product" property="productId"/>
<bean:define id="I_categId" type="java.lang.Integer"
name="categs" property="catalogCategoryId"/>

<%
String rmurl = "itemsearch.do?action=rm_item_category&amp;" +
"itemId=" + I_prodId.intValue() + "&amp;" + 
"catalogId=" + thisCatalogId + "&amp;" + 
"categoryId=" + I_categId.intValue();
%>

<td bgcolor="#cccccc" ><a style="tbar" href="<%=rmurl%>" ><img border="0" alt="[Remove from this category.]" src="/<%=storeDir%>/en/images/button_x.gif"</a>
</td>
<td bgcolor="#cccccc" >
<bean:write name="categs" property="catalogCategoryShortDesc"/>
</td>

</tr>
</logic:iterate>

</logic:greaterThan>

</table>

<% /* End - List the categories for this product. */ %>

</td>

<logic:notEqual name="CATALOG_DETAIL_FORM" 
  property="detail.catalogTypeCd" value="SYSTEM">
  <% 
    BusEntityData dist = ((ProductData)product).getCatalogDistributor();
    if (dist == null) {
  %>
      <td></td>
  <%    } else { %>
      <td>
      <bean:write name="product" property="catalogDistributor.busEntityId"/>
<br>      <bean:write name="product" property="catalogDistributor.shortDesc"/>

      </td>
  <%    }%>
</logic:notEqual>

    <td>
      <html:multibox name="ITEM_SEARCH_FORM" styleClass="smalltext"
      property="selectorBox" value="<%=prodIdx.toString()%>"/>
      <logic:equal name="CATALOG_DETAIL_FORM" property="detail.catalogTypeCd" value="SYSTEM">
       <% String linkClone = new String("itemmaster.do?action=clone&retaction=itemsearch&itemId=" + key);%>
       <html:link href="<%=linkClone%>">Clone</html:link>
      </logic:equal>
    </td>

  <td>
 </tr>
 </logic:iterate>
<tr align=center><td colspan="11">&nbsp;</td></tr>
</table>

<div style="text-align: right; margin-right: 2em; background-color: #cccccc; ">

<a href="javascript:SetChecked(1)">[Check&nbsp;All]</a>
<a href="javascript:SetChecked(0)">[&nbsp;Clear]</a> <br>
<b>Categories Available: </b>
<bean:define id="catsAvailable" scope="session"
  name="ITEM_SEARCH_FORM" property="categoryTree" 
  type="java.util.LinkedList" />
<html:select name="ITEM_SEARCH_FORM" property="selectedCategoryId">
<html:options collection="catsAvailable" 
  labelProperty="labelDesc" 
  property="catalogCategoryId"/>

</html:select>

<html:submit property="action" value="Add category to item(s).">
add_to_category
</html:submit>
<br><html:submit property="action" value="Reset Item(s) to Distributor 0"/>

</div>

</logic:greaterThan>
</div>

</html:form>
<jsp:include flush='true' page="ui/admFooter.jsp"/>

</body>

</html:html>


