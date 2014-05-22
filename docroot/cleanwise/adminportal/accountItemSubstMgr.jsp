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
<bean:define id="theForm" name="ACCOUNT_ITEM_SUBST_FORM" type="com.cleanwise.view.forms.AccountItemSubstMgrForm"/>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<%
	String isMSIE = (String)session.getAttribute("IsMSIE");
	if (null == isMSIE) isMSIE = "";
%>

<html:html>

<head>
<title>Application Administrator Home: Account</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<link rel="stylesheet" href="../externals//styles.css">
</head>


<script language="JavaScript1.2">
<!--
function popLocate(name,itemId) {
  var loc = "itemsubstmgradd.do?feedField=" + name+"&catalogId="+<%=theForm.getAccountCatalogId()%>+"&itemId="+itemId;
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
<jsp:include flush='true' page="ui/admAccountToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>
<jsp:include flush='true' page="ui/accountCtx.jsp"/>

<% if ("Y".equals(isMSIE)) { %>
<iframe style="display:none; position:absolute; z-index:1; width:148; height:194" id="CalFrame"
    marginheight=0 marginwidth=0 noresize frameborder=0 scrolling=no src="../externals/calendar.html"></iframe>
<% }  %>

<div class="text">
<font color=red>
<html:errors/>
</font>

<table border="0" cellpadding="1" cellspacing="0" width="769" class="mainbody">
<html:form name="ACCOUNT_ITEM_SUBST_FORM" action="/adminportal/accountitemsubstmgr.do"
	type="com.cleanwise.view.forms.AccountItemSubstMgrForm">

  <html:hidden name="ACCOUNT_ITEM_SUBST_FORM" property="outServiceName"/>

<tr><td>
  <tr><td>
  <table border="0" width="769" class="results">
  <tr><td colspan="4" class="largeheader">Substitutions</td>
  </tr>
  <tr><td><b>Category:</b></td>
  <td colspan="3"><html:text name="ACCOUNT_ITEM_SUBST_FORM" property="categoryTempl"/>
  </td>
  </tr>
  <tr><td><b>Short Description:<b></td>
      <td><html:text name="ACCOUNT_ITEM_SUBST_FORM" property="shortDescTempl"/></td>
      <td><b>Item Size Description:<b></td>
      <td><html:text name="ACCOUNT_ITEM_SUBST_FORM" property="sizeTempl"/></td>
  </tr>
  <tr><td><b>Long Description:</b></td>
  <td colspan="3"><html:text name="ACCOUNT_ITEM_SUBST_FORM" property="longDescTempl" size="80"/>
  </td>
  </tr>
  <tr><td><b>Manufacturer Id:<b></td>
      <td><html:text name="ACCOUNT_ITEM_SUBST_FORM" property="manuId" size="10"/>
      <html:button onclick="return popManufLocate('manuId','manuName');" value="Locate Manufacturer" property="action"/>
      </td>
     <td><b>Manufacturer Name:<b></td>
     <td>
        <html:text name="ACCOUNT_ITEM_SUBST_FORM" property="manuName" size="30" readonly="true" styleClass="resultslocatename"/>
     </td>
   </tr>
      <tr>
      <td><b>Distributor Id:<b></td>
      <td><html:text name="ACCOUNT_ITEM_SUBST_FORM" property="distributorId" size="10"/>
          <html:button onclick="return popDistLocate('distributorId','distributorName');" value="Locate Distributor" property="action" />
      </td>
      <td><b>Distributor Name:<b></td>
      <td>
          <html:text name="ACCOUNT_ITEM_SUBST_FORM" property="distributorName" size="30" readonly="true" styleClass="resultslocatename"/>
      </td>
  </tr>
  <tr><td><b>Sku:</b></td>
       <td colspan="3">
       <html:text name="ACCOUNT_ITEM_SUBST_FORM" property="skuTempl"/>
       <html:radio name="ACCOUNT_ITEM_SUBST_FORM" property="skuType" value="System"/>
       System
       <html:radio name="ACCOUNT_ITEM_SUBST_FORM" property="skuType" value="Manufacturer"/>
       Manufacturer
       <html:radio name="ACCOUNT_ITEM_SUBST_FORM" property="skuType" value="Distributor"/>
       Distributor
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
       <html:checkbox name="ACCOUNT_ITEM_SUBST_FORM" property="substOnlyFlag"/>Show Substitutions Only
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
         <html:submit property="action" value="Save"/>
         <html:submit property="action" value="Remove Substitutions"/>
         <html:submit property="action" value="Select All"/>
         <html:submit property="action" value="Clear Selections"/>
       <% } %>
    </td>
  </tr>
