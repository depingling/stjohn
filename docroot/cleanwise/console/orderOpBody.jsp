<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<% boolean clwSwitch=ClwCustomizer.getClwSwitch(); 
String portal = request.getParameter("portal");
if ( null == portal ) portal = "console";
String action = "/" + portal + "/orderOp.do";
%>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<script language="JavaScript1.2">
<!--
function popLocate(pLoc, name, pDesc) {
  var loc = pLoc + ".do?feedField=" + name + "&feedDesc=" + pDesc;
  locatewin = window.open(loc,"Locate", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
  locatewin.focus();
  return false;
}

function popLocateWithAccount(pLoc, name, pDesc) {
var loc = pLoc + ".do?feedField=" + name + "&feedDesc=" + pDesc + "&accountid=" + document.ORDER_OP_SEARCH_FORM.accountId.value;
locatewin = window.open(loc,"Locate", "menubar=no,resizable=yes,scrollbars=yes,toolbar=no,status=yes,height=500,width=769,left=100,top=165");
locatewin.focus();

return false;
}



function f_set_bdate(m_days, m_mm, m_yr) {


now =  new Date();
n_mm =	now.getMonth() + m_mm;
if ( n_mm < 0 ) {
  n_mm = n_mm + 11;
}
n_mm = n_mm + 1;

fdate = new Date( now.getFullYear() + m_yr, n_mm,
	  now.getDate() + m_days );

tstring = fdate.getMonth() + '/' + fdate.getDate() + '/' + fdate.getFullYear();

ele = document.ORDER_OP_SEARCH_FORM.elements;
for ( i = 0; i < ele.length; i++ ) {
 if ( ele[i].type == "text" &&
       ele[i].name == "orderDateRangeBegin" ) {
   ele[i].value = tstring;
//   alert (' tstring=' + tstring + ' val=' + ele[i].value);
   return;
 }
}

}


//-->
</script>

<div class="text">
<%if (portal.equals("console")) {%>
<font color=red>
<html:errors/>
</font>
<%}%>
<table cellpadding="2" cellspacing="0" border="0" width="769" class="mainbody">
<tr><td>
<html:form name="ORDER_OP_SEARCH_FORM" action="<%=action%>" 
  focus="orderDateRangeBegin"
    scope="session" type="com.cleanwise.view.forms.OrderOpSearchForm">
<table cellpadding="2" cellspacing="0" border="0" width="769" class="mainbody">




  <tr> <td><b>Search Orders:</b></td>
       <td colspan="4" align=right>
<a href="#" onclick="f_reset_fields(document.ORDER_OP_SEARCH_FORM);">
<input type="button" value="Reset search fields"></a> 
</td>
  </tr>

<tr> <td>&nbsp;</td>
   <td><b>Account(s)</b></td>
       <td colspan='3'>
       <% String onKeyPress="return submitenter(this,event,'Submit');"; %>
       <% String onClick = "popLocate('../adminportal/accountLocateMulti', 'accountIdList', '');";%>
       <html:text size='50' onkeypress="<%=onKeyPress%>" name="ORDER_OP_SEARCH_FORM"   property="accountIdList" />
       <html:button property="locateAccount"
                    onclick="<%=onClick%>"
                    value="Locate Account(s)"/>
        </td>
  
  </tr>
  
  <tr> <td>&nbsp;</td>
       <td><b>Distributor ID:</b></td>
	   <td colspan="3"> 
			<html:text name="ORDER_OP_SEARCH_FORM" property="distributorId" />
			<html:button property="action"
   				onclick="popLocate('../adminportal/distlocate', 'distributorId', '');"
   				value="Locate Distributor"/>			
       </td>
  </tr>

  <tr> <td>&nbsp;</td>
       <td><b>Site Id:</b></td>
	   <td colspan="3"> 
			<html:text name="ORDER_OP_SEARCH_FORM" property="siteId" />
			<html:button property="action"
   				onclick="popLocate('../adminportal/sitelocate', 'siteId', '');"
   				value="Locate Site"/>			
       </td>
  </tr>


 <bean:define id="theForm" name="ORDER_OP_SEARCH_FORM" 
     type="com.cleanwise.view.forms.OrderOpSearchForm"/>
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
<html:text name="ORDER_OP_SEARCH_FORM" property="orderDateRangeBegin" />
<html:text name="ORDER_OP_SEARCH_FORM" property="orderDateRangeEnd" />
<span class="reqind" valign="top">*</span>		
	   </td>
  </tr>
  
  <% if(clwSwitch) { %>  
  <tr> <td>&nbsp;</td>
       <td><b>ERP Order #:</b></td>
	   <td> 
			<html:text name="ORDER_OP_SEARCH_FORM" property="erpOrderNum" />
       </td>
       <td><b>Web Order # / Confirmation #:</b></td>
	   <td> 
			<html:text name="ORDER_OP_SEARCH_FORM" property="webOrderConfirmationNum" />
       </td>	   
  </tr>
  
  <tr> <td>&nbsp;</td>
       <td><b>Outbound PO #:</b></td>
	   <td> 
			<html:text name="ORDER_OP_SEARCH_FORM" property="outboundPoNum" />
       </td>  		
       <td><b>Customer PO #:</b></td>
	   <td> 
			<html:text name="ORDER_OP_SEARCH_FORM" property="custPONum" />
       </td>
  </tr>
  <tr>
      <td>&nbsp;</td>
       <td><b>ERP PO #:</b></td>
	   <td>
			<html:text name="ORDER_OP_SEARCH_FORM" property="erpPONum" />
       </td>
       <td colspan="2"/>
  </tr>
 <% } else { %>
  <tr> <td>&nbsp;</td>
       <td><b>Web Order # / Confirmation #:</b></td>
	   <td> 
			<html:text name="ORDER_OP_SEARCH_FORM" property="webOrderConfirmationNum" />
       </td>	   
       <td><b>Customer PO #:</b></td>
	   <td> 
			<html:text name="ORDER_OP_SEARCH_FORM" property="custPONum" />
       </td>
  </tr>
 <% } %>
  <tr> <td>&nbsp;</td>
       <td><b>Customer Requisition #:</b></td>
	   <td> 
			<html:text name="ORDER_OP_SEARCH_FORM" property="refOrderNum" />
       </td>  		
       <td><b>Order Status:</b></td>
	   <td> 
			<html:select name="ORDER_OP_SEARCH_FORM" property="orderStatus">
				<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
				<html:options  collection="Order.status.vector" property="value" />
			</html:select>
       </td>	   
  </tr>		   
  
  <tr> <td>&nbsp;</td>
       <td><b>Site Zip Code:</b></td>
	   <td> 
			<html:text name="ORDER_OP_SEARCH_FORM" property="siteZipCode" />
       </td>	   	   
       <td><b>Reference Code:</b></td>
	   <td> 
			<html:text name="ORDER_OP_SEARCH_FORM" property="referenceCode" />
       </td>	   	   
  </tr>
  
  <tr> <td>&nbsp;</td>
       <td><b>Distributor Invoice Num:</b></td>
	   <td> 
			<html:text name="ORDER_OP_SEARCH_FORM" property="invoiceDistNum" />
       </td>
       <td><b>Method:</b></td>
	   <td> 
			<html:select name="ORDER_OP_SEARCH_FORM" property="method">
				<html:option value=""><app:storeMessage  key="admin.select"/></html:option>
				<html:options  collection="Method.type.vector" property="value" />
			</html:select>
       </td>	   
  </tr>
  
  <tr> <td>&nbsp;</td>
       <td><b>Customer Invoice Num:</b></td>
	   <td> 
			<html:text name="ORDER_OP_SEARCH_FORM" property="invoiceCustNum" />
       </td>
	   <td colspan="2">&nbsp;</td>	   
  </tr>

  <tr> <td>&nbsp;</td>
       <td><b>Workflow Rule:</b></td>
	   <td colspan=3> 

<html:text name="ORDER_OP_SEARCH_FORM" property="workflowId" />
<html:button property="action"
onclick="popLocateWithAccount('../adminportal/workflowLocate', 'workflowId', '');"
  value="Locate Workflow"/>			

       </td>
	   <td>&nbsp;</td>
  </tr>
  
  <tr> <td>&nbsp;</td>
       <td><b>Ship From Id:</b></td>
	   <td colspan=3> 

<html:text name="ORDER_OP_SEARCH_FORM" property="shipFromId" />
<html:button property="action"
onclick="popLocate('../adminportal/distShipFromLocate', 'shipFromId', '');"
  value="Locate Ship From"/>			

       </td>
	   <td>&nbsp;</td>
  </tr>

  <tr> <td>&nbsp;</td>
       <td><b>Placed By:</b></td>
	   <td colspan="3"> 
			<html:text name="ORDER_OP_SEARCH_FORM" property="placedBy" />
			<html:button property="action"
   				onclick="popLocate('../adminportal/usermgrLocate', '', 'placedBy');"
   				value="Locate Placed By"/>
       </td>	   
  </tr>	
	     
  <tr>
       <td colspan="5" align="center">
       <html:hidden property="action" value="search"/>
       <html:submit property="action" value="Search"/>
       <% if(clwSwitch) { %>
       <% String backfillClick = new String("window.location.href='orderBackfill.do?action=view&id=0';");%>
       <html:button property="action" value="Backfill" onclick="<%=backfillClick%>"/>
       <% } %>
     </td>
  </tr>
  <tr><td colspan="5">&nbsp;</td>
  </tr>
</tr></td>
  </table>
</html:form>  
</table>

Search results count:&nbsp;<bean:write name="ORDER_OP_SEARCH_FORM" property="listCount" filter="true"/>

<logic:greaterThan name="ORDER_OP_SEARCH_FORM" property="listCount" value="0">


<table cellpadding="2" cellspacing="0" border="0" width="769" class="results">
<tr align=left>
<td><a href="orderOp.do?action=sort&sortField=acctname"><b>Acct Name</b></a></td>
<td class="resultscolumna"><a href="orderOp.do?action=sort&sortField=distname"><b>Dist Name</b></a></td>
<td><a href="orderOp.do?action=sort&sortField=erpordernum"><b>ERP Order&nbsp;#</b></a></td>
<td class="resultscolumna"><a href="orderOp.do?action=sort&sortField=webordernum"><b>Web Order&nbsp;#</b></a></td>
<td><a href="orderOp.do?action=sort&sortField=orderdate"><b>Order Date</b></a></td>
<td class="resultscolumna"><a href="orderOp.do?action=sort&sortField=custponum"><b>Cust PO</b></a></td>
<td><a href="orderOp.do?action=sort&sortField=sitename"><b>Site Name</b></a></td>
<td class="resultscolumna"><a href="orderOp.do?action=sort&sortField=address"><b>Address</b></a></td>
<td class="resultscolumna"><a href="orderOp.do?action=sort&sortField=sitecity"><b>City</b></a></td>
<td class="resultscolumna"><a href="orderOp.do?action=sort&sortField=sitestate"><b>State</b></a></td>
<td><a href="orderOp.do?action=sort&sortField=zipcode"><b>Zip Code</b></a></td>
<td class="resultscolumna"><a href="orderOp.do?action=sort&sortField=status"><b>Status</b></a></td>
<td><a href="orderOp.do?action=sort&sortField=method"><b>Method</b></a></td>
<td class="resultscolumna"><a href="orderOp.do?action=sort&sortField=placedby"><b>Placed&nbsp;By</b></a></td>
</tr>

 <bean:define id="pagesize" name="ORDER_OP_SEARCH_FORM" property="listCount"/>
	  
<logic:iterate id="order" name="ORDER_OP_SEARCH_FORM" property="resultList"
     offset="0" length="<%=pagesize.toString()%>" type="com.cleanwise.service.api.value.OrderStatusDescData"> 
 <bean:define id="key"  name="order" property="orderDetail.orderId"/>
 <bean:define id="orderDate"  name="order" property="orderDetail.originalOrderDate"/>
 <% String linkHref = new String("orderOpDetail.do?action=view&id=" + key);%>

 <tr><td colspan="14"><hr></td></tr>		
 <tr>
  <td><bean:write name="order" property="accountName"/></td>
  <td class="resultscolumna"><bean:write name="order" property="distName"/></td>
  <td><bean:write name="order" property="orderDetail.erpOrderNum"/></td>
  <td class="resultscolumna"><a href="<%=linkHref%>"><bean:write name="order" property="orderDetail.orderNum"/></a></td>
  <td>
  	<logic:present name="order" property="orderDetail.originalOrderDate">
  	<i18n:formatDate value="<%=orderDate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>
	</logic:present>
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




