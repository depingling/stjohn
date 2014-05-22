
<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Date" %>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="java.util.Locale" %>

<%@ taglib uri='/WEB-INF/struts-template.tld' prefix='template' %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<% String storeDir=ClwCustomizer.getStoreDir(); %>
<% String requestNumber =  (String) session.getAttribute(Constants.REQUEST_NUM);%>
<bean:define id="theForm" name="CUST_ACCT_MGT_INVOICE_DETAIL_FORM" type="com.cleanwise.view.forms.CustAcctMgtInvoiceDetailForm"/>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>


<table align="center" border="0" cellpadding="0" cellspacing="0" width="690">
  <tr>
    <td></td>
    <td>  </td>
    <td></td>
  </tr>

  <!-- content, Invoice Detail List Header -->
  <logic:notPresent name="CUST_ACCT_MGT_INVOICE_DETAIL_FOR" property="invoiceNumber">
  <tr>
    <td></td>
    <td align="center">
    <b>No invoice details available</b>
    </td>
    <td></td>
  </tr>
  </logic:notPresent>
  <!-- Order guide select section -->
  <% if(theForm.getInvoiceNumber()>0 ) {%>
  <tr>
    <td></td>
    <td class="smalltext">
    </td>
    <td></td>
  </tr>
  <% } %>

  <!-- List of items -->
  <logic:present name="CUST_ACCT_MGT_INVOICE_DETAIL_FOR" property="invoiceNumber">
  <logic:equal name="CUST_ACCT_MGT_INVOICE_DETAIL_FOR" property="<%=getResultList().size()%>" value="0">
  <tr>
    <td></td>
    <td class="text" align="center">
    <b>No Items In The Invoice Details </b>
    </td>
    <td></td>
  </logic:equal>

  <logic:greaterThan name="CUST_ACCT_MGT_INVOICE_DETAIL_FOR" property="<%=getResultList().size()%>" value="0">

   <tr height=50>
    <td></td>
  </tr>
  <tr>
    <td align="center">
    <table border="0" cellpadding="0" cellspacing="0" width="650" align="CENTER">
      <tr>
        <td align="left" colspan="2">
          <H3><%=theForm.getInvoiceNumber()%></H3>
        </td>
      </tr>
      <tr>
        <td align="left" width="40%">
          <img src='/<%=storeDir%>/en/images/cw_logo.gif'>
        </td>
        <td width="10%" rowspan="2">
          &nbsp;
        </td>
      </tr>
      <tr>
        <td align="left" width="40%">
          <H3>Invoice Number</H3>
        </td>
      </tr>
      <tr>
        <td align="left" colspan="3">
          <%=theForm.getInvoiceNumber()%>
        </td>
      </tr>
      <tr height="40">
        <td colspan="3">&nbsp;</td>
      </tr>
    </table>
    </td>
  </tr>
  <tr>
    <td align="center">
    <div style="background-color: white; border: solid 1px black; width: 650px">
    <table border="0" cellpadding="0" cellspacing="0" width="620" align="CENTER">
      <tr height=25>

        <td class="text" width="15%" align="left">Distribution Center No:
        </td>
        <td class="text" width="45%" align="left" style="border-bottom: solid 1px black;">&nbsp;
             <%=theForm.getDistributionCenterNo()%>
        </td>
      </tr>
      <tr height=25>

        <td class="text" width="15%" align="left">Distribution Center Name:
        </td>

        <td class="text" width="45%" align="left" style="border-bottom: solid 1px black;">&nbsp;
             <%=theForm.getDistributionCenterName()%>
        </td>
      </tr>
      <tr height=25>

        <td class="text" width="15%" align="left">Account Number:
        </td>
        <td class="text" width="45%" align="left" style="border-bottom: solid 1px black;">&nbsp;
             <%=theForm.getAccountNumber()%>
        </td>
      </tr>
      <tr height=25>

        <td class="text" width="15%" align="left" colspan=2>Account Name:
        </td>
        <td class="text" width="25%" align="left" style="border-bottom: solid 1px black;">&nbsp;
             <%=theForm.getAccountName()%>
        </td>
      </tr>

      <tr height=25>
        <td class="text" width="15%" align="left" colspan=2> PO Number:
        </td>
        <td class="text" width="25%" align="left" style="border-bottom: solid 1px black;">&nbsp;
             <%=theForm.getPoNumber()%>
        </td>
      </tr>
    </table>
    </div>
    </td>
  </tr>

  <tr height=25>
    <td></td>
  </tr>
  <tr>
    <td>
    <table width="650" align="CENTER" border="1" cellpadding="0" cellspacing="0" style="border-collapse: collapse;">
      <!-- page contral bar -->
      <tr>
        <td  class="shopcharthead" align="center">Line #</td>
        <td  class="shopcharthead" align="center">Quantity</td>
        <td  class="shopcharthead" align="center">Quantity Unit of Measure</td>
        <td  class="shopcharthead" align="center">Unit Price</td>
        <td  class="shopcharthead" align="center">Extended Price</td>
        <td  class="shopcharthead" align="center">Vendor Num</td>
        <td  class="shopcharthead" align="center">Product Name</td>
        <td  class="shopcharthead" align="center">Pack</td>
        <td  class="shopcharthead" align="center">Pack Size</td>
        <td  class="shopcharthead" align="center">Manuf Name</td>
        <td  class="shopcharthead" align="center">Manuf Num</td>
        <td  class="shopcharthead" align="center">UPC</td>
        <td  class="shopcharthead" align="center">Discounts</td>

      </tr>
      <!--tr>
        <td  colspan="10" class="tableoutline"></td>  </tr>
      </tr-->
      <bean:define id="offset" name="CUST_ACCT_MGT_INVOICE_DETAIL_FOR" property="offset"/>
      <bean:define id="pagesize" name="CUST_ACCT_MGT_INVOICE_DETAIL_FOR" property="pageSize"/>
      <logic:iterate id="itemele" indexId="i" name="CUST_ACCT_MGT_INVOICE_DETAIL_FORM" property="resultList" scope="session"
              type="com.cleanwise.service.api.value.InvoiceNetworkServiceData">
          <tr>
		<td class="text" align="center" bgcolor="white">  <%-- Line --%>
                    <bean:write name="itemele" property="lineNumber"/>
                </td>
		<td class="text" align="center" bgcolor="white"> <%-- Quantity --%>
                    <bean:write name="itemele" property="quantity"/>
		</td>
		<td class="text" align="center" bgcolor="white">  <%-- Quantity Unit of Measure --%>
                    <bean:write name="itemele" property="quantityUnitOfMeasure"/>
		</td>
		<td class="text" align="center" bgcolor="white">  <%-- Unit Price --%>
                    <bean:write name="itemele" property="unitPrice"/>
		</td>
		<td class="text" align="center" bgcolor="white">  <%-- Extended Price --%>
                    <bean:write name="itemele" property="extendedPrice"/>
		</td>
		<td class="text" align="center" bgcolor="white">  <%-- Product # --%>
                    <bean:write name="itemele" property="productNumber"/>
		</td>
		<td class="text" align="center" bgcolor="white">  <%-- Product Name--%>
                    <bean:write name="itemele" property="productName"/>
		</td>
		<td class="text" align="center" bgcolor="white">  <%-- Pack --%>
                     <bean:write name="itemele" property="pack"/>
		</td>
		<td class="text" align="center" bgcolor="white">  <%-- Pack Size --%>
                     <bean:write name="itemele" property="packSize"/>
		</td>
		<td class="text" align="center" bgcolor="white">  <%-- Manufacturer Name --%>
                     <bean:write name="itemele" property="manufacturerName"/>
		</td>
		<td class="text" align="center" bgcolor="white">  <%-- Manufacturer Product # --%>
                     <bean:write name="itemele" property="manufacturerProductNo"/>
		</td>
		<td class="text" align="center" bgcolor="white">  <%-- UPC --%>
                     <bean:write name="itemele" property="upc"/>
		</td>
		<td class="text" align="center" bgcolor="white">  <%-- Discounts --%>
                      <bean:write name="itemele" property="discounts"/>
		</td>
	</tr>
      </logic:iterate>
    </table>
    </td>
    <td></td>
  </tr>
  </logic:greaterThan>

</table>






