<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.SessionTool" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.cleanwise.service.api.util.*" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>


<bean:define id="theForm" name="STORE_VEN_INVOICE_SEARCH_FORM" type="com.cleanwise.view.forms.StoreVendorInvoiceSearchForm"/>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<%
String myURI = SessionTool.getActualRequestedURIPage(request);
String submitURI = SessionTool.getActualRequestedStrutsMapping(request);
if(submitURI.endsWith("submit")){
	submitURI = submitURI + ".do";
}else{
	submitURI = submitURI + "-submit.do";
}
String poDetailAction = request.getParameter("poDetailAction");
String invDetailAction = request.getParameter("invDetailAction");
String detailURI = request.getParameter("detailURI");
String invoiceDisply = request.getParameter("invoiceDisply");
String poDisplay = (String)request.getAttribute("poDisplay");
CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
String userType = appUser.getUser().getUserTypeCd();

String distributorId = null;
if (RefCodeNames.USER_TYPE_CD.DISTRIBUTOR.equals(userType)){
	distributorId = ((BusEntityData)appUser.getDistributors().get(0)).getBusEntityId()+"";
}
%>
<% String invoiceStatusParam = request.getParameter("invoiceStatus"); %>

<jsp:include flush='true' page="locateStoreAccount.jsp">
	<jsp:param name="jspFormAction" 	        value="<%=submitURI%>" /> 
	<jsp:param name="jspFormName" 	        value="STORE_VEN_INVOICE_SEARCH_FORM" /> 
	<jsp:param name="jspSubmitIdent" 	        value="" /> 
	<jsp:param name="jspReturnFilterProperty" 	value="accountFilter" /> 
	<jsp:param name="renderCurrentFilter" 	        value="true" /> 
</jsp:include>
<jsp:include flush='true' page="locateStoreDistributor.jsp">
	<jsp:param name="jspFormAction" 	        value="<%=submitURI%>" /> 
	<jsp:param name="jspFormName" 	        value="STORE_VEN_INVOICE_SEARCH_FORM" /> 
	<jsp:param name="jspSubmitIdent" 	        value="" /> 
	<jsp:param name="jspReturnFilterProperty" 	value="distributorFilter" /> 
	<jsp:param name="renderCurrentFilter" 	        value="true" /> 
</jsp:include>
<table ID=1143><tr><td>
<html:form styleId="1144" action="<%= submitURI %>"
    scope="session" type="com.cleanwise.view.forms.PurchseOrderOpSearchForm">
<table ID=1145 cellpadding="2" cellspacing="0" border="0" width="<%=Constants.TABLEWIDTH%>" class="mainbody">		
<bean:define id="columnCount" value="13"/>
  <tr> <td><b>Search:</b></td>
       <td colspan="4">&nbsp;
	   
		<html:submit property="action">
            <app:storeMessage  key="admin.button.search.invoices"/>
        </html:submit>
		<html:submit property="action">
            <app:storeMessage  key="admin.button.search.pos"/>
        </html:submit>
		<%if("true".equalsIgnoreCase(invoiceDisply)){%>
			<html:submit property="action">
				<app:storeMessage  key="admin.button.search.invoices.exception"/>
			</html:submit>
		<%}%>
       </td>
  </tr>
