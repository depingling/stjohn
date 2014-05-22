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
<font color=red>
<html:errors/>
</font>
<html:form action="/adminportal/itemsubstmgradd.do" >
<!--
<table border="0"  class="results">
</table>
-->
  <table border="0" class="results">
  <tr>
  <td colspan="4"><b>Catalog ID:&nbsp;</b> <bean:write name="ITEM_SUBST_ADD_FORM" property="catalogId"/>  </td>
  </tr>
  <tr><td colspan="4" class="largeheader"><b>Search Substitution for the Item:</b></td>
  <tr><td colspan="4" class="largeheader">
  <b>Sku:</b>&nbsp;<bean:write name="ITEM_SUBST_ADD_FORM" property="sourceProduct.skuNum"/>
  &nbsp;&nbsp;&nbsp;
  <b>Name:</b>&nbsp;<bean:write name="ITEM_SUBST_ADD_FORM" property="sourceProduct.shortDesc"/>
  </td>
  </tr>
  <tr><td><b>Category:</b></td>
  <td colspan="3"><html:text name="ITEM_SUBST_ADD_FORM" property="categoryTempl"/>
  </td>
  </tr>
  <tr><td><b>Short Description:<b></td>
      <td><html:text name="ITEM_SUBST_ADD_FORM" property="shortDescTempl"/></td>
      <td><b>Item Size Description:<b></td>
      <td><html:text name="ITEM_SUBST_ADD_FORM" property="sizeTempl"/></td>
  </tr>
  <tr><td><b>Long Description:</b></td>
  <td colspan="3"><html:text name="ITEM_SUBST_ADD_FORM" property="longDescTempl" size="40"/>
  </td>
  </tr>
  <tr><td><b>Manufacturer Id:<b></td>
      <td colspan="2"><html:text name="ITEM_SUBST_ADD_FORM" property="manuId" size="10"/>
          <html:button onclick="return popManufLocate('manuId','manuName');" value="Locate Manufacturer" property="action"/>
      </td>
      <td><b>Manufacturer Name:<b></td>
<td>
          <html:text name="ITEM_SUBST_ADD_FORM" property="manuName" size="30" readonly="true" styleClass="resultslocatename"/>
</td>
</tr>
      <tr>
      <td><b>Distributor Id:<b></td>
      <td colspan="2"><html:text name="ITEM_SUBST_ADD_FORM" property="distributorId" size="10"/>

          <html:button onclick="return popDistLocate('distributorId','distributorName');" value="Locate Distributor" property="action"/>
      </td>
      <td><b>Distributor Name:<b></td>
<td>
     <html:text name="ITEM_SUBST_ADD_FORM" property="distributorName" size="30" readonly="true" styleClass="resultslocatename"/>
</td>
</tr>
  <tr><td><b>Sku:</b></td>
       <td colspan="3">
       <html:text name="ITEM_SUBST_ADD_FORM" property="skuTempl"/>
       <html:radio name="ITEM_SUBST_ADD_FORM" property="skuType" value="System"/>
       System
       <html:radio name="ITEM_SUBST_ADD_FORM" property="skuType" value="Manufacturer"/>
       Manufacturer
       <html:radio name="ITEM_SUBST_ADD_FORM" property="skuType" value="Distributor"/>
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
    </td>
  </tr>
</table>
</div>

<div>
<table class="results">
<tr align=center bgcolor=lightgrey>
<td colspan = "9">Uom Conversion Factor: 
  <html:text name="ITEM_SUBST_ADD_FORM" property="conversionFactorS" size="4"/>
 </td>
</tr>
<tr><td colspan="9">
Search result count: <bean:write name="ITEM_SUBST_ADD_FORM" property="listSize"/>
</td>
</tr>
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
</tr>
<logic:greaterThan name="ITEM_SUBST_ADD_FORM" property="listSize" value="0">
   <bean:define id="pagesize" name="ITEM_SUBST_ADD_FORM" property="pageSize"/>
   <logic:iterate id="product" name="ITEM_SUBST_ADD_FORM" property="products"
    offset="0" indexId="kkk"
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
    <logic:equal name="ITEM_SUBST_ADD_FORM" property="resultSource" value="this">
    </logic:equal>
    <tr>
    <td><bean:write name="key"/></td>
    <td><bean:write name="sku"/></td>
    <td>
     <html:hidden name="ITEM_SUBST_ADD_FORM" property="selectedItemIdS" value="444"/>
     <A href="javascript: document.forms[0].elements['selectedItemIdS'].value='<%=key.toString()%>'; document.forms[0].submit(); ">
     <bean:write name="name"/></A>
    </td>
    <td><bean:write name="size"/></td>
    <td><bean:write name="pack"/></td>
    <td><bean:write name="uom"/></td>
    <td><bean:write name="color"/></td>
    <td><bean:write name="manuName"/></td>
    <td><bean:write name="manuSku"/></td>

  <td>
 </tr>
 </logic:iterate>

 <logic:greaterThan name="ITEM_SUBST_ADD_FORM" property="listCount" value="<%=\"\"+pagesize%>">
   <tr>
   <td colspan="9">
    <logic:iterate id="pages" name="ITEM_SUBST_ADD_FORM" property="pages"
    offset="0" indexId="ii" type="java.lang.Integer">
    <logic:notEqual name="ITEM_SUBST_ADD_FORM" property="currentPage" value="<%=\"\"+ii%>" >
    <% String linkHref = new String("itemsubstmgradd.do?action=goPage&page=" + ii);%>
    <html:link href="<%=linkHref%>">[<%=ii.intValue()+1%>]</html:link>
    </logic:notEqual>
    <logic:equal name="ITEM_SUBST_ADD_FORM" property="currentPage" value="<%=\"\"+ii%>" >
     [<%=ii.intValue()+1%>]
   </logic:equal>
   </logic:iterate>
   </td>
  </tr>
 </logic:greaterThan>
</logic:greaterThan>
<tr align=center bgcolor=lightgrey><td colspan="9">&nbsp;</td></tr>
</table>
</div>
<jsp:include flush='true' page="ui/admFooter.jsp"/>
</html:form>
</body>

</html:html>



