<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ page import="java.util.Locale" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.service.api.util.Utility" %>
<%@ page import="com.cleanwise.view.utils.CleanwiseUser" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>


<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<bean:define id="theForm" name="CUST_ACCT_MGT_INVOICE_DETAIL_FORM" type="com.cleanwise.view.forms.CustAcctMgtInvoiceDetailForm"/>

<script src="../externals/table-sort.js" language="javascript"></script>
<script language="JavaScript1.2">

fmtprt= new Image();
fmtprt.src="<%=IMGPath%>/printerfriendly.gif";

fmtexc= new Image();
fmtexc.src="<%=IMGPath%>/excelformat.gif";

function viewPrinterFriendly() {
  var loc = "printerFriendlyInvoicesDetail.do?action=pdfPrint";
  prtwin = window.open(loc,"printer_friendly",
    "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500, width=775,left=100,top=165");
  prtwin.focus();

  return false;
}

function viewExcelFormat() {
  var loc = "printerFriendlyInvoicesDetail.do?action=excelPrint";

  prtwin = window.open(loc,"excel_format",
    "menubar=yes,resizable=yes,scrollbars=yes,toolbar=yes,status=yes,height=500, width=775,left=100,top=165");
  prtwin.focus();

  return false;
}
</script>

<table width="100%" align=center CELLSPACING=0 CELLPADDING=2><tr>
   <%--Content --%>
  <td>

<form name="CUST_ACCT_MGT_INVOICE_DETAIL_FORM" method="post" action="../userportal/customerAccountManagementInvoicesDetail.do"  >
<%-- begin Table 1  --%>
<bean:define id="po" name="CUST_ACCT_MGT_INVOICE_DETAIL_FORM" property="invoiceView" />

<table width="100%">
<%java.math.BigDecimal zero=new java.math.BigDecimal(0);%>

<%
    int tabIndex=1;
    SessionTool st = new SessionTool(request);
    java.util.Locale locale=st.getUserLocaleCode(request);
    CleanwiseUser user = st.getUserData();
    String totalReadOnly = user.getUserProperties().getProperty(RefCodeNames.PROPERTY_TYPE_CD.TOTAL_FIELD_READONLY, "");
