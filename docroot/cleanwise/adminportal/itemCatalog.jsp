<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<% String storeDir=ClwCustomizer.getStoreDir(); %>
<bean:define id="imageName" name="ITEM_CATALOG_FORM" property="product.image" scope="session"/>
<bean:define id="product" name="ITEM_CATALOG_FORM" property="product" scope="session"/>


<script language="JavaScript1.2">
<!--
function popDistLocate(name,name1) {
  var loc = "distlocate.do?feedField=" + name+"&feedDesc="+name1+"&locateFilter=itemCatalog";
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
<html:form action="/adminportal/itemcatalog.do" >
<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/catalogToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>
<jsp:include flush='true' page="ui/catalogInfo.jsp"/>
<font color=red>
<html:errors/>
</font>
<table border="0"cellpadding="0" cellspacing="1" width="769">
  <tr bgcolor="#cccccc">
  <td colspan="9">
    <table width="767" border="0">
    <tr>
    <td width="10"></td>
    <td width="744">
<div style="background-color: #ccffcc; font-size: 125%; font-weight: bold;">
SKU:&nbsp;&nbsp;
<bean:write name="ITEM_CATALOG_FORM" property="product.skuNum"/>
</div>
       <table border="0" width="722">
         <tr>
         <td width="20%">
<b>Item ID:</b></td>
         <td width="30%">
           <bean:write name="ITEM_CATALOG_FORM" property="product.productId"/>
         </td>
         <td width="20%"> <b>Item Name:</b> </td>
         <td width="30%">
           <bean:write name="ITEM_CATALOG_FORM" property="product.shortDesc"/>
         </td>
         </tr>
         <tr>
         <td><b>Active Date:</b> </td>
         <td>
            <bean:write name="ITEM_CATALOG_FORM" property="effDate"/>
         </td>
         <td><b>Item Status: </b> </td>
         <td>
            <bean:write name="ITEM_CATALOG_FORM" property="itemStatus"/>
         </td>
         </tr>
         <tr>
         <td><b>Inactive Date:</b></td>
         <td>
            <bean:write name="ITEM_CATALOG_FORM" property="expDate"/>
         </td>
         <td><b>Cost Center Name:</b></td>
<td>
            <bean:write name="ITEM_CATALOG_FORM" property="product.costCenterName" />
</td>
         </tr>

         <tr>
         <td><b>Distributor Id:</b></td>
         <td colspan="3">
<bean:define id="distributorId" 
name="ITEM_CATALOG_FORM" property="distributorId" 
type="java.lang.String" />
<% if ( distributorId.equals("0") ) { distributorId = ""; } %>

<html:text name="ITEM_CATALOG_FORM" property="distributorId"
  value="<%=distributorId%>" size="10"/>

<html:button onclick="return popDistLocate('distributorId','distributorName');" value="Locate Distributor" property="action"/>
         </td>
         </tr>
         <tr>
         <td><b>Distributor Name:</b></td>
         <td>
            <html:text name="ITEM_CATALOG_FORM" property="distributorName" size="30" readonly="true" styleClass="mainbodylocatename"/>
         </td>
         </tr>

         <tr>
         <td><font size="2"><b>Distributor Sku:</b></font></td>
         <td colspan="3"><font size="2">
            <bean:write name="ITEM_CATALOG_FORM" property="distributorSku" />
         </font></td>
         </tr>

         <tr>
         <td><font size="2"><b>Customer Sku:</b></font></td>
         <td colspan="3"><font size="2">
            <html:text name="ITEM_CATALOG_FORM" property="product.customerSkuNum" size="20" maxlength="128"/>
         </font>
&nbsp;&nbsp;&nbsp;
        <html:submit property="action" value="Save Item"/>
<input type=button value="Back" onClick="javascript: history.back()">
</td>
         </tr>

<tr>
<td ><font size="2"><b>Customer Item Name:</b></font></td>
<td colspan="3">
<html:text name="ITEM_CATALOG_FORM" property="product.customerProductShortDesc" size="40" maxlength="128"/>
</td>
</tr>

         <tr>

       <tbody> </tbody>
       </table>
       <div align="left"><br>
       <br>
       <table bgcolor="#ccffcc">
         <tr>
         <td colspan="4"> <font size="2"><b>Long Description:</b></font></td>
         </tr>
         <tr>
         <td colspan="2"> <font size="2">
         <bean:write name="ITEM_CATALOG_FORM" property="product.longDesc"/>
         </font></td>
         <td colspan="2" >
           <div align="center">
           <bean:define id="image" value="<%=new String(\"../\"+imageName) %>"/>
           <html:img src="<%=image%>" width="240" height="240"/>
           </div>
         </td>
         </tr>
         <tr>
         <td width="20%"><font size="2"><b>SKU:</b></font></td>
         <td width="30%"> 
            <bean:write name="ITEM_CATALOG_FORM" property="product.skuNum"/>
         </td>
         </tr>
         <tr>
         <td><font size="2"><b>Manufacturer Id:</b></font></td>
         <td> <font size="2">
         <bean:write name="ITEM_CATALOG_FORM" property="manufacturerId" />
         </td>
         <td><font size="2"><b>Manufacturer Name:</b></font></td>
         <td>
         <bean:write name="ITEM_CATALOG_FORM" property="manufacturerName" />
         </td>
         </tr>
         <tr>
         <td><font size="2"><b>Manufacturer SKU:</b></font></td>
         <td> <font size="2">
            <bean:write name="ITEM_CATALOG_FORM" property="manufacturerSku" />
         </font></td>
         <td><font size="2"><b>MSDS:</b></font></td>
             <logic:notEqual name="ITEM_CATALOG_FORM" property="product.msds" value="">
             <bean:define id="msdsName" name="ITEM_CATALOG_FORM" property="product.msds" scope="session"/>
             <% String msdsClick = new String("window.open('/"+storeDir+"/"+msdsName+"','MSDS');");%>
	     <td><html:button onclick="<%=msdsClick%>" value="View MSDS" property="action"/></td>
	     </logic:notEqual>
             <logic:equal name="ITEM_CATALOG_FORM" property="product.msds" value="">
	     <td><html:button value="View MSDS" property="action" disabled="true"/></td>
	     </logic:equal>
         </tr>
         <tr>
         <td><font size="2"><b>Categories:</b></font></td>
         <td> <font size="2">
           <logic:greaterThan name="ITEM_CATALOG_FORM" property="categoryListSize" value="0">
             <logic:iterate id="category" name="ITEM_CATALOG_FORM" property="product.catalogCategories"
              type="com.cleanwise.service.api.value.CatalogCategoryData">
                <bean:write name="category" property="catalogCategoryShortDesc"/>
             </logic:iterate>
            </logic:greaterThan>
         </font></td>
         <td><font size="2"><b>DED:</b></font></td>
             <logic:notEqual name="ITEM_CATALOG_FORM" property="product.ded" value="">
             <bean:define id="dedName" name="ITEM_CATALOG_FORM" property="product.ded" scope="session"/>
             <% String dedClick = new String("window.open('/"+storeDir+"/"+dedName+"','DED');");%>
	     <td><html:button onclick="<%=dedClick%>" value="View DED" property="action"/></td>
	     </logic:notEqual>
             <logic:equal name="ITEM_CATALOG_FORM" property="product.ded" value="">
	     <td><html:button value="View DED" property="action" disabled="true"/></td>
	     </logic:equal>
         </tr>
         <tr>
	 <td colspan="2"></td>
         <td><font size="2"><b>Prod Spec:</b></font></td>
             <logic:notEqual name="ITEM_CATALOG_FORM" property="product.spec" value="">
             <bean:define id="specName" name="ITEM_CATALOG_FORM" property="product.spec" scope="session"/>
             <% String specClick = new String("window.open('/"+storeDir+"/"+specName+"','Spec');");%>
	     <td><html:button onclick="<%=specClick%>" value="View Product Spec" property="action"/></td>
	     </logic:notEqual>
             <logic:equal name="ITEM_CATALOG_FORM" property="product.spec" value="">
	     <td><html:button value="View Product Spec" property="action" disabled="true"/></td>
	     </logic:equal>
         </tr>
         <tr>
         <td><font size="2"><b>Product UPC:</b></font></td>
         <td> <font size="2">
            <bean:write name="ITEM_CATALOG_FORM" property="product.upc"/>
         </font></td>
         <td><font size="2"><b>Item Size:</b></font></td>
         <td> <font size="2">
            <bean:write name="ITEM_CATALOG_FORM" property="product.size"/>
         </font></td>
         </tr>
         <tr>
         <td><font size="2"><b>Pack UPC:</b></font></td>
         <td> <font size="2">
            <bean:write name="ITEM_CATALOG_FORM" property="product.pkgUpc"/>
         </font></td>
         <td><font size="2"><b>Shipping Weight:</b></font></td>
         <td>
            <bean:write name="ITEM_CATALOG_FORM" property="product.shipWeight"/>
         </font></td>
         </tr>
         <tr>
         <td><font size="2"><b>Color:</b></font></td>
         <td> <font size="2">
            <bean:write name="ITEM_CATALOG_FORM" property="product.color"/>
         </font></td>
         <td><font size="2"><b>UOM:</b></font></td>
         <td> <font size="2">
            <bean:write name="ITEM_CATALOG_FORM" property="product.uom"/>
         </font></td>
         </tr>
         <tr>
         <td><font size="2"><b>Scent:</b></font></td>
         <td> <font size="2">
            <bean:write name="ITEM_CATALOG_FORM" property="product.scent"/>
         </font></td>
         <td><font size="2"><b>Pack:</b></font></td>
         <td> <font size="2">
            <bean:write name="ITEM_CATALOG_FORM" property="product.pack"/>
         </font></td>
         </tr>
        </table>
        <!-- Control buttons -->
        <html:submit property="action" value="Save Item"/>
         <!--
              <input type="button" name="Submit3" value="Undo Changes">
              <input type="button" name="Submit2" value="Copy product as new SKU">
              <input type="button" name="Button2" value="Delete Item">
              <input type="button" name="Button22" value="View Item Associations" onClick="parent.location='itemAssociations.htm'">
          -->
         </div>
         <p align="center">&nbsp;</p>
        </td>
        <td width="0"></td>
        </tr>
      </table>
    </td>
  </tr>
</table>
<p align="center">&nbsp;</p>
</html:form>
<p>&nbsp;</p>
<jsp:include flush='true' page="ui/admFooter.jsp"/>
</body>
</html:html>

