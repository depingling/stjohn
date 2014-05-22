<%@ page language="java" %>
<%@ page import="com.cleanwise.view.utils.Constants" %>
<%@ page import="com.cleanwise.view.utils.ClwCustomizer" %>
<%@ page import="com.cleanwise.service.api.util.RefCodeNames" %>
<%@ page import="java.util.Locale" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="com.cleanwise.service.api.value.*" %>

<%@ taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>
<%@ taglib uri="/WEB-INF/application.tld" prefix="app" %>
<%@ taglib uri="/WEB-INF/i18n.tld" prefix="i18n" %>
<% String storeDir=ClwCustomizer.getStoreDir(); %>
<bean:define id="ip" name="<%=Constants.IMAGES_PATH%>" scope="session"/>
<bean:define id="theForm" name="ORDER_BACKFILL_FORM" type="com.cleanwise.view.forms.OrderBackfillForm"/>
<table border="0" cellpadding="1" cellspacing="0" width="900" class="results">
<%
OrderItemDataVector items = theForm.getOrderStatusDetail().getOrderItemList();
OrderItemDataVector itemsAfter = theForm.getOrderStatusDetailAfter().getOrderItemList();

if(items!=null) {
for(int ii=0; ii<itemsAfter.size(); ii++) {
  OrderItemData itemAfter = (OrderItemData) itemsAfter.get(ii);
  OrderItemData item = null;
  int jj=0;
  for(;jj<items.size(); jj++) {
    item = (OrderItemData) items.get(jj);
    if(item.getItemId()==itemAfter.getItemId()) {
      break;
    }
  }
  if(jj==items.size()) {
    item = OrderItemData.createValue();
  }

int itemSkuNumAfter = itemAfter.getItemSkuNum(); //             NUMBER (38)
String itemShortDescAfter = itemAfter.getItemShortDesc(); //         VARCHAR (255)
if(itemShortDescAfter==null) itemShortDescAfter="";
String orderItemStatusCdAfter = itemAfter.getOrderItemStatusCd(); //     VARCHAR (30)
if(orderItemStatusCdAfter==null) orderItemStatusCdAfter="";
String ackStatusCdAfter = itemAfter.getAckStatusCd(); //           VARCHAR (30)
if(ackStatusCdAfter==null) ackStatusCdAfter="";
String distErpNumAfter = itemAfter.getDistErpNum(); //           VARCHAR (30)
if(distErpNumAfter==null) distErpNumAfter="";
String exceptionIndAfter = itemAfter.getExceptionInd(); //           VARCHAR (1)
if(exceptionIndAfter==null) exceptionIndAfter="";
int orderLineNumAfter = itemAfter.getOrderLineNum(); //         NUMBER (38)
int custLineNumAfter = itemAfter.getCustLineNum(); //          NUMBER (38)
int erpOrderLineNumAfter = itemAfter.getErpOrderLineNum(); //      NUMBER (38)
String erpOrderNumAfter = itemAfter.getErpOrderNum(); //           VARCHAR (50)
if(erpOrderNumAfter==null) erpOrderNumAfter="";
String erpPoNumAfter = itemAfter.getErpPoNum(); //              VARCHAR (50)
if(erpPoNumAfter==null) erpPoNumAfter="";
int erpPoLineNumAfter = itemAfter.getErpPoLineNum(); //         NUMBER (38)
Date erpPoDateAfter = itemAfter.getErpPoDate(); //             DATE
Date erpPoTimeAfter = itemAfter.getErpPoTime(); //             DATE
int custContractIdAfter = itemAfter.getCustContractId(); //        NUMBER (38)
String custContractShortDescAfter = itemAfter.getCustContractShortDesc(); // VARCHAR (30)
if(custContractShortDescAfter==null) custContractShortDescAfter="";
BigDecimal custContractPriceAfter = itemAfter.getCustContractPrice(); //     NUMBER (15,3)
if(custContractPriceAfter==null) custContractPriceAfter=new BigDecimal(0);

String itemUomAfter = itemAfter.getItemUom(); //                VARCHAR (30)
if(itemUomAfter==null) itemUomAfter="";
String itemPackAfter = itemAfter.getItemPack(); //               VARCHAR (30)
if(itemPackAfter==null) itemPackAfter="";
String itemSizeAfter = itemAfter.getItemSize(); //               VARCHAR (30)
if(itemSizeAfter==null) itemSizeAfter="";
BigDecimal itemCostAfter = itemAfter.getItemCost(); //               NUMBER (15,3)
if(itemCostAfter==null) itemCostAfter=new BigDecimal(0);;
//int itemQuantityAfter = itemAfter.getItemQuantity(); //           NUMBER (38)
String distItemSkuNumAfter = itemAfter.getDistItemSkuNum(); //      VARCHAR (30)
if(distItemSkuNumAfter==null) distItemSkuNumAfter="";
String distItemShortDescAfter = itemAfter.getDistItemShortDesc(); //    VARCHAR (255)
if(distItemShortDescAfter==null) distItemShortDescAfter="";
String distItemUomAfter = itemAfter.getDistItemUom(); //           VARCHAR (30)
if(distItemUomAfter==null) distItemUomAfter="";
String distItemPackAfter = itemAfter.getDistItemPack(); //          VARCHAR (30)
if(distItemPackAfter==null) distItemPackAfter="";
BigDecimal distItemCostAfter = itemAfter.getDistItemCost(); //          NUMBER (15,3)
if(distItemCostAfter==null) distItemCostAfter=new BigDecimal(0);
String distOrderNumAfter = itemAfter.getDistOrderNum(); //          VARCHAR (50)
if(distOrderNumAfter==null) distOrderNumAfter="";
int distItemQuantityAfter = itemAfter.getDistItemQuantity(); //      NUMBER (38)
int totalQuantityOrderedAfter = itemAfter.getTotalQuantityOrdered(); //  NUMBER (38)
int totalQuantityShippedAfter = itemAfter.getTotalQuantityShipped(); //  NUMBER (38)

String custItemSkuNumAfter = itemAfter.getCustItemSkuNum(); //       VARCHAR (15)
if(custItemSkuNumAfter==null) custItemSkuNumAfter="";
String custItemShortDescAfter = itemAfter.getCustItemShortDesc(); //    VARCHAR (255)
if(custItemShortDescAfter==null) custItemShortDescAfter="";
String custItemUomAfter = itemAfter.getCustItemUom(); //           VARCHAR (30)
if(custItemUomAfter==null) custItemUomAfter="";
String custItemPackAfter = itemAfter.getCustItemPack(); //          VARCHAR (30)
if(custItemPackAfter==null) custItemPackAfter="";

String manuItemSkuNumAfter = itemAfter.getManuItemSkuNum(); //       VARCHAR (15)
if(manuItemSkuNumAfter==null) manuItemSkuNumAfter="";
String manuItemShortDescAfter = itemAfter.getManuItemShortDesc(); //    VARCHAR (255)
if(manuItemShortDescAfter==null) manuItemShortDescAfter="";
BigDecimal manuItemMsrpAfter = itemAfter.getManuItemMsrp(); //          NUMBER (15,3)
if(manuItemMsrpAfter==null) manuItemMsrpAfter=new BigDecimal(0);
String manuItemUpcNumAfter = itemAfter.getManuItemUpcNum(); //       VARCHAR (15)
if(manuItemUpcNumAfter==null) manuItemUpcNumAfter="";
String manuPackUpcNumAfter = itemAfter.getManuPackUpcNum(); //       VARCHAR (15)
if(manuPackUpcNumAfter==null) manuPackUpcNumAfter="";
//==================================================
int itemSkuNum = item.getItemSkuNum(); //             NUMBER (38)
String itemShortDesc = item.getItemShortDesc(); //         VARCHAR (255)
if(itemShortDesc==null) itemShortDesc="";
String orderItemStatusCd = item.getOrderItemStatusCd(); //     VARCHAR (30)
if(orderItemStatusCd==null) orderItemStatusCd="";
String ackStatusCd = item.getAckStatusCd(); //           VARCHAR (30)
if(ackStatusCd==null) ackStatusCd="";
String distErpNum = item.getDistErpNum(); //           VARCHAR (30)
if(distErpNum==null) distErpNum="";
String exceptionInd = item.getExceptionInd(); //           VARCHAR (1)
if(exceptionInd==null) exceptionInd="";
int orderLineNum = item.getOrderLineNum(); //         NUMBER (38)
int custLineNum = item.getCustLineNum(); //          NUMBER (38)
int erpOrderLineNum = item.getErpOrderLineNum(); //      NUMBER (38)
String erpOrderNum = item.getErpOrderNum(); //           VARCHAR (50)
if(erpOrderNum==null) erpOrderNum="";
String erpPoNum = item.getErpPoNum(); //              VARCHAR (50)
if(erpPoNum==null) erpPoNum="";
int erpPoLineNum = item.getErpPoLineNum(); //         NUMBER (38)
Date erpPoDate = item.getErpPoDate(); //             DATE
Date erpPoTime = item.getErpPoTime(); //             DATE
int custContractId = item.getCustContractId(); //        NUMBER (38)
String custContractShortDesc = item.getCustContractShortDesc(); // VARCHAR (30)
if(custContractShortDesc==null) custContractShortDesc="";
BigDecimal custContractPrice = item.getCustContractPrice(); //     NUMBER (15,3)
if(custContractPrice==null) custContractPrice=new BigDecimal(0);

String itemUom = item.getItemUom(); //                VARCHAR (30)
if(itemUom==null) itemUom="";
String itemPack = item.getItemPack(); //               VARCHAR (30)
if(itemPack==null) itemPack="";
String itemSize = item.getItemSize(); //               VARCHAR (30)
if(itemSize==null) itemSize="";
BigDecimal itemCost = item.getItemCost(); //               NUMBER (15,3)
if(itemCost==null) itemCost=new BigDecimal(0);;
//int itemQuantity = item.getItemQuantity(); //           NUMBER (38)
String distItemSkuNum = item.getDistItemSkuNum(); //      VARCHAR (30)
if(distItemSkuNum==null) distItemSkuNum="";
String distItemShortDesc = item.getDistItemShortDesc(); //    VARCHAR (255)
if(distItemShortDesc==null) distItemShortDesc="";
String distItemUom = item.getDistItemUom(); //           VARCHAR (30)
if(distItemUom==null) distItemUom="";
String distItemPack = item.getDistItemPack(); //          VARCHAR (30)
if(distItemPack==null) distItemPack="";
BigDecimal distItemCost = item.getDistItemCost(); //          NUMBER (15,3)
if(distItemCost==null) distItemCost=new BigDecimal(0);
String distOrderNum = item.getDistOrderNum(); //          VARCHAR (50)
if(distOrderNum==null) distOrderNum="";
int distItemQuantity = item.getDistItemQuantity(); //      NUMBER (38)
int totalQuantityOrdered = item.getTotalQuantityOrdered(); //  NUMBER (38)
int totalQuantityShipped = item.getTotalQuantityShipped(); //  NUMBER (38)

String custItemSkuNum = item.getCustItemSkuNum(); //       VARCHAR (15)
if(custItemSkuNum==null) custItemSkuNum="";
String custItemShortDesc = item.getCustItemShortDesc(); //    VARCHAR (255)
if(custItemShortDesc==null) custItemShortDesc="";
String custItemUom = item.getCustItemUom(); //           VARCHAR (30)
if(custItemUom==null) custItemUom="";
String custItemPack = item.getCustItemPack(); //          VARCHAR (30)
if(custItemPack==null) custItemPack="";

String manuItemSkuNum = item.getManuItemSkuNum(); //       VARCHAR (15)
if(manuItemSkuNum==null) manuItemSkuNum="";
String manuItemShortDesc = item.getManuItemShortDesc(); //    VARCHAR (255)
if(manuItemShortDesc==null) manuItemShortDesc="";
BigDecimal manuItemMsrp = item.getManuItemMsrp(); //          NUMBER (15,3)
if(manuItemMsrp==null) manuItemMsrp=new BigDecimal(0);
String manuItemUpcNum = item.getManuItemUpcNum(); //       VARCHAR (15)
if(manuItemUpcNum==null) manuItemUpcNum="";
String manuPackUpcNum = item.getManuPackUpcNum(); //       VARCHAR (15)
if(manuPackUpcNum==null) manuPackUpcNum="";
String errColor = "red";
String regColor = "black";
String color;
BigDecimal decim = new BigDecimal(0.0001);
%>
<tr>
<td><b>Sku:</b></td>
<% color = (itemSkuNum==itemSkuNumAfter)?regColor:errColor; %>
<td><font color="<%=color%>"><%=itemSkuNum%></font></td>
<td><font color="<%=color%>"><%=itemSkuNumAfter%></font></td>
<td><b>Uom:</b></td>
<% color = (itemUom.equals(itemUomAfter))?regColor:errColor; %>
<td><font color="<%=color%>"><%=itemUom%></font></td>
<td><font color="<%=color%>"><%=itemUomAfter%></font></td>
</tr>
<tr>
<td><b>Short Desc:</b></td>
<% color = (itemShortDesc.equals(itemShortDescAfter))?regColor:errColor; %>
<td><font color="<%=color%>"><%=itemShortDesc%></font></td>
<td><font color="<%=color%>"><%=itemShortDescAfter%></font></td>
<td><b>Pack:</b></td>
<% color = (itemPack.equals(itemPackAfter))?regColor:errColor; %>
<td><font color="<%=color%>"><%=itemPack%></font></td>
<td><font color="<%=color%>"><%=itemPackAfter%></font></td>
</tr>
<tr>
<td><b>Status:</b></td>
<% color = (orderItemStatusCd.equals(orderItemStatusCdAfter))?regColor:errColor; %>
<td><font color="<%=color%>"><%=orderItemStatusCd%></font></td>
<% String itemStatus = "orderItemStatus["+ii+"]"; %>
<td>
  <html:select name="ORDER_BACKFILL_FORM" property="<%=itemStatus%>" styleClass="text">
  <html:option value="<%=RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED%>"><%=RefCodeNames.ORDER_ITEM_STATUS_CD.CANCELLED%></html:option>
  <html:option value="<%=RefCodeNames.ORDER_ITEM_STATUS_CD.PENDING_ERP_PO%>"><%=RefCodeNames.ORDER_ITEM_STATUS_CD.PENDING_ERP_PO%></html:option>
  <html:option value="<%=RefCodeNames.ORDER_ITEM_STATUS_CD.PENDING_REVIEW%>"><%=RefCodeNames.ORDER_ITEM_STATUS_CD.PENDING_REVIEW%></html:option>
  <html:option value="<%=RefCodeNames.ORDER_ITEM_STATUS_CD.PENDING_FULFILLMENT%>"><%=RefCodeNames.ORDER_ITEM_STATUS_CD.PENDING_FULFILLMENT%></html:option>
  <html:option value="<%=RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR%>"><%=RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR%></html:option>
  <html:option value="<%=RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR_FAILED%>"><%=RefCodeNames.ORDER_ITEM_STATUS_CD.SENT_TO_DISTRIBUTOR_FAILED%></html:option>
  <html:option value="<%=RefCodeNames.ORDER_ITEM_STATUS_CD.INVOICED%>"><%=RefCodeNames.ORDER_ITEM_STATUS_CD.INVOICED%></html:option>
  <html:option value="<%=RefCodeNames.ORDER_ITEM_STATUS_CD.PO_ACK_SUCCESS%>"><%=RefCodeNames.ORDER_ITEM_STATUS_CD.PO_ACK_SUCCESS%></html:option>
  <html:option value="<%=RefCodeNames.ORDER_ITEM_STATUS_CD.PO_ACK_ERROR%>"><%=RefCodeNames.ORDER_ITEM_STATUS_CD.PO_ACK_ERROR%></html:option>
  <html:option value="<%=RefCodeNames.ORDER_ITEM_STATUS_CD.PO_ACK_REJECT%>"><%=RefCodeNames.ORDER_ITEM_STATUS_CD.PO_ACK_REJECT%></html:option>
  </html:select>
</td>
<td><b>Size:</b></td>
<% color = (itemSize.equals(itemSizeAfter))?regColor:errColor; %>
<td><font color="<%=color%>"><%=itemSize%></font></td>
<td><font color="<%=color%>"><%=itemSizeAfter%></font></td>
</tr>
<tr>
<td><b>Ack Status:</b></td>
<% color = (ackStatusCd.equals(ackStatusCdAfter))?regColor:errColor; %>
<td><font color="<%=color%>"><%=ackStatusCd%></font></td>
<% String ackStatus = "ackStatus["+ii+"]"; %>
<td>
  <html:select name="ORDER_BACKFILL_FORM" property="<%=ackStatus%>" styleClass="text">
  <html:option value="<%=RefCodeNames.ACK_STATUS_CD.CUST_ACK_SUCCESS%>"><%=RefCodeNames.ACK_STATUS_CD.CUST_ACK_SUCCESS%></html:option>
  <html:option value="<%=RefCodeNames.ACK_STATUS_CD.CANCELLED_ACK_SUCCESS%>"><%=RefCodeNames.ACK_STATUS_CD.CANCELLED_ACK_SUCCESS%></html:option>
  <html:option value="<%=RefCodeNames.ACK_STATUS_CD.CANCELLED_ACK_FAILED%>"><%=RefCodeNames.ACK_STATUS_CD.CANCELLED_ACK_FAILED%></html:option>
  <html:option value="<%=RefCodeNames.ACK_STATUS_CD.CUST_ACK_FAILED%>"><%=RefCodeNames.ACK_STATUS_CD.CUST_ACK_FAILED%></html:option>
  </html:select>
</td>

<td><b>Dist.Sku#:</b></td>
<% color = (distItemSkuNum.equals(distItemSkuNumAfter))?regColor:errColor; %>
<td><font color="<%=color%>"><%=distItemSkuNum%></font></td>
<td><font color="<%=color%>"><%=distItemSkuNumAfter%></font></td>
</tr>
<tr>
<td><b>Distributor Erp#:</b></td>
<% color = (distErpNum.equals(distErpNumAfter))?regColor:errColor; %>
<td><font color="<%=color%>"><%=distErpNum%></font></td>
<td><font color="<%=color%>"><%=distErpNumAfter%></font></td>
<td><b>Dist.Item Uom</b></td>
<% color = (distItemUom.equals(distItemUomAfter))?regColor:errColor; %>
<td><font color="<%=color%>"><%=distItemUom%></font></td>
<td><font color="<%=color%>"><%=distItemUomAfter%></font></td>
</tr>
<tr>
<td><b>Distributor Name:</b></td>
<% color = (distItemShortDesc.equals(distItemShortDescAfter))?regColor:errColor; %>
<td><font color="<%=color%>"><%=distItemShortDesc%></font></td>
<td><font color="<%=color%>"><%=distItemShortDescAfter%></font></td>
<td><b>Dist.Item Pack:</b></td>
<% color = (distItemPack.equals(distItemPackAfter))?regColor:errColor; %>
<td><font color="<%=color%>"><%=distItemPack%></font></td>
<td><font color="<%=color%>"><%=distItemPackAfter%></font></td>
</tr>
<tr>
<td><b>Order Line#:</b></td>
<% color = (orderLineNum==orderLineNumAfter)?regColor:errColor; %>
<td><font color="<%=color%>"><%=orderLineNum%></font></td>
<td><font color="<%=color%>"><%=orderLineNumAfter%></font></td>
<td><b>Mfg.Name:</b></td>
<% color = (manuItemShortDesc.equals(manuItemShortDescAfter))?regColor:errColor; %>
<td><font color="<%=color%>"><%=manuItemShortDesc%></font></td>
<td><font color="<%=color%>"><%=manuItemShortDescAfter%></font></td>
</tr>
<tr>
<td><b>Customer Line#:</b></td>
<% color = (custLineNum==custLineNumAfter)?regColor:errColor; %>
<td><font color="<%=color%>"><%=custLineNum%></font></td>
<td><font color="<%=color%>"><%=custLineNumAfter%></font></td>
<td><b>Mfg.Sku#:</b></td>
<% color = (manuItemSkuNum.equals(manuItemSkuNumAfter))?regColor:errColor; %>
<td><font color="<%=color%>"><%=manuItemSkuNum%></font></td>
<td><font color="<%=color%>"><%=manuItemSkuNumAfter%></font></td>
</tr>
<tr>
<td><b>Erp Order#:</b></td>
<% color = (erpOrderNum.equals(erpOrderNumAfter))?regColor:errColor; %>
<td><font color="<%=color%>"><%=erpOrderNum%></font></td>
<td><font color="<%=color%>"><%=erpOrderNumAfter%></font></td>
<td><b>Mfg.Msrp:</b></td>
<% color = (manuItemMsrp.equals(manuItemMsrpAfter))?regColor:errColor; %>
<td><font color="<%=color%>">
  <i18n:formatCurrency value="<%=manuItemMsrp%>" locale="<%=Locale.US%>"/>
</font></td>
<td><font color="<%=color%>">
  <i18n:formatCurrency value="<%=manuItemMsrpAfter%>" locale="<%=Locale.US%>"/>
</font></td>

</tr>
<tr>
<td><b>Erp Order Line#:</b></td>
<% color = (erpOrderLineNum==erpOrderLineNumAfter)?regColor:errColor; %>
<td><font color="<%=color%>"><%=erpOrderLineNum%></font></td>
<td><font color="<%=color%>"><%=erpOrderLineNumAfter%></font></td>
<td><b>Mfg.Upc#:</b></td>
<% color = (manuItemUpcNum.equals(manuItemUpcNumAfter))?regColor:errColor; %>
<td><font color="<%=color%>"><%=manuItemUpcNum%></font></td>
<td><font color="<%=color%>"><%=manuItemUpcNumAfter%></font></td>
</tr>
<tr>
<td><b>PO Num:</b></td>
<% color = (erpPoNum.equals(erpPoNumAfter))?regColor:errColor; %>
<td><font color="<%=color%>"><%=erpPoNum%></font></td>
<td><font color="<%=color%>"><%=erpPoNumAfter%></font></td>
<td><b>Mfg.Pack&nbsp;Upc#:</b></td>
<% color = (manuPackUpcNum.equals(manuPackUpcNumAfter))?regColor:errColor; %>
<td><font color="<%=color%>"><%=manuPackUpcNum%></font></td>
<td><font color="<%=color%>"><%=manuPackUpcNumAfter%></font></td>
</tr>
<tr>
<td><b>PO Line#:</b></td>
<% color = (erpPoLineNum==erpPoLineNumAfter)?regColor:errColor; %>
<td><font color="<%=color%>"><%=erpPoLineNum%></font></td>
<td><font color="<%=color%>"><%=erpPoLineNumAfter%></font></td>
<td><b>Cust.Item:</b></td>
<% color = (custItemShortDesc.equals(custItemShortDescAfter))?regColor:errColor; %>
<td><font color="<%=color%>"><%=custItemShortDesc%></font></td>
<td><font color="<%=color%>"><%=custItemShortDescAfter%></font></td>
</tr>
<tr>
<td><b>PO Date:</b></td>
<% color = errColor;
   if(erpPoDate!=null && erpPoDate.equals(erpPoDateAfter)) color = regColor;
%>
<% if(erpPoDate!=null) { %>
<td><font color="<%=color%>">
  <i18n:formatDate value="<%=erpPoDate%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>&nbsp;
  <i18n:formatDate value="<%=erpPoTime%>" pattern="hh:mm aaa" locale="<%=Locale.US%>"/>
</font></td>
<% } else { %>
<td>&nbsp;</td>
<% } %>
<% if(erpPoDateAfter!=null) { %>
<td><font color="<%=color%>">
  <i18n:formatDate value="<%=erpPoDateAfter%>" pattern="MM/dd/yyyy" locale="<%=Locale.US%>"/>&nbsp;
  <i18n:formatDate value="<%=erpPoTimeAfter%>" pattern="hh:mm aaa" locale="<%=Locale.US%>"/>
</font></td>
<% } else { %>
<td>&nbsp;</td>
<% } %>
<td><b>Cust.Item&nbsp;Sku#:</b></td>
<% color = (custItemSkuNum.equals(custItemSkuNumAfter))?regColor:errColor; %>
<td><font color="<%=color%>"><%=custItemSkuNum%></font></td>
<td><font color="<%=color%>"><%=custItemSkuNumAfter%></font></td>
</tr>
<tr>
<td><b>Dist.Item Cost:</b></td>
<% color = (distItemCost.subtract(distItemCostAfter).abs().compareTo(decim)<0)?regColor:errColor; %>
<td><font color="<%=color%>">
  <i18n:formatCurrency value="<%=distItemCost%>" locale="<%=Locale.US%>"/>
</font></td>
<td><font color="<%=color%>">
  <i18n:formatCurrency value="<%=distItemCostAfter%>" locale="<%=Locale.US%>"/>
</font></td>
<td><b>Cust.Item&nbsp;Uom:</b></td>
<% color = (custItemUom.equals(custItemUomAfter))?regColor:errColor; %>
<td><font color="<%=color%>"><%=custItemUom%></font></td>
<td><font color="<%=color%>"><%=custItemUomAfter%></font></td>
</tr>
<tr>
<td><b>Contract Price:</b></td>
<% color = (custContractPrice.subtract(custContractPriceAfter).abs().compareTo(decim)<0)?regColor:errColor; %>
<td><font color="<%=color%>">
  <i18n:formatCurrency value="<%=custContractPrice%>" locale="<%=Locale.US%>"/>
</font></td>
<td><font color="<%=color%>">
  <i18n:formatCurrency value="<%=custContractPriceAfter%>" locale="<%=Locale.US%>"/>
</font></td>
<td><b>Cust.Item&nbsp;Pack:</b></td>
<% color = (custItemPack.equals(custItemPackAfter))?regColor:errColor; %>
<td><font color="<%=color%>"><%=custItemPack%></font></td>
<td><font color="<%=color%>"><%=custItemPackAfter%></font></td>
</tr>
<tr>
<tr>
<td><b>Total&nbsp;Qty&nbsp;Ordered:</b></td>
<% color = (totalQuantityOrdered==totalQuantityOrderedAfter)?regColor:errColor; %>
<td><font color="<%=color%>"><%=totalQuantityOrdered%></font></td>
<td><font color="<%=color%>"><%=totalQuantityOrderedAfter%></font></td>
<td><b></b></td>
<td></td>
<td></td>
</tr>
<tr>
<td><b>Total&nbsp;Qty&nbsp;Shipped:</b></td>
<% color = (totalQuantityShipped==totalQuantityShippedAfter)?regColor:errColor; %>
<td><font color="<%=color%>"><%=totalQuantityShipped%></font></td>
<td><font color="<%=color%>"><%=totalQuantityShippedAfter%></font></td>
<td><b></b></td>
<td></td>
<td></td>
</tr>

<tr>
<td><b>Dist.Order#:</b></td>
<% color = (distOrderNum.equals(distOrderNumAfter))?regColor:errColor; %>
<td><font color="<%=color%>"><%=distOrderNum%></font></td>
<td><font color="<%=color%>"><%=distOrderNumAfter%></font></td>
<td><b></b></td>
<td></td>
<td></td>
</tr>

<tr>
<td><b>Exception Flag</b></td>
<% color = (exceptionInd.equals(exceptionIndAfter))?regColor:errColor; %>
<td><font color="<%=color%>"><%=exceptionInd%></font></td>
<td><font color="<%=color%>"><%=exceptionIndAfter%></font></td>
<td><b></b></td>
<td></td>
<td></td>
</tr>
<tr>
<td><b>Contract Id:</b></td>
<% color = (custContractId==custContractIdAfter)?regColor:errColor; %>
<td><font color="<%=color%>"><%=custContractId%></font></td>
<td><font color="<%=color%>"><%=custContractIdAfter%></font></td>
<td><b></b></td>
<td></td>
<td></td>
</tr>
<tr>
<tr>
<td><b>Contract Short Desc:</b></td>
<% color = (custContractShortDesc.equals(custContractShortDescAfter))?regColor:errColor; %>
<td><font color="<%=color%>"><%=custContractShortDesc%></font></td>
<td><font color="<%=color%>"><%=custContractShortDescAfter%></font></td>
<td><b></b></td>
<td></td>
<td></td>
</tr>
<tr><td colspan="6" class="mainbody"><img src="/<%=storeDir%>/<%=ip%>images/cw_spacer.gif" width="1" HEIGHT="1"></td></tr>
<% } %>
<% } %>
</table>

