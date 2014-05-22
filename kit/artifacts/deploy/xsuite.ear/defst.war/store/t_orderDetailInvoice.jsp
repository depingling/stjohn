<%@ page language="java" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.cleanwise.view.utils.*" %>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>
<%@ page import="com.cleanwise.service.api.value.*"%>
<%@ page import="com.cleanwise.service.api.util.*"%>
<%@ page import="java.math.BigDecimal"%>
<%@ page import="java.util.Locale" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Iterator" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<bean:define id="IMGPath" type="java.lang.String" name="pages.store.images"/>
<bean:define id="theForm" name="ORDER_OP_DETAIL_FORM" type="com.cleanwise.view.forms.OrderOpDetailForm"/>

<%
  OrderStatusDescData orderStatusDetail = theForm.getOrderStatusDetail();
  String orderLocale = orderStatusDetail.getOrderDetail().getLocaleCd();
  ClwI18nUtil clwI18n = ClwI18nUtil.getInstance(request,orderLocale,-1);
%>

<app:authorizedForFunction
  name="<%=RefCodeNames.APPLICATION_FUNCTIONS.VIEW_INVOICES%>">

<%
java.math.BigDecimal lTotalInvoiced = new java.math.BigDecimal(0);
int colcount = 2;
CleanwiseUser appUser = (CleanwiseUser) session.getAttribute(Constants.APP_USER);
if (appUser.getShowPrice()) {
  colcount += 4;
}
%>

<table class="tbstd" cellpadding=1 cellspacing=0>
<tr><td colspan="<%=colcount%>"><br><br></td> </tr>

<tr><td colspan="<%=colcount%>" class="shopcharthead" align=center>
<br><b><app:storeMessage key="shop.orderStatus.text.invoiceInformation"/></b><br><br></td> </tr>

<tr>
<td class="shopcharthead"><app:storeMessage key="shop.orderStatus.text.invoiceNumber"/></td>
<td class="shopcharthead"><app:storeMessage key="shop.orderStatus.text.invoiceDate"/></td>
<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
<td class="shopcharthead"><app:storeMessage key="shop.orderStatus.text.sub-Total"/></td>
<td class="shopcharthead"><app:storeMessage key="shop.orderStatus.text.miscCharges"/></td>
<td class="shopcharthead"><app:storeMessage key="shop.orderStatus.text.salesTax"/></td>
<td class="shopcharthead"><app:storeMessage key="shop.orderStatus.text.netDue"/></td>
</logic:equal>
</tr>
<logic:iterate id="invent" name="ORDER_OP_DETAIL_FORM"
  property="invoiceList"
  type="com.cleanwise.service.api.value.InvoiceCustView">

<tr>
<td class="rep_line"><%=invent.getInvoiceCustData().getInvoiceNum()%></td>
<td class="rep_line">
  <i18n:formatDate  value="<%=invent.getInvoiceCustData().getInvoiceDate()%>"
  pattern="yyyy-MM-dd"/> </td>
<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
<td class="rep_line">
  <%=clwI18n.priceFormat(invent.getInvoiceCustData().getSubTotal(),"&nbsp;")%></td>
<td class="rep_line">
<%
java.math.BigDecimal tot = new java.math.BigDecimal(0),
 frt = invent.getInvoiceCustData().getFreight(),
 msc = invent.getInvoiceCustData().getMiscCharges();

if ( frt != null ) { tot = tot.add(frt); }
if ( msc != null ) { tot = tot.add(msc); }

%>
  <%=clwI18n.priceFormat(tot,"&nbsp;")%></td>
<td class="rep_line">
  <%=clwI18n.priceFormat(invent.getInvoiceCustData().getSalesTax(),"&nbsp;")%></td>
<td class="rep_line">
  <%=clwI18n.priceFormat(invent.getInvoiceCustData().getNetDue(),"&nbsp;")%></td>
</logic:equal>
</tr>

<%
lTotalInvoiced = lTotalInvoiced.add(invent.getInvoiceCustData().getNetDue());
%>
<tr>
<td class="rep_line"><br></td>
<td class="rep_line" colspan="<%=(colcount-1)%>">
<table width="100%"cellspacing=0>


<tr>
<td class="shopcharthead"><td class="shopcharthead"><app:storeMessage key="shoppingItems.text.lineNumber"/></td>
<td class="shopcharthead"><app:storeMessage key="shoppingItems.text.ourSkuNum"/></td>
<td class="shopcharthead"><app:storeMessage key="shoppingItems.text.name"/></td>
<td class="shopcharthead"><app:storeMessage key="shoppingItems.text.uom"/></td>
<td class="shopcharthead"><app:storeMessage key="shoppingItems.text.qty"/></td>
<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
<td class="shopcharthead"><app:storeMessage key="shoppingItems.text.price"/></td>
<td class="shopcharthead"><app:storeMessage key="shoppingItems.text.lineTotal"/></td>
</logic:equal>
</tr>


<%
InvoiceCustDetailDataVector icdv = invent.getInvoiceCustDetailDataVector();

for ( int i = 0; null != icdv && i < icdv.size(); i++ ) {
  InvoiceCustDetailData icd = (InvoiceCustDetailData)icdv.get(i);
%>
<tr bgcolor="white" align="right">

<td  align="left">
<%=icd.getLineNumber()%>
</td>

<td  align="left">
<%=icd.getItemSkuNum()%>
</td>

<td align="left">
<%=icd.getItemShortDesc()%>
</td>

<td>
<%=icd.getItemUom()%>
</td>

<td>
<%=icd.getItemQuantity()%>
</td>
<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
<td>
  <%=clwI18n.priceFormat(icd.getCustContractPrice(),"&nbsp;")%></td>
<td>
  <%=clwI18n.priceFormat(icd.getLineTotal(),"&nbsp;")%></td>
</logic:equal>
</tr>
<% } %>

</table></td></tr>

</logic:iterate>

<logic:equal name="<%=Constants.APP_USER%>" property="showPrice" value="true">
<tr>
<td  class="rep_line"><app:storeMessage key="shoppingItems.text.totalInvoiced"/></td>
<td  class="rep_line" colspan="<%=(colcount-1)%>" align="left">
  <%=clwI18n.priceFormat(lTotalInvoiced,"&nbsp;")%></td>
</tr>
</logic:equal>

</table>

</app:authorizedForFunction>

