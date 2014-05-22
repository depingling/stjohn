<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<jsp:include flush='true' page="../general/checkBrowser.jsp"/>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<%
	String feedField =  (String)request.getParameter("feedField");
	if(null == feedField) {
		feedField = new String("");
	}
	
	String isMSIE = (String)session.getAttribute("IsMSIE");
	if (null == isMSIE) isMSIE = "";
	
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

/*
function changeUomCd(element, targetElement) {
	if(element.options[element.selectedIndex].value=="OTHER") {
		targetElement.readOnly = false;
		targetElement.value = "";
	}
	else {
		targetElement.readOnly = true;
		targetElement.value = element.options[element.selectedIndex].value;
	}
	return true;
}
*/

function setReadOnly(element, selectElement) {
	if(selectElement.options[selectElement.selectedIndex].value=="OTHER") {
		return true;
	}
	else {
		element.blur();
	}
	return true;
}

function changeUomCdNS(element, targetElement) {
	if(element.options[element.selectedIndex].value=="OTHER") {
		targetElement.value = "";
	}
	else {
		targetElement.value = element.options[element.selectedIndex].value;
	}
	return true;
}

function changeUomCd(element, targetElement, layerId) {
	var targetName = targetElement.name;
	if(element.options[element.selectedIndex].value=="OTHER") {
		var htmln = '<input type="text" name="' + targetName + '" value="" size="2" maxlength="2">';
	}
	else {
		var htmln = '<input type="hidden" name="' + targetName + '" value="' + element.options[element.selectedIndex].value + '">';
	}
	rewriteLayer(layerId, htmln);
	return true;
}

// from www.faqts.com
function rewriteLayer(idOrPath, html) {
  if (document.layers) {
    var l = idOrPath.indexOf('.') != -1 ? eval(document[idOrPath])
             : document[idOrPath];
    if (!l.ol) {
      var ol = l.ol = new Layer (l.clip.width, l);
      ol.clip.width = l.clip.width;
      ol.clip.height = l.clip.height;
      ol.bgColor = l.bgColor;
      l.visibility = 'hide';
      ol.visibility = 'show';
    }
    var ol = l.ol;
    ol.document.open();
    ol.document.write(html);
    ol.document.close();
  }
  else if (document.all || document.getElementById) {
    var p = idOrPath.indexOf('.');
    var id = p != -1 ?
              idOrPath.substring(idOrPath.lastIndexOf('.') + 1)
              : idOrPath;
    if (document.all)
      document.all[id].innerHTML = html;
    else {
      var l = document.getElementById(id);
      var r = document.createRange();
      r.setStartAfter(l);
      var docFrag = r.createContextualFragment(html);
      while (l.hasChildNodes())
        l.removeChild(l.firstChild);
      l.appendChild(docFrag);
    }
  }
}


//-->
</script>


<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Application Administrator Home: Select Distributors</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<style>
.UOMCD  { position: relative;}
</style>


<body class="results">

<html:form action="/adminportal/itemdistrassign.do" focus="searchField"
    scope="session" type="com.cleanwise.view.forms.ItemMgrDistrAssignForm">

<div class="text">

  <table border="0" cellpadding="1" cellspacing="0" width="769" class="results">
  <tr class="results"> <td><b>Find Distributors:</b></td>
       <td colspan="3">
			<html:text name="ITEM_DISTR_ASSIGN_FORM" property="searchField"/>
			<input type="hidden" name="feedField" value="<%=feedField%>">
       </td>
  </tr>
  <tr class="results"> <td></td>
       <td colspan="3">
         <html:radio name="ITEM_DISTR_ASSIGN_FORM" property="searchType" value="distrId" />
         ID
         <html:radio name="ITEM_DISTR_ASSIGN_FORM" property="searchType" value="distrNameStarts" />
         Name(starts with)
         <html:radio name="ITEM_DISTR_ASSIGN_FORM" property="searchType" value="distrNameContains" />
         Name(contains)
         </td>
  </tr>
  <tr> <td></td>
       <td colspan="3">
       <html:submit property="action" value="Search"/>
       <html:submit property="action" value="View All Distributors"/>
    </td>
  </tr>