</table>
</td></tr>
<% if(theForm.getSubstitutions()!=null) { %>
<tr><td>
  <table cellpadding="0" cellspacing="0" width="769" border="0" class="results">
  <tr>
  <td colspan="11"><b>List Size:</b>
  <bean:size id="itemCount" name="ACCOUNT_ITEM_SUBST_FORM" property="substitutions" />
  <bean:write name="itemCount" />
  </td>
  </tr>
  <tr>
  <html:hidden name="ACCOUNT_ITEM_SUBST_FORM" property="sortField"/>
  <td><a class="tableheader" href="javascript:  document.forms[0].elements['sortField'].value='itemId'; document.forms[0].submit();">Id</a></td>
  <td><a class="tableheader" href="javascript:  document.forms[0].elements['sortField'].value='itemSku'; document.forms[0].submit();">Sku</a></td>
  <td><a class="tableheader" href="javascript:  document.forms[0].elements['sortField'].value='itemDesc'; document.forms[0].submit();">Name</a></td>
  <td><a class="tableheader" href="javascript:  document.forms[0].elements['sortField'].value='itemSize'; document.forms[0].submit();">Size</a></td>
  <td><a class="tableheader" href="javascript:  document.forms[0].elements['sortField'].value='itemPack'; document.forms[0].submit();">Pack</a></td>
  <td><a class="tableheader" href="javascript:  document.forms[0].elements['sortField'].value='itemUom'; document.forms[0].submit();">Uom</a></td>
  <td><a class="tableheader" href="javascript:  document.forms[0].elements['sortField'].value='itemMfgName'; document.forms[0].submit();">Mfg</a></td>
  <td><a class="tableheader" href="javascript:  document.forms[0].elements['sortField'].value='itemMfgSku'; document.forms[0].submit();">Mfg&nbsp;Sku</a></td>
  <td class="tableheader">Conversion Factor</td>
  <td class="tableheader">Select</td>
  </tr>
  <%
  int itemIdPrev=-1;
  int substIdPrev=-1;
  int itemId=0;
  int lineNum=0;
  String trClass = "rowa";
  %>
  <logic:iterate id="itemele" indexId="ind" 
     name="ACCOUNT_ITEM_SUBST_FORM" property="substitutions" 
     scope="session" type="com.cleanwise.service.api.value.AccountItemSubstView">
  <%
  itemId=itemele.getItemId();
  int substInd = itemele.getItemSubstitutionDefId();
  String uomConversionFactorEl = "uomConversionFactor["+substInd+"]";
  int substId=itemele.getItemSubstitutionDefId();
  if(itemIdPrev!=itemId) if(trClass.equals("rowb")) trClass="rowa"; else trClass="rowb";
  %>
  <%
  if(itemIdPrev!=itemId && lineNum!=0) {
  %>
<!--
  <tr><td colspan="11" bgcolor="#cccccc"></td></tr>
-->
  <tr><td colspan="11" bgcolor="black"></td></tr>
  <% } %>
  <%
  if(!(itemIdPrev==itemId && substIdPrev!=0 && substId!=0) || lineNum==0) {
  %>
  <tr class="<%=trClass%>">
  <%
  if(itemIdPrev!=itemId || substIdPrev!=0 || lineNum==0) {
  %>
    <td><%=itemele.getItemId()%>&nbsp;</td>
    <td><%=itemele.getItemSku()%>&nbsp;</td>
    <td><%=itemele.getItemDesc()%>&nbsp;</td>
    <td><%=itemele.getItemSize()%>&nbsp;</td>
    <td><%=itemele.getItemPack()%>&nbsp;</td>
    <td><%=itemele.getItemUom()%>&nbsp;</td>
    <td><%=itemele.getItemMfgName()%>&nbsp;</td>
    <td><%=itemele.getItemMfgSku()%>&nbsp;</td>
    <td>&nbsp;</td>
  <% } else { %>
    <td align="center">-/-&nbsp;</td>
    <td align="center">-/-&nbsp;</td>
    <td align="center">-/-&nbsp;</td>
    <td align="left">-/-&nbsp;</td>
    <td align="left">-/-&nbsp;</td>
    <td align="left">-/-&nbsp;</td>
    <td align="left">-/-&nbsp;</td>
    <td align="left">-/-&nbsp;</td>
    <td>&nbsp;</td>
  <% } %>
    <td>
    <% String onclick="return popLocate('outServiceName','"+itemId+"');";%>
<html:submit onclick="<%=onclick%>" property="action" value="Add" styleClass="smalltext"/>
<!--
     <html:multibox name="ACCOUNT_ITEM_SUBST_FORM" property="selectorBox" value="<%=itemId+\"#\"+substId%>"/>
-->
    </td>
    </tr>
  <% } %>
  <% if(substId!=0) { %>
    <tr class="<%=trClass%>">
    <td><font color="darkblue"><%=itemele.getSubstItemId()%></font>&nbsp;</td>
    <td><font color="darkblue"><%=itemele.getSubstItemSku()%></font>&nbsp;</td>
    <td><font color="darkblue"><%=itemele.getSubstItemDesc()%></font>&nbsp;</td>
    <td><font color="darkblue"><%=itemele.getSubstItemSize()%></font>&nbsp;</td>
    <td><font color="darkblue"><%=itemele.getSubstItemPack()%></font>&nbsp;</td>
    <td><font color="darkblue"><%=itemele.getSubstItemUom()%></font>&nbsp;</td>
    <td><font color="darkblue"><%=itemele.getSubstItemMfgName()%></font>&nbsp;</td>
    <td><font color="darkblue"><%=itemele.getSubstItemMfgSku()%></font>&nbsp;</td>
    <td><html:text name="ACCOUNT_ITEM_SUBST_FORM" property="<%=uomConversionFactorEl%>" 
       size="4" styleClass="smalltext"/></td>

    <td>
     <html:multibox name="ACCOUNT_ITEM_SUBST_FORM" property="selectorBox" value="<%=itemId+\"#\"+substId%>"/>
    </td>
    </tr>
 <% } %>
 <%
  itemIdPrev=itemId;
  substIdPrev=substId;
  lineNum++;
 %>
  </logic:iterate>
  <tr><td align=center colspan=11>
</td></tr>
</table>
</td></tr>
<% } %>


</html:form>
</table>
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

