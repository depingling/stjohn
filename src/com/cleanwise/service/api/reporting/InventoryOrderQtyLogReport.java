package com.cleanwise.service.api.reporting;

import com.cleanwise.service.api.value.GenericReportResultViewVector;
import com.cleanwise.service.api.value.GenericReportData;
import com.cleanwise.service.api.value.GenericReportColumnViewVector;
import com.cleanwise.service.api.value.GenericReportResultView;
import com.cleanwise.service.api.util.ConnectionContainer;

import java.util.*;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.io.StringWriter;
import java.io.PrintWriter;

import org.apache.log4j.Logger;

/**
 * Title:        InventoryOrderQtyLogReport
 * Description:  The report of quantity of the ordered items after processing
 * Purpose:      To give the report about item quantity after the inventory order processing
 * Copyright:    Copyright (c) 2007
 * Company:      CleanWise, Inc.
 * Date:         14.12.2007
 * Time:         15:07:55
 *
 * @author Alexander Chickin, Evgeny Vlasov, TrinitySoft, Inc.
 */


public class InventoryOrderQtyLogReport implements GenericReportMulti {
    private static final Logger log = Logger.getLogger(InventoryOrderQtyLogReport.class);
    private static String className = "InventoryOrderQtyLogReport";

    public GenericReportResultViewVector process(ConnectionContainer pCons, GenericReportData pReportData, Map pParams) throws Exception {

        Connection con = pCons.getDefaultConnection();
        GenericReportResultViewVector resultV = new GenericReportResultViewVector();
        Statement stmt;
        PreparedStatement pstmt;
        ResultSet rs;

        String begDateS  = (String) pParams.get("BEG_DATE");
        String endDateS  = (String) pParams.get("END_DATE");
        String siteIdStr = (String) pParams.get("SITE");

        String sql = "select cutoff_date from clw_inventory_order_qty where cutoff_date" +
                " between to_date('" + begDateS + "' ,'mm/dd/yyyy')" +
                " and to_date('" + endDateS + "' ,'mm/dd/yyyy') " +
                " and bus_entity_id = " + siteIdStr +
                " group by cutoff_date";

        log.info("process => SQL: "+sql);

        stmt = con.createStatement();
        rs = stmt.executeQuery(sql);

        ArrayList cuttofDates = new ArrayList();
        while (rs.next()) {
            cuttofDates.add(rs.getDate(1));
        }

        sql = "select ITEM_ID," +
                " ORDER_ID," +
                " ADD_DATE," +
                " AUTO_ORDER_FACTOR," +
                " CUTOFF_DATE," +
                " ENABLE_AUTO_ORDER," +
                " INVENTORY_QTY," +
                " ITEM_TYPE," +
                " ORDER_QTY," +
                " PAR," +
                " QTY_ON_HAND," +
                " AUTO_ORDER_APPLIED FROM CLW_INVENTORY_ORDER_QTY WHERE CUTOFF_DATE = ?" +
                " AND BUS_ENTITY_ID = " + siteIdStr;

        rs.close();
        stmt.close();
        
        log.info("process => SQL: "+sql);
        pstmt = con.prepareStatement(sql);

        HashMap linesMap = new HashMap();
        Iterator it = cuttofDates.iterator();
        while (it.hasNext()) {

            QtyLineVector lines = new QtyLineVector();
            Date cutoffDate = (Date) it.next();

            pstmt.setDate(1, new java.sql.Date(cutoffDate.getTime()));
            rs = pstmt.executeQuery();

            while (rs.next()) {
                QtyLine line = new QtyLine();

                line.mItemId = rs.getInt("ITEM_ID");
                line.mOrderId = rs.getInt("ORDER_ID");
                line.mOrderQty = rs.getInt("ORDER_QTY");
                line.mPar = rs.getInt("PAR");
                line.mQtyOnHand = rs.getString("QTY_ON_HAND");
                line.mInventoryQty = rs.getString("INVENTORY_QTY");
                line.mItemType = rs.getString("ITEM_TYPE");
                line.mAutoOrderApplied = rs.getString("AUTO_ORDER_APPLIED");
                line.mEnableAutoOrder = rs.getString("ENABLE_AUTO_ORDER");
                line.mAutoOrderFactor = rs.getBigDecimal("AUTO_ORDER_FACTOR");
                line.mCutoffDate = rs.getDate("CUTOFF_DATE");
                line.mAddDate = rs.getDate("ADD_DATE");

                lines.add(line);
            }
            rs.close();
            linesMap.put(cutoffDate.toString(), lines);
        }

        pstmt.close();

        it = cuttofDates.iterator();
        while (it.hasNext()) {
            Date cutoffDate = (Date) it.next();

            GenericReportResultView result = GenericReportResultView.createValue();
            result.setTable(new ArrayList());
            QtyLineVector qtyLines = (QtyLineVector) linesMap.get(cutoffDate.toString());
            Iterator iter = qtyLines.iterator();
            while (iter.hasNext()) {
                QtyLine line = (QtyLine) iter.next();
                result.getTable().add(line.toList());
            }
            GenericReportColumnViewVector lineHeader = getInventoryQtyLogHeader();
            result.setColumnCount(lineHeader.size());
            result.setHeader(lineHeader);
            result.setName(cutoffDate.toString());
            resultV.add(result);

        }
        return resultV;

    }

