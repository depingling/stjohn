<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<jsp:include flush='true' page="../general/checkBrowser.jsp"/>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>

<app:checkLogon/>
<bean:define id="theForm" name="ITEM_SUBST_FORM" type="com.cleanwise.view.forms.ItemSubstMgrForm"/>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<%
	String isMSIE = (String)session.getAttribute("IsMSIE");
	if (null == isMSIE) isMSIE = "";
%>

<html:html>

<head>
<title>Application Administrator Home: Contracts</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../externals//styles.css">
</head>


<script language="JavaScript1.2">
<!--
function popLocate(name) {
  var loc = "itemsubstmgradd.do?feedField=" + name+"&catalogId="+<%=theForm.getStoreCatalogId()%>;
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

<% if ("Y".equals(isMSIE)) { %>
<script language="JavaScript" src="../externals/calendar.js"></script>
<% } else {  %>
<script language="JavaScript" src="../externals/calendarNS.js"></script>
<% }  %>

<body bgcolor="#FFFFFF">

<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/admContractToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>
<jsp:include flush='true' page="ui/contractInfo.jsp"/>

<% if ("Y".equals(isMSIE)) { %>
<iframe style="display:none; position:absolute; z-index:1; width:148; height:194" id="CalFrame"
    marginheight=0 marginwidth=0 noresize frameborder=0 scrolling=no src="../externals/calendar.html"></iframe>
<% }  %>

<div class="text">
<font color=red>
<html:errors/>
</font>

<pg:pager maxPageItems="<%= Constants.MAX_PAGE_ITEMS %>"
          maxIndexPages="<%= Constants.MAX_INDEX_PAGES %>">
  <pg:param name="pg"/>
  <pg:param name="q"/>

<table border="0" cellpadding="1" cellspacing="0" width="769" class="mainbody">
<html:form name="ITEM_SUBST_FORM" action="/adminportal/itemsubstmgr.do"
	type="com.cleanwise.view.forms.ContractMgrDetailForm">

<tr><td>
  <table border="0" width="769" class="results">
  <tr><td colspan="4" class="largeheader">Distributor Substitute
       <html:hidden name="ITEM_SUBST_FORM" property="outServiceName"/>
       <html:submit onclick="return popLocate('outServiceName');" property="action" value="Search Item"/>
  </td></tr>
  </table>
  <% if(theForm.getSubstItems()!=null) { %>
  <tr><td>
  <table width="100%" border="0" class="results">
  <tr align=center bgcolor=lightgrey>
  <td colspan="9">Substitute Items</td>
  <tr>
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
  <logic:iterate id="product" name="ITEM_SUBST_FORM" property="substItems"
    offset="0" indexId="iii"
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
    </tr>
  </logic:iterate>
  </table>
  </td></tr>
  <% } %>
  <tr><td>
  <table border="0" width="769" class="results">
  <tr><td colspan="4" class="largeheader">Contract Item(s)</td>
  </tr>
  <tr><td><b>Category:</b></td>
  <td colspan="3"><html:text name="ITEM_SUBST_FORM" property="categoryTempl"/>
  </td>
  </tr>
  <tr><td><b>Short Description:<b></td>
      <td><html:text name="ITEM_SUBST_FORM" property="shortDescTempl"/></td>
      <td><b>Item Size Description:<b></td>
      <td><html:text name="ITEM_SUBST_FORM" property="sizeTempl"/></td>
  </tr>
  <tr><td><b>Long Description:</b></td>
  <td colspan="3"><html:text name="ITEM_SUBST_FORM" property="longDescTempl" size="80"/>
  </td>
  </tr>
  <tr><td><b>Manufacturer Id:<b></td>
      <td><html:text name="ITEM_SUBST_FORM" property="manuId" size="10"/>
      <html:button onclick="return popManufLocate('manuId','manuName');" value="Locate Manufacturer" property="action"/>
      </td>
     <td><b>Manufacturer Name:<b></td>
     <td>
        <html:text name="ITEM_SUBST_FORM" property="manuName" size="30" readonly="true" styleClass="resultslocatename"/>
     </td>
   </tr>
      <tr>
      <td><b>Distributor Id:<b></td>
      <td><html:text name="ITEM_SUBST_FORM" property="distributorId" size="10"/>
          <html:button onclick="return popDistLocate('distributorId','distributorName');" value="Locate Distributor" property="action" />
      </td>
      <td><b>Distributor Name:<b></td>
      <td>
          <html:text name="ITEM_SUBST_FORM" property="distributorName" size="30" readonly="true" styleClass="resultslocatename"/>
      </td>
  </tr>
  <tr><td><b>Sku:</b></td>
       <td colspan="3">
       <html:text name="ITEM_SUBST_FORM" property="skuTempl"/>
       <html:radio name="ITEM_SUBST_FORM" property="skuType" value="System"/>
       System
       <html:radio name="ITEM_SUBST_FORM" property="skuType" value="Manufacturer"/>
       Manufacturer
       <html:radio name="ITEM_SUBST_FORM" property="skuType" value="Distributor"/>
       Distributor
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       <html:checkbox name="ITEM_SUBST_FORM" property="substOnlyFlag"/>Show Substitutions Only
       </td>
  </tr>
  <tr class="results"> <td></td>
       <td colspan="3">
         </td>
  </tr>
  <tr> <td></td>
       <td colspan="3">
       <html:submit property="action" value="Search"/>
       <% if(theForm.getSubstitutions()!=null && theForm.getSubstitutions().size()>0) { %>
          <% if(theForm.getSubstItems()!=null && theForm.getSubstItems().size() > 0) { %>
           <html:submit property="action" value="Add Substitutions"/>
          <% } %>
         <html:submit property="action" value="Remove Substitutions"/>
       <% } %>
    </td>
  </tr>
</table>
</td></tr>
<% if(theForm.getSubstitutions()!=null) { %>
<tr><td>
  <table width="769" border="0" class="results">
  <tr>
  <td colspan="11"><b>Contract Entries:</b>
  <bean:size id="itemCount" name="ITEM_SUBST_FORM" property="substitutions" />
  <bean:write name="itemCount" />
  </td>
  </tr>
  <tr>
  <td class="tableheader" colspan="6" align="center">Item</td>
  <td class="tableheader" colspan="7" align="center"><font color="darkblue">Substitution</font></td>
  </tr>
  <tr>
  <td><a class="tableheader" href="itemsubstmgr.do?action=sortitems&sortField=itemId">Id</td>
  <td><a class="tableheader" href="itemsubstmgr.do?action=sortitems&sortField=itemSku">Sku</td>
  <td><a class="tableheader" href="itemsubstmgr.do?action=sortitems&sortField=itemDesc">Name</td>
  <td><a class="tableheader" href="itemsubstmgr.do?action=sortitems&sortField=itemSize">Size</td>
  <td><a class="tableheader" href="itemsubstmgr.do?action=sortitems&sortField=itemPack">Pack</td>
  <td><a class="tableheader" href="itemsubstmgr.do?action=sortitems&sortField=itemUom">Uom</td>
  <td><a class="tableheader" href="itemsubstmgr.do?action=sortitems&sortField=substItemId"><font color="darkblue">Id</font></td>
  <td><a class="tableheader" href="itemsubstmgr.do?action=sortitems&sortField=substItemSku"><font color="darkblue">Sku</font></td>
  <td><a class="tableheader" href="itemsubstmgr.do?action=sortitems&sortField=substItemDesc"><font color="darkblue">Name</font></td>
  <td><a class="tableheader" href="itemsubstmgr.do?action=sortitems&sortField=substItemSize"><font color="darkblue">Size</font></td>
  <td><a class="tableheader" href="itemsubstmgr.do?action=sortitems&sortField=substItemPack"><font color="darkblue">Pack</font></td>
  <td><a class="tableheader" href="itemsubstmgr.do?action=sortitems&sortField=substItemUom"><font color="darkblue">Uom</font></td>
  <td><a class="tableheader">&nbsp;</td>
  <td class="tableheader">Select</td>
  </tr>
  <% int itemIdOld=-1; %>
  <% int itemId=0; %>
  <logic:iterate id="itemele" indexId="i" name="ITEM_SUBST_FORM" property="substitutions" scope="session" type="com.cleanwise.service.api.value.ContractItemSubstView">

  <pg:item>
  <tr>
  <%
  itemId=itemele.getItemId();
  int substId=itemele.getContractItemSubstId();
  if(itemIdOld!=itemId) {
    itemIdOld=itemId;
  %>
    <td><%=itemele.getItemId()%>&nbsp;</td>
    <td><%=itemele.getItemSku()%>&nbsp;</td>
    <td><%=itemele.getItemDesc()%>&nbsp;</td>
    <td><%=itemele.getItemSize()%>&nbsp;</td>
    <td><%=itemele.getItemPack()%>&nbsp;</td>
    <td><%=itemele.getItemUom()%>&nbsp;</td>
 <% } else { %>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
 <% } %>
  <td><font color="darkblue"><%=itemele.getSubstItemId()%></font>&nbsp;</td>
  <td><font color="darkblue"><%=itemele.getSubstItemSku()%></font>&nbsp;</td>
  <td><font color="darkblue"><%=itemele.getSubstItemDesc()%></font>&nbsp;</td>
  <td><font color="darkblue"><%=itemele.getSubstItemSize()%></font>&nbsp;</td>
  <td><font color="darkblue"><%=itemele.getSubstItemPack()%></font>&nbsp;</td>
  <td><font color="darkblue"><%=itemele.getSubstItemUom()%></font>&nbsp;</td>
  <td>&nbsp;</td>
  <td>
   <html:multibox name="ITEM_SUBST_FORM" property="selectorBox" value="<%=itemId+\"#\"+substId%>"/>
  </td>
  </tr>

  </pg:item>
  </logic:iterate>

  <tr><td align=center colspan=11>
  <pg:index>
    Result Pages:
    <pg:prev>&nbsp;<a href="<%= pageUrl %>">[<< Prev]</a></pg:prev>
    <pg:pages><%
      if (pageNumber.intValue() < 10) {
        %>&nbsp;<%
      }
      if (pageNumber == pagerPageNumber) {
        %><b><%= pageNumber %></b><%
      } else {
        %><a href="<%= pageUrl %>"><%= pageNumber %></a><%
      }
    %>
    </pg:pages>
    <pg:next>&nbsp;<a href="<%= pageUrl %>">[Next >>]</a></pg:next>
    <br></font>
  </pg:index>
</td></tr>
</table>
</td></tr>
<% } %>


</html:form>
</table>

</pg:pager>
</div>

<% if ("Y".equals(isMSIE)) { %>
<script for=document event="onclick()">
<!--
document.all.CalFrame.style.display="none";
//-->
</script>
<% }  %>

<jsp:include flush='true' page="ui/admFooter.jsp"/>

</body>
</html:html>

