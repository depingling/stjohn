<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.*" %>
<%@page import="com.cleanwise.service.api.util.RefCodeNames"%>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<% boolean clwSwitch=ClwCustomizer.getClwSwitch(); %>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<script language="JavaScript1.2">
function resetFields(m_days, m_mm, m_yr) {
  now =  new Date();
  n_mm = now.getMonth() + m_mm;
  if ( n_mm < 0 ) {
    n_mm = n_mm + 11;
  }
  n_mm = n_mm + 1;
  fdate = new Date( now.getFullYear() + m_yr, n_mm,  now.getDate() + m_days );
  tstring = fdate.getMonth() + '/' + fdate.getDate() + '/' + fdate.getFullYear();
  ele = document.STORE_ORDER_SEARCH_FORM.elements;
  for ( i = 0; i < ele.length; i++ ) {
    if ( ele[i].type == "text") {
      if (ele[i].name != "orderDateRangeBegin" ) {
        ele[i].value = "";
      } else {
        ele[i].value = tstring;
      }
    }
  }
}

// submit if Enter key was applyed.
document.onkeydown = isEnter;
if (document.layers){
  document.captureEvents(Event.KEYDOWN);
}

function isEnter() {
  var characterCode;
  if(event && event.which){
    characterCode = e.which;
  }
  else{
    characterCode = event.keyCode;
  }
  if(characterCode == 13){
    doSubmit("search");
  }
}

function doSubmit(action) {
  document.forms[0].action.value = action;
  document.forms[0].submit();
}

</script>

<jsp:include flush='true' page="locateStoreAccount.jsp">
   <jsp:param name="jspFormAction" 	value="/storeportal/storeOrder.do" />
   <jsp:param name="jspFormName" 	value="STORE_ORDER_SEARCH_FORM" />
   <jsp:param name="jspSubmitIdent" 	value="" />
   <jsp:param name="jspReturnFilterProperty" value="accountFilter"/>
</jsp:include>

<jsp:include flush='true' page="locateStoreDistributor.jsp">
        <jsp:param name="jspFormAction" 	value="/storeportal/storeOrder.do" />
        <jsp:param name="jspFormName" 	value="STORE_ORDER_SEARCH_FORM" />
	<jsp:param name="jspSubmitIdent" 	        value="" />
	<jsp:param name="jspReturnFilterProperty" 	value="distributorFilter" />
</jsp:include>


<jsp:include flush='true' page="locateStoreSite.jsp">
        <jsp:param name="jspFormAction" 	value="/storeportal/storeOrder.do" />
        <jsp:param name="jspFormName" 	value="STORE_ORDER_SEARCH_FORM" />
	<jsp:param name="jspSubmitIdent" 	        value="" />
	<jsp:param name="jspReturnFilterProperty" 	value="siteFilter" />
</jsp:include>

<jsp:include flush='true' page="locateStoreUser.jsp">
        <jsp:param name="jspFormAction" 	value="/storeportal/storeOrder.do" />
        <jsp:param name="jspFormName" 	value="STORE_ORDER_SEARCH_FORM" />
	<jsp:param name="jspSubmitIdent" 	        value="" />
        <jsp:param name="isSingleSelect" 	value="true" />
        <jsp:param name="jspReturnFilterPropertyAlt" value="userDummyConvert"/>
        <jsp:param name="jspReturnFilterProperty" value="userFilter"/>
</jsp:include>


<div class="text">
<!-- 
<font color=red>
<html:errors/>
</font>
-->
<table ID=1044 cellpadding="2" cellspacing="0" border="0" width="769" class="mainbody">
<tr><td>
<html:form styleId="1045" name="STORE_ORDER_SEARCH_FORM" action="/storeportal/storeOrder.do"
    scope="session" type="com.cleanwise.view.forms.StoreOrderSearchForm"
    focus="orderDateRangeBegin">
<table ID=1046 cellpadding="2" cellspacing="0" border="0" width="769" class="mainbody">

  <tr> <td><b>Search Orders:</b></td>
       <td colspan="4" align=right>
<a ID=1047 href="#" onclick="resetFields(0,-6,0);">
<input type="button" value="Reset search fields"></a>
</td>
  </tr>

