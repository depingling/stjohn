package com.cleanwise.service.api.process.operations;

import com.cleanwise.service.api.value.*;
import java.io.*;
import java.util.*;

import jxl.Workbook;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import org.apache.log4j.Logger;

public class Outbound850XLSBuilder_JD_FB {
    private static final Logger log = Logger.getLogger(Outbound850XLSBuilder_JD_FB.class);
    
    private final static String DELIMITER_LINE = "********************************************************************************************************************************************************************************************************";

    private static String className = "Outbound850XLSBuilder_JD_FB";

    public Outbound850XLSBuilder_JD_FB() {
    }

    /**
     * Takes in an instance of
     * 
     * @see OutboundEDIRequestData,
     * @see DistributorData, and
     * @see StoreData and converts them into an outbound purchase order xls.
     *      This document looks very similar to the output when printing from
     *      directly from Lawson.
     * 
     * @param pDist
     *            the distributor this fax is going to.
     * @param p850s
     *            the purchase orders to translate.
     * @param pStore
     *            the store this po is comming from.
     * @param pOut
     *            the output stream to write the xls to.
     * @throws java.io.IOException
     *             if any error occurs.
     */
    public void constructXlsPO(DistributorData pDist,
            OutboundEDIRequestDataVector p850s, StoreData pStore,
            OutputStream pOut) throws Exception {
        if (p850s.size() == 0) {
            throw new IllegalArgumentException(
                    "OutboundEDIRequestDataVector size cannot be emtpy");
        }
        WritableCellFormat header = new WritableCellFormat(new WritableFont(
                WritableFont.TIMES, 10, WritableFont.BOLD, false));
        final List data = new ArrayList();
        int row = 0;
        for (int i = 0, len = p850s.size(); i < len; i++) {
            OutboundEDIRequestData ediReq = (OutboundEDIRequestData) p850s
                    .get(i);
            if (ediReq.getPurchaseOrderD().getOutboundPoNum() == null) {
                throw new NullPointerException("'OutboundPoNum' is NULL in PO "
                        + ediReq.getPurchaseOrderD().getPurchaseOrderId() + "!");
            }
            row = generateAddress(data, ediReq.getShipAddr(), ediReq
                    .getBillAddr(), row, header, ediReq.getPurchaseOrderD()
                    .getOutboundPoNum(), ediReq.getPurchaseOrderD().getPoDate());
            row++;
            data.add(new Label(0, row, "Comments", header));
            data.add(new Label(1, row, ediReq.getOrderD().getComments()));
            row = generateItems(data, ediReq.getOrderItemDV(), row + 2, header);
            if (ediReq.getOrderCreditCard() != null)
            	row = generateCreditCard(data, ediReq.getOrderCreditCard(),
                    row + 2, header);
            row++;
            if (i != len - 1) {
                data.add(new Label(0, row, DELIMITER_LINE));
                row++;
            }
        }
        WritableCell[] cells = (WritableCell[]) data
                .toArray(new WritableCell[0]);
        WritableWorkbook workbook = Workbook.createWorkbook(pOut);
        WritableSheet sheet = workbook.createSheet("Sheet0", 0);
        for (int i = 0; i < cells.length; i++) {
            sheet.addCell(cells[i]);
        }
        workbook.write();
        workbook.close();
    }

    private int generateItems(final List cells, OrderItemDataVector items,
            int startRow, WritableCellFormat header) {
        int col = 0;
        int row = startRow;
        if (items != null && items.size() > 0) {
            cells.add(new Label(col++, row, "Items", header));
            cells.add(new Label(col++, row, "Line", header));
            cells.add(new Label(col++, row, "SKU", header));
            cells.add(new Label(col++, row, "Desc", header));
            cells.add(new Label(col++, row, "UOM", header));
            cells.add(new Label(col++, row, "Pack", header));
            cells.add(new Label(col++, row, "QTY", header));
            cells.add(new Label(col++, row, "Price", header));
            cells.add(new Label(col++, row, "Ext Price", header));
            for (int i = 0; i < items.size(); i++) {
                col = 1;
                row++;
                OrderItemData item = (OrderItemData) items.get(i);
                cells.add(new Number(col++, row, (i + 1)));
                cells.add(new Label(col++, row, item.getDistItemSkuNum()));
                cells.add(new Label(col++, row, item.getItemShortDesc()));
                cells.add(new Label(col++, row, item.getDistItemUom()));
                cells.add(new Label(col++, row, item.getDistItemPack()));
                int qty = item.getTotalQuantityOrdered();
                double price = item.getDistItemCost().doubleValue();
                cells.add(new Number(col++, row, qty));
                cells.add(new Number(col++, row, price));
                cells.add(new Number(col++, row, qty * price));
            }
        }
        return row;
    }

