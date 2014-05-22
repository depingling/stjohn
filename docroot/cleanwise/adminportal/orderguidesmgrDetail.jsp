
<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Locale" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<app:checkLogon/>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="Location" value="orderguide" type="java.lang.String" toScope="session"/>
<bean:define id="catalogkey" name="ORDER_GUIDES_DETAIL_FORM" property="orderGuideInfoData.orderGuideData.catalogId"/>

<html:html>

<head>
<link rel="stylesheet" href="../externals/styles.css">
<title>Application Administrator Home: Order Guides</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
</head>

<script language="JavaScript1.2">
<!--
function popLocate(pLoc, name, pDesc) {
  var loc = pLoc + ".do?reload=viewbycontract&catalogId=<%=catalogkey%>&feedField=" + name + "&amp;feedDesc=" + pDesc;
  locatewin = window.open(loc,"Locate", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();
  return false;
}

function SetChecked(val) {
 dml=document.forms["ORDER_GUIDES_DETAIL_FORM"];
 len = dml.elements.length;
 var i=0;
//alert('num of elements:' + len);
 for( i=0 ; i<len ; i++) {
   if (dml.elements[i].name == "selectItems") {
     dml.elements[i].checked=val;
   }
 }
}

//-->
</script>

<body>

<jsp:include flush='true' page="ui/admToolbar.jsp"/>
<jsp:include flush='true' page="ui/orderguidesToolbar.jsp"/>
<jsp:include flush='true' page="ui/loginInfo.jsp"/>

<div class="text">
<center>
<font color=red>
<html:errors/>
</font>
</center>

<table width="769" cellpadding="2" cellspacing="0" border="0" class="mainbody">
<html:form
name="ORDER_GUIDES_DETAIL_FORM"
scope="session"
action="adminportal/orderguidesmgrDetail.do"
type="com.cleanwise.view.forms.OrderGuidesMgrDetailForm">

<tr>
<td colspan="4" class="largeheader">Order Guide Detail</td>

</tr>
<tr>
<td><b>Order Guide&nbsp;Id:</b> </td>
<td>
<bean:write name="ORDER_GUIDES_DETAIL_FORM"
property="orderGuideInfoData.orderGuideData.orderGuideId"
/>
<html:hidden name="ORDER_GUIDES_DETAIL_FORM"
property="orderGuideInfoData.orderGuideData.orderGuideId"/>
</td>
<td><b>Catalog&nbsp;Id:</b></td>
<td>
<bean:write
name="ORDER_GUIDES_DETAIL_FORM"
property="orderGuideInfoData.orderGuideData.catalogId"
/>
</td>

</tr>
<tr>
 <td><b>Order Guide Name:</b> </td>
<td>
<html:text name="ORDER_GUIDES_DETAIL_FORM"
property="orderGuideInfoData.orderGuideData.shortDesc"
size="30" maxlength="30"/>
</td>
<td><b>Catalog&nbsp;Name:</b></td>
<td>
<bean:write
name="ORDER_GUIDES_DETAIL_FORM"
property="catalogName"
/>
</td>
</tr>

<tr>
<td><b>Order Guide&nbsp;Type:</b></td>
<td>
<bean:write name="ORDER_GUIDES_DETAIL_FORM"
property="orderGuideInfoData.orderGuideData.orderGuideTypeCd"/>
</td>
<td colspan="2">&nbsp;</td>
</tr>

<tr>
    <td colspan="4" align="center">
      <html:submit property="action">
        <app:storeMessage  key="global.action.label.save"/>
      </html:submit>
      <html:reset>
        <app:storeMessage  key="admin.button.reset"/>
      </html:reset>
      <html:submit property="action">
        <app:storeMessage  key="global.action.label.delete"/>
      </html:submit>
    </td>
</tr>
<tr>
    <td colspan="4" align="center">
        <html:hidden name="ORDER_GUIDES_DETAIL_FORM" property="viewMode"/>

        <logic:notEqual name="ORDER_GUIDES_DETAIL_FORM" property="viewMode" value="viewbycatalog">
                <html:button property="action" onclick="document.forms[0].viewMode.value='viewbycatalog'; document.forms[0].submit();" >
                        <app:storeMessage  key="admin.button.viewbycatalog"/>
                </html:button>
        </logic:notEqual>

        <html:button property="action" onclick="popLocate('contractlocate', 'contractId', 'contractName'); document.forms[0].viewMode.value='viewbycontract';">
                <app:storeMessage  key="admin.button.viewbycontract"/>
        </html:button>

        <logic:notEqual name="ORDER_GUIDES_DETAIL_FORM" property="viewMode" value="viewbylist">
                <html:button property="action" onclick="document.forms[0].viewMode.value='viewbylist'; document.forms[0].submit();">
                        <app:storeMessage  key="admin.button.viewbylist"/>
                </html:button>
        </logic:notEqual>

        <html:hidden name="ORDER_GUIDES_DETAIL_FORM" property="contractId" />
        <html:hidden name="ORDER_GUIDES_DETAIL_FORM" property="contractName" />
    </td>
</tr>

</table>



<table width="769" border="0" class="results">
<logic:equal name="ORDER_GUIDES_DETAIL_FORM" property="viewMode" value="viewbycatalog">
        <logic:equal name="ORDER_GUIDES_DETAIL_FORM" property="usingCatalogsContract" value="false">
        <tr><td colspan="13">
                <center><font color="red">
                Could not find one distinct contract for this catalog, using list price.
                </font></center>
        </td></tr>
        </logic:equal>
</logic:equal>

<logic:equal name="ORDER_GUIDES_DETAIL_FORM" property="viewMode" value="viewbycontract">
        <tr><td colspan="13">View Through Contract:
                &nbsp;<bean:write name="ORDER_GUIDES_DETAIL_FORM" property="contractName" filter="true" />
        </td></tr>
</logic:equal>

<tr>
<td colspan="7"><b>Order Guide Entries: </b>
<bean:size id="ogsCount"
name="ORDER_GUIDES_DETAIL_FORM"
property="orderGuideInfoData.orderGuideItems"
/>
<bean:write name="ogsCount" />
</td>
<td colspan="6" align="right"><b>Total Amount: </b>
<bean:write name="ORDER_GUIDES_DETAIL_FORM" property="orderGuideInfoData.totalAmount" />
</td>
</tr>

<%String priceHeader="MSRP";%>
<logic:equal name="ORDER_GUIDES_DETAIL_FORM" property="viewMode" value="viewbycatalog">
        <logic:equal name="ORDER_GUIDES_DETAIL_FORM" property="usingCatalogsContract" value="true">
                <%priceHeader="Contract Price";%>
        </logic:equal>
</logic:equal>
<logic:equal name="ORDER_GUIDES_DETAIL_FORM" property="viewMode" value="viewbycontract">
        <%priceHeader="Contract Price";%>
</logic:equal>

<tr>
<td><a class="tableheader" href="orderguidesmgrDetail.do?action=sortitems&sortField=quantity">Quantity</td>
<td><a class="tableheader" href="orderguidesmgrDetail.do?action=sortitems&sortField=amount">Extended Price</td>
<td><a class="tableheader" href="orderguidesmgrDetail.do?action=sortitems&sortField=cwSKU">SKU</td>
<td><a class="tableheader" href="orderguidesmgrDetail.do?action=sortitems&sortField=name">Product&nbsp;Name</td>
<td><a class="tableheader" href="orderguidesmgrDetail.do?action=sortitems&sortField=size">Item&nbsp;Size</td>
<td><a class="tableheader" href="orderguidesmgrDetail.do?action=sortitems&sortField=pack">Pack</td>
<td><a class="tableheader" href="orderguidesmgrDetail.do?action=sortitems&sortField=uom">UOM</td>
<!--<td><a class="tableheader" href="orderguidesmgrDetail.do?action=sortitems&sortField=color">Color</td>-->
<td><a class="tableheader" href="orderguidesmgrDetail.do?action=sortitems&sortField=manufacturer">Mfg.</td>
<td><a class="tableheader" href="orderguidesmgrDetail.do?action=sortitems&sortField=manufSKU"></b>Mfg.&nbsp;SKU</td>
<td><a class="tableheader" href="orderguidesmgrDetail.do?action=sortitems&sortField=category">Category</td>
<td><a class="tableheader" href="orderguidesmgrDetail.do?action=sortitems&sortField=price"><%=priceHeader%></td>


<td class="tableheader">
<a href="javascript:SetChecked(1);">[Check&nbsp;All]</a>
<a href="javascript:SetChecked(0);">[Clear]</a>
<br>
Select</td>
</tr>

<logic:iterate id="itemele"
indexId="i"
name="ORDER_GUIDES_DETAIL_FORM"
property="orderGuideItemCollection"
scope="session">

<%
String lrc;
if ( ( i.intValue() % 2 ) == 0 ) {
  lrc = "rowa";
}
else {
  lrc = "rowb";
}
%>

<tr class="<%=lrc%>">


<td>

<html:hidden
name="ORDER_GUIDES_DETAIL_FORM"
property='<%= "orderGuideItemDesc[" + i + "].orderGuideStructureId" %>'/>

<html:text size="3" maxlength="6"
name="ORDER_GUIDES_DETAIL_FORM"
property='<%= "orderGuideItemDesc[" + i + "].quantity" %>'/>
</td>

<td><bean:write name="itemele" property="amount"/></td>
<td><bean:write name="itemele" property="cwSKU"/>&nbsp;</td>
<td><bean:write name="itemele" property="shortDesc"/>&nbsp;</td>
<td><bean:write name="itemele" property="sizeDesc"/>&nbsp;</td>
<td><bean:write name="itemele" property="packDesc"/>&nbsp;</td>
<td><bean:write name="itemele" property="uomDesc"/>&nbsp;</td>
<!--
<td><bean:write name="itemele" property="colorDesc"/>&nbsp;</td>
-->
<td><bean:write name="itemele" property="manufacturerCd"/>&nbsp;</td>
<td><bean:write name="itemele" property="manufacturerSKU"/>&nbsp;</td>
<td><bean:write name="itemele" property="categoryDesc"/>&nbsp;</td>
<bean:define id="price" name="itemele" property="price"/>
<td><i18n:formatCurrency value="<%=price%>" locale="<%=Locale.US%>"/></td>

<td>
<html:multibox property="selectItems">
  <bean:write name="itemele" property="orderGuideStructureId"/>
</html:multibox>
</td>
</tr>

</logic:iterate>
<tr>
<td align=center colspan=13>
      <html:submit property="action">
        <app:storeMessage  key="admin.button.updateQty"/>
      </html:submit>
      <html:submit property="action">
        <app:storeMessage  key="admin.button.findItems"/>
      </html:submit>
      <html:submit property="action">
        <app:storeMessage  key="admin.button.remove"/>
      </html:submit>
</td>
</tr>

</html:form>

</table>

</div>

<jsp:include flush='true' page="ui/admFooter.jsp"/>

</body>

</html:html>