</table>
<!--
Search results
-->
<div>
<table border="0" cellpadding="1" cellspacing="0" width="100%" class="results">
<tr align=center bgcolor=lightgrey>
<td width="5%">Id </td>
<td width="20%">Name </td>
<td width="15%">City </td>
<td width="10%">State </td>
<td width="15%">Distributor Sku</td>
<td width="20%">Distributor UOM</td>
<td width="15%">Distributor Pack</td>
</tr>
<logic:greaterThan name="ITEM_DISTR_ASSIGN_FORM" property="listCount" value="0">
   <bean:define id="offset" name="ITEM_DISTR_ASSIGN_FORM" property="offset"/>
   <bean:define id="pagesize" name="ITEM_DISTR_ASSIGN_FORM" property="pageSize"/>
   <logic:iterate id="distributor" name="ITEM_DISTR_ASSIGN_FORM" property="resultList"
    offset="<%=offset.toString()%>" length="<%=pagesize.toString()%>" indexId="kkk"
    type="com.cleanwise.service.api.value.DistributorData">
    <bean:define id="key"  name="distributor" property="busEntity.busEntityId"/>
    <bean:define id="name" name="distributor" property="busEntity.shortDesc"/>
    <bean:define id="skuEl" value="<%=\"skuNumElement[\"+kkk+\"]\"%>"/>
	<bean:define id="uomEl" value="<%=\"uomElement[\"+kkk+\"]\"%>"/>
	<bean:define id="productUomEl" value="<%=\"productUomElement[\"+kkk+\"]\"%>"/>
	<bean:define id="packEl" value="<%=\"packElement[\"+kkk+\"]\"%>"/>
    <tr>
    <td><bean:write name="distributor" property="busEntity.busEntityId" filter="true"/></td>
    <td><bean:write name="distributor" property="busEntity.shortDesc" filter="true"/></td>
    <td><bean:write name="distributor" property="primaryAddress.city" filter="true"/></td>
    <td><bean:write name="distributor" property="primaryAddress.stateProvinceCd" filter="true"/></td>

    <td>
    <html:text name="ITEM_DISTR_ASSIGN_FORM" property="<%=skuEl%>"/>
    </td>
    <td>

	<% if ("Y".equals(isMSIE)) { %>		
		<table cellpadding=0 cellspacing=0 border=0>
		 <tr><td>&nbsp;&nbsp;	
		<%  String changeFunction =  new String("return changeUomCd(this, document.forms[0].elements['" + uomEl + "'], 'S" + uomEl + "');") ;%>
        <html:select name="ITEM_DISTR_ASSIGN_FORM" property="<%=productUomEl%>" onchange="<%=changeFunction%>">
        	<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
			<html:options  collection="Item.uom.vector" property="value" />
       	</html:select>
		</td><td>
		<div id="S<%=uomEl%>" class="UOMCD">
			<logic:equal name="ITEM_DISTR_ASSIGN_FORM" property="<%=productUomEl%>" value="OTHER">
    			<html:text name="ITEM_DISTR_ASSIGN_FORM" property="<%=uomEl%>" size="2" maxlength="2"/>
			</logic:equal>	
			<logic:notEqual name="ITEM_DISTR_ASSIGN_FORM" property="<%=productUomEl%>" value="OTHER">
    			<html:hidden name="ITEM_DISTR_ASSIGN_FORM" property="<%=uomEl%>"/>
			</logic:notEqual>			
		</div></td>
		</tr></table>
	<% } else { // for Netscape  %>
		<table cellpadding=0 cellspacing=0 border=0>
		 <tr><td>&nbsp;&nbsp;	
		<%  String changeFunction =  new String("return changeUomCdNS(this, document.forms[0].elements['" + uomEl + "']);") ;%>
        <html:select name="ITEM_DISTR_ASSIGN_FORM" property="<%=productUomEl%>" onchange="<%=changeFunction%>">
        	<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
			<html:options  collection="Item.uom.vector" property="value" />
       	</html:select>
		</td>
		<td>
		<%  String setReadOnlyFunction = new String("return setReadOnly(this, document.forms[0].elements['" + productUomEl + "']);"); %>	
			<html:text name="ITEM_DISTR_ASSIGN_FORM" property="<%=uomEl%>" size="2" maxlength="2" onfocus="<%=setReadOnlyFunction%>"/>
		</td>
		</tr></table>			
	<% }   %>	
		
    </td>
    <td>
    <html:text name="ITEM_DISTR_ASSIGN_FORM" property="<%=packEl%>"/>
    </td>

    </tr>
    </logic:iterate>
    <logic:greaterThan name="ITEM_DISTR_ASSIGN_FORM" property="listCount" value="<%=\"\"+pagesize%>">
      <tr>
      <td colspan="5">
      <logic:iterate id="pages" name="ITEM_DISTR_ASSIGN_FORM" property="pages"
      offset="0" indexId="ii" type="java.lang.Integer">
      <logic:notEqual name="ITEM_DISTR_ASSIGN_FORM" property="currentPage" value="<%=\"\"+ii%>" >
      <% String linkHref = new String("itemdistrassign.do?action=goPage&page=" + ii);%>
      <html:link href="<%=linkHref%>">[<%=ii.intValue()+1%>]</html:link>
      </logic:notEqual>
      <logic:equal name="ITEM_DISTR_ASSIGN_FORM" property="currentPage" value="<%=\"\"+ii%>" >
       [<%=ii.intValue()+1%>]
      </logic:equal>
      </logic:iterate>
      </td>
      </tr>
    </logic:greaterThan>

 <tr>
 	<td colspan="7" align="center">
		<html:submit property="action" value="Add/Update Item Distributors"/>
	</td>
 </tr>
 </logic:greaterThan>

<tr align=center bgcolor=lightgrey>
<td colspan="7">&nbsp;</td>
</tr>
</table>
</div>

</div>
<jsp:include flush='true' page="ui/admFooter.jsp"/>

</html:form>
</body>

</html:html>




