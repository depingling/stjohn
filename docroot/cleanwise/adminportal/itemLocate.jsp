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
	String feedDesc =  (String)request.getParameter("feedDesc");
	if(null == feedDesc) {
		feedDesc = new String("");
	}
	String feedSku =  (String)request.getParameter("feedSku");
	if(null == feedSku) {
		feedSku = new String("");
	}	
%>	

<script language="JavaScript1.2">
<!--
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

function passIdAndName(id, name, sku) {
  var feedBackFieldName = document.ITEM_LOCATE_FORM.feedField.value;
  var feedDesc = document.ITEM_LOCATE_FORM.feedDesc.value;
  var feedSku = document.ITEM_LOCATE_FORM.feedSku.value;

  if(feedBackFieldName && ""!= feedBackFieldName) {
    window.opener.document.forms[0].elements[feedBackFieldName].value = id;
  }
  if(feedDesc && ""!= feedDesc) {
    window.opener.document.forms[0].elements[feedDesc].value = unescape(name.replace(/\+/g, ' '));
  }
  if(feedSku && ""!= feedSku) {
    window.opener.document.forms[0].elements[feedSku].value = unescape(sku.replace(/\+/g, ' '));
  }
  
  self.close();
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
<html:form name="ITEM_LOCATE_FORM" action="/adminportal/itemlocate.do" 
	type="com.cleanwise.view.forms.ItemMgrLocateForm">

  <table border="0" width="769" class="results">
  <tr><td class="largeheader">Search Item</td>
  <td colspan="3">
    &nbsp; <html:hidden name="ITEM_LOCATE_FORM" property="whereToSearch" value="this"/>
	<input type="hidden" name="feedField" value="<%=feedField%>">
	<input type="hidden" name="feedDesc" value="<%=feedDesc%>">
	<input type="hidden" name="feedSku" value="<%=feedSku%>">
  </td>
  </tr>
  <tr><td><b>Category:</b></td>
  <td colspan="3"><html:text name="ITEM_LOCATE_FORM" property="categoryTempl"/>
  </td>
  </tr>
  <tr><td><b>Short Description:<b></td>
      <td><html:text name="ITEM_LOCATE_FORM" property="shortDescTempl"/></td>
      <td><b>Item Size Description:<b></td>
      <td><html:text name="ITEM_LOCATE_FORM" property="sizeTempl"/></td>
  </tr>
  <tr><td><b>Long Description:</b></td>
  <td colspan="3"><html:text name="ITEM_LOCATE_FORM" property="longDescTempl" size="80"/>
  </td>
  </tr>
  <tr><td><b>Manufacturer Id:<b></td>
      <td><html:text name="ITEM_LOCATE_FORM" property="manuId" size="10"/>
      <html:button onclick="return popManufLocate('manuId','manuName');" value="Locate Manufacturer" property="action"/>
      </td>
     <td><b>Manufacturer Name:<b></td>
     <td>
        <html:text name="ITEM_LOCATE_FORM" property="manuName" size="30" readonly="true" styleClass="resultslocatename"/>
     </td>
   </tr>
      <tr>
      <td><b>Distributor Id:<b></td>
      <td><html:text name="ITEM_LOCATE_FORM" property="distributorId" size="10"/>
          <html:button onclick="return popDistLocate('distributorId','distributorName');" value="Locate Distributor" property="action" />
      </td>
      <td><b>Distributor Name:<b></td>
      <td>
          <html:text name="ITEM_LOCATE_FORM" property="distributorName" size="30" readonly="true" styleClass="resultslocatename"/>
      </td>
  </tr>
  <tr><td><b>Sku:</b></td>
       <td colspan="3">
       <html:text name="ITEM_LOCATE_FORM" property="skuTempl"/>
       <html:radio name="ITEM_LOCATE_FORM" property="skuType" value="System"/>
       System
       <html:radio name="ITEM_LOCATE_FORM" property="skuType" value="Manufacturer"/>
       Manufacturer
       <html:radio name="ITEM_LOCATE_FORM" property="skuType" value="Distributor"/>
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
Search result count: <bean:write name="ITEM_LOCATE_FORM" property="listSize"/>
<logic:greaterThan name="ITEM_LOCATE_FORM" property="listSize" value="0">


<table width="100%" class="results">
<tr align=center bgcolor=lightgrey>
<td width="5%"><a href="itemlocate.do?action=sort&sortField=id&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>&feedSku=<%=feedSku%>"><b>Id</b> </td>
<td width="8%"><a href="itemlocate.do?action=sort&sortField=sku&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>&feedSku=<%=feedSku%>"><b>Sku</b> </td>
<td width="30%"><a href="itemlocate.do?action=sort&sortField=name&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>&feedSku=<%=feedSku%>"><b>Name</b> </td>
<td width="8%"><a href="itemlocate.do?action=sort&sortField=size&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>&feedSku=<%=feedSku%>"><b>Size</b> </td>
<td width="5%"><a href="itemlocate.do?action=sort&sortField=pack&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>&feedSku=<%=feedSku%>"><b>Pack</b> </td>
<td width="5%"><a href="itemlocate.do?action=sort&sortField=uom&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>&feedSku=<%=feedSku%>"><b>UOM</b> </td>
<td width="5%"><a href="itemlocate.do?action=sort&sortField=color&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>&feedSku=<%=feedSku%>"><b>Color</b> </td>
<td width="18%"><a href="itemlocate.do?action=sort&sortField=manufacturer&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>&feedSku=<%=feedSku%>"><b>Manufacturer</b> </td>
<td width="8%"><a href="itemlocate.do?action=sort&sortField=msku&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>&feedSku=<%=feedSku%>"><b>Manu.Sku</b> </td>
<td width="4%"><a href="itemlocate.do?action=sort&sortField=distid&feedField=<%=feedField%>&feedDesc=<%=feedDesc%>&feedSku=<%=feedSku%>"><b>Dist.Id</b> </td>
</tr>
   <logic:iterate id="product" name="ITEM_LOCATE_FORM" property="products" >

    <bean:define id="key"  name="product" property="productId"/>
    <bean:define id="sku" name="product" property="skuNum" type="java.lang.Integer"/>
    <bean:define id="name" name="product" property="shortDesc" type="java.lang.String"/>
    <bean:define id="size" name="product" property="size"/>
    <bean:define id="pack" name="product" property="pack"/>
    <bean:define id="uom" name="product" property="uom"/>
    <bean:define id="color" name="product" property="color"/>
    <bean:define id="manuName" name="product" property="manufacturerName"/>
    <bean:define id="manuSku" name="product" property="manufacturerSku"/>

    <tr>
    <td><bean:write name="key"/></td>
    <td><bean:write name="sku"/></td>
    <td>
    <% String onClick = new String("return passIdAndName('"+key+"', '"+ java.net.URLEncoder.encode(name) +"', '"+ java.net.URLEncoder.encode(sku.toString()) +"');");%>
    <a href="javascript:void(0);" onclick="<%=onClick%>">
	<bean:write name="name"/>
	</a>
	
    <td><bean:write name="size"/></td>
    <td><bean:write name="pack"/></td>
    <td><bean:write name="uom"/></td>
    <td><bean:write name="color"/></td>
    <td><bean:write name="manuName"/></td>
    <td><bean:write name="manuSku"/></td>
	<logic:present name="product" property="catalogDistributor" >
    <td>
      <bean:write name="product" property="catalogDistributor.busEntityId"/>
    </td>
	</logic:present>
	<logic:notPresent name="product" property="catalogDistributor" >
    <td>&nbsp;</td>
	</logic:notPresent>
	
  <td>
 </tr>
 </logic:iterate>
<tr align=center bgcolor=lightgrey><td colspan="11">&nbsp;</td></tr>
</table>

    <br></font>

</logic:greaterThan>
</div>
<jsp:include flush='true' page="ui/admFooter.jsp"/>
</html:form>
</body>

</html:html>


