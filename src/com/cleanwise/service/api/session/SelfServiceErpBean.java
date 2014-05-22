package com.cleanwise.service.api.session;

import com.cleanwise.service.api.APIAccess;
import com.cleanwise.service.api.dao.*;
import com.cleanwise.service.api.framework.ApplicationServicesAPI;
import com.cleanwise.service.api.util.*;
import com.cleanwise.service.api.value.*;

import javax.ejb.CreateException;
import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.Vector;

/**
 * Title:        SelfServiceErp
 * Description:  Bean implementation for SelfServiceErp Session Bean
 * Purpose:      Self-served erp system
 * Copyright:    Copyright (c) 2008
 * Company:      CleanWise, Inc.
 * Date:         02.12.2008
 * Time:         8:58:02
 *
 * @author Alexander Chickin, TrinitySoft, Inc.
 */
public class SelfServiceErpBean extends ApplicationServicesAPI {

    /**
     * Describe <code>ejbCreate</code> method here.
     *
     * @throws javax.ejb.CreateException if an error occurs
     * @throws java.rmi.RemoteException  if an error occurs
     */
    public void ejbCreate() throws CreateException, RemoteException {
    }

    public InvoiceCustDescData service(int pInvoiceId) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return service(conn, pInvoiceId, false);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }

    public InvoiceCustDescData service(int pInvoiceId, boolean pReadOnly) throws RemoteException {
        Connection conn = null;
        try {
            conn = getConnection();
            return service(conn, pInvoiceId, pReadOnly);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeConnection(conn);
        }
    }

    private InvoiceCustDescData service(Connection pCon, int pInvoiceDistId, boolean pReadOnly) throws Exception {

        logInfo("service => BEGIN.pInvoiceDistId: " + pInvoiceDistId);

        Order orderEjb = APIAccess.getAPIAccess().getOrderAPI();
        Distributor distributorEjb = APIAccess.getAPIAccess().getDistributorAPI();
        Site siteEjb = APIAccess.getAPIAccess().getSiteAPI();

        String pUser = "SelfServiceErp";
        Vector<OrderPropertyData> errors = new Vector<OrderPropertyData>();

        InvoiceDistData invoiceDist = orderEjb.getInvoiceDist(pInvoiceDistId);
        InvoiceDistDetailDataVector invDistItems = orderEjb.getInvoiceDistDetailCollection(pInvoiceDistId);

        OrderData order = orderEjb.getOrder(invoiceDist.getOrderId());
        OrderItemDataVector orderItems = orderEjb.getOrderItemCollection(invoiceDist.getOrderId());

        SelfServiceOrderDescView ssOrderDesc = getSelfServiceOrderDesc(pCon, order, orderItems, invDistItems);

        // Create the customer invoice object
        InvoiceCustData invoiceCustD = InvoiceCustData.createValue();

        invoiceCustD.setInvoiceDate(invoiceDist.getInvoiceDate());

        invoiceCustD.setShippingName(ssOrderDesc.getShippingAddress().getShortDesc());
        invoiceCustD.setShippingAddress1(ssOrderDesc.getShippingAddress().getAddress1());
        invoiceCustD.setShippingAddress2(ssOrderDesc.getShippingAddress().getAddress2());
        invoiceCustD.setShippingAddress3(ssOrderDesc.getShippingAddress().getAddress3());
        invoiceCustD.setShippingAddress4(ssOrderDesc.getShippingAddress().getAddress4());
        invoiceCustD.setShippingCity(ssOrderDesc.getShippingAddress().getCity());
        invoiceCustD.setShippingState(ssOrderDesc.getShippingAddress().getStateProvinceCd());
        invoiceCustD.setShippingPostalCode(ssOrderDesc.getShippingAddress().getPostalCode());
        invoiceCustD.setShippingCountry(ssOrderDesc.getShippingAddress().getCountryCd());

        //if using a credit card do not populate the billing information.  This
        //would be an invoice that was manually generated as the cc stuff happens
        //elsewhere outside the auto generation of invoices, so a customer should never
        //see it.
        CreditCardDAO ccdao = new CreditCardDAO(pCon);
        OrderCreditCardData card = ccdao.getOrderCreditCardDataFromOrderId(order.getOrderId());
        if (card == null) {
            invoiceCustD.setBillToName(ssOrderDesc.getBillingAddress().getShortDesc());
            invoiceCustD.setBillToAddress1(ssOrderDesc.getBillingAddress().getAddress1());
            invoiceCustD.setBillToAddress2(ssOrderDesc.getBillingAddress().getAddress2());
            invoiceCustD.setBillToAddress3(ssOrderDesc.getBillingAddress().getAddress3());
            invoiceCustD.setBillToAddress4(ssOrderDesc.getBillingAddress().getAddress4());
            invoiceCustD.setBillToCity(ssOrderDesc.getBillingAddress().getCity());
            invoiceCustD.setBillToState(ssOrderDesc.getBillingAddress().getStateProvinceCd());
            invoiceCustD.setBillToPostalCode(ssOrderDesc.getBillingAddress().getPostalCode());
            invoiceCustD.setBillToCountry(ssOrderDesc.getBillingAddress().getCountryCd());
        } else {
            invoiceCustD.setBillToName(card.getCreditCardType());
            invoiceCustD.setBillToAddress1("");
            invoiceCustD.setBillToAddress2("");
            invoiceCustD.setBillToAddress3("");
            invoiceCustD.setBillToAddress4("");
            invoiceCustD.setBillToCity("");
            invoiceCustD.setBillToState("");
            invoiceCustD.setBillToPostalCode("");
            invoiceCustD.setBillToCountry("");
        }

        double subTotal = Utility.bdNN(ssOrderDesc.getSubTotal()).doubleValue();
        //double salesTax = TaxUtilAvalara.calculateCustomerItemTaxes(ssOrderDesc.getItems()).doubleValue(); //old code
        
        
        //OrderAddressData oad = ssOrderDesc.getShippingAddress(); //???
        // create and populate AddressData Object from oad Object  //???      
        int siteId = order.getSiteId();
        int acctId = order.getAccountId();
        int storeId = order.getStoreId();
        double salesTax = TaxUtilAvalara.calculateCustomerItemTaxes(ssOrderDesc.getItems(), siteId, acctId, storeId, distributorEjb, siteEjb).doubleValue();
        
        double discount = 0;
        double miscTotal = 0;

        double frtCharge;
        double fuelSurcharge;
        double netDue;

        if (orderPartHasBeenServiced(pCon, invoiceDist.getOrderId())) {
            frtCharge = 0;
            fuelSurcharge = 0;
            logInfo("service => orderPartHasBeenServiced: TRUE");
        } else {
            frtCharge = ssOrderDesc.getShippingCost().doubleValue();
            fuelSurcharge = Utility.bdNN(ssOrderDesc.getFuelSurcharge()).doubleValue();
            logInfo("service => orderPartHasBeenServiced: FALSE");
        }

        netDue = subTotal + fuelSurcharge + frtCharge + miscTotal + salesTax - discount;

        invoiceCustD.setSubTotal(new BigDecimal(subTotal).setScale(2, BigDecimal.ROUND_HALF_UP));
        invoiceCustD.setFreight(new BigDecimal(frtCharge).setScale(2, BigDecimal.ROUND_HALF_UP));
        invoiceCustD.setFuelSurcharge(new BigDecimal(fuelSurcharge).setScale(2, BigDecimal.ROUND_HALF_UP));
        invoiceCustD.setDiscounts(new BigDecimal(discount).setScale(2, BigDecimal.ROUND_HALF_UP));
        invoiceCustD.setMiscCharges(new BigDecimal(miscTotal).setScale(2, BigDecimal.ROUND_HALF_UP));
        invoiceCustD.setNetDue(new BigDecimal(netDue).setScale(2, BigDecimal.ROUND_HALF_UP));
        invoiceCustD.setSalesTax(new BigDecimal(salesTax).setScale(2, BigDecimal.ROUND_HALF_UP));

        invoiceCustD.setInvoiceStatusCd(RefCodeNames.INVOICE_STATUS_CD.ERP_RELEASED);
        invoiceCustD.setErpSystemCd(invoiceDist.getErpSystemCd());
        invoiceCustD.setErpPoNum(invoiceDist.getErpPoNum());
        invoiceCustD.setOriginalInvoiceNum(null);

        invoiceCustD.setStoreId(invoiceDist.getStoreId());
        invoiceCustD.setAccountId(order.getAccountId());
        invoiceCustD.setSiteId(order.getSiteId());
        invoiceCustD.setOrderId(order.getOrderId());

        invoiceCustD.setInvoiceType(RefCodeNames.INVOICE_TYPE_CD.IN);
        invoiceCustD.setCitStatusCd(RefCodeNames.CIT_STATUS_CD.PENDING);
        invoiceCustD.setInvoiceNum(getCustomerInvoiceNum(invoiceDist));

        if (!pReadOnly) {
            invoiceCustD = addCustomerInvoice(pCon, invoiceCustD, pUser);
        }

        int invoiceCustId = invoiceCustD.getInvoiceCustId();
        //Items
        InvoiceCustDetailDataVector custItems = new InvoiceCustDetailDataVector();
        boolean servicedItemsIsClosed = true;
        for (int ii = 0; ii < ssOrderDesc.getItems().size(); ii++) {

            SelfServiceOrderItemDescView ssOrderItem = (SelfServiceOrderItemDescView) ssOrderDesc.getItems().get(ii);

            InvoiceCustDetailData icdD = InvoiceCustDetailData.createValue();
            icdD.setInvoiceCustId(invoiceCustId);
            icdD.setShipStatusCd(RefCodeNames.SHIP_STATUS_CD.PENDING);
            icdD.setLineNumber(ssOrderItem.getLineNumber());
            icdD.setItemUom(ssOrderItem.getUom());
            icdD.setItemSkuNum(ssOrderItem.getSkuNum());
            icdD.setItemShortDesc(ssOrderItem.getShortDesc());
            icdD.setItemQuantity(ssOrderItem.getQuantity());
            icdD.setItemPack(ssOrderItem.getPack());
            icdD.setInvoiceDetailStatusCd(null);
            icdD.setLineTotal(ssOrderItem.getTotal());
            icdD.setCustContractPrice(ssOrderItem.getPrice());
            icdD.setOrderItemId(ssOrderItem.getOrderItem().getOrderItemId());

            if (!pReadOnly) {
                icdD = addCustomerInvoiceItem(pCon, icdD, pUser);
                servicedItemsIsClosed = servicedItemsIsClosed && closeOrderedItem(pCon, invoiceCustD, icdD, ssOrderItem.getOrderItem(), pUser, errors);
            }

            custItems.add(icdD);
        }

        if (!errors.isEmpty()) {
            logInfo("service => customer invoice has errors: " + errors);
            invoiceCustD.setInvoiceStatusCd(RefCodeNames.INVOICE_STATUS_CD.ERP_RELEASED_ERROR);
            if (!pReadOnly) {
                addErrors(errors, pUser);
                updateInvoiceCust(pCon, invoiceCustD, pUser);
            }
        }

        if (!pReadOnly) {
            logInfo("service => Checking if order should be in an invoiced state.");
            //check if the order should be closed out
            if (errors.isEmpty() && servicedItemsIsClosed && orderShouldBeClosed(pCon, order.getOrderId())) {
                closeOrder(pCon, order, pUser);
                logInfo("service => The order is closed out.");
            } else {
                logInfo("service => The order is not closed.");
            }

            invoiceDist.setInvoiceStatusCd(RefCodeNames.INVOICE_STATUS_CD.PROCESS_ERP);
            updateInvoiceDist(pCon, invoiceDist, pUser);
        }

        InvoiceCustDescData result = new InvoiceCustDescData();

        result.setInvoiceCust(invoiceCustD);
        result.setInvoiceCustDetailList(custItems);

        logInfo("service => END.pInvoiceDistId: " + pInvoiceDistId);

        return result;
    }

    private boolean orderShouldBeClosed(Connection pCon, int pOrderId) throws Exception {

        DBCriteria crit = new DBCriteria();
        crit.addEqualTo(OrderItemDataAccess.ORDER_ID, pOrderId);

        for (Object oOrderItem : OrderItemDataAccess.select(pCon, crit)) {
            OrderItemData oid = (OrderItemData) oOrderItem;
            if (Utility.isGoodOrderItemStatus(oid.getOrderItemStatusCd()) && !RefCodeNames.ORDER_ITEM_STATUS_CD.INVOICED.equals(oid.getOrderItemStatusCd())) {
                return false;
            }
        }

        return true;
    }

    private boolean closeOrder(Connection pCon, OrderData order, String pUser) throws Exception {
        order.setOrderStatusCd(RefCodeNames.ORDER_STATUS_CD.INVOICED);
        int r = updateOrder(pCon, order, pUser);
        return r > 0;
    }

    private String getCustomerInvoiceNum(InvoiceDistData invoiceDist) {
        return "#" + invoiceDist.getInvoiceNum();
    }

    private boolean orderPartHasBeenServiced(Connection pCon, int pOrderId) throws Exception {

        DBCriteria dbc = new DBCriteria();

        dbc.addEqualTo(InvoiceCustDataAccess.ORDER_ID, pOrderId);
        IdVector invoiceCustIds = InvoiceCustDataAccess.selectIdOnly(pCon, dbc);

        return !invoiceCustIds.isEmpty();
    }

    private OrderItemData getOrderItem(int orderItemId, OrderItemDataVector items) {
        for (Object oItem : items) {
            OrderItemData item = (OrderItemData) oItem;
            if (orderItemId == item.getOrderItemId()) {
                return item;
            }
        }
        return null;
    }

    private double getSubTotal(SelfServiceOrderItemDescViewVector items) {

        double total = 0;

        for (Object oItem : items) {
            SelfServiceOrderItemDescView item = (SelfServiceOrderItemDescView) oItem;
            total += item.getTotal() != null ? item.getTotal().doubleValue() : 0;
        }

        return total;
    }

    private SelfServiceOrderDescView getSelfServiceOrderDesc(Connection pCon,
                                                             OrderData pOrder,
                                                             OrderItemDataVector pOrderItems,
                                                             InvoiceDistDetailDataVector invDistItems) throws Exception {

        int orderId = pOrder.getOrderId();

        VerifyBillingForOrder ver = new VerifyBillingForOrder(pCon, pOrder);

        OrderAddressData billingAddress = ver.getBillingAddress();

        if (ver.hasError()) {
            String errorMess = ver.getError() + " Order id: " + orderId;
            logError(errorMess);
            throw new Exception(errorMess);
        }

        if (!ver.readyForPO()) {
            // No ERP order number.
            String errorMess = "No ERP order number.Order Id:" + orderId;
            logError(errorMess);
            throw new Exception(errorMess);
        }

        OrderAddressData shippingAddress = getOrderAddress(pCon, orderId, RefCodeNames.ADDRESS_TYPE_CD.SHIPPING);

        DBCriteria dbcom = new DBCriteria();
        dbcom.addEqualTo(OrderMetaDataAccess.ORDER_ID, orderId);
        OrderMetaDataVector omdv = OrderMetaDataAccess.select(pCon, dbcom);

        return getSelfServiceOrderDesc(pOrder, pOrderItems, invDistItems, shippingAddress, billingAddress, omdv);
    }

    private SelfServiceOrderDescView getSelfServiceOrderDesc(OrderData pOrder,
                                                             OrderItemDataVector pOrderItems,
                                                             InvoiceDistDetailDataVector invDistItems,
                                                             OrderAddressData pShippingAddress,
                                                             OrderAddressData pBillingAddress,
                                                             OrderMetaDataVector pMeta) throws Exception {

        SelfServiceOrderDescView ssOrder = new SelfServiceOrderDescView();

        ssOrder.setClwOrderNum(pOrder.getOrderNum());

        BigDecimal totalFreigthCost = Utility.bdNN(pOrder.getTotalFreightCost());
        BigDecimal totalMiscCost = Utility.bdNN(pOrder.getTotalMiscCost());
        BigDecimal rushCharge = Utility.bdNN(pOrder.getTotalRushCharge());

        // Roll all possible freight and handling charges into 1 cost.
        BigDecimal shippingCost = new BigDecimal(0);
        shippingCost = shippingCost.add(totalFreigthCost);
        shippingCost = shippingCost.add(totalMiscCost);
        shippingCost = shippingCost.add(rushCharge);

        ssOrder.setShippingCost(shippingCost);

        String name = Utility.strNN(pOrder.getOrderSiteName());

        //temporary hack to not charge fuel surcharge if name of site has will call in it
        // (will call indicates customer is picking up at distributor warehouse).
        if (name.toLowerCase().indexOf("will call") == -1) {
            if (pMeta != null) {
                for (Object aPMeta : pMeta) {
                    OrderMetaData om = (OrderMetaData) aPMeta;
                    if (om.getValue() != null && RefCodeNames.CHARGE_CD.FUEL_SURCHARGE.equals(om.getName())) {
                        ssOrder.setFuelSurcharge(new BigDecimal(om.getValue().trim()));
                        break;
                    }
                }
            }
        }

        ssOrder.setShipNotes(pOrder.getComments());
        ssOrder.setItems(getSelfServiceOrderItems(pOrderItems, invDistItems));
        ssOrder.setShippingAddress(pShippingAddress);
        ssOrder.setBillingAddress(pBillingAddress);

        ssOrder.setSubTotal(new BigDecimal(getSubTotal(ssOrder.getItems())));

        return ssOrder;
    }

    private SelfServiceOrderItemDescViewVector getSelfServiceOrderItems(OrderItemDataVector pOrderItems,
                                                                        InvoiceDistDetailDataVector invDistItems) throws Exception {

        SelfServiceOrderItemDescViewVector ssOrderItems = new SelfServiceOrderItemDescViewVector();

        for (Object oInvDistItem : invDistItems) {

            InvoiceDistDetailData invDistItem = (InvoiceDistDetailData) oInvDistItem;

            OrderItemData oiD = getOrderItem(invDistItem.getOrderItemId(), pOrderItems);

            if (oiD != null) {

                double custContractPrice = oiD.getCustContractPrice() == null ? 0 : oiD.getCustContractPrice().doubleValue();
                double totalPrice = custContractPrice * invDistItem.getDistItemQuantity();

                SelfServiceOrderItemDescView ssOrderItemDesc = SelfServiceOrderItemDescView.createValue();
                ssOrderItemDesc.setOrderItem(oiD);
                ssOrderItemDesc.setCost(invDistItem.getItemCost());
                ssOrderItemDesc.setPrice(oiD.getCustContractPrice());
                ssOrderItemDesc.setQuantity(invDistItem.getDistItemQuantity());
                ssOrderItemDesc.setShortDesc(oiD.getItemShortDesc());
                ssOrderItemDesc.setSkuNum(oiD.getItemSkuNum());
                ssOrderItemDesc.setLineNumber(oiD.getOrderLineNum());
                ssOrderItemDesc.setUom(oiD.getItemUom());
                ssOrderItemDesc.setPack(oiD.getItemPack());
                ssOrderItemDesc.setTotal(new BigDecimal(totalPrice));

                ssOrderItems.add(ssOrderItemDesc);

            } else {

                String excMessage = "No order item found for distributor invoice item." +
                        " Dist.Invoice Id: " + invDistItem.getInvoiceDistId() +
                        " ,Dist.Item SKU: " + invDistItem.getDistItemSkuNum() + ".";
                throw new Exception(excMessage);

            }

        }

        return ssOrderItems;
    }

    private OrderAddressData getOrderAddress(Connection conn, int pOrderId, String pAddrType) throws Exception {

        DBCriteria dbc = new DBCriteria();
        dbc.addEqualTo(OrderAddressDataAccess.ORDER_ID, pOrderId);
        dbc.addEqualTo(OrderAddressDataAccess.ADDRESS_TYPE_CD, pAddrType);

        OrderAddressDataVector addresses = OrderAddressDataAccess.select(conn, dbc);

        if (addresses.size() == 0) {
            String errorMess = "No shipping address found. Order id: " + pOrderId;
            logError(errorMess);
            throw new Exception(errorMess);
        }

        if (addresses.size() > 1) {
            String errorMess = "The order has more than one shipping address. Order id: " + pOrderId;
            logError(errorMess);
            throw new Exception(errorMess);
        }

        return (OrderAddressData) addresses.get(0);

    }

    /**
     * Verify that the quantity invoiced (including the quantities from
     * any pre-existing released invoices) doesn't exceed the quantity ordered.
     * If so, log an exception.  If on the other hand, the two quantities
     * are now equal, update the order item status to INVOICED.
     *
     * @param pCon            connection
     * @param pInvoiceCust    customer invoice
     * @param pInvoiceCustDet invoiced item
     * @param pOrderItem      ordered item
     * @param pUser           user
     * @param pErrors         if an errors
     * @return success flag
     * @throws Exception if an unexpected exceptions
     */
    private boolean closeOrderedItem(Connection pCon,
                                     InvoiceCustData pInvoiceCust,
                                     InvoiceCustDetailData pInvoiceCustDet,
                                     OrderItemData pOrderItem,
                                     String pUser,
                                     Vector<OrderPropertyData> pErrors) throws Exception {

        // Sum up all the quantities previously invoiced for the order item
        int quantityInvoiced = getQuantityInvoiced(pCon, pInvoiceCustDet.getOrderItemId());
        // Get the quantity ordered
        int quantityOrdered = pOrderItem.getTotalQuantityOrdered();

        //compare.if the invoiced quantity is greater, add an error.
        if (quantityInvoiced > quantityOrdered) {

            String errorMess = "Quantity invoiced (" + quantityInvoiced + ") exceeds quantity ordered (" + quantityOrdered + ").";
            pErrors.add(buildInvoiceError(pInvoiceCust, pInvoiceCustDet, errorMess));

            return false;

        } else {

            // Otherwise all is well.  Update the order item record with the
            // updated quantity shipped and also set the status to INVOICED
            // if the total quantity is now accounted for.
            pOrderItem.setTotalQuantityShipped(quantityInvoiced);
            if (quantityInvoiced == quantityOrdered) {
                pOrderItem.setOrderItemStatusCd(RefCodeNames.ORDER_ITEM_STATUS_CD.INVOICED);
            }

            int r = updateOrderItem(pCon, pOrderItem, pUser);

            return r > 0;

        }
    }

    // Customer invoice version
    private OrderPropertyData buildInvoiceError(InvoiceCustData pInvoiceCust,
                                                InvoiceCustDetailData pInvoiceCustDet,
                                                String pErrorMess) {

        OrderPropertyData orderPropertyD = OrderPropertyData.createValue();

        orderPropertyD.setOrderPropertyTypeCd(RefCodeNames.ORDER_PROPERTY_TYPE_CD.ORDER_NOTE);
        orderPropertyD.setOrderPropertyStatusCd(RefCodeNames.ORDER_PROPERTY_STATUS_CD.ACTIVE);
        orderPropertyD.setShortDesc(RefCodeNames.ORDER_EXCEPTION_TYPE_CD.INVOICE_FROM_ERP);

        // Truncate if greater than length of database field.
        if (pErrorMess != null && pErrorMess.length() > 2000) {
            orderPropertyD.setValue(pErrorMess.substring(0, 2000));
        } else {
            orderPropertyD.setValue(pErrorMess);
        }

        orderPropertyD.setInvoiceCustId(pInvoiceCust.getInvoiceCustId());
        orderPropertyD.setOrderId(pInvoiceCust.getOrderId());

        if (pInvoiceCustDet != null) {
            orderPropertyD.setInvoiceCustDetailId(pInvoiceCustDet.getInvoiceCustDetailId());
            orderPropertyD.setOrderItemId(pInvoiceCustDet.getOrderItemId());
        }

        return orderPropertyD;
    }

    private void addError(Connection pCon, OrderPropertyData pOrderProperty, String pUser) throws Exception {

        pOrderProperty.setAddBy(pUser);
        pOrderProperty.setModBy(pUser);

        OrderPropertyDataAccess.insert(pCon, pOrderProperty);
    }

    private void addErrors(List<OrderPropertyData> pErrors, String pUser) {
        Connection con = null;
        try {
            con = getConnection();
            for (OrderPropertyData error : pErrors) {
                addError(con, error, pUser);
            }
        } catch (Exception exc) {
            String errorMess = exc.getMessage();
            logError(errorMess);
        } finally {
            closeConnection(con);
        }
    }

    private int updateOrderItem(Connection pCon, OrderItemData pData, String pUser) throws Exception {
        pData.setModBy(pUser);
        return OrderItemDataAccess.update(pCon, pData);
    }

    private int updateOrder(Connection pCon, OrderData pData, String pUser) throws Exception {
        pData.setModBy(pUser);
        return OrderDataAccess.update(pCon, pData);
    }

    private int updateInvoiceDist(Connection pCon, InvoiceDistData pData, String pUser) throws Exception {
        pData.setModBy(pUser);
        return InvoiceDistDataAccess.update(pCon, pData);
    }

    private int updateInvoiceCust(Connection pCon, InvoiceCustData pData, String pUser) throws Exception {
        pData.setModBy(pUser);
        return InvoiceCustDataAccess.update(pCon, pData);
    }

    private InvoiceCustDetailData addCustomerInvoiceItem(Connection pCon, InvoiceCustDetailData icdD, String pUser) throws Exception {
        icdD.setAddBy(pUser);
        icdD.setModBy(pUser);
        return InvoiceCustDetailDataAccess.insert(pCon, icdD);
    }

    private InvoiceCustData addCustomerInvoice(Connection pCon, InvoiceCustData pInvoice, String pUser) throws Exception {
        pInvoice.setAddBy(pUser);
        pInvoice.setModBy(pUser);
        return InvoiceCustDataAccess.insert(pCon, pInvoice);
    }

    private int getQuantityInvoiced(Connection pCon, int pOrderItemId) throws Exception {

        int quantityInvoiced = 0;

        String sql = "SELECT SUM(" + InvoiceCustDetailDataAccess.ITEM_QUANTITY + ") " +
                "FROM " + InvoiceCustDetailDataAccess.CLW_INVOICE_CUST_DETAIL +
                " WHERE  " + InvoiceCustDetailDataAccess.ORDER_ITEM_ID + " = ? " +
                " AND " + InvoiceCustDetailDataAccess.INVOICE_CUST_ID +
                " NOT IN (SELECT " + InvoiceCustDataAccess.INVOICE_CUST_ID + " FROM " + InvoiceCustDataAccess.CLW_INVOICE_CUST + " " +
                " WHERE INVOICE_STATUS_CD =  '" + RefCodeNames.INVOICE_STATUS_CD.CANCELLED + "')";

        PreparedStatement pstmt = pCon.prepareStatement(sql);
        pstmt.setInt(1, pOrderItemId);

        logDebug("getQuantityInvoiced => SQL: " + sql);
        logDebug("getQuantityInvoiced => param[pOrderItemId]: " + pOrderItemId);

        ResultSet rs = pstmt.executeQuery();
        if (rs.next()) {
            quantityInvoiced = rs.getInt(1);
        }

        return quantityInvoiced;

    }


}
