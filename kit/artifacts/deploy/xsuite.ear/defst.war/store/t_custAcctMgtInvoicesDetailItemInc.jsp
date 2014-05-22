<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.SessionTool" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer"%>
<%@ page import="com.cleanwise.service.api.util.Utility"%>
<%@ page import="com.cleanwise.view.i18n.ClwI18nUtil"%>

<%@ page import="java.util.Locale" %>
<%@ page import="com.cleanwise.service.api.value.*" %>
<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>

<script src="../externals/table-sort.js" language="javascript"></script>

<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>

<table align=center width="100%" ><%--class="stpTable_sortable" id="ts1"--%>
<thead>

<tr align=center>
<td class="shopcharthead"><a href="customerAccountManagementInvoicesDetail.do?action=sort&sortField=lineNumber"><b><app:storeMessage key="invoice.text.lineNumber" /></b></a></td>
<td class="shopcharthead"><a href="customerAccountManagementInvoicesDetail.do?action=sort&sortField=quantity"><b><app:storeMessage key="invoice.text.quantity" /></b></a></td>
<td class="shopcharthead"><a href="customerAccountManagementInvoicesDetail.do?action=sort&sortField=quantityUnitOfMeasure"><b><app:storeMessage key="invoice.text.quantityUOM" /></b></a></td>
<td class="shopcharthead"><b><app:storeMessage key="invoice.text.unitPrice" /></b></td>
<td class="shopcharthead"><b><app:storeMessage key="invoice.text.extendedPrice" /></b></td>
<td class="shopcharthead"><a href="customerAccountManagementInvoicesDetail.do?action=sort&sortField=productNumber"><b><app:storeMessage key="invoice.text.productNumber" /></b></a></td>
<td class="shopcharthead"><a href="customerAccountManagementInvoicesDetail.do?action=sort&sortField=productName"><b><app:storeMessage key="invoice.text.productName" /></b></a></td>
<td class="shopcharthead"><b><app:storeMessage key="invoice.text.pack" /></b></td>
<td class="shopcharthead"><b><app:storeMessage key="invoice.text.packSize" /></b></td>
<td class="shopcharthead"><a href="customerAccountManagementInvoicesDetail.do?action=sort&sortField=manufacturerName"><b><app:storeMessage key="invoice.text.manufName" /></b></a></td>
<td class="shopcharthead"><a href="customerAccountManagementInvoicesDetail.do?action=sort&sortField=manufacturerProductNo"><b><app:storeMessage key="invoice.text.manufNum" /></b></a></td>
<td class="shopcharthead"><a href="customerAccountManagementInvoicesDetail.do?action=sort&sortField=upc"><b><app:storeMessage key="invoice.text.upc" /></b></a></td>
<td class="shopcharthead"><b><app:storeMessage key="invoice.text.discounts" /></b></td>
</tr>


</thead>
<tbody id="resTblBdy">

<logic:iterate id="itemele" indexId="i" name="CUST_ACCT_MGT_INVOICE_DETAIL_FORM" property="resultList" scope="session"
type="com.cleanwise.service.api.value.InvoiceNetworkServiceData">
          <tr bgcolor="<%=ClwCustomizer.getEvenRowColor(session,(Integer)i)%>">
		<td>  <%-- Line --%>
                  <logic:present name="itemele" property="lineNumber">
                    <bean:write name="itemele" property="lineNumber"/>
		  </logic:present>
                </td>
		<td> <%-- Quantity --%>
                    <logic:present name="itemele" property="quantity">
                            <bean:write name="itemele" property="quantity"/>
                    </logic:present>
		</td>
		<td>  <%-- Quantity Unit of Measure --%>
                    <logic:present name="itemele" property="quantityUnitOfMeasure">
                            <bean:write name="itemele" property="quantityUnitOfMeasure"/>
                    </logic:present>
		</td>
		<td>  <%-- Unit Price --%>
                    <logic:present name="itemele" property="unitPrice">
                       <bean:write name="itemele" property="unitPrice"/>
                    </logic:present>
		</td>
		<td>  <%-- Extended Price --%>
                    <logic:present name="itemele" property="extendedPrice">
                      <bean:write name="itemele" property="extendedPrice"/>
                    </logic:present>
		</td>
		<td>  <%-- Product # --%>
                    <logic:present name="itemele" property="productNumber">
                            <bean:write name="itemele" property="productNumber"/>
                    </logic:present>
		</td>
		<td>  <%-- Product Name--%>
                    <logic:present name="itemele" property="productName">
                            <bean:write name="itemele" property="productName"/>
                    </logic:present>
		</td>
		<td>  <%-- Pack --%>
                    <logic:present name="itemele" property="pack">
                            <bean:write name="itemele" property="pack"/>
                    </logic:present>
		</td>
		<td>  <%-- Pack Size --%>
                    <logic:present name="itemele" property="packSize">
                            <bean:write name="itemele" property="packSize"/>
                    </logic:present>
		</td>
		<td>  <%-- Manufacturer Name --%>
                    <logic:present name="itemele" property="manufacturerName">
                            <bean:write name="itemele" property="manufacturerName"/>
                    </logic:present>
		</td>
		<td>  <%-- Manufacturer Product # --%>
                    <logic:present name="itemele" property="manufacturerProductNo">
                            <bean:write name="itemele" property="manufacturerProductNo"/>
                    </logic:present>
		</td>
		<td>  <%-- UPC --%>
                    <logic:present name="itemele" property="upc">
                            <bean:write name="itemele" property="upc"/>
                    </logic:present>
		</td>
		<td>  <%-- Discounts --%>
                    <logic:present name="itemele" property="discounts">
                            <bean:write name="itemele" property="discounts"/>
                    </logic:present>
		</td>

	</tr>
</logic:iterate>

</tbody>
</table>
