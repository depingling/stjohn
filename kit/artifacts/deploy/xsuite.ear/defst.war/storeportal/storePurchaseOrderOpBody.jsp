<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<script language="JavaScript1.2">
<!--
function popLocate(pLoc, name, pDesc) {
  var loc = pLoc + ".do?feedField=" + name + "&amp;feedDesc=" + pDesc;
  locatewin = window.open(loc,"Locate", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();
  return false;
}

function SetChecked(val) {
 dml=document.forms[1];
 len = dml.elements.length;
 var i=0;
 for( i=0 ; i<len ; i++) {
   if (dml.elements[i].name=='selectorBox') {
     dml.elements[i].checked=val;
   }
 }
}

function f_res(pFormId) {
 
 var dml = document.getElementById(pFormId);
 if ( null == dml ) {
   alert('no form found for pFormId=' + pFormId);
   return;
 }

 len = dml.elements.length;
 var i=0;
 for( i=0 ; i<len ; i++) {
   if (dml.elements[i].type == "text" ) {
     dml.elements[i].value = "";
   }
 }
}

function doSubmit(action) {
    document.forms[0].action.value = action;
    document.forms[0].submit();
}
//-->
</script>

<div class="text">

<jsp:include flush='true' page="locateStoreAccount.jsp">
   <jsp:param name="jspFormAction"           value="/storeportal/storePurchaseOrderMgr.do" />
   <jsp:param name="jspFormName"             value="PO_OP_SEARCH_FORM" />
   <jsp:param name="jspSubmitIdent"          value="" />
   <jsp:param name="jspReturnFilterProperty" value="accountFilter"/>
</jsp:include>

<jsp:include flush='true' page="locateStoreDistributor.jsp">
    <jsp:param name="jspFormAction"           value="/storeportal/storePurchaseOrderMgr.do" />
    <jsp:param name="jspFormName"             value="PO_OP_SEARCH_FORM" />
    <jsp:param name="jspSubmitIdent"          value="" />
    <jsp:param name="jspReturnFilterProperty" value="distributorFilter" />
</jsp:include>

<jsp:include flush='true' page="locateStoreSite.jsp">
    <jsp:param name="jspFormAction"           value="/storeportal/storePurchaseOrderMgr.do" />
    <jsp:param name="jspFormName"             value="PO_OP_SEARCH_FORM" />
    <jsp:param name="jspSubmitIdent"          value="" />
    <jsp:param name="jspReturnFilterProperty" value="siteFilter" />
</jsp:include>

<table cellpadding="2" cellspacing="0" border="0" width="769" class="mainbody">
<%
String currentURI = "/storeportal/storePurchaseOrderMgr.do";
String fullCurrentURI = request.getContextPath() + currentURI;
String detatilAction = (String) request.getAttribute("detailAction");
%>
    <html:form 
        name="PO_OP_SEARCH_FORM" 
        styleId="poSearchForm"
        action="<%= currentURI %>" 
        scope="session" 
        type="com.cleanwise.view.forms.PurchseOrderOpSearchForm"
        onsubmit="doSubmit('Search');">
    <html:hidden property="action" value="search"/>
    <tr> 
        <td><b>Search:</b></td>
        <td colspan="4" align=right>
            <a href="#" onclick="f_res('poSearchForm');"><input type="button" value="Reset search fields"></a> 
        </td>
    </tr>
    <tr> 
        <td>&nbsp;</td>
        <td><b>Distributor Id:</b></td>
        <td colspan="3">
            <html:text name="PO_OP_SEARCH_FORM" property="distributorId" />
            <html:button onclick="doSubmit('Locate Distributor');" style="width: 150px;" property="buttonSubmit" value="Locate Distributor"/>
        </td>
    </tr>
    <tr> 
        <td>&nbsp;</td>
        <td><b>Site Id:</b></td>
        <td colspan="3">
            <html:text name="PO_OP_SEARCH_FORM" property="siteId" />
            <html:button onclick="doSubmit('Locate Site');" style="width: 150px;" property="buttonSubmit" value="Locate Site"/>
        </td>
    </tr>
    <tr> 
        <td>&nbsp;</td>
        <td><b>Account Id(s):</b></td>
        <td colspan='3'>
            <html:text name="PO_OP_SEARCH_FORM" size='50' property="accountIdList" />
            <html:button onclick="doSubmit('Locate Account');" style="width: 150px;" property="buttonSubmit" value="Locate Account"/>
        </td>
    </tr>

<style type="text/css">
tr.d0 td {
	background-color: #EAEAEA; color: black;
}
</style>

  <tr class="d0"> <td>&nbsp;</td>
       <td><b>Outbound PO Date:</b><br>(mm/dd/yyyy)</td>
           <td colspan="3">

<table>
<tr><td> Begin Date Range</td><td> End Date Range</td></tr>
<tr><td>
 <html:text name="PO_OP_SEARCH_FORM" property="poDateRangeBegin" />
</td><td>
 <html:text name="PO_OP_SEARCH_FORM" property="poDateRangeEnd" />
</td></tr>
</table> 
          </td>
<td colspan=2> </td>
  </tr>

<tr class="d0"> <td>&nbsp;</td> <td><b>Outbound PO #:</b></td>           
<td>
<html:text name="PO_OP_SEARCH_FORM" property="outboundPoNum" />
</td>
<td><b>Web Order # / Confirmation #:</b></td>
<td>
<html:text name="PO_OP_SEARCH_FORM" property="webOrderConfirmationNum" />
</td>
<td colspan=2> </td>
</tr>


  <tr> <td>&nbsp;</td>
       <td><b>ERP Order #:</b></td>
           <td>
                        <html:text name="PO_OP_SEARCH_FORM" property="erpOrderNum" />
       </td>
  </tr>

<tr><td>&nbsp;</td>

<td><b>ERP PO #:</b></td>           <td>
<html:text name="PO_OP_SEARCH_FORM" property="erpPORefNum" />
</td>

<td><b>Distributor Invoice Num:</b></td>       <td>
<html:text name="PO_OP_SEARCH_FORM" property="invoiceDistNum" />
       </td>  </tr>

  <tr> <td>&nbsp;</td>
       <td><b>Site Data:</b></td>
       <td>
                <html:text name="PO_OP_SEARCH_FORM" property="siteData" />
       </td>
       <td><b>Target Facility Rank:</b></td>
       <td>
                <html:text name="PO_OP_SEARCH_FORM" property="targetFacilityRank" />
       </td>
  </tr>

  <tr> <td>&nbsp;</td>
       <td><b>Purchase Order Status:</b></td>
           <td colspan="3">
            <html:select name="PO_OP_SEARCH_FORM" property="purchaseOrderStatus">
                                <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
                                <html:options  collection="PurchaseOrder.status.vector" property="value" />
            </html:select>
       </td>
       <td></td><td></td>
  </tr>
   <tr>
        <td>&nbsp;</td>
        <td><b>Orig Customer PO Number:</b></td><td><html:text name="PO_OP_SEARCH_FORM" property="orderRequestPoNum" /></td>
        <td></td><td></td>
  </tr>
  <tr>
        <td>&nbsp;</td>
        <td><b>Return Number:</b></td>
        <td>
                <html:text name="PO_OP_SEARCH_FORM" property="returnRequestRefNum" />
        </td>
        <td><b>Distributor Return Number:</b></td>
        <td>
                <html:text name="PO_OP_SEARCH_FORM" property="distributorReturnRequestNum" />
        </td>
  </tr>
  <tr>
       <td>&nbsp;</td>
       <td><b>Due Items:</b>&nbsp;
            <html:select name="PO_OP_SEARCH_FORM" property="itemStatus">
                                <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
                                <html:options  collection="OrderProperty.ShipStatus.vector" property="value" />
                        </html:select>
       </td>
       <td><b>Due Items Date</b></td>
       <td colspan="3">
            Begin Date Range
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        End Date Range<br>
           <html:text name="PO_OP_SEARCH_FORM" property="itemTargetShipDateBeginString" />
                   <html:text name="PO_OP_SEARCH_FORM" property="itemTargetShipDateEndString" />
       </td>
  </tr>

  <tr>
       <td colspan="5" align="center">
       <html:submit onclick="doSubmit('Search');" property="buttonSearch" value="Search"/>
     </td>
  </tr>
  <tr><td colspan="5">&nbsp;</td>
  </tr>
</html:form>

<script type="text/javascript" language="JavaScript">
    <!--
    var formWithControl = document.getElementById("poSearchForm");
    if (formWithControl != null) {
        var focusControl = formWithControl.elements["distributorId"];
        if (focusControl.type != "hidden" && !focusControl.disabled) {
            focusControl.focus();
        }
    }
    // -->
</script>

<!--old form end tag-->
</table>

Search results count:&nbsp;<bean:write name="PO_OP_SEARCH_FORM" property="listCount" filter="true"/>

<logic:greaterThan name="PO_OP_SEARCH_FORM" property="listCount" value="0">


<!--deal with the result set-->



<table cellpadding="2" cellspacing="0" border="0" width="769" class="results">
<%if(request.getAttribute("mode").equals("po")){%>
        <tr>
            <td align=right colspan="14"><a href="javascript:SetChecked(1)">[Check&nbsp;All]</a>
            <a href="javascript:SetChecked(0)">[&nbsp;Clear]</a></td>
        </tr>
<%}%>
<tr align=left>
<td><a href="<%= fullCurrentURI %>?action=sort&sortField=distributorname"><b>Dist Name</b></a></td>
<td class="resultscolumna"><a href="<%= fullCurrentURI %>?action=sort&sortField=accountname"><b>Acct Name</b></a></td>

<td><a href="<%= fullCurrentURI %>?action=sort&sortField=erpordernum">
  <b>ERP Order #</b></a></td>

<td class="resultscolumna"><a href="<%= fullCurrentURI %>?action=sort&sortField=webordernum"><b>Web Order&nbsp;#</b></a></td>
<td><a href="<%= fullCurrentURI %>?action=sort&sortField=podate">
  <b>Outbound PO Date</b></a></td>
<td class="resultscolumna">
<a href="<%= fullCurrentURI %>?action=sort&sortField=erpponum">
  <b>Outbound PO #</b></a></td>
<td><a href="<%= fullCurrentURI %>?action=sort&sortField=sitename"><b>Site Name</b></a></td>
<td class="resultscolumna"><b>Address</b></td>
<td><b>City</b></td>
<td class="resultscolumna"><b>State</b></td>
<td><a href="<%= fullCurrentURI %>?action=sort&sortField=zipcode"><b>Zip Code</b></a></td>
<td class="resultscolumna"><a href="<%= fullCurrentURI %>?action=sort&sortField=status"><b>Status</b></a></td>
<td><a href="<%= fullCurrentURI %>?action=sort&sortField=method"><b>Method</b></a></td>
<%if(request.getAttribute("mode").equals("po")){%>
        <td class="resultscolumna"><b>Select</b></a></td>
<%}%>
</tr>

 <bean:define id="pagesize" name="PO_OP_SEARCH_FORM" property="listCount"/>



<html:form name="PO_OP_SEARCH_FORM" action="<%= currentURI %>"
  styleId="mainSearchForm"
    scope="session" type="com.cleanwise.view.forms.PurchseOrderOpSearchForm">
    <html:hidden property="action" value="updatepostatus"/>

<logic:iterate id="po" name="PO_OP_SEARCH_FORM" property="resultList"
     offset="0" length="<%=pagesize.toString()%>" indexId="i" type="com.cleanwise.service.api.value.PurchaseOrderStatusDescDataView">
 <bean:define id="key"  name="po" property="purchaseOrderData.purchaseOrderId"/>
 <bean:define id="poDate"  name="po" property="purchaseOrderData.poDate"/>

 <% String linkHref = new String(request.getAttribute("detailURI")+"?action="+detatilAction+"&id=" + key);%>

 <tr><td colspan="13"><hr></td></tr>
 <tr>
  <td><bean:write name="po" property="distributorBusEntityData.shortDesc"/></td>
  <td class="resultscolumna"><bean:write name="po" property="accountBusEntityData.shortDesc"/></td>
  <td><bean:write name="po" property="orderData.erpOrderNum"/></td>

  <td class="resultscolumna"><bean:write name="po" property="orderData.orderNum"/></td>

  <td>
        <logic:present name="po" property="purchaseOrderData.poDate">
          <i18n:formatDate value="<%=poDate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
        </logic:present>
  </td>
<%--  <td class="resultscolumna"><a href="<%=linkHref%>"><bean:write name="po" property="purchaseOrderData.erpPoNum"/></a></td>--%>
<td class="resultscolumna"><a href="<%=linkHref%>"><bean:write name="po" property="purchaseOrderData.outboundPoNum"/></a></td>
  <td><bean:write name="po" property="shipToAddress.shortDesc"/></td>
  <td class="resultscolumna"><bean:write name="po" property="shipToAddress.address1"/></td>
  <td><bean:write name="po" property="shipToAddress.city"/></td>
  <td class="resultscolumna"><bean:write name="po" property="shipToAddress.stateProvinceCd"/></td>
  <td><bean:write name="po" property="shipToAddress.postalCode"/></td>
  <td class="resultscolumna"><bean:write name="po" property="purchaseOrderData.purchaseOrderStatusCd"/></td>
  <td><bean:write name="po" property="orderData.orderSourceCd"/></td>
  <%if(request.getAttribute("mode").equals("po")){%>
          <td class="resultscolumna">
            <logic:equal name="po" property="purchaseOrderData.purchaseOrderStatusCd" value="<%=RefCodeNames.PURCHASE_ORDER_STATUS_CD.SENT_TO_DISTRIBUTOR%>">
                <html:multibox name="PO_OP_SEARCH_FORM" property="selectorBox" value="<%=key.toString()%>"/>
            </logic:equal>
            <logic:notEqual name="po" property="purchaseOrderData.purchaseOrderStatusCd" value="<%=RefCodeNames.PURCHASE_ORDER_STATUS_CD.SENT_TO_DISTRIBUTOR%>">
                &nbsp;
            </logic:notEqual>
          </td>
  <%}%>
  <%if(request.getAttribute("mode").equals("return")){%>
  <logic:iterate id="ret" name="po" property="returnRequestDataVector" type="com.cleanwise.service.api.value.ReturnRequestData">
  <% linkHref = "storeReturnsOpDetail.do?action=view&returnReqId=" + ret.getReturnRequestId();%>
        <tr>
                <td>&nbsp;</td><td>&nbsp;</td>
                <td><bean:write name="ret" property="returnRequestId"/></td>
                <td><a href="<%=linkHref%>"><bean:write name="ret" property="returnRequestRefNum"/></a></td>
                <td><bean:write name="ret" property="distributorRefNum"/></td>
                <td><bean:write name="ret" property="senderContactName"/></td>
                <td><bean:write name="ret" property="reason"/></td>
                <td><bean:write name="ret" property="returnRequestStatus"/></td>
        </tr>
  </logic:iterate>
  <%}%>
 </tr>
 </logic:iterate>

<tr><td>
<%if(request.getAttribute("mode").equals("po")){%>
<html:submit property="action">
   <app:storeMessage  key="global.action.label.save"/>
</html:submit>
<%}%>
</html:form>
</td></tr>



</table>
</logic:greaterThan>

</div>