<% if (RefCodeNames.USER_TYPE_CD.DISTRIBUTOR.equals(userType)){ %>
	<html:hidden property="distributorId" value="<%= distributorId %>"/> 
<% }else{ %>
   <tr>
     <td colspan="5">
	   <jsp:include flush='true' page="locateStoreAccountButtons.jsp">
		 <jsp:param name="jspFormName" 	        value="STORE_VEN_INVOICE_SEARCH_FORM" /> 
		 <jsp:param name="jspReturnFilterProperty" 	value="accountFilter" /> 
	   </jsp:include>
	   
	  </td>
    </tr>
	
	<tr>
     <td colspan="5">
	    <jsp:include flush='true' page="locateStoreDistributorButtons.jsp">
		 <jsp:param name="jspFormName" 	        value="STORE_VEN_INVOICE_SEARCH_FORM" /> 
		 <jsp:param name="jspReturnFilterProperty" 	value="distributorFilter" /> 
	    </jsp:include>
		 
   	  </td>
    </tr>
<% } %>
  <tr> 
       <td><b>Purchase Order Date:</b><br>(<%=ClwI18nUtil.getUIDateFormat(request)%>)</td>
           <td colspan="3">
                        Begin Date Range
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        End Date Range<br>
                        <html:text name="STORE_VEN_INVOICE_SEARCH_FORM" property="poDateRangeBegin" />
                        <html:text  property="poDateRangeEnd" />
           </td>
  </tr>

 <tr> 
       <td><b>Web Order # / Confirmation #:</b></td>
           <td>
                        <html:text property="webOrderConfirmationNum" />
       </td>
       <td><b>Erp PO #:</b></td>
           <td>
                        <html:text  property="erpPONum" />
       </td>
 </tr>	   
 <tr> 
       <td><b>Outbound PO #:</b></td>
           <td>
                        <html:text  property="outboundPoNum" />
       </td>
       <td><b>&nbsp;</b></td>
           <td>&nbsp;</td>
 </tr>	   
 <tr> 
       <td><b>Vendor Invoice Num:</b></td>
       <td>
            <html:text  property="invoiceDistNum" />
       </td>
	   <td><b>Vendor Invoice Exception Only:</b></td>
       <td>
            <html:checkbox  property="exceptionDistInvoiceOnly" />
       </td>
  </tr>
  <tr> 
       <td><b>Vendor Invoice Date:</b><br>(<%=ClwI18nUtil.getUIDateFormat(request)%>)</td>
           <td colspan="3">
                        Begin Date Range
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        End Date Range<br>
                        <html:text property="invoiceDistDateRangeBegin" />
                        <html:text property="invoiceDistDateRangeEnd" />
           </td>
  </tr>
  <tr> 
       <td><b>Vendor Invoice Date Received:</b><br>(<%=ClwI18nUtil.getUIDateFormat(request)%>)</td>
           <td colspan="3">
                        Begin Date Range
                        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        End Date Range<br>
                        <html:text property="invoiceDistAddDateRangeBegin" />
                        <html:text property="invoiceDistAddDateRangeEnd" />
           </td>
  </tr>

  <tr> 
       <td><b>Site Data:</b></td>
       <td>
                <html:text property="siteData" />
       </td>
       <td><b>Target Facility Rank:</b></td>
       <td>
                <html:text property="targetFacilityRank" />
       </td>
  </tr>

  <tr> 
       <td><b>Purchase Order Status:</b></td>
           <td colspan="3">
            <html:select property="purchaseOrderStatus">
                <html:option value=""><app:storeMessage  key="admin.select"/></html:option>
                                <html:options  collection="PurchaseOrder.status.vector" property="value" />
            </html:select>
       </td>
       <td></td><td></td>
  </tr>
  <tr>
        
        <td><b>Customer PO Number:</b></td><td><html:text  property="orderRequestPoNum" /></td>
        <td></td><td></td>
  </tr>
  
  
  <tr>
        
        <td><b>Return Number:</b></td>
        <td>
                <html:text  property="returnRequestRefNum" />
        </td>
        
        
  </tr>

  <tr> 
       <td><b>Invoice Status:</b></td>
           <td colspan="3">
            <html:select property="invoiceStatus" value="<%=invoiceStatusParam%>">
                    <html:option value="-Select-"/>
                    <html:option value="CANCELLED"/>
                    <html:option value="PENDING"/>
                    <html:option value="CLW_ERP_PROCESSED"/>
                    <html:option value="MANUAL_INVOICE_RELEASE"/>  
                    <html:option value="PROCESS_ERP"/>
                    <html:option value="DUPLICATE"/>
                    <html:option value="ERP_RELEASED"/>  
                    <html:option value="REJECTED"/>
                    <html:option value="PENDING_REVIEW"/>  
                    <html:option value="ERP_REJECTED"/>  
                    <html:option value="DIST_SHIPPED"/>  
                    <html:option value="CLW_ERP_RELEASED"/>  
                    <html:option value="INVOICE_HISTORY"/>
                    <html:option value="ERP_GENERATED"/>  
                    <html:option value="ERP_GENERATED_ERROR"/>
                    <html:option value="CUST_INVOICED_FAILED"/>  
                    <html:option value="CUST_INVOICED"/>
                    <html:option value="ERP_RELEASED_ERROR"/>
            </html:select>
       </td>
       <td></td><td></td>
  </tr>

  <tr>
	   <td>&nbsp;</td>
       <td colspan="4">
	   
        <html:submit property="action">
            <app:storeMessage  key="admin.button.search.invoices"/>
        </html:submit>
		<html:submit property="action">
            <app:storeMessage  key="admin.button.search.pos"/>
        </html:submit>
		<%if("true".equalsIgnoreCase(invoiceDisply)){%>
			<html:submit property="action">
				<app:storeMessage  key="admin.button.search.invoices.exception"/>
			</html:submit>
		<%}%>
     </td>
  </tr>
  <tr><td colspan="5">&nbsp;</td>
  </tr>
