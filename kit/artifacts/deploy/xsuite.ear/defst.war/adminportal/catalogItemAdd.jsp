<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<%
	String feedField =  (String)request.getParameter("feedField");
	if(null == feedField) {
		feedField = new String("");
	}
%>

<script language="JavaScript1.2">
<!--

function SetChecked(val) {
 dml=document.forms[0];
 len = dml.elements.length;
 var i=0;
 for( i=0 ; i<len ; i++) {
   if (dml.elements[i].name=='selectorBox') {
     dml.elements[i].checked=val;
   }
 }
}

function selfClose() {

	var feedBackFieldName = '<%=feedField%>';
	if(feedBackFieldName && ""!= feedBackFieldName) {
      window.opener.document.forms[0].elements[feedBackFieldName].value='distributorAssign';
      window.opener.document.forms[0].submit();
      self.close();
    }
}

function popManufLocate(name,name1) {
  var loc = "manuflocate.do?feedField=" + name+"&feedDesc="+name1;
  locatewin = window.open(loc,"tickersearch1", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();

  return false;
}

function popDistLocate(name,name1) {
  var loc = "distlocate.do?feedField=" + name+"&feedDesc="+name1;
  locatewin = window.open(loc,"tickersearch1", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
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
<html:form action="/adminportal/catalogitemadd.do" >

<table border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
  <tr><td width="20%"><b>Catalog ID:</b></td>
  <td width="30%"><bean:write name="CATALOG_ITEM_ADD_FORM" property="superCatalog.catalogId" filter="true"/>
  </td>
  <td width="20%"> <b>Catalog Name:</b> </td>
  <td width="30%"> <bean:write name="CATALOG_ITEM_ADD_FORM" property="superCatalog.shortDesc" />
  </td>
  </tr>
  <tr><td width="20%"><b>Catalog Type:</b></td>
  <td width="30%"><bean:write name="CATALOG_ITEM_ADD_FORM" property="superCatalog.catalogTypeCd" filter="true"/>
  </td>
  <td width="20%"> <b>Catalog Status:</b> </td>
  <td width="30%"> <bean:write name="CATALOG_ITEM_ADD_FORM" property="superCatalog.catalogStatusCd" />
  </td>
</table><!-- --->

  <table border="0" class="results">
  <tr><td><b>Search Item</b></td>
  <td colspan="3">
  </td>
  </tr>
  <tr><td><b>Category:</b></td>
  <td colspan="3"><html:text name="CATALOG_ITEM_ADD_FORM" property="categoryTempl"/>
  </td>
  </tr>
  <tr><td><b>Short Description:<b></td>
      <td><html:text name="CATALOG_ITEM_ADD_FORM" property="shortDescTempl"/></td>
      <td><b>Item Size Description:<b></td>
      <td><html:text name="CATALOG_ITEM_ADD_FORM" property="sizeTempl"/></td>
  </tr>
  <tr><td><b>Long Description:</b></td>
  <td colspan="3"><html:text name="CATALOG_ITEM_ADD_FORM" property="longDescTempl" size="80"/>
  </td>
  </tr>
  <tr><td><b>Manufacturer Id:<b></td>
      <td colspan="2"><html:text name="CATALOG_ITEM_ADD_FORM" property="manuId" size="10"/>
          <html:button onclick="return popManufLocate('manuId','manuName');" value="Locate Manufacturer" property="action"/>
      </td>
      <td><b>Manufacturer Name:<b></td>
<td>
          <html:text name="CATALOG_ITEM_ADD_FORM" property="manuName" size="30" readonly="true" styleClass="resultslocatename"/>
</td>
</tr>
      <tr>
      <td><b>Distributor Id:<b></td>
      <td colspan="2"><html:text name="CATALOG_ITEM_ADD_FORM" property="distributorId" size="10"/>
     
          <html:button onclick="return popDistLocate('distributorId','distributorName');" value="Locate Distributor" property="action"/>
      </td>
      <td><b>Distributor Name:<b></td>
<td>
     <html:text name="CATALOG_ITEM_ADD_FORM" property="distributorName" size="30" readonly="true" styleClass="resultslocatename"/>
</td>
</tr>
  <tr><td><b>Sku:</b></td>
       <td colspan="3">
       <html:text name="CATALOG_ITEM_ADD_FORM" property="skuTempl"/>
       <html:radio name="CATALOG_ITEM_ADD_FORM" property="skuType" value="System"/>
       System
       <html:radio name="CATALOG_ITEM_ADD_FORM" property="skuType" value="Manufacturer"/>
       Manufacturer
       <html:radio name="CATALOG_ITEM_ADD_FORM" property="skuType" value="Distributor"/>
       Distributor
       </td>
  </tr>
  <tr class="results"> <td></td>
       <td colspan="3">
         </td>
  </tr>
  <tr> <td></td>
       <td colspan="3">
       <html:submit property="action" value="Search"/>

<logic:greaterThan name="CATALOG_ITEM_ADD_FORM" property="listSize" value="0">
<b>Catalog Category: </b>
<bean:define id="catsAvailable" scope="session"
  name="ITEM_SEARCH_FORM" property="categoryTree" 
  type="java.util.LinkedList" />
<html:select name="CATALOG_ITEM_ADD_FORM" property="selectedCategoryId">
<html:options collection="catsAvailable" labelProperty="labelDesc" 
  property="catalogCategoryId"/>
</html:select>

         <html:submit property="action" value="Add To Catalog"/>
</logic:greaterThan>

    </td>
  </tr>
</table>
</div>

<div>

 Search result count: 
<bean:write name="CATALOG_ITEM_ADD_FORM" property="listSize"/>


<div style="text-align: right; margin-right: 2em;">
<a href="javascript:SetChecked(1)">[Check&nbsp;All]</a>
<a href="javascript:SetChecked(0)">[&nbsp;Clear]</a>
</div>

<table class="results">

<tr align=center bgcolor=lightgrey>
<td width="5%">Id </td>
<td width="8%">Sku </td>
<td width="32%">Name </td>
<td width="8%">Size </td>
<td width="5%">Pack </td>
<td width="5%">UOM </td>
<td width="5%">Color </td>
<td width="20%">Manufacturer </td>
<td width="8%">Manu.Sku </td>
<td width="4%">&nbsp; </td>
</tr>


   <logic:iterate id="product" name="CATALOG_ITEM_ADD_FORM" property="products"
    offset="0" indexId="iIdx"
    type="com.cleanwise.service.api.value.ProductData">

    <bean:define id="key"  name="product" property="productId"/>
    <bean:define id="sku" name="product" property="skuNum"/>
    <bean:define id="name" name="product" property="shortDesc"/>
    <bean:define id="size" name="product" property="size"/>
    <bean:define id="pack" name="product" property="pack"/>
    <bean:define id="uom" name="product" property="uom"/>
    <bean:define id="color" name="product" property="color"/>
    <bean:define id="manuName" name="product" property="manufacturerName"/>
    <bean:define id="manuSku" name="product" property="manufacturerSku"/>

    <tr>
    <td><bean:write name="key"/></td>
    <td><bean:write name="sku"/></td>
    <td><bean:write name="name"/></td>
    <td><bean:write name="size"/></td>
    <td><bean:write name="pack"/></td>
    <td><bean:write name="uom"/></td>
    <td><bean:write name="color"/></td>
    <td><bean:write name="manuName"/></td>
    <td><bean:write name="manuSku"/></td>
    <td>
    <html:multibox name="CATALOG_ITEM_ADD_FORM" property="selectorBox" value="<%=iIdx.toString()%>"/>
    </td>

  <td>
 </tr>
 </logic:iterate>

<tr align=center bgcolor=lightgrey><td colspan="10">&nbsp;</td></tr>
</table>
</div>
<jsp:include flush='true' page="ui/admFooter.jsp"/>
</html:form>
</body>

</html:html>