<tr> <td>&nbsp;</td>

   <td><b>Account Id(s):</b></td>
       <td colspan='3'>
       <html:text size='50' property="accountIdList" />
       <html:button onclick="doSubmit('Locate Account');" style="width: 150px;" property="buttonSubmit" value="Locate Account"/>
        </td>
  </tr>

  <tr> <td>&nbsp;</td>
       <td><b>Distributor Id(s):</b></td>
	   <td colspan="3">
             <html:text size='50' property="distributorIdList" />
             <html:button onclick="doSubmit('Locate Distributor');" style="width: 150px;" property="buttonSubmit" value="Locate Distributor"/>
       </td>
  </tr>

  <tr> <td>&nbsp;</td>
       <td><b>Site Id:</b></td>
	   <td colspan="3">
             <html:hidden property="siteIdList" value=""/>
             <html:text size='50' property="siteId" />
             <html:button onclick="doSubmit('Locate Site');" style="width: 150px;" property="buttonSubmit" value="Locate Site"/>
       </td>
  </tr>


 <bean:define id="theForm" name="STORE_ORDER_SEARCH_FORM" type="com.cleanwise.view.forms.StoreOrderSearchForm"/>
<%
String beginDateString = theForm.getOrderDateRangeBegin();
if ( null == beginDateString || beginDateString.length() == 0 ) {
  GregorianCalendar cal = new GregorianCalendar();
  cal.add(Calendar.MONTH, -6);

  theForm.setOrderDateRangeBegin("" +
    (cal.get(Calendar.MONTH) + 1) + "/"
    + cal.get(Calendar.DAY_OF_MONTH) + "/"
    + cal.get(Calendar.YEAR)
   );
}
%>

  <tr> <td>&nbsp;</td>
       <td><b>Order Date:</b><br>(mm/dd/yyyy)</td>
	   <td colspan="3">
Begin Date Range
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			End Date Range<br>
<html:text property="orderDateRangeBegin" />
<html:text property="orderDateRangeEnd" />
<span class="reqind" valign="top">*</span>
	   </td>
  </tr>

  <% if(clwSwitch) { %>
  <tr> <td>&nbsp;</td>
       <td><b>Outbound PO #:</b></td>
	   <td>
			<html:text name="STORE_ORDER_SEARCH_FORM" property="outboundPoNum" />
       </td>
       <td><b>Customer PO #:</b></td>
	   <td>
			<html:text name="STORE_ORDER_SEARCH_FORM" property="custPONum" />
       </td>
  </tr>
  <tr>
      <td>&nbsp;</td>
      <td><b>ERP PO #:</b></td>
      <td>
          <html:text name="STORE_ORDER_SEARCH_FORM" property="erpPONum" />
      </td>
      <td><b>Web Order # / Confirmation #:</b></td>
      <td>
          <html:text property="webOrderConfirmationNum" />
      </td>
  </tr>
 <% } else { %>
  <tr> <td>&nbsp;</td>
    <td><b>Customer PO #:</b></td>
    <td>
      <html:text property="custPONum" />
    </td>
    <td><b>Web Order # / Confirmation #:</b></td>
    <td>
      <html:text property="webOrderConfirmationNum" />
    </td>
  </tr>
 <% } %>
  <tr> <td>&nbsp;</td>
    <td><b>Site Zip Code:</b></td>
    <td>
      <html:text property="siteZipCode" />
    </td>
    <td><b>Customer Req/Ref Order #:</b></td>
    <td>
       <html:text property="refOrderNum" />
    </td>
  </tr>

  <tr> <td>&nbsp;</td>
    <td><b>Distributor Invoice Num:</b></td>
    <td>
      <html:text property="invoiceDistNum" />
    </td>
 <td><b>Order Status:</b></td>
    <td>
      <html:select property="orderStatus">
        <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
        <html:options  collection="Order.status.vector" property="value" />
      </html:select>
    </td>
  </tr>

  <tr> <td>&nbsp;</td>
    <td><b>Customer Invoice Num:</b></td>
    <td>
      <html:text property="invoiceCustNum" />
    </td>
    <td><b>Method:</b></td>
    <td>
      <html:select property="method">
        <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
        <html:options  collection="Method.type.vector" property="value" />
      </html:select>
    </td>
  </tr>

  <tr> <td>&nbsp;</td>
    <td><b>Placed By:</b></td>
    <td colspan="3">
      <html:text property="placedBy" />
      <html:button onclick="doSubmit('Locate User');" style="width: 150px;" property="buttonSubmit" value="Locate User"/>
    </td>
  </tr>

  <tr>
    <td colspan="5" align="center">
      <html:hidden property="action" value="search"/>
      <html:button onclick="doSubmit('search');" property="buttonSearch" value="Search"/>
    </td>
  </tr>
  <tr><td colspan="5">&nbsp;</td>
  </tr>
</tr></td>
  </table>
</html:form>
</table>

Search results count:&nbsp;<bean:write name="STORE_ORDER_SEARCH_FORM" property="listCount" filter="true"/>

