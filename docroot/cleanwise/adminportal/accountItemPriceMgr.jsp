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
<bean:define id="theForm" name="ACCOUNT_ITEM_PRICE_FORM" type="com.cleanwise.view.forms.AccountItemPriceMgrForm"/>
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

<script language="JavaScript1.2">
<!--
function SetChecked(val) {
 dml=document.ACCOUNT_ITEM_PRICE_FORM;
 len = dml.elements.length;
 var i=0;
 for( i=0 ; i<len ; i++) {
   if (dml.elements[i].name=='selectorBox') {
     dml.elements[i].checked=val;
   }
 }
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
<jsp:include flush='true' page="ui/admAccountToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>

<% if ("Y".equals(isMSIE)) { %>
<iframe style="display:none; position:absolute; z-index:1; width:148; height:194" id="CalFrame"
    marginheight=0 marginwidth=0 noresize frameborder=0 scrolling=no src="../externals/calendar.html"></iframe>
<% }  %>

<div class="text">
<font color=red>
<html:errors/>
</font>

<jsp:include flush='true' page="ui/accountCtx.jsp"/>

<table border="0" cellpadding="1" cellspacing="0" width="769" class="mainbody">
<html:form name="ACCOUNT_ITEM_PRICE_FORM" action="/adminportal/accountitempricemgr.do"
	type="com.cleanwise.view.forms.ContractMgrDetailForm"
focus="skuTempl" >
  <tr><td>
  <table border="0" width="769" class="results">
  <tr><td colspan="4" class="largeheader">Select Items</td>
  </tr>
  <tr><td><b>Category:</b></td>
  <td colspan="3"><html:text name="ACCOUNT_ITEM_PRICE_FORM" property="categoryTempl"/>
  </td>
  </tr>
  <tr><td><b>Short Description:<b></td>
      <td><html:text name="ACCOUNT_ITEM_PRICE_FORM" property="shortDescTempl"/></td>
      <td><b>Item Size Description:<b></td>
      <td><html:text name="ACCOUNT_ITEM_PRICE_FORM" property="sizeTempl"/></td>
  </tr>
  <tr><td><b>Long Description:</b></td>
  <td colspan="3"><html:text name="ACCOUNT_ITEM_PRICE_FORM" property="longDescTempl" size="80"/>
  </td>
  </tr>
  <tr><td><b>Manufacturer Id:<b></td>
      <td><html:text name="ACCOUNT_ITEM_PRICE_FORM" property="manuId" size="10"/>
      <html:button onclick="return popManufLocate('manuId','manuName');" value="Locate Manufacturer" property="action"/>
      </td>
     <td><b>Manufacturer Name:<b></td>
     <td>
        <html:text name="ACCOUNT_ITEM_PRICE_FORM" property="manuName" size="30" readonly="true" styleClass="resultslocatename"/>
     </td>
   </tr>
      <tr>
      <td><b>Distributor Id:<b></td>
      <td><html:text name="ACCOUNT_ITEM_PRICE_FORM" property="distributorId" size="10"/>
          <html:button onclick="return popDistLocate('distributorId','distributorName');" value="Locate Distributor" property="action" />
      </td>
      <td><b>Distributor Name:<b></td>
      <td>
          <html:text name="ACCOUNT_ITEM_PRICE_FORM" property="distributorName" size="30" readonly="true" styleClass="resultslocatename"/>
      </td>
  </tr>
  <tr><td><b>Sku:</b></td>
       <td colspan="3">
       <html:text name="ACCOUNT_ITEM_PRICE_FORM" property="skuTempl"/>
       <html:radio name="ACCOUNT_ITEM_PRICE_FORM" property="skuType" value="SystemCustomer"/>
       System + Customer
       <html:radio name="ACCOUNT_ITEM_PRICE_FORM" property="skuType" value="Manufacturer"/>
       Manufacturer
       <html:radio name="ACCOUNT_ITEM_PRICE_FORM" property="skuType" value="Distributor"/>
       Distributor
       <html:radio name="ACCOUNT_ITEM_PRICE_FORM" property="skuType" value="System"/>
       System 
       <html:radio name="ACCOUNT_ITEM_PRICE_FORM" property="skuType" value="Customer"/>
       Customer
       </td>
  </tr>
  <tr class="results"> <td></td>
       <td colspan="3">
         </td>
  </tr>
  <tr> <td colspan="4">
       <html:submit property="action" value="Search"/>
  <% if(theForm.getPriceItems()!=null && theForm.getPriceItems().size()>0) { %>
         <html:submit property="action" value="Clear Selections"/>
         <html:submit property="action" value="Save"/>
</td></tr><tr><td> <b>Price:&nbsp;</b>
</td><td colspan=3><html:text size="8" name="ACCOUNT_ITEM_PRICE_FORM" property="priceMulty"/>
    <html:submit property="action" value="Set Price For Selected"/>
    <% if(theForm.isOnlyOneItem()) { %>
      <html:submit property="action" value="Set Price For All"/>
    <% } %>
</td></tr><tr><td> <b>Distributor Cost:&nbsp;</b>
</td><td colspan=3><html:text size="8" name="ACCOUNT_ITEM_PRICE_FORM" property="costMulty"/>
    <html:submit property="action" value="Set Distributor Cost For Selected"/>
    <% if(theForm.isOnlyOneItem()) { %>
      <html:submit property="action" value="Set Distributor Cost For All"/>
    <% } %>
</td></tr><tr><td> <b>Customer Sku:</b>
</td><td colspan=3><html:text size="16" name="ACCOUNT_ITEM_PRICE_FORM" property="newCustSku"/>
    <html:submit property="action" value="Set Cust Sku For Selected"/>
    <% if(theForm.isOnlyOneItem()) { %>
      <html:submit property="action" value="Set Cust Sku For All"/>
    <% } %>
</td></tr><tr><td> <b>Customer Desc:</b>
</td><td colspan=3><html:text size="30" name="ACCOUNT_ITEM_PRICE_FORM" property="newCustDesc"/>
    <html:submit property="action" value="Set Cust Desc For Selected"/>
    <% if(theForm.isOnlyOneItem()) { %>
      <html:submit property="action" value="Set Cust Desc For All" />
    <% } %>

  <% } %>
</td>  </tr>

</table>
</td></tr>
</table>
<% if(theForm.getPriceItems()!=null) { %>
  <table cellpadding="4" cellspacing="0" width="100%" border="0" class="results">
  <tr>
  <td colspan="13"><b>List Size:</b>
  <bean:size id="itemCount" name="ACCOUNT_ITEM_PRICE_FORM" property="priceItems" />
  <bean:write name="itemCount" />
  </td>
  </tr>
  <tr bgcolor="#cccccc" >
  <html:hidden name="ACCOUNT_ITEM_PRICE_FORM" property="sortField"/>

  <td><a class="tableheader" href="javascript:  document.forms[0].elements['sortField'].value='itemSku'; document.forms[0].submit();">CW.Sku</a>&nbsp;
<br>
  <a class="tableheader" href="javascript:  document.forms[0].elements['sortField'].value='itemCustSku'; document.forms[0].submit();">Cust.Sku</a>&nbsp;</td>
 
 <td><a class="tableheader" href="javascript:  document.forms[0].elements['sortField'].value='itemDesc'; document.forms[0].submit();">Name</a>
  <br><a class="tableheader" href="javascript:  document.forms[0].elements['sortField'].value='itemCustDesc'; document.forms[0].submit();">Cust.Desc</a>&nbsp;</td>
 <td class="tableheader" ><a class="tableheader" href="javascript:  document.forms[0].elements['sortField'].value='distCost'; document.forms[0].submit();">Dist Cost</a></td>

  <td class="tableheader" ><a class="tableheader" href="javascript:  document.forms[0].elements['sortField'].value='price'; document.forms[0].submit();">Price</a></td>
  <td class="tableheader">Select<br>
<a href="javascript:SetChecked(1)">[Check&nbsp;All]</a>
&nbsp;<a href="javascript:SetChecked(0)">[Clear]</a>
</td>
 
  <td><a class="tableheader" href="javascript:  document.forms[0].elements['sortField'].value='itemSize'; document.forms[0].submit();">Size</a>
  <br><a class="tableheader" href="javascript:  document.forms[0].elements['sortField'].value='itemPack'; document.forms[0].submit();">Pack</a>
  <br><a class="tableheader" href="javascript:  document.forms[0].elements['sortField'].value='itemUom'; document.forms[0].submit();">Uom</a>&nbsp;</td>
  <td><a class="tableheader" href="javascript:  document.forms[0].elements['sortField'].value='itemMfgName'; document.forms[0].submit();">Mfg</a>
  <br><a class="tableheader" href="javascript:  document.forms[0].elements['sortField'].value='itemMfgSku'; document.forms[0].submit();">Mfg.Sku</a>&nbsp;</td>
  <td><a class="tableheader" href="javascript:  document.forms[0].elements['sortField'].value='contractName'; document.forms[0].submit();">Contract</a>&nbsp;</td>
  <td><a class="tableheader" href="javascript:  document.forms[0].elements['sortField'].value='DistName'; document.forms[0].submit();">Distributor</a>&nbsp;</td>
  </tr>
  <%
  int itemIdPrev=-1;
  int contractIdPrev=-1;
  int distIdPrev=-1;
  int itemId=0;
  int contractId=0;
  int distId=0;
  int lineNum=0;
  String trClass = "rowa";
  %>
  <logic:iterate id="itemele" indexId="i" name="ACCOUNT_ITEM_PRICE_FORM" property="priceItems" scope="session" type="com.cleanwise.service.api.value.ContractItemPriceView">
  <%
  itemId=itemele.getItemId();
  contractId=itemele.getContractId();
  distId=itemele.getDistId();
  if (( i.intValue() % 2 ) == 0 ) 
  { trClass = "rowb"; } 
  else  { trClass = "rowa"; } 
  %>
    <html:hidden name="ACCOUNT_ITEM_PRICE_FORM" property="<%=\"inputId[\"+i+\"]\"%>" value="<%=itemId+\"#\"+contractId%>"/>
 
  <tr class="<%=trClass%>" valign="bottom" >
 
    <!-- <%=itemele.getItemId()%> -->
    <td><%=itemele.getItemSku()%>
    <br>
    <html:text size="15" maxlength="255" name="ACCOUNT_ITEM_PRICE_FORM" property="<%=\"custSku[\"+i+\"]\"%>"/></td>
    <td><%=itemele.getItemDesc()%>
    <br><html:text size="30" maxlength="255" name="ACCOUNT_ITEM_PRICE_FORM" property="<%=\"custDesc[\"+i+\"]\"%>"/></td>
    <td>
    <html:text size="6" name="ACCOUNT_ITEM_PRICE_FORM" property="<%=\"distCost[\"+i+\"]\"%>"/>
    </td>
    <td>
    <html:text size="6" name="ACCOUNT_ITEM_PRICE_FORM" property="<%=\"price[\"+i+\"]\"%>"/>
    </td>
    <td>
     <html:multibox name="ACCOUNT_ITEM_PRICE_FORM" property="selectorBox" value="<%=itemId+\"#\"+contractId%>"/>
    </td>

<td width="90">
<table>
<tr bgcolor="#cccccc" ><td>    <%=itemele.getItemSize()%> </td></tr>
<tr bgcolor="#ffffff" ><td>    <%=itemele.getItemPack()%> </td></tr>
<tr bgcolor="#cccccc" ><td>    <%=itemele.getItemUom() %> </td></tr>
</table>
</td>

<td>
<table>
<tr bgcolor="#cccccc" ><td><%=itemele.getItemMfgName()%> </td></tr>
<tr bgcolor="#ffffff" ><td><%=itemele.getItemMfgSku()%> </td></tr>
</table>
</td>
 
    <td><%=itemele.getContractName()%>(<%=itemele.getContractId()%>)&nbsp;</td>
    <td><%=itemele.getDistName()%>&nbsp;</td>
    </tr>
 
  <%
  itemIdPrev=itemId;
  contractIdPrev=contractId;
  distIdPrev=distId;
  lineNum++;
 %>
  </logic:iterate>
  <tr><td align=center colspan=13>
</td></tr>
</table>
<!--
</td></tr>
-->
<% } %>


</html:form>
<!--
</table>
-->
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

