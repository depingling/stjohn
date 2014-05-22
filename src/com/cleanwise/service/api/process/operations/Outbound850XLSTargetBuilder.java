package com.cleanwise.service.api.process.operations;

import com.cleanwise.service.api.reporting.ReportingUtils;
import com.cleanwise.service.api.util.RefCodeNames;
import com.cleanwise.service.api.util.Utility;
import com.cleanwise.service.api.util.IOUtilities;
import com.cleanwise.service.api.value.*;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

import jxl.write.WritableWorkbook;
import jxl.Workbook;

/**
 * Title:        Outbound850XLSTargetBuilder
 * Description:  Constructs a xls document in the form of a xls document.
 * Purpose:      Construct a outbound purchase order xls.
 *
 * @author veronika
 */

public class Outbound850XLSTargetBuilder extends XlsBuilder {

    private static String className="Outbound850XLSTargetBuilder";

    // hardcoded values
    private static String CSO_CLEANING_SUPPLIES = "CSO CLEANING SUPPLIES";
    private static String BLANK = "";
    private static String EXPENSE = "Expense";
    private static String SINGLE_LOCATION = "Single Location";


    private static final char ADRESS_DELIM = 32;

    public Outbound850XLSTargetBuilder() {};


    public void outXls(XlsPoResultTarget pPoResult,
                       OutputStream pOut) throws IOException {
            try {
                // write to xsl
                WritableWorkbook workbook = Workbook.createWorkbook(pOut);

                GenericReportResultView reqDetail = GenericReportResultView.createValue();
                reqDetail.setTable(pPoResult.getDetailBody());
                reqDetail.setColumnCount(pPoResult.getDetailHeader().size());
                reqDetail.setHeader(pPoResult.getDetailHeader());
                writeExcelReport(reqDetail, "ZTGTRequisitionDetail", workbook);

                GenericReportResultView reqHeader = GenericReportResultView.createValue();
                reqHeader.setTable(pPoResult.getHeaderBody());
                reqHeader.setColumnCount(pPoResult.getHeaderHeader().size());
                reqHeader.setHeader(pPoResult.getHeaderHeader());
                writeExcelReport(reqHeader, "ZTGTRequisitionHeader", workbook);

                workbook.write();
                workbook.close();

            } catch (Exception e) {
                e.printStackTrace();
                throw new IOException(e.getMessage());
            }
    }

    public XlsPoResultTarget makeXlsResult(String customerRefCode,
                               OutboundEDIRequestDataVector p850s) {

        if (p850s.size() == 0) {
            throw new IllegalArgumentException("OutboundEDIRequestDataVector size cannot be emtpy");
        }
        ArrayList poList = new ArrayList();

        for (int i = 0, len = p850s.size(); i < len; i++) {
            OutboundEDIRequestData ediReq = (OutboundEDIRequestData) p850s.get(i);

            Outbound850XLSTargetBuilder.XlsPoStructTarget po =
                    new Outbound850XLSTargetBuilder.XlsPoStructTarget
                            (ediReq.getOrderItemDV(),
                                    ediReq.getSiteProperties());
            poList.add(po);
        }
        XlsPoResultTarget result = new XlsPoResultTarget();

        result.setHeaderHeader(makeXlsHeader());
        result.setHeaderBody(makeXlsHeaderBody(poList));

        result.setDetailHeader(makeXlsDetailHeader());
        result.setDetailBody(makeXlsDetailBody(poList, customerRefCode));

        return result;
    }

    public ArrayList makeXlsHeaderBody(ArrayList poList) {
       ArrayList tableData=new ArrayList();

       Iterator i = poList.iterator();
       while (i.hasNext()) {
           XlsPoStructTarget poStruct = (XlsPoStructTarget)i.next();

           String accountLocation;
            if(poStruct.getSiteProperties()!=null){
                String siteRef = Utility.getPropertyValue(poStruct.getSiteProperties(),RefCodeNames.PROPERTY_TYPE_CD.SITE_REFERENCE_NUMBER);
                if(siteRef == null){
                    accountLocation = "";
                } else {
                    if (siteRef.length() >= 8) {
                        accountLocation = siteRef.substring(4,8);
                    } else {
                        accountLocation = siteRef;
                    }
                }
            }else{
                accountLocation = "null";
            }
            poStruct.setSeqNo(accountLocation);

            ArrayList row = new ArrayList();

            row.add(accountLocation);       //SeqNo
            row.add(CSO_CLEANING_SUPPLIES); // Title
            row.add(BLANK);                 // Requester
            row.add(EXPENSE);               // TypeOfRequisition
            row.add(SINGLE_LOCATION);       // AccountLocationType
            row.add(accountLocation);       // AccountLocation
            row.add(BLANK);                 // ShipToLocation
            row.add(BLANK);                 // ActivityCode
            row.add(BLANK);                 // StoreGroup
            row.add(BLANK);                 // OtherRequisition
            row.add(BLANK);                 // ProjectCode
            row.add(BLANK);                 // WarehouseAccount
            row.add(BLANK);                 // DeliveryDate
            row.add(BLANK);                 // NRSCProjectNo

            tableData.add(row);
       }
       return tableData;
    }