<logic:greaterThan name="STORE_ORDER_SEARCH_FORM" property="listCount" value="0">


<table ID=1048 cellpadding="2" cellspacing="0" border="0" width="769" class="results">
<tr align=left>
<td><a ID=1049 href="storeOrder.do?action=sort&sortField=acctname"><b>Acct Name</b></a></td>
<td class="resultscolumna"><a ID=1050 href="storeOrder.do?action=sort&sortField=distname"><b>Dist Name</b></a></td>
<td><a ID=1051 href="storeOrder.do?action=sort&sortField=erpordernum"><b>ERP Order&nbsp;#</b></a></td>
<td class="resultscolumna"><a ID=1052 href="storeOrder.do?action=sort&sortField=webordernum"><b>Web Order&nbsp;#</b></a></td>
<td><a ID=1053 href="storeOrder.do?action=sort&sortField=orderdateRevision"><b>Order Date</b></a></td>
<td class="resultscolumna"><b>Cust PO</b></td>
<td><a ID=1054 href="storeOrder.do?action=sort&sortField=sitename"><b>Site Name</b></a></td>
<td class="resultscolumna"><b>Address</b></td>
<td><b>City</b></td>
<td class="resultscolumna"><b>State</b></td>
<td><a ID=1055 href="storeOrder.do?action=sort&sortField=zipcode"><b>Zip Code</b></a></td>
<td class="resultscolumna"><a ID=1056 href="storeOrder.do?action=sort&sortField=status"><b>Status</b></a></td>
<td><a ID=1057 href="storeOrder.do?action=sort&sortField=method"><b>Method</b></a></td>
<td class="resultscolumna"><a ID=1058 href="storeOrder.do?action=sort&sortField=placedby"><b>Placed&nbsp;By</b></a></td>
</tr>

 <bean:define id="pagesize" name="STORE_ORDER_SEARCH_FORM" property="listCount"/>

<logic:iterate id="order" name="STORE_ORDER_SEARCH_FORM" property="resultList"
     offset="0" length="<%=pagesize.toString()%>" type="com.cleanwise.service.api.value.OrderStatusDescData">
 <bean:define id="key"  name="order" property="orderDetail.orderId"/>
 <% String linkHref = new String("storeOrderDetail.do?action=view&id=" + key);
    boolean canOpen = RefCodeNames.ORDER_STATUS_CD.RECEIVED.equals(order.getOrderDetail().getOrderStatusCd()) == false; %>

 <tr><td colspan="14"><hr></td></tr>
 <tr>
  <td><bean:write name="order" property="accountName"/></td>
  <td class="resultscolumna"><bean:write name="order" property="distName"/></td>
  <td><bean:write name="order" property="orderDetail.erpOrderNum"/></td>
  <td class="resultscolumna"><%if(canOpen){%><a ID=1059 href="<%=linkHref%>"><%}%><bean:write name="order" property="orderDetail.orderNum"/><%if(canOpen){%></a><%}%></td>
  <td>
  	<logic:present name="order" property="orderDetail.revisedOrderDate">
          <bean:define id="revOrderDate"  name="order" property="orderDetail.revisedOrderDate"/>
          <i18n:formatDate value="<%=revOrderDate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
	</logic:present>
  	<logic:notPresent name="order" property="orderDetail.revisedOrderDate">
          <logic:present name="order" property="orderDetail.originalOrderDate">
            <bean:define id="orderDate"  name="order" property="orderDetail.originalOrderDate"/>
            <i18n:formatDate value="<%=orderDate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
          </logic:present>
	</logic:notPresent>
  </td>

  <td class="resultscolumna"><bean:write name="order" property="orderDetail.requestPoNum"/></td>
  <td><bean:write name="order" property="orderDetail.orderSiteName"/></td>
  <td class="resultscolumna"><bean:write name="order" property="shipTo.address1"/>&nbsp;</td>
  <td><bean:write name="order" property="shipTo.city"/>&nbsp;</td>
  <td class="resultscolumna"><bean:write name="order" property="shipTo.stateProvinceCd"/>&nbsp;</td>
  <td><bean:write name="order" property="shipTo.postalCode"/>&nbsp;</td>
  <td class="resultscolumna"><bean:write name="order" property="orderDetail.orderStatusCd"/></td>
  <td><bean:write name="order" property="orderDetail.orderSourceCd"/></td>
  <td class="resultscolumna"><bean:write name="order" property="orderDetail.addBy"/></td>
 </tr>

 </logic:iterate>

</table>
</logic:greaterThan>

</div>