</table>

<table ID=1146 width="<%=Constants.TABLEWIDTH%>">
<tr>
    <td>
    Search results count:&nbsp;<bean:write name="STORE_VEN_INVOICE_SEARCH_FORM" property="listCount" filter="true"/>
	<logic:greaterThan name="STORE_VEN_INVOICE_SEARCH_FORM" property="resultCount" value="0">
		of&nbsp; <bean:write name="STORE_VEN_INVOICE_SEARCH_FORM" property="resultCount" filter="true"/>
	</logic:greaterThan>
    </td>
    
    <td align="right">
        <logic:greaterThan name="STORE_VEN_INVOICE_SEARCH_FORM" property="listCount" value="0">
            <a ID=1147 href="<%=myURI%>?action=printList" target="spivpt">Print All Invoices</a>
        </logic:greaterThan>
    </td>
</tr>
</table>
<logic:greaterThan name="STORE_VEN_INVOICE_SEARCH_FORM" property="listCount" value="0">

<!--deal with the result set-->



<table width="850" class="stpTable_sortable" id="ts1">
<thead>
<tr>

<bean:define name="STORE_VEN_INVOICE_SEARCH_FORM" id="pagesize"  property="listCount"/>

<%-- display invoice results --%>
<%if("true".equalsIgnoreCase(invoiceDisply)){%>

  <th class="stpTH">Dist Name</th>
  <th class="stpTH">Acct Name</th>
  <th width="50" class="stpTH">PO&nbsp;#</th>
  <th class="stpTH">PO&nbsp;Date</th>
  <%if(poDisplay==null || !("true".equalsIgnoreCase(poDisplay))){%>
	<th class="stpTH">Invoice&nbsp;#</th>
	<th class="stpTH">Invoice&nbsp;Date/<br />Recvd Date</th>
  <%}%>
  <th class="stpTH">Site Name</th>
  <th class="stpTH">City</th>
  <th class="stpTH">State</th>
  <th class="stpTH">Zip Code</th>
  <th class="stpTH">Status</th>
  </tr>
</thead>
<tbody id="resTblBdy">


	<logic:iterate name="STORE_VEN_INVOICE_SEARCH_FORM" id="inv"  property="resultList"
		 offset="0" length="<%=pagesize.toString()%>" indexId="i" type="com.cleanwise.service.api.value.PurchaseOrderStatusDescDataView">
<%try{%>
	 <%-- Deine some variables --%>
	 <%String invLinkHref = null;%>
	 <%String poLinkHref = null;%>
	 <logic:present name="inv" property="invoiceDist.invoiceDistId">
		<bean:define id="key"  name="inv" property="invoiceDist.invoiceDistId"/>
		<%invLinkHref = new String(detailURI+"?action="+invDetailAction+"&id=" + key);%>
	 </logic:present>
	 <logic:present name="inv" property="purchaseOrderData.purchaseOrderId">
		<bean:define id="key"  name="inv" property="purchaseOrderData.purchaseOrderId"/>
		<%poLinkHref = new String(detailURI+"?action="+poDetailAction+"&id=" + key);%>
	 </logic:present>
	 
	 

  <tr>
	  <td>
	     <logic:present name="inv" property="distributorBusEntityData">
		  <bean:write name="inv" property="distributorBusEntityData.shortDesc"/>
		 </logic:present>
	  </td>
	  <td>
	    <logic:present name="inv" property="accountBusEntityData">
		  <bean:write name="inv" property="accountBusEntityData.shortDesc"/>
		</logic:present>
	  </td>
	
	  
	  <td width="50">
	      <logic:present name="inv" property="purchaseOrderData.erpPoNum">
			  <%if(poLinkHref != null){%>
				<a ID=1148 href="<%=poLinkHref%>"><bean:write name="inv" property="purchaseOrderData.erpPoNum"/></a>
			  <%}else{%>
				<bean:write name="inv" property="purchaseOrderData.erpPoNum"/>
			  <%}%>
		  </logic:present>
          <logic:notPresent name="inv" property="purchaseOrderData.erpPoNum">
            <bean:write name="inv" property="invoiceDist.erpPoNum"/>
          </logic:notPresent>
	  </td>
	  <td>
		<logic:present name="inv" property="purchaseOrderData.poDate">
		  <bean:define id="poDate"  type="java.util.Date" name="inv" property="purchaseOrderData.poDate"/>
		  <%=ClwI18nUtil.formatDateInp(request, poDate)%>
		</logic:present>
	  </td>
	  <%if(poDisplay==null || !("true".equalsIgnoreCase(poDisplay))){%>
	  <td>
		  <logic:present name="inv" property="invoiceDist.invoiceNum">
		  	  <%if(invLinkHref != null){%>
				<a ID=1149 href="<%=invLinkHref%>"><bean:write name="inv" property="invoiceDist.invoiceNum"/></a>
			  <%}else{%>
				<bean:write name="inv" property="invoiceDist.invoiceNum"/>
			  <%}%>
		  </logic:present>
	  </td>
	  <td>
		<logic:present name="inv" property="invoiceDist.invoiceDate">
		  <bean:define id="invDate" type="java.util.Date" name="inv" property="invoiceDist.invoiceDate"/>
		  <%=ClwI18nUtil.formatDateInp(request, invDate)%>
		</logic:present>
		/<br>
		<logic:present name="inv" property="invoiceDist.addDate">
		  <bean:define id="addDate" type="java.util.Date" name="inv" property="invoiceDist.addDate"/>
		  <%=ClwI18nUtil.formatDateInp(request, addDate)%>
		</logic:present>
	  </td>
	 <%}%>
	  <% 
	  boolean useInvoiceShippingInfo = false;
	  if(inv.getInvoiceDist()!= null){
		if(inv.getInvoiceDist().getShipToName() != null || inv.getInvoiceDist().getShipToAddress1() != null||
			inv.getInvoiceDist().getShipToCity() != null || inv.getInvoiceDist().getShipToState() != null ||
			inv.getInvoiceDist().getShipToPostalCode() != null){
            %>
            <logic:notPresent name="inv" property="shipToAddress">
            <%
				useInvoiceShippingInfo = true;
            %>
            </logic:notPresent>
            <% 
		}
	  }
	  if(useInvoiceShippingInfo){%>
		  <td><bean:write name="inv" property="invoiceDist.shipToName"/><br />
            <logic:present name="inv" property="shipToAddress.shortDesc">
            <bean:write name="inv" property="shipToAddress.shortDesc" ignore="true"/>
          </logic:present>
		  </td>
		  <td><bean:write name="inv" property="invoiceDist.shipToCity"/></td>
		  <td><bean:write name="inv" property="invoiceDist.shipToState"/></td>
		  <td><bean:write name="inv" property="invoiceDist.shipToPostalCode"/></td>
	  <%}else{%>
		  <logic:present name="inv" property="shipToAddress">
			  <td><bean:write name="inv" property="shipToAddress.shortDesc" ignore="true"/></td>
			  <td><bean:write name="inv" property="shipToAddress.city" ignore="true"/></td>
			  <td><bean:write name="inv" property="shipToAddress.stateProvinceCd" ignore="true"/></td>
			  <td><bean:write name="inv" property="shipToAddress.postalCode" ignore="true"/></td>
		  </logic:present>
		  <logic:notPresent name="inv" property="shipToAddress">
			<td colspan="4">&nbsp;</td>
		  </logic:notPresent>
	  <%}%>
	  <td>
		<logic:present name="inv" property="invoiceDist.invoiceStatusCd">
            <%=SessionTool.xlateAdminStatus(inv.getInvoiceDist().getInvoiceStatusCd(),request)%>
		</logic:present>
	  </td>
	 </tr>
     <%}catch(Exception e){e.printStackTrace();}%>
	 </logic:iterate>
	</tbody>
 
<%-- display po results --%> 
<%}else{%>
  <th class="stpTH">Dist Name</th>
  <th class="stpTH">Acct Name</th>
  <th class="stpTH">PO&nbsp;#</th>
  <%if(poDisplay==null || !("true".equalsIgnoreCase(poDisplay))){%>
	<th class="stpTH">Invoice&nbsp;#</th>
	<th class="stpTH">Invoice&nbsp;Date</th>
  <%}%>
  <th class="stpTH">Site Name</th>
  <th class="stpTH">Address</th>
  <th class="stpTH">City</th>
  <th class="stpTH">State</th>
  <th class="stpTH">Zip Code</th>
  <th class="stpTH">Status</th>
  </tr>

 
	 <logic:iterate name="STORE_VEN_INVOICE_SEARCH_FORM" id="po"  property="resultList"
		 offset="0" length="<%=pagesize.toString()%>" indexId="i" type="com.cleanwise.service.api.value.PurchaseOrderStatusDescDataView">
	 <bean:define id="key"  name="po" property="purchaseOrderData.purchaseOrderId"/>
	 <bean:define id="poDate" type="java.util.Date" name="po" property="purchaseOrderData.purchaseOrderDate"/>
	 <%String linkHref = new String(detailURI+"?action="+poDetailAction+"&id=" + key);%>
	
	<tr>
	  <td><bean:write name="po" property="distributorBusEntityData.shortDesc"/></td>
	  <td><bean:write name="po" property="accountBusEntityData.shortDesc"/></td>
	
	  <logic:present name="po" property="purchaseOrderData.purchaseOrderStatusCd">
			  <logic:equal name="po" property="purchaseOrderData.purchaseOrderStatusCd" value="<%=RefCodeNames.PURCHASE_ORDER_STATUS_CD.CANCELLED%>">
					<bean:define id="goodStatus" value="false"/>
			  </logic:equal>
			  <logic:notEqual name="po" property="purchaseOrderData.purchaseOrderStatusCd" value="<%=RefCodeNames.PURCHASE_ORDER_STATUS_CD.CANCELLED%>">
					<bean:define id="goodStatus" value="true"/>
			  </logic:notEqual>
	  </logic:present>
	  <logic:notPresent name="po" property="purchaseOrderData.poDate">
			<bean:define id="goodStatus" value="false"/>
	  </logic:notPresent>
	
	  <logic:equal name="goodStatus" value="true">
			<td><a ID=1150 href="<%=linkHref%>"><bean:write name="po" property="purchaseOrderData.erpPoNum"/></a></td>
	  </logic:equal>
	  <logic:notEqual name="goodStatus" value="true">
			<td><bean:write name="po" property="purchaseOrderData.erpPoNum"/></td>
	  </logic:notEqual>
	
	  <td>
			<logic:present name="po" property="purchaseOrderData.poDate">
      		  <%=ClwI18nUtil.formatDateInp(request, poDate)%>
			</logic:present>
	  </td>
	
	  <td><bean:write name="po" property="shipToAddress.shortDesc"/></td>
	  <td><bean:write name="po" property="shipToAddress.address1"/></td>
	  <td><bean:write name="po" property="shipToAddress.city"/></td>
	  <td><bean:write name="po" property="shipToAddress.stateProvinceCd"/></td>
	  <td><bean:write name="po" property="shipToAddress.postalCode"/></td>
	  <td><bean:write name="po" property="purchaseOrderData.purchaseOrderStatusCd"/></td>
	 </tr>
	 </logic:iterate>
<%}%>



</table>
</logic:greaterThan>
</html:form>
</td></tr></table>
</div>