    private GenericReportColumnViewVector getInventoryQtyLogHeader() {

        GenericReportColumnViewVector header = new GenericReportColumnViewVector();

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Order Id", 0, 38, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Item Id", 0, 38, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Item Type", 0, 255, "VARCHAR2"));

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Par", 0, 38, "NUMBER"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Qty On Hand", 0, 255, "VARCHAR2"));

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Inventory Qty", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.Integer", "Order Qty", 0, 38, "NUMBER"));

        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Enable Auto Order", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.lang.String", "Auto Order Applied", 0, 255, "VARCHAR2"));
        header.add(ReportingUtils.createGenericReportColumnView("java.math.BigDecimal", "Auto Order Factor", 2, 20, "NUMBER", "*", false));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "Cutoff Date", 0, 0, "DATE"));
        header.add(ReportingUtils.createGenericReportColumnView("java.sql.Timestamp", "Add Date", 0, 0, "DATE"));

        return header;
    }

    private class QtyLine {
        public int mItemId;
        public int mOrderId;
        public java.math.BigDecimal mAutoOrderFactor;
        public Date mCutoffDate;
        public String mEnableAutoOrder;
        public String mInventoryQty;
        public String mItemType;
        public int mOrderQty;
        public int mPar;
        public String mQtyOnHand;
        public String mAutoOrderApplied;
        public Date mAddDate;

        public ArrayList toEmptyList() {
            ArrayList list = new ArrayList();
            list.add(null);
            list.add(null);
            list.add(null);
            list.add(null);
            list.add(null);
            list.add(null);
            list.add(null);
            list.add(null);
            list.add(null);
            list.add(null);
            list.add(null);
            list.add(null);
            return list;
        }

        public ArrayList toList() {
            ArrayList list = new ArrayList();
            list.add(new Integer(mOrderId));
            list.add(new Integer(mItemId));
            list.add(mItemType);
            list.add(new Integer(mPar));
            list.add(mQtyOnHand);
            list.add(mInventoryQty);
            list.add(new Integer(mOrderQty));
            list.add(mEnableAutoOrder);
            list.add(mAutoOrderApplied);
            list.add(mAutoOrderFactor);
            list.add(mCutoffDate);
            list.add(mAddDate);
            return list;
        }
    }

    public class QtyLineVector extends ArrayList {

    }


    /**
     * Error logging
     *
     * @param message - message which will be pasted to log
     * @param e       - Excepiton
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