%>
<tr>     <%--Invoice Header Information --%>
  <td colspan="2">
    <table width="100%">
   <tr> <%-- 0 Row  --%>
    <td colspan="3" align="left">
        <logic:notEqual name="CUST_ACCT_MGT_INVOICE_DETAIL_FORM" property="prevInvoiceInList" value="0">
            <html:submit property="prev">
                <app:storeMessage  key="global.action.label.previous"/>
            </html:submit>
        </logic:notEqual>
    </td>
    <td colspan="3" align="right">
        <logic:notEqual name="CUST_ACCT_MGT_INVOICE_DETAIL_FORM" property="nextInvoiceInList" value="0">
            <html:submit property="next">
                <app:storeMessage  key="global.action.label.next"/>
            </html:submit>
        </logic:notEqual>
    </td>
   </tr>

   <tr>  <%-- 1 Row  --%>
     <td><b><app:storeMessage key="invoice.text.distributionCenterNo" />:</b></td>
     <td>
       <bean:write name="CUST_ACCT_MGT_INVOICE_DETAIL_FORM" property="distributionCenterNo" filter="true"/>
     </td>

     <td><b><app:storeMessage key="invoice.text.invoiceNumber" />:</b></td>
     <td >
     	 <bean:write name="CUST_ACCT_MGT_INVOICE_DETAIL_FORM" property="invoiceNumber" filter="true"/>
     </td>

    <td><b><app:storeMessage key="invoice.text.poNumber" />:</b></td>
     <td >
      <logic:present name="CUST_ACCT_MGT_INVOICE_DETAIL_FORM"  property="poNumber">
         <bean:write name="CUST_ACCT_MGT_INVOICE_DETAIL_FORM"  property="poNumber" filter="true"/>
       </logic:present>
     </td>

    </tr>

   <tr> <%-- 2 Row  --%>
     <td><b><app:storeMessage key="invoice.text.distributionCenterName" />:</b></td>

     <td>
       <bean:write name="CUST_ACCT_MGT_INVOICE_DETAIL_FORM" property="distributionCenterName" filter="true"/>
     </td>

     <td><b><app:storeMessage key="invoice.text.invoiceDate" />:</b></td>
      <td >
     	<bean:define id="invoicedate" name="CUST_ACCT_MGT_INVOICE_DETAIL_FORM" property="invoiceDate" />
 	  <i18n:formatDate value="<%=invoicedate%>" pattern="MM/dd/yyyy" locale="<%=locale%>"/>
     </td>

     <td><b><app:storeMessage key="invoice.text.taxAmount" />:</b></td>
     <td >
      <logic:present name="CUST_ACCT_MGT_INVOICE_DETAIL_FORM"  property="taxAmount">
         <bean:write name="CUST_ACCT_MGT_INVOICE_DETAIL_FORM"  property="taxAmount" filter="true"/>
       </logic:present>
     </td>
  </tr>
   <tr>  <%-- 3 Row  --%>
     <td><b><app:storeMessage key="invoice.text.accountNumber" />:</b></td>

     <td>
       <logic:present name="CUST_ACCT_MGT_INVOICE_DETAIL_FORM" property="accountNumber">
         <bean:write name="CUST_ACCT_MGT_INVOICE_DETAIL_FORM" property="accountNumber" filter="true"/>
       </logic:present>
     </td>

       <td><b><app:storeMessage key="invoice.text.invoiceStatus" />:</b></td>
       <td >
           <html:select name="CUST_ACCT_MGT_INVOICE_DETAIL_FORM" property="invoiceType">
           <html:option value="">All</html:option>
           <html:option value="<%=RefCodeNames.INVOICE_STATUS_CD.CANCELLED%>">
                  Cancelled
           </html:option>
           <html:option value="<%=RefCodeNames.INVOICE_STATUS_CD.CLW_ERP_RELEASED%>">
                  Released
           </html:option>
           <html:option value="<%=RefCodeNames.INVOICE_STATUS_CD.DUPLICATE%>">
                  Duplicated
           </html:option>
           <html:option value="<%=RefCodeNames.INVOICE_STATUS_CD.INVOICE_HISTORY%>">
                  History
           </html:option>
           <html:option value="<%=RefCodeNames.INVOICE_STATUS_CD.PENDING_REVIEW%>">
                  Pending Review
           </html:option>
           </html:select>


       </td>

     <td><b><app:storeMessage key="invoice.text.freightCharges" />:</b></td>
     <td >
      <logic:present name="CUST_ACCT_MGT_INVOICE_DETAIL_FORM"  property="freightCharges">
         <bean:write name="CUST_ACCT_MGT_INVOICE_DETAIL_FORM"  property="freightCharges" filter="true"/>
       </logic:present>
     </td>

   </tr>
    <tr>  <%-- 4 Row  --%>
     <td><b><app:storeMessage key="invoice.text.accountName" />:</b></td>

     <td>
       <logic:present name="CUST_ACCT_MGT_INVOICE_DETAIL_FORM"  property="accountName">
         <bean:write name="CUST_ACCT_MGT_INVOICE_DETAIL_FORM"  property="accountName" filter="true"/>
       </logic:present>
     </td>

     <td colspan="4">&nbsp;</td>


   </tr>

   <logic:present name="CUST_ACCT_MGT_INVOICE_DETAIL_FORM"  property="orderNotes">
       <bean:size  id="notesSize"  name="CUST_ACCT_MGT_INVOICE_DETAIL_FORM" property="orderNotes"/>
       <logic:greaterThan name="notesSize" value="0">
           <tr>
            <td valign="top"><b><app:storeMessage key="invoice.text.notes" />:</b></td>
            <td colspan="4" valign="top">
              <logic:iterate id="prop" indexId="i" name="CUST_ACCT_MGT_INVOICE_DETAIL_FORM" property="orderNotes" type="com.cleanwise.service.api.value.OrderPropertyData">
                <bean:write name="prop" property="value"/>
              </logic:iterate>
            </td>

           </tr>
         </logic:greaterThan>
   </logic:present>

    <tr>
        <td colspan="5" align="center">
            <html:submit property="action">
                    <app:storeMessage  key="invoice.button.update"/>
            </html:submit>
         </td>
    </tr>

 </table>
 </td>
</tr>
<tr><td colspan="2">&nbsp;</td></tr>
<tr>
  <td><b><app:storeMessage key="invoice.text.items" /></b></td>
  <td align="right">
    <% String b_print = IMGPath + "/b_print.gif"; %>
    <a href="#" class="linkButton" onclick="viewPrinterFriendly();"
      ><img src="<%=b_print%>" border="0"/><app:storeMessage key="global.label.printerFriendly"/></a>
      &nbsp;&nbsp;&nbsp;&nbsp;
    <% String b_excel = IMGPath + "/b_excel.gif"; %>
    <a href="#" class="linkButton" onclick="viewExcelFormat();"
      ><img src="<%=b_excel%>" border="0"/><app:storeMessage key="global.label.excelFormat"/></a>
 </td>
</tr>



</table>
<%-- end of  Table 1  --%>

<%-- Invoice Items   --%>
<logic:present name="CUST_ACCT_MGT_INVOICE_DETAIL_FORM" property="resultList">
    <jsp:include flush='true' page="t_custAcctMgtInvoicesDetailItemInc.jsp"/>
</logic:present>

</form>
</td>


</tr></table>
</div>