    private int generateCreditCard(final List cells, OrderCreditCardData item,
            int startRow, WritableCellFormat header) {
        int col = 0;
        int row = startRow;
        cells.add(new Label(col++, row, "Credit Card Number", header));
        cells.add(new Label(col++, row, "Credit Card Type", header));
        cells.add(new Label(col++, row, "Exp Year", header));
        cells.add(new Label(col++, row, "Exp Month", header));
        cells.add(new Label(col++, row, "Name", header));
        cells.add(new Label(col++, row, "Address 1", header));
        cells.add(new Label(col++, row, "Address 2", header));
        cells.add(new Label(col++, row, "Address 3", header));
        cells.add(new Label(col++, row, "Address 4", header));
        cells.add(new Label(col++, row, "City", header));
        cells.add(new Label(col++, row, "Province Code", header));
        cells.add(new Label(col++, row, "Postal Code", header));
        col = 0;
        row++;
        cells.add(new Label(col++, row, item.getEncryptedCreditCardNumber()));
        cells.add(new Label(col++, row, item.getCreditCardType()));
        cells.add(new Number(col++, row, item.getExpYear()));
        cells.add(new Number(col++, row, item.getExpMonth()));
        cells.add(new Label(col++, row, item.getName()));
        cells.add(new Label(col++, row, item.getAddress1()));
        cells.add(new Label(col++, row, item.getAddress2()));
        cells.add(new Label(col++, row, item.getAddress3()));
        cells.add(new Label(col++, row, item.getAddress4()));
        cells.add(new Label(col++, row, item.getCity()));
        cells.add(new Label(col++, row, item.getStateProvinceCd()));
        cells.add(new Label(col++, row, item.getPostalCode()));
        return row;
    }

    private int generateAddress(final List cells, OrderAddressData shipAddr,
            OrderAddressData billAddr, int startRow, WritableCellFormat header,
            String poNum, Date poDate) {
        int col = 0;
        int row = startRow;
        cells.add(new Label(col++, row, "PO Number", header));
        cells.add(new Label(col++, row, "PO Date", header));
        cells.add(new Label(col++, row, "Ship Address1", header));
        cells.add(new Label(col++, row, "Address2", header));
        cells.add(new Label(col++, row, "Address3", header));
        cells.add(new Label(col++, row, "Address4", header));
        cells.add(new Label(col++, row, "City", header));
        cells.add(new Label(col++, row, "Province Code", header));
        cells.add(new Label(col++, row, "Postal Code", header));
        col = 0;
        row = startRow + 1;
        cells.add(new Label(col++, row, poNum));
        cells.add(new DateTime(col++, row, poDate, new WritableCellFormat(
                new DateFormat("MM/dd/yyyy"))));
        cells.add(new Label(col++, row, shipAddr.getAddress1()));
        cells.add(new Label(col++, row, shipAddr.getAddress2()));
        cells.add(new Label(col++, row, shipAddr.getAddress3()));
        cells.add(new Label(col++, row, shipAddr.getAddress4()));
        cells.add(new Label(col++, row, shipAddr.getCity()));
        cells.add(new Label(col++, row, shipAddr.getStateProvinceCd()));
        cells.add(new Label(col++, row, shipAddr.getPostalCode()));
        col = 2;
        row = startRow + 2;
        cells.add(new Label(col++, row, "Bill Address1", header));
        cells.add(new Label(col++, row, "Address2", header));
        cells.add(new Label(col++, row, "Address3", header));
        cells.add(new Label(col++, row, "Address4", header));
        cells.add(new Label(col++, row, "City", header));
        cells.add(new Label(col++, row, "Province Code", header));
        col = 2;
        row = startRow + 3;
        cells.add(new Label(col++, row, billAddr.getAddress1()));
        cells.add(new Label(col++, row, billAddr.getAddress2()));
        cells.add(new Label(col++, row, billAddr.getAddress3()));
        cells.add(new Label(col++, row, billAddr.getAddress4()));
        cells.add(new Label(col++, row, billAddr.getCity()));
        cells.add(new Label(col++, row, billAddr.getStateProvinceCd()));
        cells.add(new Label(col++, row, billAddr.getPostalCode()));
        return row;
    }


    /**
     * Error logging
     * 
     * @param message -
     *            message which will be pasted to log
     * @param e -
     *            Excepiton
     */
    private static void error(String message, Exception e) {

        log.info("ERROR in " + className + " :: " + message);

        String errorMessage;
        StringWriter wr = new StringWriter();
        PrintWriter prW = new PrintWriter(wr);
        e.printStackTrace(prW);
        errorMessage = wr.getBuffer().toString();

        log.info("ERROR in " + className + " :: " + errorMessage);
    }
}
