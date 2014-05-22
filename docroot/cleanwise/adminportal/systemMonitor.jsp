<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/pager-taglib.tld" prefix="pg" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>


<%
int idx=0;
int maxCols = 3;
%>

<div class="text">
<font color=red>
<html:errors/>
</font>
<table>
        <tr>
                <td>
                        <table>
                        <tr><td colspan=2 class="mediumheader"><b>Requires Attention</b></td></tr>
                        <logic:present name="daoErrors">
                                <jsp:useBean id="daoErrors" scope="request" type="java.util.List"/>
                                <tr><td colspan=2><b>Data Access Inconsistencies:</b> <%=daoErrors.size()%></td></tr>
                                <%idx=0;%>
                                <tr>
                                <logic:iterate id="element" name="daoErrors" type="String">
                                         <%if (idx==0){%>
                                        <tr>
                                        <%}%>
                                        <td><bean:write name="element"/></td>
                                        <%if (idx>=maxCols){idx=-1;%>
                                        </tr>
                                        <%}%>
                                        <%idx++;%>
                                </logic:iterate>
                                </tr>
                        </logic:present>

                        <logic:present name="failedOrders">
                        <jsp:useBean id="failedOrders" scope="request" class="com.cleanwise.service.api.value.OrderDataVector"/>
                        <tr><td colspan=2><b>Number of Orders:</b> <%=failedOrders.size()%></td></tr>
                        <%idx=0;%>
                        <tr>
                        <logic:iterate id="element" collection="<%= failedOrders %>" type="com.cleanwise.service.api.value.OrderData">
                                <%if (idx==0){%>
                                        <tr>
                                <%}%>
                                <td>
                                <bean:define id="id" name="element" property="orderId"/>
                                <bean:write name="element" property="orderNum"/>-
                                <bean:write name="element" property="orderStatusCd"/>
                                </td>
                                <%if (idx>=maxCols){idx=-1;%>
                                        </tr>
                                <%}%>
                                <%idx++;%>
                        </logic:iterate>
                        </tr>
                        </logic:present>

                        <logic:present name="pendingOrders">
                        <jsp:useBean id="pendingOrders" scope="request" class="com.cleanwise.service.api.value.OrderDataVector"/>
                        <tr><td>&nbsp;</td></tr>
                        <tr><td colspan=2><b>Number of pending orders:</b> <%=pendingOrders.size()%></td></tr>
                        <%idx=0;%>
                        <logic:iterate id="element" collection="<%= pendingOrders %>" type="com.cleanwise.service.api.value.OrderData">
                                <%if (idx==0){%>
                                        <tr>
                                <%}%>
                                <td>
                                <bean:define id="id" name="element" property="orderId"/>
                                <%String link="../console/orderOpDetail.do?action=view&id="+id;%>
                                <a href="<%=link%>"><bean:write name="element" property="orderNum"/></a>-
                                <bean:write name="element" property="orderStatusCd"/>
                                </td>
                                <%if (idx>=maxCols){idx=-1;%>
                                        </tr>
                                <%}%>
                                <%idx++;%>
                        </logic:iterate>
                        </logic:present>

                        <logic:present name="failedDistInvoice">
                        <jsp:useBean id="failedDistInvoice" scope="request" class="com.cleanwise.service.api.value.InvoiceDistDataVector"/>
                        <tr><td>&nbsp;</td></tr>
                        <tr><td colspan=2><b>Number of distributor invoices:</b> <%=failedDistInvoice.size()%></td></tr>
                        <%idx=0;%>
                        <logic:iterate id="element" collection="<%= failedDistInvoice %>" type="com.cleanwise.service.api.value.InvoiceDistData">
                                <%if (idx==0){%>
                                        <tr>
                                <%}%>
                                <td>
                                <bean:define id="id" name="element" property="invoiceDistId"/>
                                <%String link="../console/exceptionInvoiceDistSearch.do?action=invoiceDistDetail&invoiceDistId="+id;%>
                                <a href="<%=link%>"><bean:write name="element" property="invoiceNum"/></a>-
                                <bean:write name="element" property="invoiceStatusCd"/>
                                </td>
                                <%if (idx>=maxCols){idx=-1;%>
                                        </tr>
                                <%}%>
                                <%idx++;%>
                        </logic:iterate>
                        </logic:present>

                        <logic:present name="failedCustInvoice">
                        <jsp:useBean id="failedCustInvoice" scope="request" class="com.cleanwise.service.api.value.InvoiceCustDataVector"/>
                        <tr><td>&nbsp;</td></tr>
                        <tr><td colspan=2><b>Number of customer invoices:</b> <%=failedCustInvoice.size()%></td></tr>
                        <%idx=0;%>
                        <logic:iterate id="element" collection="<%= failedCustInvoice %>" type="com.cleanwise.service.api.value.InvoiceCustData">
                                <%if (idx==0){%>
                                        <tr>
                                <%}%>
                                <td>
                                <bean:define id="id" name="element" property="orderId"/>
                                <bean:write name="element" property="invoiceNum"/>-
                                <bean:write name="element" property="invoiceStatusCd"/>
                                </td>
                                <%if (idx>=maxCols){idx=-1;%>
                                        </tr>
                                <%}%>
                                <%idx++;%>
                        </logic:iterate>
                        </logic:present>

                        <logic:present name="failedCITCustInvoice">
                        <jsp:useBean id="failedCITCustInvoice" scope="request" class="com.cleanwise.service.api.value.InvoiceCustDataVector"/>
                        <tr><td>&nbsp;</td></tr>
                        <tr><td colspan=2><b>Number of CIT customer invoices:</b> <%=failedCITCustInvoice.size()%></td></tr>
                        <%idx=0;%>
                        <logic:iterate id="element" collection="<%= failedCITCustInvoice %>" type="com.cleanwise.service.api.value.InvoiceCustData">
                                <%if (idx==0){%>
                                        <tr>
                                <%}%>
                                <td>
                                <bean:define id="id" name="element" property="orderId"/>
                                <bean:write name="element" property="invoiceNum"/>-
                                <bean:write name="element" property="citStatusCd"/>
                                </td>
                                <%if (idx>=maxCols){idx=-1;%>
                                        </tr>
                                <%}%>
                                <%idx++;%>
                        </logic:iterate>
                        </logic:present>

                        <logic:present name="failedOrderBatchEntries">
                        <jsp:useBean id="failedOrderBatchEntries" scope="request" class="com.cleanwise.service.api.value.OrderBatchLogDataVector"/>
                        <tr><td>&nbsp;</td></tr>
                        <tr><td colspan=2><b>Number of order batch log entries (Auto Orders):</b> <%=failedOrderBatchEntries.size()%></td></tr>
                        <%idx=0;%>
                        <logic:iterate id="element" collection="<%= failedOrderBatchEntries %>" type="com.cleanwise.service.api.value.OrderBatchLogData">
                                <%if (idx==0){%>
                                        <tr>
                                <%}%>
                                <td>
                                <bean:write name="element" property="orderBatchLogId"/>&nbsp;
                                <bean:write name="element" property="orderBatchStatusCd"/>&nbsp;
                                <bean:write name="element" property="message"/>
                                </td>
                                <%if (idx>=maxCols){idx=-1;%>
                                        </tr>
                                <%}%>
                                <%idx++;%>
                        </logic:iterate>
                        </logic:present>

                        <logic:present name="failedPurchaseOrders">
                        <jsp:useBean id="failedPurchaseOrders" scope="request" class="com.cleanwise.service.api.value.PurchaseOrderDataVector"/>
                        <tr><td>&nbsp;</td></tr>
                        <tr><td colspan=2><b>Number of purchase orders:</b> <%=failedPurchaseOrders.size()%></td></tr>
                        <%idx=0;%>
                        <logic:iterate id="element" collection="<%= failedPurchaseOrders %>" type="com.cleanwise.service.api.value.PurchaseOrderData">
                                <%if (idx==0){%>
                                        <tr>
                                <%}%>
                                <td>
                                <bean:define id="id" name="element" property="purchaseOrderId"/>
                                <%String link="../console/purchaseOrderOpDetail.do?action=view&id="+id;%>
                                <a href="<%=link%>"><bean:write name="element" property="erpPoNum"/></a>-
                                <bean:write name="element" property="purchaseOrderStatusCd"/>
                                </td>
                                <%if (idx>=maxCols){idx=-1;%>
                                        </tr>
                                <%}%>
                                <%idx++;%>
                        </logic:iterate>
                        </logic:present>

                        <logic:present name="failedRemittanceData">
                        <jsp:useBean id="failedRemittanceData" scope="request" class="com.cleanwise.service.api.value.RemittanceDataVector"/>
                        <tr><td>&nbsp;</td></tr>
                        <tr><td colspan=2><b>Number of Remittances:</b> <%=failedRemittanceData.size()%></td></tr>
                        <%idx=0;%>
                        <logic:iterate id="element" collection="<%= failedRemittanceData %>" type="com.cleanwise.service.api.value.RemittanceData">
                                <%if (idx==0){%>
                                        <tr>
                                <%}%>
                                <td>
                                <bean:define id="id" name="element" property="remittanceId"/>
                                <bean:write name="element" property="paymentReferenceNumber"/>-
                                <bean:write name="element" property="remittanceStatusCd"/>
                                </td>
                                <%if (idx>=maxCols){idx=-1;%>
                                        </tr>
                                <%}%>
                                <%idx++;%>
                        </logic:iterate>
                        </logic:present>

                        <logic:present name="failedRemittanceDetailData">
                        <jsp:useBean id="failedRemittanceDetailData" scope="request" class="com.cleanwise.service.api.value.RemittanceDetailDataVector"/>
                        <tr><td>&nbsp;</td></tr>
                        <tr><td colspan=2><b>Number of Remittance Detail records:</b> <%=failedRemittanceDetailData.size()%></td></tr>
                        <%idx=0;%>
                        <logic:iterate id="element" collection="<%= failedRemittanceDetailData %>" type="com.cleanwise.service.api.value.RemittanceDetailData">
                                <%if (idx==0){%>
                                        <tr>
                                <%}%>
                                <td>
                                <bean:define id="id" name="element" property="remittanceId"/>
                                <bean:write name="element" property="invoiceNumber"/>-
                                <bean:write name="element" property="remittanceDetailStatusCd"/>
                                </td>
                                <%if (idx>=maxCols){idx=-1;%>
                                        </tr>
                                <%}%>
                                <%idx++;%>
                        </logic:iterate>
                        </logic:present>

                        <logic:present name="unparsableRemittanceData">
                        <jsp:useBean id="unparsableRemittanceData" scope="request" class="com.cleanwise.service.api.value.RemittancePropertyDataVector"/>
                        <tr><td>&nbsp;</td></tr>
                        <tr><td colspan=2><b>Number unparsable remittance lines:</b> <%=unparsableRemittanceData.size()%></td></tr>
<%--                        <%if (unparsableRemittanceData.size()>0){%>
                                <tr><td colspan=6><a href="../console/exceptionOpRemittances.do">Click here</a> then press the button "Fetch Unparsable Remittance Data".</td></tr>
                        <%}%>--%>
                        </logic:present>


                        <logic:present name="freeMemory">
                                <jsp:useBean id="freeMemory" scope="request" type="java.lang.Long"/>
                                <jsp:useBean id="totalMemory" scope="request" type="java.lang.Long"/>
                                <tr><td>&nbsp;</td></tr>
                                <tr><td colspan=2 class="mediumheader"><b>Memory Information</b></td></tr>
                                <tr><td><b>Free Memory:</b></td><td> <bean:write name="freeMemory"/></td></tr>
                                <tr><td><b>Total Memory:</b></td><td> <bean:write name="totalMemory"/></td></tr>
                                <tr><td colspan=6><a href="systemhome.do?action=freeMemory">Free Availiable Memory (will slow system preformance).</a></td></tr>
                                </table>
                        </logic:present>
                </td>
        </tr>
</table>
</div>