    public ArrayList makeXlsDetailBody(ArrayList poList, String customerRefCode) {
        ArrayList tableData=new ArrayList();
        Iterator i = poList.iterator();
        while (i.hasNext()) {
            XlsPoStructTarget poStruct = (XlsPoStructTarget)i.next();

            Iterator itar=poStruct.getOrderItemDV().iterator();
            while(itar.hasNext()){

                OrderItemData oid = (OrderItemData)itar.next();
                int qty = getPoQty(oid);

                ArrayList row = new ArrayList();
                row.add(poStruct.getSeqNo());           // SeqNo
                row.add(BLANK);                         // Blank Column
                row.add(customerRefCode);  // SupplierID
                row.add(oid.getCustItemSkuNum());       // TargetPartNumber
                row.add(BLANK);                         // SupplierPartNo
                row.add(BLANK);                         // KitNumber
                row.add(BLANK);                         // DeliveryDate
                row.add(qty);                           // Quantity

                tableData.add(row);
            }
        }
        return tableData;
    }

    private int getPoQty(OrderItemData oi) {
        if (oi.getDistItemQuantity() > 0) {
            return oi.getDistItemQuantity();
        } else {
            return oi.getTotalQuantityOrdered();
        }
    }

    public GenericReportColumnViewVector makeXlsHeader() {

        GenericReportColumnViewVector header = new GenericReportColumnViewVector();

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "SeqNo",0,38, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Title", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Requester", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "TypeOfRequisition", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "AccountLocationType", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "AccountLocation", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "ShipToLocation", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "ActivityCode", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "StoreGroup", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "OtherRequisition", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "ProjectCode", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "WarehouseAccount", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "DeliveryDate", 0, 0, "DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "NRSCProjectNo", 0, 255, "VARCHAR2"));

        return header;
    }

    public GenericReportColumnViewVector makeXlsDetailHeader() {

        GenericReportColumnViewVector header = new GenericReportColumnViewVector();

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "SeqNo",0,38, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "SupplierID", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "TargetPartNumber", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "SupplierPartNo", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "KitNumber", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "DeliveryDate", 0, 0, "DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Quantity", 2, 20, "NUMBER", "*", false));

        return header;
    }

    /**
     * Private inner class to represent the data the po needs to print, and the
     * relationship between the data.  This makes converting between different
     * objects (outboundEdiRequests, and PurchaseOrderDesc/orderItemVectors, etc.)
     */
    public class XlsPoStructTarget {
        private OrderItemDataVector mOrderItemDV;
        private PropertyDataVector mSiteProps;
        private String seqNo;

        public XlsPoStructTarget(OrderItemDataVector pOrderItemDV,
                           PropertyDataVector pSiteProps
                           ) {
            mOrderItemDV = pOrderItemDV;
            mSiteProps = pSiteProps;
        }

        private OrderItemDataVector getOrderItemDV() {
            return mOrderItemDV;
        }

        public PropertyDataVector getSiteProperties() {
        	return mSiteProps;
        }

        public void setSeqNo(String v) {
            seqNo = v;
        }

        public String getSeqNo() {
            return seqNo;
        }

    }

    public class XlsPoResultTarget {
        private GenericReportColumnViewVector headerHeader;
        private ArrayList headerBody;
        private GenericReportColumnViewVector detailHeader;
        private ArrayList detailBody;

        public XlsPoResultTarget() {}

        public void setHeaderHeader(GenericReportColumnViewVector v) {
            headerHeader = v;
        }

        public GenericReportColumnViewVector getHeaderHeader() {
            return headerHeader;
        }

        public void setHeaderBody(ArrayList v) {
            headerBody = v;
        }

        public ArrayList getHeaderBody() {
            return headerBody;
        }

        public void setDetailHeader(GenericReportColumnViewVector v) {
            detailHeader = v;
        }

        public GenericReportColumnViewVector getDetailHeader() {
            return detailHeader;
        }

        public void setDetailBody(ArrayList v) {
            detailBody = v;
        }

        public ArrayList getDetailBody() {
            return detailBody;
        }

    }


}

